/**
 * ZonaOmogeneaRicettori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ZonaOmogeneaRicettori  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idRicettore;

    private com.hyperborea.sira.ws.VocTipoRicettoreAcustico recettoreAcustico;

    public ZonaOmogeneaRicettori() {
    }

    public ZonaOmogeneaRicettori(
           java.lang.Integer idRicettore,
           com.hyperborea.sira.ws.VocTipoRicettoreAcustico recettoreAcustico) {
        this.idRicettore = idRicettore;
        this.recettoreAcustico = recettoreAcustico;
    }


    /**
     * Gets the idRicettore value for this ZonaOmogeneaRicettori.
     * 
     * @return idRicettore
     */
    public java.lang.Integer getIdRicettore() {
        return idRicettore;
    }


    /**
     * Sets the idRicettore value for this ZonaOmogeneaRicettori.
     * 
     * @param idRicettore
     */
    public void setIdRicettore(java.lang.Integer idRicettore) {
        this.idRicettore = idRicettore;
    }


    /**
     * Gets the recettoreAcustico value for this ZonaOmogeneaRicettori.
     * 
     * @return recettoreAcustico
     */
    public com.hyperborea.sira.ws.VocTipoRicettoreAcustico getRecettoreAcustico() {
        return recettoreAcustico;
    }


    /**
     * Sets the recettoreAcustico value for this ZonaOmogeneaRicettori.
     * 
     * @param recettoreAcustico
     */
    public void setRecettoreAcustico(com.hyperborea.sira.ws.VocTipoRicettoreAcustico recettoreAcustico) {
        this.recettoreAcustico = recettoreAcustico;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZonaOmogeneaRicettori)) return false;
        ZonaOmogeneaRicettori other = (ZonaOmogeneaRicettori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idRicettore==null && other.getIdRicettore()==null) || 
             (this.idRicettore!=null &&
              this.idRicettore.equals(other.getIdRicettore()))) &&
            ((this.recettoreAcustico==null && other.getRecettoreAcustico()==null) || 
             (this.recettoreAcustico!=null &&
              this.recettoreAcustico.equals(other.getRecettoreAcustico())));
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
        if (getIdRicettore() != null) {
            _hashCode += getIdRicettore().hashCode();
        }
        if (getRecettoreAcustico() != null) {
            _hashCode += getRecettoreAcustico().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZonaOmogeneaRicettori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zonaOmogeneaRicettori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRicettore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRicettore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recettoreAcustico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "recettoreAcustico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoRicettoreAcustico"));
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
