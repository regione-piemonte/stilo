/**
 * StatiOstId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class StatiOstId  implements java.io.Serializable {
    private java.util.Calendar dataInizio;

    private com.hyperborea.sira.ws.StatiOperativi statiOperativi;

    private com.hyperborea.sira.ws.WsOstRef wsOstRef;

    public StatiOstId() {
    }

    public StatiOstId(
           java.util.Calendar dataInizio,
           com.hyperborea.sira.ws.StatiOperativi statiOperativi,
           com.hyperborea.sira.ws.WsOstRef wsOstRef) {
           this.dataInizio = dataInizio;
           this.statiOperativi = statiOperativi;
           this.wsOstRef = wsOstRef;
    }


    /**
     * Gets the dataInizio value for this StatiOstId.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this StatiOstId.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the statiOperativi value for this StatiOstId.
     * 
     * @return statiOperativi
     */
    public com.hyperborea.sira.ws.StatiOperativi getStatiOperativi() {
        return statiOperativi;
    }


    /**
     * Sets the statiOperativi value for this StatiOstId.
     * 
     * @param statiOperativi
     */
    public void setStatiOperativi(com.hyperborea.sira.ws.StatiOperativi statiOperativi) {
        this.statiOperativi = statiOperativi;
    }


    /**
     * Gets the wsOstRef value for this StatiOstId.
     * 
     * @return wsOstRef
     */
    public com.hyperborea.sira.ws.WsOstRef getWsOstRef() {
        return wsOstRef;
    }


    /**
     * Sets the wsOstRef value for this StatiOstId.
     * 
     * @param wsOstRef
     */
    public void setWsOstRef(com.hyperborea.sira.ws.WsOstRef wsOstRef) {
        this.wsOstRef = wsOstRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatiOstId)) return false;
        StatiOstId other = (StatiOstId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.statiOperativi==null && other.getStatiOperativi()==null) || 
             (this.statiOperativi!=null &&
              this.statiOperativi.equals(other.getStatiOperativi()))) &&
            ((this.wsOstRef==null && other.getWsOstRef()==null) || 
             (this.wsOstRef!=null &&
              this.wsOstRef.equals(other.getWsOstRef())));
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
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getStatiOperativi() != null) {
            _hashCode += getStatiOperativi().hashCode();
        }
        if (getWsOstRef() != null) {
            _hashCode += getWsOstRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatiOstId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOstId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statiOperativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statiOperativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOperativi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsOstRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsOstRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
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
