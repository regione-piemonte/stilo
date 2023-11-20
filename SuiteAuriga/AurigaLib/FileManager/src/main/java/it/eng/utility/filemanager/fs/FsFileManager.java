/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import it.eng.bean.ExecutionResultBean;
import it.eng.utility.filemanager.AbstractFileManager;
import it.eng.utility.filemanager.FileBean;
import it.eng.utility.filemanager.FileManagerConfig;
import it.eng.utility.filemanager.FileManagerException;
import it.eng.utility.filemanager.FileManagerUtil;

public class FsFileManager extends AbstractFileManager {

	public FsFileManager(FileManagerConfig config) {
		super(config);
	}

	@Override
	public boolean fileExists(FileBean fileBean) throws FileManagerException {
		File file = FileUtils.getFile(fileBean.getAbsoluteParentPath(), fileBean.getName());
		return file != null ? file.exists() : false;
	}

	@Override
	public File getFile(FileBean fileBean, String destDir) throws FileManagerException {
		File inFile = FileManagerUtil.getFileFromBean(fileBean);
		File outFile = FileUtils.getFile(StringUtils.defaultString(destDir, "."), fileBean.getName());
		try {
			FileUtils.copyFile(inFile, outFile);
		} catch (IOException e) {
			throw new FileManagerException(String.format("Errore nella copia del file da %s a %s", inFile, outFile), e);
		}
		return outFile;
	}

	@Override
	public ExecutionResultBean<Map<String, Boolean>> deleteFiles(List<FileBean> listaFiles) throws FileManagerException {
		ExecutionResultBean<Map<String, Boolean>> result = new ExecutionResultBean<>();
		Map<String, Boolean> deleteResultList = new HashMap<>();
		result.setSuccessful(true);
		if (listaFiles != null) {
			for (FileBean fileBean : listaFiles) {
				File fod = FileManagerUtil.getFileFromBean(fileBean);
				boolean deleted = FileUtils.deleteQuietly(fod);
				deleteResultList.put(fod.getAbsolutePath(), deleted);
				if (!deleted)
					result.setSuccessful(false);
				result.setMessage("Uno o più file o directory non sono stati cancellati");
			}
		} else {
			result.setMessage("Nessun file o directory da cancellare");
		}

		return result;
	}

	@Override
	public boolean deleteFile(FileBean fileBean) throws FileManagerException {
		if (fileBean != null) {
			return FileUtils.deleteQuietly(FileManagerUtil.getFileFromBean(fileBean));
		} else {
			return true;
		}
	}

	@Override
	protected void refreshConfig() throws FileManagerException {
		return; // niente da aggiornare
	}

	@Override
	public boolean isDirectory(FileBean fileBean) throws FileManagerException {
		File file = FileUtils.getFile(fileBean.getAbsoluteParentPath(), fileBean.getName());
		return file != null ? file.exists() && file.isDirectory() : false;
	}

	@Override
	public boolean copyFile(FileBean fileBean, FileBean destDir) throws FileManagerException {
		if (fileBean != null && destDir != null) {
			try {
				Path pathNuovoFile = Paths.get(destDir.getAbsolutePath()).resolve(fileBean.getName());
				pathNuovoFile = Files.copy(Paths.get(fileBean.getAbsolutePath()), pathNuovoFile, StandardCopyOption.REPLACE_EXISTING);
				return this.fileExists(FileManagerUtil.createFromFile(pathNuovoFile.toFile()));
			} catch (Exception e) {
				throw new FileManagerException(e);
			}
		}
		return false;
	}

	@Override
	public boolean moveFile(FileBean fileBean, FileBean destDir) throws FileManagerException {
		if (fileBean != null && destDir != null) {
			try {
				Path pathNuovoFile = Paths.get(destDir.getAbsolutePath()).resolve(fileBean.getName());
				pathNuovoFile = Files.move(Paths.get(fileBean.getAbsolutePath()), pathNuovoFile, StandardCopyOption.REPLACE_EXISTING);
				return this.fileExists(FileManagerUtil.createFromFile(pathNuovoFile.toFile()));
			} catch (Exception e) {
				throw new FileManagerException(e);
			}
		}
		return false;
	}

	@Override
	public boolean createDirectory(Path path) throws FileManagerException {
		try {
			Boolean dirExists = path.toFile().isDirectory();
			if (!dirExists) {
				Files.createDirectories(path);
			}
			return path.toFile().isDirectory();
		} catch (IOException e) {
			throw new FileManagerException(e);
		}
	}

	@Override
	public boolean createDirectory(FileBean fileBean) throws FileManagerException {
		try {
			return createDirectory(Paths.get(fileBean.getAbsolutePath()));
		} catch (Exception e) {
			throw new FileManagerException(e);
		}
	}

	@Override
	public boolean deleteDirectory(FileBean fileBean) throws FileManagerException {
		try {
			FileUtils.deleteDirectory(FileManagerUtil.getFileFromBean(fileBean));
			return true;
		} catch (IOException e) {
			throw new FileManagerException(e);
		}
	}

	@Override
	public boolean renameFile(FileBean fileBean, FileBean newFileBean) throws FileManagerException {
		if (fileBean != null && newFileBean != null) {
			try {
				if (this.fileExists(newFileBean)) {
					throw new FileManagerException("Impossibile rinominare il file: nome già in uso");
				}
				Files.move(Paths.get(fileBean.getAbsolutePath()), Paths.get(newFileBean.getAbsolutePath()));
				return this.fileExists(newFileBean);
			} catch (Exception e) {
				throw new FileManagerException(e);
			}
		}
		return false;
	}

	@Override
	public boolean canReadFile(FileBean fileBean) throws FileManagerException {
		File file = FileUtils.getFile(fileBean.getAbsoluteParentPath(), fileBean.getName());
		return file != null ? file.exists() && Files.isReadable(file.toPath()) : false;
	}

	@Override
	public boolean canWriteFile(FileBean fileBean) throws FileManagerException {
		File file = FileUtils.getFile(fileBean.getAbsoluteParentPath(), fileBean.getName());
		return file != null ? file.exists() && Files.isWritable(file.toPath()) : false;
	}

}
