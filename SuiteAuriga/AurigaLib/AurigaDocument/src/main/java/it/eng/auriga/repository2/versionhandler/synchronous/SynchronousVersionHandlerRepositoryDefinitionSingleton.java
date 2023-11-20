/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.sql.Types;

import org.apache.log4j.Logger;

import eng.storefunction.StoreParameter;
import eng.storefunction.StoreProcedure;
import eng.storefunction.StoreProcedureFactory;
import eng.storefunction.parameters.AutoCommitParameter;
import eng.storefunction.parameters.ErrorCodeParameter;
import eng.storefunction.parameters.ErrorContextParameter;
import eng.storefunction.parameters.ErrorMessageParameter;
import eng.storefunction.parameters.FullRollbackParameter;
import eng.storefunction.parameters.ReturnCodeParameter;
import eng.storefunction.parameters.TxtClobParameter;

/**
 * L'implementazione del Singleton segue i consigli dell'articolo http://radio.weblogs.com/0122027/stories/2003/10/20/implementingTheSingletonPatternInJava.html
 * con un po' di modifiche per implementare direttamente l'interfaccia che ne espone i servizi (StoreProcedureFactory).
 */
public class SynchronousVersionHandlerRepositoryDefinitionSingleton implements StoreProcedureFactory {

	private static Logger aLogger = Logger.getLogger(SynchronousVersionHandlerRepositoryDefinitionSingleton.class.getName());
	/**
	 * A handle to the unique Singleton instance.
	 */
	private static SynchronousVersionHandlerRepositoryDefinitionSingleton _instance = new SynchronousVersionHandlerRepositoryDefinitionSingleton();

	/**
	 * @return The unique instance of this class.
	 */
	public synchronized static StoreProcedureFactory getInstance() {
		if (null == _instance) {
			_instance = new SynchronousVersionHandlerRepositoryDefinitionSingleton();
		}
		return _instance;
	}

	/**
	 * Questo metodo crea una nuova istanza di StoreProcedure replicando un'istanza statica. La StoreProcedure ritornata è thread safe.
	 */
	public StoreProcedure getStoreProcedure(String procedureName) {
		StoreProcedure sp = (StoreProcedure) storeProcedures.get(procedureName);
		if (sp != null)
			return sp.cloneMe();
		return null;
	}

	/**
	 * Mappa delle storeprocedure. E' inutile che sia statica, tanto c'e' una sola istanza della classe e la mappa viene creata solo una volta quando il
	 * Singleton viene creato.
	 */
	protected java.util.HashMap storeProcedures = null;

	public java.util.HashMap getStoreProcedures() {
		return storeProcedures;
	}

	/**
	 * The constructor could be made private to prevent others from instantiating this class. But this would also make it impossible to create instances of
	 * Singleton subclasses.
	 */
	protected SynchronousVersionHandlerRepositoryDefinitionSingleton() {

		try {
			addStoreProcedures();

			/** Fine dichiarazione stores... */
		} catch (Exception ex) {
			aLogger.fatal("Exception in StoreProcedureDefinitionSingleton." + ex.getMessage() + ".", ex);
		}
	}

	protected void addStoreProcedures() {
		storeProcedures = new java.util.HashMap();

		// 06/06/2006 nuove funzioni....

		// ************************************ LOGIN **********************************
		storeProcedures.put("Login", new StoreProcedure("DMPK_LOGIN.Login", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "UsernameIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(3, "PasswordIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgTpDominioAutIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(5, "IdDominioAutIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(6, "CodApplicazioneEstIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "CodIstanzaApplEstIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "CodIdConnectionTokenOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "DesUserOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(10, "DesDominioOut", Types.VARCHAR, StoreParameter.OUT), new FullRollbackParameter(11, "0"),
				new AutoCommitParameter(12, "0"), new ErrorContextParameter(13), new ErrorCodeParameter(14), new ErrorMessageParameter(15) }));

		// ********************************** ADD-DOC ********************************************

		storeProcedures.put("AddDoc", new StoreProcedure("DMPK_CORE.AddDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(4, "AttributiUDDocXMLIn", null, StoreParameter.IN), new StoreParameter(5, "IdUDOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "IdDocOut", Types.VARCHAR, StoreParameter.OUT), new TxtClobParameter(7, "URIXmlOut", null, StoreParameter.OUT),
				new FullRollbackParameter(8, "0"), new AutoCommitParameter(9, "0"), new ErrorContextParameter(10), new ErrorCodeParameter(11),
				new ErrorMessageParameter(12) }));

		// ************************************* CtrlConnectionToken *******************************
		storeProcedures.put("CtrlConnectionToken", new StoreProcedure("DMPK_UTILITY.CtrlConnectionToken", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(4, "FlgTpDominioAutOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(5, "IdDominioAutOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "CodApplEsternaOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(7, "CodIstanzaApplEstOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));

		// ************************************* AddVerDoc *******************************
		storeProcedures.put("AddVerDoc", new StoreProcedure("DMPK_CORE.AddVerDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(5, "AttributiVerXMLIn", null, StoreParameter.IN),
				new StoreParameter(6, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT), new FullRollbackParameter(7, "0"),
				new AutoCommitParameter(8, "0"), new ErrorContextParameter(9), new ErrorCodeParameter(10), new ErrorMessageParameter(11) }));

		// ************************************* GetContainerForAddVer *******************************
		storeProcedures.put("GetContainerForAddVer", new StoreProcedure("DMPK_CORE.GetContainerForAddVer", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "ContainerHandlerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(4, "ContainerAliasOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(5), new ErrorCodeParameter(6),
				new ErrorMessageParameter(7) }));

		// *********************************** LOCK ****************************************
		storeProcedures.put("LockDoc", new StoreProcedure("DMPK_CORE.LockDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NroProgrLastVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "URIVerOut", Types.VARCHAR, StoreParameter.OUT), new FullRollbackParameter(7, "0"), new AutoCommitParameter(8, "0"),
				new ErrorContextParameter(9), new ErrorCodeParameter(10), new ErrorMessageParameter(11) }));

		// ********************************** EXTRACT FILE ********************************************
		storeProcedures.put("GetVersionDoc", new StoreProcedure("DMPK_CORE.ExtractVerDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(6, "DisplayFilenameVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(7, "URIVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(8, "ImprontaVerOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(9), new ErrorCodeParameter(10),
				new ErrorMessageParameter(11) }));

		// ********************************** EXTRACT FILE SENZA TOKEN *********************************
		storeProcedures.put("GetVersionDocFreeExtract", new StoreProcedure("DMPK_CORE.ExtractVerDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(4, "DisplayFilenameVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(5, "URIVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "ImprontaVerOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(7), new ErrorCodeParameter(8),
				new ErrorMessageParameter(9) }));

		// ********************************** UNLOCK ********************************************
		storeProcedures.put("UnlockDoc", new StoreProcedure("DMPK_CORE.UnlockDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "URIVerOut", Types.VARCHAR, StoreParameter.OUT), new FullRollbackParameter(6, "0"), new AutoCommitParameter(7, "0"),
				new ErrorContextParameter(8), new ErrorCodeParameter(9), new ErrorMessageParameter(10) }));

		// ***************************** preleva info x container da profilo *********************
		storeProcedures.put("GetProfiloDocXContainer", new StoreProcedure("DMPK_CORE.GetProfiloDocXContainer", new StoreParameter[] {
				new ReturnCodeParameter(1), new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "ContainerTypeIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "ContainerAliasIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgProfNotNeededIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "UsernameOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "PasswordOut", Types.VARCHAR, StoreParameter.OUT), new TxtClobParameter(10, "ProfiloXMLOUT", null, StoreParameter.OUT),
				new TxtClobParameter(11, "AttributiVerIn", null, StoreParameter.IN), new ErrorContextParameter(12), new ErrorCodeParameter(13),
				new ErrorMessageParameter(14) }));

		// ********************************** UpdDocUD ********************************************
		storeProcedures.put("UpdDocUD", new StoreProcedure("DMPK_CORE.UpdDocUD", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgTipoTargetIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "IdUDDocIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(6, "AttributiUDDocXMLIn", null, StoreParameter.IN),
				new TxtClobParameter(7, "URIXmlOut", null, StoreParameter.OUT), new FullRollbackParameter(8, "0"), new AutoCommitParameter(9, "0"),
				new ErrorContextParameter(10), new ErrorCodeParameter(11), new ErrorMessageParameter(12) }));

		// ********************************** UpdVerDoc ********************************************
		storeProcedures.put("UpdVerDoc", new StoreProcedure("DMPK_CORE.UpdVerDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new TxtClobParameter(6, "AttributiUDDocXMLIn", null, StoreParameter.IN), new TxtClobParameter(7, "URIXmlOut", null, StoreParameter.OUT),
				new FullRollbackParameter(8, "0"), new AutoCommitParameter(9, "0"), new ErrorContextParameter(10), new ErrorCodeParameter(11),
				new ErrorMessageParameter(12) }));

		// ********************************** UpdVerDoc ********************************************
		storeProcedures.put("CheckOutDoc", new StoreProcedure("DMPK_CORE.CheckOutDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NroProgrVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "DisplayFilenameVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(7, "URIVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(8, "ImprontaVerOut", Types.VARCHAR, StoreParameter.OUT), new FullRollbackParameter(9, "0"),
				new AutoCommitParameter(10, "0"), new ErrorContextParameter(11), new ErrorCodeParameter(12), new ErrorMessageParameter(13) }));

		// ********************************** AddFolder ********************************************
		storeProcedures.put("AddFolder", new StoreProcedure("DMPK_CORE.AddFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(4, "AttributiXMLIn", null, StoreParameter.IN),
				new StoreParameter(5, "IdFolderOut", Types.VARCHAR, StoreParameter.OUT), new FullRollbackParameter(6, "0"), new AutoCommitParameter(7, "0"),
				new ErrorContextParameter(8), new ErrorCodeParameter(9), new ErrorMessageParameter(10) }));

		// ********************************** AddFolder ********************************************
		storeProcedures.put("AddWorkspace", new StoreProcedure("DMPK_CORE.AddWorkspace", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(4, "AttributiXMLIn", null, StoreParameter.IN),
				new StoreParameter(5, "IdWorkspaceOut", Types.VARCHAR, StoreParameter.OUT), new FullRollbackParameter(6, "0"), new AutoCommitParameter(7, "0"),
				new ErrorContextParameter(8), new ErrorCodeParameter(9), new ErrorMessageParameter(10) }));

		// ********************************** GetURIVerDoc ********************************************
		storeProcedures.put("GetURIVerDoc", new StoreProcedure("DMPK_CORE.GetURIVerDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(6, "URIVerOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(7), new ErrorCodeParameter(8),
				new ErrorMessageParameter(9) }));

		// ********************************** GetNroLastVerDoc ********************************************
		storeProcedures.put("GetNroLastVerDoc", new StoreProcedure("DMPK_CORE.GetNroLastVerDoc", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "IdDocIn", Types.VARCHAR, StoreParameter.IN), }));

		// ********************************** LockUD ********************************************
		storeProcedures.put("LockUD", new StoreProcedure("DMPK_CORE.LockUD", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdUDIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(5, "DocUDXMLOut", null, StoreParameter.OUT), new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));
		// ********************************** UnlockUD ********************************************
		storeProcedures.put("UnlockUD", new StoreProcedure("DMPK_CORE.UnlockUD", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdUDIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(5, "DocUDXMLOut", null, StoreParameter.OUT), new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));
		// ********************************** AddContainerOperation ********************************************
		storeProcedures.put("AddContainerOperation", new StoreProcedure("DMPK_CORE.AddContainerOperation", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "ContainerTypeIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "ContainerAliasIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgTipoTargetIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "IdTargetIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NroProgrVerIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "CodOperationTyIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "FlgSuccessIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(9, "MessaggioErrIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(10, "NoteIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(11, "FlgOperDaNonRiprovareIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(12, "IdOperationOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(13, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(14, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(15), new ErrorCodeParameter(16),
				new ErrorMessageParameter(17) }));
		// ********************************** Del_UD_Doc_Ver ********************************************
		storeProcedures.put("Del_UD_Doc_Ver", new StoreProcedure("DMPK_CORE.Del_UD_Doc_Ver", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgTipoTargetIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "IdUDDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(7, "FlgCancFisicaIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(8, "URIXmlOut", null, StoreParameter.OUT),
				new StoreParameter(9, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(10, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(11), new ErrorCodeParameter(12),
				new ErrorMessageParameter(13) }));

		// ********************************** Recover_UD_Doc_Ver ********************************************
		storeProcedures.put("Recover_UD_Doc_Ver", new StoreProcedure("DMPK_CORE.Recover_UD_Doc_Ver", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgTipoTargetIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "IdUDDocIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT), new TxtClobParameter(7, "URIXmlOut", null, StoreParameter.OUT),
				new StoreParameter(8, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(9, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(10), new ErrorCodeParameter(11),
				new ErrorMessageParameter(12) }));

		// ***************************** preleva info x container da profilo (folder) *****************
		storeProcedures.put("GetProfiloFolderXContainer", new StoreProcedure("DMPK_CORE.GetProfiloFolderXContainer", new StoreParameter[] {
				new ReturnCodeParameter(1), new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "ContainerTypeIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "ContainerAliasIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "IdFolderIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgProfNotNeededIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "UsernameOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "PasswordOut", Types.VARCHAR, StoreParameter.OUT), new TxtClobParameter(10, "ProfiloXMLOUT", null, StoreParameter.OUT),
				new ErrorContextParameter(11), new ErrorCodeParameter(12), new ErrorMessageParameter(13) }));

		// ***************************** preleva info x container da profilo (folder) *****************
		storeProcedures.put("GetProfiloWorkspaceXContainer", new StoreProcedure("DMPK_CORE.GetProfiloWorkspaceXContainer", new StoreParameter[] {
				new ReturnCodeParameter(1), new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "ContainerTypeIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "ContainerAliasIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "IdWorkspaceIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgProfNotNeededIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "UsernameOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "PasswordOut", Types.VARCHAR, StoreParameter.OUT), new TxtClobParameter(10, "ProfiloXMLOUT", null, StoreParameter.OUT),
				new ErrorContextParameter(11), new ErrorCodeParameter(12), new ErrorMessageParameter(13) }));

		// ***************************** add folder *****************************************
		storeProcedures.put("AddFolder", new StoreProcedure("DMPK_CORE.AddFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(4, "AttributiXMLIn", null, StoreParameter.IN),
				new StoreParameter(5, "IdFolderOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));

		// ***************************** upd folder *****************************************
		storeProcedures.put("UpdFolder", new StoreProcedure("DMPK_CORE.UpdFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdFolderIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "IdLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NomeLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FolderPathIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(8, "AttributiXMLIn", null, StoreParameter.IN),
				new StoreParameter(9, "URIxAggContainerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(10, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(11, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(12), new ErrorCodeParameter(13),
				new ErrorMessageParameter(14) }));

		// ***************************** upd folder *****************************************
		storeProcedures.put("UpdWorkspace", new StoreProcedure("DMPK_CORE.UpdWorkspace", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdWorkspaceIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NomeWorkspaceIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(6, "AttributiXMLIn", null, StoreParameter.IN),
				new StoreParameter(7, "URIxAggContainerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(8, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(9, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(10), new ErrorCodeParameter(11),
				new ErrorMessageParameter(12) }));

		// ************** container in cui mappare un folder appena creato *******************
		storeProcedures.put("GetContainerForNewFolder", new StoreProcedure("DMPK_CORE.GetContainerForNewFolder", new StoreParameter[] {
				new ReturnCodeParameter(1), new StoreParameter(2, "IdFolderIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "ContainerTypeIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(4, "ContainerAliasIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(5, "ContainerTypeToLogOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "ContainerAliasToLogOut", Types.VARCHAR, StoreParameter.OUT), }));

		// ************************** delete di folder ***************************************
		storeProcedures.put("DelFolder", new StoreProcedure("DMPK_CORE.DelFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdFolderIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "IdLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NomeLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FolderPathIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "FlgCancFisicaIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(9, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(10, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(11, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(12), new ErrorCodeParameter(13),
				new ErrorMessageParameter(14) }));

		// ************************** delete di folder ***************************************
		storeProcedures.put("RecoverFolder", new StoreProcedure("DMPK_CORE.RecoverFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdFolderIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "IdLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NomeLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FolderPathIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(8, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(10, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(11), new ErrorCodeParameter(12),
				new ErrorMessageParameter(13) }));

		// ************************** delete di ws ***************************************
		storeProcedures.put("DelWorkspace", new StoreProcedure("DMPK_CORE.DelWorkspace", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdWorkspaceIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NomeWorkspaceIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "FlgCancFisicaIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(7, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(8, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(9, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(10), new ErrorCodeParameter(11),
				new ErrorMessageParameter(12) }));

		// ************************** delete di ws ***************************************
		storeProcedures.put("RecoverWorkspace", new StoreProcedure("DMPK_CORE.RecoverWorkspace", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdWorkspaceIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NomeWorkspaceIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(6, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(9), new ErrorCodeParameter(10),
				new ErrorMessageParameter(11) }));

		// ************************** lock di folder ******************************************
		storeProcedures.put("LockFolder", new StoreProcedure("DMPK_CORE.LockFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdFolderIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));

		// ************************** unlock di folder ****************************************
		storeProcedures.put("UnlockFolder", new StoreProcedure("DMPK_CORE.UnlockFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdFolderIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));

		// ************************** lock di workspace ******************************************
		storeProcedures.put("LockWorkspace", new StoreProcedure("DMPK_CORE.LockWorkspace", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdWorkspaceIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));

		// ************************** unlock di Workspace ****************************************
		storeProcedures.put("UnlockWorkspace", new StoreProcedure("DMPK_CORE.UnlockWorkspace", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdWorkspaceIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "URIOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(8), new ErrorCodeParameter(9),
				new ErrorMessageParameter(10) }));

		// ***************************** LOGIN CON CREDENZIALI ESTERNE ****************************
		storeProcedures.put("LoginConCredenzialiEsterne", new StoreProcedure("DMPK_LOGIN.LoginConCredenzialiEsterne", new StoreParameter[] {
				new ReturnCodeParameter(1), new StoreParameter(2, "CodApplicazioneIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "CodIstanzaApplIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "UsernameIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "PasswordIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "CodIdConnectionTokenOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(7, "DesUserOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(8, "IdDominioOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "DesDominioOut", Types.VARCHAR, StoreParameter.OUT), new FullRollbackParameter(10, "0"),
				new AutoCommitParameter(11, "0"), new ErrorContextParameter(12), new ErrorCodeParameter(13), new ErrorMessageParameter(14) }));

		// ************************************* TOGLI DA *****************************************
		storeProcedures.put("TogliDa", new StoreProcedure("DMPK_CORE.TogliDa", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgTpObjInnerIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "IdObjInnerIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NomeObjInnerIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgTpObjOuterIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "IdObjOuterIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(9, "PathNomeObjOuterIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(10, "IdLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(11, "NomeLibraryIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(12, "URIXmlOut", null, StoreParameter.OUT),
				new StoreParameter(13, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(14, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(15), new ErrorCodeParameter(16),
				new ErrorMessageParameter(17) }));

		// ************************************* MOVE *****************************************
		storeProcedures.put("Move", new StoreProcedure("DMPK_CORE.Move", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgTpObjInnerIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "IdObjInnerIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NomeObjInnerIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgTpObjFromIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "IdObjFromIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(9, "PathNomeObjFromIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(10, "FlgTpObjToIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(11, "IdObjToIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(12, "PathNomeObjToIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(13, "IdLibraryIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(14, "NomeLibraryIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(15, "URIXmlOut", null, StoreParameter.OUT),
				new StoreParameter(16, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(17, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(18), new ErrorCodeParameter(19),
				new ErrorMessageParameter(20) }));
		// ************************************* ESTRAZIONE VER MODELLO *****************************
		storeProcedures.put("ExtractVerModello", new StoreProcedure("DMPK_MODELLI_DOC.ExtractVerModello", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdModelloIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "NomeModelloIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "NroProgrVerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(7, "IdDocOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(8, "DisplayFilenameVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "URIVerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(10, "ImprontaVerOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(11), new ErrorCodeParameter(12),
				new ErrorMessageParameter(13) }));

		// ************************************* RICERCA NEL REPOSITORY *****************************
		storeProcedures.put("TrovaRepositoryObj", new StoreProcedure("DMPK_CORE.TrovaRepositoryObj", new StoreParameter[] {
				new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgPreimpostaFiltroIn", Types.INTEGER, StoreParameter.IN),
				new TxtClobParameter(5, "MatchByIndexerIn", null, StoreParameter.IN),
				new StoreParameter(6, "FlgIndexerOverflowIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(7, "Flg2ndCallIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(8, "FlgFmtEstremiRegIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(9, "FlgUDFolderIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(10, "CercaInFolderIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(11, "FlgCercaInSubFolderIO", Types.INTEGER, StoreParameter.IN_OUT),
				new TxtClobParameter(12, "CriteriAvanzatiIO", null, StoreParameter.IN_OUT),
				new TxtClobParameter(13, "CriteriPersonalizzatiIO", null, StoreParameter.IN_OUT),
				new StoreParameter(14, "ColOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(15, "FlgDescOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(16, "FlgSenzaPaginazioneIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(17, "NroPaginaIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(18, "BachSizeIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(19, "OverFlowLimitIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(20, "FlgSenzaTotIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(21, "NroTotRecOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(22, "NroRecInPaginaOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(23, "FlgBatchSearchIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(24, "ColToReturnIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(25, "ResultOut", null, StoreParameter.OUT),
				new TxtClobParameter(26, "PercorsoRicercaXMLIO", null, StoreParameter.IN_OUT),
				new TxtClobParameter(27, "DettagliCercaInFolderOut", null, StoreParameter.OUT), new ErrorContextParameter(28), new ErrorCodeParameter(29),
				new ErrorMessageParameter(30), new TxtClobParameter(31, "FinalitaIn", null, StoreParameter.IN) }));

		// ************************************* RICERCA NELLE BOZZE *****************************
		storeProcedures.put("TrovaUDInBozze", new StoreProcedure("DMPK_CORE.TrovaUDInBozze", new StoreParameter[] {
				new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgPreimpostaFiltroIn", Types.INTEGER, StoreParameter.IN),
				new TxtClobParameter(5, "MatchByIndexerIn", null, StoreParameter.IN),
				new StoreParameter(6, "FlgIndexerOverflowIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(7, "Flg2ndCallIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(8, "FlgFmtEstremiRegIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new TxtClobParameter(9, "CriteriAvanzatiIO", null, StoreParameter.IN_OUT),
				new TxtClobParameter(10, "CriteriPersonalizzatiIO", null, StoreParameter.IN_OUT),
				new StoreParameter(11, "ColOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(12, "FlgDescOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(13, "FlgSenzaPaginazioneIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(14, "NroPaginaIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(15, "BachSizeIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(16, "OverFlowLimitIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(17, "FlgSenzaTotIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(18, "NroTotRecOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(19, "NroRecInPaginaOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(20, "FlgBatchSearchIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(21, "ColToReturnIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(22, "ResultOut", null, StoreParameter.OUT),
				new ErrorContextParameter(23), new ErrorCodeParameter(24), new ErrorMessageParameter(25) }));

		// ************************************* RICERCA NELLE BOZZE *****************************
		storeProcedures.put("GetConnSchema", new StoreProcedure("DMPK_UTILITY.GetConnSchema", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "SchemaOut", Types.VARCHAR, StoreParameter.OUT), }));

		// ************************************* RIMUOVI DA AREA LAVORO *****************************
		storeProcedures.put("RemoveFromSezAreaLav", new StoreProcedure("DMPK_CORE.RemoveFromSezAreaLav", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "CISezioneAreaLavIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "FlgTipoObjIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(6, "IdObjIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "MotiviIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(8, "URIXmlOut", null, StoreParameter.OUT),
				new StoreParameter(9, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(10, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(11), new ErrorCodeParameter(12),
				new ErrorMessageParameter(13) }));

		// ************************************* RIPRISTINA IN AREA LAVORO *****************************
		storeProcedures.put("RecoverToSezAreaLav", new StoreProcedure("DMPK_CORE.RecoverToSezAreaLav", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "CISezioneAreaLavIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "FlgTipoObjIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(6, "IdObjIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(7, "URIXmlOut", null, StoreParameter.OUT), new StoreParameter(8, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(9, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN), new ErrorContextParameter(10), new ErrorCodeParameter(11),
				new ErrorMessageParameter(12) }));

		// ************************************* RICERCA NELLE UO *****************************
		storeProcedures
				.put("TrovaStruttOrgObj", new StoreProcedure("DMPK_DEF_SECURITY.TrovaStruttOrgObj", new StoreParameter[] {
						new ReturnCodeParameter(1),
						new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
						new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
						new StoreParameter(4, "FlgPreimpostaFiltroIn", Types.INTEGER, StoreParameter.IN),
						new TxtClobParameter(5, "MatchByIndexerIn", null, StoreParameter.IN),
						new StoreParameter(6, "FlgIndexerOverflowIn", Types.INTEGER, StoreParameter.IN),
						new StoreParameter(7, "Flg2ndCallIn", Types.INTEGER, StoreParameter.IN),
						new StoreParameter(8, "FlgObjTypeIO", Types.VARCHAR, StoreParameter.IN_OUT),
						new StoreParameter(9, "CercaInUOIO", Types.INTEGER, StoreParameter.IN_OUT),
						new StoreParameter(10, "FlgCercaInSubUOIO", Types.INTEGER, StoreParameter.IN_OUT),
						new StoreParameter(11, "TsRifIn", Types.VARCHAR, StoreParameter.IN),
						new TxtClobParameter(12, "CriteriAvanzatiIO", null, StoreParameter.IN_OUT),
						new TxtClobParameter(13, "CriteriPersonalizzatiIO", null, StoreParameter.IN_OUT),
						new StoreParameter(14, "ColOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
						new StoreParameter(15, "FlgDescOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
						new StoreParameter(16, "FlgSenzaPaginazioneIn", Types.VARCHAR, StoreParameter.IN),
						new StoreParameter(17, "NroPaginaIO", Types.INTEGER, StoreParameter.IN_OUT),
						new StoreParameter(18, "BachSizeIO", Types.INTEGER, StoreParameter.IN_OUT),
						new StoreParameter(19, "OverFlowLimitIn", Types.INTEGER, StoreParameter.IN),
						new StoreParameter(20, "FlgSenzaTotIn", Types.INTEGER, StoreParameter.IN),
						new StoreParameter(21, "NroTotRecOut", Types.VARCHAR, StoreParameter.OUT),//
						new StoreParameter(22, "NroRecInPaginaOut", Types.VARCHAR, StoreParameter.OUT),//
						new StoreParameter(23, "FlgBatchSearchIn", Types.INTEGER, StoreParameter.IN),
						new StoreParameter(24, "ColToReturnIn", Types.VARCHAR, StoreParameter.IN),
						new TxtClobParameter(25, "ResultOut", null, StoreParameter.OUT),
						new TxtClobParameter(26, "PercorsoRicercaXMLIO", null, StoreParameter.IN_OUT),
						new TxtClobParameter(27, "DettagliCercaInFolderOut", null, StoreParameter.OUT), new ErrorContextParameter(28),
						new ErrorCodeParameter(29), new ErrorMessageParameter(30), new TxtClobParameter(31, "FinalitaIn", null, StoreParameter.IN),
						new TxtClobParameter(32, "TyObjxFinalitaIn", null, StoreParameter.IN),
						new TxtClobParameter(33, "IdObjxFinalitaIn", null, StoreParameter.IN) }));

		// ************************************* RICERCA NEL TITOLARIO *****************************
		storeProcedures.put("TrovaTitolario", new StoreProcedure("DMPK_TITOLARIO.TrovainTitolario", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgPreimpostaFiltroIn", Types.INTEGER, StoreParameter.IN),
				new TxtClobParameter(5, "MatchByIndexerIn", null, StoreParameter.IN),
				new StoreParameter(6, "FlgIndexerOverflowIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(7, "Flg2ndCallIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(8, "CercaInCLIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(9, "FlgCercaInSubCLIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(10, "TsRifIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(11, "CriteriAvanzatiIO", null, StoreParameter.IN_OUT),
				new TxtClobParameter(12, "CriteriPersonalizzatiIO", null, StoreParameter.IN_OUT),

				new StoreParameter(13, "ColOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(14, "FlgDescOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(15, "FlgSenzaPaginazioneIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(16, "NroPaginaIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(17, "BachSizeIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(18, "OverFlowLimitIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(19, "FlgSenzaTotIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(20, "NroTotRecOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(21, "NroRecInPaginaOut", Types.VARCHAR, StoreParameter.OUT),

				new StoreParameter(22, "FlgBatchSearchIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(23, "ColToReturnIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(24, "ResultOut", null, StoreParameter.OUT),
				new TxtClobParameter(25, "PercorsoRicercaXMLIO", null, StoreParameter.OUT),
				new TxtClobParameter(26, "DettagliCercaInFolderOut", null, StoreParameter.OUT), new ErrorContextParameter(27), new ErrorCodeParameter(28),
				new ErrorMessageParameter(29), new StoreParameter(30, "FinalitaIn", Types.VARCHAR, StoreParameter.IN) }));

		// ****************************** RICERCA NELL'ANAGRAFICA DEI SOGGETTI ****************************
		storeProcedures.put("TrovaSoggetti", new StoreProcedure("DMPK_ANAGRAFICA.TrovaSoggetti", new StoreParameter[] {
				new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(4, "MatchByIndexerIn", null, StoreParameter.IN),
				new StoreParameter(5, "FlgIndexerOverflowIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(6, "Flg2ndCallIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(7, "DenominazioneIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(8, "FlgInclAltreDenomIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(9, "FlgInclDenomStoricheIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(10, "CFIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(11, "PIVAIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(12, "FlgFisicaGiuridicaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(13, "FlgNotCodTipiSottoTipiIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(14, "ListaCodTipiSottoTipiIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(15, "eMailIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(16, "CodRapidoIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(17, "CIProvSoggIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(18, "FlgSoloVldIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(19, "TsVldIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(20, "CodApplOwnerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(21, "CodIstApplOwnerIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(22, "FlgRestrApplOwnerIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(23, "FlgCertificatiIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(24, "FlgInclAnnullatiIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(25, "IdSoggRubricaIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(26, "FlgInIndicePAIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(27, "CodAmmIPAIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(28, "CodAOOIPAIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(29, "IsSoggRubricaAppIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(30, "IdGruppoAppIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(31, "NomeGruppoAppIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(32, "StrInDenominazioneIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(33, "CriteriPersonalizzatiIO", null, StoreParameter.IN_OUT),
				new StoreParameter(34, "ColOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(35, "FlgDescOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(36, "FlgSenzaPaginazioneIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(37, "NroPaginaIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(38, "BachSizeIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(39, "OverFlowLimitIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(40, "FlgSenzaTotIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(41, "NroTotRecOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(42, "NroRecInPaginaOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(43, "FlgBatchSearchIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(44, "ColToReturnIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(45, "ListaXMLOut", null, StoreParameter.OUT),
				new StoreParameter(46, "FlgAbilInsOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(47, "FlgMostraAltriAttrOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(48), new ErrorCodeParameter(49),
				new ErrorMessageParameter(50), new StoreParameter(51, "FinalitaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(52, "CodIstatComuneIndIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(53, "DesCittaIndIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(54, "RestringiARubricaDiUOIn", null, StoreParameter.IN),
				new TxtClobParameter(55, "FiltriAvanzatiIn", null, StoreParameter.IN) }));

		// ************************************* SET DELLE ACL UD *****************************
		storeProcedures.put("SetACLUD", new StoreProcedure("DMPK_CORE.SetACLUD", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(4, "IdUDIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "FlgEreditarietaConsentitaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "FlgTipoOggEreditaDaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "IdOggEreditaDaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "FlgModACLIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(9, "ACLXMLIn", null, StoreParameter.IN),
				new TxtClobParameter(10, "URIXmlOut", null, StoreParameter.OUT),
				new StoreParameter(11, "FlgIgnoreWarningIn", Types.VARCHAR, StoreParameter.IN), new FullRollbackParameter(12, "0"),
				new AutoCommitParameter(13, "0"), new StoreParameter(14, "WarningMsgOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(15),
				new ErrorCodeParameter(16), new ErrorMessageParameter(17) }));

		// *************************** SET DELLE ACL FOLDER *****************************
		storeProcedures.put("SetACLFolder", new StoreProcedure("DMPK_CORE.SetACLFolder", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdFolderIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "FlgEreditarietaConsentitaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "IdFolderEreditaDaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "FlgPropagazioneConsentitaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(8, "FlgModACLIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(9, "ACLXMLIn", null, StoreParameter.IN),
				new StoreParameter(10, "URIxAggContainerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(11, "FlgIgnoreWarningIn", Types.VARCHAR, StoreParameter.IN), new FullRollbackParameter(12, "0"),
				new AutoCommitParameter(13, "0"), new StoreParameter(14, "WarningMsgOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(15),
				new ErrorCodeParameter(16), new ErrorMessageParameter(17) }));

		// **************************** SET DELLE ACL WORKSPACE *****************************
		storeProcedures.put("SetACLWorkspace", new StoreProcedure("DMPK_CORE.SetACLWorkspace", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "IdWorkspaceIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(5, "FlgPropagazioneConsentitaIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "FlgModACLIn", Types.VARCHAR, StoreParameter.IN), new TxtClobParameter(7, "ACLXMLIn", null, StoreParameter.IN),
				new StoreParameter(8, "URIxAggContainerOut", Types.VARCHAR, StoreParameter.OUT),
				new StoreParameter(9, "FlgIgnoreWarningIn", Types.VARCHAR, StoreParameter.IN), new FullRollbackParameter(10, "0"),
				new AutoCommitParameter(11, "0"), new StoreParameter(12, "WarningMsgOut", Types.VARCHAR, StoreParameter.OUT), new ErrorContextParameter(13),
				new ErrorCodeParameter(14), new ErrorMessageParameter(15) }));

		// ************************************* RICERCA PROCESSI *****************************
		storeProcedures.put("TrovaProcessi", new StoreProcedure("DMPK_PROCESSES.TrovaProcessi", new StoreParameter[] {
				new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "IdUserLavoroIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgPreimpostaFiltroIn", Types.INTEGER, StoreParameter.IN),
				new TxtClobParameter(5, "MatchByIndexerIn", null, StoreParameter.IN),
				new StoreParameter(6, "FlgIndexerOverflowIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(7, "Flg2ndCallIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(8, "FlgFmtEstremiRegIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new TxtClobParameter(9, "CriteriAvanzatiIO", null, StoreParameter.IN_OUT),
				new TxtClobParameter(10, "CriteriPersonalizzatiIO", null, StoreParameter.IN_OUT),
				new StoreParameter(11, "ColOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(12, "FlgDescOrderByIO", Types.VARCHAR, StoreParameter.IN_OUT),
				new StoreParameter(13, "FlgSenzaPaginazioneIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(14, "NroPaginaIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(15, "BachSizeIO", Types.INTEGER, StoreParameter.IN_OUT),
				new StoreParameter(16, "OverFlowLimitIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(17, "FlgSenzaTotIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(18, "NroTotRecOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(19, "NroRecInPaginaOut", Types.VARCHAR, StoreParameter.OUT),//
				new StoreParameter(20, "FlgBatchSearchIn", Types.INTEGER, StoreParameter.IN),
				new StoreParameter(21, "ColToReturnIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(22, "AttoriToReturnIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(23, "AttrCustomToReturnIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(24, "ResultOut", null, StoreParameter.OUT), new ErrorContextParameter(25), new ErrorCodeParameter(26),
				new ErrorMessageParameter(27), new TxtClobParameter(28, "FinalitaIn", null, StoreParameter.IN) }));

		storeProcedures.put("dmfn_Load_Combo", new StoreProcedure("dmfn_Load_Combo", new StoreParameter[] { new ReturnCodeParameter(1),
				new StoreParameter(2, "CodIdConnectionToken", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(3, "TipoComboIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(4, "FlgSoloVldIn", Types.VARCHAR, StoreParameter.IN), new StoreParameter(5, "TsVldIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(6, "PKRecIn", Types.VARCHAR, StoreParameter.IN),
				new StoreParameter(7, "AltriParametriIn", Types.VARCHAR, StoreParameter.IN),
				new TxtClobParameter(8, "ListaXMLOut", null, eng.storefunction.StoreParameter.OUT), new ErrorContextParameter(9), new ErrorCodeParameter(10),
				new ErrorMessageParameter(11) }));
		/*
		 * 
		 * 
		 * //********************************** UNLOCK ******************************************** storeProcedures.put("UnlockDoc", new
		 * StoreProcedure("DMPK_CORE.UnlockDoc", new StoreParameter[] { new ReturnCodeParameter(1), new StoreParameter(2, "CodIdConnectionTokenIn",
		 * Types.VARCHAR, StoreParameter.IN ), new StoreParameter(3, "IdDocIn", Types.INTEGER, StoreParameter.IN ), new StoreParameter(4, "NroProgrLastVerOut",
		 * Types.INTEGER, StoreParameter.OUT ), new FullRollbackParameter(5, "1"), new AutoCommitParameter(6, "0" ), new ErrorContextParameter(7), new
		 * ErrorCodeParameter(8), new ErrorMessageParameter(9) }) );
		 */
	}
}
