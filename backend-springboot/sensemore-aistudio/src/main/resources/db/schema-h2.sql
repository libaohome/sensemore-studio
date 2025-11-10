-- 创建课程表
CREATE TABLE courses (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         edu INT NOT NULL,
                         type VARCHAR(50) NOT NULL,
                         price BIGINT NOT NULL,
                         duration INT NOT NULL
);

-- 为表添加注释
COMMENT ON TABLE courses IS '课程信息表';
COMMENT ON COLUMN courses.id IS '主键';
COMMENT ON COLUMN courses.name IS '学科名称';
COMMENT ON COLUMN courses.edu IS '学历背景要求：0-无，1-初中，2-高中，3-大专，4-本科以上';
COMMENT ON COLUMN courses.type IS '课程类型：编程、设计、自媒体、其它';
COMMENT ON COLUMN courses.price IS '课程价格';
COMMENT ON COLUMN courses.duration IS '学习时长，单位：天';

-- 创建学员预约表
CREATE TABLE student_reservation (
         id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
         name VARCHAR(100) NOT NULL COMMENT '姓名',
         gender TINYINT NOT NULL COMMENT '性别：0-未知，1-男，2-女',
         education TINYINT NOT NULL COMMENT '学历：0-初中及以下，1-高中，2-大专，3-本科，4-硕士，5-博士',
         phone VARCHAR(20) NOT NULL COMMENT '电话',
         email VARCHAR(100) COMMENT '邮箱',
         graduate_school VARCHAR(200) COMMENT '毕业院校',
         location VARCHAR(200) NOT NULL COMMENT '所在地',
         course VARCHAR(200) NOT NULL COMMENT '课程名称',
         remark VARCHAR(200) NOT NULL COMMENT '学员备注'
);