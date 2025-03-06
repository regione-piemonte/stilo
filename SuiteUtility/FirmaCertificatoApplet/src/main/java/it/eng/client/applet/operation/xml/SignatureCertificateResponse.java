package it.eng.client.applet.operation.xml;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class SignatureCertificateResponse {
  X509Certificate certificate;
  boolean valid;
  int depth;
  
  SignatureTimeStampResponse timeStampResponse;
  
  
  List<SignatureCertificateResponse> counterSignature;
  
  public SignatureCertificateResponse(X509Certificate certificate, SignatureTimeStampResponse timeStampResponse, boolean valid, int depth) {
    this.certificate = certificate;
    this.valid = valid;
    this.depth = depth;
    this.timeStampResponse = timeStampResponse;
    counterSignature = new ArrayList<SignatureCertificateResponse>(0);
  }
  
  void addCounterSignature(SignatureCertificateResponse counterSignatureResponse) {
    counterSignature.add(counterSignatureResponse);
  }
  
  public X509Certificate getCertificate() {
    return certificate;
  }
  
  /**
   * @return true se il documento � stato firmato con questo certificato
   */
  public boolean isValid() {
    return valid;
  }

  /**
   * @return in caso di firme ricorsive torna la profondit� 
   */
  public int getDeph() {
    return depth;
  }
  
  /**
   * Eventuale Marca temporale. Pu� essere <code>null</code>
   * @return
   */
  public SignatureTimeStampResponse getTimeStampResponse() {
    return timeStampResponse;
  }
  
  

}
