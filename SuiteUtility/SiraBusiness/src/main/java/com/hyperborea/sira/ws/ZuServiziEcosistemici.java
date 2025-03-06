/**
 * ZuServiziEcosistemici.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ZuServiziEcosistemici  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float fruizione;

    private java.lang.Integer gradoPresenza;

    private com.hyperborea.sira.ws.ZuServiziEcosistemiciId id;

    public ZuServiziEcosistemici() {
    }

    public ZuServiziEcosistemici(
           java.lang.Float fruizione,
           java.lang.Integer gradoPresenza,
           com.hyperborea.sira.ws.ZuServiziEcosistemiciId id) {
        this.fruizione = fruizione;
        this.gradoPresenza = gradoPresenza;
        this.id = id;
    }


    /**
     * Gets the fruizione value for this ZuServiziEcosistemici.
     * 
     * @return fruizione
     */
    public java.lang.Float getFruizione() {
        return fruizione;
    }


    /**
     * Sets the fruizione value for this ZuServiziEcosistemici.
     * 
     * @param fruizione
     */
    public void setFruizione(java.lang.Float fruizione) {
        this.fruizione = fruizione;
    }


    /**
     * Gets the gradoPresenza value for this ZuServiziEcosistemici.
     * 
     * @return gradoPresenza
     */
    public java.lang.Integer getGradoPresenza() {
        return gradoPresenza;
    }


    /**
     * Sets the gradoPresenza value for this ZuServiziEcosistemici.
     * 
     * @param gradoPresenza
     */
    public void setGradoPresenza(java.lang.Integer gradoPresenza) {
        this.gradoPresenza = gradoPresenza;
    }


    /**
     * Gets the id value for this ZuServiziEcosistemici.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.ZuServiziEcosistemiciId getId() {
        return id;
    }


    /**
     * Sets the id value for this ZuServiziEcosistemici.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.ZuServiziEcosistemiciId id) {
        this.id = id;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZuServiziEcosistemici)) return false;
        ZuServiziEcosistemici other = (ZuServiziEcosistemici) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fruizione==null && other.getFruizione()==null) || 
             (this.fruizione!=null &&
              this.fruizione.equals(other.getFruizione()))) &&
            ((this.gradoPresenza==null && other.getGradoPresenza()==null) || 
             (this.gradoPresenza!=null &&
              this.gradoPresenza.equals(other.getGradoPresenza()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId())));
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
        if (getFruizione() != null) {
            _hashCode += getFruizione().hashCode();
        }
        if (getGradoPresenza() != null) {
            _hashCode += getGradoPresenza().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZuServiziEcosistemici.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuServiziEcosistemici"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fruizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fruizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gradoPresenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gradoPresenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuServiziEcosistemiciId"));
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
