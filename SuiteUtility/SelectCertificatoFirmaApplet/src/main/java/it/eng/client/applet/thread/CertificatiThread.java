package it.eng.client.applet.thread;

import java.io.File;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JProgressBar;
import javax.xml.parsers.DocumentBuilderFactory;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.i18N.MessageKeys;
import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.AbstractSigner;
import it.eng.client.applet.operation.BaseSigner;
import it.eng.client.applet.operation.Factory;
import it.eng.client.applet.panel.PanelCertificati;
import it.eng.client.applet.util.CertificateUtils;
import it.eng.common.LogWriter;
import it.eng.common.bean.Firmatario;
import it.eng.common.bean.ValidationInfos;
import sun.security.pkcs11.SunPKCS11;

public class CertificatiThread extends Thread {

	DocumentBuilderFactory documentBuilderFactory;
	private PanelCertificati panelCert;
	private char[] pin;
	private int slot;
	private JProgressBar bar;

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
			LogWriter.writeLog("Errore", e1);
			panelCert.gestisciEccezione(e1.getMessage());
			panelCert.searchStopped(false, null, null, null, null); // Ritorno a PanelCertificati indicando che si è terminata l'elaborazione
			return;
		}

		List<String> lListException = new ArrayList<String>();
		// for (AbstractSigner absSigner : lAbstractSigners){

		ArrayList<Firmatario> firmatari = new ArrayList<Firmatario>(); // lista che conterrà i firmatari individuati
		boolean errorFlag = false;

		// Per ogni provider presente
		boolean certificatiTrovati = false;
		for (String path : pathProviders) {
			if( !certificatiTrovati){
				SunPKCS11 sunProvider;
				AbstractSigner absSigner = null;
				try {
					sunProvider = Factory.loginProvider(new File(path), slot, pin);
					if (sunProvider != null) {
						absSigner = new BaseSigner(sunProvider);
					}
	
					if (absSigner == null) {
						throw new Exception(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PROVIDER, "", path));
					}
	
					bar.setString(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_PROVIDERLOADED));
					sunProvider.logout();
	
					PrivateKeyAndCert[] listaPKC = null;
	
					listaPKC = absSigner.getPrivateKeyAndCert(pin, pin);
	
					if (listaPKC == null || listaPKC.length == 0) {
						throw new SmartCardException(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT));
					}
					// X509Certificate certificate=listaPKC[0].getCertificate();
					// bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_SIGNINGCERTCHEKING ) );
					// ValidationInfos certvalinfo = new ValidationInfos();
					// if( certificate!=null ){
					// certvalinfo = CertificateUtils.checkCertificate( certificate );
					// } else {
					// throw new SmartCardException( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT ) );
					// }
					// if(!certvalinfo.isValid()){
					// throw new SmartCardException( certvalinfo.getErrorsString() );
					// }
					// bar.setString( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_SIGNINGCERTCHEKED ) );
	
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
	
											// panelCert.getTextArea().append( ""+ cnCert+ " - " + alias + "\n");
											Firmatario firmatarioBuff = new Firmatario();
											firmatarioBuff.setFirmatario(cnCert);
											firmatarioBuff.setAlias(alias);
	
											firmatari.add(firmatarioBuff); // Aggiungo alla lista il firmatario individuato
	
											LogWriter.writeLog("CERTIFICATO TROVATO");
											certificatiTrovati = true;
											i++;
										}
									}
								}
							}
						} else {
							// throw new SmartCardException( Messages.getMessage( MessageKeys.MSG_FIRMAMARCA_ERROR_NOSIGNINGCERT ) );
						}
					} // End for
	
					// if( listaPKC!=null ){
					//
					// //CODIFICO IN BASE 64 DELLE INFORMAZIONI
					//// byte[] str = SerializationUtils.serialize(firmatari);
					//// ret = Base64.encodeBase64String(str);
					//
					//
					//// panelCert.saveOutput( new String(pin), ret );
					// panelCert.searchStopped(true, new String(pin), ret); //Ritorno a PanelCertificati indicando che si è terminata l'elaborazione
					// }
					// break;
	
				} catch (SmartCardException e) {
					LogWriter.writeLog("Errore", e);
					lListException.add("Errore " + e.getMessage());
					errorFlag = true;
				} catch (Exception e) {
					LogWriter.writeLog("Errore", e);
					lListException.add("Errore " + e.getMessage());
					String exceptionMessage = new SmartCardException(e).getMessage();
					if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PINLOCKED))) {
						// "Pin bloccato!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break è stato inserito perchè altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_TOKENNOTPRESENT))) {
						// Smart Card non presente!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break è stato inserito perchè altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PININVALID))) {
						// "Pin invalido!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break è stato inserito perchè altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PININCORRECT))) {
						// "Pin non corretto!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break è stato inserito perchè altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else if (exceptionMessage.equalsIgnoreCase(Messages.getMessage(MessageKeys.MSG_FIRMAMARCA_ERROR_PINEXPIRED))) {
						// "Pin scaduto!";
						panelCert.gestisciEccezione(exceptionMessage);
						errorFlag = true;
						break;
						/*
						 * Il break è stato inserito perchè altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al
						 * termine di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo
						 * questa istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
						 */
					} else {
						exceptionMessage = e.getMessage();
					}
	
				} catch (Throwable e) {
					LogWriter.writeLog("Errore", e);
					lListException.add("Errore " + e.getMessage());
					errorFlag = true;
					break;
					/*
					 * Il break è stato inserito perchè altrimenti in caso venga generata un'eccezione, ad esempio quando viene inserito un pin errato, al termine
					 * di questo ramo ciclerebbe nuovamente fino ad eseguire i 3 tentativi che porterebbero la chiavetta ad essere bloccata Inserendo questa
					 * istruzione, invece, esce dal ramo e non esegue i tentativi continuamente
					 */
				}
			}

		} // end for (String path : pathProviders)

		LogWriter.writeLog("Numero di firmatari trovati: " + firmatari.size());

		if (firmatari.size() > 1) {
			// Nel caso ci sia più di un certificato
			panelCert.addComboCertificati(new String(pin), firmatari);
		} else if (firmatari.size() == 1) {
			// Nel caso ci sia un unico firmatario
			
			// Ottengo i dati dell'unico firmatario inserito
			String firmatario = firmatari.get(0).getFirmatario();
			String alias = firmatari.get(0).getAlias();
			
			panelCert.showSignButton(new String(pin), firmatario, alias);
			
//			if ((!"".equals(new String(pin))) && (!"".equals(firmatario)) && (!"".equals(alias))) {
//				// Ritorno alla callback i valori del certificato ottenuti
//				panelCert.searchStopped(true, new String(pin), firmatario, alias); // Ritorno a PanelCertificati indicando che si è terminata l'elaborazione
//			}
		} else if (!errorFlag) // Se non c'è stato nessun errore fino a questo punto
		{
			// Nel caso non ci sia nessun certificato valido
			panelCert.searchStopped(false, null, null, null, null); // Ritorno a PanelCertificati indicando che si è terminata l'elaborazione
		}
	}

}
