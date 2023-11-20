/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SimpleAttributeTableGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;

import it.eng.auriga.ui.module.layout.server.conversionePdf.datasource.bean.RettangoloFirmaPadesBean;
import it.eng.auriga.util.PdfSignatureUtils;
import it.eng.hsm.client.Hsm;
import it.eng.hsm.client.exception.HsmClientRuntimeOperatorException;
import it.eng.hsm.client.exception.HsmClientSignatureException;
import it.eng.hsm.utils.BouncyCastleProviderUtils;
import it.eng.hsm.utils.CustomContentSigner;
import it.eng.hsm.utils.StreamUtils;
import it.eng.utility.ui.sign.FileElaborate;

public class PDFServerSigner {

	private static Logger log = Logger.getLogger(PDFServerSigner.class);

	private byte[] data;
	private int signaturePage = 1;

	private String signatureFieldName;
	private String signatureLayer2Text;

	public PDFServerSigner() {
		BouncyCastleProviderUtils.checkBouncyCastleProvider();
	}

	public String getTokenSignatureAlgorithm() {
		return "NONEwithRSA";
	}

	public void setDocument(byte[] data) {
		this.data = data;
	}

	public byte[] getDocument() {
		return data;
	}

	public int getSignaturePage() {
		return signaturePage;
	}

	/**
	 * Imposta la pagina dove visualizzare la firma. Se il numero della pagina è uguale o maggiore di 1 è riferito dall'inizio del documento, altrimenti dalla
	 * fine (0 = ultima pagina, -1 = penultima pagina)
	 * 
	 * @param signaturePage
	 */
	public void setSignaturePage(int signaturePage) {
		this.signaturePage = signaturePage;
	}

	public String getSignatureFieldName() {
		return signatureFieldName;
	}

	public void setSignatureFieldName(String signatureFieldName) {
		this.signatureFieldName = signatureFieldName;
	}

	public String getSignatureLayer2Text() {
		return signatureLayer2Text;
	}

	public void setSignatureLayer2Text(String signatureLayer2Text) {
		this.signatureLayer2Text = signatureLayer2Text;
	}

	public ByteArrayOutputStream generaPdf(byte[] data, X509Certificate userCert, Hsm client, boolean visualizzaFirmaPades, RettangoloFirmaPadesBean rettangoloFirmaPadesBean) throws HsmClientSignatureException {
		try {
			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			
			int cryptoDataSize = (int) 4000;

			PdfReader reader = new PdfReader(data);
			ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
			PdfStamper stp = null;// PdfStamper.createSignature(reader, pdfOut, '\0', null, true);
			AcroFields af = reader.getAcroFields();
			ArrayList<String> names = af.getSignatureNames();
			if (names.size() == 0) {
				log.debug("Il file non e' firmato");
				stp = PdfStamper.createSignature(reader, pdfOut, '\0');
			} else {
				log.debug("Il file e' firmato");
				stp = PdfStamper.createSignature(reader, pdfOut, '\0', null, true);
			}

			PdfSignatureAppearance sap = stp.getSignatureAppearance();
			sap.setSignDate(new GregorianCalendar());
			sap.setCertificate(userCert);
			sap.setAcro6Layers(true);

			// Se specificato mostro la firma
			if (visualizzaFirmaPades) {

				BaseFont layer2Font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
				sap.setLayer2Font(new Font(layer2Font, 9));
				sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);

				Rectangle rettangoloDiFirma = PdfSignatureUtils.calcolaRettangoloFirma(reader, rettangoloFirmaPadesBean);

				// Posiziono la firma nell'ultima pagina del documento
				sap.setVisibleSignature(rettangoloDiFirma, reader.getNumberOfPages(), null);

				X500Name x500Subject = new JcaX509CertificateHolder(userCert).getSubject();
				String cnCert = null;
				if (x500Subject != null) {
					RDN[] cns = x500Subject.getRDNs(BCStyle.CN);
					if (cns != null && cns.length > 0) {
						RDN cn = cns[0];
						if (cn != null && cn.getFirst() != null) {
							cnCert = IETFUtils.valueToString(cn.getFirst().getValue());
						}
					}
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				sap.setLayer2Text("Firmato digitalmente da " + cnCert + " in data " + dateFormat.format(new Date()));
				// sap.setLayer4Text("Firmato da " + cnCert + " in data " + dateFormat.format(new Date()));
			}

			boolean detached = true;
			PdfSignature dic;
			if (detached) {
				dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ETSI_CADES_DETACHED);
			} else {
				dic = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_SHA1);

			}

			dic.setDate(new PdfDate(sap.getSignDate()));

			X500Name x500name = new JcaX509CertificateHolder((X509Certificate) userCert).getSubject();
			String cnString = null;
			if (x500name != null) {
				RDN[] cns = x500name.getRDNs(BCStyle.CN);
				if (cns != null && cns.length > 0) {
					RDN cn = cns[0];
					if (cn != null && cn.getFirst() != null) {
						cnString = IETFUtils.valueToString(cn.getFirst().getValue());
					}
				}
			}
			dic.setName(cnString);
			if (sap.getReason() != null)
				dic.setReason(sap.getReason());
			if (sap.getLocation() != null)
				dic.setLocation(sap.getLocation());
			sap.setCryptoDictionary(dic);
			HashMap exc = new HashMap();
			exc.put(PdfName.CONTENTS, new Integer((cryptoDataSize) * 2 + 2));
			sap.preClose(exc);

			MessageDigest md = MessageDigest.getInstance("SHA256");
			byte[] buf = new byte[65664];
//			InputStream fis = new ByteArrayInputStream(StreamUtils.loadBytes(sap.getRangeStream()));
			InputStream fis = sap.getRangeStream();
			int nRead = 0;
			int written = 0;
			while ((nRead = fis.read(buf)) > 0) {
				md.update(buf, 0, nRead);
				written += nRead;
			}
			fis.close();
			byte[] contentDigest = md.digest();

			CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
			ContentSigner contentSigner = new CustomContentSigner(userCert.getEncoded(), "SHA256withRSA", client);

			md = MessageDigest.getInstance("SHA256");
			byte[] certDigest = md.digest(userCert.getEncoded());
			AttributeTable attributeTable = FirmaPadesUtil.generateSignedAttributes(contentDigest, certDigest);
			JcaDigestCalculatorProviderBuilder jcadigestbuilder = new JcaDigestCalculatorProviderBuilder();
			DigestCalculatorProvider jcadigestprov = jcadigestbuilder.setProvider("BC").build();
			JcaSignerInfoGeneratorBuilder jca_sigb = new JcaSignerInfoGeneratorBuilder(jcadigestprov);
			SignerInfoGenerator sigInfoGen = jca_sigb.build(contentSigner, (X509Certificate) userCert);
			SignerInfoGenerator sigInfoGenWithSignedAttr = new SignerInfoGenerator(sigInfoGen, new SimpleAttributeTableGenerator(attributeTable), null);

			List certList = new ArrayList();
			certList.add(userCert);
			Store y = new JcaCertStore(certList);

			generator.addSignerInfoGenerator(sigInfoGenWithSignedAttr);
			generator.addCertificates(y);

			CMSSignedData signedData;

			if (detached) {
				CMSProcessable content = new CMSProcessableRange(sap);
				signedData = generator.generate(content, false, "BC");
			} else {
				MessageDigest md1 = MessageDigest.getInstance("SHA1");
				InputStream s = sap.getRangeStream();
				int read = 0;
				byte[] buff = new byte[8192];
				while ((read = s.read(buff, 0, 8192)) > 0) {
					md1.update(buff, 0, read);
				}
				CMSProcessable content = new CMSProcessableByteArray(md1.digest());
				signedData = generator.generate(content, true, "BC");
			}

			byte[] pk = signedData.getEncoded();

			byte[] outc = new byte[cryptoDataSize];
			PdfDictionary dic2 = new PdfDictionary();
			System.arraycopy(pk, 0, outc, 0, pk.length);
			dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
			sap.close(dic2);

			return pdfOut;
		} catch (HsmClientRuntimeOperatorException e) {
			// Questa eccezione viene sollavata dal CustomContentSigner in caso di errori
			log.error("Errore nella firma remota", e);
			String errorMessage = "Errore nella firma remota";
			if (e.getMessage() != null) {
				errorMessage += ". " + e.getMessage();
			}
			throw new HsmClientSignatureException(errorMessage, e);
		} catch (NoSuchAlgorithmException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (IOException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (DocumentException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (NoSuchProviderException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (CMSException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (CertificateEncodingException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (OperatorCreationException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (Exception e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}
	}
	
	public FileElaborate calcolaHashPdf(byte[] data, X509Certificate userCert, Hsm client, boolean visualizzaFirmaPades, RettangoloFirmaPadesBean rettangoloFirmaPadesBean) throws HsmClientSignatureException {
		FileElaborate fileElaborate = new FileElaborate();
		try {
			int cryptoDataSize = (int) 4000;

			PdfReader reader = new PdfReader(data);
			ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
			PdfStamper stp = null;// PdfStamper.createSignature(reader, pdfOut, '\0', null, true);
			AcroFields af = reader.getAcroFields();
			ArrayList<String> names = af.getSignatureNames();
			if (names.size() == 0) {
				log.debug("Il file non e' firmato");
				stp = PdfStamper.createSignature(reader, pdfOut, '\0');
			} else {
				log.debug("Il file e' firmato");
				stp = PdfStamper.createSignature(reader, pdfOut, '\0', null, true);
			}

			PdfSignatureAppearance sap = stp.getSignatureAppearance();
			sap.setSignDate(new GregorianCalendar());
			sap.setCertificate(userCert);
			sap.setAcro6Layers(true);

			// Se specificato mostro la firma
			if (visualizzaFirmaPades) {

				BaseFont layer2Font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
				sap.setLayer2Font(new Font(layer2Font, 9));
				sap.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);

				Rectangle rettangoloDiFirma = PdfSignatureUtils.calcolaRettangoloFirma(reader, rettangoloFirmaPadesBean);

				// Posiziono la firma nell'ultima pagina del documento
				sap.setVisibleSignature(rettangoloDiFirma, reader.getNumberOfPages(), null);

				X500Name x500Subject = new JcaX509CertificateHolder(userCert).getSubject();
				String cnCert = null;
				if (x500Subject != null) {
					RDN[] cns = x500Subject.getRDNs(BCStyle.CN);
					if (cns != null && cns.length > 0) {
						RDN cn = cns[0];
						if (cn != null && cn.getFirst() != null) {
							cnCert = IETFUtils.valueToString(cn.getFirst().getValue());
						}
					}
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				sap.setLayer2Text("Firmato digitalmente da " + cnCert + " in data " + dateFormat.format(new Date()));
				// sap.setLayer4Text("Firmato da " + cnCert + " in data " + dateFormat.format(new Date()));
			}

			boolean detached = true;
			PdfSignature dic;
			if (detached) {
				dic = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ETSI_CADES_DETACHED);
			} else {
				dic = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_SHA1);

			}

			dic.setDate(new PdfDate(sap.getSignDate()));

			X500Name x500name = new JcaX509CertificateHolder((X509Certificate) userCert).getSubject();
			String cnString = null;
			if (x500name != null) {
				RDN[] cns = x500name.getRDNs(BCStyle.CN);
				if (cns != null && cns.length > 0) {
					RDN cn = cns[0];
					if (cn != null && cn.getFirst() != null) {
						cnString = IETFUtils.valueToString(cn.getFirst().getValue());
					}
				}
			}
			dic.setName(cnString);
			if (sap.getReason() != null)
				dic.setReason(sap.getReason());
			if (sap.getLocation() != null)
				dic.setLocation(sap.getLocation());
			sap.setCryptoDictionary(dic);
			HashMap exc = new HashMap();
			exc.put(PdfName.CONTENTS, new Integer((cryptoDataSize) * 2 + 2));
			sap.preClose(exc);

			java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			MessageDigest md = MessageDigest.getInstance("SHA256");
			byte[] buf = new byte[65664];
			InputStream fis = new ByteArrayInputStream(StreamUtils.loadBytes(sap.getRangeStream()));
//			InputStream fis = sap.getRangeStream();
			int nRead = 0;
			int written = 0;
			while ((nRead = fis.read(buf)) > 0) {
				md.update(buf, 0, nRead);

				written += nRead;
			}
			fis.close();
			byte[] contentDigest = md.digest();
			fileElaborate.setPdfsignature(sap);
			fileElaborate.setHash(contentDigest);
			fileElaborate.setOutStream(pdfOut);
			
			return fileElaborate;
		} catch (IOException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (DocumentException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (NoSuchAlgorithmException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		} catch (CertificateEncodingException e) {
			log.error("Errore nella firma remota", e);
			throw new HsmClientSignatureException(e.getMessage(), e);
		}
		
		
	}

	/**
	 * CMSProcessable implementation.
	 */
	class CMSProcessableRange implements CMSProcessable {

		private PdfSignatureAppearance sap;
		private byte[] buf = new byte[8192];

		public CMSProcessableRange(PdfSignatureAppearance sap) {
			this.sap = sap;
		}

		public void write(OutputStream outputStream) throws IOException, CMSException {
			InputStream s = sap.getRangeStream();
			int read = 0;
			while ((read = s.read(buf, 0, buf.length)) > 0) {
				outputStream.write(buf, 0, read);
			}
		}

		public Object getContent() {
			return sap;
		}
	}

}
