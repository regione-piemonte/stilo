/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciInputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributiDinamiciOutputBean;
import it.eng.auriga.ui.module.layout.server.attributiDinamici.datasource.bean.AttributoBean;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.bean.FieldBean;
import it.eng.utility.ui.module.layout.server.common.ListFieldDataSource;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

@Datasource(id="AttributiDinamiciListFieldDataSource")
public class AttributiDinamiciListFieldDataSource extends ListFieldDataSource { 

	@Override
	public PaginatorBean<FieldBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		PaginatorBean<FieldBean> lPaginatorBean = new PaginatorBean<FieldBean>();
				
		List<FieldBean> lList = super.fetch(criteria, startRow, endRow, orderby).getData();		
		
		AttributiDinamiciInputBean input = new AttributiDinamiciInputBean();
		input.setNomeTabella(getExtraparams().get("nomeTabella"));
		input.setTipoEntita(getExtraparams().get("tipoEntita"));
		AttributiDinamiciDatasource attributiDS = new AttributiDinamiciDatasource();
		attributiDS.setSession(getSession());
		AttributiDinamiciOutputBean output = attributiDS.call(input);
				
		if(output != null && output.getAttributiAdd() != null) {
			for (AttributoBean attr : output.getAttributiAdd()){
				if(!"LISTA".equals(attr.getTipo())) {
					FieldBean field = new FieldBean();
					field.setName(attr.getNome());
					if(StringUtils.isNotBlank(attr.getLabelRiquadro())) {
						String labelRiquadro = attr.getLabelRiquadro();
						if(labelRiquadro.startsWith("*")) {
							labelRiquadro = labelRiquadro.substring(1);
						}
						field.setTitle(labelRiquadro + " - " + attr.getLabel());
					} else {
						field.setTitle(attr.getLabel());
					}
					if("DATE".equals(attr.getTipo())) {
						field.setType("data_senza_ora");							
					} else if("DATETIME".equals(attr.getTipo())) {				
						field.setType("data_e_ora");
					} else if("TEXT".equals(attr.getTipo())) {
						field.setType("stringa_ricerca_estesa_case_insensitive_1");
					} else if("TEXT-AREA".equals(attr.getTipo())) {
						field.setType("stringa_ricerca_estesa_case_insensitive_1");				
					} else if("CHECK".equals(attr.getTipo())) {
						field.setType("check");
					} else if("INTEGER".equals(attr.getTipo())) {
						field.setType("numero");
					} else if("EURO".equals(attr.getTipo())) {
						field.setType("numero");
					} else if("DECIMAL".equals(attr.getTipo())) {
						field.setType("numero");
					} else if("COMBO-BOX".equals(attr.getTipo())) {						
						field.setType("lista_scelta");
					}	
					
					field.setType("stringa_ricerca_estesa_case_insensitive_1");
					lList.add(field);
				}
			}
		}
		
		lPaginatorBean.setData(lList);
		lPaginatorBean.setEndRow(lList.size());
		lPaginatorBean.setStartRow(0);
		lPaginatorBean.setTotalRows(lList.size());
		return lPaginatorBean;
	}

}
