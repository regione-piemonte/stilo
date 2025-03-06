package it.eng.core.service.client;

import it.eng.core.business.exception.CoreExceptionBean;
import it.eng.core.business.subject.SubjectBean;
import it.eng.core.business.subject.SubjectUtil;
import it.eng.core.config.ConfigUtil;
import it.eng.core.service.bean.IAuthenticationBean;
import it.eng.core.service.bean.IServiceInvokerInfo;
import it.eng.core.service.bean.IrisCall;
import it.eng.core.service.client.config.Configuration;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * Implementazione tramite API
 *
 */
public class ApiBusiness implements IBusiness {
	
	private static Logger log = Logger.getLogger(ApiBusiness.class);
	
	private Serializable callInternal(IAuthenticationBean authBean, Locale locale,Class<?> outputclass,Type outputType, String servicename, String operationname, Serializable... inputsObject) throws Exception {
		log.debug("invoking SERVICE by API");
		Serializable ret=null;
		try{
			
			String idDominio = "DEFAULT";
			if (ConfigUtil.isAuthenticationEnabled()) {
				if (authBean==null)
					throw new Exception("Parametri di autenticazione nulli, sistema configurato per utilizzare l'autenticazione interna");
				
				//se si, controllo che in configurazione sia configurato il servizio di login
				String loginService = ConfigUtil.getAuthenticationServiceName();
				
				String application =authBean.getApplicationName();
				if (application!=null) {
					org.apache.commons.configuration.Configuration configurazioneApplicazioniEnti = ConfigUtil.getMappaApplEnte();
					String tmp =  (String) configurazioneApplicazioniEnti.getProperty(application);
					idDominio=tmp==null?"DEFAULT":tmp;
				}
				
				SubjectBean subject =  SubjectUtil.subject.get();
				if (subject==null) {
					subject=new SubjectBean();
					subject.setUuidtransaction("");//patch temporanea per gestire la situazione inconsistente
					SubjectUtil.subject.set(subject);
				}
				subject.setIdDominio(idDominio);
				subject.setIdapplicazione(application);
				
				//provo a verificare la login
				if (!servicename.equals(loginService)) {
					
					IServiceInvokerInfo invokerInfo = (IServiceInvokerInfo) IrisCall.call(loginService,"login",authBean);
					
					if (invokerInfo!=null) {
						subject.setIduser(invokerInfo.getIdServiceInvoker());
						subject.setNomeapplicazione(invokerInfo.getNomeApplicazione());
					}
										
				}
								
			}				
			
			ret=IrisCall.call(servicename, operationname, inputsObject);
			return ret;
		}catch(Exception ex){
			//per compatiblità con il rest
			throw new CoreException(new CoreExceptionBean(ex));
		}
	}

	@Override
	public void init(Configuration config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Serializable call(String url, Locale locale, Class<?> outputclass,
			Type outputType, String module, String method,
			Serializable... input) throws Exception {
		// TODO Auto-generated method stub
		return callInternal(null, locale, outputclass, outputType, module, method, input);
	}

	@Override
	public Serializable call(IAuthenticationBean authBean, String url, Locale locale,
			Class<?> outputclass, Type outputType, String module,
			String method, Serializable... input) throws Exception {
		// TODO Auto-generated method stub
		return callInternal(authBean, locale, outputclass, outputType, module, method, input);
	}

	@Override
	public Serializable call(Locale locale, Class<?> outputclass,
			Type outputType, String module, String method,
			Serializable... input) throws Exception {
		// TODO Auto-generated method stub
		return callInternal(null, locale, outputclass, outputType, module, method, input);
	}
}
