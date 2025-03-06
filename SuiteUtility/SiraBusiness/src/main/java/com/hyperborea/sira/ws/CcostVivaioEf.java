/**
 * CcostVivaioEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostVivaioEf  implements java.io.Serializable {
    private java.lang.Integer certificazione;

    private java.lang.String codice;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.PianteVivaioEf[] pianteVivaioEfs;

    private java.lang.String servizioTerritoriale;

    private java.lang.Float superficie;

    public CcostVivaioEf() {
    }

    public CcostVivaioEf(
           java.lang.Integer certificazione,
           java.lang.String codice,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.PianteVivaioEf[] pianteVivaioEfs,
           java.lang.String servizioTerritoriale,
           java.lang.Float superficie) {
           this.certificazione = certificazione;
           this.codice = codice;
           this.idCcost = idCcost;
           this.pianteVivaioEfs = pianteVivaioEfs;
           this.servizioTerritoriale = servizioTerritoriale;
           this.superficie = superficie;
    }


    /**
     * Gets the certificazione value for this CcostVivaioEf.
     * 
     * @return certificazione
     */
    public java.lang.Integer getCertificazione() {
        return certificazione;
    }


    /**
     * Sets the certificazione value for this CcostVivaioEf.
     * 
     * @param certificazione
     */
    public void setCertificazione(java.lang.Integer certificazione) {
        this.certificazione = certificazione;
    }


    /**
     * Gets the codice value for this CcostVivaioEf.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostVivaioEf.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the idCcost value for this CcostVivaioEf.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostVivaioEf.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the pianteVivaioEfs value for this CcostVivaioEf.
     * 
     * @return pianteVivaioEfs
     */
    public com.hyperborea.sira.ws.PianteVivaioEf[] getPianteVivaioEfs() {
        return pianteVivaioEfs;
    }


    /**
     * Sets the pianteVivaioEfs value for this CcostVivaioEf.
     * 
     * @param pianteVivaioEfs
     */
    public void setPianteVivaioEfs(com.hyperborea.sira.ws.PianteVivaioEf[] pianteVivaioEfs) {
        this.pianteVivaioEfs = pianteVivaioEfs;
    }

    public com.hyperborea.sira.ws.PianteVivaioEf getPianteVivaioEfs(int i) {
        return this.pianteVivaioEfs[i];
    }

    public void setPianteVivaioEfs(int i, com.hyperborea.sira.ws.PianteVivaioEf _value) {
        this.pianteVivaioEfs[i] = _value;
    }


    /**
     * Gets the servizioTerritoriale value for this CcostVivaioEf.
     * 
     * @return servizioTerritoriale
     */
    public java.lang.String getServizioTerritoriale() {
        return servizioTerritoriale;
    }


    /**
     * Sets the servizioTerritoriale value for this CcostVivaioEf.
     * 
     * @param servizioTerritoriale
     */
    public void setServizioTerritoriale(java.lang.String servizioTerritoriale) {
        this.servizioTerritoriale = servizioTerritoriale;
    }


    /**
     * Gets the superficie value for this CcostVivaioEf.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostVivaioEf.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostVivaioEf)) return false;
        CcostVivaioEf other = (CcostVivaioEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.certificazione==null && other.getCertificazione()==null) || 
             (this.certificazione!=null &&
              this.certificazione.equals(other.getCertificazione()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.pianteVivaioEfs==null && other.getPianteVivaioEfs()==null) || 
             (this.pianteVivaioEfs!=null &&
              java.util.Arrays.equals(this.pianteVivaioEfs, other.getPianteVivaioEfs()))) &&
            ((this.servizioTerritoriale==null && other.getServizioTerritoriale()==null) || 
             (this.servizioTerritoriale!=null &&
              this.servizioTerritoriale.equals(other.getServizioTerritoriale()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie())));
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
        if (getCertificazione() != null) {
            _hashCode += getCertificazione().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getPianteVivaioEfs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPianteVivaioEfs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPianteVivaioEfs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getServizioTerritoriale() != null) {
            _hashCode += getServizioTerritoriale().hashCode();
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostVivaioEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostVivaioEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("pianteVivaioEfs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pianteVivaioEfs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "pianteVivaioEf"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servizioTerritoriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servizioTerritoriale"));
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
