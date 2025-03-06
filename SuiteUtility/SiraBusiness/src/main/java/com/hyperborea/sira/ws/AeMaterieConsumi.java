/**
 * AeMaterieConsumi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AeMaterieConsumi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.Integer idConsumoMat;

    private java.lang.Integer mese;

    private java.lang.String note;

    private java.lang.Float quantita;

    private com.hyperborea.sira.ws.VocTipoMaterialeAemc vocTipoMaterialeAemc;

    public AeMaterieConsumi() {
    }

    public AeMaterieConsumi(
           java.lang.Integer anno,
           java.lang.Integer idConsumoMat,
           java.lang.Integer mese,
           java.lang.String note,
           java.lang.Float quantita,
           com.hyperborea.sira.ws.VocTipoMaterialeAemc vocTipoMaterialeAemc) {
        this.anno = anno;
        this.idConsumoMat = idConsumoMat;
        this.mese = mese;
        this.note = note;
        this.quantita = quantita;
        this.vocTipoMaterialeAemc = vocTipoMaterialeAemc;
    }


    /**
     * Gets the anno value for this AeMaterieConsumi.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this AeMaterieConsumi.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the idConsumoMat value for this AeMaterieConsumi.
     * 
     * @return idConsumoMat
     */
    public java.lang.Integer getIdConsumoMat() {
        return idConsumoMat;
    }


    /**
     * Sets the idConsumoMat value for this AeMaterieConsumi.
     * 
     * @param idConsumoMat
     */
    public void setIdConsumoMat(java.lang.Integer idConsumoMat) {
        this.idConsumoMat = idConsumoMat;
    }


    /**
     * Gets the mese value for this AeMaterieConsumi.
     * 
     * @return mese
     */
    public java.lang.Integer getMese() {
        return mese;
    }


    /**
     * Sets the mese value for this AeMaterieConsumi.
     * 
     * @param mese
     */
    public void setMese(java.lang.Integer mese) {
        this.mese = mese;
    }


    /**
     * Gets the note value for this AeMaterieConsumi.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this AeMaterieConsumi.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the quantita value for this AeMaterieConsumi.
     * 
     * @return quantita
     */
    public java.lang.Float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this AeMaterieConsumi.
     * 
     * @param quantita
     */
    public void setQuantita(java.lang.Float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the vocTipoMaterialeAemc value for this AeMaterieConsumi.
     * 
     * @return vocTipoMaterialeAemc
     */
    public com.hyperborea.sira.ws.VocTipoMaterialeAemc getVocTipoMaterialeAemc() {
        return vocTipoMaterialeAemc;
    }


    /**
     * Sets the vocTipoMaterialeAemc value for this AeMaterieConsumi.
     * 
     * @param vocTipoMaterialeAemc
     */
    public void setVocTipoMaterialeAemc(com.hyperborea.sira.ws.VocTipoMaterialeAemc vocTipoMaterialeAemc) {
        this.vocTipoMaterialeAemc = vocTipoMaterialeAemc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AeMaterieConsumi)) return false;
        AeMaterieConsumi other = (AeMaterieConsumi) obj;
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
            ((this.idConsumoMat==null && other.getIdConsumoMat()==null) || 
             (this.idConsumoMat!=null &&
              this.idConsumoMat.equals(other.getIdConsumoMat()))) &&
            ((this.mese==null && other.getMese()==null) || 
             (this.mese!=null &&
              this.mese.equals(other.getMese()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.quantita==null && other.getQuantita()==null) || 
             (this.quantita!=null &&
              this.quantita.equals(other.getQuantita()))) &&
            ((this.vocTipoMaterialeAemc==null && other.getVocTipoMaterialeAemc()==null) || 
             (this.vocTipoMaterialeAemc!=null &&
              this.vocTipoMaterialeAemc.equals(other.getVocTipoMaterialeAemc())));
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
        if (getIdConsumoMat() != null) {
            _hashCode += getIdConsumoMat().hashCode();
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
        if (getVocTipoMaterialeAemc() != null) {
            _hashCode += getVocTipoMaterialeAemc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AeMaterieConsumi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "aeMaterieConsumi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idConsumoMat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idConsumoMat"));
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
        elemField.setFieldName("vocTipoMaterialeAemc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoMaterialeAemc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoMaterialeAemc"));
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
