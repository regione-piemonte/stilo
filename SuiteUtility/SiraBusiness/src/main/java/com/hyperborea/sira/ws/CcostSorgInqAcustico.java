/**
 * CcostSorgInqAcustico.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSorgInqAcustico  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostSorgAcusticaLineare ccostSorgAcusticaLineare;

    private com.hyperborea.sira.ws.CcostSorgAcusticaPuntuale ccostSorgAcusticaPuntuale;

    private com.hyperborea.sira.ws.ClassiAcustiche classiAcustiche;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.SorgAcuPeriodoAttivita[] sorgAcuPeriodoAttivitas;

    private com.hyperborea.sira.ws.SorgAcuRicettori[] sorgAcuRicettoris;

    public CcostSorgInqAcustico() {
    }

    public CcostSorgInqAcustico(
           com.hyperborea.sira.ws.CcostSorgAcusticaLineare ccostSorgAcusticaLineare,
           com.hyperborea.sira.ws.CcostSorgAcusticaPuntuale ccostSorgAcusticaPuntuale,
           com.hyperborea.sira.ws.ClassiAcustiche classiAcustiche,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.SorgAcuPeriodoAttivita[] sorgAcuPeriodoAttivitas,
           com.hyperborea.sira.ws.SorgAcuRicettori[] sorgAcuRicettoris) {
           this.ccostSorgAcusticaLineare = ccostSorgAcusticaLineare;
           this.ccostSorgAcusticaPuntuale = ccostSorgAcusticaPuntuale;
           this.classiAcustiche = classiAcustiche;
           this.idCcost = idCcost;
           this.sorgAcuPeriodoAttivitas = sorgAcuPeriodoAttivitas;
           this.sorgAcuRicettoris = sorgAcuRicettoris;
    }


    /**
     * Gets the ccostSorgAcusticaLineare value for this CcostSorgInqAcustico.
     * 
     * @return ccostSorgAcusticaLineare
     */
    public com.hyperborea.sira.ws.CcostSorgAcusticaLineare getCcostSorgAcusticaLineare() {
        return ccostSorgAcusticaLineare;
    }


    /**
     * Sets the ccostSorgAcusticaLineare value for this CcostSorgInqAcustico.
     * 
     * @param ccostSorgAcusticaLineare
     */
    public void setCcostSorgAcusticaLineare(com.hyperborea.sira.ws.CcostSorgAcusticaLineare ccostSorgAcusticaLineare) {
        this.ccostSorgAcusticaLineare = ccostSorgAcusticaLineare;
    }


    /**
     * Gets the ccostSorgAcusticaPuntuale value for this CcostSorgInqAcustico.
     * 
     * @return ccostSorgAcusticaPuntuale
     */
    public com.hyperborea.sira.ws.CcostSorgAcusticaPuntuale getCcostSorgAcusticaPuntuale() {
        return ccostSorgAcusticaPuntuale;
    }


    /**
     * Sets the ccostSorgAcusticaPuntuale value for this CcostSorgInqAcustico.
     * 
     * @param ccostSorgAcusticaPuntuale
     */
    public void setCcostSorgAcusticaPuntuale(com.hyperborea.sira.ws.CcostSorgAcusticaPuntuale ccostSorgAcusticaPuntuale) {
        this.ccostSorgAcusticaPuntuale = ccostSorgAcusticaPuntuale;
    }


    /**
     * Gets the classiAcustiche value for this CcostSorgInqAcustico.
     * 
     * @return classiAcustiche
     */
    public com.hyperborea.sira.ws.ClassiAcustiche getClassiAcustiche() {
        return classiAcustiche;
    }


    /**
     * Sets the classiAcustiche value for this CcostSorgInqAcustico.
     * 
     * @param classiAcustiche
     */
    public void setClassiAcustiche(com.hyperborea.sira.ws.ClassiAcustiche classiAcustiche) {
        this.classiAcustiche = classiAcustiche;
    }


    /**
     * Gets the idCcost value for this CcostSorgInqAcustico.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSorgInqAcustico.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the sorgAcuPeriodoAttivitas value for this CcostSorgInqAcustico.
     * 
     * @return sorgAcuPeriodoAttivitas
     */
    public com.hyperborea.sira.ws.SorgAcuPeriodoAttivita[] getSorgAcuPeriodoAttivitas() {
        return sorgAcuPeriodoAttivitas;
    }


    /**
     * Sets the sorgAcuPeriodoAttivitas value for this CcostSorgInqAcustico.
     * 
     * @param sorgAcuPeriodoAttivitas
     */
    public void setSorgAcuPeriodoAttivitas(com.hyperborea.sira.ws.SorgAcuPeriodoAttivita[] sorgAcuPeriodoAttivitas) {
        this.sorgAcuPeriodoAttivitas = sorgAcuPeriodoAttivitas;
    }

    public com.hyperborea.sira.ws.SorgAcuPeriodoAttivita getSorgAcuPeriodoAttivitas(int i) {
        return this.sorgAcuPeriodoAttivitas[i];
    }

    public void setSorgAcuPeriodoAttivitas(int i, com.hyperborea.sira.ws.SorgAcuPeriodoAttivita _value) {
        this.sorgAcuPeriodoAttivitas[i] = _value;
    }


    /**
     * Gets the sorgAcuRicettoris value for this CcostSorgInqAcustico.
     * 
     * @return sorgAcuRicettoris
     */
    public com.hyperborea.sira.ws.SorgAcuRicettori[] getSorgAcuRicettoris() {
        return sorgAcuRicettoris;
    }


    /**
     * Sets the sorgAcuRicettoris value for this CcostSorgInqAcustico.
     * 
     * @param sorgAcuRicettoris
     */
    public void setSorgAcuRicettoris(com.hyperborea.sira.ws.SorgAcuRicettori[] sorgAcuRicettoris) {
        this.sorgAcuRicettoris = sorgAcuRicettoris;
    }

    public com.hyperborea.sira.ws.SorgAcuRicettori getSorgAcuRicettoris(int i) {
        return this.sorgAcuRicettoris[i];
    }

    public void setSorgAcuRicettoris(int i, com.hyperborea.sira.ws.SorgAcuRicettori _value) {
        this.sorgAcuRicettoris[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSorgInqAcustico)) return false;
        CcostSorgInqAcustico other = (CcostSorgInqAcustico) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostSorgAcusticaLineare==null && other.getCcostSorgAcusticaLineare()==null) || 
             (this.ccostSorgAcusticaLineare!=null &&
              this.ccostSorgAcusticaLineare.equals(other.getCcostSorgAcusticaLineare()))) &&
            ((this.ccostSorgAcusticaPuntuale==null && other.getCcostSorgAcusticaPuntuale()==null) || 
             (this.ccostSorgAcusticaPuntuale!=null &&
              this.ccostSorgAcusticaPuntuale.equals(other.getCcostSorgAcusticaPuntuale()))) &&
            ((this.classiAcustiche==null && other.getClassiAcustiche()==null) || 
             (this.classiAcustiche!=null &&
              this.classiAcustiche.equals(other.getClassiAcustiche()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.sorgAcuPeriodoAttivitas==null && other.getSorgAcuPeriodoAttivitas()==null) || 
             (this.sorgAcuPeriodoAttivitas!=null &&
              java.util.Arrays.equals(this.sorgAcuPeriodoAttivitas, other.getSorgAcuPeriodoAttivitas()))) &&
            ((this.sorgAcuRicettoris==null && other.getSorgAcuRicettoris()==null) || 
             (this.sorgAcuRicettoris!=null &&
              java.util.Arrays.equals(this.sorgAcuRicettoris, other.getSorgAcuRicettoris())));
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
        if (getCcostSorgAcusticaLineare() != null) {
            _hashCode += getCcostSorgAcusticaLineare().hashCode();
        }
        if (getCcostSorgAcusticaPuntuale() != null) {
            _hashCode += getCcostSorgAcusticaPuntuale().hashCode();
        }
        if (getClassiAcustiche() != null) {
            _hashCode += getClassiAcustiche().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getSorgAcuPeriodoAttivitas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSorgAcuPeriodoAttivitas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSorgAcuPeriodoAttivitas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSorgAcuRicettoris() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSorgAcuRicettoris());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSorgAcuRicettoris(), i);
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
        new org.apache.axis.description.TypeDesc(CcostSorgInqAcustico.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgInqAcustico"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostSorgAcusticaLineare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostSorgAcusticaLineare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgAcusticaLineare"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostSorgAcusticaPuntuale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostSorgAcusticaPuntuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSorgAcusticaPuntuale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classiAcustiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classiAcustiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiAcustiche"));
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
        elemField.setFieldName("sorgAcuPeriodoAttivitas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgAcuPeriodoAttivitas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgAcuPeriodoAttivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgAcuRicettoris");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgAcuRicettoris"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgAcuRicettori"));
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
