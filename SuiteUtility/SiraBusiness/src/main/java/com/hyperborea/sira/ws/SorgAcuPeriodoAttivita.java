/**
 * SorgAcuPeriodoAttivita.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SorgAcuPeriodoAttivita  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idPeriodo;

    private java.lang.Float oreFunzionamento;

    public SorgAcuPeriodoAttivita() {
    }

    public SorgAcuPeriodoAttivita(
           java.lang.String descrizione,
           java.lang.Integer idPeriodo,
           java.lang.Float oreFunzionamento) {
        this.descrizione = descrizione;
        this.idPeriodo = idPeriodo;
        this.oreFunzionamento = oreFunzionamento;
    }


    /**
     * Gets the descrizione value for this SorgAcuPeriodoAttivita.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this SorgAcuPeriodoAttivita.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idPeriodo value for this SorgAcuPeriodoAttivita.
     * 
     * @return idPeriodo
     */
    public java.lang.Integer getIdPeriodo() {
        return idPeriodo;
    }


    /**
     * Sets the idPeriodo value for this SorgAcuPeriodoAttivita.
     * 
     * @param idPeriodo
     */
    public void setIdPeriodo(java.lang.Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }


    /**
     * Gets the oreFunzionamento value for this SorgAcuPeriodoAttivita.
     * 
     * @return oreFunzionamento
     */
    public java.lang.Float getOreFunzionamento() {
        return oreFunzionamento;
    }


    /**
     * Sets the oreFunzionamento value for this SorgAcuPeriodoAttivita.
     * 
     * @param oreFunzionamento
     */
    public void setOreFunzionamento(java.lang.Float oreFunzionamento) {
        this.oreFunzionamento = oreFunzionamento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SorgAcuPeriodoAttivita)) return false;
        SorgAcuPeriodoAttivita other = (SorgAcuPeriodoAttivita) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idPeriodo==null && other.getIdPeriodo()==null) || 
             (this.idPeriodo!=null &&
              this.idPeriodo.equals(other.getIdPeriodo()))) &&
            ((this.oreFunzionamento==null && other.getOreFunzionamento()==null) || 
             (this.oreFunzionamento!=null &&
              this.oreFunzionamento.equals(other.getOreFunzionamento())));
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdPeriodo() != null) {
            _hashCode += getIdPeriodo().hashCode();
        }
        if (getOreFunzionamento() != null) {
            _hashCode += getOreFunzionamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SorgAcuPeriodoAttivita.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgAcuPeriodoAttivita"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPeriodo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPeriodo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oreFunzionamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oreFunzionamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
