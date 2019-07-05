package io.IssuerBankService.IssuerBankService.model;

public class EncodedKey
{
    private String encodedKey;

    public EncodedKey() {
    }

    public EncodedKey(String encodedKey) {
        this.encodedKey = encodedKey;
    }

    public String getEncodedKey() {
        return encodedKey;
    }

    public void setEncodedKey(String encodedKey) {
        this.encodedKey = encodedKey;
    }
}
