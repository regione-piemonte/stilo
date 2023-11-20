/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class JmsUtil {

    // JMS objects
    public static final String JMS_CONNECTION_FACTORY = "ConnectionFactory";
    public static final String JMS_QUEUE_LOG = "jms/LogQueue";

    // JMS properties names
    public static final String JMS_LOG_TYPE = "logType";
    public static final String JMS_LOG_REF_ENTITY = "refEntity";
    public static final String JMS_LOG_OUT_BOUND = "outBound";
    public static final String JMS_LOG_ID_FLOW = "idFlow";

    // JMS properties values
    public static final String JMS_LOG_WS = "WS-LOG";
    public static final String JMS_LOG_GENERIC = "Generic-LOG";

}
