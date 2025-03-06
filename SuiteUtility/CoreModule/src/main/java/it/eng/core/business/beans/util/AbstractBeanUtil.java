package it.eng.core.business.beans.util;

import it.eng.core.business.beans.AbstractBean;
import it.eng.core.business.exception.ServiceException;
import it.eng.core.config.CoreConfig;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe di utilita' per i bean di interfaccia
 * @author upescato
 */

public class AbstractBeanUtil {

    /**
     * Metodo che ritorna la lista dei nomi delle property dichiarate nel bean
     * concreto passato in input. <br>
     * <i>Non ritorna i nomi delle property dichiarate all'interno di eventuali
     * superclassi.</i>
     * @param clazz
     *            - bean di cui si vuole recuperare la lista delle properties
     * @return
     * @throws Exception
     *             - se non esistono variabili d'istanza per la presente classe
     */
    public static String[] getDeclaredBeanPropertyNames(Class<?> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        return getFieldsNames(fields);
    }

    /**
     * Metodo che ritorna la lista dei nomi delle property dichiarate nel bean
     * concreto (e relative superclassi) passato in input. <br>
     * <i>Ritorna i nomi delle property dichiarate all'interno di eventuali
     * superclassi.</i>
     * @param clazz
     *            - bean di cui si vuole recuperare la lista delle properties
     * @return
     * @throws Exception
     *             - se non esistono variabili d'istanza per la presente classe
     *             e sue relative superclassi
     */
    public static String[] getBeanPropertyNames(Class<?> clazz) throws Exception {
        Field[] fields = clazz.getFields();
        return getFieldsNames(fields);
    }

    private static String[] getFieldsNames(Field[] fields) throws Exception {

        if (fields == null || fields.length == 0) {
            throw new ServiceException(CoreConfig.modulename, "VAR_IST_NOT_DEF");
            // throw new
            // Exception("Nella presente classe non sono definite variabili d'istanza");

        }

        String[] variableNames = new String[fields.length];
        for (int j = 0; j < fields.length; j++) {
            variableNames[j] = fields[j].getName();
        }

        return variableNames;
    }

    /**
     * Verifica se nel bean passato in input sono state modificate property
     * ammissibili. <br>
     * <br>
     * La verifica avviene nel seguente modo: <br>
     * Controllo che, su ciascun propertyName non appartenente all'insieme delle
     * modificabili, non sia avvenuta modifica.
     * @param bean
     *            - bean contenente i valori modificati
     * @param primaryKeyProps
     *            - insieme delle properties che compongono la chiave primaria
     *            del bean (queste sono sempre property modificabili)
     * @param propsModificabili
     *            - insieme delle properties modificabili
     * @throws Exception
     */
    public static void verificaPropertiesModificate(AbstractBean bean, String[] primarykeys, String[] modificabili) throws Exception {
        if (primarykeys == null || primarykeys.length == 0 || modificabili == null || modificabili.length == 0) {
            // throw new
            // Exception("Le properties modificabili non sono correttamente valorizzate!");
            throw new ServiceException(CoreConfig.modulename, "PROP_MOD_NOT_VAL");
        }

        Set<String> primaryKeyProps = new HashSet<String>(Arrays.asList(primarykeys));
        Set<String> propsModificabili = new HashSet<String>(Arrays.asList(modificabili));

        // Recupero i nomi di tutte le property del bean
        String[] allPropertiesNames = AbstractBeanUtil.getDeclaredBeanPropertyNames(bean.getClass());

        // Per ciascuna property che non corrisponde a quelle per cui la
        // modifica è ammissibile (o alla property dell'identificativo)
        // verifico se è stata modificata. In caso affermativo lancio
        // un'eccezione
        for (String propertyName : allPropertiesNames) {

            if (!primaryKeyProps.contains(propertyName) && !propsModificabili.contains(propertyName)) {
                if (bean.hasPropertyBeenModified(propertyName)) {
                    // throw new
                    // Exception("La property "+propertyName+" non è modificabile!");
                    throw new ServiceException(CoreConfig.modulename, "PROP_NOT_MOD", propertyName);
                }
            }
        }
    }
}
