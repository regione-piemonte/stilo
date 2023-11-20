/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.job.importdocindex.constants.FilePostManageActionEnum;
import it.eng.utility.filemanager.FileManagerConfig;

/**
 * Configurazioni directory da scansionare per ricerca file indice
 * 
 * @author Mattia Zanetti
 *
 */

@XmlRootElement
public class ConfigurazioniDirectoryScansione {

	/**
	 * Valore di default scansione ricorsiva sotto-directory
	 */

	public static final Boolean DEFAULT_SCANSIONE_SOTTO_DIRECTORY = false;

	/**
	 * Nessun limite per la scansione sottodirectory
	 */

	public static final Integer NESSUN_LIMITE_SCANSIONE_SOTTODIRECTORY = -1;

	/**
	 * Valore di default livello ricorsione scansione directory
	 */

	public static final Integer DEFAULT_LIVELLO_MASSIMO_SCANSIONE_SOTTODIRECTORY = NESSUN_LIMITE_SCANSIONE_SOTTODIRECTORY;

	/**
	 * Valore di default de-compressione
	 */

	public static final boolean DEFAULT_ABILITA_DECOMPRESSIONE = true;

	public static final FilePostManageActionEnum DEFAULT_AZIONE_FILE_ELABORATO = FilePostManageActionEnum.KEEP;

	/**
	 * Percorso radice da cui iniziare la scansione
	 */

	private String directoryRadice;

	/**
	 * Directory di lavoro
	 */

	private String directoryLavoro;

	/**
	 * Directory di backup
	 */

	private String directoryBackup;

	/**
	 * Directory di errore
	 */

	private String directoryErrori;

	/**
	 * Directory finale per i file elaborati
	 */

	private String directoryElaborati;

	/**
	 * Abilita la scansione delle sotto-directory (ricorsiva)
	 */

	private Boolean abilitaScansioneSottoDirectory = DEFAULT_SCANSIONE_SOTTO_DIRECTORY;

	/**
	 * Livello massimo per la scansione delle sotto-directory<br>
	 * se -1 nessun limite, se 0 solo la directory principale
	 */

	private Integer livelloMassimoScansioneSottoDirectory = DEFAULT_LIVELLO_MASSIMO_SCANSIONE_SOTTODIRECTORY;

	/**
	 * Configurazioni tipologia File Manager
	 */

	private FileManagerConfig configurazioniFileManager;

	/**
	 * Abilitazione della de-compressione
	 */

	private Boolean abilitaDecompressione = DEFAULT_ABILITA_DECOMPRESSIONE;

	/**
	 * Azione da svolgere sul file elaborato
	 */

	private FilePostManageActionEnum azioneFile = DEFAULT_AZIONE_FILE_ELABORATO;

	public String getDirectoryRadice() {
		return directoryRadice;
	}

	public void setDirectoryRadice(String directoryRadice) {
		this.directoryRadice = directoryRadice;
	}

	public String getDirectoryLavoro() {
		return directoryLavoro;
	}

	public void setDirectoryLavoro(String directoryLavoro) {
		this.directoryLavoro = directoryLavoro;
	}

	public String getDirectoryBackup() {
		return directoryBackup;
	}

	public void setDirectoryBackup(String directoryBackup) {
		this.directoryBackup = directoryBackup;
	}

	public String getDirectoryErrori() {
		return directoryErrori;
	}

	public void setDirectoryErrori(String directoryErrori) {
		this.directoryErrori = directoryErrori;
	}

	public String getDirectoryElaborati() {
		return directoryElaborati;
	}

	public void setDirectoryElaborati(String directoryElaborati) {
		this.directoryElaborati = directoryElaborati;
	}

	public Boolean getAbilitaScansioneSottoDirectory() {
		return abilitaScansioneSottoDirectory;
	}

	public void setAbilitaScansioneSottoDirectory(Boolean abilitaScansioneSottoDirectory) {
		this.abilitaScansioneSottoDirectory = abilitaScansioneSottoDirectory;
	}

	public Integer getLivelloMassimoScansioneSottoDirectory() {
		return livelloMassimoScansioneSottoDirectory;
	}

	public void setLivelloMassimoScansioneSottoDirectory(Integer livelloMassimoScansioneSottoDirectory) {
		this.livelloMassimoScansioneSottoDirectory = livelloMassimoScansioneSottoDirectory;
	}

	public FileManagerConfig getConfigurazioniFileManager() {
		return configurazioniFileManager;
	}

	public void setConfigurazioniFileManager(FileManagerConfig configurazioniFileManager) {
		this.configurazioniFileManager = configurazioniFileManager;
	}

	public Boolean getAbilitaDecompressione() {
		return abilitaDecompressione;
	}

	public void setAbilitaDecompressione(Boolean abilitaDecompressione) {
		this.abilitaDecompressione = abilitaDecompressione;
	}

	public FilePostManageActionEnum getAzioneFile() {
		return azioneFile;
	}

	public void setAzioneFile(FilePostManageActionEnum azioneFile) {
		this.azioneFile = azioneFile;
	}

}
