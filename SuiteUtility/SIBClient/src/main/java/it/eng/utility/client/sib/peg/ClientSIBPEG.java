package it.eng.utility.client.sib.peg;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ws.dataservice.Capitolo;
import org.wso2.ws.dataservice.Capitolo2;
import org.wso2.ws.dataservice.DataServiceFault;
import org.wso2.ws.dataservice.DataServiceFault_Exception;
import org.wso2.ws.dataservice.GetCapitolo;
import org.wso2.ws.dataservice.GetVociPeg;
import org.wso2.ws.dataservice.GetVociPegVLiv;
import org.wso2.ws.dataservice.SibPeg11PortType;
import org.wso2.ws.dataservice.VocePeg;

import it.eng.utility.client.sib.Util;
import it.eng.utility.client.sib.peg.data.OutputGetCapitolo;
import it.eng.utility.client.sib.peg.data.OutputGetVociPeg;
import it.eng.utility.client.sib.peg.data.OutputGetVociPegVLiv;

public final class ClientSIBPEG {

	public static final String BEAN_ID = "clientSIBPEG";
	private static final Logger logger = LoggerFactory.getLogger(ClientSIBPEG.class);
	private SibPeg11PortType proxy;

	public OutputGetVociPeg getVociPeg(final GetVociPeg input) {
		final OutputGetVociPeg output = new OutputGetVociPeg();
		List<VocePeg> entries = null;
		try {
			entries = proxy.getVociPeg(input.getEntrateUscite(), input.getEsercizio(), input.getCapitolo(), input.getArticolo(), input.getNumero(),
					input.getCompetenzaPluriennale(), input.getDescrizioneCapitolo(), input.getDescrizioneDirezioneCompetente(), input.getPDCLivello1(),
					input.getMissione(), input.getProgramma(), input.getCDR(), input.getDescrizioneCDR(), input.getCAN(), input.getDescrizioneCAN(),
					input.getDisponibile(), input.getFoglia());
			output.setOk(true);
		} catch (DataServiceFault_Exception e) {
			final DataServiceFault dsfault = e.getFaultInfo();
			final String msg = "Fallita chiamata getVociPeg().";
			logger.error(msg, e);
			output.setOk(false);
			output.setMessaggio(dsfault.getNestedException());
		} catch (Exception e) {
			Util.aggiornaEsito(output, e);
			logger.error("Errore di comunicazione con SIB", e);
			return output;
		}
		output.setEntries(entries);
		return output;
	}// getVociPeg

	public OutputGetVociPegVLiv getVociPegVLiv(final GetVociPegVLiv input) {
		final OutputGetVociPegVLiv output = new OutputGetVociPegVLiv();
		List<Capitolo> entries = null;
		try {
			entries = proxy.getVociPegVLiv(input.getEntrateUscite(), input.getEsercizio(), input.getPDCLivello1(), input.getPDCLivello2(),
					input.getPDCLivello3(), input.getPDCLivello4());
			output.setOk(true);
		} catch (DataServiceFault_Exception e) {
			final DataServiceFault dsfault = e.getFaultInfo();
			final String msg = "Fallita chiamata getVociPegVLiv().";
			logger.error(msg, e);
			output.setOk(false);
			output.setMessaggio(dsfault.getNestedException());
		} catch (Exception e) {
			Util.aggiornaEsito(output, e);
			logger.error("Errore di comunicazione con SIB", e);
			return output;
		}
		output.setEntries(entries);
		return output;
	}// getVociPegVLiv

	public OutputGetCapitolo getCapitolo(final GetCapitolo input) {
		final OutputGetCapitolo output = new OutputGetCapitolo();
		List<Capitolo2> entries = null;
		try {
			entries = proxy.getCapitolo(input.getEntrateUscite(), input.getEsercizio(), input.getCDR(), input.getDescrizioneCDR());
			output.setOk(true);
		} catch (DataServiceFault_Exception e) {
			final DataServiceFault dsfault = e.getFaultInfo();
			final String msg = "Fallita chiamata getCapitolo().";
			logger.error(msg, e);
			output.setOk(false);
			output.setMessaggio(dsfault.getNestedException());
		} catch (Exception e) {
			Util.aggiornaEsito(output, e);
			logger.error("Errore di comunicazione con SIB", e);
			return output;
		}
		output.setEntries(entries);
		return output;
	}// getCapitolo

	public SibPeg11PortType getProxy() {
		return proxy;
	}

	public void setProxy(SibPeg11PortType proxy) {
		this.proxy = proxy;
	}

}// ClientSIBPEG
