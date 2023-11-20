/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TRelCaselleFruitoriBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -7341833444762147789L;
	
	private boolean flgAnn;
	private boolean flgUtilizzoXInvio;
	private boolean flgUtilizzoXRicezione;
	private String idCasella;
	private String idRelFruitoreCasella;
	private String idUteIns;
	private String idUteUltimoAgg;
	private Date tsIns;
	private Date tsUltimoAgg;
	private String idFruitoreCasella;

	public boolean getFlgAnn(){ return flgAnn;}

	public void setFlgAnn(boolean flgAnn){ this.flgAnn=flgAnn;}

	public boolean getFlgUtilizzoXInvio(){ return flgUtilizzoXInvio;}

	public void setFlgUtilizzoXInvio(boolean flgUtilizzoXInvio){ this.flgUtilizzoXInvio=flgUtilizzoXInvio;}

	public boolean getFlgUtilizzoXRicezione(){ return flgUtilizzoXRicezione;}

	public void setFlgUtilizzoXRicezione(boolean flgUtilizzoXRicezione){ this.flgUtilizzoXRicezione=flgUtilizzoXRicezione;}

	public String getIdCasella(){ return idCasella;}

	public void setIdCasella(String idCasella){ this.idCasella=idCasella;}

	public String getIdRelFruitoreCasella(){ return idRelFruitoreCasella;}

	public void setIdRelFruitoreCasella(String idRelFruitoreCasella){ this.idRelFruitoreCasella=idRelFruitoreCasella;}

	public String getIdUteIns(){ return idUteIns;}

	public void setIdUteIns(String idUteIns){ this.idUteIns=idUteIns;}

	public String getIdUteUltimoAgg(){ return idUteUltimoAgg;}

	public void setIdUteUltimoAgg(String idUteUltimoAgg){ this.idUteUltimoAgg=idUteUltimoAgg;}

	public Date getTsIns(){ return tsIns;}

	public void setTsIns(Date tsIns){ this.tsIns=tsIns;}

	public Date getTsUltimoAgg(){ return tsUltimoAgg;}

	public void setTsUltimoAgg(Date tsUltimoAgg){ this.tsUltimoAgg=tsUltimoAgg;}

	public String getIdFruitoreCasella() {
		return idFruitoreCasella;
	}

	public void setIdFruitoreCasella(String idFruitoreCasella) {
		this.idFruitoreCasella = idFruitoreCasella;
	}


}    