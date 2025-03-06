/**
 * UtilEffZooTabellaU.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UtilEffZooTabellaU  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String destinazione;

    private java.lang.Integer idTabellaU;

    private float quantita;

    private java.lang.String tipoAcquaReflua;

    public UtilEffZooTabellaU() {
    }

    public UtilEffZooTabellaU(
           java.lang.String destinazione,
           java.lang.Integer idTabellaU,
           float quantita,
           java.lang.String tipoAcquaReflua) {
        this.destinazione = destinazione;
        this.idTabellaU = idTabellaU;
        this.quantita = quantita;
        this.tipoAcquaReflua = tipoAcquaReflua;
    }


    /**
     * Gets the destinazione value for this UtilEffZooTabellaU.
     * 
     * @return destinazione
     */
    public java.lang.String getDestinazione() {
        return destinazione;
    }


    /**
     * Sets the destinazione value for this UtilEffZooTabellaU.
     * 
     * @param destinazione
     */
    public void setDestinazione(java.lang.String destinazione) {
        this.destinazione = destinazione;
    }


    /**
     * Gets the idTabellaU value for this UtilEffZooTabellaU.
     * 
     * @return idTabellaU
     */
    public java.lang.Integer getIdTabellaU() {
        return idTabellaU;
    }


    /**
     * Sets the idTabellaU value for this UtilEffZooTabellaU.
     * 
     * @param idTabellaU
     */
    public void setIdTabellaU(java.lang.Integer idTabellaU) {
        this.idTabellaU = idTabellaU;
    }


    /**
     * Gets the quantita value for this UtilEffZooTabellaU.
     * 
     * @return quantita
     */
    public float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this UtilEffZooTabellaU.
     * 
     * @param quantita
     */
    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the tipoAcquaReflua value for this UtilEffZooTabellaU.
     * 
     * @return tipoAcquaReflua
     */
    public java.lang.String getTipoAcquaReflua() {
        return tipoAcquaReflua;
    }


    /**
     * Sets the tipoAcquaReflua value for this UtilEffZooTabellaU.
     * 
     * @param tipoAcquaReflua
     */
    public void setTipoAcquaReflua(java.lang.String tipoAcquaReflua) {
        this.tipoAcquaReflua = tipoAcquaReflua;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilEffZooTabellaU)) return false;
        UtilEffZooTabellaU other = (UtilEffZooTabellaU) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.destinazione==null && other.getDestinazione()==null) || 
             (this.destinazione!=null &&
              this.destinazione.equals(other.getDestinazione()))) &&
            ((this.idTabellaU==null && other.getIdTabellaU()==null) || 
             (this.idTabellaU!=null &&
              this.idTabellaU.equals(other.getIdTabellaU()))) &&
            this.quantita == other.getQuantita() &&
            ((this.tipoAcquaReflua==null && other.getTipoAcquaReflua()==null) || 
             (this.tipoAcquaReflua!=null &&
              this.tipoAcquaReflua.equals(other.getTipoAcquaReflua())));
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
        if (getDestinazione() != null) {
            _hashCode += getDestinazione().hashCode();
        }
        if (getIdTabellaU() != null) {
            _hashCode += getIdTabellaU().hashCode();
        }
        _hashCode += new Float(getQuantita()).hashCode();
        if (getTipoAcquaReflua() != null) {
            _hashCode += getTipoAcquaReflua().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtilEffZooTabellaU.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaU"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaU");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaU"));
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
        elemField.setFieldName("tipoAcquaReflua");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoAcquaReflua"));
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
