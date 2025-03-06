package it.eng.client.applet.operation;

import it.eng.client.applet.bean.PrivateKeyAndCert;
import it.eng.client.applet.exception.SmartCardException;
import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.client.applet.util.PreferenceKeys;
import it.eng.client.applet.util.PreferenceManager;
import it.eng.common.LogWriter;
import it.eng.common.bean.HashFileBean;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.type.SignerType;

import java.security.AuthProvider;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;

/**
 * Classe Base signer che implementa i metodi base di recupero dati della smartcard
 * @author michele
 *
 */
public class BaseSigner extends AbstractSigner {

	public BaseSigner(HashAlgorithm digest,SignerType signerType,AuthProvider provider) {
		super(digest,signerType,provider);
	}

	public String signerfile(HashFileBean hashFileBean, PrivateKeyAndCert pvc, char[] pin, boolean timemark) throws SmartCardException {
		LogWriter.writeLog("INIZIO FIRMA");	
		String ret = null;
		SignerObjectBean bean = null;
		
		try{
			
			LogWriter.writeLog("signerType " + signerType);	
			if(signerType.equals(SignerType.PDF)){
				if(hashFileBean.getFileType().equals(SignerType.PDF)){
					bean = signerPDF(pvc.getPrivateKey(), pvc.getCertificate(),hashFileBean.getHash());
					bean.setFileName(hashFileBean.getFileName());
					bean.setFileType(SignerType.PDF);
				}else{
					bean = signerP7M(pvc.getPrivateKey(), pvc.getCertificate(), hashFileBean.getHash(), pin, timemark);
					bean.setFileName(hashFileBean.getFileName());
					bean.setFileType(SignerType.P7M);
				}
			}else{
				bean = signerP7M(pvc.getPrivateKey(), pvc.getCertificate(),hashFileBean.getHash(), pin, timemark);
				bean.setFileName(hashFileBean.getFileName());
				bean.setFileType(SignerType.P7M);
			}
				
			if(bean !=null){
				//LogWriter.writeLog("restituisco " + bean );
				bean.setId(hashFileBean.getId());
				byte[] str = SerializationUtils.serialize(bean);
				ret = Base64.encodeBase64String(str);
			}

		} catch(Exception e){
			e.printStackTrace();
			throw new SmartCardException(e);
		}finally{
//			try{
//				provider.logout();
//			}catch(Exception e){
//				//Errore gestito
//			}
		}
		return ret;
	}

	
	@Override
	public X509Certificate[] getSigningCertificates(char[] pin, char[] certPin) throws SmartCardException {
		List<X509Certificate> certificateList = new ArrayList<X509Certificate>();
		try{
			provider.login(new Subject(), new PasswordHandler(pin));

			KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
			keyStore.load(null,null);

			boolean emulation = false;
			if( PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE ) )
				emulation = true;
			
			Enumeration enumeration = keyStore.aliases();
			while(enumeration.hasMoreElements()){
				String alias = enumeration.nextElement().toString();
				LogWriter.writeLog("ALIAS:"+alias);
				X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
				boolean[] usage = certificate.getKeyUsage();
				LogWriter.writeLog("usage[1]:"+usage[1]);
				if (usage[1] || emulation)
					certificateList.add(certificate);
			}
		} catch(Exception e){
			LogWriter.writeLog("Errore ", e);	
			throw new SmartCardException(e);
		} finally{
			try{
				provider.logout();
			} catch(Exception e){}
		}
		return certificateList.toArray(new X509Certificate[certificateList.size()]);
	}
	
	@Override
	public PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin, char[] certPin) throws SmartCardException {
		List<PrivateKeyAndCert> pkcList = new ArrayList<PrivateKeyAndCert>();
		try{
			provider.login(new Subject(), new PasswordHandler(pin));

			KeyStore keyStore = KeyStore.getInstance("PKCS11",provider);
			LogWriter.writeLog("Cerco di recuperare il key store ", true);
			keyStore.load(null,certPin);

//			boolean emulation = false;
//			if( PreferenceManager.getBoolean( PreferenceKeys.PROPERTY_SIGN_EMULATIONMODE ) )
//				emulation = true;
			
			Enumeration enumeration = keyStore.aliases();
			while(enumeration.hasMoreElements()){
				String alias = enumeration.nextElement().toString();
				LogWriter.writeLog("ALIAS:"+alias);
				PrivateKey privateKey = (PrivateKey)keyStore.getKey(alias, certPin); 
				LogWriter.writeLog("recuperata la chiave");
				X509Certificate certificate=(X509Certificate)keyStore.getCertificate(alias);
				boolean[] usage = certificate.getKeyUsage();
//				LogWriter.writeLog("non considero usage");
				if (usage[1]){//|| emulation){
					Date dataScadenza = certificate.getNotAfter();
					Date dataInizioValidita = certificate.getNotBefore();
					Date dataCorrente = new Date();
					String disabilitaControlloCertificatoScaduto = PreferenceManager.getString(PreferenceKeys.PROPERTY_DISABILITA_CONTROLLO_CERTIFICATO_SCADUTO);
					if ((dataCorrente.before(dataScadenza) && dataCorrente.after(dataInizioValidita)) || ((disabilitaControlloCertificatoScaduto != null) && ("true".equalsIgnoreCase(disabilitaControlloCertificatoScaduto)))) {	
						LogWriter.writeLog("Certificato con data valida e bit di non ripudio");
						PrivateKeyAndCert privAndCert=new PrivateKeyAndCert();
						privAndCert.setCertificate(certificate);
		            	privAndCert.setPrivateKey(privateKey);
		            	privAndCert.setChain( keyStore.getCertificateChain(alias));
		            	privAndCert.setValidita(true);
						pkcList.add(privAndCert);
					} else {
						LogWriter.writeLog("Certificato con data non valida e bit di non ripudio");
						PrivateKeyAndCert privAndCert=new PrivateKeyAndCert();
						privAndCert.setCertificate(certificate);
		            	privAndCert.setPrivateKey(privateKey);
		            	privAndCert.setChain( keyStore.getCertificateChain(alias));
		            	privAndCert.setValidita(false);
		            	privAndCert.setMotivoErrore("Certificato scaduto");
						pkcList.add(privAndCert);
					}
					
				} else {
					LogWriter.writeLog("Certificato senza bit di non ripudio");
			}
			}
		} catch(Exception e){
			LogWriter.writeLog("Errore ", e);	
			throw new SmartCardException(e);
		} finally{
//			try{
//				provider.logout();
//			} catch(Exception e){}
		}
		return pkcList.toArray(new PrivateKeyAndCert[pkcList.size()]);
	}

	@Override
	public X509Certificate[] getSigningCertificates(char[] pin)
			throws SmartCardException {
		return getSigningCertificates(pin, null);
	}

	@Override
	public PrivateKeyAndCert[] getPrivateKeyAndCert(char[] pin)
			throws SmartCardException {
		return getPrivateKeyAndCert(pin, null);
	}



}