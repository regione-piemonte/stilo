/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.CreaModDocumentoInBean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModDDTInBean extends CreaModDocumentoInBean {
	
	private static final long serialVersionUID = -4011655723485405230L;
	
	@XmlVariabile(nome = "FATT_NUM_REL_DDT_Doc", tipo = TipoVariabile.SEMPLICE)
	private Integer fattNumRelDDT;
	
	@XmlVariabile(nome="FATT_DATA_REL_DDT_Doc", tipo = TipoVariabile.SEMPLICE)
	private String fattDataRelDDT;
	
	@XmlVariabile(nome="DDT_COD_PAZIENTE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codicePazienteDDT;
	
	@XmlVariabile(nome="ROUTE_Doc", tipo = TipoVariabile.SEMPLICE)
	private String route;
	
	@XmlVariabile(nome="TERAPIA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String terapia;
	
	@XmlVariabile(nome="CODICE_TIPO_DOCUMENTO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String codiceTipoDocumento;
	
	@XmlVariabile(nome="STATO_BOLLA_Doc", tipo = TipoVariabile.SEMPLICE)
	private String statoBolla;
	
	@XmlVariabile(nome="BOLLA_A_ZERO_Doc", tipo = TipoVariabile.SEMPLICE)
	private String bollaAZero;
	
	public Integer getFattNumRelDDT() {
		return fattNumRelDDT;
	}

	public void setFattNumRelDDT(Integer fattNumRelDDT) {
		this.fattNumRelDDT = fattNumRelDDT;
	}

	public String getFattDataRelDDT() {
		return fattDataRelDDT;
	}

	public void setFattDataRelDDT(String fattDataRelDDT) {
		this.fattDataRelDDT = fattDataRelDDT;
	}

	public String getCodicePazienteDDT() {
		return codicePazienteDDT;
	}

	public void setCodicePazienteDDT(String codicePazienteDDT) {
		this.codicePazienteDDT = codicePazienteDDT;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getTerapia() {
		return terapia;
	}

	public void setTerapia(String terapia) {
		this.terapia = terapia;
	}

	public String getCodiceTipoDocumento() {
		return codiceTipoDocumento;
	}

	public void setCodiceTipoDocumento(String codiceTipoDocumento) {
		this.codiceTipoDocumento = codiceTipoDocumento;
	}

	public String getStatoBolla() {
		return statoBolla;
	}

	public void setStatoBolla(String statoBolla) {
		this.statoBolla = statoBolla;
	}

	public String getBollaAZero() {
		return bollaAZero;
	}

	public void setBollaAZero(String bollaAZero) {
		this.bollaAZero = bollaAZero;
	}
}