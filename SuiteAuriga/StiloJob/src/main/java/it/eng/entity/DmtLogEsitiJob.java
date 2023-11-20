/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */



import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "DMT_LOG_ESITI_JOB")
public class DmtLogEsitiJob implements java.io.Serializable {

	private static final long serialVersionUID = 5519033433460081362L;
 
	private BigDecimal idCaricamento;
    
	private BigDecimal idSpAoo;
	
	private String codApplOwner;
	
	private String idLotto;

	private String nomeFileCreato;

    private String stato;
    
    private Date tsElaborazione;
    
	private Date tsIns;

	private BigDecimal idUserIns;

	private Date tsLastUpd;

	private BigDecimal idUserLastUpd;


	public DmtLogEsitiJob() {
	}
    
	
    
	public DmtLogEsitiJob(BigDecimal idCaricamento) {
		super();
		this.idCaricamento = idCaricamento;
	}



	public DmtLogEsitiJob(BigDecimal idCaricamento, BigDecimal idSpAoo, String codApplOwner, String idLotto,
			String nomeFileCreato, String stato, Date tsElaborazione, Date tsIns, BigDecimal idUserIns, Date tsLastUpd,
			BigDecimal idUserLastUpd) {
		super();
		this.idCaricamento = idCaricamento;
		this.idSpAoo = idSpAoo;
		this.codApplOwner = codApplOwner;
		this.idLotto = idLotto;
		this.nomeFileCreato = nomeFileCreato;
		this.stato = stato;
		this.tsElaborazione = tsElaborazione;
		this.tsIns = tsIns;
		this.idUserIns = idUserIns;
		this.tsLastUpd = tsLastUpd;
		this.idUserLastUpd = idUserLastUpd;
	}

    

	@Override
	public String toString() {
		return "DmtLogEsitiJob [idCaricamento=" + idCaricamento + ", idSpAoo=" + idSpAoo + ", codApplOwner="
				+ codApplOwner + ", idLotto=" + idLotto + ", nomeFileCreato=" + nomeFileCreato + ", stato=" + stato
				+ ", tsElaborazione=" + tsElaborazione + ", tsIns=" + tsIns + ", idUserIns=" + idUserIns
				+ ", tsLastUpd=" + tsLastUpd + ", idUserLastUpd=" + idUserLastUpd + "]";
	}



	@Id
	@Column(name = "ID_CARICAMENTO", unique = true, nullable = false)
	public BigDecimal getIdCaricamento() {
		return idCaricamento;
	}



	public void setIdCaricamento(BigDecimal idCaricamento) {
		this.idCaricamento = idCaricamento;
	}


	@Column(name = "NOME_FILE_CREATO", length = 4000)
	public String getNomeFileCreato() {
		return nomeFileCreato;
	}



	public void setNomeFileCreato(String nomeFileCreato) {
		this.nomeFileCreato = nomeFileCreato;
	}


	@Column(name = "STATO", length = 50)
	public String getStato() {
		return stato;
	}

    public void setStato(String stato) {
		this.stato = stato;
	}

    @Column(name = "ID_LOTTO", unique = true, nullable = false, length = 64)
	public String getIdLotto() {
		return this.idLotto;
	}

	public void setIdLotto(String idLotto) {
		this.idLotto = idLotto;
	}

	@Column(name = "ID_SP_AOO", precision = 22, scale = 0)
	public BigDecimal getIdSpAoo() {
		return this.idSpAoo;
	}

	public void setIdSpAoo(BigDecimal idSpAoo) {
		this.idSpAoo = idSpAoo;
	}

	@Column(name = "COD_APPL_OWNER", length = 30)
	public String getCodApplOwner() {
		return this.codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

    @Column(name = "TS_INS", length = 7)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "ID_USER_INS", precision = 22, scale = 0)
	public BigDecimal getIdUserIns() {
		return this.idUserIns;
	}

	public void setIdUserIns(BigDecimal idUserIns) {
		this.idUserIns = idUserIns;
	}

	@Column(name = "TS_LAST_UPD", length = 7)
	public Date getTsLastUpd() {
		return this.tsLastUpd;
	}

	public void setTsLastUpd(Date tsLastUpd) {
		this.tsLastUpd = tsLastUpd;
	}

	@Column(name = "ID_USER_LAST_UPD", precision = 22, scale = 0)
	public BigDecimal getIdUserLastUpd() {
		return this.idUserLastUpd;
	}

	public void setIdUserLastUpd(BigDecimal idUserLastUpd) {
		this.idUserLastUpd = idUserLastUpd;
	}

	@Column(name = "TS_ELABORAZIONE", length = 7)
	public Date getTsElaborazione() {
		return tsElaborazione;
	}


	public void setTsElaborazione(Date tsElaborazione) {
		this.tsElaborazione = tsElaborazione;
	}

	

}
