package controlador.validationPackage;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import conexion.dbAccess.ObfuscatorQueries;
import conexion.drivers.DriverDB;
import controlador.common.UserConnectionData;
import controlador.controlResult.ObfuscatorStatus;
import controlador.dao.ObfuscatorDAO;
import controlador.objects.obfuscator.ObfuscatorObject;
import ofuscado.query.UpdateObject;
import ofuscado.validation.GeneratorValidation;
import vista.ui.ProgressBar.Validation.ProgressBarValidation;

public class ObfuscatorValidation extends Validation {

	private Date obfusDate = null;
	private HashMap<String, ArrayList<UpdateObject>> map;
	private File file;
	private UserConnectionData data;
	private DriverDB driver;
	private ObfuscatorObject obfuscator;
	private ProgressBarValidation container;
	
	public ObfuscatorValidation(File f, UserConnectionData data) {
		file = f;
		this.data = data;
		obfuscator = new ObfuscatorObject();
		driver = new DriverDB(data);
	}
	
	public ObfuscatorValidation(File f, UserConnectionData data, ProgressBarValidation container) {
		this.container = container;
		file = f;
		this.data = data;
		obfuscator = new ObfuscatorObject();
		driver = new DriverDB(data);
	}

	@Override
	public void validate() throws Exception {
		getTableRelation();
		container.setMaxBar(map.keySet().size());
		try{
			driver.openConnection();
		}catch(SQLException e){
			System.out.println("Se ha producido un error al intentar conectarse a la BBDD.");
			throw e;
		}
		int numTables = 0;
		int numObfusTables = 0;
		int numPdtObfusTables = 0;
		ArrayList<String> tablasAOfuscar = new ArrayList<String>();
		ArrayList<String> tablasOfuscadas = new ArrayList<String>();
		ArrayList<String> tablasSinOfuscar = new ArrayList<String>();
		for(String table:map.keySet()){
			String query = generateQuerySimpleValidation(table,map.get(table));
			container.setToolTip(table);
//			System.out.println("--------------------------------------------------");
//			System.out.println(query);
//			System.out.println("--------------------------------------------------");
			try{
				ResultSet r = driver.executeQuery(query);
				r.next();
				int number = r.getInt("REGISTROS");
				r.close();
				//Se incrementa el numero total de tablas
				numTables++;
				tablasAOfuscar.add(table);
				if(number > 0){
					//Se marca la tabla actual no enteramente ofuscada
					tablasSinOfuscar.add(table);
					numPdtObfusTables++;
				}else if(number == 0){
					if(obfusDate == null){
						//Se marca la tabla como ofuscada correctamente y se consulta la fecha de ofuscado
						query = generateQueryOfusDate(table);
						r = driver.executeQuery(query);
						r.next();
						this.setDate(r.getDate("UPDATE_DATE"));
						r.close();
					}
					numObfusTables++;
					tablasOfuscadas.add(table);
				}
				driver.closeStatement();
			}catch(SQLException ex){
				//En caso de excepcion comprobar que es del tipo de excepciones
				//identificadas como validas e ignoramos esa tabla/vista
				System.out.println("Ha fallado la tabla: " + table);
				System.out.println("La excepcion ha sido: " + ex.getLocalizedMessage());
//				ex.printStackTrace();
			}
			container.increaseProgression();
		}
		obfuscator.setTablasAOfuscar(tablasAOfuscar);
		obfuscator.setTablasOfuscadas(tablasOfuscadas);
		obfuscator.setTablasSinOfuscar(tablasSinOfuscar);
		obfuscator.setFechaValidacion(new Date());
		obfuscator.setFechaOfuscado(obfusDate);
		container.setToolTip("");
		String s = "Resumen de la ejecucion actual\n";
		s = s + "------------------------------------------------------------\n";
		s = s + "Hay un total de " + numTables + " de tablas validas\n";
		s = s + "Hay un total de " + numObfusTables + " de tablas ofuscadas\n";
		s = s + "Hay un total de " + numPdtObfusTables + " de tablas sin ofuscar totalmente\n";
		System.out.println(s);
//		MessageCenterDialog.showInformationDialog((Component)container, s);
		driver.closeConnection();
		
		ObfuscatorDAO dao = new ObfuscatorDAO(data);
		dao.writeFile(obfuscator);
//		LogObfuscator log = new LogObfuscator(data);
//		log.writeObfuscatorItem(obfuscator);
	}

	@Override
	public ObfuscatorStatus getCurrentStatus() {
		return null;
	}

	@Override
	protected void logActivity() {

	}
	
	public ObfuscatorObject getObject(){
		return obfuscator;
	}
	
	private void setDate(Date newDate){
		if(newDate!=null)
			obfusDate = newDate;
	}

	public void setFile(File file) {
		this.file = file;
	}

	private void getTableRelation() {
		GeneratorValidation gen = new GeneratorValidation(file);
		map = gen.geTableColumnRelation();
	}

//	/**
//	 * Comprueba que todos los registros que cumplan una condicion de ofuscado
//	 * esten ofuscdos con su valor correspondiente
//	 * 
//	 * @return
//	 */
//	private boolean checkValidation() {
//		return false;
//	}

//	/**
//	 * Comprueba si hay registros comunes en distintas columnas
//	 */
//	private boolean searchForCommonColumn(String tableName,
//			ArrayList<UpdateObject> columns) {
//		// Obtiene la query que comprueba registros comunes
//		String query = ObfuscatorQueries.getSearchMultipleRowsQuery(tableName, columns);
//		// Se ejecuta la query obtenida comprobando que el numero de registros
//		return false;
//	}

	/**
	 * Genera el script para realizar la validacion simple sobre la tabla
	 * correspondiente
	 * 
	 * @return
	 */
	private String generateQuerySimpleValidation(String tableName, ArrayList<UpdateObject> columns) {
		return ObfuscatorQueries.getSimpleQuery(tableName, columns);
	}
	
	private String generateQueryOfusDate(String tableName){
		return ObfuscatorQueries.getOfusDate(tableName);
	}
}
