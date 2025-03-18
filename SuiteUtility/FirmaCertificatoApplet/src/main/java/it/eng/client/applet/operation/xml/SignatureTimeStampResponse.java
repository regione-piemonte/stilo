/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.Date;

public class SignatureTimeStampResponse {
  
  X509Certificate certificate;
  boolean valid;
  Date genTime;
  BigInteger serialNumber;
  String tsa;
  
  
  
  public X509Certificate getCertificate() {
    return certificate;
  }
  
  public boolean isValid() {
    return valid;
  }
  
  public Date getGenTime() {
    return genTime;
  }
  
  public BigInteger getSerialNumber() {
    return serialNumber;
  }

  
  
}
