/**
 * PersonaleAttivita.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PersonaleAttivita  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.PersonaleAttivitaId id;

    private java.lang.Long tempoImpiegato;

    public PersonaleAttivita() {
    }

    public PersonaleAttivita(
           com.hyperborea.sira.ws.PersonaleAttivitaId id,
           java.lang.Long tempoImpiegato) {
        this.id = id;
        this.tempoImpiegato = tempoImpiegato;
    }


    /**
     * Gets the id value for this PersonaleAttivita.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.PersonaleAttivitaId getId() {
        return id;
    }


    /**
     * Sets the id value for this PersonaleAttivita.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.PersonaleAttivitaId id) {
        this.id = id;
    }


    /**
     * Gets the tempoImpiegato value for this PersonaleAttivita.
     * 
     * @return tempoImpiegato
     */
    public java.lang.Long getTempoImpiegato() {
        return tempoImpiegato;
    }


    /**
     * Sets the tempoImpiegato value for this PersonaleAttivita.
     * 
     * @param tempoImpiegato
     */
    public void setTempoImpiegato(java.lang.Long tempoImpiegato) {
        this.tempoImpiegato = tempoImpiegato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonaleAttivita)) return false;
        PersonaleAttivita other = (PersonaleAttivita) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.tempoImpiegato==null && other.getTempoImpiegato()==null) || 
             (this.tempoImpiegato!=null &&
              this.tempoImpiegato.equals(other.getTempoImpiegato())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getTempoImpiegato() != null) {
            _hashCode += getTempoImpiegato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonaleAttivita.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personaleAttivita"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personaleAttivitaId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tempoImpiegato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tempoImpiegato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
