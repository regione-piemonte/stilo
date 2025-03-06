/**
 * SpL3A.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SpL3A  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.String descrizioneUnitaMisura;

    private java.lang.Integer idSpL3A;

    private java.lang.Float mediaGiornaliera;

    private java.lang.Float mediaMensile;

    private com.hyperborea.sira.ws.SostanzeTab3A sostanzeTab3A;

    private java.lang.String umProduzione;

    public SpL3A() {
    }

    public SpL3A(
           java.lang.String descrizione,
           java.lang.String descrizioneUnitaMisura,
           java.lang.Integer idSpL3A,
           java.lang.Float mediaGiornaliera,
           java.lang.Float mediaMensile,
           com.hyperborea.sira.ws.SostanzeTab3A sostanzeTab3A,
           java.lang.String umProduzione) {
        this.descrizione = descrizione;
        this.descrizioneUnitaMisura = descrizioneUnitaMisura;
        this.idSpL3A = idSpL3A;
        this.mediaGiornaliera = mediaGiornaliera;
        this.mediaMensile = mediaMensile;
        this.sostanzeTab3A = sostanzeTab3A;
        this.umProduzione = umProduzione;
    }


    /**
     * Gets the descrizione value for this SpL3A.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this SpL3A.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the descrizioneUnitaMisura value for this SpL3A.
     * 
     * @return descrizioneUnitaMisura
     */
    public java.lang.String getDescrizioneUnitaMisura() {
        return descrizioneUnitaMisura;
    }


    /**
     * Sets the descrizioneUnitaMisura value for this SpL3A.
     * 
     * @param descrizioneUnitaMisura
     */
    public void setDescrizioneUnitaMisura(java.lang.String descrizioneUnitaMisura) {
        this.descrizioneUnitaMisura = descrizioneUnitaMisura;
    }


    /**
     * Gets the idSpL3A value for this SpL3A.
     * 
     * @return idSpL3A
     */
    public java.lang.Integer getIdSpL3A() {
        return idSpL3A;
    }


    /**
     * Sets the idSpL3A value for this SpL3A.
     * 
     * @param idSpL3A
     */
    public void setIdSpL3A(java.lang.Integer idSpL3A) {
        this.idSpL3A = idSpL3A;
    }


    /**
     * Gets the mediaGiornaliera value for this SpL3A.
     * 
     * @return mediaGiornaliera
     */
    public java.lang.Float getMediaGiornaliera() {
        return mediaGiornaliera;
    }


    /**
     * Sets the mediaGiornaliera value for this SpL3A.
     * 
     * @param mediaGiornaliera
     */
    public void setMediaGiornaliera(java.lang.Float mediaGiornaliera) {
        this.mediaGiornaliera = mediaGiornaliera;
    }


    /**
     * Gets the mediaMensile value for this SpL3A.
     * 
     * @return mediaMensile
     */
    public java.lang.Float getMediaMensile() {
        return mediaMensile;
    }


    /**
     * Sets the mediaMensile value for this SpL3A.
     * 
     * @param mediaMensile
     */
    public void setMediaMensile(java.lang.Float mediaMensile) {
        this.mediaMensile = mediaMensile;
    }


    /**
     * Gets the sostanzeTab3A value for this SpL3A.
     * 
     * @return sostanzeTab3A
     */
    public com.hyperborea.sira.ws.SostanzeTab3A getSostanzeTab3A() {
        return sostanzeTab3A;
    }


    /**
     * Sets the sostanzeTab3A value for this SpL3A.
     * 
     * @param sostanzeTab3A
     */
    public void setSostanzeTab3A(com.hyperborea.sira.ws.SostanzeTab3A sostanzeTab3A) {
        this.sostanzeTab3A = sostanzeTab3A;
    }


    /**
     * Gets the umProduzione value for this SpL3A.
     * 
     * @return umProduzione
     */
    public java.lang.String getUmProduzione() {
        return umProduzione;
    }


    /**
     * Sets the umProduzione value for this SpL3A.
     * 
     * @param umProduzione
     */
    public void setUmProduzione(java.lang.String umProduzione) {
        this.umProduzione = umProduzione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SpL3A)) return false;
        SpL3A other = (SpL3A) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.descrizioneUnitaMisura==null && other.getDescrizioneUnitaMisura()==null) || 
             (this.descrizioneUnitaMisura!=null &&
              this.descrizioneUnitaMisura.equals(other.getDescrizioneUnitaMisura()))) &&
            ((this.idSpL3A==null && other.getIdSpL3A()==null) || 
             (this.idSpL3A!=null &&
              this.idSpL3A.equals(other.getIdSpL3A()))) &&
            ((this.mediaGiornaliera==null && other.getMediaGiornaliera()==null) || 
             (this.mediaGiornaliera!=null &&
              this.mediaGiornaliera.equals(other.getMediaGiornaliera()))) &&
            ((this.mediaMensile==null && other.getMediaMensile()==null) || 
             (this.mediaMensile!=null &&
              this.mediaMensile.equals(other.getMediaMensile()))) &&
            ((this.sostanzeTab3A==null && other.getSostanzeTab3A()==null) || 
             (this.sostanzeTab3A!=null &&
              this.sostanzeTab3A.equals(other.getSostanzeTab3A()))) &&
            ((this.umProduzione==null && other.getUmProduzione()==null) || 
             (this.umProduzione!=null &&
              this.umProduzione.equals(other.getUmProduzione())));
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDescrizioneUnitaMisura() != null) {
            _hashCode += getDescrizioneUnitaMisura().hashCode();
        }
        if (getIdSpL3A() != null) {
            _hashCode += getIdSpL3A().hashCode();
        }
        if (getMediaGiornaliera() != null) {
            _hashCode += getMediaGiornaliera().hashCode();
        }
        if (getMediaMensile() != null) {
            _hashCode += getMediaMensile().hashCode();
        }
        if (getSostanzeTab3A() != null) {
            _hashCode += getSostanzeTab3A().hashCode();
        }
        if (getUmProduzione() != null) {
            _hashCode += getUmProduzione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SpL3A.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "spL3A"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneUnitaMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneUnitaMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSpL3A");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSpL3a"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediaGiornaliera");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mediaGiornaliera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mediaMensile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mediaMensile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzeTab3A");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzeTab3a"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanzeTab3A"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("umProduzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "umProduzione"));
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
