package com.nullnull.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author nullnull
 * @since 2024/12/10
 */
public class PasswordDTO {

    private String raw;

    private String secured;

    @JsonCreator
    public PasswordDTO(
            @JsonProperty("raw")
            String raw,
            @JsonProperty("secured")
            String secured) {
        this.raw = raw;
        this.secured = secured;
    }

    public String getRaw() {
        return raw;
    }

    public String getSecured() {
        return secured;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public void setSecured(String secured) {
        this.secured = secured;
    }
}
