/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "recuperaPerUri", query = "from TCompDigitali t where upper(t.uri) like upper(:uri)"), 
	@NamedQuery(name = "recuperaConReplyPerUri", query = "from TCompDigitali as t inner join fetch t.TCompDigitReplies where upper(t.uri) like upper(:uri)") 
})
@Table(name = "T_COMP_DIGITALI")
public class TCompDigitali implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id	@Column(name = "ID_CD") 
	private String idCd;
	public String getIdCd() { return this.idCd;	}
	public void setIdCd(String idCd) { this.idCd = idCd; }

	@Basic(optional=false)
	private String crc;
	public String getCrc() { return this.crc; }
	public void setCrc(String crc) { this.crc = crc; }

	@Basic(optional=false)
	private String digest;
	public String getDigest() {	return this.digest;	}
	public void setDigest(String digest) { this.digest = digest; }

	@Basic(optional=false) @Column(name = "DIGEST_ENCODING", nullable=false)
	private String digestEncoding;
	public String getDigestEncoding() {	return this.digestEncoding;	}
	public void setDigestEncoding(String digestEncoding) { this.digestEncoding = digestEncoding;	}

	@Basic(optional=false)
	private BigDecimal dimensione;
	public BigDecimal getDimensione() {	return this.dimensione;	}
	public void setDimensione(BigDecimal dimensione) { this.dimensione = dimensione;	}

	@Basic(optional=false) @Column(name = "FLG_ANN", nullable=false)
	private BigDecimal flgAnn;
	public BigDecimal getFlgAnn() { return this.flgAnn;	}
	public void setFlgAnn(BigDecimal flgAnn) { this.flgAnn = flgAnn; }

	@Column(name = "ID_APPL_INS")
	private String idApplIns;
	public String getIdApplIns() { return this.idApplIns; }
	public void setIdApplIns(String idApplIns) { this.idApplIns = idApplIns; }

	@Column(name = "ID_APPL_ULTIMO_AGG")
	private String idApplUltimoAgg;
	public String getIdApplUltimoAgg() { return this.idApplUltimoAgg; }
	public void setIdApplUltimoAgg(String idApplUltimoAgg) { this.idApplUltimoAgg = idApplUltimoAgg; }

	@Basic(optional=false) @Column(name = "ID_DIG_FORMAT", nullable=false)
	private String idDigFormat;
	public String getIdDigFormat() { return this.idDigFormat; }
	public void setIdDigFormat(String idDigFormat) { this.idDigFormat = idDigFormat; }

	@Basic(optional=false) @Column(name = "ID_REC_DIZ_ALGORITMO_CRC", nullable=false)
	private String idRecDizAlgoritmoCrc;
	public String getIdRecDizAlgoritmoCrc() { return this.idRecDizAlgoritmoCrc; }
	public void setIdRecDizAlgoritmoCrc(String idRecDizAlgoritmoCrc) { this.idRecDizAlgoritmoCrc = idRecDizAlgoritmoCrc; }

	@Basic(optional=false) @Column(name = "ID_REC_DIZ_ALGORITMO_DIGEST", nullable=false)
	private String idRecDizAlgoritmoDigest;
	public String getIdRecDizAlgoritmoDigest() { return this.idRecDizAlgoritmoDigest; }
	public void setIdRecDizAlgoritmoDigest(String idRecDizAlgoritmoDigest) { this.idRecDizAlgoritmoDigest = idRecDizAlgoritmoDigest; }

	@Column(name = "ID_REC_DIZ_BYTE_ORDER")
	private String idRecDizByteOrder;
	public String getIdRecDizByteOrder() { return this.idRecDizByteOrder; }
	public void setIdRecDizByteOrder(String idRecDizByteOrder) { this.idRecDizByteOrder = idRecDizByteOrder; }

	@Column(name = "ID_REC_DIZ_COMPR_SCHEME")
	private String idRecDizComprScheme;
	public String getIdRecDizComprScheme() { return this.idRecDizComprScheme; }
	public void setIdRecDizComprScheme(String idRecDizComprScheme) { this.idRecDizComprScheme = idRecDizComprScheme; }

	@Column(name = "ID_REC_DIZ_TIPO_BUSTA_CRITT")
	private String idRecDizTipoBustaCritt;
	public String getIdRecDizTipoBustaCritt() {	return this.idRecDizTipoBustaCritt;	}
	public void setIdRecDizTipoBustaCritt(String idRecDizTipoBustaCritt) { this.idRecDizTipoBustaCritt = idRecDizTipoBustaCritt; }

	@Basic(optional=false) @Column(name = "ID_SOGG_VERS", nullable=false) 
	private String idSoggVers;
	public String getIdSoggVers() {	return this.idSoggVers;	}
	public void setIdSoggVers(String idSoggVers) { this.idSoggVers = idSoggVers; }

	@Column(name = "ID_UTE_INS")
	private String idUteIns;
	public String getIdUteIns() { return this.idUteIns; }
	public void setIdUteIns(String idUteIns) { this.idUteIns = idUteIns; }

	@Column(name = "ID_UTE_ULTIMO_AGG")
	private String idUteUltimoAgg;
	public String getIdUteUltimoAgg() {	return this.idUteUltimoAgg;	}
	public void setIdUteUltimoAgg(String idUteUltimoAgg) { this.idUteUltimoAgg = idUteUltimoAgg; }

	@Basic(optional=false) @Column(name = "NOME_FILE", nullable=false)
	private String nomeFile;
	public String getNomeFile() { return this.nomeFile;	}
	public void setNomeFile(String nomeFile) { this.nomeFile = nomeFile; }

	@Basic(optional=false) @Column(name = "TS_INS", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date tsIns;
	public Date getTsIns() { return this.tsIns; }
	public void setTsIns(Date tsIns) {	this.tsIns = tsIns;	}

	@Basic(optional=false) @Column(name = "TS_ULTIMO_AGG", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date tsUltimoAgg;
	public Date getTsUltimoAgg() { return this.tsUltimoAgg; }
	public void setTsUltimoAgg(Date tsUltimoAgg) { this.tsUltimoAgg = tsUltimoAgg;	}

	@Basic(optional=false) @Column(unique=true, nullable=false)
	private String uri;
	public String getUri() { return this.uri; }
	public void setUri(String uri) { this.uri = uri; }
	
	// bi-directional many-to-one association to TCompDigitReply
	@OneToMany(mappedBy = "TCompDigitali"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
	private Set<TCompDigitReply> TCompDigitReplies;
	public Set<TCompDigitReply> getTCompDigitReplies() { return this.TCompDigitReplies;	}
	public void setTCompDigitReplies(Set<TCompDigitReply> TCompDigitReplies) { 
		this.TCompDigitReplies = TCompDigitReplies; 
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("[idCd=");
		builder.append(idCd);
		builder.append(", uri=");
		builder.append(uri);
		builder.append(", crc=");
		builder.append(crc);
		builder.append(", digest=");
		builder.append(digest);
		builder.append(", digestEncoding=");
		builder.append(digestEncoding);
		builder.append(", dimensione=");
		builder.append(dimensione);
		builder.append(", flgAnn=");
		builder.append(flgAnn);
		builder.append(", idApplIns=");
		builder.append(idApplIns);
		builder.append(", idApplUltimoAgg=");
		builder.append(idApplUltimoAgg);
		builder.append(", idDigFormat=");
		builder.append(idDigFormat);
		builder.append(", idRecDizAlgoritmoCrc=");
		builder.append(idRecDizAlgoritmoCrc);
		builder.append(", idRecDizAlgoritmoDigest=");
		builder.append(idRecDizAlgoritmoDigest);
		builder.append(", idRecDizByteOrder=");
		builder.append(idRecDizByteOrder);
		builder.append(", idRecDizComprScheme=");
		builder.append(idRecDizComprScheme);
		builder.append(", idRecDizTipoBustaCritt=");
		builder.append(idRecDizTipoBustaCritt);
		builder.append(", idSoggVers=");
		builder.append(idSoggVers);
		builder.append(", idUteIns=");
		builder.append(idUteIns);
		builder.append(", idUteUltimoAgg=");
		builder.append(idUteUltimoAgg);
		builder.append(", nomeFile=");
		builder.append(nomeFile);
		builder.append(", tsIns=");
		builder.append(tsIns);
		builder.append(", tsUltimoAgg=");
		builder.append(tsUltimoAgg);
		builder.append("]");
		return builder.toString();
	}

}