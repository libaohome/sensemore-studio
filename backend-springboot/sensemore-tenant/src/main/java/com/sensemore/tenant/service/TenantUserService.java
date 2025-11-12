package com.sensemore.tenant.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sensemore.tenant.entity.TenantUser;
import com.sensemore.tenant.mapper.TenantUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 租户用户管理服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TenantUserService extends ServiceImpl<TenantUserMapper, TenantUser> {
    
    /**
     * 根据租户代码和用户名获取用户
     */
    public TenantUser getByTenantCodeAndUsername(String tenantCode, String username) {
        return lambdaQuery().eq(TenantUser::getTenantCode, tenantCode)
                            .eq(TenantUser::getUsername, username)
                            .one();
    }

    /**
     * 根据租户代码和手机号获取用户
     */
    public TenantUser getByTenantCodeAndPhone(String tenantCode, String phone) {
        return lambdaQuery().eq(TenantUser::getTenantCode, tenantCode)
                            .eq(TenantUser::getPhone, phone)
                            .one();
    }

    /**
     * 创建用户
     */
    @Transactional
    public void createUser(TenantUser user) {
        log.info("创建用户: {}@{}", user.getUsername(), user.getTenantCode());
        user.setStatus(1);
        LocalDateTime now = LocalDateTime.now();
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(now);
        }
        user.setUpdatedAt(now);
        baseMapper.insert(user);
    }

    /**
     * 更新用户信息
     */
    @Transactional
    public void updateUser(TenantUser user) {
        log.info("更新用户: {}@{}", user.getUsername(), user.getTenantCode());
        user.setUpdatedAt(LocalDateTime.now());
        baseMapper.updateById(user);
    }

    /**
     * 更新用户密码
     */
    @Transactional
    public void updatePassword(Long userId, String newPassword) {
        log.info("更新用户密码: userId={}", userId);
        TenantUser user = baseMapper.selectById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            user.setUpdatedAt(LocalDateTime.now());
            baseMapper.updateById(user);
        }
    }

    /**
     * 根据租户代码和手机号更新密码
     */
    @Transactional
    public void updatePasswordByPhone(String tenantCode, String phone, String newPassword) {
        log.info("根据手机号更新密码: {}@{}", phone, tenantCode);
        TenantUser user = getByTenantCodeAndPhone(tenantCode, phone);
        if (user != null) {
            user.setPassword(newPassword);
            user.setUpdatedAt(LocalDateTime.now());
            baseMapper.updateById(user);
        }
    }

    /**
     * 禁用用户（通过用户ID）
     */
    @Transactional
    public void disableUser(Long userId) {
        log.info("禁用用户: userId={}", userId);
        TenantUser user = baseMapper.selectById(userId);
        if (user != null) {
            user.setStatus(0);
            user.setUpdatedAt(LocalDateTime.now());
            baseMapper.updateById(user);
        }
    }

    /**
     * 启用用户（通过用户ID）
     */
    @Transactional
    public void enableUser(Long userId) {
        log.info("启用用户: userId={}", userId);
        TenantUser user = baseMapper.selectById(userId);
        if (user != null) {
            user.setStatus(1);
            user.setUpdatedAt(LocalDateTime.now());
            baseMapper.updateById(user);
        }
    }

    /**
     * 删除用户（通过用户ID）
     */
    @Transactional
    public void removeUser(Long userId) {
        log.info("删除用户: userId={}", userId);
        baseMapper.deleteById(userId);
    }

    /**
     * 获取租户下的所有用户
     */
    public List<TenantUser> getUsersByTenantCode(String tenantCode) {
        return lambdaQuery().eq(TenantUser::getTenantCode, tenantCode).list();
    }
}
