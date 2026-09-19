package com.engine.module.erp.vo;

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
    public Boolean isSuccess(){
        return "0".equals(std_data.getExecution().getCode());
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
        private List<SuccessItem> success;
        private List<ErrorItem> error;

        public List<SuccessItem> getSuccess() {
            return success;
        }
        public void setSuccess(List<SuccessItem> success) {
            this.success = success;
        }
        public List<ErrorItem> getError() {
            return error;
        }
        public void setError(List<ErrorItem> error) {
            this.error = error;
        }
    }

    public static class SuccessItem {
        private String doc_no;

        public String getDoc_no() {
            return doc_no;
        }
        public void setDoc_no(String doc_no) {
            this.doc_no = doc_no;
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
