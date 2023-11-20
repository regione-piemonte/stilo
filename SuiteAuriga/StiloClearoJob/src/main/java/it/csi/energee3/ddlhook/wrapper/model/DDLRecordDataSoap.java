/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DDLRecordDataSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.energee3.ddlhook.wrapper.model;

public class DDLRecordDataSoap  extends com.liferay.portlet.dynamicdatalists.model.DDLRecordSoap  implements java.io.Serializable {
    private java.util.HashMap fields;

    public DDLRecordDataSoap() {
    }

    public DDLRecordDataSoap(
           long DDMStorageId,
           long companyId,
           java.util.Calendar createDate,
           int displayIndex,
           long groupId,
           java.util.Calendar modifiedDate,
           long primaryKey,
           long recordId,
           long recordSetId,
           long userId,
           java.lang.String userName,
           java.lang.String uuid,
           java.lang.String version,
           long versionUserId,
           java.lang.String versionUserName,
           java.util.HashMap fields) {
        super(
            DDMStorageId,
            companyId,
            createDate,
            displayIndex,
            groupId,
            modifiedDate,
            primaryKey,
            recordId,
            recordSetId,
            userId,
            userName,
            uuid,
            version,
            versionUserId,
            versionUserName);
        this.fields = fields;
    }


    /**
     * Gets the fields value for this DDLRecordDataSoap.
     * 
     * @return fields
     */
    public java.util.HashMap getFields() {
        return fields;
    }


    /**
     * Sets the fields value for this DDLRecordDataSoap.
     * 
     * @param fields
     */
    public void setFields(java.util.HashMap fields) {
        this.fields = fields;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DDLRecordDataSoap)) return false;
        DDLRecordDataSoap other = (DDLRecordDataSoap) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fields==null && other.getFields()==null) || 
             (this.fields!=null &&
              this.fields.equals(other.getFields())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getFields() != null) {
            _hashCode += getFields().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DDLRecordDataSoap.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.wrapper.ddlhook.energee3.csi.it", "DDLRecordDataSoap"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fields");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fields"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://xml.apache.org/xml-soap", "Map"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
