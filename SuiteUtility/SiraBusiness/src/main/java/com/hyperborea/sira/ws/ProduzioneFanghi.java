/**
 * ProduzioneFanghi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProduzioneFanghi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String attrezzature;

    private java.lang.String descrizioneCiclo;

    private java.lang.Integer idCcost;

    private java.lang.String luogoRegistro;

    private java.lang.String sistemaCondizionamento;

    private com.hyperborea.sira.ws.StoccaggioFanghi[] stoccaggioFanghis;

    public ProduzioneFanghi() {
    }

    public ProduzioneFanghi(
           java.lang.String attrezzature,
           java.lang.String descrizioneCiclo,
           java.lang.Integer idCcost,
           java.lang.String luogoRegistro,
           java.lang.String sistemaCondizionamento,
           com.hyperborea.sira.ws.StoccaggioFanghi[] stoccaggioFanghis) {
        this.attrezzature = attrezzature;
        this.descrizioneCiclo = descrizioneCiclo;
        this.idCcost = idCcost;
        this.luogoRegistro = luogoRegistro;
        this.sistemaCondizionamento = sistemaCondizionamento;
        this.stoccaggioFanghis = stoccaggioFanghis;
    }


    /**
     * Gets the attrezzature value for this ProduzioneFanghi.
     * 
     * @return attrezzature
     */
    public java.lang.String getAttrezzature() {
        return attrezzature;
    }


    /**
     * Sets the attrezzature value for this ProduzioneFanghi.
     * 
     * @param attrezzature
     */
    public void setAttrezzature(java.lang.String attrezzature) {
        this.attrezzature = attrezzature;
    }


    /**
     * Gets the descrizioneCiclo value for this ProduzioneFanghi.
     * 
     * @return descrizioneCiclo
     */
    public java.lang.String getDescrizioneCiclo() {
        return descrizioneCiclo;
    }


    /**
     * Sets the descrizioneCiclo value for this ProduzioneFanghi.
     * 
     * @param descrizioneCiclo
     */
    public void setDescrizioneCiclo(java.lang.String descrizioneCiclo) {
        this.descrizioneCiclo = descrizioneCiclo;
    }


    /**
     * Gets the idCcost value for this ProduzioneFanghi.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this ProduzioneFanghi.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the luogoRegistro value for this ProduzioneFanghi.
     * 
     * @return luogoRegistro
     */
    public java.lang.String getLuogoRegistro() {
        return luogoRegistro;
    }


    /**
     * Sets the luogoRegistro value for this ProduzioneFanghi.
     * 
     * @param luogoRegistro
     */
    public void setLuogoRegistro(java.lang.String luogoRegistro) {
        this.luogoRegistro = luogoRegistro;
    }


    /**
     * Gets the sistemaCondizionamento value for this ProduzioneFanghi.
     * 
     * @return sistemaCondizionamento
     */
    public java.lang.String getSistemaCondizionamento() {
        return sistemaCondizionamento;
    }


    /**
     * Sets the sistemaCondizionamento value for this ProduzioneFanghi.
     * 
     * @param sistemaCondizionamento
     */
    public void setSistemaCondizionamento(java.lang.String sistemaCondizionamento) {
        this.sistemaCondizionamento = sistemaCondizionamento;
    }


    /**
     * Gets the stoccaggioFanghis value for this ProduzioneFanghi.
     * 
     * @return stoccaggioFanghis
     */
    public com.hyperborea.sira.ws.StoccaggioFanghi[] getStoccaggioFanghis() {
        return stoccaggioFanghis;
    }


    /**
     * Sets the stoccaggioFanghis value for this ProduzioneFanghi.
     * 
     * @param stoccaggioFanghis
     */
    public void setStoccaggioFanghis(com.hyperborea.sira.ws.StoccaggioFanghi[] stoccaggioFanghis) {
        this.stoccaggioFanghis = stoccaggioFanghis;
    }

    public com.hyperborea.sira.ws.StoccaggioFanghi getStoccaggioFanghis(int i) {
        return this.stoccaggioFanghis[i];
    }

    public void setStoccaggioFanghis(int i, com.hyperborea.sira.ws.StoccaggioFanghi _value) {
        this.stoccaggioFanghis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProduzioneFanghi)) return false;
        ProduzioneFanghi other = (ProduzioneFanghi) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attrezzature==null && other.getAttrezzature()==null) || 
             (this.attrezzature!=null &&
              this.attrezzature.equals(other.getAttrezzature()))) &&
            ((this.descrizioneCiclo==null && other.getDescrizioneCiclo()==null) || 
             (this.descrizioneCiclo!=null &&
              this.descrizioneCiclo.equals(other.getDescrizioneCiclo()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.luogoRegistro==null && other.getLuogoRegistro()==null) || 
             (this.luogoRegistro!=null &&
              this.luogoRegistro.equals(other.getLuogoRegistro()))) &&
            ((this.sistemaCondizionamento==null && other.getSistemaCondizionamento()==null) || 
             (this.sistemaCondizionamento!=null &&
              this.sistemaCondizionamento.equals(other.getSistemaCondizionamento()))) &&
            ((this.stoccaggioFanghis==null && other.getStoccaggioFanghis()==null) || 
             (this.stoccaggioFanghis!=null &&
              java.util.Arrays.equals(this.stoccaggioFanghis, other.getStoccaggioFanghis())));
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
        if (getAttrezzature() != null) {
            _hashCode += getAttrezzature().hashCode();
        }
        if (getDescrizioneCiclo() != null) {
            _hashCode += getDescrizioneCiclo().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLuogoRegistro() != null) {
            _hashCode += getLuogoRegistro().hashCode();
        }
        if (getSistemaCondizionamento() != null) {
            _hashCode += getSistemaCondizionamento().hashCode();
        }
        if (getStoccaggioFanghis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStoccaggioFanghis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStoccaggioFanghis(), i);
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
        new org.apache.axis.description.TypeDesc(ProduzioneFanghi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioneFanghi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attrezzature");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attrezzature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCiclo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizioneCiclo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("luogoRegistro");
        elemField.setXmlName(new javax.xml.namespace.QName("", "luogoRegistro"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemaCondizionamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemaCondizionamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stoccaggioFanghis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stoccaggioFanghis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "stoccaggioFanghi"));
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
