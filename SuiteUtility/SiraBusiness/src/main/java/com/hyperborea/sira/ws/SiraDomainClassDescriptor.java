/**
 * SiraDomainClassDescriptor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SiraDomainClassDescriptor  implements java.io.Serializable {
    private java.lang.String className;

    private com.hyperborea.sira.ws.SiraDomainPropertyDescriptor[] propertyDescriptors;

    public SiraDomainClassDescriptor() {
    }

    public SiraDomainClassDescriptor(
           java.lang.String className,
           com.hyperborea.sira.ws.SiraDomainPropertyDescriptor[] propertyDescriptors) {
           this.className = className;
           this.propertyDescriptors = propertyDescriptors;
    }


    /**
     * Gets the className value for this SiraDomainClassDescriptor.
     * 
     * @return className
     */
    public java.lang.String getClassName() {
        return className;
    }


    /**
     * Sets the className value for this SiraDomainClassDescriptor.
     * 
     * @param className
     */
    public void setClassName(java.lang.String className) {
        this.className = className;
    }


    /**
     * Gets the propertyDescriptors value for this SiraDomainClassDescriptor.
     * 
     * @return propertyDescriptors
     */
    public com.hyperborea.sira.ws.SiraDomainPropertyDescriptor[] getPropertyDescriptors() {
        return propertyDescriptors;
    }


    /**
     * Sets the propertyDescriptors value for this SiraDomainClassDescriptor.
     * 
     * @param propertyDescriptors
     */
    public void setPropertyDescriptors(com.hyperborea.sira.ws.SiraDomainPropertyDescriptor[] propertyDescriptors) {
        this.propertyDescriptors = propertyDescriptors;
    }

    public com.hyperborea.sira.ws.SiraDomainPropertyDescriptor getPropertyDescriptors(int i) {
        return this.propertyDescriptors[i];
    }

    public void setPropertyDescriptors(int i, com.hyperborea.sira.ws.SiraDomainPropertyDescriptor _value) {
        this.propertyDescriptors[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SiraDomainClassDescriptor)) return false;
        SiraDomainClassDescriptor other = (SiraDomainClassDescriptor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.className==null && other.getClassName()==null) || 
             (this.className!=null &&
              this.className.equals(other.getClassName()))) &&
            ((this.propertyDescriptors==null && other.getPropertyDescriptors()==null) || 
             (this.propertyDescriptors!=null &&
              java.util.Arrays.equals(this.propertyDescriptors, other.getPropertyDescriptors())));
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
        if (getClassName() != null) {
            _hashCode += getClassName().hashCode();
        }
        if (getPropertyDescriptors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPropertyDescriptors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPropertyDescriptors(), i);
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
        new org.apache.axis.description.TypeDesc(SiraDomainClassDescriptor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "siraDomainClassDescriptor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("className");
        elemField.setXmlName(new javax.xml.namespace.QName("", "className"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("propertyDescriptors");
        elemField.setXmlName(new javax.xml.namespace.QName("", "propertyDescriptors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "siraDomainPropertyDescriptor"));
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
