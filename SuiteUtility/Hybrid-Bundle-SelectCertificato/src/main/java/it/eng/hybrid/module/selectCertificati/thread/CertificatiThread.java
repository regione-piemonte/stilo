package it.eng.hybrid.module.selectCertificati.thread;

import java.io.File;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;
import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.eng.common.bean.Firmatario;
import it.eng.common.bean.ValidationInfos;
import it.eng.hybrid.module.selectCertificati.bean.PrivateKeyAndCert;
import it.eng.hybrid.module.selectCertificati.exception.SmartCardException;
import it.eng.hybrid.module.selectCertificati.messages.MessageKeys;
import it.eng.hybrid.module.selectCertificati.messages.Messages;
import it.eng.hybrid.module.selectCertificati.tools.AbstractSigner;
import it.eng.hybrid.module.selectCertificati.tools.BaseSigner;
import it.eng.hybrid.module.selectCertificati.tools.CertificateUtils;
import it.eng.hybrid.module.selectCertificati.tools.Factory;
import it.eng.hybrid.module.selectCertificati.ui.PanelCertificati;
import sun.security.pkcs11.SunPKCS11;

public class CertificatiThread extends Thread {

	public final static Logger logger = Logger.getLogger(CertificatiThread.class);

	DocumentBuilderFactory documentBuilderFactory;
	private PanelCertificati panelCert;
	private char[] pin;
	private int slot;
	private JProgressBar bar;
	private SunPKCS11 sunProvider;

	public CertificatiThread(PanelCertificati panelCert, char[] pin, JProgressBar bar, int slot) {
		this.panelCert = panelCert;
		this.pin = pin;
		this.bar = bar;
		this.bar.setMinimum(0);
		this.slot = slot;
	}

	@Override
	public void run() {

		documentBuilderFactory = DocumentBuilderFactory.newInstance();

		Security.addProvider(new BouncyCastleProvider());

		panelCert.searchStarted();

		bar.setVisible(true);
		bar.setStringPainted(true);
		bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADING));

		List<String> pathProviders = null;
		try {
			pathProviders = Factory.getPathProviders();
			if (pathProviders == null || pathProviders.size() == 0) {
				throw new Exception(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOPROVIDER));
			}
		} catch (Exception e1) {
			logger.info("Errore", e1);
			panelCert.gestisciEccezione(e1.getMessage());
			panelCert.searchStopped(false, null, null, null, null); // Ritorno a PanelCertificati indicando che si � terminata l'elaborazione
			return;
		}

		List<String> lListException = new ArrayList<String>();

		ArrayList<Firmatario> firmatari = new ArrayList<Firmatario>(); // lista che conterr� i firmatari individuati
		boolean errorFlag = false;

		// Per ogni provider presente
		boolean certificatiTrovati = false;
		for (String path : pathProviders) {
			if( !certificatiTrovati){
				logger.info("tentativo con il provider: " + path);
				sunProvider = null;
				AbstractSigner absSigner = null;
				try {
					/*
					 * La seguente istruzione potrebbe generare un CKR_TOKEN_NOT_RECOGNIZED
					 * che � una ProviderException quando fallisce l'inizializzazione
					 * La ProviderException viene gestita silenziosamente.
					 * Il problema sembra essere dovuto dalla dll con la quale si cerca di eseguire tale operazione   
					 */
					sunProvider = Factory.loginProvider(new File(path), slot, pin);
					if (sunProvider != null) {
						absSigner = new BaseSigner(sunProvider);
						closeConnectionSunProvider();
						if (absSigner == null) {
							throw new Exception(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PROVIDER, "", path));
						}
	
						bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADED));
						PrivateKeyAndCert[] listaPKC = null;
	
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
							listaPKC = absSigner.getPrivateKeyAndCert(pin, pin);
						} catch (SmartCardException e) {
							logger.error("Errore nell'istruzione getPrivateKeyAndCert -> Avrebbe generato un CKR_FUNCTION_FAILED");
						}
						if (listaPKC == null || listaPKC.length == 0) {
							throw new SmartCardException(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT));
						}
	
						String cnCert = null;
						String alias = null;
						// Firmatario[] firmatari = new Firmatario[listaPKC.length];
						int i = 0;
						ValidationInfos certvalinfo;
						for (PrivateKeyAndCert pkc : listaPKC) {
		
							certvalinfo = new ValidationInfos();
							X509Certificate certificate = pkc.getCertificate();
							bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_SIGNINGCERTCHEKING));
							if (certificate != null) {
								certvalinfo = CertificateUtils.checkCertificate(certificate);
								if (certvalinfo.isValid()) {
									X500Name x500Subject = new JcaX509CertificateHolder(pkc.getCertificate()).getSubject();
									alias = pkc.getAlias();
									if (x500Subject != null) {
										RDN[] cns = x500Subject.getRDNs(BCStyle.CN);
										if (cns != null && cns.length > 0) {
											RDN cn = cns[0];
											if (cn != null && cn.getFirst() != null) {
												cnCert = IETFUtils.valueToString(cn.getFirst().getValue());
		
												Firmatario firmatarioBuff = new Firmatario();
												firmatarioBuff.setFirmatario(cnCert);
												firmatarioBuff.setAlias(alias);
		
												firmatari.add(firmatarioBuff); // Aggiungo alla lista il firmatario individuato
		
												logger.info("CERTIFICATO TROVATO");
												certificatiTrovati = true;
												i++;
											}
										}
									}
								}
							} else {
							}
						} // End for
					} else {
						logger.info("sunProvider e' null");
					}
				} catch (SmartCardException e) {
					logger.info("Errore", e);
					lListException.add("Errore " + e.getMessage());
					errorFlag = true;
				} catch (Exception e) {
					logger.info("Errore", e);
					lListException.add("Errore " + e.getMessage());
					String exceptionMessage = new SmartCardException(e).getMessage();
					if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PINLOCKED))) {
						// "Pin bloccato!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_TOKENNOTPRESENT))) {
						// Smart Card non presente!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PININVALID))) {
						// "Pin invalido!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PININCORRECT))) {
						// "Pin non corretto!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PINEXPIRED))) {
						// "Pin scaduto!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else {
						exceptionMessage = e.getMessage();
					}
	
				} catch (Throwable e) {
					logger.info("Errore", e);
					lListException.add("Errore " + e.getMessage());
					errorFlag = true;
					break;
					/*
					 * Il break � stato inserito perch� altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al termine
					 * di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo questa
					 * istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
					 */
				}
			}

		} // end for (String path : pathProviders)

		logger.info("Numero di firmatari trovati: " + firmatari.size());

		if (firmatari.size() > 1) {
			// Nel caso ci sia pi� di un certificato
			panelCert.addComboCertificati(new String(pin), firmatari);
		} else if (firmatari.size() == 1) {
			// Nel caso ci sia un unico firmatario
			// Ottengo i dati dell'unico firmatario inserito
			String firmatario = firmatari.get(0).getFirmatario();
			String alias = firmatari.get(0).getAlias();
			panelCert.showSignButton(new String(pin), firmatario, alias);
		} else if (!errorFlag) {
			// Se non c'� stato nessun errore fino a questo punto 
			// Nel caso non ci sia nessun certificato valido
			panelCert.searchStopped(false, null, null, null, null); // Ritorno a PanelCertificati indicando che si � terminata l'elaborazione
		} else {
			panelCert.searchStopped(false, null, null, null, null); // Ritorno a PanelCertificati indicando che si � terminata l'elaborazione
		}
	}
	
	private void closeConnectionSunProvider() {
		logger.info("chiudo la connessione con sunProvider");
		try {
			sunProvider.logout();
		} catch (LoginException e) {
			logger.error("Errore durante la chiusura della connessione con sunProvider: " + e.getMessage(), e);
		}
	}

}
