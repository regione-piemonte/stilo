/**
 * DepuratoreTipoOpere.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DepuratoreTipoOpere  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idDepTipoOpere;

    private java.lang.String stato;

    private com.hyperborea.sira.ws.VocDepTipologieOpere vocDepTipologieOpere;

    public DepuratoreTipoOpere() {
    }

    public DepuratoreTipoOpere(
           java.lang.Integer idDepTipoOpere,
           java.lang.String stato,
           com.hyperborea.sira.ws.VocDepTipologieOpere vocDepTipologieOpere) {
        this.idDepTipoOpere = idDepTipoOpere;
        this.stato = stato;
        this.vocDepTipologieOpere = vocDepTipologieOpere;
    }


    /**
     * Gets the idDepTipoOpere value for this DepuratoreTipoOpere.
     * 
     * @return idDepTipoOpere
     */
    public java.lang.Integer getIdDepTipoOpere() {
        return idDepTipoOpere;
    }


    /**
     * Sets the idDepTipoOpere value for this DepuratoreTipoOpere.
     * 
     * @param idDepTipoOpere
     */
    public void setIdDepTipoOpere(java.lang.Integer idDepTipoOpere) {
        this.idDepTipoOpere = idDepTipoOpere;
    }


    /**
     * Gets the stato value for this DepuratoreTipoOpere.
     * 
     * @return stato
     */
    public java.lang.String getStato() {
        return stato;
    }


    /**
     * Sets the stato value for this DepuratoreTipoOpere.
     * 
     * @param stato
     */
    public void setStato(java.lang.String stato) {
        this.stato = stato;
    }


    /**
     * Gets the vocDepTipologieOpere value for this DepuratoreTipoOpere.
     * 
     * @return vocDepTipologieOpere
     */
    public com.hyperborea.sira.ws.VocDepTipologieOpere getVocDepTipologieOpere() {
        return vocDepTipologieOpere;
    }


    /**
     * Sets the vocDepTipologieOpere value for this DepuratoreTipoOpere.
     * 
     * @param vocDepTipologieOpere
     */
    public void setVocDepTipologieOpere(com.hyperborea.sira.ws.VocDepTipologieOpere vocDepTipologieOpere) {
        this.vocDepTipologieOpere = vocDepTipologieOpere;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepuratoreTipoOpere)) return false;
        DepuratoreTipoOpere other = (DepuratoreTipoOpere) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idDepTipoOpere==null && other.getIdDepTipoOpere()==null) || 
             (this.idDepTipoOpere!=null &&
              this.idDepTipoOpere.equals(other.getIdDepTipoOpere()))) &&
            ((this.stato==null && other.getStato()==null) || 
             (this.stato!=null &&
              this.stato.equals(other.getStato()))) &&
            ((this.vocDepTipologieOpere==null && other.getVocDepTipologieOpere()==null) || 
             (this.vocDepTipologieOpere!=null &&
              this.vocDepTipologieOpere.equals(other.getVocDepTipologieOpere())));
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
        if (getIdDepTipoOpere() != null) {
            _hashCode += getIdDepTipoOpere().hashCode();
        }
        if (getStato() != null) {
            _hashCode += getStato().hashCode();
        }
        if (getVocDepTipologieOpere() != null) {
            _hashCode += getVocDepTipologieOpere().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepuratoreTipoOpere.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreTipoOpere"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDepTipoOpere");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDepTipoOpere"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocDepTipologieOpere");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocDepTipologieOpere"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDepTipologieOpere"));
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
