/**
 * ClassiValori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ClassiValori  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String denominazione;

    private java.lang.Integer idClasse;

    private java.lang.Integer ordine;

    public ClassiValori() {
    }

    public ClassiValori(
           java.lang.String denominazione,
           java.lang.Integer idClasse,
           java.lang.Integer ordine) {
        this.denominazione = denominazione;
        this.idClasse = idClasse;
        this.ordine = ordine;
    }


    /**
     * Gets the denominazione value for this ClassiValori.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this ClassiValori.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the idClasse value for this ClassiValori.
     * 
     * @return idClasse
     */
    public java.lang.Integer getIdClasse() {
        return idClasse;
    }


    /**
     * Sets the idClasse value for this ClassiValori.
     * 
     * @param idClasse
     */
    public void setIdClasse(java.lang.Integer idClasse) {
        this.idClasse = idClasse;
    }


    /**
     * Gets the ordine value for this ClassiValori.
     * 
     * @return ordine
     */
    public java.lang.Integer getOrdine() {
        return ordine;
    }


    /**
     * Sets the ordine value for this ClassiValori.
     * 
     * @param ordine
     */
    public void setOrdine(java.lang.Integer ordine) {
        this.ordine = ordine;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ClassiValori)) return false;
        ClassiValori other = (ClassiValori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.idClasse==null && other.getIdClasse()==null) || 
             (this.idClasse!=null &&
              this.idClasse.equals(other.getIdClasse()))) &&
            ((this.ordine==null && other.getOrdine()==null) || 
             (this.ordine!=null &&
              this.ordine.equals(other.getOrdine())));
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
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getIdClasse() != null) {
            _hashCode += getIdClasse().hashCode();
        }
        if (getOrdine() != null) {
            _hashCode += getOrdine().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ClassiValori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiValori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idClasse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idClasse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
