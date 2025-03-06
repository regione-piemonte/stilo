/**
 * InquinantiEmAcqua.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class InquinantiEmAcqua  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float concentrazione;

    private java.lang.Float concentrazioneAnnoRif;

    private java.lang.Float flussoOrario;

    private java.lang.Float flussoOrarioAnnoRif;

    private com.hyperborea.sira.ws.InquinantiEmAcquaId id;

    private org.apache.axis.types.UnsignedShort modAcquisizConcAnnoRif;

    private org.apache.axis.types.UnsignedShort modAcquisizMassaAnnoRif;

    private org.apache.axis.types.UnsignedShort modAcquisizioneMassa;

    private org.apache.axis.types.UnsignedShort modalitaAcquisizConc;

    private java.lang.String sostanzaPericolosa;

    public InquinantiEmAcqua() {
    }

    public InquinantiEmAcqua(
           java.lang.Float concentrazione,
           java.lang.Float concentrazioneAnnoRif,
           java.lang.Float flussoOrario,
           java.lang.Float flussoOrarioAnnoRif,
           com.hyperborea.sira.ws.InquinantiEmAcquaId id,
           org.apache.axis.types.UnsignedShort modAcquisizConcAnnoRif,
           org.apache.axis.types.UnsignedShort modAcquisizMassaAnnoRif,
           org.apache.axis.types.UnsignedShort modAcquisizioneMassa,
           org.apache.axis.types.UnsignedShort modalitaAcquisizConc,
           java.lang.String sostanzaPericolosa) {
        this.concentrazione = concentrazione;
        this.concentrazioneAnnoRif = concentrazioneAnnoRif;
        this.flussoOrario = flussoOrario;
        this.flussoOrarioAnnoRif = flussoOrarioAnnoRif;
        this.id = id;
        this.modAcquisizConcAnnoRif = modAcquisizConcAnnoRif;
        this.modAcquisizMassaAnnoRif = modAcquisizMassaAnnoRif;
        this.modAcquisizioneMassa = modAcquisizioneMassa;
        this.modalitaAcquisizConc = modalitaAcquisizConc;
        this.sostanzaPericolosa = sostanzaPericolosa;
    }


    /**
     * Gets the concentrazione value for this InquinantiEmAcqua.
     * 
     * @return concentrazione
     */
    public java.lang.Float getConcentrazione() {
        return concentrazione;
    }


    /**
     * Sets the concentrazione value for this InquinantiEmAcqua.
     * 
     * @param concentrazione
     */
    public void setConcentrazione(java.lang.Float concentrazione) {
        this.concentrazione = concentrazione;
    }


    /**
     * Gets the concentrazioneAnnoRif value for this InquinantiEmAcqua.
     * 
     * @return concentrazioneAnnoRif
     */
    public java.lang.Float getConcentrazioneAnnoRif() {
        return concentrazioneAnnoRif;
    }


    /**
     * Sets the concentrazioneAnnoRif value for this InquinantiEmAcqua.
     * 
     * @param concentrazioneAnnoRif
     */
    public void setConcentrazioneAnnoRif(java.lang.Float concentrazioneAnnoRif) {
        this.concentrazioneAnnoRif = concentrazioneAnnoRif;
    }


    /**
     * Gets the flussoOrario value for this InquinantiEmAcqua.
     * 
     * @return flussoOrario
     */
    public java.lang.Float getFlussoOrario() {
        return flussoOrario;
    }


    /**
     * Sets the flussoOrario value for this InquinantiEmAcqua.
     * 
     * @param flussoOrario
     */
    public void setFlussoOrario(java.lang.Float flussoOrario) {
        this.flussoOrario = flussoOrario;
    }


    /**
     * Gets the flussoOrarioAnnoRif value for this InquinantiEmAcqua.
     * 
     * @return flussoOrarioAnnoRif
     */
    public java.lang.Float getFlussoOrarioAnnoRif() {
        return flussoOrarioAnnoRif;
    }


    /**
     * Sets the flussoOrarioAnnoRif value for this InquinantiEmAcqua.
     * 
     * @param flussoOrarioAnnoRif
     */
    public void setFlussoOrarioAnnoRif(java.lang.Float flussoOrarioAnnoRif) {
        this.flussoOrarioAnnoRif = flussoOrarioAnnoRif;
    }


    /**
     * Gets the id value for this InquinantiEmAcqua.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.InquinantiEmAcquaId getId() {
        return id;
    }


    /**
     * Sets the id value for this InquinantiEmAcqua.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.InquinantiEmAcquaId id) {
        this.id = id;
    }


    /**
     * Gets the modAcquisizConcAnnoRif value for this InquinantiEmAcqua.
     * 
     * @return modAcquisizConcAnnoRif
     */
    public org.apache.axis.types.UnsignedShort getModAcquisizConcAnnoRif() {
        return modAcquisizConcAnnoRif;
    }


    /**
     * Sets the modAcquisizConcAnnoRif value for this InquinantiEmAcqua.
     * 
     * @param modAcquisizConcAnnoRif
     */
    public void setModAcquisizConcAnnoRif(org.apache.axis.types.UnsignedShort modAcquisizConcAnnoRif) {
        this.modAcquisizConcAnnoRif = modAcquisizConcAnnoRif;
    }


    /**
     * Gets the modAcquisizMassaAnnoRif value for this InquinantiEmAcqua.
     * 
     * @return modAcquisizMassaAnnoRif
     */
    public org.apache.axis.types.UnsignedShort getModAcquisizMassaAnnoRif() {
        return modAcquisizMassaAnnoRif;
    }


    /**
     * Sets the modAcquisizMassaAnnoRif value for this InquinantiEmAcqua.
     * 
     * @param modAcquisizMassaAnnoRif
     */
    public void setModAcquisizMassaAnnoRif(org.apache.axis.types.UnsignedShort modAcquisizMassaAnnoRif) {
        this.modAcquisizMassaAnnoRif = modAcquisizMassaAnnoRif;
    }


    /**
     * Gets the modAcquisizioneMassa value for this InquinantiEmAcqua.
     * 
     * @return modAcquisizioneMassa
     */
    public org.apache.axis.types.UnsignedShort getModAcquisizioneMassa() {
        return modAcquisizioneMassa;
    }


    /**
     * Sets the modAcquisizioneMassa value for this InquinantiEmAcqua.
     * 
     * @param modAcquisizioneMassa
     */
    public void setModAcquisizioneMassa(org.apache.axis.types.UnsignedShort modAcquisizioneMassa) {
        this.modAcquisizioneMassa = modAcquisizioneMassa;
    }


    /**
     * Gets the modalitaAcquisizConc value for this InquinantiEmAcqua.
     * 
     * @return modalitaAcquisizConc
     */
    public org.apache.axis.types.UnsignedShort getModalitaAcquisizConc() {
        return modalitaAcquisizConc;
    }


    /**
     * Sets the modalitaAcquisizConc value for this InquinantiEmAcqua.
     * 
     * @param modalitaAcquisizConc
     */
    public void setModalitaAcquisizConc(org.apache.axis.types.UnsignedShort modalitaAcquisizConc) {
        this.modalitaAcquisizConc = modalitaAcquisizConc;
    }


    /**
     * Gets the sostanzaPericolosa value for this InquinantiEmAcqua.
     * 
     * @return sostanzaPericolosa
     */
    public java.lang.String getSostanzaPericolosa() {
        return sostanzaPericolosa;
    }


    /**
     * Sets the sostanzaPericolosa value for this InquinantiEmAcqua.
     * 
     * @param sostanzaPericolosa
     */
    public void setSostanzaPericolosa(java.lang.String sostanzaPericolosa) {
        this.sostanzaPericolosa = sostanzaPericolosa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InquinantiEmAcqua)) return false;
        InquinantiEmAcqua other = (InquinantiEmAcqua) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.concentrazione==null && other.getConcentrazione()==null) || 
             (this.concentrazione!=null &&
              this.concentrazione.equals(other.getConcentrazione()))) &&
            ((this.concentrazioneAnnoRif==null && other.getConcentrazioneAnnoRif()==null) || 
             (this.concentrazioneAnnoRif!=null &&
              this.concentrazioneAnnoRif.equals(other.getConcentrazioneAnnoRif()))) &&
            ((this.flussoOrario==null && other.getFlussoOrario()==null) || 
             (this.flussoOrario!=null &&
              this.flussoOrario.equals(other.getFlussoOrario()))) &&
            ((this.flussoOrarioAnnoRif==null && other.getFlussoOrarioAnnoRif()==null) || 
             (this.flussoOrarioAnnoRif!=null &&
              this.flussoOrarioAnnoRif.equals(other.getFlussoOrarioAnnoRif()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.modAcquisizConcAnnoRif==null && other.getModAcquisizConcAnnoRif()==null) || 
             (this.modAcquisizConcAnnoRif!=null &&
              this.modAcquisizConcAnnoRif.equals(other.getModAcquisizConcAnnoRif()))) &&
            ((this.modAcquisizMassaAnnoRif==null && other.getModAcquisizMassaAnnoRif()==null) || 
             (this.modAcquisizMassaAnnoRif!=null &&
              this.modAcquisizMassaAnnoRif.equals(other.getModAcquisizMassaAnnoRif()))) &&
            ((this.modAcquisizioneMassa==null && other.getModAcquisizioneMassa()==null) || 
             (this.modAcquisizioneMassa!=null &&
              this.modAcquisizioneMassa.equals(other.getModAcquisizioneMassa()))) &&
            ((this.modalitaAcquisizConc==null && other.getModalitaAcquisizConc()==null) || 
             (this.modalitaAcquisizConc!=null &&
              this.modalitaAcquisizConc.equals(other.getModalitaAcquisizConc()))) &&
            ((this.sostanzaPericolosa==null && other.getSostanzaPericolosa()==null) || 
             (this.sostanzaPericolosa!=null &&
              this.sostanzaPericolosa.equals(other.getSostanzaPericolosa())));
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
        if (getConcentrazione() != null) {
            _hashCode += getConcentrazione().hashCode();
        }
        if (getConcentrazioneAnnoRif() != null) {
            _hashCode += getConcentrazioneAnnoRif().hashCode();
        }
        if (getFlussoOrario() != null) {
            _hashCode += getFlussoOrario().hashCode();
        }
        if (getFlussoOrarioAnnoRif() != null) {
            _hashCode += getFlussoOrarioAnnoRif().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getModAcquisizConcAnnoRif() != null) {
            _hashCode += getModAcquisizConcAnnoRif().hashCode();
        }
        if (getModAcquisizMassaAnnoRif() != null) {
            _hashCode += getModAcquisizMassaAnnoRif().hashCode();
        }
        if (getModAcquisizioneMassa() != null) {
            _hashCode += getModAcquisizioneMassa().hashCode();
        }
        if (getModalitaAcquisizConc() != null) {
            _hashCode += getModalitaAcquisizConc().hashCode();
        }
        if (getSostanzaPericolosa() != null) {
            _hashCode += getSostanzaPericolosa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InquinantiEmAcqua.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmAcqua"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concentrazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concentrazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concentrazioneAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concentrazioneAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flussoOrario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flussoOrario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flussoOrarioAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flussoOrarioAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmAcquaId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modAcquisizConcAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modAcquisizConcAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modAcquisizMassaAnnoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modAcquisizMassaAnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modAcquisizioneMassa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modAcquisizioneMassa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modalitaAcquisizConc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modalitaAcquisizConc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sostanzaPericolosa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sostanzaPericolosa"));
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
