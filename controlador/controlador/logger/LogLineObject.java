package controlador.logger;

import java.util.Date;

import controlador.controlResult.GenericStatus;
import controlador.controlResult.GenericStatus.CurrentStatusValue;

public class LogLineObject {
	private Date fechaValidacion;
	private int estado;
	private String mensajeError;
	private int tiempoRespuesta;
	private String serverVersion;
	private String arqVersion;
	private boolean error;

	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	public void setValidationDate(Date fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getErrorMessage() {
		return mensajeError;
	}

	public void setErrorMessage(String mensajeError) {
		this.mensajeError = mensajeError;
		setError(true);
	}

	public int getResponseTime() {
		return tiempoRespuesta;
	}

	public void setResponseTime(int tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}

	public int getStatus() {
		return estado;
	}

	public void setStatus(int estado) {
		this.estado = estado;
	}

	public String getServerVersion() {
		return serverVersion;
	}

	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}

	public String getArqVersion() {
		return arqVersion;
	}

	public void setArqVersion(String arqVersion) {
		this.arqVersion = arqVersion;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

}
