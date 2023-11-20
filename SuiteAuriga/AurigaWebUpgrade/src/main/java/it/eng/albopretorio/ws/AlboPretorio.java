/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * AlboPretorio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.albopretorio.ws;

public interface AlboPretorio extends javax.xml.rpc.Service {
    public java.lang.String getAlboPretorioSoap12Address();

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap12() throws javax.xml.rpc.ServiceException;

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getAlboPretorioSoapAddress();

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap() throws javax.xml.rpc.ServiceException;

    public it.eng.albopretorio.ws.AlboPretorioSoap_PortType getAlboPretorioSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
