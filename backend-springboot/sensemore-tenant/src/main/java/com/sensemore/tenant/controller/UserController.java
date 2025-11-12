package com.sensemore.tenant.controller;

import com.sensemore.tenant.dto.*;
import com.sensemore.tenant.dto.CaptchaResult.CaptchaData;
import com.sensemore.tenant.dto.LoginResult.AuthData;
import com.sensemore.tenant.dto.LoginResult.UserInfo;
import com.sensemore.tenant.convertor.UserConvertor;
import com.sensemore.tenant.entity.Tenant;
import com.sensemore.tenant.entity.TenantUser;
import com.sensemore.tenant.entity.UserToken;
import com.sensemore.tenant.service.SmsCodeService;
import com.sensemore.tenant.service.TenantService;
import com.sensemore.tenant.service.TenantUserService;
import com.sensemore.tenant.service.UserTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户用户管理 API
 * 
 * 提供租户用户的创建、登录、注销、修改密码、找回密码等操作
 */
@Slf4j
@RestController
@RequestMapping("/tenants/{tenantCode}/users")
@RequiredArgsConstructor
@Tag(name = "租户用户管理", description = "租户用户的创建、登录、注销、修改密码、找回密码操作")
public class UserController {

    private final TenantService tenantService;
    private final TenantUserService tenantUserService;
    private final UserTokenService userTokenService;
    private final SmsCodeService smsCodeService;
    private final UserConvertor userConvertor;

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    @Operation(summary = "获取验证码", description = "获取图形验证码")
    public ApiResponse<CaptchaData> getCaptcha() {
        try {
            CaptchaData captchaData = new CaptchaData();
            captchaData.setCaptchaId("123456");
            captchaData.setCaptchaImg("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==");
            return ApiResponse.success(captchaData, "获取验证码成功");
        } catch (Exception e) {
            log.error("获取验证码失败", e);
            return ApiResponse.fail("获取验证码失败: " + e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "根据租户代码、用户名和密码登录")
    public ApiResponse<AuthData> login(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @RequestBody LoginRequest request) {
        try {
            // 设置租户代码
            request.setTenantCode(tenantCode);
            
            // 校验租户
            Tenant tenant = tenantService.getTenantByCode(tenantCode);
            if (tenant == null || tenant.getStatus() != 1) {
                return ApiResponse.fail("租户不存在或已禁用");
            }
            
            // 校验用户
            TenantUser user = tenantUserService.getByTenantCodeAndUsername(tenantCode, request.getUsername());
            if (user == null || user.getStatus() != 1) {
                return ApiResponse.fail("用户不存在或已禁用");
            }
            
            // 校验密码（此处应加密校验，示例为明文）
            if (!user.getPassword().equals(request.getPassword())) {
                return ApiResponse.fail("密码错误");
            }
            
            // 生成token（此处为示例，实际应生成JWT等）
            String token = "token-" + user.getId() + "-" + System.currentTimeMillis();
            String refreshToken = "refresh-" + user.getId() + "-" + System.currentTimeMillis();
            
            // 保存token到数据库
            UserToken userToken = new UserToken();
            userToken.setUserId(user.getId());
            userToken.setTenantCode(tenantCode);
            userToken.setToken(token);
            userToken.setRefreshToken(refreshToken);
            LocalDateTime now = LocalDateTime.now();
            userToken.setCreatedAt(now);
            userToken.setExpireAt(now.plusHours(12));
            userTokenService.save(userToken);
            
            // 构建返回结果
            AuthData authData = new AuthData();
            authData.setToken(token);
            authData.setRefreshToken(refreshToken);
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getId().toString());
            userInfo.setUsername(user.getUsername());
            userInfo.setEmail(user.getEmail());
            userInfo.setPhone(user.getPhone());
            userInfo.setTenantCode(tenant.getTenantCode());
            userInfo.setTenantName(tenant.getTenantName());
            authData.setUserInfo(userInfo);
            
            return ApiResponse.success(authData, "登录成功");
        } catch (Exception e) {
            log.error("用户登录失败", e);
            return ApiResponse.fail("登录失败: " + e.getMessage());
        }
    }

    /**
     * 用户注销
     */
    @PostMapping("/logout")
    @Operation(summary = "用户注销", description = "用户退出登录，清除token")
    public ApiResponse<String> logout(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            // 实际应该根据token删除对应的UserToken记录
            // 这里简化处理
            log.info("用户注销: tenantCode={}", tenantCode);
            return ApiResponse.success("注销成功", "用户已注销");
        } catch (Exception e) {
            log.error("用户注销失败", e);
            return ApiResponse.fail("注销失败: " + e.getMessage());
        }
    }

    /**
     * 创建用户
     */
    @PostMapping
    @Operation(summary = "创建新用户", description = "在指定租户下创建一个新用户（自动填充id、createdAt、updatedAt、status等字段）")
    public ApiResponse<Void> createUser(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @RequestBody CreateUserRequest request) {
        try {
            // 校验租户是否存在
            Tenant tenant = tenantService.getTenantByCode(tenantCode);
            if (tenant == null || tenant.getStatus() != 1) {
                return ApiResponse.fail("租户不存在或已禁用");
            }
            
            // 检查用户名是否已存在
            TenantUser existingUser = tenantUserService.getByTenantCodeAndUsername(tenantCode, request.getUsername());
            if (existingUser != null) {
                return ApiResponse.fail("用户名已存在");
            }
            
            // 创建用户
            TenantUser user = new TenantUser();
            user.setTenantCode(tenantCode);
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword()); // 实际应该加密存储
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setStatus(1);
            
            tenantUserService.createUser(user);
            return ApiResponse.success(null, "用户创建成功");
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return ApiResponse.fail("用户创建失败: " + e.getMessage());
        }
    }

    /**
     * 获取租户下的所有用户
     */
    @GetMapping
    @Operation(summary = "获取租户下的所有用户", description = "返回指定租户下的所有用户列表（不包含密码等敏感信息）")
    public ApiResponse<List<UserResponse>> getUsers(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode) {
        try {
            List<TenantUser> users = tenantUserService.getUsersByTenantCode(tenantCode);
            List<UserResponse> userResponses = userConvertor.toResponseList(users);
            return ApiResponse.success(userResponses, "获取用户列表成功");
        } catch (Exception e) {
            log.error("获取用户列表失败", e);
            return ApiResponse.fail("获取用户列表失败: " + e.getMessage());
        }
    }

    /**
     * 修改密码
     */
    @PostMapping("/change-password")
    @Operation(summary = "修改密码", description = "用户修改自己的密码")
    public ApiResponse<Void> changePassword(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @RequestBody ChangePasswordRequest request) {
        try {
            request.setTenantCode(tenantCode);
            
            // 校验用户
            TenantUser user = tenantUserService.getByTenantCodeAndUsername(tenantCode, request.getUsername());
            if (user == null || user.getStatus() != 1) {
                return ApiResponse.fail("用户不存在或已禁用");
            }
            
            // 校验旧密码
            if (!user.getPassword().equals(request.getOldPassword())) {
                return ApiResponse.fail("旧密码错误");
            }
            
            // 更新密码（实际应该加密存储）
            tenantUserService.updatePassword(user.getId(), request.getNewPassword());
            return ApiResponse.success(null, "密码修改成功");
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return ApiResponse.fail("修改密码失败: " + e.getMessage());
        }
    }

    /**
     * 发送短信验证码
     */
    @PostMapping("/send-sms-code")
    @Operation(summary = "发送短信验证码", description = "向指定手机号发送短信验证码，用于找回密码")
    public ApiResponse<String> sendSmsCode(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @RequestBody SendSmsCodeRequest request) {
        try {
            request.setTenantCode(tenantCode);
            
            // 校验租户
            Tenant tenant = tenantService.getTenantByCode(tenantCode);
            if (tenant == null || tenant.getStatus() != 1) {
                return ApiResponse.fail("租户不存在或已禁用");
            }
            
            // 校验用户是否存在
            TenantUser user = tenantUserService.getByTenantCodeAndPhone(tenantCode, request.getPhone());
            if (user == null) {
                return ApiResponse.fail("该手机号未注册");
            }
            
            // 发送短信验证码
            String code = smsCodeService.sendSmsCode(tenantCode, request.getPhone());
            // 实际生产环境不应返回验证码，这里仅用于测试
            log.info("短信验证码已发送: tenantCode={}, phone={}, code={}", tenantCode, request.getPhone(), code);
            return ApiResponse.success("验证码已发送", "短信验证码已发送，请查收（测试环境返回验证码: " + code + "）");
        } catch (Exception e) {
            log.error("发送短信验证码失败", e);
            return ApiResponse.fail("发送短信验证码失败: " + e.getMessage());
        }
    }

    /**
     * 根据手机号短信验证码找回密码
     */
    @PostMapping("/reset-password")
    @Operation(summary = "找回密码", description = "根据手机号和短信验证码重置密码")
    public ApiResponse<Void> resetPassword(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @RequestBody ResetPasswordRequest request) {
        try {
            request.setTenantCode(tenantCode);
            
            // 校验租户
            Tenant tenant = tenantService.getTenantByCode(tenantCode);
            if (tenant == null || tenant.getStatus() != 1) {
                return ApiResponse.fail("租户不存在或已禁用");
            }
            
            // 校验用户是否存在
            TenantUser user = tenantUserService.getByTenantCodeAndPhone(tenantCode, request.getPhone());
            if (user == null) {
                return ApiResponse.fail("该手机号未注册");
            }
            
            // 校验短信验证码
            if (!smsCodeService.verifyCode(tenantCode, request.getPhone(), request.getSmsCode())) {
                return ApiResponse.fail("短信验证码错误或已过期");
            }
            
            // 更新密码（实际应该加密存储）
            tenantUserService.updatePasswordByPhone(tenantCode, request.getPhone(), request.getNewPassword());
            return ApiResponse.success(null, "密码重置成功");
        } catch (Exception e) {
            log.error("找回密码失败", e);
            return ApiResponse.fail("找回密码失败: " + e.getMessage());
        }
    }

    /**
     * 删除用户（通过用户ID）
     */
    @PostMapping("/{userId}/delete")
    @Operation(summary = "删除用户", description = "根据用户ID删除指定的用户")
    public ApiResponse<Void> deleteUser(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        try {
            TenantUser user = tenantUserService.getById(userId);
            if (user == null) {
                return ApiResponse.fail("用户不存在");
            }
            if (!user.getTenantCode().equals(tenantCode)) {
                return ApiResponse.fail("用户不属于该租户");
            }
            tenantUserService.removeUser(userId);
            return ApiResponse.success(null, "用户已删除");
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return ApiResponse.fail("删除用户失败: " + e.getMessage());
        }
    }

    /**
     * 禁用用户（通过用户ID）
     */
    @PostMapping("/{userId}/disable")
    @Operation(summary = "禁用用户", description = "将指定用户的状态更改为禁用")
    public ApiResponse<Void> disableUser(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        try {
            TenantUser user = tenantUserService.getById(userId);
            if (user == null) {
                return ApiResponse.fail("用户不存在");
            }
            if (!user.getTenantCode().equals(tenantCode)) {
                return ApiResponse.fail("用户不属于该租户");
            }
            tenantUserService.disableUser(userId);
            return ApiResponse.success(null, "用户已禁用");
        } catch (Exception e) {
            log.error("禁用用户失败", e);
            return ApiResponse.fail("禁用用户失败: " + e.getMessage());
        }
    }

    /**
     * 启用用户（通过用户ID）
     */
    @PostMapping("/{userId}/enable")
    @Operation(summary = "启用用户", description = "将指定用户的状态更改为启用")
    public ApiResponse<Void> enableUser(
            @Parameter(description = "租户代码", required = true) @PathVariable String tenantCode,
            @Parameter(description = "用户ID", required = true) @PathVariable Long userId) {
        try {
            TenantUser user = tenantUserService.getById(userId);
            if (user == null) {
                return ApiResponse.fail("用户不存在");
            }
            if (!user.getTenantCode().equals(tenantCode)) {
                return ApiResponse.fail("用户不属于该租户");
            }
            tenantUserService.enableUser(userId);
            return ApiResponse.success(null, "用户已启用");
        } catch (Exception e) {
            log.error("启用用户失败", e);
            return ApiResponse.fail("启用用户失败: " + e.getMessage());
        }
    }
}
