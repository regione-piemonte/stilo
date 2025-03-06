/**
 * PersonaleUoId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PersonaleUoId  implements java.io.Serializable {
    private org.apache.axis.types.UnsignedShort attivo;

    private com.hyperborea.sira.ws.Personale personale;

    private com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative;

    public PersonaleUoId() {
    }

    public PersonaleUoId(
           org.apache.axis.types.UnsignedShort attivo,
           com.hyperborea.sira.ws.Personale personale,
           com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
           this.attivo = attivo;
           this.personale = personale;
           this.unitaOrganizzative = unitaOrganizzative;
    }


    /**
     * Gets the attivo value for this PersonaleUoId.
     * 
     * @return attivo
     */
    public org.apache.axis.types.UnsignedShort getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this PersonaleUoId.
     * 
     * @param attivo
     */
    public void setAttivo(org.apache.axis.types.UnsignedShort attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the personale value for this PersonaleUoId.
     * 
     * @return personale
     */
    public com.hyperborea.sira.ws.Personale getPersonale() {
        return personale;
    }


    /**
     * Sets the personale value for this PersonaleUoId.
     * 
     * @param personale
     */
    public void setPersonale(com.hyperborea.sira.ws.Personale personale) {
        this.personale = personale;
    }


    /**
     * Gets the unitaOrganizzative value for this PersonaleUoId.
     * 
     * @return unitaOrganizzative
     */
    public com.hyperborea.sira.ws.UnitaOrganizzative getUnitaOrganizzative() {
        return unitaOrganizzative;
    }


    /**
     * Sets the unitaOrganizzative value for this PersonaleUoId.
     * 
     * @param unitaOrganizzative
     */
    public void setUnitaOrganizzative(com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
        this.unitaOrganizzative = unitaOrganizzative;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonaleUoId)) return false;
        PersonaleUoId other = (PersonaleUoId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attivo==null && other.getAttivo()==null) || 
             (this.attivo!=null &&
              this.attivo.equals(other.getAttivo()))) &&
            ((this.personale==null && other.getPersonale()==null) || 
             (this.personale!=null &&
              this.personale.equals(other.getPersonale()))) &&
            ((this.unitaOrganizzative==null && other.getUnitaOrganizzative()==null) || 
             (this.unitaOrganizzative!=null &&
              this.unitaOrganizzative.equals(other.getUnitaOrganizzative())));
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
        if (getAttivo() != null) {
            _hashCode += getAttivo().hashCode();
        }
        if (getPersonale() != null) {
            _hashCode += getPersonale().hashCode();
        }
        if (getUnitaOrganizzative() != null) {
            _hashCode += getUnitaOrganizzative().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonaleUoId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "personaleUoId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitaOrganizzative");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitaOrganizzative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaOrganizzative"));
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
