/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

/**
 * Rappresenta i valori possibili per un operatore su un filtro di tipo stringa.
 * I valori possibili sono <li>{@link #INIZIA}</li> <li>{@link #UGUALE}</li> <li>
 * {@link #FINISCE}</li> <li>{@link #CONTIENE}</li> <li>{@link #LIKE}</li>
 * @author rametta
 */
public enum OperatoreStringaFullTextMistaFilter {
    INIZIA, UGUALE, FINISCE, CONTIENE, LIKE;

    public String getDbValue() {
        return name().toLowerCase();
    }

    public static OperatoreStringaFullTextMistaFilter getFromOperator(String operator) {
        if ("wordsStartWith".equals(operator)) {
            return CONTIENE;
        } else if ("like".equals(operator)) {
            return LIKE;
        } else if ("iEquals".equals(operator)) {
            return UGUALE;
        } else if ("iStartsWith".equals(operator)) {
            return INIZIA;
        } else if ("iEndsWith".equals(operator)) {
            return FINISCE;
        }
        return null;
    }
}
