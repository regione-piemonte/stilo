/**
 * PaOstId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PaOstId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali;

    private com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative;

    public PaOstId() {
    }

    public PaOstId(
           com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali,
           com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative) {
           this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
           this.praticheAmministrative = praticheAmministrative;
    }


    /**
     * Gets the oggettiStruttureTerritoriali value for this PaOstId.
     * 
     * @return oggettiStruttureTerritoriali
     */
    public com.hyperborea.sira.ws.OggettiStruttureTerritoriali getOggettiStruttureTerritoriali() {
        return oggettiStruttureTerritoriali;
    }


    /**
     * Sets the oggettiStruttureTerritoriali value for this PaOstId.
     * 
     * @param oggettiStruttureTerritoriali
     */
    public void setOggettiStruttureTerritoriali(com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali) {
        this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
    }


    /**
     * Gets the praticheAmministrative value for this PaOstId.
     * 
     * @return praticheAmministrative
     */
    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministrative() {
        return praticheAmministrative;
    }


    /**
     * Sets the praticheAmministrative value for this PaOstId.
     * 
     * @param praticheAmministrative
     */
    public void setPraticheAmministrative(com.hyperborea.sira.ws.PraticheAmministrative praticheAmministrative) {
        this.praticheAmministrative = praticheAmministrative;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PaOstId)) return false;
        PaOstId other = (PaOstId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.oggettiStruttureTerritoriali==null && other.getOggettiStruttureTerritoriali()==null) || 
             (this.oggettiStruttureTerritoriali!=null &&
              this.oggettiStruttureTerritoriali.equals(other.getOggettiStruttureTerritoriali()))) &&
            ((this.praticheAmministrative==null && other.getPraticheAmministrative()==null) || 
             (this.praticheAmministrative!=null &&
              this.praticheAmministrative.equals(other.getPraticheAmministrative())));
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
        if (getOggettiStruttureTerritoriali() != null) {
            _hashCode += getOggettiStruttureTerritoriali().hashCode();
        }
        if (getPraticheAmministrative() != null) {
            _hashCode += getPraticheAmministrative().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PaOstId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "paOstId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggettiStruttureTerritoriali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggettiStruttureTerritoriali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "oggettiStruttureTerritoriali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("praticheAmministrative");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministrative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
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
