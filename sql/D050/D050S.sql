/* Formatted on 2013/6/12 12:16:55 (QP5 v5.163.1008.3004) */
DROP TABLE d050s CASCADE CONSTRAINTS;

CREATE TABLE d050s (id      NUMBER (18) DEFAULT 0, -- ID
                    bdbh    NVARCHAR2 (20) DEFAULT '', -- 表單編碼
                    vbeln   NVARCHAR2 (255) DEFAULT '', -- 訂單號
                    posnr   NVARCHAR2 (255) DEFAULT '', -- 行號
                    werks   NVARCHAR2 (255) DEFAULT '', -- 工廠
                    matnr   NVARCHAR2 (255) DEFAULT '', -- 料號
                    matkl   NVARCHAR2 (255) DEFAULT '', -- 類別
                    maktx   NVARCHAR2 (255) DEFAULT '', -- 規格
                    menge   NUMBER (15, 6) DEFAULT 0, -- 數量
                    meins   NVARCHAR2 (255) DEFAULT '', -- 單位
                    waerk   NVARCHAR2 (255) DEFAULT '', -- 幣別
                    netpr   NUMBER (15, 6) DEFAULT 0, -- 單價
                    netwr   NUMBER (15, 6) DEFAULT 0, -- 金額
                    exrat   NUMBER (15, 6) DEFAULT 0, -- 匯率
                    usdpr   NUMBER (15, 6) DEFAULT 0, -- USD價格
                    usdam   NUMBER (15, 6) DEFAULT 0, -- USD金額
                    duedt   NVARCHAR2 (8) DEFAULT '' -- 到期日期
                                                    );

ALTER TABLE d050s ADD CONSTRAINT d050s_pk PRIMARY KEY (id);

CREATE INDEX d050s_id01
   ON d050s (bdbh);

DROP SEQUENCE d050s_seq;

CREATE SEQUENCE d050s_seq
   START WITH 1000000001
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;