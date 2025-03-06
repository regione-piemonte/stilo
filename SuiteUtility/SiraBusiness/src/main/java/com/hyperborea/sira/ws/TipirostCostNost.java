/**
 * TipirostCostNost.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipirostCostNost  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ClassiRost classeRost;

    private com.hyperborea.sira.ws.TipirostCostNostId id;

    private java.lang.Integer padreUnicoPerTipo;

    public TipirostCostNost() {
    }

    public TipirostCostNost(
           com.hyperborea.sira.ws.ClassiRost classeRost,
           com.hyperborea.sira.ws.TipirostCostNostId id,
           java.lang.Integer padreUnicoPerTipo) {
        this.classeRost = classeRost;
        this.id = id;
        this.padreUnicoPerTipo = padreUnicoPerTipo;
    }


    /**
     * Gets the classeRost value for this TipirostCostNost.
     * 
     * @return classeRost
     */
    public com.hyperborea.sira.ws.ClassiRost getClasseRost() {
        return classeRost;
    }


    /**
     * Sets the classeRost value for this TipirostCostNost.
     * 
     * @param classeRost
     */
    public void setClasseRost(com.hyperborea.sira.ws.ClassiRost classeRost) {
        this.classeRost = classeRost;
    }


    /**
     * Gets the id value for this TipirostCostNost.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.TipirostCostNostId getId() {
        return id;
    }


    /**
     * Sets the id value for this TipirostCostNost.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.TipirostCostNostId id) {
        this.id = id;
    }


    /**
     * Gets the padreUnicoPerTipo value for this TipirostCostNost.
     * 
     * @return padreUnicoPerTipo
     */
    public java.lang.Integer getPadreUnicoPerTipo() {
        return padreUnicoPerTipo;
    }


    /**
     * Sets the padreUnicoPerTipo value for this TipirostCostNost.
     * 
     * @param padreUnicoPerTipo
     */
    public void setPadreUnicoPerTipo(java.lang.Integer padreUnicoPerTipo) {
        this.padreUnicoPerTipo = padreUnicoPerTipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipirostCostNost)) return false;
        TipirostCostNost other = (TipirostCostNost) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.classeRost==null && other.getClasseRost()==null) || 
             (this.classeRost!=null &&
              this.classeRost.equals(other.getClasseRost()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.padreUnicoPerTipo==null && other.getPadreUnicoPerTipo()==null) || 
             (this.padreUnicoPerTipo!=null &&
              this.padreUnicoPerTipo.equals(other.getPadreUnicoPerTipo())));
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
        if (getClasseRost() != null) {
            _hashCode += getClasseRost().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getPadreUnicoPerTipo() != null) {
            _hashCode += getPadreUnicoPerTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipirostCostNost.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipirostCostNost"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classeRost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classeRost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiRost"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipirostCostNostId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("padreUnicoPerTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "padreUnicoPerTipo"));
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
