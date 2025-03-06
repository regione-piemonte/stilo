/**
 * UbicazioniOst.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UbicazioniOst  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String cap;

    private java.lang.String comune;

    private com.hyperborea.sira.ws.ComuniItalia comuneItalia;

    private com.hyperborea.sira.ws.DatiCatastali[] datiCatastalis;

    private java.lang.Float gbEst;

    private java.lang.Float gbNord;

    private java.lang.String geocodice;

    private java.lang.Integer idUbicazione;

    private java.lang.String localita;

    private java.lang.String nomeStrada;

    private java.lang.String numeroCivico;

    private java.lang.String provincia;

    private java.lang.Float quotaSlmM;

    private java.lang.String regione;

    private java.lang.String tipoStrada;

    private java.lang.Float wgs84X;

    private java.lang.Float wgs84Y;

    public UbicazioniOst() {
    }

    public UbicazioniOst(
           java.lang.String cap,
           java.lang.String comune,
           com.hyperborea.sira.ws.ComuniItalia comuneItalia,
           com.hyperborea.sira.ws.DatiCatastali[] datiCatastalis,
           java.lang.Float gbEst,
           java.lang.Float gbNord,
           java.lang.String geocodice,
           java.lang.Integer idUbicazione,
           java.lang.String localita,
           java.lang.String nomeStrada,
           java.lang.String numeroCivico,
           java.lang.String provincia,
           java.lang.Float quotaSlmM,
           java.lang.String regione,
           java.lang.String tipoStrada,
           java.lang.Float wgs84X,
           java.lang.Float wgs84Y) {
        this.cap = cap;
        this.comune = comune;
        this.comuneItalia = comuneItalia;
        this.datiCatastalis = datiCatastalis;
        this.gbEst = gbEst;
        this.gbNord = gbNord;
        this.geocodice = geocodice;
        this.idUbicazione = idUbicazione;
        this.localita = localita;
        this.nomeStrada = nomeStrada;
        this.numeroCivico = numeroCivico;
        this.provincia = provincia;
        this.quotaSlmM = quotaSlmM;
        this.regione = regione;
        this.tipoStrada = tipoStrada;
        this.wgs84X = wgs84X;
        this.wgs84Y = wgs84Y;
    }


    /**
     * Gets the cap value for this UbicazioniOst.
     * 
     * @return cap
     */
    public java.lang.String getCap() {
        return cap;
    }


    /**
     * Sets the cap value for this UbicazioniOst.
     * 
     * @param cap
     */
    public void setCap(java.lang.String cap) {
        this.cap = cap;
    }


    /**
     * Gets the comune value for this UbicazioniOst.
     * 
     * @return comune
     */
    public java.lang.String getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this UbicazioniOst.
     * 
     * @param comune
     */
    public void setComune(java.lang.String comune) {
        this.comune = comune;
    }


    /**
     * Gets the comuneItalia value for this UbicazioniOst.
     * 
     * @return comuneItalia
     */
    public com.hyperborea.sira.ws.ComuniItalia getComuneItalia() {
        return comuneItalia;
    }


    /**
     * Sets the comuneItalia value for this UbicazioniOst.
     * 
     * @param comuneItalia
     */
    public void setComuneItalia(com.hyperborea.sira.ws.ComuniItalia comuneItalia) {
        this.comuneItalia = comuneItalia;
    }


    /**
     * Gets the datiCatastalis value for this UbicazioniOst.
     * 
     * @return datiCatastalis
     */
    public com.hyperborea.sira.ws.DatiCatastali[] getDatiCatastalis() {
        return datiCatastalis;
    }


    /**
     * Sets the datiCatastalis value for this UbicazioniOst.
     * 
     * @param datiCatastalis
     */
    public void setDatiCatastalis(com.hyperborea.sira.ws.DatiCatastali[] datiCatastalis) {
        this.datiCatastalis = datiCatastalis;
    }

    public com.hyperborea.sira.ws.DatiCatastali getDatiCatastalis(int i) {
        return this.datiCatastalis[i];
    }

    public void setDatiCatastalis(int i, com.hyperborea.sira.ws.DatiCatastali _value) {
        this.datiCatastalis[i] = _value;
    }


    /**
     * Gets the gbEst value for this UbicazioniOst.
     * 
     * @return gbEst
     */
    public java.lang.Float getGbEst() {
        return gbEst;
    }


    /**
     * Sets the gbEst value for this UbicazioniOst.
     * 
     * @param gbEst
     */
    public void setGbEst(java.lang.Float gbEst) {
        this.gbEst = gbEst;
    }


    /**
     * Gets the gbNord value for this UbicazioniOst.
     * 
     * @return gbNord
     */
    public java.lang.Float getGbNord() {
        return gbNord;
    }


    /**
     * Sets the gbNord value for this UbicazioniOst.
     * 
     * @param gbNord
     */
    public void setGbNord(java.lang.Float gbNord) {
        this.gbNord = gbNord;
    }


    /**
     * Gets the geocodice value for this UbicazioniOst.
     * 
     * @return geocodice
     */
    public java.lang.String getGeocodice() {
        return geocodice;
    }


    /**
     * Sets the geocodice value for this UbicazioniOst.
     * 
     * @param geocodice
     */
    public void setGeocodice(java.lang.String geocodice) {
        this.geocodice = geocodice;
    }


    /**
     * Gets the idUbicazione value for this UbicazioniOst.
     * 
     * @return idUbicazione
     */
    public java.lang.Integer getIdUbicazione() {
        return idUbicazione;
    }


    /**
     * Sets the idUbicazione value for this UbicazioniOst.
     * 
     * @param idUbicazione
     */
    public void setIdUbicazione(java.lang.Integer idUbicazione) {
        this.idUbicazione = idUbicazione;
    }


    /**
     * Gets the localita value for this UbicazioniOst.
     * 
     * @return localita
     */
    public java.lang.String getLocalita() {
        return localita;
    }


    /**
     * Sets the localita value for this UbicazioniOst.
     * 
     * @param localita
     */
    public void setLocalita(java.lang.String localita) {
        this.localita = localita;
    }


    /**
     * Gets the nomeStrada value for this UbicazioniOst.
     * 
     * @return nomeStrada
     */
    public java.lang.String getNomeStrada() {
        return nomeStrada;
    }


    /**
     * Sets the nomeStrada value for this UbicazioniOst.
     * 
     * @param nomeStrada
     */
    public void setNomeStrada(java.lang.String nomeStrada) {
        this.nomeStrada = nomeStrada;
    }


    /**
     * Gets the numeroCivico value for this UbicazioniOst.
     * 
     * @return numeroCivico
     */
    public java.lang.String getNumeroCivico() {
        return numeroCivico;
    }


    /**
     * Sets the numeroCivico value for this UbicazioniOst.
     * 
     * @param numeroCivico
     */
    public void setNumeroCivico(java.lang.String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }


    /**
     * Gets the provincia value for this UbicazioniOst.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this UbicazioniOst.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the quotaSlmM value for this UbicazioniOst.
     * 
     * @return quotaSlmM
     */
    public java.lang.Float getQuotaSlmM() {
        return quotaSlmM;
    }


    /**
     * Sets the quotaSlmM value for this UbicazioniOst.
     * 
     * @param quotaSlmM
     */
    public void setQuotaSlmM(java.lang.Float quotaSlmM) {
        this.quotaSlmM = quotaSlmM;
    }


    /**
     * Gets the regione value for this UbicazioniOst.
     * 
     * @return regione
     */
    public java.lang.String getRegione() {
        return regione;
    }


    /**
     * Sets the regione value for this UbicazioniOst.
     * 
     * @param regione
     */
    public void setRegione(java.lang.String regione) {
        this.regione = regione;
    }


    /**
     * Gets the tipoStrada value for this UbicazioniOst.
     * 
     * @return tipoStrada
     */
    public java.lang.String getTipoStrada() {
        return tipoStrada;
    }


    /**
     * Sets the tipoStrada value for this UbicazioniOst.
     * 
     * @param tipoStrada
     */
    public void setTipoStrada(java.lang.String tipoStrada) {
        this.tipoStrada = tipoStrada;
    }


    /**
     * Gets the wgs84X value for this UbicazioniOst.
     * 
     * @return wgs84X
     */
    public java.lang.Float getWgs84X() {
        return wgs84X;
    }


    /**
     * Sets the wgs84X value for this UbicazioniOst.
     * 
     * @param wgs84X
     */
    public void setWgs84X(java.lang.Float wgs84X) {
        this.wgs84X = wgs84X;
    }


    /**
     * Gets the wgs84Y value for this UbicazioniOst.
     * 
     * @return wgs84Y
     */
    public java.lang.Float getWgs84Y() {
        return wgs84Y;
    }


    /**
     * Sets the wgs84Y value for this UbicazioniOst.
     * 
     * @param wgs84Y
     */
    public void setWgs84Y(java.lang.Float wgs84Y) {
        this.wgs84Y = wgs84Y;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UbicazioniOst)) return false;
        UbicazioniOst other = (UbicazioniOst) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cap==null && other.getCap()==null) || 
             (this.cap!=null &&
              this.cap.equals(other.getCap()))) &&
            ((this.comune==null && other.getComune()==null) || 
             (this.comune!=null &&
              this.comune.equals(other.getComune()))) &&
            ((this.comuneItalia==null && other.getComuneItalia()==null) || 
             (this.comuneItalia!=null &&
              this.comuneItalia.equals(other.getComuneItalia()))) &&
            ((this.datiCatastalis==null && other.getDatiCatastalis()==null) || 
             (this.datiCatastalis!=null &&
              java.util.Arrays.equals(this.datiCatastalis, other.getDatiCatastalis()))) &&
            ((this.gbEst==null && other.getGbEst()==null) || 
             (this.gbEst!=null &&
              this.gbEst.equals(other.getGbEst()))) &&
            ((this.gbNord==null && other.getGbNord()==null) || 
             (this.gbNord!=null &&
              this.gbNord.equals(other.getGbNord()))) &&
            ((this.geocodice==null && other.getGeocodice()==null) || 
             (this.geocodice!=null &&
              this.geocodice.equals(other.getGeocodice()))) &&
            ((this.idUbicazione==null && other.getIdUbicazione()==null) || 
             (this.idUbicazione!=null &&
              this.idUbicazione.equals(other.getIdUbicazione()))) &&
            ((this.localita==null && other.getLocalita()==null) || 
             (this.localita!=null &&
              this.localita.equals(other.getLocalita()))) &&
            ((this.nomeStrada==null && other.getNomeStrada()==null) || 
             (this.nomeStrada!=null &&
              this.nomeStrada.equals(other.getNomeStrada()))) &&
            ((this.numeroCivico==null && other.getNumeroCivico()==null) || 
             (this.numeroCivico!=null &&
              this.numeroCivico.equals(other.getNumeroCivico()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.quotaSlmM==null && other.getQuotaSlmM()==null) || 
             (this.quotaSlmM!=null &&
              this.quotaSlmM.equals(other.getQuotaSlmM()))) &&
            ((this.regione==null && other.getRegione()==null) || 
             (this.regione!=null &&
              this.regione.equals(other.getRegione()))) &&
            ((this.tipoStrada==null && other.getTipoStrada()==null) || 
             (this.tipoStrada!=null &&
              this.tipoStrada.equals(other.getTipoStrada()))) &&
            ((this.wgs84X==null && other.getWgs84X()==null) || 
             (this.wgs84X!=null &&
              this.wgs84X.equals(other.getWgs84X()))) &&
            ((this.wgs84Y==null && other.getWgs84Y()==null) || 
             (this.wgs84Y!=null &&
              this.wgs84Y.equals(other.getWgs84Y())));
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
        if (getCap() != null) {
            _hashCode += getCap().hashCode();
        }
        if (getComune() != null) {
            _hashCode += getComune().hashCode();
        }
        if (getComuneItalia() != null) {
            _hashCode += getComuneItalia().hashCode();
        }
        if (getDatiCatastalis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDatiCatastalis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDatiCatastalis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getGbEst() != null) {
            _hashCode += getGbEst().hashCode();
        }
        if (getGbNord() != null) {
            _hashCode += getGbNord().hashCode();
        }
        if (getGeocodice() != null) {
            _hashCode += getGeocodice().hashCode();
        }
        if (getIdUbicazione() != null) {
            _hashCode += getIdUbicazione().hashCode();
        }
        if (getLocalita() != null) {
            _hashCode += getLocalita().hashCode();
        }
        if (getNomeStrada() != null) {
            _hashCode += getNomeStrada().hashCode();
        }
        if (getNumeroCivico() != null) {
            _hashCode += getNumeroCivico().hashCode();
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getQuotaSlmM() != null) {
            _hashCode += getQuotaSlmM().hashCode();
        }
        if (getRegione() != null) {
            _hashCode += getRegione().hashCode();
        }
        if (getTipoStrada() != null) {
            _hashCode += getTipoStrada().hashCode();
        }
        if (getWgs84X() != null) {
            _hashCode += getWgs84X().hashCode();
        }
        if (getWgs84Y() != null) {
            _hashCode += getWgs84Y().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UbicazioniOst.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ubicazioniOst"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cap");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comuneItalia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comuneItalia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItalia"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiCatastalis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "datiCatastalis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "datiCatastali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gbEst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gbEst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gbNord");
        elemField.setXmlName(new javax.xml.namespace.QName("", "gbNord"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("geocodice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "geocodice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUbicazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUbicazione"));
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
        elemField.setFieldName("nomeStrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeStrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroCivico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroCivico"));
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
        elemField.setFieldName("quotaSlmM");
        elemField.setXmlName(new javax.xml.namespace.QName("", "quotaSlmM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("regione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "regione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoStrada");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoStrada"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wgs84X");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wgs84X"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wgs84Y");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wgs84Y"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
