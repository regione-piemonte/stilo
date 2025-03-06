/**
 * CampiSchedaMon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CampiSchedaMon  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codiceCsv;

    private java.lang.String filtroSpecie;

    private java.lang.Integer idCampo;

    private java.lang.Integer idCampoPadre;

    private java.lang.Integer maxlenght;

    private java.lang.String nomeCampo;

    private java.lang.String nomeCampoVoc;

    private java.lang.String nomeVocabolario;

    private int ordineCampo;

    private java.lang.String regExpValidator;

    private com.hyperborea.sira.ws.RelSottoschede[] relSottoschedes;

    private java.lang.String searchType;

    private java.lang.Integer sizeCampo;

    private com.hyperborea.sira.ws.TipiDatoSchedaMon tipiDatoSchedaMon;

    private java.lang.Integer wsIdSezioneRef;

    private com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor wsSottoSchedaDescriptor;

    private com.hyperborea.sira.ws.VocabularyDescription wsVocabularyDescription;

    public CampiSchedaMon() {
    }

    public CampiSchedaMon(
           java.lang.String codiceCsv,
           java.lang.String filtroSpecie,
           java.lang.Integer idCampo,
           java.lang.Integer idCampoPadre,
           java.lang.Integer maxlenght,
           java.lang.String nomeCampo,
           java.lang.String nomeCampoVoc,
           java.lang.String nomeVocabolario,
           int ordineCampo,
           java.lang.String regExpValidator,
           com.hyperborea.sira.ws.RelSottoschede[] relSottoschedes,
           java.lang.String searchType,
           java.lang.Integer sizeCampo,
           com.hyperborea.sira.ws.TipiDatoSchedaMon tipiDatoSchedaMon,
           java.lang.Integer wsIdSezioneRef,
           com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor wsSottoSchedaDescriptor,
           com.hyperborea.sira.ws.VocabularyDescription wsVocabularyDescription) {
        this.codiceCsv = codiceCsv;
        this.filtroSpecie = filtroSpecie;
        this.idCampo = idCampo;
        this.idCampoPadre = idCampoPadre;
        this.maxlenght = maxlenght;
        this.nomeCampo = nomeCampo;
        this.nomeCampoVoc = nomeCampoVoc;
        this.nomeVocabolario = nomeVocabolario;
        this.ordineCampo = ordineCampo;
        this.regExpValidator = regExpValidator;
        this.relSottoschedes = relSottoschedes;
        this.searchType = searchType;
        this.sizeCampo = sizeCampo;
        this.tipiDatoSchedaMon = tipiDatoSchedaMon;
        this.wsIdSezioneRef = wsIdSezioneRef;
        this.wsSottoSchedaDescriptor = wsSottoSchedaDescriptor;
        this.wsVocabularyDescription = wsVocabularyDescription;
    }


    /**
     * Gets the codiceCsv value for this CampiSchedaMon.
     * 
     * @return codiceCsv
     */
    public java.lang.String getCodiceCsv() {
        return codiceCsv;
    }


    /**
     * Sets the codiceCsv value for this CampiSchedaMon.
     * 
     * @param codiceCsv
     */
    public void setCodiceCsv(java.lang.String codiceCsv) {
        this.codiceCsv = codiceCsv;
    }


    /**
     * Gets the filtroSpecie value for this CampiSchedaMon.
     * 
     * @return filtroSpecie
     */
    public java.lang.String getFiltroSpecie() {
        return filtroSpecie;
    }


    /**
     * Sets the filtroSpecie value for this CampiSchedaMon.
     * 
     * @param filtroSpecie
     */
    public void setFiltroSpecie(java.lang.String filtroSpecie) {
        this.filtroSpecie = filtroSpecie;
    }


    /**
     * Gets the idCampo value for this CampiSchedaMon.
     * 
     * @return idCampo
     */
    public java.lang.Integer getIdCampo() {
        return idCampo;
    }


    /**
     * Sets the idCampo value for this CampiSchedaMon.
     * 
     * @param idCampo
     */
    public void setIdCampo(java.lang.Integer idCampo) {
        this.idCampo = idCampo;
    }


    /**
     * Gets the idCampoPadre value for this CampiSchedaMon.
     * 
     * @return idCampoPadre
     */
    public java.lang.Integer getIdCampoPadre() {
        return idCampoPadre;
    }


    /**
     * Sets the idCampoPadre value for this CampiSchedaMon.
     * 
     * @param idCampoPadre
     */
    public void setIdCampoPadre(java.lang.Integer idCampoPadre) {
        this.idCampoPadre = idCampoPadre;
    }


    /**
     * Gets the maxlenght value for this CampiSchedaMon.
     * 
     * @return maxlenght
     */
    public java.lang.Integer getMaxlenght() {
        return maxlenght;
    }


    /**
     * Sets the maxlenght value for this CampiSchedaMon.
     * 
     * @param maxlenght
     */
    public void setMaxlenght(java.lang.Integer maxlenght) {
        this.maxlenght = maxlenght;
    }


    /**
     * Gets the nomeCampo value for this CampiSchedaMon.
     * 
     * @return nomeCampo
     */
    public java.lang.String getNomeCampo() {
        return nomeCampo;
    }


    /**
     * Sets the nomeCampo value for this CampiSchedaMon.
     * 
     * @param nomeCampo
     */
    public void setNomeCampo(java.lang.String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }


    /**
     * Gets the nomeCampoVoc value for this CampiSchedaMon.
     * 
     * @return nomeCampoVoc
     */
    public java.lang.String getNomeCampoVoc() {
        return nomeCampoVoc;
    }


    /**
     * Sets the nomeCampoVoc value for this CampiSchedaMon.
     * 
     * @param nomeCampoVoc
     */
    public void setNomeCampoVoc(java.lang.String nomeCampoVoc) {
        this.nomeCampoVoc = nomeCampoVoc;
    }


    /**
     * Gets the nomeVocabolario value for this CampiSchedaMon.
     * 
     * @return nomeVocabolario
     */
    public java.lang.String getNomeVocabolario() {
        return nomeVocabolario;
    }


    /**
     * Sets the nomeVocabolario value for this CampiSchedaMon.
     * 
     * @param nomeVocabolario
     */
    public void setNomeVocabolario(java.lang.String nomeVocabolario) {
        this.nomeVocabolario = nomeVocabolario;
    }


    /**
     * Gets the ordineCampo value for this CampiSchedaMon.
     * 
     * @return ordineCampo
     */
    public int getOrdineCampo() {
        return ordineCampo;
    }


    /**
     * Sets the ordineCampo value for this CampiSchedaMon.
     * 
     * @param ordineCampo
     */
    public void setOrdineCampo(int ordineCampo) {
        this.ordineCampo = ordineCampo;
    }


    /**
     * Gets the regExpValidator value for this CampiSchedaMon.
     * 
     * @return regExpValidator
     */
    public java.lang.String getRegExpValidator() {
        return regExpValidator;
    }


    /**
     * Sets the regExpValidator value for this CampiSchedaMon.
     * 
     * @param regExpValidator
     */
    public void setRegExpValidator(java.lang.String regExpValidator) {
        this.regExpValidator = regExpValidator;
    }


    /**
     * Gets the relSottoschedes value for this CampiSchedaMon.
     * 
     * @return relSottoschedes
     */
    public com.hyperborea.sira.ws.RelSottoschede[] getRelSottoschedes() {
        return relSottoschedes;
    }


    /**
     * Sets the relSottoschedes value for this CampiSchedaMon.
     * 
     * @param relSottoschedes
     */
    public void setRelSottoschedes(com.hyperborea.sira.ws.RelSottoschede[] relSottoschedes) {
        this.relSottoschedes = relSottoschedes;
    }

    public com.hyperborea.sira.ws.RelSottoschede getRelSottoschedes(int i) {
        return this.relSottoschedes[i];
    }

    public void setRelSottoschedes(int i, com.hyperborea.sira.ws.RelSottoschede _value) {
        this.relSottoschedes[i] = _value;
    }


    /**
     * Gets the searchType value for this CampiSchedaMon.
     * 
     * @return searchType
     */
    public java.lang.String getSearchType() {
        return searchType;
    }


    /**
     * Sets the searchType value for this CampiSchedaMon.
     * 
     * @param searchType
     */
    public void setSearchType(java.lang.String searchType) {
        this.searchType = searchType;
    }


    /**
     * Gets the sizeCampo value for this CampiSchedaMon.
     * 
     * @return sizeCampo
     */
    public java.lang.Integer getSizeCampo() {
        return sizeCampo;
    }


    /**
     * Sets the sizeCampo value for this CampiSchedaMon.
     * 
     * @param sizeCampo
     */
    public void setSizeCampo(java.lang.Integer sizeCampo) {
        this.sizeCampo = sizeCampo;
    }


    /**
     * Gets the tipiDatoSchedaMon value for this CampiSchedaMon.
     * 
     * @return tipiDatoSchedaMon
     */
    public com.hyperborea.sira.ws.TipiDatoSchedaMon getTipiDatoSchedaMon() {
        return tipiDatoSchedaMon;
    }


    /**
     * Sets the tipiDatoSchedaMon value for this CampiSchedaMon.
     * 
     * @param tipiDatoSchedaMon
     */
    public void setTipiDatoSchedaMon(com.hyperborea.sira.ws.TipiDatoSchedaMon tipiDatoSchedaMon) {
        this.tipiDatoSchedaMon = tipiDatoSchedaMon;
    }


    /**
     * Gets the wsIdSezioneRef value for this CampiSchedaMon.
     * 
     * @return wsIdSezioneRef
     */
    public java.lang.Integer getWsIdSezioneRef() {
        return wsIdSezioneRef;
    }


    /**
     * Sets the wsIdSezioneRef value for this CampiSchedaMon.
     * 
     * @param wsIdSezioneRef
     */
    public void setWsIdSezioneRef(java.lang.Integer wsIdSezioneRef) {
        this.wsIdSezioneRef = wsIdSezioneRef;
    }


    /**
     * Gets the wsSottoSchedaDescriptor value for this CampiSchedaMon.
     * 
     * @return wsSottoSchedaDescriptor
     */
    public com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor getWsSottoSchedaDescriptor() {
        return wsSottoSchedaDescriptor;
    }


    /**
     * Sets the wsSottoSchedaDescriptor value for this CampiSchedaMon.
     * 
     * @param wsSottoSchedaDescriptor
     */
    public void setWsSottoSchedaDescriptor(com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioDescriptor wsSottoSchedaDescriptor) {
        this.wsSottoSchedaDescriptor = wsSottoSchedaDescriptor;
    }


    /**
     * Gets the wsVocabularyDescription value for this CampiSchedaMon.
     * 
     * @return wsVocabularyDescription
     */
    public com.hyperborea.sira.ws.VocabularyDescription getWsVocabularyDescription() {
        return wsVocabularyDescription;
    }


    /**
     * Sets the wsVocabularyDescription value for this CampiSchedaMon.
     * 
     * @param wsVocabularyDescription
     */
    public void setWsVocabularyDescription(com.hyperborea.sira.ws.VocabularyDescription wsVocabularyDescription) {
        this.wsVocabularyDescription = wsVocabularyDescription;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CampiSchedaMon)) return false;
        CampiSchedaMon other = (CampiSchedaMon) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codiceCsv==null && other.getCodiceCsv()==null) || 
             (this.codiceCsv!=null &&
              this.codiceCsv.equals(other.getCodiceCsv()))) &&
            ((this.filtroSpecie==null && other.getFiltroSpecie()==null) || 
             (this.filtroSpecie!=null &&
              this.filtroSpecie.equals(other.getFiltroSpecie()))) &&
            ((this.idCampo==null && other.getIdCampo()==null) || 
             (this.idCampo!=null &&
              this.idCampo.equals(other.getIdCampo()))) &&
            ((this.idCampoPadre==null && other.getIdCampoPadre()==null) || 
             (this.idCampoPadre!=null &&
              this.idCampoPadre.equals(other.getIdCampoPadre()))) &&
            ((this.maxlenght==null && other.getMaxlenght()==null) || 
             (this.maxlenght!=null &&
              this.maxlenght.equals(other.getMaxlenght()))) &&
            ((this.nomeCampo==null && other.getNomeCampo()==null) || 
             (this.nomeCampo!=null &&
              this.nomeCampo.equals(other.getNomeCampo()))) &&
            ((this.nomeCampoVoc==null && other.getNomeCampoVoc()==null) || 
             (this.nomeCampoVoc!=null &&
              this.nomeCampoVoc.equals(other.getNomeCampoVoc()))) &&
            ((this.nomeVocabolario==null && other.getNomeVocabolario()==null) || 
             (this.nomeVocabolario!=null &&
              this.nomeVocabolario.equals(other.getNomeVocabolario()))) &&
            this.ordineCampo == other.getOrdineCampo() &&
            ((this.regExpValidator==null && other.getRegExpValidator()==null) || 
             (this.regExpValidator!=null &&
              this.regExpValidator.equals(other.getRegExpValidator()))) &&
            ((this.relSottoschedes==null && other.getRelSottoschedes()==null) || 
             (this.relSottoschedes!=null &&
              java.util.Arrays.equals(this.relSottoschedes, other.getRelSottoschedes()))) &&
            ((this.searchType==null && other.getSearchType()==null) || 
             (this.searchType!=null &&
              this.searchType.equals(other.getSearchType()))) &&
            ((this.sizeCampo==null && other.getSizeCampo()==null) || 
             (this.sizeCampo!=null &&
              this.sizeCampo.equals(other.getSizeCampo()))) &&
            ((this.tipiDatoSchedaMon==null && other.getTipiDatoSchedaMon()==null) || 
             (this.tipiDatoSchedaMon!=null &&
              this.tipiDatoSchedaMon.equals(other.getTipiDatoSchedaMon()))) &&
            ((this.wsIdSezioneRef==null && other.getWsIdSezioneRef()==null) || 
             (this.wsIdSezioneRef!=null &&
              this.wsIdSezioneRef.equals(other.getWsIdSezioneRef()))) &&
            ((this.wsSottoSchedaDescriptor==null && other.getWsSottoSchedaDescriptor()==null) || 
             (this.wsSottoSchedaDescriptor!=null &&
              this.wsSottoSchedaDescriptor.equals(other.getWsSottoSchedaDescriptor()))) &&
            ((this.wsVocabularyDescription==null && other.getWsVocabularyDescription()==null) || 
             (this.wsVocabularyDescription!=null &&
              this.wsVocabularyDescription.equals(other.getWsVocabularyDescription())));
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
        if (getCodiceCsv() != null) {
            _hashCode += getCodiceCsv().hashCode();
        }
        if (getFiltroSpecie() != null) {
            _hashCode += getFiltroSpecie().hashCode();
        }
        if (getIdCampo() != null) {
            _hashCode += getIdCampo().hashCode();
        }
        if (getIdCampoPadre() != null) {
            _hashCode += getIdCampoPadre().hashCode();
        }
        if (getMaxlenght() != null) {
            _hashCode += getMaxlenght().hashCode();
        }
        if (getNomeCampo() != null) {
            _hashCode += getNomeCampo().hashCode();
        }
        if (getNomeCampoVoc() != null) {
            _hashCode += getNomeCampoVoc().hashCode();
        }
        if (getNomeVocabolario() != null) {
            _hashCode += getNomeVocabolario().hashCode();
        }
        _hashCode += getOrdineCampo();
        if (getRegExpValidator() != null) {
            _hashCode += getRegExpValidator().hashCode();
        }
        if (getRelSottoschedes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRelSottoschedes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRelSottoschedes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSearchType() != null) {
            _hashCode += getSearchType().hashCode();
        }
        if (getSizeCampo() != null) {
            _hashCode += getSizeCampo().hashCode();
        }
        if (getTipiDatoSchedaMon() != null) {
            _hashCode += getTipiDatoSchedaMon().hashCode();
        }
        if (getWsIdSezioneRef() != null) {
            _hashCode += getWsIdSezioneRef().hashCode();
        }
        if (getWsSottoSchedaDescriptor() != null) {
            _hashCode += getWsSottoSchedaDescriptor().hashCode();
        }
        if (getWsVocabularyDescription() != null) {
            _hashCode += getWsVocabularyDescription().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CampiSchedaMon.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campiSchedaMon"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCsv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceCsv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filtroSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "filtroSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCampoPadre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCampoPadre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxlenght");
        elemField.setXmlName(new javax.xml.namespace.QName("", "maxlenght"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCampoVoc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeCampoVoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeVocabolario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeVocabolario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordineCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordineCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regExpValidator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "regExpValidator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relSottoschedes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relSottoschedes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relSottoschede"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sizeCampo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sizeCampo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiDatoSchedaMon");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiDatoSchedaMon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiDatoSchedaMon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsIdSezioneRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsIdSezioneRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsSottoSchedaDescriptor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsSottoSchedaDescriptor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioDescriptor"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsVocabularyDescription");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsVocabularyDescription"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyDescription"));
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
