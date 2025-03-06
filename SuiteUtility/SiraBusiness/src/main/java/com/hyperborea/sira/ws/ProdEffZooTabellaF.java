/**
 * ProdEffZooTabellaF.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaF  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar concessioneData;

    private java.lang.Integer concessioneNumero;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.ProdEffZooTabellaFDati[] prodEffZooTabellaFDatis;

    private float totVolumeFranco;

    private java.lang.Float totVolumeNecessario;

    private java.lang.Float volumeDaRealizzare;

    private java.lang.Float volumeDisponibile;

    private java.lang.Float volumeInRealizzazione;

    public ProdEffZooTabellaF() {
    }

    public ProdEffZooTabellaF(
           java.util.Calendar concessioneData,
           java.lang.Integer concessioneNumero,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.ProdEffZooTabellaFDati[] prodEffZooTabellaFDatis,
           float totVolumeFranco,
           java.lang.Float totVolumeNecessario,
           java.lang.Float volumeDaRealizzare,
           java.lang.Float volumeDisponibile,
           java.lang.Float volumeInRealizzazione) {
        this.concessioneData = concessioneData;
        this.concessioneNumero = concessioneNumero;
        this.idCcost = idCcost;
        this.prodEffZooTabellaFDatis = prodEffZooTabellaFDatis;
        this.totVolumeFranco = totVolumeFranco;
        this.totVolumeNecessario = totVolumeNecessario;
        this.volumeDaRealizzare = volumeDaRealizzare;
        this.volumeDisponibile = volumeDisponibile;
        this.volumeInRealizzazione = volumeInRealizzazione;
    }


    /**
     * Gets the concessioneData value for this ProdEffZooTabellaF.
     * 
     * @return concessioneData
     */
    public java.util.Calendar getConcessioneData() {
        return concessioneData;
    }


    /**
     * Sets the concessioneData value for this ProdEffZooTabellaF.
     * 
     * @param concessioneData
     */
    public void setConcessioneData(java.util.Calendar concessioneData) {
        this.concessioneData = concessioneData;
    }


    /**
     * Gets the concessioneNumero value for this ProdEffZooTabellaF.
     * 
     * @return concessioneNumero
     */
    public java.lang.Integer getConcessioneNumero() {
        return concessioneNumero;
    }


    /**
     * Sets the concessioneNumero value for this ProdEffZooTabellaF.
     * 
     * @param concessioneNumero
     */
    public void setConcessioneNumero(java.lang.Integer concessioneNumero) {
        this.concessioneNumero = concessioneNumero;
    }


    /**
     * Gets the idCcost value for this ProdEffZooTabellaF.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this ProdEffZooTabellaF.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the prodEffZooTabellaFDatis value for this ProdEffZooTabellaF.
     * 
     * @return prodEffZooTabellaFDatis
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaFDati[] getProdEffZooTabellaFDatis() {
        return prodEffZooTabellaFDatis;
    }


    /**
     * Sets the prodEffZooTabellaFDatis value for this ProdEffZooTabellaF.
     * 
     * @param prodEffZooTabellaFDatis
     */
    public void setProdEffZooTabellaFDatis(com.hyperborea.sira.ws.ProdEffZooTabellaFDati[] prodEffZooTabellaFDatis) {
        this.prodEffZooTabellaFDatis = prodEffZooTabellaFDatis;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaFDati getProdEffZooTabellaFDatis(int i) {
        return this.prodEffZooTabellaFDatis[i];
    }

    public void setProdEffZooTabellaFDatis(int i, com.hyperborea.sira.ws.ProdEffZooTabellaFDati _value) {
        this.prodEffZooTabellaFDatis[i] = _value;
    }


    /**
     * Gets the totVolumeFranco value for this ProdEffZooTabellaF.
     * 
     * @return totVolumeFranco
     */
    public float getTotVolumeFranco() {
        return totVolumeFranco;
    }


    /**
     * Sets the totVolumeFranco value for this ProdEffZooTabellaF.
     * 
     * @param totVolumeFranco
     */
    public void setTotVolumeFranco(float totVolumeFranco) {
        this.totVolumeFranco = totVolumeFranco;
    }


    /**
     * Gets the totVolumeNecessario value for this ProdEffZooTabellaF.
     * 
     * @return totVolumeNecessario
     */
    public java.lang.Float getTotVolumeNecessario() {
        return totVolumeNecessario;
    }


    /**
     * Sets the totVolumeNecessario value for this ProdEffZooTabellaF.
     * 
     * @param totVolumeNecessario
     */
    public void setTotVolumeNecessario(java.lang.Float totVolumeNecessario) {
        this.totVolumeNecessario = totVolumeNecessario;
    }


    /**
     * Gets the volumeDaRealizzare value for this ProdEffZooTabellaF.
     * 
     * @return volumeDaRealizzare
     */
    public java.lang.Float getVolumeDaRealizzare() {
        return volumeDaRealizzare;
    }


    /**
     * Sets the volumeDaRealizzare value for this ProdEffZooTabellaF.
     * 
     * @param volumeDaRealizzare
     */
    public void setVolumeDaRealizzare(java.lang.Float volumeDaRealizzare) {
        this.volumeDaRealizzare = volumeDaRealizzare;
    }


    /**
     * Gets the volumeDisponibile value for this ProdEffZooTabellaF.
     * 
     * @return volumeDisponibile
     */
    public java.lang.Float getVolumeDisponibile() {
        return volumeDisponibile;
    }


    /**
     * Sets the volumeDisponibile value for this ProdEffZooTabellaF.
     * 
     * @param volumeDisponibile
     */
    public void setVolumeDisponibile(java.lang.Float volumeDisponibile) {
        this.volumeDisponibile = volumeDisponibile;
    }


    /**
     * Gets the volumeInRealizzazione value for this ProdEffZooTabellaF.
     * 
     * @return volumeInRealizzazione
     */
    public java.lang.Float getVolumeInRealizzazione() {
        return volumeInRealizzazione;
    }


    /**
     * Sets the volumeInRealizzazione value for this ProdEffZooTabellaF.
     * 
     * @param volumeInRealizzazione
     */
    public void setVolumeInRealizzazione(java.lang.Float volumeInRealizzazione) {
        this.volumeInRealizzazione = volumeInRealizzazione;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaF)) return false;
        ProdEffZooTabellaF other = (ProdEffZooTabellaF) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.concessioneData==null && other.getConcessioneData()==null) || 
             (this.concessioneData!=null &&
              this.concessioneData.equals(other.getConcessioneData()))) &&
            ((this.concessioneNumero==null && other.getConcessioneNumero()==null) || 
             (this.concessioneNumero!=null &&
              this.concessioneNumero.equals(other.getConcessioneNumero()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.prodEffZooTabellaFDatis==null && other.getProdEffZooTabellaFDatis()==null) || 
             (this.prodEffZooTabellaFDatis!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaFDatis, other.getProdEffZooTabellaFDatis()))) &&
            this.totVolumeFranco == other.getTotVolumeFranco() &&
            ((this.totVolumeNecessario==null && other.getTotVolumeNecessario()==null) || 
             (this.totVolumeNecessario!=null &&
              this.totVolumeNecessario.equals(other.getTotVolumeNecessario()))) &&
            ((this.volumeDaRealizzare==null && other.getVolumeDaRealizzare()==null) || 
             (this.volumeDaRealizzare!=null &&
              this.volumeDaRealizzare.equals(other.getVolumeDaRealizzare()))) &&
            ((this.volumeDisponibile==null && other.getVolumeDisponibile()==null) || 
             (this.volumeDisponibile!=null &&
              this.volumeDisponibile.equals(other.getVolumeDisponibile()))) &&
            ((this.volumeInRealizzazione==null && other.getVolumeInRealizzazione()==null) || 
             (this.volumeInRealizzazione!=null &&
              this.volumeInRealizzazione.equals(other.getVolumeInRealizzazione())));
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
        if (getConcessioneData() != null) {
            _hashCode += getConcessioneData().hashCode();
        }
        if (getConcessioneNumero() != null) {
            _hashCode += getConcessioneNumero().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getProdEffZooTabellaFDatis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaFDatis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaFDatis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += new Float(getTotVolumeFranco()).hashCode();
        if (getTotVolumeNecessario() != null) {
            _hashCode += getTotVolumeNecessario().hashCode();
        }
        if (getVolumeDaRealizzare() != null) {
            _hashCode += getVolumeDaRealizzare().hashCode();
        }
        if (getVolumeDisponibile() != null) {
            _hashCode += getVolumeDisponibile().hashCode();
        }
        if (getVolumeInRealizzazione() != null) {
            _hashCode += getVolumeInRealizzazione().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaF.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaF"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concessioneData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concessioneData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concessioneNumero");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concessioneNumero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("prodEffZooTabellaFDatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaFDatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaFDati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totVolumeFranco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totVolumeFranco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totVolumeNecessario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "totVolumeNecessario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeDaRealizzare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeDaRealizzare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeDisponibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeDisponibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeInRealizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeInRealizzazione"));
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
