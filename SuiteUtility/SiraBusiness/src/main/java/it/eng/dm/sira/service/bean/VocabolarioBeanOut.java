package it.eng.dm.sira.service.bean;

import java.util.List;

import com.hyperborea.sira.ws.VocabularyItem;

public class VocabolarioBeanOut {
	
	private java.lang.String className;
    
    private java.lang.String descriptionProperty;
    
    private java.lang.String vocProperty;
    
    private java.lang.String idProperty;
	
	private List<VocabularyItem> vociVocabolario;
	
    public java.lang.String getClassName() {
		return className;
	}

	public void setClassName(java.lang.String className) {
		this.className = className;
	}

	public java.lang.String getDescriptionProperty() {
		return descriptionProperty;
	}

	public void setDescriptionProperty(java.lang.String descriptionProperty) {
		this.descriptionProperty = descriptionProperty;
	}

	public java.lang.String getVocProperty() {
		return vocProperty;
	}

	public void setVocProperty(java.lang.String vocProperty) {
		this.vocProperty = vocProperty;
	}
	
	public java.lang.String getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(java.lang.String idProperty) {
		this.idProperty = idProperty;
	}

	public List<VocabularyItem> getVociVocabolario() {
		return vociVocabolario;
	}

	public void setVociVocabolario(List<VocabularyItem> vociVocabolario) {
		this.vociVocabolario = vociVocabolario;
	}

}
