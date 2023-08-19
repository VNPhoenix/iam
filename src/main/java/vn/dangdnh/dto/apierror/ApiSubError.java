package vn.dangdnh.dto.apierror;

import lombok.Getter;

@Getter
public class ApiSubError {

    protected String message;

    public ApiSubError(String message) {
        this.message = message;
    }
}
