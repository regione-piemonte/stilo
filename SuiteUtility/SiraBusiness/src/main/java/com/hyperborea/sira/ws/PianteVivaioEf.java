/**
 * PianteVivaioEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PianteVivaioEf  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String cureFitosanitarie;

    private java.lang.Integer idPianta;

    private com.hyperborea.sira.ws.Specie specie;

    public PianteVivaioEf() {
    }

    public PianteVivaioEf(
           java.lang.String cureFitosanitarie,
           java.lang.Integer idPianta,
           com.hyperborea.sira.ws.Specie specie) {
        this.cureFitosanitarie = cureFitosanitarie;
        this.idPianta = idPianta;
        this.specie = specie;
    }


    /**
     * Gets the cureFitosanitarie value for this PianteVivaioEf.
     * 
     * @return cureFitosanitarie
     */
    public java.lang.String getCureFitosanitarie() {
        return cureFitosanitarie;
    }


    /**
     * Sets the cureFitosanitarie value for this PianteVivaioEf.
     * 
     * @param cureFitosanitarie
     */
    public void setCureFitosanitarie(java.lang.String cureFitosanitarie) {
        this.cureFitosanitarie = cureFitosanitarie;
    }


    /**
     * Gets the idPianta value for this PianteVivaioEf.
     * 
     * @return idPianta
     */
    public java.lang.Integer getIdPianta() {
        return idPianta;
    }


    /**
     * Sets the idPianta value for this PianteVivaioEf.
     * 
     * @param idPianta
     */
    public void setIdPianta(java.lang.Integer idPianta) {
        this.idPianta = idPianta;
    }


    /**
     * Gets the specie value for this PianteVivaioEf.
     * 
     * @return specie
     */
    public com.hyperborea.sira.ws.Specie getSpecie() {
        return specie;
    }


    /**
     * Sets the specie value for this PianteVivaioEf.
     * 
     * @param specie
     */
    public void setSpecie(com.hyperborea.sira.ws.Specie specie) {
        this.specie = specie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PianteVivaioEf)) return false;
        PianteVivaioEf other = (PianteVivaioEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cureFitosanitarie==null && other.getCureFitosanitarie()==null) || 
             (this.cureFitosanitarie!=null &&
              this.cureFitosanitarie.equals(other.getCureFitosanitarie()))) &&
            ((this.idPianta==null && other.getIdPianta()==null) || 
             (this.idPianta!=null &&
              this.idPianta.equals(other.getIdPianta()))) &&
            ((this.specie==null && other.getSpecie()==null) || 
             (this.specie!=null &&
              this.specie.equals(other.getSpecie())));
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
        if (getCureFitosanitarie() != null) {
            _hashCode += getCureFitosanitarie().hashCode();
        }
        if (getIdPianta() != null) {
            _hashCode += getIdPianta().hashCode();
        }
        if (getSpecie() != null) {
            _hashCode += getSpecie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PianteVivaioEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pianteVivaioEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cureFitosanitarie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cureFitosanitarie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPianta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPianta"));
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
