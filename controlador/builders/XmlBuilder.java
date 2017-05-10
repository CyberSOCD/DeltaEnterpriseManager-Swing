package builders;

import common.UserConnectionData;

import fileAccess.LoadFile;

/**
 * 
 * Genera el xml que se enviara al servlet
 *
 */
public class XmlBuilder {
	
	private final String regex_LOGIN_NAME = "%%LOGIN_NAME%%";
	private final String regex_LOGIN_PASSWORD = "%%LOGIN_PASSWORD%%";
	private UserConnectionData data;
	
	private String method;
	public XmlBuilder(UserConnectionData data, String method){
		this.method = method;
		this.data = data;
	}
	
	public String getXml(){
		String xml = "";
		for(String line:LoadFile.getFile("/LoginDataActions.properties")){
			if(line.split("=")[0].equals(method)){
				xml = line.replace(method + "=", "");
				xml = replaceVariables(xml);
				break;
			}
		}
		return xml;
	}
	
	private String replaceVariables(String value){
		String aux = value.replace(regex_LOGIN_NAME, data.getUser());
		aux = aux.replace(regex_LOGIN_PASSWORD, data.getPassword());
		return aux;
	}
}
