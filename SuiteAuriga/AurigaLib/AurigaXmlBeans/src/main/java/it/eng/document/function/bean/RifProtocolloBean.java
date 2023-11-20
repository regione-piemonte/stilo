/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;



public class RifProtocolloBean {

	   @NumeroColonna(numero="1")
	   private String categoria;
	   
	   @NumeroColonna(numero="2")
	   private String sigla;
	   
	   @NumeroColonna(numero="3")
	   private String anno;
	   
	   @NumeroColonna(numero="4")
	   private String numero;

	   @NumeroColonna(numero="5")
	   @TipoData(tipo=Tipo.DATA_SENZA_ORA)
	   private Date    dataProtocollo;

		
	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Date getDataProtocollo() {
		return dataProtocollo;
	}

	public void setDataProtocollo(Date dataProtocollo) {
		this.dataProtocollo = dataProtocollo;
	}	   
	
	}
