-- =====================================================
-- 初始数据脚本（使用业务代码替代内部ID）
-- =====================================================

-- 插入示例租户（使用业务代码）
-- 注意：tenant 表可能在 schema.sql 之外定义，但为了数据完整性保留
INSERT INTO tenant (tenant_code, tenant_name, contact_person, contact_email, status, description) VALUES
('TENANT1001', '演示租户1', '李四', 'demo1@sensemore.com', 1, '用于演示的第一个租户'),
('TENANT1002', '演示租户2', '王五', 'demo2@sensemore.com', 1, '用于演示的第二个租户'),
('TENANT2001', '测试租户', '张三', 'test@sensemore.com', 1, '用于测试的租户')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例AI厂商配置（使用业务代码）
INSERT INTO ai_provider_config (config_code, tenant_code, provider_name, api_key, api_secret, base_url, model_name, config_json, status, priority, description) VALUES
('PROVIDER1001', 'TENANT1001', 'openai', 'sk-proj-abc123def456ghi789', NULL, 'https://api.openai.com/v1', 'gpt-4-turbo', '{"timeout": 30, "max_retries": 3}', 1, 1, 'OpenAI GPT-4 Turbo'),
('PROVIDER1002', 'TENANT1001', 'openai', 'sk-proj-xyz789uvw456rst123', NULL, 'https://api.openai.com/v1', 'gpt-3.5-turbo', '{"timeout": 30, "max_retries": 3}', 1, 2, 'OpenAI GPT-3.5 Turbo'),
('PROVIDER1003', 'TENANT1001', 'claude', 'sk-ant-api03-abc123def456', 'secret123', 'https://api.anthropic.com', 'claude-3-opus', '{"max_tokens": 4096}', 1, 3, 'Claude 3 Opus'),
('PROVIDER2001', 'TENANT1002', 'openai', 'sk-proj-mno456pqr789stu012', NULL, 'https://api.openai.com/v1', 'gpt-4-turbo', '{"timeout": 30}', 1, 1, 'OpenAI GPT-4 Turbo'),
('PROVIDER2002', 'TENANT1002', 'gemini', 'AIzaSyAbc123Def456Ghi789', NULL, 'https://generativelanguage.googleapis.com', 'gemini-pro', '{"safety_settings": "moderate"}', 1, 2, 'Google Gemini Pro'),
('PROVIDER3001', 'TENANT2001', 'claude', 'sk-ant-api03-xyz789uvw456', 'secret456', 'https://api.anthropic.com', 'claude-3-opus', '{"max_tokens": 4096}', 1, 1, 'Claude 3 Opus'),
('PROVIDER3002', 'TENANT2001', 'ollama', 'ollama', NULL, 'http://localhost:11434', 'llama2', '{"temperature": 0.7}', 2, 2, '本地 Ollama 部署')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例模型配置（使用业务代码）
INSERT INTO model_config (model_code, tenant_code, config_code, config_name, temperature, top_p, max_tokens, top_k, frequency_penalty, presence_penalty, stop_sequences, system_prompt, is_default, status, description) VALUES
('MODEL1001', 'TENANT1001', 'PROVIDER1001', '专业回复', 0.3, 0.9, 2000, NULL, 0.0, 0.0, NULL, '你是一个专业的AI助手，请用专业、准确的语言回答问题。', 1, 1, '用于专业场景的配置'),
('MODEL1002', 'TENANT1001', 'PROVIDER1001', '创意写作', 0.8, 0.95, 3000, NULL, 0.0, 0.0, NULL, '你是一个创意写作助手，请用生动、有趣的语言进行创意写作。', 0, 1, '用于创意写作场景的配置'),
('MODEL1003', 'TENANT1001', 'PROVIDER1002', '简洁回复', 0.5, 0.9, 1000, NULL, 0.0, 0.0, NULL, '请用简洁、清晰的语言回答问题。', 0, 1, '用于简洁回复场景的配置'),
('MODEL1004', 'TENANT1001', 'PROVIDER1003', 'Claude专业模式', 0.2, 0.9, 2000, NULL, NULL, NULL, NULL, '你是一个专业的AI助手，擅长分析和推理。', 0, 1, 'Claude专业分析配置'),
('MODEL2001', 'TENANT1002', 'PROVIDER2001', '默认配置', 0.7, 0.9, 2000, NULL, 0.0, 0.0, NULL, '你是一个有用的AI助手。', 1, 1, '租户TENANT1002的默认模型配置'),
('MODEL2002', 'TENANT1002', 'PROVIDER2001', '高创意模式', 0.9, 0.95, 4000, NULL, 0.0, 0.0, NULL, '你是一个富有创意的AI助手，擅长生成新颖的想法。', 0, 1, '高创意模式配置'),
('MODEL2003', 'TENANT1002', 'PROVIDER2002', 'Gemini默认', 0.7, 0.9, 2000, NULL, NULL, NULL, NULL, '你是Google Gemini AI助手。', 0, 1, 'Gemini默认配置'),
('MODEL3001', 'TENANT2001', 'PROVIDER3001', 'Claude默认配置', 0.7, 0.9, 2000, NULL, NULL, NULL, NULL, '你是Claude AI助手。', 1, 1, '租户TENANT2001的默认模型配置'),
('MODEL3002', 'TENANT2001', 'PROVIDER3002', '本地测试配置', 0.7, 0.9, 2000, 40, NULL, NULL, NULL, '你是本地部署的AI助手。', 0, 1, '本地Ollama测试配置')
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例API调用量统计（使用业务代码）
INSERT INTO api_usage_stat (stat_code, tenant_code, config_code, date_key, request_count, success_count, failed_count, total_prompt_tokens, total_completion_tokens, total_tokens, total_cost, avg_response_time_ms, max_response_time_ms, min_response_time_ms) VALUES
('STAT1001', 'TENANT1001', 'PROVIDER1001', CURDATE(), 1250, 1200, 50, 150000, 450000, 600000, 12.50, 850, 3500, 200),
('STAT1002', 'TENANT1001', 'PROVIDER1001', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 980, 950, 30, 120000, 360000, 480000, 9.80, 820, 3200, 180),
('STAT1003', 'TENANT1001', 'PROVIDER1002', CURDATE(), 560, 550, 10, 45000, 135000, 180000, 1.80, 650, 2800, 150),
('STAT1004', 'TENANT1001', 'PROVIDER1003', CURDATE(), 320, 310, 10, 38000, 114000, 152000, 3.20, 1200, 4500, 300),
('STAT2001', 'TENANT1002', 'PROVIDER2001', CURDATE(), 890, 870, 20, 110000, 330000, 440000, 8.90, 900, 3800, 220),
('STAT2002', 'TENANT1002', 'PROVIDER2001', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 750, 740, 10, 95000, 285000, 380000, 7.50, 880, 3600, 200),
('STAT2003', 'TENANT1002', 'PROVIDER2002', CURDATE(), 210, 205, 5, 25000, 75000, 100000, 0.50, 1100, 4000, 250),
('STAT3001', 'TENANT2001', 'PROVIDER3001', CURDATE(), 450, 445, 5, 55000, 165000, 220000, 4.50, 1300, 5000, 350)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例API调用日志（使用业务代码）
INSERT INTO api_call_log (log_code, tenant_code, config_code, model_code, user_id, request_content, response_content, prompt_tokens, completion_tokens, total_tokens, cost, response_time_ms, status, error_message) VALUES
('LOG1001', 'TENANT1001', 'PROVIDER1001', 'MODEL1001', 'user001', '请解释一下什么是机器学习？', '机器学习是人工智能的一个分支，它使计算机能够从数据中学习，而无需明确编程...', 15, 245, 260, 0.026, 850, 1, NULL),
('LOG1002', 'TENANT1001', 'PROVIDER1001', 'MODEL1002', 'user002', '写一首关于春天的诗', '春风轻拂面，花开满枝头。燕子归来时，万物复苏中...', 12, 180, 192, 0.019, 720, 1, NULL),
('LOG1003', 'TENANT1001', 'PROVIDER1002', 'MODEL1003', 'user001', '什么是API？', 'API（应用程序编程接口）是不同软件组件之间通信的协议...', 8, 120, 128, 0.013, 580, 1, NULL),
('LOG1004', 'TENANT1001', 'PROVIDER1001', 'MODEL1001', 'user003', '分析一下市场趋势', '根据当前数据分析，市场呈现以下趋势...', 20, 380, 400, 0.040, 1200, 1, NULL),
('LOG1005', 'TENANT1001', 'PROVIDER1001', 'MODEL1001', 'user001', '测试请求', NULL, 5, 0, 5, 0.000, 150, 0, 'API密钥无效'),
('LOG2001', 'TENANT1002', 'PROVIDER2001', 'MODEL2001', 'user201', '帮我写一份项目计划', '项目计划应包括以下部分：1. 项目概述 2. 目标与范围 3. 时间表...', 18, 320, 338, 0.034, 950, 1, NULL),
('LOG2002', 'TENANT1002', 'PROVIDER2001', 'MODEL2002', 'user202', '创作一个科幻故事', '在遥远的未来，人类已经掌握了星际旅行的技术...', 15, 520, 535, 0.054, 1100, 1, NULL),
('LOG2003', 'TENANT1002', 'PROVIDER2002', 'MODEL2003', 'user201', '翻译：Hello World', '你好，世界', 3, 5, 8, 0.001, 450, 1, NULL),
('LOG3001', 'TENANT2001', 'PROVIDER3001', 'MODEL3001', 'user301', '解释量子计算', '量子计算是一种利用量子力学现象进行计算的技术...', 12, 280, 292, 0.029, 1350, 1, NULL),
('LOG3002', 'TENANT2001', 'PROVIDER3001', 'MODEL3001', 'user302', '代码审查', '这段代码存在以下问题：1. 缺少错误处理 2. 性能可以优化...', 25, 150, 175, 0.018, 980, 1, NULL)
ON DUPLICATE KEY UPDATE log_code = log_code;

-- 插入示例配额限制（使用业务代码）
INSERT INTO quota_limit (quota_code, tenant_code, daily_request_limit, daily_token_limit, monthly_cost_limit, current_day_requests, current_day_tokens, current_month_cost, last_reset_time, status) VALUES
('QUOTA1001', 'TENANT1001', 10000, 1000000, 5000.00, 2130, 1382000, 26.30, DATE_SUB(CURDATE(), INTERVAL 0 DAY), 1),
('QUOTA1002', 'TENANT1002', 5000, 500000, 2000.00, 1100, 540000, 16.90, DATE_SUB(CURDATE(), INTERVAL 0 DAY), 1),
('QUOTA2001', 'TENANT2001', -1, -1, -1, 450, 220000, 4.50, DATE_SUB(CURDATE(), INTERVAL 0 DAY), 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例告警规则（使用业务代码）
INSERT INTO alert_rule (rule_code, tenant_code, rule_name, rule_type, condition_value, alert_email, alert_phone, status) VALUES
('RULE1001', 'TENANT1001', '日请求超限告警', 'quota_exceeded', 10000, 'demo1@sensemore.com,admin@sensemore.com', '13800138000,13900139000', 1),
('RULE1002', 'TENANT1001', '月费用超限告警', 'cost_exceeded', 5000.00, 'demo1@sensemore.com,admin@sensemore.com', '13800138000', 1),
('RULE1003', 'TENANT1001', '错误率高告警', 'error_rate_high', 0.05, 'demo1@sensemore.com', NULL, 1),
('RULE1004', 'TENANT1002', '日请求超限告警', 'quota_exceeded', 5000, 'demo2@sensemore.com,admin@sensemore.com', '13800138001', 1),
('RULE1005', 'TENANT1002', '月费用超限告警', 'cost_exceeded', 2000.00, 'demo2@sensemore.com', NULL, 1),
('RULE2001', 'TENANT2001', '错误率高告警', 'error_rate_high', 0.1, 'test@sensemore.com,admin@sensemore.com', '13800138002', 1)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;

-- 插入示例租户用户（使用业务代码）
INSERT INTO tenant_user (tenant_code, username, password, email, phone, status) VALUES
('TENANT1001', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@tenant1001.com', '13800138000', 1),
('TENANT1001', 'user001', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user001@tenant1001.com', '13800138001', 1),
('TENANT1001', 'user002', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user002@tenant1001.com', '13800138002', 1),
('TENANT1001', 'user003', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user003@tenant1001.com', '13800138003', 1),
('TENANT1002', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@tenant1002.com', '13900139000', 1),
('TENANT1002', 'user201', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user201@tenant1002.com', '13900139001', 1),
('TENANT1002', 'user202', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user202@tenant1002.com', '13900139002', 1),
('TENANT2001', 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@tenant2001.com', '13700137000', 1),
('TENANT2001', 'user301', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user301@tenant2001.com', '13700137001', 1),
('TENANT2001', 'user302', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'user302@tenant2001.com', '13700137002', 1),
('TENANT2001', 'disabled_user', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'disabled@tenant2001.com', '13700137003', 0)
ON DUPLICATE KEY UPDATE updated_at = CURRENT_TIMESTAMP;
