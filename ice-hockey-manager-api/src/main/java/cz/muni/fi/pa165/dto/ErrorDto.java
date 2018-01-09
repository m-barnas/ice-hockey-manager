package cz.muni.fi.pa165.dto;

/**
 * Created by Lukas Kotol on 1/9/2018.
 */
public class ErrorDto {
    private String errorName;

    public ErrorDto(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }
}
