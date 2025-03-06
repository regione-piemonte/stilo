/**
 * AdaOstId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AdaOstId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm;

    private com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali;

    public AdaOstId() {
    }

    public AdaOstId(
           com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm,
           com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali) {
           this.attiDisposizioniAmm = attiDisposizioniAmm;
           this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
    }


    /**
     * Gets the attiDisposizioniAmm value for this AdaOstId.
     * 
     * @return attiDisposizioniAmm
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmm() {
        return attiDisposizioniAmm;
    }


    /**
     * Sets the attiDisposizioniAmm value for this AdaOstId.
     * 
     * @param attiDisposizioniAmm
     */
    public void setAttiDisposizioniAmm(com.hyperborea.sira.ws.AttiDisposizioniAmm attiDisposizioniAmm) {
        this.attiDisposizioniAmm = attiDisposizioniAmm;
    }


    /**
     * Gets the oggettiStruttureTerritoriali value for this AdaOstId.
     * 
     * @return oggettiStruttureTerritoriali
     */
    public com.hyperborea.sira.ws.OggettiStruttureTerritoriali getOggettiStruttureTerritoriali() {
        return oggettiStruttureTerritoriali;
    }


    /**
     * Sets the oggettiStruttureTerritoriali value for this AdaOstId.
     * 
     * @param oggettiStruttureTerritoriali
     */
    public void setOggettiStruttureTerritoriali(com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali) {
        this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdaOstId)) return false;
        AdaOstId other = (AdaOstId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attiDisposizioniAmm==null && other.getAttiDisposizioniAmm()==null) || 
             (this.attiDisposizioniAmm!=null &&
              this.attiDisposizioniAmm.equals(other.getAttiDisposizioniAmm()))) &&
            ((this.oggettiStruttureTerritoriali==null && other.getOggettiStruttureTerritoriali()==null) || 
             (this.oggettiStruttureTerritoriali!=null &&
              this.oggettiStruttureTerritoriali.equals(other.getOggettiStruttureTerritoriali())));
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
        if (getAttiDisposizioniAmm() != null) {
            _hashCode += getAttiDisposizioniAmm().hashCode();
        }
        if (getOggettiStruttureTerritoriali() != null) {
            _hashCode += getOggettiStruttureTerritoriali().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AdaOstId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "adaOstId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attiDisposizioniAmm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggettiStruttureTerritoriali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggettiStruttureTerritoriali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "oggettiStruttureTerritoriali"));
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
