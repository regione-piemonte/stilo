/**
 * SfOstId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class SfOstId  implements java.io.Serializable {
    private java.util.Calendar dataInizio;

    private com.hyperborea.sira.ws.FunzioniSf funzioniSf;

    private com.hyperborea.sira.ws.SoggettiFisici soggettiFisici;

    private com.hyperborea.sira.ws.WsOstRef wsOstRef;

    public SfOstId() {
    }

    public SfOstId(
           java.util.Calendar dataInizio,
           com.hyperborea.sira.ws.FunzioniSf funzioniSf,
           com.hyperborea.sira.ws.SoggettiFisici soggettiFisici,
           com.hyperborea.sira.ws.WsOstRef wsOstRef) {
           this.dataInizio = dataInizio;
           this.funzioniSf = funzioniSf;
           this.soggettiFisici = soggettiFisici;
           this.wsOstRef = wsOstRef;
    }


    /**
     * Gets the dataInizio value for this SfOstId.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this SfOstId.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the funzioniSf value for this SfOstId.
     * 
     * @return funzioniSf
     */
    public com.hyperborea.sira.ws.FunzioniSf getFunzioniSf() {
        return funzioniSf;
    }


    /**
     * Sets the funzioniSf value for this SfOstId.
     * 
     * @param funzioniSf
     */
    public void setFunzioniSf(com.hyperborea.sira.ws.FunzioniSf funzioniSf) {
        this.funzioniSf = funzioniSf;
    }


    /**
     * Gets the soggettiFisici value for this SfOstId.
     * 
     * @return soggettiFisici
     */
    public com.hyperborea.sira.ws.SoggettiFisici getSoggettiFisici() {
        return soggettiFisici;
    }


    /**
     * Sets the soggettiFisici value for this SfOstId.
     * 
     * @param soggettiFisici
     */
    public void setSoggettiFisici(com.hyperborea.sira.ws.SoggettiFisici soggettiFisici) {
        this.soggettiFisici = soggettiFisici;
    }


    /**
     * Gets the wsOstRef value for this SfOstId.
     * 
     * @return wsOstRef
     */
    public com.hyperborea.sira.ws.WsOstRef getWsOstRef() {
        return wsOstRef;
    }


    /**
     * Sets the wsOstRef value for this SfOstId.
     * 
     * @param wsOstRef
     */
    public void setWsOstRef(com.hyperborea.sira.ws.WsOstRef wsOstRef) {
        this.wsOstRef = wsOstRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SfOstId)) return false;
        SfOstId other = (SfOstId) obj;
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
            ((this.funzioniSf==null && other.getFunzioniSf()==null) || 
             (this.funzioniSf!=null &&
              this.funzioniSf.equals(other.getFunzioniSf()))) &&
            ((this.soggettiFisici==null && other.getSoggettiFisici()==null) || 
             (this.soggettiFisici!=null &&
              this.soggettiFisici.equals(other.getSoggettiFisici()))) &&
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
        if (getFunzioniSf() != null) {
            _hashCode += getFunzioniSf().hashCode();
        }
        if (getSoggettiFisici() != null) {
            _hashCode += getSoggettiFisici().hashCode();
        }
        if (getWsOstRef() != null) {
            _hashCode += getWsOstRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SfOstId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sfOstId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("funzioniSf");
        elemField.setXmlName(new javax.xml.namespace.QName("", "funzioniSf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "funzioniSf"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("soggettiFisici");
        elemField.setXmlName(new javax.xml.namespace.QName("", "soggettiFisici"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "soggettiFisici"));
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
