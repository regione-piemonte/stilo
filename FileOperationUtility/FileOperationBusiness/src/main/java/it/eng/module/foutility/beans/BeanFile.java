/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Attachment;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class BeanFile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2953277412859846983L;
	@XmlTransient
	@Attachment
	private File beanFile;
	private String name;
	
	public File getBeanFile() {
		return beanFile;
	}
	public void setBeanFile(File beanFile) {
		this.beanFile = beanFile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
