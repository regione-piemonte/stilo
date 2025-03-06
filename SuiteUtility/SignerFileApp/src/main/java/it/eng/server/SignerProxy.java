package it.eng.server;

import it.eng.client.applet.operation.jsignpdf.HashAlgorithm;
import it.eng.common.bean.HashFileBean;
import it.eng.common.bean.SignerObjectBean;
import it.eng.common.type.SignerType;
import it.eng.common.type.TabType;
import it.eng.server.util.CRLUtil;

import java.awt.image.BufferedImage;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.directwebremoting.annotations.RemoteProxy;

@RemoteProxy(name="SignerProxy")
public class SignerProxy {

	public HashMap<String,Object> getHashFileSigner(HttpSession session) throws Exception{
		try{
			
			HashMap<String,Object> ret = new HashMap<String, Object>();
			List<String> str = new ArrayList<String>();
			//Recupero i file da firmare
			SignerSessionBean signerSessionBean = (SignerSessionBean)session.getAttribute(SignerUtil.SESSION_SIGNER);
					
			List<FileElaborate> files = signerSessionBean.getFiles();
			HashAlgorithm digest = signerSessionBean.getDigest();
			SignerType type = signerSessionBean.getSignerType();
			SignerType[] typesList = signerSessionBean.getSignerTypes();
			TabType[] tabsToview = signerSessionBean.getTabsView();
			SignerUtil util = new SignerUtil(session,digest,false);
				
			for(FileElaborate file:files){
				try{
					HashFileBean bean = new HashFileBean();
					bean.setFileName(file.getUnsigned().getName());
					bean.setId(file.getId());
					bean.setHash(util.getHash(file.getUnsigned()));
					bean.setFileType(SignerType.P7M);
					if(util.isPDF(file.getUnsigned())){
						try{
							bean.setHashPdf(util.getHashPdf(file.getUnsigned()));
							bean.setFileType(SignerType.PDF);
						}catch(Throwable e){
							
						}
					}
					SerializationUtils.serialize(bean);
					Base64.encodeBase64String(SerializationUtils.serialize(bean));
					str.add(Base64.encodeBase64String(SerializationUtils.serialize(bean)));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			ret.put("lista", str);
			ret.put("digest", digest.name());
			ret.put("signerType", type.name());
						
			String tmp = "";
			for(SignerType mytype:typesList){
				tmp+=mytype.name()+";";				
			}
			
			tmp = tmp.substring(0, tmp.length()-1);
			ret.put("signerTypeList", tmp);

			//Inserisco i tab visibili
			String tmp2 = "";
			for(TabType tab:tabsToview){
				tmp2+=tab.name()+";";				
			}
			tmp2 = tmp2.substring(0, tmp2.length()-1);
			ret.put("tabConfig", tmp2);
					
			return ret; 
		}catch(Throwable e){
			e.printStackTrace();
			throw new Exception("Errore file");
		}
	}
		
	public String signerFile(List<String> hashSigners,HttpSession session) throws Exception {
		SignerUtil util = SignerUtil.getSignerUtil(session);
		boolean validfile = true;
		
		//Controllo se ho gia' tutti i file firmati
		List<FileElaborate> files = util.getFilesSigned();
		for(FileElaborate file:files){
			if(file.getSigned()==null){
				validfile = false;
				break;
			}
		}
		String ret = null;
		if(!validfile){
			try{
				if(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)==null){
					Security.addProvider(new BouncyCastleProvider());
				}	
				for(String signer:hashSigners){
					SignerObjectBean bean = (SignerObjectBean)SerializationUtils.deserialize(Base64.decodeBase64(signer));
					//Controllo la CRL del certificato di firma
					boolean valid = true;
					if(util.isVerifyCRL()){
						CRLUtil crlutil = new CRLUtil();
						List<X509Certificate> certificates = bean.getCertificates();
						valid = false;
						for(X509Certificate cert:certificates){
							try{
								valid = crlutil.isValidCertificate(cert);
							}catch(Exception e){
								valid = true;
								if(ret != null){
									ret = SignerUtil.messagebean.errordownloadCRL;
								}
								break;
							}
						}
					}
					if(valid){
						if(bean.getFileType().equals(SignerType.PDF)){
							//Creo il file in formato pdf
							util.signerPDF(bean);
						}else{
							//Creo il file in formato p7m
							util.signerP7M(bean);
						}	
					}else{
						if(ret == null){
							ret = SignerUtil.messagebean.invalidCRL;
						}
					}
				}
			}catch(Throwable e){
				e.printStackTrace();
				ret = "Errore generico:"+e.getMessage();
			}
		}
		if(ret==null){
			ret = "";
		}
		
		
		return ret;
	}
	
	
	public void uploadsigner(BufferedImage file){
			
		System.out.println("FILE:"+file);
		
	}
	
	
}