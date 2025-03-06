/**
 * CavaImpiantiMezzi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CavaImpiantiMezzi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.String descrizione;

    private java.lang.Integer idCavaImpiantiMezzi;

    private java.lang.String note;

    private java.lang.Float portata;

    private java.lang.Float potenzaMotori;

    private com.hyperborea.sira.ws.VocTipoImpiantoMezzo vocTipoImpiantoMezzo;

    public CavaImpiantiMezzi() {
    }

    public CavaImpiantiMezzi(
           java.lang.Integer anno,
           java.lang.String descrizione,
           java.lang.Integer idCavaImpiantiMezzi,
           java.lang.String note,
           java.lang.Float portata,
           java.lang.Float potenzaMotori,
           com.hyperborea.sira.ws.VocTipoImpiantoMezzo vocTipoImpiantoMezzo) {
        this.anno = anno;
        this.descrizione = descrizione;
        this.idCavaImpiantiMezzi = idCavaImpiantiMezzi;
        this.note = note;
        this.portata = portata;
        this.potenzaMotori = potenzaMotori;
        this.vocTipoImpiantoMezzo = vocTipoImpiantoMezzo;
    }


    /**
     * Gets the anno value for this CavaImpiantiMezzi.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this CavaImpiantiMezzi.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the descrizione value for this CavaImpiantiMezzi.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this CavaImpiantiMezzi.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idCavaImpiantiMezzi value for this CavaImpiantiMezzi.
     * 
     * @return idCavaImpiantiMezzi
     */
    public java.lang.Integer getIdCavaImpiantiMezzi() {
        return idCavaImpiantiMezzi;
    }


    /**
     * Sets the idCavaImpiantiMezzi value for this CavaImpiantiMezzi.
     * 
     * @param idCavaImpiantiMezzi
     */
    public void setIdCavaImpiantiMezzi(java.lang.Integer idCavaImpiantiMezzi) {
        this.idCavaImpiantiMezzi = idCavaImpiantiMezzi;
    }


    /**
     * Gets the note value for this CavaImpiantiMezzi.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CavaImpiantiMezzi.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the portata value for this CavaImpiantiMezzi.
     * 
     * @return portata
     */
    public java.lang.Float getPortata() {
        return portata;
    }


    /**
     * Sets the portata value for this CavaImpiantiMezzi.
     * 
     * @param portata
     */
    public void setPortata(java.lang.Float portata) {
        this.portata = portata;
    }


    /**
     * Gets the potenzaMotori value for this CavaImpiantiMezzi.
     * 
     * @return potenzaMotori
     */
    public java.lang.Float getPotenzaMotori() {
        return potenzaMotori;
    }


    /**
     * Sets the potenzaMotori value for this CavaImpiantiMezzi.
     * 
     * @param potenzaMotori
     */
    public void setPotenzaMotori(java.lang.Float potenzaMotori) {
        this.potenzaMotori = potenzaMotori;
    }


    /**
     * Gets the vocTipoImpiantoMezzo value for this CavaImpiantiMezzi.
     * 
     * @return vocTipoImpiantoMezzo
     */
    public com.hyperborea.sira.ws.VocTipoImpiantoMezzo getVocTipoImpiantoMezzo() {
        return vocTipoImpiantoMezzo;
    }


    /**
     * Sets the vocTipoImpiantoMezzo value for this CavaImpiantiMezzi.
     * 
     * @param vocTipoImpiantoMezzo
     */
    public void setVocTipoImpiantoMezzo(com.hyperborea.sira.ws.VocTipoImpiantoMezzo vocTipoImpiantoMezzo) {
        this.vocTipoImpiantoMezzo = vocTipoImpiantoMezzo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CavaImpiantiMezzi)) return false;
        CavaImpiantiMezzi other = (CavaImpiantiMezzi) obj;
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
            ((this.idCavaImpiantiMezzi==null && other.getIdCavaImpiantiMezzi()==null) || 
             (this.idCavaImpiantiMezzi!=null &&
              this.idCavaImpiantiMezzi.equals(other.getIdCavaImpiantiMezzi()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.portata==null && other.getPortata()==null) || 
             (this.portata!=null &&
              this.portata.equals(other.getPortata()))) &&
            ((this.potenzaMotori==null && other.getPotenzaMotori()==null) || 
             (this.potenzaMotori!=null &&
              this.potenzaMotori.equals(other.getPotenzaMotori()))) &&
            ((this.vocTipoImpiantoMezzo==null && other.getVocTipoImpiantoMezzo()==null) || 
             (this.vocTipoImpiantoMezzo!=null &&
              this.vocTipoImpiantoMezzo.equals(other.getVocTipoImpiantoMezzo())));
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
        if (getIdCavaImpiantiMezzi() != null) {
            _hashCode += getIdCavaImpiantiMezzi().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getPortata() != null) {
            _hashCode += getPortata().hashCode();
        }
        if (getPotenzaMotori() != null) {
            _hashCode += getPotenzaMotori().hashCode();
        }
        if (getVocTipoImpiantoMezzo() != null) {
            _hashCode += getVocTipoImpiantoMezzo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CavaImpiantiMezzi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cavaImpiantiMezzi"));
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
        elemField.setFieldName("idCavaImpiantiMezzi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCavaImpiantiMezzi"));
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
        elemField.setFieldName("portata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaMotori");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaMotori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoImpiantoMezzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoImpiantoMezzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoImpiantoMezzo"));
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
