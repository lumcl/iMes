/* Formatted on 2012/10/24 11:30:24 (QP5 v5.163.1008.3004) */
DROP TABLE q001h CASCADE CONSTRAINTS;

CREATE TABLE q001h
(
   id     NUMBER (18) DEFAULT 0, -- ID
   gsdm   NVARCHAR2 (4) DEFAULT '', -- 公司代碼
   bddm   NVARCHAR2 (4) DEFAULT '', -- 表單代碼
   bdbh   NVARCHAR2 (20) DEFAULT '', -- 表單編碼
   bdrq   NVARCHAR2 (8) DEFAULT '', -- 表單日期
   bdzt   NVARCHAR2 (1) DEFAULT '', -- 表單狀態 C=建立 0=簽核中 X=完成
   bdjg   NVARCHAR2 (1) DEFAULT '', -- 表單結果 Y=核准 N=否決 X=退件
   bdfd   NVARCHAR2 (255) DEFAULT '', -- 表單附件
   bdyy   NVARCHAR2 (30) DEFAULT '', -- 表單原因
   sqyh   NVARCHAR2 (30) DEFAULT '', -- 申請用戶
   qhyh   NVARCHAR2 (30) DEFAULT '', -- 表單核准人
   qhsj   DATE DEFAULT '', -- 表單核准時間
   qhks   NVARCHAR2 (1) DEFAULT '', -- 流程開始標示
   hqyh   NVARCHAR2 (255) DEFAULT '', -- 會簽用戶
   jlyh   NVARCHAR2 (30) DEFAULT '', -- 建立用戶
   jlsj   DATE DEFAULT '', -- 建立時間
   gxyh   NVARCHAR2 (30) DEFAULT '', -- 更新用戶
   gxsj   DATE DEFAULT '', -- 更新時間
   lflb   NVARCHAR2 (255) DEFAULT '', -- 來訪類別
   lfdw   NVARCHAR2 (255) DEFAULT '', -- 來訪單位
   lfdd   NVARCHAR2 (255) DEFAULT '', -- 來訪擔當
   ydrq   NVARCHAR2 (8) DEFAULT '', -- 預計到達日期
   ydsj   NVARCHAR2 (4) DEFAULT '', -- 預計到達時間
   ylrq   NVARCHAR2 (8) DEFAULT '', -- 預計離開日期
   ylsj   NVARCHAR2 (4) DEFAULT '', -- 預計離開時間
   lfmd   NVARCHAR2 (255) DEFAULT '', -- 來訪目的
   pbry   NVARCHAR2 (255) DEFAULT '', -- 陪伴人員
   gjzg   NVARCHAR2 (255) DEFAULT '', -- 高階主管
   hqrq   NVARCHAR2 (8) DEFAULT '', -- 會前會日期
   hqsj   NVARCHAR2 (4) DEFAULT '', -- 會前會時間
   bezu   NVARCHAR2 (512) DEFAULT '', -- 備註
   jcfs   NVARCHAR2 (255) DEFAULT '', -- 客戶進場方式
   jsdd   NVARCHAR2 (255) DEFAULT '', -- 接送地點
   bfrs   NUMBER (3) DEFAULT 0, -- 拜訪人數
   jcdd   NVARCHAR2 (255) DEFAULT '', -- 就餐地點
   jcbz   NUMBER (10, 2) DEFAULT 0, -- 就餐標準
   jcsj   NVARCHAR2 (255) DEFAULT '', -- 就餐時間
   jcry   NVARCHAR2 (255) DEFAULT '', -- 就餐人員
   fygs   NVARCHAR2 (255) DEFAULT '', -- 就餐費用歸屬
   zsap   NVARCHAR2 (255) DEFAULT '', -- 住宿安排
   zsts   NUMBER (3) DEFAULT 0, -- 住宿天數
   hyzy   NVARCHAR2 (255) DEFAULT '', -- 會議室資源
   xlfs   NUMBER (3) DEFAULT 0, -- 公司型錄分數
   qtzy   NVARCHAR2 (255) DEFAULT '', -- 其他資源
   zwbz   NVARCHAR2 (512) DEFAULT '', -- 總務備註
   sdrq   NVARCHAR2 (8) DEFAULT '', -- 實際到達日期
   sdsj   NVARCHAR2 (4) DEFAULT '', -- 實際到達時間
   slrq   NVARCHAR2 (8) DEFAULT '', -- 實際離開日期
   slsj   NVARCHAR2 (4) DEFAULT '' -- 實際離開時間
);

ALTER TABLE q001h add CONSTRAINT q001h_pk PRIMARY KEY (id);
CREATE INDEX q001h_id01 ON q001h (bdbh);

DROP SEQUENCE q001h_seq;

CREATE SEQUENCE q001h_seq
   START WITH 0
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;