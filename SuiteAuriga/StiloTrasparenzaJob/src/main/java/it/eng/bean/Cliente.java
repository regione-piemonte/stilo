/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;

/**
 * 
 * @author denis.bragato
 *
 */
public class Cliente implements java.io.Serializable {

	private static final long serialVersionUID = -2306906309867877818L;

	private ClienteId id;

	private String codFiscale;

	private String pIva;

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
	
	private BigDecimal flgFeAttivaFtp;

	
	public Cliente() {
	}

	public Cliente(ClienteId id) {
		this.id = id;
	}

	public ClienteId getId() {
		return this.id;
	}

	public void setId(ClienteId id) {
		this.id = id;
	}

	public String getCodFiscale() {
		return this.codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getPIva() {
		return this.pIva;
	}

	public void setPIva(String pIva) {
		this.pIva = pIva;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCifratura() {
		return this.cifratura;
	}

	public void setCifratura(String cifratura) {
		this.cifratura = cifratura;
	}

	public String getChiaveCifratura() {
		return this.chiaveCifratura;
	}

	public void setChiaveCifratura(String chiaveCifratura) {
		this.chiaveCifratura = chiaveCifratura;
	}

	public String getConnToken() {
		return this.connToken;
	}

	public void setConnToken(String connToken) {
		this.connToken = connToken;
	}

	public BigDecimal getFlgNoInvioSdi() {
		return this.flgNoInvioSdi;
	}

	public void setFlgNoInvioSdi(BigDecimal flgNoInvioSdi) {
		this.flgNoInvioSdi = flgNoInvioSdi;
	}

	public BigDecimal getFlgAttivaConsFatt() {
		return this.flgAttivaConsFatt;
	}

	public void setFlgAttivaConsFatt(BigDecimal flgAttivaConsFatt) {
		this.flgAttivaConsFatt = flgAttivaConsFatt;
	}

	public String getNomeSistemaCsEsterno() {
		return this.nomeSistemaCsEsterno;
	}

	public void setNomeSistemaCsEsterno(String nomeSistemaCsEsterno) {
		this.nomeSistemaCsEsterno = nomeSistemaCsEsterno;
	}

	public String getUseridSistemaCsEsterno() {
		return this.useridSistemaCsEsterno;
	}

	public void setUseridSistemaCsEsterno(String useridSistemaCsEsterno) {
		this.useridSistemaCsEsterno = useridSistemaCsEsterno;
	}

	public String getPasswordSistemaCsEsterno() {
		return this.passwordSistemaCsEsterno;
	}

	public void setPasswordSistemaCsEsterno(String passwordSistemaCsEsterno) {
		this.passwordSistemaCsEsterno = passwordSistemaCsEsterno;
	}

	public BigDecimal getFlgAttivaInvioFattCliente() {
		return this.flgAttivaInvioFattCliente;
	}

	public void setFlgAttivaInvioFattCliente(BigDecimal flgAttivaInvioFattCliente) {
		this.flgAttivaInvioFattCliente = flgAttivaInvioFattCliente;
	}

	public BigDecimal getFlgFeAttivaFtp() {
		return flgFeAttivaFtp;
	}
	
	public void setFlgFeAttivaFtp(BigDecimal flgFeAttivaFtp) {
		this.flgFeAttivaFtp = flgFeAttivaFtp;
	}
	
	@Override
	public String toString() {
		return String
				.format("Cliente [id=%s, codFiscale=%s, pIva=%s, userid=%s, password=%s, descrizione=%s, cifratura=%s, chiaveCifratura=%s, connToken=%s, flgNoInvioSdi=%s, flgAttivaConsFatt=%s, nomeSistemaCsEsterno=%s, useridSistemaCsEsterno=%s, passwordSistemaCsEsterno=%s, flgAttivaInvioFattCliente=%s, flgFeAttivaFtp=%s]",
						id, codFiscale, pIva, userid, password, descrizione, cifratura, chiaveCifratura, connToken, flgNoInvioSdi,
						flgAttivaConsFatt, nomeSistemaCsEsterno, useridSistemaCsEsterno, passwordSistemaCsEsterno,
						flgAttivaInvioFattCliente, flgFeAttivaFtp);
	}


}
