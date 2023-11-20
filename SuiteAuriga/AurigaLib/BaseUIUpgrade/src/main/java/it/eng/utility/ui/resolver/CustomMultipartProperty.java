/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.servlet.UploadServlet;

public class CustomMultipartProperty {
	private Long maxSize;
	private String tempRepository;
	private Long threshold;
	
	public Long getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Long maxSize) {
		UploadServlet.maxSize = maxSize;
		this.maxSize = maxSize;
	}
	public String getTempRepository() {
		return tempRepository;
	}
	public void setTempRepository(String tempRepository) {
		this.tempRepository = tempRepository;
	}
	public Long getThreshold() {
		return threshold;
	}
	public void setThreshold(Long threshold) {
		this.threshold = threshold;
	}
}
