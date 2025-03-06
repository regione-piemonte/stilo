/**
 * CcostImpProdEnTermicaFer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostImpProdEnTermicaFer  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostImpAerotermico ccostImpAerotermico;

    private com.hyperborea.sira.ws.CcostImpGeotermico ccostImpGeotermico;

    private com.hyperborea.sira.ws.CcostImpIdrotermico ccostImpIdrotermico;

    private com.hyperborea.sira.ws.CcostImpTermicoSolare ccostImpTermicoSolare;

    private java.lang.String destEnNonAutocons;

    private java.lang.Float enNonAutoconsumata;

    private java.lang.Integer idCcost;

    private java.lang.Integer perAcquaCalda;

    private java.lang.Integer perRaffrescamento;

    private java.lang.Integer perRiscaldamento;

    private java.lang.Float potenzaTermica;

    private java.lang.Float producibilitaTermica;

    private java.lang.Integer teleriscaldamento;

    public CcostImpProdEnTermicaFer() {
    }

    public CcostImpProdEnTermicaFer(
           com.hyperborea.sira.ws.CcostImpAerotermico ccostImpAerotermico,
           com.hyperborea.sira.ws.CcostImpGeotermico ccostImpGeotermico,
           com.hyperborea.sira.ws.CcostImpIdrotermico ccostImpIdrotermico,
           com.hyperborea.sira.ws.CcostImpTermicoSolare ccostImpTermicoSolare,
           java.lang.String destEnNonAutocons,
           java.lang.Float enNonAutoconsumata,
           java.lang.Integer idCcost,
           java.lang.Integer perAcquaCalda,
           java.lang.Integer perRaffrescamento,
           java.lang.Integer perRiscaldamento,
           java.lang.Float potenzaTermica,
           java.lang.Float producibilitaTermica,
           java.lang.Integer teleriscaldamento) {
           this.ccostImpAerotermico = ccostImpAerotermico;
           this.ccostImpGeotermico = ccostImpGeotermico;
           this.ccostImpIdrotermico = ccostImpIdrotermico;
           this.ccostImpTermicoSolare = ccostImpTermicoSolare;
           this.destEnNonAutocons = destEnNonAutocons;
           this.enNonAutoconsumata = enNonAutoconsumata;
           this.idCcost = idCcost;
           this.perAcquaCalda = perAcquaCalda;
           this.perRaffrescamento = perRaffrescamento;
           this.perRiscaldamento = perRiscaldamento;
           this.potenzaTermica = potenzaTermica;
           this.producibilitaTermica = producibilitaTermica;
           this.teleriscaldamento = teleriscaldamento;
    }


    /**
     * Gets the ccostImpAerotermico value for this CcostImpProdEnTermicaFer.
     * 
     * @return ccostImpAerotermico
     */
    public com.hyperborea.sira.ws.CcostImpAerotermico getCcostImpAerotermico() {
        return ccostImpAerotermico;
    }


    /**
     * Sets the ccostImpAerotermico value for this CcostImpProdEnTermicaFer.
     * 
     * @param ccostImpAerotermico
     */
    public void setCcostImpAerotermico(com.hyperborea.sira.ws.CcostImpAerotermico ccostImpAerotermico) {
        this.ccostImpAerotermico = ccostImpAerotermico;
    }


    /**
     * Gets the ccostImpGeotermico value for this CcostImpProdEnTermicaFer.
     * 
     * @return ccostImpGeotermico
     */
    public com.hyperborea.sira.ws.CcostImpGeotermico getCcostImpGeotermico() {
        return ccostImpGeotermico;
    }


    /**
     * Sets the ccostImpGeotermico value for this CcostImpProdEnTermicaFer.
     * 
     * @param ccostImpGeotermico
     */
    public void setCcostImpGeotermico(com.hyperborea.sira.ws.CcostImpGeotermico ccostImpGeotermico) {
        this.ccostImpGeotermico = ccostImpGeotermico;
    }


    /**
     * Gets the ccostImpIdrotermico value for this CcostImpProdEnTermicaFer.
     * 
     * @return ccostImpIdrotermico
     */
    public com.hyperborea.sira.ws.CcostImpIdrotermico getCcostImpIdrotermico() {
        return ccostImpIdrotermico;
    }


    /**
     * Sets the ccostImpIdrotermico value for this CcostImpProdEnTermicaFer.
     * 
     * @param ccostImpIdrotermico
     */
    public void setCcostImpIdrotermico(com.hyperborea.sira.ws.CcostImpIdrotermico ccostImpIdrotermico) {
        this.ccostImpIdrotermico = ccostImpIdrotermico;
    }


    /**
     * Gets the ccostImpTermicoSolare value for this CcostImpProdEnTermicaFer.
     * 
     * @return ccostImpTermicoSolare
     */
    public com.hyperborea.sira.ws.CcostImpTermicoSolare getCcostImpTermicoSolare() {
        return ccostImpTermicoSolare;
    }


    /**
     * Sets the ccostImpTermicoSolare value for this CcostImpProdEnTermicaFer.
     * 
     * @param ccostImpTermicoSolare
     */
    public void setCcostImpTermicoSolare(com.hyperborea.sira.ws.CcostImpTermicoSolare ccostImpTermicoSolare) {
        this.ccostImpTermicoSolare = ccostImpTermicoSolare;
    }


    /**
     * Gets the destEnNonAutocons value for this CcostImpProdEnTermicaFer.
     * 
     * @return destEnNonAutocons
     */
    public java.lang.String getDestEnNonAutocons() {
        return destEnNonAutocons;
    }


    /**
     * Sets the destEnNonAutocons value for this CcostImpProdEnTermicaFer.
     * 
     * @param destEnNonAutocons
     */
    public void setDestEnNonAutocons(java.lang.String destEnNonAutocons) {
        this.destEnNonAutocons = destEnNonAutocons;
    }


    /**
     * Gets the enNonAutoconsumata value for this CcostImpProdEnTermicaFer.
     * 
     * @return enNonAutoconsumata
     */
    public java.lang.Float getEnNonAutoconsumata() {
        return enNonAutoconsumata;
    }


    /**
     * Sets the enNonAutoconsumata value for this CcostImpProdEnTermicaFer.
     * 
     * @param enNonAutoconsumata
     */
    public void setEnNonAutoconsumata(java.lang.Float enNonAutoconsumata) {
        this.enNonAutoconsumata = enNonAutoconsumata;
    }


    /**
     * Gets the idCcost value for this CcostImpProdEnTermicaFer.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostImpProdEnTermicaFer.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the perAcquaCalda value for this CcostImpProdEnTermicaFer.
     * 
     * @return perAcquaCalda
     */
    public java.lang.Integer getPerAcquaCalda() {
        return perAcquaCalda;
    }


    /**
     * Sets the perAcquaCalda value for this CcostImpProdEnTermicaFer.
     * 
     * @param perAcquaCalda
     */
    public void setPerAcquaCalda(java.lang.Integer perAcquaCalda) {
        this.perAcquaCalda = perAcquaCalda;
    }


    /**
     * Gets the perRaffrescamento value for this CcostImpProdEnTermicaFer.
     * 
     * @return perRaffrescamento
     */
    public java.lang.Integer getPerRaffrescamento() {
        return perRaffrescamento;
    }


    /**
     * Sets the perRaffrescamento value for this CcostImpProdEnTermicaFer.
     * 
     * @param perRaffrescamento
     */
    public void setPerRaffrescamento(java.lang.Integer perRaffrescamento) {
        this.perRaffrescamento = perRaffrescamento;
    }


    /**
     * Gets the perRiscaldamento value for this CcostImpProdEnTermicaFer.
     * 
     * @return perRiscaldamento
     */
    public java.lang.Integer getPerRiscaldamento() {
        return perRiscaldamento;
    }


    /**
     * Sets the perRiscaldamento value for this CcostImpProdEnTermicaFer.
     * 
     * @param perRiscaldamento
     */
    public void setPerRiscaldamento(java.lang.Integer perRiscaldamento) {
        this.perRiscaldamento = perRiscaldamento;
    }


    /**
     * Gets the potenzaTermica value for this CcostImpProdEnTermicaFer.
     * 
     * @return potenzaTermica
     */
    public java.lang.Float getPotenzaTermica() {
        return potenzaTermica;
    }


    /**
     * Sets the potenzaTermica value for this CcostImpProdEnTermicaFer.
     * 
     * @param potenzaTermica
     */
    public void setPotenzaTermica(java.lang.Float potenzaTermica) {
        this.potenzaTermica = potenzaTermica;
    }


    /**
     * Gets the producibilitaTermica value for this CcostImpProdEnTermicaFer.
     * 
     * @return producibilitaTermica
     */
    public java.lang.Float getProducibilitaTermica() {
        return producibilitaTermica;
    }


    /**
     * Sets the producibilitaTermica value for this CcostImpProdEnTermicaFer.
     * 
     * @param producibilitaTermica
     */
    public void setProducibilitaTermica(java.lang.Float producibilitaTermica) {
        this.producibilitaTermica = producibilitaTermica;
    }


    /**
     * Gets the teleriscaldamento value for this CcostImpProdEnTermicaFer.
     * 
     * @return teleriscaldamento
     */
    public java.lang.Integer getTeleriscaldamento() {
        return teleriscaldamento;
    }


    /**
     * Sets the teleriscaldamento value for this CcostImpProdEnTermicaFer.
     * 
     * @param teleriscaldamento
     */
    public void setTeleriscaldamento(java.lang.Integer teleriscaldamento) {
        this.teleriscaldamento = teleriscaldamento;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostImpProdEnTermicaFer)) return false;
        CcostImpProdEnTermicaFer other = (CcostImpProdEnTermicaFer) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostImpAerotermico==null && other.getCcostImpAerotermico()==null) || 
             (this.ccostImpAerotermico!=null &&
              this.ccostImpAerotermico.equals(other.getCcostImpAerotermico()))) &&
            ((this.ccostImpGeotermico==null && other.getCcostImpGeotermico()==null) || 
             (this.ccostImpGeotermico!=null &&
              this.ccostImpGeotermico.equals(other.getCcostImpGeotermico()))) &&
            ((this.ccostImpIdrotermico==null && other.getCcostImpIdrotermico()==null) || 
             (this.ccostImpIdrotermico!=null &&
              this.ccostImpIdrotermico.equals(other.getCcostImpIdrotermico()))) &&
            ((this.ccostImpTermicoSolare==null && other.getCcostImpTermicoSolare()==null) || 
             (this.ccostImpTermicoSolare!=null &&
              this.ccostImpTermicoSolare.equals(other.getCcostImpTermicoSolare()))) &&
            ((this.destEnNonAutocons==null && other.getDestEnNonAutocons()==null) || 
             (this.destEnNonAutocons!=null &&
              this.destEnNonAutocons.equals(other.getDestEnNonAutocons()))) &&
            ((this.enNonAutoconsumata==null && other.getEnNonAutoconsumata()==null) || 
             (this.enNonAutoconsumata!=null &&
              this.enNonAutoconsumata.equals(other.getEnNonAutoconsumata()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.perAcquaCalda==null && other.getPerAcquaCalda()==null) || 
             (this.perAcquaCalda!=null &&
              this.perAcquaCalda.equals(other.getPerAcquaCalda()))) &&
            ((this.perRaffrescamento==null && other.getPerRaffrescamento()==null) || 
             (this.perRaffrescamento!=null &&
              this.perRaffrescamento.equals(other.getPerRaffrescamento()))) &&
            ((this.perRiscaldamento==null && other.getPerRiscaldamento()==null) || 
             (this.perRiscaldamento!=null &&
              this.perRiscaldamento.equals(other.getPerRiscaldamento()))) &&
            ((this.potenzaTermica==null && other.getPotenzaTermica()==null) || 
             (this.potenzaTermica!=null &&
              this.potenzaTermica.equals(other.getPotenzaTermica()))) &&
            ((this.producibilitaTermica==null && other.getProducibilitaTermica()==null) || 
             (this.producibilitaTermica!=null &&
              this.producibilitaTermica.equals(other.getProducibilitaTermica()))) &&
            ((this.teleriscaldamento==null && other.getTeleriscaldamento()==null) || 
             (this.teleriscaldamento!=null &&
              this.teleriscaldamento.equals(other.getTeleriscaldamento())));
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
        if (getCcostImpAerotermico() != null) {
            _hashCode += getCcostImpAerotermico().hashCode();
        }
        if (getCcostImpGeotermico() != null) {
            _hashCode += getCcostImpGeotermico().hashCode();
        }
        if (getCcostImpIdrotermico() != null) {
            _hashCode += getCcostImpIdrotermico().hashCode();
        }
        if (getCcostImpTermicoSolare() != null) {
            _hashCode += getCcostImpTermicoSolare().hashCode();
        }
        if (getDestEnNonAutocons() != null) {
            _hashCode += getDestEnNonAutocons().hashCode();
        }
        if (getEnNonAutoconsumata() != null) {
            _hashCode += getEnNonAutoconsumata().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getPerAcquaCalda() != null) {
            _hashCode += getPerAcquaCalda().hashCode();
        }
        if (getPerRaffrescamento() != null) {
            _hashCode += getPerRaffrescamento().hashCode();
        }
        if (getPerRiscaldamento() != null) {
            _hashCode += getPerRiscaldamento().hashCode();
        }
        if (getPotenzaTermica() != null) {
            _hashCode += getPotenzaTermica().hashCode();
        }
        if (getProducibilitaTermica() != null) {
            _hashCode += getProducibilitaTermica().hashCode();
        }
        if (getTeleriscaldamento() != null) {
            _hashCode += getTeleriscaldamento().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostImpProdEnTermicaFer.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpProdEnTermicaFer"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpAerotermico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpAerotermico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpAerotermico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpGeotermico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpGeotermico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpGeotermico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpIdrotermico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpIdrotermico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpIdrotermico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostImpTermicoSolare");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostImpTermicoSolare"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostImpTermicoSolare"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destEnNonAutocons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destEnNonAutocons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enNonAutoconsumata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enNonAutoconsumata"));
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
        elemField.setFieldName("perAcquaCalda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perAcquaCalda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perRaffrescamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perRaffrescamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("perRiscaldamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "perRiscaldamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzaTermica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzaTermica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("producibilitaTermica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "producibilitaTermica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("teleriscaldamento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "teleriscaldamento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
