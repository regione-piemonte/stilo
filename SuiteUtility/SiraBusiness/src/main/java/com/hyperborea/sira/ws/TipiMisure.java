/**
 * TipiMisure.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipiMisure  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float accuratezza;

    private com.hyperborea.sira.ws.CategorieIipa categorieIipa;

    private com.hyperborea.sira.ws.DominioValori[] dominioValoris;

    private java.lang.Integer idTipomisura;

    private java.lang.Float incertezza;

    private java.lang.Float limiteRilevamento;

    private com.hyperborea.sira.ws.MetodiMisura metodiMisura;

    private com.hyperborea.sira.ws.ParametriAmbientali parametriAmbientali;

    private java.lang.Float precisione;

    private com.hyperborea.sira.ws.UnitaDiMisuraCia unitaDiMisuraCia;

    private com.hyperborea.sira.ws.WsRiferimentiNormativiRef wsRiferimentiNormativiRef;

    public TipiMisure() {
    }

    public TipiMisure(
           java.lang.Float accuratezza,
           com.hyperborea.sira.ws.CategorieIipa categorieIipa,
           com.hyperborea.sira.ws.DominioValori[] dominioValoris,
           java.lang.Integer idTipomisura,
           java.lang.Float incertezza,
           java.lang.Float limiteRilevamento,
           com.hyperborea.sira.ws.MetodiMisura metodiMisura,
           com.hyperborea.sira.ws.ParametriAmbientali parametriAmbientali,
           java.lang.Float precisione,
           com.hyperborea.sira.ws.UnitaDiMisuraCia unitaDiMisuraCia,
           com.hyperborea.sira.ws.WsRiferimentiNormativiRef wsRiferimentiNormativiRef) {
        this.accuratezza = accuratezza;
        this.categorieIipa = categorieIipa;
        this.dominioValoris = dominioValoris;
        this.idTipomisura = idTipomisura;
        this.incertezza = incertezza;
        this.limiteRilevamento = limiteRilevamento;
        this.metodiMisura = metodiMisura;
        this.parametriAmbientali = parametriAmbientali;
        this.precisione = precisione;
        this.unitaDiMisuraCia = unitaDiMisuraCia;
        this.wsRiferimentiNormativiRef = wsRiferimentiNormativiRef;
    }


    /**
     * Gets the accuratezza value for this TipiMisure.
     * 
     * @return accuratezza
     */
    public java.lang.Float getAccuratezza() {
        return accuratezza;
    }


    /**
     * Sets the accuratezza value for this TipiMisure.
     * 
     * @param accuratezza
     */
    public void setAccuratezza(java.lang.Float accuratezza) {
        this.accuratezza = accuratezza;
    }


    /**
     * Gets the categorieIipa value for this TipiMisure.
     * 
     * @return categorieIipa
     */
    public com.hyperborea.sira.ws.CategorieIipa getCategorieIipa() {
        return categorieIipa;
    }


    /**
     * Sets the categorieIipa value for this TipiMisure.
     * 
     * @param categorieIipa
     */
    public void setCategorieIipa(com.hyperborea.sira.ws.CategorieIipa categorieIipa) {
        this.categorieIipa = categorieIipa;
    }


    /**
     * Gets the dominioValoris value for this TipiMisure.
     * 
     * @return dominioValoris
     */
    public com.hyperborea.sira.ws.DominioValori[] getDominioValoris() {
        return dominioValoris;
    }


    /**
     * Sets the dominioValoris value for this TipiMisure.
     * 
     * @param dominioValoris
     */
    public void setDominioValoris(com.hyperborea.sira.ws.DominioValori[] dominioValoris) {
        this.dominioValoris = dominioValoris;
    }

    public com.hyperborea.sira.ws.DominioValori getDominioValoris(int i) {
        return this.dominioValoris[i];
    }

    public void setDominioValoris(int i, com.hyperborea.sira.ws.DominioValori _value) {
        this.dominioValoris[i] = _value;
    }


    /**
     * Gets the idTipomisura value for this TipiMisure.
     * 
     * @return idTipomisura
     */
    public java.lang.Integer getIdTipomisura() {
        return idTipomisura;
    }


    /**
     * Sets the idTipomisura value for this TipiMisure.
     * 
     * @param idTipomisura
     */
    public void setIdTipomisura(java.lang.Integer idTipomisura) {
        this.idTipomisura = idTipomisura;
    }


    /**
     * Gets the incertezza value for this TipiMisure.
     * 
     * @return incertezza
     */
    public java.lang.Float getIncertezza() {
        return incertezza;
    }


    /**
     * Sets the incertezza value for this TipiMisure.
     * 
     * @param incertezza
     */
    public void setIncertezza(java.lang.Float incertezza) {
        this.incertezza = incertezza;
    }


    /**
     * Gets the limiteRilevamento value for this TipiMisure.
     * 
     * @return limiteRilevamento
     */
    public java.lang.Float getLimiteRilevamento() {
        return limiteRilevamento;
    }


    /**
     * Sets the limiteRilevamento value for this TipiMisure.
     * 
     * @param limiteRilevamento
     */
    public void setLimiteRilevamento(java.lang.Float limiteRilevamento) {
        this.limiteRilevamento = limiteRilevamento;
    }


    /**
     * Gets the metodiMisura value for this TipiMisure.
     * 
     * @return metodiMisura
     */
    public com.hyperborea.sira.ws.MetodiMisura getMetodiMisura() {
        return metodiMisura;
    }


    /**
     * Sets the metodiMisura value for this TipiMisure.
     * 
     * @param metodiMisura
     */
    public void setMetodiMisura(com.hyperborea.sira.ws.MetodiMisura metodiMisura) {
        this.metodiMisura = metodiMisura;
    }


    /**
     * Gets the parametriAmbientali value for this TipiMisure.
     * 
     * @return parametriAmbientali
     */
    public com.hyperborea.sira.ws.ParametriAmbientali getParametriAmbientali() {
        return parametriAmbientali;
    }


    /**
     * Sets the parametriAmbientali value for this TipiMisure.
     * 
     * @param parametriAmbientali
     */
    public void setParametriAmbientali(com.hyperborea.sira.ws.ParametriAmbientali parametriAmbientali) {
        this.parametriAmbientali = parametriAmbientali;
    }


    /**
     * Gets the precisione value for this TipiMisure.
     * 
     * @return precisione
     */
    public java.lang.Float getPrecisione() {
        return precisione;
    }


    /**
     * Sets the precisione value for this TipiMisure.
     * 
     * @param precisione
     */
    public void setPrecisione(java.lang.Float precisione) {
        this.precisione = precisione;
    }


    /**
     * Gets the unitaDiMisuraCia value for this TipiMisure.
     * 
     * @return unitaDiMisuraCia
     */
    public com.hyperborea.sira.ws.UnitaDiMisuraCia getUnitaDiMisuraCia() {
        return unitaDiMisuraCia;
    }


    /**
     * Sets the unitaDiMisuraCia value for this TipiMisure.
     * 
     * @param unitaDiMisuraCia
     */
    public void setUnitaDiMisuraCia(com.hyperborea.sira.ws.UnitaDiMisuraCia unitaDiMisuraCia) {
        this.unitaDiMisuraCia = unitaDiMisuraCia;
    }


    /**
     * Gets the wsRiferimentiNormativiRef value for this TipiMisure.
     * 
     * @return wsRiferimentiNormativiRef
     */
    public com.hyperborea.sira.ws.WsRiferimentiNormativiRef getWsRiferimentiNormativiRef() {
        return wsRiferimentiNormativiRef;
    }


    /**
     * Sets the wsRiferimentiNormativiRef value for this TipiMisure.
     * 
     * @param wsRiferimentiNormativiRef
     */
    public void setWsRiferimentiNormativiRef(com.hyperborea.sira.ws.WsRiferimentiNormativiRef wsRiferimentiNormativiRef) {
        this.wsRiferimentiNormativiRef = wsRiferimentiNormativiRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipiMisure)) return false;
        TipiMisure other = (TipiMisure) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.accuratezza==null && other.getAccuratezza()==null) || 
             (this.accuratezza!=null &&
              this.accuratezza.equals(other.getAccuratezza()))) &&
            ((this.categorieIipa==null && other.getCategorieIipa()==null) || 
             (this.categorieIipa!=null &&
              this.categorieIipa.equals(other.getCategorieIipa()))) &&
            ((this.dominioValoris==null && other.getDominioValoris()==null) || 
             (this.dominioValoris!=null &&
              java.util.Arrays.equals(this.dominioValoris, other.getDominioValoris()))) &&
            ((this.idTipomisura==null && other.getIdTipomisura()==null) || 
             (this.idTipomisura!=null &&
              this.idTipomisura.equals(other.getIdTipomisura()))) &&
            ((this.incertezza==null && other.getIncertezza()==null) || 
             (this.incertezza!=null &&
              this.incertezza.equals(other.getIncertezza()))) &&
            ((this.limiteRilevamento==null && other.getLimiteRilevamento()==null) || 
             (this.limiteRilevamento!=null &&
              this.limiteRilevamento.equals(other.getLimiteRilevamento()))) &&
            ((this.metodiMisura==null && other.getMetodiMisura()==null) || 
             (this.metodiMisura!=null &&
              this.metodiMisura.equals(other.getMetodiMisura()))) &&
            ((this.parametriAmbientali==null && other.getParametriAmbientali()==null) || 
             (this.parametriAmbientali!=null &&
              this.parametriAmbientali.equals(other.getParametriAmbientali()))) &&
            ((this.precisione==null && other.getPrecisione()==null) || 
             (this.precisione!=null &&
              this.precisione.equals(other.getPrecisione()))) &&
            ((this.unitaDiMisuraCia==null && other.getUnitaDiMisuraCia()==null) || 
             (this.unitaDiMisuraCia!=null &&
              this.unitaDiMisuraCia.equals(other.getUnitaDiMisuraCia()))) &&
            ((this.wsRiferimentiNormativiRef==null && other.getWsRiferimentiNormativiRef()==null) || 
             (this.wsRiferimentiNormativiRef!=null &&
              this.wsRiferimentiNormativiRef.equals(other.getWsRiferimentiNormativiRef())));
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
        if (getAccuratezza() != null) {
            _hashCode += getAccuratezza().hashCode();
        }
        if (getCategorieIipa() != null) {
            _hashCode += getCategorieIipa().hashCode();
        }
        if (getDominioValoris() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDominioValoris());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDominioValoris(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdTipomisura() != null) {
            _hashCode += getIdTipomisura().hashCode();
        }
        if (getIncertezza() != null) {
            _hashCode += getIncertezza().hashCode();
        }
        if (getLimiteRilevamento() != null) {
            _hashCode += getLimiteRilevamento().hashCode();
        }
        if (getMetodiMisura() != null) {
            _hashCode += getMetodiMisura().hashCode();
        }
        if (getParametriAmbientali() != null) {
            _hashCode += getParametriAmbientali().hashCode();
        }
        if (getPrecisione() != null) {
            _hashCode += getPrecisione().hashCode();
        }
        if (getUnitaDiMisuraCia() != null) {
            _hashCode += getUnitaDiMisuraCia().hashCode();
        }
        if (getWsRiferimentiNormativiRef() != null) {
            _hashCode += getWsRiferimentiNormativiRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipiMisure.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiMisure"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accuratezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accuratezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categorieIipa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "categorieIipa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "categorieIipa"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dominioValoris");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dominioValoris"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dominioValori"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipomisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipomisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incertezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "incertezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limiteRilevamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limiteRilevamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("metodiMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "metodiMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "metodiMisura"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parametriAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parametriAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "parametriAmbientali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("precisione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "precisione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitaDiMisuraCia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitaDiMisuraCia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaDiMisuraCia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsRiferimentiNormativiRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsRiferimentiNormativiRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsRiferimentiNormativiRef"));
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
