/**
 * CcostPuntiMonitoraggioCon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostPuntiMonitoraggioCon  implements java.io.Serializable {
    private java.lang.String ambienteCircostante;

    private com.hyperborea.sira.ws.CcostCentralineBordoni ccostCentralineBordoni;

    private com.hyperborea.sira.ws.CcostStazioniCor ccostStazioniCor;

    private com.hyperborea.sira.ws.CcostStazioniGrass ccostStazioniGrass;

    private com.hyperborea.sira.ws.CcostStazioniQualAcque ccostStazioniQualAcque;

    private com.hyperborea.sira.ws.CcostStazioniSar ccostStazioniSar;

    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioCon[] puntiRetiMonitoraggioCons;

    public CcostPuntiMonitoraggioCon() {
    }

    public CcostPuntiMonitoraggioCon(
           java.lang.String ambienteCircostante,
           com.hyperborea.sira.ws.CcostCentralineBordoni ccostCentralineBordoni,
           com.hyperborea.sira.ws.CcostStazioniCor ccostStazioniCor,
           com.hyperborea.sira.ws.CcostStazioniGrass ccostStazioniGrass,
           com.hyperborea.sira.ws.CcostStazioniQualAcque ccostStazioniQualAcque,
           com.hyperborea.sira.ws.CcostStazioniSar ccostStazioniSar,
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioCon[] puntiRetiMonitoraggioCons) {
           this.ambienteCircostante = ambienteCircostante;
           this.ccostCentralineBordoni = ccostCentralineBordoni;
           this.ccostStazioniCor = ccostStazioniCor;
           this.ccostStazioniGrass = ccostStazioniGrass;
           this.ccostStazioniQualAcque = ccostStazioniQualAcque;
           this.ccostStazioniSar = ccostStazioniSar;
           this.idCcost = idCcost;
           this.puntiRetiMonitoraggioCons = puntiRetiMonitoraggioCons;
    }


    /**
     * Gets the ambienteCircostante value for this CcostPuntiMonitoraggioCon.
     * 
     * @return ambienteCircostante
     */
    public java.lang.String getAmbienteCircostante() {
        return ambienteCircostante;
    }


    /**
     * Sets the ambienteCircostante value for this CcostPuntiMonitoraggioCon.
     * 
     * @param ambienteCircostante
     */
    public void setAmbienteCircostante(java.lang.String ambienteCircostante) {
        this.ambienteCircostante = ambienteCircostante;
    }


    /**
     * Gets the ccostCentralineBordoni value for this CcostPuntiMonitoraggioCon.
     * 
     * @return ccostCentralineBordoni
     */
    public com.hyperborea.sira.ws.CcostCentralineBordoni getCcostCentralineBordoni() {
        return ccostCentralineBordoni;
    }


    /**
     * Sets the ccostCentralineBordoni value for this CcostPuntiMonitoraggioCon.
     * 
     * @param ccostCentralineBordoni
     */
    public void setCcostCentralineBordoni(com.hyperborea.sira.ws.CcostCentralineBordoni ccostCentralineBordoni) {
        this.ccostCentralineBordoni = ccostCentralineBordoni;
    }


    /**
     * Gets the ccostStazioniCor value for this CcostPuntiMonitoraggioCon.
     * 
     * @return ccostStazioniCor
     */
    public com.hyperborea.sira.ws.CcostStazioniCor getCcostStazioniCor() {
        return ccostStazioniCor;
    }


    /**
     * Sets the ccostStazioniCor value for this CcostPuntiMonitoraggioCon.
     * 
     * @param ccostStazioniCor
     */
    public void setCcostStazioniCor(com.hyperborea.sira.ws.CcostStazioniCor ccostStazioniCor) {
        this.ccostStazioniCor = ccostStazioniCor;
    }


    /**
     * Gets the ccostStazioniGrass value for this CcostPuntiMonitoraggioCon.
     * 
     * @return ccostStazioniGrass
     */
    public com.hyperborea.sira.ws.CcostStazioniGrass getCcostStazioniGrass() {
        return ccostStazioniGrass;
    }


    /**
     * Sets the ccostStazioniGrass value for this CcostPuntiMonitoraggioCon.
     * 
     * @param ccostStazioniGrass
     */
    public void setCcostStazioniGrass(com.hyperborea.sira.ws.CcostStazioniGrass ccostStazioniGrass) {
        this.ccostStazioniGrass = ccostStazioniGrass;
    }


    /**
     * Gets the ccostStazioniQualAcque value for this CcostPuntiMonitoraggioCon.
     * 
     * @return ccostStazioniQualAcque
     */
    public com.hyperborea.sira.ws.CcostStazioniQualAcque getCcostStazioniQualAcque() {
        return ccostStazioniQualAcque;
    }


    /**
     * Sets the ccostStazioniQualAcque value for this CcostPuntiMonitoraggioCon.
     * 
     * @param ccostStazioniQualAcque
     */
    public void setCcostStazioniQualAcque(com.hyperborea.sira.ws.CcostStazioniQualAcque ccostStazioniQualAcque) {
        this.ccostStazioniQualAcque = ccostStazioniQualAcque;
    }


    /**
     * Gets the ccostStazioniSar value for this CcostPuntiMonitoraggioCon.
     * 
     * @return ccostStazioniSar
     */
    public com.hyperborea.sira.ws.CcostStazioniSar getCcostStazioniSar() {
        return ccostStazioniSar;
    }


    /**
     * Sets the ccostStazioniSar value for this CcostPuntiMonitoraggioCon.
     * 
     * @param ccostStazioniSar
     */
    public void setCcostStazioniSar(com.hyperborea.sira.ws.CcostStazioniSar ccostStazioniSar) {
        this.ccostStazioniSar = ccostStazioniSar;
    }


    /**
     * Gets the idCcost value for this CcostPuntiMonitoraggioCon.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostPuntiMonitoraggioCon.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the puntiRetiMonitoraggioCons value for this CcostPuntiMonitoraggioCon.
     * 
     * @return puntiRetiMonitoraggioCons
     */
    public com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioCon[] getPuntiRetiMonitoraggioCons() {
        return puntiRetiMonitoraggioCons;
    }


    /**
     * Sets the puntiRetiMonitoraggioCons value for this CcostPuntiMonitoraggioCon.
     * 
     * @param puntiRetiMonitoraggioCons
     */
    public void setPuntiRetiMonitoraggioCons(com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioCon[] puntiRetiMonitoraggioCons) {
        this.puntiRetiMonitoraggioCons = puntiRetiMonitoraggioCons;
    }

    public com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioCon getPuntiRetiMonitoraggioCons(int i) {
        return this.puntiRetiMonitoraggioCons[i];
    }

    public void setPuntiRetiMonitoraggioCons(int i, com.hyperborea.sira.ws.RetiMonitoraggioCcostPuntiMonitoraggioCon _value) {
        this.puntiRetiMonitoraggioCons[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostPuntiMonitoraggioCon)) return false;
        CcostPuntiMonitoraggioCon other = (CcostPuntiMonitoraggioCon) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ambienteCircostante==null && other.getAmbienteCircostante()==null) || 
             (this.ambienteCircostante!=null &&
              this.ambienteCircostante.equals(other.getAmbienteCircostante()))) &&
            ((this.ccostCentralineBordoni==null && other.getCcostCentralineBordoni()==null) || 
             (this.ccostCentralineBordoni!=null &&
              this.ccostCentralineBordoni.equals(other.getCcostCentralineBordoni()))) &&
            ((this.ccostStazioniCor==null && other.getCcostStazioniCor()==null) || 
             (this.ccostStazioniCor!=null &&
              this.ccostStazioniCor.equals(other.getCcostStazioniCor()))) &&
            ((this.ccostStazioniGrass==null && other.getCcostStazioniGrass()==null) || 
             (this.ccostStazioniGrass!=null &&
              this.ccostStazioniGrass.equals(other.getCcostStazioniGrass()))) &&
            ((this.ccostStazioniQualAcque==null && other.getCcostStazioniQualAcque()==null) || 
             (this.ccostStazioniQualAcque!=null &&
              this.ccostStazioniQualAcque.equals(other.getCcostStazioniQualAcque()))) &&
            ((this.ccostStazioniSar==null && other.getCcostStazioniSar()==null) || 
             (this.ccostStazioniSar!=null &&
              this.ccostStazioniSar.equals(other.getCcostStazioniSar()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.puntiRetiMonitoraggioCons==null && other.getPuntiRetiMonitoraggioCons()==null) || 
             (this.puntiRetiMonitoraggioCons!=null &&
              java.util.Arrays.equals(this.puntiRetiMonitoraggioCons, other.getPuntiRetiMonitoraggioCons())));
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
        if (getAmbienteCircostante() != null) {
            _hashCode += getAmbienteCircostante().hashCode();
        }
        if (getCcostCentralineBordoni() != null) {
            _hashCode += getCcostCentralineBordoni().hashCode();
        }
        if (getCcostStazioniCor() != null) {
            _hashCode += getCcostStazioniCor().hashCode();
        }
        if (getCcostStazioniGrass() != null) {
            _hashCode += getCcostStazioniGrass().hashCode();
        }
        if (getCcostStazioniQualAcque() != null) {
            _hashCode += getCcostStazioniQualAcque().hashCode();
        }
        if (getCcostStazioniSar() != null) {
            _hashCode += getCcostStazioniSar().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getPuntiRetiMonitoraggioCons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPuntiRetiMonitoraggioCons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPuntiRetiMonitoraggioCons(), i);
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
        new org.apache.axis.description.TypeDesc(CcostPuntiMonitoraggioCon.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMonitoraggioCon"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ambienteCircostante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ambienteCircostante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostCentralineBordoni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostCentralineBordoni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCentralineBordoni"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostStazioniCor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostStazioniCor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniCor"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostStazioniGrass");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostStazioniGrass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniGrass"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostStazioniQualAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostStazioniQualAcque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniQualAcque"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostStazioniSar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostStazioniSar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostStazioniSar"));
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
        elemField.setFieldName("puntiRetiMonitoraggioCons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "puntiRetiMonitoraggioCons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggioCcostPuntiMonitoraggioCon"));
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
