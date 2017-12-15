/* Formatted on 2012/10/27 16:25:54 (QP5 v5.163.1008.3004) */
DROP TABLE eqpmas CASCADE CONSTRAINTS;

CREATE TABLE eqpmas
(
   id       NUMBER (18) DEFAULT 0, -- id
   cmpnbr   NVARCHAR2 (4) DEFAULT '', -- 公司
   facnbr   NVARCHAR2 (4) DEFAULT '', -- 工廠
   eqpnbr   NVARCHAR2 (255) DEFAULT '', -- 物件號碼 (唯一碼)
   eqpdes   NVARCHAR2 (255) DEFAULT '', -- 物件說明
   eqpmdl   NVARCHAR2 (255) DEFAULT '', -- 物件型號
   eqpspc   NVARCHAR2 (255) DEFAULT '', -- 物件規格
   eqpsts   NVARCHAR2 (2) DEFAULT '', -- 物件狀態
   astnbr   NVARCHAR2 (255) DEFAULT '', -- 財產編號
   eqptyp   NVARCHAR2 (255) DEFAULT '', -- 物件大類
   eqpgrp   NVARCHAR2 (255) DEFAULT '', -- 物件小類
   fixtyp   NVARCHAR2 (255) DEFAULT '', -- 固定資產 / 列管資產
   duedat   NVARCHAR2 (8) DEFAULT '', -- 預計資產到期日
   locate   NVARCHAR2 (255) DEFAULT '', -- 放置位置
   rspdep   NVARCHAR2 (255) DEFAULT '', -- 保管部門
   rspuid   NVARCHAR2 (255) DEFAULT '', -- 保管人
   eqpqty   NUMBER (15, 2) DEFAULT 0, -- 數量
   eqpuom   NVARCHAR2 (4) DEFAULT '', -- 單位
   locker   NVARCHAR2 (255) DEFAULT '', -- 治具放置櫃
   prdnbr   NVARCHAR2 (255) DEFAULT '', -- 使用機種
   prddes   NVARCHAR2 (255) DEFAULT '', -- 使用機種說明
   pcbnbr   NVARCHAR2 (255) DEFAULT '', -- PCB板號
   compnr   NVARCHAR2 (255) DEFAULT '', -- 組件
   compmd   NVARCHAR2 (255) DEFAULT '', -- 組件型號
   serial   NVARCHAR2 (255) DEFAULT '', -- 機身編號
   prdlin   NVARCHAR2 (255) DEFAULT '', -- 線別
   strmnt   NVARCHAR2 (8) DEFAULT '', -- 開始保養日期
   mntcyc   NUMBER (8) DEFAULT 0, -- 保養週期 (天)
   mntdat   NVARCHAR2 (8) DEFAULT '', -- 保養日期
   remark   NVARCHAR2 (255) DEFAULT '', -- 備註
   mfgnam   NVARCHAR2 (255) DEFAULT '', -- 生產廠家
   brand    NVARCHAR2 (255) DEFAULT '', -- 品牌
   buyer    NVARCHAR2 (255) DEFAULT '', -- 購買者
   purdat   NVARCHAR2 (8) DEFAULT '', -- 購買日期
   rmbamt   NUMBER (15, 2) DEFAULT 0, -- 購買價格 (RMB)
   purdoc   NVARCHAR2 (255) DEFAULT '', -- 購買參考文件
   appdoc   NVARCHAR2 (255) DEFAULT '', -- 簽呈編號
   iqcuid   NVARCHAR2 (255) DEFAULT '', -- 驗收人員
   iqcdat   NVARCHAR2 (8) DEFAULT '', -- 驗收日期
   wtydue   NVARCHAR2 (8) DEFAULT '', -- 質保到期日
   vendor   NVARCHAR2 (255) DEFAULT '', -- 供應商編號
   vdrnam   NVARCHAR2 (255) DEFAULT '', -- 供應商公司名稱
   vdrcnt   NVARCHAR2 (255) DEFAULT '', -- 供應商聯繫人
   vdradd   NVARCHAR2 (255) DEFAULT '', -- 供應商聯繫地址
   vdrphn   NVARCHAR2 (255) DEFAULT '', -- 供應商聯繫電話
   crtdat   DATE DEFAULT '', -- 建立時間
   crtuid   NVARCHAR2 (255) DEFAULT '', -- 建立用戶
   chgdat   DATE DEFAULT '', -- 更改時間
   chguid   NVARCHAR2 (255) DEFAULT '' -- 更改用戶
);

DROP SEQUENCE eqpmas_seq;

CREATE SEQUENCE eqpmas_seq
   START WITH 1
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;