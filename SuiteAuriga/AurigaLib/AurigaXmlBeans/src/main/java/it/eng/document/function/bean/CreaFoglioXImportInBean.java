/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaFoglioXImportInBean extends RebuildedFile implements Serializable {
	
	public static final String TIPO_CONTENUTO_EXPORT_ZIP = "DOCUMENTI DA ESPORTARE COME ZIP";

	private static final long serialVersionUID = -7886575288881728315L;
	
	private String tipoContenuto;

	public String getTipoContenuto() {
		return tipoContenuto;
	}

	public void setTipoContenuto(String tipoContenuto) {
		this.tipoContenuto = tipoContenuto;
	}
	
}
