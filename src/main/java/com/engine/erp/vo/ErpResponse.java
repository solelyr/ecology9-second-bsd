package com.engine.erp.vo;

import java.util.List;

/**
 * @DESCRIPTION: ERP返回的数据对象
 * @USER: solelyr
 * @DATE: 2026/9/15 12:20
 */
public class ErpResponse {
    private StdData std_data;

    public StdData getStd_data() {
        return std_data;
    }
    public void setStd_data(StdData std_data) {
        this.std_data = std_data;
    }

    public static class StdData {
        private Execution execution;
        private Parameter parameter;

        public Execution getExecution() {
            return execution;
        }
        public void setExecution(Execution execution) {
            this.execution = execution;
        }
        public Parameter getParameter() {
            return parameter;
        }
        public void setParameter(Parameter parameter) {
            this.parameter = parameter;
        }
    }

    public static class Execution {
        private String code;
        private String description;

        public String getCode() {
            return code;
        }
        public void setCode(String code) {
            this.code = code;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Parameter {
        private Result result;

        public Result getResult() {
            return result;
        }
        public void setResult(Result result) {
            this.result = result;
        }
    }

    public static class Result {
        private List<String> success;
        private List<String> error;

        public List<String> getSuccess() {
            return success;
        }
        public void setSuccess(List<String> success) {
            this.success = success;
        }
        public List<String> getError() {
            return error;
        }
        public void setError(List<String> error) {
            this.error = error;
        }
    }
}
