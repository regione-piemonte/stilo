/**
 * ProdEffZooTabellaA.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaA  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private float coefficienteStabulazione;

    private java.lang.Integer idTabellaA;

    private float letameMatPalabPeso;

    private float letameMatPalabVolume;

    private float liquame;

    private int numeroCapi;

    private float pesoVivoTotale;

    private com.hyperborea.sira.ws.ProdEffZooTipoStab1 prodEffZooTipoStab1;

    public ProdEffZooTabellaA() {
    }

    public ProdEffZooTabellaA(
           float coefficienteStabulazione,
           java.lang.Integer idTabellaA,
           float letameMatPalabPeso,
           float letameMatPalabVolume,
           float liquame,
           int numeroCapi,
           float pesoVivoTotale,
           com.hyperborea.sira.ws.ProdEffZooTipoStab1 prodEffZooTipoStab1) {
        this.coefficienteStabulazione = coefficienteStabulazione;
        this.idTabellaA = idTabellaA;
        this.letameMatPalabPeso = letameMatPalabPeso;
        this.letameMatPalabVolume = letameMatPalabVolume;
        this.liquame = liquame;
        this.numeroCapi = numeroCapi;
        this.pesoVivoTotale = pesoVivoTotale;
        this.prodEffZooTipoStab1 = prodEffZooTipoStab1;
    }


    /**
     * Gets the coefficienteStabulazione value for this ProdEffZooTabellaA.
     * 
     * @return coefficienteStabulazione
     */
    public float getCoefficienteStabulazione() {
        return coefficienteStabulazione;
    }


    /**
     * Sets the coefficienteStabulazione value for this ProdEffZooTabellaA.
     * 
     * @param coefficienteStabulazione
     */
    public void setCoefficienteStabulazione(float coefficienteStabulazione) {
        this.coefficienteStabulazione = coefficienteStabulazione;
    }


    /**
     * Gets the idTabellaA value for this ProdEffZooTabellaA.
     * 
     * @return idTabellaA
     */
    public java.lang.Integer getIdTabellaA() {
        return idTabellaA;
    }


    /**
     * Sets the idTabellaA value for this ProdEffZooTabellaA.
     * 
     * @param idTabellaA
     */
    public void setIdTabellaA(java.lang.Integer idTabellaA) {
        this.idTabellaA = idTabellaA;
    }


    /**
     * Gets the letameMatPalabPeso value for this ProdEffZooTabellaA.
     * 
     * @return letameMatPalabPeso
     */
    public float getLetameMatPalabPeso() {
        return letameMatPalabPeso;
    }


    /**
     * Sets the letameMatPalabPeso value for this ProdEffZooTabellaA.
     * 
     * @param letameMatPalabPeso
     */
    public void setLetameMatPalabPeso(float letameMatPalabPeso) {
        this.letameMatPalabPeso = letameMatPalabPeso;
    }


    /**
     * Gets the letameMatPalabVolume value for this ProdEffZooTabellaA.
     * 
     * @return letameMatPalabVolume
     */
    public float getLetameMatPalabVolume() {
        return letameMatPalabVolume;
    }


    /**
     * Sets the letameMatPalabVolume value for this ProdEffZooTabellaA.
     * 
     * @param letameMatPalabVolume
     */
    public void setLetameMatPalabVolume(float letameMatPalabVolume) {
        this.letameMatPalabVolume = letameMatPalabVolume;
    }


    /**
     * Gets the liquame value for this ProdEffZooTabellaA.
     * 
     * @return liquame
     */
    public float getLiquame() {
        return liquame;
    }


    /**
     * Sets the liquame value for this ProdEffZooTabellaA.
     * 
     * @param liquame
     */
    public void setLiquame(float liquame) {
        this.liquame = liquame;
    }


    /**
     * Gets the numeroCapi value for this ProdEffZooTabellaA.
     * 
     * @return numeroCapi
     */
    public int getNumeroCapi() {
        return numeroCapi;
    }


    /**
     * Sets the numeroCapi value for this ProdEffZooTabellaA.
     * 
     * @param numeroCapi
     */
    public void setNumeroCapi(int numeroCapi) {
        this.numeroCapi = numeroCapi;
    }


    /**
     * Gets the pesoVivoTotale value for this ProdEffZooTabellaA.
     * 
     * @return pesoVivoTotale
     */
    public float getPesoVivoTotale() {
        return pesoVivoTotale;
    }


    /**
     * Sets the pesoVivoTotale value for this ProdEffZooTabellaA.
     * 
     * @param pesoVivoTotale
     */
    public void setPesoVivoTotale(float pesoVivoTotale) {
        this.pesoVivoTotale = pesoVivoTotale;
    }


    /**
     * Gets the prodEffZooTipoStab1 value for this ProdEffZooTabellaA.
     * 
     * @return prodEffZooTipoStab1
     */
    public com.hyperborea.sira.ws.ProdEffZooTipoStab1 getProdEffZooTipoStab1() {
        return prodEffZooTipoStab1;
    }


    /**
     * Sets the prodEffZooTipoStab1 value for this ProdEffZooTabellaA.
     * 
     * @param prodEffZooTipoStab1
     */
    public void setProdEffZooTipoStab1(com.hyperborea.sira.ws.ProdEffZooTipoStab1 prodEffZooTipoStab1) {
        this.prodEffZooTipoStab1 = prodEffZooTipoStab1;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaA)) return false;
        ProdEffZooTabellaA other = (ProdEffZooTabellaA) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.coefficienteStabulazione == other.getCoefficienteStabulazione() &&
            ((this.idTabellaA==null && other.getIdTabellaA()==null) || 
             (this.idTabellaA!=null &&
              this.idTabellaA.equals(other.getIdTabellaA()))) &&
            this.letameMatPalabPeso == other.getLetameMatPalabPeso() &&
            this.letameMatPalabVolume == other.getLetameMatPalabVolume() &&
            this.liquame == other.getLiquame() &&
            this.numeroCapi == other.getNumeroCapi() &&
            this.pesoVivoTotale == other.getPesoVivoTotale() &&
            ((this.prodEffZooTipoStab1==null && other.getProdEffZooTipoStab1()==null) || 
             (this.prodEffZooTipoStab1!=null &&
              this.prodEffZooTipoStab1.equals(other.getProdEffZooTipoStab1())));
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
        _hashCode += new Float(getCoefficienteStabulazione()).hashCode();
        if (getIdTabellaA() != null) {
            _hashCode += getIdTabellaA().hashCode();
        }
        _hashCode += new Float(getLetameMatPalabPeso()).hashCode();
        _hashCode += new Float(getLetameMatPalabVolume()).hashCode();
        _hashCode += new Float(getLiquame()).hashCode();
        _hashCode += getNumeroCapi();
        _hashCode += new Float(getPesoVivoTotale()).hashCode();
        if (getProdEffZooTipoStab1() != null) {
            _hashCode += getProdEffZooTipoStab1().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaA.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaA"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coefficienteStabulazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "coefficienteStabulazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaA");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaA"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("letameMatPalabPeso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "letameMatPalabPeso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("letameMatPalabVolume");
        elemField.setXmlName(new javax.xml.namespace.QName("", "letameMatPalabVolume"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liquame");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liquame"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCapi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCapi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pesoVivoTotale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pesoVivoTotale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTipoStab1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTipoStab1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTipoStab1"));
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
