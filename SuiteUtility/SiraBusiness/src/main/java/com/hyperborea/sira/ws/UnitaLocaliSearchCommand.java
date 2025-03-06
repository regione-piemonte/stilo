/**
 * UnitaLocaliSearchCommand.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UnitaLocaliSearchCommand  implements java.io.Serializable {
    private java.lang.String denominazione;

    private java.lang.Integer[] idcatasti;

    private int idost;

    public UnitaLocaliSearchCommand() {
    }

    public UnitaLocaliSearchCommand(
           java.lang.String denominazione,
           java.lang.Integer[] idcatasti,
           int idost) {
           this.denominazione = denominazione;
           this.idcatasti = idcatasti;
           this.idost = idost;
    }


    /**
     * Gets the denominazione value for this UnitaLocaliSearchCommand.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this UnitaLocaliSearchCommand.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the idcatasti value for this UnitaLocaliSearchCommand.
     * 
     * @return idcatasti
     */
    public java.lang.Integer[] getIdcatasti() {
        return idcatasti;
    }


    /**
     * Sets the idcatasti value for this UnitaLocaliSearchCommand.
     * 
     * @param idcatasti
     */
    public void setIdcatasti(java.lang.Integer[] idcatasti) {
        this.idcatasti = idcatasti;
    }

    public java.lang.Integer getIdcatasti(int i) {
        return this.idcatasti[i];
    }

    public void setIdcatasti(int i, java.lang.Integer _value) {
        this.idcatasti[i] = _value;
    }


    /**
     * Gets the idost value for this UnitaLocaliSearchCommand.
     * 
     * @return idost
     */
    public int getIdost() {
        return idost;
    }


    /**
     * Sets the idost value for this UnitaLocaliSearchCommand.
     * 
     * @param idost
     */
    public void setIdost(int idost) {
        this.idost = idost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UnitaLocaliSearchCommand)) return false;
        UnitaLocaliSearchCommand other = (UnitaLocaliSearchCommand) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.idcatasti==null && other.getIdcatasti()==null) || 
             (this.idcatasti!=null &&
              java.util.Arrays.equals(this.idcatasti, other.getIdcatasti()))) &&
            this.idost == other.getIdost();
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
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getIdcatasti() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIdcatasti());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIdcatasti(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getIdost();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UnitaLocaliSearchCommand.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaLocaliSearchCommand"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idcatasti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idcatasti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
