/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * AlboPretorioSoap_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.albopretorio.ws;

public interface AlboPretorioSoap_PortType extends java.rmi.Remote {
    public java.lang.String getVersion() throws java.rmi.RemoteException;
    public it.eng.albopretorio.ws.RispostaDocumentoType caricaDocumento(it.eng.albopretorio.ws.DocumentoType documento) throws java.rmi.RemoteException;
    public it.eng.albopretorio.ws.DocumentoInfoType leggiDocumento(int idAlbo) throws java.rmi.RemoteException;
    public boolean impostaNotificheStato(int idAlbo, int tipoNotifica, java.lang.String parametriNotifica) throws java.rmi.RemoteException;
}
