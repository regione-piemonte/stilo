/**
 * CcostImpGeotermico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpGeotermico  implements java.io.Serializable {
    private java.lang.Float copGue;

    private java.lang.Float eer;

    private java.lang.Integer idCcost;

    private java.lang.Integer numeroPozzi;

    private java.lang.Float potenzaFrigorifera;

    private java.lang.Float producibilitaFrigorifera;

    private com.hyperborea.sira.ws.VocAlimentPompaCalore vocAlimentPompaCalore;

    private com.hyperborea.sira.ws.VocDestinazioneUsoTerm vocDestinazioneUsoTerm;

    private com.hyperborea.sira.ws.VocSorgScambioTermico vocSorgScambioTermico;

    private com.hyperborea.sira.ws.VocTipologiaScaricoTerm vocTipologiaScaricoTerm;

    private java.lang.Integer nPompeCalore;

    private java.lang.String nPrRegSondeTermiche;

    public CcostImpGeotermico() {
    }

    public CcostImpGeotermico(
           java.lang.Float copGue,
           java.lang.Float eer,
           java.lang.Integer idCcost,
           java.lang.Integer numeroPozzi,
           java.lang.Float potenzaFrigorifera,
           java.lang.Float producibilitaFrigorifera,
           com.hyperborea.sira.ws.VocAlimentPompaCalore vocAlimentPompaCalore,
           com.hyperborea.sira.ws.VocDestinazioneUsoTerm vocDestinazioneUsoTerm,
           com.hyperborea.sira.ws.VocSorgScambioTermico vocSorgScambioTermico,
           com.hyperborea.sira.ws.VocTipologiaScaricoTerm vocTipologiaScaricoTerm,
           java.lang.Integer nPompeCalore,
           java.lang.String nPrRegSondeTermiche) {
           this.copGue = copGue;
           this.eer = eer;
           this.idCcost = idCcost;
           this.numeroPozzi = numeroPozzi;
           this.potenzaFrigorifera = potenzaFrigorifera;
           this.producibilitaFrigorifera = producibilitaFrigorifera;
           this.vocAlimentPompaCalore = vocAlimentPompaCalore;
           this.vocDestinazioneUsoTerm = vocDestinazioneUsoTerm;
           this.vocSorgScambioTermico = vocSorgScambioTermico;
           this.vocTipologiaScaricoTerm = vocTipologiaScaricoTerm;
           this.nPompeCalore = nPompeCalore;
           this.nPrRegSondeTermiche = nPrRegSondeTermiche;
    }


    /**
     * Gets the copGue value for this CcostImpGeotermico.
     * 
     * @return copGue
     */
    public java.lang.Float getCopGue() {
        return copGue;
    }


    /**
     * Sets the copGue value for this CcostImpGeotermico.
     * 
     * @param copGue
     */
    public void setCopGue(java.lang.Float copGue) {
        this.copGue = copGue;
    }


    /**
     * Gets the eer value for this CcostImpGeotermico.
     * 
     * @return eer
     */
    public java.lang.Float getEer() {
        return eer;
    }


    /**
     * Sets the eer value for this CcostImpGeotermico.
     * 
     * @param eer
     */
    public void setEer(java.lang.Float eer) {
        this.eer = eer;
    }


    /**
     * Gets the idCcost value for this CcostImpGeotermico.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpGeotermico.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the numeroPozzi value for this CcostImpGeotermico.
     * 
     * @return numeroPozzi
     */
    public java.lang.Integer getNumeroPozzi() {
        return numeroPozzi;
    }


    /**
     * Sets the numeroPozzi value for this CcostImpGeotermico.
     * 
     * @param numeroPozzi
     */
    public void setNumeroPozzi(java.lang.Integer numeroPozzi) {
        this.numeroPozzi = numeroPozzi;
    }


    /**
     * Gets the potenzaFrigorifera value for this CcostImpGeotermico.
     * 
     * @return potenzaFrigorifera
     */
    public java.lang.Float getPotenzaFrigorifera() {
        return potenzaFrigorifera;
    }


    /**
     * Sets the potenzaFrigorifera value for this CcostImpGeotermico.
     * 
     * @param potenzaFrigorifera
     */
    public void setPotenzaFrigorifera(java.lang.Float potenzaFrigorifera) {
        this.potenzaFrigorifera = potenzaFrigorifera;
    }


    /**
     * Gets the producibilitaFrigorifera value for this CcostImpGeotermico.
     * 
     * @return producibilitaFrigorifera
     */
    public java.lang.Float getProducibilitaFrigorifera() {
        return producibilitaFrigorifera;
    }


    /**
     * Sets the producibilitaFrigorifera value for this CcostImpGeotermico.
     * 
     * @param producibilitaFrigorifera
     */
    public void setProducibilitaFrigorifera(java.lang.Float producibilitaFrigorifera) {
        this.producibilitaFrigorifera = producibilitaFrigorifera;
    }


    /**
     * Gets the vocAlimentPompaCalore value for this CcostImpGeotermico.
     * 
     * @return vocAlimentPompaCalore
     */
    public com.hyperborea.sira.ws.VocAlimentPompaCalore getVocAlimentPompaCalore() {
        return vocAlimentPompaCalore;
    }


    /**
     * Sets the vocAlimentPompaCalore value for this CcostImpGeotermico.
     * 
     * @param vocAlimentPompaCalore
     */
    public void setVocAlimentPompaCalore(com.hyperborea.sira.ws.VocAlimentPompaCalore vocAlimentPompaCalore) {
        this.vocAlimentPompaCalore = vocAlimentPompaCalore;
    }


    /**
     * Gets the vocDestinazioneUsoTerm value for this CcostImpGeotermico.
     * 
     * @return vocDestinazioneUsoTerm
     */
    public com.hyperborea.sira.ws.VocDestinazioneUsoTerm getVocDestinazioneUsoTerm() {
        return vocDestinazioneUsoTerm;
    }


    /**
     * Sets the vocDestinazioneUsoTerm value for this CcostImpGeotermico.
     * 
     * @param vocDestinazioneUsoTerm
     */
    public void setVocDestinazioneUsoTerm(com.hyperborea.sira.ws.VocDestinazioneUsoTerm vocDestinazioneUsoTerm) {
        this.vocDestinazioneUsoTerm = vocDestinazioneUsoTerm;
    }


    /**
     * Gets the vocSorgScambioTermico value for this CcostImpGeotermico.
     * 
     * @return vocSorgScambioTermico
     */
    public com.hyperborea.sira.ws.VocSorgScambioTermico getVocSorgScambioTermico() {
        return vocSorgScambioTermico;
    }


    /**
     * Sets the vocSorgScambioTermico value for this CcostImpGeotermico.
     * 
     * @param vocSorgScambioTermico
     */
    public void setVocSorgScambioTermico(com.hyperborea.sira.ws.VocSorgScambioTermico vocSorgScambioTermico) {
        this.vocSorgScambioTermico = vocSorgScambioTermico;
    }


    /**
     * Gets the vocTipologiaScaricoTerm value for this CcostImpGeotermico.
     * 
     * @return vocTipologiaScaricoTerm
     */
    public com.hyperborea.sira.ws.VocTipologiaScaricoTerm getVocTipologiaScaricoTerm() {
        return vocTipologiaScaricoTerm;
    }


    /**
     * Sets the vocTipologiaScaricoTerm value for this CcostImpGeotermico.
     * 
     * @param vocTipologiaScaricoTerm
     */
    public void setVocTipologiaScaricoTerm(com.hyperborea.sira.ws.VocTipologiaScaricoTerm vocTipologiaScaricoTerm) {
        this.vocTipologiaScaricoTerm = vocTipologiaScaricoTerm;
    }


    /**
     * Gets the nPompeCalore value for this CcostImpGeotermico.
     * 
     * @return nPompeCalore
     */
    public java.lang.Integer getNPompeCalore() {
        return nPompeCalore;
    }


    /**
     * Sets the nPompeCalore value for this CcostImpGeotermico.
     * 
     * @param nPompeCalore
     */
    public void setNPompeCalore(java.lang.Integer nPompeCalore) {
        this.nPompeCalore = nPompeCalore;
    }


    /**
     * Gets the nPrRegSondeTermiche value for this CcostImpGeotermico.
     * 
     * @return nPrRegSondeTermiche
     */
    public java.lang.String getNPrRegSondeTermiche() {
        return nPrRegSondeTermiche;
    }


    /**
     * Sets the nPrRegSondeTermiche value for this CcostImpGeotermico.
     * 
     * @param nPrRegSondeTermiche
     */
    public void setNPrRegSondeTermiche(java.lang.String nPrRegSondeTermiche) {
        this.nPrRegSondeTermiche = nPrRegSondeTermiche;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpGeotermico)) return false;
        CcostImpGeotermico other = (CcostImpGeotermico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.copGue==null && other.getCopGue()==null) || 
             (this.copGue!=null &&
              this.copGue.equals(other.getCopGue()))) &&
            ((this.eer==null && other.getEer()==null) || 
             (this.eer!=null &&
              this.eer.equals(other.getEer()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.numeroPozzi==null && other.getNumeroPozzi()==null) || 
             (this.numeroPozzi!=null &&
              this.numeroPozzi.equals(other.getNumeroPozzi()))) &&
            ((this.potenzaFrigorifera==null && other.getPotenzaFrigorifera()==null) || 
             (this.potenzaFrigorifera!=null &&
              this.potenzaFrigorifera.equals(other.getPotenzaFrigorifera()))) &&
            ((this.producibilitaFrigorifera==null && other.getProducibilitaFrigorifera()==null) || 
             (this.producibilitaFrigorifera!=null &&
              this.producibilitaFrigorifera.equals(other.getProducibilitaFrigorifera()))) &&
            ((this.vocAlimentPompaCalore==null && other.getVocAlimentPompaCalore()==null) || 
             (this.vocAlimentPompaCalore!=null &&
              this.vocAlimentPompaCalore.equals(other.getVocAlimentPompaCalore()))) &&
            ((this.vocDestinazioneUsoTerm==null && other.getVocDestinazioneUsoTerm()==null) || 
             (this.vocDestinazioneUsoTerm!=null &&
              this.vocDestinazioneUsoTerm.equals(other.getVocDestinazioneUsoTerm()))) &&
            ((this.vocSorgScambioTermico==null && other.getVocSorgScambioTermico()==null) || 
             (this.vocSorgScambioTermico!=null &&
              this.vocSorgScambioTermico.equals(other.getVocSorgScambioTermico()))) &&
            ((this.vocTipologiaScaricoTerm==null && other.getVocTipologiaScaricoTerm()==null) || 
             (this.vocTipologiaScaricoTerm!=null &&
              this.vocTipologiaScaricoTerm.equals(other.getVocTipologiaScaricoTerm()))) &&
            ((this.nPompeCalore==null && other.getNPompeCalore()==null) || 
             (this.nPompeCalore!=null &&
              this.nPompeCalore.equals(other.getNPompeCalore()))) &&
            ((this.nPrRegSondeTermiche==null && other.getNPrRegSondeTermiche()==null) || 
             (this.nPrRegSondeTermiche!=null &&
              this.nPrRegSondeTermiche.equals(other.getNPrRegSondeTermiche())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCopGue() != null) {
            _hashCode += getCopGue().hashCode();
        }
        if (getEer() != null) {
            _hashCode += getEer().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNumeroPozzi() != null) {
            _hashCode += getNumeroPozzi().hashCode();
        }
        if (getPotenzaFrigorifera() != null) {
            _hashCode += getPotenzaFrigorifera().hashCode();
        }
        if (getProducibilitaFrigorifera() != null) {
            _hashCode += getProducibilitaFrigorifera().hashCode();
        }
        if (getVocAlimentPompaCalore() != null) {
            _hashCode += getVocAlimentPompaCalore().hashCode();
        }
        if (getVocDestinazioneUsoTerm() != null) {
            _hashCode += getVocDestinazioneUsoTerm().hashCode();
        }
        if (getVocSorgScambioTermico() != null) {
            _hashCode += getVocSorgScambioTermico().hashCode();
        }
        if (getVocTipologiaScaricoTerm() != null) {
            _hashCode += getVocTipologiaScaricoTerm().hashCode();
        }
        if (getNPompeCalore() != null) {
            _hashCode += getNPompeCalore().hashCode();
        }
        if (getNPrRegSondeTermiche() != null) {
            _hashCode += getNPrRegSondeTermiche().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpGeotermico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpGeotermico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("copGue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "copGue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroPozzi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroPozzi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaFrigorifera");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaFrigorifera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producibilitaFrigorifera");
        elemField.setXmlName(new javax.xml.namespace.QName("", "producibilitaFrigorifera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocAlimentPompaCalore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocAlimentPompaCalore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocAlimentPompaCalore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocDestinazioneUsoTerm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocDestinazioneUsoTerm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDestinazioneUsoTerm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocSorgScambioTermico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocSorgScambioTermico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocSorgScambioTermico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaScaricoTerm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaScaricoTerm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaScaricoTerm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NPompeCalore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nPompeCalore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NPrRegSondeTermiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nPrRegSondeTermiche"));
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
