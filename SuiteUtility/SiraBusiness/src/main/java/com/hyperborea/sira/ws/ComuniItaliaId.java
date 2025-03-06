/**
 * ComuniItaliaId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ComuniItaliaId  implements java.io.Serializable {
    private java.lang.String istatCom;

    private java.lang.String istatProv;

    private java.lang.String istatReg;

    public ComuniItaliaId() {
    }

    public ComuniItaliaId(
           java.lang.String istatCom,
           java.lang.String istatProv,
           java.lang.String istatReg) {
           this.istatCom = istatCom;
           this.istatProv = istatProv;
           this.istatReg = istatReg;
    }


    /**
     * Gets the istatCom value for this ComuniItaliaId.
     * 
     * @return istatCom
     */
    public java.lang.String getIstatCom() {
        return istatCom;
    }


    /**
     * Sets the istatCom value for this ComuniItaliaId.
     * 
     * @param istatCom
     */
    public void setIstatCom(java.lang.String istatCom) {
        this.istatCom = istatCom;
    }


    /**
     * Gets the istatProv value for this ComuniItaliaId.
     * 
     * @return istatProv
     */
    public java.lang.String getIstatProv() {
        return istatProv;
    }


    /**
     * Sets the istatProv value for this ComuniItaliaId.
     * 
     * @param istatProv
     */
    public void setIstatProv(java.lang.String istatProv) {
        this.istatProv = istatProv;
    }


    /**
     * Gets the istatReg value for this ComuniItaliaId.
     * 
     * @return istatReg
     */
    public java.lang.String getIstatReg() {
        return istatReg;
    }


    /**
     * Sets the istatReg value for this ComuniItaliaId.
     * 
     * @param istatReg
     */
    public void setIstatReg(java.lang.String istatReg) {
        this.istatReg = istatReg;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ComuniItaliaId)) return false;
        ComuniItaliaId other = (ComuniItaliaId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.istatCom==null && other.getIstatCom()==null) || 
             (this.istatCom!=null &&
              this.istatCom.equals(other.getIstatCom()))) &&
            ((this.istatProv==null && other.getIstatProv()==null) || 
             (this.istatProv!=null &&
              this.istatProv.equals(other.getIstatProv()))) &&
            ((this.istatReg==null && other.getIstatReg()==null) || 
             (this.istatReg!=null &&
              this.istatReg.equals(other.getIstatReg())));
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
        if (getIstatCom() != null) {
            _hashCode += getIstatCom().hashCode();
        }
        if (getIstatProv() != null) {
            _hashCode += getIstatProv().hashCode();
        }
        if (getIstatReg() != null) {
            _hashCode += getIstatReg().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ComuniItaliaId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItaliaId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("istatCom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "istatCom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("istatProv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "istatProv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("istatReg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "istatReg"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
