/**
 * Marchi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Marchi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String denominazione;

    private java.lang.Integer idMarchio;

    private com.hyperborea.sira.ws.ImpiantiRcTipo impiantiRcTipo;

    private java.lang.Integer wsRefCcostSediLegaliId;

    public Marchi() {
    }

    public Marchi(
           java.lang.String denominazione,
           java.lang.Integer idMarchio,
           com.hyperborea.sira.ws.ImpiantiRcTipo impiantiRcTipo,
           java.lang.Integer wsRefCcostSediLegaliId) {
        this.denominazione = denominazione;
        this.idMarchio = idMarchio;
        this.impiantiRcTipo = impiantiRcTipo;
        this.wsRefCcostSediLegaliId = wsRefCcostSediLegaliId;
    }


    /**
     * Gets the denominazione value for this Marchi.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this Marchi.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the idMarchio value for this Marchi.
     * 
     * @return idMarchio
     */
    public java.lang.Integer getIdMarchio() {
        return idMarchio;
    }


    /**
     * Sets the idMarchio value for this Marchi.
     * 
     * @param idMarchio
     */
    public void setIdMarchio(java.lang.Integer idMarchio) {
        this.idMarchio = idMarchio;
    }


    /**
     * Gets the impiantiRcTipo value for this Marchi.
     * 
     * @return impiantiRcTipo
     */
    public com.hyperborea.sira.ws.ImpiantiRcTipo getImpiantiRcTipo() {
        return impiantiRcTipo;
    }


    /**
     * Sets the impiantiRcTipo value for this Marchi.
     * 
     * @param impiantiRcTipo
     */
    public void setImpiantiRcTipo(com.hyperborea.sira.ws.ImpiantiRcTipo impiantiRcTipo) {
        this.impiantiRcTipo = impiantiRcTipo;
    }


    /**
     * Gets the wsRefCcostSediLegaliId value for this Marchi.
     * 
     * @return wsRefCcostSediLegaliId
     */
    public java.lang.Integer getWsRefCcostSediLegaliId() {
        return wsRefCcostSediLegaliId;
    }


    /**
     * Sets the wsRefCcostSediLegaliId value for this Marchi.
     * 
     * @param wsRefCcostSediLegaliId
     */
    public void setWsRefCcostSediLegaliId(java.lang.Integer wsRefCcostSediLegaliId) {
        this.wsRefCcostSediLegaliId = wsRefCcostSediLegaliId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Marchi)) return false;
        Marchi other = (Marchi) obj;
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
            ((this.idMarchio==null && other.getIdMarchio()==null) || 
             (this.idMarchio!=null &&
              this.idMarchio.equals(other.getIdMarchio()))) &&
            ((this.impiantiRcTipo==null && other.getImpiantiRcTipo()==null) || 
             (this.impiantiRcTipo!=null &&
              this.impiantiRcTipo.equals(other.getImpiantiRcTipo()))) &&
            ((this.wsRefCcostSediLegaliId==null && other.getWsRefCcostSediLegaliId()==null) || 
             (this.wsRefCcostSediLegaliId!=null &&
              this.wsRefCcostSediLegaliId.equals(other.getWsRefCcostSediLegaliId())));
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
        if (getIdMarchio() != null) {
            _hashCode += getIdMarchio().hashCode();
        }
        if (getImpiantiRcTipo() != null) {
            _hashCode += getImpiantiRcTipo().hashCode();
        }
        if (getWsRefCcostSediLegaliId() != null) {
            _hashCode += getWsRefCcostSediLegaliId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Marchi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "marchi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMarchio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMarchio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("impiantiRcTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantiRcTipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiRcTipo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsRefCcostSediLegaliId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsRefCcostSediLegaliId"));
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
