/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.math.BigDecimal;
import it.eng.document.NumeroColonna;


public class DocumentoBancoDoBrasilXmlMasterDataBean {

	@NumeroColonna(numero="1")
	private String dataDocumento;
	
	@NumeroColonna(numero="2")
	private String dataElaborazione;
	
	@NumeroColonna(numero="3")
	private String codAccountClass;

    @NumeroColonna(numero="4")
	private String descAccountClass;
	
    @NumeroColonna(numero="5")
	private String codTipoDocumento;

	@NumeroColonna(numero="6")
	private String descTipoDocumento;

	@NumeroColonna(numero="7")
	private BigDecimal nrDocumenti;

	
	public String getDataDocumento() {
		return dataDocumento;
	}

	
	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	
	public String getDataElaborazione() {
		return dataElaborazione;
	}

	
	public void setDataElaborazione(String dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}

	
	public String getCodAccountClass() {
		return codAccountClass;
	}

	
	public void setCodAccountClass(String codAccountClass) {
		this.codAccountClass = codAccountClass;
	}

	
	public String getDescAccountClass() {
		return descAccountClass;
	}

	
	public void setDescAccountClass(String descAccountClass) {
		this.descAccountClass = descAccountClass;
	}

	
	public String getCodTipoDocumento() {
		return codTipoDocumento;
	}

	
	public void setCodTipoDocumento(String codTipoDocumento) {
		this.codTipoDocumento = codTipoDocumento;
	}

	
	public String getDescTipoDocumento() {
		return descTipoDocumento;
	}

	
	public void setDescTipoDocumento(String descTipoDocumento) {
		this.descTipoDocumento = descTipoDocumento;
	}

	
	public BigDecimal getNrDocumenti() {
		return nrDocumenti;
	}

	
	public void setNrDocumenti(BigDecimal nrDocumenti) {
		this.nrDocumenti = nrDocumenti;
	}
	
	}
