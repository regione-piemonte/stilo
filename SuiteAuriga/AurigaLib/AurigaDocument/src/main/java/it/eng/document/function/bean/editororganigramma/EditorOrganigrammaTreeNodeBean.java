/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="EditorOrganigrammaTreeNodeBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class EditorOrganigrammaTreeNodeBean {

//	private BigDecimal nroLivello;
    @XmlElement(name = "idNode")
	private String idNode;
    @XmlElement(name = "idNodeDB")
	private String idNodeDB;
    @XmlElement(name = "idUo")
	private String idUo;
    @XmlElement(name = "idNodeOrig")
    private String idNodeOrig;
	@XmlElement(name = "tipo")
	private String tipo;
    @XmlElement(name = "parentId")
	private String parentId;
    @XmlElement(name = "parentIdDB")
   	private String parentIdDB;
	@XmlElement(name = "dettagli")
	private String dettagli;
    @XmlElement(name = "descrUoSvUt")
	private String descrUoSvUt;
    @XmlElement(name = "nome")
	private String nome;
    @XmlElement(name = "nroProgr")
	private BigDecimal nroProgr;
    @XmlElement(name = "codRapidoUo")
	private String codRapidoUo;
    @XmlElement(name = "flgEsplodiNodo")
	private String flgEsplodiNodo;
    @XmlElement(name = "competenze")
	private String competenze;
    @XmlElement(name = "note")
	private String note;
    @XmlElement(name = "level")
	private BigDecimal level;
    @XmlElement(name = "staff")
	private String staff;
    @XmlElementWrapper(name = "listaUtentiAssociati") 
    @XmlElement(name = "utenteAssociato")
	private List<UtenteXOrganigrammaBean> listaUtentiAssociati;
    @XmlElement(name = "children")
	private List<EditorOrganigrammaTreeNodeBean> children = new ArrayList<EditorOrganigrammaTreeNodeBean>();	
    @XmlElementWrapper(name = "listaUOIncorporate") 
    @XmlElement(name = "UOincorporata")
	private List<String> listaUOIncorporate;
	
	public List<String> getListaUOIncorporate() {
		return listaUOIncorporate;
	}

	public void setListaUOIncorporate(List<String> listaUOIncorporate) {
		this.listaUOIncorporate = listaUOIncorporate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<UtenteXOrganigrammaBean> getListaUtentiAssociati() {
		return listaUtentiAssociati;
	}

	public void setListaUtentiAssociati(List<UtenteXOrganigrammaBean> listaUtentiAssociati) {
		this.listaUtentiAssociati = listaUtentiAssociati;
	}
	
	public String getCompetenze() {
		return competenze;
	}

	public void setCompetenze(String competenze) {
		this.competenze = competenze;
	}
	
	public String getIdNode() {
		return idNode;
	}
	
	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getDettagli() {
		return dettagli;
	}
	
	public void setDettagli(String dettagli) {
		this.dettagli = dettagli;
	}
	
	public String getDescrUoSvUt() {
		return descrUoSvUt;
	}
	
	public void setDescrUoSvUt(String descrUoSvUt) {
		this.descrUoSvUt = descrUoSvUt;
	}
	
	public String getCodRapidoUo() {
		return codRapidoUo;
	}
	
	public void setCodRapidoUo(String codRapidoUo) {
		this.codRapidoUo = codRapidoUo;
	}
	
	public List<EditorOrganigrammaTreeNodeBean> getChildren() {
		return children;
	}
	
	public void setChildren(List<EditorOrganigrammaTreeNodeBean> children) {
		this.children = children;
	}

	public String getFlgEsplodiNodo() {
		return flgEsplodiNodo;
	}

	public void setFlgEsplodiNodo(String flgEsplodiNodo) {
		this.flgEsplodiNodo = flgEsplodiNodo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getNroProgr() {
		return nroProgr;
	}

	public void setNroProgr(BigDecimal nroProgr) {
		this.nroProgr = nroProgr;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getIdNodeDB() {
		return idNodeDB;
	}

	public void setIdNodeDB(String idNodeDB) {
		this.idNodeDB = idNodeDB;
	}
	
	public BigDecimal getLevel() {
		return level;
	}

	public void setLevel(BigDecimal level) {
		this.level = level;
	}
	
	public String getIdNodeOrig() {
		return idNodeOrig;
	}

	public void setIdNodeOrig(String idNodeOrig) {
		this.idNodeOrig = idNodeOrig;
	}
	
    public String getParentIdDB() {
		return parentIdDB;
	}

	public void setParentIdDB(String parentIdDB) {
		this.parentIdDB = parentIdDB;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

}
