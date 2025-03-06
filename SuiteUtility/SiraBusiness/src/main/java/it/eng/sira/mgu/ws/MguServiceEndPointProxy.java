package it.eng.sira.mgu.ws;

public class MguServiceEndPointProxy implements it.eng.sira.mgu.ws.MguServiceEndPoint {
  private String _endpoint = null;
  private it.eng.sira.mgu.ws.MguServiceEndPoint mguServiceEndPoint = null;
  
  public MguServiceEndPointProxy() {
    _initMguServiceEndPointProxy();
  }
  
  public MguServiceEndPointProxy(String endpoint) {
    _endpoint = endpoint;
    _initMguServiceEndPointProxy();
  }
  
  private void _initMguServiceEndPointProxy() {
    try {
      mguServiceEndPoint = (new it.eng.sira.mgu.ws.MguServiceLocator()).getMguServiceEndPointPort();
      if (mguServiceEndPoint != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mguServiceEndPoint)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mguServiceEndPoint)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mguServiceEndPoint != null)
      ((javax.xml.rpc.Stub)mguServiceEndPoint)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.eng.sira.mgu.ws.MguServiceEndPoint getMguServiceEndPoint() {
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint;
  }
  
  public it.eng.sira.mgu.ws.MguDelegaDTO[] getDelegheRicevute(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getDelegheRicevute(arg0);
  }
  
  public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorse(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getRisorse(arg0);
  }
  
  public it.eng.sira.mgu.ws.MguDominioDTO getDominioFromId(java.lang.Long arg0) throws java.rmi.RemoteException{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getDominioFromId(arg0);
  }
  
  public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] getPermessiRisorseRadice(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getPermessiRisorseRadice(arg0, arg1);
  }
  
  public it.eng.sira.mgu.ws.MguUtenteDTO getUtente(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getUtente(arg0);
  }
  
  public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] getPermessiRisorse(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getPermessiRisorse(arg0);
  }
  
  public it.eng.sira.mgu.ws.MguPermessoDTO[] getPermessi(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getPermessi(arg0);
  }
  
  public it.eng.sira.mgu.ws.MguDominioDTO[] getDomini(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getDomini(arg0);
  }
  
  public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorseRadice(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean{
    if (mguServiceEndPoint == null)
      _initMguServiceEndPointProxy();
    return mguServiceEndPoint.getRisorseRadice(arg0, arg1);
  }
  
  
}