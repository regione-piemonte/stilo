/**
 * TipimisureProfiliprovaId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipimisureProfiliprovaId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsTipiMisureRef wsTipiMisureRef;

    public TipimisureProfiliprovaId() {
    }

    public TipimisureProfiliprovaId(
           com.hyperborea.sira.ws.WsTipiMisureRef wsTipiMisureRef) {
           this.wsTipiMisureRef = wsTipiMisureRef;
    }


    /**
     * Gets the wsTipiMisureRef value for this TipimisureProfiliprovaId.
     * 
     * @return wsTipiMisureRef
     */
    public com.hyperborea.sira.ws.WsTipiMisureRef getWsTipiMisureRef() {
        return wsTipiMisureRef;
    }


    /**
     * Sets the wsTipiMisureRef value for this TipimisureProfiliprovaId.
     * 
     * @param wsTipiMisureRef
     */
    public void setWsTipiMisureRef(com.hyperborea.sira.ws.WsTipiMisureRef wsTipiMisureRef) {
        this.wsTipiMisureRef = wsTipiMisureRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipimisureProfiliprovaId)) return false;
        TipimisureProfiliprovaId other = (TipimisureProfiliprovaId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsTipiMisureRef==null && other.getWsTipiMisureRef()==null) || 
             (this.wsTipiMisureRef!=null &&
              this.wsTipiMisureRef.equals(other.getWsTipiMisureRef())));
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
        if (getWsTipiMisureRef() != null) {
            _hashCode += getWsTipiMisureRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipimisureProfiliprovaId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipimisureProfiliprovaId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTipiMisureRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTipiMisureRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiMisureRef"));
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
