/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.module.foutility.beans.OutputOperations;
import it.eng.module.foutility.beans.RapportoVerificaStampaCertificazioneBean;
import it.eng.module.foutility.beans.RapportoVerificaStampaCertificazioneBean.RapportoVerificaFirmatarioExtBean;
import it.eng.module.foutility.beans.generated.AbstractInputOperationType;
import it.eng.module.foutility.beans.generated.DigestAlgID;
import it.eng.module.foutility.beans.generated.DigestEncID;
import it.eng.module.foutility.beans.generated.DnType;
import it.eng.module.foutility.beans.generated.InputRapportoVerificaType;
import it.eng.module.foutility.beans.generated.ResponseRapportoVerificaType;
import it.eng.module.foutility.beans.generated.SigVerifyResultType;
import it.eng.module.foutility.beans.generated.SigVerifyResultType.SigVerifyResult;
import it.eng.module.foutility.beans.generated.SignerInformationType;
import it.eng.module.foutility.beans.generated.VerificationStatusType;
import it.eng.module.foutility.util.FileOpMessage;


/**
 * 
 * @author dbe4235
 *
 * Servizio che permette la stampa del rapporto di verifica di un file firmato
 */

public class RapportoVerificaCtrl extends AbstractFileController {

	public static final  Logger log = LogManager.getLogger( RapportoVerificaCtrl.class );
	// chiave identificativa della operazione
	public static final String RapportoVerificaCtrlCode = RapportoVerificaCtrl.class.getName();
	public String operationType;

	@Override
	public boolean execute(InputFileBean input, AbstractInputOperationType customInput, OutputOperations output,
			String requestKey) {
		
		boolean verify = false;
		boolean hasChild = false;
		// Viene usato per indendare i firmatari
		int livelloCorrente = 1;
		ResponseRapportoVerificaType response = new ResponseRapportoVerificaType();
		if(customInput instanceof InputRapportoVerificaType){
			
			try {
				
				File file = null;
				boolean fileTempCreated = false;
				try {
					file = File.createTempFile("export", "");
					fileTempCreated = true;
				} catch (IOException e1) {
					log.error("Errore nella creazione del file temporaneo");
					OutputOperations.addError(response, FileOpMessage.PDF_OP_ERROR, VerificationStatusType.ERROR, e1.getMessage());
				}
				
				if (fileTempCreated) {
					
					Font font_10 = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
					Font font_10Bold = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
					Font font_10BoldBlue = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.BLUE);
					Font font_10BoldRed = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD, BaseColor.RED);
					Font font_14 = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD);

					Document document = new Document(PageSize.A4);
					PdfWriter.getInstance(document, new FileOutputStream(file));

					document.newPage();
					document.open();
					
					/**
					 *  Nome file
					 */
					File inputFile = input.getInputFile();
					String nomeFile = input.getFileName();
					/** 
					 * Impronta
					 */
					String digest = output.getPropOfType(DigestCtrl.RAPPORTO_VERIFICA_Digest, String.class);
					/**
					 *  Algoritmo
					 */
					DigestAlgID algoritmo = output.getPropOfType(DigestCtrl.RAPPORTO_VERIFICA_Algoritmo, DigestAlgID.class);
					/**
					 *  Encoding
					 */
					DigestEncID encoding = output.getPropOfType(DigestCtrl.RAPPORTO_VERIFICA_Encoding, DigestEncID.class);
					
					
					document.add(new Paragraph("Rapporto di verifica firma documento digitale", font_14));
					if(nomeFile != null && !"".equalsIgnoreCase(nomeFile)) {
						document.add(new Paragraph("Nome file : " + nomeFile, font_10));
					}
					document.add(new Paragraph("Impronta del file : " + digest, font_10));
					document.add(new Paragraph("Algoritmo di calcolo dell'impronta : " + algoritmo.value() + " " + encoding.value(), font_10));
					
					SigVerifyResultType sigVerifyResultType = output.getPropOfType(SigVerifyCtrl.RAPPORTO_VERIFICA_SigVerifyResult, SigVerifyResultType.class);
					
					/**
					 * ESITO VERIFICA	
					 */
					Chunk esitoVerificaChunk = new Chunk("Esito verifica : ", font_10);
					Chunk certificatoIntegroChunk = new Chunk();
					if(sigVerifyResultType.getVerificationStatus().equals(VerificationStatusType.OK)) {
						certificatoIntegroChunk = new Chunk("Le firme risultano valide", font_10BoldBlue);
					} else {
						certificatoIntegroChunk = new Chunk("Una/alcune delle firme risultano non valide", font_10BoldRed);
					}
					Paragraph p = new Paragraph();
					p.add(esitoVerificaChunk);
					p.add(certificatoIntegroChunk);
					document.add(p);
					
					hasChild = sigVerifyResultType.getSigVerifyResult().getChild() != null;
					if(hasChild && sigVerifyResultType.getVerificationStatus().equals(VerificationStatusType.OK)) {
						document.add(new Paragraph("La validità si riferisce solo alle firme apposte sulla \"busta crittografica\" più esterna come previsto da normativa vigente.\n", font_10Bold));
					}
					/**
					 * Codice eseguibile
					 */
					String codiceEseguibile = "";
					if (sigVerifyResultType.getSigVerifyResult().getDetectionCodeResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
						codiceEseguibile = "Non sono presenti codice eseguibile ne parti variabili all'interno del documento";
					} else if (sigVerifyResultType.getSigVerifyResult().getDetectionCodeResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED)) {
						codiceEseguibile = "Verifica della presenza di codice eseguibile o parti variabili all'interno del documento non eseguita";
					} else {
						log.error("Firma non valida a causa della presenza di codice eseguibile o parti variabili all'interno del documento");
						codiceEseguibile = "Firma non valida a causa della presenza di codice eseguibile o parti variabili all'interno del documento";
					}					
					document.add(new Paragraph("Codice eseguibile : " + codiceEseguibile, font_10));
					
					document.add(new Paragraph("\nFirme", font_14));
					

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					int indent = 0;
					
					/**
					 * FIRMATARI
					 */
					
					RapportoVerificaStampaCertificazioneBean stampaRapportoCertificazioneBean =  new RapportoVerificaStampaCertificazioneBean();
					List<SignerInformationType> infos = sigVerifyResultType.getSigVerifyResult().getSignerInformations().getSignerInformation();
					List<RapportoVerificaFirmatarioExtBean> firmatariExt = new ArrayList<RapportoVerificaStampaCertificazioneBean.RapportoVerificaFirmatarioExtBean>();
					for (SignerInformationType info : infos) {
						firmatariExt.add(creaFirmatarioBean(stampaRapportoCertificazioneBean, info, livelloCorrente));
					}
					if (sigVerifyResultType.getSigVerifyResult().getChild() != null) {
						hasChild = true;
						analizzaChild(stampaRapportoCertificazioneBean, sigVerifyResultType.getSigVerifyResult().getChild(), firmatariExt, livelloCorrente + 1);
					}
					stampaRapportoCertificazioneBean.setFirmatari(firmatariExt);
					
					populateFirmatari(livelloCorrente, font_10, font_10BoldBlue, font_10BoldRed, font_14, document, sdf,
							indent, stampaRapportoCertificazioneBean);
					
//					if (aggiungiFirma) {
//						Paragraph footer = new Paragraph(
//								"\n\nPer copia conforme del documento informatico sopra indicato costituito da ___ pagine verificato da __________________________ il __________",
//								font_14);
//						document.add(footer);
//
//						Paragraph firma = new Paragraph("\n\n Firma _____________________ ", font_14);
//						firma.setAlignment(Element.ALIGN_RIGHT);
//						document.add(firma);
//					}
					document.close();
					
					response.setVerificationStatus(VerificationStatusType.OK);
					output.addResult(this.getClass().getName(), response);
					output.setFileResult(file);
					verify = true;
				}
				
			} catch (Exception e) {
				response.setVerificationStatus(VerificationStatusType.KO);
				verify = false;
				log.error(e.getMessage(), e);
			}
		}
		
		return verify;
	}

	private void populateFirmatari(int livelloCorrente, Font font_10, Font font_10BoldBlue, Font font_10BoldRed,
			Font font_14, Document document, SimpleDateFormat sdf, int indent,
			RapportoVerificaStampaCertificazioneBean stampaRapportoCertificazioneBean) throws DocumentException {
		if (stampaRapportoCertificazioneBean.getFirmatari() != null && !stampaRapportoCertificazioneBean.getFirmatari().isEmpty()) {
			boolean isFirmaInnestata = true;
			for (RapportoVerificaFirmatarioExtBean firmatario : stampaRapportoCertificazioneBean.getFirmatari()) {
				
				int livelloFiglio = new Integer(firmatario.getFiglioDi()).intValue();

				if (livelloFiglio > livelloCorrente) {
					indent += 30;
					if(isFirmaInnestata) {
						document.add(new Paragraph("\nLe firme riportate a seguire non sono apposte sulla \"busta crittografica\" più esterna che è quella rilevante ai fini della validità della sottoscrizione digitale del file", font_10));
						isFirmaInnestata=false;
					}
					
				} else if (livelloFiglio < livelloCorrente) {
					indent -= 30;
				}

				Paragraph firmatarioTitle = new Paragraph("\nFirma ");
				firmatarioTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
				firmatarioTitle.setIndentationLeft(indent);
				document.add(firmatarioTitle);

				String tipoFirmaQATitle="";
				if("Q".equalsIgnoreCase(firmatario.getTipoFirmaQA())) {
					tipoFirmaQATitle = "firma elettronica qualificata (digitale)";
				} else if("A".equalsIgnoreCase(firmatario.getTipoFirmaQA())) {
					tipoFirmaQATitle = "firma elettronica avanzata (sigillo)";
				}
				Paragraph tipoFirmaQA = new Paragraph("Tipo di firma: " + tipoFirmaQATitle, font_10BoldBlue);
				tipoFirmaQA.setIndentationLeft(indent + 10);
				document.add(tipoFirmaQA);
				
				Paragraph firma = new Paragraph("Intestatario " + firmatario.getSubject(), font_10);
				firma.setIndentationLeft(indent + 10);
				document.add(firma);

				Paragraph ente = new Paragraph("Ente certificatore " + firmatario.getEnteCertificatore(), font_10);
				ente.setIndentationLeft(indent + 10);
				document.add(ente);

				Paragraph statoCertificato = new Paragraph(firmatario.getStatoCertificato(), font_10);
				statoCertificato.setIndentationLeft(indent + 10);
				document.add(statoCertificato);

				Paragraph dataEmissione = new Paragraph("Data emissione certificato " + firmatario.getDataEmissione(), font_10);
				dataEmissione.setIndentationLeft(indent + 10);
				document.add(dataEmissione);

				Paragraph dataScadenza = new Paragraph("Data scadenza certificato " + firmatario.getDataScadenza(),
						font_10);
				dataScadenza.setIndentationLeft(indent + 10);
				document.add(dataScadenza);

				if (firmatario.getSerialNumber() != null && !firmatario.getSerialNumber().equals("")) {
					Paragraph serialNumber = new Paragraph("SerialNumber " + firmatario.getSerialNumber(), font_10);
					serialNumber.setIndentationLeft(indent + 10);
					document.add(serialNumber);
				}

				Paragraph firmeTitle = new Paragraph("\nVerifiche ");
				firmeTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
				firmeTitle.setIndentationLeft(indent);
				document.add(firmeTitle);

				Paragraph verificationStatus;
				if (firmatario.isFirmaValida()) {
					verificationStatus = new Paragraph(firmatario.getVerificationStatus(), font_10BoldBlue);
				} else {
					verificationStatus = new Paragraph(firmatario.getVerificationStatus(), font_10BoldRed);
				}
				verificationStatus.setIndentationLeft(indent + 10);
				document.add(verificationStatus);

				Paragraph certExpiration = null;
				if (firmatario.getMarca() != null && firmatario.getMarca().getDate() != null && firmatario.getMarca().getDate().toGregorianCalendar() != null) {
					certExpiration = new Paragraph(firmatario.getCertExpiration() + " alla data della marca " + sdf.format(firmatario.getMarca().getDate().toGregorianCalendar().getTime()), font_10);
				} else {
					certExpiration = new Paragraph(firmatario.getCertExpiration(), font_10);
				}
				certExpiration.setIndentationLeft(indent + 10);
				document.add(certExpiration);

				Paragraph crlResult = null;
				if (firmatario.getMarca() != null && firmatario.getMarca().getDate() != null
						&& firmatario.getMarca().getDate().toGregorianCalendar() != null) {
					crlResult = new Paragraph(firmatario.getCrlResult() + " alla data della marca " + sdf.format(firmatario.getMarca().getDate().toGregorianCalendar().getTime()), font_10);
				} else {
					crlResult = new Paragraph(firmatario.getCrlResult(), font_10);
				}
				crlResult.setIndentationLeft(indent + 10);
				document.add(crlResult);

				Paragraph caReliability = new Paragraph(firmatario.getCaReliability(), font_10);
				caReliability.setIndentationLeft(indent + 10);
				document.add(caReliability);

				if (firmatario.getQcStatements() != null && firmatario.getQcStatements().length > 0) {

					Paragraph qcStatementsTitle = new Paragraph("\nQcStatements ");
					qcStatementsTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
					qcStatementsTitle.setIndentationLeft(indent);
					document.add(qcStatementsTitle);

					float indentqc = indent + 10;

					for (int i = 0; i < firmatario.getQcStatements().length; i++) {
						Paragraph qcStatement = new Paragraph("QcStatement " + firmatario.getQcStatements()[i], font_10);
						qcStatement.setIndentationLeft(indentqc);
						document.add(qcStatement);
					}
				}

				if (firmatario.getKeyUsages() != null && firmatario.getKeyUsages().length > 0) {

					Paragraph keyUsagesTitle = new Paragraph("\nKeyUsages ");
					keyUsagesTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
					keyUsagesTitle.setIndentationLeft(indent);
					document.add(keyUsagesTitle);

					float indentkeyUsages = indent + 10;

					for (int i = 0; i < firmatario.getKeyUsages().length; i++) {
						Paragraph keyUsage = new Paragraph("KeyUsage " + firmatario.getKeyUsages()[i], font_10);
						keyUsage.setIndentationLeft(indentkeyUsages);
						document.add(keyUsage);
					}
				}

				if (firmatario.getMarca() != null) {
					Paragraph marcaTitle = new Paragraph("\nMarca ");
					marcaTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
					marcaTitle.setIndentationLeft(indent);
					document.add(marcaTitle);

					float indentMarca = indent + 10;

					Paragraph dataMarca = new Paragraph("Data marca " + sdf.format(firmatario.getMarca().getDate().toGregorianCalendar().getTime()), font_10);
					dataMarca.setIndentationLeft(indentMarca);
					document.add(dataMarca);

					Paragraph validitaMarca = null;

					if (firmatario.getMarca().getVerificationStatus().equals(VerificationStatusType.OK)) {
						validitaMarca = new Paragraph("Marca temporale valida", font_10);
					} else {
						validitaMarca = new Paragraph("Marca temporale non valida", font_10);
					}

					validitaMarca.setIndentationLeft(indentMarca);
					document.add(validitaMarca);
				}

				if (firmatario.getControFirma() != null) {
					Paragraph controFirmaTitle = new Paragraph("\nContro firma ");
					controFirmaTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
					controFirmaTitle.setIndentationLeft(indent);
					document.add(controFirmaTitle);

					DnType subject = firmatario.getControFirma().getCertificato().getSubject();

					String controFirmatario = (subject.getC().equals(null) ? "" : "C=" + subject.getC())
							+ (subject.getCn() == null ? "" : " CN=" + subject.getCn())
							+ (subject.getName() == null ? "" : " NAME=" + subject.getName())
							+ (subject.getO() == null ? "" : " O=" + subject.getO())
							+ (subject.getOu() == null ? "" : " OU=" + subject.getOu());

					Paragraph controFirma = new Paragraph("Contro firma " + controFirmatario, font_10);
					controFirma.setIndentationLeft(indent + 10);
					document.add(controFirma);
				}
			} 
			if (stampaRapportoCertificazioneBean.getDataOraMarcaTemporale() != null) {
				Paragraph marcaEsternaTitle = new Paragraph("\nMarca temporale ", font_14);
				document.add(marcaEsternaTitle);
				
				Paragraph firmatarioTitle = new Paragraph("\n");
				firmatarioTitle.setFont(new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
				firmatarioTitle.setIndentationLeft(indent);
				document.add(firmatarioTitle);

				float indentMarca = indent + 10;
				
				Paragraph validitaMarca = null;					

				if (!stampaRapportoCertificazioneBean.isMarcaTemporaleNonValida()) {
					validitaMarca = new Paragraph("Marca temporale valida", font_10BoldBlue);
				} else {
					validitaMarca = new Paragraph("Marca temporale non valida", font_10BoldRed);
				}

				validitaMarca.setIndentationLeft(indentMarca);
				document.add(validitaMarca);

				Paragraph nameMarca = new Paragraph("Time Stamping Authority " + stampaRapportoCertificazioneBean.getTsaName(), font_10);
				nameMarca.setIndentationLeft(indentMarca);
				document.add(nameMarca);

				Paragraph dataMarca = new Paragraph("Data marca " + sdf.format(stampaRapportoCertificazioneBean.getDataOraMarcaTemporale()), font_10);
				dataMarca.setIndentationLeft(indentMarca);
				document.add(dataMarca);		
			}
			
		} else {
			log.error("Errore nella creazione del file di certificazione: nessun firmatario recuperato.");
		}
	}
	
	private RapportoVerificaFirmatarioExtBean creaFirmatarioBean(RapportoVerificaStampaCertificazioneBean stampaCertificazioneBean, SignerInformationType info, int livelloCorrente) {

		RapportoVerificaFirmatarioExtBean firmatarioExt = stampaCertificazioneBean.new RapportoVerificaFirmatarioExtBean();

		if( info.getCertificato()!=null ){
			DnType subject = info.getCertificato().getSubject();
	
			String firmatario = (subject.getC() == null ? "" : "C=" + subject.getC()) + (subject.getCn() == null ? "" : " CN=" + subject.getCn())
					+ (subject.getName() == null ? "" : " NAME=" + subject.getName()) + (subject.getO() == null ? "" : " O=" + subject.getO())
					+ (subject.getOu() == null ? "" : " OU=" + subject.getOu());
	
			DnType issuer = info.getCertificato().getIssuer();
	
			firmatarioExt.setSubject(firmatario);
			firmatarioExt.setEnteCertificatore(issuer.getO());
	
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
			firmatarioExt.setDataEmissione(sdf.format(info.getCertificato().getDataDecorrenza().toGregorianCalendar().getTime()));
			firmatarioExt.setDataScadenza(sdf.format(info.getCertificato().getDataScadenza().toGregorianCalendar().getTime()));
	
			firmatarioExt.setSerialNumber(info.getCertificato().getSerialNumber());
			firmatarioExt.setQcStatements(info.getCertificato().getQcStatements().getQcStatement()
					.toArray(new String[info.getCertificato().getQcStatements().getQcStatement().size()]));
			firmatarioExt.setKeyUsages(info.getCertificato().getKeyUsages().getKeyUsage()
					.toArray(new String[info.getCertificato().getKeyUsages().getKeyUsage().size()]));
		}
		
		firmatarioExt.setTipoFirmaQA( info.getTipoFirmaQA().name() );
		
		firmatarioExt.setMarca(info.getMarca());

		firmatarioExt.setControFirma(info.getControFirma());

		firmatarioExt.setStatoCertificato(info.getVerificationStatus().equals(VerificationStatusType.OK) ? "Certificato credibile"
				: "Certificato non credibile");
		firmatarioExt
				.setCertExpiration(info.getSigVerifyResult().getCertExpirationResult().getVerificationStatus().equals(VerificationStatusType.OK) ? "Il certificato è in corso di validità"
						: "Il certificato non è in corso di validità");

		if (info.getVerificationStatus().equals(VerificationStatusType.OK)) {
			firmatarioExt.setFirmaValida(true);
			firmatarioExt.setVerificationStatus("La firma risulta valida");
		} else {
			firmatarioExt.setFirmaValida(false);
			firmatarioExt.setVerificationStatus("La firma risulta non valida");
		}

		if (info.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
			firmatarioExt.setCrlResult("Il certificato non è revocato ne sospeso alla data");
		}
		if (info.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
			firmatarioExt.setCrlResult("Il certificato è revocato e sospeso alla data");
		}
		if (info.getSigVerifyResult().getCRLResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED)) {
			firmatarioExt.setCrlResult("La verifica dello stato revoca/sospensione non è stata possibile");
		}

		if (info.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.OK)) {
			firmatarioExt.setCaReliability("Il certificato è emesso da una CA accreditata");
		}
		if (info.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.KO)) {
			firmatarioExt.setCaReliability("Il certificato non è emesso da una CA accreditata");
		}
		if (info.getSigVerifyResult().getCAReliabilityResult().getVerificationStatus().equals(VerificationStatusType.SKIPPED)) {
			firmatarioExt.setCaReliability("Non è possibile verificare l'affidabilità della CA");
		}

		firmatarioExt.setFiglioDi("" + livelloCorrente);

		return firmatarioExt;
	}
	
	private void analizzaChild(RapportoVerificaStampaCertificazioneBean stampaCertificazioneBean, SigVerifyResultType child, List<RapportoVerificaFirmatarioExtBean> firmatari, int livello) {
		SigVerifyResult lSigVerifyResultTypeSigVerifyResultChild = child.getSigVerifyResult();
		lSigVerifyResultTypeSigVerifyResultChild.getFormatResult().getEnvelopeFormat();
		List<SignerInformationType> infosChild = lSigVerifyResultTypeSigVerifyResultChild.getSignerInformations().getSignerInformation();
		for (SignerInformationType info : infosChild) {
			firmatari.add(creaFirmatarioBean(stampaCertificazioneBean, info, livello));
		}
		if (child.getSigVerifyResult().getChild() != null) {
			analizzaChild(stampaCertificazioneBean, child.getSigVerifyResult().getChild(), firmatari, livello + 1);
		}
	}

	@Override
	public String getOperationType() {
		return operationType;
	}
	
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}