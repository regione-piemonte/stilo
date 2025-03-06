/**
 * CcostAreaRicercaForestale.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAreaRicercaForestale  implements java.io.Serializable {
    private java.lang.String codice;

    private java.lang.Float dimParcelle;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.InfrastrRicercaForestale[] infrastrRicercaForestales;

    private java.lang.Integer numParcelle;

    private java.lang.String paroleChiave;

    private com.hyperborea.sira.ws.ProgettiRicercaForestale[] progettiRicercaForestales;

    private java.lang.Float superficie;

    private com.hyperborea.sira.ws.VocSettoreRicerca vocSettoreRicerca;

    private com.hyperborea.sira.ws.VocUnitaMisuraAreale vocUnitaMisuraAreale;

    public CcostAreaRicercaForestale() {
    }

    public CcostAreaRicercaForestale(
           java.lang.String codice,
           java.lang.Float dimParcelle,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.InfrastrRicercaForestale[] infrastrRicercaForestales,
           java.lang.Integer numParcelle,
           java.lang.String paroleChiave,
           com.hyperborea.sira.ws.ProgettiRicercaForestale[] progettiRicercaForestales,
           java.lang.Float superficie,
           com.hyperborea.sira.ws.VocSettoreRicerca vocSettoreRicerca,
           com.hyperborea.sira.ws.VocUnitaMisuraAreale vocUnitaMisuraAreale) {
           this.codice = codice;
           this.dimParcelle = dimParcelle;
           this.idCcost = idCcost;
           this.infrastrRicercaForestales = infrastrRicercaForestales;
           this.numParcelle = numParcelle;
           this.paroleChiave = paroleChiave;
           this.progettiRicercaForestales = progettiRicercaForestales;
           this.superficie = superficie;
           this.vocSettoreRicerca = vocSettoreRicerca;
           this.vocUnitaMisuraAreale = vocUnitaMisuraAreale;
    }


    /**
     * Gets the codice value for this CcostAreaRicercaForestale.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostAreaRicercaForestale.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the dimParcelle value for this CcostAreaRicercaForestale.
     * 
     * @return dimParcelle
     */
    public java.lang.Float getDimParcelle() {
        return dimParcelle;
    }


    /**
     * Sets the dimParcelle value for this CcostAreaRicercaForestale.
     * 
     * @param dimParcelle
     */
    public void setDimParcelle(java.lang.Float dimParcelle) {
        this.dimParcelle = dimParcelle;
    }


    /**
     * Gets the idCcost value for this CcostAreaRicercaForestale.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAreaRicercaForestale.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the infrastrRicercaForestales value for this CcostAreaRicercaForestale.
     * 
     * @return infrastrRicercaForestales
     */
    public com.hyperborea.sira.ws.InfrastrRicercaForestale[] getInfrastrRicercaForestales() {
        return infrastrRicercaForestales;
    }


    /**
     * Sets the infrastrRicercaForestales value for this CcostAreaRicercaForestale.
     * 
     * @param infrastrRicercaForestales
     */
    public void setInfrastrRicercaForestales(com.hyperborea.sira.ws.InfrastrRicercaForestale[] infrastrRicercaForestales) {
        this.infrastrRicercaForestales = infrastrRicercaForestales;
    }

    public com.hyperborea.sira.ws.InfrastrRicercaForestale getInfrastrRicercaForestales(int i) {
        return this.infrastrRicercaForestales[i];
    }

    public void setInfrastrRicercaForestales(int i, com.hyperborea.sira.ws.InfrastrRicercaForestale _value) {
        this.infrastrRicercaForestales[i] = _value;
    }


    /**
     * Gets the numParcelle value for this CcostAreaRicercaForestale.
     * 
     * @return numParcelle
     */
    public java.lang.Integer getNumParcelle() {
        return numParcelle;
    }


    /**
     * Sets the numParcelle value for this CcostAreaRicercaForestale.
     * 
     * @param numParcelle
     */
    public void setNumParcelle(java.lang.Integer numParcelle) {
        this.numParcelle = numParcelle;
    }


    /**
     * Gets the paroleChiave value for this CcostAreaRicercaForestale.
     * 
     * @return paroleChiave
     */
    public java.lang.String getParoleChiave() {
        return paroleChiave;
    }


    /**
     * Sets the paroleChiave value for this CcostAreaRicercaForestale.
     * 
     * @param paroleChiave
     */
    public void setParoleChiave(java.lang.String paroleChiave) {
        this.paroleChiave = paroleChiave;
    }


    /**
     * Gets the progettiRicercaForestales value for this CcostAreaRicercaForestale.
     * 
     * @return progettiRicercaForestales
     */
    public com.hyperborea.sira.ws.ProgettiRicercaForestale[] getProgettiRicercaForestales() {
        return progettiRicercaForestales;
    }


    /**
     * Sets the progettiRicercaForestales value for this CcostAreaRicercaForestale.
     * 
     * @param progettiRicercaForestales
     */
    public void setProgettiRicercaForestales(com.hyperborea.sira.ws.ProgettiRicercaForestale[] progettiRicercaForestales) {
        this.progettiRicercaForestales = progettiRicercaForestales;
    }

    public com.hyperborea.sira.ws.ProgettiRicercaForestale getProgettiRicercaForestales(int i) {
        return this.progettiRicercaForestales[i];
    }

    public void setProgettiRicercaForestales(int i, com.hyperborea.sira.ws.ProgettiRicercaForestale _value) {
        this.progettiRicercaForestales[i] = _value;
    }


    /**
     * Gets the superficie value for this CcostAreaRicercaForestale.
     * 
     * @return superficie
     */
    public java.lang.Float getSuperficie() {
        return superficie;
    }


    /**
     * Sets the superficie value for this CcostAreaRicercaForestale.
     * 
     * @param superficie
     */
    public void setSuperficie(java.lang.Float superficie) {
        this.superficie = superficie;
    }


    /**
     * Gets the vocSettoreRicerca value for this CcostAreaRicercaForestale.
     * 
     * @return vocSettoreRicerca
     */
    public com.hyperborea.sira.ws.VocSettoreRicerca getVocSettoreRicerca() {
        return vocSettoreRicerca;
    }


    /**
     * Sets the vocSettoreRicerca value for this CcostAreaRicercaForestale.
     * 
     * @param vocSettoreRicerca
     */
    public void setVocSettoreRicerca(com.hyperborea.sira.ws.VocSettoreRicerca vocSettoreRicerca) {
        this.vocSettoreRicerca = vocSettoreRicerca;
    }


    /**
     * Gets the vocUnitaMisuraAreale value for this CcostAreaRicercaForestale.
     * 
     * @return vocUnitaMisuraAreale
     */
    public com.hyperborea.sira.ws.VocUnitaMisuraAreale getVocUnitaMisuraAreale() {
        return vocUnitaMisuraAreale;
    }


    /**
     * Sets the vocUnitaMisuraAreale value for this CcostAreaRicercaForestale.
     * 
     * @param vocUnitaMisuraAreale
     */
    public void setVocUnitaMisuraAreale(com.hyperborea.sira.ws.VocUnitaMisuraAreale vocUnitaMisuraAreale) {
        this.vocUnitaMisuraAreale = vocUnitaMisuraAreale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAreaRicercaForestale)) return false;
        CcostAreaRicercaForestale other = (CcostAreaRicercaForestale) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.dimParcelle==null && other.getDimParcelle()==null) || 
             (this.dimParcelle!=null &&
              this.dimParcelle.equals(other.getDimParcelle()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.infrastrRicercaForestales==null && other.getInfrastrRicercaForestales()==null) || 
             (this.infrastrRicercaForestales!=null &&
              java.util.Arrays.equals(this.infrastrRicercaForestales, other.getInfrastrRicercaForestales()))) &&
            ((this.numParcelle==null && other.getNumParcelle()==null) || 
             (this.numParcelle!=null &&
              this.numParcelle.equals(other.getNumParcelle()))) &&
            ((this.paroleChiave==null && other.getParoleChiave()==null) || 
             (this.paroleChiave!=null &&
              this.paroleChiave.equals(other.getParoleChiave()))) &&
            ((this.progettiRicercaForestales==null && other.getProgettiRicercaForestales()==null) || 
             (this.progettiRicercaForestales!=null &&
              java.util.Arrays.equals(this.progettiRicercaForestales, other.getProgettiRicercaForestales()))) &&
            ((this.superficie==null && other.getSuperficie()==null) || 
             (this.superficie!=null &&
              this.superficie.equals(other.getSuperficie()))) &&
            ((this.vocSettoreRicerca==null && other.getVocSettoreRicerca()==null) || 
             (this.vocSettoreRicerca!=null &&
              this.vocSettoreRicerca.equals(other.getVocSettoreRicerca()))) &&
            ((this.vocUnitaMisuraAreale==null && other.getVocUnitaMisuraAreale()==null) || 
             (this.vocUnitaMisuraAreale!=null &&
              this.vocUnitaMisuraAreale.equals(other.getVocUnitaMisuraAreale())));
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDimParcelle() != null) {
            _hashCode += getDimParcelle().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getInfrastrRicercaForestales() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInfrastrRicercaForestales());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInfrastrRicercaForestales(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getNumParcelle() != null) {
            _hashCode += getNumParcelle().hashCode();
        }
        if (getParoleChiave() != null) {
            _hashCode += getParoleChiave().hashCode();
        }
        if (getProgettiRicercaForestales() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProgettiRicercaForestales());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProgettiRicercaForestales(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSuperficie() != null) {
            _hashCode += getSuperficie().hashCode();
        }
        if (getVocSettoreRicerca() != null) {
            _hashCode += getVocSettoreRicerca().hashCode();
        }
        if (getVocUnitaMisuraAreale() != null) {
            _hashCode += getVocUnitaMisuraAreale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAreaRicercaForestale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaRicercaForestale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dimParcelle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dimParcelle"));
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
        elemField.setFieldName("infrastrRicercaForestales");
        elemField.setXmlName(new javax.xml.namespace.QName("", "infrastrRicercaForestales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "infrastrRicercaForestale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numParcelle");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numParcelle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paroleChiave");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paroleChiave"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("progettiRicercaForestales");
        elemField.setXmlName(new javax.xml.namespace.QName("", "progettiRicercaForestales"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "progettiRicercaForestale"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocSettoreRicerca");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocSettoreRicerca"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocSettoreRicerca"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocUnitaMisuraAreale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocUnitaMisuraAreale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocUnitaMisuraAreale"));
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
