/**
 * RelCampiTipiSchedaMonPK.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RelCampiTipiSchedaMonPK  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CampiSchedaMon campiSchedaMon;

    private com.hyperborea.sira.ws.TipiSchedaMonitoraggio tipiSchedaMonitoraggio;

    public RelCampiTipiSchedaMonPK() {
    }

    public RelCampiTipiSchedaMonPK(
           com.hyperborea.sira.ws.CampiSchedaMon campiSchedaMon,
           com.hyperborea.sira.ws.TipiSchedaMonitoraggio tipiSchedaMonitoraggio) {
           this.campiSchedaMon = campiSchedaMon;
           this.tipiSchedaMonitoraggio = tipiSchedaMonitoraggio;
    }


    /**
     * Gets the campiSchedaMon value for this RelCampiTipiSchedaMonPK.
     * 
     * @return campiSchedaMon
     */
    public com.hyperborea.sira.ws.CampiSchedaMon getCampiSchedaMon() {
        return campiSchedaMon;
    }


    /**
     * Sets the campiSchedaMon value for this RelCampiTipiSchedaMonPK.
     * 
     * @param campiSchedaMon
     */
    public void setCampiSchedaMon(com.hyperborea.sira.ws.CampiSchedaMon campiSchedaMon) {
        this.campiSchedaMon = campiSchedaMon;
    }


    /**
     * Gets the tipiSchedaMonitoraggio value for this RelCampiTipiSchedaMonPK.
     * 
     * @return tipiSchedaMonitoraggio
     */
    public com.hyperborea.sira.ws.TipiSchedaMonitoraggio getTipiSchedaMonitoraggio() {
        return tipiSchedaMonitoraggio;
    }


    /**
     * Sets the tipiSchedaMonitoraggio value for this RelCampiTipiSchedaMonPK.
     * 
     * @param tipiSchedaMonitoraggio
     */
    public void setTipiSchedaMonitoraggio(com.hyperborea.sira.ws.TipiSchedaMonitoraggio tipiSchedaMonitoraggio) {
        this.tipiSchedaMonitoraggio = tipiSchedaMonitoraggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RelCampiTipiSchedaMonPK)) return false;
        RelCampiTipiSchedaMonPK other = (RelCampiTipiSchedaMonPK) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.campiSchedaMon==null && other.getCampiSchedaMon()==null) || 
             (this.campiSchedaMon!=null &&
              this.campiSchedaMon.equals(other.getCampiSchedaMon()))) &&
            ((this.tipiSchedaMonitoraggio==null && other.getTipiSchedaMonitoraggio()==null) || 
             (this.tipiSchedaMonitoraggio!=null &&
              this.tipiSchedaMonitoraggio.equals(other.getTipiSchedaMonitoraggio())));
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
        if (getCampiSchedaMon() != null) {
            _hashCode += getCampiSchedaMon().hashCode();
        }
        if (getTipiSchedaMonitoraggio() != null) {
            _hashCode += getTipiSchedaMonitoraggio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RelCampiTipiSchedaMonPK.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relCampiTipiSchedaMonPK"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campiSchedaMon");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campiSchedaMon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campiSchedaMon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiSchedaMonitoraggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiSchedaMonitoraggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiSchedaMonitoraggio"));
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
