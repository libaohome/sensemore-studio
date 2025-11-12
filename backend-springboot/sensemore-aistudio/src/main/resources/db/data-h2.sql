-- 插入Java课程数据
INSERT INTO courses (name, edu, type, price, duration) VALUES
    ('Java', 4, '编程', 12800, 180);

-- 插入.NET课程数据
INSERT INTO courses (name, edu, type, price, duration) VALUES
    ('.NET', 3, '编程', 11800, 160);

-- 插入PHP课程数据
INSERT INTO courses (name, edu, type, price, duration) VALUES
    ('PHP', 2, '编程', 9800, 120);

-- 插入前端课程数据
INSERT INTO courses (name, edu, type, price, duration) VALUES
    ('前端', 2, '编程', 10800, 150);

-- 插入C++课程数据
INSERT INTO courses (name, edu, type, price, duration) VALUES
    ('C++', 4, '编程', 13500, 200);

-- 插入Linux云计算课程数据
INSERT INTO courses (name, edu, type, price, duration) VALUES
    ('Linux云计算', 3, '编程', 15800, 210);

-- 插入AI配置数据
INSERT INTO ai_config (provider, api_key, base_url, model, is_active) VALUES
    ('openai', '115925abb19ec543cdcbe8af4506ff463ea2b5e8', 'https://api-77aaidn1l8c5b7xa.aistudio-app.com/', 'gemma3:27b', TRUE);
