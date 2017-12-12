package controlador.xmlCheck;

import vista.ui.Profiles.ProfileControl.TipoSistema;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import controlador.common.ResultServlet;

public class CheckXmlOnline {

	private XmlPage xml;
	private TipoSistema sistema;
	
	public CheckXmlOnline(ResultServlet result, TipoSistema tipo){
		if(result.getDriver() == null)
			xml=null;
		else
			xml = result.getDriver().getXmlPage();
		sistema = tipo;
	}
	/**
	 * Comprueba si no hay error en la respuesta del servidor
	 */
	public boolean validarResultado(){
		return xml == null?false:validate();
	}
	
	public String getErrorMessage(){
		if(xml!=null){
			if(getAttribute(xml.getDocumentElement(), "errormessage")!=null)
				return getAttribute(xml.getDocumentElement(), "errormessage").getValue();
		}
		return null;
	}
	
	private DomAttr getAttribute(DomElement domElement, String attribute){
		return domElement.getAttributeNode(attribute);
	}
	
	private boolean validate(){
		boolean valor = false;
		if(sistema.equals(TipoSistema.MAYORISTA)){
			valor = validateAM();
		}else if(sistema.equals(TipoSistema.MINORISTA)){
			valor = validateMIN();
		}
		return valor;
	}
	
	private boolean validateAM(){
		boolean returnValue = false;
		for(DomElement d:xml.getDocumentElement().getChildElements()){
			returnValue = d.getNodeName().equals("customerList");
		}
		return returnValue;
	}
	
	private boolean validateMIN(){
		boolean returnValue = false;
		for(DomElement d:xml.getDocumentElement().getChildElements()){
			if(d.getNodeName().equals("CC_MIN_SEARCH_BY_CUSTOMER")){
				returnValue = getAttribute(d, "NUM_RETURNED_ROWS")!=null;
			}
		}
		return returnValue;
	}
}
