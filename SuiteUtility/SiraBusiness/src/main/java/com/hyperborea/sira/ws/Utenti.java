/**
 * Utenti.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Utenti  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String authenticationClassname;

    private java.lang.String cfPiva;

    private java.lang.String cognome;

    private java.lang.String EMailUtente;

    private com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno;

    private java.util.Calendar fineValidita;

    private java.lang.Integer idOstSl;

    private java.lang.String idPersona;

    private java.lang.Integer idUtente;

    private java.lang.String idUtenteSysdoc;

    private java.util.Calendar inizioValidita;

    private java.lang.String login;

    private java.lang.String matricola;

    private java.lang.String nome;

    private com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali;

    private java.lang.String password;

    private com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives;

    private com.hyperborea.sira.ws.ProfiliUtente profiliUtente;

    private com.hyperborea.sira.ws.RuoliUtenti[] ruoliUtentis;

    private java.lang.String telefonoUtente;

    private com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative;

    public Utenti() {
    }

    public Utenti(
           java.lang.String authenticationClassname,
           java.lang.String cfPiva,
           java.lang.String cognome,
           java.lang.String EMailUtente,
           com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno,
           java.util.Calendar fineValidita,
           java.lang.Integer idOstSl,
           java.lang.String idPersona,
           java.lang.Integer idUtente,
           java.lang.String idUtenteSysdoc,
           java.util.Calendar inizioValidita,
           java.lang.String login,
           java.lang.String matricola,
           java.lang.String nome,
           com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali,
           java.lang.String password,
           com.hyperborea.sira.ws.PraticheAmministrative[] praticheAmministratives,
           com.hyperborea.sira.ws.ProfiliUtente profiliUtente,
           com.hyperborea.sira.ws.RuoliUtenti[] ruoliUtentis,
           java.lang.String telefonoUtente,
           com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
        this.authenticationClassname = authenticationClassname;
        this.cfPiva = cfPiva;
        this.cognome = cognome;
        this.EMailUtente = EMailUtente;
        this.entiControlloGoverno = entiControlloGoverno;
        this.fineValidita = fineValidita;
        this.idOstSl = idOstSl;
        this.idPersona = idPersona;
        this.idUtente = idUtente;
        this.idUtenteSysdoc = idUtenteSysdoc;
        this.inizioValidita = inizioValidita;
        this.login = login;
        this.matricola = matricola;
        this.nome = nome;
        this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
        this.password = password;
        this.praticheAmministratives = praticheAmministratives;
        this.profiliUtente = profiliUtente;
        this.ruoliUtentis = ruoliUtentis;
        this.telefonoUtente = telefonoUtente;
        this.unitaOrganizzative = unitaOrganizzative;
    }


    /**
     * Gets the authenticationClassname value for this Utenti.
     * 
     * @return authenticationClassname
     */
    public java.lang.String getAuthenticationClassname() {
        return authenticationClassname;
    }


    /**
     * Sets the authenticationClassname value for this Utenti.
     * 
     * @param authenticationClassname
     */
    public void setAuthenticationClassname(java.lang.String authenticationClassname) {
        this.authenticationClassname = authenticationClassname;
    }


    /**
     * Gets the cfPiva value for this Utenti.
     * 
     * @return cfPiva
     */
    public java.lang.String getCfPiva() {
        return cfPiva;
    }


    /**
     * Sets the cfPiva value for this Utenti.
     * 
     * @param cfPiva
     */
    public void setCfPiva(java.lang.String cfPiva) {
        this.cfPiva = cfPiva;
    }


    /**
     * Gets the cognome value for this Utenti.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this Utenti.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the EMailUtente value for this Utenti.
     * 
     * @return EMailUtente
     */
    public java.lang.String getEMailUtente() {
        return EMailUtente;
    }


    /**
     * Sets the EMailUtente value for this Utenti.
     * 
     * @param EMailUtente
     */
    public void setEMailUtente(java.lang.String EMailUtente) {
        this.EMailUtente = EMailUtente;
    }


    /**
     * Gets the entiControlloGoverno value for this Utenti.
     * 
     * @return entiControlloGoverno
     */
    public com.hyperborea.sira.ws.EntiControlloGoverno getEntiControlloGoverno() {
        return entiControlloGoverno;
    }


    /**
     * Sets the entiControlloGoverno value for this Utenti.
     * 
     * @param entiControlloGoverno
     */
    public void setEntiControlloGoverno(com.hyperborea.sira.ws.EntiControlloGoverno entiControlloGoverno) {
        this.entiControlloGoverno = entiControlloGoverno;
    }


    /**
     * Gets the fineValidita value for this Utenti.
     * 
     * @return fineValidita
     */
    public java.util.Calendar getFineValidita() {
        return fineValidita;
    }


    /**
     * Sets the fineValidita value for this Utenti.
     * 
     * @param fineValidita
     */
    public void setFineValidita(java.util.Calendar fineValidita) {
        this.fineValidita = fineValidita;
    }


    /**
     * Gets the idOstSl value for this Utenti.
     * 
     * @return idOstSl
     */
    public java.lang.Integer getIdOstSl() {
        return idOstSl;
    }


    /**
     * Sets the idOstSl value for this Utenti.
     * 
     * @param idOstSl
     */
    public void setIdOstSl(java.lang.Integer idOstSl) {
        this.idOstSl = idOstSl;
    }


    /**
     * Gets the idPersona value for this Utenti.
     * 
     * @return idPersona
     */
    public java.lang.String getIdPersona() {
        return idPersona;
    }


    /**
     * Sets the idPersona value for this Utenti.
     * 
     * @param idPersona
     */
    public void setIdPersona(java.lang.String idPersona) {
        this.idPersona = idPersona;
    }


    /**
     * Gets the idUtente value for this Utenti.
     * 
     * @return idUtente
     */
    public java.lang.Integer getIdUtente() {
        return idUtente;
    }


    /**
     * Sets the idUtente value for this Utenti.
     * 
     * @param idUtente
     */
    public void setIdUtente(java.lang.Integer idUtente) {
        this.idUtente = idUtente;
    }


    /**
     * Gets the idUtenteSysdoc value for this Utenti.
     * 
     * @return idUtenteSysdoc
     */
    public java.lang.String getIdUtenteSysdoc() {
        return idUtenteSysdoc;
    }


    /**
     * Sets the idUtenteSysdoc value for this Utenti.
     * 
     * @param idUtenteSysdoc
     */
    public void setIdUtenteSysdoc(java.lang.String idUtenteSysdoc) {
        this.idUtenteSysdoc = idUtenteSysdoc;
    }


    /**
     * Gets the inizioValidita value for this Utenti.
     * 
     * @return inizioValidita
     */
    public java.util.Calendar getInizioValidita() {
        return inizioValidita;
    }


    /**
     * Sets the inizioValidita value for this Utenti.
     * 
     * @param inizioValidita
     */
    public void setInizioValidita(java.util.Calendar inizioValidita) {
        this.inizioValidita = inizioValidita;
    }


    /**
     * Gets the login value for this Utenti.
     * 
     * @return login
     */
    public java.lang.String getLogin() {
        return login;
    }


    /**
     * Sets the login value for this Utenti.
     * 
     * @param login
     */
    public void setLogin(java.lang.String login) {
        this.login = login;
    }


    /**
     * Gets the matricola value for this Utenti.
     * 
     * @return matricola
     */
    public java.lang.String getMatricola() {
        return matricola;
    }


    /**
     * Sets the matricola value for this Utenti.
     * 
     * @param matricola
     */
    public void setMatricola(java.lang.String matricola) {
        this.matricola = matricola;
    }


    /**
     * Gets the nome value for this Utenti.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this Utenti.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the oggettiStruttureTerritoriali value for this Utenti.
     * 
     * @return oggettiStruttureTerritoriali
     */
    public com.hyperborea.sira.ws.OggettiStruttureTerritoriali getOggettiStruttureTerritoriali() {
        return oggettiStruttureTerritoriali;
    }


    /**
     * Sets the oggettiStruttureTerritoriali value for this Utenti.
     * 
     * @param oggettiStruttureTerritoriali
     */
    public void setOggettiStruttureTerritoriali(com.hyperborea.sira.ws.OggettiStruttureTerritoriali oggettiStruttureTerritoriali) {
        this.oggettiStruttureTerritoriali = oggettiStruttureTerritoriali;
    }


    /**
     * Gets the password value for this Utenti.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Utenti.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the praticheAmministratives value for this Utenti.
     * 
     * @return praticheAmministratives
     */
    public com.hyperborea.sira.ws.PraticheAmministrative[] getPraticheAmministratives() {
        return praticheAmministratives;
    }


    /**
     * Sets the praticheAmministratives value for this Utenti.
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
     * Gets the profiliUtente value for this Utenti.
     * 
     * @return profiliUtente
     */
    public com.hyperborea.sira.ws.ProfiliUtente getProfiliUtente() {
        return profiliUtente;
    }


    /**
     * Sets the profiliUtente value for this Utenti.
     * 
     * @param profiliUtente
     */
    public void setProfiliUtente(com.hyperborea.sira.ws.ProfiliUtente profiliUtente) {
        this.profiliUtente = profiliUtente;
    }


    /**
     * Gets the ruoliUtentis value for this Utenti.
     * 
     * @return ruoliUtentis
     */
    public com.hyperborea.sira.ws.RuoliUtenti[] getRuoliUtentis() {
        return ruoliUtentis;
    }


    /**
     * Sets the ruoliUtentis value for this Utenti.
     * 
     * @param ruoliUtentis
     */
    public void setRuoliUtentis(com.hyperborea.sira.ws.RuoliUtenti[] ruoliUtentis) {
        this.ruoliUtentis = ruoliUtentis;
    }

    public com.hyperborea.sira.ws.RuoliUtenti getRuoliUtentis(int i) {
        return this.ruoliUtentis[i];
    }

    public void setRuoliUtentis(int i, com.hyperborea.sira.ws.RuoliUtenti _value) {
        this.ruoliUtentis[i] = _value;
    }


    /**
     * Gets the telefonoUtente value for this Utenti.
     * 
     * @return telefonoUtente
     */
    public java.lang.String getTelefonoUtente() {
        return telefonoUtente;
    }


    /**
     * Sets the telefonoUtente value for this Utenti.
     * 
     * @param telefonoUtente
     */
    public void setTelefonoUtente(java.lang.String telefonoUtente) {
        this.telefonoUtente = telefonoUtente;
    }


    /**
     * Gets the unitaOrganizzative value for this Utenti.
     * 
     * @return unitaOrganizzative
     */
    public com.hyperborea.sira.ws.UnitaOrganizzative getUnitaOrganizzative() {
        return unitaOrganizzative;
    }


    /**
     * Sets the unitaOrganizzative value for this Utenti.
     * 
     * @param unitaOrganizzative
     */
    public void setUnitaOrganizzative(com.hyperborea.sira.ws.UnitaOrganizzative unitaOrganizzative) {
        this.unitaOrganizzative = unitaOrganizzative;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Utenti)) return false;
        Utenti other = (Utenti) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.authenticationClassname==null && other.getAuthenticationClassname()==null) || 
             (this.authenticationClassname!=null &&
              this.authenticationClassname.equals(other.getAuthenticationClassname()))) &&
            ((this.cfPiva==null && other.getCfPiva()==null) || 
             (this.cfPiva!=null &&
              this.cfPiva.equals(other.getCfPiva()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.EMailUtente==null && other.getEMailUtente()==null) || 
             (this.EMailUtente!=null &&
              this.EMailUtente.equals(other.getEMailUtente()))) &&
            ((this.entiControlloGoverno==null && other.getEntiControlloGoverno()==null) || 
             (this.entiControlloGoverno!=null &&
              this.entiControlloGoverno.equals(other.getEntiControlloGoverno()))) &&
            ((this.fineValidita==null && other.getFineValidita()==null) || 
             (this.fineValidita!=null &&
              this.fineValidita.equals(other.getFineValidita()))) &&
            ((this.idOstSl==null && other.getIdOstSl()==null) || 
             (this.idOstSl!=null &&
              this.idOstSl.equals(other.getIdOstSl()))) &&
            ((this.idPersona==null && other.getIdPersona()==null) || 
             (this.idPersona!=null &&
              this.idPersona.equals(other.getIdPersona()))) &&
            ((this.idUtente==null && other.getIdUtente()==null) || 
             (this.idUtente!=null &&
              this.idUtente.equals(other.getIdUtente()))) &&
            ((this.idUtenteSysdoc==null && other.getIdUtenteSysdoc()==null) || 
             (this.idUtenteSysdoc!=null &&
              this.idUtenteSysdoc.equals(other.getIdUtenteSysdoc()))) &&
            ((this.inizioValidita==null && other.getInizioValidita()==null) || 
             (this.inizioValidita!=null &&
              this.inizioValidita.equals(other.getInizioValidita()))) &&
            ((this.login==null && other.getLogin()==null) || 
             (this.login!=null &&
              this.login.equals(other.getLogin()))) &&
            ((this.matricola==null && other.getMatricola()==null) || 
             (this.matricola!=null &&
              this.matricola.equals(other.getMatricola()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.oggettiStruttureTerritoriali==null && other.getOggettiStruttureTerritoriali()==null) || 
             (this.oggettiStruttureTerritoriali!=null &&
              this.oggettiStruttureTerritoriali.equals(other.getOggettiStruttureTerritoriali()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.praticheAmministratives==null && other.getPraticheAmministratives()==null) || 
             (this.praticheAmministratives!=null &&
              java.util.Arrays.equals(this.praticheAmministratives, other.getPraticheAmministratives()))) &&
            ((this.profiliUtente==null && other.getProfiliUtente()==null) || 
             (this.profiliUtente!=null &&
              this.profiliUtente.equals(other.getProfiliUtente()))) &&
            ((this.ruoliUtentis==null && other.getRuoliUtentis()==null) || 
             (this.ruoliUtentis!=null &&
              java.util.Arrays.equals(this.ruoliUtentis, other.getRuoliUtentis()))) &&
            ((this.telefonoUtente==null && other.getTelefonoUtente()==null) || 
             (this.telefonoUtente!=null &&
              this.telefonoUtente.equals(other.getTelefonoUtente()))) &&
            ((this.unitaOrganizzative==null && other.getUnitaOrganizzative()==null) || 
             (this.unitaOrganizzative!=null &&
              this.unitaOrganizzative.equals(other.getUnitaOrganizzative())));
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
        if (getAuthenticationClassname() != null) {
            _hashCode += getAuthenticationClassname().hashCode();
        }
        if (getCfPiva() != null) {
            _hashCode += getCfPiva().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getEMailUtente() != null) {
            _hashCode += getEMailUtente().hashCode();
        }
        if (getEntiControlloGoverno() != null) {
            _hashCode += getEntiControlloGoverno().hashCode();
        }
        if (getFineValidita() != null) {
            _hashCode += getFineValidita().hashCode();
        }
        if (getIdOstSl() != null) {
            _hashCode += getIdOstSl().hashCode();
        }
        if (getIdPersona() != null) {
            _hashCode += getIdPersona().hashCode();
        }
        if (getIdUtente() != null) {
            _hashCode += getIdUtente().hashCode();
        }
        if (getIdUtenteSysdoc() != null) {
            _hashCode += getIdUtenteSysdoc().hashCode();
        }
        if (getInizioValidita() != null) {
            _hashCode += getInizioValidita().hashCode();
        }
        if (getLogin() != null) {
            _hashCode += getLogin().hashCode();
        }
        if (getMatricola() != null) {
            _hashCode += getMatricola().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getOggettiStruttureTerritoriali() != null) {
            _hashCode += getOggettiStruttureTerritoriali().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
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
        if (getProfiliUtente() != null) {
            _hashCode += getProfiliUtente().hashCode();
        }
        if (getRuoliUtentis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRuoliUtentis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRuoliUtentis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTelefonoUtente() != null) {
            _hashCode += getTelefonoUtente().hashCode();
        }
        if (getUnitaOrganizzative() != null) {
            _hashCode += getUnitaOrganizzative().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Utenti.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utenti"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticationClassname");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authenticationClassname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cfPiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cfPiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("EMailUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "EMailUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entiControlloGoverno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entiControlloGoverno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "entiControlloGoverno"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fineValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fineValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOstSl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOstSl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPersona");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPersona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUtenteSysdoc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUtenteSysdoc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inizioValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inizioValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("login");
        elemField.setXmlName(new javax.xml.namespace.QName("", "login"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matricola");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matricola"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oggettiStruttureTerritoriali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggettiStruttureTerritoriali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "oggettiStruttureTerritoriali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
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
        elemField.setFieldName("profiliUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profiliUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "profiliUtente"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruoliUtentis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ruoliUtentis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ruoliUtenti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("telefonoUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "telefonoUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitaOrganizzative");
        elemField.setXmlName(new javax.xml.namespace.QName("", "unitaOrganizzative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "unitaOrganizzative"));
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
