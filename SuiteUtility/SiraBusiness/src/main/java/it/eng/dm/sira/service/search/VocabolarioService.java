package it.eng.dm.sira.service.search;

import it.eng.dm.sira.service.SiraService;
import it.eng.dm.sira.service.bean.VocabolarioBeanIn;
import it.eng.dm.sira.service.bean.VocabolarioBeanOut;
import it.eng.spring.utility.SpringAppContext;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyperborea.sira.ws.VocId;
import com.hyperborea.sira.ws.VocabularyDescription;
import com.hyperborea.sira.ws.VocabularyItem;
import com.hyperborea.sira.ws.WsGetVocabularyDescriptionByVocClassnameRequest;
import com.hyperborea.sira.ws.WsGetVocabularyDescriptionRequest;
import com.hyperborea.sira.ws.WsGetVocabularyDescriptionResponse;

import org.apache.log4j.Logger;

public class VocabolarioService {

	private static Logger log = Logger.getLogger(VocabolarioService.class);
	
	public VocabolarioBeanOut getVociVocabolario(VocabolarioBeanIn in) throws RemoteException {
		//SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		VocabolarioBeanOut out = new VocabolarioBeanOut();
		List<VocabularyItem> voices = new ArrayList<VocabularyItem>();
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		WsGetVocabularyDescriptionRequest request = new WsGetVocabularyDescriptionRequest();
		VocId id = new VocId();
		id.setClassName(in.getClassName());
		id.setFieldName(in.getFieldName());
		request.setVocId(id);
		WsGetVocabularyDescriptionResponse response = service.getCatastiProxy().getVocabularyDescription(request);
		if (response != null) {
			VocabularyDescription desc = response.getVocabularyDescription();
			if (desc != null) {
				VocabularyItem[] voci = desc.getItems();
				for (VocabularyItem item : voci) {
					voices.add(item);
				}
			}
		}
		out.setVociVocabolario(voices);
		return out;
	}
	
	public VocabolarioBeanOut getVociVocabolarioByClassName(VocabolarioBeanIn in) throws RemoteException {
//		SpringAppContext.setContext(new ClassPathXmlApplicationContext("SiraBusiness.xml"));
		VocabolarioBeanOut out = new VocabolarioBeanOut();
		String descriptionProperty = "";
		String vocProperty = "";
		String idProperty = "";
		List<VocabularyItem> voices = new ArrayList<VocabularyItem>();
		SiraService service = (SiraService) SpringAppContext.getContext().getBean("siraService");
		if (service.getProxyManager().needProxy()) {
			service.getProxySetter().initProxy();
		}
		WsGetVocabularyDescriptionByVocClassnameRequest request = new WsGetVocabularyDescriptionByVocClassnameRequest();
		request.setVocClassName(in.getClassName());
		
		log.info("\n\n#######################\n\n" + request.toString() + "\n\n#######################\n\n");
		
		WsGetVocabularyDescriptionResponse response = service.getCatastiProxy().getVocabularyDescriptionByVocClassname(request);
		if (response != null) {
			VocabularyDescription desc = response.getVocabularyDescription();
			if (desc != null) {
				
				descriptionProperty = desc.getDescriptionProperty();
				vocProperty = desc.getVocProperty();
				idProperty = desc.getIdProperty();
				
				VocabularyItem[] voci = desc.getItems();
				for (VocabularyItem item : voci) {
					voices.add(item);
				}
			}
		}
		out.setClassName(in.getClassName());
		out.setDescriptionProperty(descriptionProperty);
		out.setVocProperty(vocProperty);
		out.setIdProperty(idProperty);
		out.setVociVocabolario(voices);
		return out;
	}

}
