package controlador.xmlCheck;

import com.gargoylesoftware.htmlunit.html.DomAttr;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

import controlador.common.ResultServlet;

public class CheckXmlOffline {

	private XmlPage xml;
	
	public CheckXmlOffline(ResultServlet result){
		if(result.getDriver() == null)
			xml=null;
		else
			xml = result.getDriver().getXmlPage();
	}
	/**
	 * Comprueba si no hay error en la respuesta del servidor
	 */
	public boolean respuestaCorrecta(){
		return xml == null?false:true;
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
	
	public String getIdProccess(){
		String idProcces = "";
		for(DomElement d:xml.getDocumentElement().getChildElements()){
			if(d.getNodeName().equals("idProcess"))
				idProcces = d.getTextContent();
		}
		return idProcces;
	}
}
