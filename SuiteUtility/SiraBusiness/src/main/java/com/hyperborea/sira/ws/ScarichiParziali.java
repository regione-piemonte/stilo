/**
 * ScarichiParziali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ScarichiParziali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.InquinantiEmAcqua[] inquinantiEmAcquas;

    private org.apache.axis.types.UnsignedShort modalitaScarico;

    private java.lang.Float ph;

    private java.lang.Float phAnnoRif;

    private java.lang.String superficieProv;

    private java.lang.Float superficieRelativa;

    private java.lang.Float superficieRelativaAnnoRif;

    private java.lang.Float temperatura;

    private java.lang.Float temperaturaAnnoRif;

    private com.hyperborea.sira.ws.VocTipoScaricoParziale tipoScarico;

    private java.lang.Float volPercAnnoRif;

    private java.lang.Float volumePercentuale;

    private java.lang.Integer wsCcostImpTrattacqueRef;

    private java.lang.Integer wsCcostScarichiidriciRef;

    private int idScaricoParziale;  // attribute

    public ScarichiParziali() {
    }

    public ScarichiParziali(
           int idScaricoParziale,
           java.lang.Integer anno,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.InquinantiEmAcqua[] inquinantiEmAcquas,
           org.apache.axis.types.UnsignedShort modalitaScarico,
           java.lang.Float ph,
           java.lang.Float phAnnoRif,
           java.lang.String superficieProv,
           java.lang.Float superficieRelativa,
           java.lang.Float superficieRelativaAnnoRif,
           java.lang.Float temperatura,
           java.lang.Float temperaturaAnnoRif,
           com.hyperborea.sira.ws.VocTipoScaricoParziale tipoScarico,
           java.lang.Float volPercAnnoRif,
           java.lang.Float volumePercentuale,
           java.lang.Integer wsCcostImpTrattacqueRef,
           java.lang.Integer wsCcostScarichiidriciRef) {
        this.idScaricoParziale = idScaricoParziale;
        this.anno = anno;
        this.descrizione = descrizione;
        this.inquinantiEmAcquas = inquinantiEmAcquas;
        this.modalitaScarico = modalitaScarico;
        this.ph = ph;
        this.phAnnoRif = phAnnoRif;
        this.superficieProv = superficieProv;
        this.superficieRelativa = superficieRelativa;
        this.superficieRelativaAnnoRif = superficieRelativaAnnoRif;
        this.temperatura = temperatura;
        this.temperaturaAnnoRif = temperaturaAnnoRif;
        this.tipoScarico = tipoScarico;
        this.volPercAnnoRif = volPercAnnoRif;
        this.volumePercentuale = volumePercentuale;
        this.wsCcostImpTrattacqueRef = wsCcostImpTrattacqueRef;
        this.wsCcostScarichiidriciRef = wsCcostScarichiidriciRef;
    }


    /**
     * Gets the anno value for this ScarichiParziali.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this ScarichiParziali.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the descrizione value for this ScarichiParziali.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ScarichiParziali.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the inquinantiEmAcquas value for this ScarichiParziali.
     * 
     * @return inquinantiEmAcquas
     */
    public com.hyperborea.sira.ws.InquinantiEmAcqua[] getInquinantiEmAcquas() {
        return inquinantiEmAcquas;
    }


    /**
     * Sets the inquinantiEmAcquas value for this ScarichiParziali.
     * 
     * @param inquinantiEmAcquas
     */
    public void setInquinantiEmAcquas(com.hyperborea.sira.ws.InquinantiEmAcqua[] inquinantiEmAcquas) {
        this.inquinantiEmAcquas = inquinantiEmAcquas;
    }

    public com.hyperborea.sira.ws.InquinantiEmAcqua getInquinantiEmAcquas(int i) {
        return this.inquinantiEmAcquas[i];
    }

    public void setInquinantiEmAcquas(int i, com.hyperborea.sira.ws.InquinantiEmAcqua _value) {
        this.inquinantiEmAcquas[i] = _value;
    }


    /**
     * Gets the modalitaScarico value for this ScarichiParziali.
     * 
     * @return modalitaScarico
     */
    public org.apache.axis.types.UnsignedShort getModalitaScarico() {
        return modalitaScarico;
    }


    /**
     * Sets the modalitaScarico value for this ScarichiParziali.
     * 
     * @param modalitaScarico
     */
    public void setModalitaScarico(org.apache.axis.types.UnsignedShort modalitaScarico) {
        this.modalitaScarico = modalitaScarico;
    }


    /**
     * Gets the ph value for this ScarichiParziali.
     * 
     * @return ph
     */
    public java.lang.Float getPh() {
        return ph;
    }


    /**
     * Sets the ph value for this ScarichiParziali.
     * 
     * @param ph
     */
    public void setPh(java.lang.Float ph) {
        this.ph = ph;
    }


    /**
     * Gets the phAnnoRif value for this ScarichiParziali.
     * 
     * @return phAnnoRif
     */
    public java.lang.Float getPhAnnoRif() {
        return phAnnoRif;
    }


    /**
     * Sets the phAnnoRif value for this ScarichiParziali.
     * 
     * @param phAnnoRif
     */
    public void setPhAnnoRif(java.lang.Float phAnnoRif) {
        this.phAnnoRif = phAnnoRif;
    }


    /**
     * Gets the superficieProv value for this ScarichiParziali.
     * 
     * @return superficieProv
     */
    public java.lang.String getSuperficieProv() {
        return superficieProv;
    }


    /**
     * Sets the superficieProv value for this ScarichiParziali.
     * 
     * @param superficieProv
     */
    public void setSuperficieProv(java.lang.String superficieProv) {
        this.superficieProv = superficieProv;
    }


    /**
     * Gets the superficieRelativa value for this ScarichiParziali.
     * 
     * @return superficieRelativa
     */
    public java.lang.Float getSuperficieRelativa() {
        return superficieRelativa;
    }


    /**
     * Sets the superficieRelativa value for this ScarichiParziali.
     * 
     * @param superficieRelativa
     */
    public void setSuperficieRelativa(java.lang.Float superficieRelativa) {
        this.superficieRelativa = superficieRelativa;
    }


    /**
     * Gets the superficieRelativaAnnoRif value for this ScarichiParziali.
     * 
     * @return superficieRelativaAnnoRif
     */
    public java.lang.Float getSuperficieRelativaAnnoRif() {
        return superficieRelativaAnnoRif;
    }


    /**
     * Sets the superficieRelativaAnnoRif value for this ScarichiParziali.
     * 
     * @param superficieRelativaAnnoRif
     */
    public void setSuperficieRelativaAnnoRif(java.lang.Float superficieRelativaAnnoRif) {
        this.superficieRelativaAnnoRif = superficieRelativaAnnoRif;
    }


    /**
     * Gets the temperatura value for this ScarichiParziali.
     * 
     * @return temperatura
     */
    public java.lang.Float getTemperatura() {
        return temperatura;
    }


    /**
     * Sets the temperatura value for this ScarichiParziali.
     * 
     * @param temperatura
     */
    public void setTemperatura(java.lang.Float temperatura) {
        this.temperatura = temperatura;
    }


    /**
     * Gets the temperaturaAnnoRif value for this ScarichiParziali.
     * 
     * @return temperaturaAnnoRif
     */
    public java.lang.Float getTemperaturaAnnoRif() {
        return temperaturaAnnoRif;
    }


    /**
     * Sets the temperaturaAnnoRif value for this ScarichiParziali.
     * 
     * @param temperaturaAnnoRif
     */
    public void setTemperaturaAnnoRif(java.lang.Float temperaturaAnnoRif) {
        this.temperaturaAnnoRif = temperaturaAnnoRif;
    }


    /**
     * Gets the tipoScarico value for this ScarichiParziali.
     * 
     * @return tipoScarico
     */
    public com.hyperborea.sira.ws.VocTipoScaricoParziale getTipoScarico() {
        return tipoScarico;
    }


    /**
     * Sets the tipoScarico value for this ScarichiParziali.
     * 
     * @param tipoScarico
     */
    public void setTipoScarico(com.hyperborea.sira.ws.VocTipoScaricoParziale tipoScarico) {
        this.tipoScarico = tipoScarico;
    }


    /**
     * Gets the volPercAnnoRif value for this ScarichiParziali.
     * 
     * @return volPercAnnoRif
     */
    public java.lang.Float getVolPercAnnoRif() {
        return volPercAnnoRif;
    }


    /**
     * Sets the volPercAnnoRif value for this ScarichiParziali.
     * 
     * @param volPercAnnoRif
     */
    public void setVolPercAnnoRif(java.lang.Float volPercAnnoRif) {
        this.volPercAnnoRif = volPercAnnoRif;
    }


    /**
     * Gets the volumePercentuale value for this ScarichiParziali.
     * 
     * @return volumePercentuale
     */
    public java.lang.Float getVolumePercentuale() {
        return volumePercentuale;
    }


    /**
     * Sets the volumePercentuale value for this ScarichiParziali.
     * 
     * @param volumePercentuale
     */
    public void setVolumePercentuale(java.lang.Float volumePercentuale) {
        this.volumePercentuale = volumePercentuale;
    }


    /**
     * Gets the wsCcostImpTrattacqueRef value for this ScarichiParziali.
     * 
     * @return wsCcostImpTrattacqueRef
     */
    public java.lang.Integer getWsCcostImpTrattacqueRef() {
        return wsCcostImpTrattacqueRef;
    }


    /**
     * Sets the wsCcostImpTrattacqueRef value for this ScarichiParziali.
     * 
     * @param wsCcostImpTrattacqueRef
     */
    public void setWsCcostImpTrattacqueRef(java.lang.Integer wsCcostImpTrattacqueRef) {
        this.wsCcostImpTrattacqueRef = wsCcostImpTrattacqueRef;
    }


    /**
     * Gets the wsCcostScarichiidriciRef value for this ScarichiParziali.
     * 
     * @return wsCcostScarichiidriciRef
     */
    public java.lang.Integer getWsCcostScarichiidriciRef() {
        return wsCcostScarichiidriciRef;
    }


    /**
     * Sets the wsCcostScarichiidriciRef value for this ScarichiParziali.
     * 
     * @param wsCcostScarichiidriciRef
     */
    public void setWsCcostScarichiidriciRef(java.lang.Integer wsCcostScarichiidriciRef) {
        this.wsCcostScarichiidriciRef = wsCcostScarichiidriciRef;
    }


    /**
     * Gets the idScaricoParziale value for this ScarichiParziali.
     * 
     * @return idScaricoParziale
     */
    public int getIdScaricoParziale() {
        return idScaricoParziale;
    }


    /**
     * Sets the idScaricoParziale value for this ScarichiParziali.
     * 
     * @param idScaricoParziale
     */
    public void setIdScaricoParziale(int idScaricoParziale) {
        this.idScaricoParziale = idScaricoParziale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScarichiParziali)) return false;
        ScarichiParziali other = (ScarichiParziali) obj;
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
            ((this.inquinantiEmAcquas==null && other.getInquinantiEmAcquas()==null) || 
             (this.inquinantiEmAcquas!=null &&
              java.util.Arrays.equals(this.inquinantiEmAcquas, other.getInquinantiEmAcquas()))) &&
            ((this.modalitaScarico==null && other.getModalitaScarico()==null) || 
             (this.modalitaScarico!=null &&
              this.modalitaScarico.equals(other.getModalitaScarico()))) &&
            ((this.ph==null && other.getPh()==null) || 
             (this.ph!=null &&
              this.ph.equals(other.getPh()))) &&
            ((this.phAnnoRif==null && other.getPhAnnoRif()==null) || 
             (this.phAnnoRif!=null &&
              this.phAnnoRif.equals(other.getPhAnnoRif()))) &&
            ((this.superficieProv==null && other.getSuperficieProv()==null) || 
             (this.superficieProv!=null &&
              this.superficieProv.equals(other.getSuperficieProv()))) &&
            ((this.superficieRelativa==null && other.getSuperficieRelativa()==null) || 
             (this.superficieRelativa!=null &&
              this.superficieRelativa.equals(other.getSuperficieRelativa()))) &&
            ((this.superficieRelativaAnnoRif==null && other.getSuperficieRelativaAnnoRif()==null) || 
             (this.superficieRelativaAnnoRif!=null &&
              this.superficieRelativaAnnoRif.equals(other.getSuperficieRelativaAnnoRif()))) &&
            ((this.temperatura==null && other.getTemperatura()==null) || 
             (this.temperatura!=null &&
              this.temperatura.equals(other.getTemperatura()))) &&
            ((this.temperaturaAnnoRif==null && other.getTemperaturaAnnoRif()==null) || 
             (this.temperaturaAnnoRif!=null &&
              this.temperaturaAnnoRif.equals(other.getTemperaturaAnnoRif()))) &&
            ((this.tipoScarico==null && other.getTipoScarico()==null) || 
             (this.tipoScarico!=null &&
              this.tipoScarico.equals(other.getTipoScarico()))) &&
            ((this.volPercAnnoRif==null && other.getVolPercAnnoRif()==null) || 
             (this.volPercAnnoRif!=null &&
              this.volPercAnnoRif.equals(other.getVolPercAnnoRif()))) &&
            ((this.volumePercentuale==null && other.getVolumePercentuale()==null) || 
             (this.volumePercentuale!=null &&
              this.volumePercentuale.equals(other.getVolumePercentuale()))) &&
            ((this.wsCcostImpTrattacqueRef==null && other.getWsCcostImpTrattacqueRef()==null) || 
             (this.wsCcostImpTrattacqueRef!=null &&
              this.wsCcostImpTrattacqueRef.equals(other.getWsCcostImpTrattacqueRef()))) &&
            ((this.wsCcostScarichiidriciRef==null && other.getWsCcostScarichiidriciRef()==null) || 
             (this.wsCcostScarichiidriciRef!=null &&
              this.wsCcostScarichiidriciRef.equals(other.getWsCcostScarichiidriciRef()))) &&
            this.idScaricoParziale == other.getIdScaricoParziale();
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
        if (getInquinantiEmAcquas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInquinantiEmAcquas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInquinantiEmAcquas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getModalitaScarico() != null) {
            _hashCode += getModalitaScarico().hashCode();
        }
        if (getPh() != null) {
            _hashCode += getPh().hashCode();
        }
        if (getPhAnnoRif() != null) {
            _hashCode += getPhAnnoRif().hashCode();
        }
        if (getSuperficieProv() != null) {
            _hashCode += getSuperficieProv().hashCode();
        }
        if (getSuperficieRelativa() != null) {
            _hashCode += getSuperficieRelativa().hashCode();
        }
        if (getSuperficieRelativaAnnoRif() != null) {
            _hashCode += getSuperficieRelativaAnnoRif().hashCode();
        }
        if (getTemperatura() != null) {
            _hashCode += getTemperatura().hashCode();
        }
        if (getTemperaturaAnnoRif() != null) {
            _hashCode += getTemperaturaAnnoRif().hashCode();
        }
        if (getTipoScarico() != null) {
            _hashCode += getTipoScarico().hashCode();
        }
        if (getVolPercAnnoRif() != null) {
            _hashCode += getVolPercAnnoRif().hashCode();
        }
        if (getVolumePercentuale() != null) {
            _hashCode += getVolumePercentuale().hashCode();
        }
        if (getWsCcostImpTrattacqueRef() != null) {
            _hashCode += getWsCcostImpTrattacqueRef().hashCode();
        }
        if (getWsCcostScarichiidriciRef() != null) {
            _hashCode += getWsCcostScarichiidriciRef().hashCode();
        }
        _hashCode += getIdScaricoParziale();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScarichiParziali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scarichiParziali"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("idScaricoParziale");
        attrField.setXmlName(new javax.xml.namespace.QName("", "idScaricoParziale"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
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
        elemField.setFieldName("inquinantiEmAcquas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inquinantiEmAcquas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmAcqua"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modalitaScarico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modalitaScarico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ph");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ph"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("phAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "phAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieProv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieProv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieRelativa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieRelativa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieRelativaAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieRelativaAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temperatura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temperatura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temperaturaAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temperaturaAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoScarico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoScarico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoScaricoParziale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volPercAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volPercAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumePercentuale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumePercentuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostImpTrattacqueRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostImpTrattacqueRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCcostScarichiidriciRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCcostScarichiidriciRef"));
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
