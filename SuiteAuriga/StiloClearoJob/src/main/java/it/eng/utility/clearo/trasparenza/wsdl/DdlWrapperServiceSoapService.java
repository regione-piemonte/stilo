/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DdlWrapperServiceSoapService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.utility.clearo.trasparenza.wsdl;

public interface DdlWrapperServiceSoapService extends javax.xml.rpc.Service {
    public java.lang.String getPlugin_ddlWrapper_ddlWrapperServiceAddress();

    public it.eng.utility.clearo.trasparenza.wsdl.DdlWrapperServiceSoap getPlugin_ddlWrapper_ddlWrapperService() throws javax.xml.rpc.ServiceException;

    public it.eng.utility.clearo.trasparenza.wsdl.DdlWrapperServiceSoap getPlugin_ddlWrapper_ddlWrapperService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
