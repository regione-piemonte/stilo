/**
 * EntiControlloGoverno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class EntiControlloGoverno  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String acronimo;

    private com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms;

    private org.apache.axis.types.UnsignedShort attivo;

    private java.lang.String cap;

    private java.lang.String comune;

    private java.lang.String denominazione;

    private com.hyperborea.sira.ws.EcgUo[] ecgUos;

    private java.lang.String email;

    private com.hyperborea.sira.ws.EntiControlloGoverno[] entiControlloGovernoFigli;

    private com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGovernoPadre;

    private java.lang.String fullDeno;

    private java.lang.String idEcg;

    private java.lang.String numeroCivico;

    private com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives;

    private com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministrativesForEcgRichiedenteParere;

    private java.lang.String provincia;

    private java.lang.String regione;

    private java.lang.String telefono;

    private java.lang.String tipoSede;

    private java.lang.String via;

    public EntiControlloGoverno() {
    }

    public EntiControlloGoverno(
           java.lang.String acronimo,
           com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms,
           org.apache.axis.types.UnsignedShort attivo,
           java.lang.String cap,
           java.lang.String comune,
           java.lang.String denominazione,
           com.hyperborea.sira.ws.EcgUo[] ecgUos,
           java.lang.String email,
           com.hyperborea.sira.ws.EntiControlloGoverno[] entiControlloGovernoFigli,
           com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGovernoPadre,
           java.lang.String fullDeno,
           java.lang.String idEcg,
           java.lang.String numeroCivico,
           com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives,
           com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministrativesForEcgRichiedenteParere,
           java.lang.String provincia,
           java.lang.String regione,
           java.lang.String telefono,
           java.lang.String tipoSede,
           java.lang.String via) {
        this.acronimo = acronimo;
        this.attiDisposizioniAmms = attiDisposizioniAmms;
        this.attivo = attivo;
        this.cap = cap;
        this.comune = comune;
        this.denominazione = denominazione;
        this.ecgUos = ecgUos;
        this.email = email;
        this.entiControlloGovernoFigli = entiControlloGovernoFigli;
        this.entiControlloGovernoPadre = entiControlloGovernoPadre;
        this.fullDeno = fullDeno;
        this.idEcg = idEcg;
        this.numeroCivico = numeroCivico;
        this.praticheAmministratives = praticheAmministratives;
        this.praticheAmministrativesForEcgRichiedenteParere = praticheAmministrativesForEcgRichiedenteParere;
        this.provincia = provincia;
        this.regione = regione;
        this.telefono = telefono;
        this.tipoSede = tipoSede;
        this.via = via;
    }


    /**
     * Gets the acronimo value for this EntiControlloGoverno.
     * 
     * @return acronimo
     */
    public java.lang.String getAcronimo() {
        return acronimo;
    }


    /**
     * Sets the acronimo value for this EntiControlloGoverno.
     * 
     * @param acronimo
     */
    public void setAcronimo(java.lang.String acronimo) {
        this.acronimo = acronimo;
    }


    /**
     * Gets the attiDisposizioniAmms value for this EntiControlloGoverno.
     * 
     * @return attiDisposizioniAmms
     */
    public com.hyperborea.sira.ws.AttiDisposizioniAmm[] getAttiDisposizioniAmms() {
        return attiDisposizioniAmms;
    }


    /**
     * Sets the attiDisposizioniAmms value for this EntiControlloGoverno.
     * 
     * @param attiDisposizioniAmms
     */
    public void setAttiDisposizioniAmms(com.hyperborea.sira.ws.AttiDisposizioniAmm[] attiDisposizioniAmms) {
        this.attiDisposizioniAmms = attiDisposizioniAmms;
    }

    public com.hyperborea.sira.ws.AttiDisposizioniAmm getAttiDisposizioniAmms(int i) {
        return this.attiDisposizioniAmms[i];
    }

    public void setAttiDisposizioniAmms(int i, com.hyperborea.sira.ws.AttiDisposizioniAmm _value) {
        this.attiDisposizioniAmms[i] = _value;
    }


    /**
     * Gets the attivo value for this EntiControlloGoverno.
     * 
     * @return attivo
     */
    public org.apache.axis.types.UnsignedShort getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this EntiControlloGoverno.
     * 
     * @param attivo
     */
    public void setAttivo(org.apache.axis.types.UnsignedShort attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the cap value for this EntiControlloGoverno.
     * 
     * @return cap
     */
    public java.lang.String getCap() {
        return cap;
    }


    /**
     * Sets the cap value for this EntiControlloGoverno.
     * 
     * @param cap
     */
    public void setCap(java.lang.String cap) {
        this.cap = cap;
    }


    /**
     * Gets the comune value for this EntiControlloGoverno.
     * 
     * @return comune
     */
    public java.lang.String getComune() {
        return comune;
    }


    /**
     * Sets the comune value for this EntiControlloGoverno.
     * 
     * @param comune
     */
    public void setComune(java.lang.String comune) {
        this.comune = comune;
    }


    /**
     * Gets the denominazione value for this EntiControlloGoverno.
     * 
     * @return denominazione
     */
    public java.lang.String getDenominazione() {
        return denominazione;
    }


    /**
     * Sets the denominazione value for this EntiControlloGoverno.
     * 
     * @param denominazione
     */
    public void setDenominazione(java.lang.String denominazione) {
        this.denominazione = denominazione;
    }


    /**
     * Gets the ecgUos value for this EntiControlloGoverno.
     * 
     * @return ecgUos
     */
    public com.hyperborea.sira.ws.EcgUo[] getEcgUos() {
        return ecgUos;
    }


    /**
     * Sets the ecgUos value for this EntiControlloGoverno.
     * 
     * @param ecgUos
     */
    public void setEcgUos(com.hyperborea.sira.ws.EcgUo[] ecgUos) {
        this.ecgUos = ecgUos;
    }

    public com.hyperborea.sira.ws.EcgUo getEcgUos(int i) {
        return this.ecgUos[i];
    }

    public void setEcgUos(int i, com.hyperborea.sira.ws.EcgUo _value) {
        this.ecgUos[i] = _value;
    }


    /**
     * Gets the email value for this EntiControlloGoverno.
     * 
     * @return email
     */
    public java.lang.String getEmail() {
        return email;
    }


    /**
     * Sets the email value for this EntiControlloGoverno.
     * 
     * @param email
     */
    public void setEmail(java.lang.String email) {
        this.email = email;
    }


    /**
     * Gets the entiControlloGovernoFigli value for this EntiControlloGoverno.
     * 
     * @return entiControlloGovernoFigli
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno[] getEntiControlloGovernoFigli() {
        return entiControlloGovernoFigli;
    }


    /**
     * Sets the entiControlloGovernoFigli value for this EntiControlloGoverno.
     * 
     * @param entiControlloGovernoFigli
     */
    public void setEntiControlloGovernoFigli(com.hyperborea.sira.ws.EntiControlloGoverno[] entiControlloGovernoFigli) {
        this.entiControlloGovernoFigli = entiControlloGovernoFigli;
    }

    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGovernoFigli(int i) {
        return this.entiControlloGovernoFigli[i];
    }

    public void setEntiControlloGovernoFigli(int i, com.hyperborea.sira.ws.EntiControlloGoverno _value) {
        this.entiControlloGovernoFigli[i] = _value;
    }


    /**
     * Gets the entiControlloGovernoPadre value for this EntiControlloGoverno.
     * 
     * @return entiControlloGovernoPadre
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGovernoPadre() {
        return entiControlloGovernoPadre;
    }


    /**
     * Sets the entiControlloGovernoPadre value for this EntiControlloGoverno.
     * 
     * @param entiControlloGovernoPadre
     */
    public void setEntiControlloGovernoPadre(com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGovernoPadre) {
        this.entiControlloGovernoPadre = entiControlloGovernoPadre;
    }


    /**
     * Gets the fullDeno value for this EntiControlloGoverno.
     * 
     * @return fullDeno
     */
    public java.lang.String getFullDeno() {
        return fullDeno;
    }


    /**
     * Sets the fullDeno value for this EntiControlloGoverno.
     * 
     * @param fullDeno
     */
    public void setFullDeno(java.lang.String fullDeno) {
        this.fullDeno = fullDeno;
    }


    /**
     * Gets the idEcg value for this EntiControlloGoverno.
     * 
     * @return idEcg
     */
    public java.lang.String getIdEcg() {
        return idEcg;
    }


    /**
     * Sets the idEcg value for this EntiControlloGoverno.
     * 
     * @param idEcg
     */
    public void setIdEcg(java.lang.String idEcg) {
        this.idEcg = idEcg;
    }


    /**
     * Gets the numeroCivico value for this EntiControlloGoverno.
     * 
     * @return numeroCivico
     */
    public java.lang.String getNumeroCivico() {
        return numeroCivico;
    }


    /**
     * Sets the numeroCivico value for this EntiControlloGoverno.
     * 
     * @param numeroCivico
     */
    public void setNumeroCivico(java.lang.String numeroCivico) {
        this.numeroCivico = numeroCivico;
    }


    /**
     * Gets the praticheAmministratives value for this EntiControlloGoverno.
     * 
     * @return praticheAmministratives
     */
    public com.hyperborea.sira.ws.PraticheAmministrative[] getPraticheAmministratives() {
        return praticheAmministratives;
    }


    /**
     * Sets the praticheAmministratives value for this EntiControlloGoverno.
     * 
     * @param praticheAmministratives
     */
    public void setPraticheAmministratives(com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives) {
        this.praticheAmministratives = praticheAmministratives;
    }

    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministratives(int i) {
        return this.praticheAmministratives[i];
    }

    public void setPraticheAmministratives(int i, com.hyperborea.sira.ws.PraticheAmministrative _value) {
        this.praticheAmministratives[i] = _value;
    }


    /**
     * Gets the praticheAmministrativesForEcgRichiedenteParere value for this EntiControlloGoverno.
     * 
     * @return praticheAmministrativesForEcgRichiedenteParere
     */
    public com.hyperborea.sira.ws.PraticheAmministrative[] getPraticheAmministrativesForEcgRichiedenteParere() {
        return praticheAmministrativesForEcgRichiedenteParere;
    }


    /**
     * Sets the praticheAmministrativesForEcgRichiedenteParere value for this EntiControlloGoverno.
     * 
     * @param praticheAmministrativesForEcgRichiedenteParere
     */
    public void setPraticheAmministrativesForEcgRichiedenteParere(com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministrativesForEcgRichiedenteParere) {
        this.praticheAmministrativesForEcgRichiedenteParere = praticheAmministrativesForEcgRichiedenteParere;
    }

    public com.hyperborea.sira.ws.PraticheAmministrative getPraticheAmministrativesForEcgRichiedenteParere(int i) {
        return this.praticheAmministrativesForEcgRichiedenteParere[i];
    }

    public void setPraticheAmministrativesForEcgRichiedenteParere(int i, com.hyperborea.sira.ws.PraticheAmministrative _value) {
        this.praticheAmministrativesForEcgRichiedenteParere[i] = _value;
    }


    /**
     * Gets the provincia value for this EntiControlloGoverno.
     * 
     * @return provincia
     */
    public java.lang.String getProvincia() {
        return provincia;
    }


    /**
     * Sets the provincia value for this EntiControlloGoverno.
     * 
     * @param provincia
     */
    public void setProvincia(java.lang.String provincia) {
        this.provincia = provincia;
    }


    /**
     * Gets the regione value for this EntiControlloGoverno.
     * 
     * @return regione
     */
    public java.lang.String getRegione() {
        return regione;
    }


    /**
     * Sets the regione value for this EntiControlloGoverno.
     * 
     * @param regione
     */
    public void setRegione(java.lang.String regione) {
        this.regione = regione;
    }


    /**
     * Gets the telefono value for this EntiControlloGoverno.
     * 
     * @return telefono
     */
    public java.lang.String getTelefono() {
        return telefono;
    }


    /**
     * Sets the telefono value for this EntiControlloGoverno.
     * 
     * @param telefono
     */
    public void setTelefono(java.lang.String telefono) {
        this.telefono = telefono;
    }


    /**
     * Gets the tipoSede value for this EntiControlloGoverno.
     * 
     * @return tipoSede
     */
    public java.lang.String getTipoSede() {
        return tipoSede;
    }


    /**
     * Sets the tipoSede value for this EntiControlloGoverno.
     * 
     * @param tipoSede
     */
    public void setTipoSede(java.lang.String tipoSede) {
        this.tipoSede = tipoSede;
    }


    /**
     * Gets the via value for this EntiControlloGoverno.
     * 
     * @return via
     */
    public java.lang.String getVia() {
        return via;
    }


    /**
     * Sets the via value for this EntiControlloGoverno.
     * 
     * @param via
     */
    public void setVia(java.lang.String via) {
        this.via = via;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof EntiControlloGoverno)) return false;
        EntiControlloGoverno other = (EntiControlloGoverno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.acronimo==null && other.getAcronimo()==null) || 
             (this.acronimo!=null &&
              this.acronimo.equals(other.getAcronimo()))) &&
            ((this.attiDisposizioniAmms==null && other.getAttiDisposizioniAmms()==null) || 
             (this.attiDisposizioniAmms!=null &&
              java.util.Arrays.equals(this.attiDisposizioniAmms, other.getAttiDisposizioniAmms()))) &&
            ((this.attivo==null && other.getAttivo()==null) || 
             (this.attivo!=null &&
              this.attivo.equals(other.getAttivo()))) &&
            ((this.cap==null && other.getCap()==null) || 
             (this.cap!=null &&
              this.cap.equals(other.getCap()))) &&
            ((this.comune==null && other.getComune()==null) || 
             (this.comune!=null &&
              this.comune.equals(other.getComune()))) &&
            ((this.denominazione==null && other.getDenominazione()==null) || 
             (this.denominazione!=null &&
              this.denominazione.equals(other.getDenominazione()))) &&
            ((this.ecgUos==null && other.getEcgUos()==null) || 
             (this.ecgUos!=null &&
              java.util.Arrays.equals(this.ecgUos, other.getEcgUos()))) &&
            ((this.email==null && other.getEmail()==null) || 
             (this.email!=null &&
              this.email.equals(other.getEmail()))) &&
            ((this.entiControlloGovernoFigli==null && other.getEntiControlloGovernoFigli()==null) || 
             (this.entiControlloGovernoFigli!=null &&
              java.util.Arrays.equals(this.entiControlloGovernoFigli, other.getEntiControlloGovernoFigli()))) &&
            ((this.entiControlloGovernoPadre==null && other.getEntiControlloGovernoPadre()==null) || 
             (this.entiControlloGovernoPadre!=null &&
              this.entiControlloGovernoPadre.equals(other.getEntiControlloGovernoPadre()))) &&
            ((this.fullDeno==null && other.getFullDeno()==null) || 
             (this.fullDeno!=null &&
              this.fullDeno.equals(other.getFullDeno()))) &&
            ((this.idEcg==null && other.getIdEcg()==null) || 
             (this.idEcg!=null &&
              this.idEcg.equals(other.getIdEcg()))) &&
            ((this.numeroCivico==null && other.getNumeroCivico()==null) || 
             (this.numeroCivico!=null &&
              this.numeroCivico.equals(other.getNumeroCivico()))) &&
            ((this.praticheAmministratives==null && other.getPraticheAmministratives()==null) || 
             (this.praticheAmministratives!=null &&
              java.util.Arrays.equals(this.praticheAmministratives, other.getPraticheAmministratives()))) &&
            ((this.praticheAmministrativesForEcgRichiedenteParere==null && other.getPraticheAmministrativesForEcgRichiedenteParere()==null) || 
             (this.praticheAmministrativesForEcgRichiedenteParere!=null &&
              java.util.Arrays.equals(this.praticheAmministrativesForEcgRichiedenteParere, other.getPraticheAmministrativesForEcgRichiedenteParere()))) &&
            ((this.provincia==null && other.getProvincia()==null) || 
             (this.provincia!=null &&
              this.provincia.equals(other.getProvincia()))) &&
            ((this.regione==null && other.getRegione()==null) || 
             (this.regione!=null &&
              this.regione.equals(other.getRegione()))) &&
            ((this.telefono==null && other.getTelefono()==null) || 
             (this.telefono!=null &&
              this.telefono.equals(other.getTelefono()))) &&
            ((this.tipoSede==null && other.getTipoSede()==null) || 
             (this.tipoSede!=null &&
              this.tipoSede.equals(other.getTipoSede()))) &&
            ((this.via==null && other.getVia()==null) || 
             (this.via!=null &&
              this.via.equals(other.getVia())));
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
        if (getAcronimo() != null) {
            _hashCode += getAcronimo().hashCode();
        }
        if (getAttiDisposizioniAmms() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttiDisposizioniAmms());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttiDisposizioniAmms(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAttivo() != null) {
            _hashCode += getAttivo().hashCode();
        }
        if (getCap() != null) {
            _hashCode += getCap().hashCode();
        }
        if (getComune() != null) {
            _hashCode += getComune().hashCode();
        }
        if (getDenominazione() != null) {
            _hashCode += getDenominazione().hashCode();
        }
        if (getEcgUos() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEcgUos());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEcgUos(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEmail() != null) {
            _hashCode += getEmail().hashCode();
        }
        if (getEntiControlloGovernoFigli() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEntiControlloGovernoFigli());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEntiControlloGovernoFigli(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEntiControlloGovernoPadre() != null) {
            _hashCode += getEntiControlloGovernoPadre().hashCode();
        }
        if (getFullDeno() != null) {
            _hashCode += getFullDeno().hashCode();
        }
        if (getIdEcg() != null) {
            _hashCode += getIdEcg().hashCode();
        }
        if (getNumeroCivico() != null) {
            _hashCode += getNumeroCivico().hashCode();
        }
        if (getPraticheAmministratives() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPraticheAmministratives());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPraticheAmministratives(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPraticheAmministrativesForEcgRichiedenteParere() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPraticheAmministrativesForEcgRichiedenteParere());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPraticheAmministrativesForEcgRichiedenteParere(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProvincia() != null) {
            _hashCode += getProvincia().hashCode();
        }
        if (getRegione() != null) {
            _hashCode += getRegione().hashCode();
        }
        if (getTelefono() != null) {
            _hashCode += getTelefono().hashCode();
        }
        if (getTipoSede() != null) {
            _hashCode += getTipoSede().hashCode();
        }
        if (getVia() != null) {
            _hashCode += getVia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EntiControlloGoverno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acronimo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "acronimo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attiDisposizioniAmms");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attiDisposizioniAmms"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attiDisposizioniAmm"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedShort"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("denominazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ecgUos");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ecgUos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ecgUo"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("email");
        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entiControlloGovernoFigli");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entiControlloGovernoFigli"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entiControlloGovernoPadre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entiControlloGovernoPadre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullDeno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fullDeno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idEcg");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idEcg"));
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
        elemField.setFieldName("praticheAmministratives");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministratives"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("praticheAmministrativesForEcgRichiedenteParere");
        elemField.setXmlName(new javax.xml.namespace.QName("", "praticheAmministrativesForEcgRichiedenteParere"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "praticheAmministrative"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("provincia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "provincia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField.setFieldName("telefono");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telefono"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoSede");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoSede"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("via");
        elemField.setXmlName(new javax.xml.namespace.QName("", "via"));
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
