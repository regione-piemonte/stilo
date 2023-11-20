/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

public class MessageBean implements Serializable {

    private String logType;
    private String flowId;
    private Boolean outBound;
    private Object refDocument;
    private String text;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Boolean getOutBound() {
        return outBound;
    }

    public void setOutBound(Boolean outBound) {
        this.outBound = outBound;
    }

    public Object getRefDocument() {
        return refDocument;
    }

    public void setRefDocument(Object refDocument) {
        this.refDocument = refDocument;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
