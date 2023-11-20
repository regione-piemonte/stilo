/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class ClienteDestDocXmlBean {

	@NumeroColonna(numero = "1")
	private String cidSocieta;
	
	@NumeroColonna(numero = "2")
	private String idCliente;

	@NumeroColonna(numero = "3")
	private String codCliente;
	
    @NumeroColonna(numero = "4")
	private String desCliente;

	@NumeroColonna(numero = "5")
	private String cfPivaCliente;

	@NumeroColonna(numero = "6")
	private String canaleInvioCliente;
	
	@NumeroColonna(numero = "7")	
	private String codiceCommittente;
	
	@NumeroColonna(numero = "8")	
	private String idCommerciale;

	@NumeroColonna(numero = "9")	
	private String idAgente;

	

	
	public String getCidSocieta() {
		return cidSocieta;
	}

	public void setCidSocieta(String cidSocieta) {
		this.cidSocieta = cidSocieta;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getDesCliente() {
		return desCliente;
	}

	public void setDesCliente(String desCliente) {
		this.desCliente = desCliente;
	}

	public String getCfPivaCliente() {
		return cfPivaCliente;
	}

	public void setCfPivaCliente(String cfPivaCliente) {
		this.cfPivaCliente = cfPivaCliente;
	}

	public String getCanaleInvioCliente() {
		return canaleInvioCliente;
	}

	public void setCanaleInvioCliente(String canaleInvioCliente) {
		this.canaleInvioCliente = canaleInvioCliente;
	}

	public String getCodiceCommittente() {
		return codiceCommittente;
	}

	public void setCodiceCommittente(String codiceCommittente) {
		this.codiceCommittente = codiceCommittente;
	}

	public String getIdCommerciale() {
		return idCommerciale;
	}

	public void setIdCommerciale(String idCommerciale) {
		this.idCommerciale = idCommerciale;
	}

	public String getIdAgente() {
		return idAgente;
	}

	public void setIdAgente(String idAgente) {
		this.idAgente = idAgente;
	}
}