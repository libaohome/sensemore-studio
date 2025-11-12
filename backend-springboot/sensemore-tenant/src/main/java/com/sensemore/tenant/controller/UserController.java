package com.sensemore.tenant.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sensemore.tenant.dto.CaptchaResult;
import com.sensemore.tenant.dto.LoginRequest;
import com.sensemore.tenant.dto.LoginResult;
import com.sensemore.tenant.dto.CaptchaResult.CaptchaData;
import com.sensemore.tenant.dto.LoginResult.LoginData;
import com.sensemore.tenant.dto.LoginResult.UserInfo;
import com.sensemore.tenant.dto.LogoutResult;
import com.sensemore.tenant.entity.TenantUser;
import com.sensemore.tenant.entity.UserToken;
import com.sensemore.tenant.entity.Tenant;
import com.sensemore.tenant.service.TenantService;
import com.sensemore.tenant.service.TenantUserService;
import com.sensemore.tenant.service.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private TenantUserService tenantUserService;
    @Autowired
    private UserTokenService userTokenService;

    @GetMapping("captcha")    
    public CaptchaResult<CaptchaData> getCaptcha() {
        CaptchaResult<CaptchaData> captchaResult = new CaptchaResult<>();
        captchaResult.setCode(200);
        captchaResult.setMessage("success");
        CaptchaData captchaData = new CaptchaData();
        captchaData.setCaptchaId("123456");
        captchaData.setCaptchaImg("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==");
        captchaResult.setData(captchaData);
        return captchaResult;
    }

    @PostMapping("login")
    public LoginResult<LoginData> login(@RequestBody LoginRequest request) {
        LoginResult<LoginData> loginResult = new LoginResult<>();
        // 校验租户
        Tenant tenant = tenantService.getTenantByCode(request.getTenantCode());
        if (tenant == null || tenant.getStatus() != 1) {
            loginResult.setCode(400);
            loginResult.setMessage("租户不存在或已禁用");
            return loginResult;
        }
        // 校验用户
        TenantUser user = tenantUserService.getByTenantCodeAndUsername(request.getTenantCode(), request.getUsername());
        if (user == null || user.getStatus() != 1) {
            loginResult.setCode(401);
            loginResult.setMessage("用户不存在或已禁用");
            return loginResult;
        }
        // 校验密码（此处应加密校验，示例为明文）
        if (!user.getPassword().equals(request.getPassword())) {
            loginResult.setCode(403);
            loginResult.setMessage("密码错误");
            return loginResult;
        }
        // 生成token（此处为示例，实际应生成JWT等）
        String token = "token-" + user.getId() + "-" + System.currentTimeMillis();
        String refreshToken = "refresh-" + user.getId() + "-" + System.currentTimeMillis();
        // 保存token到数据库
        UserToken userToken = new UserToken();
        userToken.setUserId(user.getId());
        userToken.setTenantCode(request.getTenantCode());
        userToken.setToken(token);
        userToken.setRefreshToken(refreshToken);
        userToken.setCreatedAt(java.time.LocalDateTime.now());
        userTokenService.save(userToken);
        // 返回结果
        loginResult.setCode(200);
        loginResult.setMessage("success");
        LoginData loginData = new LoginData();
        loginData.setToken(token);
        loginData.setRefreshToken(refreshToken);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId().toString());
        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(user.getEmail());
        userInfo.setTenantCode(tenant.getTenantCode());
        userInfo.setTenantName(tenant.getTenantName());
        loginData.setUserInfo(userInfo);
        loginResult.setData(loginData);
        return loginResult;
    }

    @PostMapping("logout")
    public LogoutResult<String> logout() {
        LogoutResult<String> logoutResult = new LogoutResult<>();
        logoutResult.setCode(200);
        logoutResult.setMessage("success");
        logoutResult.setData("logout success");
        return logoutResult;
    }
}
