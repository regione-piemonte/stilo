/**
 * ParametriAmbientali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ParametriAmbientali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String casNumber;

    private java.lang.String descConvenz;

    private java.lang.String descrizione;

    private org.apache.axis.types.UnsignedShort organicoInorganico;

    private int idIipa;  // attribute

    public ParametriAmbientali() {
    }

    public ParametriAmbientali(
           int idIipa,
           java.lang.String casNumber,
           java.lang.String descConvenz,
           java.lang.String descrizione,
           org.apache.axis.types.UnsignedShort organicoInorganico) {
        this.idIipa = idIipa;
        this.casNumber = casNumber;
        this.descConvenz = descConvenz;
        this.descrizione = descrizione;
        this.organicoInorganico = organicoInorganico;
    }


    /**
     * Gets the casNumber value for this ParametriAmbientali.
     * 
     * @return casNumber
     */
    public java.lang.String getCasNumber() {
        return casNumber;
    }


    /**
     * Sets the casNumber value for this ParametriAmbientali.
     * 
     * @param casNumber
     */
    public void setCasNumber(java.lang.String casNumber) {
        this.casNumber = casNumber;
    }


    /**
     * Gets the descConvenz value for this ParametriAmbientali.
     * 
     * @return descConvenz
     */
    public java.lang.String getDescConvenz() {
        return descConvenz;
    }


    /**
     * Sets the descConvenz value for this ParametriAmbientali.
     * 
     * @param descConvenz
     */
    public void setDescConvenz(java.lang.String descConvenz) {
        this.descConvenz = descConvenz;
    }


    /**
     * Gets the descrizione value for this ParametriAmbientali.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ParametriAmbientali.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the organicoInorganico value for this ParametriAmbientali.
     * 
     * @return organicoInorganico
     */
    public org.apache.axis.types.UnsignedShort getOrganicoInorganico() {
        return organicoInorganico;
    }


    /**
     * Sets the organicoInorganico value for this ParametriAmbientali.
     * 
     * @param organicoInorganico
     */
    public void setOrganicoInorganico(org.apache.axis.types.UnsignedShort organicoInorganico) {
        this.organicoInorganico = organicoInorganico;
    }


    /**
     * Gets the idIipa value for this ParametriAmbientali.
     * 
     * @return idIipa
     */
    public int getIdIipa() {
        return idIipa;
    }


    /**
     * Sets the idIipa value for this ParametriAmbientali.
     * 
     * @param idIipa
     */
    public void setIdIipa(int idIipa) {
        this.idIipa = idIipa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ParametriAmbientali)) return false;
        ParametriAmbientali other = (ParametriAmbientali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.casNumber==null && other.getCasNumber()==null) || 
             (this.casNumber!=null &&
              this.casNumber.equals(other.getCasNumber()))) &&
            ((this.descConvenz==null && other.getDescConvenz()==null) || 
             (this.descConvenz!=null &&
              this.descConvenz.equals(other.getDescConvenz()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.organicoInorganico==null && other.getOrganicoInorganico()==null) || 
             (this.organicoInorganico!=null &&
              this.organicoInorganico.equals(other.getOrganicoInorganico()))) &&
            this.idIipa == other.getIdIipa();
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
        if (getCasNumber() != null) {
            _hashCode += getCasNumber().hashCode();
        }
        if (getDescConvenz() != null) {
            _hashCode += getDescConvenz().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getOrganicoInorganico() != null) {
            _hashCode += getOrganicoInorganico().hashCode();
        }
        _hashCode += getIdIipa();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ParametriAmbientali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "parametriAmbientali"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("idIipa");
        attrField.setXmlName(new javax.xml.namespace.QName("", "idIipa"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("casNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "casNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descConvenz");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descConvenz"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
        elemField.setFieldName("organicoInorganico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organicoInorganico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
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
