/**
 * EmissioniInquinanteAtm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class EmissioniInquinanteAtm  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float flussoMassa;

    private java.lang.Integer idEmissione;

    private com.hyperborea.sira.ws.VocPericolositaInquinante pericolositaInquinante;

    private java.lang.String sostanzeInquinanti;

    public EmissioniInquinanteAtm() {
    }

    public EmissioniInquinanteAtm(
           java.lang.Float flussoMassa,
           java.lang.Integer idEmissione,
           com.hyperborea.sira.ws.VocPericolositaInquinante pericolositaInquinante,
           java.lang.String sostanzeInquinanti) {
        this.flussoMassa = flussoMassa;
        this.idEmissione = idEmissione;
        this.pericolositaInquinante = pericolositaInquinante;
        this.sostanzeInquinanti = sostanzeInquinanti;
    }


    /**
     * Gets the flussoMassa value for this EmissioniInquinanteAtm.
     * 
     * @return flussoMassa
     */
    public java.lang.Float getFlussoMassa() {
        return flussoMassa;
    }


    /**
     * Sets the flussoMassa value for this EmissioniInquinanteAtm.
     * 
     * @param flussoMassa
     */
    public void setFlussoMassa(java.lang.Float flussoMassa) {
        this.flussoMassa = flussoMassa;
    }


    /**
     * Gets the idEmissione value for this EmissioniInquinanteAtm.
     * 
     * @return idEmissione
     */
    public java.lang.Integer getIdEmissione() {
        return idEmissione;
    }


    /**
     * Sets the idEmissione value for this EmissioniInquinanteAtm.
     * 
     * @param idEmissione
     */
    public void setIdEmissione(java.lang.Integer idEmissione) {
        this.idEmissione = idEmissione;
    }


    /**
     * Gets the pericolositaInquinante value for this EmissioniInquinanteAtm.
     * 
     * @return pericolositaInquinante
     */
    public com.hyperborea.sira.ws.VocPericolositaInquinante getPericolositaInquinante() {
        return pericolositaInquinante;
    }


    /**
     * Sets the pericolositaInquinante value for this EmissioniInquinanteAtm.
     * 
     * @param pericolositaInquinante
     */
    public void setPericolositaInquinante(com.hyperborea.sira.ws.VocPericolositaInquinante pericolositaInquinante) {
        this.pericolositaInquinante = pericolositaInquinante;
    }


    /**
     * Gets the sostanzeInquinanti value for this EmissioniInquinanteAtm.
     * 
     * @return sostanzeInquinanti
     */
    public java.lang.String getSostanzeInquinanti() {
        return sostanzeInquinanti;
    }


    /**
     * Sets the sostanzeInquinanti value for this EmissioniInquinanteAtm.
     * 
     * @param sostanzeInquinanti
     */
    public void setSostanzeInquinanti(java.lang.String sostanzeInquinanti) {
        this.sostanzeInquinanti = sostanzeInquinanti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmissioniInquinanteAtm)) return false;
        EmissioniInquinanteAtm other = (EmissioniInquinanteAtm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.flussoMassa==null && other.getFlussoMassa()==null) || 
             (this.flussoMassa!=null &&
              this.flussoMassa.equals(other.getFlussoMassa()))) &&
            ((this.idEmissione==null && other.getIdEmissione()==null) || 
             (this.idEmissione!=null &&
              this.idEmissione.equals(other.getIdEmissione()))) &&
            ((this.pericolositaInquinante==null && other.getPericolositaInquinante()==null) || 
             (this.pericolositaInquinante!=null &&
              this.pericolositaInquinante.equals(other.getPericolositaInquinante()))) &&
            ((this.sostanzeInquinanti==null && other.getSostanzeInquinanti()==null) || 
             (this.sostanzeInquinanti!=null &&
              this.sostanzeInquinanti.equals(other.getSostanzeInquinanti())));
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
        if (getFlussoMassa() != null) {
            _hashCode += getFlussoMassa().hashCode();
        }
        if (getIdEmissione() != null) {
            _hashCode += getIdEmissione().hashCode();
        }
        if (getPericolositaInquinante() != null) {
            _hashCode += getPericolositaInquinante().hashCode();
        }
        if (getSostanzeInquinanti() != null) {
            _hashCode += getSostanzeInquinanti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmissioniInquinanteAtm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniInquinanteAtm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flussoMassa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flussoMassa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEmissione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEmissione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pericolositaInquinante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pericolositaInquinante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocPericolositaInquinante"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzeInquinanti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzeInquinanti"));
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
