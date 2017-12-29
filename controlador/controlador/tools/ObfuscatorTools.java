package controlador.tools;

import java.util.ArrayList;
import java.util.Date;

import conexion.fileAccess.LoadFile;
import controlador.common.UserConnectionData;
import controlador.objects.obfuscator.ObfuscatorObject;

/**
 * Carga los objetos recuperados del fichero correspondiente
 * 
 */
public class ObfuscatorTools {

	private final static String FOLDER_NAME = "ofuscado";
	private final static String FILE_EXTENSION = "_ofus_data.log";

	// Separaciones en el objeto serializado manteniendo el orden en que se
	// declaran
	private final static String MARCA_VALIDA_DATE_INI = "<v>";
	private final static String MARCA_VALIDA_DATE_FIN = "</v>";

	private final static String MARCA_OFUS_DATE_INI = "<d>";
	private final static String MARCA_OFUS_DATE_FIN = "</d>";

	private final static String MARCA_LISTA_TABLAS_TOTAL_INI = "<t>";
	private final static String MARCA_LISTA_TABLAS_TOTAL_FIN = "</t>";
	private final static String MARCA_LISTA_TABLAS_SIN_INI = "<s>";
	private final static String MARCA_LISTA_TABLAS_SIN_FIN = "</s>";
	private final static String MARCA_LISTA_TABLAS_OFUS_INI = "<o>";
	private final static String MARCA_LISTA_TABLAS_OFUS_FIN = "</o>";

	private final static String MARCA_COMA = ",";

	private ArrayList<ObfuscatorObject> list;
	private String fileName;

	public ObfuscatorTools(UserConnectionData data) {
		fileName = data.getEnvKey() + FILE_EXTENSION;
		list = new ArrayList<ObfuscatorObject>();
	}
	
	public void updateEnv(UserConnectionData data){
		fileName = data.getEnvKey() + FILE_EXTENSION;
	}

	public ArrayList<ObfuscatorObject> getList() {
		list.clear();
		processFile();
		return list;
	}
	
	public void writeFile(ObfuscatorObject object){
		String line = serializeObfuscatorObject(object);
		LoadFile.appendText(FOLDER_NAME, fileName , line);
		LoadFile.appendNewLine(FOLDER_NAME, fileName);
	}
	
	private void processFile() {
		for(String line:LoadFile.getFile(FOLDER_NAME, fileName)){
			list.add(deserialize(line));
		}
	}

	/**
	 * Transforma un @ObfuscatorObject en un @String para su almacenamiento
	 * 
	 * @param object
	 * @return
	 */
	public static String serializeObfuscatorObject(ObfuscatorObject object) {
		String temporal = "";
		temporal = MARCA_VALIDA_DATE_INI
				+ object.getFechaValidacion().getTime() + MARCA_VALIDA_DATE_FIN;
		temporal += MARCA_OFUS_DATE_INI + object.getFechaOfuscado().getTime()
				+ MARCA_OFUS_DATE_FIN;
		// Recorre la lista de tablas
		temporal += MARCA_LISTA_TABLAS_TOTAL_INI;
		for (String table : object.getTablasAOfuscar()) {
			temporal += table + MARCA_COMA;
		}
		temporal += MARCA_LISTA_TABLAS_TOTAL_FIN;
		// Recorre la lista de tablas sin ofuscar
		temporal += MARCA_LISTA_TABLAS_SIN_INI;
		for (String table : object.getTablasSinOfuscar()) {
			temporal += table + MARCA_COMA;
		}
		temporal += MARCA_LISTA_TABLAS_SIN_FIN;
		// Recorre la lista de tablas ofuscadas
		temporal += MARCA_LISTA_TABLAS_OFUS_INI;
		for (String table : object.getTablasOfuscadas()) {
			temporal += table + MARCA_COMA;
		}
		temporal += MARCA_LISTA_TABLAS_OFUS_FIN;
		return temporal;
	}

	public static ObfuscatorObject deserialize(String object) {
		// Se crea el objeto inicialmente
		ObfuscatorObject obj = new ObfuscatorObject();
		// Se lee la fecha de validacion
		Long date = Long.valueOf(object.substring(
				object.indexOf(MARCA_VALIDA_DATE_INI) + 3,
				object.indexOf(MARCA_VALIDA_DATE_FIN)));
		obj.setFechaValidacion(new Date(date));
		// Se lee la fecha de ofuscado
		date = Long.valueOf(object.substring(
				object.indexOf(MARCA_OFUS_DATE_INI) + 3,
				object.indexOf(MARCA_OFUS_DATE_FIN)));
		obj.setFechaOfuscado(new Date(date));
		// Se carga la lista de tablas totales
		String tableList = object.substring(
				object.indexOf(MARCA_LISTA_TABLAS_TOTAL_INI) + 3,
				object.indexOf(MARCA_LISTA_TABLAS_TOTAL_FIN));
		// Se crea Array y se carga de tablas de la lista
		ArrayList<String> list = new ArrayList<String>();
		for (String table : tableList.split(MARCA_COMA)) {
			if (!table.isEmpty())
				list.add(table);
		}
		obj.setTablasAOfuscar(list);
		obj.setNumTablasAOfuscar(list.size());
		// Se carga la lista de tablas sin ofuscar
		tableList = object.substring(
				object.indexOf(MARCA_LISTA_TABLAS_SIN_INI) + 3,
				object.indexOf(MARCA_LISTA_TABLAS_SIN_FIN));
		// Se crea Array y se carga de tablas de la lista
		list = new ArrayList<String>();
		for (String table : tableList.split(MARCA_COMA)) {
			if (!table.isEmpty())
				list.add(table);
		}
		obj.setTablasSinOfuscar(list);
		obj.setNumTablasSinOfuscar(list.size());
		// Se carga la lista de tablas ofuscadas
		tableList = object.substring(
				object.indexOf(MARCA_LISTA_TABLAS_OFUS_INI) + 3,
				object.indexOf(MARCA_LISTA_TABLAS_OFUS_FIN));
		// Se crea Array y se carga de tablas de la lista
		list = new ArrayList<String>();
		for (String table : tableList.split(MARCA_COMA)) {
			if (!table.isEmpty())
				list.add(table);
		}
		obj.setTablasOfuscadas(list);
		obj.setNumTablasOfuscadas(list.size());
		return obj;
	}
}
