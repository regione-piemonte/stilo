/**
 * WsNewFontiDatiRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewFontiDatiRequest  implements java.io.Serializable {
    private com.hyperborea.sira.ws.FontiDati fontiDati;

    private com.hyperborea.sira.ws.WsOstRef[] wsOstRefs;

    private com.hyperborea.sira.ws.WsToken wsToken;

    public WsNewFontiDatiRequest() {
    }

    public WsNewFontiDatiRequest(
           com.hyperborea.sira.ws.FontiDati fontiDati,
           com.hyperborea.sira.ws.WsOstRef[] wsOstRefs,
           com.hyperborea.sira.ws.WsToken wsToken) {
           this.fontiDati = fontiDati;
           this.wsOstRefs = wsOstRefs;
           this.wsToken = wsToken;
    }


    /**
     * Gets the fontiDati value for this WsNewFontiDatiRequest.
     * 
     * @return fontiDati
     */
    public com.hyperborea.sira.ws.FontiDati getFontiDati() {
        return fontiDati;
    }


    /**
     * Sets the fontiDati value for this WsNewFontiDatiRequest.
     * 
     * @param fontiDati
     */
    public void setFontiDati(com.hyperborea.sira.ws.FontiDati fontiDati) {
        this.fontiDati = fontiDati;
    }


    /**
     * Gets the wsOstRefs value for this WsNewFontiDatiRequest.
     * 
     * @return wsOstRefs
     */
    public com.hyperborea.sira.ws.WsOstRef[] getWsOstRefs() {
        return wsOstRefs;
    }


    /**
     * Sets the wsOstRefs value for this WsNewFontiDatiRequest.
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


    /**
     * Gets the wsToken value for this WsNewFontiDatiRequest.
     * 
     * @return wsToken
     */
    public com.hyperborea.sira.ws.WsToken getWsToken() {
        return wsToken;
    }


    /**
     * Sets the wsToken value for this WsNewFontiDatiRequest.
     * 
     * @param wsToken
     */
    public void setWsToken(com.hyperborea.sira.ws.WsToken wsToken) {
        this.wsToken = wsToken;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewFontiDatiRequest)) return false;
        WsNewFontiDatiRequest other = (WsNewFontiDatiRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fontiDati==null && other.getFontiDati()==null) || 
             (this.fontiDati!=null &&
              this.fontiDati.equals(other.getFontiDati()))) &&
            ((this.wsOstRefs==null && other.getWsOstRefs()==null) || 
             (this.wsOstRefs!=null &&
              java.util.Arrays.equals(this.wsOstRefs, other.getWsOstRefs()))) &&
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
        if (getFontiDati() != null) {
            _hashCode += getFontiDati().hashCode();
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
        if (getWsToken() != null) {
            _hashCode += getWsToken().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewFontiDatiRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewFontiDatiRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fontiDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fontiDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fontiDati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsOstRefs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsOstRefs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
