CREATE TABLE D045H
(
  GSDM       NVARCHAR2(4),    -- ��˾����
  BDDM       NVARCHAR2(4),    -- ������
  BDBH       NVARCHAR2(20),   -- �����
  BDRQ       NVARCHAR2(8),    -- ������
  BDZT       NVARCHAR2(1),    -- ���ˠ�B
  BDJG       NVARCHAR2(1),    -- ���˽Y��
  BDLX       NVARCHAR2(40),   -- ������
  WERKS      NVARCHAR2(4),    -- ����
  CUST       NVARCHAR2(40),   -- �ͻ�����
  KOSTL      NVARCHAR2(10),   -- ���ű��
  KTEXT      NVARCHAR2(20),   -- ��������
  DELADD     NVARCHAR2(200),  -- �����ص�
  BDAMT      NUMBER(15,6),    -- �����
  INVNO      NVARCHAR2(20),   -- ��Ʊ��    
  CONO       NVARCHAR2(20),   -- ������
  SQYH       NVARCHAR2(30),   -- ǩ���û�
  QHYH       NVARCHAR2(30),   -- ǩ���û�
  QHSJ       DATE,            -- ǩ��ʱ��
  JLYH       NVARCHAR2(30),   -- �û�
  JLSJ       DATE,            -- ʱ��
  GXYH       NVARCHAR2(30),   -- �û�
  GXSJ       DATE,            -- ʱ��
  BDFD       NVARCHAR2(100),  -- ǩ�ʸ���
  RSNUM      NVARCHAR2(10),   -- ��¼��
  SAPUPDSTS  NVARCHAR2(1),
  SAPUPDUSR  NVARCHAR2(30),
  SAPUPDDAT  DATE,
  SAPUPDMSG  NVARCHAR2(200)
)