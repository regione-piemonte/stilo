/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.shared.bean.OpzioniCopertinaTimbroBean;

/**
 * Permette di definire valori di configurazione necessari a al servizio
 * AurigaAgibilitaResource
 * 
 * @author Antonio Peluso
 *
 */

public class RicercaAgibilitaWSConfigBean {

	private String defaultSchema;

	private String codIdConnectionToken;

	private String pathServiceFile;
	
	private String nomeModello;
	
	private String tipoBarcodeModello;

	// OPZIONI TIMBRO
	private OpzioniCopertinaTimbroBean opzioniTimbro;

	public String getDefaultSchema() {
		return defaultSchema;
	}

	public void setDefaultSchema(String defaultSchema) {
		this.defaultSchema = defaultSchema;
	}

	public OpzioniCopertinaTimbroBean getOpzioniTimbro() {
		return opzioniTimbro;
	}

	public void setOpzioniTimbro(OpzioniCopertinaTimbroBean opzioniTimbro) {
		this.opzioniTimbro = opzioniTimbro;
	}

	public String getCodIdConnectionToken() {
		return codIdConnectionToken;
	}

	public void setCodIdConnectionToken(String codIdConnectionToken) {
		this.codIdConnectionToken = codIdConnectionToken;
	}

	public String getPathServiceFile() {
		return pathServiceFile;
	}

	public void setPathServiceFile(String pathServiceFile) {
		this.pathServiceFile = pathServiceFile;
	}

	public String getNomeModello() {
		return nomeModello;
	}

	public String getTipoBarcodeModello() {
		return tipoBarcodeModello;
	}

	public void setNomeModello(String nomeModello) {
		this.nomeModello = nomeModello;
	}

	public void setTipoBarcodeModello(String tipoBarcodeModello) {
		this.tipoBarcodeModello = tipoBarcodeModello;
	}

}
