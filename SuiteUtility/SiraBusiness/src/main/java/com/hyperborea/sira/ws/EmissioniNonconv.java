/**
 * EmissioniNonconv.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class EmissioniNonconv  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.String descrizione;

    private java.lang.Integer idEmissioneNonconv;

    private com.hyperborea.sira.ws.InquinantiEmNonconv[] inquinantiEmNonconvs;

    private java.lang.String note;

    private java.lang.String tipologia;

    public EmissioniNonconv() {
    }

    public EmissioniNonconv(
           java.lang.Integer anno,
           java.lang.String descrizione,
           java.lang.Integer idEmissioneNonconv,
           com.hyperborea.sira.ws.InquinantiEmNonconv[] inquinantiEmNonconvs,
           java.lang.String note,
           java.lang.String tipologia) {
        this.anno = anno;
        this.descrizione = descrizione;
        this.idEmissioneNonconv = idEmissioneNonconv;
        this.inquinantiEmNonconvs = inquinantiEmNonconvs;
        this.note = note;
        this.tipologia = tipologia;
    }


    /**
     * Gets the anno value for this EmissioniNonconv.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this EmissioniNonconv.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the descrizione value for this EmissioniNonconv.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this EmissioniNonconv.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idEmissioneNonconv value for this EmissioniNonconv.
     * 
     * @return idEmissioneNonconv
     */
    public java.lang.Integer getIdEmissioneNonconv() {
        return idEmissioneNonconv;
    }


    /**
     * Sets the idEmissioneNonconv value for this EmissioniNonconv.
     * 
     * @param idEmissioneNonconv
     */
    public void setIdEmissioneNonconv(java.lang.Integer idEmissioneNonconv) {
        this.idEmissioneNonconv = idEmissioneNonconv;
    }


    /**
     * Gets the inquinantiEmNonconvs value for this EmissioniNonconv.
     * 
     * @return inquinantiEmNonconvs
     */
    public com.hyperborea.sira.ws.InquinantiEmNonconv[] getInquinantiEmNonconvs() {
        return inquinantiEmNonconvs;
    }


    /**
     * Sets the inquinantiEmNonconvs value for this EmissioniNonconv.
     * 
     * @param inquinantiEmNonconvs
     */
    public void setInquinantiEmNonconvs(com.hyperborea.sira.ws.InquinantiEmNonconv[] inquinantiEmNonconvs) {
        this.inquinantiEmNonconvs = inquinantiEmNonconvs;
    }

    public com.hyperborea.sira.ws.InquinantiEmNonconv getInquinantiEmNonconvs(int i) {
        return this.inquinantiEmNonconvs[i];
    }

    public void setInquinantiEmNonconvs(int i, com.hyperborea.sira.ws.InquinantiEmNonconv _value) {
        this.inquinantiEmNonconvs[i] = _value;
    }


    /**
     * Gets the note value for this EmissioniNonconv.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this EmissioniNonconv.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the tipologia value for this EmissioniNonconv.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this EmissioniNonconv.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EmissioniNonconv)) return false;
        EmissioniNonconv other = (EmissioniNonconv) obj;
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
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idEmissioneNonconv==null && other.getIdEmissioneNonconv()==null) || 
             (this.idEmissioneNonconv!=null &&
              this.idEmissioneNonconv.equals(other.getIdEmissioneNonconv()))) &&
            ((this.inquinantiEmNonconvs==null && other.getInquinantiEmNonconvs()==null) || 
             (this.inquinantiEmNonconvs!=null &&
              java.util.Arrays.equals(this.inquinantiEmNonconvs, other.getInquinantiEmNonconvs()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia())));
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdEmissioneNonconv() != null) {
            _hashCode += getIdEmissioneNonconv().hashCode();
        }
        if (getInquinantiEmNonconvs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInquinantiEmNonconvs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInquinantiEmNonconvs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EmissioniNonconv.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniNonconv"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEmissioneNonconv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEmissioneNonconv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inquinantiEmNonconvs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inquinantiEmNonconvs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmNonconv"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
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
