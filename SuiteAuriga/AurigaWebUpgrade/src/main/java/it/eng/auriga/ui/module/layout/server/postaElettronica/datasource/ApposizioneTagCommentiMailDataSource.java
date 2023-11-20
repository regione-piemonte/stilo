/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_int_mgo_email.bean.DmpkIntMgoEmailTagemailBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ApposizioneTagCommentiMailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.ItemTagCommentiMailBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.PostaElettronicaBean;
import it.eng.auriga.ui.module.layout.server.postaElettronica.datasource.bean.TagNoteXmlBean;
import it.eng.auriga.ui.module.layout.server.pratiche.datasource.bean.SimpleValueBean;
import it.eng.client.DmpkIntMgoEmailTagemail;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.module.core.server.datasource.AbstractServiceDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id="ApposizioneTagCommentiMailDataSource")
public class ApposizioneTagCommentiMailDataSource extends AbstractServiceDataSource<ApposizioneTagCommentiMailBean, ApposizioneTagCommentiMailBean> {

	private static Logger mLogger = Logger.getLogger(ApposizioneTagCommentiMailDataSource.class);
	
	@Override
	public ApposizioneTagCommentiMailBean call(ApposizioneTagCommentiMailBean pInBean) throws Exception {
		
		return new ApposizioneTagCommentiMailBean();
	}
	
	public ApposizioneTagCommentiMailBean saveTagCommenti(ApposizioneTagCommentiMailBean pInBean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());

		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		HashMap<String, String> errorMessages = null;
		
		DmpkIntMgoEmailTagemailBean input = new DmpkIntMgoEmailTagemailBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setListaidemailin(getListaIdEmail(pInBean.getListaEmail()));
		input.setXmltagin(getNote(pInBean.getItemTagCommentiMail()));
 
		DmpkIntMgoEmailTagemail dmpkIntMgoEmailTagemail = new DmpkIntMgoEmailTagemail();
		StoreResultBean<DmpkIntMgoEmailTagemailBean> result = dmpkIntMgoEmailTagemail.execute(getLocale(), loginBean, input);
		if (StringUtils.isNotBlank(result.getDefaultMessage())) {
			if (result.isInError()) {
				mLogger.error("Errore nel recupero dell'output: " + result.getDefaultMessage());
				throw new StoreException(result);
			} else {
				addMessage(result.getDefaultMessage(), "", MessageType.WARNING);
			}
		}
		
		if(result.getResultBean().getEsitiout() != null && !"".equals(result.getResultBean().getEsitiout())){
			errorMessages = new HashMap<String, String>();
			StringReader sr = new StringReader(result.getResultBean().getEsitiout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);

			for (int i = 0; i < lista.getRiga().size(); i++) {
				Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));

				if (v.get(3).equalsIgnoreCase("ko") || v.get(3).equalsIgnoreCase("KO")) {
					errorMessages.put(v.get(2), v.get(4));
				}
			}
			pInBean.setErrorMessages(errorMessages);
		}
		
		return pInBean;
	}
	
	private String getListaIdEmail(List<PostaElettronicaBean> pInBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException {
		
		String idEmails = null;
		List<SimpleValueBean> listaIdEmail = new ArrayList<SimpleValueBean>();
		for(PostaElettronicaBean item : pInBean){
			SimpleValueBean lSimpleValueBean = new SimpleValueBean();
			lSimpleValueBean.setValue(item.getIdEmail());
			listaIdEmail.add(lSimpleValueBean);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		idEmails = lXmlUtilitySerializer.bindXmlList(listaIdEmail);
		
		return idEmails;
	}
	
	private String getNote(List<ItemTagCommentiMailBean> pInBean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JAXBException{
		
		String note = null;
		List<TagNoteXmlBean> listaNoteTag = new ArrayList<TagNoteXmlBean>();
		for(ItemTagCommentiMailBean item : pInBean){
			TagNoteXmlBean lTagNoteXmlBean = new TagNoteXmlBean();
			lTagNoteXmlBean.setTag(item.getItemLavTag());
			lTagNoteXmlBean.setNote(item.getItemLavCommento());
			lTagNoteXmlBean.setFlgInibitaModificaCancellazione(item.getFlgInibitaModificaCancellazione() != null && 
					item.getFlgInibitaModificaCancellazione() ? 1 : 0);
			listaNoteTag.add(lTagNoteXmlBean);
		}
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		note = lXmlUtilitySerializer.bindXmlList(listaNoteTag);
		
		return note;
	}
	
}
