CREATE TABLE D045H
(
  GSDM       NVARCHAR2(4),    -- 公司代码
  BDDM       NVARCHAR2(4),    -- 表单代码
  BDBH       NVARCHAR2(20),   -- 表单编号
  BDRQ       NVARCHAR2(8),    -- 表单日期
  BDZT       NVARCHAR2(1),    -- 簽核狀態
  BDJG       NVARCHAR2(1),    -- 簽核結果
  BDLX       NVARCHAR2(40),   -- 表单类型
  WERKS      NVARCHAR2(4),    -- 厂别
  CUST       NVARCHAR2(40),   -- 客户名称
  KOSTL      NVARCHAR2(10),   -- 部门编号
  KTEXT      NVARCHAR2(20),   -- 部门名称
  DELADD     NVARCHAR2(200),  -- 交货地点
  BDAMT      NUMBER(15,6),    -- 表单金额
  INVNO      NVARCHAR2(20),   -- 发票号    
  CONO       NVARCHAR2(20),   -- 订单号
  SQYH       NVARCHAR2(30),   -- 签核用户
  QHYH       NVARCHAR2(30),   -- 签核用户
  QHSJ       DATE,            -- 签核时间
  JLYH       NVARCHAR2(30),   -- 用户
  JLSJ       DATE,            -- 时间
  GXYH       NVARCHAR2(30),   -- 用户
  GXSJ       DATE,            -- 时间
  BDFD       NVARCHAR2(100),  -- 签呈附件
  RSNUM      NVARCHAR2(10),   -- 记录数
  SAPUPDSTS  NVARCHAR2(1),
  SAPUPDUSR  NVARCHAR2(30),
  SAPUPDDAT  DATE,
  SAPUPDMSG  NVARCHAR2(200)
)