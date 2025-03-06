/**
 * ProdEffZooTabellaH.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaH  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idTabellaH;

    private com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento;

    private com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente;

    private float volumeEffluente;

    public ProdEffZooTabellaH() {
    }

    public ProdEffZooTabellaH(
           java.lang.Integer idTabellaH,
           com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento,
           com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente,
           float volumeEffluente) {
        this.idTabellaH = idTabellaH;
        this.vocLineaTrattamento = vocLineaTrattamento;
        this.vocTipologiaEffluente = vocTipologiaEffluente;
        this.volumeEffluente = volumeEffluente;
    }


    /**
     * Gets the idTabellaH value for this ProdEffZooTabellaH.
     * 
     * @return idTabellaH
     */
    public java.lang.Integer getIdTabellaH() {
        return idTabellaH;
    }


    /**
     * Sets the idTabellaH value for this ProdEffZooTabellaH.
     * 
     * @param idTabellaH
     */
    public void setIdTabellaH(java.lang.Integer idTabellaH) {
        this.idTabellaH = idTabellaH;
    }


    /**
     * Gets the vocLineaTrattamento value for this ProdEffZooTabellaH.
     * 
     * @return vocLineaTrattamento
     */
    public com.hyperborea.sira.ws.VocLineaTrattamento getVocLineaTrattamento() {
        return vocLineaTrattamento;
    }


    /**
     * Sets the vocLineaTrattamento value for this ProdEffZooTabellaH.
     * 
     * @param vocLineaTrattamento
     */
    public void setVocLineaTrattamento(com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento) {
        this.vocLineaTrattamento = vocLineaTrattamento;
    }


    /**
     * Gets the vocTipologiaEffluente value for this ProdEffZooTabellaH.
     * 
     * @return vocTipologiaEffluente
     */
    public com.hyperborea.sira.ws.VocTipologiaEffluente getVocTipologiaEffluente() {
        return vocTipologiaEffluente;
    }


    /**
     * Sets the vocTipologiaEffluente value for this ProdEffZooTabellaH.
     * 
     * @param vocTipologiaEffluente
     */
    public void setVocTipologiaEffluente(com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }


    /**
     * Gets the volumeEffluente value for this ProdEffZooTabellaH.
     * 
     * @return volumeEffluente
     */
    public float getVolumeEffluente() {
        return volumeEffluente;
    }


    /**
     * Sets the volumeEffluente value for this ProdEffZooTabellaH.
     * 
     * @param volumeEffluente
     */
    public void setVolumeEffluente(float volumeEffluente) {
        this.volumeEffluente = volumeEffluente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaH)) return false;
        ProdEffZooTabellaH other = (ProdEffZooTabellaH) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idTabellaH==null && other.getIdTabellaH()==null) || 
             (this.idTabellaH!=null &&
              this.idTabellaH.equals(other.getIdTabellaH()))) &&
            ((this.vocLineaTrattamento==null && other.getVocLineaTrattamento()==null) || 
             (this.vocLineaTrattamento!=null &&
              this.vocLineaTrattamento.equals(other.getVocLineaTrattamento()))) &&
            ((this.vocTipologiaEffluente==null && other.getVocTipologiaEffluente()==null) || 
             (this.vocTipologiaEffluente!=null &&
              this.vocTipologiaEffluente.equals(other.getVocTipologiaEffluente()))) &&
            this.volumeEffluente == other.getVolumeEffluente();
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
        if (getIdTabellaH() != null) {
            _hashCode += getIdTabellaH().hashCode();
        }
        if (getVocLineaTrattamento() != null) {
            _hashCode += getVocLineaTrattamento().hashCode();
        }
        if (getVocTipologiaEffluente() != null) {
            _hashCode += getVocTipologiaEffluente().hashCode();
        }
        _hashCode += new Float(getVolumeEffluente()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaH.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaH"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaH");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeEffluente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeEffluente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
