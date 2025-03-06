/**
 * OggettiStruttureTerritoriali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class OggettiStruttureTerritoriali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts;

    private java.util.Calendar dataFine;

    private java.util.Calendar dataInizio;

    private java.lang.Integer idOst;

    private com.hyperborea.sira.ws.RelazioniOst[] relazioniOstsForIdOst1;

    private com.hyperborea.sira.ws.RelazioniOst[] relazioniOstsForIdOst2;

    private com.hyperborea.sira.ws.SfOst[] sfOsts;

    private com.hyperborea.sira.ws.StatiOst[] statiOsts;

    public OggettiStruttureTerritoriali() {
    }

    public OggettiStruttureTerritoriali(
           com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts,
           java.util.Calendar dataFine,
           java.util.Calendar dataInizio,
           java.lang.Integer idOst,
           com.hyperborea.sira.ws.RelazioniOst[] relazioniOstsForIdOst1,
           com.hyperborea.sira.ws.RelazioniOst[] relazioniOstsForIdOst2,
           com.hyperborea.sira.ws.SfOst[] sfOsts,
           com.hyperborea.sira.ws.StatiOst[] statiOsts) {
        this.caratterizzazioniOsts = caratterizzazioniOsts;
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
        this.idOst = idOst;
        this.relazioniOstsForIdOst1 = relazioniOstsForIdOst1;
        this.relazioniOstsForIdOst2 = relazioniOstsForIdOst2;
        this.sfOsts = sfOsts;
        this.statiOsts = statiOsts;
    }


    /**
     * Gets the caratterizzazioniOsts value for this OggettiStruttureTerritoriali.
     * 
     * @return caratterizzazioniOsts
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst[] getCaratterizzazioniOsts() {
        return caratterizzazioniOsts;
    }


    /**
     * Sets the caratterizzazioniOsts value for this OggettiStruttureTerritoriali.
     * 
     * @param caratterizzazioniOsts
     */
    public void setCaratterizzazioniOsts(com.hyperborea.sira.ws.CaratterizzazioniOst[] caratterizzazioniOsts) {
        this.caratterizzazioniOsts = caratterizzazioniOsts;
    }

    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOsts(int i) {
        return this.caratterizzazioniOsts[i];
    }

    public void setCaratterizzazioniOsts(int i, com.hyperborea.sira.ws.CaratterizzazioniOst _value) {
        this.caratterizzazioniOsts[i] = _value;
    }


    /**
     * Gets the dataFine value for this OggettiStruttureTerritoriali.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this OggettiStruttureTerritoriali.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the dataInizio value for this OggettiStruttureTerritoriali.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this OggettiStruttureTerritoriali.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the idOst value for this OggettiStruttureTerritoriali.
     * 
     * @return idOst
     */
    public java.lang.Integer getIdOst() {
        return idOst;
    }


    /**
     * Sets the idOst value for this OggettiStruttureTerritoriali.
     * 
     * @param idOst
     */
    public void setIdOst(java.lang.Integer idOst) {
        this.idOst = idOst;
    }


    /**
     * Gets the relazioniOstsForIdOst1 value for this OggettiStruttureTerritoriali.
     * 
     * @return relazioniOstsForIdOst1
     */
    public com.hyperborea.sira.ws.RelazioniOst[] getRelazioniOstsForIdOst1() {
        return relazioniOstsForIdOst1;
    }


    /**
     * Sets the relazioniOstsForIdOst1 value for this OggettiStruttureTerritoriali.
     * 
     * @param relazioniOstsForIdOst1
     */
    public void setRelazioniOstsForIdOst1(com.hyperborea.sira.ws.RelazioniOst[] relazioniOstsForIdOst1) {
        this.relazioniOstsForIdOst1 = relazioniOstsForIdOst1;
    }

    public com.hyperborea.sira.ws.RelazioniOst getRelazioniOstsForIdOst1(int i) {
        return this.relazioniOstsForIdOst1[i];
    }

    public void setRelazioniOstsForIdOst1(int i, com.hyperborea.sira.ws.RelazioniOst _value) {
        this.relazioniOstsForIdOst1[i] = _value;
    }


    /**
     * Gets the relazioniOstsForIdOst2 value for this OggettiStruttureTerritoriali.
     * 
     * @return relazioniOstsForIdOst2
     */
    public com.hyperborea.sira.ws.RelazioniOst[] getRelazioniOstsForIdOst2() {
        return relazioniOstsForIdOst2;
    }


    /**
     * Sets the relazioniOstsForIdOst2 value for this OggettiStruttureTerritoriali.
     * 
     * @param relazioniOstsForIdOst2
     */
    public void setRelazioniOstsForIdOst2(com.hyperborea.sira.ws.RelazioniOst[] relazioniOstsForIdOst2) {
        this.relazioniOstsForIdOst2 = relazioniOstsForIdOst2;
    }

    public com.hyperborea.sira.ws.RelazioniOst getRelazioniOstsForIdOst2(int i) {
        return this.relazioniOstsForIdOst2[i];
    }

    public void setRelazioniOstsForIdOst2(int i, com.hyperborea.sira.ws.RelazioniOst _value) {
        this.relazioniOstsForIdOst2[i] = _value;
    }


    /**
     * Gets the sfOsts value for this OggettiStruttureTerritoriali.
     * 
     * @return sfOsts
     */
    public com.hyperborea.sira.ws.SfOst[] getSfOsts() {
        return sfOsts;
    }


    /**
     * Sets the sfOsts value for this OggettiStruttureTerritoriali.
     * 
     * @param sfOsts
     */
    public void setSfOsts(com.hyperborea.sira.ws.SfOst[] sfOsts) {
        this.sfOsts = sfOsts;
    }

    public com.hyperborea.sira.ws.SfOst getSfOsts(int i) {
        return this.sfOsts[i];
    }

    public void setSfOsts(int i, com.hyperborea.sira.ws.SfOst _value) {
        this.sfOsts[i] = _value;
    }


    /**
     * Gets the statiOsts value for this OggettiStruttureTerritoriali.
     * 
     * @return statiOsts
     */
    public com.hyperborea.sira.ws.StatiOst[] getStatiOsts() {
        return statiOsts;
    }


    /**
     * Sets the statiOsts value for this OggettiStruttureTerritoriali.
     * 
     * @param statiOsts
     */
    public void setStatiOsts(com.hyperborea.sira.ws.StatiOst[] statiOsts) {
        this.statiOsts = statiOsts;
    }

    public com.hyperborea.sira.ws.StatiOst getStatiOsts(int i) {
        return this.statiOsts[i];
    }

    public void setStatiOsts(int i, com.hyperborea.sira.ws.StatiOst _value) {
        this.statiOsts[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OggettiStruttureTerritoriali)) return false;
        OggettiStruttureTerritoriali other = (OggettiStruttureTerritoriali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.caratterizzazioniOsts==null && other.getCaratterizzazioniOsts()==null) || 
             (this.caratterizzazioniOsts!=null &&
              java.util.Arrays.equals(this.caratterizzazioniOsts, other.getCaratterizzazioniOsts()))) &&
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.idOst==null && other.getIdOst()==null) || 
             (this.idOst!=null &&
              this.idOst.equals(other.getIdOst()))) &&
            ((this.relazioniOstsForIdOst1==null && other.getRelazioniOstsForIdOst1()==null) || 
             (this.relazioniOstsForIdOst1!=null &&
              java.util.Arrays.equals(this.relazioniOstsForIdOst1, other.getRelazioniOstsForIdOst1()))) &&
            ((this.relazioniOstsForIdOst2==null && other.getRelazioniOstsForIdOst2()==null) || 
             (this.relazioniOstsForIdOst2!=null &&
              java.util.Arrays.equals(this.relazioniOstsForIdOst2, other.getRelazioniOstsForIdOst2()))) &&
            ((this.sfOsts==null && other.getSfOsts()==null) || 
             (this.sfOsts!=null &&
              java.util.Arrays.equals(this.sfOsts, other.getSfOsts()))) &&
            ((this.statiOsts==null && other.getStatiOsts()==null) || 
             (this.statiOsts!=null &&
              java.util.Arrays.equals(this.statiOsts, other.getStatiOsts())));
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
        if (getCaratterizzazioniOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCaratterizzazioniOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCaratterizzazioniOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getIdOst() != null) {
            _hashCode += getIdOst().hashCode();
        }
        if (getRelazioniOstsForIdOst1() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRelazioniOstsForIdOst1());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRelazioniOstsForIdOst1(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRelazioniOstsForIdOst2() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRelazioniOstsForIdOst2());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRelazioniOstsForIdOst2(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSfOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSfOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSfOsts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatiOsts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStatiOsts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStatiOsts(), i);
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
        new org.apache.axis.description.TypeDesc(OggettiStruttureTerritoriali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "oggettiStruttureTerritoriali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relazioniOstsForIdOst1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relazioniOstsForIdOst1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relazioniOstsForIdOst2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relazioniOstsForIdOst2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sfOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sfOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sfOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statiOsts");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statiOsts"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOst"));
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
