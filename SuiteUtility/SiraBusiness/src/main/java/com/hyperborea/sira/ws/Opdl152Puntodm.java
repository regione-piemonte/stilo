/**
 * Opdl152Puntodm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Opdl152Puntodm  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String desSubattivita;

    private com.hyperborea.sira.ws.Opdl152PuntodmId id;

    private java.lang.Float limiteMax;

    public Opdl152Puntodm() {
    }

    public Opdl152Puntodm(
           java.lang.String desSubattivita,
           com.hyperborea.sira.ws.Opdl152PuntodmId id,
           java.lang.Float limiteMax) {
        this.desSubattivita = desSubattivita;
        this.id = id;
        this.limiteMax = limiteMax;
    }


    /**
     * Gets the desSubattivita value for this Opdl152Puntodm.
     * 
     * @return desSubattivita
     */
    public java.lang.String getDesSubattivita() {
        return desSubattivita;
    }


    /**
     * Sets the desSubattivita value for this Opdl152Puntodm.
     * 
     * @param desSubattivita
     */
    public void setDesSubattivita(java.lang.String desSubattivita) {
        this.desSubattivita = desSubattivita;
    }


    /**
     * Gets the id value for this Opdl152Puntodm.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.Opdl152PuntodmId getId() {
        return id;
    }


    /**
     * Sets the id value for this Opdl152Puntodm.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.Opdl152PuntodmId id) {
        this.id = id;
    }


    /**
     * Gets the limiteMax value for this Opdl152Puntodm.
     * 
     * @return limiteMax
     */
    public java.lang.Float getLimiteMax() {
        return limiteMax;
    }


    /**
     * Sets the limiteMax value for this Opdl152Puntodm.
     * 
     * @param limiteMax
     */
    public void setLimiteMax(java.lang.Float limiteMax) {
        this.limiteMax = limiteMax;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Opdl152Puntodm)) return false;
        Opdl152Puntodm other = (Opdl152Puntodm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.desSubattivita==null && other.getDesSubattivita()==null) || 
             (this.desSubattivita!=null &&
              this.desSubattivita.equals(other.getDesSubattivita()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.limiteMax==null && other.getLimiteMax()==null) || 
             (this.limiteMax!=null &&
              this.limiteMax.equals(other.getLimiteMax())));
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
        if (getDesSubattivita() != null) {
            _hashCode += getDesSubattivita().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getLimiteMax() != null) {
            _hashCode += getLimiteMax().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Opdl152Puntodm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152Puntodm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("desSubattivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "desSubattivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152PuntodmId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limiteMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limiteMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
