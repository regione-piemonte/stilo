package it.eng.client.applet.thread;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AuthProvider;
import java.security.ProviderException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.exception.SmartCardAuthorizationException;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.BaseSigner;
import it.eng.client.applet.operation.Factory;
import it.eng.client.applet.operation.FactorySigner;
import it.eng.client.applet.operation.ISigner;
import it.eng.client.applet.operation.PDFSigner;
import it.eng.client.applet.panel.PanelSign;
import it.eng.client.applet.util.CommonNameUtils;
import it.eng.client.applet.util.FileUtility;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.client.applet.util.WSClientUtils;
import it.eng.common.LogWriter;
import it.eng.common.bean.FileBean;
import it.eng.common.bean.HashFileBean;
import it.eng.common.bean.ValidationInfos;
import it.eng.common.type.SignerType;
import sun.security.pkcs11.SunPKCS11;

public class SignerThread extends Thread{


	DocumentBuilderFactory documentBuilderFactory;

	private static final String wsPostPrototype = "" +
	"<soapenv:Envelope " +
	"	xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
	"	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
	"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +
	"	<soapenv:Body>" +
	"		<authorize xmlns=\"http://ws.server.eng.it\">" +
	"			<certificate>%1$s</certificate>" +
	"		</authorize>" +
	"	</soapenv:Body>" +
	"</soapenv:Envelope>";

	//private SmartCard card;
	private PanelSign panelSign;
	private List<HashFileBean> hashfilebean;
	private List<FileBean> fileBeanList;

	private SignerType tipoFirma;
	private String modalitaFirma;
	private char[] pin;
	private String alias;
	private int slot;
	private JProgressBar bar;
	private boolean timemark=false;//se apporre la marca alla firma
	private boolean counterSign=false;//se controfirmare 

	public SignerThread(PanelSign panelSign, List<HashFileBean> hashfilebean, List<FileBean> fileBeanList,
			SignerType type, String modalitaFirma, char[] pin, String alias,
			JProgressBar bar,int slot, boolean timemark,boolean counterSign) {
		this.panelSign = panelSign;
		this.hashfilebean = hashfilebean;
		this.fileBeanList = fileBeanList;
		this.tipoFirma = type;
		this.modalitaFirma = modalitaFirma;
		this.pin =pin;
		this.alias = alias;
		this.bar = bar;
		if( this.hashfilebean!=null && this.hashfilebean.size()>0)
			this.bar.setMaximum( this.hashfilebean.size() );
		if( this.fileBeanList!=null && this.fileBeanList.size()>0)
			this.bar.setMaximum( this.fileBeanList.size() );
		this.bar.setMinimum(0);
		this.slot = slot;
		this.timemark=timemark;
		this.counterSign=counterSign;
	}

	@Override
	public void run() {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();

		Security.addProvider(new BouncyCastleProvider());

		panelSign.signStarted();

		bar.setVisible( true );
		bar.setStringPainted( true );
		bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADING));

//		AbstractSigner[] lAbstractSigners = null;
//		try {
//			lAbstractSigners = Factory.getSigner( PreferenceManager.getSignerDigestAlg(), PreferenceManager.getEnvelopeType(), pin, slot, timemark );
//			if (lAbstractSigners == null || lAbstractSigners.length == 0){
//				throw new Exception( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOPROVIDER ) );
//			}
//		} catch (Exception e1) {
//			LogWriter.writeLog("Errore", e1);
//			panelSign.gestisciEccezione( e1.getMessage() );
//			panelSign.signStopped();
//			return;
//		}
		
		List<String> pathProviders = null;
		try {
			pathProviders = Factory.getPathProviders();
			if (pathProviders == null || pathProviders.size() == 0){
				throw new Exception(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOPROVIDER));
			}
		} catch (Exception e1) {
			LogWriter.writeLog("Errore", e1);
			panelSign.gestisciEccezione(e1.getMessage());
			panelSign.signStopped(false);
			return;
		}
			
		List<String> lListException = new ArrayList<String>();
		boolean success = false;
		//for (AbstractSigner absSigner : lAbstractSigners){
		for (String path : pathProviders){
			SunPKCS11 sunProvider;
			AbstractSigner absSigner = null;
			try {
				/*
				 * La seguente istruzione potrebbe generare un CKR_TOKEN_NOT_RECOGNIZED
				 * che � una ProviderException quando fallisce l'inizializzazione
				 * La ProviderException viene gestita silenziosamente.
				 * Il problema sembra essere dovuto dalla dll con la quale si cerca di eseguire tale operazione   
				 */
				sunProvider = Factory.loginProvider(new File(path), slot, pin);
								
				if(sunProvider!=null){
					absSigner = new BaseSigner( PreferenceManager.getSignerDigestAlg(), PreferenceManager.getEnvelopeType(), sunProvider);
				}
				
				if (absSigner == null) {
					throw new Exception(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PROVIDER, "", path ));
				}

				bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADED));
				sunProvider.logout();
								
				PrivateKeyAndCert pkc = null;
				try {
					
					/*
					 * La seguente istruzione potrebbe generare
					 * 
					 * java.io.IOException: load failed
							at sun.security.pkcs11.P11KeyStore.engineLoad(P11KeyStore.java:778)
							at java.security.KeyStore.load(Unknown Source)
							at it.eng.client.applet.operation.BaseSigner.getPrivateKeyAndCertByAlias(BaseSigner.java:93)
							at it.eng.client.applet.thread.SignerThread.run(SignerThread.java:176)
						Caused by: sun.security.pkcs11.wrapper.PKCS11Exception: CKR_FUNCTION_FAILED
							at sun.security.pkcs11.wrapper.PKCS11.C_GetAttributeValue(Native Method)
							at sun.security.pkcs11.P11KeyStore.loadCert(P11KeyStore.java:1183)
							at sun.security.pkcs11.P11KeyStore.mapLabels(P11KeyStore.java:2356)
							at sun.security.pkcs11.P11KeyStore.engineLoad(P11KeyStore.java:770)
							... 3 more
							
							Il problema sembra essere causato da delle dll che generano l'errore a questo punto
					 */
					pkc = absSigner.getPrivateKeyAndCertByAlias(pin, alias);
				}catch (SmartCardException e) {
					LogWriter.writeLog("Errore nell'istruzione getPrivateKeyAndCertByAlias -> Avrebbe generato un CKR_FUNCTION_FAILED");
				}
				
				
				if (pkc == null) {
					/*
					 * TOLTA LA GESTIONE DEL SECONDO PIN.
					 * UNA VOLTA NON SI REITERAVA PIU' VOLTE. ADESSO INVECE VIENE FATTO 
					 */
//					LogWriter.writeLog("Errore nel recupero dei certificati, provo a richiedere il secondo pin");
//					
//					String certPin = null;
//					JPasswordField pf = new JPasswordField();
//					JOptionPane.showMessageDialog(panelSign, pf, "Inserisci il pin della carta", JOptionPane.INFORMATION_MESSAGE);
//
//					certPin = new String(pf.getPassword());
//					listaPKC = absSigner.getPrivateKeyAndCert(pin, certPin.toCharArray() );
//					if (listaPKC==null || listaPKC.length==0)
					
					throw new SmartCardException(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT));
				}
				X509Certificate certificate=pkc.getCertificate();
				
				//controllo MACRO
				boolean codeCheck = PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_CODECHECKENABLED );
				LogWriter.writeLog("Proprietà " + PreferenceKeys.PROPERTY_SIGN_CODECHECKENABLED + "=" + codeCheck );
				if (codeCheck) {
					ValidationInfos macroValInfo = null;
					if( fileBeanList!=null ){
						for (FileBean bean : fileBeanList) {
							try {
								bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_MACROVERIFING));
								macroValInfo = WSClientUtils.checkFileForMacro(bean.getFile());
							} catch(Exception e){
								LogWriter.writeLog("Errore nella verifica macro, il controllo non e' stato eseguito", e);
							}
							if (macroValInfo != null && !macroValInfo.isValid()) {
								throw new SmartCardException("Errore nella verifica macro, " + macroValInfo.getErrorsString());
							} else {
								bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_MACROVERIFIED));
							}
						}
					}
				} else {
					LogWriter.writeLog("Controllo macro non eseguito");
				}
				
				/*
				 * Controllo se il certificato � null o meno.
				 * Il certificato pu� essere null ad esempio se il provider che si sta utilizzando non lo riconosce
				 */
				if(certificate != null) {
					LogWriter.writeLog("Certificato: " + certificate.toString());
					String lStrCommoName = CommonNameUtils.extractCommonName(certificate);
					panelSign.sendCommonName(lStrCommoName);

					ValidationInfos authorizationInfos = preSignFunction(absSigner);
					// controllo di avere le autorizzazioni per procedere alla firma
					if (authorizationInfos.isValid()){
	
						LogWriter.writeLog("authorizationInfos.isValid()");	
						if( hashfilebean != null && !hashfilebean.isEmpty()) {
							LogWriter.writeLog("Modalita' hash");
							boolean ctrlUserVsCertifFirmaDigAttivato = PreferenceManager.getBoolean(PreferenceKeys.PROPERTY_CTRL_USER_VS_CETIF_FIRMA_DIG_ATTIVATO);
							if (ctrlUserVsCertifFirmaDigAttivato) {
								// bar.setString("Operazione in corso");
								bar.setString("");
								panelSign.getTextArea().append("Verifica certificato e firma in corso");
							}
							boolean esitoFirmaComplessiva = true;
							for (int i = 0; i < hashfilebean.size(); i++) {
	
								//Deserializzo l'oggetto
								HashFileBean hash = hashfilebean.get(i);
								
								hash.setIdSmartCard(String.valueOf(pin));
								bar.setValue(i+1);
								if (!ctrlUserVsCertifFirmaDigAttivato) {
									bar.setString("Firma "+/*hash.getFileName() +*/" in corso ("+(i+1)+"/"+ hashfilebean.size() +")");
								}
								LogWriter.writeLog("Firma "+/*hash.getFileName() +*/" in corso ("+(i+1) + "/" + hashfilebean.size() + ")");
								
								//Firmo l'oggetto
								X500Name x500Subject = new JcaX509CertificateHolder(pkc.getCertificate()).getSubject();
								String cnCert = null;
								if (x500Subject != null){
									RDN[] cns = x500Subject.getRDNs(BCStyle.CN);
									if (cns != null && cns.length > 0) {
										RDN cn = cns[0];
										if (cn != null && cn.getFirst() != null) {
											cnCert = IETFUtils.valueToString( cn.getFirst().getValue());
											LogWriter.writeLog("CN subject certificato " + cnCert);
											LogWriter.writeLog("hash.getFirmatario() " + hash.getFirmatario());
											boolean esitoFirma = true;
	//										if( hash.getFirmatario()==null || hash.getFirmatario().equalsIgnoreCase("") || 
	//												!hash.getFirmatario().contains( cnCert ) ) {
												String str = absSigner.signerfile(hash, pkc, pin, timemark);
												//LogWriter.writeLog("hashFileBean " + hash );
												hash.setSignedBean(str);
												hash.setFirmatario(cnCert);
												hash.setSubjectX500Principal(pkc.getCertificate().getSubjectX500Principal() != null ? pkc.getCertificate().getSubjectX500Principal().toString() : "");
												if (str == null) {
													esitoFirma = false;
													LogWriter.writeLog("Il file " + hash.getFileName() + " non e' stato firmato correttamente");
													panelSign.getTextArea().append("File " + /* hash.getFileName() */(i + 1) + " non firmato correttamente\n");
												} else {
													LogWriter.writeLog("Il file " + hash.getFileName() + " e' stato firmato");
													if (!ctrlUserVsCertifFirmaDigAttivato) {
														panelSign.getTextArea().append("File " + /* hash.getFileName() */(i + 1) + " firmato\n");
													}
												}
												esitoFirmaComplessiva = esitoFirma && esitoFirmaComplessiva;
	//										} else {
	//											LogWriter.writeLog("Il file è stato già firmato dal CN subject certificato " );
	//											panelSign.getTextArea().append("Il file " + hash.getFileName() + " è stato già firmato dal CN subject certificato.\n");
	//										}
										}
									}
								}						
							}
	
							if (esitoFirmaComplessiva ) {
								
								/*
								 * Effettuo il download sul client
								 * QUI E' DOVE VIENE ESEGUITA LA CHIAMATA AL CLIENT
								 */
								panelSign.saveOutputFiles(certificate);
								
								success = true;
								
								
	//							String messaggio = panelSign.getTextArea().getText();
	//							if( messaggio!=null && messaggio.equalsIgnoreCase( "si procede alla firma verticale" )){
	//								panelSign.showMessageDialog( Messages.getMessage( MessageKeys.MSG_END_WARNING ), "", JOptionPane.INFORMATION_MESSAGE );
	//							}
	//							else {
	//								panelSign.showMessageDialog( Messages.getMessage( MessageKeys.MSG_OPSUCCESS ), "", JOptionPane.INFORMATION_MESSAGE );
	//							}
								break;
							} else {
	//							String messaggio = panelSign.getTextArea().getText();
	//							panelSign.showMessageDialog( messaggio, "",JOptionPane.INFORMATION_MESSAGE );
							}
	
							//JOptionPane.showMessageDialog( card.getJTabbedPane(),Messages.getMessage( MessageKeys.MSG_OPSUCCESS ),"", JOptionPane.INFORMATION_MESSAGE);
							//JOptionPane.showMessageDialog(card.getJTabbedPane(),"operazione eseguita","", JOptionPane.INFORMATION_MESSAGE);
							//JSObject.getWindow((SmartCard)card).call("addObjectSigner", new Object[]{str});
						} else{
							LogWriter.writeLog("Modalita' file");
							//evito di ricaricare il provider 
							AuthProvider provider = absSigner.getProvider();
							
							LogWriter.writeLog("Controfirma? " + counterSign);
							boolean esitoFirmaComplessiva = true;
							if(fileBeanList != null) {
								int i = 0;
								for(FileBean bean: fileBeanList){
									bar.setValue(i + 1);
									bar.setString("Firma " + " in corso (" + (i + 1) + "/" + fileBeanList.size() + ")");
									LogWriter.writeLog("Firma "+/*bean.getFileName()+*/" in corso (" + (i + 1) + "/" + fileBeanList.size() + ")");
	
									// verifica se hai scelto PDF come busta ma il file non è PDF proponi cades
									if (!bean.getIsPdf() && tipoFirma.equals(SignerType.PDF)) {
										LogWriter.writeLog("cambio il tipo busta a cades bes poiche' il file non e un PDF non posso fare una busta PADES");
										tipoFirma=SignerType.CAdES_BES;
									}
									ISigner signer=FactorySigner.getSigner(tipoFirma);
									if (counterSign) {
										if (!bean.getIsFirmato()) {
											panelSign.getTextArea().append("Controfirma sul file " + bean.getFileName() + " non consentita - file non firmato.\n");
										} else {
											signer=FactorySigner.getSigner( bean.getTipoBusta());
											if (signer instanceof PDFSigner) {
												panelSign.getTextArea().append("Controfirma sul file " + bean.getFileName() + " non consentita - file formato pdf.\n");
											} else {
												//se contro firmi devi mantenere al busta attuale,
												//per cui devi usare il signer corrispondente alal busta
												boolean esitoControFirma = signer.addCounterSignature(bean, pkc, null, provider);
												LogWriter.writeLog("File " + bean.getFileName() + " controfirmato con successo: " + esitoControFirma);
												if (esitoControFirma) {
													panelSign.getTextArea().append("File " + bean.getFileName() + " controfirmato con successo.\n");
												} else {
													panelSign.getTextArea().append("File " + bean.getFileName() + " controfirmato con errore.\n");
												}
												esitoFirmaComplessiva = esitoControFirma && esitoFirmaComplessiva;
											}
										}
									} else {
										LogWriter.writeLog("Firma congiunta? " + bean.isFirmaCongiuntaRequired());
										boolean esitoFirma = signer.firma(bean, pkc, provider, timemark, false, bean.isFirmaCongiuntaRequired(), bean.getTipoBusta() != null);
										LogWriter.writeLog("File " + bean.getFileName() + " firmato con successo: " +esitoFirma);
										if( esitoFirma )
											panelSign.getTextArea().append("File " + (i + 1)/*bean.getFileName()*/ + " firmato con successo.\n");
										else
											panelSign.getTextArea().append("File " + (i + 1)/*bean.getFileName()*/ + " firmato con errore.\n");
										esitoFirmaComplessiva = esitoFirma && esitoFirmaComplessiva ;
									}
									i++;
								}
							}
							try{
								provider.logout();
							}catch(Exception e){
								LogWriter.writeLog("Errore", e);
							}
							if (esitoFirmaComplessiva) {
								panelSign.saveOutputFiles(certificate);
								if( fileBeanList!=null){
									for (FileBean bean : fileBeanList) {
										FileUtility.deleteTempDirectory(bean.getRootFileDirectory());
									}
								}
	
								String messaggio = panelSign.getTextArea().getText();
								if (messaggio != null && messaggio.equalsIgnoreCase("si procede alla firma verticale")) {
									panelSign.showMessageDialog(Messages.getMessage(MessageKeys.MSG_END_WARNING ), "", JOptionPane.INFORMATION_MESSAGE);
								}
								else {
									panelSign.showMessageDialog(Messages.getMessage(MessageKeys.MSG_OPSUCCESS), "", JOptionPane.INFORMATION_MESSAGE);
								}
								success = true;
								break;
								//	JOptionPane.showMessageDialog( card.getJTabbedPane(),
								//	Messages.getMessage( MessageKeys.MSG_OPSUCCESS ), "", JOptionPane.INFORMATION_MESSAGE);
							}
							else {
								String messaggio = panelSign.getTextArea().getText();
								panelSign.showMessageDialog(messaggio, "", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					} else{
						throw new Exception("Il certificato di firma fornito non e' quello associato all'utenza di lavoro: " + authorizationInfos.getErrorsString());
					}
					//Avviso il client che ho firmato tutti gli hash della pagina
					//JSObject.getWindow((SmartCard)card).eval("endObjectSigner();");
				}
			} catch(SmartCardException e){
				LogWriter.writeLog("Errore", e);
				lListException.add("Errore " + e.getMessage());
			} catch(ProviderException e){
				LogWriter.writeLog("Errore nel login al provider -> Factory.loginProvider. Verrebbe generato un CKR_TOKEN_NOT_RECOGNIZED");
			} catch(Exception e){
				LogWriter.writeLog("Errore", e);
				lListException.add("Errore " + e.getMessage());
				String exceptionMessage = new SmartCardException(e).getMessage();
				if (exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINLOCKED))) {
					//"Pin bloccato!";
					panelSign.gestisciEccezione(exceptionMessage);
					panelSign.signStopped(success);
					break; 
					/*
					 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato,
					 * al termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata
					 * Inserendo questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
					 */
				} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_TOKENNOTPRESENT))) {
					//Smart Card non presente!";
					panelSign.gestisciEccezione(exceptionMessage);
					panelSign.signStopped(success);
					break; 
					/*
					 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato,
					 * al termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata
					 * Inserendo questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
					 */
				} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PININVALID))) {
					//"Pin invalido!";
					panelSign.gestisciEccezione(exceptionMessage);
					panelSign.signStopped(success);
					break; 
					/*
					 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato,
					 * al termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata
					 * Inserendo questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
					 */
				} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PININCORRECT))){
					//"Pin non corretto!";
					panelSign.gestisciEccezione(exceptionMessage);
					panelSign.signStopped(success);
					break; 
					/*
					 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato,
					 * al termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata
					 * Inserendo questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
					 */
				} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_PINEXPIRED))){
					//"Pin scaduto!";
					panelSign.gestisciEccezione(exceptionMessage);
					panelSign.signStopped(success);
					break; 
					/*
					 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato,
					 * al termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata
					 * Inserendo questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
					 */
				} else {
					exceptionMessage = e.getMessage();
				}
				
			} catch (Throwable e) {
				LogWriter.writeLog("Errore", e);
				lListException.add("Errore " + e.getMessage());
				break; 
				/*
				 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato,
				 * al termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata
				 * Inserendo questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
				 */
			}
		}
		if (!success) {
			String exceptionMessage = "";
			for (String lString : lListException) {
				exceptionMessage += "\n" + lString ;
			}
			panelSign.gestisciEccezione(exceptionMessage);
		}
		panelSign.signStopped(success);
	}



	private byte[] calcolaHash(InputStream is, String digestId)throws Exception{
		LogWriter.writeLog("----" + digestId);
		if(digestId.equals(CMSSignedDataGenerator.DIGEST_SHA256)){
			return DigestUtils.sha256(is);
		}else  {
			return DigestUtils.sha(is);
		}
	}


	// Controlla la validita' del certificato del 
	// firmatario rispetto al'identita' dell'utente
	private ValidationInfos preSignFunction(AbstractSigner signer) throws SmartCardAuthorizationException{
		ValidationInfos authorizationInfos = new ValidationInfos();

		String wsAuth = PreferenceManager.getString( PreferenceKeys.PROPERTY_SIGN_ENVELOPEWSAUTH );
		if (wsAuth!=null && !"".equals(wsAuth.trim())){ 
			try{
				URL endPoint = new URL(wsAuth); 
				X509Certificate[] certificates = signer.getSigningCertificates(pin);
				if (certificates==null || certificates.length==0){
					throw new SmartCardException( Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT ));
				}
				Base64Encoder encoder = new Base64Encoder();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] certificateEncoded = certificates[0].getEncoded();
				encoder.encode(certificateEncoded, 0, certificateEncoded.length, baos);
				String certificateEncodedB64 = new String(baos.toByteArray());

				String request = String.format(wsPostPrototype, certificateEncodedB64);

				HttpURLConnection connection= (HttpURLConnection)endPoint.openConnection();
				connection.setRequestProperty("SOAPAction", "");
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				PrintWriter out = new PrintWriter(connection.getOutputStream());
				out.print(request);
				out.close();
				InputStream is = null;
				Document document = null;
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				try{
					connection.connect();
					is = connection.getInputStream();
					document = documentBuilder.parse(is);
				} finally{
					if (is!=null)
						is.close();
				}

				Element root = document.getDocumentElement();
				NodeList authorizeReturnList = root.getElementsByTagName("authorizeReturn");
				if (authorizeReturnList==null || authorizeReturnList.getLength()!=1)
					throw new  SmartCardAuthorizationException( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOAUTHTOSIGN ) );
				Element authorizeReturnElement = (Element)authorizeReturnList.item(0);
				NodeList authorizationInfosList = authorizeReturnElement.getElementsByTagName("authorizationInfos");
				if (authorizationInfosList==null || authorizationInfosList.getLength()!=1)
					throw new SmartCardAuthorizationException( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOAUTHTOSIGN  ) );
				Element authorizationInfosElement = (Element)authorizationInfosList.item(0);
				authorizationInfos = parseAuthorizationInfos(authorizationInfosElement);

			} catch(Exception e){
				throw new SmartCardAuthorizationException( e.getMessage() );
			}
		}
		return authorizationInfos;	
	}

	/*
	 * La struttura XML della response del WS e' la seguente:
	 *  <authorizationInfos>
	 *    <errors>
	 *       <errors>Errore 1</errors>
	 *       <errors>Errore 2</errors>
	 *     </errors>
	 *     <valid>false</valid>
	 *     <warnings>
	 *       <warnings>Warning 1</warnings>
	 *       <warnings>Warning 2</warnings>
	 *     </warnings>
	 *   </authorizationInfos>
	 */
	private ValidationInfos parseAuthorizationInfos(
			Element authorizationInfosElement) throws SmartCardAuthorizationException{
		ValidationInfos validationInfos = new ValidationInfos();
		Element errorsElement = getChildByTagName(authorizationInfosElement, "errors");
		Element warningsElement = getChildByTagName(authorizationInfosElement, "warnings");

		// Aggiungo gli errori
		if (errorsElement!=null){
			NodeList errorsList = errorsElement.getElementsByTagName("errors");
			if (errorsList!=null && errorsList.getLength()!=0){
				for (int i=0; i<errorsList.getLength(); i++){
					Element errorElement = (Element)errorsList.item(i);
					validationInfos.addError(errorElement.getNodeValue());
				}
			}
		}

		// Aggiungo i warning
		if (warningsElement!=null){
			NodeList warningsList = errorsElement.getElementsByTagName("warnings");
			if (warningsList!=null && warningsList.getLength()!=0){
				for (int i=0; i<warningsList.getLength(); i++){
					Element warningElment = (Element)warningsList.item(i);
					validationInfos.addWarning(warningElment.getNodeValue());
				}
			}
		}

		return validationInfos;
	}

	public String convertStreamToString(InputStream is)
	throws IOException {
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
				"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public Element getChildByTagName(Element parent, String name) {
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child instanceof Element && name.equals(child.getNodeName())) {
				return (Element) child;
			}
		}
		return null;
	}


}