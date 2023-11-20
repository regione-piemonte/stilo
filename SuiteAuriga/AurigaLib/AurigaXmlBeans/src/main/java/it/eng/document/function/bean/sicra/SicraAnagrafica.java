/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraAnagrafica implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger pkid;
	private String ragSociale;
	private String cf;
	private String piva;
	private SicraResidenza residenza;
	private SicraRifImport rifImport;
	private BigInteger daBonificare;
	private BigInteger nominativoFalso;
	private BigInteger irriconoscibile;
	private Calendar dataFineAttivita;
	private String nome;
	private String cognome;
	private List<SicraFormaPagamento> formePagamento;
	
	public BigInteger getPkid() {
		return pkid;
	}
	
	public void setPkid(BigInteger pkid) {
		this.pkid = pkid;
	}
	
	public String getRagSociale() {
		return ragSociale;
	}
	
	public void setRagSociale(String ragSociale) {
		this.ragSociale = ragSociale;
	}
	
	public String getCf() {
		return cf;
	}
	
	public void setCf(String cf) {
		this.cf = cf;
	}
	
	public String getPiva() {
		return piva;
	}
	
	public void setPiva(String piva) {
		this.piva = piva;
	}
	
	public SicraResidenza getResidenza() {
		return residenza;
	}
	
	public void setResidenza(SicraResidenza residenza) {
		this.residenza = residenza;
	}
	
	public SicraRifImport getRifImport() {
		return rifImport;
	}
	
	public void setRifImport(SicraRifImport rifImport) {
		this.rifImport = rifImport;
	}
	
	public BigInteger getDaBonificare() {
		return daBonificare;
	}
	
	public void setDaBonificare(BigInteger daBonificare) {
		this.daBonificare = daBonificare;
	}
	
	public BigInteger getNominativoFalso() {
		return nominativoFalso;
	}
	
	public void setNominativoFalso(BigInteger nominativoFalso) {
		this.nominativoFalso = nominativoFalso;
	}
	
	public BigInteger getIrriconoscibile() {
		return irriconoscibile;
	}
	
	public void setIrriconoscibile(BigInteger irriconoscibile) {
		this.irriconoscibile = irriconoscibile;
	}
	
	public Calendar getDataFineAttivita() {
		return dataFineAttivita;
	}
	
	public void setDataFineAttivita(Calendar dataFineAttivita) {
		this.dataFineAttivita = dataFineAttivita;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public List<SicraFormaPagamento> getFormePagamento() {
		return formePagamento;
	}
	
	public void setFormePagamento(List<SicraFormaPagamento> formePagamento) {
		this.formePagamento = formePagamento;
	}
	
}
