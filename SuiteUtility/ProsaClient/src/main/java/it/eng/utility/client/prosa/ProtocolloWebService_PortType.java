/**
 * ProtocolloWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 09, 2010 (01:02:43 CEST) WSDL2Java emitter.
 */

package it.eng.utility.client.prosa;

public interface ProtocolloWebService_PortType extends java.rmi.Remote {
    public byte[] getContenutoAllegato(it.eng.utility.client.prosa.WSAuthentication in0, long in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.Allegato rimuoviAllegato(it.eng.utility.client.prosa.WSAuthentication in0, long in1) throws java.rmi.RemoteException;
    public java.util.Vector estraiFlussiLavorazioneDocumento(it.eng.utility.client.prosa.WSAuthentication in0, long in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.WSEsito inserisciContenutoDaDocumentale(it.eng.utility.client.prosa.WSAuthentication in0, java.lang.Long in1, java.lang.Long in2, boolean in3) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.WSEsito inserisciAllegatoDaFascicolo(it.eng.utility.client.prosa.WSAuthentication in0, java.lang.Long in1, it.eng.utility.client.prosa.Allegato in2) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.WSEsito inserisciAllegatoZip(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.Allegato in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.WSEsito protocollaProfilo(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.DatiProtocollo in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.DocumentoProtocollato protocolla(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.DocumentoProtocollato in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.Assegnazione assegna(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.Assegnazione in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.ImmagineDocumentale recuperaContenuto(it.eng.utility.client.prosa.WSAuthentication in0, long in1) throws java.rmi.RemoteException;
    public byte[] getContenutoDocumento(it.eng.utility.client.prosa.WSAuthentication in0, long in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.Allegato inserisciAllegato(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.Allegato in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.Allegato[] getAllegati(it.eng.utility.client.prosa.WSAuthentication in0, long in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.Allegato getAllegato(it.eng.utility.client.prosa.WSAuthentication in0, long in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.VoceTitolario[] ricercaTitolarioPerCodiceDescrizione(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.VoceTitolario in1) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.ImmagineDocumentale inserisciContenuto(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.ImmagineDocumentale in1, boolean in2) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.WSEsito testLogin(it.eng.utility.client.prosa.WSAuthentication in0) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.WSEsito testStato(it.eng.utility.client.prosa.WSAuthentication in0) throws java.rmi.RemoteException;
    public it.eng.utility.client.prosa.DocumentoProtocollato[] ricercaProtocolli(it.eng.utility.client.prosa.WSAuthentication in0, it.eng.utility.client.prosa.Ricerca in1) throws java.rmi.RemoteException;
}
