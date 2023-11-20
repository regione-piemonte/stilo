/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the DMT_REL_DESTINATARI_UO database table.
 * 
 */
@Entity
@Table(name="DMT_REL_DESTINATARI_UO")
public class DmtRelDestinatariUo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_RELAZIONE")
	private long idRelazione;

	private String canale;
	
	@Column(name="ID_CANALE")
	private BigDecimal idCanale;

	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="ID_USER")
	private BigDecimal idUser;

	@Column(name="MAIL_DEST")
	private String mailDest;

	@Column(name="MAIL_MITT")
	private String mailMitt;
	
	@Column(name="COD_DESTINATARIO")
	private String codDestinatario;
	
	@Column(name="MAIL_LANG")
	private String mailLang;
	
	@Column(name="RAG_SOC")
   	private String ragioneSociale;

	//bi-directional many-to-one association to DmtDestinatariDoc
    @ManyToOne
	@JoinColumn(name="ID_DESTINATARIO")
	private DmtDestinatariDoc dmtDestinatariDoc;

    @Column(name="ID_UO")
	private long idUo;
    
    public DmtRelDestinatariUo() {
    }

	public long getIdRelazione() {
		return this.idRelazione;
	}

	public void setIdRelazione(long idRelazione) {
		this.idRelazione = idRelazione;
	}

	public String getCanale() {
		return this.canale;
	}

	public void setCanale(String canale) {
		this.canale = canale;
	}

	public BigDecimal getFlgAnn() {
		return this.flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public BigDecimal getIdUser() {
		return this.idUser;
	}

	public void setIdUser(BigDecimal idUser) {
		this.idUser = idUser;
	}

	public String getMailDest() {
		return this.mailDest;
	}

	public void setMailDest(String mailDest) {
		this.mailDest = mailDest;
	}

	public String getMailMitt() {
		return this.mailMitt;
	}

	public void setMailMitt(String mailMitt) {
		this.mailMitt = mailMitt;
	}

	public DmtDestinatariDoc getDmtDestinatariDoc() {
		return this.dmtDestinatariDoc;
	}

	public void setDmtDestinatariDoc(DmtDestinatariDoc dmtDestinatariDoc) {
		this.dmtDestinatariDoc = dmtDestinatariDoc;
	}

	public long getIdUo() {
		return idUo;
	}

	public void setIdUo(long idUo) {
		this.idUo = idUo;
	}

	public String getCodDestinatario() {
		return codDestinatario;
	}

	public void setCodDestinatario(String codDestinatario) {
		this.codDestinatario = codDestinatario;
	}

	public BigDecimal getIdCanale() {
		return idCanale;
	}

	public void setIdCanale(BigDecimal idCanale) {
		this.idCanale = idCanale;
	}

	public String getMailLang() {
		return mailLang;
	}

	public void setMailLang(String mailLang) {
		this.mailLang = mailLang;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	
}