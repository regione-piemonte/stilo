/**
 * Fasi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Fasi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float fase;

    private java.lang.Integer idFase;

    private com.hyperborea.sira.ws.PuntiSospensione puntiSospensione;

    public Fasi() {
    }

    public Fasi(
           java.lang.Float fase,
           java.lang.Integer idFase,
           com.hyperborea.sira.ws.PuntiSospensione puntiSospensione) {
        this.fase = fase;
        this.idFase = idFase;
        this.puntiSospensione = puntiSospensione;
    }


    /**
     * Gets the fase value for this Fasi.
     * 
     * @return fase
     */
    public java.lang.Float getFase() {
        return fase;
    }


    /**
     * Sets the fase value for this Fasi.
     * 
     * @param fase
     */
    public void setFase(java.lang.Float fase) {
        this.fase = fase;
    }


    /**
     * Gets the idFase value for this Fasi.
     * 
     * @return idFase
     */
    public java.lang.Integer getIdFase() {
        return idFase;
    }


    /**
     * Sets the idFase value for this Fasi.
     * 
     * @param idFase
     */
    public void setIdFase(java.lang.Integer idFase) {
        this.idFase = idFase;
    }


    /**
     * Gets the puntiSospensione value for this Fasi.
     * 
     * @return puntiSospensione
     */
    public com.hyperborea.sira.ws.PuntiSospensione getPuntiSospensione() {
        return puntiSospensione;
    }


    /**
     * Sets the puntiSospensione value for this Fasi.
     * 
     * @param puntiSospensione
     */
    public void setPuntiSospensione(com.hyperborea.sira.ws.PuntiSospensione puntiSospensione) {
        this.puntiSospensione = puntiSospensione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Fasi)) return false;
        Fasi other = (Fasi) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fase==null && other.getFase()==null) || 
             (this.fase!=null &&
              this.fase.equals(other.getFase()))) &&
            ((this.idFase==null && other.getIdFase()==null) || 
             (this.idFase!=null &&
              this.idFase.equals(other.getIdFase()))) &&
            ((this.puntiSospensione==null && other.getPuntiSospensione()==null) || 
             (this.puntiSospensione!=null &&
              this.puntiSospensione.equals(other.getPuntiSospensione())));
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
        if (getFase() != null) {
            _hashCode += getFase().hashCode();
        }
        if (getIdFase() != null) {
            _hashCode += getIdFase().hashCode();
        }
        if (getPuntiSospensione() != null) {
            _hashCode += getPuntiSospensione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Fasi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fasi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fase");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idFase");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFase"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("puntiSospensione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntiSospensione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntiSospensione"));
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
