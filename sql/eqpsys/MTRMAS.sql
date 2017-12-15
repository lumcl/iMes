/* Formatted on 2012/10/28 15:06:04 (QP5 v5.163.1008.3004) */
DROP TABLE mtrmas CASCADE CONSTRAINTS;

CREATE TABLE mtrmas
(
   id       NUMBER (18) DEFAULT 0, -- id
   cmpnbr   NVARCHAR2 (4) DEFAULT '', -- 公司
   facnbr   NVARCHAR2 (4) DEFAULT '', -- 工廠
   matnbr   NVARCHAR2 (255) DEFAULT '', -- 物料號碼
   mattyp   NVARCHAR2 (256) DEFAULT '', -- 物料大類
   matgrp   NVARCHAR2 (257) DEFAULT '', -- 物料小類
   matsts   NVARCHAR2 (2) DEFAULT '', -- 物料狀態
   matdes   NVARCHAR2 (255) DEFAULT '', -- 物料說明
   matmdl   NVARCHAR2 (255) DEFAULT '', -- 物料型號
   matspe   NVARCHAR2 (255) DEFAULT '', -- 物料規格
   matbrd   NVARCHAR2 (255) DEFAULT '', -- 物料品牌
   safqty   NUMBER (15, 6) DEFAULT 0, -- 安全數量
   stkqty   NUMBER (15, 6) DEFAULT 0, -- 庫存數量
   matuom   NVARCHAR2 (4) DEFAULT '', -- 物料單位
   mfgnam   NVARCHAR2 (255) DEFAULT '', -- 生產廠家
   locate   NVARCHAR2 (255) DEFAULT '', -- 放置位置
   repdep   NVARCHAR2 (255) DEFAULT '', -- 保管部門
   repuid   NVARCHAR2 (255) DEFAULT '', -- 保管人
   crtdat   DATE DEFAULT '', -- 建立时间
   crtuid   NVARCHAR2 (255) DEFAULT '', -- 建立用户
   chgdat   DATE DEFAULT '', -- 更改时间
   chguid   NVARCHAR2 (255) DEFAULT '' -- 更改用户
);

DROP SEQUENCE mtrmas_seq;

CREATE SEQUENCE mtrmas_seq
   START WITH 1
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;