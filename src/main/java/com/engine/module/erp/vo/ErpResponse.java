package com.engine.module.erp.vo;

import java.util.List;

/**
 * @DESCRIPTION: ERP返回的数据对象
 * @USER: solelyr
 * @DATE: 2026/9/15 12:20
 */
public class ErpResponse<T> {
    private StdData<T> std_data;

    public StdData<T> getStd_data() {
        return std_data;
    }
    public void setStd_data(StdData<T> std_data) {
        this.std_data = std_data;
    }
    public Boolean isSuccess(){
        return "0".equals(std_data.getExecution().getCode());
    }


    public static class StdData<T> {
        private Execution execution;
        private Parameter<T> parameter;

        public Execution getExecution() {
            return execution;
        }
        public void setExecution(Execution execution) {
            this.execution = execution;
        }
        public Parameter<T> getParameter() {
            return parameter;
        }
        public void setParameter(Parameter<T> parameter) {
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

    public static class Parameter<T> {
        private Result<T> result;

        public Result<T> getResult() {
            return result;
        }
        public void setResult(Result<T> result) {
            this.result = result;
        }
    }

    public static class Result<T> {
        private List<T> success;
        private List<ErrorItem> error;

        public List<T> getSuccess() {
            return success;
        }
        public void setSuccess(List<T> success) {
            this.success = success;
        }
        public List<ErrorItem> getError() {
            return error;
        }
        public void setError(List<ErrorItem> error) {
            this.error = error;
        }
    }

    public static class ErrorItem {
        private String message;

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
    }
}
