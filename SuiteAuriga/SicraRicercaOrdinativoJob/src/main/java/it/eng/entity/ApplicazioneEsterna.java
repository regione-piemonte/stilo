/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 * 
 * @author denis.bragato
 *
 */
@Entity
@NamedNativeQueries(value = {
	@NamedNativeQuery(
		name = "getApplicazioneEsterna",
		query = "SELECT ae.ID_SP_AOO,"
				+ "     ae.CI_APPLICAZIONE,"
				+ "     AE.CI_ISTANZA_APPLICAZIONE,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'COD_FISCALE') COD_FISCALE,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'PIVA') PIVA,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'USERID') USERID,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'PASSWORD') PASSWORD,"
				+ "     CASE ae.DESCRIZIONE"
				+ "        WHEN ae.CI_APPLICAZIONE || ' - ' || ae.CI_ISTANZA_APPLICAZIONE"
				+ "        THEN"
				+ "           (SELECT r.DENOMINAZIONE"
				+ "              FROM DMT_RUBRICA_SOGGETTI r, DMT_SOGGETTI_PROD_AOO sp"
				+ "             WHERE     ae.ID_SP_AOO = sp.ID_SP_AOO"
				+ "                   AND sp.ID_IN_RUBRICA = r.ID_SOGG_RUBRICA)"
				+ "        ELSE"
				+ "           ae.DESCRIZIONE"
				+ "     END DESCRIZIONE,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'CIFRATURA') CIFRATURA,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'CHIAVE_CIFRATURA') CHIAVE_CIFRATURA,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'CONN_TOKEN') CONN_TOKEN,"
				+ "     (SELECT COUNT (ntaa.NUM_VALUE)"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE     ntaa.ATTR_NAME = 'FLG_NO_INVIO_SDI') FLG_NO_INVIO_SDI,"
				+ "     (SELECT COUNT (ntaa.NUM_VALUE)"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE     ntaa.ATTR_NAME = 'FLG_ATTIVA_CONS_FATT') FLG_ATTIVA_CONS_FATT,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'NOME_SISTEMA_CS_ESTERNO') NOME_SISTEMA_CS_ESTERNO,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'USERID_SISTEMA_CS_ESTERNO') USERID_SISTEMA_CS_ESTERNO,"
				+ "     (SELECT ntaa.STR_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'PASSWORD_SISTEMA_CS_ESTERNO') PASSWORD_SISTEMA_CS_ESTERNO,"
				+ "     (SELECT ntaa.NUM_VALUE"
				+ "        FROM TABLE (ae.ALTRI_ATTRIBUTI) ntaa"
				+ "       WHERE ntaa.ATTR_NAME = 'FLG_ATTIVA_INVIO_FATT_CLIENTE') FLG_ATTIVA_INVIO_FATT_CLIENTE"
				+ "  FROM DMT_APPLICAZIONI_ESTERNE ae"
				+ " WHERE ae.CI_APPLICAZIONE = :ciApplicazione"
				+ "     AND AE.CI_ISTANZA_APPLICAZIONE = :ciIstanzaApplicazione",
		resultSetMapping = "ApplicazioneEsternaResults"
	)
})
@SqlResultSetMapping(
	name = "ApplicazioneEsternaResults", 
	entities = { 
		@EntityResult(
			entityClass = ApplicazioneEsterna.class, 
			fields = {
				@FieldResult(name = "id.idSpAoo", column = "ID_SP_AOO"), 
				@FieldResult(name = "id.ciApplicazione", column = "CI_APPLICAZIONE"),
				@FieldResult(name = "id.ciIstanzaApplicazione", column = "CI_ISTANZA_APPLICAZIONE"),
				@FieldResult(name = "codFiscale", column = "COD_FISCALE"),
				@FieldResult(name = "piva", column = "PIVA"),
				@FieldResult(name = "userid", column = "USERID"), 
				@FieldResult(name = "password", column = "PASSWORD"),
				@FieldResult(name = "descrizione", column = "DESCRIZIONE"), 
				@FieldResult(name = "cifratura", column = "CIFRATURA"),
				@FieldResult(name = "chiaveCifratura", column = "CHIAVE_CIFRATURA"), 
				@FieldResult(name = "connToken", column = "CONN_TOKEN"),
				@FieldResult(name = "flgNoInvioSdi", column = "FLG_NO_INVIO_SDI"), 
				@FieldResult(name = "flgAttivaConsFatt", column = "FLG_ATTIVA_CONS_FATT"),
				@FieldResult(name = "nomeSistemaCsEsterno", column = "NOME_SISTEMA_CS_ESTERNO"), 
				@FieldResult(name = "useridSistemaCsEsterno", column = "USERID_SISTEMA_CS_ESTERNO"),
				@FieldResult(name = "passwordSistemaCsEsterno", column = "PASSWORD_SISTEMA_CS_ESTERNO"), 
				@FieldResult(name = "flgAttivaInvioFattCliente", column = "FLG_ATTIVA_INVIO_FATT_CLIENTE") 
			}
		)
	}
)
public class ApplicazioneEsterna implements java.io.Serializable {

	private static final long serialVersionUID = -2306906309867877818L;

	private ApplicazioneEsternaId id;

	private String codFiscale;

	private String piva;

	private String userid;

	private String password;

	private String descrizione;

	private String cifratura;

	private String chiaveCifratura;

	private String connToken;

	private BigDecimal flgNoInvioSdi;

	private BigDecimal flgAttivaConsFatt;

	private String nomeSistemaCsEsterno;

	private String useridSistemaCsEsterno;

	private String passwordSistemaCsEsterno;

	private BigDecimal flgAttivaInvioFattCliente;

	
	public ApplicazioneEsterna() {
	}

	public ApplicazioneEsterna(ApplicazioneEsternaId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "idSpAoo", column = @Column(name = "ID_SP_AOO", nullable = false, precision = 22, scale = 0)),
			@AttributeOverride(name = "ciApplicazione", column = @Column(name = "CI_APPLICAZIONE", nullable = false, length = 30)),
			@AttributeOverride(name = "ciIstanzaApplicazione", column = @Column(name = "CI_ISTANZA_APPLICAZIONE", length = 30)) })
	public ApplicazioneEsternaId getId() {
		return this.id;
	}

	public void setId(ApplicazioneEsternaId id) {
		this.id = id;
	}

	//@Column(name = "COD_FISCALE", length = 4000)
	public String getCodFiscale() {
		return this.codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	//@Column(name = "PIVA", length = 4000)
	public String getPiva() {
		return this.piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	//@Column(name = "USERID", length = 4000)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	//@Column(name = "PASSWORD", length = 4000)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//@Column(name = "DESCRIZIONE", length = 1000)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	//@Column(name = "CIFRATURA", length = 4000)
	public String getCifratura() {
		return this.cifratura;
	}

	public void setCifratura(String cifratura) {
		this.cifratura = cifratura;
	}

	//@Column(name = "CHIAVE_CIFRATURA", length = 4000)
	public String getChiaveCifratura() {
		return this.chiaveCifratura;
	}

	public void setChiaveCifratura(String chiaveCifratura) {
		this.chiaveCifratura = chiaveCifratura;
	}

	//@Column(name = "CONN_TOKEN", length = 4000)
	public String getConnToken() {
		return this.connToken;
	}

	public void setConnToken(String connToken) {
		this.connToken = connToken;
	}

	//@Column(name = "FLG_NO_INVIO_SDI", precision = 22, scale = 0)
	public BigDecimal getFlgNoInvioSdi() {
		return this.flgNoInvioSdi;
	}

	public void setFlgNoInvioSdi(BigDecimal flgNoInvioSdi) {
		this.flgNoInvioSdi = flgNoInvioSdi;
	}

	//@Column(name = "FLG_ATTIVA_CONS_FATT", precision = 22, scale = 0)
	public BigDecimal getFlgAttivaConsFatt() {
		return this.flgAttivaConsFatt;
	}

	public void setFlgAttivaConsFatt(BigDecimal flgAttivaConsFatt) {
		this.flgAttivaConsFatt = flgAttivaConsFatt;
	}

	//@Column(name = "NOME_SISTEMA_CS_ESTERNO", length = 4000)
	public String getNomeSistemaCsEsterno() {
		return this.nomeSistemaCsEsterno;
	}

	public void setNomeSistemaCsEsterno(String nomeSistemaCsEsterno) {
		this.nomeSistemaCsEsterno = nomeSistemaCsEsterno;
	}

	//@Column(name = "USERID_SISTEMA_CS_ESTERNO", length = 4000)
	public String getUseridSistemaCsEsterno() {
		return this.useridSistemaCsEsterno;
	}

	public void setUseridSistemaCsEsterno(String useridSistemaCsEsterno) {
		this.useridSistemaCsEsterno = useridSistemaCsEsterno;
	}

	//@Column(name = "PASSWORD_SISTEMA_CS_ESTERNO", length = 4000)
	public String getPasswordSistemaCsEsterno() {
		return this.passwordSistemaCsEsterno;
	}

	public void setPasswordSistemaCsEsterno(String passwordSistemaCsEsterno) {
		this.passwordSistemaCsEsterno = passwordSistemaCsEsterno;
	}

	//@Column(name = "FLG_ATTIVA_INVIO_FATT_CLIENTE", precision = 22, scale = 0)
	public BigDecimal getFlgAttivaInvioFattCliente() {
		return this.flgAttivaInvioFattCliente;
	}

	public void setFlgAttivaInvioFattCliente(BigDecimal flgAttivaInvioFattCliente) {
		this.flgAttivaInvioFattCliente = flgAttivaInvioFattCliente;
	}

	
	@Override
	public String toString() {
		return String
				.format("ApplicazioneEsterna [id=%s, codFiscale=%s, pIva=%s, userid=%s, password=%s, descrizione=%s, cifratura=%s, chiaveCifratura=%s, connToken=%s, flgNoInvioSdi=%s, flgAttivaConsFatt=%s, nomeSistemaCsEsterno=%s, useridSistemaCsEsterno=%s, passwordSistemaCsEsterno=%s, flgAttivaInvioFattCliente=%s]",
						id, codFiscale, piva, userid, password, descrizione, cifratura, chiaveCifratura, connToken, flgNoInvioSdi,
						flgAttivaConsFatt, nomeSistemaCsEsterno, useridSistemaCsEsterno, passwordSistemaCsEsterno,
						flgAttivaInvioFattCliente);
	}


}
