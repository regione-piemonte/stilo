/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author DANCRIST
 *
 */

public class CriterioIterNonSvoltoBean {
	
	 @NumeroColonna(numero="1")
	 private String tipoFaseEvento;
	 
	 @NumeroColonna(numero="2")
	 private String nomeTaskFase;
	 
	 @NumeroColonna(numero = "4")
	 private String statoFaseEvento;
	 
	 @NumeroColonna(numero="6")
	 @TipoData(tipo=Tipo.DATA)
	 private Date dataInvioFaseAppDa;
	 
	 @NumeroColonna(numero="7")
	 @TipoData(tipo=Tipo.DATA)
	 private Date dataInvioFaseAppA;
	 
	 @NumeroColonna(numero="8")
	 @TipoData(tipo=Tipo.DATA)
	 private Date dataInvioVerContDa;
	 
	 @NumeroColonna(numero="9")
	 @TipoData(tipo=Tipo.DATA)
	 private Date dataInvioVerContA;
	 
	 @NumeroColonna(numero="14")
	 private String esito;

	
	/**
	 * @return the tipoFaseEvento
	 */
	public String getTipoFaseEvento() {
		return tipoFaseEvento;
	}

	
	/**
	 * @param tipoFaseEvento the tipoFaseEvento to set
	 */
	public void setTipoFaseEvento(String tipoFaseEvento) {
		this.tipoFaseEvento = tipoFaseEvento;
	}

	
	/**
	 * @return the nomeTaskFase
	 */
	public String getNomeTaskFase() {
		return nomeTaskFase;
	}

	
	/**
	 * @param nomeTaskFase the nomeTaskFase to set
	 */
	public void setNomeTaskFase(String nomeTaskFase) {
		this.nomeTaskFase = nomeTaskFase;
	}

	
	
	/**
	 * @return the statoFaseEvento
	 */
	public String getStatoFaseEvento() {
		return statoFaseEvento;
	}


	
	/**
	 * @param statoFaseEvento the statoFaseEvento to set
	 */
	public void setStatoFaseEvento(String statoFaseEvento) {
		this.statoFaseEvento = statoFaseEvento;
	}


	/**
	 * @return the dataInvioFaseAppDa
	 */
	public Date getDataInvioFaseAppDa() {
		return dataInvioFaseAppDa;
	}

	
	/**
	 * @param dataInvioFaseAppDa the dataInvioFaseAppDa to set
	 */
	public void setDataInvioFaseAppDa(Date dataInvioFaseAppDa) {
		this.dataInvioFaseAppDa = dataInvioFaseAppDa;
	}

	
	/**
	 * @return the dataInvioFaseAppA
	 */
	public Date getDataInvioFaseAppA() {
		return dataInvioFaseAppA;
	}

	
	/**
	 * @param dataInvioFaseAppA the dataInvioFaseAppA to set
	 */
	public void setDataInvioFaseAppA(Date dataInvioFaseAppA) {
		this.dataInvioFaseAppA = dataInvioFaseAppA;
	}

	
	/**
	 * @return the dataInvioVerContDa
	 */
	public Date getDataInvioVerContDa() {
		return dataInvioVerContDa;
	}

	
	/**
	 * @param dataInvioVerContDa the dataInvioVerContDa to set
	 */
	public void setDataInvioVerContDa(Date dataInvioVerContDa) {
		this.dataInvioVerContDa = dataInvioVerContDa;
	}

	
	/**
	 * @return the dataInvioVerContA
	 */
	public Date getDataInvioVerContA() {
		return dataInvioVerContA;
	}

	
	/**
	 * @param dataInvioVerContA the dataInvioVerContA to set
	 */
	public void setDataInvioVerContA(Date dataInvioVerContA) {
		this.dataInvioVerContA = dataInvioVerContA;
	}

	
	/**
	 * @return the esito
	 */
	public String getEsito() {
		return esito;
	}

	
	/**
	 * @param esito the esito to set
	 */
	public void setEsito(String esito) {
		this.esito = esito;
	}

}
