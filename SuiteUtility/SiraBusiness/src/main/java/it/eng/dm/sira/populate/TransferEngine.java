package it.eng.dm.sira.populate;

import it.eng.core.business.KeyGenerator;
import it.eng.core.business.TFilterFetch;
import it.eng.dm.sira.dao.DaoAttributiOst;
import it.eng.dm.sira.entity.AttributiOst;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hyperborea.sira.ws.CostNostId;

public class TransferEngine {

	public TransferEngine() {
	}

	private static Map<String, String> mappaAttributi = new HashMap<String, String>();

	private List<String> attributiCreati = new ArrayList<String>();
	
	private static final String URL_VOCABOLARI_SIRA = "VOCABOLARI_SIRA";
	
	private AttributiOst getAttribute(List<AttributiOst> lista, BigDecimal idAttributo) {
		AttributiOst retVal = null;
		for(AttributiOst app : lista) {
			if(app.getIdAttributo() == idAttributo.longValue()) {
				retVal = app;
				break;
			}
		}
		return retVal;
	}

	public int transfer(CostNostId id, String mainLabel) throws Exception {
		AttributiCustomManager manager = new AttributiCustomManager();
		Integer categoria = id.getCodCost();
		Integer natura = id.getCodNost();
		DaoAttributiOst daoAttributi = new DaoAttributiOst();
		TFilterFetch<AttributiOst> filterFetch = new TFilterFetch<AttributiOst>();
		AttributiOst filter = new AttributiOst();
		filter.setCategoria(String.valueOf(categoria));
		filter.setNatura(String.valueOf(natura));
		filterFetch.setFilter(filter);
		List<AttributiOst> risultati = daoAttributi.search(filterFetch).getData();
		
		AttributiCustomIn fakeAci = null;
		
		Map<String, Integer> sectionCounterMap = new HashMap<String, Integer>();
		
		for (AttributiOst attributo : risultati) {
			if (!attributo.getAttributo().equals("caratterizzazioniOst")) {
				System.out.println(attributo.getAttributo());
				AttributiCustomIn aci = convert(attributo, mainLabel);
				if(attributo.getIdPadre() != null) {
					AttributiOst padre = getAttribute(risultati, attributo.getIdPadre());
					if (padre != null){
						AttributiCustomIn padreAci = convert(padre, mainLabel);
						String parentName = "";
						
						if (padre.getFlgObbl().equals("1") && padre.getFlgMultiplo().equals("1"))
							parentName = padreAci.getAttrName() + "__OL";
						else if (padre.getFlgObbl().equals("1"))
							parentName = padreAci.getAttrName() + "__O";
						else if (padre.getFlgMultiplo().equals("1"))
							parentName = padreAci.getAttrName() + "__L";
						
						if(sectionCounterMap.containsKey(parentName)) {
							sectionCounterMap.put(parentName, sectionCounterMap.get(parentName) + 1);
							
							int row = Math.round(sectionCounterMap.get(parentName) / 4) + 1;
							aci.setRowNumber("" + row);
							aci.setOrder("" + sectionCounterMap.get(parentName));
							
						} else {
							System.out.println("---------------------Setting for the first time: "+aci.getAttrName());
							
							sectionCounterMap.put(parentName, 1);
							aci.setRowNumber("1");
							aci.setOrder("1");
						}
						
						System.out.println("-----------------Attribute type:" + attributo.getTipo());
						String typeElement= getTypeAttribute(attributo);
						if (parentName.equals(mainLabel+"_CARATTERIZZAZIONIOST__O") && !typeElement.equals("COMPLEX")  ) {
							
							if (fakeAci== null){
								fakeAci= new AttributiCustomIn();
								fakeAci.setAttrName(mainLabel + "_ALTRI_DATI");
								fakeAci.setAttrType("COMPLEX");
								fakeAci.setAttrLabel("Altri Dati");									
								fakeAci.setAttrNameSup(parentName);
								fakeAci.setAttrSize(null);
								fakeAci.setAttrWidth(null);
								fakeAci.setRowNumber(null);
								fakeAci.setOrder(null);
								//fakeAci.setProvProperty("ccostElementiSignificativi");
								System.out.println("I'm going to insert a fake");
								manager.insertRecord(fakeAci);
								sectionCounterMap.put(mainLabel + "_ALTRI_DATI", 0);
								attributiCreati.add(fakeAci.getAttrName());
							}
							
							sectionCounterMap.put(mainLabel + "_ALTRI_DATI", sectionCounterMap.get(mainLabel + "_ALTRI_DATI") + 1);
							
							int row = Math.round(sectionCounterMap.get(mainLabel + "_ALTRI_DATI") / 4) + 1;
							aci.setRowNumber("" + row);
							aci.setOrder("" + sectionCounterMap.get(mainLabel + "_ALTRI_DATI"));
							aci.setAttrNameSup(mainLabel + "_ALTRI_DATI");
							
						} else{
							aci.setAttrNameSup(parentName);
						}
						

					}
				}
				if (attributiCreati.contains(aci.getAttrName())) {
					// se esiste gi l'attributo con quel nome ne creo un altro con codice univoco
					String key = KeyGenerator.gen();
					aci.setAttrName(aci.getAttrName() + "_" + key);
				} else {
					attributiCreati.add(aci.getAttrName());
				}
				
				if (attributo.getFlgObbl().equals("1") && attributo.getFlgMultiplo().equals("1")) {
					aci.setAttrName(aci.getAttrName() + "__OL");
					aci.setFlagMandatory(true);
				}
				else if (attributo.getFlgObbl().equals("1")) {
					aci.setAttrName(aci.getAttrName() + "__O");
					aci.setFlagMandatory(true);
				}
				else if (attributo.getFlgMultiplo().equals("1")) {
					aci.setAttrName(aci.getAttrName() + "__L");
				}
				
//				aci.setAttrName(aci.getAttrName());
				manager.insertRecord(aci);
				
				
			}
		}
		return risultati.size();
	}

	
	
	
	public void ToProcObjTypes(String mainLabel, String idProcObjType) throws Exception {
		ProcObjTypesManager tm = new ProcObjTypesManager();
		List<String> attributi = tm.selectRecordAttributi(mainLabel);
		tm.insertProcType(attributi, idProcObjType);
	}

	private AttributiCustomIn convert(AttributiOst in, String mainLabel) {
		AttributiCustomIn out = new AttributiCustomIn();
		out.setAttrName(mainLabel + "_" + in.getAttributo().toUpperCase());
		out.setAttrLabel(in.getLabel());
		out.setAttrSize(in.getDimensione() != null ? in.getDimensione() : "255");
	
		out.setAttrWidth(in.getDimensione() != null ? in.getDimensione() : "100");
		out.setRowNumber(null);
		out.setOrder(null);
		
		out.setAttrType(getType(in, out.getAttrName()));
		out.setProvProperty(in.getNomeComplesso().startsWith("caratterizzazioniOst.") ? in.getNomeComplesso().substring(21) : in
				.getNomeComplesso());
		if (StringUtils.isNotEmpty(in.getClasseVocabolario())) {
			out.setXmlRequestWs(in.getClasseVocabolario());
			out.setWsdlUrl(URL_VOCABOLARI_SIRA);
		}
		if (in.getIdPadre() != null) {
			
			out.setAttrNameSup(mappaAttributi.get(in.getIdPadre().toString()));
		}
		return out;
	}

	private String getType(AttributiOst in, String attrName) {
		if (in.getTipo().equals("String"))
			return TipoAttributo.TEXT_BOX.getValue();
		else {
			if (in.getTipo().equals("Float") || in.getTipo().equals("Integer"))
				return TipoAttributo.NUMBER.getValue();
			else {
				if (in.getTipo().equals("Date"))
					return TipoAttributo.DATE.getValue();
				else {
					if (in.getTipo().equals("Character"))
						return TipoAttributo.CHECK.getValue();
					else if (StringUtils.isEmpty(in.getClasseVocabolario())) {
						mappaAttributi.put(String.valueOf(in.getIdAttributo()), attrName);
						return TipoAttributo.COMPLEX.getValue();
					} else
						return TipoAttributo.COMBO_BOX.getValue();
				}
			}

		}

	}
	

	private String getTypeAttribute(AttributiOst in) {
		if (in.getTipo().equals("String"))
			return TipoAttributo.TEXT_BOX.getValue();
		else {
			if (in.getTipo().equals("Float") || in.getTipo().equals("Integer"))
				return TipoAttributo.NUMBER.getValue();
			else {
				if (in.getTipo().equals("Date"))
					return TipoAttributo.DATE.getValue();
				else {
					if (in.getTipo().equals("Character"))
						return TipoAttributo.CHECK.getValue();
					else if (StringUtils.isEmpty(in.getClasseVocabolario())) {						
						return TipoAttributo.COMPLEX.getValue();
					} else
						return TipoAttributo.COMBO_BOX.getValue();
				}
			}

		}

	}
	

}
