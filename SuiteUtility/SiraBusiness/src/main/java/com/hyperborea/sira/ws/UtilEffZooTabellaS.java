/**
 * UtilEffZooTabellaS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UtilEffZooTabellaS  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private float contenutoN;

    private java.lang.Integer idTabellaS;

    private float quantita;

    private java.lang.String tipoRefluo;

    private java.lang.String tipoUtilizzo;

    private com.hyperborea.sira.ws.VocTipoAzienda vocTipoAzienda;

    public UtilEffZooTabellaS() {
    }

    public UtilEffZooTabellaS(
           float contenutoN,
           java.lang.Integer idTabellaS,
           float quantita,
           java.lang.String tipoRefluo,
           java.lang.String tipoUtilizzo,
           com.hyperborea.sira.ws.VocTipoAzienda vocTipoAzienda) {
        this.contenutoN = contenutoN;
        this.idTabellaS = idTabellaS;
        this.quantita = quantita;
        this.tipoRefluo = tipoRefluo;
        this.tipoUtilizzo = tipoUtilizzo;
        this.vocTipoAzienda = vocTipoAzienda;
    }


    /**
     * Gets the contenutoN value for this UtilEffZooTabellaS.
     * 
     * @return contenutoN
     */
    public float getContenutoN() {
        return contenutoN;
    }


    /**
     * Sets the contenutoN value for this UtilEffZooTabellaS.
     * 
     * @param contenutoN
     */
    public void setContenutoN(float contenutoN) {
        this.contenutoN = contenutoN;
    }


    /**
     * Gets the idTabellaS value for this UtilEffZooTabellaS.
     * 
     * @return idTabellaS
     */
    public java.lang.Integer getIdTabellaS() {
        return idTabellaS;
    }


    /**
     * Sets the idTabellaS value for this UtilEffZooTabellaS.
     * 
     * @param idTabellaS
     */
    public void setIdTabellaS(java.lang.Integer idTabellaS) {
        this.idTabellaS = idTabellaS;
    }


    /**
     * Gets the quantita value for this UtilEffZooTabellaS.
     * 
     * @return quantita
     */
    public float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this UtilEffZooTabellaS.
     * 
     * @param quantita
     */
    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }


    /**
     * Gets the tipoRefluo value for this UtilEffZooTabellaS.
     * 
     * @return tipoRefluo
     */
    public java.lang.String getTipoRefluo() {
        return tipoRefluo;
    }


    /**
     * Sets the tipoRefluo value for this UtilEffZooTabellaS.
     * 
     * @param tipoRefluo
     */
    public void setTipoRefluo(java.lang.String tipoRefluo) {
        this.tipoRefluo = tipoRefluo;
    }


    /**
     * Gets the tipoUtilizzo value for this UtilEffZooTabellaS.
     * 
     * @return tipoUtilizzo
     */
    public java.lang.String getTipoUtilizzo() {
        return tipoUtilizzo;
    }


    /**
     * Sets the tipoUtilizzo value for this UtilEffZooTabellaS.
     * 
     * @param tipoUtilizzo
     */
    public void setTipoUtilizzo(java.lang.String tipoUtilizzo) {
        this.tipoUtilizzo = tipoUtilizzo;
    }


    /**
     * Gets the vocTipoAzienda value for this UtilEffZooTabellaS.
     * 
     * @return vocTipoAzienda
     */
    public com.hyperborea.sira.ws.VocTipoAzienda getVocTipoAzienda() {
        return vocTipoAzienda;
    }


    /**
     * Sets the vocTipoAzienda value for this UtilEffZooTabellaS.
     * 
     * @param vocTipoAzienda
     */
    public void setVocTipoAzienda(com.hyperborea.sira.ws.VocTipoAzienda vocTipoAzienda) {
        this.vocTipoAzienda = vocTipoAzienda;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilEffZooTabellaS)) return false;
        UtilEffZooTabellaS other = (UtilEffZooTabellaS) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.contenutoN == other.getContenutoN() &&
            ((this.idTabellaS==null && other.getIdTabellaS()==null) || 
             (this.idTabellaS!=null &&
              this.idTabellaS.equals(other.getIdTabellaS()))) &&
            this.quantita == other.getQuantita() &&
            ((this.tipoRefluo==null && other.getTipoRefluo()==null) || 
             (this.tipoRefluo!=null &&
              this.tipoRefluo.equals(other.getTipoRefluo()))) &&
            ((this.tipoUtilizzo==null && other.getTipoUtilizzo()==null) || 
             (this.tipoUtilizzo!=null &&
              this.tipoUtilizzo.equals(other.getTipoUtilizzo()))) &&
            ((this.vocTipoAzienda==null && other.getVocTipoAzienda()==null) || 
             (this.vocTipoAzienda!=null &&
              this.vocTipoAzienda.equals(other.getVocTipoAzienda())));
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
        _hashCode += new Float(getContenutoN()).hashCode();
        if (getIdTabellaS() != null) {
            _hashCode += getIdTabellaS().hashCode();
        }
        _hashCode += new Float(getQuantita()).hashCode();
        if (getTipoRefluo() != null) {
            _hashCode += getTipoRefluo().hashCode();
        }
        if (getTipoUtilizzo() != null) {
            _hashCode += getTipoUtilizzo().hashCode();
        }
        if (getVocTipoAzienda() != null) {
            _hashCode += getVocTipoAzienda().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtilEffZooTabellaS.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaS"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contenutoN");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contenutoN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRefluo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRefluo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoUtilizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoUtilizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipoAzienda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipoAzienda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipoAzienda"));
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
