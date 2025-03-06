/**
 * PersonaleAttivitaId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PersonaleAttivitaId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Attivita attivita;

    private com.hyperborea.sira.ws.Personale personale;

    public PersonaleAttivitaId() {
    }

    public PersonaleAttivitaId(
           com.hyperborea.sira.ws.Attivita attivita,
           com.hyperborea.sira.ws.Personale personale) {
           this.attivita = attivita;
           this.personale = personale;
    }


    /**
     * Gets the attivita value for this PersonaleAttivitaId.
     * 
     * @return attivita
     */
    public com.hyperborea.sira.ws.Attivita getAttivita() {
        return attivita;
    }


    /**
     * Sets the attivita value for this PersonaleAttivitaId.
     * 
     * @param attivita
     */
    public void setAttivita(com.hyperborea.sira.ws.Attivita attivita) {
        this.attivita = attivita;
    }


    /**
     * Gets the personale value for this PersonaleAttivitaId.
     * 
     * @return personale
     */
    public com.hyperborea.sira.ws.Personale getPersonale() {
        return personale;
    }


    /**
     * Sets the personale value for this PersonaleAttivitaId.
     * 
     * @param personale
     */
    public void setPersonale(com.hyperborea.sira.ws.Personale personale) {
        this.personale = personale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonaleAttivitaId)) return false;
        PersonaleAttivitaId other = (PersonaleAttivitaId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attivita==null && other.getAttivita()==null) || 
             (this.attivita!=null &&
              this.attivita.equals(other.getAttivita()))) &&
            ((this.personale==null && other.getPersonale()==null) || 
             (this.personale!=null &&
              this.personale.equals(other.getPersonale())));
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
        if (getAttivita() != null) {
            _hashCode += getAttivita().hashCode();
        }
        if (getPersonale() != null) {
            _hashCode += getPersonale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonaleAttivitaId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personaleAttivitaId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "personale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personale"));
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
