-- =====================================================
-- 初始数据脚本（使用业务代码替代内部ID）
-- =====================================================

-- 插入示例租户（使用业务代码）
INSERT INTO tenant (tenant_code, tenant_name, contact_person, contact_email, status, description) VALUES
('TENANT1001', '演示租户1', '李四', 'demo1@sensemore.com', 1, '用于演示的第一个租户'),
('TENANT1002', '演示租户2', '王五', 'demo2@sensemore.com', 1, '用于演示的第二个租户'),
('TENANT2001', '测试租户', '张三', 'test@sensemore.com', 1, '用于测试的租户')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例AI厂商配置（使用业务代码）
INSERT INTO ai_provider_config (config_code, tenant_code, provider_name, api_key, base_url, model_name, status, priority, description) VALUES
('PROVIDER1001', 'TENANT1001', 'openai', 'sk-***', 'https://api.openai.com/v1', 'gpt-4-turbo', 1, 1, 'OpenAI GPT-4 Turbo'),
('PROVIDER1002', 'TENANT1001', 'openai', 'sk-***', 'https://api.openai.com/v1', 'gpt-3.5-turbo', 1, 2, 'OpenAI GPT-3.5 Turbo'),
('PROVIDER2001', 'TENANT1002', 'openai', 'sk-***', 'https://api.openai.com/v1', 'gpt-4-turbo', 1, 1, 'OpenAI GPT-4 Turbo'),
('PROVIDER3001', 'TENANT2001', 'claude', 'sk-***', 'https://api.anthropic.com', 'claude-3-opus', 1, 1, 'Claude 3 Opus')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例模型配置（使用业务代码）
INSERT INTO model_config (model_code, tenant_code, config_code, config_name, temperature, top_p, max_tokens, system_prompt, is_default, status, description) VALUES
('MODEL1001', 'TENANT1001', 'PROVIDER1001', '专业回复', 0.3, 0.9, 2000, '你是一个专业的AI助手，请用专业、准确的语言回答问题。', 1, 1, '用于专业场景的配置'),
('MODEL1002', 'TENANT1001', 'PROVIDER1001', '创意写作', 0.8, 0.95, 3000, '你是一个创意写作助手，请用生动、有趣的语言进行创意写作。', 0, 1, '用于创意写作场景的配置'),
('MODEL1003', 'TENANT1001', 'PROVIDER1002', '简洁回复', 0.5, 0.9, 1000, '请用简洁、清晰的语言回答问题。', 0, 1, '用于简洁回复场景的配置'),
('MODEL2001', 'TENANT1002', 'PROVIDER2001', '默认配置', 0.7, 0.9, 2000, '你是一个有用的AI助手。', 1, 1, '租户TENANT1002的默认模型配置'),
('MODEL3001', 'TENANT2001', 'PROVIDER3001', 'Claude默认配置', 0.7, 0.9, 2000, '你是Claude AI助手。', 1, 1, '租户TENANT2001的默认模型配置')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例配额限制（使用业务代码）
INSERT INTO quota_limit (quota_code, tenant_code, daily_request_limit, daily_token_limit, monthly_cost_limit, status) VALUES
('QUOTA1001', 'TENANT1001', 10000, 1000000, 5000.00, 1),
('QUOTA1002', 'TENANT1002', 5000, 500000, 2000.00, 1),
('QUOTA2001', 'TENANT2001', -1, -1, -1, 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例告警规则（使用业务代码）
INSERT INTO alert_rule (rule_code, tenant_code, rule_name, rule_type, condition_value, alert_email, status) VALUES
('RULE1001', 'TENANT1001', '日请求超限告警', 'quota_exceeded', 10000, 'demo1@sensemore.com,admin@sensemore.com', 1),
('RULE1002', 'TENANT1001', '月费用超限告警', 'cost_exceeded', 5000.00, 'demo1@sensemore.com,admin@sensemore.com', 1),
('RULE1003', 'TENANT1002', '日请求超限告警', 'quota_exceeded', 5000, 'demo2@sensemore.com,admin@sensemore.com', 1),
('RULE2001', 'TENANT2001', '错误率高告警', 'error_rate_high', 0.1, 'test@sensemore.com,admin@sensemore.com', 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;
