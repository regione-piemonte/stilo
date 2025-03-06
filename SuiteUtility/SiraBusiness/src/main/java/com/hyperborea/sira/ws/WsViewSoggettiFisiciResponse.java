/**
 * WsViewSoggettiFisiciResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsViewSoggettiFisiciResponse  implements java.io.Serializable {
    private com.hyperborea.sira.ws.SoggettiFisici soggettiFisici;

    public WsViewSoggettiFisiciResponse() {
    }

    public WsViewSoggettiFisiciResponse(
           com.hyperborea.sira.ws.SoggettiFisici soggettiFisici) {
           this.soggettiFisici = soggettiFisici;
    }


    /**
     * Gets the soggettiFisici value for this WsViewSoggettiFisiciResponse.
     * 
     * @return soggettiFisici
     */
    public com.hyperborea.sira.ws.SoggettiFisici getSoggettiFisici() {
        return soggettiFisici;
    }


    /**
     * Sets the soggettiFisici value for this WsViewSoggettiFisiciResponse.
     * 
     * @param soggettiFisici
     */
    public void setSoggettiFisici(com.hyperborea.sira.ws.SoggettiFisici soggettiFisici) {
        this.soggettiFisici = soggettiFisici;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsViewSoggettiFisiciResponse)) return false;
        WsViewSoggettiFisiciResponse other = (WsViewSoggettiFisiciResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.soggettiFisici==null && other.getSoggettiFisici()==null) || 
             (this.soggettiFisici!=null &&
              this.soggettiFisici.equals(other.getSoggettiFisici())));
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
        if (getSoggettiFisici() != null) {
            _hashCode += getSoggettiFisici().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsViewSoggettiFisiciResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsViewSoggettiFisiciResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggettiFisici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggettiFisici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisici"));
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
