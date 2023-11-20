/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * DDLRecordSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.liferay.portlet.dynamicdatalists.model;

public class DDLRecordSoap  implements java.io.Serializable {
    private long DDMStorageId;

    private long companyId;

    private java.util.Calendar createDate;

    private int displayIndex;

    private long groupId;

    private java.util.Calendar modifiedDate;

    private long primaryKey;

    private long recordId;

    private long recordSetId;

    private long userId;

    private java.lang.String userName;

    private java.lang.String uuid;

    private java.lang.String version;

    private long versionUserId;

    private java.lang.String versionUserName;

    public DDLRecordSoap() {
    }

    public DDLRecordSoap(
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
           java.lang.String versionUserName) {
           this.DDMStorageId = DDMStorageId;
           this.companyId = companyId;
           this.createDate = createDate;
           this.displayIndex = displayIndex;
           this.groupId = groupId;
           this.modifiedDate = modifiedDate;
           this.primaryKey = primaryKey;
           this.recordId = recordId;
           this.recordSetId = recordSetId;
           this.userId = userId;
           this.userName = userName;
           this.uuid = uuid;
           this.version = version;
           this.versionUserId = versionUserId;
           this.versionUserName = versionUserName;
    }


    /**
     * Gets the DDMStorageId value for this DDLRecordSoap.
     * 
     * @return DDMStorageId
     */
    public long getDDMStorageId() {
        return DDMStorageId;
    }


    /**
     * Sets the DDMStorageId value for this DDLRecordSoap.
     * 
     * @param DDMStorageId
     */
    public void setDDMStorageId(long DDMStorageId) {
        this.DDMStorageId = DDMStorageId;
    }


    /**
     * Gets the companyId value for this DDLRecordSoap.
     * 
     * @return companyId
     */
    public long getCompanyId() {
        return companyId;
    }


    /**
     * Sets the companyId value for this DDLRecordSoap.
     * 
     * @param companyId
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }


    /**
     * Gets the createDate value for this DDLRecordSoap.
     * 
     * @return createDate
     */
    public java.util.Calendar getCreateDate() {
        return createDate;
    }


    /**
     * Sets the createDate value for this DDLRecordSoap.
     * 
     * @param createDate
     */
    public void setCreateDate(java.util.Calendar createDate) {
        this.createDate = createDate;
    }


    /**
     * Gets the displayIndex value for this DDLRecordSoap.
     * 
     * @return displayIndex
     */
    public int getDisplayIndex() {
        return displayIndex;
    }


    /**
     * Sets the displayIndex value for this DDLRecordSoap.
     * 
     * @param displayIndex
     */
    public void setDisplayIndex(int displayIndex) {
        this.displayIndex = displayIndex;
    }


    /**
     * Gets the groupId value for this DDLRecordSoap.
     * 
     * @return groupId
     */
    public long getGroupId() {
        return groupId;
    }


    /**
     * Sets the groupId value for this DDLRecordSoap.
     * 
     * @param groupId
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }


    /**
     * Gets the modifiedDate value for this DDLRecordSoap.
     * 
     * @return modifiedDate
     */
    public java.util.Calendar getModifiedDate() {
        return modifiedDate;
    }


    /**
     * Sets the modifiedDate value for this DDLRecordSoap.
     * 
     * @param modifiedDate
     */
    public void setModifiedDate(java.util.Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    /**
     * Gets the primaryKey value for this DDLRecordSoap.
     * 
     * @return primaryKey
     */
    public long getPrimaryKey() {
        return primaryKey;
    }


    /**
     * Sets the primaryKey value for this DDLRecordSoap.
     * 
     * @param primaryKey
     */
    public void setPrimaryKey(long primaryKey) {
        this.primaryKey = primaryKey;
    }


    /**
     * Gets the recordId value for this DDLRecordSoap.
     * 
     * @return recordId
     */
    public long getRecordId() {
        return recordId;
    }


    /**
     * Sets the recordId value for this DDLRecordSoap.
     * 
     * @param recordId
     */
    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }


    /**
     * Gets the recordSetId value for this DDLRecordSoap.
     * 
     * @return recordSetId
     */
    public long getRecordSetId() {
        return recordSetId;
    }


    /**
     * Sets the recordSetId value for this DDLRecordSoap.
     * 
     * @param recordSetId
     */
    public void setRecordSetId(long recordSetId) {
        this.recordSetId = recordSetId;
    }


    /**
     * Gets the userId value for this DDLRecordSoap.
     * 
     * @return userId
     */
    public long getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this DDLRecordSoap.
     * 
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }


    /**
     * Gets the userName value for this DDLRecordSoap.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this DDLRecordSoap.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the uuid value for this DDLRecordSoap.
     * 
     * @return uuid
     */
    public java.lang.String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this DDLRecordSoap.
     * 
     * @param uuid
     */
    public void setUuid(java.lang.String uuid) {
        this.uuid = uuid;
    }


    /**
     * Gets the version value for this DDLRecordSoap.
     * 
     * @return version
     */
    public java.lang.String getVersion() {
        return version;
    }


    /**
     * Sets the version value for this DDLRecordSoap.
     * 
     * @param version
     */
    public void setVersion(java.lang.String version) {
        this.version = version;
    }


    /**
     * Gets the versionUserId value for this DDLRecordSoap.
     * 
     * @return versionUserId
     */
    public long getVersionUserId() {
        return versionUserId;
    }


    /**
     * Sets the versionUserId value for this DDLRecordSoap.
     * 
     * @param versionUserId
     */
    public void setVersionUserId(long versionUserId) {
        this.versionUserId = versionUserId;
    }


    /**
     * Gets the versionUserName value for this DDLRecordSoap.
     * 
     * @return versionUserName
     */
    public java.lang.String getVersionUserName() {
        return versionUserName;
    }


    /**
     * Sets the versionUserName value for this DDLRecordSoap.
     * 
     * @param versionUserName
     */
    public void setVersionUserName(java.lang.String versionUserName) {
        this.versionUserName = versionUserName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DDLRecordSoap)) return false;
        DDLRecordSoap other = (DDLRecordSoap) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.DDMStorageId == other.getDDMStorageId() &&
            this.companyId == other.getCompanyId() &&
            ((this.createDate==null && other.getCreateDate()==null) || 
             (this.createDate!=null &&
              this.createDate.equals(other.getCreateDate()))) &&
            this.displayIndex == other.getDisplayIndex() &&
            this.groupId == other.getGroupId() &&
            ((this.modifiedDate==null && other.getModifiedDate()==null) || 
             (this.modifiedDate!=null &&
              this.modifiedDate.equals(other.getModifiedDate()))) &&
            this.primaryKey == other.getPrimaryKey() &&
            this.recordId == other.getRecordId() &&
            this.recordSetId == other.getRecordSetId() &&
            this.userId == other.getUserId() &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.uuid==null && other.getUuid()==null) || 
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            this.versionUserId == other.getVersionUserId() &&
            ((this.versionUserName==null && other.getVersionUserName()==null) || 
             (this.versionUserName!=null &&
              this.versionUserName.equals(other.getVersionUserName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getDDMStorageId()).hashCode();
        _hashCode += new Long(getCompanyId()).hashCode();
        if (getCreateDate() != null) {
            _hashCode += getCreateDate().hashCode();
        }
        _hashCode += getDisplayIndex();
        _hashCode += new Long(getGroupId()).hashCode();
        if (getModifiedDate() != null) {
            _hashCode += getModifiedDate().hashCode();
        }
        _hashCode += new Long(getPrimaryKey()).hashCode();
        _hashCode += new Long(getRecordId()).hashCode();
        _hashCode += new Long(getRecordSetId()).hashCode();
        _hashCode += new Long(getUserId()).hashCode();
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        _hashCode += new Long(getVersionUserId()).hashCode();
        if (getVersionUserName() != null) {
            _hashCode += getVersionUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DDLRecordSoap.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.dynamicdatalists.portlet.liferay.com", "DDLRecordSoap"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DDMStorageId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DDMStorageId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "companyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("displayIndex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "displayIndex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "groupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifiedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modifiedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryKey");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryKey"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recordId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recordSetId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recordSetId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "uuid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionUserId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "versionUserId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("versionUserName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "versionUserName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
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
