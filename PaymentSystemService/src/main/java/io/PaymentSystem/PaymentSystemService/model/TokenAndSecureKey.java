package io.PaymentSystem.PaymentSystemService.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("tokensecurekey")
public class TokenAndSecureKey
{
    private String dateastoken;
    private String securekey;

    public TokenAndSecureKey() {
    }

    public TokenAndSecureKey(String dateastoken, String securekey) {
        this.dateastoken = dateastoken;
        this.securekey = securekey;
    }

    public String getDateastoken() {
        return dateastoken;
    }

    public void setDateastoken(String dateastoken) {
        this.dateastoken = dateastoken;
    }

    public String getSecurekey() {
        return securekey;
    }

    public void setSecurekey(String securekey) {
        this.securekey = securekey;
    }
}
