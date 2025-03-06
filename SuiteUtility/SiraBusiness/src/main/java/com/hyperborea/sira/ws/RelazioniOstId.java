/**
 * RelazioniOstId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RelazioniOstId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsOstRef childOstRef;

    private java.util.Calendar dataInizio;

    private com.hyperborea.sira.ws.WsOstRef parentOstRef;

    private com.hyperborea.sira.ws.TipologieRost tipologieRost;

    public RelazioniOstId() {
    }

    public RelazioniOstId(
           com.hyperborea.sira.ws.WsOstRef childOstRef,
           java.util.Calendar dataInizio,
           com.hyperborea.sira.ws.WsOstRef parentOstRef,
           com.hyperborea.sira.ws.TipologieRost tipologieRost) {
           this.childOstRef = childOstRef;
           this.dataInizio = dataInizio;
           this.parentOstRef = parentOstRef;
           this.tipologieRost = tipologieRost;
    }


    /**
     * Gets the childOstRef value for this RelazioniOstId.
     * 
     * @return childOstRef
     */
    public com.hyperborea.sira.ws.WsOstRef getChildOstRef() {
        return childOstRef;
    }


    /**
     * Sets the childOstRef value for this RelazioniOstId.
     * 
     * @param childOstRef
     */
    public void setChildOstRef(com.hyperborea.sira.ws.WsOstRef childOstRef) {
        this.childOstRef = childOstRef;
    }


    /**
     * Gets the dataInizio value for this RelazioniOstId.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this RelazioniOstId.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the parentOstRef value for this RelazioniOstId.
     * 
     * @return parentOstRef
     */
    public com.hyperborea.sira.ws.WsOstRef getParentOstRef() {
        return parentOstRef;
    }


    /**
     * Sets the parentOstRef value for this RelazioniOstId.
     * 
     * @param parentOstRef
     */
    public void setParentOstRef(com.hyperborea.sira.ws.WsOstRef parentOstRef) {
        this.parentOstRef = parentOstRef;
    }


    /**
     * Gets the tipologieRost value for this RelazioniOstId.
     * 
     * @return tipologieRost
     */
    public com.hyperborea.sira.ws.TipologieRost getTipologieRost() {
        return tipologieRost;
    }


    /**
     * Sets the tipologieRost value for this RelazioniOstId.
     * 
     * @param tipologieRost
     */
    public void setTipologieRost(com.hyperborea.sira.ws.TipologieRost tipologieRost) {
        this.tipologieRost = tipologieRost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RelazioniOstId)) return false;
        RelazioniOstId other = (RelazioniOstId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.childOstRef==null && other.getChildOstRef()==null) || 
             (this.childOstRef!=null &&
              this.childOstRef.equals(other.getChildOstRef()))) &&
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.parentOstRef==null && other.getParentOstRef()==null) || 
             (this.parentOstRef!=null &&
              this.parentOstRef.equals(other.getParentOstRef()))) &&
            ((this.tipologieRost==null && other.getTipologieRost()==null) || 
             (this.tipologieRost!=null &&
              this.tipologieRost.equals(other.getTipologieRost())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getChildOstRef() != null) {
            _hashCode += getChildOstRef().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getParentOstRef() != null) {
            _hashCode += getParentOstRef().hashCode();
        }
        if (getTipologieRost() != null) {
            _hashCode += getTipologieRost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RelazioniOstId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relazioniOstId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("childOstRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "childOstRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentOstRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentOstRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieRost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieRost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieRost"));
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
