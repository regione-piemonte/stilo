/**
 * TipiSchedaMonitoraggio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipiSchedaMonitoraggio  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codiceCsv;

    private java.lang.Integer idTipoScheda;

    private com.hyperborea.sira.ws.RelCampiTipiSchedaMon[] relCampiTipiSchedaMons;

    private com.hyperborea.sira.ws.RelSottoschede[] relSottoschedes;

    private com.hyperborea.sira.ws.SchedaMonitoraggio[] schedaMonitoraggios;

    private com.hyperborea.sira.ws.TemiAmbientali temiAmbientali;

    private java.lang.String templateTitolo;

    private com.hyperborea.sira.ws.TipiElementoSchedaMonitoraggio tipoElemento;

    private com.hyperborea.sira.ws.TipologieSpecie tipologieSpecie;

    private java.lang.String titolo;

    private java.lang.String url;

    public TipiSchedaMonitoraggio() {
    }

    public TipiSchedaMonitoraggio(
           java.lang.String codiceCsv,
           java.lang.Integer idTipoScheda,
           com.hyperborea.sira.ws.RelCampiTipiSchedaMon[] relCampiTipiSchedaMons,
           com.hyperborea.sira.ws.RelSottoschede[] relSottoschedes,
           com.hyperborea.sira.ws.SchedaMonitoraggio[] schedaMonitoraggios,
           com.hyperborea.sira.ws.TemiAmbientali temiAmbientali,
           java.lang.String templateTitolo,
           com.hyperborea.sira.ws.TipiElementoSchedaMonitoraggio tipoElemento,
           com.hyperborea.sira.ws.TipologieSpecie tipologieSpecie,
           java.lang.String titolo,
           java.lang.String url) {
        this.codiceCsv = codiceCsv;
        this.idTipoScheda = idTipoScheda;
        this.relCampiTipiSchedaMons = relCampiTipiSchedaMons;
        this.relSottoschedes = relSottoschedes;
        this.schedaMonitoraggios = schedaMonitoraggios;
        this.temiAmbientali = temiAmbientali;
        this.templateTitolo = templateTitolo;
        this.tipoElemento = tipoElemento;
        this.tipologieSpecie = tipologieSpecie;
        this.titolo = titolo;
        this.url = url;
    }


    /**
     * Gets the codiceCsv value for this TipiSchedaMonitoraggio.
     * 
     * @return codiceCsv
     */
    public java.lang.String getCodiceCsv() {
        return codiceCsv;
    }


    /**
     * Sets the codiceCsv value for this TipiSchedaMonitoraggio.
     * 
     * @param codiceCsv
     */
    public void setCodiceCsv(java.lang.String codiceCsv) {
        this.codiceCsv = codiceCsv;
    }


    /**
     * Gets the idTipoScheda value for this TipiSchedaMonitoraggio.
     * 
     * @return idTipoScheda
     */
    public java.lang.Integer getIdTipoScheda() {
        return idTipoScheda;
    }


    /**
     * Sets the idTipoScheda value for this TipiSchedaMonitoraggio.
     * 
     * @param idTipoScheda
     */
    public void setIdTipoScheda(java.lang.Integer idTipoScheda) {
        this.idTipoScheda = idTipoScheda;
    }


    /**
     * Gets the relCampiTipiSchedaMons value for this TipiSchedaMonitoraggio.
     * 
     * @return relCampiTipiSchedaMons
     */
    public com.hyperborea.sira.ws.RelCampiTipiSchedaMon[] getRelCampiTipiSchedaMons() {
        return relCampiTipiSchedaMons;
    }


    /**
     * Sets the relCampiTipiSchedaMons value for this TipiSchedaMonitoraggio.
     * 
     * @param relCampiTipiSchedaMons
     */
    public void setRelCampiTipiSchedaMons(com.hyperborea.sira.ws.RelCampiTipiSchedaMon[] relCampiTipiSchedaMons) {
        this.relCampiTipiSchedaMons = relCampiTipiSchedaMons;
    }

    public com.hyperborea.sira.ws.RelCampiTipiSchedaMon getRelCampiTipiSchedaMons(int i) {
        return this.relCampiTipiSchedaMons[i];
    }

    public void setRelCampiTipiSchedaMons(int i, com.hyperborea.sira.ws.RelCampiTipiSchedaMon _value) {
        this.relCampiTipiSchedaMons[i] = _value;
    }


    /**
     * Gets the relSottoschedes value for this TipiSchedaMonitoraggio.
     * 
     * @return relSottoschedes
     */
    public com.hyperborea.sira.ws.RelSottoschede[] getRelSottoschedes() {
        return relSottoschedes;
    }


    /**
     * Sets the relSottoschedes value for this TipiSchedaMonitoraggio.
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
     * Gets the schedaMonitoraggios value for this TipiSchedaMonitoraggio.
     * 
     * @return schedaMonitoraggios
     */
    public com.hyperborea.sira.ws.SchedaMonitoraggio[] getSchedaMonitoraggios() {
        return schedaMonitoraggios;
    }


    /**
     * Sets the schedaMonitoraggios value for this TipiSchedaMonitoraggio.
     * 
     * @param schedaMonitoraggios
     */
    public void setSchedaMonitoraggios(com.hyperborea.sira.ws.SchedaMonitoraggio[] schedaMonitoraggios) {
        this.schedaMonitoraggios = schedaMonitoraggios;
    }

    public com.hyperborea.sira.ws.SchedaMonitoraggio getSchedaMonitoraggios(int i) {
        return this.schedaMonitoraggios[i];
    }

    public void setSchedaMonitoraggios(int i, com.hyperborea.sira.ws.SchedaMonitoraggio _value) {
        this.schedaMonitoraggios[i] = _value;
    }


    /**
     * Gets the temiAmbientali value for this TipiSchedaMonitoraggio.
     * 
     * @return temiAmbientali
     */
    public com.hyperborea.sira.ws.TemiAmbientali getTemiAmbientali() {
        return temiAmbientali;
    }


    /**
     * Sets the temiAmbientali value for this TipiSchedaMonitoraggio.
     * 
     * @param temiAmbientali
     */
    public void setTemiAmbientali(com.hyperborea.sira.ws.TemiAmbientali temiAmbientali) {
        this.temiAmbientali = temiAmbientali;
    }


    /**
     * Gets the templateTitolo value for this TipiSchedaMonitoraggio.
     * 
     * @return templateTitolo
     */
    public java.lang.String getTemplateTitolo() {
        return templateTitolo;
    }


    /**
     * Sets the templateTitolo value for this TipiSchedaMonitoraggio.
     * 
     * @param templateTitolo
     */
    public void setTemplateTitolo(java.lang.String templateTitolo) {
        this.templateTitolo = templateTitolo;
    }


    /**
     * Gets the tipoElemento value for this TipiSchedaMonitoraggio.
     * 
     * @return tipoElemento
     */
    public com.hyperborea.sira.ws.TipiElementoSchedaMonitoraggio getTipoElemento() {
        return tipoElemento;
    }


    /**
     * Sets the tipoElemento value for this TipiSchedaMonitoraggio.
     * 
     * @param tipoElemento
     */
    public void setTipoElemento(com.hyperborea.sira.ws.TipiElementoSchedaMonitoraggio tipoElemento) {
        this.tipoElemento = tipoElemento;
    }


    /**
     * Gets the tipologieSpecie value for this TipiSchedaMonitoraggio.
     * 
     * @return tipologieSpecie
     */
    public com.hyperborea.sira.ws.TipologieSpecie getTipologieSpecie() {
        return tipologieSpecie;
    }


    /**
     * Sets the tipologieSpecie value for this TipiSchedaMonitoraggio.
     * 
     * @param tipologieSpecie
     */
    public void setTipologieSpecie(com.hyperborea.sira.ws.TipologieSpecie tipologieSpecie) {
        this.tipologieSpecie = tipologieSpecie;
    }


    /**
     * Gets the titolo value for this TipiSchedaMonitoraggio.
     * 
     * @return titolo
     */
    public java.lang.String getTitolo() {
        return titolo;
    }


    /**
     * Sets the titolo value for this TipiSchedaMonitoraggio.
     * 
     * @param titolo
     */
    public void setTitolo(java.lang.String titolo) {
        this.titolo = titolo;
    }


    /**
     * Gets the url value for this TipiSchedaMonitoraggio.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this TipiSchedaMonitoraggio.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipiSchedaMonitoraggio)) return false;
        TipiSchedaMonitoraggio other = (TipiSchedaMonitoraggio) obj;
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
            ((this.idTipoScheda==null && other.getIdTipoScheda()==null) || 
             (this.idTipoScheda!=null &&
              this.idTipoScheda.equals(other.getIdTipoScheda()))) &&
            ((this.relCampiTipiSchedaMons==null && other.getRelCampiTipiSchedaMons()==null) || 
             (this.relCampiTipiSchedaMons!=null &&
              java.util.Arrays.equals(this.relCampiTipiSchedaMons, other.getRelCampiTipiSchedaMons()))) &&
            ((this.relSottoschedes==null && other.getRelSottoschedes()==null) || 
             (this.relSottoschedes!=null &&
              java.util.Arrays.equals(this.relSottoschedes, other.getRelSottoschedes()))) &&
            ((this.schedaMonitoraggios==null && other.getSchedaMonitoraggios()==null) || 
             (this.schedaMonitoraggios!=null &&
              java.util.Arrays.equals(this.schedaMonitoraggios, other.getSchedaMonitoraggios()))) &&
            ((this.temiAmbientali==null && other.getTemiAmbientali()==null) || 
             (this.temiAmbientali!=null &&
              this.temiAmbientali.equals(other.getTemiAmbientali()))) &&
            ((this.templateTitolo==null && other.getTemplateTitolo()==null) || 
             (this.templateTitolo!=null &&
              this.templateTitolo.equals(other.getTemplateTitolo()))) &&
            ((this.tipoElemento==null && other.getTipoElemento()==null) || 
             (this.tipoElemento!=null &&
              this.tipoElemento.equals(other.getTipoElemento()))) &&
            ((this.tipologieSpecie==null && other.getTipologieSpecie()==null) || 
             (this.tipologieSpecie!=null &&
              this.tipologieSpecie.equals(other.getTipologieSpecie()))) &&
            ((this.titolo==null && other.getTitolo()==null) || 
             (this.titolo!=null &&
              this.titolo.equals(other.getTitolo()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl())));
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
        if (getIdTipoScheda() != null) {
            _hashCode += getIdTipoScheda().hashCode();
        }
        if (getRelCampiTipiSchedaMons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRelCampiTipiSchedaMons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRelCampiTipiSchedaMons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        if (getSchedaMonitoraggios() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSchedaMonitoraggios());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSchedaMonitoraggios(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTemiAmbientali() != null) {
            _hashCode += getTemiAmbientali().hashCode();
        }
        if (getTemplateTitolo() != null) {
            _hashCode += getTemplateTitolo().hashCode();
        }
        if (getTipoElemento() != null) {
            _hashCode += getTipoElemento().hashCode();
        }
        if (getTipologieSpecie() != null) {
            _hashCode += getTipologieSpecie().hashCode();
        }
        if (getTitolo() != null) {
            _hashCode += getTitolo().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipiSchedaMonitoraggio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiSchedaMonitoraggio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceCsv");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codiceCsv"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoScheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoScheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("relCampiTipiSchedaMons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relCampiTipiSchedaMons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relCampiTipiSchedaMon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
        elemField.setFieldName("schedaMonitoraggios");
        elemField.setXmlName(new javax.xml.namespace.QName("", "schedaMonitoraggios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "schedaMonitoraggio"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temiAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temiAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "temiAmbientali"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("templateTitolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "templateTitolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoElemento");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoElemento"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiElementoSchedaMonitoraggio"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieSpecie");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieSpecie"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieSpecie"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("", "url"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
