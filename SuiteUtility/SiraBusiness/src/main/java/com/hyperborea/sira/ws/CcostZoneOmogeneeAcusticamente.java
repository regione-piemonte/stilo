/**
 * CcostZoneOmogeneeAcusticamente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostZoneOmogeneeAcusticamente  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ClassiAcustiche classiAcustiche;

    private java.lang.Float densitaAttivArtInd;

    private java.lang.Float densitaAttivComm;

    private java.lang.Float densitaPopolazione;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.VocValidita validita;

    private java.lang.Float volumeTraffico;

    private com.hyperborea.sira.ws.ZonaOmogeneaRicettori[] zonaOmogeneaRicettoris;

    public CcostZoneOmogeneeAcusticamente() {
    }

    public CcostZoneOmogeneeAcusticamente(
           com.hyperborea.sira.ws.ClassiAcustiche classiAcustiche,
           java.lang.Float densitaAttivArtInd,
           java.lang.Float densitaAttivComm,
           java.lang.Float densitaPopolazione,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.VocValidita validita,
           java.lang.Float volumeTraffico,
           com.hyperborea.sira.ws.ZonaOmogeneaRicettori[] zonaOmogeneaRicettoris) {
           this.classiAcustiche = classiAcustiche;
           this.densitaAttivArtInd = densitaAttivArtInd;
           this.densitaAttivComm = densitaAttivComm;
           this.densitaPopolazione = densitaPopolazione;
           this.idCcost = idCcost;
           this.validita = validita;
           this.volumeTraffico = volumeTraffico;
           this.zonaOmogeneaRicettoris = zonaOmogeneaRicettoris;
    }


    /**
     * Gets the classiAcustiche value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return classiAcustiche
     */
    public com.hyperborea.sira.ws.ClassiAcustiche getClassiAcustiche() {
        return classiAcustiche;
    }


    /**
     * Sets the classiAcustiche value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param classiAcustiche
     */
    public void setClassiAcustiche(com.hyperborea.sira.ws.ClassiAcustiche classiAcustiche) {
        this.classiAcustiche = classiAcustiche;
    }


    /**
     * Gets the densitaAttivArtInd value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return densitaAttivArtInd
     */
    public java.lang.Float getDensitaAttivArtInd() {
        return densitaAttivArtInd;
    }


    /**
     * Sets the densitaAttivArtInd value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param densitaAttivArtInd
     */
    public void setDensitaAttivArtInd(java.lang.Float densitaAttivArtInd) {
        this.densitaAttivArtInd = densitaAttivArtInd;
    }


    /**
     * Gets the densitaAttivComm value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return densitaAttivComm
     */
    public java.lang.Float getDensitaAttivComm() {
        return densitaAttivComm;
    }


    /**
     * Sets the densitaAttivComm value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param densitaAttivComm
     */
    public void setDensitaAttivComm(java.lang.Float densitaAttivComm) {
        this.densitaAttivComm = densitaAttivComm;
    }


    /**
     * Gets the densitaPopolazione value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return densitaPopolazione
     */
    public java.lang.Float getDensitaPopolazione() {
        return densitaPopolazione;
    }


    /**
     * Sets the densitaPopolazione value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param densitaPopolazione
     */
    public void setDensitaPopolazione(java.lang.Float densitaPopolazione) {
        this.densitaPopolazione = densitaPopolazione;
    }


    /**
     * Gets the idCcost value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the validita value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return validita
     */
    public com.hyperborea.sira.ws.VocValidita getValidita() {
        return validita;
    }


    /**
     * Sets the validita value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param validita
     */
    public void setValidita(com.hyperborea.sira.ws.VocValidita validita) {
        this.validita = validita;
    }


    /**
     * Gets the volumeTraffico value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return volumeTraffico
     */
    public java.lang.Float getVolumeTraffico() {
        return volumeTraffico;
    }


    /**
     * Sets the volumeTraffico value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param volumeTraffico
     */
    public void setVolumeTraffico(java.lang.Float volumeTraffico) {
        this.volumeTraffico = volumeTraffico;
    }


    /**
     * Gets the zonaOmogeneaRicettoris value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @return zonaOmogeneaRicettoris
     */
    public com.hyperborea.sira.ws.ZonaOmogeneaRicettori[] getZonaOmogeneaRicettoris() {
        return zonaOmogeneaRicettoris;
    }


    /**
     * Sets the zonaOmogeneaRicettoris value for this CcostZoneOmogeneeAcusticamente.
     * 
     * @param zonaOmogeneaRicettoris
     */
    public void setZonaOmogeneaRicettoris(com.hyperborea.sira.ws.ZonaOmogeneaRicettori[] zonaOmogeneaRicettoris) {
        this.zonaOmogeneaRicettoris = zonaOmogeneaRicettoris;
    }

    public com.hyperborea.sira.ws.ZonaOmogeneaRicettori getZonaOmogeneaRicettoris(int i) {
        return this.zonaOmogeneaRicettoris[i];
    }

    public void setZonaOmogeneaRicettoris(int i, com.hyperborea.sira.ws.ZonaOmogeneaRicettori _value) {
        this.zonaOmogeneaRicettoris[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostZoneOmogeneeAcusticamente)) return false;
        CcostZoneOmogeneeAcusticamente other = (CcostZoneOmogeneeAcusticamente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.classiAcustiche==null && other.getClassiAcustiche()==null) || 
             (this.classiAcustiche!=null &&
              this.classiAcustiche.equals(other.getClassiAcustiche()))) &&
            ((this.densitaAttivArtInd==null && other.getDensitaAttivArtInd()==null) || 
             (this.densitaAttivArtInd!=null &&
              this.densitaAttivArtInd.equals(other.getDensitaAttivArtInd()))) &&
            ((this.densitaAttivComm==null && other.getDensitaAttivComm()==null) || 
             (this.densitaAttivComm!=null &&
              this.densitaAttivComm.equals(other.getDensitaAttivComm()))) &&
            ((this.densitaPopolazione==null && other.getDensitaPopolazione()==null) || 
             (this.densitaPopolazione!=null &&
              this.densitaPopolazione.equals(other.getDensitaPopolazione()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.validita==null && other.getValidita()==null) || 
             (this.validita!=null &&
              this.validita.equals(other.getValidita()))) &&
            ((this.volumeTraffico==null && other.getVolumeTraffico()==null) || 
             (this.volumeTraffico!=null &&
              this.volumeTraffico.equals(other.getVolumeTraffico()))) &&
            ((this.zonaOmogeneaRicettoris==null && other.getZonaOmogeneaRicettoris()==null) || 
             (this.zonaOmogeneaRicettoris!=null &&
              java.util.Arrays.equals(this.zonaOmogeneaRicettoris, other.getZonaOmogeneaRicettoris())));
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
        if (getClassiAcustiche() != null) {
            _hashCode += getClassiAcustiche().hashCode();
        }
        if (getDensitaAttivArtInd() != null) {
            _hashCode += getDensitaAttivArtInd().hashCode();
        }
        if (getDensitaAttivComm() != null) {
            _hashCode += getDensitaAttivComm().hashCode();
        }
        if (getDensitaPopolazione() != null) {
            _hashCode += getDensitaPopolazione().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getValidita() != null) {
            _hashCode += getValidita().hashCode();
        }
        if (getVolumeTraffico() != null) {
            _hashCode += getVolumeTraffico().hashCode();
        }
        if (getZonaOmogeneaRicettoris() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getZonaOmogeneaRicettoris());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getZonaOmogeneaRicettoris(), i);
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
        new org.apache.axis.description.TypeDesc(CcostZoneOmogeneeAcusticamente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostZoneOmogeneeAcusticamente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classiAcustiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classiAcustiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiAcustiche"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("densitaAttivArtInd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "densitaAttivArtInd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("densitaAttivComm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "densitaAttivComm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("densitaPopolazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "densitaPopolazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
        elemField.setFieldName("validita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "validita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocValidita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeTraffico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeTraffico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zonaOmogeneaRicettoris");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zonaOmogeneaRicettoris"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zonaOmogeneaRicettori"));
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
