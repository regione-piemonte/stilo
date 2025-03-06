/**
 * CcostForestaCertificata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostForestaCertificata  implements java.io.Serializable {
    private java.lang.String certificatore;

    private java.lang.String codice;

    private java.lang.String descFisicoAmbientale;

    private java.util.Calendar emissioneFsc;

    private java.util.Calendar emissionePefc;

    private java.lang.Integer idCcost;

    private java.lang.String licenza;

    private com.hyperborea.sira.ws.ProdottiForestaCertificata[] prodottiForestaCertificatas;

    private java.util.Calendar scadenzaFsc;

    private java.util.Calendar scadenzaPefc;

    private java.util.Calendar sospensioneFsc;

    private java.util.Calendar sospensionePefc;

    private com.hyperborea.sira.ws.VocEfTipoCertificato vocEfTipoCertificato;

    public CcostForestaCertificata() {
    }

    public CcostForestaCertificata(
           java.lang.String certificatore,
           java.lang.String codice,
           java.lang.String descFisicoAmbientale,
           java.util.Calendar emissioneFsc,
           java.util.Calendar emissionePefc,
           java.lang.Integer idCcost,
           java.lang.String licenza,
           com.hyperborea.sira.ws.ProdottiForestaCertificata[] prodottiForestaCertificatas,
           java.util.Calendar scadenzaFsc,
           java.util.Calendar scadenzaPefc,
           java.util.Calendar sospensioneFsc,
           java.util.Calendar sospensionePefc,
           com.hyperborea.sira.ws.VocEfTipoCertificato vocEfTipoCertificato) {
           this.certificatore = certificatore;
           this.codice = codice;
           this.descFisicoAmbientale = descFisicoAmbientale;
           this.emissioneFsc = emissioneFsc;
           this.emissionePefc = emissionePefc;
           this.idCcost = idCcost;
           this.licenza = licenza;
           this.prodottiForestaCertificatas = prodottiForestaCertificatas;
           this.scadenzaFsc = scadenzaFsc;
           this.scadenzaPefc = scadenzaPefc;
           this.sospensioneFsc = sospensioneFsc;
           this.sospensionePefc = sospensionePefc;
           this.vocEfTipoCertificato = vocEfTipoCertificato;
    }


    /**
     * Gets the certificatore value for this CcostForestaCertificata.
     * 
     * @return certificatore
     */
    public java.lang.String getCertificatore() {
        return certificatore;
    }


    /**
     * Sets the certificatore value for this CcostForestaCertificata.
     * 
     * @param certificatore
     */
    public void setCertificatore(java.lang.String certificatore) {
        this.certificatore = certificatore;
    }


    /**
     * Gets the codice value for this CcostForestaCertificata.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostForestaCertificata.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the descFisicoAmbientale value for this CcostForestaCertificata.
     * 
     * @return descFisicoAmbientale
     */
    public java.lang.String getDescFisicoAmbientale() {
        return descFisicoAmbientale;
    }


    /**
     * Sets the descFisicoAmbientale value for this CcostForestaCertificata.
     * 
     * @param descFisicoAmbientale
     */
    public void setDescFisicoAmbientale(java.lang.String descFisicoAmbientale) {
        this.descFisicoAmbientale = descFisicoAmbientale;
    }


    /**
     * Gets the emissioneFsc value for this CcostForestaCertificata.
     * 
     * @return emissioneFsc
     */
    public java.util.Calendar getEmissioneFsc() {
        return emissioneFsc;
    }


    /**
     * Sets the emissioneFsc value for this CcostForestaCertificata.
     * 
     * @param emissioneFsc
     */
    public void setEmissioneFsc(java.util.Calendar emissioneFsc) {
        this.emissioneFsc = emissioneFsc;
    }


    /**
     * Gets the emissionePefc value for this CcostForestaCertificata.
     * 
     * @return emissionePefc
     */
    public java.util.Calendar getEmissionePefc() {
        return emissionePefc;
    }


    /**
     * Sets the emissionePefc value for this CcostForestaCertificata.
     * 
     * @param emissionePefc
     */
    public void setEmissionePefc(java.util.Calendar emissionePefc) {
        this.emissionePefc = emissionePefc;
    }


    /**
     * Gets the idCcost value for this CcostForestaCertificata.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostForestaCertificata.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the licenza value for this CcostForestaCertificata.
     * 
     * @return licenza
     */
    public java.lang.String getLicenza() {
        return licenza;
    }


    /**
     * Sets the licenza value for this CcostForestaCertificata.
     * 
     * @param licenza
     */
    public void setLicenza(java.lang.String licenza) {
        this.licenza = licenza;
    }


    /**
     * Gets the prodottiForestaCertificatas value for this CcostForestaCertificata.
     * 
     * @return prodottiForestaCertificatas
     */
    public com.hyperborea.sira.ws.ProdottiForestaCertificata[] getProdottiForestaCertificatas() {
        return prodottiForestaCertificatas;
    }


    /**
     * Sets the prodottiForestaCertificatas value for this CcostForestaCertificata.
     * 
     * @param prodottiForestaCertificatas
     */
    public void setProdottiForestaCertificatas(com.hyperborea.sira.ws.ProdottiForestaCertificata[] prodottiForestaCertificatas) {
        this.prodottiForestaCertificatas = prodottiForestaCertificatas;
    }

    public com.hyperborea.sira.ws.ProdottiForestaCertificata getProdottiForestaCertificatas(int i) {
        return this.prodottiForestaCertificatas[i];
    }

    public void setProdottiForestaCertificatas(int i, com.hyperborea.sira.ws.ProdottiForestaCertificata _value) {
        this.prodottiForestaCertificatas[i] = _value;
    }


    /**
     * Gets the scadenzaFsc value for this CcostForestaCertificata.
     * 
     * @return scadenzaFsc
     */
    public java.util.Calendar getScadenzaFsc() {
        return scadenzaFsc;
    }


    /**
     * Sets the scadenzaFsc value for this CcostForestaCertificata.
     * 
     * @param scadenzaFsc
     */
    public void setScadenzaFsc(java.util.Calendar scadenzaFsc) {
        this.scadenzaFsc = scadenzaFsc;
    }


    /**
     * Gets the scadenzaPefc value for this CcostForestaCertificata.
     * 
     * @return scadenzaPefc
     */
    public java.util.Calendar getScadenzaPefc() {
        return scadenzaPefc;
    }


    /**
     * Sets the scadenzaPefc value for this CcostForestaCertificata.
     * 
     * @param scadenzaPefc
     */
    public void setScadenzaPefc(java.util.Calendar scadenzaPefc) {
        this.scadenzaPefc = scadenzaPefc;
    }


    /**
     * Gets the sospensioneFsc value for this CcostForestaCertificata.
     * 
     * @return sospensioneFsc
     */
    public java.util.Calendar getSospensioneFsc() {
        return sospensioneFsc;
    }


    /**
     * Sets the sospensioneFsc value for this CcostForestaCertificata.
     * 
     * @param sospensioneFsc
     */
    public void setSospensioneFsc(java.util.Calendar sospensioneFsc) {
        this.sospensioneFsc = sospensioneFsc;
    }


    /**
     * Gets the sospensionePefc value for this CcostForestaCertificata.
     * 
     * @return sospensionePefc
     */
    public java.util.Calendar getSospensionePefc() {
        return sospensionePefc;
    }


    /**
     * Sets the sospensionePefc value for this CcostForestaCertificata.
     * 
     * @param sospensionePefc
     */
    public void setSospensionePefc(java.util.Calendar sospensionePefc) {
        this.sospensionePefc = sospensionePefc;
    }


    /**
     * Gets the vocEfTipoCertificato value for this CcostForestaCertificata.
     * 
     * @return vocEfTipoCertificato
     */
    public com.hyperborea.sira.ws.VocEfTipoCertificato getVocEfTipoCertificato() {
        return vocEfTipoCertificato;
    }


    /**
     * Sets the vocEfTipoCertificato value for this CcostForestaCertificata.
     * 
     * @param vocEfTipoCertificato
     */
    public void setVocEfTipoCertificato(com.hyperborea.sira.ws.VocEfTipoCertificato vocEfTipoCertificato) {
        this.vocEfTipoCertificato = vocEfTipoCertificato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostForestaCertificata)) return false;
        CcostForestaCertificata other = (CcostForestaCertificata) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.certificatore==null && other.getCertificatore()==null) || 
             (this.certificatore!=null &&
              this.certificatore.equals(other.getCertificatore()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descFisicoAmbientale==null && other.getDescFisicoAmbientale()==null) || 
             (this.descFisicoAmbientale!=null &&
              this.descFisicoAmbientale.equals(other.getDescFisicoAmbientale()))) &&
            ((this.emissioneFsc==null && other.getEmissioneFsc()==null) || 
             (this.emissioneFsc!=null &&
              this.emissioneFsc.equals(other.getEmissioneFsc()))) &&
            ((this.emissionePefc==null && other.getEmissionePefc()==null) || 
             (this.emissionePefc!=null &&
              this.emissionePefc.equals(other.getEmissionePefc()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.licenza==null && other.getLicenza()==null) || 
             (this.licenza!=null &&
              this.licenza.equals(other.getLicenza()))) &&
            ((this.prodottiForestaCertificatas==null && other.getProdottiForestaCertificatas()==null) || 
             (this.prodottiForestaCertificatas!=null &&
              java.util.Arrays.equals(this.prodottiForestaCertificatas, other.getProdottiForestaCertificatas()))) &&
            ((this.scadenzaFsc==null && other.getScadenzaFsc()==null) || 
             (this.scadenzaFsc!=null &&
              this.scadenzaFsc.equals(other.getScadenzaFsc()))) &&
            ((this.scadenzaPefc==null && other.getScadenzaPefc()==null) || 
             (this.scadenzaPefc!=null &&
              this.scadenzaPefc.equals(other.getScadenzaPefc()))) &&
            ((this.sospensioneFsc==null && other.getSospensioneFsc()==null) || 
             (this.sospensioneFsc!=null &&
              this.sospensioneFsc.equals(other.getSospensioneFsc()))) &&
            ((this.sospensionePefc==null && other.getSospensionePefc()==null) || 
             (this.sospensionePefc!=null &&
              this.sospensionePefc.equals(other.getSospensionePefc()))) &&
            ((this.vocEfTipoCertificato==null && other.getVocEfTipoCertificato()==null) || 
             (this.vocEfTipoCertificato!=null &&
              this.vocEfTipoCertificato.equals(other.getVocEfTipoCertificato())));
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
        if (getCertificatore() != null) {
            _hashCode += getCertificatore().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescFisicoAmbientale() != null) {
            _hashCode += getDescFisicoAmbientale().hashCode();
        }
        if (getEmissioneFsc() != null) {
            _hashCode += getEmissioneFsc().hashCode();
        }
        if (getEmissionePefc() != null) {
            _hashCode += getEmissionePefc().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getLicenza() != null) {
            _hashCode += getLicenza().hashCode();
        }
        if (getProdottiForestaCertificatas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProdottiForestaCertificatas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProdottiForestaCertificatas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getScadenzaFsc() != null) {
            _hashCode += getScadenzaFsc().hashCode();
        }
        if (getScadenzaPefc() != null) {
            _hashCode += getScadenzaPefc().hashCode();
        }
        if (getSospensioneFsc() != null) {
            _hashCode += getSospensioneFsc().hashCode();
        }
        if (getSospensionePefc() != null) {
            _hashCode += getSospensionePefc().hashCode();
        }
        if (getVocEfTipoCertificato() != null) {
            _hashCode += getVocEfTipoCertificato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostForestaCertificata.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostForestaCertificata"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificatore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificatore"));
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
        elemField.setFieldName("descFisicoAmbientale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descFisicoAmbientale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emissioneFsc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emissioneFsc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emissionePefc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emissionePefc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField.setFieldName("licenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "licenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodottiForestaCertificatas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodottiForestaCertificatas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiForestaCertificata"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scadenzaFsc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scadenzaFsc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scadenzaPefc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scadenzaPefc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sospensioneFsc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sospensioneFsc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sospensionePefc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sospensionePefc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipoCertificato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipoCertificato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipoCertificato"));
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
