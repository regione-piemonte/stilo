/**
 * CcostCostaAntropizzata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostCostaAntropizzata  implements java.io.Serializable {
    private java.lang.Integer formazioneTombolo;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.RipascimentoAreaCostiera[] ripascimentoAreaCostieras;

    private com.hyperborea.sira.ws.VocClassCAntr vocClassCAntr;

    private com.hyperborea.sira.ws.VocMaterialeCAntr vocMaterialeCAntr;

    private com.hyperborea.sira.ws.VocTipo1CAntr vocTipo1CAntr;

    private com.hyperborea.sira.ws.VocTipo2CAntr vocTipo2CAntr;

    private com.hyperborea.sira.ws.VocTipo3CAntr vocTipo3CAntr;

    private com.hyperborea.sira.ws.VocTipo4CAntr vocTipo4CAntr;

    private com.hyperborea.sira.ws.VocTipo5CAntr vocTipo5CAntr;

    private com.hyperborea.sira.ws.VocTipo6CAntr vocTipo6CAntr;

    public CcostCostaAntropizzata() {
    }

    public CcostCostaAntropizzata(
           java.lang.Integer formazioneTombolo,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.RipascimentoAreaCostiera[] ripascimentoAreaCostieras,
           com.hyperborea.sira.ws.VocClassCAntr vocClassCAntr,
           com.hyperborea.sira.ws.VocMaterialeCAntr vocMaterialeCAntr,
           com.hyperborea.sira.ws.VocTipo1CAntr vocTipo1CAntr,
           com.hyperborea.sira.ws.VocTipo2CAntr vocTipo2CAntr,
           com.hyperborea.sira.ws.VocTipo3CAntr vocTipo3CAntr,
           com.hyperborea.sira.ws.VocTipo4CAntr vocTipo4CAntr,
           com.hyperborea.sira.ws.VocTipo5CAntr vocTipo5CAntr,
           com.hyperborea.sira.ws.VocTipo6CAntr vocTipo6CAntr) {
           this.formazioneTombolo = formazioneTombolo;
           this.idCcost = idCcost;
           this.ripascimentoAreaCostieras = ripascimentoAreaCostieras;
           this.vocClassCAntr = vocClassCAntr;
           this.vocMaterialeCAntr = vocMaterialeCAntr;
           this.vocTipo1CAntr = vocTipo1CAntr;
           this.vocTipo2CAntr = vocTipo2CAntr;
           this.vocTipo3CAntr = vocTipo3CAntr;
           this.vocTipo4CAntr = vocTipo4CAntr;
           this.vocTipo5CAntr = vocTipo5CAntr;
           this.vocTipo6CAntr = vocTipo6CAntr;
    }


    /**
     * Gets the formazioneTombolo value for this CcostCostaAntropizzata.
     * 
     * @return formazioneTombolo
     */
    public java.lang.Integer getFormazioneTombolo() {
        return formazioneTombolo;
    }


    /**
     * Sets the formazioneTombolo value for this CcostCostaAntropizzata.
     * 
     * @param formazioneTombolo
     */
    public void setFormazioneTombolo(java.lang.Integer formazioneTombolo) {
        this.formazioneTombolo = formazioneTombolo;
    }


    /**
     * Gets the idCcost value for this CcostCostaAntropizzata.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostCostaAntropizzata.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the ripascimentoAreaCostieras value for this CcostCostaAntropizzata.
     * 
     * @return ripascimentoAreaCostieras
     */
    public com.hyperborea.sira.ws.RipascimentoAreaCostiera[] getRipascimentoAreaCostieras() {
        return ripascimentoAreaCostieras;
    }


    /**
     * Sets the ripascimentoAreaCostieras value for this CcostCostaAntropizzata.
     * 
     * @param ripascimentoAreaCostieras
     */
    public void setRipascimentoAreaCostieras(com.hyperborea.sira.ws.RipascimentoAreaCostiera[] ripascimentoAreaCostieras) {
        this.ripascimentoAreaCostieras = ripascimentoAreaCostieras;
    }

    public com.hyperborea.sira.ws.RipascimentoAreaCostiera getRipascimentoAreaCostieras(int i) {
        return this.ripascimentoAreaCostieras[i];
    }

    public void setRipascimentoAreaCostieras(int i, com.hyperborea.sira.ws.RipascimentoAreaCostiera _value) {
        this.ripascimentoAreaCostieras[i] = _value;
    }


    /**
     * Gets the vocClassCAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocClassCAntr
     */
    public com.hyperborea.sira.ws.VocClassCAntr getVocClassCAntr() {
        return vocClassCAntr;
    }


    /**
     * Sets the vocClassCAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocClassCAntr
     */
    public void setVocClassCAntr(com.hyperborea.sira.ws.VocClassCAntr vocClassCAntr) {
        this.vocClassCAntr = vocClassCAntr;
    }


    /**
     * Gets the vocMaterialeCAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocMaterialeCAntr
     */
    public com.hyperborea.sira.ws.VocMaterialeCAntr getVocMaterialeCAntr() {
        return vocMaterialeCAntr;
    }


    /**
     * Sets the vocMaterialeCAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocMaterialeCAntr
     */
    public void setVocMaterialeCAntr(com.hyperborea.sira.ws.VocMaterialeCAntr vocMaterialeCAntr) {
        this.vocMaterialeCAntr = vocMaterialeCAntr;
    }


    /**
     * Gets the vocTipo1CAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocTipo1CAntr
     */
    public com.hyperborea.sira.ws.VocTipo1CAntr getVocTipo1CAntr() {
        return vocTipo1CAntr;
    }


    /**
     * Sets the vocTipo1CAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocTipo1CAntr
     */
    public void setVocTipo1CAntr(com.hyperborea.sira.ws.VocTipo1CAntr vocTipo1CAntr) {
        this.vocTipo1CAntr = vocTipo1CAntr;
    }


    /**
     * Gets the vocTipo2CAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocTipo2CAntr
     */
    public com.hyperborea.sira.ws.VocTipo2CAntr getVocTipo2CAntr() {
        return vocTipo2CAntr;
    }


    /**
     * Sets the vocTipo2CAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocTipo2CAntr
     */
    public void setVocTipo2CAntr(com.hyperborea.sira.ws.VocTipo2CAntr vocTipo2CAntr) {
        this.vocTipo2CAntr = vocTipo2CAntr;
    }


    /**
     * Gets the vocTipo3CAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocTipo3CAntr
     */
    public com.hyperborea.sira.ws.VocTipo3CAntr getVocTipo3CAntr() {
        return vocTipo3CAntr;
    }


    /**
     * Sets the vocTipo3CAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocTipo3CAntr
     */
    public void setVocTipo3CAntr(com.hyperborea.sira.ws.VocTipo3CAntr vocTipo3CAntr) {
        this.vocTipo3CAntr = vocTipo3CAntr;
    }


    /**
     * Gets the vocTipo4CAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocTipo4CAntr
     */
    public com.hyperborea.sira.ws.VocTipo4CAntr getVocTipo4CAntr() {
        return vocTipo4CAntr;
    }


    /**
     * Sets the vocTipo4CAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocTipo4CAntr
     */
    public void setVocTipo4CAntr(com.hyperborea.sira.ws.VocTipo4CAntr vocTipo4CAntr) {
        this.vocTipo4CAntr = vocTipo4CAntr;
    }


    /**
     * Gets the vocTipo5CAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocTipo5CAntr
     */
    public com.hyperborea.sira.ws.VocTipo5CAntr getVocTipo5CAntr() {
        return vocTipo5CAntr;
    }


    /**
     * Sets the vocTipo5CAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocTipo5CAntr
     */
    public void setVocTipo5CAntr(com.hyperborea.sira.ws.VocTipo5CAntr vocTipo5CAntr) {
        this.vocTipo5CAntr = vocTipo5CAntr;
    }


    /**
     * Gets the vocTipo6CAntr value for this CcostCostaAntropizzata.
     * 
     * @return vocTipo6CAntr
     */
    public com.hyperborea.sira.ws.VocTipo6CAntr getVocTipo6CAntr() {
        return vocTipo6CAntr;
    }


    /**
     * Sets the vocTipo6CAntr value for this CcostCostaAntropizzata.
     * 
     * @param vocTipo6CAntr
     */
    public void setVocTipo6CAntr(com.hyperborea.sira.ws.VocTipo6CAntr vocTipo6CAntr) {
        this.vocTipo6CAntr = vocTipo6CAntr;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostCostaAntropizzata)) return false;
        CcostCostaAntropizzata other = (CcostCostaAntropizzata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.formazioneTombolo==null && other.getFormazioneTombolo()==null) || 
             (this.formazioneTombolo!=null &&
              this.formazioneTombolo.equals(other.getFormazioneTombolo()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.ripascimentoAreaCostieras==null && other.getRipascimentoAreaCostieras()==null) || 
             (this.ripascimentoAreaCostieras!=null &&
              java.util.Arrays.equals(this.ripascimentoAreaCostieras, other.getRipascimentoAreaCostieras()))) &&
            ((this.vocClassCAntr==null && other.getVocClassCAntr()==null) || 
             (this.vocClassCAntr!=null &&
              this.vocClassCAntr.equals(other.getVocClassCAntr()))) &&
            ((this.vocMaterialeCAntr==null && other.getVocMaterialeCAntr()==null) || 
             (this.vocMaterialeCAntr!=null &&
              this.vocMaterialeCAntr.equals(other.getVocMaterialeCAntr()))) &&
            ((this.vocTipo1CAntr==null && other.getVocTipo1CAntr()==null) || 
             (this.vocTipo1CAntr!=null &&
              this.vocTipo1CAntr.equals(other.getVocTipo1CAntr()))) &&
            ((this.vocTipo2CAntr==null && other.getVocTipo2CAntr()==null) || 
             (this.vocTipo2CAntr!=null &&
              this.vocTipo2CAntr.equals(other.getVocTipo2CAntr()))) &&
            ((this.vocTipo3CAntr==null && other.getVocTipo3CAntr()==null) || 
             (this.vocTipo3CAntr!=null &&
              this.vocTipo3CAntr.equals(other.getVocTipo3CAntr()))) &&
            ((this.vocTipo4CAntr==null && other.getVocTipo4CAntr()==null) || 
             (this.vocTipo4CAntr!=null &&
              this.vocTipo4CAntr.equals(other.getVocTipo4CAntr()))) &&
            ((this.vocTipo5CAntr==null && other.getVocTipo5CAntr()==null) || 
             (this.vocTipo5CAntr!=null &&
              this.vocTipo5CAntr.equals(other.getVocTipo5CAntr()))) &&
            ((this.vocTipo6CAntr==null && other.getVocTipo6CAntr()==null) || 
             (this.vocTipo6CAntr!=null &&
              this.vocTipo6CAntr.equals(other.getVocTipo6CAntr())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFormazioneTombolo() != null) {
            _hashCode += getFormazioneTombolo().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getRipascimentoAreaCostieras() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRipascimentoAreaCostieras());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRipascimentoAreaCostieras(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVocClassCAntr() != null) {
            _hashCode += getVocClassCAntr().hashCode();
        }
        if (getVocMaterialeCAntr() != null) {
            _hashCode += getVocMaterialeCAntr().hashCode();
        }
        if (getVocTipo1CAntr() != null) {
            _hashCode += getVocTipo1CAntr().hashCode();
        }
        if (getVocTipo2CAntr() != null) {
            _hashCode += getVocTipo2CAntr().hashCode();
        }
        if (getVocTipo3CAntr() != null) {
            _hashCode += getVocTipo3CAntr().hashCode();
        }
        if (getVocTipo4CAntr() != null) {
            _hashCode += getVocTipo4CAntr().hashCode();
        }
        if (getVocTipo5CAntr() != null) {
            _hashCode += getVocTipo5CAntr().hashCode();
        }
        if (getVocTipo6CAntr() != null) {
            _hashCode += getVocTipo6CAntr().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostCostaAntropizzata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCostaAntropizzata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formazioneTombolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formazioneTombolo"));
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
        elemField.setFieldName("ripascimentoAreaCostieras");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ripascimentoAreaCostieras"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ripascimentoAreaCostiera"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClassCAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClassCAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassCAntr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocMaterialeCAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocMaterialeCAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocMaterialeCAntr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipo1CAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipo1CAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo1CAntr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipo2CAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipo2CAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo2CAntr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipo3CAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipo3CAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo3CAntr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipo4CAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipo4CAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo4CAntr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipo5CAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipo5CAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo5CAntr"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipo6CAntr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipo6CAntr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipo6CAntr"));
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
