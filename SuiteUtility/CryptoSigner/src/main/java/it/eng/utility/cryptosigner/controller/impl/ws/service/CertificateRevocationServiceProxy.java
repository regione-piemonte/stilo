package it.eng.utility.cryptosigner.controller.impl.ws.service;

import java.util.Calendar;

public class CertificateRevocationServiceProxy implements it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationService {
  private String _endpoint = null;
  private it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationService certificateRevocationService = null;
  
  public CertificateRevocationServiceProxy() {
    _initCertificateRevocationServiceProxy();
  }
  
  public CertificateRevocationServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initCertificateRevocationServiceProxy();
  }
  
  private void _initCertificateRevocationServiceProxy() {
    try {
      certificateRevocationService = (new it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationServiceServiceLocator()).getCertificateRevocationService();
      if (certificateRevocationService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)certificateRevocationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)certificateRevocationService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (certificateRevocationService != null)
      ((javax.xml.rpc.Stub)certificateRevocationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.eng.utility.cryptosigner.controller.impl.ws.service.CertificateRevocationService getCertificateRevocationService() {
    if (certificateRevocationService == null)
      _initCertificateRevocationServiceProxy();
    return certificateRevocationService;
  }
  
  public it.eng.utility.cryptosigner.controller.bean.ValidationInfos checkCertificateRevocationStatus(byte[] certificate, Calendar referenceCal) throws java.rmi.RemoteException, it.eng.utility.cryptosigner.exception.WSCryptoSignerException {
    if (certificateRevocationService == null)
      _initCertificateRevocationServiceProxy();
    return certificateRevocationService.checkCertificateRevocationStatus(certificate, referenceCal);
  }
  
  public it.eng.utility.cryptosigner.controller.bean.ValidationInfos checkCertificateAndIssuerRevocationStatus(byte[] certificate, byte[] issuerCertificate, Calendar referenceCal) throws java.rmi.RemoteException, it.eng.utility.cryptosigner.exception.WSCryptoSignerException {
    if (certificateRevocationService == null)
      _initCertificateRevocationServiceProxy();
    return certificateRevocationService.checkCertificateAndIssuerRevocationStatus(certificate, issuerCertificate, referenceCal);
  }
  
  
}