/* Formatted on 2012/10/18 9:43:33 (QP5 v5.163.1008.3004) */
DROP TABLE d089l CASCADE CONSTRAINTS;

CREATE TABLE d089l
(
   id     NUMBER (18) DEFAULT 0, -- ID
   gsdm    NVARCHAR2 (4) DEFAULT ' ', -- Company
   bddm    NVARCHAR2 (4) DEFAULT ' ', -- Form Type
   bdbh    NVARCHAR2 (20) DEFAULT ' ', -- Form Number
   sqnr    NUMBER (5) DEFAULT 0, -- Sequence Number
   infnr   NVARCHAR2 (10) DEFAULT ' ', -- Number of Purchasing Info Record
   matnr   NVARCHAR2 (18) DEFAULT ' ', -- Material Number
   matkl   NVARCHAR2 (9) DEFAULT ' ', -- Material Group
   maktx   NVARCHAR2 (40) DEFAULT ' ', -- Short Text
   meins   NVARCHAR2 (3) DEFAULT ' ', -- Unit Of Measure
   ekgrp   NVARCHAR2 (3) DEFAULT ' ', -- Purchase Group
   datab   NVARCHAR2 (8) DEFAULT ' ', -- Price Valid From
   prdat   NVARCHAR2 (8) DEFAULT ' ', -- Price Valid Until
   untpr   NUMBER (15, 8) DEFAULT 0, -- Unit Price
   netpr   NUMBER (11, 2) DEFAULT 0, -- Net Price / Per
   peinh   NUMBER (5) DEFAULT 0, -- Price Unit
   rmbpr   NUMBER (15, 8) DEFAULT 0, -- RMB Price
   oldpr   NUMBER (15, 8) DEFAULT 0, -- Old Price (RMB)
   minpr   NUMBER (15, 8) DEFAULT 0, -- Location Minimum Price (RMB)
   olddf   NUMBER (15, 8) DEFAULT 0, -- Old Price Diff (RMB)
   oldpt   NUMBER (8, 2) DEFAULT 0, -- Old Price Diff %
   mindf   NUMBER (15, 8) DEFAULT 0, -- Loc Minimum Price
   minpt   NUMBER (8, 2) DEFAULT 0, -- Loc Minimum Price %
   plifz   NUMBER (3) DEFAULT 0, -- Lead Time
   bstrf   NUMBER (13, 3) DEFAULT 0, -- MOQ
   chgpo   NVARCHAR2 (1) DEFAULT ' ', -- Change Type (1=BEST, 2=OPEN PO, 3=NEW PO)
   datyp   NVARCHAR2 (1) DEFAULT ' ', -- PO Date Type (C=CREATE/ D=DELIVERY) DATE
   podat   NVARCHAR2 (8) DEFAULT ' ', -- Change PO Date
   dlvdt   NVARCHAR2 (8) DEFAULT ' ', -- Claim Delivery Date
   saptm   DATE DEFAULT '', -- SAP MESSAGE DATE TIME
   sapcf   NVARCHAR2 (1) DEFAULT ' ', -- SAP Update Complete Flag = 'C', DLFT='A'
   saprc   NUMBER (5) DEFAULT 0, -- SAP Remaining Update Count
   sapem   NVARCHAR2 (200) DEFAULT ' ', -- SAP Update Error Message
   msgid   NVARCHAR2 (20) DEFAULT ' ', -- SAP MESSAGE ID
   msgnr   NVARCHAR2 (3) DEFAULT ' ', -- SAP MESSAGE NUMBER
   msgty   NVARCHAR2 (1) DEFAULT ' ', -- SAP MESSAGE TYPE
   msgtx   NVARCHAR2 (220) DEFAULT ' ', -- SAP MESSAGE TEXT
   rfctm   DATE DEFAULT '', -- RFC CALL TIME
   rfccf   NVARCHAR2 (1) DEFAULT ' ', -- RFC COMPLETE FLAG = 'C', DLFT='A'
   rfmid   NVARCHAR2 (20) DEFAULT ' ', -- RFC MESSAGE ID
   rfmnr   NVARCHAR2 (3) DEFAULT ' ', -- RFC MESSAGE NUMBER
   rfmty   NVARCHAR2 (1) DEFAULT ' ', -- RFC MESSAGE TYPE
   rfmtx   NVARCHAR2 (220) DEFAULT ' ', -- RFC MESSAGE TEXT
   dltfg   NVARCHAR2 (1) DEFAULT ' ', -- DELETE FLAG = 'Y', DLFT='N'
   dltid   NVARCHAR2 (30) DEFAULT ' ', -- DELETE USER ID
   dlttx   NVARCHAR2 (200) DEFAULT ' ', -- DELETE REMARK TEXT
   dltdt   DATE DEFAULT '' -- DELETE DATE
);

ALTER TABLE d089l add CONSTRAINT d089l_pk PRIMARY KEY (id);
CREATE INDEX d089l_id01 ON d089l (bdbh);

DROP SEQUENCE d089l_seq;

CREATE SEQUENCE d089l_seq
   START WITH 0
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;