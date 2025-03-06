/**
 * Habitat.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Habitat  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codice;

    private java.lang.String denominazione;

    private java.lang.String denominazioneEn;

    private java.lang.Integer idHabitat;

    private java.lang.String prioritario;

    private java.lang.String tipologia;

    public Habitat() {
    }

    public Habitat(
           java.lang.String codice,
           java.lang.String denominazione,
           java.lang.String denominazioneEn,
           java.lang.Integer idHabitat,
           java.lang.String prioritario,
           java.lang.String tipologia) {
        this.codice = codice;
        this.denominazione = denominazione;
        this.denominazioneEn = denominazioneEn;
        this.idHabitat = idHabitat;
        this.prioritario = prioritario;
        this.tipologia = tipologia;
    }


    /**
     * Gets the codice value for this Habitat.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this Habitat.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the denominazione value for this Habitat.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this Habitat.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the denominazioneEn value for this Habitat.
     * 
     * @return denominazioneEn
     */
    public java.lang.String getDenominazioneEn() {
        return denominazioneEn;
    }


    /**
     * Sets the denominazioneEn value for this Habitat.
     * 
     * @param denominazioneEn
     */
    public void setDenominazioneEn(java.lang.String denominazioneEn) {
        this.denominazioneEn = denominazioneEn;
    }


    /**
     * Gets the idHabitat value for this Habitat.
     * 
     * @return idHabitat
     */
    public java.lang.Integer getIdHabitat() {
        return idHabitat;
    }


    /**
     * Sets the idHabitat value for this Habitat.
     * 
     * @param idHabitat
     */
    public void setIdHabitat(java.lang.Integer idHabitat) {
        this.idHabitat = idHabitat;
    }


    /**
     * Gets the prioritario value for this Habitat.
     * 
     * @return prioritario
     */
    public java.lang.String getPrioritario() {
        return prioritario;
    }


    /**
     * Sets the prioritario value for this Habitat.
     * 
     * @param prioritario
     */
    public void setPrioritario(java.lang.String prioritario) {
        this.prioritario = prioritario;
    }


    /**
     * Gets the tipologia value for this Habitat.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this Habitat.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Habitat)) return false;
        Habitat other = (Habitat) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.denominazioneEn==null && other.getDenominazioneEn()==null) || 
             (this.denominazioneEn!=null &&
              this.denominazioneEn.equals(other.getDenominazioneEn()))) &&
            ((this.idHabitat==null && other.getIdHabitat()==null) || 
             (this.idHabitat!=null &&
              this.idHabitat.equals(other.getIdHabitat()))) &&
            ((this.prioritario==null && other.getPrioritario()==null) || 
             (this.prioritario!=null &&
              this.prioritario.equals(other.getPrioritario()))) &&
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getDenominazioneEn() != null) {
            _hashCode += getDenominazioneEn().hashCode();
        }
        if (getIdHabitat() != null) {
            _hashCode += getIdHabitat().hashCode();
        }
        if (getPrioritario() != null) {
            _hashCode += getPrioritario().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Habitat.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "habitat"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazioneEn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazioneEn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idHabitat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idHabitat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prioritario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prioritario"));
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
