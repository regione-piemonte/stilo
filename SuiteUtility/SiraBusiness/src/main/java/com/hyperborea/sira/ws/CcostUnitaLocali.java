/**
 * CcostUnitaLocali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostUnitaLocali  implements java.io.Serializable {
    private java.lang.Integer annoDenunciaAddetti;

    private com.hyperborea.sira.ws.AreeStoccMat[] areeStoccMats;

    private com.hyperborea.sira.ws.AreeStoccRifiuti[] areeStoccRifiutis;

    private com.hyperborea.sira.ws.Ateco atecoByFkAteco1;

    private com.hyperborea.sira.ws.Ateco atecoByFkAteco2;

    private com.hyperborea.sira.ws.Ateco atecoByFkAteco3;

    private com.hyperborea.sira.ws.Ateco atecoByFkAteco4;

    private com.hyperborea.sira.ws.AttEcoSvolta[] attEcoSvoltas;

    private java.lang.String certificazioneQualita1;

    private java.lang.String certificazioneQualita2;

    private java.lang.String certificazioneQualita3;

    private com.hyperborea.sira.ws.Combustibili[] combustibilis;

    private com.hyperborea.sira.ws.ConsumoEnergia[] consumoEnergias;

    private com.hyperborea.sira.ws.ConsumoMatPrime[] consumoMatPrimes;

    private com.hyperborea.sira.ws.ConsumoRisIdriche[] consumoRisIdriches;

    private java.lang.String denominazioneUl;

    private java.lang.String descrizAttivita;

    private com.hyperborea.sira.ws.EffZooDatiAzienda effZooDatiAzienda;

    private com.hyperborea.sira.ws.EmissioniConv[] emissioniConvs;

    private java.lang.Integer idCcost;

    private java.lang.String note;

    private java.lang.Integer numeroAddetti;

    private java.lang.Integer numeroProgUl;

    private com.hyperborea.sira.ws.ProduzioneFanghi produzioneFanghi;

    private com.hyperborea.sira.ws.ScarichiParziali[] scarichiParzialis;

    private com.hyperborea.sira.ws.SorgOdore[] sorgOdores;

    private com.hyperborea.sira.ws.SorgRumore[] sorgRumores;

    private java.lang.String statoAttivita;

    private java.lang.Float superficieCopertaM2;

    private java.lang.Float superficieM2;

    private java.lang.Float superficieScopertaNonpavM2;

    private java.lang.Float superficieScopertaPavM2;

    private java.lang.String tipoUnitaLocale;

    private com.hyperborea.sira.ws.VocClassiInformazioni vocClassiInformazioni;

    public CcostUnitaLocali() {
    }

    public CcostUnitaLocali(
           java.lang.Integer annoDenunciaAddetti,
           com.hyperborea.sira.ws.AreeStoccMat[] areeStoccMats,
           com.hyperborea.sira.ws.AreeStoccRifiuti[] areeStoccRifiutis,
           com.hyperborea.sira.ws.Ateco atecoByFkAteco1,
           com.hyperborea.sira.ws.Ateco atecoByFkAteco2,
           com.hyperborea.sira.ws.Ateco atecoByFkAteco3,
           com.hyperborea.sira.ws.Ateco atecoByFkAteco4,
           com.hyperborea.sira.ws.AttEcoSvolta[] attEcoSvoltas,
           java.lang.String certificazioneQualita1,
           java.lang.String certificazioneQualita2,
           java.lang.String certificazioneQualita3,
           com.hyperborea.sira.ws.Combustibili[] combustibilis,
           com.hyperborea.sira.ws.ConsumoEnergia[] consumoEnergias,
           com.hyperborea.sira.ws.ConsumoMatPrime[] consumoMatPrimes,
           com.hyperborea.sira.ws.ConsumoRisIdriche[] consumoRisIdriches,
           java.lang.String denominazioneUl,
           java.lang.String descrizAttivita,
           com.hyperborea.sira.ws.EffZooDatiAzienda effZooDatiAzienda,
           com.hyperborea.sira.ws.EmissioniConv[] emissioniConvs,
           java.lang.Integer idCcost,
           java.lang.String note,
           java.lang.Integer numeroAddetti,
           java.lang.Integer numeroProgUl,
           com.hyperborea.sira.ws.ProduzioneFanghi produzioneFanghi,
           com.hyperborea.sira.ws.ScarichiParziali[] scarichiParzialis,
           com.hyperborea.sira.ws.SorgOdore[] sorgOdores,
           com.hyperborea.sira.ws.SorgRumore[] sorgRumores,
           java.lang.String statoAttivita,
           java.lang.Float superficieCopertaM2,
           java.lang.Float superficieM2,
           java.lang.Float superficieScopertaNonpavM2,
           java.lang.Float superficieScopertaPavM2,
           java.lang.String tipoUnitaLocale,
           com.hyperborea.sira.ws.VocClassiInformazioni vocClassiInformazioni) {
           this.annoDenunciaAddetti = annoDenunciaAddetti;
           this.areeStoccMats = areeStoccMats;
           this.areeStoccRifiutis = areeStoccRifiutis;
           this.atecoByFkAteco1 = atecoByFkAteco1;
           this.atecoByFkAteco2 = atecoByFkAteco2;
           this.atecoByFkAteco3 = atecoByFkAteco3;
           this.atecoByFkAteco4 = atecoByFkAteco4;
           this.attEcoSvoltas = attEcoSvoltas;
           this.certificazioneQualita1 = certificazioneQualita1;
           this.certificazioneQualita2 = certificazioneQualita2;
           this.certificazioneQualita3 = certificazioneQualita3;
           this.combustibilis = combustibilis;
           this.consumoEnergias = consumoEnergias;
           this.consumoMatPrimes = consumoMatPrimes;
           this.consumoRisIdriches = consumoRisIdriches;
           this.denominazioneUl = denominazioneUl;
           this.descrizAttivita = descrizAttivita;
           this.effZooDatiAzienda = effZooDatiAzienda;
           this.emissioniConvs = emissioniConvs;
           this.idCcost = idCcost;
           this.note = note;
           this.numeroAddetti = numeroAddetti;
           this.numeroProgUl = numeroProgUl;
           this.produzioneFanghi = produzioneFanghi;
           this.scarichiParzialis = scarichiParzialis;
           this.sorgOdores = sorgOdores;
           this.sorgRumores = sorgRumores;
           this.statoAttivita = statoAttivita;
           this.superficieCopertaM2 = superficieCopertaM2;
           this.superficieM2 = superficieM2;
           this.superficieScopertaNonpavM2 = superficieScopertaNonpavM2;
           this.superficieScopertaPavM2 = superficieScopertaPavM2;
           this.tipoUnitaLocale = tipoUnitaLocale;
           this.vocClassiInformazioni = vocClassiInformazioni;
    }


    /**
     * Gets the annoDenunciaAddetti value for this CcostUnitaLocali.
     * 
     * @return annoDenunciaAddetti
     */
    public java.lang.Integer getAnnoDenunciaAddetti() {
        return annoDenunciaAddetti;
    }


    /**
     * Sets the annoDenunciaAddetti value for this CcostUnitaLocali.
     * 
     * @param annoDenunciaAddetti
     */
    public void setAnnoDenunciaAddetti(java.lang.Integer annoDenunciaAddetti) {
        this.annoDenunciaAddetti = annoDenunciaAddetti;
    }


    /**
     * Gets the areeStoccMats value for this CcostUnitaLocali.
     * 
     * @return areeStoccMats
     */
    public com.hyperborea.sira.ws.AreeStoccMat[] getAreeStoccMats() {
        return areeStoccMats;
    }


    /**
     * Sets the areeStoccMats value for this CcostUnitaLocali.
     * 
     * @param areeStoccMats
     */
    public void setAreeStoccMats(com.hyperborea.sira.ws.AreeStoccMat[] areeStoccMats) {
        this.areeStoccMats = areeStoccMats;
    }

    public com.hyperborea.sira.ws.AreeStoccMat getAreeStoccMats(int i) {
        return this.areeStoccMats[i];
    }

    public void setAreeStoccMats(int i, com.hyperborea.sira.ws.AreeStoccMat _value) {
        this.areeStoccMats[i] = _value;
    }


    /**
     * Gets the areeStoccRifiutis value for this CcostUnitaLocali.
     * 
     * @return areeStoccRifiutis
     */
    public com.hyperborea.sira.ws.AreeStoccRifiuti[] getAreeStoccRifiutis() {
        return areeStoccRifiutis;
    }


    /**
     * Sets the areeStoccRifiutis value for this CcostUnitaLocali.
     * 
     * @param areeStoccRifiutis
     */
    public void setAreeStoccRifiutis(com.hyperborea.sira.ws.AreeStoccRifiuti[] areeStoccRifiutis) {
        this.areeStoccRifiutis = areeStoccRifiutis;
    }

    public com.hyperborea.sira.ws.AreeStoccRifiuti getAreeStoccRifiutis(int i) {
        return this.areeStoccRifiutis[i];
    }

    public void setAreeStoccRifiutis(int i, com.hyperborea.sira.ws.AreeStoccRifiuti _value) {
        this.areeStoccRifiutis[i] = _value;
    }


    /**
     * Gets the atecoByFkAteco1 value for this CcostUnitaLocali.
     * 
     * @return atecoByFkAteco1
     */
    public com.hyperborea.sira.ws.Ateco getAtecoByFkAteco1() {
        return atecoByFkAteco1;
    }


    /**
     * Sets the atecoByFkAteco1 value for this CcostUnitaLocali.
     * 
     * @param atecoByFkAteco1
     */
    public void setAtecoByFkAteco1(com.hyperborea.sira.ws.Ateco atecoByFkAteco1) {
        this.atecoByFkAteco1 = atecoByFkAteco1;
    }


    /**
     * Gets the atecoByFkAteco2 value for this CcostUnitaLocali.
     * 
     * @return atecoByFkAteco2
     */
    public com.hyperborea.sira.ws.Ateco getAtecoByFkAteco2() {
        return atecoByFkAteco2;
    }


    /**
     * Sets the atecoByFkAteco2 value for this CcostUnitaLocali.
     * 
     * @param atecoByFkAteco2
     */
    public void setAtecoByFkAteco2(com.hyperborea.sira.ws.Ateco atecoByFkAteco2) {
        this.atecoByFkAteco2 = atecoByFkAteco2;
    }


    /**
     * Gets the atecoByFkAteco3 value for this CcostUnitaLocali.
     * 
     * @return atecoByFkAteco3
     */
    public com.hyperborea.sira.ws.Ateco getAtecoByFkAteco3() {
        return atecoByFkAteco3;
    }


    /**
     * Sets the atecoByFkAteco3 value for this CcostUnitaLocali.
     * 
     * @param atecoByFkAteco3
     */
    public void setAtecoByFkAteco3(com.hyperborea.sira.ws.Ateco atecoByFkAteco3) {
        this.atecoByFkAteco3 = atecoByFkAteco3;
    }


    /**
     * Gets the atecoByFkAteco4 value for this CcostUnitaLocali.
     * 
     * @return atecoByFkAteco4
     */
    public com.hyperborea.sira.ws.Ateco getAtecoByFkAteco4() {
        return atecoByFkAteco4;
    }


    /**
     * Sets the atecoByFkAteco4 value for this CcostUnitaLocali.
     * 
     * @param atecoByFkAteco4
     */
    public void setAtecoByFkAteco4(com.hyperborea.sira.ws.Ateco atecoByFkAteco4) {
        this.atecoByFkAteco4 = atecoByFkAteco4;
    }


    /**
     * Gets the attEcoSvoltas value for this CcostUnitaLocali.
     * 
     * @return attEcoSvoltas
     */
    public com.hyperborea.sira.ws.AttEcoSvolta[] getAttEcoSvoltas() {
        return attEcoSvoltas;
    }


    /**
     * Sets the attEcoSvoltas value for this CcostUnitaLocali.
     * 
     * @param attEcoSvoltas
     */
    public void setAttEcoSvoltas(com.hyperborea.sira.ws.AttEcoSvolta[] attEcoSvoltas) {
        this.attEcoSvoltas = attEcoSvoltas;
    }

    public com.hyperborea.sira.ws.AttEcoSvolta getAttEcoSvoltas(int i) {
        return this.attEcoSvoltas[i];
    }

    public void setAttEcoSvoltas(int i, com.hyperborea.sira.ws.AttEcoSvolta _value) {
        this.attEcoSvoltas[i] = _value;
    }


    /**
     * Gets the certificazioneQualita1 value for this CcostUnitaLocali.
     * 
     * @return certificazioneQualita1
     */
    public java.lang.String getCertificazioneQualita1() {
        return certificazioneQualita1;
    }


    /**
     * Sets the certificazioneQualita1 value for this CcostUnitaLocali.
     * 
     * @param certificazioneQualita1
     */
    public void setCertificazioneQualita1(java.lang.String certificazioneQualita1) {
        this.certificazioneQualita1 = certificazioneQualita1;
    }


    /**
     * Gets the certificazioneQualita2 value for this CcostUnitaLocali.
     * 
     * @return certificazioneQualita2
     */
    public java.lang.String getCertificazioneQualita2() {
        return certificazioneQualita2;
    }


    /**
     * Sets the certificazioneQualita2 value for this CcostUnitaLocali.
     * 
     * @param certificazioneQualita2
     */
    public void setCertificazioneQualita2(java.lang.String certificazioneQualita2) {
        this.certificazioneQualita2 = certificazioneQualita2;
    }


    /**
     * Gets the certificazioneQualita3 value for this CcostUnitaLocali.
     * 
     * @return certificazioneQualita3
     */
    public java.lang.String getCertificazioneQualita3() {
        return certificazioneQualita3;
    }


    /**
     * Sets the certificazioneQualita3 value for this CcostUnitaLocali.
     * 
     * @param certificazioneQualita3
     */
    public void setCertificazioneQualita3(java.lang.String certificazioneQualita3) {
        this.certificazioneQualita3 = certificazioneQualita3;
    }


    /**
     * Gets the combustibilis value for this CcostUnitaLocali.
     * 
     * @return combustibilis
     */
    public com.hyperborea.sira.ws.Combustibili[] getCombustibilis() {
        return combustibilis;
    }


    /**
     * Sets the combustibilis value for this CcostUnitaLocali.
     * 
     * @param combustibilis
     */
    public void setCombustibilis(com.hyperborea.sira.ws.Combustibili[] combustibilis) {
        this.combustibilis = combustibilis;
    }

    public com.hyperborea.sira.ws.Combustibili getCombustibilis(int i) {
        return this.combustibilis[i];
    }

    public void setCombustibilis(int i, com.hyperborea.sira.ws.Combustibili _value) {
        this.combustibilis[i] = _value;
    }


    /**
     * Gets the consumoEnergias value for this CcostUnitaLocali.
     * 
     * @return consumoEnergias
     */
    public com.hyperborea.sira.ws.ConsumoEnergia[] getConsumoEnergias() {
        return consumoEnergias;
    }


    /**
     * Sets the consumoEnergias value for this CcostUnitaLocali.
     * 
     * @param consumoEnergias
     */
    public void setConsumoEnergias(com.hyperborea.sira.ws.ConsumoEnergia[] consumoEnergias) {
        this.consumoEnergias = consumoEnergias;
    }

    public com.hyperborea.sira.ws.ConsumoEnergia getConsumoEnergias(int i) {
        return this.consumoEnergias[i];
    }

    public void setConsumoEnergias(int i, com.hyperborea.sira.ws.ConsumoEnergia _value) {
        this.consumoEnergias[i] = _value;
    }


    /**
     * Gets the consumoMatPrimes value for this CcostUnitaLocali.
     * 
     * @return consumoMatPrimes
     */
    public com.hyperborea.sira.ws.ConsumoMatPrime[] getConsumoMatPrimes() {
        return consumoMatPrimes;
    }


    /**
     * Sets the consumoMatPrimes value for this CcostUnitaLocali.
     * 
     * @param consumoMatPrimes
     */
    public void setConsumoMatPrimes(com.hyperborea.sira.ws.ConsumoMatPrime[] consumoMatPrimes) {
        this.consumoMatPrimes = consumoMatPrimes;
    }

    public com.hyperborea.sira.ws.ConsumoMatPrime getConsumoMatPrimes(int i) {
        return this.consumoMatPrimes[i];
    }

    public void setConsumoMatPrimes(int i, com.hyperborea.sira.ws.ConsumoMatPrime _value) {
        this.consumoMatPrimes[i] = _value;
    }


    /**
     * Gets the consumoRisIdriches value for this CcostUnitaLocali.
     * 
     * @return consumoRisIdriches
     */
    public com.hyperborea.sira.ws.ConsumoRisIdriche[] getConsumoRisIdriches() {
        return consumoRisIdriches;
    }


    /**
     * Sets the consumoRisIdriches value for this CcostUnitaLocali.
     * 
     * @param consumoRisIdriches
     */
    public void setConsumoRisIdriches(com.hyperborea.sira.ws.ConsumoRisIdriche[] consumoRisIdriches) {
        this.consumoRisIdriches = consumoRisIdriches;
    }

    public com.hyperborea.sira.ws.ConsumoRisIdriche getConsumoRisIdriches(int i) {
        return this.consumoRisIdriches[i];
    }

    public void setConsumoRisIdriches(int i, com.hyperborea.sira.ws.ConsumoRisIdriche _value) {
        this.consumoRisIdriches[i] = _value;
    }


    /**
     * Gets the denominazioneUl value for this CcostUnitaLocali.
     * 
     * @return denominazioneUl
     */
    public java.lang.String getDenominazioneUl() {
        return denominazioneUl;
    }


    /**
     * Sets the denominazioneUl value for this CcostUnitaLocali.
     * 
     * @param denominazioneUl
     */
    public void setDenominazioneUl(java.lang.String denominazioneUl) {
        this.denominazioneUl = denominazioneUl;
    }


    /**
     * Gets the descrizAttivita value for this CcostUnitaLocali.
     * 
     * @return descrizAttivita
     */
    public java.lang.String getDescrizAttivita() {
        return descrizAttivita;
    }


    /**
     * Sets the descrizAttivita value for this CcostUnitaLocali.
     * 
     * @param descrizAttivita
     */
    public void setDescrizAttivita(java.lang.String descrizAttivita) {
        this.descrizAttivita = descrizAttivita;
    }


    /**
     * Gets the effZooDatiAzienda value for this CcostUnitaLocali.
     * 
     * @return effZooDatiAzienda
     */
    public com.hyperborea.sira.ws.EffZooDatiAzienda getEffZooDatiAzienda() {
        return effZooDatiAzienda;
    }


    /**
     * Sets the effZooDatiAzienda value for this CcostUnitaLocali.
     * 
     * @param effZooDatiAzienda
     */
    public void setEffZooDatiAzienda(com.hyperborea.sira.ws.EffZooDatiAzienda effZooDatiAzienda) {
        this.effZooDatiAzienda = effZooDatiAzienda;
    }


    /**
     * Gets the emissioniConvs value for this CcostUnitaLocali.
     * 
     * @return emissioniConvs
     */
    public com.hyperborea.sira.ws.EmissioniConv[] getEmissioniConvs() {
        return emissioniConvs;
    }


    /**
     * Sets the emissioniConvs value for this CcostUnitaLocali.
     * 
     * @param emissioniConvs
     */
    public void setEmissioniConvs(com.hyperborea.sira.ws.EmissioniConv[] emissioniConvs) {
        this.emissioniConvs = emissioniConvs;
    }

    public com.hyperborea.sira.ws.EmissioniConv getEmissioniConvs(int i) {
        return this.emissioniConvs[i];
    }

    public void setEmissioniConvs(int i, com.hyperborea.sira.ws.EmissioniConv _value) {
        this.emissioniConvs[i] = _value;
    }


    /**
     * Gets the idCcost value for this CcostUnitaLocali.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostUnitaLocali.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the note value for this CcostUnitaLocali.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this CcostUnitaLocali.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the numeroAddetti value for this CcostUnitaLocali.
     * 
     * @return numeroAddetti
     */
    public java.lang.Integer getNumeroAddetti() {
        return numeroAddetti;
    }


    /**
     * Sets the numeroAddetti value for this CcostUnitaLocali.
     * 
     * @param numeroAddetti
     */
    public void setNumeroAddetti(java.lang.Integer numeroAddetti) {
        this.numeroAddetti = numeroAddetti;
    }


    /**
     * Gets the numeroProgUl value for this CcostUnitaLocali.
     * 
     * @return numeroProgUl
     */
    public java.lang.Integer getNumeroProgUl() {
        return numeroProgUl;
    }


    /**
     * Sets the numeroProgUl value for this CcostUnitaLocali.
     * 
     * @param numeroProgUl
     */
    public void setNumeroProgUl(java.lang.Integer numeroProgUl) {
        this.numeroProgUl = numeroProgUl;
    }


    /**
     * Gets the produzioneFanghi value for this CcostUnitaLocali.
     * 
     * @return produzioneFanghi
     */
    public com.hyperborea.sira.ws.ProduzioneFanghi getProduzioneFanghi() {
        return produzioneFanghi;
    }


    /**
     * Sets the produzioneFanghi value for this CcostUnitaLocali.
     * 
     * @param produzioneFanghi
     */
    public void setProduzioneFanghi(com.hyperborea.sira.ws.ProduzioneFanghi produzioneFanghi) {
        this.produzioneFanghi = produzioneFanghi;
    }


    /**
     * Gets the scarichiParzialis value for this CcostUnitaLocali.
     * 
     * @return scarichiParzialis
     */
    public com.hyperborea.sira.ws.ScarichiParziali[] getScarichiParzialis() {
        return scarichiParzialis;
    }


    /**
     * Sets the scarichiParzialis value for this CcostUnitaLocali.
     * 
     * @param scarichiParzialis
     */
    public void setScarichiParzialis(com.hyperborea.sira.ws.ScarichiParziali[] scarichiParzialis) {
        this.scarichiParzialis = scarichiParzialis;
    }

    public com.hyperborea.sira.ws.ScarichiParziali getScarichiParzialis(int i) {
        return this.scarichiParzialis[i];
    }

    public void setScarichiParzialis(int i, com.hyperborea.sira.ws.ScarichiParziali _value) {
        this.scarichiParzialis[i] = _value;
    }


    /**
     * Gets the sorgOdores value for this CcostUnitaLocali.
     * 
     * @return sorgOdores
     */
    public com.hyperborea.sira.ws.SorgOdore[] getSorgOdores() {
        return sorgOdores;
    }


    /**
     * Sets the sorgOdores value for this CcostUnitaLocali.
     * 
     * @param sorgOdores
     */
    public void setSorgOdores(com.hyperborea.sira.ws.SorgOdore[] sorgOdores) {
        this.sorgOdores = sorgOdores;
    }

    public com.hyperborea.sira.ws.SorgOdore getSorgOdores(int i) {
        return this.sorgOdores[i];
    }

    public void setSorgOdores(int i, com.hyperborea.sira.ws.SorgOdore _value) {
        this.sorgOdores[i] = _value;
    }


    /**
     * Gets the sorgRumores value for this CcostUnitaLocali.
     * 
     * @return sorgRumores
     */
    public com.hyperborea.sira.ws.SorgRumore[] getSorgRumores() {
        return sorgRumores;
    }


    /**
     * Sets the sorgRumores value for this CcostUnitaLocali.
     * 
     * @param sorgRumores
     */
    public void setSorgRumores(com.hyperborea.sira.ws.SorgRumore[] sorgRumores) {
        this.sorgRumores = sorgRumores;
    }

    public com.hyperborea.sira.ws.SorgRumore getSorgRumores(int i) {
        return this.sorgRumores[i];
    }

    public void setSorgRumores(int i, com.hyperborea.sira.ws.SorgRumore _value) {
        this.sorgRumores[i] = _value;
    }


    /**
     * Gets the statoAttivita value for this CcostUnitaLocali.
     * 
     * @return statoAttivita
     */
    public java.lang.String getStatoAttivita() {
        return statoAttivita;
    }


    /**
     * Sets the statoAttivita value for this CcostUnitaLocali.
     * 
     * @param statoAttivita
     */
    public void setStatoAttivita(java.lang.String statoAttivita) {
        this.statoAttivita = statoAttivita;
    }


    /**
     * Gets the superficieCopertaM2 value for this CcostUnitaLocali.
     * 
     * @return superficieCopertaM2
     */
    public java.lang.Float getSuperficieCopertaM2() {
        return superficieCopertaM2;
    }


    /**
     * Sets the superficieCopertaM2 value for this CcostUnitaLocali.
     * 
     * @param superficieCopertaM2
     */
    public void setSuperficieCopertaM2(java.lang.Float superficieCopertaM2) {
        this.superficieCopertaM2 = superficieCopertaM2;
    }


    /**
     * Gets the superficieM2 value for this CcostUnitaLocali.
     * 
     * @return superficieM2
     */
    public java.lang.Float getSuperficieM2() {
        return superficieM2;
    }


    /**
     * Sets the superficieM2 value for this CcostUnitaLocali.
     * 
     * @param superficieM2
     */
    public void setSuperficieM2(java.lang.Float superficieM2) {
        this.superficieM2 = superficieM2;
    }


    /**
     * Gets the superficieScopertaNonpavM2 value for this CcostUnitaLocali.
     * 
     * @return superficieScopertaNonpavM2
     */
    public java.lang.Float getSuperficieScopertaNonpavM2() {
        return superficieScopertaNonpavM2;
    }


    /**
     * Sets the superficieScopertaNonpavM2 value for this CcostUnitaLocali.
     * 
     * @param superficieScopertaNonpavM2
     */
    public void setSuperficieScopertaNonpavM2(java.lang.Float superficieScopertaNonpavM2) {
        this.superficieScopertaNonpavM2 = superficieScopertaNonpavM2;
    }


    /**
     * Gets the superficieScopertaPavM2 value for this CcostUnitaLocali.
     * 
     * @return superficieScopertaPavM2
     */
    public java.lang.Float getSuperficieScopertaPavM2() {
        return superficieScopertaPavM2;
    }


    /**
     * Sets the superficieScopertaPavM2 value for this CcostUnitaLocali.
     * 
     * @param superficieScopertaPavM2
     */
    public void setSuperficieScopertaPavM2(java.lang.Float superficieScopertaPavM2) {
        this.superficieScopertaPavM2 = superficieScopertaPavM2;
    }


    /**
     * Gets the tipoUnitaLocale value for this CcostUnitaLocali.
     * 
     * @return tipoUnitaLocale
     */
    public java.lang.String getTipoUnitaLocale() {
        return tipoUnitaLocale;
    }


    /**
     * Sets the tipoUnitaLocale value for this CcostUnitaLocali.
     * 
     * @param tipoUnitaLocale
     */
    public void setTipoUnitaLocale(java.lang.String tipoUnitaLocale) {
        this.tipoUnitaLocale = tipoUnitaLocale;
    }


    /**
     * Gets the vocClassiInformazioni value for this CcostUnitaLocali.
     * 
     * @return vocClassiInformazioni
     */
    public com.hyperborea.sira.ws.VocClassiInformazioni getVocClassiInformazioni() {
        return vocClassiInformazioni;
    }


    /**
     * Sets the vocClassiInformazioni value for this CcostUnitaLocali.
     * 
     * @param vocClassiInformazioni
     */
    public void setVocClassiInformazioni(com.hyperborea.sira.ws.VocClassiInformazioni vocClassiInformazioni) {
        this.vocClassiInformazioni = vocClassiInformazioni;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostUnitaLocali)) return false;
        CcostUnitaLocali other = (CcostUnitaLocali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.annoDenunciaAddetti==null && other.getAnnoDenunciaAddetti()==null) || 
             (this.annoDenunciaAddetti!=null &&
              this.annoDenunciaAddetti.equals(other.getAnnoDenunciaAddetti()))) &&
            ((this.areeStoccMats==null && other.getAreeStoccMats()==null) || 
             (this.areeStoccMats!=null &&
              java.util.Arrays.equals(this.areeStoccMats, other.getAreeStoccMats()))) &&
            ((this.areeStoccRifiutis==null && other.getAreeStoccRifiutis()==null) || 
             (this.areeStoccRifiutis!=null &&
              java.util.Arrays.equals(this.areeStoccRifiutis, other.getAreeStoccRifiutis()))) &&
            ((this.atecoByFkAteco1==null && other.getAtecoByFkAteco1()==null) || 
             (this.atecoByFkAteco1!=null &&
              this.atecoByFkAteco1.equals(other.getAtecoByFkAteco1()))) &&
            ((this.atecoByFkAteco2==null && other.getAtecoByFkAteco2()==null) || 
             (this.atecoByFkAteco2!=null &&
              this.atecoByFkAteco2.equals(other.getAtecoByFkAteco2()))) &&
            ((this.atecoByFkAteco3==null && other.getAtecoByFkAteco3()==null) || 
             (this.atecoByFkAteco3!=null &&
              this.atecoByFkAteco3.equals(other.getAtecoByFkAteco3()))) &&
            ((this.atecoByFkAteco4==null && other.getAtecoByFkAteco4()==null) || 
             (this.atecoByFkAteco4!=null &&
              this.atecoByFkAteco4.equals(other.getAtecoByFkAteco4()))) &&
            ((this.attEcoSvoltas==null && other.getAttEcoSvoltas()==null) || 
             (this.attEcoSvoltas!=null &&
              java.util.Arrays.equals(this.attEcoSvoltas, other.getAttEcoSvoltas()))) &&
            ((this.certificazioneQualita1==null && other.getCertificazioneQualita1()==null) || 
             (this.certificazioneQualita1!=null &&
              this.certificazioneQualita1.equals(other.getCertificazioneQualita1()))) &&
            ((this.certificazioneQualita2==null && other.getCertificazioneQualita2()==null) || 
             (this.certificazioneQualita2!=null &&
              this.certificazioneQualita2.equals(other.getCertificazioneQualita2()))) &&
            ((this.certificazioneQualita3==null && other.getCertificazioneQualita3()==null) || 
             (this.certificazioneQualita3!=null &&
              this.certificazioneQualita3.equals(other.getCertificazioneQualita3()))) &&
            ((this.combustibilis==null && other.getCombustibilis()==null) || 
             (this.combustibilis!=null &&
              java.util.Arrays.equals(this.combustibilis, other.getCombustibilis()))) &&
            ((this.consumoEnergias==null && other.getConsumoEnergias()==null) || 
             (this.consumoEnergias!=null &&
              java.util.Arrays.equals(this.consumoEnergias, other.getConsumoEnergias()))) &&
            ((this.consumoMatPrimes==null && other.getConsumoMatPrimes()==null) || 
             (this.consumoMatPrimes!=null &&
              java.util.Arrays.equals(this.consumoMatPrimes, other.getConsumoMatPrimes()))) &&
            ((this.consumoRisIdriches==null && other.getConsumoRisIdriches()==null) || 
             (this.consumoRisIdriches!=null &&
              java.util.Arrays.equals(this.consumoRisIdriches, other.getConsumoRisIdriches()))) &&
            ((this.denominazioneUl==null && other.getDenominazioneUl()==null) || 
             (this.denominazioneUl!=null &&
              this.denominazioneUl.equals(other.getDenominazioneUl()))) &&
            ((this.descrizAttivita==null && other.getDescrizAttivita()==null) || 
             (this.descrizAttivita!=null &&
              this.descrizAttivita.equals(other.getDescrizAttivita()))) &&
            ((this.effZooDatiAzienda==null && other.getEffZooDatiAzienda()==null) || 
             (this.effZooDatiAzienda!=null &&
              this.effZooDatiAzienda.equals(other.getEffZooDatiAzienda()))) &&
            ((this.emissioniConvs==null && other.getEmissioniConvs()==null) || 
             (this.emissioniConvs!=null &&
              java.util.Arrays.equals(this.emissioniConvs, other.getEmissioniConvs()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.numeroAddetti==null && other.getNumeroAddetti()==null) || 
             (this.numeroAddetti!=null &&
              this.numeroAddetti.equals(other.getNumeroAddetti()))) &&
            ((this.numeroProgUl==null && other.getNumeroProgUl()==null) || 
             (this.numeroProgUl!=null &&
              this.numeroProgUl.equals(other.getNumeroProgUl()))) &&
            ((this.produzioneFanghi==null && other.getProduzioneFanghi()==null) || 
             (this.produzioneFanghi!=null &&
              this.produzioneFanghi.equals(other.getProduzioneFanghi()))) &&
            ((this.scarichiParzialis==null && other.getScarichiParzialis()==null) || 
             (this.scarichiParzialis!=null &&
              java.util.Arrays.equals(this.scarichiParzialis, other.getScarichiParzialis()))) &&
            ((this.sorgOdores==null && other.getSorgOdores()==null) || 
             (this.sorgOdores!=null &&
              java.util.Arrays.equals(this.sorgOdores, other.getSorgOdores()))) &&
            ((this.sorgRumores==null && other.getSorgRumores()==null) || 
             (this.sorgRumores!=null &&
              java.util.Arrays.equals(this.sorgRumores, other.getSorgRumores()))) &&
            ((this.statoAttivita==null && other.getStatoAttivita()==null) || 
             (this.statoAttivita!=null &&
              this.statoAttivita.equals(other.getStatoAttivita()))) &&
            ((this.superficieCopertaM2==null && other.getSuperficieCopertaM2()==null) || 
             (this.superficieCopertaM2!=null &&
              this.superficieCopertaM2.equals(other.getSuperficieCopertaM2()))) &&
            ((this.superficieM2==null && other.getSuperficieM2()==null) || 
             (this.superficieM2!=null &&
              this.superficieM2.equals(other.getSuperficieM2()))) &&
            ((this.superficieScopertaNonpavM2==null && other.getSuperficieScopertaNonpavM2()==null) || 
             (this.superficieScopertaNonpavM2!=null &&
              this.superficieScopertaNonpavM2.equals(other.getSuperficieScopertaNonpavM2()))) &&
            ((this.superficieScopertaPavM2==null && other.getSuperficieScopertaPavM2()==null) || 
             (this.superficieScopertaPavM2!=null &&
              this.superficieScopertaPavM2.equals(other.getSuperficieScopertaPavM2()))) &&
            ((this.tipoUnitaLocale==null && other.getTipoUnitaLocale()==null) || 
             (this.tipoUnitaLocale!=null &&
              this.tipoUnitaLocale.equals(other.getTipoUnitaLocale()))) &&
            ((this.vocClassiInformazioni==null && other.getVocClassiInformazioni()==null) || 
             (this.vocClassiInformazioni!=null &&
              this.vocClassiInformazioni.equals(other.getVocClassiInformazioni())));
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
        if (getAnnoDenunciaAddetti() != null) {
            _hashCode += getAnnoDenunciaAddetti().hashCode();
        }
        if (getAreeStoccMats() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAreeStoccMats());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAreeStoccMats(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAreeStoccRifiutis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAreeStoccRifiutis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAreeStoccRifiutis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAtecoByFkAteco1() != null) {
            _hashCode += getAtecoByFkAteco1().hashCode();
        }
        if (getAtecoByFkAteco2() != null) {
            _hashCode += getAtecoByFkAteco2().hashCode();
        }
        if (getAtecoByFkAteco3() != null) {
            _hashCode += getAtecoByFkAteco3().hashCode();
        }
        if (getAtecoByFkAteco4() != null) {
            _hashCode += getAtecoByFkAteco4().hashCode();
        }
        if (getAttEcoSvoltas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttEcoSvoltas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttEcoSvoltas(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCertificazioneQualita1() != null) {
            _hashCode += getCertificazioneQualita1().hashCode();
        }
        if (getCertificazioneQualita2() != null) {
            _hashCode += getCertificazioneQualita2().hashCode();
        }
        if (getCertificazioneQualita3() != null) {
            _hashCode += getCertificazioneQualita3().hashCode();
        }
        if (getCombustibilis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCombustibilis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCombustibilis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConsumoEnergias() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsumoEnergias());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsumoEnergias(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConsumoMatPrimes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsumoMatPrimes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsumoMatPrimes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getConsumoRisIdriches() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getConsumoRisIdriches());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getConsumoRisIdriches(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDenominazioneUl() != null) {
            _hashCode += getDenominazioneUl().hashCode();
        }
        if (getDescrizAttivita() != null) {
            _hashCode += getDescrizAttivita().hashCode();
        }
        if (getEffZooDatiAzienda() != null) {
            _hashCode += getEffZooDatiAzienda().hashCode();
        }
        if (getEmissioniConvs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEmissioniConvs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEmissioniConvs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getNumeroAddetti() != null) {
            _hashCode += getNumeroAddetti().hashCode();
        }
        if (getNumeroProgUl() != null) {
            _hashCode += getNumeroProgUl().hashCode();
        }
        if (getProduzioneFanghi() != null) {
            _hashCode += getProduzioneFanghi().hashCode();
        }
        if (getScarichiParzialis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getScarichiParzialis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getScarichiParzialis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSorgOdores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSorgOdores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSorgOdores(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSorgRumores() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSorgRumores());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSorgRumores(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getStatoAttivita() != null) {
            _hashCode += getStatoAttivita().hashCode();
        }
        if (getSuperficieCopertaM2() != null) {
            _hashCode += getSuperficieCopertaM2().hashCode();
        }
        if (getSuperficieM2() != null) {
            _hashCode += getSuperficieM2().hashCode();
        }
        if (getSuperficieScopertaNonpavM2() != null) {
            _hashCode += getSuperficieScopertaNonpavM2().hashCode();
        }
        if (getSuperficieScopertaPavM2() != null) {
            _hashCode += getSuperficieScopertaPavM2().hashCode();
        }
        if (getTipoUnitaLocale() != null) {
            _hashCode += getTipoUnitaLocale().hashCode();
        }
        if (getVocClassiInformazioni() != null) {
            _hashCode += getVocClassiInformazioni().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostUnitaLocali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostUnitaLocali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoDenunciaAddetti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "annoDenunciaAddetti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areeStoccMats");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areeStoccMats"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeStoccMat"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areeStoccRifiutis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areeStoccRifiutis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeStoccRifiuti"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atecoByFkAteco1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atecoByFkAteco1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ateco"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atecoByFkAteco2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atecoByFkAteco2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ateco"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atecoByFkAteco3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atecoByFkAteco3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ateco"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("atecoByFkAteco4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "atecoByFkAteco4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ateco"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attEcoSvoltas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attEcoSvoltas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attEcoSvolta"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificazioneQualita1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificazioneQualita1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificazioneQualita2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificazioneQualita2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificazioneQualita3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificazioneQualita3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("combustibilis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "combustibilis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "combustibili"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoEnergias");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoEnergias"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoEnergia"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoMatPrimes");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoMatPrimes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoMatPrime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumoRisIdriches");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumoRisIdriches"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumoRisIdriche"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazioneUl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "denominazioneUl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("effZooDatiAzienda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "effZooDatiAzienda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "effZooDatiAzienda"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("emissioniConvs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "emissioniConvs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "emissioniConv"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroAddetti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroAddetti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProgUl");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProgUl"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzioneFanghi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzioneFanghi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioneFanghi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scarichiParzialis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "scarichiParzialis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "scarichiParziali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgOdores");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgOdores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgOdore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sorgRumores");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sorgRumores"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sorgRumore"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statoAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieCopertaM2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieCopertaM2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieM2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieM2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieScopertaNonpavM2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieScopertaNonpavM2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieScopertaPavM2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieScopertaPavM2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoUnitaLocale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoUnitaLocale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocClassiInformazioni");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocClassiInformazioni"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocClassiInformazioni"));
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
