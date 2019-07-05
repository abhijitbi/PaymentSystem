package io.AcquirerBank.AquirerBankService.model;

public class Token
{
    private String tokenSystem;

    public Token() {
    }

    public Token(String tokenSystem) {
        this.tokenSystem = tokenSystem;
    }

    public String getTokenSystem() {
        return tokenSystem;
    }

    public void setTokenSystem(String tokenSystem) {
        this.tokenSystem = tokenSystem;
    }
}
