ALTER TABLE AURI_OWNER_1.ATTRIBUTI_OST
 DROP PRIMARY KEY CASCADE;

DROP TABLE AURI_OWNER_1.ATTRIBUTI_OST CASCADE CONSTRAINTS;

CREATE TABLE AURI_OWNER_1.ATTRIBUTI_OST
(
  ID_ATTRIBUTO        NUMBER                    NOT NULL,
  NATURA              VARCHAR2(3 BYTE),
  TIPO                VARCHAR2(50 BYTE)         NOT NULL,
  CATEGORIA           VARCHAR2(3 BYTE)          NOT NULL,
  ATTRIBUTO           VARCHAR2(50 BYTE)         NOT NULL,
  FLG_OBBL            VARCHAR2(1 BYTE)          DEFAULT 0,
  FLG_MULTIPLO        VARCHAR2(1 BYTE)          DEFAULT 0,
  CLASSE_VOCABOLARIO  VARCHAR2(45 BYTE),
  ID_PADRE            NUMBER(22),
  LABEL               VARCHAR2(100 BYTE),
  DIMENSIONE          VARCHAR2(10 BYTE),
  NOME_COMPLESSO      VARCHAR2(400 BYTE)
)
TABLESPACE DM_TBS_D_001
RESULT_CACHE (MODE DEFAULT)
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE UNIQUE INDEX AURI_OWNER_1.ATTRIBUTI_OST_PK ON AURI_OWNER_1.ATTRIBUTI_OST
(ID_ATTRIBUTO)
LOGGING
TABLESPACE DM_TBS_D_001
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MAXSIZE          UNLIMITED
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;

ALTER TABLE AURI_OWNER_1.ATTRIBUTI_OST ADD (
  CONSTRAINT ATTRIBUTI_OST_PK
  PRIMARY KEY
  (ID_ATTRIBUTO)
  USING INDEX AURI_OWNER_1.ATTRIBUTI_OST_PK
  ENABLE VALIDATE);

 <!--INIZIALIZZAZIONE NESTED TABLE -->
  
update dmt_proc_obj_types m set attr_add_x_proc_obj_del_tipo=NVL(attr_add_x_proc_obj_del_tipo, dmto_attr_values()) where m.id_proc_obj_type=:myId

insert into TABLE(select m.attr_add_x_proc_obj_del_tipo from dmt_proc_obj_types m where m.id_proc_obj_type=215)
 (ATTR_NAME, NRO_POSIZIONE, FLG_OBBLIG, MAX_NUM_VALUES) SELECT ATTR_NAME, rownum ,0 ,1 
 from dmt_attributes_def d WHERE d.attr_name LIKE 'UNITALOCALE%' and (d.ATTR_NAME_SUP is null OR d.ATTR_TYPE='COMPLEX')