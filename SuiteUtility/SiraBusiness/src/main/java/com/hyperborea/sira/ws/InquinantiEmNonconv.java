/**
 * InquinantiEmNonconv.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class InquinantiEmNonconv  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float flussoAnnuo;

    private com.hyperborea.sira.ws.InquinantiEmNonconvId id;

    private java.lang.String modAcquisizioneMassa;

    public InquinantiEmNonconv() {
    }

    public InquinantiEmNonconv(
           java.lang.Float flussoAnnuo,
           com.hyperborea.sira.ws.InquinantiEmNonconvId id,
           java.lang.String modAcquisizioneMassa) {
        this.flussoAnnuo = flussoAnnuo;
        this.id = id;
        this.modAcquisizioneMassa = modAcquisizioneMassa;
    }


    /**
     * Gets the flussoAnnuo value for this InquinantiEmNonconv.
     * 
     * @return flussoAnnuo
     */
    public java.lang.Float getFlussoAnnuo() {
        return flussoAnnuo;
    }


    /**
     * Sets the flussoAnnuo value for this InquinantiEmNonconv.
     * 
     * @param flussoAnnuo
     */
    public void setFlussoAnnuo(java.lang.Float flussoAnnuo) {
        this.flussoAnnuo = flussoAnnuo;
    }


    /**
     * Gets the id value for this InquinantiEmNonconv.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.InquinantiEmNonconvId getId() {
        return id;
    }


    /**
     * Sets the id value for this InquinantiEmNonconv.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.InquinantiEmNonconvId id) {
        this.id = id;
    }


    /**
     * Gets the modAcquisizioneMassa value for this InquinantiEmNonconv.
     * 
     * @return modAcquisizioneMassa
     */
    public java.lang.String getModAcquisizioneMassa() {
        return modAcquisizioneMassa;
    }


    /**
     * Sets the modAcquisizioneMassa value for this InquinantiEmNonconv.
     * 
     * @param modAcquisizioneMassa
     */
    public void setModAcquisizioneMassa(java.lang.String modAcquisizioneMassa) {
        this.modAcquisizioneMassa = modAcquisizioneMassa;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InquinantiEmNonconv)) return false;
        InquinantiEmNonconv other = (InquinantiEmNonconv) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.flussoAnnuo==null && other.getFlussoAnnuo()==null) || 
             (this.flussoAnnuo!=null &&
              this.flussoAnnuo.equals(other.getFlussoAnnuo()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.modAcquisizioneMassa==null && other.getModAcquisizioneMassa()==null) || 
             (this.modAcquisizioneMassa!=null &&
              this.modAcquisizioneMassa.equals(other.getModAcquisizioneMassa())));
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
        if (getFlussoAnnuo() != null) {
            _hashCode += getFlussoAnnuo().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getModAcquisizioneMassa() != null) {
            _hashCode += getModAcquisizioneMassa().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InquinantiEmNonconv.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmNonconv"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flussoAnnuo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flussoAnnuo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "inquinantiEmNonconvId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modAcquisizioneMassa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modAcquisizioneMassa"));
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
