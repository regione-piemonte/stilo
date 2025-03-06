/**
 * VocabularyDescription.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VocabularyDescription  implements java.io.Serializable {
    private java.lang.String className;

    private java.lang.String descriptionProperty;

    private com.hyperborea.sira.ws.WsPropertyDescriptor[] extras;

    private java.lang.String idProperty;

    private com.hyperborea.sira.ws.VocabularyItem[] items;

    private java.lang.String vocProperty;

    public VocabularyDescription() {
    }

    public VocabularyDescription(
           java.lang.String className,
           java.lang.String descriptionProperty,
           com.hyperborea.sira.ws.WsPropertyDescriptor[] extras,
           java.lang.String idProperty,
           com.hyperborea.sira.ws.VocabularyItem[] items,
           java.lang.String vocProperty) {
           this.className = className;
           this.descriptionProperty = descriptionProperty;
           this.extras = extras;
           this.idProperty = idProperty;
           this.items = items;
           this.vocProperty = vocProperty;
    }


    /**
     * Gets the className value for this VocabularyDescription.
     * 
     * @return className
     */
    public java.lang.String getClassName() {
        return className;
    }


    /**
     * Sets the className value for this VocabularyDescription.
     * 
     * @param className
     */
    public void setClassName(java.lang.String className) {
        this.className = className;
    }


    /**
     * Gets the descriptionProperty value for this VocabularyDescription.
     * 
     * @return descriptionProperty
     */
    public java.lang.String getDescriptionProperty() {
        return descriptionProperty;
    }


    /**
     * Sets the descriptionProperty value for this VocabularyDescription.
     * 
     * @param descriptionProperty
     */
    public void setDescriptionProperty(java.lang.String descriptionProperty) {
        this.descriptionProperty = descriptionProperty;
    }


    /**
     * Gets the extras value for this VocabularyDescription.
     * 
     * @return extras
     */
    public com.hyperborea.sira.ws.WsPropertyDescriptor[] getExtras() {
        return extras;
    }


    /**
     * Sets the extras value for this VocabularyDescription.
     * 
     * @param extras
     */
    public void setExtras(com.hyperborea.sira.ws.WsPropertyDescriptor[] extras) {
        this.extras = extras;
    }

    public com.hyperborea.sira.ws.WsPropertyDescriptor getExtras(int i) {
        return this.extras[i];
    }

    public void setExtras(int i, com.hyperborea.sira.ws.WsPropertyDescriptor _value) {
        this.extras[i] = _value;
    }


    /**
     * Gets the idProperty value for this VocabularyDescription.
     * 
     * @return idProperty
     */
    public java.lang.String getIdProperty() {
        return idProperty;
    }


    /**
     * Sets the idProperty value for this VocabularyDescription.
     * 
     * @param idProperty
     */
    public void setIdProperty(java.lang.String idProperty) {
        this.idProperty = idProperty;
    }


    /**
     * Gets the items value for this VocabularyDescription.
     * 
     * @return items
     */
    public com.hyperborea.sira.ws.VocabularyItem[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this VocabularyDescription.
     * 
     * @param items
     */
    public void setItems(com.hyperborea.sira.ws.VocabularyItem[] items) {
        this.items = items;
    }

    public com.hyperborea.sira.ws.VocabularyItem getItems(int i) {
        return this.items[i];
    }

    public void setItems(int i, com.hyperborea.sira.ws.VocabularyItem _value) {
        this.items[i] = _value;
    }


    /**
     * Gets the vocProperty value for this VocabularyDescription.
     * 
     * @return vocProperty
     */
    public java.lang.String getVocProperty() {
        return vocProperty;
    }


    /**
     * Sets the vocProperty value for this VocabularyDescription.
     * 
     * @param vocProperty
     */
    public void setVocProperty(java.lang.String vocProperty) {
        this.vocProperty = vocProperty;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VocabularyDescription)) return false;
        VocabularyDescription other = (VocabularyDescription) obj;
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
            ((this.descriptionProperty==null && other.getDescriptionProperty()==null) || 
             (this.descriptionProperty!=null &&
              this.descriptionProperty.equals(other.getDescriptionProperty()))) &&
            ((this.extras==null && other.getExtras()==null) || 
             (this.extras!=null &&
              java.util.Arrays.equals(this.extras, other.getExtras()))) &&
            ((this.idProperty==null && other.getIdProperty()==null) || 
             (this.idProperty!=null &&
              this.idProperty.equals(other.getIdProperty()))) &&
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems()))) &&
            ((this.vocProperty==null && other.getVocProperty()==null) || 
             (this.vocProperty!=null &&
              this.vocProperty.equals(other.getVocProperty())));
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
        if (getDescriptionProperty() != null) {
            _hashCode += getDescriptionProperty().hashCode();
        }
        if (getExtras() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExtras());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExtras(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdProperty() != null) {
            _hashCode += getIdProperty().hashCode();
        }
        if (getItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVocProperty() != null) {
            _hashCode += getVocProperty().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VocabularyDescription.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyDescription"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("className");
        elemField.setXmlName(new javax.xml.namespace.QName("", "className"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionProperty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descriptionProperty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extras");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extras"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsPropertyDescriptor"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProperty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProperty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("items");
        elemField.setXmlName(new javax.xml.namespace.QName("", "items"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyItem"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocProperty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocProperty"));
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
