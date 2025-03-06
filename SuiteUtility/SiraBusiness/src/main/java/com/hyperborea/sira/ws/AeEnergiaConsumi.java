/**
 * AeEnergiaConsumi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AeEnergiaConsumi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.Integer idConsumoEn;

    private java.lang.Integer mese;

    private java.lang.String note;

    private java.lang.Float quantita;

    private com.hyperborea.sira.ws.VocFonteEnergia vocFonteEnergia;

    public AeEnergiaConsumi() {
    }

    public AeEnergiaConsumi(
           java.lang.Integer anno,
           java.lang.Integer idConsumoEn,
           java.lang.Integer mese,
           java.lang.String note,
           java.lang.Float quantita,
           com.hyperborea.sira.ws.VocFonteEnergia vocFonteEnergia) {
        this.anno = anno;
        this.idConsumoEn = idConsumoEn;
        this.mese = mese;
        this.note = note;
        this.quantita = quantita;
        this.vocFonteEnergia = vocFonteEnergia;
    }


    /**
     * Gets the anno value for this AeEnergiaConsumi.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this AeEnergiaConsumi.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the idConsumoEn value for this AeEnergiaConsumi.
     * 
     * @return idConsumoEn
     */
    public java.lang.Integer getIdConsumoEn() {
        return idConsumoEn;
    }


    /**
     * Sets the idConsumoEn value for this AeEnergiaConsumi.
     * 
     * @param idConsumoEn
     */
    public void setIdConsumoEn(java.lang.Integer idConsumoEn) {
        this.idConsumoEn = idConsumoEn;
    }


    /**
     * Gets the mese value for this AeEnergiaConsumi.
     * 
     * @return mese
     */
    public java.lang.Integer getMese() {
        return mese;
    }


    /**
     * Sets the mese value for this AeEnergiaConsumi.
     * 
     * @param mese
     */
    public void setMese(java.lang.Integer mese) {
        this.mese = mese;
    }


    /**
     * Gets the note value for this AeEnergiaConsumi.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this AeEnergiaConsumi.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the quantita value for this AeEnergiaConsumi.
     * 
     * @return quantita
     */
    public java.lang.Float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this AeEnergiaConsumi.
     * 
     * @param quantita
     */
    public void setQuantita(java.lang.Float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the vocFonteEnergia value for this AeEnergiaConsumi.
     * 
     * @return vocFonteEnergia
     */
    public com.hyperborea.sira.ws.VocFonteEnergia getVocFonteEnergia() {
        return vocFonteEnergia;
    }


    /**
     * Sets the vocFonteEnergia value for this AeEnergiaConsumi.
     * 
     * @param vocFonteEnergia
     */
    public void setVocFonteEnergia(com.hyperborea.sira.ws.VocFonteEnergia vocFonteEnergia) {
        this.vocFonteEnergia = vocFonteEnergia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AeEnergiaConsumi)) return false;
        AeEnergiaConsumi other = (AeEnergiaConsumi) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.idConsumoEn==null && other.getIdConsumoEn()==null) || 
             (this.idConsumoEn!=null &&
              this.idConsumoEn.equals(other.getIdConsumoEn()))) &&
            ((this.mese==null && other.getMese()==null) || 
             (this.mese!=null &&
              this.mese.equals(other.getMese()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.quantita==null && other.getQuantita()==null) || 
             (this.quantita!=null &&
              this.quantita.equals(other.getQuantita()))) &&
            ((this.vocFonteEnergia==null && other.getVocFonteEnergia()==null) || 
             (this.vocFonteEnergia!=null &&
              this.vocFonteEnergia.equals(other.getVocFonteEnergia())));
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
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getIdConsumoEn() != null) {
            _hashCode += getIdConsumoEn().hashCode();
        }
        if (getMese() != null) {
            _hashCode += getMese().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getQuantita() != null) {
            _hashCode += getQuantita().hashCode();
        }
        if (getVocFonteEnergia() != null) {
            _hashCode += getVocFonteEnergia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AeEnergiaConsumi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "aeEnergiaConsumi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idConsumoEn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idConsumoEn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mese");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mese"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocFonteEnergia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocFonteEnergia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFonteEnergia"));
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
