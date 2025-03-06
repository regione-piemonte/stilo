/**
 * UtilEffZooTabellaP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UtilEffZooTabellaP  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idTabellaP;

    private float quantEffluente;

    private com.hyperborea.sira.ws.VocDestinazioneEffluente vocDestinazioneEffluente;

    private com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente;

    public UtilEffZooTabellaP() {
    }

    public UtilEffZooTabellaP(
           java.lang.Integer idTabellaP,
           float quantEffluente,
           com.hyperborea.sira.ws.VocDestinazioneEffluente vocDestinazioneEffluente,
           com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.idTabellaP = idTabellaP;
        this.quantEffluente = quantEffluente;
        this.vocDestinazioneEffluente = vocDestinazioneEffluente;
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }


    /**
     * Gets the idTabellaP value for this UtilEffZooTabellaP.
     * 
     * @return idTabellaP
     */
    public java.lang.Integer getIdTabellaP() {
        return idTabellaP;
    }


    /**
     * Sets the idTabellaP value for this UtilEffZooTabellaP.
     * 
     * @param idTabellaP
     */
    public void setIdTabellaP(java.lang.Integer idTabellaP) {
        this.idTabellaP = idTabellaP;
    }


    /**
     * Gets the quantEffluente value for this UtilEffZooTabellaP.
     * 
     * @return quantEffluente
     */
    public float getQuantEffluente() {
        return quantEffluente;
    }


    /**
     * Sets the quantEffluente value for this UtilEffZooTabellaP.
     * 
     * @param quantEffluente
     */
    public void setQuantEffluente(float quantEffluente) {
        this.quantEffluente = quantEffluente;
    }


    /**
     * Gets the vocDestinazioneEffluente value for this UtilEffZooTabellaP.
     * 
     * @return vocDestinazioneEffluente
     */
    public com.hyperborea.sira.ws.VocDestinazioneEffluente getVocDestinazioneEffluente() {
        return vocDestinazioneEffluente;
    }


    /**
     * Sets the vocDestinazioneEffluente value for this UtilEffZooTabellaP.
     * 
     * @param vocDestinazioneEffluente
     */
    public void setVocDestinazioneEffluente(com.hyperborea.sira.ws.VocDestinazioneEffluente vocDestinazioneEffluente) {
        this.vocDestinazioneEffluente = vocDestinazioneEffluente;
    }


    /**
     * Gets the vocTipologiaEffluente value for this UtilEffZooTabellaP.
     * 
     * @return vocTipologiaEffluente
     */
    public com.hyperborea.sira.ws.VocTipologiaEffluente getVocTipologiaEffluente() {
        return vocTipologiaEffluente;
    }


    /**
     * Sets the vocTipologiaEffluente value for this UtilEffZooTabellaP.
     * 
     * @param vocTipologiaEffluente
     */
    public void setVocTipologiaEffluente(com.hyperborea.sira.ws.VocTipologiaEffluente vocTipologiaEffluente) {
        this.vocTipologiaEffluente = vocTipologiaEffluente;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilEffZooTabellaP)) return false;
        UtilEffZooTabellaP other = (UtilEffZooTabellaP) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idTabellaP==null && other.getIdTabellaP()==null) || 
             (this.idTabellaP!=null &&
              this.idTabellaP.equals(other.getIdTabellaP()))) &&
            this.quantEffluente == other.getQuantEffluente() &&
            ((this.vocDestinazioneEffluente==null && other.getVocDestinazioneEffluente()==null) || 
             (this.vocDestinazioneEffluente!=null &&
              this.vocDestinazioneEffluente.equals(other.getVocDestinazioneEffluente()))) &&
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
        if (getIdTabellaP() != null) {
            _hashCode += getIdTabellaP().hashCode();
        }
        _hashCode += new Float(getQuantEffluente()).hashCode();
        if (getVocDestinazioneEffluente() != null) {
            _hashCode += getVocDestinazioneEffluente().hashCode();
        }
        if (getVocTipologiaEffluente() != null) {
            _hashCode += getVocTipologiaEffluente().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtilEffZooTabellaP.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaP"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantEffluente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantEffluente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocDestinazioneEffluente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocDestinazioneEffluente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocDestinazioneEffluente"));
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
