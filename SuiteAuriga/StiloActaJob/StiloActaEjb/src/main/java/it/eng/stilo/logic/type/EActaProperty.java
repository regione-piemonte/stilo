/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum EActaProperty {

    FISCAL_CODE("fis-code"),
    APPLICATION_KEY("app-key"),
    AOO_CODE("aoo-code"),
    STRUCTURE_CODE("structure-code"),
    NODE_CODE("node-code"),
    REPO_NAME("repo-name"),
    REPO_CODE("repo-code");

    String code;

    EActaProperty(final String eCode) {
        this.code = eCode;
    }

    public String getCode() {
        return this.code;
    }

}
