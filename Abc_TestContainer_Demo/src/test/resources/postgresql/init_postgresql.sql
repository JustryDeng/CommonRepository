
DROP TABLE IF EXISTS employee;
CREATE TABLE employee  (
  "id" int NOT NULL,
  "name" varchar(50) DEFAULT NULL,
  "age" int DEFAULT NULL,
  "gender" char(1) DEFAULT NULL,
  "motto" varchar(255) DEFAULT NULL,
  "birthday" timestamp DEFAULT NULL,
  "hobby" varchar(255) DEFAULT NULL
);

create index  primary_idx  on  employee(id);


INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (1, 'JustryDeng', 1, '男', '废言0', '1994-02-05 00:00:00', '玩游戏0');
INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (2, 'a', 2, '男', '废言1', '1994-02-05 00:00:00', '玩游戏1');
INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (3, 'l', 3, '女', '废言2', '1994-02-05 00:00:00', '玩游戏2');
INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (4, 'JustryDeng', 6, '男', '废言3', '1994-02-05 00:00:00', '玩游戏3');
INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (5, 'l', 12, '男', '废言4', '1994-02-05 00:00:00', '玩游戏4');
INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (6, 'u', 9, '女', '废言5', '1994-02-05 00:00:00', '玩游戏5');
INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (36, '张三', 11, '男', '我不是黄蓉,我不会武功~', NULL, NULL);
INSERT INTO "employee"("id", "name", "age", "gender", "motto", "birthday", "hobby") VALUES (39, 'JustryDeng679', 25, '男', '我是一只小小小小鸟~嗷！嗷！', NULL, NULL);
