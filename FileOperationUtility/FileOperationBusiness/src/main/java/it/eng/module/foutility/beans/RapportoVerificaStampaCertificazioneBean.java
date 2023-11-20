/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.List;

import it.eng.module.foutility.beans.generated.SignerInformationType;
import it.eng.module.foutility.beans.generated.SignerInformationType.Marca;



/**
 * 
 * @author dbe4235
 *
 */

public class RapportoVerificaStampaCertificazioneBean {

	private String nomeFileCertificato;
	private String encode;
	private String impronta;
	private String algoritmo;
	private String encoding;
	private String certificatoIntegro;
	private String codiceEseguibile;
	private List<RapportoVerificaFirmatarioExtBean> firmatari;
	private Date dataOraMarcaTemporale;
	private String tipoMarcaTemporale;
	private String infoMarcaTemporale;
	private boolean marcaTemporaleNonValida;
	private Date dataOraVerificaMarcaTemporale;
	private String tsaName;
	private boolean esitoVerificaFirmaBustaEsterna;
	
	public String getNomeFileCertificato() {
		return nomeFileCertificato;
	}

	public void setNomeFileCertificato(String nomeFileCertificato) {
		this.nomeFileCertificato = nomeFileCertificato;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	public String getImpronta() {
		return impronta;
	}

	public void setImpronta(String impronta) {
		this.impronta = impronta;
	}
	
	public String getAlgoritmo() {
		return algoritmo;
	}

	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public String getCertificatoIntegro() {
		return certificatoIntegro;
	}

	public void setCertificatoIntegro(String certificatoIntegro) {
		this.certificatoIntegro = certificatoIntegro;
	}

	public List<RapportoVerificaFirmatarioExtBean> getFirmatari() {
		return firmatari;
	}

	public void setFirmatari(List<RapportoVerificaFirmatarioExtBean> firmatari) {
		this.firmatari = firmatari;
	}

	public String getCodiceEseguibile() {
		return codiceEseguibile;
	}

	public void setCodiceEseguibile(String codiceEseguibile) {
		this.codiceEseguibile = codiceEseguibile;
	}
	
	public Date getDataOraMarcaTemporale() {
		return dataOraMarcaTemporale;
	}

	public void setDataOraMarcaTemporale(Date dataOraMarcaTemporale) {
		this.dataOraMarcaTemporale = dataOraMarcaTemporale;
	}
	
	public String getTipoMarcaTemporale() {
		return tipoMarcaTemporale;
	}

	public void setTipoMarcaTemporale(String tipoMarcaTemporale) {
		this.tipoMarcaTemporale = tipoMarcaTemporale;
	}

	public String getInfoMarcaTemporale() {
		return infoMarcaTemporale;
	}
	
	public void setInfoMarcaTemporale(String infoMarcaTemporale) {
		this.infoMarcaTemporale = infoMarcaTemporale;
	}
	
	public boolean isMarcaTemporaleNonValida() {
		return marcaTemporaleNonValida;
	}
	
	public void setMarcaTemporaleNonValida(boolean marcaTemporaleNonValida) {
		this.marcaTemporaleNonValida = marcaTemporaleNonValida;
	}
	
	public Date getDataOraVerificaMarcaTemporale() {
		return dataOraVerificaMarcaTemporale;
	}
	
	public void setDataOraVerificaMarcaTemporale(Date dataOraVerificaMarcaTemporale) {
		this.dataOraVerificaMarcaTemporale = dataOraVerificaMarcaTemporale;
	}
	
	public String getTsaName() {
		return tsaName;
	}

	public void setTsaName(String tsaName) {
		this.tsaName = tsaName;
	}

	public boolean isEsitoVerificaFirmaBustaEsterna() {
		return esitoVerificaFirmaBustaEsterna;
	}

	public void setEsitoVerificaFirmaBustaEsterna(boolean esitoVerificaFirmaBustaEsterna) {
		this.esitoVerificaFirmaBustaEsterna = esitoVerificaFirmaBustaEsterna;
	}

	public class RapportoVerificaFirmatarioExtBean {
		
		private String subject;
		private String id;
		private String figlioDi;		
		private String enteCertificatore;
		private String dataScadenza;
		private String dataEmissione;
		private String statoCertificato;
		private String numeroCertificato;
		private String serialNumber;
		private String tipoFirmaQA; 
		private String[] qcStatements;
		private String[] keyUsages;
		private String verificationStatus;
		private String certExpiration;
		private String crlResult;
		private String caReliability;
		private boolean firmaValida;
		private Marca marca;
		private SignerInformationType controFirma;
		
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getFiglioDi() {
			return figlioDi;
		}
		public void setFiglioDi(String figlioDi) {
			this.figlioDi = figlioDi;
		}
		public boolean isFirmaValida() {
			return firmaValida;
		}
		public void setFirmaValida(boolean firmaValida) {
			this.firmaValida = firmaValida;
		}
		public String getVerificationStatus() {
			return verificationStatus;
		}
		public void setVerificationStatus(String verificationStatus) {
			this.verificationStatus = verificationStatus;
		}
		public String getCertExpiration() {
			return certExpiration;
		}
		public void setCertExpiration(String certExpiration) {
			this.certExpiration = certExpiration;
		}
		public String getCrlResult() {
			return crlResult;
		}
		public void setCrlResult(String crlResult) {
			this.crlResult = crlResult;
		}
		public String getCaReliability() {
			return caReliability;
		}
		public void setCaReliability(String caReliability) {
			this.caReliability = caReliability;
		}
		public String getEnteCertificatore() {
			return enteCertificatore;
		}
		public void setEnteCertificatore(String enteCertificatore) {
			this.enteCertificatore = enteCertificatore;
		}
		public String getDataScadenza() {
			return dataScadenza;
		}
		public void setDataScadenza(String dataScadenza) {
			this.dataScadenza = dataScadenza;
		}
		public String getDataEmissione() {
			return dataEmissione;
		}
		public void setDataEmissione(String dataEmissione) {
			this.dataEmissione = dataEmissione;
		}
		public String getStatoCertificato() {
			return statoCertificato;
		}
		public void setStatoCertificato(String statoCertificato) {
			this.statoCertificato = statoCertificato;
		}
		public String getNumeroCertificato() {
			return numeroCertificato;
		}
		public void setNumeroCertificato(String numeroCertificato) {
			this.numeroCertificato = numeroCertificato;
		}
		public String getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}
		public String[] getQcStatements() {
			return qcStatements;
		}
		public void setQcStatements(String[] qcStatements) {
			this.qcStatements = qcStatements;
		}
		public String[] getKeyUsages() {
			return keyUsages;
		}
		public void setKeyUsages(String[] keyUsages) {
			this.keyUsages = keyUsages;
		}
		public Marca getMarca() {
			return marca;
		}
		public void setMarca(Marca marca) {
			this.marca = marca;
		}
		public SignerInformationType getControFirma() {
			return controFirma;
		}
		public void setControFirma(SignerInformationType controFirma) {
			this.controFirma = controFirma;
		}
		public String getTipoFirmaQA() {
			return tipoFirmaQA;
		}
		public void setTipoFirmaQA(String tipoFirmaQA) {
			this.tipoFirmaQA = tipoFirmaQA;
		}
		
	}

}