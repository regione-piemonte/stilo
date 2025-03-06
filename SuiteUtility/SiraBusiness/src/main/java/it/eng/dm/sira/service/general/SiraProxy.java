package it.eng.dm.sira.service.general;

import java.rmi.RemoteException;

import com.hyperborea.sira.ws.CcostAnySearchComand;
import com.hyperborea.sira.ws.OggettiStruttureTerritoriali;
import com.hyperborea.sira.ws.WsOstRef;
import com.hyperborea.sira.ws.WsPage;

public abstract class SiraProxy {

	public abstract Long countOst(CcostAnySearchComand arg0) throws RemoteException;

	public abstract WsPage searchOst(CcostAnySearchComand arg0, Integer arg1, Integer arg2) throws RemoteException;
	
	public abstract OggettiStruttureTerritoriali viewOst(WsOstRef arg0) throws java.rmi.RemoteException;

	public abstract void setEndpoint(String endpoint);

}
