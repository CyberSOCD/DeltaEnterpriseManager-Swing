package fuentes;

import java.util.ArrayList;
import java.util.HashMap;

import fileAccess.LoadFile;

public class EnvironmentData {
	private HashMap<String, String> EnvironmentsURLS = new HashMap<String, String>();
	private HashMap<String, String> EnvironmentsNames = new HashMap<String, String>();
	private HashMap<String, String> EnvironmentsKeys = new HashMap<String, String>();
	private HashMap<String, String> EnvironmentsDDBB = new HashMap<String, String>();
	//Keys de los Hashmaps
//	public static final String DESA_CORR_AM = "DESARROLLO_CORRECTIVO_AM_LX";
//	public static final String TEST_CORR_AM = "TEST_CORRECTIVO_AM_LX";
//	public static final String DESA_EVO_INDRA_AM = "DESARROLLO_EVOLUTIVO_INDRA_AM_LX";
//	public static final String DESA_EVO_EVERIS_AM = "DESARROLLO_EVOLUTIVO_EVERIS_AM_LX";
//	public static final String TEST_EVO_AM = "TEST_EVOLUTIVO_AM_LX";
//	public static final String ACEP_EVO_AM = "ACEPTACION_EVOLUTIVO_AM_LX";
//	public static final String PARALELO_AM = "PARALELO_AM_LX";
	
	public EnvironmentData(){
		ArrayList<String> lista = LoadFile.getFile("EnvironmentList.properties");
		for(String line:lista){
			if(line.contains("__SKIP__"))
				continue;
			this.EnvironmentsURLS.put(line.split("=")[0], line.split("=")[1]);
			this.EnvironmentsNames.put(line.split("=")[0], line.split("=")[2]);
			this.EnvironmentsKeys.put(line.split("=")[2], line.split("=")[0]);
		}
		lista = LoadFile.getFile("DataBasesList.properties");
		for(String line:lista){
			if(line.contains("__SKIP__"))
				continue;
			EnvironmentsDDBB.put(line.split("=")[0],line.split("=")[1]);
		}
	}
	
	public ArrayList<String> getNamesList(){
		ArrayList<String> list = new ArrayList<String>();
		for(String value:this.EnvironmentsNames.values()){
			list.add(value);
		}
		return list;
	}
	
	public String getURL(String nameEnvKey){
		return this.EnvironmentsURLS.get(this.EnvironmentsKeys.get(nameEnvKey));
	}
	
	public String getFormatName(String nameEnvKey){
		return this.EnvironmentsNames.get(this.EnvironmentsKeys.get(nameEnvKey));
	}
	
	public String getDatabase(String nameEnvKey){
		return this.EnvironmentsDDBB.get(this.EnvironmentsKeys.get(nameEnvKey));
	}
	
}
