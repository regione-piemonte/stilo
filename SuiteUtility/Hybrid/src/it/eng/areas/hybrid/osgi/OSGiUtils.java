package it.eng.areas.hybrid.osgi;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;

public final class OSGiUtils {
  
  private OSGiUtils() {
    //NOP
  }
  
  public static String bundleTypeToString(BundleEvent event) {
    switch (event.getType()) {
    case BundleEvent.INSTALLED:
      return "INSTALLED";
    case BundleEvent.LAZY_ACTIVATION:
      return "LAZY_ACTIVATION";
    case BundleEvent.RESOLVED:
      return "RESOLVED";
    case BundleEvent.STARTED:
      return "STARTED";
    case BundleEvent.STARTING:
      return "STARTING";
    case BundleEvent.STOPPED:
      return "STOPPED";
    case BundleEvent.STOPPING:
      return "STOPPING";
    case BundleEvent.UNINSTALLED:
      return "UNINSTALLED";  
    case BundleEvent.UNRESOLVED:
      return "UNRESOLVED";  
    case BundleEvent.UPDATED:
      return "UPDATED";  
    }
    return null;
  }
  
  public static String bundleStateToString(int state) {
    switch (state) {
    case Bundle.ACTIVE:
      return "ACTIVE";
    case Bundle.INSTALLED:
      return "INSTALLED";
    case Bundle.RESOLVED:
      return "RESOLVED";
    case Bundle.STARTING:
      return "STARTING";
    case Bundle.STOPPING:
      return "STOPPING";
    case Bundle.UNINSTALLED:
      return "UNINSTALLED";
    }
    return null;
  }
  
  
  public static String bundleStateToString(Bundle bundle) {
    return (bundle != null) ? bundleStateToString(bundle.getState()) : "";
  }
  

}
