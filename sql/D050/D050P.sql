/* Formatted on 2013/6/12 12:32:30 (QP5 v5.163.1008.3004) */
DROP TABLE d050p CASCADE CONSTRAINTS;

CREATE TABLE d050p (id      NUMBER (18) DEFAULT 0, -- ID
                    bdbh    NVARCHAR2 (20) DEFAULT '', -- 表單編碼
                    aufnr   NVARCHAR2 (255) DEFAULT '', -- 工單
                    werks   NVARCHAR2 (255) DEFAULT '', -- 工廠
                    matnr   NVARCHAR2 (255) DEFAULT '', -- 料號
                    matkl   NVARCHAR2 (255) DEFAULT '', -- 類別
                    maktx   NVARCHAR2 (255) DEFAULT '', -- 規格
                    menge   NUMBER (15, 6) DEFAULT 0, -- 數量
                    meins   NVARCHAR2 (255) DEFAULT '', -- 單位
                    duedt   NVARCHAR2 (8) DEFAULT '', -- 到期日期
                    matam   NUMBER (15, 6) DEFAULT 0 --
                                                    );

ALTER TABLE d050p ADD CONSTRAINT d050p_pk PRIMARY KEY (id);

CREATE INDEX d050p_id01
   ON d050p (bdbh);

DROP SEQUENCE d050p_seq;

CREATE SEQUENCE d050p_seq
   START WITH 1000000001
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;