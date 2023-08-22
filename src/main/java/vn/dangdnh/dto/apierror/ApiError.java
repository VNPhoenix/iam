package vn.dangdnh.dto.apierror;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import vn.dangdnh.definition.message.exception.ExceptionMessages;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus status;

    private ZonedDateTime timestamp;

    private String message;

    @JsonProperty("debug_message")
    private String debugMessage;

    @JsonProperty("sub_errors")
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = ZonedDateTime.now(ZoneId.systemDefault());
        this.message = ExceptionMessages.UNEXPECTED_ERROR;
        subErrors = new LinkedList<>();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable e) {
        this();
        this.status = status;
        this.debugMessage = Objects.nonNull(e) ? ExceptionUtils.getStackTrace(e) : null;
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
        this.debugMessage = Objects.nonNull(e) ? ExceptionUtils.getStackTrace(e) : null;
    }

    public boolean addSubError(ApiSubError subError) {
        return subErrors.add(subError);
    }
}
