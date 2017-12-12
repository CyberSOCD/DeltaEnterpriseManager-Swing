----------------------------------------------------------------------------------------------------------------------------
--							SCRIPT_OFUSCADO_UNIFICADO_MAYORISTAS - VERSION. 1.0
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
DELETE GCUC_USER_QUERY_FIELDS
WHERE FIELD_NAME IN ('email_day','email_period') AND USER_QUERY_CUSTOMIZATION_ID IN (
SELECT USER_QUERY_CUSTOMIZATION_ID FROM GCUC_USER_QUERY_CUSTOMIZATION WHERE QUERY_NAME IN ('BIBLC01055','BIBLC01056')
);

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
-- La tabla APEX_APPLICATION_PAGE_IR
-------------------------------------------------------
UPDATE APEX_APPLICATION_PAGE_IR SET
EMAIL_FROM=REPLACE('ofusdelta@&SERVICE_DB..com','_'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_FROM IS NOT NULL AND EMAIL_FROM LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCSW_CLAIM_ACCESS_REQUEST
-------------------------------------------------------
UPDATE GCSW_CLAIM_ACCESS_REQUEST SET
FAX_NUMBER='&FAX',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  FAX_NUMBER IS NOT NULL 
;
-------------------------------------------------------
-- La tabla WWV_FLOW_USERS
-------------------------------------------------------
UPDATE WWV_FLOW_USERS SET
EMAIL_ADDRESS=REPLACE('ofusdelta@&SERVICE_DB..com','_'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL_ADDRESS IS NOT NULL AND EMAIL_ADDRESS LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCCOM_DYNAMIC_REPORT_CONFIG
-------------------------------------------------------
UPDATE GCCOM_DYNAMIC_REPORT_CONFIG SET
LIST_EMAILS_CO=REPLACE('ofusdelta@&SERVICE_DB..com','_'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  LIST_EMAILS_CO IS NOT NULL AND LIST_EMAILS_CO LIKE'%@%'
;
UPDATE GCCOM_DYNAMIC_REPORT_CONFIG SET
LIST_EMAILS_CC=REPLACE('ofusdelta@&SERVICE_DB..com','_'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  LIST_EMAILS_CC IS NOT NULL AND LIST_EMAILS_CC LIKE'%@%'
;
UPDATE GCCOM_DYNAMIC_REPORT_CONFIG SET
LIST_EMAILS=REPLACE('ofusdelta@&SERVICE_DB..com','_'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  LIST_EMAILS IS NOT NULL AND LIST_EMAILS LIKE'%@%'
;
-------------------------------------------------------
-- La tabla APEX_WORKSPACE_DEVELOPERS
-------------------------------------------------------
UPDATE APEX_WORKSPACE_DEVELOPERS SET
EMAIL=REPLACE('ofusdelta@&SERVICE_DB..com','_'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
-------------------------------------------------------
-- La tabla GCBILL_BATCH_LAUNCHCOND
-------------------------------------------------------
UPDATE GCBILL_BATCH_LAUNCHCOND SET
LAUNCH_COND='<?xml version="1.0" encoding="UTF-8"?><content><arqv10:exec_variants><exec_variant><field_name>variant_type</field_name><operator>=</operator><value>BIBLC01056-Variante Informe CdM por Tarifa(Lecturas)</value></exec_variant><exec_variant><field_name>id_company</field_name><operator>=</operator><value>96</value></exec_variant><exec_vari ant><field_name>id_sector</field_name><operator>=</operator><value>1</value></exec_variant><exec_variant><field_name>fare_group</field_name><operator>=</operator><value>BIFARGR024</value></exec_variant><exec _variant><field_name>email_to</field_name><operator>=</operator><value>ofusdelta@tdelta01.com</value></exec_variant><exec_variant><field_name>email_cc</field_name><operator>=</operator><value>ofusdelta@tdelt a01.com</value></exec_variant><exec_variant><field_name>email_period</field_name><operator></operator><value></value></exec_variant><exec_variant><field_name>email_day</field_name><operator></operator><value ></value></exec_variant><exec_variant><field_name>email_subject</field_name><operator>=</operator><value>CdM Facturaci󮠤d/MM/aaaa(Variante Lecturas)</value></exec_variant><exec_variant><field_name>email_b ody</field_name><operator>=</operator><value>Se adjunta cuadro mando facturacion para tarifas comerciales seleccionadas en variante de ejecucion de Lecturas extra a fecha de hoy. </value></exec_variant></ arqv10:exec_variants><clausesList><MultipleCompany><include_conditions><condition><operator>=</operator><value>96</value></condition></include_conditions></MultipleCompany><MultipleSector><include_conditions ><condition><operator>=</operator><value>1</value></condition></include_conditions></MultipleSector><MultipleFareGroup><include_conditions><condition><operator>=</operator><value>BIFARGR024</value></conditio n></include_conditions></MultipleFareGroup><MultipleFareCom></MultipleFareCom><MultipleFareATR></MultipleFareATR><MultipleIndGuarantor></MultipleIndGuarantor><MultipleIndEventualSupply></MultipleIndEventualS upply><MultipleCustomerGroup></MultipleCustomerGroup><MultipleIndTelemSupply></MultipleIndTelemSupply><MultipleCifCustomer></MultipleCifCustomer><emailto>ofusdelta@tdelta01.com</emailto><emailcc>ofusdelta@td elta01.com</emailcc><emailsendperiod></emailsendperiod><emailsendday></emailsendday><emailsubject>CdM Facturaci󮠤d/MM/aaaa(Variante Lecturas)</emailsubject><emailbody>Se adjunta cuadro mando facturacion p ara tarifas comerciales seleccionadas en variante de ejecuci󮠤e Lecturas extra a fecha de hoy.</emailbody></clausesList></content>',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_DEVELOP='BIBLC01056'
;
UPDATE GCBILL_BATCH_LAUNCHCOND SET
LAUNCH_COND='<?xml version="1.0" encoding="UTF-8"?><content><arqv10:exec_variants><exec_variant><field_name>variant_type</field_name><operator>=</operator><value>BIBLC01055 - Variante Informe CdM por Tarifa(Facturacion)</value></exec_variant><exec_variant><field_name>id_company</field_name><operator>=</operator><value>96</value></exec_variant><exec_variant><field_name>id_sector</field_name><operator>=</operator><value>1</value></exec_variant><exec_variant><field_name>fare_group</field_name><operator>=</operator><value>BIFARGR024</value></exec_variant><exec_variant><field_name>email_to</field_name><operator>=</operator><value>ofusdelta@tdelta01.com</value></exec_variant><exec_variant><field_name>email_cc</field_name><operator>=</operator><value>ofusdelta@tdelta01.com</value></exec_variant><exec_variant><field_name>email_period</field_name><operator></operator><value></value></exec_variant><exec_variant><field_name>email_day</field_name><operator></operator><value></value></exec_variant><exec_variant><field_name>email_subject</field_name><operator>=</operator><value>CdM Facturaciód/MM/aaaa</value></exec_variant><exec_variant><field_name>email_body</field_name><operator>=</operator><value>Se adjunta el cuadro de mando de facturacióara las tarifas comerciales seleccionadas en la variante de ejecucióxtraí a fecha de hoy. </value></exec_variant></arqv10:exec_variants><clausesList><MultipleCompany><include_conditions><condition><operator>=</operator><value>96</value></condition></include_conditions></MultipleCompany><MultipleSector><include_conditions><condition><operator>=</operator><value>1</value></condition></include_conditions></MultipleSector><MultipleFareGroup><include_conditions><condition><operator>=</operator><value>BIFARGR024</value></condition></include_conditions></MultipleFareGroup><MultipleFareCom></MultipleFareCom><MultipleFareATR></MultipleFareATR><emailto>ofusdelta@tdelta01.com</emailto><emailcc>ofusdelta@tdelta01.com</emailcc><emailsendperiod></emailsendperiod><emailsendday></emailsendday><emailsubject>CdM Facturaciód/MM/aaaa</emailsubject><emailbody>Se adjunta el cuadro de mando de facturacióara las tarifas comerciales seleccionadas en la variante de ejecucióxtraí a fecha de hoy. </emailbody></clausesList></content>',
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  COD_DEVELOP='BIBLC01055'
;
-------------------------------------------------------
-- La tabla GCCOM_EMPLOYEE
-------------------------------------------------------
UPDATE GCCOM_EMPLOYEE SET
EMAIL=REPLACE('ofusdelta@&SERVICE_DB..com','_'),
UPDATE_DATE = SYSDATE,
UPDATE_USER='OFUSCADO_2',
UPDATE_PROGRAM='ofuscado'
WHERE 
  EMAIL IS NOT NULL AND EMAIL LIKE'%@%'
;
