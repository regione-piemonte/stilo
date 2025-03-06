/**
 * StatioperativiCostNostId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class StatioperativiCostNostId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.StatiOperativi statiOperativi;

    public StatioperativiCostNostId() {
    }

    public StatioperativiCostNostId(
           com.hyperborea.sira.ws.StatiOperativi statiOperativi) {
           this.statiOperativi = statiOperativi;
    }


    /**
     * Gets the statiOperativi value for this StatioperativiCostNostId.
     * 
     * @return statiOperativi
     */
    public com.hyperborea.sira.ws.StatiOperativi getStatiOperativi() {
        return statiOperativi;
    }


    /**
     * Sets the statiOperativi value for this StatioperativiCostNostId.
     * 
     * @param statiOperativi
     */
    public void setStatiOperativi(com.hyperborea.sira.ws.StatiOperativi statiOperativi) {
        this.statiOperativi = statiOperativi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatioperativiCostNostId)) return false;
        StatioperativiCostNostId other = (StatioperativiCostNostId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statiOperativi==null && other.getStatiOperativi()==null) || 
             (this.statiOperativi!=null &&
              this.statiOperativi.equals(other.getStatiOperativi())));
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
        if (getStatiOperativi() != null) {
            _hashCode += getStatiOperativi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatioperativiCostNostId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statioperativiCostNostId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statiOperativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statiOperativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOperativi"));
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
