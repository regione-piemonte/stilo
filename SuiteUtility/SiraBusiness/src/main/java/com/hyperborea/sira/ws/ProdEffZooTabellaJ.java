/**
 * ProdEffZooTabellaJ.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaJ  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private float azotoAlCampo;

    private float concAzotoAlCampo;

    private java.lang.Integer idTabellaJ;

    private float quantita;

    private com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente;

    public ProdEffZooTabellaJ() {
    }

    public ProdEffZooTabellaJ(
           float azotoAlCampo,
           float concAzotoAlCampo,
           java.lang.Integer idTabellaJ,
           float quantita,
           com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.azotoAlCampo = azotoAlCampo;
        this.concAzotoAlCampo = concAzotoAlCampo;
        this.idTabellaJ = idTabellaJ;
        this.quantita = quantita;
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }


    /**
     * Gets the azotoAlCampo value for this ProdEffZooTabellaJ.
     * 
     * @return azotoAlCampo
     */
    public float getAzotoAlCampo() {
        return azotoAlCampo;
    }


    /**
     * Sets the azotoAlCampo value for this ProdEffZooTabellaJ.
     * 
     * @param azotoAlCampo
     */
    public void setAzotoAlCampo(float azotoAlCampo) {
        this.azotoAlCampo = azotoAlCampo;
    }


    /**
     * Gets the concAzotoAlCampo value for this ProdEffZooTabellaJ.
     * 
     * @return concAzotoAlCampo
     */
    public float getConcAzotoAlCampo() {
        return concAzotoAlCampo;
    }


    /**
     * Sets the concAzotoAlCampo value for this ProdEffZooTabellaJ.
     * 
     * @param concAzotoAlCampo
     */
    public void setConcAzotoAlCampo(float concAzotoAlCampo) {
        this.concAzotoAlCampo = concAzotoAlCampo;
    }


    /**
     * Gets the idTabellaJ value for this ProdEffZooTabellaJ.
     * 
     * @return idTabellaJ
     */
    public java.lang.Integer getIdTabellaJ() {
        return idTabellaJ;
    }


    /**
     * Sets the idTabellaJ value for this ProdEffZooTabellaJ.
     * 
     * @param idTabellaJ
     */
    public void setIdTabellaJ(java.lang.Integer idTabellaJ) {
        this.idTabellaJ = idTabellaJ;
    }


    /**
     * Gets the quantita value for this ProdEffZooTabellaJ.
     * 
     * @return quantita
     */
    public float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this ProdEffZooTabellaJ.
     * 
     * @param quantita
     */
    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the vocTipologiaEffluente value for this ProdEffZooTabellaJ.
     * 
     * @return vocTipologiaEffluente
     */
    public com.hyperborea.sira.ws.VocTipologiaEffluente getVocTipologiaEffluente() {
        return vocTipologiaEffluente;
    }


    /**
     * Sets the vocTipologiaEffluente value for this ProdEffZooTabellaJ.
     * 
     * @param vocTipologiaEffluente
     */
    public void setVocTipologiaEffluente(com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaJ)) return false;
        ProdEffZooTabellaJ other = (ProdEffZooTabellaJ) obj;
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
            ((this.idTabellaJ==null && other.getIdTabellaJ()==null) || 
             (this.idTabellaJ!=null &&
              this.idTabellaJ.equals(other.getIdTabellaJ()))) &&
            this.quantita == other.getQuantita() &&
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
        if (getIdTabellaJ() != null) {
            _hashCode += getIdTabellaJ().hashCode();
        }
        _hashCode += new Float(getQuantita()).hashCode();
        if (getVocTipologiaEffluente() != null) {
            _hashCode += getVocTipologiaEffluente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaJ.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaJ"));
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
        elemField.setFieldName("idTabellaJ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaJ"));
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
