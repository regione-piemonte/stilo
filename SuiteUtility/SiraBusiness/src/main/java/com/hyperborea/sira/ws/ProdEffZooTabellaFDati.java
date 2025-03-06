/**
 * ProdEffZooTabellaFDati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaFDati  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String altroSpecifica;

    private int giorni;

    private java.lang.Integer idTabellaFdati;

    private float plateaNecessaria;

    private com.hyperborea.sira.ws.ProdEffZooMatNonPalabile prodEffZooMatNonPalabile;

    private float quantita;

    public ProdEffZooTabellaFDati() {
    }

    public ProdEffZooTabellaFDati(
           java.lang.String altroSpecifica,
           int giorni,
           java.lang.Integer idTabellaFdati,
           float plateaNecessaria,
           com.hyperborea.sira.ws.ProdEffZooMatNonPalabile prodEffZooMatNonPalabile,
           float quantita) {
        this.altroSpecifica = altroSpecifica;
        this.giorni = giorni;
        this.idTabellaFdati = idTabellaFdati;
        this.plateaNecessaria = plateaNecessaria;
        this.prodEffZooMatNonPalabile = prodEffZooMatNonPalabile;
        this.quantita = quantita;
    }


    /**
     * Gets the altroSpecifica value for this ProdEffZooTabellaFDati.
     * 
     * @return altroSpecifica
     */
    public java.lang.String getAltroSpecifica() {
        return altroSpecifica;
    }


    /**
     * Sets the altroSpecifica value for this ProdEffZooTabellaFDati.
     * 
     * @param altroSpecifica
     */
    public void setAltroSpecifica(java.lang.String altroSpecifica) {
        this.altroSpecifica = altroSpecifica;
    }


    /**
     * Gets the giorni value for this ProdEffZooTabellaFDati.
     * 
     * @return giorni
     */
    public int getGiorni() {
        return giorni;
    }


    /**
     * Sets the giorni value for this ProdEffZooTabellaFDati.
     * 
     * @param giorni
     */
    public void setGiorni(int giorni) {
        this.giorni = giorni;
    }


    /**
     * Gets the idTabellaFdati value for this ProdEffZooTabellaFDati.
     * 
     * @return idTabellaFdati
     */
    public java.lang.Integer getIdTabellaFdati() {
        return idTabellaFdati;
    }


    /**
     * Sets the idTabellaFdati value for this ProdEffZooTabellaFDati.
     * 
     * @param idTabellaFdati
     */
    public void setIdTabellaFdati(java.lang.Integer idTabellaFdati) {
        this.idTabellaFdati = idTabellaFdati;
    }


    /**
     * Gets the plateaNecessaria value for this ProdEffZooTabellaFDati.
     * 
     * @return plateaNecessaria
     */
    public float getPlateaNecessaria() {
        return plateaNecessaria;
    }


    /**
     * Sets the plateaNecessaria value for this ProdEffZooTabellaFDati.
     * 
     * @param plateaNecessaria
     */
    public void setPlateaNecessaria(float plateaNecessaria) {
        this.plateaNecessaria = plateaNecessaria;
    }


    /**
     * Gets the prodEffZooMatNonPalabile value for this ProdEffZooTabellaFDati.
     * 
     * @return prodEffZooMatNonPalabile
     */
    public com.hyperborea.sira.ws.ProdEffZooMatNonPalabile getProdEffZooMatNonPalabile() {
        return prodEffZooMatNonPalabile;
    }


    /**
     * Sets the prodEffZooMatNonPalabile value for this ProdEffZooTabellaFDati.
     * 
     * @param prodEffZooMatNonPalabile
     */
    public void setProdEffZooMatNonPalabile(com.hyperborea.sira.ws.ProdEffZooMatNonPalabile prodEffZooMatNonPalabile) {
        this.prodEffZooMatNonPalabile = prodEffZooMatNonPalabile;
    }


    /**
     * Gets the quantita value for this ProdEffZooTabellaFDati.
     * 
     * @return quantita
     */
    public float getQuantita() {
        return quantita;
    }


    /**
     * Sets the quantita value for this ProdEffZooTabellaFDati.
     * 
     * @param quantita
     */
    public void setQuantita(float quantita) {
        this.quantita = quantita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaFDati)) return false;
        ProdEffZooTabellaFDati other = (ProdEffZooTabellaFDati) obj;
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
            ((this.idTabellaFdati==null && other.getIdTabellaFdati()==null) || 
             (this.idTabellaFdati!=null &&
              this.idTabellaFdati.equals(other.getIdTabellaFdati()))) &&
            this.plateaNecessaria == other.getPlateaNecessaria() &&
            ((this.prodEffZooMatNonPalabile==null && other.getProdEffZooMatNonPalabile()==null) || 
             (this.prodEffZooMatNonPalabile!=null &&
              this.prodEffZooMatNonPalabile.equals(other.getProdEffZooMatNonPalabile()))) &&
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
        if (getIdTabellaFdati() != null) {
            _hashCode += getIdTabellaFdati().hashCode();
        }
        _hashCode += new Float(getPlateaNecessaria()).hashCode();
        if (getProdEffZooMatNonPalabile() != null) {
            _hashCode += getProdEffZooMatNonPalabile().hashCode();
        }
        _hashCode += new Float(getQuantita()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaFDati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaFDati"));
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
        elemField.setFieldName("idTabellaFdati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaFdati"));
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
        elemField.setFieldName("prodEffZooMatNonPalabile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooMatNonPalabile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooMatNonPalabile"));
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
