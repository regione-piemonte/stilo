/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "T_EMAIL_COMPRESSIONE")
public class TEmailCompressione implements java.io.Serializable {

	private static final long serialVersionUID = 4048571548321783404L;
	private String idEmail;
	private TEmailMgo TEmailMgo;
	private String uriEmail;
	private Date tsIns;
	private String azioneErrore;
	private Byte nroTentativi;
	private Date tsUltimoTentativo;
	private String ultimoErrore;

	public TEmailCompressione() {
	}

	public TEmailCompressione(TEmailMgo TEmailMgo, String uriEmail, Date tsIns) {
		this.TEmailMgo = TEmailMgo;
		this.uriEmail = uriEmail;
		this.tsIns = tsIns;
	}

	public TEmailCompressione(TEmailMgo TEmailMgo, String uriEmail, Date tsIns, String azioneErrore, Byte nroTentativi,
			Date tsUltimoTentativo, String ultimoErrore) {
		this.TEmailMgo = TEmailMgo;
		this.uriEmail = uriEmail;
		this.tsIns = tsIns;
		this.azioneErrore = azioneErrore;
		this.nroTentativi = nroTentativi;
		this.tsUltimoTentativo = tsUltimoTentativo;
		this.ultimoErrore = ultimoErrore;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "TEmailMgo"))
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "ID_EMAIL", unique = true, nullable = false, length = 64)
	public String getIdEmail() {
		return this.idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public TEmailMgo getTEmailMgo() {
		return this.TEmailMgo;
	}

	public void setTEmailMgo(TEmailMgo TEmailMgo) {
		this.TEmailMgo = TEmailMgo;
	}

	@Column(name = "URI_EMAIL", nullable = false, length = 2000)
	public String getUriEmail() {
		return this.uriEmail;
	}

	public void setUriEmail(String uriEmail) {
		this.uriEmail = uriEmail;
	}

	@Column(name = "TS_INS", nullable = false)
	public Date getTsIns() {
		return this.tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
	}

	@Column(name = "AZIONE_ERRORE", length = 15)
	public String getAzioneErrore() {
		return this.azioneErrore;
	}

	public void setAzioneErrore(String azioneErrore) {
		this.azioneErrore = azioneErrore;
	}

	@Column(name = "NRO_TENTATIVI", precision = 2, scale = 0)
	public Byte getNroTentativi() {
		return this.nroTentativi;
	}

	public void setNroTentativi(Byte nroTentativi) {
		this.nroTentativi = nroTentativi;
	}

	@Column(name = "TS_ULTIMO_TENTATIVO")
	public Date getTsUltimoTentativo() {
		return this.tsUltimoTentativo;
	}

	public void setTsUltimoTentativo(Date tsUltimoTentativo) {
		this.tsUltimoTentativo = tsUltimoTentativo;
	}

	@Column(name = "ULTIMO_ERRORE")
	public String getUltimoErrore() {
		return this.ultimoErrore;
	}

	public void setUltimoErrore(String ultimoErrore) {
		this.ultimoErrore = ultimoErrore;
	}

}
