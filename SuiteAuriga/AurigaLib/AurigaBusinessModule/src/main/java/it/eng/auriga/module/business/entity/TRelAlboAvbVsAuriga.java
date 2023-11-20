/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Table(name = "T_REL_ALBO_AVB_VS_AURIGA")
public class TRelAlboAvbVsAuriga implements Serializable
{
    private static final long serialVersionUID = 1L;
    private BigDecimal idTipoAttoAuriga;
    private BigDecimal idTipoAttoAvb;
    private String nomeAttoAuriga;
    private String nomeAttoAvb;
    private BigDecimal durataPubblLegale;
    private BigDecimal oblioLegale;
    private BigDecimal visibNumRegGen;
    private BigDecimal visibNumRegArea;
    
    public TRelAlboAvbVsAuriga() {
    }
    
    public TRelAlboAvbVsAuriga(final BigDecimal idTipoAttoAuriga, final BigDecimal idTipoAttoAvb) {
        this.idTipoAttoAuriga = idTipoAttoAuriga;
        this.idTipoAttoAvb = idTipoAttoAvb;
    }
    
    public TRelAlboAvbVsAuriga(final BigDecimal idTipoAttoAuriga, final BigDecimal idTipoAttoAvb, final String nomeTipoAttoAuriga, final String nomeTipoAttoAvb, final BigDecimal durataPubblLegale, final BigDecimal oblioLegale, final BigDecimal visibNumRegGen, final BigDecimal visibNumRegArea) {
        this.idTipoAttoAuriga = idTipoAttoAuriga;
        this.idTipoAttoAvb = idTipoAttoAvb;
        this.nomeAttoAuriga = nomeTipoAttoAuriga;
        this.nomeAttoAvb = nomeTipoAttoAvb;
        this.durataPubblLegale = durataPubblLegale;
        this.oblioLegale = oblioLegale;
        this.visibNumRegGen = visibNumRegGen;
        this.visibNumRegArea = visibNumRegArea;
    }
    
    @Id
    @Column(name = "ID_TIPO_ATTO_AURIGA", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getIdTipoAttoAuriga() {
        return this.idTipoAttoAuriga;
    }
    
    public void setIdTipoAttoAuriga(final BigDecimal idTipoAttoAuriga) {
        this.idTipoAttoAuriga = idTipoAttoAuriga;
    }
    
    @Column(name = "ID_TIPO_ATTO_AVB", unique = true, nullable = false, precision = 22, scale = 0)
    public BigDecimal getIdTipoAttoAvb() {
        return this.idTipoAttoAvb;
    }
    
    public void setIdTipoAttoAvb(final BigDecimal idTipoAttoAvb) {
        this.idTipoAttoAvb = idTipoAttoAvb;
    }
    
    @Column(name = "NOME_ATTO_AURIGA", length = 2000)
    public String getNomeAttoAuriga() {
        return this.nomeAttoAuriga;
    }
    
    public void setNomeAttoAuriga(final String nomeAttoAuriga) {
        this.nomeAttoAuriga = nomeAttoAuriga;
    }
    
    @Column(name = "NOME_ATTO_AVB", length = 2000)
    public String getNomeAttoAvb() {
        return this.nomeAttoAvb;
    }
    
    public void setNomeAttoAvb(final String nomeAttoAvb) {
        this.nomeAttoAvb = nomeAttoAvb;
    }
    
    @Column(name = "DURATA_PUBBL_LEGALE", precision = 22, scale = 0)
    public BigDecimal getDurataPubblLegale() {
        return this.durataPubblLegale;
    }
    
    public void setDurataPubblLegale(final BigDecimal durataPubblLegale) {
        this.durataPubblLegale = durataPubblLegale;
    }
    
    @Column(name = "OBLIO_LEGALE", precision = 22, scale = 0)
    public BigDecimal getOblioLegale() {
        return this.oblioLegale;
    }
    
    public void setOblioLegale(final BigDecimal oblioLegale) {
        this.oblioLegale = oblioLegale;
    }
    
    @Column(name = "VISIB_NUM_REG_GEN", precision = 22, scale = 0)
    public BigDecimal getVisibNumRegGen() {
        return this.visibNumRegGen;
    }
    
    public void setVisibNumRegGen(final BigDecimal visibNumRegGen) {
        this.visibNumRegGen = visibNumRegGen;
    }
    
    @Column(name = "VISIB_NUM_REG_AREA", precision = 22, scale = 0)
    public BigDecimal getVisibNumRegArea() {
        return this.visibNumRegArea;
    }
    
    public void setVisibNumRegArea(final BigDecimal visibNumRegArea) {
        this.visibNumRegArea = visibNumRegArea;
    }
}