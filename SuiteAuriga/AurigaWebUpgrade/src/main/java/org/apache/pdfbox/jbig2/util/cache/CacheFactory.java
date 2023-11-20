/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Iterator;
import org.apache.pdfbox.jbig2.util.ServiceLookup;

public class CacheFactory {
  private static CacheBridge cacheBridge;
  
  private static ClassLoader clsLoader;
  
  public static Cache getCache(ClassLoader paramClassLoader) {
    if (null == cacheBridge) {
      ServiceLookup serviceLookup = new ServiceLookup();
      Iterator<CacheBridge> iterator = serviceLookup.getServices(CacheBridge.class, paramClassLoader);
      if (!iterator.hasNext())
        throw new IllegalStateException("No implementation of " + CacheBridge.class + " was avaliable using META-INF/services lookup"); 
      cacheBridge = iterator.next();
    } 
    return cacheBridge.getCache();
  }
  
  public static Cache getCache() {
    //return getCache((clsLoader != null) ? clsLoader : CacheBridge.class.getClassLoader());
	/**
	* Errore org.apache.pdfbox.jbig2.util.cache.CacheBridge: Provider org.apache.pdfbox.jbig2.util.cache.SoftReferenceCacheBridge not a subtype
	*/
	return new SoftReferenceCache();
  }
  
  public static void setClassLoader(ClassLoader paramClassLoader) {
    clsLoader = paramClassLoader;
  }
}