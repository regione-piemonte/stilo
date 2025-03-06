/**
 * MaterialeCostiero.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MaterialeCostiero  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String arrotondamento;

    private java.lang.String colore;

    private java.lang.String granulometria;

    private java.lang.Integer idMateriale;

    private java.lang.String mineralogia;

    public MaterialeCostiero() {
    }

    public MaterialeCostiero(
           java.lang.String arrotondamento,
           java.lang.String colore,
           java.lang.String granulometria,
           java.lang.Integer idMateriale,
           java.lang.String mineralogia) {
        this.arrotondamento = arrotondamento;
        this.colore = colore;
        this.granulometria = granulometria;
        this.idMateriale = idMateriale;
        this.mineralogia = mineralogia;
    }


    /**
     * Gets the arrotondamento value for this MaterialeCostiero.
     * 
     * @return arrotondamento
     */
    public java.lang.String getArrotondamento() {
        return arrotondamento;
    }


    /**
     * Sets the arrotondamento value for this MaterialeCostiero.
     * 
     * @param arrotondamento
     */
    public void setArrotondamento(java.lang.String arrotondamento) {
        this.arrotondamento = arrotondamento;
    }


    /**
     * Gets the colore value for this MaterialeCostiero.
     * 
     * @return colore
     */
    public java.lang.String getColore() {
        return colore;
    }


    /**
     * Sets the colore value for this MaterialeCostiero.
     * 
     * @param colore
     */
    public void setColore(java.lang.String colore) {
        this.colore = colore;
    }


    /**
     * Gets the granulometria value for this MaterialeCostiero.
     * 
     * @return granulometria
     */
    public java.lang.String getGranulometria() {
        return granulometria;
    }


    /**
     * Sets the granulometria value for this MaterialeCostiero.
     * 
     * @param granulometria
     */
    public void setGranulometria(java.lang.String granulometria) {
        this.granulometria = granulometria;
    }


    /**
     * Gets the idMateriale value for this MaterialeCostiero.
     * 
     * @return idMateriale
     */
    public java.lang.Integer getIdMateriale() {
        return idMateriale;
    }


    /**
     * Sets the idMateriale value for this MaterialeCostiero.
     * 
     * @param idMateriale
     */
    public void setIdMateriale(java.lang.Integer idMateriale) {
        this.idMateriale = idMateriale;
    }


    /**
     * Gets the mineralogia value for this MaterialeCostiero.
     * 
     * @return mineralogia
     */
    public java.lang.String getMineralogia() {
        return mineralogia;
    }


    /**
     * Sets the mineralogia value for this MaterialeCostiero.
     * 
     * @param mineralogia
     */
    public void setMineralogia(java.lang.String mineralogia) {
        this.mineralogia = mineralogia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MaterialeCostiero)) return false;
        MaterialeCostiero other = (MaterialeCostiero) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.arrotondamento==null && other.getArrotondamento()==null) || 
             (this.arrotondamento!=null &&
              this.arrotondamento.equals(other.getArrotondamento()))) &&
            ((this.colore==null && other.getColore()==null) || 
             (this.colore!=null &&
              this.colore.equals(other.getColore()))) &&
            ((this.granulometria==null && other.getGranulometria()==null) || 
             (this.granulometria!=null &&
              this.granulometria.equals(other.getGranulometria()))) &&
            ((this.idMateriale==null && other.getIdMateriale()==null) || 
             (this.idMateriale!=null &&
              this.idMateriale.equals(other.getIdMateriale()))) &&
            ((this.mineralogia==null && other.getMineralogia()==null) || 
             (this.mineralogia!=null &&
              this.mineralogia.equals(other.getMineralogia())));
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
        if (getArrotondamento() != null) {
            _hashCode += getArrotondamento().hashCode();
        }
        if (getColore() != null) {
            _hashCode += getColore().hashCode();
        }
        if (getGranulometria() != null) {
            _hashCode += getGranulometria().hashCode();
        }
        if (getIdMateriale() != null) {
            _hashCode += getIdMateriale().hashCode();
        }
        if (getMineralogia() != null) {
            _hashCode += getMineralogia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MaterialeCostiero.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "materialeCostiero"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("arrotondamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "arrotondamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("colore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "colore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("granulometria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "granulometria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMateriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMateriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mineralogia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mineralogia"));
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
