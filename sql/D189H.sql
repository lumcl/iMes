CREATE TABLE D189H
(
  GSDM  NVARCHAR2(4),    -- 公司代码
  BDDM  NVARCHAR2(4),    -- 表单代码
  BDBH  NVARCHAR2(20),   -- 表单编号
  SQYH  NVARCHAR2(40),   -- 申請用戶
  BDRQ  NVARCHAR2(8),    -- 表单日期
  BMMC  NVARCHAR2(40),   -- 部门名称
  BDFD  NVARCHAR2(200),  -- 签呈附件
  QHYH  NVARCHAR2(30),   -- 签核用户
  BDZT  NVARCHAR2(1),    -- 簽核狀態
  BDJG  NVARCHAR2(1),    -- 簽核結果
  QHSJ  DATE,            -- 签核时间
  JLYH  NVARCHAR2(30),   -- 
  JLSJ  DATE,  
  GXYH  NVARCHAR2(30),
  GXSJ  DATE,
  CGYY  CLOB,            -- 重工原因说明
  CGFS  CLOB,            -- 重工处理方式说明
  KCCB  NVARCHAR2(10),   -- 库存仓别
  KHBH  NVARCHAR2(40),   -- 客户编号
  SJBH  NVARCHAR2(40),   -- 设计编号
  CHSJ  DATE,            -- 出货日期
  CQTY  NUMBER(15,0),    -- 重工数量
  SMH   NVARCHAR2(10),   -- 重工说明会:需要，不需要
  DYZT  CHAR(4)   -- 打印状态
)





