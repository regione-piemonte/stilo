/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.InputStream;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContabilitaAdspGetContenutoDocumentoResponse extends ContabilitaAdspResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private InputStream contenutoDocumento;

	public InputStream getContenutoDocumento() {
		return contenutoDocumento;
	}

	public void setContenutoDocumento(InputStream contenutoDocumento) {
		this.contenutoDocumento = contenutoDocumento;
	}

	@Override
	public String toString() {
		return "ContabilitaAdspGetContenutoDocumentoResponse [contenutoDocumento=" + contenutoDocumento + "]";
	}

}
