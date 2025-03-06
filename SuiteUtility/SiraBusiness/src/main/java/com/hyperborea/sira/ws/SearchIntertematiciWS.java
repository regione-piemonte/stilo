/**
 * SearchIntertematiciWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public interface SearchIntertematiciWS extends java.rmi.Remote {
    public com.hyperborea.sira.ws.WsCountSoggettiFisiciResponse countSoggettiFisici(com.hyperborea.sira.ws.WsCountSoggettiFisiciRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsSearchSoggettiFisiciResponse searchSoggettiFisici(com.hyperborea.sira.ws.WsSearchSoggettiFisiciRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsUnitaLocaliRef[] searchUnitaLocali(com.hyperborea.sira.ws.WsSearchUnitaLocaliRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsViewSoggettiFisiciResponse viewSoggettiFisici(com.hyperborea.sira.ws.WsViewSoggettiFisiciRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewSoggettiFisiciResponse newSoggettiFisici(com.hyperborea.sira.ws.WsNewSoggettiFisiciRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsEditSoggettiFisiciResponse editSoggettiFisici(com.hyperborea.sira.ws.WsEditSoggettiFisiciRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewSfOstResponse newSfOst(com.hyperborea.sira.ws.WsNewSfOstRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse getVocabularyDescription(com.hyperborea.sira.ws.WsGetVocabularyDescriptionRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse getVocabularyDescriptionByVocClassname(com.hyperborea.sira.ws.WsGetVocabularyDescriptionByVocClassnameRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewCampioneResponse newCampione(com.hyperborea.sira.ws.WsNewCampioneRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewAttivitaEsternaResponse newAttivitaEsterna(com.hyperborea.sira.ws.WsNewAttivitaEsternaRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsTipoCampioneRef[] getTipoCampioneList(com.hyperborea.sira.ws.WsGetTipoCampioneListRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsGetTipoCampioneResponse getTipoCampione(com.hyperborea.sira.ws.WsGetTipoCampioneRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewUbicazioniOstResponse newUbicazioniOst(com.hyperborea.sira.ws.WsNewUbicazioniOstRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewRetiMonitoraggioResponse newReteMonitoraggio(com.hyperborea.sira.ws.WsNewRetiMonitoraggioRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioResponse getTipiSchedaMonitoraggio(com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef[] getTipiSchedaMonitoraggioList(com.hyperborea.sira.ws.WsGetTipiSchedaMonitoraggioListRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewSchedaMonitoraggioResponse newSchedaMonitoraggio(com.hyperborea.sira.ws.WsNewSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsViewSchedaMonitoraggioResponse viewSchedaMonitoraggio(com.hyperborea.sira.ws.WsViewSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioResponse searchSchedaMonitoraggio(com.hyperborea.sira.ws.WsSearchSchedaMonitoraggioRequest arg0) throws java.rmi.RemoteException;
    public java.lang.Long strLenght(java.lang.String arg0) throws java.rmi.RemoteException;
    public java.lang.Long countOst(com.hyperborea.sira.ws.CcostAnySearchComand arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsSearchOstResponse searchOst(com.hyperborea.sira.ws.WsSearchOstRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsVisitedOst[] visitOst(com.hyperborea.sira.ws.WsVisitOstRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsViewOstResponse viewOst(com.hyperborea.sira.ws.WsViewOstRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewCcostResponse newCcost(com.hyperborea.sira.ws.WsNewCcostRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsViewFonteResponse viewFonte(com.hyperborea.sira.ws.WsViewFonteRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsFontiDatiResponse newFontiDati(com.hyperborea.sira.ws.WsNewFontiDatiRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewOstResponse newOst(com.hyperborea.sira.ws.WsNewOstRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsNewRostResponse newRelazioniOst(com.hyperborea.sira.ws.WsNewRostRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsGetCategoriaMetadataResponse getCategoriaMetadata(com.hyperborea.sira.ws.WsGetCategoriaMetadataRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsGetCategoriaInfoResponse getCategoriaInfo(com.hyperborea.sira.ws.WsGetCategoriaInfoRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsCostNostRef[] getAvailableCategoriaList(com.hyperborea.sira.ws.WsGetAvailableCategoriaListRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsSubmitRapportoDiProvaResponse newRapportoDiProva(com.hyperborea.sira.ws.WsSubmitRapportoDiProvaRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsBeginTransactionResponse beginTransaction(com.hyperborea.sira.ws.WsBeginTransactionRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsCommitTransactionResponse closeTransaction(com.hyperborea.sira.ws.WsCommitTransactionRequest arg0) throws java.rmi.RemoteException;
    public com.hyperborea.sira.ws.WsStatiOstResponse newStatiOst(com.hyperborea.sira.ws.WsStatiOstRequest arg0) throws java.rmi.RemoteException;
}
