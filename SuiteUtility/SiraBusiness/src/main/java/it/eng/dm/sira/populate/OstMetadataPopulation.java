package it.eng.dm.sira.populate;

import java.rmi.RemoteException;

import it.eng.dm.sira.exceptions.SiraBusinessException;
import it.eng.dm.sira.service.structure.DataModelGetter;

import com.hyperborea.sira.ws.CostNostId;
import com.hyperborea.sira.ws.WsCcostDescriptor;
import com.hyperborea.sira.ws.WsCostNostRef;
import com.hyperborea.sira.ws.WsGetCategoriaInfoResponse;

public class OstMetadataPopulation {
	
	public void populate(CostNostId id) throws Exception{
		DataModelGetter getter = new DataModelGetter();
		WsCcostDescriptor descriptor = getter.getDataDescriptor(id);		
		writeData(descriptor,String.valueOf(id.getCodCost()),String.valueOf(id.getCodNost()));
	}
	
	public void populateMandatory(CostNostId id) throws Exception{
		DataModelGetter getter = new DataModelGetter();
		WsCcostDescriptor descriptor = getter.getDataDescriptor(id);		
		mandatoryWriteData(descriptor,String.valueOf(id.getCodCost()),String.valueOf(id.getCodNost()));
	}
	
	private void writeData(WsCcostDescriptor in, String categoria, String natura) throws Exception{
		DataModelGetter dataModeler = new DataModelGetter();
		CostNostId id = new CostNostId();
		id.setCodCost(Integer.parseInt(categoria));
		id.setCodNost(Integer.parseInt(natura));		
		dataModeler.write(in, id, null);
	}
	
	private void mandatoryWriteData(WsCcostDescriptor in, String categoria, String natura) throws Exception {
		DataModelGetter dataModeler = new DataModelGetter();
		CostNostId id = new CostNostId();
		id.setCodCost(Integer.parseInt(categoria));
		id.setCodNost(Integer.parseInt(natura));		
		dataModeler.writePlainHierarchy(in, id, null, "", 0, null);
	}
	
	public WsCostNostRef[] getAvailableCategoriaList() throws Exception {
		DataModelGetter dataModeler = new DataModelGetter();
		return dataModeler.getAvailableCategoriaList();
	}
	
	public WsGetCategoriaInfoResponse getCategoriaInfo(CostNostId costNostId) throws Exception {
		DataModelGetter dataModeler = new DataModelGetter();
		return dataModeler.getCategoriaInfo(costNostId);
	}
	
}
