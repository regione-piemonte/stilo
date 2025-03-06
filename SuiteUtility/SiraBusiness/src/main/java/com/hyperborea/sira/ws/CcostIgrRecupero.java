/**
 * CcostIgrRecupero.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIgrRecupero  implements java.io.Serializable {
    private java.lang.String destinazioneProdotti;

    private java.lang.String destinazioneScarti;

    private java.lang.Integer idCcost;

    private java.lang.String presidiAmbientali;

    private java.lang.String pretrattamenti;

    private java.lang.String tipoImpianto;

    private java.lang.String tipoRifiuti;

    public CcostIgrRecupero() {
    }

    public CcostIgrRecupero(
           java.lang.String destinazioneProdotti,
           java.lang.String destinazioneScarti,
           java.lang.Integer idCcost,
           java.lang.String presidiAmbientali,
           java.lang.String pretrattamenti,
           java.lang.String tipoImpianto,
           java.lang.String tipoRifiuti) {
           this.destinazioneProdotti = destinazioneProdotti;
           this.destinazioneScarti = destinazioneScarti;
           this.idCcost = idCcost;
           this.presidiAmbientali = presidiAmbientali;
           this.pretrattamenti = pretrattamenti;
           this.tipoImpianto = tipoImpianto;
           this.tipoRifiuti = tipoRifiuti;
    }


    /**
     * Gets the destinazioneProdotti value for this CcostIgrRecupero.
     * 
     * @return destinazioneProdotti
     */
    public java.lang.String getDestinazioneProdotti() {
        return destinazioneProdotti;
    }


    /**
     * Sets the destinazioneProdotti value for this CcostIgrRecupero.
     * 
     * @param destinazioneProdotti
     */
    public void setDestinazioneProdotti(java.lang.String destinazioneProdotti) {
        this.destinazioneProdotti = destinazioneProdotti;
    }


    /**
     * Gets the destinazioneScarti value for this CcostIgrRecupero.
     * 
     * @return destinazioneScarti
     */
    public java.lang.String getDestinazioneScarti() {
        return destinazioneScarti;
    }


    /**
     * Sets the destinazioneScarti value for this CcostIgrRecupero.
     * 
     * @param destinazioneScarti
     */
    public void setDestinazioneScarti(java.lang.String destinazioneScarti) {
        this.destinazioneScarti = destinazioneScarti;
    }


    /**
     * Gets the idCcost value for this CcostIgrRecupero.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIgrRecupero.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the presidiAmbientali value for this CcostIgrRecupero.
     * 
     * @return presidiAmbientali
     */
    public java.lang.String getPresidiAmbientali() {
        return presidiAmbientali;
    }


    /**
     * Sets the presidiAmbientali value for this CcostIgrRecupero.
     * 
     * @param presidiAmbientali
     */
    public void setPresidiAmbientali(java.lang.String presidiAmbientali) {
        this.presidiAmbientali = presidiAmbientali;
    }


    /**
     * Gets the pretrattamenti value for this CcostIgrRecupero.
     * 
     * @return pretrattamenti
     */
    public java.lang.String getPretrattamenti() {
        return pretrattamenti;
    }


    /**
     * Sets the pretrattamenti value for this CcostIgrRecupero.
     * 
     * @param pretrattamenti
     */
    public void setPretrattamenti(java.lang.String pretrattamenti) {
        this.pretrattamenti = pretrattamenti;
    }


    /**
     * Gets the tipoImpianto value for this CcostIgrRecupero.
     * 
     * @return tipoImpianto
     */
    public java.lang.String getTipoImpianto() {
        return tipoImpianto;
    }


    /**
     * Sets the tipoImpianto value for this CcostIgrRecupero.
     * 
     * @param tipoImpianto
     */
    public void setTipoImpianto(java.lang.String tipoImpianto) {
        this.tipoImpianto = tipoImpianto;
    }


    /**
     * Gets the tipoRifiuti value for this CcostIgrRecupero.
     * 
     * @return tipoRifiuti
     */
    public java.lang.String getTipoRifiuti() {
        return tipoRifiuti;
    }


    /**
     * Sets the tipoRifiuti value for this CcostIgrRecupero.
     * 
     * @param tipoRifiuti
     */
    public void setTipoRifiuti(java.lang.String tipoRifiuti) {
        this.tipoRifiuti = tipoRifiuti;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIgrRecupero)) return false;
        CcostIgrRecupero other = (CcostIgrRecupero) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.destinazioneProdotti==null && other.getDestinazioneProdotti()==null) || 
             (this.destinazioneProdotti!=null &&
              this.destinazioneProdotti.equals(other.getDestinazioneProdotti()))) &&
            ((this.destinazioneScarti==null && other.getDestinazioneScarti()==null) || 
             (this.destinazioneScarti!=null &&
              this.destinazioneScarti.equals(other.getDestinazioneScarti()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.presidiAmbientali==null && other.getPresidiAmbientali()==null) || 
             (this.presidiAmbientali!=null &&
              this.presidiAmbientali.equals(other.getPresidiAmbientali()))) &&
            ((this.pretrattamenti==null && other.getPretrattamenti()==null) || 
             (this.pretrattamenti!=null &&
              this.pretrattamenti.equals(other.getPretrattamenti()))) &&
            ((this.tipoImpianto==null && other.getTipoImpianto()==null) || 
             (this.tipoImpianto!=null &&
              this.tipoImpianto.equals(other.getTipoImpianto()))) &&
            ((this.tipoRifiuti==null && other.getTipoRifiuti()==null) || 
             (this.tipoRifiuti!=null &&
              this.tipoRifiuti.equals(other.getTipoRifiuti())));
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
        if (getDestinazioneProdotti() != null) {
            _hashCode += getDestinazioneProdotti().hashCode();
        }
        if (getDestinazioneScarti() != null) {
            _hashCode += getDestinazioneScarti().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getPresidiAmbientali() != null) {
            _hashCode += getPresidiAmbientali().hashCode();
        }
        if (getPretrattamenti() != null) {
            _hashCode += getPretrattamenti().hashCode();
        }
        if (getTipoImpianto() != null) {
            _hashCode += getTipoImpianto().hashCode();
        }
        if (getTipoRifiuti() != null) {
            _hashCode += getTipoRifiuti().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostIgrRecupero.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrRecupero"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazioneProdotti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazioneProdotti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazioneScarti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazioneScarti"));
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
        elemField.setFieldName("presidiAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "presidiAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pretrattamenti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pretrattamenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoImpianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoRifiuti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoRifiuti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
