/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.security.Provider;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BouncyCastleProviderUtils {
  
  public static final String PROVIDER_NAME = "BC";

  public static void checkBouncyCastleProvider() {
    Provider provider = Security.getProvider(PROVIDER_NAME);
    if (provider == null) {
      Security.insertProviderAt(new BouncyCastleProvider(), 3);
    }
    
  }
  
  private BouncyCastleProviderUtils() {
  }

}
