/**
 * VocClassificazioneStrada.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VocClassificazioneStrada  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String categoriaRiferimento;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private java.lang.String limitiVelocita;

    private java.lang.String tipo;

    public VocClassificazioneStrada() {
    }

    public VocClassificazioneStrada(
           java.lang.String categoriaRiferimento,
           java.lang.Integer codice,
           java.lang.String descrizione,
           java.lang.String limitiVelocita,
           java.lang.String tipo) {
        this.categoriaRiferimento = categoriaRiferimento;
        this.codice = codice;
        this.descrizione = descrizione;
        this.limitiVelocita = limitiVelocita;
        this.tipo = tipo;
    }


    /**
     * Gets the categoriaRiferimento value for this VocClassificazioneStrada.
     * 
     * @return categoriaRiferimento
     */
    public java.lang.String getCategoriaRiferimento() {
        return categoriaRiferimento;
    }


    /**
     * Sets the categoriaRiferimento value for this VocClassificazioneStrada.
     * 
     * @param categoriaRiferimento
     */
    public void setCategoriaRiferimento(java.lang.String categoriaRiferimento) {
        this.categoriaRiferimento = categoriaRiferimento;
    }


    /**
     * Gets the codice value for this VocClassificazioneStrada.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this VocClassificazioneStrada.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this VocClassificazioneStrada.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this VocClassificazioneStrada.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the limitiVelocita value for this VocClassificazioneStrada.
     * 
     * @return limitiVelocita
     */
    public java.lang.String getLimitiVelocita() {
        return limitiVelocita;
    }


    /**
     * Sets the limitiVelocita value for this VocClassificazioneStrada.
     * 
     * @param limitiVelocita
     */
    public void setLimitiVelocita(java.lang.String limitiVelocita) {
        this.limitiVelocita = limitiVelocita;
    }


    /**
     * Gets the tipo value for this VocClassificazioneStrada.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this VocClassificazioneStrada.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VocClassificazioneStrada)) return false;
        VocClassificazioneStrada other = (VocClassificazioneStrada) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.categoriaRiferimento==null && other.getCategoriaRiferimento()==null) || 
             (this.categoriaRiferimento!=null &&
              this.categoriaRiferimento.equals(other.getCategoriaRiferimento()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.limitiVelocita==null && other.getLimitiVelocita()==null) || 
             (this.limitiVelocita!=null &&
              this.limitiVelocita.equals(other.getLimitiVelocita()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo())));
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
        if (getCategoriaRiferimento() != null) {
            _hashCode += getCategoriaRiferimento().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getLimitiVelocita() != null) {
            _hashCode += getLimitiVelocita().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VocClassificazioneStrada.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassificazioneStrada"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoriaRiferimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categoriaRiferimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
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
        elemField.setFieldName("limitiVelocita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitiVelocita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipo"));
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
