/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Componentes de Firma XAdES 1.1.7".
 *
 * Licencia con arreglo a la EUPL, Versión 1.1 o –en cuanto sean aprobadas por la Comisión Europea– versiones posteriores de la EUPL (la Licencia);
 * Solo podrá usarse esta obra si se respeta la Licencia.
 *
 * Puede obtenerse una copia de la Licencia en:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Salvo cuando lo exija la legislación aplicable o se acuerde por escrito, el programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL»,
 * SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas.
 * Véase la Licencia en el idioma concreto que rige los permisos y limitaciones que establece la Licencia.
 */
package es.mityc.firmaJava.ocsp.exception;


/**
* Clase encargada de tratar los errores producidos en la validacion OCSP
*
*/

public final class OCSPClienteException extends OCSPException {

	public OCSPClienteException() {
		super();
	}

	/**
	 * Constructor de la clase OSCPClienteError
	 * @param mensaje
	 */
	public OCSPClienteException(String mensaje) {
		  super(mensaje);
	}

	/**
	 * Constructor de la clase OSCPClienteError
	 * @param causa
	 */
	public OCSPClienteException(Throwable causa) {
		super(causa);
	}

	/**
	 * Constructor de la clase OSCPClienteError
	 * @param mensaje
	 * @param causa
	 */
	public OCSPClienteException(String mensaje, Throwable causa) {
		super(mensaje, causa);
	}
	
	public String toString(){
		return super.toString();
	}
	
	

}
