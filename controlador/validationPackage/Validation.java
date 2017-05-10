package validationPackage;

import controlResult.GenericStatus;

/**
 * 
 * Se encarga de informar del estado de los entornos
 *
 */
public abstract class Validation {
	/**
	 * Valida el estado actual del entorno con los datos pasados
	 * @param data
	 */
	public abstract void validate() throws Exception;
	/**
	 * Devuelve el estado actual del entorno
	 * @return
	 */
	public abstract GenericStatus getCurrentStatus();
	/**
	 * Almacena en el log todos los datos relacionados con la validacion
	 */
	protected abstract void logActivity();
}
