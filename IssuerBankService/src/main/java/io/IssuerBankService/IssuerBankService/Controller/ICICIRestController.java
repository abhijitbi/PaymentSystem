package io.IssuerBankService.IssuerBankService.Controller;

import io.IssuerBankService.IssuerBankService.model.*;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
@RequestMapping("/ICICI")
public class ICICIRestController
{
    private static String token=null;

    private static Cipher cipher;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenAndKeyRepository tokenAndKeyRepository;

    @Autowired
    private TokenAndSecurekeyRepository tokenAndSecurekeyRepository;

    @Autowired
    private IssuerBankAccountRepository issuerBankAccountRepository;

    Logger logger = Logger.getLogger("log");

    @RequestMapping(value = "/hello/{network}")
    public String helloWorld(@PathVariable("network") String network)  throws ISOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException {

        GenericPackager packager = new GenericPackager("src/main/resources/basic.xml");
        restTemplate=new RestTemplate();                                                                                //Create instance of RestTemplate
        token=restTemplate.getForObject("http://localhost:8086/"+network+"/tokenForIssuer",String.class);           //Get Token from payment System
        logger.info("Token::"+token);                                                                                   //Token create Payment System(eg.RuPay)
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
        logger.info("Decrypted Text::"+decryptedText);                                                                  //Decrypted text
        ISOMsg msg = new ISOMsg();                                                                                      // Create ISO Message
        msg.setPackager(packager);
        msg.unpack(decryptedText.getBytes());
        logger.info("----ISO MESSAGE ParseISOMessage-----");
        try {
            logger.info("  MTI : " + msg.getMTI());
            logger.info("MaxFeild::" + msg.getMaxField());
            for (int i = 1; i <= msg.getMaxField(); i++) {

                if (msg.hasField(i)) {
                    logger.info("    Field-" + i + " : " + msg.getString(i));
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        } finally {
            logger.info("--------------------");
        }
        return "Approved.";
    }

}
