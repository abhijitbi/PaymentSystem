package io.IssuerBankService.IssuerBankService.Controller;

import io.IssuerBankService.IssuerBankService.model.*;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@RestController
@RequestMapping("/HDFC")
public class HDFCRestController
{
    private static SecretKey secretKey;

    private static String token=null;

    private static Cipher cipher;

    private String network;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenAndKeyRepository tokenAndKeyRepository;

    @Autowired
    private TokenAndSecurekeyRepository tokenAndSecurekeyRepository;

    @Autowired
    private IssuerBankAccountRepository issuerBankAccountRepository;

    @RequestMapping(value = "/hello/{network}")
    public String helloWorld(@PathVariable("network") String network)  throws Exception{

        GenericPackager packager = new GenericPackager("src/main/resources/basic.xml");
        restTemplate=new RestTemplate();                                                                                //Create instance of RestTemplate
        String token=restTemplate.getForObject("http://localhost:8086/"+network+"/tokenForIssuer",String.class);    //Get Token from payment System
        System.out.println("Token::"+token);                                                                            //Token create Payment System(eg.RuPay)
        TokenAndKey getKeyAndData=tokenAndKeyRepository.findSecurekeyByToken(token);                                    //Retrieve Encrypted Data from keyandate document for given Token
        TokenAndSecureKey t=tokenAndSecurekeyRepository.findSecurekeyByDateastoken(token);                              //Retrieve Key from tokensecurekey Document
        byte[] decodedKey = Base64.getDecoder().decode( t.getSecurekey());                                              //Decode the base64 encoded string
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");             //Rebuild key using SecretKeySpec
        Base64.Decoder decoder = Base64.getDecoder();                                                                   //Get base64 decoded version of the key
        byte[] encryptedTextByte = decoder.decode(getKeyAndData.getKey());                                              //Decode the given Key
        cipher = Cipher.getInstance("AES");                                                                             //Get Cipher Instance for "AES:Advanced Encryption Standard"
        cipher.init(Cipher.DECRYPT_MODE, originalKey);                                                                  //Decrypt data using SecureKey
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);                                                       //Get Decrypted data in Bytes
        String decryptedText = new String(decryptedByte);                                                               //Store it into the String
        System.out.println("Decrypted Text::"+decryptedText);                                                           //Decrypted text
        ISOMsg msg = new ISOMsg();                                                                                      // Create ISO Message
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
        }
        return "Approved.";
    }

}
