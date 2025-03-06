package it.eng.dm.sira.service.filter;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import it.eng.core.business.TFilterFetch;
import it.eng.dm.sira.dao.DaoTAttributesFilter;
import it.eng.dm.sira.entity.TAttributesFilter;
import it.eng.dm.sira.entity.TAttributesFilterId;
import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.bean.FilterBeanIn;
import it.eng.dm.sira.service.bean.FilterBeanOut;
import it.eng.dm.sira.service.util.ConnectionUtil;

public class AttributesFilter {

	private static Logger log = Logger.getLogger(AttributesFilter.class);

	private static final String NO_ANNULLAMENTO = "0";

	private static final String ALL_RIGHTS = "A";

	public List<FilterBeanOut> getFilteredAttribute(FilterBeanIn in) throws Exception {
		List<FilterBeanOut> attributiFiltrati = new ArrayList<FilterBeanOut>();
		BigDecimal idProcessType = null;
		Connection connection = ConnectionUtil.getConnection();
		Statement statement = null;
		String selectTableSQL = null;
		if (in.getIdProcess() != null) {
			selectTableSQL = "select id_process_type from dmt_processes where id_process=" + in.getIdProcess();
			try {
				statement = connection.createStatement();
				System.out.println(selectTableSQL);
				// execute insert SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);
				while (rs.next()) {
					idProcessType = rs.getBigDecimal("ID_PROCESS_TYPE");
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (statement != null) {
					statement.close();
				}
				connection.close();
			}

		} else {
			log.error("DATI OBBLIGATORI MANCANTI: IDPROCESS");
			throw new SiraBusinessException("DATI OBBLIGATORI MANCANTI: IDPROCESS");
		}
		DaoTAttributesFilter daoFilter = new DaoTAttributesFilter();
		TFilterFetch<TAttributesFilter> filterFetch = new TFilterFetch<TAttributesFilter>();
		TAttributesFilter af = new TAttributesFilter();
		TAttributesFilterId id = new TAttributesFilterId();
		id.setIdProcessType(idProcessType);
		id.setFlgAnn(NO_ANNULLAMENTO);
		af.setId(id);
		af.setFlgProv(in.getProv());
		filterFetch.setFilter(af);
		List<TAttributesFilter> listaAttributiRicavati = daoFilter.search(filterFetch).getData();
		if (listaAttributiRicavati == null || listaAttributiRicavati.size() == 0) {
			List<String> attributiIn = in.getAttributesIn();
			for (String attr : attributiIn) {
				FilterBeanOut fbo = new FilterBeanOut();
				fbo.setAttrName(attr);
				fbo.setPrivilegi(ALL_RIGHTS);
				attributiFiltrati.add(fbo);
			}
		} else {
			List<String> attributiPostFilter = new ArrayList<String>();
			Map<String, String> mappaAttributi = new HashMap<String, String>();
			for (TAttributesFilter attr : listaAttributiRicavati) {
				attributiPostFilter.add(attr.getId().getAttrName());
				mappaAttributi.put(attr.getId().getAttrName(), attr.getFlgStatus());
			}
			List<String> attributiFinali = intersect(in.getAttributesIn(), attributiPostFilter);
			for (String attrName : attributiFinali) {
				FilterBeanOut fo = new FilterBeanOut();
				fo.setAttrName(attrName);
				fo.setPrivilegi(mappaAttributi.get(attrName));
				attributiFiltrati.add(fo);
			}
		}
		return attributiFiltrati;
	}

	private List<String> intersect(List<String> A, List<String> B) {
		List<String> rtnList = new ArrayList<String>();
		for (String dto : A) {
			if (B.contains(dto)) {
				rtnList.add(dto);
			}
		}
		return rtnList;
	}

}
