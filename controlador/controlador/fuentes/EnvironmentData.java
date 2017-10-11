package controlador.fuentes;

import java.util.ArrayList;
import java.util.HashMap;

import conexion.fileAccess.LoadFile;

public class EnvironmentData {
	private HashMap<String, String> EnvironmentsURLS = new HashMap<String, String>();
	private HashMap<String, String> EnvironmentsNames = new HashMap<String, String>();
	private HashMap<String, String> EnvironmentsKeys = new HashMap<String, String>();
	private HashMap<String, String> EnvironmentsDDBB = new HashMap<String, String>();

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
	
	public String getEnvKey(String nameEnvKey){
		return this.EnvironmentsKeys.get(nameEnvKey);
	}
	
}
