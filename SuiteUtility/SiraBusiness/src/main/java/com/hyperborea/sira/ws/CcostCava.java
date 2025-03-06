/**
 * CcostCava.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostCava  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CavaAltriDati[] cavaAltriDatis;

    private com.hyperborea.sira.ws.CavaImpiantiMezzi[] cavaImpiantiMezzis;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.MaterialeEstratto[] materialeEstrattos;

    private java.lang.String prodottoPrevalente;

    private java.lang.String tipologiaEstrazione;

    public CcostCava() {
    }

    public CcostCava(
           com.hyperborea.sira.ws.CavaAltriDati[] cavaAltriDatis,
           com.hyperborea.sira.ws.CavaImpiantiMezzi[] cavaImpiantiMezzis,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.MaterialeEstratto[] materialeEstrattos,
           java.lang.String prodottoPrevalente,
           java.lang.String tipologiaEstrazione) {
           this.cavaAltriDatis = cavaAltriDatis;
           this.cavaImpiantiMezzis = cavaImpiantiMezzis;
           this.idCcost = idCcost;
           this.materialeEstrattos = materialeEstrattos;
           this.prodottoPrevalente = prodottoPrevalente;
           this.tipologiaEstrazione = tipologiaEstrazione;
    }


    /**
     * Gets the cavaAltriDatis value for this CcostCava.
     * 
     * @return cavaAltriDatis
     */
    public com.hyperborea.sira.ws.CavaAltriDati[] getCavaAltriDatis() {
        return cavaAltriDatis;
    }


    /**
     * Sets the cavaAltriDatis value for this CcostCava.
     * 
     * @param cavaAltriDatis
     */
    public void setCavaAltriDatis(com.hyperborea.sira.ws.CavaAltriDati[] cavaAltriDatis) {
        this.cavaAltriDatis = cavaAltriDatis;
    }

    public com.hyperborea.sira.ws.CavaAltriDati getCavaAltriDatis(int i) {
        return this.cavaAltriDatis[i];
    }

    public void setCavaAltriDatis(int i, com.hyperborea.sira.ws.CavaAltriDati _value) {
        this.cavaAltriDatis[i] = _value;
    }


    /**
     * Gets the cavaImpiantiMezzis value for this CcostCava.
     * 
     * @return cavaImpiantiMezzis
     */
    public com.hyperborea.sira.ws.CavaImpiantiMezzi[] getCavaImpiantiMezzis() {
        return cavaImpiantiMezzis;
    }


    /**
     * Sets the cavaImpiantiMezzis value for this CcostCava.
     * 
     * @param cavaImpiantiMezzis
     */
    public void setCavaImpiantiMezzis(com.hyperborea.sira.ws.CavaImpiantiMezzi[] cavaImpiantiMezzis) {
        this.cavaImpiantiMezzis = cavaImpiantiMezzis;
    }

    public com.hyperborea.sira.ws.CavaImpiantiMezzi getCavaImpiantiMezzis(int i) {
        return this.cavaImpiantiMezzis[i];
    }

    public void setCavaImpiantiMezzis(int i, com.hyperborea.sira.ws.CavaImpiantiMezzi _value) {
        this.cavaImpiantiMezzis[i] = _value;
    }


    /**
     * Gets the idCcost value for this CcostCava.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostCava.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the materialeEstrattos value for this CcostCava.
     * 
     * @return materialeEstrattos
     */
    public com.hyperborea.sira.ws.MaterialeEstratto[] getMaterialeEstrattos() {
        return materialeEstrattos;
    }


    /**
     * Sets the materialeEstrattos value for this CcostCava.
     * 
     * @param materialeEstrattos
     */
    public void setMaterialeEstrattos(com.hyperborea.sira.ws.MaterialeEstratto[] materialeEstrattos) {
        this.materialeEstrattos = materialeEstrattos;
    }

    public com.hyperborea.sira.ws.MaterialeEstratto getMaterialeEstrattos(int i) {
        return this.materialeEstrattos[i];
    }

    public void setMaterialeEstrattos(int i, com.hyperborea.sira.ws.MaterialeEstratto _value) {
        this.materialeEstrattos[i] = _value;
    }


    /**
     * Gets the prodottoPrevalente value for this CcostCava.
     * 
     * @return prodottoPrevalente
     */
    public java.lang.String getProdottoPrevalente() {
        return prodottoPrevalente;
    }


    /**
     * Sets the prodottoPrevalente value for this CcostCava.
     * 
     * @param prodottoPrevalente
     */
    public void setProdottoPrevalente(java.lang.String prodottoPrevalente) {
        this.prodottoPrevalente = prodottoPrevalente;
    }


    /**
     * Gets the tipologiaEstrazione value for this CcostCava.
     * 
     * @return tipologiaEstrazione
     */
    public java.lang.String getTipologiaEstrazione() {
        return tipologiaEstrazione;
    }


    /**
     * Sets the tipologiaEstrazione value for this CcostCava.
     * 
     * @param tipologiaEstrazione
     */
    public void setTipologiaEstrazione(java.lang.String tipologiaEstrazione) {
        this.tipologiaEstrazione = tipologiaEstrazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostCava)) return false;
        CcostCava other = (CcostCava) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cavaAltriDatis==null && other.getCavaAltriDatis()==null) || 
             (this.cavaAltriDatis!=null &&
              java.util.Arrays.equals(this.cavaAltriDatis, other.getCavaAltriDatis()))) &&
            ((this.cavaImpiantiMezzis==null && other.getCavaImpiantiMezzis()==null) || 
             (this.cavaImpiantiMezzis!=null &&
              java.util.Arrays.equals(this.cavaImpiantiMezzis, other.getCavaImpiantiMezzis()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.materialeEstrattos==null && other.getMaterialeEstrattos()==null) || 
             (this.materialeEstrattos!=null &&
              java.util.Arrays.equals(this.materialeEstrattos, other.getMaterialeEstrattos()))) &&
            ((this.prodottoPrevalente==null && other.getProdottoPrevalente()==null) || 
             (this.prodottoPrevalente!=null &&
              this.prodottoPrevalente.equals(other.getProdottoPrevalente()))) &&
            ((this.tipologiaEstrazione==null && other.getTipologiaEstrazione()==null) || 
             (this.tipologiaEstrazione!=null &&
              this.tipologiaEstrazione.equals(other.getTipologiaEstrazione())));
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
        if (getCavaAltriDatis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCavaAltriDatis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCavaAltriDatis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCavaImpiantiMezzis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCavaImpiantiMezzis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCavaImpiantiMezzis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getMaterialeEstrattos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMaterialeEstrattos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMaterialeEstrattos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProdottoPrevalente() != null) {
            _hashCode += getProdottoPrevalente().hashCode();
        }
        if (getTipologiaEstrazione() != null) {
            _hashCode += getTipologiaEstrazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostCava.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCava"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cavaAltriDatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cavaAltriDatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cavaAltriDati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cavaImpiantiMezzis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cavaImpiantiMezzis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cavaImpiantiMezzi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("materialeEstrattos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "materialeEstrattos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialeEstratto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodottoPrevalente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodottoPrevalente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaEstrazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologiaEstrazione"));
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
