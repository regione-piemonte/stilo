/**
 * CcostTronchiEd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostTronchiEd  implements java.io.Serializable {
    private java.lang.String codice;

    private java.lang.Float correnteMediana;

    private java.lang.Integer idCcost;

    private java.lang.Float lunghezza;

    private int progrLinea;

    private java.lang.Float tensioneEse;

    public CcostTronchiEd() {
    }

    public CcostTronchiEd(
           java.lang.String codice,
           java.lang.Float correnteMediana,
           java.lang.Integer idCcost,
           java.lang.Float lunghezza,
           int progrLinea,
           java.lang.Float tensioneEse) {
           this.codice = codice;
           this.correnteMediana = correnteMediana;
           this.idCcost = idCcost;
           this.lunghezza = lunghezza;
           this.progrLinea = progrLinea;
           this.tensioneEse = tensioneEse;
    }


    /**
     * Gets the codice value for this CcostTronchiEd.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostTronchiEd.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the correnteMediana value for this CcostTronchiEd.
     * 
     * @return correnteMediana
     */
    public java.lang.Float getCorrenteMediana() {
        return correnteMediana;
    }


    /**
     * Sets the correnteMediana value for this CcostTronchiEd.
     * 
     * @param correnteMediana
     */
    public void setCorrenteMediana(java.lang.Float correnteMediana) {
        this.correnteMediana = correnteMediana;
    }


    /**
     * Gets the idCcost value for this CcostTronchiEd.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostTronchiEd.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the lunghezza value for this CcostTronchiEd.
     * 
     * @return lunghezza
     */
    public java.lang.Float getLunghezza() {
        return lunghezza;
    }


    /**
     * Sets the lunghezza value for this CcostTronchiEd.
     * 
     * @param lunghezza
     */
    public void setLunghezza(java.lang.Float lunghezza) {
        this.lunghezza = lunghezza;
    }


    /**
     * Gets the progrLinea value for this CcostTronchiEd.
     * 
     * @return progrLinea
     */
    public int getProgrLinea() {
        return progrLinea;
    }


    /**
     * Sets the progrLinea value for this CcostTronchiEd.
     * 
     * @param progrLinea
     */
    public void setProgrLinea(int progrLinea) {
        this.progrLinea = progrLinea;
    }


    /**
     * Gets the tensioneEse value for this CcostTronchiEd.
     * 
     * @return tensioneEse
     */
    public java.lang.Float getTensioneEse() {
        return tensioneEse;
    }


    /**
     * Sets the tensioneEse value for this CcostTronchiEd.
     * 
     * @param tensioneEse
     */
    public void setTensioneEse(java.lang.Float tensioneEse) {
        this.tensioneEse = tensioneEse;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostTronchiEd)) return false;
        CcostTronchiEd other = (CcostTronchiEd) obj;
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
            ((this.correnteMediana==null && other.getCorrenteMediana()==null) || 
             (this.correnteMediana!=null &&
              this.correnteMediana.equals(other.getCorrenteMediana()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.lunghezza==null && other.getLunghezza()==null) || 
             (this.lunghezza!=null &&
              this.lunghezza.equals(other.getLunghezza()))) &&
            this.progrLinea == other.getProgrLinea() &&
            ((this.tensioneEse==null && other.getTensioneEse()==null) || 
             (this.tensioneEse!=null &&
              this.tensioneEse.equals(other.getTensioneEse())));
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
        if (getCorrenteMediana() != null) {
            _hashCode += getCorrenteMediana().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLunghezza() != null) {
            _hashCode += getLunghezza().hashCode();
        }
        _hashCode += getProgrLinea();
        if (getTensioneEse() != null) {
            _hashCode += getTensioneEse().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostTronchiEd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostTronchiEd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("correnteMediana");
        elemField.setXmlName(new javax.xml.namespace.QName("", "correnteMediana"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("lunghezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lunghezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("progrLinea");
        elemField.setXmlName(new javax.xml.namespace.QName("", "progrLinea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tensioneEse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tensioneEse"));
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
