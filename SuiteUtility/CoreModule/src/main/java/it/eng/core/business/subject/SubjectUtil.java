package it.eng.core.business.subject;

import java.util.Locale;

/**
 * Utilità per l'istanziazione della variabile SubjectBean
 * @author michele
 *
 */
public class SubjectUtil {

	/**
	 * Variabile statica ThreadLocal che contiene il subject bean con i parametri utilizzati nel thread in uso	
	 */
	public static ThreadLocal<SubjectBean> subject = new ThreadLocal<SubjectBean>();
 
    
	 
    /**
     * Get the general local for the current thread, will revert to the default locale if none 
     * specified for this thread.
     * 
     * @return  the general locale
     */
    public static Locale getLocale()
    {
    	Locale locale=Locale.getDefault();
    	SubjectBean sb = subject.get(); 
    	if(sb!=null){
    		if (locale != null && sb.getLocale()!=null)
    		{
    		  locale=sb.getLocale();
    		}
    	}
        return locale;
    }
	
}
