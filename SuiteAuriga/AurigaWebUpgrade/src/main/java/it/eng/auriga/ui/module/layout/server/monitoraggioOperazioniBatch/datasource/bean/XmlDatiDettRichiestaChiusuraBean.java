/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Registrazione;
import it.eng.auriga.ui.module.layout.server.archivio.datasource.bean.Stato;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;


public class XmlDatiDettRichiestaChiusuraBean implements Serializable{
	
	@XmlVariabile(nome="@Caselle", tipo=TipoVariabile.LISTA)
	private List<CasellaBean> caselle;
	
	@XmlVariabile(nome="@UOSVAssegnatarie", tipo=TipoVariabile.LISTA)
	private List<AssegnatarioOpBatchXmlBean> uosvAssegnatarie;
	
	@XmlVariabile(nome="UOSVTarget", tipo=TipoVariabile.SEMPLICE)
	private String uosvTarget;
	 
	@XmlVariabile(nome="DataInvioDa", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioDa;
	
	@XmlVariabile(nome="DataInvioA", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInvioA;
	
	@XmlVariabile(nome="OggApertiDaMinGG", tipo=TipoVariabile.SEMPLICE)
	private String oggApertiDaMinGG;

	@XmlVariabile(nome="OggApertiDaMinMesi", tipo=TipoVariabile.SEMPLICE)
	private String oggApertiDaMinMesi;

	@XmlVariabile(nome="OggSenzaOperDaMinGG", tipo=TipoVariabile.SEMPLICE)
	private String oggSenzaOperDaMinGG;

	@XmlVariabile(nome="OggSenzaOperDaMinMesi", tipo=TipoVariabile.SEMPLICE)
	private String oggSenzaOperDaMinMesi;

	public List<CasellaBean> getCaselle() {
		return caselle;
	}

	public void setCaselle(List<CasellaBean> caselle) {
		this.caselle = caselle;
	}

	

	public String getUosvTarget() {
		return uosvTarget;
	}

	public void setUosvTarget(String uosvTarget) {
		this.uosvTarget = uosvTarget;
	}

	public Date getDataInvioDa() {
		return dataInvioDa;
	}

	public void setDataInvioDa(Date dataInvioDa) {
		this.dataInvioDa = dataInvioDa;
	}

	public Date getDataInvioA() {
		return dataInvioA;
	}

	public void setDataInvioA(Date dataInvioA) {
		this.dataInvioA = dataInvioA;
	}

	public String getOggApertiDaMinGG() {
		return oggApertiDaMinGG;
	}

	public void setOggApertiDaMinGG(String oggApertiDaMinGG) {
		this.oggApertiDaMinGG = oggApertiDaMinGG;
	}

	public String getOggApertiDaMinMesi() {
		return oggApertiDaMinMesi;
	}

	public void setOggApertiDaMinMesi(String oggApertiDaMinMesi) {
		this.oggApertiDaMinMesi = oggApertiDaMinMesi;
	}

	public String getOggSenzaOperDaMinGG() {
		return oggSenzaOperDaMinGG;
	}

	public void setOggSenzaOperDaMinGG(String oggSenzaOperDaMinGG) {
		this.oggSenzaOperDaMinGG = oggSenzaOperDaMinGG;
	}

	public String getOggSenzaOperDaMinMesi() {
		return oggSenzaOperDaMinMesi;
	}

	public void setOggSenzaOperDaMinMesi(String oggSenzaOperDaMinMesi) {
		this.oggSenzaOperDaMinMesi = oggSenzaOperDaMinMesi;
	}

	public List<AssegnatarioOpBatchXmlBean> getUosvAssegnatarie() {
		return uosvAssegnatarie;
	}

	public void setUosvAssegnatarie(List<AssegnatarioOpBatchXmlBean> uosvAssegnatarie) {
		this.uosvAssegnatarie = uosvAssegnatarie;
	}
			
}