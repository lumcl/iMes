
CREATE TABLE PRD001
(
   id     NUMBER (18) DEFAULT 0, -- id
   line   NVARCHAR2 (40) DEFAULT '', -- ���e
   mo    NVARCHAR2 (40) DEFAULT '', -- MO
   factqty   NUMBER (18) DEFAULT 0, -- ���H����
   planqty   NUMBER (18) DEFAULT 0, -- Ӌ������
   scsj   DATE DEFAULT '', -- �����r�g
   jlsj   DATE DEFAULT '', -- �����r�g
   gxsj   DATE DEFAULT '', -- ���r�g
   jlyh   NVARCHAR2 (255) DEFAULT '', -- �����Ñ�
   gxyh   NVARCHAR2 (255) DEFAULT '' -- �����Ñ�
);


