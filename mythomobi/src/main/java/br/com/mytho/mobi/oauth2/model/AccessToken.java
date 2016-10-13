package br.com.mytho.mobi.oauth2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AccessToken {

    @JsonProperty("access_token")
    private String code;

    @Deprecated
    public AccessToken() {

    }

    public AccessToken(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}