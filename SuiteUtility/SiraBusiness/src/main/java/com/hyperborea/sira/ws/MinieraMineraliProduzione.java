/**
 * MinieraMineraliProduzione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MinieraMineraliProduzione  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.Float giacenza;

    private java.lang.Integer idProduzione;

    private java.lang.Integer mese;

    private java.lang.Float potereCalorifico;

    private java.lang.Float quantita;

    private java.lang.Float tenoreMedio;

    private com.hyperborea.sira.ws.VocTipoMineraleProdotto vocTipoMineraleProdotto;

    public MinieraMineraliProduzione() {
    }

    public MinieraMineraliProduzione(
           java.lang.Integer anno,
           java.lang.Float giacenza,
           java.lang.Integer idProduzione,
           java.lang.Integer mese,
           java.lang.Float potereCalorifico,
           java.lang.Float quantita,
           java.lang.Float tenoreMedio,
           com.hyperborea.sira.ws.VocTipoMineraleProdotto vocTipoMineraleProdotto) {
        this.anno = anno;
        this.giacenza = giacenza;
        this.idProduzione = idProduzione;
        this.mese = mese;
        this.potereCalorifico = potereCalorifico;
        this.quantita = quantita;
        this.tenoreMedio = tenoreMedio;
        this.vocTipoMineraleProdotto = vocTipoMineraleProdotto;
    }


    /**
     * Gets the anno value for this MinieraMineraliProduzione.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this MinieraMineraliProduzione.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the giacenza value for this MinieraMineraliProduzione.
     * 
     * @return giacenza
     */
    public java.lang.Float getGiacenza() {
        return giacenza;
    }


    /**
     * Sets the giacenza value for this MinieraMineraliProduzione.
     * 
     * @param giacenza
     */
    public void setGiacenza(java.lang.Float giacenza) {
        this.giacenza = giacenza;
    }


    /**
     * Gets the idProduzione value for this MinieraMineraliProduzione.
     * 
     * @return idProduzione
     */
    public java.lang.Integer getIdProduzione() {
        return idProduzione;
    }


    /**
     * Sets the idProduzione value for this MinieraMineraliProduzione.
     * 
     * @param idProduzione
     */
    public void setIdProduzione(java.lang.Integer idProduzione) {
        this.idProduzione = idProduzione;
    }


    /**
     * Gets the mese value for this MinieraMineraliProduzione.
     * 
     * @return mese
     */
    public java.lang.Integer getMese() {
        return mese;
    }


    /**
     * Sets the mese value for this MinieraMineraliProduzione.
     * 
     * @param mese
     */
    public void setMese(java.lang.Integer mese) {
        this.mese = mese;
    }


    /**
     * Gets the potereCalorifico value for this MinieraMineraliProduzione.
     * 
     * @return potereCalorifico
     */
    public java.lang.Float getPotereCalorifico() {
        return potereCalorifico;
    }


    /**
     * Sets the potereCalorifico value for this MinieraMineraliProduzione.
     * 
     * @param potereCalorifico
     */
    public void setPotereCalorifico(java.lang.Float potereCalorifico) {
        this.potereCalorifico = potereCalorifico;
    }


    /**
     * Gets the quantita value for this MinieraMineraliProduzione.
     * 
     * @return quantita
     */
    public java.lang.Float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this MinieraMineraliProduzione.
     * 
     * @param quantita
     */
    public void setQuantita(java.lang.Float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the tenoreMedio value for this MinieraMineraliProduzione.
     * 
     * @return tenoreMedio
     */
    public java.lang.Float getTenoreMedio() {
        return tenoreMedio;
    }


    /**
     * Sets the tenoreMedio value for this MinieraMineraliProduzione.
     * 
     * @param tenoreMedio
     */
    public void setTenoreMedio(java.lang.Float tenoreMedio) {
        this.tenoreMedio = tenoreMedio;
    }


    /**
     * Gets the vocTipoMineraleProdotto value for this MinieraMineraliProduzione.
     * 
     * @return vocTipoMineraleProdotto
     */
    public com.hyperborea.sira.ws.VocTipoMineraleProdotto getVocTipoMineraleProdotto() {
        return vocTipoMineraleProdotto;
    }


    /**
     * Sets the vocTipoMineraleProdotto value for this MinieraMineraliProduzione.
     * 
     * @param vocTipoMineraleProdotto
     */
    public void setVocTipoMineraleProdotto(com.hyperborea.sira.ws.VocTipoMineraleProdotto vocTipoMineraleProdotto) {
        this.vocTipoMineraleProdotto = vocTipoMineraleProdotto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MinieraMineraliProduzione)) return false;
        MinieraMineraliProduzione other = (MinieraMineraliProduzione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.giacenza==null && other.getGiacenza()==null) || 
             (this.giacenza!=null &&
              this.giacenza.equals(other.getGiacenza()))) &&
            ((this.idProduzione==null && other.getIdProduzione()==null) || 
             (this.idProduzione!=null &&
              this.idProduzione.equals(other.getIdProduzione()))) &&
            ((this.mese==null && other.getMese()==null) || 
             (this.mese!=null &&
              this.mese.equals(other.getMese()))) &&
            ((this.potereCalorifico==null && other.getPotereCalorifico()==null) || 
             (this.potereCalorifico!=null &&
              this.potereCalorifico.equals(other.getPotereCalorifico()))) &&
            ((this.quantita==null && other.getQuantita()==null) || 
             (this.quantita!=null &&
              this.quantita.equals(other.getQuantita()))) &&
            ((this.tenoreMedio==null && other.getTenoreMedio()==null) || 
             (this.tenoreMedio!=null &&
              this.tenoreMedio.equals(other.getTenoreMedio()))) &&
            ((this.vocTipoMineraleProdotto==null && other.getVocTipoMineraleProdotto()==null) || 
             (this.vocTipoMineraleProdotto!=null &&
              this.vocTipoMineraleProdotto.equals(other.getVocTipoMineraleProdotto())));
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
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getGiacenza() != null) {
            _hashCode += getGiacenza().hashCode();
        }
        if (getIdProduzione() != null) {
            _hashCode += getIdProduzione().hashCode();
        }
        if (getMese() != null) {
            _hashCode += getMese().hashCode();
        }
        if (getPotereCalorifico() != null) {
            _hashCode += getPotereCalorifico().hashCode();
        }
        if (getQuantita() != null) {
            _hashCode += getQuantita().hashCode();
        }
        if (getTenoreMedio() != null) {
            _hashCode += getTenoreMedio().hashCode();
        }
        if (getVocTipoMineraleProdotto() != null) {
            _hashCode += getVocTipoMineraleProdotto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MinieraMineraliProduzione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "minieraMineraliProduzione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giacenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giacenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProduzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProduzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mese");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mese"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potereCalorifico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potereCalorifico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenoreMedio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tenoreMedio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoMineraleProdotto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoMineraleProdotto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoMineraleProdotto"));
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
