/**
 * CcostAreaRischioIdrogeo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAreaRischioIdrogeo  implements java.io.Serializable {
    private java.lang.String amministrazioneCompetente;

    private java.lang.Integer finanziamentoRichiesto;

    private java.lang.Integer idCcost;

    private java.lang.Float superficie;

    private com.hyperborea.sira.ws.VocClasseRischioIdrogeo vocClasseRischioIdrogeo;

    private com.hyperborea.sira.ws.VocTipoRischioIdrogeo vocTipoRischioIdrogeo;

    public CcostAreaRischioIdrogeo() {
    }

    public CcostAreaRischioIdrogeo(
           java.lang.String amministrazioneCompetente,
           java.lang.Integer finanziamentoRichiesto,
           java.lang.Integer idCcost,
           java.lang.Float superficie,
           com.hyperborea.sira.ws.VocClasseRischioIdrogeo vocClasseRischioIdrogeo,
           com.hyperborea.sira.ws.VocTipoRischioIdrogeo vocTipoRischioIdrogeo) {
           this.amministrazioneCompetente = amministrazioneCompetente;
           this.finanziamentoRichiesto = finanziamentoRichiesto;
           this.idCcost = idCcost;
           this.superficie = superficie;
           this.vocClasseRischioIdrogeo = vocClasseRischioIdrogeo;
           this.vocTipoRischioIdrogeo = vocTipoRischioIdrogeo;
    }


    /**
     * Gets the amministrazioneCompetente value for this CcostAreaRischioIdrogeo.
     * 
     * @return amministrazioneCompetente
     */
    public java.lang.String getAmministrazioneCompetente() {
        return amministrazioneCompetente;
    }


    /**
     * Sets the amministrazioneCompetente value for this CcostAreaRischioIdrogeo.
     * 
     * @param amministrazioneCompetente
     */
    public void setAmministrazioneCompetente(java.lang.String amministrazioneCompetente) {
        this.amministrazioneCompetente = amministrazioneCompetente;
    }


    /**
     * Gets the finanziamentoRichiesto value for this CcostAreaRischioIdrogeo.
     * 
     * @return finanziamentoRichiesto
     */
    public java.lang.Integer getFinanziamentoRichiesto() {
        return finanziamentoRichiesto;
    }


    /**
     * Sets the finanziamentoRichiesto value for this CcostAreaRischioIdrogeo.
     * 
     * @param finanziamentoRichiesto
     */
    public void setFinanziamentoRichiesto(java.lang.Integer finanziamentoRichiesto) {
        this.finanziamentoRichiesto = finanziamentoRichiesto;
    }


    /**
     * Gets the idCcost value for this CcostAreaRischioIdrogeo.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAreaRischioIdrogeo.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the superficie value for this CcostAreaRischioIdrogeo.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostAreaRischioIdrogeo.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the vocClasseRischioIdrogeo value for this CcostAreaRischioIdrogeo.
     * 
     * @return vocClasseRischioIdrogeo
     */
    public com.hyperborea.sira.ws.VocClasseRischioIdrogeo getVocClasseRischioIdrogeo() {
        return vocClasseRischioIdrogeo;
    }


    /**
     * Sets the vocClasseRischioIdrogeo value for this CcostAreaRischioIdrogeo.
     * 
     * @param vocClasseRischioIdrogeo
     */
    public void setVocClasseRischioIdrogeo(com.hyperborea.sira.ws.VocClasseRischioIdrogeo vocClasseRischioIdrogeo) {
        this.vocClasseRischioIdrogeo = vocClasseRischioIdrogeo;
    }


    /**
     * Gets the vocTipoRischioIdrogeo value for this CcostAreaRischioIdrogeo.
     * 
     * @return vocTipoRischioIdrogeo
     */
    public com.hyperborea.sira.ws.VocTipoRischioIdrogeo getVocTipoRischioIdrogeo() {
        return vocTipoRischioIdrogeo;
    }


    /**
     * Sets the vocTipoRischioIdrogeo value for this CcostAreaRischioIdrogeo.
     * 
     * @param vocTipoRischioIdrogeo
     */
    public void setVocTipoRischioIdrogeo(com.hyperborea.sira.ws.VocTipoRischioIdrogeo vocTipoRischioIdrogeo) {
        this.vocTipoRischioIdrogeo = vocTipoRischioIdrogeo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAreaRischioIdrogeo)) return false;
        CcostAreaRischioIdrogeo other = (CcostAreaRischioIdrogeo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.amministrazioneCompetente==null && other.getAmministrazioneCompetente()==null) || 
             (this.amministrazioneCompetente!=null &&
              this.amministrazioneCompetente.equals(other.getAmministrazioneCompetente()))) &&
            ((this.finanziamentoRichiesto==null && other.getFinanziamentoRichiesto()==null) || 
             (this.finanziamentoRichiesto!=null &&
              this.finanziamentoRichiesto.equals(other.getFinanziamentoRichiesto()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.vocClasseRischioIdrogeo==null && other.getVocClasseRischioIdrogeo()==null) || 
             (this.vocClasseRischioIdrogeo!=null &&
              this.vocClasseRischioIdrogeo.equals(other.getVocClasseRischioIdrogeo()))) &&
            ((this.vocTipoRischioIdrogeo==null && other.getVocTipoRischioIdrogeo()==null) || 
             (this.vocTipoRischioIdrogeo!=null &&
              this.vocTipoRischioIdrogeo.equals(other.getVocTipoRischioIdrogeo())));
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
        if (getAmministrazioneCompetente() != null) {
            _hashCode += getAmministrazioneCompetente().hashCode();
        }
        if (getFinanziamentoRichiesto() != null) {
            _hashCode += getFinanziamentoRichiesto().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getVocClasseRischioIdrogeo() != null) {
            _hashCode += getVocClasseRischioIdrogeo().hashCode();
        }
        if (getVocTipoRischioIdrogeo() != null) {
            _hashCode += getVocTipoRischioIdrogeo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAreaRischioIdrogeo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaRischioIdrogeo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amministrazioneCompetente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amministrazioneCompetente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finanziamentoRichiesto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finanziamentoRichiesto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClasseRischioIdrogeo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClasseRischioIdrogeo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClasseRischioIdrogeo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoRischioIdrogeo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoRischioIdrogeo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoRischioIdrogeo"));
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
