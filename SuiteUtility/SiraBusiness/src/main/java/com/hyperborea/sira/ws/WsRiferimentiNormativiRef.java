/**
 * WsRiferimentiNormativiRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsRiferimentiNormativiRef  implements java.io.Serializable {
    private java.lang.Integer idRiferimentiNormativi;

    public WsRiferimentiNormativiRef() {
    }

    public WsRiferimentiNormativiRef(
           java.lang.Integer idRiferimentiNormativi) {
           this.idRiferimentiNormativi = idRiferimentiNormativi;
    }


    /**
     * Gets the idRiferimentiNormativi value for this WsRiferimentiNormativiRef.
     * 
     * @return idRiferimentiNormativi
     */
    public java.lang.Integer getIdRiferimentiNormativi() {
        return idRiferimentiNormativi;
    }


    /**
     * Sets the idRiferimentiNormativi value for this WsRiferimentiNormativiRef.
     * 
     * @param idRiferimentiNormativi
     */
    public void setIdRiferimentiNormativi(java.lang.Integer idRiferimentiNormativi) {
        this.idRiferimentiNormativi = idRiferimentiNormativi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsRiferimentiNormativiRef)) return false;
        WsRiferimentiNormativiRef other = (WsRiferimentiNormativiRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idRiferimentiNormativi==null && other.getIdRiferimentiNormativi()==null) || 
             (this.idRiferimentiNormativi!=null &&
              this.idRiferimentiNormativi.equals(other.getIdRiferimentiNormativi())));
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
        if (getIdRiferimentiNormativi() != null) {
            _hashCode += getIdRiferimentiNormativi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsRiferimentiNormativiRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsRiferimentiNormativiRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRiferimentiNormativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRiferimentiNormativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
