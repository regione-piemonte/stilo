/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class LoadAttrDinamiciOutputBean {

	private Integer flgMostraAltriAttr;
	private List<AttributoBean> attributiAdd;
	
	public Integer getFlgMostraAltriAttr() {
		return flgMostraAltriAttr;
	}
	public void setFlgMostraAltriAttr(Integer flgMostraAltriAttr) {
		this.flgMostraAltriAttr = flgMostraAltriAttr;
	}
	public List<AttributoBean> getAttributiAdd() {
		return attributiAdd;
	}
	public void setAttributiAdd(List<AttributoBean> attributiAdd) {
		this.attributiAdd = attributiAdd;
	}
	
}
