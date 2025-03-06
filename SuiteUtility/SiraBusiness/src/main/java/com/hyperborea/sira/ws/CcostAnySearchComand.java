/**
 * CcostAnySearchComand.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAnySearchComand  implements java.io.Serializable {
    private java.lang.String comune;

    private java.lang.Integer costId;

    private java.lang.String denominazione;

    private java.lang.Integer idOstSedeLegale;

    private java.lang.Integer idSoggettoFisico;

    private java.lang.String localita;

    private java.lang.Integer nostId;

    private java.lang.String partitaIva;

    private java.lang.String provincia;

    private java.lang.String ragioneSociale;

    private com.hyperborea.sira.ws.CcostAnySearchStringCriteria ccostAnySearchStringCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria ccostAnySearchDateRangeCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria ccostAnySearchFloatRangeCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria ccostAnySearchIntegerCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria ccostAnySearchIntegerRangeCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria ccostAnySearchDoubleRangeCriteria;

    public CcostAnySearchComand() {
    }

    public CcostAnySearchComand(
           java.lang.String comune,
           java.lang.Integer costId,
           java.lang.String denominazione,
           java.lang.Integer idOstSedeLegale,
           java.lang.Integer idSoggettoFisico,
           java.lang.String localita,
           java.lang.Integer nostId,
           java.lang.String partitaIva,
           java.lang.String provincia,
           java.lang.String ragioneSociale,
           com.hyperborea.sira.ws.CcostAnySearchStringCriteria ccostAnySearchStringCriteria,
           com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria ccostAnySearchDateRangeCriteria,
           com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria ccostAnySearchFloatRangeCriteria,
           com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria ccostAnySearchIntegerCriteria,
           com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria ccostAnySearchIntegerRangeCriteria,
           com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria ccostAnySearchDoubleRangeCriteria) {
           this.comune = comune;
           this.costId = costId;
           this.denominazione = denominazione;
           this.idOstSedeLegale = idOstSedeLegale;
           this.idSoggettoFisico = idSoggettoFisico;
           this.localita = localita;
           this.nostId = nostId;
           this.partitaIva = partitaIva;
           this.provincia = provincia;
           this.ragioneSociale = ragioneSociale;
           this.ccostAnySearchStringCriteria = ccostAnySearchStringCriteria;
           this.ccostAnySearchDateRangeCriteria = ccostAnySearchDateRangeCriteria;
           this.ccostAnySearchFloatRangeCriteria = ccostAnySearchFloatRangeCriteria;
           this.ccostAnySearchIntegerCriteria = ccostAnySearchIntegerCriteria;
           this.ccostAnySearchIntegerRangeCriteria = ccostAnySearchIntegerRangeCriteria;
           this.ccostAnySearchDoubleRangeCriteria = ccostAnySearchDoubleRangeCriteria;
    }


    /**
     * Gets the comune value for this CcostAnySearchComand.
     * 
     * @return comune
     */
    public java.lang.String getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this CcostAnySearchComand.
     * 
     * @param comune
     */
    public void setComune(java.lang.String comune) {
        this.comune = comune;
    }


    /**
     * Gets the costId value for this CcostAnySearchComand.
     * 
     * @return costId
     */
    public java.lang.Integer getCostId() {
        return costId;
    }


    /**
     * Sets the costId value for this CcostAnySearchComand.
     * 
     * @param costId
     */
    public void setCostId(java.lang.Integer costId) {
        this.costId = costId;
    }


    /**
     * Gets the denominazione value for this CcostAnySearchComand.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this CcostAnySearchComand.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the idOstSedeLegale value for this CcostAnySearchComand.
     * 
     * @return idOstSedeLegale
     */
    public java.lang.Integer getIdOstSedeLegale() {
        return idOstSedeLegale;
    }


    /**
     * Sets the idOstSedeLegale value for this CcostAnySearchComand.
     * 
     * @param idOstSedeLegale
     */
    public void setIdOstSedeLegale(java.lang.Integer idOstSedeLegale) {
        this.idOstSedeLegale = idOstSedeLegale;
    }


    /**
     * Gets the idSoggettoFisico value for this CcostAnySearchComand.
     * 
     * @return idSoggettoFisico
     */
    public java.lang.Integer getIdSoggettoFisico() {
        return idSoggettoFisico;
    }


    /**
     * Sets the idSoggettoFisico value for this CcostAnySearchComand.
     * 
     * @param idSoggettoFisico
     */
    public void setIdSoggettoFisico(java.lang.Integer idSoggettoFisico) {
        this.idSoggettoFisico = idSoggettoFisico;
    }


    /**
     * Gets the localita value for this CcostAnySearchComand.
     * 
     * @return localita
     */
    public java.lang.String getLocalita() {
        return localita;
    }


    /**
     * Sets the localita value for this CcostAnySearchComand.
     * 
     * @param localita
     */
    public void setLocalita(java.lang.String localita) {
        this.localita = localita;
    }


    /**
     * Gets the nostId value for this CcostAnySearchComand.
     * 
     * @return nostId
     */
    public java.lang.Integer getNostId() {
        return nostId;
    }


    /**
     * Sets the nostId value for this CcostAnySearchComand.
     * 
     * @param nostId
     */
    public void setNostId(java.lang.Integer nostId) {
        this.nostId = nostId;
    }


    /**
     * Gets the partitaIva value for this CcostAnySearchComand.
     * 
     * @return partitaIva
     */
    public java.lang.String getPartitaIva() {
        return partitaIva;
    }


    /**
     * Sets the partitaIva value for this CcostAnySearchComand.
     * 
     * @param partitaIva
     */
    public void setPartitaIva(java.lang.String partitaIva) {
        this.partitaIva = partitaIva;
    }


    /**
     * Gets the provincia value for this CcostAnySearchComand.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this CcostAnySearchComand.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the ragioneSociale value for this CcostAnySearchComand.
     * 
     * @return ragioneSociale
     */
    public java.lang.String getRagioneSociale() {
        return ragioneSociale;
    }


    /**
     * Sets the ragioneSociale value for this CcostAnySearchComand.
     * 
     * @param ragioneSociale
     */
    public void setRagioneSociale(java.lang.String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }


    /**
     * Gets the ccostAnySearchStringCriteria value for this CcostAnySearchComand.
     * 
     * @return ccostAnySearchStringCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchStringCriteria getCcostAnySearchStringCriteria() {
        return ccostAnySearchStringCriteria;
    }


    /**
     * Sets the ccostAnySearchStringCriteria value for this CcostAnySearchComand.
     * 
     * @param ccostAnySearchStringCriteria
     */
    public void setCcostAnySearchStringCriteria(com.hyperborea.sira.ws.CcostAnySearchStringCriteria ccostAnySearchStringCriteria) {
        this.ccostAnySearchStringCriteria = ccostAnySearchStringCriteria;
    }


    /**
     * Gets the ccostAnySearchDateRangeCriteria value for this CcostAnySearchComand.
     * 
     * @return ccostAnySearchDateRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria getCcostAnySearchDateRangeCriteria() {
        return ccostAnySearchDateRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchDateRangeCriteria value for this CcostAnySearchComand.
     * 
     * @param ccostAnySearchDateRangeCriteria
     */
    public void setCcostAnySearchDateRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria ccostAnySearchDateRangeCriteria) {
        this.ccostAnySearchDateRangeCriteria = ccostAnySearchDateRangeCriteria;
    }


    /**
     * Gets the ccostAnySearchFloatRangeCriteria value for this CcostAnySearchComand.
     * 
     * @return ccostAnySearchFloatRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria getCcostAnySearchFloatRangeCriteria() {
        return ccostAnySearchFloatRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchFloatRangeCriteria value for this CcostAnySearchComand.
     * 
     * @param ccostAnySearchFloatRangeCriteria
     */
    public void setCcostAnySearchFloatRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria ccostAnySearchFloatRangeCriteria) {
        this.ccostAnySearchFloatRangeCriteria = ccostAnySearchFloatRangeCriteria;
    }


    /**
     * Gets the ccostAnySearchIntegerCriteria value for this CcostAnySearchComand.
     * 
     * @return ccostAnySearchIntegerCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria getCcostAnySearchIntegerCriteria() {
        return ccostAnySearchIntegerCriteria;
    }


    /**
     * Sets the ccostAnySearchIntegerCriteria value for this CcostAnySearchComand.
     * 
     * @param ccostAnySearchIntegerCriteria
     */
    public void setCcostAnySearchIntegerCriteria(com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria ccostAnySearchIntegerCriteria) {
        this.ccostAnySearchIntegerCriteria = ccostAnySearchIntegerCriteria;
    }


    /**
     * Gets the ccostAnySearchIntegerRangeCriteria value for this CcostAnySearchComand.
     * 
     * @return ccostAnySearchIntegerRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria getCcostAnySearchIntegerRangeCriteria() {
        return ccostAnySearchIntegerRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchIntegerRangeCriteria value for this CcostAnySearchComand.
     * 
     * @param ccostAnySearchIntegerRangeCriteria
     */
    public void setCcostAnySearchIntegerRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria ccostAnySearchIntegerRangeCriteria) {
        this.ccostAnySearchIntegerRangeCriteria = ccostAnySearchIntegerRangeCriteria;
    }


    /**
     * Gets the ccostAnySearchDoubleRangeCriteria value for this CcostAnySearchComand.
     * 
     * @return ccostAnySearchDoubleRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria getCcostAnySearchDoubleRangeCriteria() {
        return ccostAnySearchDoubleRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchDoubleRangeCriteria value for this CcostAnySearchComand.
     * 
     * @param ccostAnySearchDoubleRangeCriteria
     */
    public void setCcostAnySearchDoubleRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria ccostAnySearchDoubleRangeCriteria) {
        this.ccostAnySearchDoubleRangeCriteria = ccostAnySearchDoubleRangeCriteria;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAnySearchComand)) return false;
        CcostAnySearchComand other = (CcostAnySearchComand) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.comune==null && other.getComune()==null) || 
             (this.comune!=null &&
              this.comune.equals(other.getComune()))) &&
            ((this.costId==null && other.getCostId()==null) || 
             (this.costId!=null &&
              this.costId.equals(other.getCostId()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.idOstSedeLegale==null && other.getIdOstSedeLegale()==null) || 
             (this.idOstSedeLegale!=null &&
              this.idOstSedeLegale.equals(other.getIdOstSedeLegale()))) &&
            ((this.idSoggettoFisico==null && other.getIdSoggettoFisico()==null) || 
             (this.idSoggettoFisico!=null &&
              this.idSoggettoFisico.equals(other.getIdSoggettoFisico()))) &&
            ((this.localita==null && other.getLocalita()==null) || 
             (this.localita!=null &&
              this.localita.equals(other.getLocalita()))) &&
            ((this.nostId==null && other.getNostId()==null) || 
             (this.nostId!=null &&
              this.nostId.equals(other.getNostId()))) &&
            ((this.partitaIva==null && other.getPartitaIva()==null) || 
             (this.partitaIva!=null &&
              this.partitaIva.equals(other.getPartitaIva()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.ragioneSociale==null && other.getRagioneSociale()==null) || 
             (this.ragioneSociale!=null &&
              this.ragioneSociale.equals(other.getRagioneSociale()))) &&
            ((this.ccostAnySearchStringCriteria==null && other.getCcostAnySearchStringCriteria()==null) || 
             (this.ccostAnySearchStringCriteria!=null &&
              this.ccostAnySearchStringCriteria.equals(other.getCcostAnySearchStringCriteria()))) &&
            ((this.ccostAnySearchDateRangeCriteria==null && other.getCcostAnySearchDateRangeCriteria()==null) || 
             (this.ccostAnySearchDateRangeCriteria!=null &&
              this.ccostAnySearchDateRangeCriteria.equals(other.getCcostAnySearchDateRangeCriteria()))) &&
            ((this.ccostAnySearchFloatRangeCriteria==null && other.getCcostAnySearchFloatRangeCriteria()==null) || 
             (this.ccostAnySearchFloatRangeCriteria!=null &&
              this.ccostAnySearchFloatRangeCriteria.equals(other.getCcostAnySearchFloatRangeCriteria()))) &&
            ((this.ccostAnySearchIntegerCriteria==null && other.getCcostAnySearchIntegerCriteria()==null) || 
             (this.ccostAnySearchIntegerCriteria!=null &&
              this.ccostAnySearchIntegerCriteria.equals(other.getCcostAnySearchIntegerCriteria()))) &&
            ((this.ccostAnySearchIntegerRangeCriteria==null && other.getCcostAnySearchIntegerRangeCriteria()==null) || 
             (this.ccostAnySearchIntegerRangeCriteria!=null &&
              this.ccostAnySearchIntegerRangeCriteria.equals(other.getCcostAnySearchIntegerRangeCriteria()))) &&
            ((this.ccostAnySearchDoubleRangeCriteria==null && other.getCcostAnySearchDoubleRangeCriteria()==null) || 
             (this.ccostAnySearchDoubleRangeCriteria!=null &&
              this.ccostAnySearchDoubleRangeCriteria.equals(other.getCcostAnySearchDoubleRangeCriteria())));
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
        if (getComune() != null) {
            _hashCode += getComune().hashCode();
        }
        if (getCostId() != null) {
            _hashCode += getCostId().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getIdOstSedeLegale() != null) {
            _hashCode += getIdOstSedeLegale().hashCode();
        }
        if (getIdSoggettoFisico() != null) {
            _hashCode += getIdSoggettoFisico().hashCode();
        }
        if (getLocalita() != null) {
            _hashCode += getLocalita().hashCode();
        }
        if (getNostId() != null) {
            _hashCode += getNostId().hashCode();
        }
        if (getPartitaIva() != null) {
            _hashCode += getPartitaIva().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getRagioneSociale() != null) {
            _hashCode += getRagioneSociale().hashCode();
        }
        if (getCcostAnySearchStringCriteria() != null) {
            _hashCode += getCcostAnySearchStringCriteria().hashCode();
        }
        if (getCcostAnySearchDateRangeCriteria() != null) {
            _hashCode += getCcostAnySearchDateRangeCriteria().hashCode();
        }
        if (getCcostAnySearchFloatRangeCriteria() != null) {
            _hashCode += getCcostAnySearchFloatRangeCriteria().hashCode();
        }
        if (getCcostAnySearchIntegerCriteria() != null) {
            _hashCode += getCcostAnySearchIntegerCriteria().hashCode();
        }
        if (getCcostAnySearchIntegerRangeCriteria() != null) {
            _hashCode += getCcostAnySearchIntegerRangeCriteria().hashCode();
        }
        if (getCcostAnySearchDoubleRangeCriteria() != null) {
            _hashCode += getCcostAnySearchDoubleRangeCriteria().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAnySearchComand.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchComand"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("costId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "costId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOstSedeLegale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOstSedeLegale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSoggettoFisico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSoggettoFisico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nostId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nostId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partitaIva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "partitaIva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ragioneSociale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ragioneSociale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchStringCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchStringCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchStringCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchDateRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDateRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDateRangeCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchFloatRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchFloatRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchFloatRangeCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchIntegerCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchIntegerRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerRangeCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchDoubleRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDoubleRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDoubleRangeCriteria"));
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
