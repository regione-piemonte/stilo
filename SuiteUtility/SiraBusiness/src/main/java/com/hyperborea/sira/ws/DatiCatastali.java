/**
 * DatiCatastali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DatiCatastali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ComuniItalia comuniItalia;

    private java.lang.String foglio;

    private java.lang.Integer idDatoCatastale;

    private java.lang.String particella;

    private java.lang.String subalterno;

    private java.lang.Float superficie;

    private com.hyperborea.sira.ws.VocTipoSuperficie tipoSuperficie;

    private java.lang.Integer wsRefId;

    public DatiCatastali() {
    }

    public DatiCatastali(
           com.hyperborea.sira.ws.ComuniItalia comuniItalia,
           java.lang.String foglio,
           java.lang.Integer idDatoCatastale,
           java.lang.String particella,
           java.lang.String subalterno,
           java.lang.Float superficie,
           com.hyperborea.sira.ws.VocTipoSuperficie tipoSuperficie,
           java.lang.Integer wsRefId) {
        this.comuniItalia = comuniItalia;
        this.foglio = foglio;
        this.idDatoCatastale = idDatoCatastale;
        this.particella = particella;
        this.subalterno = subalterno;
        this.superficie = superficie;
        this.tipoSuperficie = tipoSuperficie;
        this.wsRefId = wsRefId;
    }


    /**
     * Gets the comuniItalia value for this DatiCatastali.
     * 
     * @return comuniItalia
     */
    public com.hyperborea.sira.ws.ComuniItalia getComuniItalia() {
        return comuniItalia;
    }


    /**
     * Sets the comuniItalia value for this DatiCatastali.
     * 
     * @param comuniItalia
     */
    public void setComuniItalia(com.hyperborea.sira.ws.ComuniItalia comuniItalia) {
        this.comuniItalia = comuniItalia;
    }


    /**
     * Gets the foglio value for this DatiCatastali.
     * 
     * @return foglio
     */
    public java.lang.String getFoglio() {
        return foglio;
    }


    /**
     * Sets the foglio value for this DatiCatastali.
     * 
     * @param foglio
     */
    public void setFoglio(java.lang.String foglio) {
        this.foglio = foglio;
    }


    /**
     * Gets the idDatoCatastale value for this DatiCatastali.
     * 
     * @return idDatoCatastale
     */
    public java.lang.Integer getIdDatoCatastale() {
        return idDatoCatastale;
    }


    /**
     * Sets the idDatoCatastale value for this DatiCatastali.
     * 
     * @param idDatoCatastale
     */
    public void setIdDatoCatastale(java.lang.Integer idDatoCatastale) {
        this.idDatoCatastale = idDatoCatastale;
    }


    /**
     * Gets the particella value for this DatiCatastali.
     * 
     * @return particella
     */
    public java.lang.String getParticella() {
        return particella;
    }


    /**
     * Sets the particella value for this DatiCatastali.
     * 
     * @param particella
     */
    public void setParticella(java.lang.String particella) {
        this.particella = particella;
    }


    /**
     * Gets the subalterno value for this DatiCatastali.
     * 
     * @return subalterno
     */
    public java.lang.String getSubalterno() {
        return subalterno;
    }


    /**
     * Sets the subalterno value for this DatiCatastali.
     * 
     * @param subalterno
     */
    public void setSubalterno(java.lang.String subalterno) {
        this.subalterno = subalterno;
    }


    /**
     * Gets the superficie value for this DatiCatastali.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this DatiCatastali.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the tipoSuperficie value for this DatiCatastali.
     * 
     * @return tipoSuperficie
     */
    public com.hyperborea.sira.ws.VocTipoSuperficie getTipoSuperficie() {
        return tipoSuperficie;
    }


    /**
     * Sets the tipoSuperficie value for this DatiCatastali.
     * 
     * @param tipoSuperficie
     */
    public void setTipoSuperficie(com.hyperborea.sira.ws.VocTipoSuperficie tipoSuperficie) {
        this.tipoSuperficie = tipoSuperficie;
    }


    /**
     * Gets the wsRefId value for this DatiCatastali.
     * 
     * @return wsRefId
     */
    public java.lang.Integer getWsRefId() {
        return wsRefId;
    }


    /**
     * Sets the wsRefId value for this DatiCatastali.
     * 
     * @param wsRefId
     */
    public void setWsRefId(java.lang.Integer wsRefId) {
        this.wsRefId = wsRefId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiCatastali)) return false;
        DatiCatastali other = (DatiCatastali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.comuniItalia==null && other.getComuniItalia()==null) || 
             (this.comuniItalia!=null &&
              this.comuniItalia.equals(other.getComuniItalia()))) &&
            ((this.foglio==null && other.getFoglio()==null) || 
             (this.foglio!=null &&
              this.foglio.equals(other.getFoglio()))) &&
            ((this.idDatoCatastale==null && other.getIdDatoCatastale()==null) || 
             (this.idDatoCatastale!=null &&
              this.idDatoCatastale.equals(other.getIdDatoCatastale()))) &&
            ((this.particella==null && other.getParticella()==null) || 
             (this.particella!=null &&
              this.particella.equals(other.getParticella()))) &&
            ((this.subalterno==null && other.getSubalterno()==null) || 
             (this.subalterno!=null &&
              this.subalterno.equals(other.getSubalterno()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.tipoSuperficie==null && other.getTipoSuperficie()==null) || 
             (this.tipoSuperficie!=null &&
              this.tipoSuperficie.equals(other.getTipoSuperficie()))) &&
            ((this.wsRefId==null && other.getWsRefId()==null) || 
             (this.wsRefId!=null &&
              this.wsRefId.equals(other.getWsRefId())));
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
        if (getComuniItalia() != null) {
            _hashCode += getComuniItalia().hashCode();
        }
        if (getFoglio() != null) {
            _hashCode += getFoglio().hashCode();
        }
        if (getIdDatoCatastale() != null) {
            _hashCode += getIdDatoCatastale().hashCode();
        }
        if (getParticella() != null) {
            _hashCode += getParticella().hashCode();
        }
        if (getSubalterno() != null) {
            _hashCode += getSubalterno().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getTipoSuperficie() != null) {
            _hashCode += getTipoSuperficie().hashCode();
        }
        if (getWsRefId() != null) {
            _hashCode += getWsRefId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiCatastali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "datiCatastali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comuniItalia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comuniItalia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItalia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("foglio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "foglio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDatoCatastale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDatoCatastale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("particella");
        elemField.setXmlName(new javax.xml.namespace.QName("", "particella"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subalterno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "subalterno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSuperficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSuperficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoSuperficie"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsRefId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsRefId"));
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
