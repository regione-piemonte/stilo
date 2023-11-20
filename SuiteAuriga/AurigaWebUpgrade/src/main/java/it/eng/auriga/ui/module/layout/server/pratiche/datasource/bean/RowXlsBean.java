/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

public class RowXlsBean {

private String rowId;	

private List<CellXlsBean> listaCellXls;

public String getRowId() {
	return rowId;
}

public void setRowId(String rowId) {
	this.rowId = rowId;
}

public List<CellXlsBean> getListaCellXls() {
	return listaCellXls;
}

public void setListaCellXls(List<CellXlsBean> listaCellXls) {
	this.listaCellXls = listaCellXls;
}

}