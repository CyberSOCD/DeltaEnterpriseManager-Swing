package conexion.drivers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.http.conn.HttpHostConnectException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

public class WebClass {
	private WebClient webClient;
	private Page page;

	/**
	 * Realiza la llamada efectiva contra la URL pasada
	 * 
	 * @param URL
	 * @param xml
	 * @throws Exception
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 * @throws UnknownHostException
	 * @throws SocketTimeoutException
	 * @throws HttpHostConnectException
	 */
	public void setURL(String URL, String xml) throws Exception, IOException,
			MalformedURLException, FailingHttpStatusCodeException,
			UnknownHostException, SocketTimeoutException,
			HttpHostConnectException {
		WebRequest requestSettings = new WebRequest(new URL(URL),
				HttpMethod.POST);
		requestSettings.setRequestBody(xml);
		webClient = new WebClient();
		this.page = webClient.getPage(requestSettings);
	}

	/**
	 * Devuelve el xml recuperado en la llamada
	 * 
	 * @return
	 */
	public XmlPage getXmlPage() {
		return (XmlPage) this.page;
	}
	
	/**
	 * Devuelve el html recuperado en la llamada
	 * 
	 * @return
	 */
	public HtmlPage getHtmlPage(){
		return (HtmlPage) this.page;
	}
}
