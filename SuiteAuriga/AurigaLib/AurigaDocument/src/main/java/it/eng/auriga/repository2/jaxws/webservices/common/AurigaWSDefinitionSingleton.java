/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import eng.storefunction.*;
import eng.storefunction.parameters.*;

import java.sql.Types;

import org.apache.log4j.Logger;


/**
	L'implementazione del Singleton segue i consigli dell'articolo
	http://radio.weblogs.com/0122027/stories/2003/10/20/implementingTheSingletonPatternInJava.html
	con un po' di modifiche per implementare direttamente l'interfaccia che ne espone i servizi
	(StoreProcedureFactory).
*/
public class AurigaWSDefinitionSingleton implements StoreProcedureFactory
{
    private static Logger aLogger = Logger.getLogger(AurigaWSDefinitionSingleton.class.getName());
	/**
	* A handle to the unique Singleton instance.
	*/
	private static  AurigaWSDefinitionSingleton _instance = new AurigaWSDefinitionSingleton();

	/**
        * @return The unique instance of this class.
        */
	public synchronized static StoreProcedureFactory getInstance()
	{
		 if(null == _instance) {
	 		_instance = new AurigaWSDefinitionSingleton();
		}
		return _instance;
	}

	/**
	* Questo metodo crea una nuova istanza di StoreProcedure replicando un'istanza
	* statica. La StoreProcedure ritornata e' thread safe.
	*/
	public StoreProcedure getStoreProcedure(String procedureName)
	{
		StoreProcedure sp = (StoreProcedure)storeProcedures.get(procedureName);
		if (sp != null) return sp.cloneMe();
		return null;
	}


	/**
	* Mappa delle storeprocedure. E' inutile che sia statica, tanto c'e' una sola istanza
	* della classe e la mappa viene creata solo una volta quando il Singleton viene creato.
	*/
	protected java.util.HashMap storeProcedures = null;

	public java.util.HashMap getStoreProcedures()
	{
		return 	storeProcedures;
	}

	/**
	* The constructor could be made private
	* to prevent others from instantiating this class.
	* But this would also make it impossible to
	* create instances of Singleton subclasses.
	*/
	protected AurigaWSDefinitionSingleton() {

	     try
	     {
  	        addStoreProcedures();

  	       /** Fine dichiarazione stores... */
	    }
	    catch(Exception ex)
	    {
	      aLogger.fatal("Exception in AurigaWSDefinitionSingleton." + ex.getMessage() + ".",ex);
    	    }
	}

	protected  void  addStoreProcedures()
	{
		 storeProcedures = new java.util.HashMap();

                 //  06/06/2006 nuove funzioni....

     //************************************ LOGIN **********************************
//                 storeProcedures.put("Login", new StoreProcedure("DMPK_CORE.LoginConCredenzialiEsterne",
//                        new StoreParameter[] {
//                                new ReturnCodeParameter(1),
//                                new StoreParameter(2, "CodApplicazioneIn", Types.VARCHAR, StoreParameter.IN ),
//                                new StoreParameter(3, "CodIstanzaApplIn", Types.VARCHAR, StoreParameter.IN ),
//                                new StoreParameter(4, "UsernameIn", Types.VARCHAR, StoreParameter.IN ),
//                                new StoreParameter(5, "PasswordIn", Types.VARCHAR, StoreParameter.IN ),
//                                new StoreParameter(6, "CodIdConnectionTokenOut", Types.VARCHAR, StoreParameter.OUT ),
//                                new StoreParameter(7, "DesUserOut", Types.VARCHAR, StoreParameter.OUT ),
//                                new StoreParameter(8, "DesDominioOut", Types.VARCHAR, StoreParameter.OUT ),
//                                new ErrorContextParameter(9),
//                                new ErrorCodeParameter(10),
//                                new ErrorMessageParameter(11)
//                        })
//                  );
		 
	   //************************************ LOGIN **********************************
       storeProcedures.put("ExtractFileUD", new StoreProcedure("DMPK_WS.ExtractFileUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "IdDocToExtractOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "NroVerToExtractOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
        );
	   //********************** LOCK/UNLOCK/CHECOUT **********************************
       storeProcedures.put("LockCheckOut", new StoreProcedure("DMPK_WS.LockCheckOut",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(3, "FlgTipoWSIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(4, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(5, "IdDocOut", Types.VARCHAR, StoreParameter.OUT ),
                      new TxtClobParameter(6, "XMLOut", null, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
        );
	   //********************** ADD UD ************************************************
       storeProcedures.put("AddUD", new StoreProcedure("DMPK_WS.AddUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "DocAttachXMLOut", null, StoreParameter.OUT ),
                      new TxtClobParameter(5, "RegNumDaRichASistEstOut", null, StoreParameter.OUT ),
                      new TxtClobParameter(6, "XMLOut", null, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
        );
       
	   //********************** CHECKIN **********************************
       storeProcedures.put("CheckIn", new StoreProcedure("DMPK_WS.CheckIn",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdDoc", Types.NUMERIC, StoreParameter.OUT ),
                      new StoreParameter(5, "NroVersioneEstratta", Types.INTEGER, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgVerificaFirmaFile", Types.INTEGER, StoreParameter.OUT ),
                      new TxtClobParameter(7, "AttributiVerXML", null, StoreParameter.OUT ),
                      new StoreParameter(8, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(9, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(10),
                      new ErrorCodeParameter(11),
                      new ErrorMessageParameter(12)
              })
        );
       
	   //********************** GET METADATA UD **********************************
       storeProcedures.put("GetDatiUD", new StoreProcedure("DMPK_WS.GetDatiUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);

	   //********************** UPD UD ************************************************
       storeProcedures.put("UpdUD", new StoreProcedure("DMPK_WS.UpdUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "OperVsDocXMLOut", null, StoreParameter.OUT ),
                      new TxtClobParameter(5, "RegNumDaRichASistEstOut", null, StoreParameter.OUT ),
                      new TxtClobParameter(6, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
        );
       
       
       //********************** EXTRACT **********************************
       storeProcedures.put("ExtractFilesUD", new StoreProcedure("DMPK_WS.ExtractFilesUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new TxtClobParameter(5, "VerDocToExtractTab", null, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
       //********************** IDENTIFICAZIONE UD **********************************
       storeProcedures.put("LeggiEstremiXIdentificazioneUD", new StoreProcedure("DMPK_WS.LeggiEstremiXIdentificazioneUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdUDOut", Types.VARCHAR, StoreParameter.OUT ),
                      new ErrorContextParameter(5),
                      new ErrorCodeParameter(6),
                      new ErrorMessageParameter(7)
              })
		);
       
       //********************** DELETE UD *******************************************
       storeProcedures.put("DelUD", new StoreProcedure("DMPK_WS.DelUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdUDOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
	
       //********************** DELETE DOC *******************************************
       storeProcedures.put("DelDoc", new StoreProcedure("DMPK_WS.DelDoc",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgTipoTargetOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "IdUDDocOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
		);
	
       //********************** DELETE VER DOC *******************************************
       storeProcedures.put("DelVerDoc", new StoreProcedure("DMPK_WS.DelVerDoc",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdDocOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "NroProgrVerOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
		);
       
       //********************** ADD FOLDER **********************************************
       storeProcedures.put("AddFolder", new StoreProcedure("DMPK_WS.AddFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** UPDATE FOLDER *******************************************
       storeProcedures.put("UpdFolder", new StoreProcedure("DMPK_WS.UpdFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdFolderOut", Types.VARCHAR, StoreParameter.OUT ),
                      new TxtClobParameter(5, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
       //********************** DELETE FOLDER *******************************************
       storeProcedures.put("DelFolder", new StoreProcedure("DMPK_WS.DelFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdFolderOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
       //********************** LOCK GETDATIFOLDER *******************************************
       storeProcedures.put("LockGetDatiFolder", new StoreProcedure("DMPK_WS.LockGetDatiFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(3, "FlgTipoWSIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(4, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(5, "IdFolderOut", Types.VARCHAR, StoreParameter.OUT ),
                      new TxtClobParameter(6, "XMLOut", null, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
		);
       
       //********************** TROVA TIPI DOC **********************************************
       storeProcedures.put("TrovaTipiDocumento", new StoreProcedure("DMPK_WS.TrovaTipiDocumento",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XMLOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** ADD TIPO DOC *********************************************
       storeProcedures.put("AddTipoDoc", new StoreProcedure("DMPK_WS.AddTipoDoc",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "IdTipoDocOut", Types.VARCHAR, StoreParameter.OUT ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** MODIFICA TIPO DOC *********************************************
       storeProcedures.put("UpdTipoDoc", new StoreProcedure("DMPK_WS.UpdTipoDoc",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);
       
       //********************** DEL TIPO DOC *********************************************
       storeProcedures.put("DelTipoDoc", new StoreProcedure("DMPK_WS.DelTipoDoc",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdTipoDocOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
       //********************** TROVA TIPI FOLDER **********************************************
       storeProcedures.put("TrovaTipiFolder", new StoreProcedure("DMPK_WS.TrovaTipiFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XMLOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** ADD TIPO FOLDER *********************************************
       storeProcedures.put("AddTipoFolder", new StoreProcedure("DMPK_WS.AddTipoFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "IdTipoFolderOut", Types.VARCHAR, StoreParameter.OUT ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** MODIFICA TIPO FOLDER *********************************************
       storeProcedures.put("UpdTipoFolder", new StoreProcedure("DMPK_WS.UpdTipoFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);
       
       //********************** DEL TIPO FOLDER *********************************************
       storeProcedures.put("DelTipoFolder", new StoreProcedure("DMPK_WS.DelTipoFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdTipoFolderOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
       //********************** ADD/UPD ATTRIBUTO CUSTOM ****************************************
       storeProcedures.put("AddUpdAttrCustom", new StoreProcedure("DMPK_WS.AddUpdAttrCustom",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(3, "FlgOperazioneIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(4, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** CANCELLAZIONE ATTRIBUTO CUSTOM **********************************
       storeProcedures.put("DelAttrCustom", new StoreProcedure("DMPK_WS.DelAttrCustom",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "AttrNameOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       

       //********************** AGGIUNTA/MOD APPLICAZIONE ESTERNA **********************************
       storeProcedures.put("AddUpdApplicazioneEsterna", new StoreProcedure("DMPK_WS.AddUpdApplicazioneEsterna",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);

       //********************** CANCELLAZIONE APPLICAZIONE ESTERNA **********************************
       storeProcedures.put("DelApplicazioneEsterna", new StoreProcedure("DMPK_WS.DelApplicazioneEsterna",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgTipoDelOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** METADATI APPLICAZIONE ESTERNA **********************************
       storeProcedures.put("GetDatiApplicazioneEsterna", new StoreProcedure("DMPK_WS.GetDatiApplicazioneEsterna",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);

       //********************** RICERCA APPLICAZIONE ESTERNA **********************************
       storeProcedures.put("TrovaApplicazioneEsterne", new StoreProcedure("DMPK_WS.TrovaApplicazioneEsterne",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** AGGIUNTA UTENTE *********************************************
       storeProcedures.put("AddUser", new StoreProcedure("DMPK_WS.AddUser",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);
       
       //********************** MODIFICA UTENTE *********************************************
       storeProcedures.put("ModUser", new StoreProcedure("DMPK_WS.ModUser",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);
       
       //********************** CANCELLAZIONE UTENTE ******************************************
       storeProcedures.put("DelUser", new StoreProcedure("DMPK_WS.DelUser",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);
       
       //********************** ADD MOD PRIVILEGI ******************************************
       storeProcedures.put("AddModPrivilegi", new StoreProcedure("DMPK_WS.AddUpdPrivilegi",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);
       
       //********************** ELIMINA PRIVILEGI ******************************************
       storeProcedures.put("DelPrivilegi", new StoreProcedure("DMPK_WS.RevokePrivilegi",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(5, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(6),
                      new ErrorCodeParameter(7),
                      new ErrorMessageParameter(8)
              })
		);
       
       //********************** REGISTRAZIONE UTENTE COMMUNITY********************************
       storeProcedures.put("RegistrazioneUtenteCommunity", new StoreProcedure("DMPK_WS.RegistrazioneUtenteCommunity",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new TxtClobParameter(2, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(3, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(4, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(5),
                      new ErrorCodeParameter(6),
                      new ErrorMessageParameter(7)
              })
		);
       
       //********************** REGISTRAZIONE UTENTE COMMUNITY********************************
       storeProcedures.put("NewForumThread", new StoreProcedure("DMPK_WS.NewForumThread",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "AttrFolderXmlOut", null, StoreParameter.OUT ),
                      new TxtClobParameter(5, "AttrDocPrimarioXMLOut", null, StoreParameter.OUT ),
                      new TxtClobParameter(6, "DocAttachXMLOut", null, StoreParameter.OUT ),
                      new StoreParameter(7, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(8, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
		);   
       
       //********************** REGISTRAZIONE UTENTE COMMUNITY********************************
       storeProcedures.put("ComponiOutput_ForumThread", new StoreProcedure("DMPK_WS.ComponiOutput_ForumThread",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "IdUDAvvioThreadIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLOut", null, StoreParameter.OUT ),
                      new ErrorContextParameter(4),
                      new ErrorCodeParameter(5),
                      new ErrorMessageParameter(6)
              })
		);  
       
       //********************** SEND BY FAX ********************************
       storeProcedures.put("SendByFax", new StoreProcedure("DMPK_WS.SendByFax",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "NroFaxDestinatarioOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FaxServerAddressOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "UsernameFaxServerOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(7, "PasswordFaxServerOut", Types.VARCHAR, StoreParameter.OUT ),
                      new TxtClobParameter(8, "DocToExtractOut", null, StoreParameter.OUT ),
                      new ErrorContextParameter(9),
                      new ErrorCodeParameter(10),
                      new ErrorMessageParameter(11)
              })
		);        
       
       //********************** TROVA FAX ********************************
       storeProcedures.put("TrovaRichTrasmissioneFax", new StoreProcedure("DMPK_WS.TrovaRichTrasmissioneFax",
              new StoreParameter[] {
               new ReturnCodeParameter(1),
               new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
               new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
               new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
               new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
               new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
               new ErrorContextParameter(7),
               new ErrorCodeParameter(8),
               new ErrorMessageParameter(9)
              })
		);     
       
       //********************** TrovaDocFolder ********************************
       storeProcedures.put("TrovaDocFolder", new StoreProcedure("DMPK_WS.TrovaDocFolder",
              new StoreParameter[] {
               new ReturnCodeParameter(1),
               new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
               new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
               new StoreParameter(4, "FlgUDFolderOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(5, "CercaInFolderOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(6, "FlgCercaInSubFolderOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(7, "FiltroFullTextOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(8, "FlgTutteLeParoleOut", Types.VARCHAR, StoreParameter.OUT ),
               new TxtClobParameter(9, "AttributiXRicercaFTOut", null, StoreParameter.OUT ),
               new TxtClobParameter(10, "CriteriAvanzatiOut", null, StoreParameter.OUT ),
               new TxtClobParameter(11, "CriteriPersonalizzatiOut", null, StoreParameter.OUT ),
               new StoreParameter(12, "ColOrderByOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(13, "FlgDescOrderByOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(14, "FlgSenzaPaginazioneOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(15, "NroPaginaOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(16, "BachSizeOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(17, "FlgBatchSearchOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(18, "ColToReturnOut", Types.VARCHAR, StoreParameter.OUT ),
               new StoreParameter(19, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
               new StoreParameter(20, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
               new ErrorContextParameter(21),
               new ErrorCodeParameter(22),
               new ErrorMessageParameter(23)
              })
		); 
       
     //********************** RICERCA UTENTI **********************************
       storeProcedures.put("TrovaUtenti", new StoreProcedure("DMPK_WS.TrovaUtenti",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
     //********************** RICERCA GRUPPI **********************************
       storeProcedures.put("TrovaGruppi", new StoreProcedure("DMPK_WS.TrovaGruppi",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
     //********************** RICERCA VERSIONI DOCUMENTO **********************************
       storeProcedures.put("GetListaVersioniDoc", new StoreProcedure("DMPK_WS.GetListaVersioniDoc",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "IdDocOut", Types.VARCHAR, StoreParameter.OUT ),
                      new TxtClobParameter(5, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
       //********************** AGGIUNGI/RIMUOVI UD/FOLDER DA PREFERITI **********************************
       storeProcedures.put("AddRemoveFromFavourite", new StoreProcedure("DMPK_WS.AddRemoveFromFavourite",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(3, "OperationTypeIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(4, "FlgTypeObjIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(5, "XMLIn", null, StoreParameter.IN ),                                            
                      new StoreParameter(6, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(7, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
     //********************** RICERCA VERSIONI DEI METADATI UD **********************************
       storeProcedures.put("GetListaVersioniDatiUD", new StoreProcedure("DMPK_WS.GetListaVersioniDatiUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
     //********************** RICERCA NOTIFICHE UD **********************************
       storeProcedures.put("GetListaNotificheUD", new StoreProcedure("DMPK_WS.GetListaNotificheUD",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
       //********************** NOTIFICA DOC FOLDER **********************************
       storeProcedures.put("NotificaDocFolder", new StoreProcedure("DMPK_WS.NotificaDocFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new StoreParameter(4, "FlgTypeObjToNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "IdObjToNotifOut", Types.VARCHAR, StoreParameter.OUT ),                     
                      new TxtClobParameter(6, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(7, "SenderTypeOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(8, "SenderIdOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(9, "CodMotivoNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(10, "MotivoNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(11, "MessaggioNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(12, "LivelloPrioritaOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(13, "RichConfermaPresaVisOut", Types.VARCHAR, StoreParameter.OUT ),                             
                      new StoreParameter(14, "FlgEmailNotifPresaVisOut", Types.VARCHAR, StoreParameter.OUT ),       
                      new StoreParameter(15, "IndEmailNotifPresaVisOut", Types.VARCHAR, StoreParameter.OUT ),       
                      new StoreParameter(16, "NotNoPresaVisEntroGGOut", Types.VARCHAR, StoreParameter.OUT ),       
                      new StoreParameter(17, "FlgEmailNoPresaVisOut", Types.VARCHAR, StoreParameter.OUT ),       
                      new StoreParameter(18, "IndEmailNoPresaVisOut", Types.VARCHAR, StoreParameter.OUT ),                            
                      new StoreParameter(19, "TsDecorrenzaNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(20, "FlgNotificaEmailNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(21, "IndXNotifEmailNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(22, "OggEmailOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(23, "BodyEmailOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(24, "FlgNotificaSMSNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(25, "NriCellXNotifSMSNotifOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(26, "TestoSMSOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(27, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(28, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(29),
                      new ErrorCodeParameter(30),
                      new ErrorMessageParameter(31)
              })
		);
              
     //********************** ESTRAI FILE IN FOLDER **********************************
       storeProcedures.put("ExtractFilesInFolder", new StoreProcedure("DMPK_WS.ExtractFilesInFolder",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),                                          
                      new StoreParameter(4, "IdJobOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
      
   	 //********************** CONTROLLA LA VALIDITA' DEL TOKEN **********************************
       storeProcedures.put("CtrlConnectionToken", new StoreProcedure("DMPK_UTILITY.CtrlConnectionToken",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),                                                                
                      new StoreParameter(3, "IdUserOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(4, "FlgTpDominioAutOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(5, "IdDominioAutOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(6, "CodApplEsternaOut", Types.VARCHAR, StoreParameter.OUT ),
                      new StoreParameter(7, "CodIstanzaApplEstOut", Types.VARCHAR, StoreParameter.OUT ),                      
                      new ErrorContextParameter(8),
                      new ErrorCodeParameter(9),
                      new ErrorMessageParameter(10)
              })
		);
       
     //********************** RICERCA POSSIBILI DESTINATARI OSSERVAZIONI SU UD **********************************
       storeProcedures.put("GetPossibiliDestOsservazSuUD", new StoreProcedure("DMPK_WS.GetPossibiliDestOsservazSuUD",
               new StoreParameter[] {
                       new ReturnCodeParameter(1),
                       new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                       new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                       new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                       new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                       new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                       new ErrorContextParameter(7),
                       new ErrorCodeParameter(8),
                       new ErrorMessageParameter(9)
               })
 		);
       
     //********************** SINCRONIZZA EVENTO **********************************
       storeProcedures.put("SincronizzaEvento", new StoreProcedure("DMPK_SINCRO_EVENTI_UPSO.SincronizzaEvento",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "CodIdConnectionTokenIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new StoreParameter(5, "FlgRollBckFullIn", Types.VARCHAR, StoreParameter.IN ),
                      new StoreParameter(6, "FlgAutoCommitIn", Types.VARCHAR, StoreParameter.IN ),
                      new ErrorContextParameter(7),
                      new ErrorCodeParameter(8),
                      new ErrorMessageParameter(9)
              })
		);
       
     //********************** SINCRONIZZA EVENTO SU EGRAMMATA **********************************
       storeProcedures.put("SincronizzaEventoEgr", new StoreProcedure("PTPK_SINCRO_EVENTI_UPSO.SincronizzaEvento",
              new StoreParameter[] {
                      new ReturnCodeParameter(1),
                      new StoreParameter(2, "IdPostazioneIn", Types.VARCHAR, StoreParameter.IN ),
                      new TxtClobParameter(3, "XMLIn", null, StoreParameter.IN ),
                      new TxtClobParameter(4, "XmlOut", null, StoreParameter.OUT ),
                      new ErrorContextParameter(5),
                      new ErrorCodeParameter(6),
                      new ErrorMessageParameter(7)
              })
		);
           
	}
}
