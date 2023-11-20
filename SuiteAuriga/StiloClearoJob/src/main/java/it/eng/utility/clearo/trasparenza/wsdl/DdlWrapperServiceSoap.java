/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DdlWrapperServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.utility.clearo.trasparenza.wsdl;

public interface DdlWrapperServiceSoap extends java.rmi.Remote {
    public it.csi.energee3.ddlhook.wrapper.model.DDLRecordDataSoap addRecord(long recordSetId, java.util.HashMap fieldsMap) throws java.rmi.RemoteException;
    public void deleteRecord(long recordId) throws java.rmi.RemoteException;
    public void deleteRecords(long recordSetId) throws java.rmi.RemoteException;
    public it.csi.energee3.ddlhook.wrapper.model.DDLRecordDataSoap fetchRecord(long recordId) throws java.rmi.RemoteException;
    public it.csi.energee3.ddlhook.wrapper.model.DDLRecordDataSoap[] getRecords(long recordSetId) throws java.rmi.RemoteException;
    public void testMethod() throws java.rmi.RemoteException;
    public it.csi.energee3.ddlhook.wrapper.model.DDLRecordDataSoap updateRecord(long recordId, java.util.HashMap fieldsMap) throws java.rmi.RemoteException;
}
