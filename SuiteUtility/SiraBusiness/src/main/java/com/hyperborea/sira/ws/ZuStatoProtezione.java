/**
 * ZuStatoProtezione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ZuStatoProtezione  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ZuStatoProtezioneId id;

    private java.lang.Float percCoperta;

    private com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi;

    public ZuStatoProtezione() {
    }

    public ZuStatoProtezione(
           com.hyperborea.sira.ws.ZuStatoProtezioneId id,
           java.lang.Float percCoperta,
           com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.id = id;
        this.percCoperta = percCoperta;
        this.riferimentiNormativi = riferimentiNormativi;
    }


    /**
     * Gets the id value for this ZuStatoProtezione.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.ZuStatoProtezioneId getId() {
        return id;
    }


    /**
     * Sets the id value for this ZuStatoProtezione.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.ZuStatoProtezioneId id) {
        this.id = id;
    }


    /**
     * Gets the percCoperta value for this ZuStatoProtezione.
     * 
     * @return percCoperta
     */
    public java.lang.Float getPercCoperta() {
        return percCoperta;
    }


    /**
     * Sets the percCoperta value for this ZuStatoProtezione.
     * 
     * @param percCoperta
     */
    public void setPercCoperta(java.lang.Float percCoperta) {
        this.percCoperta = percCoperta;
    }


    /**
     * Gets the riferimentiNormativi value for this ZuStatoProtezione.
     * 
     * @return riferimentiNormativi
     */
    public com.hyperborea.sira.ws.RiferimentiNormativi getRiferimentiNormativi() {
        return riferimentiNormativi;
    }


    /**
     * Sets the riferimentiNormativi value for this ZuStatoProtezione.
     * 
     * @param riferimentiNormativi
     */
    public void setRiferimentiNormativi(com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.riferimentiNormativi = riferimentiNormativi;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ZuStatoProtezione)) return false;
        ZuStatoProtezione other = (ZuStatoProtezione) obj;
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
            ((this.percCoperta==null && other.getPercCoperta()==null) || 
             (this.percCoperta!=null &&
              this.percCoperta.equals(other.getPercCoperta()))) &&
            ((this.riferimentiNormativi==null && other.getRiferimentiNormativi()==null) || 
             (this.riferimentiNormativi!=null &&
              this.riferimentiNormativi.equals(other.getRiferimentiNormativi())));
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
        if (getPercCoperta() != null) {
            _hashCode += getPercCoperta().hashCode();
        }
        if (getRiferimentiNormativi() != null) {
            _hashCode += getRiferimentiNormativi().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ZuStatoProtezione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuStatoProtezione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuStatoProtezioneId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percCoperta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "percCoperta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentiNormativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentiNormativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "riferimentiNormativi"));
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
