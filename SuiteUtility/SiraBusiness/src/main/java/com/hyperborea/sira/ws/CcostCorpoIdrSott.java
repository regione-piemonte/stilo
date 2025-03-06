/**
 * CcostCorpoIdrSott.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostCorpoIdrSott  implements java.io.Serializable {
    private java.lang.String codice;

    private com.hyperborea.sira.ws.VocGradoQualita gradoVulnerabilita;

    private java.lang.Integer idCcost;

    private java.lang.Float quotaLetto;

    private java.lang.Float quotaTetto;

    public CcostCorpoIdrSott() {
    }

    public CcostCorpoIdrSott(
           java.lang.String codice,
           com.hyperborea.sira.ws.VocGradoQualita gradoVulnerabilita,
           java.lang.Integer idCcost,
           java.lang.Float quotaLetto,
           java.lang.Float quotaTetto) {
           this.codice = codice;
           this.gradoVulnerabilita = gradoVulnerabilita;
           this.idCcost = idCcost;
           this.quotaLetto = quotaLetto;
           this.quotaTetto = quotaTetto;
    }


    /**
     * Gets the codice value for this CcostCorpoIdrSott.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostCorpoIdrSott.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the gradoVulnerabilita value for this CcostCorpoIdrSott.
     * 
     * @return gradoVulnerabilita
     */
    public com.hyperborea.sira.ws.VocGradoQualita getGradoVulnerabilita() {
        return gradoVulnerabilita;
    }


    /**
     * Sets the gradoVulnerabilita value for this CcostCorpoIdrSott.
     * 
     * @param gradoVulnerabilita
     */
    public void setGradoVulnerabilita(com.hyperborea.sira.ws.VocGradoQualita gradoVulnerabilita) {
        this.gradoVulnerabilita = gradoVulnerabilita;
    }


    /**
     * Gets the idCcost value for this CcostCorpoIdrSott.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostCorpoIdrSott.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the quotaLetto value for this CcostCorpoIdrSott.
     * 
     * @return quotaLetto
     */
    public java.lang.Float getQuotaLetto() {
        return quotaLetto;
    }


    /**
     * Sets the quotaLetto value for this CcostCorpoIdrSott.
     * 
     * @param quotaLetto
     */
    public void setQuotaLetto(java.lang.Float quotaLetto) {
        this.quotaLetto = quotaLetto;
    }


    /**
     * Gets the quotaTetto value for this CcostCorpoIdrSott.
     * 
     * @return quotaTetto
     */
    public java.lang.Float getQuotaTetto() {
        return quotaTetto;
    }


    /**
     * Sets the quotaTetto value for this CcostCorpoIdrSott.
     * 
     * @param quotaTetto
     */
    public void setQuotaTetto(java.lang.Float quotaTetto) {
        this.quotaTetto = quotaTetto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostCorpoIdrSott)) return false;
        CcostCorpoIdrSott other = (CcostCorpoIdrSott) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.gradoVulnerabilita==null && other.getGradoVulnerabilita()==null) || 
             (this.gradoVulnerabilita!=null &&
              this.gradoVulnerabilita.equals(other.getGradoVulnerabilita()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.quotaLetto==null && other.getQuotaLetto()==null) || 
             (this.quotaLetto!=null &&
              this.quotaLetto.equals(other.getQuotaLetto()))) &&
            ((this.quotaTetto==null && other.getQuotaTetto()==null) || 
             (this.quotaTetto!=null &&
              this.quotaTetto.equals(other.getQuotaTetto())));
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getGradoVulnerabilita() != null) {
            _hashCode += getGradoVulnerabilita().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getQuotaLetto() != null) {
            _hashCode += getQuotaLetto().hashCode();
        }
        if (getQuotaTetto() != null) {
            _hashCode += getQuotaTetto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostCorpoIdrSott.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCorpoIdrSott"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gradoVulnerabilita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gradoVulnerabilita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocGradoQualita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaLetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotaLetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quotaTetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotaTetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
