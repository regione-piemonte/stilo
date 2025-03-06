package it.eng.utility.cryptosigner.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Utility class providing methods to access the Locale of the current thread  
 * 
 
 */
public class LocaleHolder
{
    /**
     * Thread-local containing the general Locale for the current thread
     */
    private static ThreadLocal<Locale> threadLocale = new ThreadLocal<Locale>();
    
    /**
     * Thread-local containing the content Locale for for the current thread.  This
     * can be used for content and property filtering.
     */
    private static ThreadLocal<Locale> threadContentLocale = new ThreadLocal<Locale>();
    
    
    
    
    /**
     * Set the locale for the current thread.
     * 
     * @param locale    the locale
     */
    public static void setLocale(Locale locale)
    {
        threadLocale.set(locale);
    }

    /**
     * Get the general local for the current thread, will revert to the default locale if none 
     * specified for this thread.
     * 
     * @return  the general locale
     */
    public static Locale getLocale()
    {
        Locale locale = threadLocale.get(); 
        if (locale == null)
        {
            // Get the default locale
            locale = Locale.getDefault();
        }
        return locale;
    }
    
    /**
     * Set the <b>content locale</b> for the current thread.
     * 
     * @param locale    the content locale
     */
    public static void setContentLocale(Locale locale)
    {
        threadContentLocale.set(locale);
    }

    /**
     * Get the content local for the current thread.<br/>
     * This will revert to {@link #getLocale()} if no value has been defined.
     * 
     * @return  Returns the content locale
     */
    public static Locale getContentLocale()
    {
        Locale locale = threadContentLocale.get(); 
        if (locale == null)
        {
            // Revert to the normal locale
            locale = getLocale();
        }
        return locale;
    }
    
    /**
* Get the content local for the current thread.<br/>
     * This will revert <tt>null</tt> if no value has been defined.
     * 
     * @return  Returns the content locale
     */
    public static Locale getContentLocaleOrNull()
    {
        Locale locale = threadContentLocale.get(); 
        
        return locale;
    }
    
    
    /**
     * Searches for the nearest locale from the available options.  To match any locale, pass in
     * <tt>null</tt>.
     * 
     * @param templateLocale the template to search for or <tt>null</tt> to match any locale
     * @param options the available locales to search from
     * @return Returns the best match from the available options, or the <tt>null</tt> if
     *      all matches fail
     */
    public static Locale getNearestLocale(Locale templateLocale, Set<Locale> options)
    {
        if (options.isEmpty())                          // No point if there are no options
        {
            return null;
        }
        else if (templateLocale == null)
        {
            for (Locale locale : options)
            {
                return locale;
            }
        }
        else if (options.contains(templateLocale))      // First see if there is an exact match
        {
            return templateLocale;
        }
        // make a copy of the set
        Set<Locale> remaining = new HashSet<Locale>(options);
        
        // eliminate those without matching languages
        Locale lastMatchingOption = null;
        String templateLanguage = templateLocale.getLanguage();
        if (templateLanguage != null && !templateLanguage.equals(""))
        {
            Iterator<Locale> iterator = remaining.iterator();
            while (iterator.hasNext())
            {
                Locale option = iterator.next();
                if (option != null && !templateLanguage.equals(option.getLanguage()))
                {
                    iterator.remove();                  // It doesn't match, so remove
                }
                else
                {
                    lastMatchingOption = option;       // Keep a record of the last match
                }
            }
        }
        if (remaining.isEmpty())
        {
            return null;
        }
        else if (remaining.size() == 1 && lastMatchingOption != null)
        {
            return lastMatchingOption;
        }
        
        // eliminate those without matching country codes
        lastMatchingOption = null;
        String templateCountry = templateLocale.getCountry();
        if (templateCountry != null && !templateCountry.equals(""))
        {
            Iterator<Locale> iterator = remaining.iterator();
            while (iterator.hasNext())
            {
                Locale option = iterator.next();
                if (option != null && !templateCountry.equals(option.getCountry()))
                {
                    // It doesn't match language - remove
                    // Don't remove the iterator. If it matchs a langage but not the country, returns any matched language                     
                    // iterator.remove();
                }
                else
                {
                    lastMatchingOption = option;       // Keep a record of the last match
                }
            }
        }
        /*if (remaining.isEmpty())
        {
            return null;
        }
        else */
        if (remaining.size() == 1 && lastMatchingOption != null)
        {
            return lastMatchingOption;
        }
        else
        {
            // We have done an earlier equality check, so there isn't a matching variant
            // Also, we know that there are multiple options at this point, either of which will do.
        	
        	// This gets any country match (there will be worse matches so we take the last the country match)
        	if(lastMatchingOption != null)
        	{
        		return lastMatchingOption;
        	}
        	else
        	{
                for (Locale locale : remaining)
                {
                    return locale;
                }
        	}
        }
        // The logic guarantees that this code can't be called
        throw new RuntimeException("Logic should not allow code to get here.");
    }
    
    /**
     * Factory method to create a Locale from a <tt>lang_country_variant</tt> string.
     * 
     * @param localeStr e.g. fr_FR
     * @return Returns the locale instance, or the {@link Locale#getDefault() default} if the
     *      string is invalid
     */
    public static Locale parseLocale(String localeStr)
    {
        if(localeStr == null)
        {
            return null; 
        }
        Locale locale = Locale.getDefault();
        
        StringTokenizer t = new StringTokenizer(localeStr, "_");
        int tokens = t.countTokens();
        if (tokens == 1)
        {
           locale = new Locale(t.nextToken());
        }
        else if (tokens == 2)
        {
           locale = new Locale(t.nextToken(), t.nextToken());
        }
        else if (tokens == 3)
        {
           locale = new Locale(t.nextToken(), t.nextToken(), t.nextToken());
        }
        
        return locale;
    }
    
    
}
