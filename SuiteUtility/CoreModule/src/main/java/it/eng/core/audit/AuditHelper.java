package it.eng.core.audit;

import it.eng.core.config.ConfigKey;
import it.eng.core.config.ConfigUtil;

import java.io.Serializable;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

public class AuditHelper {
	private static Logger log = Logger.getLogger(AuditHelper.class);
	private static IAudit auditer;
	private static boolean auditEnabled=true;
	
	private static IAudit getAuditer(){
		//verifica se il sistema di audit è attivo
		auditEnabled=ConfigUtil.getConfig().getBoolean(ConfigKey.AUDIT_ENABLED);
		if(auditEnabled && auditer==null){
			log.info("reading auditer from config");
			String auditerclazz=ConfigUtil.getConfig().getString(ConfigKey.AUDIT_AUDITER, null);
			if(auditerclazz!=null){
				createAuditer(auditerclazz);
			}
		}
		return auditer;
	}

	private static void createAuditer(String auditerclazz){
		try{
			Class clazz=Class.forName(auditerclazz);
			auditer=(IAudit)clazz.newInstance();
		}catch(Exception ex){
			log.fatal("exception creating auditer with name:"+auditerclazz,ex);
		}
	}

	public static String traceStart(String serviceName,String operationName, Serializable...  input) throws Exception{
		String ret=null;
		auditer=getAuditer();
		if(auditEnabled){
			if(auditer==null){
				throw new ServiceException("sistema di audot non configurato");
			}
			ret= auditer.traceStart(serviceName, operationName, input);
		}
		return ret;
	}

	public static void traceEnd(String idLogIniz,String serviceName,String operationName,Serializable output) throws Exception{
		auditer=getAuditer();
		if(auditEnabled){

			if(auditer==null){
				throw new ServiceException("sistema di audot non configurato");
			}
			auditer.traceEnd(idLogIniz, serviceName, operationName, output);
		}
	}

	public static void traceError(String idLogIniz,String serviceName,String operationName, Throwable e) throws Exception{
		auditer=getAuditer();
		if(auditEnabled){
			if(auditer==null){
				throw new ServiceException("sistema di audot non configurato");
			}
			auditer.traceError(idLogIniz, serviceName, operationName, e);
		}
	}
}
