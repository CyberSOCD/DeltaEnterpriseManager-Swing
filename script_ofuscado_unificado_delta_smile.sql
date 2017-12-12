----------------------------------------------------------------------------------------------------------------------------
--							SCRIPT_OFUSCADO_UNIFICADO_MINORISTAS - VERSION. 1.6
----------------------------------------------------------------------------------------------------------------------------
-- PARAMETROS
--		&APP_DNS			URL del balanceador (si hay), en caso de no ser necesario dar valor NULL
--		&SERVER_DNS			El DNS del servidor del entorno
--		&SERVER_DNS2		El DNS del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
--		&SERVER_DNS3		El DNS del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
--		&SERVER_DNS4		El DNS del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
--		&SERVER_IP			La IP del servidor del entorno
--		&SERVER_IP2			La IP del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
--		&SERVER_IP3			La IP del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
--		&SERVER_IP4			La IP del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
--		&SERVER_PORT		Puerto utilizado por el DNS
--		&SERVICE_DB			Utilizado para ofuscar campos de correo
--		&FAX				Utilizado para ofuscar Fax. Valor por defecto:99999999999
--		&FTP_COMUN			Servidor FTP comun para todos los entornos (Solicitado Sonsoles)
--		&FTP_COMUN_USER		Usuario acceso FTP comun para todos los entornos (Solicitado Sonsoles)
--		&FTP_COMUN_PASS		Password FTP comun para todos los entornos (Solicitado Sonsoles)
--		&SERVER_FTP_USER	Usuario FTP acceso al entorno
--		&SERVER_FTP_PASS	Password FTP acceso al entorno
----------------------------------------------------------------------------------------------------------------------------
define APP_DNS=&1
define SERVER_DNS=&2
define SERVER_DNS2=&3
define SERVER_DNS3=&4
define SERVER_DNS4=&5
define SERVER_IP=&6
define SERVER_IP2=&7
define SERVER_IP3=&8
define SERVER_IP4=&9
define SERVER_PORT=&10
define SERVICE_DB=&11
define FAX=&12
define FTP_COMUN=&13
define FTP_COMUN_USER=&14
define FTP_COMUN_PASS=&15
define SERVER_FTP_USER=&16
define SERVER_FTP_PASS=&17
----------------------------------------------------------------------------------------------------------------------------
--  SENTENCIAS PREVIAS
----------------------------------------------------------------------------------------------------------------------------
delete from GCXS_2_ACTIVE_SESSIONS ;

delete from GCXS_2_SESSION_AUTHORITY;

delete from GCXS_2_server;

----------------------------------------------------------------------------------------------------------------------------
Insert into GCXS_2_SERVER
	(CREATE_DATE, UPDATE_DATE, UPDATE_USER, UPDATE_PROGRAM, OPTIMIST_LOCK, SERVER_ID, NAME, IP_ADDRESS)
Values
	(sysdate,sysdate, 'OFUSCADO_2', 'ofuscado', 0, 1, '&SERVER_DNS', '&SERVER_IP');

Insert into GCXS_2_SERVER
	(CREATE_DATE, UPDATE_DATE, UPDATE_USER, UPDATE_PROGRAM, OPTIMIST_LOCK, SERVER_ID, NAME, IP_ADDRESS)
Values
	(sysdate,sysdate, CASE WHEN '&SERVER_DNS2' = 'NULL' THEN 'BORRAR' ELSE 'OFUSCADO_2' END, 'ofuscado', 0, 2, '&SERVER_DNS2', '&SERVER_IP2');

Insert into GCXS_2_SERVER
	(CREATE_DATE, UPDATE_DATE, UPDATE_USER, UPDATE_PROGRAM, OPTIMIST_LOCK, SERVER_ID, NAME, IP_ADDRESS)
Values
	(sysdate,sysdate, CASE WHEN '&SERVER_DNS3' = 'NULL' THEN 'BORRAR' ELSE 'OFUSCADO_2' END, 'ofuscado', 0, 3, '&SERVER_DNS3', '&SERVER_IP3');

Insert into GCXS_2_SERVER
	(CREATE_DATE, UPDATE_DATE, UPDATE_USER, UPDATE_PROGRAM, OPTIMIST_LOCK, SERVER_ID, NAME, IP_ADDRESS)
Values
	(sysdate,sysdate, CASE WHEN '&SERVER_DNS4' = 'NULL' THEN 'BORRAR' ELSE 'OFUSCADO_2' END, 'ofuscado', 0, 4, '&SERVER_DNS4', '&SERVER_IP4');

DELETE GCXS_2_SERVER WHERE UPDATE_USER = 'BORRAR';
-----------------------------------------------------------------------------------------------------------------------------

alter table delta_arq_Adminis.GCOFF_TRACE_STATUS disable constraint FK_GCOFF_TRACE_ST_GCOFF_TASK ;

alter table delta_arq_Adminis.gcoff_exec_variant disable constraint FK_GCOFF_EXEC_VARIANT ;

alter table delta_arq_Adminis.GCOFF_TASK disable constraint FK_GCOFF_TASK_GCOFF_TASK_SCH ;

truncate table delta_Arq_Adminis.gcoff_task;

truncate table delta_Arq_Adminis.gcoff_exec_variant;

truncate table delta_Arq_Adminis.gcoff_task_schedule;

truncate table delta_Arq_Adminis.gcoff_trace_status ;

alter table delta_arq_Adminis.GCOFF_TRACE_STATUS enable constraint FK_GCOFF_TRACE_ST_GCOFF_TASK ;

alter table delta_arq_Adminis.gcoff_exec_variant enable constraint FK_GCOFF_EXEC_VARIANT ;

alter table delta_arq_Adminis.GCOFF_TASK disable constraint FK_GCOFF_TASK_GCOFF_TASK_SCH ;
-----------------------------------------------------------
-- UPDATES GENERADOS
-----------------------------------------------------------
-------------------------------------------------------
-- La tabla GCCB_CAMP_REQUEST
-------------------------------------------------------
UPDATE GCCB_CAMP_REQUEST SET
BUROFAX_TYPE='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  BUROFAX_TYPE IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCVM_PROT_CONT_ADVISING
-------------------------------------------------------
UPDATE GCVM_PROT_CONT_ADVISING SET
MAIL_TYPE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_TYPE IS NOT NULL AND MAIL_TYPE LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_EMAIL_ALERT
-------------------------------------------------------
UPDATE GCCOM_EMAIL_ALERT SET
DIR_MAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  DIR_MAIL IS NOT NULL AND DIR_MAIL LIKE'%@%'
;
UPDATE GCCOM_EMAIL_ALERT SET
MAIL_CONTENT='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_CONTENT IS NOT NULL AND MAIL_CONTENT LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCVM_NO_INACT_DISTR_LIST
-------------------------------------------------------
UPDATE GCVM_NO_INACT_DISTR_LIST SET
EMAIL_ADDRESS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_ADDRESS IS NOT NULL AND EMAIL_ADDRESS LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_EMPLOYEE
-------------------------------------------------------
UPDATE GCCOM_EMPLOYEE SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_TEMPLATE_MS_DETAIL
-------------------------------------------------------
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_HEADER='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_HEADER IS NOT NULL AND EMAIL_HEADER LIKE'%@%'
;
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_SUBJECT_VO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_SUBJECT_VO IS NOT NULL AND EMAIL_SUBJECT_VO LIKE'%@%'
;
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_FOOTER_VO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FOOTER_VO IS NOT NULL AND EMAIL_FOOTER_VO LIKE'%@%'
;
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_HEADER_VO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_HEADER_VO IS NOT NULL AND EMAIL_HEADER_VO LIKE'%@%'
;
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_SUBJECT='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_SUBJECT IS NOT NULL AND EMAIL_SUBJECT LIKE'%@%'
;
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_BODY_VO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_BODY_VO IS NOT NULL AND EMAIL_BODY_VO LIKE'%@%'
;
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_FOOTER='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FOOTER IS NOT NULL AND EMAIL_FOOTER LIKE'%@%'
;
UPDATE GCCC_MIN_TEMPLATE_MS_DETAIL SET
EMAIL_BODY='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_BODY IS NOT NULL AND EMAIL_BODY LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCSW_OFFICE
-------------------------------------------------------
UPDATE GCSW_OFFICE SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_CUSTOMER
-------------------------------------------------------
UPDATE GCCC_CUSTOMER SET
FAX2='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX2 IS NOT NULL 
;
UPDATE GCCC_CUSTOMER SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
UPDATE GCCC_CUSTOMER SET
FAX1='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX1 IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCOM_ACCOUNT_GROUP_HEAD
-------------------------------------------------------
UPDATE GCCOM_ACCOUNT_GROUP_HEAD SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
UPDATE GCCOM_ACCOUNT_GROUP_HEAD SET
COURTESY2_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COURTESY2_EMAIL IS NOT NULL AND COURTESY2_EMAIL LIKE'%@%'
;
UPDATE GCCOM_ACCOUNT_GROUP_HEAD SET
EMAIL_FE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FE IS NOT NULL AND EMAIL_FE LIKE'%@%'
;
UPDATE GCCOM_ACCOUNT_GROUP_HEAD SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_SR_ATCOM
-------------------------------------------------------
UPDATE GCCC_MIN_SR_ATCOM SET
CLAIMANT_MAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  CLAIMANT_MAIL IS NOT NULL AND CLAIMANT_MAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCVM_CLUB_PARTNER
-------------------------------------------------------
UPDATE GCVM_CLUB_PARTNER SET
MAIL_ADDRESS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_ADDRESS IS NOT NULL AND MAIL_ADDRESS LIKE'%@%'
;
UPDATE GCVM_CLUB_PARTNER SET
ROBINSON_FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ROBINSON_FAX IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCGT_MR_BILL_ATR_CONTACTS
-------------------------------------------------------
UPDATE GCGT_MR_BILL_ATR_CONTACTS SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCVM_QUALITY_DISTR_LIST
-------------------------------------------------------
UPDATE GCVM_QUALITY_DISTR_LIST SET
EMAIL_ADDRESS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_ADDRESS IS NOT NULL AND EMAIL_ADDRESS LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_HOLDER_CHANGE
-------------------------------------------------------
UPDATE GCCC_MIN_HOLDER_CHANGE SET
CUSTOMER_FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  CUSTOMER_FAX IS NOT NULL 
;
UPDATE GCCC_MIN_HOLDER_CHANGE SET
CUSTOMER_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  CUSTOMER_EMAIL IS NOT NULL AND CUSTOMER_EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_SEND_SERVICE_HIST
-------------------------------------------------------
UPDATE GCCOM_SEND_SERVICE_HIST SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE_HIST SET
COURTESY2_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COURTESY2_EMAIL IS NOT NULL AND COURTESY2_EMAIL LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE_HIST SET
EMAIL_FE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FE IS NOT NULL AND EMAIL_FE LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE_HIST SET
FAX_NUM='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX_NUM IS NOT NULL 
;
UPDATE GCCOM_SEND_SERVICE_HIST SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCSW_ATCOM_ACCESS_REQUEST
-------------------------------------------------------
UPDATE GCSW_ATCOM_ACCESS_REQUEST SET
FAX_NUMBER='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX_NUMBER IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCC_MIN_CLIENT_REPRESENTATIVE
-------------------------------------------------------
UPDATE GCCC_MIN_CLIENT_REPRESENTATIVE SET
FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX IS NOT NULL 
;
UPDATE GCCC_MIN_CLIENT_REPRESENTATIVE SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_AGREEMENT
-------------------------------------------------------
UPDATE GCCB_AGREEMENT SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla DELTA_ARQ_ADMINIS.GCDOC_REPOSITORIES_TRANSLATION
-------------------------------------------------------
UPDATE DELTA_ARQ_ADMINIS.GCDOC_REPOSITORIES_TRANSLATION SET
CODE_HEX='002710',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  TRANSLATION_ID=1 
;
UPDATE DELTA_ARQ_ADMINIS.GCDOC_REPOSITORIES_TRANSLATION SET
CODE_HEX='003abc',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  TRANSLATION_ID=5 
;
UPDATE DELTA_ARQ_ADMINIS.GCDOC_REPOSITORIES_TRANSLATION SET
CODE_HEX='003ab4',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  TRANSLATION_ID=4 
;
UPDATE DELTA_ARQ_ADMINIS.GCDOC_REPOSITORIES_TRANSLATION SET
CODE_HEX='002711',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  TRANSLATION_ID=3 
;
UPDATE DELTA_ARQ_ADMINIS.GCDOC_REPOSITORIES_TRANSLATION SET
CODE_HEX='004e20',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  TRANSLATION_ID=2 
;
UPDATE DELTA_ARQ_ADMINIS.GCDOC_REPOSITORIES_TRANSLATION SET
CODE_HEX='003abf',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  TRANSLATION_ID=6 
;
-------------------------------------------------------
-- La tabla GCCC_CUST_COMM_EMAIL
-------------------------------------------------------
UPDATE GCCC_CUST_COMM_EMAIL SET
BODY_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  BODY_EMAIL IS NOT NULL AND BODY_EMAIL LIKE'%@%'
;
UPDATE GCCC_CUST_COMM_EMAIL SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_COMM_ASSOC
-------------------------------------------------------
UPDATE GCCC_MIN_COMM_ASSOC SET
MAIL_STATUS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_STATUS IS NOT NULL AND MAIL_STATUS LIKE'%@%'
;
UPDATE GCCC_MIN_COMM_ASSOC SET
MAIL_CONTACT_MOTIVE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_CONTACT_MOTIVE IS NOT NULL AND MAIL_CONTACT_MOTIVE LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_LEVEL_ZERO
-------------------------------------------------------
UPDATE GCCC_MIN_LEVEL_ZERO SET
ID_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ID_EMAIL IS NOT NULL AND ID_EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_CAMP_PROCESSED_MOD
-------------------------------------------------------
UPDATE GCCB_CAMP_PROCESSED_MOD SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_MAIL_TEMPLATE
-------------------------------------------------------
UPDATE GCCC_MIN_MAIL_TEMPLATE SET
COD_VL_MAIL_FROM='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_VL_MAIL_FROM IS NOT NULL AND COD_VL_MAIL_FROM LIKE'%@%'
;
UPDATE GCCC_MIN_MAIL_TEMPLATE SET
COD_VL_MAIL_TYPE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_VL_MAIL_TYPE IS NOT NULL AND COD_VL_MAIL_TYPE LIKE'%@%'
;
UPDATE GCCC_MIN_MAIL_TEMPLATE SET
MAIL_SUBJECT='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_SUBJECT IS NOT NULL AND MAIL_SUBJECT LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCBILL_INSET
-------------------------------------------------------
UPDATE GCBILL_INSET SET
BLANK_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  BLANK_EMAIL IS NOT NULL AND BLANK_EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_COLLECTION_CENTER
-------------------------------------------------------
UPDATE GCCB_COLLECTION_CENTER SET
CONTACT_MAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  CONTACT_MAIL IS NOT NULL AND CONTACT_MAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MY_CUSTOMER
-------------------------------------------------------
UPDATE GCCC_MY_CUSTOMER SET
EMAIL3='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL3 IS NOT NULL AND EMAIL3 LIKE'%@%'
;
UPDATE GCCC_MY_CUSTOMER SET
EMAIL2='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL2 IS NOT NULL AND EMAIL2 LIKE'%@%'
;
UPDATE GCCC_MY_CUSTOMER SET
EMAIL_OV='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_OV IS NOT NULL AND EMAIL_OV LIKE'%@%'
;
UPDATE GCCC_MY_CUSTOMER SET
FAX3='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX3 IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCC_VALIDATION_STATUS_HIST
-------------------------------------------------------
UPDATE GCCC_VALIDATION_STATUS_HIST SET
EMAIL_AUT_RISK='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_AUT_RISK IS NOT NULL AND EMAIL_AUT_RISK LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCVM_OFFER_SEND_SERVICE
-------------------------------------------------------
UPDATE GCVM_OFFER_SEND_SERVICE SET
FAX_NUM='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX_NUM IS NOT NULL 
;
UPDATE GCVM_OFFER_SEND_SERVICE SET
FAX_NUM_PREFIX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX_NUM_PREFIX IS NOT NULL 
;
UPDATE GCVM_OFFER_SEND_SERVICE SET
COURTESY2_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COURTESY2_EMAIL IS NOT NULL AND COURTESY2_EMAIL LIKE'%@%'
;
UPDATE GCVM_OFFER_SEND_SERVICE SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
UPDATE GCVM_OFFER_SEND_SERVICE SET
EMAIL_FE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FE IS NOT NULL AND EMAIL_FE LIKE'%@%'
;
UPDATE GCVM_OFFER_SEND_SERVICE SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_REPRESEN_CONTACT
-------------------------------------------------------
UPDATE GCCC_MIN_REPRESEN_CONTACT SET
FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX IS NOT NULL 
;
UPDATE GCCC_MIN_REPRESEN_CONTACT SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_FUSION_REQUEST
-------------------------------------------------------
UPDATE GCCC_FUSION_REQUEST SET
FAX2='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX2 IS NOT NULL 
;
UPDATE GCCC_FUSION_REQUEST SET
FAX3='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX3 IS NOT NULL 
;
UPDATE GCCC_FUSION_REQUEST SET
FAX1='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX1 IS NOT NULL 
;
UPDATE GCCC_FUSION_REQUEST SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
UPDATE GCCC_FUSION_REQUEST SET
EMAIL2='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL2 IS NOT NULL AND EMAIL2 LIKE'%@%'
;
UPDATE GCCC_FUSION_REQUEST SET
EMAIL3='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL3 IS NOT NULL AND EMAIL3 LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_CONTACT
-------------------------------------------------------
UPDATE GCCC_MIN_CONTACT SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCVM_OFFER_LINE_PRINT_REQUEST
-------------------------------------------------------
UPDATE GCVM_OFFER_LINE_PRINT_REQUEST SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
 EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCXS_2_APPLICATION_SERVER
-------------------------------------------------------
UPDATE GCXS_2_APPLICATION_SERVER SET
SERVER_HOST=CASE WHEN'&APP_DNS'='NULL'THEN'&SERVER_DNS'ELSE'&APP_DNS'END,
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  	APPLICATION_SERVER_ID=1 
;
UPDATE GCXS_2_APPLICATION_SERVER SET
SERVER_PORT='&SERVER_PORT',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  	APPLICATION_SERVER_ID=1 
;
-------------------------------------------------------
-- La tabla GCCC_MIN_SR_COMMUNICATIONS
-------------------------------------------------------
UPDATE GCCC_MIN_SR_COMMUNICATIONS SET
MAIL_STATUS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_STATUS IS NOT NULL AND MAIL_STATUS LIKE'%@%'
;
UPDATE GCCC_MIN_SR_COMMUNICATIONS SET
MAIL_CONTACT_MOTIVE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_CONTACT_MOTIVE IS NOT NULL AND MAIL_CONTACT_MOTIVE LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_PROVINCIA_EMAIL
-------------------------------------------------------
UPDATE GCCB_PROVINCIA_EMAIL SET
EMAIL_PARA='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_PARA IS NOT NULL AND EMAIL_PARA LIKE'%@%'
;
UPDATE GCCB_PROVINCIA_EMAIL SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_CONTACT
-------------------------------------------------------
UPDATE GCCOM_CONTACT SET
EMAIL1='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL1 IS NOT NULL AND EMAIL1 LIKE'%@%'
;
UPDATE GCCOM_CONTACT SET
FAX2='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX2 IS NOT NULL 
;
UPDATE GCCOM_CONTACT SET
FAX1='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX1 IS NOT NULL 
;
UPDATE GCCOM_CONTACT SET
FAX3='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX3 IS NOT NULL 
;
UPDATE GCCOM_CONTACT SET
EMAIL2='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL2 IS NOT NULL AND EMAIL2 LIKE'%@%'
;
UPDATE GCCOM_CONTACT SET
EMAIL3='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL3 IS NOT NULL AND EMAIL3 LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCVM_CLUB_CARD_BENEFICIARY
-------------------------------------------------------
UPDATE GCVM_CLUB_CARD_BENEFICIARY SET
ROBINSON_FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ROBINSON_FAX IS NOT NULL 
;
UPDATE GCVM_CLUB_CARD_BENEFICIARY SET
MAIL_ADDRESS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_ADDRESS IS NOT NULL AND MAIL_ADDRESS LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCGT_RE_READING_UNIT
-------------------------------------------------------
UPDATE GCGT_RE_READING_UNIT SET
FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX IS NOT NULL 
;
UPDATE GCGT_RE_READING_UNIT SET
E_MAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  E_MAIL IS NOT NULL AND E_MAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCBILL_PRINT_CONTACT
-------------------------------------------------------
UPDATE GCBILL_PRINT_CONTACT SET
MAIL_RECLAMACIONES='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_RECLAMACIONES IS NOT NULL AND MAIL_RECLAMACIONES LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_SR_LOG
-------------------------------------------------------
UPDATE GCCC_MIN_SR_LOG SET
EMAIL_SKILL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_SKILL IS NOT NULL AND EMAIL_SKILL LIKE'%@%'
;
UPDATE GCCC_MIN_SR_LOG SET
DEST_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  DEST_EMAIL IS NOT NULL AND DEST_EMAIL LIKE'%@%'
;
UPDATE GCCC_MIN_SR_LOG SET
ID_MAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ID_MAIL IS NOT NULL AND ID_MAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_THIRD
-------------------------------------------------------
UPDATE GCCC_THIRD SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
UPDATE GCCC_THIRD SET
FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCOM_COMPANY
-------------------------------------------------------
UPDATE GCCOM_COMPANY SET
EMAIL_ADDRESS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_ADDRESS IS NOT NULL AND EMAIL_ADDRESS LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCGT_CM_TRANSFER_FTP
-------------------------------------------------------
UPDATE GCGT_CM_TRANSFER_FTP SET
USER_FTP='&SERVER_FTP_USER',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
;
UPDATE GCGT_CM_TRANSFER_FTP SET
PATH_FTP='/bdatos00/desgnf/delta1/datos/SCTD_OUT/SCTD-E/ELECTRICIDAD',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
;
UPDATE GCGT_CM_TRANSFER_FTP SET
SERVER_NAME='&SERVER_DNS',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
;
UPDATE GCGT_CM_TRANSFER_FTP SET
PASS_FTP='&SERVER_FTP_PASS',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_EMAIL_REGISTER
-------------------------------------------------------
UPDATE GCCC_MIN_EMAIL_REGISTER SET
EMAIL_FROM='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FROM IS NOT NULL AND EMAIL_FROM LIKE'%@%'
;
UPDATE GCCC_MIN_EMAIL_REGISTER SET
COD_VL_SKILL_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_VL_SKILL_EMAIL IS NOT NULL AND COD_VL_SKILL_EMAIL LIKE'%@%'
;
UPDATE GCCC_MIN_EMAIL_REGISTER SET
EMAIL_TO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_TO IS NOT NULL AND EMAIL_TO LIKE'%@%'
;
UPDATE GCCC_MIN_EMAIL_REGISTER SET
EMAIL_CCO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CCO IS NOT NULL AND EMAIL_CCO LIKE'%@%'
;
UPDATE GCCC_MIN_EMAIL_REGISTER SET
COD_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_EMAIL IS NOT NULL AND COD_EMAIL LIKE'%@%'
;
UPDATE GCCC_MIN_EMAIL_REGISTER SET
MAIL_FORWARD_DESTINATION='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_FORWARD_DESTINATION IS NOT NULL AND MAIL_FORWARD_DESTINATION LIKE'%@%'
;
UPDATE GCCC_MIN_EMAIL_REGISTER SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_CUSTOMER_EMAIL
-------------------------------------------------------
UPDATE GCCC_CUSTOMER_EMAIL SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_CAMP_CONTRACTOR
-------------------------------------------------------
UPDATE GCCB_CAMP_CONTRACTOR SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
UPDATE GCCB_CAMP_CONTRACTOR SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_SEND_SERVICE
-------------------------------------------------------
UPDATE GCCOM_SEND_SERVICE SET
EMAIL_MANAGER_FILE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_MANAGER_FILE IS NOT NULL AND EMAIL_MANAGER_FILE LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE SET
SENDING_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  SENDING_EMAIL IS NOT NULL AND SENDING_EMAIL LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE SET
COURTESY2_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COURTESY2_EMAIL IS NOT NULL AND COURTESY2_EMAIL LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE SET
EMAIL_FE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FE IS NOT NULL AND EMAIL_FE LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
UPDATE GCCOM_SEND_SERVICE SET
FAX_NUM='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX_NUM IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCB_POVERTY_MODEL
-------------------------------------------------------
UPDATE GCCB_POVERTY_MODEL SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_POVERTY_MODEL_HIST
-------------------------------------------------------
UPDATE GCCB_POVERTY_MODEL_HIST SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_LETTER_DATA
-------------------------------------------------------
UPDATE /*+ PARALLEL ( GCCB_LETTER_DATA , 32 )*/ GCCB_LETTER_DATA SET
DATA_VALUE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE  id_letter_key IN(SELECT id_letter_key from gccb_letter_form_key WHERE cod_letter_key LIKE'%email%'AND id_letter_key NOT IN(10008 , 10007 , 10009)) 

;
UPDATE /*+ PARALLEL ( GCCB_LETTER_DATA , 32 )*/ GCCB_LETTER_DATA SET
DATA_VALUE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE (data_value LIKE'%@%') 

;
-------------------------------------------------------
-- La tabla GCCC_VP_OFFER
-------------------------------------------------------
UPDATE GCCC_VP_OFFER SET
COMMUNICATIONS_MAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COMMUNICATIONS_MAIL IS NOT NULL AND COMMUNICATIONS_MAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_OPERATIVE_BASE_CODE
-------------------------------------------------------
UPDATE GCCB_OPERATIVE_BASE_CODE SET
EMAIL2='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL2 IS NOT NULL AND EMAIL2 LIKE'%@%'
;
UPDATE GCCB_OPERATIVE_BASE_CODE SET
FAX1='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX1 IS NOT NULL 
;
UPDATE GCCB_OPERATIVE_BASE_CODE SET
FAX3='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX3 IS NOT NULL 
;
UPDATE GCCB_OPERATIVE_BASE_CODE SET
EMAIL3='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL3 IS NOT NULL AND EMAIL3 LIKE'%@%'
;
UPDATE GCCB_OPERATIVE_BASE_CODE SET
FAX2='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX2 IS NOT NULL 
;
UPDATE GCCB_OPERATIVE_BASE_CODE SET
EMAIL1='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL1 IS NOT NULL AND EMAIL1 LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_ZSCTD_A12
-------------------------------------------------------
UPDATE GCCOM_ZSCTD_A12 SET
Z_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  Z_EMAIL IS NOT NULL AND Z_EMAIL LIKE'%@%'
;
UPDATE GCCOM_ZSCTD_A12 SET
ZFAXCLI='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZFAXCLI IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCC_SEND_READING_NOTICE
-------------------------------------------------------
UPDATE GCCC_SEND_READING_NOTICE SET
CUSTOMER_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
 CUSTOMER_EMAIL IS NOT NULL AND CUSTOMER_EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_PROVIDER_COMPANY
-------------------------------------------------------
UPDATE GCCOM_PROVIDER_COMPANY SET
FAX1='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX1 IS NOT NULL 
;
UPDATE GCCOM_PROVIDER_COMPANY SET
EMAIL1='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL1 IS NOT NULL AND EMAIL1 LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_PARAMETER
-------------------------------------------------------
UPDATE GCCOM_PARAMETER SET
VALUE1='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  value1 LIKE'%@%'OR(cod_develop='CBLTCND005')OR(cod_develop='CBCNDMCC01')OR(cod_develop='CBCNDMTO01')OR(cod_develop='CBAPPREMIT')OR(cod_develop='CBIMMAILAD')
;
UPDATE GCCOM_PARAMETER SET
VALUE1='correctivo_test@gasnatural.com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_DEVELOP='MAILINFCUA'
;
UPDATE GCCOM_PARAMETER SET
VALUE2='correctivo_test@gasnatural.com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_DEVELOP='MAILINFCUA'
;
UPDATE GCCOM_PARAMETER SET
VALUE1='&FTP_COMUN',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  cod_develop='BIFACFTPFE'
;
UPDATE GCCOM_PARAMETER SET
VALUE2='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  value2 LIKE'%@%'OR(cod_develop='CBLTCND005')OR(cod_develop='CBIMMAILAD')OR(cod_develop='ACCMPYBTCH'AND value2 LIKE'%@%')
;
UPDATE GCCOM_PARAMETER SET
VALUE4='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  value4 LIKE'%@%'OR(cod_develop='ACCMPYBTCH'AND value4 LIKE'%@%')
;
UPDATE GCCOM_PARAMETER SET
VALUE5='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  value5 LIKE'%@%'OR(cod_develop='ACCMPYBTCH'AND value5 LIKE'%@%')
;
UPDATE GCCOM_PARAMETER SET
VALUE3='XXXX:adss:signing:profile:010',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  cod_develop='CODPTCERTI'
;
UPDATE GCCOM_PARAMETER SET
VALUE3='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  value3 LIKE'%@%'OR(cod_develop='CBLTCND005')OR(cod_develop='ACCMPYBTCH'AND value3 LIKE'%@%')
;
UPDATE GCCOM_PARAMETER SET
VALUE2='&FTP_COMUN_USER',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  cod_develop='BIFACFTPFE'
;
UPDATE GCCOM_PARAMETER SET
VALUE1='False',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  cod_develop='SW_TRANSON'
;
UPDATE GCCOM_PARAMETER SET
VALUE2='http://XXXXinadss03.intranet.gasnatural.com:8777/adss/signing/dss',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  cod_develop='CODPTCERTI'
;
UPDATE GCCOM_PARAMETER SET
VALUE3='&FTP_COMUN_PASS',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  cod_develop='BIFACFTPFE'
;
UPDATE GCCOM_PARAMETER SET
VALUE1='Correctivo_test@gasnatural.com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  cod_develop IN('VPANUAUT','VPEMAILEUR','VPEMAILTC')OR(cod_develop IN('VPRISK0001')AND search_key IN('FACSTR00010000000000000000000000000000000000000000000000000000000000000000000000','FACSTR00020000000000000000000000000000000000000000000000000000000000000000000000','FACSTR00100000000000000000000000000000000000000000000000000000000000000000000000'))
;
-------------------------------------------------------
-- La tabla GCCOM_ZSCTD_A3
-------------------------------------------------------
UPDATE GCCOM_ZSCTD_A3 SET
ZNV_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZNV_EMAIL IS NOT NULL AND ZNV_EMAIL LIKE'%@%'
;
UPDATE GCCOM_ZSCTD_A3 SET
ZNV_FAXCLI='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZNV_FAXCLI IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCOM_BILL_ATR_CONTACTS
-------------------------------------------------------
UPDATE GCCOM_BILL_ATR_CONTACTS SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_ZSCTD_A2
-------------------------------------------------------
UPDATE GCCOM_ZSCTD_A2 SET
ZNV_FAXCLI='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZNV_FAXCLI IS NOT NULL 
;
UPDATE GCCOM_ZSCTD_A2 SET
ZNV_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZNV_EMAIL IS NOT NULL AND ZNV_EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_ZSCTD_A1
-------------------------------------------------------
UPDATE GCCOM_ZSCTD_A1 SET
ZNV_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZNV_EMAIL IS NOT NULL AND ZNV_EMAIL LIKE'%@%'
;
UPDATE GCCOM_ZSCTD_A1 SET
ZNV_FAXCLI='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZNV_FAXCLI IS NOT NULL 
;
UPDATE GCCOM_ZSCTD_A1 SET
Z_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  Z_EMAIL IS NOT NULL AND Z_EMAIL LIKE'%@%'
;
UPDATE GCCOM_ZSCTD_A1 SET
ZFAXCLI='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  ZFAXCLI IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCC_MIN_COMM_LEVEL_ZERO
-------------------------------------------------------
UPDATE GCCC_MIN_COMM_LEVEL_ZERO SET
MAIL_CONTACT_MOTIVE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_CONTACT_MOTIVE IS NOT NULL AND MAIL_CONTACT_MOTIVE LIKE'%@%'
;
UPDATE GCCC_MIN_COMM_LEVEL_ZERO SET
MAIL_STATUS='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_STATUS IS NOT NULL AND MAIL_STATUS LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCXS_2_USER
-------------------------------------------------------
UPDATE GCXS_2_USER SET
PROGRAMMED_DROP_DATE_TS=to_Date('2999/12/31','yyyy/mm/dd'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  racf_users_code='SGCV10'
;
UPDATE GCXS_2_USER SET
PASSWORD='DELTAOF',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  RACF_USERS_CODE='DELTAOF'
;
-------------------------------------------------------
-- La tabla GCCC_MY_SR
-------------------------------------------------------
UPDATE GCCC_MY_SR SET
CONTACT_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  CONTACT_EMAIL IS NOT NULL AND CONTACT_EMAIL LIKE'%@%'
;
UPDATE GCCC_MY_SR SET
CONTACT_FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  CONTACT_FAX IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCCB_REMITTANCE_EMAILS
-------------------------------------------------------
UPDATE GCCB_REMITTANCE_EMAILS SET
EMAIL_TO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_TO IS NOT NULL AND EMAIL_TO LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_ACCOUNT_GROUP
-------------------------------------------------------
UPDATE GCCOM_ACCOUNT_GROUP SET
FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX IS NOT NULL 
;
UPDATE GCCOM_ACCOUNT_GROUP SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCVM_PROT_CONT_ADVISING_H
-------------------------------------------------------
UPDATE GCVM_PROT_CONT_ADVISING_H SET
MAIL_TYPE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  MAIL_TYPE IS NOT NULL AND MAIL_TYPE LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_GENERIC_ACCOUNT_EMAIL
-------------------------------------------------------
UPDATE GCCC_MIN_GENERIC_ACCOUNT_EMAIL SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCB_POVERTY_COUNCIL_PARAM
-------------------------------------------------------
UPDATE GCCB_POVERTY_COUNCIL_PARAM SET
EMAIL_CC='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_CC IS NOT NULL AND EMAIL_CC LIKE'%@%'
;
UPDATE GCCB_POVERTY_COUNCIL_PARAM SET
EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCC_MIN_CUSTOMER
-------------------------------------------------------
UPDATE GCCC_MIN_CUSTOMER SET
INFO_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  INFO_EMAIL IS NOT NULL AND INFO_EMAIL LIKE'%@%'
;
UPDATE GCCC_MIN_CUSTOMER SET
STATUS_VALID_EMAIL='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  STATUS_VALID_EMAIL IS NOT NULL AND STATUS_VALID_EMAIL LIKE'%@%'
;
UPDATE GCCC_MIN_CUSTOMER SET
EMAIL_SAVE='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_SAVE IS NOT NULL AND EMAIL_SAVE LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCSW_ATCOM_GAS_MESSAGES
-------------------------------------------------------
UPDATE GCSW_ATCOM_GAS_MESSAGES SET
FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX IS NOT NULL 
;
UPDATE GCSW_ATCOM_GAS_MESSAGES SET
PREFIX_FAX='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  PREFIX_FAX IS NOT NULL 
;
-------------------------------------------------------
-- La tabla GCGT_CM_TRANSFER_EMAIL
-------------------------------------------------------
UPDATE GCGT_CM_TRANSFER_EMAIL SET
EMAIL_SUBJECT='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_SUBJECT IS NOT NULL AND EMAIL_SUBJECT LIKE'%@%'
;
UPDATE GCGT_CM_TRANSFER_EMAIL SET
EMAIL_FROM='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FROM IS NOT NULL AND EMAIL_FROM LIKE'%@%'
;
UPDATE GCGT_CM_TRANSFER_EMAIL SET
EMAIL_TO='ofusdelta@&SERVICE_DB..com',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_TO IS NOT NULL AND EMAIL_TO LIKE'%@%'
;
