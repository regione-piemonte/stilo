/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationNotificaBean;
import it.eng.auriga.database.store.dmpk_collaboration.bean.DmpkCollaborationTrovanotificheosservazioniBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.osservazioniNotifiche.datasource.bean.DestinatariOsservazioniXmlBean;
import it.eng.auriga.ui.module.layout.server.osservazioniNotifiche.datasource.bean.OsservazioniNotificheBean;
import it.eng.client.DmpkCollaborationNotifica;
import it.eng.client.DmpkCollaborationTrovanotificheosservazioni;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.Lista;
import it.eng.utility.XmlUtility;
import it.eng.utility.ui.html.HtmlNormalizeUtil;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractFetchDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.server.StringSplitterServer;
import it.eng.utility.ui.user.AurigaUserUtil;
import it.eng.xml.XmlUtilitySerializer;

@Datasource(id = "AurigaOsservazioniNotificheDataSource")
public class AurigaOsservazioniNotificheDataSource extends AbstractFetchDataSource<OsservazioniNotificheBean> {
	
	private static final Logger log = Logger.getLogger(AurigaOsservazioniNotificheDataSource.class);

	@Override
	public PaginatorBean<OsservazioniNotificheBean> fetch(AdvancedCriteria criteria,
			Integer startRow, Integer endRow, List<OrderByBean> orderby)
			throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String idUdFolder = getExtraparams().get("idUdFolder");
		String flgUdFolder = getExtraparams().get("flgUdFolder");
				
		DmpkCollaborationTrovanotificheosservazioniBean input = new DmpkCollaborationTrovanotificheosservazioniBean();
	    input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtypeobjrifin(flgUdFolder);
		input.setIdobjrifin(idUdFolder != null && !"".equals(idUdFolder) ? new BigDecimal(idUdFolder) : null);
		input.setFlgsenzapaginazionein(1);
		
		DmpkCollaborationTrovanotificheosservazioni dmpkCollaborationTrovanotificheosservazioni = new DmpkCollaborationTrovanotificheosservazioni();
		StoreResultBean<DmpkCollaborationTrovanotificheosservazioniBean> output = dmpkCollaborationTrovanotificheosservazioni.execute(getLocale(), loginBean, input);
			
		List<OsservazioniNotificheBean> listaOperazioni = new ArrayList<OsservazioniNotificheBean>();					
		if(output.getResultBean().getListaxmlout() != null) {
			StringReader sr = new StringReader(output.getResultBean().getListaxmlout());
			Lista lista = (Lista) SingletonJAXBContext.getInstance().createUnmarshaller().unmarshal(sr);			
			if(lista != null) {
				for (int i = 0; i < lista.getRiga().size(); i++) {
					Vector<String> v = new XmlUtility().getValoriRiga(lista.getRiga().get(i));																									       		

					/**
					 *-- 1: Id. osservazione/notifica
					 *-- 2: Timestamp notifica (formato data e ora dato dal parametro FMT_STD_TIMESTAMP)
					 *-- 3: Nominativo del mittente
					 *-- 4: Nominativo del delegato che ha inviato per il mittente (A nome di)
					 *-- 5: Livello di priorità (per colonna iconica a GUI con le icone con i punti esclamativi)
					 *-- 6: Messaggio
					 *-- 7: Estremi dei destinatari
					 *-- 8: (flag 1/0) evidenza se il messaggio è destinato personalmente a me
					 *-- 9: (flag 1/0) evidenza se il messaggio è destinato SOLO a me
					 */
		       		OsservazioniNotificheBean osservazioneNotifica = new OsservazioniNotificheBean();
		       		osservazioneNotifica.setIdOsservazioneNotifica(v.get(0));
		       		osservazioneNotifica.setDataOsservazioneNotifica(v.get(1) != null ? new SimpleDateFormat(FMT_STD_TIMESTAMP).parse(v.get(1)) : null);
		       		osservazioneNotifica.setMittenteOsservazioneNotifica(v.get(2));
		       		osservazioneNotifica.setNominativoDelegato(v.get(3));
		       		osservazioneNotifica.setLivelloPriorita(v.get(4));
		       		
		       		if(v.get(5) != null && !"".equals(v.get(5))){
		       			osservazioneNotifica.setMessaggioOsservazioneNotificaHTML(v.get(5));
				       	String msgNormalize = HtmlNormalizeUtil.getPlainXmlWithCarriageReturn(v.get(5));
				       	osservazioneNotifica.setMessaggioOsservazioneNotifica(msgNormalize);
		       		}
		       		
		       		osservazioneNotifica.setDestinatariOsservazioneNotifica(v.get(6));
		       		osservazioneNotifica.setFlgPersonaleOsservazioneNotifica(v.get(7) != null && "1".equals(v.get(7)) ? true : false);
		       		osservazioneNotifica.setFlgEsclusivoOsservazioneNotifica(v.get(8) != null && "1".equals(v.get(8)) ? true : false);
     					       	
		       		listaOperazioni.add(osservazioneNotifica);		        		
			   	}							
			}								
		}
		
		//OsservazioniNotificheBean osservazioneNotifica = new OsservazioniNotificheBean();
		//osservazioneNotifica.setIdOsservazioneNotifica("1");
   		//osservazioneNotifica.setDataOsservazioneNotifica(new Date());
   		//osservazioneNotifica.setMittenteOsservazioneNotifica("daniele");
		//osservazioneNotifica.setNominativoDelegato("dc");
		//osservazioneNotifica.setLivelloPriorita("0");
		//osservazioneNotifica.setMessaggioOsservazioneNotifica("123456789");
		//osservazioneNotifica.setDestinatariOsservazioneNotifica("test");
		//osservazioneNotifica.setFlgPersonaleOsservazioneNotifica(true);
		//osservazioneNotifica.setFlgEsclusivoOsservazioneNotifica(true);
   		
		//OsservazioniNotificheBean osservazioneNotifica2 = new OsservazioniNotificheBean();
		//osservazioneNotifica2.setIdOsservazioneNotifica("2");
		//osservazioneNotifica2.setDataOsservazioneNotifica(new Date());
		//osservazioneNotifica2.setMittenteOsservazioneNotifica("cristiano");
		//osservazioneNotifica2.setNominativoDelegato("aa");
		//osservazioneNotifica2.setLivelloPriorita("-1");
		//osservazioneNotifica2.setMessaggioOsservazioneNotifica("12345678911111111111111111111111111111111111111111111111111111111111111\n1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123456789111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111123456789111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
		//osservazioneNotifica2.setDestinatariOsservazioneNotifica("test");
		//osservazioneNotifica2.setFlgPersonaleOsservazioneNotifica(false);
		//osservazioneNotifica2.setFlgEsclusivoOsservazioneNotifica(true);
   		
   		//listaOperazioni.add(osservazioneNotifica);
   		//listaOperazioni.add(osservazioneNotifica2);
		
		PaginatorBean<OsservazioniNotificheBean> lPaginatorBean = new PaginatorBean<OsservazioniNotificheBean>();
		lPaginatorBean.setData(listaOperazioni);
		lPaginatorBean.setStartRow(startRow);
		lPaginatorBean.setEndRow(endRow == null ? listaOperazioni.size() - 1 : endRow);
		lPaginatorBean.setTotalRows(listaOperazioni.size());
		
		return lPaginatorBean;		
	}	
	
	@Override
	public OsservazioniNotificheBean get(OsservazioniNotificheBean bean) throws Exception {		
		return null;
	}
	
	@Override
	public OsservazioniNotificheBean remove(OsservazioniNotificheBean bean)
	throws Exception {
		return null;
	}
	
	@Override
	public OsservazioniNotificheBean add(OsservazioniNotificheBean bean) throws Exception {
		
		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		String idUdFolder = getExtraparams().get("idUdFolder");
		String flgUdFolder = getExtraparams().get("flgUdFolder");
		
		DmpkCollaborationNotificaBean input = new DmpkCollaborationNotificaBean();
		input.setIduserlavoroin(idUserLavoro != null && !"".equals(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setFlgtypeobjtonotifin(flgUdFolder);
		input.setIdobjtonotifin(idUdFolder != null && !"".equals(idUdFolder) ? new BigDecimal(idUdFolder) : null);
		input.setRecipientsxmlin(getDestinatariOsservazioni(bean));
		input.setLivelloprioritain(bean.getLivelloPriorita() != null && !"".equals(bean.getLivelloPriorita()) ?
				new BigDecimal(bean.getLivelloPriorita()) : null);
		input.setMessaggionotifin(bean.getMessaggioOsservazioneNotifica());
		
		DmpkCollaborationNotifica dmpkCollaborationNotificaBean = new DmpkCollaborationNotifica();
		StoreResultBean<DmpkCollaborationNotificaBean> output = dmpkCollaborationNotificaBean.execute(getLocale(), loginBean, input);
		
		if (StringUtils.isNotBlank(output.getResultBean().getWarningmsgout())) {
			addMessage(output.getResultBean().getWarningmsgout(), "", MessageType.WARNING);
		}

		if (StringUtils.isNotBlank(output.getDefaultMessage())) {
			if (output.isInError()) {
				log.error("Errore nel recupero dell'output: " + output.getDefaultMessage());
				throw new StoreException(output);
			} else {
				addMessage(output.getDefaultMessage(), "", MessageType.WARNING);
			}
		} 
		
		return bean;
	}
	
	public String getDestinatariOsservazioni(OsservazioniNotificheBean bean) throws Exception {
		
		XmlUtilitySerializer lXmlUtilitySerializer = new XmlUtilitySerializer();
		List<DestinatariOsservazioniXmlBean> listaDestOss = new ArrayList<DestinatariOsservazioniXmlBean>();
		if(bean.getDestinatariOsservazioneNotifica() != null && !"".equals(bean.getDestinatariOsservazioneNotifica())){
			String [] destOssNtf = bean.getDestinatariOsservazioneNotifica().split(",");
			for(int i=0; i < destOssNtf.length; i++){
				DestinatariOsservazioniXmlBean destinatariOsservazioniXmlBean = new DestinatariOsservazioniXmlBean();
				StringSplitterServer st = new StringSplitterServer(destOssNtf[i], "|*|");
				int n = 0;
				while (st.hasMoreTokens()) {
					if (n == 0) {
						destinatariOsservazioniXmlBean.setTipoDestinatario(st.nextToken().toString());
					} else if (n == 1) {
						destinatariOsservazioniXmlBean.setIdDestinatario(st.nextToken().toString());
					} 
					n++;
				}
				destinatariOsservazioniXmlBean.setTipoNotifica("OSS");
				destinatariOsservazioniXmlBean.setFlgModalitaAccesso("V");
				listaDestOss.add(destinatariOsservazioniXmlBean);
			}
		}
		
		return lXmlUtilitySerializer.bindXmlList(listaDestOss);
	}
	
	@Override
	public OsservazioniNotificheBean update(OsservazioniNotificheBean bean,
			OsservazioniNotificheBean oldvalue) throws Exception {
		return bean;
	}

	@Override
	public Map<String, ErrorBean> validate(OsservazioniNotificheBean bean)
	throws Exception {
		return null;
	}

}