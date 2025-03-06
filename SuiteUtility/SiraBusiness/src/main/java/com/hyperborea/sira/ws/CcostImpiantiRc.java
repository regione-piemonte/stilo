/**
 * CcostImpiantiRc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpiantiRc  implements java.io.Serializable {
    private java.lang.Integer amatoriale;

    private java.lang.String codFacoltativo;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.ImpiantiRcTipo impiantiRcTipo;

    private java.lang.String note;

    private com.hyperborea.sira.ws.Sistemiradianti[] sistemiradiantis;

    public CcostImpiantiRc() {
    }

    public CcostImpiantiRc(
           java.lang.Integer amatoriale,
           java.lang.String codFacoltativo,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.ImpiantiRcTipo impiantiRcTipo,
           java.lang.String note,
           com.hyperborea.sira.ws.Sistemiradianti[] sistemiradiantis) {
           this.amatoriale = amatoriale;
           this.codFacoltativo = codFacoltativo;
           this.idCcost = idCcost;
           this.impiantiRcTipo = impiantiRcTipo;
           this.note = note;
           this.sistemiradiantis = sistemiradiantis;
    }


    /**
     * Gets the amatoriale value for this CcostImpiantiRc.
     * 
     * @return amatoriale
     */
    public java.lang.Integer getAmatoriale() {
        return amatoriale;
    }


    /**
     * Sets the amatoriale value for this CcostImpiantiRc.
     * 
     * @param amatoriale
     */
    public void setAmatoriale(java.lang.Integer amatoriale) {
        this.amatoriale = amatoriale;
    }


    /**
     * Gets the codFacoltativo value for this CcostImpiantiRc.
     * 
     * @return codFacoltativo
     */
    public java.lang.String getCodFacoltativo() {
        return codFacoltativo;
    }


    /**
     * Sets the codFacoltativo value for this CcostImpiantiRc.
     * 
     * @param codFacoltativo
     */
    public void setCodFacoltativo(java.lang.String codFacoltativo) {
        this.codFacoltativo = codFacoltativo;
    }


    /**
     * Gets the idCcost value for this CcostImpiantiRc.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpiantiRc.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the impiantiRcTipo value for this CcostImpiantiRc.
     * 
     * @return impiantiRcTipo
     */
    public com.hyperborea.sira.ws.ImpiantiRcTipo getImpiantiRcTipo() {
        return impiantiRcTipo;
    }


    /**
     * Sets the impiantiRcTipo value for this CcostImpiantiRc.
     * 
     * @param impiantiRcTipo
     */
    public void setImpiantiRcTipo(com.hyperborea.sira.ws.ImpiantiRcTipo impiantiRcTipo) {
        this.impiantiRcTipo = impiantiRcTipo;
    }


    /**
     * Gets the note value for this CcostImpiantiRc.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostImpiantiRc.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the sistemiradiantis value for this CcostImpiantiRc.
     * 
     * @return sistemiradiantis
     */
    public com.hyperborea.sira.ws.Sistemiradianti[] getSistemiradiantis() {
        return sistemiradiantis;
    }


    /**
     * Sets the sistemiradiantis value for this CcostImpiantiRc.
     * 
     * @param sistemiradiantis
     */
    public void setSistemiradiantis(com.hyperborea.sira.ws.Sistemiradianti[] sistemiradiantis) {
        this.sistemiradiantis = sistemiradiantis;
    }

    public com.hyperborea.sira.ws.Sistemiradianti getSistemiradiantis(int i) {
        return this.sistemiradiantis[i];
    }

    public void setSistemiradiantis(int i, com.hyperborea.sira.ws.Sistemiradianti _value) {
        this.sistemiradiantis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpiantiRc)) return false;
        CcostImpiantiRc other = (CcostImpiantiRc) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.amatoriale==null && other.getAmatoriale()==null) || 
             (this.amatoriale!=null &&
              this.amatoriale.equals(other.getAmatoriale()))) &&
            ((this.codFacoltativo==null && other.getCodFacoltativo()==null) || 
             (this.codFacoltativo!=null &&
              this.codFacoltativo.equals(other.getCodFacoltativo()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.impiantiRcTipo==null && other.getImpiantiRcTipo()==null) || 
             (this.impiantiRcTipo!=null &&
              this.impiantiRcTipo.equals(other.getImpiantiRcTipo()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.sistemiradiantis==null && other.getSistemiradiantis()==null) || 
             (this.sistemiradiantis!=null &&
              java.util.Arrays.equals(this.sistemiradiantis, other.getSistemiradiantis())));
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
        if (getAmatoriale() != null) {
            _hashCode += getAmatoriale().hashCode();
        }
        if (getCodFacoltativo() != null) {
            _hashCode += getCodFacoltativo().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getImpiantiRcTipo() != null) {
            _hashCode += getImpiantiRcTipo().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getSistemiradiantis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSistemiradiantis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSistemiradiantis(), i);
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
        new org.apache.axis.description.TypeDesc(CcostImpiantiRc.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpiantiRc"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amatoriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "amatoriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codFacoltativo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codFacoltativo"));
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
        elemField.setFieldName("impiantiRcTipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "impiantiRcTipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiRcTipo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemiradiantis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemiradiantis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sistemiradianti"));
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
