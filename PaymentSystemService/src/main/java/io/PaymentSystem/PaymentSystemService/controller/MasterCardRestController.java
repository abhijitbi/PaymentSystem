package io.PaymentSystem.PaymentSystemService.controller;

import io.PaymentSystem.PaymentSystemService.model.*;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/MASTERCARD")
public class MasterCardRestController
{
    private static SecretKey secretKey;

    private static String token=null;

    static Cipher cipher;

    private static String encodedKey;

    private String issuerBank;

    private static String tokenForIssuerBank;

    private String network="MASTERCARD";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenAndKeyRepository tokenAndKeyRepository;

    @Autowired
    private TokenAndSecurekeyRepository tokenAndSecurekeyRepository;

    @Autowired
    private IssuerBankAccountRepository issuerBankAccountRepository;

    @RequestMapping(value = "/secKey")
    public  String createSecureKey() throws Exception{                                                                  //Create Encrypted Secret Key and send it back to AcquirerBankService

        System.out.println("****************************************MASTER CARD****************************************");

        secretKey = KeyGenerator.getInstance("AES").generateKey();                                                      //Generate Secret Key
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());                                 //Get base64 encoded version of the key
        Date date=new Date();                                                                                           //Get instance of Date
        token=date.toString();                                                                                          //Convert it into String
        TokenAndSecureKey tokenAndSecureKey=new TokenAndSecureKey(token=date.toString(),encodedKey);                    //Assign Token and key to the tokenandsecurekey instance
        tokenAndSecurekeyRepository.save(new TokenAndSecureKey(token,encodedKey));                                      //Store Token and Secure Key into the Database
        TokenAndSecureKey getData=tokenAndSecurekeyRepository.findDateastokenBySecurekey(encodedKey);                   //Get the data using encoded key

        return  getData.getDateastoken();
    }

    @RequestMapping(value="/keyAndData")
    public String decryptedReceivedData() throws Exception {

        GenericPackager packager = new GenericPackager("src/main/resources/basic.xml");
        TokenAndKey getKeyAndData=tokenAndKeyRepository.getSecurekeyByToken(token);                                     //Retrieve Key from database for given Token
        Base64.Decoder decoder = Base64.getDecoder();                                                                   //Get base64 decoded version of the key
        byte[] encryptedTextByte = decoder.decode(getKeyAndData.getKey());                                              //Decode the given Key
        cipher = Cipher.getInstance("AES");                                                                             //Get Cipher Instance for "AES:Advanced Encryption Standard"
        cipher.init(Cipher.DECRYPT_MODE, secretKey);                                                                    //Assign secure key to the Cipher
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);                                                       //Get Decrypted data in Bytes
        String decryptedText = new String(decryptedByte);                                                               //Store it into the String
        System.out.println("Decrypted Text::"+decryptedText);                                                           //Decrypted Text
        IssuerBankAccount issuerBankAccount=issuerBankAccountRepository.findBankByBin("559405");                        //Find Bank Account using BIN Number
        issuerBank=issuerBankAccount.getBank();                                                                         //Get the issuer bank from the database
        System.out.println("Issuer Bank::"+issuerBank);                                                                 //Print the issuer bank
        String uri = "http://localhost:8088/"+issuerBank+"/hello/"+network;                                             //Get URI link for the Issuer bank
        RestTemplate restTemplate=new RestTemplate();                                                                   //Create the instance of RestTemplate
        String status=restTemplate.getForObject(uri,String.class);                                                      //Get the status from the Issuer bank Account
        System.out.println(status);

        System.out.println("Got the Call from Acquirer Bank.");
        return status;

       //Print the Status
     /*// Create ISO Message
        ISOMsg msg = new ISOMsg();
        msg.setPackager(packager);
        msg.unpack(decryptedText.getBytes());

        System.out.println("----ISO MESSAGE ParseISOMessage-----");
        try {
            System.out.println("  MTI : " + msg.getMTI());
            System.out.println("MaxFeild::" + msg.getMaxField());
            for (int i = 1; i <= msg.getMaxField(); i++) {

                if (msg.hasField(i)) {
                    System.out.println("    Field-" + i + " : " + msg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("--------------------");
        }*/



    }

    @RequestMapping(value="/tokenForIssuer")
    private String getTokenForIssuerBank(){

        System.out.println("Token for Issuer bank::"+tokenForIssuerBank);
        return tokenForIssuerBank;
    }
}
