package xmlCheck;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import common.ResultServlet;

public class CheckXmlLogin {
	private XmlPage xml;
	
	public CheckXmlLogin(ResultServlet result){
		xml = result.getDriver().getXmlPage();
	}
	/**
	 * Comprueba si no hay error en la respuesta del servidor
	 */
	public boolean validarResultado(){
		return (getAttribute("errormessage").getValue().isEmpty());
	}
	/**
	 * Recupera el error devuelto via por el xml
	 * @return
	 */
	public String getErrorMessage(){
		if(getAttribute("errormessage")!=null)
			return getAttribute("errormessage").getValue();
		return "Error desconocido, contacte con Administrador";
	}
	
	public String getArqVersion(){
		String value = "";
		value = xml.getDocumentElement().getByXPath("//arqVersion/text()").get(0).toString();
		return value;
	}
	
	private DomAttr getAttribute(String attribute){
		return xml.getDocumentElement().getAttributeNode(attribute);
	}
}