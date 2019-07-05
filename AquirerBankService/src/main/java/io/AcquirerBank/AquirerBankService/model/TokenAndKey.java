package io.AcquirerBank.AquirerBankService.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("keyandata")
public class TokenAndKey
{
    private String token;
    private String key;

    public TokenAndKey() {
    }

    public TokenAndKey(String token, String key) {
        this.token = token;
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
