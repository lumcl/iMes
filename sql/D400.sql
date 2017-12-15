CREATE TABLE D400H
(
  GSDM  NVARCHAR2(4),   -- ��˾����
  BDDM  NVARCHAR2(4),   -- �?����
  BDBH  NVARCHAR2(20),  -- �?���
  SQYH  NVARCHAR2(40), -- ��Ո�Ñ�
  BDRQ  NVARCHAR2(8),  -- �?����
  QCLX  NVARCHAR2(20),   -- ǩ������
  BMMC  NVARCHAR2(40),    -- �������
  WJSX  NVARCHAR2(30),   --�ļ�����
  WJJM  NVARCHAR2(30),   --�ļ�����
  QCAY  NVARCHAR2(100),  --ǩ�ʰ���
  QCNR  NVARCHAR2(1000),  -- ǩ������
  QCNR1 CLOB,             -- ǩ������
  BDFD  NVARCHAR2(200),  -- ǩ�ʸ���
  QHYH  NVARCHAR2(30),-- ǩ���û�
  BDZT  NVARCHAR2(1),  -- ���ˠ�B
  BDJG  NVARCHAR2(1),  -- ���˽Y��
  QHSJ  DATE,  -- ǩ��ʱ��
  JLYH  NVARCHAR2(30),  -- 
  JLSJ  DATE,  
  GXYH  NVARCHAR2(30),
  GXSJ  DATE
)




ALTER TABLE D400H
ADD DYZT CHAR(1);

