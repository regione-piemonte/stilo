/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.junrar.Archive;
import com.github.junrar.impl.FileVolumeManager;
import com.github.junrar.rarfile.FileHeader;

import it.eng.job.importdocindex.constants.ArchiveTypeEnum;
import it.eng.job.importdocindex.constants.ErrorInfoEnum;
import it.eng.job.importdocindex.exceptions.ImportDocIndexException;
import it.eng.job.importdocindex.manager.Manager;

/**
 * Accentra il codice per la gestione dei file compressi
 * 
 * @author massimo malvestio
 *
 */
public class DecompressionUtil {

	private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * 
	 * @param archive,
	 *            archivio da decomprimere
	 * @return la directory in cui è stato decopresso l'archivio
	 * @throws Exception
	 */
	public static void decompressArchive(File archive) throws Exception {
		decompressArchive(archive, archive.toPath());
	}

	/**
	 * Crea una directory nel percorso specificato, se non esiste già
	 * 
	 * @param newPath
	 * @throws IOException
	 */
	private static void createDirectoryIfNotExists(Path newPath) throws IOException {
		Boolean dirExists = newPath.toFile().exists();
		if (!dirExists) {
			Files.createDirectories(newPath);
		}
	}

	/**
	 * 
	 * @param archive,
	 *            archivio da decomprimere
	 * @param pathArchivioDecomp,
	 *            il percorso in cui decomprimere l'archivio
	 * @throws Exception
	 */
	public static void decompressArchive(File archive, Path directoryDestinazione) throws ImportDocIndexException {

		javax.activation.MimeType mimeType = Manager.ottieniMimeType(archive);

		switch (ArchiveTypeEnum.fromMimeType(mimeType.getBaseType())) {
		case GZIP:
			decompressioneGZip(archive, directoryDestinazione);
			break;
		case TAR:
			decompressioneTar(archive, directoryDestinazione);
			break;
		case RAR:
			decompressioneRar(archive, directoryDestinazione);
			break;
		case SEVENZIP:
			decompressione7z(archive, directoryDestinazione);
			break;
		case ZIP:
			decompressioneZip(archive, directoryDestinazione);
			break;
		default:
			throw new ImportDocIndexException("Tipo archivio non gestito: " + archive.getName(), ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}

	}

	/**
	 * Decompressione del {@link file} zip nella directory {@link destDir} <br>
	 * Se la directory non esiste viene creata
	 * 
	 * @param file
	 * @param mime
	 * @param destDir
	 * @throws Exception
	 */
	public static void decompressioneZip(File file, Path destDir) throws ImportDocIndexException {

		logger.debug("Decompressione archivio Zip {}", file.getAbsolutePath());

		try (ZipInputStream zis = new ZipInputStream(new FileInputStream(file))) {
			createDirectoryIfNotExists(destDir);
			logger.debug("Directory di destinazione: {}", destDir);
			ZipEntry zipEntry = zis.getNextEntry();
			while (zipEntry != null) {
				byte[] buffer = new byte[(int) zipEntry.getSize()];
				Path newPath = destDir.resolve(zipEntry.getName());
				if (zipEntry.isDirectory()) {
					createDirectoryIfNotExists(newPath);
				} else {
					logger.debug("Decompressione: {}", newPath);
					// in alcuni casi le entry potrebbero non essere ordinate, conviene inserire un ulteriore controllo
					createDirectoryIfNotExists(newPath.getParent());
					try (FileOutputStream fos = new FileOutputStream(newPath.toFile())) {
						int len;
						while ((len = zis.read(buffer)) > 0) {
							fos.write(buffer, 0, len);
						}
					}
				}
				zipEntry = zis.getNextEntry();
			}
			zis.closeEntry();
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}

	}

	/**
	 * Decompressione del {@link file} rar nella directory {@link destDir} <br>
	 * Se la directory non esiste viene creata
	 * 
	 * @param file
	 * @param destDir
	 * @throws Exception
	 */
	public static void decompressioneRar(File file, Path destDir) throws ImportDocIndexException {

		logger.debug("Decompressione archivio Rar {}", file.getAbsolutePath());

		try (Archive archive = new Archive(new FileVolumeManager(file));) {

			if (archive.isEncrypted()) {
				throw new ImportDocIndexException("Archivio criptato, impossibile da estrarre", ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
			}

			createDirectoryIfNotExists(destDir);
			logger.debug("Directory di destinazione: {}", destDir);

			List<FileHeader> fileHeaders = archive.getFileHeaders();
			for (FileHeader fileHeader : fileHeaders) {

				if (fileHeader.isEncrypted()) {
					throw new ImportDocIndexException("Archivio criptato, impossibile da estrarre", ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
				}

				String fileName;
				if (fileHeader.isFileHeader() && fileHeader.isUnicode()) {
					fileName = fileHeader.getFileNameW();
				} else {
					fileName = fileHeader.getFileNameString();
				}

				Path newPath = destDir.resolve(fileName);
				if (fileHeader.isDirectory()) {
					createDirectoryIfNotExists(newPath);
				} else {
					// in alcuni casi le entry potrebbero non essere ordinate, conviene inserire un ulteriore controllo
					createDirectoryIfNotExists(newPath.getParent());
					try (FileOutputStream os = new FileOutputStream(newPath.toFile())) {
						archive.extractFile(fileHeader, os);
					}
				}
			}
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}

	}

	/**
	 * Decompressione del {@link file} gzip nella directory {@link destDir} <br>
	 * Se la directory non esiste viene creata
	 * 
	 * @param file
	 * @param mime
	 * @param tempDir
	 * @throws Exception
	 */
	public static void decompressioneTar(File file, Path destDir) throws ImportDocIndexException {

		logger.debug("Decompressione archivio Tar {}", file.getAbsolutePath());

		try (TarArchiveInputStream tarIS = new TarArchiveInputStream(new FileInputStream(file))) {

			createDirectoryIfNotExists(destDir);
			logger.debug("Directory di destinazione: {}", destDir);

			TarArchiveEntry tare = null;

			while ((tare = tarIS.getNextTarEntry()) != null) {

				Path newPath = destDir.resolve(tare.getName());
				if (tare.isDirectory()) {
					createDirectoryIfNotExists(newPath);
				} else {
					// in alcuni casi le entry potrebbero non essere ordinate, conviene inserire un ulteriore controllo
					createDirectoryIfNotExists(newPath.getParent());
					try (OutputStream outputFileStream = new FileOutputStream(newPath.toFile())) {
						IOUtils.copy(tarIS, outputFileStream);
					}

				}
			}
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}

	}

	/**
	 * Decompressione del {@link file} gzip nella directory {@link destDir} <br>
	 * Se la directory non esiste viene creata
	 * 
	 * @param file
	 * @param destDir
	 * @throws Exception
	 */

	public static void decompressioneGZip(File file, Path destDir) throws ImportDocIndexException {

		logger.debug("Decompressione archivio Gzip {}", file.getAbsolutePath());

		try {
			createDirectoryIfNotExists(destDir);
			logger.debug("Directory di destinazione: {}", destDir);
		} catch (IOException e) {
			logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}

		try (GzipCompressorInputStream in = new GzipCompressorInputStream(new FileInputStream(file))) {
			Path newPath = destDir.resolve(FilenameUtils.getBaseName(file.getName()));
			IOUtils.copy(in, new FileOutputStream(newPath.toFile()));
		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}

	}

	/**
	 * Decompressione del {@link file} 7z nella directory {@link destDir} <br>
	 * Se la directory non esiste viene creata
	 * 
	 * @param file
	 * @param destDir
	 * @throws Exception
	 */

	public static void decompressione7z(File file, Path destDir) throws ImportDocIndexException {

		logger.debug("Decompressione archivio 7z {}", file.getAbsolutePath());

		try {
			createDirectoryIfNotExists(destDir);
			logger.debug("Directory di destinazione: {}", destDir);
		} catch (IOException e) {
			logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}

		try (SevenZFile sevenZFile = new SevenZFile(file)) {

			SevenZArchiveEntry entry;

			while ((entry = sevenZFile.getNextEntry()) != null) {

				Path newPath = destDir.resolve(entry.getName());
				if (entry.isDirectory()) {
					createDirectoryIfNotExists(newPath);
				} else {
					// in alcuni casi le entry potrebbero non essere ordinate, conviene inserire un ulteriore controllo
					createDirectoryIfNotExists(newPath.getParent());
					try (FileOutputStream out = new FileOutputStream(newPath.toFile())) {
						byte[] content = new byte[(int) entry.getSize()];
						sevenZFile.read(content, 0, content.length);
						out.write(content);
					}
				}
			}

		} catch (Exception e) {
			logger.error(ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE.getDescription(), e);
			throw new ImportDocIndexException(e, ErrorInfoEnum.ERRORE_DECOMPRESSIONE_FILE);
		}
	}

}
