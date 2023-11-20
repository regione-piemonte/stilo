/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.util.Date;

public class DelegaVsUtenteBean {
	
	@NumeroColonna(numero = "2")
	private String idUtente;	
	@NumeroColonna(numero = "3")
	private String descrizione;	
	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataVldDal;	
	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataVldFinoAl;	
	@NumeroColonna(numero = "6")
	private String motivo;
	
	public String getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Date getDataVldDal() {
		return dataVldDal;
	}
	public void setDataVldDal(Date dataVldDal) {
		this.dataVldDal = dataVldDal;
	}
	public Date getDataVldFinoAl() {
		return dataVldFinoAl;
	}
	public void setDataVldFinoAl(Date dataVldFinoAl) {
		this.dataVldFinoAl = dataVldFinoAl;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}	
	
}
