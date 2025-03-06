/**
 * MinieraMinerali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MinieraMinerali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String denominazione;

    private java.lang.Integer idMinerale;

    private com.hyperborea.sira.ws.MinieraMineraliProduzione[] minieraMineraliProduziones;

    public MinieraMinerali() {
    }

    public MinieraMinerali(
           java.lang.String denominazione,
           java.lang.Integer idMinerale,
           com.hyperborea.sira.ws.MinieraMineraliProduzione[] minieraMineraliProduziones) {
        this.denominazione = denominazione;
        this.idMinerale = idMinerale;
        this.minieraMineraliProduziones = minieraMineraliProduziones;
    }


    /**
     * Gets the denominazione value for this MinieraMinerali.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this MinieraMinerali.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the idMinerale value for this MinieraMinerali.
     * 
     * @return idMinerale
     */
    public java.lang.Integer getIdMinerale() {
        return idMinerale;
    }


    /**
     * Sets the idMinerale value for this MinieraMinerali.
     * 
     * @param idMinerale
     */
    public void setIdMinerale(java.lang.Integer idMinerale) {
        this.idMinerale = idMinerale;
    }


    /**
     * Gets the minieraMineraliProduziones value for this MinieraMinerali.
     * 
     * @return minieraMineraliProduziones
     */
    public com.hyperborea.sira.ws.MinieraMineraliProduzione[] getMinieraMineraliProduziones() {
        return minieraMineraliProduziones;
    }


    /**
     * Sets the minieraMineraliProduziones value for this MinieraMinerali.
     * 
     * @param minieraMineraliProduziones
     */
    public void setMinieraMineraliProduziones(com.hyperborea.sira.ws.MinieraMineraliProduzione[] minieraMineraliProduziones) {
        this.minieraMineraliProduziones = minieraMineraliProduziones;
    }

    public com.hyperborea.sira.ws.MinieraMineraliProduzione getMinieraMineraliProduziones(int i) {
        return this.minieraMineraliProduziones[i];
    }

    public void setMinieraMineraliProduziones(int i, com.hyperborea.sira.ws.MinieraMineraliProduzione _value) {
        this.minieraMineraliProduziones[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MinieraMinerali)) return false;
        MinieraMinerali other = (MinieraMinerali) obj;
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
            ((this.idMinerale==null && other.getIdMinerale()==null) || 
             (this.idMinerale!=null &&
              this.idMinerale.equals(other.getIdMinerale()))) &&
            ((this.minieraMineraliProduziones==null && other.getMinieraMineraliProduziones()==null) || 
             (this.minieraMineraliProduziones!=null &&
              java.util.Arrays.equals(this.minieraMineraliProduziones, other.getMinieraMineraliProduziones())));
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
        if (getIdMinerale() != null) {
            _hashCode += getIdMinerale().hashCode();
        }
        if (getMinieraMineraliProduziones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMinieraMineraliProduziones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMinieraMineraliProduziones(), i);
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
        new org.apache.axis.description.TypeDesc(MinieraMinerali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraMinerali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMinerale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMinerale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minieraMineraliProduziones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minieraMineraliProduziones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraMineraliProduzione"));
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
