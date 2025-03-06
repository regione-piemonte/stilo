/**
 * ProdEffZooTabellaD.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaD  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar concessioneData;

    private java.lang.Integer concessioneNumero;

    private java.lang.Integer idCcost;

    private java.lang.Float plateaDaRealizzare;

    private java.lang.Float plateaDisponibile;

    private java.lang.Float plateaInRealizzazione;

    private java.lang.Float plateaTotNecessaria;

    private com.hyperborea.sira.ws.ProdEffZooTabellaDDati[] prodEffZooTabellaDDatis;

    public ProdEffZooTabellaD() {
    }

    public ProdEffZooTabellaD(
           java.util.Calendar concessioneData,
           java.lang.Integer concessioneNumero,
           java.lang.Integer idCcost,
           java.lang.Float plateaDaRealizzare,
           java.lang.Float plateaDisponibile,
           java.lang.Float plateaInRealizzazione,
           java.lang.Float plateaTotNecessaria,
           com.hyperborea.sira.ws.ProdEffZooTabellaDDati[] prodEffZooTabellaDDatis) {
        this.concessioneData = concessioneData;
        this.concessioneNumero = concessioneNumero;
        this.idCcost = idCcost;
        this.plateaDaRealizzare = plateaDaRealizzare;
        this.plateaDisponibile = plateaDisponibile;
        this.plateaInRealizzazione = plateaInRealizzazione;
        this.plateaTotNecessaria = plateaTotNecessaria;
        this.prodEffZooTabellaDDatis = prodEffZooTabellaDDatis;
    }


    /**
     * Gets the concessioneData value for this ProdEffZooTabellaD.
     * 
     * @return concessioneData
     */
    public java.util.Calendar getConcessioneData() {
        return concessioneData;
    }


    /**
     * Sets the concessioneData value for this ProdEffZooTabellaD.
     * 
     * @param concessioneData
     */
    public void setConcessioneData(java.util.Calendar concessioneData) {
        this.concessioneData = concessioneData;
    }


    /**
     * Gets the concessioneNumero value for this ProdEffZooTabellaD.
     * 
     * @return concessioneNumero
     */
    public java.lang.Integer getConcessioneNumero() {
        return concessioneNumero;
    }


    /**
     * Sets the concessioneNumero value for this ProdEffZooTabellaD.
     * 
     * @param concessioneNumero
     */
    public void setConcessioneNumero(java.lang.Integer concessioneNumero) {
        this.concessioneNumero = concessioneNumero;
    }


    /**
     * Gets the idCcost value for this ProdEffZooTabellaD.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this ProdEffZooTabellaD.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the plateaDaRealizzare value for this ProdEffZooTabellaD.
     * 
     * @return plateaDaRealizzare
     */
    public java.lang.Float getPlateaDaRealizzare() {
        return plateaDaRealizzare;
    }


    /**
     * Sets the plateaDaRealizzare value for this ProdEffZooTabellaD.
     * 
     * @param plateaDaRealizzare
     */
    public void setPlateaDaRealizzare(java.lang.Float plateaDaRealizzare) {
        this.plateaDaRealizzare = plateaDaRealizzare;
    }


    /**
     * Gets the plateaDisponibile value for this ProdEffZooTabellaD.
     * 
     * @return plateaDisponibile
     */
    public java.lang.Float getPlateaDisponibile() {
        return plateaDisponibile;
    }


    /**
     * Sets the plateaDisponibile value for this ProdEffZooTabellaD.
     * 
     * @param plateaDisponibile
     */
    public void setPlateaDisponibile(java.lang.Float plateaDisponibile) {
        this.plateaDisponibile = plateaDisponibile;
    }


    /**
     * Gets the plateaInRealizzazione value for this ProdEffZooTabellaD.
     * 
     * @return plateaInRealizzazione
     */
    public java.lang.Float getPlateaInRealizzazione() {
        return plateaInRealizzazione;
    }


    /**
     * Sets the plateaInRealizzazione value for this ProdEffZooTabellaD.
     * 
     * @param plateaInRealizzazione
     */
    public void setPlateaInRealizzazione(java.lang.Float plateaInRealizzazione) {
        this.plateaInRealizzazione = plateaInRealizzazione;
    }


    /**
     * Gets the plateaTotNecessaria value for this ProdEffZooTabellaD.
     * 
     * @return plateaTotNecessaria
     */
    public java.lang.Float getPlateaTotNecessaria() {
        return plateaTotNecessaria;
    }


    /**
     * Sets the plateaTotNecessaria value for this ProdEffZooTabellaD.
     * 
     * @param plateaTotNecessaria
     */
    public void setPlateaTotNecessaria(java.lang.Float plateaTotNecessaria) {
        this.plateaTotNecessaria = plateaTotNecessaria;
    }


    /**
     * Gets the prodEffZooTabellaDDatis value for this ProdEffZooTabellaD.
     * 
     * @return prodEffZooTabellaDDatis
     */
    public com.hyperborea.sira.ws.ProdEffZooTabellaDDati[] getProdEffZooTabellaDDatis() {
        return prodEffZooTabellaDDatis;
    }


    /**
     * Sets the prodEffZooTabellaDDatis value for this ProdEffZooTabellaD.
     * 
     * @param prodEffZooTabellaDDatis
     */
    public void setProdEffZooTabellaDDatis(com.hyperborea.sira.ws.ProdEffZooTabellaDDati[] prodEffZooTabellaDDatis) {
        this.prodEffZooTabellaDDatis = prodEffZooTabellaDDatis;
    }

    public com.hyperborea.sira.ws.ProdEffZooTabellaDDati getProdEffZooTabellaDDatis(int i) {
        return this.prodEffZooTabellaDDatis[i];
    }

    public void setProdEffZooTabellaDDatis(int i, com.hyperborea.sira.ws.ProdEffZooTabellaDDati _value) {
        this.prodEffZooTabellaDDatis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaD)) return false;
        ProdEffZooTabellaD other = (ProdEffZooTabellaD) obj;
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
            ((this.plateaDaRealizzare==null && other.getPlateaDaRealizzare()==null) || 
             (this.plateaDaRealizzare!=null &&
              this.plateaDaRealizzare.equals(other.getPlateaDaRealizzare()))) &&
            ((this.plateaDisponibile==null && other.getPlateaDisponibile()==null) || 
             (this.plateaDisponibile!=null &&
              this.plateaDisponibile.equals(other.getPlateaDisponibile()))) &&
            ((this.plateaInRealizzazione==null && other.getPlateaInRealizzazione()==null) || 
             (this.plateaInRealizzazione!=null &&
              this.plateaInRealizzazione.equals(other.getPlateaInRealizzazione()))) &&
            ((this.plateaTotNecessaria==null && other.getPlateaTotNecessaria()==null) || 
             (this.plateaTotNecessaria!=null &&
              this.plateaTotNecessaria.equals(other.getPlateaTotNecessaria()))) &&
            ((this.prodEffZooTabellaDDatis==null && other.getProdEffZooTabellaDDatis()==null) || 
             (this.prodEffZooTabellaDDatis!=null &&
              java.util.Arrays.equals(this.prodEffZooTabellaDDatis, other.getProdEffZooTabellaDDatis())));
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
        if (getPlateaDaRealizzare() != null) {
            _hashCode += getPlateaDaRealizzare().hashCode();
        }
        if (getPlateaDisponibile() != null) {
            _hashCode += getPlateaDisponibile().hashCode();
        }
        if (getPlateaInRealizzazione() != null) {
            _hashCode += getPlateaInRealizzazione().hashCode();
        }
        if (getPlateaTotNecessaria() != null) {
            _hashCode += getPlateaTotNecessaria().hashCode();
        }
        if (getProdEffZooTabellaDDatis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdEffZooTabellaDDatis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdEffZooTabellaDDatis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaD.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaD"));
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
        elemField.setFieldName("plateaDaRealizzare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateaDaRealizzare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateaDisponibile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateaDisponibile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateaInRealizzazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateaInRealizzazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("plateaTotNecessaria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "plateaTotNecessaria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooTabellaDDatis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooTabellaDDatis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaDDati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
