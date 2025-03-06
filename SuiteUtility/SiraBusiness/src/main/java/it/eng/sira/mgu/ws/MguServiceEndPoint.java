/**
 * MguServiceEndPoint.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public interface MguServiceEndPoint extends java.rmi.Remote {
    public it.eng.sira.mgu.ws.MguDelegaDTO[] getDelegheRicevute(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
    public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorse(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
    public it.eng.sira.mgu.ws.MguDominioDTO getDominioFromId(java.lang.Long arg0) throws java.rmi.RemoteException;
    public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] getPermessiRisorseRadice(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
    public it.eng.sira.mgu.ws.MguUtenteDTO getUtente(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
    public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] getPermessiRisorse(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
    public it.eng.sira.mgu.ws.MguPermessoDTO[] getPermessi(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
    public it.eng.sira.mgu.ws.MguDominioDTO[] getDomini(java.lang.String arg0) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
    public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorseRadice(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, it.eng.sira.mgu.ws.UnknowUserExceptionbean;
}
