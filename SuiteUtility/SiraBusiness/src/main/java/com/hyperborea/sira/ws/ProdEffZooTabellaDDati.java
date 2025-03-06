/**
 * ProdEffZooTabellaDDati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaDDati  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String altroSpecifica;

    private int giorni;

    private java.lang.Integer idTabellaDdati;

    private float plateaNecessaria;

    private com.hyperborea.sira.ws.ProdEffZooMatPalabile prodEffZooMatPalabile;

    private float quantita;

    public ProdEffZooTabellaDDati() {
    }

    public ProdEffZooTabellaDDati(
           java.lang.String altroSpecifica,
           int giorni,
           java.lang.Integer idTabellaDdati,
           float plateaNecessaria,
           com.hyperborea.sira.ws.ProdEffZooMatPalabile prodEffZooMatPalabile,
           float quantita) {
        this.altroSpecifica = altroSpecifica;
        this.giorni = giorni;
        this.idTabellaDdati = idTabellaDdati;
        this.plateaNecessaria = plateaNecessaria;
        this.prodEffZooMatPalabile = prodEffZooMatPalabile;
        this.quantita = quantita;
    }


    /**
     * Gets the altroSpecifica value for this ProdEffZooTabellaDDati.
     * 
     * @return altroSpecifica
     */
    public java.lang.String getAltroSpecifica() {
        return altroSpecifica;
    }


    /**
     * Sets the altroSpecifica value for this ProdEffZooTabellaDDati.
     * 
     * @param altroSpecifica
     */
    public void setAltroSpecifica(java.lang.String altroSpecifica) {
        this.altroSpecifica = altroSpecifica;
    }


    /**
     * Gets the giorni value for this ProdEffZooTabellaDDati.
     * 
     * @return giorni
     */
    public int getGiorni() {
        return giorni;
    }


    /**
     * Sets the giorni value for this ProdEffZooTabellaDDati.
     * 
     * @param giorni
     */
    public void setGiorni(int giorni) {
        this.giorni = giorni;
    }


    /**
     * Gets the idTabellaDdati value for this ProdEffZooTabellaDDati.
     * 
     * @return idTabellaDdati
     */
    public java.lang.Integer getIdTabellaDdati() {
        return idTabellaDdati;
    }


    /**
     * Sets the idTabellaDdati value for this ProdEffZooTabellaDDati.
     * 
     * @param idTabellaDdati
     */
    public void setIdTabellaDdati(java.lang.Integer idTabellaDdati) {
        this.idTabellaDdati = idTabellaDdati;
    }


    /**
     * Gets the plateaNecessaria value for this ProdEffZooTabellaDDati.
     * 
     * @return plateaNecessaria
     */
    public float getPlateaNecessaria() {
        return plateaNecessaria;
    }


    /**
     * Sets the plateaNecessaria value for this ProdEffZooTabellaDDati.
     * 
     * @param plateaNecessaria
     */
    public void setPlateaNecessaria(float plateaNecessaria) {
        this.plateaNecessaria = plateaNecessaria;
    }


    /**
     * Gets the prodEffZooMatPalabile value for this ProdEffZooTabellaDDati.
     * 
     * @return prodEffZooMatPalabile
     */
    public com.hyperborea.sira.ws.ProdEffZooMatPalabile getProdEffZooMatPalabile() {
        return prodEffZooMatPalabile;
    }


    /**
     * Sets the prodEffZooMatPalabile value for this ProdEffZooTabellaDDati.
     * 
     * @param prodEffZooMatPalabile
     */
    public void setProdEffZooMatPalabile(com.hyperborea.sira.ws.ProdEffZooMatPalabile prodEffZooMatPalabile) {
        this.prodEffZooMatPalabile = prodEffZooMatPalabile;
    }


    /**
     * Gets the quantita value for this ProdEffZooTabellaDDati.
     * 
     * @return quantita
     */
    public float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this ProdEffZooTabellaDDati.
     * 
     * @param quantita
     */
    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaDDati)) return false;
        ProdEffZooTabellaDDati other = (ProdEffZooTabellaDDati) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.altroSpecifica==null && other.getAltroSpecifica()==null) || 
             (this.altroSpecifica!=null &&
              this.altroSpecifica.equals(other.getAltroSpecifica()))) &&
            this.giorni == other.getGiorni() &&
            ((this.idTabellaDdati==null && other.getIdTabellaDdati()==null) || 
             (this.idTabellaDdati!=null &&
              this.idTabellaDdati.equals(other.getIdTabellaDdati()))) &&
            this.plateaNecessaria == other.getPlateaNecessaria() &&
            ((this.prodEffZooMatPalabile==null && other.getProdEffZooMatPalabile()==null) || 
             (this.prodEffZooMatPalabile!=null &&
              this.prodEffZooMatPalabile.equals(other.getProdEffZooMatPalabile()))) &&
            this.quantita == other.getQuantita();
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
        if (getAltroSpecifica() != null) {
            _hashCode += getAltroSpecifica().hashCode();
        }
        _hashCode += getGiorni();
        if (getIdTabellaDdati() != null) {
            _hashCode += getIdTabellaDdati().hashCode();
        }
        _hashCode += new Float(getPlateaNecessaria()).hashCode();
        if (getProdEffZooMatPalabile() != null) {
            _hashCode += getProdEffZooMatPalabile().hashCode();
        }
        _hashCode += new Float(getQuantita()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaDDati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaDDati"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("altroSpecifica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "altroSpecifica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giorni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "giorni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaDdati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaDdati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateaNecessaria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateaNecessaria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooMatPalabile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooMatPalabile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooMatPalabile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quantita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quantita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
