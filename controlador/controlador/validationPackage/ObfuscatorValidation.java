package controlador.validationPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import conexion.dbAccess.ObfuscatorQueries;
import conexion.drivers.DriverDB;
import controlador.common.UserConnectionData;
import controlador.controlResult.ObfuscatorStatus;
import ofuscado.query.UpdateObject;
import ofuscado.validation.GeneratorValidation;

public class ObfuscatorValidation extends Validation {

	private HashMap<String, ArrayList<UpdateObject>> map;
	private File file;
	private UserConnectionData data;

	public ObfuscatorValidation(File f, UserConnectionData data) {
		file = f;
		this.data = data;
		try {
			validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ObfuscatorValidation() {

	}

	@Override
	public void validate() throws Exception {
		getTableRelation();
		for (String table : map.keySet()) {
			if (map.get(table).size() > 1) {
				if (searchForCommonColumn(table, map.get(table))) {
					if (!checkValidation()) {
						saveInvalidTable();
					}
				} else {
					if (!checkSimpleValidation(table,map.get(table))) {
						saveInvalidTable();
					}
				}
			} else {
				if (!checkSimpleValidation(table,map.get(table))) {
					saveInvalidTable();
				}
			}
		}
	}

	@Override
	public ObfuscatorStatus getCurrentStatus() {
		return null;
	}

	@Override
	protected void logActivity() {

	}

	public void setFile(File file) {
		this.file = file;
	}

	private void saveInvalidTable() {

	}

	private void getTableRelation() {
		GeneratorValidation gen = new GeneratorValidation(file);
		map = gen.geTableColumnRelation();
	}

	/**
	 * Comprueba que los registros que complen condicion han sido modificados
	 * por el usuario OFUSCADO_2
	 * 
	 * @return
	 */
	private boolean checkSimpleValidation(String tableName, ArrayList<UpdateObject> columns) {
		String query = ObfuscatorQueries.getSimpleQuery(tableName, columns);
		System.out.println(query);
		return false;
	}

	/**
	 * Comprueba que todos los registros que cumplan una condicion de ofuscado
	 * esten ofuscdos con su valor correspondiente
	 * 
	 * @return
	 */
	private boolean checkValidation() {
		return false;
	}

	/**
	 * Comprueba si hay registros comunes en distintas columnas
	 */
	private boolean searchForCommonColumn(String tableName,
			ArrayList<UpdateObject> columns) {
		// Obtiene la query que comprueba registros comunes
		String query = ObfuscatorQueries.getSearchMultipleRowsQuery(tableName, columns);
		// Se ejecuta la query obtenida comprobando que el numero de registros
		return false;
	}

	/**
	 * Genera el script para realizar la validacion simple sobre la tabla
	 * correspondiente
	 * 
	 * @return
	 */
	private String generateQuerySimpleValidation() {
		return null;
	}
	
	/**
	 * Genera la query para comprobar que para una condición y una columna
	 * todos los registros estan correctamente ofuscados
	 * @return
	 */
	private String generateQueryValidation(){
		return null;
	}
}
