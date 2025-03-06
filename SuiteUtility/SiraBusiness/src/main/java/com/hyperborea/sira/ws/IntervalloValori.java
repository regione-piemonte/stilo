/**
 * IntervalloValori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class IntervalloValori  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idIntervallo;

    private java.lang.String tipoIntervallo;

    private java.lang.Float valLimiteInferiore;

    private java.lang.Float valLimiteSuperiore;

    public IntervalloValori() {
    }

    public IntervalloValori(
           java.lang.Integer idIntervallo,
           java.lang.String tipoIntervallo,
           java.lang.Float valLimiteInferiore,
           java.lang.Float valLimiteSuperiore) {
        this.idIntervallo = idIntervallo;
        this.tipoIntervallo = tipoIntervallo;
        this.valLimiteInferiore = valLimiteInferiore;
        this.valLimiteSuperiore = valLimiteSuperiore;
    }


    /**
     * Gets the idIntervallo value for this IntervalloValori.
     * 
     * @return idIntervallo
     */
    public java.lang.Integer getIdIntervallo() {
        return idIntervallo;
    }


    /**
     * Sets the idIntervallo value for this IntervalloValori.
     * 
     * @param idIntervallo
     */
    public void setIdIntervallo(java.lang.Integer idIntervallo) {
        this.idIntervallo = idIntervallo;
    }


    /**
     * Gets the tipoIntervallo value for this IntervalloValori.
     * 
     * @return tipoIntervallo
     */
    public java.lang.String getTipoIntervallo() {
        return tipoIntervallo;
    }


    /**
     * Sets the tipoIntervallo value for this IntervalloValori.
     * 
     * @param tipoIntervallo
     */
    public void setTipoIntervallo(java.lang.String tipoIntervallo) {
        this.tipoIntervallo = tipoIntervallo;
    }


    /**
     * Gets the valLimiteInferiore value for this IntervalloValori.
     * 
     * @return valLimiteInferiore
     */
    public java.lang.Float getValLimiteInferiore() {
        return valLimiteInferiore;
    }


    /**
     * Sets the valLimiteInferiore value for this IntervalloValori.
     * 
     * @param valLimiteInferiore
     */
    public void setValLimiteInferiore(java.lang.Float valLimiteInferiore) {
        this.valLimiteInferiore = valLimiteInferiore;
    }


    /**
     * Gets the valLimiteSuperiore value for this IntervalloValori.
     * 
     * @return valLimiteSuperiore
     */
    public java.lang.Float getValLimiteSuperiore() {
        return valLimiteSuperiore;
    }


    /**
     * Sets the valLimiteSuperiore value for this IntervalloValori.
     * 
     * @param valLimiteSuperiore
     */
    public void setValLimiteSuperiore(java.lang.Float valLimiteSuperiore) {
        this.valLimiteSuperiore = valLimiteSuperiore;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IntervalloValori)) return false;
        IntervalloValori other = (IntervalloValori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idIntervallo==null && other.getIdIntervallo()==null) || 
             (this.idIntervallo!=null &&
              this.idIntervallo.equals(other.getIdIntervallo()))) &&
            ((this.tipoIntervallo==null && other.getTipoIntervallo()==null) || 
             (this.tipoIntervallo!=null &&
              this.tipoIntervallo.equals(other.getTipoIntervallo()))) &&
            ((this.valLimiteInferiore==null && other.getValLimiteInferiore()==null) || 
             (this.valLimiteInferiore!=null &&
              this.valLimiteInferiore.equals(other.getValLimiteInferiore()))) &&
            ((this.valLimiteSuperiore==null && other.getValLimiteSuperiore()==null) || 
             (this.valLimiteSuperiore!=null &&
              this.valLimiteSuperiore.equals(other.getValLimiteSuperiore())));
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
        if (getIdIntervallo() != null) {
            _hashCode += getIdIntervallo().hashCode();
        }
        if (getTipoIntervallo() != null) {
            _hashCode += getTipoIntervallo().hashCode();
        }
        if (getValLimiteInferiore() != null) {
            _hashCode += getValLimiteInferiore().hashCode();
        }
        if (getValLimiteSuperiore() != null) {
            _hashCode += getValLimiteSuperiore().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IntervalloValori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "intervalloValori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idIntervallo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idIntervallo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoIntervallo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoIntervallo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valLimiteInferiore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valLimiteInferiore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valLimiteSuperiore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valLimiteSuperiore"));
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
