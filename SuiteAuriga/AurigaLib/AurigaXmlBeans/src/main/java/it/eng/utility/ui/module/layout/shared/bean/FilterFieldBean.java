/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class FilterFieldBean {

	private String name;
	private String title;
	private boolean toShowIfSpec;
	private boolean required;
	private FilterType type;
	private String customType;
	private FilterSelectBean select;
	private List<String> dependsFrom;
	private boolean fixed;
	private boolean requiredIfPrivilegi;
	private String categoria;
	private boolean showFlgRicorsiva;
	private boolean showSelectAttributi;
	private boolean requiredForDepends;
	private String lookupType;
	private String lookupField;
	private boolean showLookupDetail;
	private String nomeTabella;
	private boolean showFiltriCustom;
	private String dictionaryEntry;
	private boolean hasFloat;
	private String nroDecimali;
	private String defaultIdNameField;
	private String defaultDisplayNameField;
	private boolean canEdit;
	
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public boolean isToShowIfSpec() {
		return toShowIfSpec;
	}

	public void setToShowIfSpec(boolean toShowIfSpec) {
		this.toShowIfSpec = toShowIfSpec;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setSelect(FilterSelectBean select) {
		this.select = select;
	}

	public FilterSelectBean getSelect() {
		return select;
	}

	public void setType(FilterType type) {
		this.type = type;
	}

	public FilterType getType() {
		return type;
	}

	public String getCustomType() {
		return customType;
	}

	public void setCustomType(String customType) {
		this.customType = customType;
	}

	public void setDependsFrom(List<String> dependsFrom) {
		this.dependsFrom = dependsFrom;
	}

	public List<String> getDependsFrom() {
		return dependsFrom;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public boolean isRequiredIfPrivilegi() {
		return requiredIfPrivilegi;
	}

	public void setRequiredIfPrivilegi(boolean requiredIfPrivilegi) {
		this.requiredIfPrivilegi = requiredIfPrivilegi;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public boolean isShowFlgRicorsiva() {
		return showFlgRicorsiva;
	}

	public void setShowFlgRicorsiva(boolean showFlgRicorsiva) {
		this.showFlgRicorsiva = showFlgRicorsiva;
	}

	public boolean isShowSelectAttributi() {
		return showSelectAttributi;
	}

	public void setShowSelectAttributi(boolean showSelectAttributi) {
		this.showSelectAttributi = showSelectAttributi;
	}

	public boolean isRequiredForDepends() {
		return requiredForDepends;
	}

	public void setRequiredForDepends(boolean requiredForDepends) {
		this.requiredForDepends = requiredForDepends;
	}

	public String getLookupType() {
		return lookupType;
	}

	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}

	public String getLookupField() {
		return lookupField;
	}

	public void setLookupField(String lookupField) {
		this.lookupField = lookupField;
	}

	public boolean isShowLookupDetail() {
		return showLookupDetail;
	}

	public void setShowLookupDetail(boolean showLookupDetail) {
		this.showLookupDetail = showLookupDetail;
	}
	
	public String getNomeTabella() {
		return nomeTabella;
	}

	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

	public boolean isShowFiltriCustom() {
		return showFiltriCustom;
	}

	public void setShowFiltriCustom(boolean showFiltriCustom) {
		this.showFiltriCustom = showFiltriCustom;
	}

	public String getDictionaryEntry() {
		return dictionaryEntry;
	}

	public void setDictionaryEntry(String dictionaryEntry) {
		this.dictionaryEntry = dictionaryEntry;
	}

	public boolean isHasFloat() {
		return hasFloat;
	}

	public void setHasFloat(boolean hasFloat) {
		this.hasFloat = hasFloat;
	}

	public String getNroDecimali() {
		return nroDecimali;
	}

	public void setNroDecimali(String nroDecimali) {
		this.nroDecimali = nroDecimali;
	}

	public String getDefaultIdNameField() {
		return defaultIdNameField;
	}

	public void setDefaultIdNameField(String defaultIdNameField) {
		this.defaultIdNameField = defaultIdNameField;
	}

	public String getDefaultDisplayNameField() {
		return defaultDisplayNameField;
	}

	public void setDefaultDisplayNameField(String defaultDisplayNameField) {
		this.defaultDisplayNameField = defaultDisplayNameField;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

}
