/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;

import it.eng.auriga.ui.module.layout.client.pratiche.dettaglio.TaskFlussoInterface;

public class CustomTaskButton extends FrontendButton {
	
	private TaskFlussoInterface taskDetail = null;

	public CustomTaskButton(String title) {
		super(title);
	}
	
	public CustomTaskButton(String title, String icon) {
		super(title, icon);
	}
	
	public boolean isToShow(Record recordEvento) {
		return true;	
	}

	public TaskFlussoInterface getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(TaskFlussoInterface taskDetail) {
		this.taskDetail = taskDetail;
	}
	
}
