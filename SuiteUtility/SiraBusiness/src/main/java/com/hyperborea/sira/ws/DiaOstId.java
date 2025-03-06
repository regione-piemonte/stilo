/**
 * DiaOstId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DiaOstId  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali;

    private com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali;

    public DiaOstId() {
    }

    public DiaOstId(
           com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali,
           com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali) {
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
        this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
    }


    /**
     * Gets the dichiarazioniAmbientali value for this DiaOstId.
     * 
     * @return dichiarazioniAmbientali
     */
    public com.hyperborea.sira.ws.DichiarazioniAmbientali getDichiarazioniAmbientali() {
        return dichiarazioniAmbientali;
    }


    /**
     * Sets the dichiarazioniAmbientali value for this DiaOstId.
     * 
     * @param dichiarazioniAmbientali
     */
    public void setDichiarazioniAmbientali(com.hyperborea.sira.ws.DichiarazioniAmbientali dichiarazioniAmbientali) {
        this.dichiarazioniAmbientali = dichiarazioniAmbientali;
    }


    /**
     * Gets the oggettiStruttureTerritoriali value for this DiaOstId.
     * 
     * @return oggettiStruttureTerritoriali
     */
    public com.hyperborea.sira.ws.OggettiStruttureTerritoriali getOggettiStruttureTerritoriali() {
        return oggettiStruttureTerritoriali;
    }


    /**
     * Sets the oggettiStruttureTerritoriali value for this DiaOstId.
     * 
     * @param oggettiStruttureTerritoriali
     */
    public void setOggettiStruttureTerritoriali(com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali) {
        this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DiaOstId)) return false;
        DiaOstId other = (DiaOstId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dichiarazioniAmbientali==null && other.getDichiarazioniAmbientali()==null) || 
             (this.dichiarazioniAmbientali!=null &&
              this.dichiarazioniAmbientali.equals(other.getDichiarazioniAmbientali()))) &&
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
        int _hashCode = super.hashCode();
        if (getDichiarazioniAmbientali() != null) {
            _hashCode += getDichiarazioniAmbientali().hashCode();
        }
        if (getOggettiStruttureTerritoriali() != null) {
            _hashCode += getOggettiStruttureTerritoriali().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DiaOstId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "diaOstId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dichiarazioniAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dichiarazioniAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dichiarazioniAmbientali"));
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
