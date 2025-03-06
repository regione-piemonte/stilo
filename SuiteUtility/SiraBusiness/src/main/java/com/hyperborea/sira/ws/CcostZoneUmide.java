/**
 * CcostZoneUmide.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostZoneUmide  implements java.io.Serializable {
    private com.hyperborea.sira.ws.AreaRamsar areaRamsar;

    private java.lang.String codice;

    private java.lang.Float estMax;

    private java.lang.Float estMin;

    private java.lang.Float estensione;

    private java.lang.Integer idCcost;

    private java.lang.Float nordMax;

    private java.lang.Float nordMin;

    private com.hyperborea.sira.ws.VocCategoriaZu vocCategoriaZu;

    private com.hyperborea.sira.ws.VocTipologiaZu vocTipologiaZu;

    private java.lang.String zonaClimatica;

    private com.hyperborea.sira.ws.ZuAttivitaUmane[] zuAttivitaUmanes;

    private com.hyperborea.sira.ws.ZuImpatti[] zuImpattis;

    private com.hyperborea.sira.ws.ZuServiziEcosistemici[] zuServiziEcosistemicis;

    private com.hyperborea.sira.ws.ZuStatoProtezione[] zuStatoProteziones;

    public CcostZoneUmide() {
    }

    public CcostZoneUmide(
           com.hyperborea.sira.ws.AreaRamsar areaRamsar,
           java.lang.String codice,
           java.lang.Float estMax,
           java.lang.Float estMin,
           java.lang.Float estensione,
           java.lang.Integer idCcost,
           java.lang.Float nordMax,
           java.lang.Float nordMin,
           com.hyperborea.sira.ws.VocCategoriaZu vocCategoriaZu,
           com.hyperborea.sira.ws.VocTipologiaZu vocTipologiaZu,
           java.lang.String zonaClimatica,
           com.hyperborea.sira.ws.ZuAttivitaUmane[] zuAttivitaUmanes,
           com.hyperborea.sira.ws.ZuImpatti[] zuImpattis,
           com.hyperborea.sira.ws.ZuServiziEcosistemici[] zuServiziEcosistemicis,
           com.hyperborea.sira.ws.ZuStatoProtezione[] zuStatoProteziones) {
           this.areaRamsar = areaRamsar;
           this.codice = codice;
           this.estMax = estMax;
           this.estMin = estMin;
           this.estensione = estensione;
           this.idCcost = idCcost;
           this.nordMax = nordMax;
           this.nordMin = nordMin;
           this.vocCategoriaZu = vocCategoriaZu;
           this.vocTipologiaZu = vocTipologiaZu;
           this.zonaClimatica = zonaClimatica;
           this.zuAttivitaUmanes = zuAttivitaUmanes;
           this.zuImpattis = zuImpattis;
           this.zuServiziEcosistemicis = zuServiziEcosistemicis;
           this.zuStatoProteziones = zuStatoProteziones;
    }


    /**
     * Gets the areaRamsar value for this CcostZoneUmide.
     * 
     * @return areaRamsar
     */
    public com.hyperborea.sira.ws.AreaRamsar getAreaRamsar() {
        return areaRamsar;
    }


    /**
     * Sets the areaRamsar value for this CcostZoneUmide.
     * 
     * @param areaRamsar
     */
    public void setAreaRamsar(com.hyperborea.sira.ws.AreaRamsar areaRamsar) {
        this.areaRamsar = areaRamsar;
    }


    /**
     * Gets the codice value for this CcostZoneUmide.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostZoneUmide.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the estMax value for this CcostZoneUmide.
     * 
     * @return estMax
     */
    public java.lang.Float getEstMax() {
        return estMax;
    }


    /**
     * Sets the estMax value for this CcostZoneUmide.
     * 
     * @param estMax
     */
    public void setEstMax(java.lang.Float estMax) {
        this.estMax = estMax;
    }


    /**
     * Gets the estMin value for this CcostZoneUmide.
     * 
     * @return estMin
     */
    public java.lang.Float getEstMin() {
        return estMin;
    }


    /**
     * Sets the estMin value for this CcostZoneUmide.
     * 
     * @param estMin
     */
    public void setEstMin(java.lang.Float estMin) {
        this.estMin = estMin;
    }


    /**
     * Gets the estensione value for this CcostZoneUmide.
     * 
     * @return estensione
     */
    public java.lang.Float getEstensione() {
        return estensione;
    }


    /**
     * Sets the estensione value for this CcostZoneUmide.
     * 
     * @param estensione
     */
    public void setEstensione(java.lang.Float estensione) {
        this.estensione = estensione;
    }


    /**
     * Gets the idCcost value for this CcostZoneUmide.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostZoneUmide.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the nordMax value for this CcostZoneUmide.
     * 
     * @return nordMax
     */
    public java.lang.Float getNordMax() {
        return nordMax;
    }


    /**
     * Sets the nordMax value for this CcostZoneUmide.
     * 
     * @param nordMax
     */
    public void setNordMax(java.lang.Float nordMax) {
        this.nordMax = nordMax;
    }


    /**
     * Gets the nordMin value for this CcostZoneUmide.
     * 
     * @return nordMin
     */
    public java.lang.Float getNordMin() {
        return nordMin;
    }


    /**
     * Sets the nordMin value for this CcostZoneUmide.
     * 
     * @param nordMin
     */
    public void setNordMin(java.lang.Float nordMin) {
        this.nordMin = nordMin;
    }


    /**
     * Gets the vocCategoriaZu value for this CcostZoneUmide.
     * 
     * @return vocCategoriaZu
     */
    public com.hyperborea.sira.ws.VocCategoriaZu getVocCategoriaZu() {
        return vocCategoriaZu;
    }


    /**
     * Sets the vocCategoriaZu value for this CcostZoneUmide.
     * 
     * @param vocCategoriaZu
     */
    public void setVocCategoriaZu(com.hyperborea.sira.ws.VocCategoriaZu vocCategoriaZu) {
        this.vocCategoriaZu = vocCategoriaZu;
    }


    /**
     * Gets the vocTipologiaZu value for this CcostZoneUmide.
     * 
     * @return vocTipologiaZu
     */
    public com.hyperborea.sira.ws.VocTipologiaZu getVocTipologiaZu() {
        return vocTipologiaZu;
    }


    /**
     * Sets the vocTipologiaZu value for this CcostZoneUmide.
     * 
     * @param vocTipologiaZu
     */
    public void setVocTipologiaZu(com.hyperborea.sira.ws.VocTipologiaZu vocTipologiaZu) {
        this.vocTipologiaZu = vocTipologiaZu;
    }


    /**
     * Gets the zonaClimatica value for this CcostZoneUmide.
     * 
     * @return zonaClimatica
     */
    public java.lang.String getZonaClimatica() {
        return zonaClimatica;
    }


    /**
     * Sets the zonaClimatica value for this CcostZoneUmide.
     * 
     * @param zonaClimatica
     */
    public void setZonaClimatica(java.lang.String zonaClimatica) {
        this.zonaClimatica = zonaClimatica;
    }


    /**
     * Gets the zuAttivitaUmanes value for this CcostZoneUmide.
     * 
     * @return zuAttivitaUmanes
     */
    public com.hyperborea.sira.ws.ZuAttivitaUmane[] getZuAttivitaUmanes() {
        return zuAttivitaUmanes;
    }


    /**
     * Sets the zuAttivitaUmanes value for this CcostZoneUmide.
     * 
     * @param zuAttivitaUmanes
     */
    public void setZuAttivitaUmanes(com.hyperborea.sira.ws.ZuAttivitaUmane[] zuAttivitaUmanes) {
        this.zuAttivitaUmanes = zuAttivitaUmanes;
    }

    public com.hyperborea.sira.ws.ZuAttivitaUmane getZuAttivitaUmanes(int i) {
        return this.zuAttivitaUmanes[i];
    }

    public void setZuAttivitaUmanes(int i, com.hyperborea.sira.ws.ZuAttivitaUmane _value) {
        this.zuAttivitaUmanes[i] = _value;
    }


    /**
     * Gets the zuImpattis value for this CcostZoneUmide.
     * 
     * @return zuImpattis
     */
    public com.hyperborea.sira.ws.ZuImpatti[] getZuImpattis() {
        return zuImpattis;
    }


    /**
     * Sets the zuImpattis value for this CcostZoneUmide.
     * 
     * @param zuImpattis
     */
    public void setZuImpattis(com.hyperborea.sira.ws.ZuImpatti[] zuImpattis) {
        this.zuImpattis = zuImpattis;
    }

    public com.hyperborea.sira.ws.ZuImpatti getZuImpattis(int i) {
        return this.zuImpattis[i];
    }

    public void setZuImpattis(int i, com.hyperborea.sira.ws.ZuImpatti _value) {
        this.zuImpattis[i] = _value;
    }


    /**
     * Gets the zuServiziEcosistemicis value for this CcostZoneUmide.
     * 
     * @return zuServiziEcosistemicis
     */
    public com.hyperborea.sira.ws.ZuServiziEcosistemici[] getZuServiziEcosistemicis() {
        return zuServiziEcosistemicis;
    }


    /**
     * Sets the zuServiziEcosistemicis value for this CcostZoneUmide.
     * 
     * @param zuServiziEcosistemicis
     */
    public void setZuServiziEcosistemicis(com.hyperborea.sira.ws.ZuServiziEcosistemici[] zuServiziEcosistemicis) {
        this.zuServiziEcosistemicis = zuServiziEcosistemicis;
    }

    public com.hyperborea.sira.ws.ZuServiziEcosistemici getZuServiziEcosistemicis(int i) {
        return this.zuServiziEcosistemicis[i];
    }

    public void setZuServiziEcosistemicis(int i, com.hyperborea.sira.ws.ZuServiziEcosistemici _value) {
        this.zuServiziEcosistemicis[i] = _value;
    }


    /**
     * Gets the zuStatoProteziones value for this CcostZoneUmide.
     * 
     * @return zuStatoProteziones
     */
    public com.hyperborea.sira.ws.ZuStatoProtezione[] getZuStatoProteziones() {
        return zuStatoProteziones;
    }


    /**
     * Sets the zuStatoProteziones value for this CcostZoneUmide.
     * 
     * @param zuStatoProteziones
     */
    public void setZuStatoProteziones(com.hyperborea.sira.ws.ZuStatoProtezione[] zuStatoProteziones) {
        this.zuStatoProteziones = zuStatoProteziones;
    }

    public com.hyperborea.sira.ws.ZuStatoProtezione getZuStatoProteziones(int i) {
        return this.zuStatoProteziones[i];
    }

    public void setZuStatoProteziones(int i, com.hyperborea.sira.ws.ZuStatoProtezione _value) {
        this.zuStatoProteziones[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostZoneUmide)) return false;
        CcostZoneUmide other = (CcostZoneUmide) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.areaRamsar==null && other.getAreaRamsar()==null) || 
             (this.areaRamsar!=null &&
              this.areaRamsar.equals(other.getAreaRamsar()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.estMax==null && other.getEstMax()==null) || 
             (this.estMax!=null &&
              this.estMax.equals(other.getEstMax()))) &&
            ((this.estMin==null && other.getEstMin()==null) || 
             (this.estMin!=null &&
              this.estMin.equals(other.getEstMin()))) &&
            ((this.estensione==null && other.getEstensione()==null) || 
             (this.estensione!=null &&
              this.estensione.equals(other.getEstensione()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.nordMax==null && other.getNordMax()==null) || 
             (this.nordMax!=null &&
              this.nordMax.equals(other.getNordMax()))) &&
            ((this.nordMin==null && other.getNordMin()==null) || 
             (this.nordMin!=null &&
              this.nordMin.equals(other.getNordMin()))) &&
            ((this.vocCategoriaZu==null && other.getVocCategoriaZu()==null) || 
             (this.vocCategoriaZu!=null &&
              this.vocCategoriaZu.equals(other.getVocCategoriaZu()))) &&
            ((this.vocTipologiaZu==null && other.getVocTipologiaZu()==null) || 
             (this.vocTipologiaZu!=null &&
              this.vocTipologiaZu.equals(other.getVocTipologiaZu()))) &&
            ((this.zonaClimatica==null && other.getZonaClimatica()==null) || 
             (this.zonaClimatica!=null &&
              this.zonaClimatica.equals(other.getZonaClimatica()))) &&
            ((this.zuAttivitaUmanes==null && other.getZuAttivitaUmanes()==null) || 
             (this.zuAttivitaUmanes!=null &&
              java.util.Arrays.equals(this.zuAttivitaUmanes, other.getZuAttivitaUmanes()))) &&
            ((this.zuImpattis==null && other.getZuImpattis()==null) || 
             (this.zuImpattis!=null &&
              java.util.Arrays.equals(this.zuImpattis, other.getZuImpattis()))) &&
            ((this.zuServiziEcosistemicis==null && other.getZuServiziEcosistemicis()==null) || 
             (this.zuServiziEcosistemicis!=null &&
              java.util.Arrays.equals(this.zuServiziEcosistemicis, other.getZuServiziEcosistemicis()))) &&
            ((this.zuStatoProteziones==null && other.getZuStatoProteziones()==null) || 
             (this.zuStatoProteziones!=null &&
              java.util.Arrays.equals(this.zuStatoProteziones, other.getZuStatoProteziones())));
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
        if (getAreaRamsar() != null) {
            _hashCode += getAreaRamsar().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getEstMax() != null) {
            _hashCode += getEstMax().hashCode();
        }
        if (getEstMin() != null) {
            _hashCode += getEstMin().hashCode();
        }
        if (getEstensione() != null) {
            _hashCode += getEstensione().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNordMax() != null) {
            _hashCode += getNordMax().hashCode();
        }
        if (getNordMin() != null) {
            _hashCode += getNordMin().hashCode();
        }
        if (getVocCategoriaZu() != null) {
            _hashCode += getVocCategoriaZu().hashCode();
        }
        if (getVocTipologiaZu() != null) {
            _hashCode += getVocTipologiaZu().hashCode();
        }
        if (getZonaClimatica() != null) {
            _hashCode += getZonaClimatica().hashCode();
        }
        if (getZuAttivitaUmanes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getZuAttivitaUmanes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getZuAttivitaUmanes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getZuImpattis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getZuImpattis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getZuImpattis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getZuServiziEcosistemicis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getZuServiziEcosistemicis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getZuServiziEcosistemicis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getZuStatoProteziones() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getZuStatoProteziones());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getZuStatoProteziones(), i);
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
        new org.apache.axis.description.TypeDesc(CcostZoneUmide.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostZoneUmide"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaRamsar");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaRamsar"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areaRamsar"));
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
        elemField.setFieldName("estMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("estensione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "estensione"));
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
        elemField.setFieldName("nordMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nordMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nordMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nordMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocCategoriaZu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocCategoriaZu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocCategoriaZu"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaZu");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaZu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaZu"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zonaClimatica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zonaClimatica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zuAttivitaUmanes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zuAttivitaUmanes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuAttivitaUmane"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zuImpattis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zuImpattis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuImpatti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zuServiziEcosistemicis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zuServiziEcosistemicis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuServiziEcosistemici"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zuStatoProteziones");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zuStatoProteziones"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "zuStatoProtezione"));
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
