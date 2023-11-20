/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.eng.aurigamailbusiness.bean.SenderBean;
import it.eng.core.business.subject.SubjectBean;

/**
 * Classe che effettua l'invio asincrono delle mail {@link listaMailDaInviare},
 * attendendo un secondo da quando è avviato il metodo run()<br>
 * In caso di errori nell'invio effettua fino a 3 tentativi di re-invio. <br>
 * Sono valorizzati {@link listaMailInviate} e {@link listaMailNonInviate} con
 * la lista dei bean inviati e non
 *
 */

public class ThreadSend extends Send implements Runnable {

	private List<SenderBean> listaMailDaInviare;
	private List<SenderBean> listaMailNonInviate;
	private boolean invia;
	private boolean cancellaDopoInvio;

	private static Integer delayInvio = 0;
	private static Integer numeroTentativiInvio = 3;
	private static Logger mLogger = LogManager.getLogger(ThreadSend.class);

	public ThreadSend(List<SenderBean> pListSenderBean, String pStrIdAccount, SubjectBean pSubjectBean,
			boolean pBoolInvia, boolean pBoolCancellaDopoInvio) {
		super(pStrIdAccount, pSubjectBean);
		listaMailDaInviare = pListSenderBean;
		invia = pBoolInvia;
		cancellaDopoInvio = pBoolCancellaDopoInvio;
		listaMailNonInviate = new ArrayList<SenderBean>();
	}

	public void run() {
		try {
			// attendo un secondo prima di avviare l'invio della mail
			Thread.sleep(delayInvio);
		} catch (InterruptedException e) {
			mLogger.error("InterruptedException ThreadSend");
		}
		List<SenderBean> nuovaListaInvio;
		// è restituita una nuova lista di mail non inviate
		nuovaListaInvio = super.send(listaMailDaInviare, invia, cancellaDopoInvio);
		Integer tryNum = 0;
		while (!nuovaListaInvio.isEmpty() && tryNum < numeroTentativiInvio) {
			// effettuo più tentativi di invio
			nuovaListaInvio = super.send(nuovaListaInvio, invia, cancellaDopoInvio);
			tryNum++;
		}
		// al termine dei tentativi verifico se ci sono mail ancora in errore e
		// le inserisco nell'apposita lista
		listaMailNonInviate.addAll(nuovaListaInvio);
	}

	public List<SenderBean> getListaMailDaInviare() {
		return listaMailDaInviare;
	}

	public List<SenderBean> getListaMailNonInviate() {
		return listaMailNonInviate;
	}
}
