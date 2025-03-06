/**
 * CcostArt.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostArt  implements java.io.Serializable {
    private java.lang.String descrAfferente;

    private java.lang.String descrEffluente;

    private java.lang.Integer flagartificiale;

    private java.lang.Integer idCcost;

    private java.lang.String idCorpoIdrico;

    private java.lang.String idsiramigra;

    private java.lang.Float lunghezza;

    private java.lang.Float portatatotalemedia;

    private java.lang.String tipologia;

    public CcostArt() {
    }

    public CcostArt(
           java.lang.String descrAfferente,
           java.lang.String descrEffluente,
           java.lang.Integer flagartificiale,
           java.lang.Integer idCcost,
           java.lang.String idCorpoIdrico,
           java.lang.String idsiramigra,
           java.lang.Float lunghezza,
           java.lang.Float portatatotalemedia,
           java.lang.String tipologia) {
           this.descrAfferente = descrAfferente;
           this.descrEffluente = descrEffluente;
           this.flagartificiale = flagartificiale;
           this.idCcost = idCcost;
           this.idCorpoIdrico = idCorpoIdrico;
           this.idsiramigra = idsiramigra;
           this.lunghezza = lunghezza;
           this.portatatotalemedia = portatatotalemedia;
           this.tipologia = tipologia;
    }


    /**
     * Gets the descrAfferente value for this CcostArt.
     * 
     * @return descrAfferente
     */
    public java.lang.String getDescrAfferente() {
        return descrAfferente;
    }


    /**
     * Sets the descrAfferente value for this CcostArt.
     * 
     * @param descrAfferente
     */
    public void setDescrAfferente(java.lang.String descrAfferente) {
        this.descrAfferente = descrAfferente;
    }


    /**
     * Gets the descrEffluente value for this CcostArt.
     * 
     * @return descrEffluente
     */
    public java.lang.String getDescrEffluente() {
        return descrEffluente;
    }


    /**
     * Sets the descrEffluente value for this CcostArt.
     * 
     * @param descrEffluente
     */
    public void setDescrEffluente(java.lang.String descrEffluente) {
        this.descrEffluente = descrEffluente;
    }


    /**
     * Gets the flagartificiale value for this CcostArt.
     * 
     * @return flagartificiale
     */
    public java.lang.Integer getFlagartificiale() {
        return flagartificiale;
    }


    /**
     * Sets the flagartificiale value for this CcostArt.
     * 
     * @param flagartificiale
     */
    public void setFlagartificiale(java.lang.Integer flagartificiale) {
        this.flagartificiale = flagartificiale;
    }


    /**
     * Gets the idCcost value for this CcostArt.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostArt.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the idCorpoIdrico value for this CcostArt.
     * 
     * @return idCorpoIdrico
     */
    public java.lang.String getIdCorpoIdrico() {
        return idCorpoIdrico;
    }


    /**
     * Sets the idCorpoIdrico value for this CcostArt.
     * 
     * @param idCorpoIdrico
     */
    public void setIdCorpoIdrico(java.lang.String idCorpoIdrico) {
        this.idCorpoIdrico = idCorpoIdrico;
    }


    /**
     * Gets the idsiramigra value for this CcostArt.
     * 
     * @return idsiramigra
     */
    public java.lang.String getIdsiramigra() {
        return idsiramigra;
    }


    /**
     * Sets the idsiramigra value for this CcostArt.
     * 
     * @param idsiramigra
     */
    public void setIdsiramigra(java.lang.String idsiramigra) {
        this.idsiramigra = idsiramigra;
    }


    /**
     * Gets the lunghezza value for this CcostArt.
     * 
     * @return lunghezza
     */
    public java.lang.Float getLunghezza() {
        return lunghezza;
    }


    /**
     * Sets the lunghezza value for this CcostArt.
     * 
     * @param lunghezza
     */
    public void setLunghezza(java.lang.Float lunghezza) {
        this.lunghezza = lunghezza;
    }


    /**
     * Gets the portatatotalemedia value for this CcostArt.
     * 
     * @return portatatotalemedia
     */
    public java.lang.Float getPortatatotalemedia() {
        return portatatotalemedia;
    }


    /**
     * Sets the portatatotalemedia value for this CcostArt.
     * 
     * @param portatatotalemedia
     */
    public void setPortatatotalemedia(java.lang.Float portatatotalemedia) {
        this.portatatotalemedia = portatatotalemedia;
    }


    /**
     * Gets the tipologia value for this CcostArt.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostArt.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostArt)) return false;
        CcostArt other = (CcostArt) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.descrAfferente==null && other.getDescrAfferente()==null) || 
             (this.descrAfferente!=null &&
              this.descrAfferente.equals(other.getDescrAfferente()))) &&
            ((this.descrEffluente==null && other.getDescrEffluente()==null) || 
             (this.descrEffluente!=null &&
              this.descrEffluente.equals(other.getDescrEffluente()))) &&
            ((this.flagartificiale==null && other.getFlagartificiale()==null) || 
             (this.flagartificiale!=null &&
              this.flagartificiale.equals(other.getFlagartificiale()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.idCorpoIdrico==null && other.getIdCorpoIdrico()==null) || 
             (this.idCorpoIdrico!=null &&
              this.idCorpoIdrico.equals(other.getIdCorpoIdrico()))) &&
            ((this.idsiramigra==null && other.getIdsiramigra()==null) || 
             (this.idsiramigra!=null &&
              this.idsiramigra.equals(other.getIdsiramigra()))) &&
            ((this.lunghezza==null && other.getLunghezza()==null) || 
             (this.lunghezza!=null &&
              this.lunghezza.equals(other.getLunghezza()))) &&
            ((this.portatatotalemedia==null && other.getPortatatotalemedia()==null) || 
             (this.portatatotalemedia!=null &&
              this.portatatotalemedia.equals(other.getPortatatotalemedia()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia())));
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
        if (getDescrAfferente() != null) {
            _hashCode += getDescrAfferente().hashCode();
        }
        if (getDescrEffluente() != null) {
            _hashCode += getDescrEffluente().hashCode();
        }
        if (getFlagartificiale() != null) {
            _hashCode += getFlagartificiale().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getIdCorpoIdrico() != null) {
            _hashCode += getIdCorpoIdrico().hashCode();
        }
        if (getIdsiramigra() != null) {
            _hashCode += getIdsiramigra().hashCode();
        }
        if (getLunghezza() != null) {
            _hashCode += getLunghezza().hashCode();
        }
        if (getPortatatotalemedia() != null) {
            _hashCode += getPortatatotalemedia().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostArt.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostArt"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrAfferente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrAfferente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrEffluente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrEffluente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagartificiale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "flagartificiale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("idCorpoIdrico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCorpoIdrico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idsiramigra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idsiramigra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lunghezza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "lunghezza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("portatatotalemedia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "portatatotalemedia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
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
