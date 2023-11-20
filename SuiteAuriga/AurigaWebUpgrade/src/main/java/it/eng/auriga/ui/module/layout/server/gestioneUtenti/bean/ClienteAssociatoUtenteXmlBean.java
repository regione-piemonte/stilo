/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ClienteAssociatoUtenteXmlBean {

	@NumeroColonna(numero = "1")
	private String denominazioneSocieta;
	
	@NumeroColonna(numero = "2")
	private String denominazioneSoggetto;

	@NumeroColonna(numero = "3")
	private String codFiscalePiva;
	
	@NumeroColonna(numero = "4")
	private String cid;

	@NumeroColonna(numero = "5")
	private String gruppoDiRiferimento;

	@NumeroColonna(numero = "6")
	private String idCliente;

	public String getDenominazioneSocieta() {
		return denominazioneSocieta;
	}

	public void setDenominazioneSocieta(String denominazioneSocieta) {
		this.denominazioneSocieta = denominazioneSocieta;
	}

	public String getDenominazioneSoggetto() {
		return denominazioneSoggetto;
	}

	public void setDenominazioneSoggetto(String denominazioneSoggetto) {
		this.denominazioneSoggetto = denominazioneSoggetto;
	}

	public String getCodFiscalePiva() {
		return codFiscalePiva;
	}

	public void setCodFiscalePiva(String codFiscalePiva) {
		this.codFiscalePiva = codFiscalePiva;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getGruppoDiRiferimento() {
		return gruppoDiRiferimento;
	}

	public void setGruppoDiRiferimento(String gruppoDiRiferimento) {
		this.gruppoDiRiferimento = gruppoDiRiferimento;
	}

	}
