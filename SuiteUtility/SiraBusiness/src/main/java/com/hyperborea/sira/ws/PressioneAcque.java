/**
 * PressioneAcque.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PressioneAcque  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.PressioneAcqueId id;

    private java.lang.Integer importanzaPressione;

    public PressioneAcque() {
    }

    public PressioneAcque(
           com.hyperborea.sira.ws.PressioneAcqueId id,
           java.lang.Integer importanzaPressione) {
        this.id = id;
        this.importanzaPressione = importanzaPressione;
    }


    /**
     * Gets the id value for this PressioneAcque.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.PressioneAcqueId getId() {
        return id;
    }


    /**
     * Sets the id value for this PressioneAcque.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.PressioneAcqueId id) {
        this.id = id;
    }


    /**
     * Gets the importanzaPressione value for this PressioneAcque.
     * 
     * @return importanzaPressione
     */
    public java.lang.Integer getImportanzaPressione() {
        return importanzaPressione;
    }


    /**
     * Sets the importanzaPressione value for this PressioneAcque.
     * 
     * @param importanzaPressione
     */
    public void setImportanzaPressione(java.lang.Integer importanzaPressione) {
        this.importanzaPressione = importanzaPressione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PressioneAcque)) return false;
        PressioneAcque other = (PressioneAcque) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.importanzaPressione==null && other.getImportanzaPressione()==null) || 
             (this.importanzaPressione!=null &&
              this.importanzaPressione.equals(other.getImportanzaPressione())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getImportanzaPressione() != null) {
            _hashCode += getImportanzaPressione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PressioneAcque.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pressioneAcque"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pressioneAcqueId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("importanzaPressione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "importanzaPressione"));
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
