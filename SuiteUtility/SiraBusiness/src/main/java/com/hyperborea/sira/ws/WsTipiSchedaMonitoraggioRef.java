/**
 * WsTipiSchedaMonitoraggioRef.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsTipiSchedaMonitoraggioRef  implements java.io.Serializable {
    private java.lang.Integer idTipoScheda;

    private java.lang.String templateTitolo;

    private java.lang.String titolo;

    private java.lang.String url;

    public WsTipiSchedaMonitoraggioRef() {
    }

    public WsTipiSchedaMonitoraggioRef(
           java.lang.Integer idTipoScheda,
           java.lang.String templateTitolo,
           java.lang.String titolo,
           java.lang.String url) {
           this.idTipoScheda = idTipoScheda;
           this.templateTitolo = templateTitolo;
           this.titolo = titolo;
           this.url = url;
    }


    /**
     * Gets the idTipoScheda value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @return idTipoScheda
     */
    public java.lang.Integer getIdTipoScheda() {
        return idTipoScheda;
    }


    /**
     * Sets the idTipoScheda value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @param idTipoScheda
     */
    public void setIdTipoScheda(java.lang.Integer idTipoScheda) {
        this.idTipoScheda = idTipoScheda;
    }


    /**
     * Gets the templateTitolo value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @return templateTitolo
     */
    public java.lang.String getTemplateTitolo() {
        return templateTitolo;
    }


    /**
     * Sets the templateTitolo value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @param templateTitolo
     */
    public void setTemplateTitolo(java.lang.String templateTitolo) {
        this.templateTitolo = templateTitolo;
    }


    /**
     * Gets the titolo value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @return titolo
     */
    public java.lang.String getTitolo() {
        return titolo;
    }


    /**
     * Sets the titolo value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @param titolo
     */
    public void setTitolo(java.lang.String titolo) {
        this.titolo = titolo;
    }


    /**
     * Gets the url value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this WsTipiSchedaMonitoraggioRef.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsTipiSchedaMonitoraggioRef)) return false;
        WsTipiSchedaMonitoraggioRef other = (WsTipiSchedaMonitoraggioRef) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idTipoScheda==null && other.getIdTipoScheda()==null) || 
             (this.idTipoScheda!=null &&
              this.idTipoScheda.equals(other.getIdTipoScheda()))) &&
            ((this.templateTitolo==null && other.getTemplateTitolo()==null) || 
             (this.templateTitolo!=null &&
              this.templateTitolo.equals(other.getTemplateTitolo()))) &&
            ((this.titolo==null && other.getTitolo()==null) || 
             (this.titolo!=null &&
              this.titolo.equals(other.getTitolo()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl())));
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
        if (getIdTipoScheda() != null) {
            _hashCode += getIdTipoScheda().hashCode();
        }
        if (getTemplateTitolo() != null) {
            _hashCode += getTemplateTitolo().hashCode();
        }
        if (getTitolo() != null) {
            _hashCode += getTitolo().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsTipiSchedaMonitoraggioRef.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioRef"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoScheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoScheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateTitolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "templateTitolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url"));
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
