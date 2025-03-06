/**
 * WsOstRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsOstRef  implements java.io.Serializable {
    private java.lang.Integer costId;

    private java.lang.Integer idOst;

    private java.lang.Integer nostId;

    private java.lang.String title;

    public WsOstRef() {
    }

    public WsOstRef(
           java.lang.Integer costId,
           java.lang.Integer idOst,
           java.lang.Integer nostId,
           java.lang.String title) {
           this.costId = costId;
           this.idOst = idOst;
           this.nostId = nostId;
           this.title = title;
    }


    /**
     * Gets the costId value for this WsOstRef.
     * 
     * @return costId
     */
    public java.lang.Integer getCostId() {
        return costId;
    }


    /**
     * Sets the costId value for this WsOstRef.
     * 
     * @param costId
     */
    public void setCostId(java.lang.Integer costId) {
        this.costId = costId;
    }


    /**
     * Gets the idOst value for this WsOstRef.
     * 
     * @return idOst
     */
    public java.lang.Integer getIdOst() {
        return idOst;
    }


    /**
     * Sets the idOst value for this WsOstRef.
     * 
     * @param idOst
     */
    public void setIdOst(java.lang.Integer idOst) {
        this.idOst = idOst;
    }


    /**
     * Gets the nostId value for this WsOstRef.
     * 
     * @return nostId
     */
    public java.lang.Integer getNostId() {
        return nostId;
    }


    /**
     * Sets the nostId value for this WsOstRef.
     * 
     * @param nostId
     */
    public void setNostId(java.lang.Integer nostId) {
        this.nostId = nostId;
    }


    /**
     * Gets the title value for this WsOstRef.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this WsOstRef.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsOstRef)) return false;
        WsOstRef other = (WsOstRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.costId==null && other.getCostId()==null) || 
             (this.costId!=null &&
              this.costId.equals(other.getCostId()))) &&
            ((this.idOst==null && other.getIdOst()==null) || 
             (this.idOst!=null &&
              this.idOst.equals(other.getIdOst()))) &&
            ((this.nostId==null && other.getNostId()==null) || 
             (this.nostId!=null &&
              this.nostId.equals(other.getNostId()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle())));
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
        if (getCostId() != null) {
            _hashCode += getCostId().hashCode();
        }
        if (getIdOst() != null) {
            _hashCode += getIdOst().hashCode();
        }
        if (getNostId() != null) {
            _hashCode += getNostId().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsOstRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nostId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nostId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
