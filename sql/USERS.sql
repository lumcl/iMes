
ALTER TABLE USERS 
ADD FUND400 char(1) DEFAULT '';

ALTER TABLE USERS
ADD (
  XL        CHAR(1 BYTE),
  JL        CHAR(1 BYTE),
  FL        CHAR(1 BYTE),
  KZ        CHAR(1 BYTE),
  ZJL       CHAR(1 BYTE)
  );
  
  

ALTER TABLE IMES_TX.USERS
ADD (
  NOTES       CHAR(30 BYTE)
  );