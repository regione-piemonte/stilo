/**
 * DanniSubparticella.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DanniSubparticella  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idDanno;

    private com.hyperborea.sira.ws.VocEfCausaDannoSubpart vocEfCausaDannoSubpart;

    private com.hyperborea.sira.ws.VocEfDistribuzioneDanno vocEfDistribuzioneDanno;

    private com.hyperborea.sira.ws.VocEfGravitaDanno vocEfGravitaDanno;

    private com.hyperborea.sira.ws.VocEfSuperficieDanno vocEfSuperficieDanno;

    private com.hyperborea.sira.ws.VocEfTipologiaDanno vocEfTipologiaDanno;

    public DanniSubparticella() {
    }

    public DanniSubparticella(
           java.lang.Integer idDanno,
           com.hyperborea.sira.ws.VocEfCausaDannoSubpart vocEfCausaDannoSubpart,
           com.hyperborea.sira.ws.VocEfDistribuzioneDanno vocEfDistribuzioneDanno,
           com.hyperborea.sira.ws.VocEfGravitaDanno vocEfGravitaDanno,
           com.hyperborea.sira.ws.VocEfSuperficieDanno vocEfSuperficieDanno,
           com.hyperborea.sira.ws.VocEfTipologiaDanno vocEfTipologiaDanno) {
        this.idDanno = idDanno;
        this.vocEfCausaDannoSubpart = vocEfCausaDannoSubpart;
        this.vocEfDistribuzioneDanno = vocEfDistribuzioneDanno;
        this.vocEfGravitaDanno = vocEfGravitaDanno;
        this.vocEfSuperficieDanno = vocEfSuperficieDanno;
        this.vocEfTipologiaDanno = vocEfTipologiaDanno;
    }


    /**
     * Gets the idDanno value for this DanniSubparticella.
     * 
     * @return idDanno
     */
    public java.lang.Integer getIdDanno() {
        return idDanno;
    }


    /**
     * Sets the idDanno value for this DanniSubparticella.
     * 
     * @param idDanno
     */
    public void setIdDanno(java.lang.Integer idDanno) {
        this.idDanno = idDanno;
    }


    /**
     * Gets the vocEfCausaDannoSubpart value for this DanniSubparticella.
     * 
     * @return vocEfCausaDannoSubpart
     */
    public com.hyperborea.sira.ws.VocEfCausaDannoSubpart getVocEfCausaDannoSubpart() {
        return vocEfCausaDannoSubpart;
    }


    /**
     * Sets the vocEfCausaDannoSubpart value for this DanniSubparticella.
     * 
     * @param vocEfCausaDannoSubpart
     */
    public void setVocEfCausaDannoSubpart(com.hyperborea.sira.ws.VocEfCausaDannoSubpart vocEfCausaDannoSubpart) {
        this.vocEfCausaDannoSubpart = vocEfCausaDannoSubpart;
    }


    /**
     * Gets the vocEfDistribuzioneDanno value for this DanniSubparticella.
     * 
     * @return vocEfDistribuzioneDanno
     */
    public com.hyperborea.sira.ws.VocEfDistribuzioneDanno getVocEfDistribuzioneDanno() {
        return vocEfDistribuzioneDanno;
    }


    /**
     * Sets the vocEfDistribuzioneDanno value for this DanniSubparticella.
     * 
     * @param vocEfDistribuzioneDanno
     */
    public void setVocEfDistribuzioneDanno(com.hyperborea.sira.ws.VocEfDistribuzioneDanno vocEfDistribuzioneDanno) {
        this.vocEfDistribuzioneDanno = vocEfDistribuzioneDanno;
    }


    /**
     * Gets the vocEfGravitaDanno value for this DanniSubparticella.
     * 
     * @return vocEfGravitaDanno
     */
    public com.hyperborea.sira.ws.VocEfGravitaDanno getVocEfGravitaDanno() {
        return vocEfGravitaDanno;
    }


    /**
     * Sets the vocEfGravitaDanno value for this DanniSubparticella.
     * 
     * @param vocEfGravitaDanno
     */
    public void setVocEfGravitaDanno(com.hyperborea.sira.ws.VocEfGravitaDanno vocEfGravitaDanno) {
        this.vocEfGravitaDanno = vocEfGravitaDanno;
    }


    /**
     * Gets the vocEfSuperficieDanno value for this DanniSubparticella.
     * 
     * @return vocEfSuperficieDanno
     */
    public com.hyperborea.sira.ws.VocEfSuperficieDanno getVocEfSuperficieDanno() {
        return vocEfSuperficieDanno;
    }


    /**
     * Sets the vocEfSuperficieDanno value for this DanniSubparticella.
     * 
     * @param vocEfSuperficieDanno
     */
    public void setVocEfSuperficieDanno(com.hyperborea.sira.ws.VocEfSuperficieDanno vocEfSuperficieDanno) {
        this.vocEfSuperficieDanno = vocEfSuperficieDanno;
    }


    /**
     * Gets the vocEfTipologiaDanno value for this DanniSubparticella.
     * 
     * @return vocEfTipologiaDanno
     */
    public com.hyperborea.sira.ws.VocEfTipologiaDanno getVocEfTipologiaDanno() {
        return vocEfTipologiaDanno;
    }


    /**
     * Sets the vocEfTipologiaDanno value for this DanniSubparticella.
     * 
     * @param vocEfTipologiaDanno
     */
    public void setVocEfTipologiaDanno(com.hyperborea.sira.ws.VocEfTipologiaDanno vocEfTipologiaDanno) {
        this.vocEfTipologiaDanno = vocEfTipologiaDanno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DanniSubparticella)) return false;
        DanniSubparticella other = (DanniSubparticella) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idDanno==null && other.getIdDanno()==null) || 
             (this.idDanno!=null &&
              this.idDanno.equals(other.getIdDanno()))) &&
            ((this.vocEfCausaDannoSubpart==null && other.getVocEfCausaDannoSubpart()==null) || 
             (this.vocEfCausaDannoSubpart!=null &&
              this.vocEfCausaDannoSubpart.equals(other.getVocEfCausaDannoSubpart()))) &&
            ((this.vocEfDistribuzioneDanno==null && other.getVocEfDistribuzioneDanno()==null) || 
             (this.vocEfDistribuzioneDanno!=null &&
              this.vocEfDistribuzioneDanno.equals(other.getVocEfDistribuzioneDanno()))) &&
            ((this.vocEfGravitaDanno==null && other.getVocEfGravitaDanno()==null) || 
             (this.vocEfGravitaDanno!=null &&
              this.vocEfGravitaDanno.equals(other.getVocEfGravitaDanno()))) &&
            ((this.vocEfSuperficieDanno==null && other.getVocEfSuperficieDanno()==null) || 
             (this.vocEfSuperficieDanno!=null &&
              this.vocEfSuperficieDanno.equals(other.getVocEfSuperficieDanno()))) &&
            ((this.vocEfTipologiaDanno==null && other.getVocEfTipologiaDanno()==null) || 
             (this.vocEfTipologiaDanno!=null &&
              this.vocEfTipologiaDanno.equals(other.getVocEfTipologiaDanno())));
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
        if (getIdDanno() != null) {
            _hashCode += getIdDanno().hashCode();
        }
        if (getVocEfCausaDannoSubpart() != null) {
            _hashCode += getVocEfCausaDannoSubpart().hashCode();
        }
        if (getVocEfDistribuzioneDanno() != null) {
            _hashCode += getVocEfDistribuzioneDanno().hashCode();
        }
        if (getVocEfGravitaDanno() != null) {
            _hashCode += getVocEfGravitaDanno().hashCode();
        }
        if (getVocEfSuperficieDanno() != null) {
            _hashCode += getVocEfSuperficieDanno().hashCode();
        }
        if (getVocEfTipologiaDanno() != null) {
            _hashCode += getVocEfTipologiaDanno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DanniSubparticella.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "danniSubparticella"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDanno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDanno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfCausaDannoSubpart");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfCausaDannoSubpart"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfCausaDannoSubpart"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfDistribuzioneDanno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfDistribuzioneDanno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfDistribuzioneDanno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfGravitaDanno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfGravitaDanno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfGravitaDanno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfSuperficieDanno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfSuperficieDanno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfSuperficieDanno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipologiaDanno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipologiaDanno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaDanno"));
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
