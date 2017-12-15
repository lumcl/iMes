/* Formatted on 2013/6/12 14:25:23 (QP5 v5.163.1008.3004) */
DROP TABLE d050m CASCADE CONSTRAINTS;

CREATE TABLE d050m (id       NUMBER (18) DEFAULT 0, -- ID
                    bdbh     NVARCHAR2 (20) DEFAULT '', -- 表單編碼
                    aufnr    NVARCHAR2 (255) DEFAULT '', -- 工單
                    werks    NVARCHAR2 (255) DEFAULT '', -- 工廠
                    matnr    NVARCHAR2 (255) DEFAULT '', -- 料號
                    matkl    NVARCHAR2 (255) DEFAULT '', -- 類別
                    maktx    NVARCHAR2 (255) DEFAULT '', -- 規格
                    meins    NVARCHAR2 (255) DEFAULT '', -- 單位
                    bomqty   NUMBER (15, 6) DEFAULT 0, -- 單位用量
                    reqqty   NUMBER (15, 6) DEFAULT 0, -- 需求用量
                    stkqty   NUMBER (15, 6) DEFAULT 0, -- 庫存數量
                    posqty   NUMBER (15, 6) DEFAULT 0, -- 採購數量
                    ebeln    NVARCHAR2 (512) DEFAULT '', -- 採購訂單
                    ebelp    NVARCHAR2 (512) DEFAULT '', -- 採購行號
                    emeng    NVARCHAR2 (512) DEFAULT '', -- 採購數量
                    cfmqty   NUMBER (15, 6) DEFAULT 0, -- 確認數量
                    cfmusr   NVARCHAR2 (255) DEFAULT '', -- 確認用戶
                    buyer    NVARCHAR2 (255) DEFAULT '', -- 採購員
                    loekz    NVARCHAR2 (1) DEFAULT '', -- 刪除標誌
                    notes    NVARCHAR2 (255) DEFAULT '', -- 文字
                    usdpr    NUMBER (15, 6) DEFAULT 0, -- USD價格
                    usdam    NUMBER (15, 6) DEFAULT 0, -- USD金額
                    jlyh     NVARCHAR2 (255) DEFAULT '', -- 建立用戶
                    jlsj     DATE DEFAULT '', -- 建立時間
                    gxyh     NVARCHAR2 (255) DEFAULT '', -- 更新用戶
                    gxsj     DATE DEFAULT '' -- 更新時間
                                            );

ALTER TABLE d050m ADD CONSTRAINT d050m_pk PRIMARY KEY (id);

CREATE INDEX d050m_id01
   ON d050m (bdbh);

DROP SEQUENCE d050m_seq;

CREATE SEQUENCE d050m_seq
   START WITH 1000000001
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;