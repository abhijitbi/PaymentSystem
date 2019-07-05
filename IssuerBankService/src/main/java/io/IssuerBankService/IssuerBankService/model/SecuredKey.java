package io.IssuerBankService.IssuerBankService.model;

public class SecuredKey
{
    private SecuredKey securedKey;

    public SecuredKey() {
    }

    public SecuredKey(SecuredKey securedKey) {
        this.securedKey = securedKey;
    }

    public SecuredKey getSecuredKey() {
        return securedKey;
    }

    public void setSecuredKey(SecuredKey securedKey) {
        this.securedKey = securedKey;
    }
}
