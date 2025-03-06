package it.eng.core.config;

import it.eng.core.business.subject.SubjectUtil;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.context.NoSuchMessageException;

/**
 * utilità per recuperare i messaggi
 *
 */
public class MessageHelper {
	private static final Logger log=Logger.getLogger(MessageHelper.class);
	
	//private static ResourceBundleMessageSource
	public static String getMessage(String code){
		return getMessage(code, null, SubjectUtil.getLocale());
	}

	public static String getMessage(String code,Object... args){
		return getMessage(code, args, SubjectUtil.getLocale());
	}
	
	public static String getMessage(String code,Object[] args,Locale locale){
		if(SpringCoreContextProvider.getContext()==null) {
			log.fatal("<<<<<<< contesto spring non configurato \n " +
					"hai configurato it.eng.core.config.SpringCoreContextProvider nel file di spring >>>>>>>>>");
		}
		
		try {
			return  SpringCoreContextProvider.getContext().getMessage(code, args, locale);
		}
		catch (NoSuchMessageException e) {
			log.warn(e.getMessage(), e);
			return "Messaggio non configurato ["+code+"]["+SubjectUtil.getLocale()+"]:"+e.getMessage();
		}
		catch (Exception e) {
			log.warn(e.getMessage(), e);
			return "Errore nella decodifica del messaggio ["+code+"]["+SubjectUtil.getLocale()+"]:"+e.getMessage();
		}		
	}
}
