package io.AcquirerBank.AquirerBankService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("paymentsystem")
public class IINumber
{
    @Id
    private String id;
    private String network;
    private int iin[];
    private int length;

    public IINumber() {
    }

    public IINumber(String id, String network, int[] iin, int length) {
        this.id = id;
        this.network = network;
        this.iin = iin;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public int[] getIin() {
        return iin;
    }

    public void setIin(int[] iin) {
        this.iin = iin;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
