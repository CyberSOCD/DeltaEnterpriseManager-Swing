package conexion.dbAccess;

import java.util.ArrayList;
import java.util.Iterator;

import ofuscado.query.UpdateObject;

public class ObfuscatorQueries {
	
	/**
	 * 
	 ****	PARAMETROS DE LA QUERY
	 * 
	 * &APP_DNS			URL del balanceador (si hay), en caso de no ser necesario dar valor NULL
	 * &SERVER_DNS			El DNS del servidor del entorno
	 * &SERVER_DNS2		El DNS del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
	 * &SERVER_DNS3		El DNS del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
	 * &SERVER_DNS4		El DNS del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
	 * &SERVER_IP			La IP del servidor del entorno
	 * &SERVER_IP2			La IP del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
	 * &SERVER_IP3			La IP del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
	 * &SERVER_IP4			La IP del servidor del entorno, opcional, en caso de no ser necesario, dar valor NULL
	 * &SERVER_PORT		Puerto utilizado por el DNS
	 * &SERVICE_DB			Utilizado para ofuscar campos de correo
	 * &FAX				Utilizado para ofuscar Fax. Valor por defecto:99999999999
	 * &FTP_COMUN			Servidor FTP comun para todos los entornos (Solicitado Sonsoles)
	 * &FTP_COMUN_USER		Usuario acceso FTP comun para todos los entornos (Solicitado Sonsoles)
	 * &FTP_COMUN_PASS		Password FTP comun para todos los entornos (Solicitado Sonsoles)
	 * &SERVER_FTP_USER	Usuario FTP acceso al entorno
	 * &SERVER_FTP_PASS	Password FTP acceso al entorno
	 * 
	 * 
	 * 
	 * FTP_COMUN/USER/PASS :::  ftp.intranet.gasnatural.com/delta1/delta1      Es comun a todos los entornos
	 * 
	 * 
	 */
	
	
	public static String getSimpleQuery(String tableName, ArrayList<UpdateObject> columns){
		Iterator<UpdateObject> it = columns.iterator();
		String query = "SELECT COUNT(*) FROM " + tableName + " WHERE (\n";
		while(it.hasNext()){
			query += it.next().getConditionValue();
			if(it.hasNext())
				query += " OR ";
		}
		query += ") AND UPDATE_USER != 'OFUSCADO_2';";
		return query;
	}
	
	
	public static String getSearchMultipleRowsQuery(String tableName,
			ArrayList<UpdateObject> columns) {
		Iterator<UpdateObject> it = columns.iterator();
		String header = "SELECT COUNT(*) FROM ";
		String alias = "TABLE_";
		String inner = " INNER JOIN ( ";
		String query = header;
		int cont = 1;
		String baseCompare = alias + cont;
		// Se prepara subquery inicial dejando al bucle los INNER JOIN
		UpdateObject init = it.next();
		query += "( SELECT * FROM " + tableName + " WHERE \n";
		query += init.getConditionValue() + ") " + alias + cont++;
		query += inner;
		while (it.hasNext()) {
			UpdateObject obj = it.next();
			query += "SELECT * FROM " + tableName + " WHERE \n";
			query += obj.getConditionValue() + ") " + alias + cont
					+ "\n";
			query += " ON " + baseCompare + ".ROWID = " + alias + cont
					+ ".ROWID \n";
			if (it.hasNext())
				query += inner;
			cont++;
		}
		query += ";";
		System.out.println("-- TABLA " + tableName + " \n" + query);

		// Errores detectados en ejecucion
		// 1- Tabla no existe.
		// 2- Consulta ROWID en vista.
		// 3-
		// 4-
		return query;
	}
}
