/**
 * WsNewCcostRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewCcostRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst;

    private com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef;

    private com.hyperborea.sira.ws.WsOstRef wsOstRef;

    private com.hyperborea.sira.ws.WsToken wsToken;

    public WsNewCcostRequest() {
    }

    public WsNewCcostRequest(
           com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst,
           com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef,
           com.hyperborea.sira.ws.WsOstRef wsOstRef,
           com.hyperborea.sira.ws.WsToken wsToken) {
           this.caratterizzazioniOst = caratterizzazioniOst;
           this.wsFontiDatiRef = wsFontiDatiRef;
           this.wsOstRef = wsOstRef;
           this.wsToken = wsToken;
    }


    /**
     * Gets the caratterizzazioniOst value for this WsNewCcostRequest.
     * 
     * @return caratterizzazioniOst
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOst() {
        return caratterizzazioniOst;
    }


    /**
     * Sets the caratterizzazioniOst value for this WsNewCcostRequest.
     * 
     * @param caratterizzazioniOst
     */
    public void setCaratterizzazioniOst(com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
        this.caratterizzazioniOst = caratterizzazioniOst;
    }


    /**
     * Gets the wsFontiDatiRef value for this WsNewCcostRequest.
     * 
     * @return wsFontiDatiRef
     */
    public com.hyperborea.sira.ws.WsFontiDatiRef getWsFontiDatiRef() {
        return wsFontiDatiRef;
    }


    /**
     * Sets the wsFontiDatiRef value for this WsNewCcostRequest.
     * 
     * @param wsFontiDatiRef
     */
    public void setWsFontiDatiRef(com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef) {
        this.wsFontiDatiRef = wsFontiDatiRef;
    }


    /**
     * Gets the wsOstRef value for this WsNewCcostRequest.
     * 
     * @return wsOstRef
     */
    public com.hyperborea.sira.ws.WsOstRef getWsOstRef() {
        return wsOstRef;
    }


    /**
     * Sets the wsOstRef value for this WsNewCcostRequest.
     * 
     * @param wsOstRef
     */
    public void setWsOstRef(com.hyperborea.sira.ws.WsOstRef wsOstRef) {
        this.wsOstRef = wsOstRef;
    }


    /**
     * Gets the wsToken value for this WsNewCcostRequest.
     * 
     * @return wsToken
     */
    public com.hyperborea.sira.ws.WsToken getWsToken() {
        return wsToken;
    }


    /**
     * Sets the wsToken value for this WsNewCcostRequest.
     * 
     * @param wsToken
     */
    public void setWsToken(com.hyperborea.sira.ws.WsToken wsToken) {
        this.wsToken = wsToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewCcostRequest)) return false;
        WsNewCcostRequest other = (WsNewCcostRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.caratterizzazioniOst==null && other.getCaratterizzazioniOst()==null) || 
             (this.caratterizzazioniOst!=null &&
              this.caratterizzazioniOst.equals(other.getCaratterizzazioniOst()))) &&
            ((this.wsFontiDatiRef==null && other.getWsFontiDatiRef()==null) || 
             (this.wsFontiDatiRef!=null &&
              this.wsFontiDatiRef.equals(other.getWsFontiDatiRef()))) &&
            ((this.wsOstRef==null && other.getWsOstRef()==null) || 
             (this.wsOstRef!=null &&
              this.wsOstRef.equals(other.getWsOstRef()))) &&
            ((this.wsToken==null && other.getWsToken()==null) || 
             (this.wsToken!=null &&
              this.wsToken.equals(other.getWsToken())));
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
        if (getCaratterizzazioniOst() != null) {
            _hashCode += getCaratterizzazioniOst().hashCode();
        }
        if (getWsFontiDatiRef() != null) {
            _hashCode += getWsFontiDatiRef().hashCode();
        }
        if (getWsOstRef() != null) {
            _hashCode += getWsOstRef().hashCode();
        }
        if (getWsToken() != null) {
            _hashCode += getWsToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewCcostRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewCcostRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFontiDatiRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFontiDatiRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsOstRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsOstRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsToken");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsToken"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsToken"));
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
