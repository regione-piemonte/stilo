/**
 * ProdEffZooTabellaG.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaG  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idTabellaG;

    private com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento;

    private float volumeLiquame;

    public ProdEffZooTabellaG() {
    }

    public ProdEffZooTabellaG(
           java.lang.Integer idTabellaG,
           com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento,
           float volumeLiquame) {
        this.idTabellaG = idTabellaG;
        this.vocLineaTrattamento = vocLineaTrattamento;
        this.volumeLiquame = volumeLiquame;
    }


    /**
     * Gets the idTabellaG value for this ProdEffZooTabellaG.
     * 
     * @return idTabellaG
     */
    public java.lang.Integer getIdTabellaG() {
        return idTabellaG;
    }


    /**
     * Sets the idTabellaG value for this ProdEffZooTabellaG.
     * 
     * @param idTabellaG
     */
    public void setIdTabellaG(java.lang.Integer idTabellaG) {
        this.idTabellaG = idTabellaG;
    }


    /**
     * Gets the vocLineaTrattamento value for this ProdEffZooTabellaG.
     * 
     * @return vocLineaTrattamento
     */
    public com.hyperborea.sira.ws.VocLineaTrattamento getVocLineaTrattamento() {
        return vocLineaTrattamento;
    }


    /**
     * Sets the vocLineaTrattamento value for this ProdEffZooTabellaG.
     * 
     * @param vocLineaTrattamento
     */
    public void setVocLineaTrattamento(com.hyperborea.sira.ws.VocLineaTrattamento vocLineaTrattamento) {
        this.vocLineaTrattamento = vocLineaTrattamento;
    }


    /**
     * Gets the volumeLiquame value for this ProdEffZooTabellaG.
     * 
     * @return volumeLiquame
     */
    public float getVolumeLiquame() {
        return volumeLiquame;
    }


    /**
     * Sets the volumeLiquame value for this ProdEffZooTabellaG.
     * 
     * @param volumeLiquame
     */
    public void setVolumeLiquame(float volumeLiquame) {
        this.volumeLiquame = volumeLiquame;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaG)) return false;
        ProdEffZooTabellaG other = (ProdEffZooTabellaG) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idTabellaG==null && other.getIdTabellaG()==null) || 
             (this.idTabellaG!=null &&
              this.idTabellaG.equals(other.getIdTabellaG()))) &&
            ((this.vocLineaTrattamento==null && other.getVocLineaTrattamento()==null) || 
             (this.vocLineaTrattamento!=null &&
              this.vocLineaTrattamento.equals(other.getVocLineaTrattamento()))) &&
            this.volumeLiquame == other.getVolumeLiquame();
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
        if (getIdTabellaG() != null) {
            _hashCode += getIdTabellaG().hashCode();
        }
        if (getVocLineaTrattamento() != null) {
            _hashCode += getVocLineaTrattamento().hashCode();
        }
        _hashCode += new Float(getVolumeLiquame()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaG.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaG"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaG");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaG"));
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
        elemField.setFieldName("volumeLiquame");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeLiquame"));
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
