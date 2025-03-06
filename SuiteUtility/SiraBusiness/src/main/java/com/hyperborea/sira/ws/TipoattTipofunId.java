/**
 * TipoattTipofunId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipoattTipofunId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.TipologieAttivita tipologieAttivita;

    private com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni;

    public TipoattTipofunId() {
    }

    public TipoattTipofunId(
           com.hyperborea.sira.ws.TipologieAttivita tipologieAttivita,
           com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni) {
           this.tipologieAttivita = tipologieAttivita;
           this.tipologieFunzioni = tipologieFunzioni;
    }


    /**
     * Gets the tipologieAttivita value for this TipoattTipofunId.
     * 
     * @return tipologieAttivita
     */
    public com.hyperborea.sira.ws.TipologieAttivita getTipologieAttivita() {
        return tipologieAttivita;
    }


    /**
     * Sets the tipologieAttivita value for this TipoattTipofunId.
     * 
     * @param tipologieAttivita
     */
    public void setTipologieAttivita(com.hyperborea.sira.ws.TipologieAttivita tipologieAttivita) {
        this.tipologieAttivita = tipologieAttivita;
    }


    /**
     * Gets the tipologieFunzioni value for this TipoattTipofunId.
     * 
     * @return tipologieFunzioni
     */
    public com.hyperborea.sira.ws.TipologieFunzioni getTipologieFunzioni() {
        return tipologieFunzioni;
    }


    /**
     * Sets the tipologieFunzioni value for this TipoattTipofunId.
     * 
     * @param tipologieFunzioni
     */
    public void setTipologieFunzioni(com.hyperborea.sira.ws.TipologieFunzioni tipologieFunzioni) {
        this.tipologieFunzioni = tipologieFunzioni;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipoattTipofunId)) return false;
        TipoattTipofunId other = (TipoattTipofunId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipologieAttivita==null && other.getTipologieAttivita()==null) || 
             (this.tipologieAttivita!=null &&
              this.tipologieAttivita.equals(other.getTipologieAttivita()))) &&
            ((this.tipologieFunzioni==null && other.getTipologieFunzioni()==null) || 
             (this.tipologieFunzioni!=null &&
              this.tipologieFunzioni.equals(other.getTipologieFunzioni())));
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
        if (getTipologieAttivita() != null) {
            _hashCode += getTipologieAttivita().hashCode();
        }
        if (getTipologieFunzioni() != null) {
            _hashCode += getTipologieFunzioni().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipoattTipofunId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipoattTipofunId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieAttivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieFunzioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieFunzioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieFunzioni"));
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
