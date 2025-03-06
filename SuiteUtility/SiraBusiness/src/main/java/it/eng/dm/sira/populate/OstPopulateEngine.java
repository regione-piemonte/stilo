package it.eng.dm.sira.populate;

import java.sql.SQLException;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.structure.DataModelGetter;

import com.hyperborea.sira.ws.CostNost;
import com.hyperborea.sira.ws.CostNostId;
import com.hyperborea.sira.ws.WsCostNostRef;
import com.hyperborea.sira.ws.WsGetCategoriaInfoResponse;

/**
 * classe che trasporta i valori dei catasti nelle tabelle degli attributi custom e poi nella tabella associativa
 * 
 * @author jravagnan
 * 
 */
public class OstPopulateEngine {

	public boolean putValuesOst(OstPopulateEngineBeanIn in) {
		boolean ok = false;
		OstMetadataPopulation initPopulation = new OstMetadataPopulation();
		TransferEngine transEngine = new TransferEngine();
		try {
			initPopulation.populate(in.getId());
			
			int created = transEngine.transfer(in.getId(), in.getMainLabel());
			if (created > 0) {
				transEngine.ToProcObjTypes(in.getMainLabel(), in.getIdProcObjType());
			}
			ok = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	public boolean putValuesOstMandatory(OstPopulateEngineBeanIn in) {
		boolean ok = false;
		OstMetadataPopulation initPopulation = new OstMetadataPopulation();
		TransferEngine transEngine = new TransferEngine();
		try {
			initPopulation.populateMandatory(in.getId());
			
			int created = transEngine.transfer(in.getId(), in.getMainLabel());
			if (created > 0) {
				transEngine.ToProcObjTypes(in.getMainLabel(), in.getIdProcObjType());
			}
			
			ok = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	public WsCostNostRef[] getAvailableCategoriaList() {
		WsCostNostRef[] retVal = null;
		try {
			OstMetadataPopulation dataGetter = new OstMetadataPopulation();
			retVal = dataGetter.getAvailableCategoriaList();
		} catch (SiraBusinessException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public WsGetCategoriaInfoResponse getCategoriaInfo(CostNostId costNostId) {
		WsGetCategoriaInfoResponse retVal = null;
		try {
			OstMetadataPopulation dataGetter = new OstMetadataPopulation();
			retVal = dataGetter.getCategoriaInfo(costNostId);
		} catch (SiraBusinessException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public String getIdProcObjTypeByCostNost(CostNost in) {
		String retVal = null;

		try {
			ProcObjTypesManager tm = new ProcObjTypesManager();
			retVal = tm.getIdProcObjTypeByCostNost(in);
		} catch (SiraBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return retVal;
	}
	
	public boolean callDeleteAttrDynProcedure(int categoria, int natura, String etichetta, int objType) {
		boolean retVal = false;

		try {
			ProcObjTypesManager tm = new ProcObjTypesManager();
			retVal = tm.callDeleteAttrDynProcedure(categoria, natura, etichetta, objType);
		} catch (SiraBusinessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return retVal;
	}
	
	public String getIdProcObjTypeByCodCostAndNost(int codCost, int codNost) {
		String retVal = null;
		
		try {
			ProcObjTypesManager tm = new ProcObjTypesManager();
			retVal = tm.getIdProcObjTypeByCodCostAndNost(codCost, codNost);
		} catch (SiraBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	public boolean insertAltriAttributiProcType(String relazione, String idProc, String num_value, int nro_occ_value) {
		boolean retVal = false;
		
		try {
			ProcObjTypesManager tm = new ProcObjTypesManager();
			retVal = tm.insertAltriAttributiProcType(relazione, idProc, num_value, nro_occ_value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retVal;
	}

}
