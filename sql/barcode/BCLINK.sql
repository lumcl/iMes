/* Formatted on 2013/3/2 9:20:22 (QP5 v5.163.1008.3004) */
DROP TABLE bclink CASCADE CONSTRAINTS;

CREATE TABLE bclink (id       NUMBER (18) DEFAULT 0, -- id
                     bcdnr    NVARCHAR2 (128) DEFAULT '', -- parent id
                     cbcdnr   NVARCHAR2 (128) DEFAULT '', -- child id
                     posnr    NVARCHAR2 (30) DEFAULT '', -- position
                     ernam    NVARCHAR2 (30) DEFAULT '', -- enter user
                     erdat    NVARCHAR2 (8) DEFAULT '', -- enter date
                     ertim    NVARCHAR2 (6) DEFAULT '' -- enter time
                                                      );

ALTER TABLE bclink ADD CONSTRAINT bclink_pk PRIMARY KEY (id);

CREATE INDEX bclink_id01
   ON bclink (bcdnr);

CREATE INDEX bclink_id02
   ON bclink (cbcdnr);

DROP SEQUENCE bclink_seq;

CREATE SEQUENCE bclink_seq
   START WITH 0
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;