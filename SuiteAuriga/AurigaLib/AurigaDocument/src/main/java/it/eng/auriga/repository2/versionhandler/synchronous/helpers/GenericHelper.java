/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.repository2.generic.ObjectHandlerException;
import it.eng.auriga.repository2.generic.VersionHandlerException;
import it.eng.auriga.repository2.lucene.LuceneHandler;
import it.eng.auriga.repository2.versionhandler.synchronous.SynchronousVersionHandler;
import it.eng.auriga.repository2.versionhandler.synchronous.SynchronousVersionHandlerRepositoryDefinitionSingleton;
import it.eng.storeutil.ClobUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.CLOB;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import eng.storefunction.StoreParameter;
import eng.storefunction.StoreProcedure;
import eng.storefunction.StoreProcedureException;
import eng.storefunction.parameters.TxtClobParameter;

public class GenericHelper {

	Logger aLogger;
	Logger specialLogger;
	LuceneHandler luceneHandler;
	
	public GenericHelper(Logger aLogger, Logger specialLogger,
			LuceneHandler luceneHandler) {
		this.aLogger = aLogger;
		this.specialLogger = specialLogger;
		this.luceneHandler = luceneHandler;		
	}

	/**
	 * Metodo wrapper per reperire l'oggetto StoreProcedure, associare la
	 * connessione e gestire eventuale errore.
	 * 
	 * @param con
	 *            Connection
	 * @param nomestored
	 *            String
	 * @param msg
	 *            String
	 * @return StoreProcedure
	 * @throws VersionHandlerException
	 */
	protected StoreProcedure getStoreProcedureFromRepositoryDefinitionSingleton(
			String nomestored, String msg) throws VersionHandlerException {
		StoreProcedure store = null;

		// ricavo l'interfaccia verso la stored specifica...
		store = SynchronousVersionHandlerRepositoryDefinitionSingleton
				.getInstance().getStoreProcedure(nomestored);
		if (store == null) {
			// lancio eccezione
			throw new VersionHandlerException(
					SynchronousVersionHandler.STORE_FUNC_NULL_COD,
					SynchronousVersionHandler.STORE_FUNC_NULL_MSG + msg);
		}

		return store;
	}

	/**
	 * Metodo wrapper per inizializzare i valori di input della StoreProcedure.
	 * La lunghezza di nome[] deve essere uguale a valore[]
	 * 
	 * @param store
	 *            StoreProcedure
	 * @param nome
	 *            String[]
	 * @param valore
	 *            String[]
	 */
	protected void setStoreProcedureParametersValues(Connection conn,
			StoreProcedure store, Object[] nome_e_valore) throws SQLException,
			ObjectHandlerException, Exception {
		// imposto la connessione
		store.setConnection(conn);

		eng.storefunction.StoreParameter sprm = null;

		// ciclo per tutti i nomi
		for (int i = 0; i < nome_e_valore.length; i += 2) {
			// (String)nome_e_valore[i + 1]);
			// recupero il parametro ad assegno il valore
			sprm = (StoreParameter) store
					.getParameterByName((String) nome_e_valore[i]);
			if (sprm instanceof TxtClobParameter) {
				// e' un CLOB, quindi uso il metodo adatto.
				if (nome_e_valore[i + 1] instanceof String) {
					CLOB lCLOB = setString((String) nome_e_valore[i + 1], conn);
					((TxtClobParameter) sprm).setValue(lCLOB);
					// xml come stringa
//					((TxtClobParameter) sprm).setNewTemporaryCLOBStringValue(
//							conn, (String) nome_e_valore[i + 1]);
				} 
			} else {
				
				// e' un parametro classico
				store.getParameterByName((String) nome_e_valore[i]).setValue(
						(String) nome_e_valore[i + 1]);
			}
		}
	}
	
	public CLOB setString(String str,Connection con) throws Exception{
		ClobUtil util = new ClobUtil();
		return util.setString(str, con);
	}

	/**
	 * Restituisce i valori di output della StoreProcedure come array di stringhe
	 * 
	 * @param store
	 *            StoreProcedure
	 * @param nome
	 *            String[]
	 * @return String[]
	 */
	protected String[] getStoreProcedureParametersValues(StoreProcedure store,
			String[] nome) {
		// istanzio l'Array da restituire
		String[] res = new String[nome.length];
		StoreParameter sprm = null;

		// ciclo per tutti i nomi
		for (int i = 0; i < nome.length; i++) {
			sprm = store.getParameterByName(nome[i]);
			// e' un TxtClob
			if (sprm instanceof TxtClobParameter) {
				try {
					
					String str = IOUtils.toString(((CLOB)sprm.getValue()).characterStreamValue());
					try { ((CLOB)sprm.getValue()).close(); }
					catch (Exception e){}
					
					res[i] = str;
				} catch (Exception e) {
				}
			} else {
								
				// recupero il parametro ad assegno il valore
				Object parameterValue = store.getParameterByName(nome[i]).getValue();
				
				if(parameterValue != null) {
					res[i] = String.valueOf(parameterValue);
				} else {
					res[i] = null;
				}
			}
		}
		
		// restituisco l'array
		return res;
	}

	protected String[] getInfoFromStoreProcedureException(StoreProcedure store,
			StoreProcedureException spe) {

		String ret[] = new String[2];

		try {
			ret[0] = (String) store.getParameterByName("ErrorCode").getValue();
			if (ret[0] == null)
				ret[0] = new Integer(spe.getReturnCode()).toString();
		} catch (Exception e) {
			try {
				ret[0] = ((BigDecimal) store.getParameterByName("ErrorCode")
						.getValue()).toString();
			} catch (Exception ex) {
				ret[0] = new Integer(spe.getReturnCode()).toString();
			}
		}

		try {
			ret[1] = /*
					 * "[" + store.getParameterByName("ErrorContext").getValue()
					 * + "] " +
					 */"" + store.getParameterByName("ErrorMessage").getValue();
			if (ret[1] == null
					|| (/*
						 * store.getParameterByName("ErrorContext").getValue()
						 * == null &&
						 */store.getParameterByName("ErrorMessage").getValue() == null))
				ret[1] = "" + spe.getMessage();
		} catch (Exception e) {
			ret[1] = "" + spe.getMessage();
		}

		return ret;
	}

    /**
     * Restituisce le informazioni prelevabili dal token
     *
     * @param token String
     * @param conn Connection
     * @return String[]
     * [0] IdUserOut
     * [1] FlgTpDominioAutOut
     * [2] IdDominioAutOut
     * [3] CodApplEsternaOut
     * [4] CodIstanzaApplEstOut
     *
     * @throws VersionHandlerException
     */
    public String[] getApplIstanzaFromToken(Connection conn, String token)
           throws VersionHandlerException
    {

        StoreProcedure store = null;
        int    errCode = 0;
        String errMsg = "";
        String[] res = null;

        try
        {
      	  	aLogger.info("Inizio getApplIstanzaFromToken");            	

               // ricavo l'interfaccia verso la stored specifica e imposto la connessione
               store = getStoreProcedureFromRepositoryDefinitionSingleton( "CtrlConnectionToken", "CtrlConnectionToken");
               
               ///log
               aLogger.debug("getApplIstanzaFromToken: token = " + token);

               // Impostiamo i valori per la store procedure....
               setStoreProcedureParametersValues(conn, store, new Object[] {
                                                 //Token
                                                 "CodIdConnectionTokenIn", token,
               });

               aLogger.debug("Eseguo store per reperire Applicazione ed Istanza dal token di sicurezza");
               // eseguo store
               store.execute();

               // leggo parametri di out
               res = getStoreProcedureParametersValues( store, new String[] {
                                                     "IdUserOut", // [0] IdUserOut
                                                     "FlgTpDominioAutOut", // [1] FlgTpDominioAutOut
                                                     "IdDominioAutOut", // [2] IdDominioAutOut
                                                     "CodApplEsternaOut", // [3] CodApplEsternaOut
                                                     "CodIstanzaApplEstOut" // [4] CodIstanzaApplEstOut
                                                    });
               
               //log
               if (res != null && res.length >= 5) {
               aLogger.debug("getApplIstanzaFromToken: IdUserOut = " + res[0]);
               aLogger.debug("getApplIstanzaFromToken: FlgTpDominioAutOut = " + res[1]);
               aLogger.debug("getApplIstanzaFromToken: IdDominioAutOut = " + res[2]);
               aLogger.debug("getApplIstanzaFromToken: CodApplEsternaOut = " + res[3]);
               aLogger.debug("getApplIstanzaFromToken: CodIstanzaApplEstOut = " + res[4]);
               }

               
               // restituisco l'array
               return res;

       }
       catch (VersionHandlerException ex1)
       {
           // log
      	 aLogger.error("Errore in Internal Login.\nErrcode:" + ex1.getCodice() + "\nErrMsg: " + ex1.getMessage(), ex1);
           // rilancio l'eccezione
           throw ex1;
       }
       catch (StoreProcedureException ex0)
       {
              // Qualcosa è andato storto nella storefunc!!!
      	 	String err[] = getInfoFromStoreProcedureException(store, ex0);
      	 	errCode = Integer.parseInt(err[0]);
      	 	errMsg = err[1]; 		
              //log
              aLogger.error("Errore SQL CtrlConnectionToken.\nException: " + errMsg, ex0);
              // lancio l'eccezione
              throw new VersionHandlerException(errCode, errMsg);
       }
       catch (Exception ex)
       {
              //log
              aLogger.error("Errore IMPREVISTO CtrlConnectionToken.\nException: " + ex.getMessage(), ex);
              // rilancio l'eccezione trasformata
              throw new VersionHandlerException(SynchronousVersionHandler.IMPREVISTO_COD, SynchronousVersionHandler.IMPREVISTO_MSG + "(" + ex.getMessage() + ")");
       }
       finally{
   	  	aLogger.info("Fine getApplIstanzaFromToken");            	
       }
    }
	
}
