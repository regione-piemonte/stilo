/**
 * CompSpecSubparticella.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CompSpecSubparticella  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idComposizioneSpecifica;

    private com.hyperborea.sira.ws.Specie specie;

    private com.hyperborea.sira.ws.VocEfClasseCopertura vocEfClasseCopertura;

    public CompSpecSubparticella() {
    }

    public CompSpecSubparticella(
           java.lang.Integer idComposizioneSpecifica,
           com.hyperborea.sira.ws.Specie specie,
           com.hyperborea.sira.ws.VocEfClasseCopertura vocEfClasseCopertura) {
        this.idComposizioneSpecifica = idComposizioneSpecifica;
        this.specie = specie;
        this.vocEfClasseCopertura = vocEfClasseCopertura;
    }


    /**
     * Gets the idComposizioneSpecifica value for this CompSpecSubparticella.
     * 
     * @return idComposizioneSpecifica
     */
    public java.lang.Integer getIdComposizioneSpecifica() {
        return idComposizioneSpecifica;
    }


    /**
     * Sets the idComposizioneSpecifica value for this CompSpecSubparticella.
     * 
     * @param idComposizioneSpecifica
     */
    public void setIdComposizioneSpecifica(java.lang.Integer idComposizioneSpecifica) {
        this.idComposizioneSpecifica = idComposizioneSpecifica;
    }


    /**
     * Gets the specie value for this CompSpecSubparticella.
     * 
     * @return specie
     */
    public com.hyperborea.sira.ws.Specie getSpecie() {
        return specie;
    }


    /**
     * Sets the specie value for this CompSpecSubparticella.
     * 
     * @param specie
     */
    public void setSpecie(com.hyperborea.sira.ws.Specie specie) {
        this.specie = specie;
    }


    /**
     * Gets the vocEfClasseCopertura value for this CompSpecSubparticella.
     * 
     * @return vocEfClasseCopertura
     */
    public com.hyperborea.sira.ws.VocEfClasseCopertura getVocEfClasseCopertura() {
        return vocEfClasseCopertura;
    }


    /**
     * Sets the vocEfClasseCopertura value for this CompSpecSubparticella.
     * 
     * @param vocEfClasseCopertura
     */
    public void setVocEfClasseCopertura(com.hyperborea.sira.ws.VocEfClasseCopertura vocEfClasseCopertura) {
        this.vocEfClasseCopertura = vocEfClasseCopertura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CompSpecSubparticella)) return false;
        CompSpecSubparticella other = (CompSpecSubparticella) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idComposizioneSpecifica==null && other.getIdComposizioneSpecifica()==null) || 
             (this.idComposizioneSpecifica!=null &&
              this.idComposizioneSpecifica.equals(other.getIdComposizioneSpecifica()))) &&
            ((this.specie==null && other.getSpecie()==null) || 
             (this.specie!=null &&
              this.specie.equals(other.getSpecie()))) &&
            ((this.vocEfClasseCopertura==null && other.getVocEfClasseCopertura()==null) || 
             (this.vocEfClasseCopertura!=null &&
              this.vocEfClasseCopertura.equals(other.getVocEfClasseCopertura())));
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
        if (getIdComposizioneSpecifica() != null) {
            _hashCode += getIdComposizioneSpecifica().hashCode();
        }
        if (getSpecie() != null) {
            _hashCode += getSpecie().hashCode();
        }
        if (getVocEfClasseCopertura() != null) {
            _hashCode += getVocEfClasseCopertura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CompSpecSubparticella.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "compSpecSubparticella"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idComposizioneSpecifica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idComposizioneSpecifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("specie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "specie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "specie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfClasseCopertura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfClasseCopertura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfClasseCopertura"));
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
