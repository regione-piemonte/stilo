package it.eng.utility.cryptosigner.utils;

import it.eng.utility.cryptosigner.context.CryptoSignerApplicationContextProvider;

import java.util.Locale;

/**
 * utilità per recuperare i messaggi
 *
 */
public class MessageHelper {
	
	//private static ResourceBundleMessageSource
	public static String getMessage(String code){
		return  CryptoSignerApplicationContextProvider.getContext().getMessage(code, null, LocaleHolder.getContentLocale());
	}
	
//	public static String getMessage(String code,Object[] args){
//		return  CryptoSignerApplicationContextProvider.getContext().getMessage(code, args, LocaleHolder.getContentLocale());
//	}
	
	public static String getMessage(String code,Object... args){
		return  CryptoSignerApplicationContextProvider.getContext().getMessage(code, args, LocaleHolder.getContentLocale());
	}
	public static String getMessage(String code,Object[] args,Locale locale){
		return  CryptoSignerApplicationContextProvider.getContext().getMessage(code, args, locale);
	}
}
