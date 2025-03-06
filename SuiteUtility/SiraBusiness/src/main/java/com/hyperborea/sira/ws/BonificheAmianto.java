/**
 * BonificheAmianto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class BonificheAmianto  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float costoIntervento;

    private java.util.Calendar dataFineIntervento;

    private java.util.Calendar dataInizioIntervento;

    private java.lang.Integer id;

    private java.lang.String note;

    private java.lang.Float quantita;

    private com.hyperborea.sira.ws.VocAmStatoFinanziamento vocAmStatoFinanziamento;

    private com.hyperborea.sira.ws.VocAmTipologiaBonifica vocAmTipologiaBonifica;

    private com.hyperborea.sira.ws.VocAmTipologiaFondi vocAmTipologiaFondi;

    private com.hyperborea.sira.ws.VocAmTipologiaIntervento vocAmTipologiaIntervento;

    private java.lang.Integer wsRefCccostId;

    public BonificheAmianto() {
    }

    public BonificheAmianto(
           java.lang.Float costoIntervento,
           java.util.Calendar dataFineIntervento,
           java.util.Calendar dataInizioIntervento,
           java.lang.Integer id,
           java.lang.String note,
           java.lang.Float quantita,
           com.hyperborea.sira.ws.VocAmStatoFinanziamento vocAmStatoFinanziamento,
           com.hyperborea.sira.ws.VocAmTipologiaBonifica vocAmTipologiaBonifica,
           com.hyperborea.sira.ws.VocAmTipologiaFondi vocAmTipologiaFondi,
           com.hyperborea.sira.ws.VocAmTipologiaIntervento vocAmTipologiaIntervento,
           java.lang.Integer wsRefCccostId) {
        this.costoIntervento = costoIntervento;
        this.dataFineIntervento = dataFineIntervento;
        this.dataInizioIntervento = dataInizioIntervento;
        this.id = id;
        this.note = note;
        this.quantita = quantita;
        this.vocAmStatoFinanziamento = vocAmStatoFinanziamento;
        this.vocAmTipologiaBonifica = vocAmTipologiaBonifica;
        this.vocAmTipologiaFondi = vocAmTipologiaFondi;
        this.vocAmTipologiaIntervento = vocAmTipologiaIntervento;
        this.wsRefCccostId = wsRefCccostId;
    }


    /**
     * Gets the costoIntervento value for this BonificheAmianto.
     * 
     * @return costoIntervento
     */
    public java.lang.Float getCostoIntervento() {
        return costoIntervento;
    }


    /**
     * Sets the costoIntervento value for this BonificheAmianto.
     * 
     * @param costoIntervento
     */
    public void setCostoIntervento(java.lang.Float costoIntervento) {
        this.costoIntervento = costoIntervento;
    }


    /**
     * Gets the dataFineIntervento value for this BonificheAmianto.
     * 
     * @return dataFineIntervento
     */
    public java.util.Calendar getDataFineIntervento() {
        return dataFineIntervento;
    }


    /**
     * Sets the dataFineIntervento value for this BonificheAmianto.
     * 
     * @param dataFineIntervento
     */
    public void setDataFineIntervento(java.util.Calendar dataFineIntervento) {
        this.dataFineIntervento = dataFineIntervento;
    }


    /**
     * Gets the dataInizioIntervento value for this BonificheAmianto.
     * 
     * @return dataInizioIntervento
     */
    public java.util.Calendar getDataInizioIntervento() {
        return dataInizioIntervento;
    }


    /**
     * Sets the dataInizioIntervento value for this BonificheAmianto.
     * 
     * @param dataInizioIntervento
     */
    public void setDataInizioIntervento(java.util.Calendar dataInizioIntervento) {
        this.dataInizioIntervento = dataInizioIntervento;
    }


    /**
     * Gets the id value for this BonificheAmianto.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this BonificheAmianto.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the note value for this BonificheAmianto.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this BonificheAmianto.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the quantita value for this BonificheAmianto.
     * 
     * @return quantita
     */
    public java.lang.Float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this BonificheAmianto.
     * 
     * @param quantita
     */
    public void setQuantita(java.lang.Float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the vocAmStatoFinanziamento value for this BonificheAmianto.
     * 
     * @return vocAmStatoFinanziamento
     */
    public com.hyperborea.sira.ws.VocAmStatoFinanziamento getVocAmStatoFinanziamento() {
        return vocAmStatoFinanziamento;
    }


    /**
     * Sets the vocAmStatoFinanziamento value for this BonificheAmianto.
     * 
     * @param vocAmStatoFinanziamento
     */
    public void setVocAmStatoFinanziamento(com.hyperborea.sira.ws.VocAmStatoFinanziamento vocAmStatoFinanziamento) {
        this.vocAmStatoFinanziamento = vocAmStatoFinanziamento;
    }


    /**
     * Gets the vocAmTipologiaBonifica value for this BonificheAmianto.
     * 
     * @return vocAmTipologiaBonifica
     */
    public com.hyperborea.sira.ws.VocAmTipologiaBonifica getVocAmTipologiaBonifica() {
        return vocAmTipologiaBonifica;
    }


    /**
     * Sets the vocAmTipologiaBonifica value for this BonificheAmianto.
     * 
     * @param vocAmTipologiaBonifica
     */
    public void setVocAmTipologiaBonifica(com.hyperborea.sira.ws.VocAmTipologiaBonifica vocAmTipologiaBonifica) {
        this.vocAmTipologiaBonifica = vocAmTipologiaBonifica;
    }


    /**
     * Gets the vocAmTipologiaFondi value for this BonificheAmianto.
     * 
     * @return vocAmTipologiaFondi
     */
    public com.hyperborea.sira.ws.VocAmTipologiaFondi getVocAmTipologiaFondi() {
        return vocAmTipologiaFondi;
    }


    /**
     * Sets the vocAmTipologiaFondi value for this BonificheAmianto.
     * 
     * @param vocAmTipologiaFondi
     */
    public void setVocAmTipologiaFondi(com.hyperborea.sira.ws.VocAmTipologiaFondi vocAmTipologiaFondi) {
        this.vocAmTipologiaFondi = vocAmTipologiaFondi;
    }


    /**
     * Gets the vocAmTipologiaIntervento value for this BonificheAmianto.
     * 
     * @return vocAmTipologiaIntervento
     */
    public com.hyperborea.sira.ws.VocAmTipologiaIntervento getVocAmTipologiaIntervento() {
        return vocAmTipologiaIntervento;
    }


    /**
     * Sets the vocAmTipologiaIntervento value for this BonificheAmianto.
     * 
     * @param vocAmTipologiaIntervento
     */
    public void setVocAmTipologiaIntervento(com.hyperborea.sira.ws.VocAmTipologiaIntervento vocAmTipologiaIntervento) {
        this.vocAmTipologiaIntervento = vocAmTipologiaIntervento;
    }


    /**
     * Gets the wsRefCccostId value for this BonificheAmianto.
     * 
     * @return wsRefCccostId
     */
    public java.lang.Integer getWsRefCccostId() {
        return wsRefCccostId;
    }


    /**
     * Sets the wsRefCccostId value for this BonificheAmianto.
     * 
     * @param wsRefCccostId
     */
    public void setWsRefCccostId(java.lang.Integer wsRefCccostId) {
        this.wsRefCccostId = wsRefCccostId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BonificheAmianto)) return false;
        BonificheAmianto other = (BonificheAmianto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.costoIntervento==null && other.getCostoIntervento()==null) || 
             (this.costoIntervento!=null &&
              this.costoIntervento.equals(other.getCostoIntervento()))) &&
            ((this.dataFineIntervento==null && other.getDataFineIntervento()==null) || 
             (this.dataFineIntervento!=null &&
              this.dataFineIntervento.equals(other.getDataFineIntervento()))) &&
            ((this.dataInizioIntervento==null && other.getDataInizioIntervento()==null) || 
             (this.dataInizioIntervento!=null &&
              this.dataInizioIntervento.equals(other.getDataInizioIntervento()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.quantita==null && other.getQuantita()==null) || 
             (this.quantita!=null &&
              this.quantita.equals(other.getQuantita()))) &&
            ((this.vocAmStatoFinanziamento==null && other.getVocAmStatoFinanziamento()==null) || 
             (this.vocAmStatoFinanziamento!=null &&
              this.vocAmStatoFinanziamento.equals(other.getVocAmStatoFinanziamento()))) &&
            ((this.vocAmTipologiaBonifica==null && other.getVocAmTipologiaBonifica()==null) || 
             (this.vocAmTipologiaBonifica!=null &&
              this.vocAmTipologiaBonifica.equals(other.getVocAmTipologiaBonifica()))) &&
            ((this.vocAmTipologiaFondi==null && other.getVocAmTipologiaFondi()==null) || 
             (this.vocAmTipologiaFondi!=null &&
              this.vocAmTipologiaFondi.equals(other.getVocAmTipologiaFondi()))) &&
            ((this.vocAmTipologiaIntervento==null && other.getVocAmTipologiaIntervento()==null) || 
             (this.vocAmTipologiaIntervento!=null &&
              this.vocAmTipologiaIntervento.equals(other.getVocAmTipologiaIntervento()))) &&
            ((this.wsRefCccostId==null && other.getWsRefCccostId()==null) || 
             (this.wsRefCccostId!=null &&
              this.wsRefCccostId.equals(other.getWsRefCccostId())));
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
        if (getCostoIntervento() != null) {
            _hashCode += getCostoIntervento().hashCode();
        }
        if (getDataFineIntervento() != null) {
            _hashCode += getDataFineIntervento().hashCode();
        }
        if (getDataInizioIntervento() != null) {
            _hashCode += getDataInizioIntervento().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getQuantita() != null) {
            _hashCode += getQuantita().hashCode();
        }
        if (getVocAmStatoFinanziamento() != null) {
            _hashCode += getVocAmStatoFinanziamento().hashCode();
        }
        if (getVocAmTipologiaBonifica() != null) {
            _hashCode += getVocAmTipologiaBonifica().hashCode();
        }
        if (getVocAmTipologiaFondi() != null) {
            _hashCode += getVocAmTipologiaFondi().hashCode();
        }
        if (getVocAmTipologiaIntervento() != null) {
            _hashCode += getVocAmTipologiaIntervento().hashCode();
        }
        if (getWsRefCccostId() != null) {
            _hashCode += getWsRefCccostId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BonificheAmianto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "bonificheAmianto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costoIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costoIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFineIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizioIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
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
        elemField.setFieldName("vocAmStatoFinanziamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmStatoFinanziamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmStatoFinanziamento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmTipologiaBonifica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmTipologiaBonifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaBonifica"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmTipologiaFondi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmTipologiaFondi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaFondi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAmTipologiaIntervento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAmTipologiaIntervento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAmTipologiaIntervento"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsRefCccostId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsRefCccostId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
