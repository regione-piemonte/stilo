/**
 * RapportiProva.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RapportiProva  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar dataRapp;

    private java.util.Calendar dataValPFR;

    private java.util.Calendar dataValUtente;

    private java.lang.String flagValPFR;

    private java.lang.String flagValUtente;

    private java.lang.Integer idRapporto;

    private java.lang.String idUtente;

    private java.lang.String idUtentePFR;

    private com.hyperborea.sira.ws.Misure[] misures;

    private java.lang.String riferimento;

    private java.lang.Integer tipoLaboratorio;

    private com.hyperborea.sira.ws.WsProfiliProvaRef wsProfiliProvaRef;

    public RapportiProva() {
    }

    public RapportiProva(
           java.util.Calendar dataRapp,
           java.util.Calendar dataValPFR,
           java.util.Calendar dataValUtente,
           java.lang.String flagValPFR,
           java.lang.String flagValUtente,
           java.lang.Integer idRapporto,
           java.lang.String idUtente,
           java.lang.String idUtentePFR,
           com.hyperborea.sira.ws.Misure[] misures,
           java.lang.String riferimento,
           java.lang.Integer tipoLaboratorio,
           com.hyperborea.sira.ws.WsProfiliProvaRef wsProfiliProvaRef) {
        this.dataRapp = dataRapp;
        this.dataValPFR = dataValPFR;
        this.dataValUtente = dataValUtente;
        this.flagValPFR = flagValPFR;
        this.flagValUtente = flagValUtente;
        this.idRapporto = idRapporto;
        this.idUtente = idUtente;
        this.idUtentePFR = idUtentePFR;
        this.misures = misures;
        this.riferimento = riferimento;
        this.tipoLaboratorio = tipoLaboratorio;
        this.wsProfiliProvaRef = wsProfiliProvaRef;
    }


    /**
     * Gets the dataRapp value for this RapportiProva.
     * 
     * @return dataRapp
     */
    public java.util.Calendar getDataRapp() {
        return dataRapp;
    }


    /**
     * Sets the dataRapp value for this RapportiProva.
     * 
     * @param dataRapp
     */
    public void setDataRapp(java.util.Calendar dataRapp) {
        this.dataRapp = dataRapp;
    }


    /**
     * Gets the dataValPFR value for this RapportiProva.
     * 
     * @return dataValPFR
     */
    public java.util.Calendar getDataValPFR() {
        return dataValPFR;
    }


    /**
     * Sets the dataValPFR value for this RapportiProva.
     * 
     * @param dataValPFR
     */
    public void setDataValPFR(java.util.Calendar dataValPFR) {
        this.dataValPFR = dataValPFR;
    }


    /**
     * Gets the dataValUtente value for this RapportiProva.
     * 
     * @return dataValUtente
     */
    public java.util.Calendar getDataValUtente() {
        return dataValUtente;
    }


    /**
     * Sets the dataValUtente value for this RapportiProva.
     * 
     * @param dataValUtente
     */
    public void setDataValUtente(java.util.Calendar dataValUtente) {
        this.dataValUtente = dataValUtente;
    }


    /**
     * Gets the flagValPFR value for this RapportiProva.
     * 
     * @return flagValPFR
     */
    public java.lang.String getFlagValPFR() {
        return flagValPFR;
    }


    /**
     * Sets the flagValPFR value for this RapportiProva.
     * 
     * @param flagValPFR
     */
    public void setFlagValPFR(java.lang.String flagValPFR) {
        this.flagValPFR = flagValPFR;
    }


    /**
     * Gets the flagValUtente value for this RapportiProva.
     * 
     * @return flagValUtente
     */
    public java.lang.String getFlagValUtente() {
        return flagValUtente;
    }


    /**
     * Sets the flagValUtente value for this RapportiProva.
     * 
     * @param flagValUtente
     */
    public void setFlagValUtente(java.lang.String flagValUtente) {
        this.flagValUtente = flagValUtente;
    }


    /**
     * Gets the idRapporto value for this RapportiProva.
     * 
     * @return idRapporto
     */
    public java.lang.Integer getIdRapporto() {
        return idRapporto;
    }


    /**
     * Sets the idRapporto value for this RapportiProva.
     * 
     * @param idRapporto
     */
    public void setIdRapporto(java.lang.Integer idRapporto) {
        this.idRapporto = idRapporto;
    }


    /**
     * Gets the idUtente value for this RapportiProva.
     * 
     * @return idUtente
     */
    public java.lang.String getIdUtente() {
        return idUtente;
    }


    /**
     * Sets the idUtente value for this RapportiProva.
     * 
     * @param idUtente
     */
    public void setIdUtente(java.lang.String idUtente) {
        this.idUtente = idUtente;
    }


    /**
     * Gets the idUtentePFR value for this RapportiProva.
     * 
     * @return idUtentePFR
     */
    public java.lang.String getIdUtentePFR() {
        return idUtentePFR;
    }


    /**
     * Sets the idUtentePFR value for this RapportiProva.
     * 
     * @param idUtentePFR
     */
    public void setIdUtentePFR(java.lang.String idUtentePFR) {
        this.idUtentePFR = idUtentePFR;
    }


    /**
     * Gets the misures value for this RapportiProva.
     * 
     * @return misures
     */
    public com.hyperborea.sira.ws.Misure[] getMisures() {
        return misures;
    }


    /**
     * Sets the misures value for this RapportiProva.
     * 
     * @param misures
     */
    public void setMisures(com.hyperborea.sira.ws.Misure[] misures) {
        this.misures = misures;
    }

    public com.hyperborea.sira.ws.Misure getMisures(int i) {
        return this.misures[i];
    }

    public void setMisures(int i, com.hyperborea.sira.ws.Misure _value) {
        this.misures[i] = _value;
    }


    /**
     * Gets the riferimento value for this RapportiProva.
     * 
     * @return riferimento
     */
    public java.lang.String getRiferimento() {
        return riferimento;
    }


    /**
     * Sets the riferimento value for this RapportiProva.
     * 
     * @param riferimento
     */
    public void setRiferimento(java.lang.String riferimento) {
        this.riferimento = riferimento;
    }


    /**
     * Gets the tipoLaboratorio value for this RapportiProva.
     * 
     * @return tipoLaboratorio
     */
    public java.lang.Integer getTipoLaboratorio() {
        return tipoLaboratorio;
    }


    /**
     * Sets the tipoLaboratorio value for this RapportiProva.
     * 
     * @param tipoLaboratorio
     */
    public void setTipoLaboratorio(java.lang.Integer tipoLaboratorio) {
        this.tipoLaboratorio = tipoLaboratorio;
    }


    /**
     * Gets the wsProfiliProvaRef value for this RapportiProva.
     * 
     * @return wsProfiliProvaRef
     */
    public com.hyperborea.sira.ws.WsProfiliProvaRef getWsProfiliProvaRef() {
        return wsProfiliProvaRef;
    }


    /**
     * Sets the wsProfiliProvaRef value for this RapportiProva.
     * 
     * @param wsProfiliProvaRef
     */
    public void setWsProfiliProvaRef(com.hyperborea.sira.ws.WsProfiliProvaRef wsProfiliProvaRef) {
        this.wsProfiliProvaRef = wsProfiliProvaRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RapportiProva)) return false;
        RapportiProva other = (RapportiProva) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataRapp==null && other.getDataRapp()==null) || 
             (this.dataRapp!=null &&
              this.dataRapp.equals(other.getDataRapp()))) &&
            ((this.dataValPFR==null && other.getDataValPFR()==null) || 
             (this.dataValPFR!=null &&
              this.dataValPFR.equals(other.getDataValPFR()))) &&
            ((this.dataValUtente==null && other.getDataValUtente()==null) || 
             (this.dataValUtente!=null &&
              this.dataValUtente.equals(other.getDataValUtente()))) &&
            ((this.flagValPFR==null && other.getFlagValPFR()==null) || 
             (this.flagValPFR!=null &&
              this.flagValPFR.equals(other.getFlagValPFR()))) &&
            ((this.flagValUtente==null && other.getFlagValUtente()==null) || 
             (this.flagValUtente!=null &&
              this.flagValUtente.equals(other.getFlagValUtente()))) &&
            ((this.idRapporto==null && other.getIdRapporto()==null) || 
             (this.idRapporto!=null &&
              this.idRapporto.equals(other.getIdRapporto()))) &&
            ((this.idUtente==null && other.getIdUtente()==null) || 
             (this.idUtente!=null &&
              this.idUtente.equals(other.getIdUtente()))) &&
            ((this.idUtentePFR==null && other.getIdUtentePFR()==null) || 
             (this.idUtentePFR!=null &&
              this.idUtentePFR.equals(other.getIdUtentePFR()))) &&
            ((this.misures==null && other.getMisures()==null) || 
             (this.misures!=null &&
              java.util.Arrays.equals(this.misures, other.getMisures()))) &&
            ((this.riferimento==null && other.getRiferimento()==null) || 
             (this.riferimento!=null &&
              this.riferimento.equals(other.getRiferimento()))) &&
            ((this.tipoLaboratorio==null && other.getTipoLaboratorio()==null) || 
             (this.tipoLaboratorio!=null &&
              this.tipoLaboratorio.equals(other.getTipoLaboratorio()))) &&
            ((this.wsProfiliProvaRef==null && other.getWsProfiliProvaRef()==null) || 
             (this.wsProfiliProvaRef!=null &&
              this.wsProfiliProvaRef.equals(other.getWsProfiliProvaRef())));
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
        if (getDataRapp() != null) {
            _hashCode += getDataRapp().hashCode();
        }
        if (getDataValPFR() != null) {
            _hashCode += getDataValPFR().hashCode();
        }
        if (getDataValUtente() != null) {
            _hashCode += getDataValUtente().hashCode();
        }
        if (getFlagValPFR() != null) {
            _hashCode += getFlagValPFR().hashCode();
        }
        if (getFlagValUtente() != null) {
            _hashCode += getFlagValUtente().hashCode();
        }
        if (getIdRapporto() != null) {
            _hashCode += getIdRapporto().hashCode();
        }
        if (getIdUtente() != null) {
            _hashCode += getIdUtente().hashCode();
        }
        if (getIdUtentePFR() != null) {
            _hashCode += getIdUtentePFR().hashCode();
        }
        if (getMisures() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMisures());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMisures(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRiferimento() != null) {
            _hashCode += getRiferimento().hashCode();
        }
        if (getTipoLaboratorio() != null) {
            _hashCode += getTipoLaboratorio().hashCode();
        }
        if (getWsProfiliProvaRef() != null) {
            _hashCode += getWsProfiliProvaRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RapportiProva.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "rapportiProva"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataRapp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataRapp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataValPFR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataValPFR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataValUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataValUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagValPFR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagValPFR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagValUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagValUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRapporto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRapporto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUtentePFR");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUtentePFR"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misures");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misures"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoLaboratorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoLaboratorio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsProfiliProvaRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsProfiliProvaRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsProfiliProvaRef"));
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
