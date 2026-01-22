-- 插入管理员账户 (密码是 123456)
-- 使用 MERGE INTO 或 WHERE NOT EXISTS 防止重复插入报错
MERGE INTO users (username, password, nickname, role, email) 
KEY(username) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376No.1.x.q/Vq.x/Vq.x/Vq.x/Vq.x/Vq', 'Dr.Zhao', 'ADMIN', 'admin@portal.com');