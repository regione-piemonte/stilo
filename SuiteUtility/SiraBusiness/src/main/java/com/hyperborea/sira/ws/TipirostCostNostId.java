/**
 * TipirostCostNostId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipirostCostNostId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.WsCostNostRef costNostChild;

    private com.hyperborea.sira.ws.WsCostNostRef costNostParent;

    private com.hyperborea.sira.ws.TipologieRost tipologieRost;

    public TipirostCostNostId() {
    }

    public TipirostCostNostId(
           com.hyperborea.sira.ws.WsCostNostRef costNostChild,
           com.hyperborea.sira.ws.WsCostNostRef costNostParent,
           com.hyperborea.sira.ws.TipologieRost tipologieRost) {
           this.costNostChild = costNostChild;
           this.costNostParent = costNostParent;
           this.tipologieRost = tipologieRost;
    }


    /**
     * Gets the costNostChild value for this TipirostCostNostId.
     * 
     * @return costNostChild
     */
    public com.hyperborea.sira.ws.WsCostNostRef getCostNostChild() {
        return costNostChild;
    }


    /**
     * Sets the costNostChild value for this TipirostCostNostId.
     * 
     * @param costNostChild
     */
    public void setCostNostChild(com.hyperborea.sira.ws.WsCostNostRef costNostChild) {
        this.costNostChild = costNostChild;
    }


    /**
     * Gets the costNostParent value for this TipirostCostNostId.
     * 
     * @return costNostParent
     */
    public com.hyperborea.sira.ws.WsCostNostRef getCostNostParent() {
        return costNostParent;
    }


    /**
     * Sets the costNostParent value for this TipirostCostNostId.
     * 
     * @param costNostParent
     */
    public void setCostNostParent(com.hyperborea.sira.ws.WsCostNostRef costNostParent) {
        this.costNostParent = costNostParent;
    }


    /**
     * Gets the tipologieRost value for this TipirostCostNostId.
     * 
     * @return tipologieRost
     */
    public com.hyperborea.sira.ws.TipologieRost getTipologieRost() {
        return tipologieRost;
    }


    /**
     * Sets the tipologieRost value for this TipirostCostNostId.
     * 
     * @param tipologieRost
     */
    public void setTipologieRost(com.hyperborea.sira.ws.TipologieRost tipologieRost) {
        this.tipologieRost = tipologieRost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipirostCostNostId)) return false;
        TipirostCostNostId other = (TipirostCostNostId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.costNostChild==null && other.getCostNostChild()==null) || 
             (this.costNostChild!=null &&
              this.costNostChild.equals(other.getCostNostChild()))) &&
            ((this.costNostParent==null && other.getCostNostParent()==null) || 
             (this.costNostParent!=null &&
              this.costNostParent.equals(other.getCostNostParent()))) &&
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
        if (getCostNostChild() != null) {
            _hashCode += getCostNostChild().hashCode();
        }
        if (getCostNostParent() != null) {
            _hashCode += getCostNostParent().hashCode();
        }
        if (getTipologieRost() != null) {
            _hashCode += getTipologieRost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipirostCostNostId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipirostCostNostId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costNostChild");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costNostChild"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCostNostRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costNostParent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costNostParent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCostNostRef"));
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
