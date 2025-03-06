/**
 * WsFonteGenerica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsFonteGenerica  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsCcostRef ccostIntestatarioRef;

    private java.lang.Integer tipoFonte;

    private com.hyperborea.sira.ws.WsCcostRef[] wsCcostRefs;

    private com.hyperborea.sira.ws.WsOstRef[] wsOstRefs;

    public WsFonteGenerica() {
    }

    public WsFonteGenerica(
           com.hyperborea.sira.ws.WsCcostRef ccostIntestatarioRef,
           java.lang.Integer tipoFonte,
           com.hyperborea.sira.ws.WsCcostRef[] wsCcostRefs,
           com.hyperborea.sira.ws.WsOstRef[] wsOstRefs) {
           this.ccostIntestatarioRef = ccostIntestatarioRef;
           this.tipoFonte = tipoFonte;
           this.wsCcostRefs = wsCcostRefs;
           this.wsOstRefs = wsOstRefs;
    }


    /**
     * Gets the ccostIntestatarioRef value for this WsFonteGenerica.
     * 
     * @return ccostIntestatarioRef
     */
    public com.hyperborea.sira.ws.WsCcostRef getCcostIntestatarioRef() {
        return ccostIntestatarioRef;
    }


    /**
     * Sets the ccostIntestatarioRef value for this WsFonteGenerica.
     * 
     * @param ccostIntestatarioRef
     */
    public void setCcostIntestatarioRef(com.hyperborea.sira.ws.WsCcostRef ccostIntestatarioRef) {
        this.ccostIntestatarioRef = ccostIntestatarioRef;
    }


    /**
     * Gets the tipoFonte value for this WsFonteGenerica.
     * 
     * @return tipoFonte
     */
    public java.lang.Integer getTipoFonte() {
        return tipoFonte;
    }


    /**
     * Sets the tipoFonte value for this WsFonteGenerica.
     * 
     * @param tipoFonte
     */
    public void setTipoFonte(java.lang.Integer tipoFonte) {
        this.tipoFonte = tipoFonte;
    }


    /**
     * Gets the wsCcostRefs value for this WsFonteGenerica.
     * 
     * @return wsCcostRefs
     */
    public com.hyperborea.sira.ws.WsCcostRef[] getWsCcostRefs() {
        return wsCcostRefs;
    }


    /**
     * Sets the wsCcostRefs value for this WsFonteGenerica.
     * 
     * @param wsCcostRefs
     */
    public void setWsCcostRefs(com.hyperborea.sira.ws.WsCcostRef[] wsCcostRefs) {
        this.wsCcostRefs = wsCcostRefs;
    }

    public com.hyperborea.sira.ws.WsCcostRef getWsCcostRefs(int i) {
        return this.wsCcostRefs[i];
    }

    public void setWsCcostRefs(int i, com.hyperborea.sira.ws.WsCcostRef _value) {
        this.wsCcostRefs[i] = _value;
    }


    /**
     * Gets the wsOstRefs value for this WsFonteGenerica.
     * 
     * @return wsOstRefs
     */
    public com.hyperborea.sira.ws.WsOstRef[] getWsOstRefs() {
        return wsOstRefs;
    }


    /**
     * Sets the wsOstRefs value for this WsFonteGenerica.
     * 
     * @param wsOstRefs
     */
    public void setWsOstRefs(com.hyperborea.sira.ws.WsOstRef[] wsOstRefs) {
        this.wsOstRefs = wsOstRefs;
    }

    public com.hyperborea.sira.ws.WsOstRef getWsOstRefs(int i) {
        return this.wsOstRefs[i];
    }

    public void setWsOstRefs(int i, com.hyperborea.sira.ws.WsOstRef _value) {
        this.wsOstRefs[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsFonteGenerica)) return false;
        WsFonteGenerica other = (WsFonteGenerica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostIntestatarioRef==null && other.getCcostIntestatarioRef()==null) || 
             (this.ccostIntestatarioRef!=null &&
              this.ccostIntestatarioRef.equals(other.getCcostIntestatarioRef()))) &&
            ((this.tipoFonte==null && other.getTipoFonte()==null) || 
             (this.tipoFonte!=null &&
              this.tipoFonte.equals(other.getTipoFonte()))) &&
            ((this.wsCcostRefs==null && other.getWsCcostRefs()==null) || 
             (this.wsCcostRefs!=null &&
              java.util.Arrays.equals(this.wsCcostRefs, other.getWsCcostRefs()))) &&
            ((this.wsOstRefs==null && other.getWsOstRefs()==null) || 
             (this.wsOstRefs!=null &&
              java.util.Arrays.equals(this.wsOstRefs, other.getWsOstRefs())));
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
        if (getCcostIntestatarioRef() != null) {
            _hashCode += getCcostIntestatarioRef().hashCode();
        }
        if (getTipoFonte() != null) {
            _hashCode += getTipoFonte().hashCode();
        }
        if (getWsCcostRefs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getWsCcostRefs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getWsCcostRefs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getWsOstRefs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getWsOstRefs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getWsOstRefs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsFonteGenerica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteGenerica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostIntestatarioRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostIntestatarioRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCcostRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoFonte");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoFonte"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostRefs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostRefs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCcostRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsOstRefs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsOstRefs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
