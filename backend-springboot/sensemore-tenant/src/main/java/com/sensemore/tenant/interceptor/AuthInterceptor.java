package com.sensemore.tenant.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensemore.tenant.context.TenantContext;
import com.sensemore.tenant.dto.ApiResponse;
import com.sensemore.tenant.entity.UserToken;
import com.sensemore.tenant.service.UserTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 认证拦截器，校验用户token
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final Map<String, List<String>> WHITE_LIST = Map.of(
            "POST", List.of(
                    "/api/tenants/create",
                    "/api/tenants/*/users/login",
                    "/api/tenants/*/users/send-sms-code",
                    "/api/tenants/*/users/reset-password"
            ),
            "GET", List.of(
                    "/api/tenants/*/users/captcha"
            )
    );

    private final UserTokenService userTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        if (isWhiteListed(request)) {
            return true;
        }

        String token = extractToken(request.getHeader("Authorization"));
        if (!StringUtils.hasText(token)) {
            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "未登录或缺少token");
            return false;
        }

        UserToken tokenRecord = userTokenService.getByToken(token);
        if (tokenRecord == null) {
            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "token无效或已过期");
            return false;
        }

        if (userTokenService.isExpired(tokenRecord)) {
            writeError(response, HttpServletResponse.SC_UNAUTHORIZED, "token已过期");
            return false;
        }

        String tenantCodeOnPath = resolveTenantCode(request.getRequestURI());
        if (StringUtils.hasText(tenantCodeOnPath)
                && !tenantCodeOnPath.equals(tokenRecord.getTenantCode())) {
            writeError(response, HttpServletResponse.SC_FORBIDDEN, "无权访问其他租户资源");
            return false;
        }

        TenantContext.setTenantCode(tokenRecord.getTenantCode());
        Optional.ofNullable(tokenRecord.getUserId()).ifPresent(userId -> request.setAttribute("CURRENT_USER_ID", userId));
        request.setAttribute("CURRENT_TOKEN", tokenRecord.getToken());
        return true;
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                @Nullable Exception ex) throws Exception {
        TenantContext.clear();
    }

    private boolean isWhiteListed(HttpServletRequest request) {
        List<String> patterns = WHITE_LIST.get(request.getMethod());
        if (patterns == null) {
            return false;
        }
        String uri = request.getRequestURI();
        return patterns.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, uri));
    }

    private String extractToken(String authorizationHeader) {
        if (!StringUtils.hasText(authorizationHeader)) {
            return null;
        }
        String token = authorizationHeader.trim();
        if (token.toLowerCase().startsWith("bearer ")) {
            token = token.substring(7).trim();
        }
        return token;
    }

    private String resolveTenantCode(String requestUri) {
        if (!StringUtils.hasText(requestUri)) {
            return null;
        }
        String[] segments = requestUri.split("/");
        for (int i = 0; i < segments.length - 1; i++) {
            if ("tenants".equals(segments[i])) {
                return segments[i + 1];
            }
        }
        return null;
    }

    private void writeError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        ApiResponse<Void> body = ApiResponse.fail(status, message);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}

