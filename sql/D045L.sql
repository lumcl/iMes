CREATE TABLE D045L
(
  GSDM       NVARCHAR2(4),    -- 公司代码
  BDDM       NVARCHAR2(4),    -- 表单代码
  BDBH       NVARCHAR2(20),   -- 表单编号
  SQNR       NUMBER(6),       -- 项数  
  CMATNR     NVARCHAR2(18),   -- 元件 
  CWERKS     NVARCHAR2(4),    -- 厂别 
  CMATKL     NVARCHAR2(9),    -- 采购分类 
  CMAKTX     NVARCHAR2(40),   -- 规格 
  CCUM       NVARCHAR2(10),   -- 单位 
  CCQTY      NUMBER(15,6),   -- 数量 
  CPRICE     NUMBER(15,6),   -- 单价 
  CCURR      NVARCHAR2(30),   -- 货币 
  CWEIGHT    NUMBER(15,6),   -- 单重 
  JLYH       NVARCHAR2(30),
  JLSJ       DATE,
  GXYH       NVARCHAR2(30),
  GXSJ       DATE
)