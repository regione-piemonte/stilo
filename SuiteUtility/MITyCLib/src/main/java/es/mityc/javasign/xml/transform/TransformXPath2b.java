/**
 * Copyright 2013 Ministerio de Industria, Energía y Turismo
 *
 * Este fichero es parte de "Componentes de Firma XAdES".
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
package es.mityc.javasign.xml.transform;

import es.mityc.javasign.xml.xades.TransformProxy;

/**
 * <p>Transformada de XPath 2b.</p>
 */
public class TransformXPath2b extends Transform {
	/** Listado de paths de la transformada. */
	private XPathTransformData data = new XPathTransformData();
	
	/** Constructor. */
	public TransformXPath2b() {
		super(TransformProxy.TRANSFORM_XPATH2FILTER, null);
		setTransformData(data);
	}
	
	/**
	 * <p>Incluye un path en los paths que aplicará esta transformada.</p>
	 * @param path
	 */
	public void addPath(String path) {
		data.addPath(path);
	}

}
