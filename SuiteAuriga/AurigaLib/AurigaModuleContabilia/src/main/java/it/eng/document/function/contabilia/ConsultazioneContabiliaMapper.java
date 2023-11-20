/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

 
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import it.csi.siac.ricerche.svc._1.RicercaAccertamentoResponse;
import it.csi.siac.ricerche.svc._1.RicercaImpegnoResponse;
import it.csi.siac.stilo.svc._1.RicercaMovimentoGestioneStiloResponse;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiRequest;
import it.eng.document.function.bean.ContabiliaElaboraAttiAmministrativiResponse;
import it.eng.document.function.bean.ContabiliaOutputElaboraAttiAmministrativi;
import it.eng.document.function.bean.ContabiliaOutputRicercaAccertamento;
import it.eng.document.function.bean.ContabiliaOutputRicercaImpegno;
import it.eng.document.function.bean.ContabiliaOutputRicercaMovimentoGestione;
import it.eng.document.function.bean.ContabiliaRicercaAccertamentoRequest;
import it.eng.document.function.bean.ContabiliaRicercaAccertamentoResponse;
import it.eng.document.function.bean.ContabiliaRicercaImpegnoRequest;
import it.eng.document.function.bean.ContabiliaRicercaImpegnoResponse;
import it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloRequest;
import it.eng.document.function.bean.ContabiliaRicercaMovimentoGestioneStiloResponse;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiRequest;
import it.eng.utility.client.contabilia.documenti.data.ElaboraAttiAmministrativiResponse;
import it.eng.utility.client.contabilia.documenti.data.OutputElaboraAttiAmministrativi;
import it.eng.utility.client.contabilia.movimenti.data.OutputRicercaMovimentoGestione;
import it.eng.utility.client.contabilia.movimenti.data.RicercaMovimentoGestioneStiloRequest;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaAccertamento;
import it.eng.utility.client.contabilia.ricerche.data.OutputRicercaImpegno;
import it.eng.utility.client.contabilia.ricerche.data.RicercaAccertamentoRequest;
import it.eng.utility.client.contabilia.ricerche.data.RicercaImpegnoRequest;

//@Mapper /* (componentModel = "spring") */
public interface ConsultazioneContabiliaMapper {
	
	ConsultazioneContabiliaMapper INSTANCE = Mappers.getMapper(ConsultazioneContabiliaMapper.class);
	
	/*
	 * Mapping da bean Auriga a bean client WS per ricerca accertamento (parametri in input)
	 */
	RicercaAccertamentoRequest fromContabiliaRicercaAccertamento(ContabiliaRicercaAccertamentoRequest source);
	
	/*
	 * Mapping da bean Auriga a bean client WS per ricerca impegno (parametri in input)
	 */
	RicercaImpegnoRequest fromContabiliaRicercaImpegno(ContabiliaRicercaImpegnoRequest source);
	
	/*
	 * Mapping da bean Auriga a bean client WS per ricerca movimento gestione (parametri in input)
	 */
	RicercaMovimentoGestioneStiloRequest fromContabiliaRicercaMovimentoGestione(ContabiliaRicercaMovimentoGestioneStiloRequest source);
	
	/*
	 * Mapping da bean Auriga a bean client WS per elabora atti amministrativi (parametri in input)
	 */
	ElaboraAttiAmministrativiRequest fromContabiliaElaboraAttiAmministrativi(ContabiliaElaboraAttiAmministrativiRequest source);
	
	/*
	 * Mapping da bean client WS a bean Auriga per ricerca accertamento (output chiamata servizio)
	 */
	@Mappings({ @Mapping(target = "entry", qualifiedByName = "entryToContabiliaRicercaAccertamentoResponse") })
	ContabiliaOutputRicercaAccertamento outputRicercaAccertamentoToContabiliaOutputRicercaAccertamento(OutputRicercaAccertamento source);
	
	/*
	 * Mapping response client WS in bean Auriga per ricerca accertamento
	 */
	@Named("entryToContabiliaRicercaAccertamentoResponse")
	ContabiliaRicercaAccertamentoResponse entryToContabiliaRicercaAccertamentoResponse(RicercaAccertamentoResponse source);
	
	/*
	 * Mapping da bean client WS a bean Auriga per ricerca impegno (output chiamata servizio)
	 */
	@Mappings({ @Mapping(target = "entry", qualifiedByName = "entryToContabiliaRicercaImpegnoResponse") })
	ContabiliaOutputRicercaImpegno outputRicercaImpegnoToContabiliaOutputRicercaImpegno(OutputRicercaImpegno source);
	
	/*
	 * Mapping response client WS in bean Auriga per ricerca impegno
	 */
	@Named("entryToContabiliaRicercaImpegnoResponse")
	ContabiliaRicercaImpegnoResponse entryToContabiliaRicercaImpegnoResponse(RicercaImpegnoResponse source);
	
	/*
	 * Mapping da bean client WS a bean Auriga per ricerca movimento gestione (output chiamata servizio)
	 */
	@Mappings({ @Mapping(target = "entry", qualifiedByName = "entryToContabiliaRicercaMovimentoGestioneStiloResponse") })
	ContabiliaOutputRicercaMovimentoGestione outputRicercaMovimentoGestioneToContabiliaOutputRicercaMovimentoGestione(OutputRicercaMovimentoGestione source);
	
	/*
	 * Mapping response client WS in bean Auriga per ricerca movimento gestione
	 */
	@Named("entryToContabiliaRicercaMovimentoGestioneStiloResponse")
	ContabiliaRicercaMovimentoGestioneStiloResponse entryToContabiliaRicercaMovimentoGestioneStiloResponse(RicercaMovimentoGestioneStiloResponse source);
	
	/*
	 * Mapping da bean client WS a bean Auriga per elabora atti amministrativi (output chiamata servizio)
	 */
	@Mappings({ @Mapping(target = "entry", qualifiedByName = "entryToContabiliaElaboraAttiAmministrativiResponse") })
	ContabiliaOutputElaboraAttiAmministrativi outputElaboraAttiAmministrativiToContabiliaOutputElaboraAttiAmministrativi(OutputElaboraAttiAmministrativi source);
	
	/*
	 * Mapping response client WS in bean Auriga per ricerca impegno
	 */
	@Named("entryToContabiliaElaboraAttiAmministrativiResponse")
	ContabiliaElaboraAttiAmministrativiResponse entryToContabiliaElaboraAttiAmministrativiResponse(ElaboraAttiAmministrativiResponse source);
	
}
