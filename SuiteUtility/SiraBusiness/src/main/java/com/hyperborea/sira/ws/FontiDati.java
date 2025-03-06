/**
 * FontiDati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class FontiDati  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar dataFonteDati;

    private java.lang.String descrizione;

    private java.lang.Integer idFonteDati;

    private java.lang.String riferimentoAmministrativo;

    private java.lang.String riferimentoEsterno;

    private java.lang.String riferimentoNormativo;

    private java.lang.String titolo;

    public FontiDati() {
    }

    public FontiDati(
           java.util.Calendar dataFonteDati,
           java.lang.String descrizione,
           java.lang.Integer idFonteDati,
           java.lang.String riferimentoAmministrativo,
           java.lang.String riferimentoEsterno,
           java.lang.String riferimentoNormativo,
           java.lang.String titolo) {
        this.dataFonteDati = dataFonteDati;
        this.descrizione = descrizione;
        this.idFonteDati = idFonteDati;
        this.riferimentoAmministrativo = riferimentoAmministrativo;
        this.riferimentoEsterno = riferimentoEsterno;
        this.riferimentoNormativo = riferimentoNormativo;
        this.titolo = titolo;
    }


    /**
     * Gets the dataFonteDati value for this FontiDati.
     * 
     * @return dataFonteDati
     */
    public java.util.Calendar getDataFonteDati() {
        return dataFonteDati;
    }


    /**
     * Sets the dataFonteDati value for this FontiDati.
     * 
     * @param dataFonteDati
     */
    public void setDataFonteDati(java.util.Calendar dataFonteDati) {
        this.dataFonteDati = dataFonteDati;
    }


    /**
     * Gets the descrizione value for this FontiDati.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this FontiDati.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idFonteDati value for this FontiDati.
     * 
     * @return idFonteDati
     */
    public java.lang.Integer getIdFonteDati() {
        return idFonteDati;
    }


    /**
     * Sets the idFonteDati value for this FontiDati.
     * 
     * @param idFonteDati
     */
    public void setIdFonteDati(java.lang.Integer idFonteDati) {
        this.idFonteDati = idFonteDati;
    }


    /**
     * Gets the riferimentoAmministrativo value for this FontiDati.
     * 
     * @return riferimentoAmministrativo
     */
    public java.lang.String getRiferimentoAmministrativo() {
        return riferimentoAmministrativo;
    }


    /**
     * Sets the riferimentoAmministrativo value for this FontiDati.
     * 
     * @param riferimentoAmministrativo
     */
    public void setRiferimentoAmministrativo(java.lang.String riferimentoAmministrativo) {
        this.riferimentoAmministrativo = riferimentoAmministrativo;
    }


    /**
     * Gets the riferimentoEsterno value for this FontiDati.
     * 
     * @return riferimentoEsterno
     */
    public java.lang.String getRiferimentoEsterno() {
        return riferimentoEsterno;
    }


    /**
     * Sets the riferimentoEsterno value for this FontiDati.
     * 
     * @param riferimentoEsterno
     */
    public void setRiferimentoEsterno(java.lang.String riferimentoEsterno) {
        this.riferimentoEsterno = riferimentoEsterno;
    }


    /**
     * Gets the riferimentoNormativo value for this FontiDati.
     * 
     * @return riferimentoNormativo
     */
    public java.lang.String getRiferimentoNormativo() {
        return riferimentoNormativo;
    }


    /**
     * Sets the riferimentoNormativo value for this FontiDati.
     * 
     * @param riferimentoNormativo
     */
    public void setRiferimentoNormativo(java.lang.String riferimentoNormativo) {
        this.riferimentoNormativo = riferimentoNormativo;
    }


    /**
     * Gets the titolo value for this FontiDati.
     * 
     * @return titolo
     */
    public java.lang.String getTitolo() {
        return titolo;
    }


    /**
     * Sets the titolo value for this FontiDati.
     * 
     * @param titolo
     */
    public void setTitolo(java.lang.String titolo) {
        this.titolo = titolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FontiDati)) return false;
        FontiDati other = (FontiDati) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataFonteDati==null && other.getDataFonteDati()==null) || 
             (this.dataFonteDati!=null &&
              this.dataFonteDati.equals(other.getDataFonteDati()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idFonteDati==null && other.getIdFonteDati()==null) || 
             (this.idFonteDati!=null &&
              this.idFonteDati.equals(other.getIdFonteDati()))) &&
            ((this.riferimentoAmministrativo==null && other.getRiferimentoAmministrativo()==null) || 
             (this.riferimentoAmministrativo!=null &&
              this.riferimentoAmministrativo.equals(other.getRiferimentoAmministrativo()))) &&
            ((this.riferimentoEsterno==null && other.getRiferimentoEsterno()==null) || 
             (this.riferimentoEsterno!=null &&
              this.riferimentoEsterno.equals(other.getRiferimentoEsterno()))) &&
            ((this.riferimentoNormativo==null && other.getRiferimentoNormativo()==null) || 
             (this.riferimentoNormativo!=null &&
              this.riferimentoNormativo.equals(other.getRiferimentoNormativo()))) &&
            ((this.titolo==null && other.getTitolo()==null) || 
             (this.titolo!=null &&
              this.titolo.equals(other.getTitolo())));
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
        if (getDataFonteDati() != null) {
            _hashCode += getDataFonteDati().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdFonteDati() != null) {
            _hashCode += getIdFonteDati().hashCode();
        }
        if (getRiferimentoAmministrativo() != null) {
            _hashCode += getRiferimentoAmministrativo().hashCode();
        }
        if (getRiferimentoEsterno() != null) {
            _hashCode += getRiferimentoEsterno().hashCode();
        }
        if (getRiferimentoNormativo() != null) {
            _hashCode += getRiferimentoNormativo().hashCode();
        }
        if (getTitolo() != null) {
            _hashCode += getTitolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FontiDati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "fontiDati"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFonteDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFonteDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("idFonteDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idFonteDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentoAmministrativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentoAmministrativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentoEsterno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentoEsterno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentoNormativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentoNormativo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titolo"));
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
