/**
 * PianiStralcio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PianiStralcio  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String agglomerato;

    private java.lang.String attuazione;

    private java.lang.String codice;

    private java.lang.Float collettatiTotali;

    private java.lang.Integer definito;

    private java.lang.Float estensioneRetiFognarie;

    private java.lang.Integer idPianoStralcio;

    private java.lang.String note;

    private java.lang.String tipoSchema;

    private java.lang.String vecchioCodice;

    public PianiStralcio() {
    }

    public PianiStralcio(
           java.lang.String agglomerato,
           java.lang.String attuazione,
           java.lang.String codice,
           java.lang.Float collettatiTotali,
           java.lang.Integer definito,
           java.lang.Float estensioneRetiFognarie,
           java.lang.Integer idPianoStralcio,
           java.lang.String note,
           java.lang.String tipoSchema,
           java.lang.String vecchioCodice) {
        this.agglomerato = agglomerato;
        this.attuazione = attuazione;
        this.codice = codice;
        this.collettatiTotali = collettatiTotali;
        this.definito = definito;
        this.estensioneRetiFognarie = estensioneRetiFognarie;
        this.idPianoStralcio = idPianoStralcio;
        this.note = note;
        this.tipoSchema = tipoSchema;
        this.vecchioCodice = vecchioCodice;
    }


    /**
     * Gets the agglomerato value for this PianiStralcio.
     * 
     * @return agglomerato
     */
    public java.lang.String getAgglomerato() {
        return agglomerato;
    }


    /**
     * Sets the agglomerato value for this PianiStralcio.
     * 
     * @param agglomerato
     */
    public void setAgglomerato(java.lang.String agglomerato) {
        this.agglomerato = agglomerato;
    }


    /**
     * Gets the attuazione value for this PianiStralcio.
     * 
     * @return attuazione
     */
    public java.lang.String getAttuazione() {
        return attuazione;
    }


    /**
     * Sets the attuazione value for this PianiStralcio.
     * 
     * @param attuazione
     */
    public void setAttuazione(java.lang.String attuazione) {
        this.attuazione = attuazione;
    }


    /**
     * Gets the codice value for this PianiStralcio.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this PianiStralcio.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the collettatiTotali value for this PianiStralcio.
     * 
     * @return collettatiTotali
     */
    public java.lang.Float getCollettatiTotali() {
        return collettatiTotali;
    }


    /**
     * Sets the collettatiTotali value for this PianiStralcio.
     * 
     * @param collettatiTotali
     */
    public void setCollettatiTotali(java.lang.Float collettatiTotali) {
        this.collettatiTotali = collettatiTotali;
    }


    /**
     * Gets the definito value for this PianiStralcio.
     * 
     * @return definito
     */
    public java.lang.Integer getDefinito() {
        return definito;
    }


    /**
     * Sets the definito value for this PianiStralcio.
     * 
     * @param definito
     */
    public void setDefinito(java.lang.Integer definito) {
        this.definito = definito;
    }


    /**
     * Gets the estensioneRetiFognarie value for this PianiStralcio.
     * 
     * @return estensioneRetiFognarie
     */
    public java.lang.Float getEstensioneRetiFognarie() {
        return estensioneRetiFognarie;
    }


    /**
     * Sets the estensioneRetiFognarie value for this PianiStralcio.
     * 
     * @param estensioneRetiFognarie
     */
    public void setEstensioneRetiFognarie(java.lang.Float estensioneRetiFognarie) {
        this.estensioneRetiFognarie = estensioneRetiFognarie;
    }


    /**
     * Gets the idPianoStralcio value for this PianiStralcio.
     * 
     * @return idPianoStralcio
     */
    public java.lang.Integer getIdPianoStralcio() {
        return idPianoStralcio;
    }


    /**
     * Sets the idPianoStralcio value for this PianiStralcio.
     * 
     * @param idPianoStralcio
     */
    public void setIdPianoStralcio(java.lang.Integer idPianoStralcio) {
        this.idPianoStralcio = idPianoStralcio;
    }


    /**
     * Gets the note value for this PianiStralcio.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this PianiStralcio.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the tipoSchema value for this PianiStralcio.
     * 
     * @return tipoSchema
     */
    public java.lang.String getTipoSchema() {
        return tipoSchema;
    }


    /**
     * Sets the tipoSchema value for this PianiStralcio.
     * 
     * @param tipoSchema
     */
    public void setTipoSchema(java.lang.String tipoSchema) {
        this.tipoSchema = tipoSchema;
    }


    /**
     * Gets the vecchioCodice value for this PianiStralcio.
     * 
     * @return vecchioCodice
     */
    public java.lang.String getVecchioCodice() {
        return vecchioCodice;
    }


    /**
     * Sets the vecchioCodice value for this PianiStralcio.
     * 
     * @param vecchioCodice
     */
    public void setVecchioCodice(java.lang.String vecchioCodice) {
        this.vecchioCodice = vecchioCodice;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PianiStralcio)) return false;
        PianiStralcio other = (PianiStralcio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.agglomerato==null && other.getAgglomerato()==null) || 
             (this.agglomerato!=null &&
              this.agglomerato.equals(other.getAgglomerato()))) &&
            ((this.attuazione==null && other.getAttuazione()==null) || 
             (this.attuazione!=null &&
              this.attuazione.equals(other.getAttuazione()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.collettatiTotali==null && other.getCollettatiTotali()==null) || 
             (this.collettatiTotali!=null &&
              this.collettatiTotali.equals(other.getCollettatiTotali()))) &&
            ((this.definito==null && other.getDefinito()==null) || 
             (this.definito!=null &&
              this.definito.equals(other.getDefinito()))) &&
            ((this.estensioneRetiFognarie==null && other.getEstensioneRetiFognarie()==null) || 
             (this.estensioneRetiFognarie!=null &&
              this.estensioneRetiFognarie.equals(other.getEstensioneRetiFognarie()))) &&
            ((this.idPianoStralcio==null && other.getIdPianoStralcio()==null) || 
             (this.idPianoStralcio!=null &&
              this.idPianoStralcio.equals(other.getIdPianoStralcio()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.tipoSchema==null && other.getTipoSchema()==null) || 
             (this.tipoSchema!=null &&
              this.tipoSchema.equals(other.getTipoSchema()))) &&
            ((this.vecchioCodice==null && other.getVecchioCodice()==null) || 
             (this.vecchioCodice!=null &&
              this.vecchioCodice.equals(other.getVecchioCodice())));
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
        if (getAgglomerato() != null) {
            _hashCode += getAgglomerato().hashCode();
        }
        if (getAttuazione() != null) {
            _hashCode += getAttuazione().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getCollettatiTotali() != null) {
            _hashCode += getCollettatiTotali().hashCode();
        }
        if (getDefinito() != null) {
            _hashCode += getDefinito().hashCode();
        }
        if (getEstensioneRetiFognarie() != null) {
            _hashCode += getEstensioneRetiFognarie().hashCode();
        }
        if (getIdPianoStralcio() != null) {
            _hashCode += getIdPianoStralcio().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getTipoSchema() != null) {
            _hashCode += getTipoSchema().hashCode();
        }
        if (getVecchioCodice() != null) {
            _hashCode += getVecchioCodice().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PianiStralcio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pianiStralcio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agglomerato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agglomerato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attuazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attuazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collettatiTotali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "collettatiTotali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("definito");
        elemField.setXmlName(new javax.xml.namespace.QName("", "definito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estensioneRetiFognarie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estensioneRetiFognarie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPianoStralcio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPianoStralcio"));
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
        elemField.setFieldName("tipoSchema");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSchema"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vecchioCodice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vecchioCodice"));
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
