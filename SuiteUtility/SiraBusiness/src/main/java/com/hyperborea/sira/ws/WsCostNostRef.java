/**
 * WsCostNostRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsCostNostRef  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CostNostId costNostId;

    private java.lang.String descrizioneCategoria;

    private java.lang.String descrizioneNatura;

    public WsCostNostRef() {
    }

    public WsCostNostRef(
           com.hyperborea.sira.ws.CostNostId costNostId,
           java.lang.String descrizioneCategoria,
           java.lang.String descrizioneNatura) {
           this.costNostId = costNostId;
           this.descrizioneCategoria = descrizioneCategoria;
           this.descrizioneNatura = descrizioneNatura;
    }


    /**
     * Gets the costNostId value for this WsCostNostRef.
     * 
     * @return costNostId
     */
    public com.hyperborea.sira.ws.CostNostId getCostNostId() {
        return costNostId;
    }


    /**
     * Sets the costNostId value for this WsCostNostRef.
     * 
     * @param costNostId
     */
    public void setCostNostId(com.hyperborea.sira.ws.CostNostId costNostId) {
        this.costNostId = costNostId;
    }


    /**
     * Gets the descrizioneCategoria value for this WsCostNostRef.
     * 
     * @return descrizioneCategoria
     */
    public java.lang.String getDescrizioneCategoria() {
        return descrizioneCategoria;
    }


    /**
     * Sets the descrizioneCategoria value for this WsCostNostRef.
     * 
     * @param descrizioneCategoria
     */
    public void setDescrizioneCategoria(java.lang.String descrizioneCategoria) {
        this.descrizioneCategoria = descrizioneCategoria;
    }


    /**
     * Gets the descrizioneNatura value for this WsCostNostRef.
     * 
     * @return descrizioneNatura
     */
    public java.lang.String getDescrizioneNatura() {
        return descrizioneNatura;
    }


    /**
     * Sets the descrizioneNatura value for this WsCostNostRef.
     * 
     * @param descrizioneNatura
     */
    public void setDescrizioneNatura(java.lang.String descrizioneNatura) {
        this.descrizioneNatura = descrizioneNatura;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsCostNostRef)) return false;
        WsCostNostRef other = (WsCostNostRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.costNostId==null && other.getCostNostId()==null) || 
             (this.costNostId!=null &&
              this.costNostId.equals(other.getCostNostId()))) &&
            ((this.descrizioneCategoria==null && other.getDescrizioneCategoria()==null) || 
             (this.descrizioneCategoria!=null &&
              this.descrizioneCategoria.equals(other.getDescrizioneCategoria()))) &&
            ((this.descrizioneNatura==null && other.getDescrizioneNatura()==null) || 
             (this.descrizioneNatura!=null &&
              this.descrizioneNatura.equals(other.getDescrizioneNatura())));
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
        if (getCostNostId() != null) {
            _hashCode += getCostNostId().hashCode();
        }
        if (getDescrizioneCategoria() != null) {
            _hashCode += getDescrizioneCategoria().hashCode();
        }
        if (getDescrizioneNatura() != null) {
            _hashCode += getDescrizioneNatura().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsCostNostRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCostNostRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costNostId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costNostId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "costNostId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCategoria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneCategoria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneNatura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneNatura"));
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
