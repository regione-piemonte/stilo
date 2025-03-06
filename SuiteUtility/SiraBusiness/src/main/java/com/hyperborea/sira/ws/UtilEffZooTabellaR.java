/**
 * UtilEffZooTabellaR.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UtilEffZooTabellaR  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idTabellaR;

    private java.lang.String tipoDisponibilita;

    private java.lang.String tipoMacchina;

    public UtilEffZooTabellaR() {
    }

    public UtilEffZooTabellaR(
           java.lang.Integer idTabellaR,
           java.lang.String tipoDisponibilita,
           java.lang.String tipoMacchina) {
        this.idTabellaR = idTabellaR;
        this.tipoDisponibilita = tipoDisponibilita;
        this.tipoMacchina = tipoMacchina;
    }


    /**
     * Gets the idTabellaR value for this UtilEffZooTabellaR.
     * 
     * @return idTabellaR
     */
    public java.lang.Integer getIdTabellaR() {
        return idTabellaR;
    }


    /**
     * Sets the idTabellaR value for this UtilEffZooTabellaR.
     * 
     * @param idTabellaR
     */
    public void setIdTabellaR(java.lang.Integer idTabellaR) {
        this.idTabellaR = idTabellaR;
    }


    /**
     * Gets the tipoDisponibilita value for this UtilEffZooTabellaR.
     * 
     * @return tipoDisponibilita
     */
    public java.lang.String getTipoDisponibilita() {
        return tipoDisponibilita;
    }


    /**
     * Sets the tipoDisponibilita value for this UtilEffZooTabellaR.
     * 
     * @param tipoDisponibilita
     */
    public void setTipoDisponibilita(java.lang.String tipoDisponibilita) {
        this.tipoDisponibilita = tipoDisponibilita;
    }


    /**
     * Gets the tipoMacchina value for this UtilEffZooTabellaR.
     * 
     * @return tipoMacchina
     */
    public java.lang.String getTipoMacchina() {
        return tipoMacchina;
    }


    /**
     * Sets the tipoMacchina value for this UtilEffZooTabellaR.
     * 
     * @param tipoMacchina
     */
    public void setTipoMacchina(java.lang.String tipoMacchina) {
        this.tipoMacchina = tipoMacchina;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilEffZooTabellaR)) return false;
        UtilEffZooTabellaR other = (UtilEffZooTabellaR) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idTabellaR==null && other.getIdTabellaR()==null) || 
             (this.idTabellaR!=null &&
              this.idTabellaR.equals(other.getIdTabellaR()))) &&
            ((this.tipoDisponibilita==null && other.getTipoDisponibilita()==null) || 
             (this.tipoDisponibilita!=null &&
              this.tipoDisponibilita.equals(other.getTipoDisponibilita()))) &&
            ((this.tipoMacchina==null && other.getTipoMacchina()==null) || 
             (this.tipoMacchina!=null &&
              this.tipoMacchina.equals(other.getTipoMacchina())));
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
        if (getIdTabellaR() != null) {
            _hashCode += getIdTabellaR().hashCode();
        }
        if (getTipoDisponibilita() != null) {
            _hashCode += getTipoDisponibilita().hashCode();
        }
        if (getTipoMacchina() != null) {
            _hashCode += getTipoMacchina().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtilEffZooTabellaR.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaR"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoDisponibilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoDisponibilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoMacchina");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoMacchina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
