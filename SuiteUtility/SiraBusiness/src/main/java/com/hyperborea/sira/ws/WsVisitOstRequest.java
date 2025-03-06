/**
 * WsVisitOstRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsVisitOstRequest  implements java.io.Serializable {
    private java.util.Calendar date;

    private java.lang.Integer idOst;

    private java.lang.Integer maxDepth;

    public WsVisitOstRequest() {
    }

    public WsVisitOstRequest(
           java.util.Calendar date,
           java.lang.Integer idOst,
           java.lang.Integer maxDepth) {
           this.date = date;
           this.idOst = idOst;
           this.maxDepth = maxDepth;
    }


    /**
     * Gets the date value for this WsVisitOstRequest.
     * 
     * @return date
     */
    public java.util.Calendar getDate() {
        return date;
    }


    /**
     * Sets the date value for this WsVisitOstRequest.
     * 
     * @param date
     */
    public void setDate(java.util.Calendar date) {
        this.date = date;
    }


    /**
     * Gets the idOst value for this WsVisitOstRequest.
     * 
     * @return idOst
     */
    public java.lang.Integer getIdOst() {
        return idOst;
    }


    /**
     * Sets the idOst value for this WsVisitOstRequest.
     * 
     * @param idOst
     */
    public void setIdOst(java.lang.Integer idOst) {
        this.idOst = idOst;
    }


    /**
     * Gets the maxDepth value for this WsVisitOstRequest.
     * 
     * @return maxDepth
     */
    public java.lang.Integer getMaxDepth() {
        return maxDepth;
    }


    /**
     * Sets the maxDepth value for this WsVisitOstRequest.
     * 
     * @param maxDepth
     */
    public void setMaxDepth(java.lang.Integer maxDepth) {
        this.maxDepth = maxDepth;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsVisitOstRequest)) return false;
        WsVisitOstRequest other = (WsVisitOstRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.date==null && other.getDate()==null) || 
             (this.date!=null &&
              this.date.equals(other.getDate()))) &&
            ((this.idOst==null && other.getIdOst()==null) || 
             (this.idOst!=null &&
              this.idOst.equals(other.getIdOst()))) &&
            ((this.maxDepth==null && other.getMaxDepth()==null) || 
             (this.maxDepth!=null &&
              this.maxDepth.equals(other.getMaxDepth())));
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
        if (getDate() != null) {
            _hashCode += getDate().hashCode();
        }
        if (getIdOst() != null) {
            _hashCode += getIdOst().hashCode();
        }
        if (getMaxDepth() != null) {
            _hashCode += getMaxDepth().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsVisitOstRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsVisitOstRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxDepth");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxDepth"));
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
