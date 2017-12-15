/* Formatted on 2013/6/12 12:11:48 (QP5 v5.163.1008.3004) */
DROP TABLE d050h CASCADE CONSTRAINTS;

CREATE TABLE d050h (id      NUMBER (18) DEFAULT 0, -- ID
                    gsdm    NVARCHAR2 (4) DEFAULT '', -- 公司代碼
                    bddm    NVARCHAR2 (4) DEFAULT '', -- 表單代碼
                    bdbh    NVARCHAR2 (20) DEFAULT '', -- 表單編碼
                    bdrq    NVARCHAR2 (8) DEFAULT '', -- 表單日期
                    bdzt    NVARCHAR2 (1) DEFAULT '', -- 表單狀態 C=建立 0=簽核中 X=完成
                    bdjg    NVARCHAR2 (1) DEFAULT '', -- 表單結果 Y=核准 N=否決 X=退件
                    bdfd    NVARCHAR2 (255) DEFAULT '', -- 表單附件
                    bdyy    NVARCHAR2 (255) DEFAULT '', -- 表單原因
                    sqyh    NVARCHAR2 (255) DEFAULT '', -- 申請用戶
                    qhyh    NVARCHAR2 (255) DEFAULT '', -- 表單核准人
                    qhsj    DATE DEFAULT '', -- 表單核准時間
                    qhks    NVARCHAR2 (1) DEFAULT '', -- 流程開始標示
                    hqyh    NVARCHAR2 (255) DEFAULT '', -- 會簽用戶
                    jlyh    NVARCHAR2 (255) DEFAULT '', -- 建立用戶
                    jlsj    DATE DEFAULT '', -- 建立時間
                    gxyh    NVARCHAR2 (255) DEFAULT '', -- 更新用戶
                    gxsj    DATE DEFAULT '', -- 更新時間
                    kunnr   NVARCHAR2 (255) DEFAULT '', -- 客戶代號
                    name1   NVARCHAR2 (255) DEFAULT '', -- 客戶名稱
                    sortl   NVARCHAR2 (255) DEFAULT '', -- 客戶簡稱
                    salam   NUMBER (15, 6) DEFAULT 0, -- 銷售金額
                    matam   NUMBER (15, 6) DEFAULT 0 -- 材料金額
                                                    );

ALTER TABLE d050h ADD CONSTRAINT d050h_pk PRIMARY KEY (id);

CREATE INDEX d050h_id01
   ON d050h (bdbh);

DROP SEQUENCE d050h_seq;

CREATE SEQUENCE d050h_seq
   START WITH 1000000001
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;