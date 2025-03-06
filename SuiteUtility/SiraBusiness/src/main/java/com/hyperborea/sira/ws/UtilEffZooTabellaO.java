/**
 * UtilEffZooTabellaO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UtilEffZooTabellaO  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private float concAzoto;

    private java.lang.Integer idTabellaO;

    private float quantAzoto;

    private float quantEffluente;

    private com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento;

    private com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente;

    public UtilEffZooTabellaO() {
    }

    public UtilEffZooTabellaO(
           float concAzoto,
           java.lang.Integer idTabellaO,
           float quantAzoto,
           float quantEffluente,
           com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento,
           com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.concAzoto = concAzoto;
        this.idTabellaO = idTabellaO;
        this.quantAzoto = quantAzoto;
        this.quantEffluente = quantEffluente;
        this.vocLineaTrattamento = vocLineaTrattamento;
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }


    /**
     * Gets the concAzoto value for this UtilEffZooTabellaO.
     * 
     * @return concAzoto
     */
    public float getConcAzoto() {
        return concAzoto;
    }


    /**
     * Sets the concAzoto value for this UtilEffZooTabellaO.
     * 
     * @param concAzoto
     */
    public void setConcAzoto(float concAzoto) {
        this.concAzoto = concAzoto;
    }


    /**
     * Gets the idTabellaO value for this UtilEffZooTabellaO.
     * 
     * @return idTabellaO
     */
    public java.lang.Integer getIdTabellaO() {
        return idTabellaO;
    }


    /**
     * Sets the idTabellaO value for this UtilEffZooTabellaO.
     * 
     * @param idTabellaO
     */
    public void setIdTabellaO(java.lang.Integer idTabellaO) {
        this.idTabellaO = idTabellaO;
    }


    /**
     * Gets the quantAzoto value for this UtilEffZooTabellaO.
     * 
     * @return quantAzoto
     */
    public float getQuantAzoto() {
        return quantAzoto;
    }


    /**
     * Sets the quantAzoto value for this UtilEffZooTabellaO.
     * 
     * @param quantAzoto
     */
    public void setQuantAzoto(float quantAzoto) {
        this.quantAzoto = quantAzoto;
    }


    /**
     * Gets the quantEffluente value for this UtilEffZooTabellaO.
     * 
     * @return quantEffluente
     */
    public float getQuantEffluente() {
        return quantEffluente;
    }


    /**
     * Sets the quantEffluente value for this UtilEffZooTabellaO.
     * 
     * @param quantEffluente
     */
    public void setQuantEffluente(float quantEffluente) {
        this.quantEffluente = quantEffluente;
    }


    /**
     * Gets the vocLineaTrattamento value for this UtilEffZooTabellaO.
     * 
     * @return vocLineaTrattamento
     */
    public com.hyperborea.sira.ws.VocLineaTrattamento getVocLineaTrattamento() {
        return vocLineaTrattamento;
    }


    /**
     * Sets the vocLineaTrattamento value for this UtilEffZooTabellaO.
     * 
     * @param vocLineaTrattamento
     */
    public void setVocLineaTrattamento(com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento) {
        this.vocLineaTrattamento = vocLineaTrattamento;
    }


    /**
     * Gets the vocTipologiaEffluente value for this UtilEffZooTabellaO.
     * 
     * @return vocTipologiaEffluente
     */
    public com.hyperborea.sira.ws.VocTipologiaEffluente getVocTipologiaEffluente() {
        return vocTipologiaEffluente;
    }


    /**
     * Sets the vocTipologiaEffluente value for this UtilEffZooTabellaO.
     * 
     * @param vocTipologiaEffluente
     */
    public void setVocTipologiaEffluente(com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilEffZooTabellaO)) return false;
        UtilEffZooTabellaO other = (UtilEffZooTabellaO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.concAzoto == other.getConcAzoto() &&
            ((this.idTabellaO==null && other.getIdTabellaO()==null) || 
             (this.idTabellaO!=null &&
              this.idTabellaO.equals(other.getIdTabellaO()))) &&
            this.quantAzoto == other.getQuantAzoto() &&
            this.quantEffluente == other.getQuantEffluente() &&
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
        _hashCode += new Float(getConcAzoto()).hashCode();
        if (getIdTabellaO() != null) {
            _hashCode += getIdTabellaO().hashCode();
        }
        _hashCode += new Float(getQuantAzoto()).hashCode();
        _hashCode += new Float(getQuantEffluente()).hashCode();
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
        new org.apache.axis.description.TypeDesc(UtilEffZooTabellaO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concAzoto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concAzoto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaO");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantAzoto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantAzoto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantEffluente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantEffluente"));
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
