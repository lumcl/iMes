
CREATE TABLE PRD001
(
   id     NUMBER (18) DEFAULT 0, -- id
   line   NVARCHAR2 (40) DEFAULT '', -- 線別
   mo    NVARCHAR2 (40) DEFAULT '', -- MO
   factqty   NUMBER (18) DEFAULT 0, -- 實際數量
   planqty   NUMBER (18) DEFAULT 0, -- 計畫數量
   scsj   DATE DEFAULT '', -- 生产時間
   jlsj   DATE DEFAULT '', -- 建立時間
   gxsj   DATE DEFAULT '', -- 更新時間
   jlyh   NVARCHAR2 (255) DEFAULT '', -- 建立用戶
   gxyh   NVARCHAR2 (255) DEFAULT '' -- 更新用戶
);


