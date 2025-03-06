package it.eng.core.business.export;

import java.io.File;

import javax.swing.event.EventListenerList;

import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.impl.AbstractScrollableResults;

import it.eng.core.business.export.trasformer.EntityRowTraformer;
import it.eng.core.business.export.trasformer.IRowTrasformer;
import it.eng.core.business.export.utility.ExportFormat;
import it.eng.core.business.export.utility.ExportInfo;

/**
 * 
 * Estrattore dei dati
 *
 */
public class DataExtractor {

	private EventListenerList listeners;
	private int chunckSize = 100;
	private int totPages;
	// private Class<?> entityClass;
	private IRowTrasformer rowTrasformer;

	public DataExtractor() {
		// Class<?> entity
		super();
		// this.entityClass=entity;
		// this.chunckSize = chunckSize;

		listeners = new EventListenerList();
	}

	// private List<AuditInfo> forward() throws Exception{
	//
	// DaoTAuditTrail dao = new DaoTAuditTrail();
	// TFilterFetch<AuditInfo> tFilter= new TFilterFetch<AuditInfo>();
	// //tFilter.setEndRow(30);
	// tFilter.setFilter(new AuditInfo());
	// TPagingList<AuditInfo> tPaging=dao.search(tFilter);
	//
	// }

	// FIXME uso ScrollableRresults attenzione che su alcuni db il driver non lo supporta
	public void exportCriteria(Session session, Criteria criteria) throws Exception {
		ScrollableResults results = criteria.setReadOnly(true).setCacheable(false).scroll(ScrollMode.FORWARD_ONLY);
		AbstractScrollableResults abs = (AbstractScrollableResults) results;
		fireEvent(0, ExportEventType.START);
		int count = 0;
		while (results.next()) {
			// System.out.println(results.get()[0]);

			// UtilPopulate.populate(org, E, populate)
			// E entity =(E) results.get()[0];
			if (getRowTrasformer() == null) {
				setRowTrasformer(new EntityRowTraformer(Object.class));
			}
			// trasforma il riusltato della query nell'oggetto da esportare
			// le prop dell'oggetto creato saranno esportate o tutte o in parte in base
			// al mapping contenuto in exportInfo
			Object entity = getRowTrasformer().trasform(results.get());
			// make it with event
			fireEvent(entity, count, ExportEventType.DATA);
			if (count % 10 == 0) {
				fireEvent(count, ExportEventType.RUNNING);
			}
			count++;
			session.evict(entity);
		}
		fireEvent(count, ExportEventType.FINISH);
		results.close();
	}

	private void fireEvent(int recs, ExportEventType type) throws Exception {
		ExportEvent event = new ExportEvent(recs, type);
		Object[] listenersArray = listeners.getListenerList();
		for (int i = listenersArray.length - 2; i >= 0; i -= 2) {
			((ExportListener) listenersArray[i + 1]).manageEvent(event);
		}
	}

	private void fireEvent(Object record, int recs, ExportEventType type) throws Exception {
		ExportEvent event = new ExportEvent(record, recs, type);
		Object[] listenersArray = listeners.getListenerList();
		for (int i = listenersArray.length - 2; i >= 0; i -= 2) {
			((ExportListener) listenersArray[i + 1]).manageEvent(event);
		}
	}

	public void addExportListener(ExportListener listener) {
		listeners.add(ExportListener.class, listener);
	}

	public void removeExportListener(ExportListener listener) {
		listeners.remove(ExportListener.class, listener);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

	}

	public static void exportCriteria(Session session, Criteria criteria, ExportInfo info) throws Exception {
		exportCriteria(session, criteria, info, null);
	}

	public static void exportCriteria(Session session, Criteria criteria, ExportInfo info, IRowTrasformer trasformer) throws Exception {

		DataExtractor extractor = new DataExtractor();
		extractor.setRowTrasformer(trasformer);

		for (ExportFormat exportFormat : info.getExportFormat()) {
			switch (exportFormat) {
			case XML:
				ExportListener listener = new XmlExporter(info.getBaseFolder() + File.separator + info.getFileName());
				extractor.addExportListener(listener);
				break;
			case PDF:
				ExportListener pdflistener = new PDFExporter(info);
				extractor.addExportListener(pdflistener);
				break;
			default:
				throw new Exception("formato non supportato");
			}
		}
		extractor.exportCriteria(session, criteria);
	}

	public IRowTrasformer getRowTrasformer() {
		return rowTrasformer;
	}

	public void setRowTrasformer(IRowTrasformer rowTrasformer) {
		this.rowTrasformer = rowTrasformer;
	}

}
