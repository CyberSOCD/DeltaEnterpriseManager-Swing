package envAccess;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.conn.HttpHostConnectException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import common.ResultServlet;
import common.UserConnectionData;
import drivers.WebClass;

public class EnvAccess {

	private UserConnectionData data;
	private String conectionString = "";
	private String envParameter = "";
	private WebClass driver;
	private ResultServlet returnValue;
	private String servletConection;

	/**
	 * Constructor para peticiones con parametros
	 * @param data
	 * @param servletParameter
	 */
	public EnvAccess(UserConnectionData data, String servletParameter, String servletConnection) {
		this.driver = new WebClass();
		this.data = data;
		this.envParameter = servletParameter;
		servletConection = servletConnection;
		returnValue = new ResultServlet();
		this.buildConexionString();
	}
	/**
	 * Constructor para peticiones simples sin parametros
	 * @param data
	 */
	public EnvAccess(UserConnectionData data) {
		this.driver = new WebClass();
		this.data = data;
		this.buildConexionString();
		returnValue = new ResultServlet();
	}

	/**
	 * Realiza la llamada a un servlet con los datos cargados en el objeto
	 * 
	 * @return Devuelve un objeto para consultar el resultado devuelto
	 * @throws Exception
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 * @throws UnknownHostException
	 * @throws SocketTimeoutException
	 * @throws HttpHostConnectException
	 */
	public ResultServlet invokeServlet() throws Exception, IOException,
			MalformedURLException, FailingHttpStatusCodeException,
			UnknownHostException, SocketTimeoutException,
			HttpHostConnectException {
		try {
			driver.setURL(this.conectionString, envParameter);
			returnValue.setDriver(driver);
			returnValue.setErrorCode(0);
		} catch (UnknownHostException e) {
			returnValue.setErrorMessage("El entorno al que intenta acceder no existe");
			returnValue.setErrorCode(1);
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			returnValue
					.setErrorMessage("Se ha producido un error al conectarse al entorno");
			e.printStackTrace();
			returnValue.setErrorCode(2);
		} catch (SocketTimeoutException e) {
			returnValue
					.setErrorMessage("Se ha producido un Timeout durante la conexion al entorno");
			returnValue.setErrorCode(3);
			e.printStackTrace();
		} catch (FailingHttpStatusCodeException e) {
			returnValue
				.setErrorMessage("Se ha producido un error al conectarse al entorno");
			returnValue.setErrorMessage(e.getMessage());
			returnValue.setErrorCode(4);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			returnValue.setErrorCode(5);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			returnValue.setErrorCode(6);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			returnValue.setErrorCode(7);
			e.printStackTrace();
			throw e;
		}
		return this.returnValue;
	}

	/**
	 * Recupera un html del servidor
	 * @return
	 * @throws Exception
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws FailingHttpStatusCodeException
	 * @throws UnknownHostException
	 * @throws SocketTimeoutException
	 * @throws HttpHostConnectException
	 */
	public HtmlPage invokeHtml() throws Exception, IOException,
			MalformedURLException, FailingHttpStatusCodeException,
			UnknownHostException, SocketTimeoutException,
			HttpHostConnectException {
		String URL = data.getDnsAccess();
		try {
			driver.setURL(URL, "");
			driver.getHtmlPage();
		} catch (UnknownHostException e) {
			returnValue.setErrorMessage("El entorno está indisponible");
			e.printStackTrace();
		} catch (HttpHostConnectException e) {
			returnValue
					.setErrorMessage("Se ha producido un error al conectarse al entorno");
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			returnValue
					.setErrorMessage("Se ha producido un Timeout durante la conexion al entorno");
			e.printStackTrace();
		} catch (FailingHttpStatusCodeException e) {
			returnValue.setErrorMessage(e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {

		}
		return driver.getHtmlPage();
	}

	/**
	 * Construye la cadena de conexión necesaria para acceder al entorno
	 */
	private void buildConexionString() {
		this.conectionString = this.data.getDnsAccess() + servletConection;
	}

}
