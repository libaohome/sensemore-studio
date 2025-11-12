
-- AI 模型厂商配置表
CREATE TABLE IF NOT EXISTS ai_provider_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内部ID（仅用于索引）',
    config_code VARCHAR(50) NOT NULL UNIQUE COMMENT '配置业务代码（如 PROVIDER1001）',
    tenant_code VARCHAR(50) NOT NULL COMMENT '租户业务代码（替代外键）',
    provider_name VARCHAR(50) NOT NULL COMMENT '厂商名称: openai, claude, gemini, ollama等',
    api_key VARCHAR(500) NOT NULL COMMENT 'API密钥',
    api_secret VARCHAR(500) COMMENT 'API密钥（某些厂商需要）',
    base_url VARCHAR(500) COMMENT '基础URL（用于自定义端点或私有部署）',
    model_name VARCHAR(100) NOT NULL COMMENT '使用的模型名称',
    config_json JSON COMMENT '厂商特定配置（JSON格式）',
    status TINYINT DEFAULT 1 COMMENT '配置状态: 0-禁用, 1-启用, 2-测试中',
    priority INT DEFAULT 0 COMMENT '优先级（用于多配置时的选择）',
    description VARCHAR(500) COMMENT '描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_config_code (config_code),
    INDEX idx_tenant_code (tenant_code),
    INDEX idx_provider_status (provider_name, status)
) COMMENT 'AI厂商配置表（无外键，通过 tenant_code 关联）';

-- 模型使用配置表（租户可配置的参数）
CREATE TABLE IF NOT EXISTS model_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内部ID（仅用于索引）',
    model_code VARCHAR(50) NOT NULL UNIQUE COMMENT '模型配置业务代码（如 MODEL1001）',
    tenant_code VARCHAR(50) NOT NULL COMMENT '租户业务代码（替代外键）',
    config_code VARCHAR(50) NOT NULL COMMENT '厂商配置业务代码（替代外键）',
    config_name VARCHAR(100) NOT NULL COMMENT '配置名称（e.g., 常规回复、创意写作）',
    temperature DOUBLE DEFAULT 0.7 COMMENT '温度参数（0-2）',
    top_p DOUBLE DEFAULT 0.9 COMMENT 'Top-P参数（0-1）',
    max_tokens INT DEFAULT 2000 COMMENT '最大生成token数',
    top_k INT COMMENT 'Top-K参数（适用于某些厂商）',
    frequency_penalty DOUBLE COMMENT '频率惩罚（适用于某些厂商）',
    presence_penalty DOUBLE COMMENT '存在惩罚（适用于某些厂商）',
    stop_sequences VARCHAR(500) COMMENT '停止序列（逗号分隔）',
    system_prompt TEXT COMMENT '系统提示词',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    is_default TINYINT DEFAULT 0 COMMENT '是否为默认配置',
    description VARCHAR(500) COMMENT '描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_model_code (model_code),
    INDEX idx_tenant_code (tenant_code),
    INDEX idx_config_code (config_code),
    INDEX idx_is_default (tenant_code, is_default)
) COMMENT '模型配置表（无外键，通过 tenant_code 和 config_code 关联）';

-- API 调用量统计表
CREATE TABLE IF NOT EXISTS api_usage_stat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内部ID（仅用于索引）',
    stat_code VARCHAR(50) NOT NULL UNIQUE COMMENT '统计业务代码（如 STAT1001）',
    tenant_code VARCHAR(50) NOT NULL COMMENT '租户业务代码',
    config_code VARCHAR(50) NOT NULL COMMENT '厂商配置业务代码',
    date_key DATE NOT NULL COMMENT '统计日期',
    request_count BIGINT DEFAULT 0 COMMENT '请求数量',
    success_count BIGINT DEFAULT 0 COMMENT '成功请求数',
    failed_count BIGINT DEFAULT 0 COMMENT '失败请求数',
    total_prompt_tokens BIGINT DEFAULT 0 COMMENT '总输入token数',
    total_completion_tokens BIGINT DEFAULT 0 COMMENT '总输出token数',
    total_tokens BIGINT DEFAULT 0 COMMENT '总token数',
    total_cost DECIMAL(12, 4) DEFAULT 0 COMMENT '总费用',
    avg_response_time_ms INT DEFAULT 0 COMMENT '平均响应时间(ms)',
    max_response_time_ms INT DEFAULT 0 COMMENT '最大响应时间(ms)',
    min_response_time_ms INT DEFAULT 2147483647 COMMENT '最小响应时间(ms)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_stat_code (stat_code),
    INDEX idx_tenant_date (tenant_code, date_key),
    INDEX idx_config_date (config_code, date_key)
) COMMENT 'API调用量统计表（无外键，通过 tenant_code 和 config_code 关联）';

-- 调用日志表
CREATE TABLE IF NOT EXISTS api_call_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内部ID（仅用于索引）',
    log_code VARCHAR(50) NOT NULL UNIQUE COMMENT '日志业务代码（如 LOG1001）',
    tenant_code VARCHAR(50) NOT NULL COMMENT '租户业务代码',
    config_code VARCHAR(50) NOT NULL COMMENT '厂商配置业务代码',
    model_code VARCHAR(50) COMMENT '模型配置业务代码',
    user_id VARCHAR(100) COMMENT '用户ID',
    request_content LONGTEXT NOT NULL COMMENT '请求内容',
    response_content LONGTEXT COMMENT '响应内容',
    prompt_tokens INT DEFAULT 0 COMMENT '输入token数',
    completion_tokens INT DEFAULT 0 COMMENT '输出token数',
    total_tokens INT DEFAULT 0 COMMENT '总token数',
    cost DECIMAL(10, 4) DEFAULT 0 COMMENT '本次调用费用',
    response_time_ms INT DEFAULT 0 COMMENT '响应时间(ms)',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-失败, 1-成功',
    error_message VARCHAR(500) COMMENT '错误信息',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_log_code (log_code),
    INDEX idx_tenant_date (tenant_code, created_at),
    INDEX idx_config_date (config_code, created_at)
) COMMENT 'API调用日志表（无外键，通过 tenant_code、config_code、model_code 关联）';

-- 配额限制表
CREATE TABLE IF NOT EXISTS quota_limit (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内部ID（仅用于索引）',
    quota_code VARCHAR(50) NOT NULL UNIQUE COMMENT '配额业务代码（如 QUOTA1001）',
    tenant_code VARCHAR(50) NOT NULL UNIQUE COMMENT '租户业务代码',
    daily_request_limit BIGINT DEFAULT -1 COMMENT '每日请求限制 (-1 = 无限)',
    daily_token_limit BIGINT DEFAULT -1 COMMENT '每日token限制 (-1 = 无限)',
    monthly_cost_limit DECIMAL(12, 2) DEFAULT -1 COMMENT '每月费用限制 (-1 = 无限)',
    current_day_requests BIGINT DEFAULT 0 COMMENT '当日请求数',
    current_day_tokens BIGINT DEFAULT 0 COMMENT '当日token数',
    current_month_cost DECIMAL(12, 2) DEFAULT 0 COMMENT '当月费用',
    last_reset_time TIMESTAMP COMMENT '最后重置时间',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_quota_code (quota_code),
    UNIQUE KEY uk_tenant_code (tenant_code),
    INDEX idx_status (status)
) COMMENT '配额限制表（无外键，通过 tenant_code 关联）';

-- 告警规则表
CREATE TABLE IF NOT EXISTS alert_rule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '内部ID（仅用于索引）',
    rule_code VARCHAR(50) NOT NULL UNIQUE COMMENT '规则业务代码（如 RULE1001）',
    tenant_code VARCHAR(50) NOT NULL COMMENT '租户业务代码',
    rule_name VARCHAR(100) NOT NULL COMMENT '规则名称',
    rule_type VARCHAR(50) NOT NULL COMMENT '规则类型: quota_exceeded, cost_exceeded, error_rate_high',
    condition_value DECIMAL(12, 2) NOT NULL COMMENT '触发条件值',
    alert_email VARCHAR(500) NOT NULL COMMENT '告警邮箱（逗号分隔）',
    alert_phone VARCHAR(500) COMMENT '告警电话（逗号分隔）',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_rule_code (rule_code),
    INDEX idx_tenant_code (tenant_code),
    INDEX idx_rule_type (rule_type),
    INDEX idx_status (status)
) COMMENT '告警规则表（无外键，通过 tenant_code 关联）';

-- 租户用户表
CREATE TABLE IF NOT EXISTS tenant_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    tenant_code VARCHAR(50) NOT NULL COMMENT '租户业务代码',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT DEFAULT 1 COMMENT '用户状态: 0-禁用, 1-启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_tenant_username (tenant_code, username),
    INDEX idx_tenant_code (tenant_code),
    INDEX idx_tenant_phone (tenant_code, phone)
) COMMENT '租户用户表';
