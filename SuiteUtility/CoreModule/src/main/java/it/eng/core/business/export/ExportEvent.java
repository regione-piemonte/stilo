package it.eng.core.business.export;


/**
 * evento che raccoglie lo stato dell'export
 * per l'entity E
 * 
 *
 */
public class ExportEvent  {
	 
	private ExportEventType type;//type of event 
	private int numRec;//numero di record corrente che si sta esportando
	private Object record;//oggeto che rappresenta i record che stai esportando
	
	public ExportEvent(  int numRec,ExportEventType type) {
		super();
		 
		this.numRec = numRec;
		this.type=type;
	}
	
	public ExportEvent(Object record, int numRec,ExportEventType type) {
		super();
		this.record = record;
		 
		this.numRec = numRec;
		this.type=type;
	}
	 

	public Object getRecord() {
		return record;
	}

	public void setRecord(Object record) {
		this.record = record;
	}

	 
	public int getNumRec() {
		return numRec;
	}
	public void setNumRec(int numRec) {
		this.numRec = numRec;
	}
	public ExportEventType getType() {
		return type;
	}
	public void setType(ExportEventType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ExportEvent [  type=" + type
				+ ", numRec=" + numRec + "]";
	}
	
}

  
