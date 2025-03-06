/**
 * CcostComplessoForestale.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostComplessoForestale  implements java.io.Serializable {
    private java.lang.String attoCostituzione;

    private java.lang.String codice;

    private java.lang.String descrizione;

    private java.lang.Integer idCcost;

    private java.lang.String servizioTerritoriale;

    private com.hyperborea.sira.ws.VocEfProprietaComp vocEfProprietaComp;

    public CcostComplessoForestale() {
    }

    public CcostComplessoForestale(
           java.lang.String attoCostituzione,
           java.lang.String codice,
           java.lang.String descrizione,
           java.lang.Integer idCcost,
           java.lang.String servizioTerritoriale,
           com.hyperborea.sira.ws.VocEfProprietaComp vocEfProprietaComp) {
           this.attoCostituzione = attoCostituzione;
           this.codice = codice;
           this.descrizione = descrizione;
           this.idCcost = idCcost;
           this.servizioTerritoriale = servizioTerritoriale;
           this.vocEfProprietaComp = vocEfProprietaComp;
    }


    /**
     * Gets the attoCostituzione value for this CcostComplessoForestale.
     * 
     * @return attoCostituzione
     */
    public java.lang.String getAttoCostituzione() {
        return attoCostituzione;
    }


    /**
     * Sets the attoCostituzione value for this CcostComplessoForestale.
     * 
     * @param attoCostituzione
     */
    public void setAttoCostituzione(java.lang.String attoCostituzione) {
        this.attoCostituzione = attoCostituzione;
    }


    /**
     * Gets the codice value for this CcostComplessoForestale.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostComplessoForestale.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this CcostComplessoForestale.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this CcostComplessoForestale.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idCcost value for this CcostComplessoForestale.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostComplessoForestale.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the servizioTerritoriale value for this CcostComplessoForestale.
     * 
     * @return servizioTerritoriale
     */
    public java.lang.String getServizioTerritoriale() {
        return servizioTerritoriale;
    }


    /**
     * Sets the servizioTerritoriale value for this CcostComplessoForestale.
     * 
     * @param servizioTerritoriale
     */
    public void setServizioTerritoriale(java.lang.String servizioTerritoriale) {
        this.servizioTerritoriale = servizioTerritoriale;
    }


    /**
     * Gets the vocEfProprietaComp value for this CcostComplessoForestale.
     * 
     * @return vocEfProprietaComp
     */
    public com.hyperborea.sira.ws.VocEfProprietaComp getVocEfProprietaComp() {
        return vocEfProprietaComp;
    }


    /**
     * Sets the vocEfProprietaComp value for this CcostComplessoForestale.
     * 
     * @param vocEfProprietaComp
     */
    public void setVocEfProprietaComp(com.hyperborea.sira.ws.VocEfProprietaComp vocEfProprietaComp) {
        this.vocEfProprietaComp = vocEfProprietaComp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostComplessoForestale)) return false;
        CcostComplessoForestale other = (CcostComplessoForestale) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attoCostituzione==null && other.getAttoCostituzione()==null) || 
             (this.attoCostituzione!=null &&
              this.attoCostituzione.equals(other.getAttoCostituzione()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.servizioTerritoriale==null && other.getServizioTerritoriale()==null) || 
             (this.servizioTerritoriale!=null &&
              this.servizioTerritoriale.equals(other.getServizioTerritoriale()))) &&
            ((this.vocEfProprietaComp==null && other.getVocEfProprietaComp()==null) || 
             (this.vocEfProprietaComp!=null &&
              this.vocEfProprietaComp.equals(other.getVocEfProprietaComp())));
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
        if (getAttoCostituzione() != null) {
            _hashCode += getAttoCostituzione().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getServizioTerritoriale() != null) {
            _hashCode += getServizioTerritoriale().hashCode();
        }
        if (getVocEfProprietaComp() != null) {
            _hashCode += getVocEfProprietaComp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostComplessoForestale.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostComplessoForestale"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attoCostituzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attoCostituzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
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
        elemField.setFieldName("servizioTerritoriale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "servizioTerritoriale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfProprietaComp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfProprietaComp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfProprietaComp"));
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
