/* Formatted on 2013/3/4 17:55:34 (QP5 v5.163.1008.3004) */
DROP TABLE bcinsp CASCADE CONSTRAINTS;

CREATE TABLE bcinsp (id             NUMBER (18) DEFAULT 0, -- id
                     bcdnr          NVARCHAR2 (128) DEFAULT '', -- 掃描序號
                     mtype          NUMBER (16, 6) DEFAULT 0, -- MTYPE
                     mev            NUMBER (16, 6) DEFAULT 0, -- MEV
                     mtcp           NUMBER (16, 6) DEFAULT 0, -- MTCP
                     led_vout_1_1   NUMBER (16, 6) DEFAULT 0, -- LED_VOUT_1_1
                     led_iout_1_1   NUMBER (16, 6) DEFAULT 0, -- LED_IOUT_1_1
                     vout_2_1_2     NUMBER (16, 6) DEFAULT 0, -- VOUT_2_1_2
                     iout_2_1_2     NUMBER (16, 6) DEFAULT 0, -- IOUT_2_1_2
                     vout_1_1_3     NUMBER (16, 6) DEFAULT 0, -- VOUT_1_1_3
                     iout_1_1_3     NUMBER (16, 6) DEFAULT 0, -- IOUT_1_1_3
                     vout_2_1_3     NUMBER (16, 6) DEFAULT 0, -- VOUT_2_1_3
                     iout_2_1_3     NUMBER (16, 6) DEFAULT 0, -- IOUT_2_1_3
                     thd_1_4        NUMBER (16, 6) DEFAULT 0, -- THD_1_4
                     pf_1_6         NUMBER (16, 6) DEFAULT 0, -- PF_1_6
                     eff_1_6        NUMBER (16, 6) DEFAULT 0, -- EFF_1_6
                     ovp_trip_1_7   NUMBER (16, 6) DEFAULT 0, -- OVP_TRIP_1_7
                     iinrms_1_6     NUMBER (16, 6) DEFAULT 0, -- IINRMS_1_6
                     pin_1_4        NUMBER (16, 6) DEFAULT 0, -- PIN_1_4
                     pout_1_4       NUMBER (16, 6) DEFAULT 0 -- POUT_1_4
                                                            );

ALTER TABLE bcinsp ADD CONSTRAINT bcinsp_pk PRIMARY KEY (id);

CREATE INDEX bcinsp_id01
   ON bcinsp (bcdnr);

DROP SEQUENCE bcinsp_seq;

CREATE SEQUENCE bcinsp_seq
   START WITH 0
   MAXVALUE 999999999999999999999999999
   MINVALUE 0
   NOCYCLE
   NOCACHE
   NOORDER;