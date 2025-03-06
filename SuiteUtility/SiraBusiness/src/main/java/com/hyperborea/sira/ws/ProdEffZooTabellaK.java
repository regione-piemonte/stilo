/**
 * ProdEffZooTabellaK.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaK  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private float azotoAlCampo;

    private float concAzotoAlCampo;

    private java.lang.Integer idTabellaK;

    private float quantita;

    private com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento;

    private com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente;

    public ProdEffZooTabellaK() {
    }

    public ProdEffZooTabellaK(
           float azotoAlCampo,
           float concAzotoAlCampo,
           java.lang.Integer idTabellaK,
           float quantita,
           com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento,
           com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.azotoAlCampo = azotoAlCampo;
        this.concAzotoAlCampo = concAzotoAlCampo;
        this.idTabellaK = idTabellaK;
        this.quantita = quantita;
        this.vocLineaTrattamento = vocLineaTrattamento;
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }


    /**
     * Gets the azotoAlCampo value for this ProdEffZooTabellaK.
     * 
     * @return azotoAlCampo
     */
    public float getAzotoAlCampo() {
        return azotoAlCampo;
    }


    /**
     * Sets the azotoAlCampo value for this ProdEffZooTabellaK.
     * 
     * @param azotoAlCampo
     */
    public void setAzotoAlCampo(float azotoAlCampo) {
        this.azotoAlCampo = azotoAlCampo;
    }


    /**
     * Gets the concAzotoAlCampo value for this ProdEffZooTabellaK.
     * 
     * @return concAzotoAlCampo
     */
    public float getConcAzotoAlCampo() {
        return concAzotoAlCampo;
    }


    /**
     * Sets the concAzotoAlCampo value for this ProdEffZooTabellaK.
     * 
     * @param concAzotoAlCampo
     */
    public void setConcAzotoAlCampo(float concAzotoAlCampo) {
        this.concAzotoAlCampo = concAzotoAlCampo;
    }


    /**
     * Gets the idTabellaK value for this ProdEffZooTabellaK.
     * 
     * @return idTabellaK
     */
    public java.lang.Integer getIdTabellaK() {
        return idTabellaK;
    }


    /**
     * Sets the idTabellaK value for this ProdEffZooTabellaK.
     * 
     * @param idTabellaK
     */
    public void setIdTabellaK(java.lang.Integer idTabellaK) {
        this.idTabellaK = idTabellaK;
    }


    /**
     * Gets the quantita value for this ProdEffZooTabellaK.
     * 
     * @return quantita
     */
    public float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this ProdEffZooTabellaK.
     * 
     * @param quantita
     */
    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the vocLineaTrattamento value for this ProdEffZooTabellaK.
     * 
     * @return vocLineaTrattamento
     */
    public com.hyperborea.sira.ws.VocLineaTrattamento getVocLineaTrattamento() {
        return vocLineaTrattamento;
    }


    /**
     * Sets the vocLineaTrattamento value for this ProdEffZooTabellaK.
     * 
     * @param vocLineaTrattamento
     */
    public void setVocLineaTrattamento(com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento) {
        this.vocLineaTrattamento = vocLineaTrattamento;
    }


    /**
     * Gets the vocTipologiaEffluente value for this ProdEffZooTabellaK.
     * 
     * @return vocTipologiaEffluente
     */
    public com.hyperborea.sira.ws.VocTipologiaEffluente getVocTipologiaEffluente() {
        return vocTipologiaEffluente;
    }


    /**
     * Sets the vocTipologiaEffluente value for this ProdEffZooTabellaK.
     * 
     * @param vocTipologiaEffluente
     */
    public void setVocTipologiaEffluente(com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaK)) return false;
        ProdEffZooTabellaK other = (ProdEffZooTabellaK) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.azotoAlCampo == other.getAzotoAlCampo() &&
            this.concAzotoAlCampo == other.getConcAzotoAlCampo() &&
            ((this.idTabellaK==null && other.getIdTabellaK()==null) || 
             (this.idTabellaK!=null &&
              this.idTabellaK.equals(other.getIdTabellaK()))) &&
            this.quantita == other.getQuantita() &&
            ((this.vocLineaTrattamento==null && other.getVocLineaTrattamento()==null) || 
             (this.vocLineaTrattamento!=null &&
              this.vocLineaTrattamento.equals(other.getVocLineaTrattamento()))) &&
            ((this.vocTipologiaEffluente==null && other.getVocTipologiaEffluente()==null) || 
             (this.vocTipologiaEffluente!=null &&
              this.vocTipologiaEffluente.equals(other.getVocTipologiaEffluente())));
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
        _hashCode += new Float(getAzotoAlCampo()).hashCode();
        _hashCode += new Float(getConcAzotoAlCampo()).hashCode();
        if (getIdTabellaK() != null) {
            _hashCode += getIdTabellaK().hashCode();
        }
        _hashCode += new Float(getQuantita()).hashCode();
        if (getVocLineaTrattamento() != null) {
            _hashCode += getVocLineaTrattamento().hashCode();
        }
        if (getVocTipologiaEffluente() != null) {
            _hashCode += getVocTipologiaEffluente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaK.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaK"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("azotoAlCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "azotoAlCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concAzotoAlCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concAzotoAlCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaK"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocLineaTrattamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocLineaTrattamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocLineaTrattamento"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaEffluente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaEffluente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaEffluente"));
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
