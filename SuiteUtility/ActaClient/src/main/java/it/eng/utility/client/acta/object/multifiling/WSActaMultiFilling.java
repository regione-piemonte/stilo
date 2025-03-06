package it.eng.utility.client.acta.object.multifiling;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.doqui.acta.acaris.multifiling.MultifilingService;
import it.doqui.acta.acaris.multifiling.MultifilingServicePort;
import it.doqui.acta.acaris.multifiling.ObjectIdType;
import it.eng.utility.client.acta.WSActa;

public class WSActaMultiFilling extends WSActa {

	private static final Logger logger = LoggerFactory.getLogger(WSActaMultiFilling.class);

	protected String endpointMultifiling;

	public String addClassification(String repoId, String principalId, String idClassificazione, boolean addConAllegati, String idFascicoloDossier) {
		try {
			final URL url = new URL(getEndpointMultifilling());
			final MultifilingService multifilingService = new MultifilingService(url);
			MultifilingServicePort multifilingServicePort = multifilingService.getMultifilingServicePort();
			((BindingProvider) multifilingServicePort).getBinding();
            setTimeout(multifilingServicePort);

			it.doqui.acta.acaris.multifiling.ObjectIdType repo = new it.doqui.acta.acaris.multifiling.ObjectIdType();
			repo.setValue(repoId);

			it.doqui.acta.acaris.multifiling.PrincipalIdType principal = new it.doqui.acta.acaris.multifiling.PrincipalIdType();
			principal.setValue(principalId);

			it.doqui.acta.acaris.multifiling.ObjectIdType classificazioneDiPartenza = new it.doqui.acta.acaris.multifiling.ObjectIdType();
			classificazioneDiPartenza.setValue(idClassificazione);

			it.doqui.acta.acaris.multifiling.VarargsType params = new it.doqui.acta.acaris.multifiling.VarargsType();
			it.doqui.acta.acaris.multifiling.ItemType itemType = new it.doqui.acta.acaris.multifiling.ItemType();
			itemType.setKey("addConAllegati");
			itemType.setValue(String.valueOf(addConAllegati));
			params.getItems().add(itemType);

			it.doqui.acta.acaris.multifiling.ItemType itemType1 = new it.doqui.acta.acaris.multifiling.ItemType();
			itemType1.setKey("offlineAddRequest");
			itemType1.setValue(String.valueOf(false));
			params.getItems().add(itemType1);

			it.doqui.acta.acaris.multifiling.ObjectIdType folderId = new it.doqui.acta.acaris.multifiling.ObjectIdType();
			folderId.setValue(idFascicoloDossier);

			ObjectIdType response = multifilingServicePort.aggiungiClassificazione(repo, principal, classificazioneDiPartenza, folderId, params);
			if (response != null)
				return response.getValue();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (it.doqui.acta.acaris.multifiling.AcarisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getEndpointMultifilling() {
		return endpointMultifiling;
	}

	public void setEndpointMultifilling(String endpointMultifilling) {
		this.endpointMultifiling = endpointMultifilling;
	}
}
