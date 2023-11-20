/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.xml.bind.Marshaller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import it.eng.auriga.compiler.ModelliUtil;
import it.eng.auriga.database.store.dmpk_core.bean.DmpkCoreExtractverdocBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocGetdatixgendamodelloBean;
import it.eng.auriga.database.store.dmpk_modelli_doc.bean.DmpkModelliDocLoaddettmodelloBean;
import it.eng.auriga.database.store.result.bean.StoreResultBean;
import it.eng.auriga.exception.StoreException;
import it.eng.auriga.module.business.beans.AurigaLoginBean;
import it.eng.auriga.ui.module.layout.server.common.SezioneCacheAttributiDinamici;
import it.eng.auriga.ui.module.layout.server.protocollazione.datasource.bean.GeneraDaModelloBean;
import it.eng.auriga.ui.module.layout.shared.util.TipoModelloDoc;
import it.eng.client.DmpkCoreExtractverdoc;
import it.eng.client.DmpkModelliDocGetdatixgendamodello;
import it.eng.client.DmpkModelliDocLoaddettmodello;
import it.eng.jaxb.context.SingletonJAXBContext;
import it.eng.jaxb.variabili.SezioneCache;
import it.eng.jaxb.variabili.SezioneCache.Variabile;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga;
import it.eng.jaxb.variabili.SezioneCache.Variabile.Lista.Riga.Colonna;
import it.eng.utility.ui.module.core.server.bean.AdvancedCriteria;
import it.eng.utility.ui.module.core.server.bean.OrderByBean;
import it.eng.utility.ui.module.core.server.bean.PaginatorBean;
import it.eng.utility.ui.module.core.server.datasource.AbstractDataSource;
import it.eng.utility.ui.module.core.server.datasource.annotation.Datasource;
import it.eng.utility.ui.module.core.server.service.ErrorBean;
import it.eng.utility.ui.module.layout.shared.bean.FileDaFirmareBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;
import it.eng.utility.ui.user.AurigaUserUtil;

@Datasource(id = "GeneraDaModelloDataSource")
public class GeneraDaModelloDataSource extends AbstractDataSource<GeneraDaModelloBean, GeneraDaModelloBean> {

	private static Logger mLogger = Logger.getLogger(GeneraDaModelloDataSource.class);

	public GeneraDaModelloBean caricaModello(GeneraDaModelloBean bean) throws Exception {

		AurigaLoginBean loginBean = AurigaUserUtil.getLoginInfo(getSession());
		String token = loginBean.getToken();
		String idUserLavoro = loginBean.getIdUserLavoro();
		
		boolean flgConvertiInPdf = getExtraparams().get("flgConvertiInPdf") != null && "1".equals(getExtraparams().get("flgConvertiInPdf"));
		Boolean flgMostraDatiSensibili = StringUtils.isNotBlank(getExtraparams().get("flgMostraDatiSensibili")) ? new Boolean(getExtraparams().get("flgMostraDatiSensibili")) : null;
		
		DmpkModelliDocLoaddettmodelloBean input = new DmpkModelliDocLoaddettmodelloBean();
		input.setCodidconnectiontokenin(token);
		input.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
		input.setIdmodelloio(new BigDecimal(bean.getIdModello()));

		DmpkModelliDocLoaddettmodello dmpkModelliDocLoaddettmodello = new DmpkModelliDocLoaddettmodello();
		StoreResultBean<DmpkModelliDocLoaddettmodelloBean> output = dmpkModelliDocLoaddettmodello.execute(getLocale(), loginBean, input);

		GeneraDaModelloBean result = new GeneraDaModelloBean();
		result.setIdModello(output.getResultBean().getIdmodelloio() != null ? output.getResultBean().getIdmodelloio().toString() : null);

		result.setIdDocPrimario(output.getResultBean().getIddocout() != null ? output.getResultBean().getIddocout() : null);
		
		String nomeFilePrimario = output.getResultBean().getNomeout() + "."
				+ FilenameUtils.getExtension(output.getResultBean().getDisplayfilenamevereldocout());
		result.setNomeFilePrimario(normalizeFilename(nomeFilePrimario));
		
		if(StringUtils.isNotBlank(bean.getProcessId())) {
			
			if (output.getResultBean().getIddocout() != null) {
	
				SezioneCache lSezioneCache = null;
				if (bean.getValori() != null && bean.getValori().size() > 0 && bean.getTipiValori() != null && bean.getTipiValori().size() > 0) {
					lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, bean.getValori(), bean.getTipiValori(), getSession());
				} else {
					lSezioneCache = new SezioneCache();
				}

				if (StringUtils.isNotBlank(bean.getOggetto())) {
					Variabile varDesOgg = new Variabile();
					varDesOgg.setNome("#DesOgg");
					varDesOgg.setValoreSemplice(bean.getOggetto());
					lSezioneCache.getVariabile().add(varDesOgg);
				}
				
				Variabile varUoCoinvolte = new Variabile();
				varUoCoinvolte.setNome("#UOCoinvolte");
				Lista listaUoCoinvolte = new Lista();
				if (bean.getListaUoCoinvolte() != null) {
					for (int i = 0; i < bean.getListaUoCoinvolte().size(); i++) {
						Riga riga = new Riga();
						Colonna col = new Colonna();
						col.setNro(new BigInteger("1"));
						col.setContent(bean.getListaUoCoinvolte().get(i).getIdUo());
						if (col.getContent() != null && col.getContent().toUpperCase().startsWith("UO")) {
							col.setContent(col.getContent().substring(2));
						}
						riga.getColonna().add(col);
						listaUoCoinvolte.getRiga().add(riga);
					}
				}
				varUoCoinvolte.setLista(listaUoCoinvolte);
				lSezioneCache.getVariabile().add(varUoCoinvolte);
	
				String xmlSezioneCacheAttributiAdd = null;
				if (lSezioneCache != null) {
					StringWriter lStringWriter = new StringWriter();
					Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
					lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
					lMarshaller.marshal(lSezioneCache, lStringWriter);
					xmlSezioneCacheAttributiAdd = lStringWriter.toString();
				}
				
				//TODO Da togliere?  
				DmpkCoreExtractverdocBean inputDoc = new DmpkCoreExtractverdocBean();
				inputDoc.setIddocin(output.getResultBean().getIddocout());
				inputDoc.setIduserlavoroin(StringUtils.isNotBlank(idUserLavoro) ? new BigDecimal(idUserLavoro) : null);
				DmpkCoreExtractverdoc dmpkCoreExtractverdoc = new DmpkCoreExtractverdoc();
				StoreResultBean<DmpkCoreExtractverdocBean> outputDoc = dmpkCoreExtractverdoc.execute(getLocale(), loginBean, inputDoc);

				DmpkModelliDocGetdatixgendamodello lGetdatixgendamodello = new DmpkModelliDocGetdatixgendamodello();
				DmpkModelliDocGetdatixgendamodelloBean lGetdatixgendamodelloInput = new DmpkModelliDocGetdatixgendamodelloBean();
				lGetdatixgendamodelloInput.setCodidconnectiontokenin(loginBean.getToken());
				lGetdatixgendamodelloInput.setIdobjrifin(bean.getIdUd());
				lGetdatixgendamodelloInput.setFlgtpobjrifin("U");
				lGetdatixgendamodelloInput.setNomemodelloin(output.getResultBean().getNomeout());
				lGetdatixgendamodelloInput.setAttributiaddin(xmlSezioneCacheAttributiAdd);
				
				StoreResultBean<DmpkModelliDocGetdatixgendamodelloBean> lGetdatixgendamodelloOutput = lGetdatixgendamodello.execute(getLocale(), loginBean,
						lGetdatixgendamodelloInput);
				
				if (lGetdatixgendamodelloOutput.isInError()) {
					throw new StoreException(lGetdatixgendamodelloOutput);
				}
				
				String templateValues = lGetdatixgendamodelloOutput.getResultBean().getDatixmodelloxmlout();

				mLogger.debug("Dati documento da iniettare nel modello: " + templateValues);
				
				//TODO prima faceva la conversione se il parametro ATTIVA_PROPOSTA_ATTO_2 era uguale a true, ora invece controllo quello che mi arriva in flgConvertiInPdf
				if (flgConvertiInPdf) {
	
					mLogger.debug("Modello da convertire in pdf");

					FileDaFirmareBean fillTemplateBean = ModelliUtil.fillTemplate(bean.getProcessId(), bean.getIdModello(), templateValues, true, flgMostraDatiSensibili, getSession()); 					

					result.setUriFilePrimario(fillTemplateBean.getUri());
					result.setNomeFilePrimario(FilenameUtils.getBaseName(result.getNomeFilePrimario()) + ".pdf");

					MimeTypeFirmaBean infoFile = fillTemplateBean.getInfoFile();
					infoFile.setCorrectFileName(result.getNomeFilePrimario());
					result.setInfoFile(infoFile);
					
				} else {
					
					FileDaFirmareBean fillTemplateBean = ModelliUtil.fillTemplate(bean.getProcessId(), bean.getIdModello(), templateValues, false, flgMostraDatiSensibili, getSession());
					
					// Se il modello è un odt freemarker ricevo un doc, ma il modello di partenza è un odt
					if (TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equalsIgnoreCase(output.getResultBean().getTipomodelloout())) {
						result.setNomeFilePrimario(FilenameUtils.getBaseName(result.getNomeFilePrimario()) + ".doc");	
					}
					
					result.setUriFilePrimario(fillTemplateBean.getUri());

					MimeTypeFirmaBean lMimeTypeFirmaBean = fillTemplateBean.getInfoFile();
					lMimeTypeFirmaBean.setCorrectFileName(result.getNomeFilePrimario());
					result.setInfoFile(lMimeTypeFirmaBean);
					
				}
				
			}
		} else {
			
			SezioneCache lSezioneCache = null;
			if (bean.getValori() != null && bean.getValori().size() > 0 && bean.getTipiValori() != null && bean.getTipiValori().size() > 0) {
				lSezioneCache = SezioneCacheAttributiDinamici.createSezioneCacheAttributiDinamici(null, bean.getValori(), bean.getTipiValori(), getSession());
			} else {
				lSezioneCache = new SezioneCache();
			}

			String xmlSezioneCacheAttributiAdd = null;
			if (lSezioneCache != null) {
				StringWriter lStringWriter = new StringWriter();
				Marshaller lMarshaller = SingletonJAXBContext.getInstance().createMarshaller();
				lMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				lMarshaller.marshal(lSezioneCache, lStringWriter);
				xmlSezioneCacheAttributiAdd = lStringWriter.toString();
			}
			
			FileDaFirmareBean fillTemplateBean = ModelliUtil.fillTemplate("", bean.getIdModello(), xmlSezioneCacheAttributiAdd, null, flgMostraDatiSensibili, getSession());
			
			result.setUriFilePrimario(fillTemplateBean.getUri());
			
			// La conversione pdf è governata dal flag flggeneraformatopdfout 
			if (output.getResultBean().getFlggeneraformatopdfout() != null && output.getResultBean().getFlggeneraformatopdfout().intValue() == 1) {
				result.setNomeFilePrimario(FilenameUtils.getBaseName(result.getNomeFilePrimario()) + ".pdf");	
			} else if (TipoModelloDoc.ODT_CON_FREEMARKERS.getValue().equalsIgnoreCase(output.getResultBean().getTipomodelloout())) {
				result.setNomeFilePrimario(FilenameUtils.getBaseName(result.getNomeFilePrimario()) + ".doc");	
			}
			
			MimeTypeFirmaBean lMimeTypeFirmaBean = fillTemplateBean.getInfoFile();
			lMimeTypeFirmaBean.setCorrectFileName(result.getNomeFilePrimario());
			result.setInfoFile(lMimeTypeFirmaBean);
		}

		return result;

	}
	
	public String normalizeFilename(String fileName) {
		String newFileName = fileName;
		newFileName = newFileName.replace(" ", "_");
		newFileName = newFileName.replace("\\", "_");
		newFileName = newFileName.replace("/", "_");
		newFileName = newFileName.replace(":", "_");
		newFileName = newFileName.replace("*", "_");
		newFileName = newFileName.replace("?", "_");
		newFileName = newFileName.replace("<", "_");
		newFileName = newFileName.replace(">", "_");
		newFileName = newFileName.replace("|", "_");
		newFileName = newFileName.replace("\"", "_");
		return newFileName;
	}

	@Override
	public GeneraDaModelloBean get(GeneraDaModelloBean bean) throws Exception {
		
		return null;
	}

	@Override
	public GeneraDaModelloBean add(GeneraDaModelloBean bean) throws Exception {
		
		return null;
	}

	@Override
	public GeneraDaModelloBean remove(GeneraDaModelloBean bean) throws Exception {
		
		return null;
	}

	@Override
	public GeneraDaModelloBean update(GeneraDaModelloBean bean, GeneraDaModelloBean oldvalue) throws Exception {
		
		return null;
	}

	@Override
	public PaginatorBean<GeneraDaModelloBean> fetch(AdvancedCriteria criteria, Integer startRow, Integer endRow, List<OrderByBean> orderby) throws Exception {
		
		return null;
	}

	@Override
	public Map<String, ErrorBean> validate(GeneraDaModelloBean bean) throws Exception {
		
		return null;
	}

}
