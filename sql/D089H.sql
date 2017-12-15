/* Formatted on 2012/10/18 9:43:25 (QP5 v5.163.1008.3004) */
DROP TABLE d089h CASCADE CONSTRAINTS;

CREATE TABLE d089h
(
	 id     NUMBER (18) DEFAULT 0, -- ID
   gsdm    NVARCHAR2 (4) DEFAULT ' ', -- Company
   bddm    NVARCHAR2 (4) DEFAULT ' ', -- Form Type
   bdbh    NVARCHAR2 (20) DEFAULT ' ', -- Form Number
   bdrq    NVARCHAR2 (8) DEFAULT ' ', -- Form Date
   bdzt    NVARCHAR2 (1) DEFAULT ' ', -- Form Status
   bdjg    NVARCHAR2 (1) DEFAULT ' ', -- Form Result
   bdfd    NVARCHAR2 (200) DEFAULT ' ', -- Attachment
   bdyy    NVARCHAR2 (30) DEFAULT ' ', -- Form Reason Code
   bdnr    NVARCHAR2 (500) DEFAULT ' ', -- Long Text
   sqyh    NVARCHAR2 (30) DEFAULT ' ', -- Created By
   qhyh    NVARCHAR2 (30) DEFAULT ' ', -- Sign By
   qhsj    DATE DEFAULT '', -- Sign At
   qhks    NVARCHAR2 (1) DEFAULT ' ', -- Approved Flow Start
   jlyh    NVARCHAR2 (30) DEFAULT ' ', -- Created By
   jlsj    DATE DEFAULT '', -- Created At
   gxyh    NVARCHAR2 (30) DEFAULT ' ', -- Changed By
   gxsj    DATE DEFAULT '', -- Changed At
   refnr   NVARCHAR2 (30) DEFAULT ' ', -- Supplier Reference Number
   mandt   NVARCHAR2 (3) DEFAULT ' ', -- Client
   ekorg   NVARCHAR2 (4) DEFAULT ' ', -- Purchasing Organization
   werks   NVARCHAR2 (4) DEFAULT ' ', -- Plant
   lifnr   NVARCHAR2 (10) DEFAULT ' ', -- Vendor Account Number
   name1   NVARCHAR2 (35) DEFAULT ' ', -- Name1
   sortl   NVARCHAR2 (10) DEFAULT ' ', -- Short Name
   mwskz   NVARCHAR2 (2) DEFAULT ' ', -- Sales Tax Code
   waers   NVARCHAR2 (5) DEFAULT ' ', -- Currency Key
   bdamt   NUMBER (15, 6) DEFAULT 0, -- Amount
   minpt   NUMBER (8, 2) DEFAULT 0, -- Minimum Percentage
   maxpt   NUMBER (8, 2) DEFAULT 0, -- Maximun Percentage
   snamt   NUMBER (15, 2) DEFAULT 0, -- Tin
   agamt   NUMBER (15, 2) DEFAULT 0, -- Silver
   cuamt   NUMBER (15, 2) DEFAULT 0, -- Copper
   mtcur   NVARCHAR2 (4) DEFAULT ' ', -- Metal Currency
   saptm   DATE DEFAULT '', -- SAP UPDATE DATE TIME
   sapcf   NVARCHAR2 (1) DEFAULT ' ', -- SAP Update Complete Flag
   saprc   NUMBER (5) DEFAULT 0, -- SAP Remaining Update Count
   sapem   NVARCHAR2 (200) DEFAULT ' ', -- SAP MESSAGE TEXT
   rfctm   DATE DEFAULT '', -- RFC CALL TIME
   rfccf   NVARCHAR2 (1) DEFAULT ' ' -- RFC COMPLETE FLAG = 'X'
);

ALTER TABLE d089h add CONSTRAINT d089h_pk PRIMARY KEY (id);
CREATE INDEX d089h_id01 ON d089h (bdbh);

DROP SEQUENCE d089h_seq;

CREATE SEQUENCE d089h_seq
   START WITH 0
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;