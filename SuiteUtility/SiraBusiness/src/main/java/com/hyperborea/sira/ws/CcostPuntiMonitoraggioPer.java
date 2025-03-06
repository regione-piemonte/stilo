/**
 * CcostPuntiMonitoraggioPer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostPuntiMonitoraggioPer  implements java.io.Serializable {
    private java.lang.String frequenza;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPer[] monitoraggioCcostPuntiMonitoraggioPers;

    public CcostPuntiMonitoraggioPer() {
    }

    public CcostPuntiMonitoraggioPer(
           java.lang.String frequenza,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPer[] monitoraggioCcostPuntiMonitoraggioPers) {
           this.frequenza = frequenza;
           this.idCcost = idCcost;
           this.monitoraggioCcostPuntiMonitoraggioPers = monitoraggioCcostPuntiMonitoraggioPers;
    }


    /**
     * Gets the frequenza value for this CcostPuntiMonitoraggioPer.
     * 
     * @return frequenza
     */
    public java.lang.String getFrequenza() {
        return frequenza;
    }


    /**
     * Sets the frequenza value for this CcostPuntiMonitoraggioPer.
     * 
     * @param frequenza
     */
    public void setFrequenza(java.lang.String frequenza) {
        this.frequenza = frequenza;
    }


    /**
     * Gets the idCcost value for this CcostPuntiMonitoraggioPer.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostPuntiMonitoraggioPer.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the monitoraggioCcostPuntiMonitoraggioPers value for this CcostPuntiMonitoraggioPer.
     * 
     * @return monitoraggioCcostPuntiMonitoraggioPers
     */
    public com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPer[] getMonitoraggioCcostPuntiMonitoraggioPers() {
        return monitoraggioCcostPuntiMonitoraggioPers;
    }


    /**
     * Sets the monitoraggioCcostPuntiMonitoraggioPers value for this CcostPuntiMonitoraggioPer.
     * 
     * @param monitoraggioCcostPuntiMonitoraggioPers
     */
    public void setMonitoraggioCcostPuntiMonitoraggioPers(com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPer[] monitoraggioCcostPuntiMonitoraggioPers) {
        this.monitoraggioCcostPuntiMonitoraggioPers = monitoraggioCcostPuntiMonitoraggioPers;
    }

    public com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPer getMonitoraggioCcostPuntiMonitoraggioPers(int i) {
        return this.monitoraggioCcostPuntiMonitoraggioPers[i];
    }

    public void setMonitoraggioCcostPuntiMonitoraggioPers(int i, com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioPer _value) {
        this.monitoraggioCcostPuntiMonitoraggioPers[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostPuntiMonitoraggioPer)) return false;
        CcostPuntiMonitoraggioPer other = (CcostPuntiMonitoraggioPer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.frequenza==null && other.getFrequenza()==null) || 
             (this.frequenza!=null &&
              this.frequenza.equals(other.getFrequenza()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.monitoraggioCcostPuntiMonitoraggioPers==null && other.getMonitoraggioCcostPuntiMonitoraggioPers()==null) || 
             (this.monitoraggioCcostPuntiMonitoraggioPers!=null &&
              java.util.Arrays.equals(this.monitoraggioCcostPuntiMonitoraggioPers, other.getMonitoraggioCcostPuntiMonitoraggioPers())));
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
        if (getFrequenza() != null) {
            _hashCode += getFrequenza().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getMonitoraggioCcostPuntiMonitoraggioPers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMonitoraggioCcostPuntiMonitoraggioPers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMonitoraggioCcostPuntiMonitoraggioPers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostPuntiMonitoraggioPer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMonitoraggioPer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("frequenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "frequenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monitoraggioCcostPuntiMonitoraggioPers");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monitoraggioCcostPuntiMonitoraggioPers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggioCcostPuntiMonitoraggioPer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
