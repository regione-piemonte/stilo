/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class DatiExcelBean {

	private List<RowXlsBean> listaDatiXls;
	private List<DatiXlsInErrorBean> listaDatiXlsInError;
	
	public List<RowXlsBean> getListaDatiXls() {
		return listaDatiXls;
	}
	public void setListaDatiXls(List<RowXlsBean> listaDatiXls) {
		this.listaDatiXls = listaDatiXls;
	}
	public List<DatiXlsInErrorBean> getListaDatiXlsInError() {
		return listaDatiXlsInError;
	}
	public void setListaDatiXlsInError(List<DatiXlsInErrorBean> listaDatiXlsInError) {
		this.listaDatiXlsInError = listaDatiXlsInError;
	}
	
	
}



