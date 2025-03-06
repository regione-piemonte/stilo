package it.eng.utility.cryptosigner.data;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CRL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTerminalField;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.cmp.PKIStatus;
import org.bouncycastle.asn1.cmp.PKIStatusInfo;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.tsp.TimeStampResp;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Store;

//import com.itextpdf.text.pdf.AcroFields;
//import com.itextpdf.text.pdf.PdfReader;
//import com.itextpdf.text.pdf.PdfStamper;
//import com.itextpdf.text.pdf.security.PdfPKCS7;

import it.eng.utility.FileUtil;
import it.eng.utility.cryptosigner.controller.bean.ValidationInfos;
import it.eng.utility.cryptosigner.controller.impl.cert.CertMessage;
import it.eng.utility.cryptosigner.data.signature.ISignature;
import it.eng.utility.cryptosigner.data.signature.PDFSignature;
import it.eng.utility.cryptosigner.data.type.SignerType;
import it.eng.utility.cryptosigner.utils.DeleteOnCloseFileInputStream;
import it.eng.utility.cryptosigner.utils.MessageConstants;
import it.eng.utility.cryptosigner.utils.MessageHelper;
import it.eng.utility.pdfUtility.PdfUtil;
import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.commenti.PdfCommentiUtil;
import it.eng.utility.pdfUtility.editabili.PdfEditabiliUtil;

/**
 * Implementa i controlli su firme di tipo PAdES. Il contenuto di un file è riconosciuto se implementa le specifiche ETSI TS 102 778-1
 * 
 * @author Stefano Zennaro
 */
public class PdfSigner extends AbstractSigner {

	//private AcroFields acroFields;
	//private Map<String, PdfPKCS7> signaturesByName;
	private Map<String, PDSignature> signaturesByName;
	private Map<String, Integer> signaturesGraphic;
	
	public static void main(String[] args) {
		// Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		PdfSigner signer = new PdfSigner();
		File file = new File("C:/Users/DBE4131/Desktop/ASL-LATINA.REGISTRO_UFFICIALE.2022.0009830.pdf");
		System.out.println("file esiste "+file.exists());
		// signer.setFile(file);
		boolean isOk = signer.isSignedType(file);
		System.out.println(isOk);
		//
		// if( isOk ){
		try {
			List<ISignature> signatures = signer.getSignatures(file);
			System.out.println(signatures);

			InputStream stream = signer.getContentAsInputStream(file);

			File extracttempfile = File.createTempFile("Extract", "file",new File("C:/Users/DBE4131/Downloads/"));
			FileOutputStream fos = new FileOutputStream(extracttempfile);
			IOUtils.copyLarge(stream, fos);
			fos.close();
			if( stream!=null)
				stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isSignedType(File file) {
		boolean signed = false;
		//PdfReader reader = null;
		PDDocument pdDoc = null;
		FileInputStream fis = null;
		//this.file = file;
		try {
			// Resetto il signer
			reset();
			//acroFields = null;
			signaturesByName = null;
			//signaturesByName = new HashMap<String, PdfPKCS7>();
			signaturesByName = new HashMap<String, PDSignature>();
			signaturesGraphic = new HashMap<String, Integer>();
			try {
				fis = FileUtils.openInputStream(file);
				//reader = new PdfReader(fis);
				//acroFields = reader.getAcroFields();
				//ArrayList<String> names = acroFields.getSignatureNames();
				
				pdDoc = PDDocument.load(fis);
				
				ArrayList<String> names = new ArrayList<String>();
				//List<PDSignature> pdSignatures = pdDoc.getSignatureDictionaries();
				//if( pdSignatures!=null ){
				List<PDSignatureField> signatureFields = pdDoc.getSignatureFields();
				if( signatureFields!=null ){
					//signatureFields.ge
					//System.out.println(pdSignatures.size());
					String name = null;
					//for(PDSignature pdSignature : pdSignatures){
					for(PDSignatureField signatureField : signatureFields){
						//System.out.println("-- " + pdSignature.getName());
						//for (Iterator<String> it = names.iterator(); it.hasNext();) {
						//name = pdSignature.getName();
						//PdfPKCS7 pkcs = acroFields.verifySignature(name, "BC");
						//if( acroFields.signatureCoversWholeDocument( name ) )
						//	signaturesByName.put(name, pkcs);
						//name = pdSignature.getName();
						name = signatureField.getFullyQualifiedName();
						PDSignature pdSignature = signatureField.getSignature();
						if( pdSignature!=null){
							
							log.debug("Verifico se la firma con name " + name + " ha evidenza grafica");
							List<PDAnnotationWidget> wList = signatureField.getWidgets();
							for(PDAnnotationWidget w : wList){
								PDRectangle rec = w.getRectangle();
								if(rec!=null && rec.getWidth()!=0 && rec.getHeight()!=0) {
									if(pdDoc.getPages()!=null && w.getPage()!=null ){
										int pageIndex = pdDoc.getPages().indexOf(w.getPage())+1;
										log.debug("La firma ha evidenza grafica nella pagina "+pageIndex +
												". Rettangolo firma: "+ rec.getWidth() + " " + rec.getHeight());
										signaturesGraphic.put(name, pageIndex);
									} else {
										log.error("Non riesco a recuperare il numero di pagina della firma grafica");
									}
								}
							}
							
							signaturesByName.put(name, pdSignature);
							names.add(name);
						}
					}
				}

				log.debug("----> isSignedType isSignedType " + names);
				if (names == null || names.isEmpty()) {
					signed = false;
				} else {
					signed = true;
				}
			} catch (IOException e) {
				log.info("Errore in isSignedType di PdfSigner - " + e.getMessage() + " - Il file non ha firma pdf"/*, e*/);
				signed = false;
			} finally {
				//if (reader != null)
				//	reader.close();
				if( pdDoc!=null )
					pdDoc.close();
				if (fis != null)
					fis.close();
			}
			return signed;
		} catch (Exception e) {
			log.info("Error isSignedType di PdfSigner", e);
			signed = false;
		} finally {
			//if (reader != null)
			//	reader.close();
			if( pdDoc!=null ){
				try {
					pdDoc.close();
				} catch (IOException e) {
					log.info("Error isSignedType di PdfSigner", e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.info("Error isSignedType di PdfSigner", e);
				}
			}
		}
		return signed;
	}

	public TimeStampToken[] getTimeStampTokens(File file) {
		PDDocument pdDoc = null;
		if (timestamptokens == null) {
			ArrayList<TimeStampToken> timestamptokenList = new ArrayList<TimeStampToken>();
			FileInputStream fis = null;
			//PdfReader reader = null;
			try {
				fis = FileUtils.openInputStream(file);
				//reader = new PdfReader(fis);
				//acroFields = reader.getAcroFields();
				pdDoc = PDDocument.load(fis);
				
				//ArrayList<String> names = acroFields.getSignatureNames();
				
				ArrayList<String> names = new ArrayList<String>();
				List<PDSignature> pdSignatures = pdDoc.getSignatureDictionaries();
				if( pdSignatures!=null ){
					String name;
					//for (Iterator<String> it = names.iterator(); it.hasNext();) {
					for(PDSignature pdSignature : pdSignatures){
						name = pdSignature.getName();
	//					name = it.next();
	//					PdfPKCS7 pkcs = acroFields.verifySignature(name);
	//					if (pkcs.getTimeStampToken() != null) {
	//						timestamptokenList.add(pkcs.getTimeStampToken());
	//					}
						
						String subFilter = pdSignature.getSubFilter();
		                if (subFilter != null)
		                {
		                    switch (subFilter)
		                    {
		                    	case "ETSI.RFC3161":
			                    	COSDictionary sigDict = pdSignature.getCOSObject();
			                        COSString contents = (COSString) sigDict.getDictionaryObject(COSName.CONTENTS);
			                        TimeStampToken timeStampToken = new TimeStampToken(new CMSSignedData(contents.getBytes()));
			                       // System.out.println("Time stamp gen time: " + timeStampToken.getTimeStampInfo().getGenTime());
			                        //System.out.println("Time stamp tsa name: " + timeStampToken.getTimeStampInfo().getTsa().getName());
			                        if (timeStampToken != null) {
			                        	timestamptokenList.add( timeStampToken);
			                        	if( signaturesByName.containsKey( name ))
			                        		signaturesByName.remove(name);
			    					}
			                        break;
		                    }
		                }
					}
				}

			} catch (Exception e) {
				log.info("Error getTimeStampTokens di PdfSigner", e);
			} finally {
				//if (reader != null)
				//	reader.close();
				if( pdDoc!=null ){
					try {
						pdDoc.close();
					} catch (IOException e) {
						log.info("Error getTimeStampTokens di PdfSigner", e);
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						log.info("Error getTimeStampTokens di PdfSigner", e);
					}
				}
			}
			timestamptokens = timestamptokenList.toArray(new TimeStampToken[timestamptokenList.size()]);
			return timestamptokens;
		} else {
			return timestamptokens;
		}
	}
	
	private byte[] getSignedContent(File file){
		FileInputStream fis = null;
		FileInputStream fis1 = null;
		PDDocument pdDoc = null;
		byte[] signedContent = null;
		try {
			fis = FileUtils.openInputStream(file);
			fis1 = FileUtils.openInputStream(file);
			pdDoc = PDDocument.load(fis);
			
			ArrayList<String> names = new ArrayList<String>();
			List<PDSignature> pdSignatures = pdDoc.getSignatureDictionaries();
			if( pdSignatures!=null ){
				String name;
				for(PDSignature pdSignature : pdSignatures){
					name = pdSignature.getName();
					
					String subFilter = pdSignature.getSubFilter();
	                if (subFilter != null)
	                {
	                    switch (subFilter)
	                    {
	                    	case "ETSI.RFC3161":
		                    	COSDictionary sigDict = pdSignature.getCOSObject();
		                        COSString contents = (COSString) sigDict.getDictionaryObject(COSName.CONTENTS);
		                        byte[] bytes = IOUtils.toByteArray(fis1);
		                        signedContent = pdSignature.getSignedContent(bytes);
		                        break;
	                    }
	                }
				}
			}

		} catch (Exception e) {
			log.info("Error getTimeStampTokens di PdfSigner", e);
		} finally {
			if( pdDoc!=null ){
				try {
					pdDoc.close();
				} catch (IOException e) {
					log.info("Error getTimeStampTokens di PdfSigner", e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.info("Error getTimeStampTokens di PdfSigner", e);
				}
			}
			if (fis1 != null) {
				try {
					fis1.close();
				} catch (IOException e) {
					log.info("Error getTimeStampTokens di PdfSigner", e);
				}
			}
		}
		return signedContent;
	}
	
	/*private ValidationInfos validateTimeStamp(TimeStampToken timeStampToken){
		ValidationInfos validationInfos = new ValidationInfos();
		String hashAlgOID = timeStampToken.getTimeStampInfo().getMessageImprintAlgOID().getId();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(hashAlgOID);
			TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
			TimeStampRequest request = gen.generate(hashAlgOID, digest.digest(signature));

			checkTimeStampTokenOverRequest(validationInfos, timeStampToken, request);

		} catch (NoSuchAlgorithmException e) {
			log.error("NoSuchAlgorithmException", e);
			validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED,
					MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
		}
		
		return validationInfos;
	}*/

	public Map<byte[], TimeStampToken> getMapSignatureTimeStampTokens(File file) {
		Map<byte[], TimeStampToken> map = new HashMap<byte[], TimeStampToken>();
		FileInputStream fis = null;
		//PdfReader reader = null;
		PDDocument pdDoc = null;
		try {
			fis = FileUtils.openInputStream(file);
//			reader = new PdfReader(fis);
//			acroFields = reader.getAcroFields();
//			ArrayList<String> names = acroFields.getSignatureNames();
			pdDoc = PDDocument.load(fis);
			
			String name;
			ArrayList<String> names = new ArrayList<String>();
			//List<PDSignature> pdSignatures = pdDoc.getSignatureDictionaries();
			List<PDSignatureField> signatureFields = pdDoc.getSignatureFields();
			if( signatureFields!=null ){
			//if( pdSignatures!=null ){
				//for(PDSignature pdSignature : pdSignatures){
				
				int indexFirma = 0;
				for(PDSignatureField signatureField : signatureFields){
					//	for(PDSignature pdSignature : pdSignatures){
					//for (Iterator<String> it = names.iterator(); it.hasNext();) {
	//				name = it.next();
					//name = pdSignature.getName();
					name = signatureField.getFullyQualifiedName();
					log.info("Name " + name);
					PDSignature pdSignature = signatureField.getSignature();
					
					
	//					PdfPKCS7 pkcs = acroFields.verifySignature(name);
	//					boolean verifica = pkcs.verify();
	//					log.info("Verifica firma pdf: " + verifica);
	//	
					//PDFSignature pdfSignature = new PDFSignature(pdSignature, file, indexFirma);
					if( pdSignature!=null ){
						String subFilter = pdSignature.getSubFilter();
		                if (subFilter != null) {
		                    switch (subFilter)
		                    {
		                    	case "ETSI.RFC3161":
			                    	COSDictionary sigDict = pdSignature.getCOSObject();
			                        COSString contents = (COSString) sigDict.getDictionaryObject(COSName.CONTENTS);
			                        TimeStampToken timeStampToken = new TimeStampToken(new CMSSignedData(contents.getBytes()));
			                       // System.out.println("Time stamp gen time: " + timeStampToken.getTimeStampInfo().getGenTime());
			                       // System.out.println("Time stamp tsa name: " + timeStampToken.getTimeStampInfo().getTsa().getName());
			                        if (timeStampToken != null) {
			    						//map.put(pdfSignature.getSignatureBytes(), timeStampToken);
			                        	if( signaturesByName.containsKey( name ))
			                        		signaturesByName.remove(name);
			                        	
			                        	
			                        	//PDFSignature pdfSignature = new PDFSignature(pdSignature, file, indexFirma);
			            				//map.put(pdfSignature.getSignatureBytes(), timeStampToken);
			    					}
			                        break;
		                    }
		                }
		//					PDFSignature pdfSignature = new PDFSignature(pkcs);
		//					TimeStampToken tsToken = pkcs.getTimeStampToken();
		//					if (tsToken != null) {
		//						map.put(pdfSignature.getSignatureBytes(), tsToken);
		//					}
		                
		                indexFirma++;
					}
				}
			}
		} catch (Exception e) {
			log.error("Eccezione getMapSignatureTimeStampTokens", e);
		} finally {
			//if (reader != null)
			//	reader.close();
			if( pdDoc!=null ){
				try {
					pdDoc.close();
				} catch (IOException e) {
					log.error("Eccezione getMapSignatureTimeStampTokens", e);
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("Eccezione getMapSignatureTimeStampTokens", e);
				}
			}
		}
		return map;
	}

	public SignerType getFormat() {
		return SignerType.PAdES;
	}

	public ValidationInfos validateTimeStampTokensEmbedded(File file) {
		ValidationInfos validationInfos = new ValidationInfos();
		
		if (timestamptokens != null) {
			
			byte[] signatureContent = getSignedContent(file);
       	 	log.debug("signatureContent " + signatureContent);
       	 	
			for(TimeStampToken timestamptoken : timestamptokens){
				log.debug("Validazione marca ");
				validationInfos = validateTimeStamp(timestamptoken, signatureContent);
			}
		} else {
			log.debug("Nessuna marca presente");
		}
//		if (this.signaturesByName == null) {
//			if (!this.isSignedType(file)) {
//				validationInfos.addError(MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
//				validationInfos.addErrorWithCode(MessageConstants.FV_FILE_FORMAT_ERROR,
//						MessageHelper.getMessage(MessageConstants.FV_FILE_FORMAT_ERROR, this.getFormat()));
//				return validationInfos;
//			} else
//				getTimeStampTokens();
//		}
//		Set<String> signatureNames = signaturesByName.keySet();
//		for (String signatureName : signatureNames) {
//			try {
//				PdfPKCS7 signature = signaturesByName.get(signatureName);
//				if (!signature.verifyTimestampImprint()) {
//					validationInfos.addErrorWithCode(MessageConstants.SIGN_MARKSIGN_NOTVALID,
//							MessageHelper.getMessage(MessageConstants.SIGN_MARKSIGN_NOTVALID, signatureName));
//					validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARKSIGN_NOTVALID, signatureName));
//				}
//			} catch (NoSuchAlgorithmException e) {
//				validationInfos.addErrorWithCode(MessageConstants.SIGN_MARKSIGN_ALGORITHM_NOTSUPPORTED,
//						MessageHelper.getMessage(MessageConstants.SIGN_MARKSIGN_ALGORITHM_NOTSUPPORTED, signatureName));
//				validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARKSIGN_ALGORITHM_NOTSUPPORTED, signatureName));
//			} catch (GeneralSecurityException e) {
//				log.error("Eccezione validateTimeStampTokensEmbedded", e);
//			}
//		}
		return validationInfos;
	}

	public List<ISignature> getSignatures(File file) {
		if (signaturesByName == null) {
			if (!this.isSignedType(file))
				return null;
		}
		List<ISignature> pdfSignatures = new ArrayList<ISignature>();
//		for (PdfPKCS7 signature : signaturesByName.values()) {
//			PDFSignature pdfSignature = new PDFSignature(signature);
//			pdfSignatures.add(pdfSignature);
//		}
		int indexFirma = 0;
		for (PDSignature signature : signaturesByName.values()) {
			PDFSignature pdfSignature = new PDFSignature(signature, file, indexFirma);
			pdfSignatures.add(pdfSignature);
			indexFirma++;
		}
		
		log.debug("--- getSignatures " + pdfSignatures);
		return pdfSignatures;
	}
	
	

	/*
	 * TODO!!
	 */
	public byte[] getUnsignedContentDigest(MessageDigest digestAlgorithm, File file) {
		return null;
	}

	public InputStream getUnsignedContent(File file) {
		
		PDDocument pdDoc = null;
		try {

			File fileinternal = File.createTempFile("ExtrcatInt", "ext", file.getParentFile());
			File fileinternalExtr = File.createTempFile("ExtrcatExt", "ext", file.getParentFile());

			// creo una copia del file originale
			FileUtils.copyFile(file, fileinternal);

			pdDoc = PDDocument.load(fileinternal);
			
			if(pdDoc.isEncrypted())
				pdDoc.setAllSecurityToBeRemoved(true);
			
			List<PDSignatureField> signatureFields = pdDoc.getSignatureFields();
			
			boolean daStaticizzare = false;
			List<Integer> listPagesToConvert = new ArrayList<Integer>();
			for(PDSignatureField signatureField : signatureFields ){
				
				String fullFieldName = signatureField.getFullyQualifiedName();
				if(signaturesGraphic.containsKey( fullFieldName ) ){
					int paginaFirmaGrafica = signaturesGraphic.get(fullFieldName);
					log.debug("File con campo firma " + fullFieldName + " con evidenza grafica nella pagina "+ paginaFirmaGrafica + "!");
					if( paginaFirmaGrafica!=0 && !listPagesToConvert.contains(paginaFirmaGrafica))
						listPagesToConvert.add(paginaFirmaGrafica);
					daStaticizzare = true;
				} 

				if(!daStaticizzare){
					removeField(pdDoc, fullFieldName);
				}
			}
			
			if(daStaticizzare){
				boolean isEditable=false;
				boolean containXForms=false;
				try {
					PdfBean pdfBean = PdfEditabiliUtil.isPdfEditable( file );
					if( pdfBean.getEditable()!=null  ){
						isEditable = pdfBean.getEditable();
					} 
					if( pdfBean.getContainXForm()!=null ){
						containXForms = pdfBean.getContainXForm();
					} 
				} catch (Exception e1) {
					log.error(e1);
				} finally {
					
				}
			
				// staticizzo solo se il file e' editabile e non contiene xform
				if(isEditable && !containXForms){
					try {
						fileinternalExtr = PdfEditabiliUtil.staticizzaPdfEditabile( file );
						log.debug(" - File staticizzato: " + fileinternalExtr );
					} catch (Exception e) {
						log.error(" Errore nella staticizzazione del file in input ", e);
					}
				} else {
					fileinternalExtr = PdfUtil.rewriteFile(fileinternal, listPagesToConvert );
				}
			} else {
				FileOutputStream fos = new FileOutputStream(fileinternalExtr);
				//PdfStamper stamper = new PdfStamper(reader, fos);
				//stamper.close();
				pdDoc.save( fos );
				//reader.close();
				fos.close();
			}

			FileUtil.deleteFile(fileinternal);
			log.debug("Ho cancellato " + fileinternal);
			log.debug("Restituisco lo stream ");

			return new DeleteOnCloseFileInputStream(fileinternalExtr);

			// return FileUtils.openInputStream(fileinternalExtr1);
			// return in;
		} catch (Exception e) {
			log.error("Error getUnsignedContent di PdfSigner ", e);
		} finally {
			if( pdDoc!=null ){
				try {
					pdDoc.close();
				} catch (IOException e) {
					log.error("Error getUnsignedContent di PdfSigner ", e);
				}
			}
		}
		return null;
	}
	
	private PDField removeField(PDDocument document, String fullFieldName) throws IOException {
	    PDDocumentCatalog documentCatalog = document.getDocumentCatalog();
	    PDAcroForm acroForm = documentCatalog.getAcroForm();

	    if (acroForm == null) {
	        System.out.println("No form defined.");
	        return null;
	    }

	    PDField targetField = null;

	    for (PDField field : acroForm.getFieldTree()) {
	        if (fullFieldName.equals(field.getFullyQualifiedName())) {
	            targetField = field;
	            break;
	        }
	    }
	    if (targetField == null) {
	        System.out.println("Form does not contain field with given name.");
	        return null;
	    }

	    PDNonTerminalField parentField = targetField.getParent();
	    System.out.println("parentField " +parentField);
	    if (parentField != null) {
	        List<PDField> childFields = parentField.getChildren();
	        boolean removed = false;
	        for (PDField field : childFields)
	        {
	            if (field.getCOSObject().equals(targetField.getCOSObject())) {
	                removed = childFields.remove(field);
	                parentField.setChildren(childFields);
	                break;
	            }
	        }
	        if (!removed)
	            System.out.println("Inconsistent form definition: Parent field does not reference the target field.");
	    } else {
	        List<PDField> rootFields = acroForm.getFields();
	        boolean removed = false;
	        for (PDField field : rootFields)
	        {
	            if (field.getCOSObject().equals(targetField.getCOSObject())) {
	                removed = rootFields.remove(field);
	                break;
	            }
	        }
	        if (!removed)
	            System.out.println("Inconsistent form definition: Root fields do not include the target field.");
	    }

	    removeWidgets(targetField);

	    return targetField;
	}

	void removeWidgets(PDField targetField) throws IOException {
	    
		if (targetField instanceof PDTerminalField) {
	        
			List<PDAnnotationWidget> widgets = ((PDTerminalField)targetField).getWidgets();
	        for (PDAnnotationWidget widget : widgets) {
	            PDPage page = widget.getPage();
	            if (page != null) {
	                List<PDAnnotation> annotations = page.getAnnotations();
	                boolean removed = false;
	                for (PDAnnotation annotation : annotations) {
	                    if (annotation.getCOSObject().equals(widget.getCOSObject()))
	                    {
	                        removed = annotations.remove(annotation);
	                        break;
	                    }
	                }
	                if (!removed)
	                    System.out.println("Inconsistent annotation definition: Page annotations do not include the target widget.");
	            } else {
	                System.out.println("Widget annotation does not have an associated page; cannot remove widget.");
	                // TODO: In this case iterate all pages and try to find and remove widget in all of them
	            }
	        }
	    } else if (targetField instanceof PDNonTerminalField) {
	        List<PDField> childFields = ((PDNonTerminalField)targetField).getChildren();
	        for (PDField field : childFields)
	            removeWidgets(field);
	    } else {
	        System.out.println("Target field is neither terminal nor non-terminal; cannot remove widgets.");
	    }
	}

	public boolean canContentBeSigned() {
		return false;
	}

	public Collection<CRL> getEmbeddedCRLs(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<? extends Certificate> getEmbeddedCertificates(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	private ValidationInfos validateTimeStamp(TimeStampToken timeStampToken, byte[] signature){
		ValidationInfos validationInfos = new ValidationInfos();
		String hashAlgOID = timeStampToken.getTimeStampInfo().getMessageImprintAlgOID().getId();
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(hashAlgOID);
			TimeStampRequestGenerator gen = new TimeStampRequestGenerator();
			TimeStampRequest request = gen.generate(hashAlgOID, digest.digest(signature));

			checkTimeStampTokenOverRequest(validationInfos, timeStampToken, request);

		} catch (NoSuchAlgorithmException e) {
			log.error(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED);
			validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED,
					MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, hashAlgOID));
		}
		
		return validationInfos;
	}
	
	protected void checkTimeStampTokenOverRequest(ValidationInfos validationInfos, TimeStampToken timestamptoken, TimeStampRequest request) {

		try {

			PKIStatusInfo paramPKIStatusInfo = new PKIStatusInfo(PKIStatus.granted); // Assomiglia tanto a come era prima quando gli apssavo 0

			ASN1InputStream aIn = new ASN1InputStream(timestamptoken.getEncoded());
			ASN1Sequence seq = (ASN1Sequence) aIn.readObject();
			ContentInfo paramContentInfo = new ContentInfo(seq);
			TimeStampResp tsr = new TimeStampResp(paramPKIStatusInfo, paramContentInfo);
			TimeStampResponse response = new TimeStampResponse(tsr);

			checkTimeStampRequestOverTimeStampResponse(validationInfos, timestamptoken, request, response);

		} catch (IOException e) {
			log.debug(MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
			validationInfos.addErrorWithCode(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK,
					MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
		} catch (TSPException e) {
			log.debug(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
			validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR, MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
		}
	}
	
	private void checkTimeStampRequestOverTimeStampResponse(ValidationInfos validationInfos, TimeStampToken timestamptoken, TimeStampRequest request,
			TimeStampResponse response) {

		String digestAlgorithmOID = null;
		/*
		 * Verifica che la marca temporale sia effettivamente associata alla request
		 */
		try {
			response.validate(request);
		} catch (TSPException e) {
			//e.printStackTrace();
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
			validationInfos.addErrorWithCode(MessageConstants.GM_GENERIC_ERROR, MessageHelper.getMessage(MessageConstants.GM_GENERIC_ERROR, e.getMessage()));
		}

		/*
		 * Occorre quindi verificare che il timestamp sia stato effettivamente calcolato a partire dall'impronta del file in ingresso, cioè:
		 * SignerInfo.digestAlgorithm(ContentInfo.Content / ContentInfo.signedAttributes) = SignerInfo.signaturealgorithm^-1(SignerInfo.cid.publickey,
		 * SignerInfo.Signature)
		 */
		try {

			CMSSignedData cms = timestamptoken.toCMSSignedData();
			// MODIFICA ANNA 1.53
			// CertStore certStore = timestamptoken.getCertificatesAndCRLs("Collection", "BC");
			// Collection<?> saCertificates = (Collection<?>)certStore.getCertificates(null);
			Store certStore = cms.getCertificates();
			Collection<?> saCertificates = (Collection<?>) certStore.getMatches(null);
			if (saCertificates == null)
				throw new Exception("Il certificato di TSA non risulta presente");

			Object certificate = saCertificates.iterator().next();
			if (certificate == null)
				throw new Exception("Il certificato di TSA non risulta presente");

			PublicKey publicKey = null;
			if (certificate instanceof X509CertificateHolder) {
				Certificate certificateM = new JcaX509CertificateConverter().setProvider(BouncyCastleProvider.PROVIDER_NAME)
						.getCertificate((X509CertificateHolder) certificate);
				publicKey = certificateM.getPublicKey();
			} else {
				publicKey = ((Certificate) certificate).getPublicKey();
			}

			if (publicKey == null)
				throw new Exception("La publicKey della TSA non risulta presente");

			Collection<SignerInformation> signers = (Collection<SignerInformation>) cms.getSignerInfos().getSigners();
			SignerInformation signerInfo = signers.iterator().next();
			digestAlgorithmOID = signerInfo.getDigestAlgOID();
			MessageDigest contentDigestAlgorithm = MessageDigest.getInstance(digestAlgorithmOID);

			/*
			 * I due byte array da verificare
			 */
			byte[] encodedDataToVerify = null;
			byte[] encodedSignedData = null;

			/*
			 * Verifica che il certificato sia corretto ripetto al firmatario - la public key � correttamente associata al contenuto firmato
			 */
			boolean certificateVerified = false;

			if (signerInfo.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(publicKey))) {
				certificateVerified = true;
			}
			CMSProcessable signedContent = cms.getSignedContent();
			byte[] originalContent = (byte[]) signedContent.getContent();

			log.debug("originalContent.length: " + originalContent.length + " originalContent: " + SignerUtil.asHex(originalContent));

			/*
			 * Controllo se occorre calcolare il digest dell'eContent oppure degli attributi firmati
			 */
			byte[] encodedSignedAttributes = signerInfo.getEncodedSignedAttributes();
			if (encodedSignedAttributes != null) {
				encodedDataToVerify = contentDigestAlgorithm.digest(encodedSignedAttributes);
			} else {
				encodedDataToVerify = contentDigestAlgorithm.digest((byte[]) cms.getSignedContent().getContent());
			}

			log.debug("encodedDataToVerify: " + SignerUtil.asHex(encodedDataToVerify));

			// Hash dell'econtent (da confrontare con l'hash del TSTInfo)
			byte[] contentDigest = signerInfo.getContentDigest();
			/*
			 * FIXME: tstInfo.getEncoded() è stato sostituito con getSignedContent().getContent() poich� nelle m7m la chiamata restituisce un errore - occorre
			 * verificare che i due metodi restituiscano lo stesso oggetto (attualmente la mancata verifica viene segnalata solo come warning)
			 */
			// TSTInfo tstInfo = timestamptoken.getTimeStampInfo().toTSTInfo();
			// byte[] tstInfoEncoded = contentDigestAlgorithm.digest(tstInfo.getEncoded());
			byte[] tstInfoEncoded = contentDigestAlgorithm.digest((byte[]) cms.getSignedContent().getContent());
			boolean contentVerified = Arrays.constantTimeAreEqual(contentDigest, tstInfoEncoded);

			digestAlgorithmOID = signerInfo.getEncryptionAlgOID();
			byte[] signature = signerInfo.getSignature();
			Cipher cipher = null;
			try {
				String algorithmName = null;
				if (PKCSObjectIdentifiers.rsaEncryption.getId().equals(digestAlgorithmOID))
					algorithmName = "RSA/ECB/PKCS1Padding";
				else if (PKCSObjectIdentifiers.sha1WithRSAEncryption.getId().equals(digestAlgorithmOID))
					algorithmName = "RSA/ECB/PKCS1Padding";
				else
					algorithmName = digestAlgorithmOID;
				cipher = Cipher.getInstance(algorithmName);
			} catch (Throwable e) {

			}
			if (cipher != null) {
				try {
					log.debug("Cipher: " + cipher.getAlgorithm());
					cipher.init(Cipher.DECRYPT_MODE, publicKey);
					byte[] decryptedSignature = cipher.doFinal(signature);

					ASN1InputStream asn1is = new ASN1InputStream(decryptedSignature);
					ASN1Sequence asn1Seq = (ASN1Sequence) asn1is.readObject();

					Enumeration<? extends ASN1Primitive> objs = asn1Seq.getObjects();
					while (objs.hasMoreElements()) {
						ASN1Primitive derObject = objs.nextElement();
						if (derObject instanceof ASN1OctetString) {
							ASN1OctetString octectString = (ASN1OctetString) derObject;
							encodedSignedData = octectString.getOctets();
							break;
						}
					}
					log.debug("encodedSignedData: " + SignerUtil.asHex(encodedSignedData));
					boolean signatureVerified = Arrays.constantTimeAreEqual(encodedSignedData, encodedDataToVerify);

					log.debug("Verifica timestampToken: certificateVerified = " + certificateVerified + ", signatureVerified=" + signatureVerified
							+ ", contentVerified=" + contentVerified);
					if (!certificateVerified) {
						validationInfos.addErrorWithCode(CertMessage.CERT_NOTVALID, MessageHelper.getMessage(CertMessage.CERT_NOTVALID));
						validationInfos.addError(MessageHelper.getMessage(CertMessage.CERT_NOTVALID));
					}
					if (!signatureVerified) {
						validationInfos.addErrorWithCode(MessageConstants.SIGN_HASH_ERROR, MessageHelper.getMessage(CertMessage.SIGN_HASH_ERROR,
								SignerUtil.asHex(encodedDataToVerify), SignerUtil.asHex(encodedSignedData)));
						validationInfos.addError(MessageHelper.getMessage(CertMessage.SIGN_HASH_ERROR, SignerUtil.asHex(encodedDataToVerify),
								SignerUtil.asHex(encodedSignedData)));
					}
					if (!contentVerified) {
						validationInfos.addWarning(MessageHelper.getMessage(MessageConstants.SIGN_SIGNEDCONTENT_WARNING, SignerUtil.asHex(tstInfoEncoded),
								SignerUtil.asHex(contentDigest)));
						validationInfos.addWarningWithCode(MessageConstants.SIGN_SIGNEDCONTENT_WARNING, MessageHelper
								.getMessage(MessageConstants.SIGN_SIGNEDCONTENT_WARNING, SignerUtil.asHex(tstInfoEncoded), SignerUtil.asHex(contentDigest)));
					}
				} catch (Exception e) {
					validationInfos.addWarning(MessageConstants.SIGN_MARK_VALIDATION_ERROR);// , MessageHelper.getMessage(
																							// MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage() ) );
					validationInfos.addWarningWithCode(MessageConstants.SIGN_MARK_VALIDATION_ERROR,
							MessageHelper.getMessage(MessageConstants.SIGN_MARK_VALIDATION_ERROR));
				}
			}
		} catch (IOException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK,
					MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_TOKEN_WITHOUT_VALIDMARK));
		} catch (NoSuchAlgorithmException e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED,
					MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, digestAlgorithmOID));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_ALGORITHM_NOTSUPPORTED, digestAlgorithmOID));
		} catch (Exception e) {
			validationInfos.addErrorWithCode(MessageConstants.SIGN_MARK_VALIDATION_ERROR,
					MessageHelper.getMessage(MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage()));
			validationInfos.addError(MessageHelper.getMessage(MessageConstants.SIGN_MARK_VALIDATION_ERROR, e.getMessage()));
		}
	}
	
}