/**
 * EmissioniInquinantiAe.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class EmissioniInquinantiAe  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idEmissione;

    private java.lang.String inquinanteDisperso;

    private java.lang.String sistemiContenimento;

    private com.hyperborea.sira.ws.ScTipoMatrice vocMatriceCoinv;

    public EmissioniInquinantiAe() {
    }

    public EmissioniInquinantiAe(
           java.lang.Integer idEmissione,
           java.lang.String inquinanteDisperso,
           java.lang.String sistemiContenimento,
           com.hyperborea.sira.ws.ScTipoMatrice vocMatriceCoinv) {
        this.idEmissione = idEmissione;
        this.inquinanteDisperso = inquinanteDisperso;
        this.sistemiContenimento = sistemiContenimento;
        this.vocMatriceCoinv = vocMatriceCoinv;
    }


    /**
     * Gets the idEmissione value for this EmissioniInquinantiAe.
     * 
     * @return idEmissione
     */
    public java.lang.Integer getIdEmissione() {
        return idEmissione;
    }


    /**
     * Sets the idEmissione value for this EmissioniInquinantiAe.
     * 
     * @param idEmissione
     */
    public void setIdEmissione(java.lang.Integer idEmissione) {
        this.idEmissione = idEmissione;
    }


    /**
     * Gets the inquinanteDisperso value for this EmissioniInquinantiAe.
     * 
     * @return inquinanteDisperso
     */
    public java.lang.String getInquinanteDisperso() {
        return inquinanteDisperso;
    }


    /**
     * Sets the inquinanteDisperso value for this EmissioniInquinantiAe.
     * 
     * @param inquinanteDisperso
     */
    public void setInquinanteDisperso(java.lang.String inquinanteDisperso) {
        this.inquinanteDisperso = inquinanteDisperso;
    }


    /**
     * Gets the sistemiContenimento value for this EmissioniInquinantiAe.
     * 
     * @return sistemiContenimento
     */
    public java.lang.String getSistemiContenimento() {
        return sistemiContenimento;
    }


    /**
     * Sets the sistemiContenimento value for this EmissioniInquinantiAe.
     * 
     * @param sistemiContenimento
     */
    public void setSistemiContenimento(java.lang.String sistemiContenimento) {
        this.sistemiContenimento = sistemiContenimento;
    }


    /**
     * Gets the vocMatriceCoinv value for this EmissioniInquinantiAe.
     * 
     * @return vocMatriceCoinv
     */
    public com.hyperborea.sira.ws.ScTipoMatrice getVocMatriceCoinv() {
        return vocMatriceCoinv;
    }


    /**
     * Sets the vocMatriceCoinv value for this EmissioniInquinantiAe.
     * 
     * @param vocMatriceCoinv
     */
    public void setVocMatriceCoinv(com.hyperborea.sira.ws.ScTipoMatrice vocMatriceCoinv) {
        this.vocMatriceCoinv = vocMatriceCoinv;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmissioniInquinantiAe)) return false;
        EmissioniInquinantiAe other = (EmissioniInquinantiAe) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idEmissione==null && other.getIdEmissione()==null) || 
             (this.idEmissione!=null &&
              this.idEmissione.equals(other.getIdEmissione()))) &&
            ((this.inquinanteDisperso==null && other.getInquinanteDisperso()==null) || 
             (this.inquinanteDisperso!=null &&
              this.inquinanteDisperso.equals(other.getInquinanteDisperso()))) &&
            ((this.sistemiContenimento==null && other.getSistemiContenimento()==null) || 
             (this.sistemiContenimento!=null &&
              this.sistemiContenimento.equals(other.getSistemiContenimento()))) &&
            ((this.vocMatriceCoinv==null && other.getVocMatriceCoinv()==null) || 
             (this.vocMatriceCoinv!=null &&
              this.vocMatriceCoinv.equals(other.getVocMatriceCoinv())));
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
        if (getIdEmissione() != null) {
            _hashCode += getIdEmissione().hashCode();
        }
        if (getInquinanteDisperso() != null) {
            _hashCode += getInquinanteDisperso().hashCode();
        }
        if (getSistemiContenimento() != null) {
            _hashCode += getSistemiContenimento().hashCode();
        }
        if (getVocMatriceCoinv() != null) {
            _hashCode += getVocMatriceCoinv().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmissioniInquinantiAe.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniInquinantiAe"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEmissione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEmissione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inquinanteDisperso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inquinanteDisperso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemiContenimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemiContenimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocMatriceCoinv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocMatriceCoinv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scTipoMatrice"));
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
