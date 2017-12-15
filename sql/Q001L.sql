/* Formatted on 2012/10/24 11:31:55 (QP5 v5.163.1008.3004) */
DROP TABLE q001l CASCADE CONSTRAINTS;

CREATE TABLE q001l
(
   id         NUMBER (18) DEFAULT 0, -- id
   q001h_id   NUMBER (18) DEFAULT 0, -- Q001H ID
   bdbh       NVARCHAR2 (20) DEFAULT '', -- 表單號碼
   sqnr       NUMBER (3) DEFAULT 0, -- 序列
   lfxm       NVARCHAR2 (255) DEFAULT '', -- 姓名
   sex        NVARCHAR2 (1) DEFAULT '', -- 性別
   zjlx       NVARCHAR2 (255) DEFAULT '', -- 證件類型
   zjhm       NVARCHAR2 (255) DEFAULT '', -- 證件號碼
   lfdw       NVARCHAR2 (255) DEFAULT '', -- 來訪單位
   lfzw       NVARCHAR2 (255) DEFAULT '', -- 職位
   lfdh       NVARCHAR2 (255) DEFAULT '', -- 電話
   lfyj       NVARCHAR2 (255) DEFAULT '', -- 郵件
   addr       NVARCHAR2 (255) DEFAULT '', -- 地址
   sdrq       NVARCHAR2 (8) DEFAULT '', -- 實際到達日期
   sdsj       NVARCHAR2 (4) DEFAULT '', -- 實際到達時間
   slrq       NVARCHAR2 (8) DEFAULT '', -- 實際離開日期
   slsj       NVARCHAR2 (4) DEFAULT '', -- 實際離開時間
   cphm       NVARCHAR2 (255) DEFAULT '', -- 車牌號碼
   hghm       NVARCHAR2 (255) DEFAULT '', -- 貨櫃號碼
   lfbz       NVARCHAR2 (512) DEFAULT '', -- 備註
   lffd       NVARCHAR2 (255) DEFAULT '', -- 附檔
   jlsj       DATE DEFAULT '', -- 建立時間
   gxsj       DATE DEFAULT '', -- 更新時間
   jlyh       NVARCHAR2 (255) DEFAULT '', -- 建立用戶
   gxyh       NVARCHAR2 (255) DEFAULT '' -- 更新用戶
);

ALTER TABLE q001l add CONSTRAINT q001l_pk PRIMARY KEY (id);
CREATE INDEX q001l_id01 ON q001l (bdbh);
CREATE INDEX q001l_id02 ON q001l (q001h_id);

DROP SEQUENCE q001l_seq;

CREATE SEQUENCE q001l_seq
   START WITH 0
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;