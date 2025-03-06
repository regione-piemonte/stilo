package it.eng.utility.cryptosigner.controller.impl.ws.service;

public class CertificateReliabilityServiceProxy implements it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityService {
  private String _endpoint = null;
  private it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityService certificateReliabilityService = null;
  
  public CertificateReliabilityServiceProxy() {
    _initCertificateReliabilityServiceProxy();
  }
  
  public CertificateReliabilityServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCertificateReliabilityServiceProxy();
  }
  
  private void _initCertificateReliabilityServiceProxy() {
    try {
      certificateReliabilityService = (new it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityServiceServiceLocator()).getCertificateReliabilityService();
      if (certificateReliabilityService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)certificateReliabilityService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)certificateReliabilityService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (certificateReliabilityService != null)
      ((javax.xml.rpc.Stub)certificateReliabilityService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateReliabilityService getCertificateReliabilityService() {
    if (certificateReliabilityService == null)
      _initCertificateReliabilityServiceProxy();
    return certificateReliabilityService;
  }
  
  public byte[] checkReliableCertificate(byte[] principal, java.util.Calendar referenceDate) throws java.rmi.RemoteException, it.eng.utility.cryptosigner.exception.WSCryptoSignerException {
    if (certificateReliabilityService == null)
      _initCertificateReliabilityServiceProxy();
    return certificateReliabilityService.checkReliableCertificate(principal, referenceDate);
  }
  
  
}