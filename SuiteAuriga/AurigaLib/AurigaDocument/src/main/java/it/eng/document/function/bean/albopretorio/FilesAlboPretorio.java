/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antonio Peluso
 */

@XmlRootElement(name = "filesAlboPretorio")
@XmlAccessorType(XmlAccessType.FIELD)
public class FilesAlboPretorio implements Serializable {
	
	private static final long serialVersionUID = -8920233303552220069L;

	@XmlElement(name = "fileAlbo")
	List<FileAlbo> filesAlbo;

	public List<FileAlbo> getFilesAlbo() {
		return filesAlbo;
	}

	public void setFilesAlbo(List<FileAlbo> filesAlbo) {
		this.filesAlbo = filesAlbo;
	}

}
