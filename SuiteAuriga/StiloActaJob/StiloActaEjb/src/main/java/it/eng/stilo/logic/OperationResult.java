/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationResult implements Serializable {

    private final Map<AttributeType, Object> attributes = new HashMap<>();
    private String key;
    private ResultType resultType;

    public OperationResult(final String key) {
        this.key = key;
        this.resultType = ResultType.FAILED;
    }

    public OperationResult(final String key, final ResultType resultType) {
        this.key = key;
        this.resultType = resultType;
    }

    public String getKey() {
        return key;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public Map<AttributeType, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "OperationResult{" +
                "key='" + key + '\'' +
                ", resultType=" + resultType.name() +
                ", attributes=[" + attributes.entrySet().stream().map(e -> e.getKey().name() + ":" + e.getValue())
                .collect(Collectors.joining(";")) + "]";
    }

    public enum AttributeType {
        OBJECT_ID_DOCUMENT
    }

    public enum ResultType {
        AUTO_SUCCESS, FAILED, FAILED_ATTACHMENT, FAILED_CLASSIFICATION, SUCCESS
    }

}
