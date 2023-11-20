/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class FileManagerUtil {

	private FileManagerUtil() {
	}

	public static FileBean createFromFile(File file) {
		FileBean fileBean = null;
		if (file != null) {
			fileBean = new FileBean();
			fileBean.setAbsoluteParentPath(file.getParent());
			fileBean.setAbsolutePath(file.getAbsolutePath());
			fileBean.setName(file.getName());
		}

		return fileBean;
	}

	public static FileBean createFromString(String path, String filename) {
		FileBean fileBean = new FileBean();
		String lPath = StringUtils.defaultString(path, ".");
		fileBean.setAbsolutePath(lPath + File.separator + filename);
		fileBean.setAbsoluteParentPath(lPath);
		if (StringUtils.isNotBlank(filename)) {
			fileBean.setName(filename);
		}

		return fileBean;
	}

	public static File getFileFromBean(FileBean fileBean) {
		if (fileBean != null) {
			return FileUtils.getFile(fileBean.getAbsoluteParentPath(), fileBean.getName());
		} else {
			return null;
		}
	}

}
