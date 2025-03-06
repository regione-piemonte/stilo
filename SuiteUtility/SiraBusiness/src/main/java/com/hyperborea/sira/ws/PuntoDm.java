/**
 * PuntoDm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PuntoDm  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer codicePdm;

    private java.lang.String codicePunto;

    private com.hyperborea.sira.ws.DmOperazRecupero dmOperazRecupero;

    private java.lang.String tipologiaRifiuti;

    public PuntoDm() {
    }

    public PuntoDm(
           java.lang.Integer codicePdm,
           java.lang.String codicePunto,
           com.hyperborea.sira.ws.DmOperazRecupero dmOperazRecupero,
           java.lang.String tipologiaRifiuti) {
        this.codicePdm = codicePdm;
        this.codicePunto = codicePunto;
        this.dmOperazRecupero = dmOperazRecupero;
        this.tipologiaRifiuti = tipologiaRifiuti;
    }


    /**
     * Gets the codicePdm value for this PuntoDm.
     * 
     * @return codicePdm
     */
    public java.lang.Integer getCodicePdm() {
        return codicePdm;
    }


    /**
     * Sets the codicePdm value for this PuntoDm.
     * 
     * @param codicePdm
     */
    public void setCodicePdm(java.lang.Integer codicePdm) {
        this.codicePdm = codicePdm;
    }


    /**
     * Gets the codicePunto value for this PuntoDm.
     * 
     * @return codicePunto
     */
    public java.lang.String getCodicePunto() {
        return codicePunto;
    }


    /**
     * Sets the codicePunto value for this PuntoDm.
     * 
     * @param codicePunto
     */
    public void setCodicePunto(java.lang.String codicePunto) {
        this.codicePunto = codicePunto;
    }


    /**
     * Gets the dmOperazRecupero value for this PuntoDm.
     * 
     * @return dmOperazRecupero
     */
    public com.hyperborea.sira.ws.DmOperazRecupero getDmOperazRecupero() {
        return dmOperazRecupero;
    }


    /**
     * Sets the dmOperazRecupero value for this PuntoDm.
     * 
     * @param dmOperazRecupero
     */
    public void setDmOperazRecupero(com.hyperborea.sira.ws.DmOperazRecupero dmOperazRecupero) {
        this.dmOperazRecupero = dmOperazRecupero;
    }


    /**
     * Gets the tipologiaRifiuti value for this PuntoDm.
     * 
     * @return tipologiaRifiuti
     */
    public java.lang.String getTipologiaRifiuti() {
        return tipologiaRifiuti;
    }


    /**
     * Sets the tipologiaRifiuti value for this PuntoDm.
     * 
     * @param tipologiaRifiuti
     */
    public void setTipologiaRifiuti(java.lang.String tipologiaRifiuti) {
        this.tipologiaRifiuti = tipologiaRifiuti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PuntoDm)) return false;
        PuntoDm other = (PuntoDm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codicePdm==null && other.getCodicePdm()==null) || 
             (this.codicePdm!=null &&
              this.codicePdm.equals(other.getCodicePdm()))) &&
            ((this.codicePunto==null && other.getCodicePunto()==null) || 
             (this.codicePunto!=null &&
              this.codicePunto.equals(other.getCodicePunto()))) &&
            ((this.dmOperazRecupero==null && other.getDmOperazRecupero()==null) || 
             (this.dmOperazRecupero!=null &&
              this.dmOperazRecupero.equals(other.getDmOperazRecupero()))) &&
            ((this.tipologiaRifiuti==null && other.getTipologiaRifiuti()==null) || 
             (this.tipologiaRifiuti!=null &&
              this.tipologiaRifiuti.equals(other.getTipologiaRifiuti())));
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
        if (getCodicePdm() != null) {
            _hashCode += getCodicePdm().hashCode();
        }
        if (getCodicePunto() != null) {
            _hashCode += getCodicePunto().hashCode();
        }
        if (getDmOperazRecupero() != null) {
            _hashCode += getDmOperazRecupero().hashCode();
        }
        if (getTipologiaRifiuti() != null) {
            _hashCode += getTipologiaRifiuti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PuntoDm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntoDm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codicePdm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codicePdm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codicePunto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codicePunto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dmOperazRecupero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dmOperazRecupero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dmOperazRecupero"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologiaRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
