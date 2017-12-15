/* Formatted on 2013/3/2 9:14:38 (QP5 v5.163.1008.3004) */
DROP TABLE bcmast CASCADE CONSTRAINTS;

CREATE TABLE bcmast (id      NUMBER (18) DEFAULT 0, -- id
                     bcdnr   NVARCHAR2 (128) DEFAULT '', -- 掃描序號
                     matnr   NVARCHAR2 (18) DEFAULT '', -- 料號
                     charg   NVARCHAR2 (10) DEFAULT '', -- 批次
                     erdat   NVARCHAR2 (8) DEFAULT '', -- 輸入日期
                     ertim   NVARCHAR2 (6) DEFAULT '', -- 輸入時間
                     ernam   NVARCHAR2 (30) DEFAULT '', -- 輸入用戶
                     loekz   NVARCHAR2 (1) DEFAULT '', -- 刪除旗標
                     stats   NVARCHAR2 (2) DEFAULT '', -- 狀態
                     aufnr   NVARCHAR2 (12) DEFAULT '', -- 工單號
                     ebeln   NVARCHAR2 (10) DEFAULT '', -- 採購單
                     ebelp   NVARCHAR2 (6) DEFAULT '', -- 採購單行號
                     maktx   NVARCHAR2 (40) DEFAULT '', -- 機種說明
                     matkl   NVARCHAR2 (10) DEFAULT '', -- 機種類別
                     linfr   NVARCHAR2 (10) DEFAULT '', -- 供應商號碼
                     sortl   NVARCHAR2 (40) DEFAULT '', -- 供應商名稱
                     werks   NVARCHAR2 (4) DEFAULT '', -- 工廠
                     budat   NVARCHAR2 (8) DEFAULT '', -- 收貨日期
                     mfgdt   NVARCHAR2 (8) DEFAULT '', -- 生產日期
                     batch   NVARCHAR2 (100) DEFAULT '' -- 供應商批次
                                                       );

ALTER TABLE bcmast ADD CONSTRAINT bcmast_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX bcmast_id01
   ON bcmast (bcdnr);

DROP SEQUENCE bcmast_seq;

CREATE SEQUENCE bcmast_seq
   START WITH 0
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;