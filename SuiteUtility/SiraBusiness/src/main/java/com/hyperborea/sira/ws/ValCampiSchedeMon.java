/**
 * ValCampiSchedeMon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ValCampiSchedeMon  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ValCampiSchedeMonPK id;

    private java.lang.String valBoolean;

    private java.util.Calendar valData;

    private java.lang.Double valFloat;

    private java.math.BigInteger valInteger;

    private java.lang.String valString;

    private java.lang.String valTextarea;

    private com.hyperborea.sira.ws.VoceDiVocabolario valVocabolario;

    private com.hyperborea.sira.ws.WsCampiSchedaMonitoraggioRef wsCampiSchedaMonitoraggioRef;

    public ValCampiSchedeMon() {
    }

    public ValCampiSchedeMon(
           com.hyperborea.sira.ws.ValCampiSchedeMonPK id,
           java.lang.String valBoolean,
           java.util.Calendar valData,
           java.lang.Double valFloat,
           java.math.BigInteger valInteger,
           java.lang.String valString,
           java.lang.String valTextarea,
           com.hyperborea.sira.ws.VoceDiVocabolario valVocabolario,
           com.hyperborea.sira.ws.WsCampiSchedaMonitoraggioRef wsCampiSchedaMonitoraggioRef) {
        this.id = id;
        this.valBoolean = valBoolean;
        this.valData = valData;
        this.valFloat = valFloat;
        this.valInteger = valInteger;
        this.valString = valString;
        this.valTextarea = valTextarea;
        this.valVocabolario = valVocabolario;
        this.wsCampiSchedaMonitoraggioRef = wsCampiSchedaMonitoraggioRef;
    }


    /**
     * Gets the id value for this ValCampiSchedeMon.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.ValCampiSchedeMonPK getId() {
        return id;
    }


    /**
     * Sets the id value for this ValCampiSchedeMon.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.ValCampiSchedeMonPK id) {
        this.id = id;
    }


    /**
     * Gets the valBoolean value for this ValCampiSchedeMon.
     * 
     * @return valBoolean
     */
    public java.lang.String getValBoolean() {
        return valBoolean;
    }


    /**
     * Sets the valBoolean value for this ValCampiSchedeMon.
     * 
     * @param valBoolean
     */
    public void setValBoolean(java.lang.String valBoolean) {
        this.valBoolean = valBoolean;
    }


    /**
     * Gets the valData value for this ValCampiSchedeMon.
     * 
     * @return valData
     */
    public java.util.Calendar getValData() {
        return valData;
    }


    /**
     * Sets the valData value for this ValCampiSchedeMon.
     * 
     * @param valData
     */
    public void setValData(java.util.Calendar valData) {
        this.valData = valData;
    }


    /**
     * Gets the valFloat value for this ValCampiSchedeMon.
     * 
     * @return valFloat
     */
    public java.lang.Double getValFloat() {
        return valFloat;
    }


    /**
     * Sets the valFloat value for this ValCampiSchedeMon.
     * 
     * @param valFloat
     */
    public void setValFloat(java.lang.Double valFloat) {
        this.valFloat = valFloat;
    }


    /**
     * Gets the valInteger value for this ValCampiSchedeMon.
     * 
     * @return valInteger
     */
    public java.math.BigInteger getValInteger() {
        return valInteger;
    }


    /**
     * Sets the valInteger value for this ValCampiSchedeMon.
     * 
     * @param valInteger
     */
    public void setValInteger(java.math.BigInteger valInteger) {
        this.valInteger = valInteger;
    }


    /**
     * Gets the valString value for this ValCampiSchedeMon.
     * 
     * @return valString
     */
    public java.lang.String getValString() {
        return valString;
    }


    /**
     * Sets the valString value for this ValCampiSchedeMon.
     * 
     * @param valString
     */
    public void setValString(java.lang.String valString) {
        this.valString = valString;
    }


    /**
     * Gets the valTextarea value for this ValCampiSchedeMon.
     * 
     * @return valTextarea
     */
    public java.lang.String getValTextarea() {
        return valTextarea;
    }


    /**
     * Sets the valTextarea value for this ValCampiSchedeMon.
     * 
     * @param valTextarea
     */
    public void setValTextarea(java.lang.String valTextarea) {
        this.valTextarea = valTextarea;
    }


    /**
     * Gets the valVocabolario value for this ValCampiSchedeMon.
     * 
     * @return valVocabolario
     */
    public com.hyperborea.sira.ws.VoceDiVocabolario getValVocabolario() {
        return valVocabolario;
    }


    /**
     * Sets the valVocabolario value for this ValCampiSchedeMon.
     * 
     * @param valVocabolario
     */
    public void setValVocabolario(com.hyperborea.sira.ws.VoceDiVocabolario valVocabolario) {
        this.valVocabolario = valVocabolario;
    }


    /**
     * Gets the wsCampiSchedaMonitoraggioRef value for this ValCampiSchedeMon.
     * 
     * @return wsCampiSchedaMonitoraggioRef
     */
    public com.hyperborea.sira.ws.WsCampiSchedaMonitoraggioRef getWsCampiSchedaMonitoraggioRef() {
        return wsCampiSchedaMonitoraggioRef;
    }


    /**
     * Sets the wsCampiSchedaMonitoraggioRef value for this ValCampiSchedeMon.
     * 
     * @param wsCampiSchedaMonitoraggioRef
     */
    public void setWsCampiSchedaMonitoraggioRef(com.hyperborea.sira.ws.WsCampiSchedaMonitoraggioRef wsCampiSchedaMonitoraggioRef) {
        this.wsCampiSchedaMonitoraggioRef = wsCampiSchedaMonitoraggioRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ValCampiSchedeMon)) return false;
        ValCampiSchedeMon other = (ValCampiSchedeMon) obj;
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
            ((this.valBoolean==null && other.getValBoolean()==null) || 
             (this.valBoolean!=null &&
              this.valBoolean.equals(other.getValBoolean()))) &&
            ((this.valData==null && other.getValData()==null) || 
             (this.valData!=null &&
              this.valData.equals(other.getValData()))) &&
            ((this.valFloat==null && other.getValFloat()==null) || 
             (this.valFloat!=null &&
              this.valFloat.equals(other.getValFloat()))) &&
            ((this.valInteger==null && other.getValInteger()==null) || 
             (this.valInteger!=null &&
              this.valInteger.equals(other.getValInteger()))) &&
            ((this.valString==null && other.getValString()==null) || 
             (this.valString!=null &&
              this.valString.equals(other.getValString()))) &&
            ((this.valTextarea==null && other.getValTextarea()==null) || 
             (this.valTextarea!=null &&
              this.valTextarea.equals(other.getValTextarea()))) &&
            ((this.valVocabolario==null && other.getValVocabolario()==null) || 
             (this.valVocabolario!=null &&
              this.valVocabolario.equals(other.getValVocabolario()))) &&
            ((this.wsCampiSchedaMonitoraggioRef==null && other.getWsCampiSchedaMonitoraggioRef()==null) || 
             (this.wsCampiSchedaMonitoraggioRef!=null &&
              this.wsCampiSchedaMonitoraggioRef.equals(other.getWsCampiSchedaMonitoraggioRef())));
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
        if (getValBoolean() != null) {
            _hashCode += getValBoolean().hashCode();
        }
        if (getValData() != null) {
            _hashCode += getValData().hashCode();
        }
        if (getValFloat() != null) {
            _hashCode += getValFloat().hashCode();
        }
        if (getValInteger() != null) {
            _hashCode += getValInteger().hashCode();
        }
        if (getValString() != null) {
            _hashCode += getValString().hashCode();
        }
        if (getValTextarea() != null) {
            _hashCode += getValTextarea().hashCode();
        }
        if (getValVocabolario() != null) {
            _hashCode += getValVocabolario().hashCode();
        }
        if (getWsCampiSchedaMonitoraggioRef() != null) {
            _hashCode += getWsCampiSchedaMonitoraggioRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ValCampiSchedeMon.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valCampiSchedeMon"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "valCampiSchedeMonPK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valBoolean");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valBoolean"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valFloat");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valFloat"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valInteger");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valInteger"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valString");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valString"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valTextarea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valTextarea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valVocabolario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valVocabolario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "voceDiVocabolario"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsCampiSchedaMonitoraggioRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsCampiSchedaMonitoraggioRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsCampiSchedaMonitoraggioRef"));
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
