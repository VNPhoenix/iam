package vn.dangdnh.dto.apierror;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date datetime;

    private String message;

    @JsonProperty("debug_message")
    private String debugMessage;

    @JsonProperty("sub_errors")
    private List<ApiSubError> subErrors;

    private ApiError() {
        this.datetime = new Date();
        this.message = "Unexpected error";
        this.subErrors = new LinkedList<>();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable e) {
        this();
        this.status = status;
        this.debugMessage = e != null ? ExceptionUtils.getStackTrace(e) : null;
    }

    public ApiError(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String message, Throwable e) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = e != null ? ExceptionUtils.getStackTrace(e) : null;
    }

    public boolean addSubError(ApiSubError subError) {
        return subErrors.add(subError);
    }
}
