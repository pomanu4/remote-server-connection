package com.company.project.component;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@Component
public class XmlSignatureUtill {

    private static final String CHARSET = "UTF-8";
    private static final String SIGN_ALGORYTHM = "SHA1withRSA";
    private static final String KEY_ALGORYTHM = "RSA";
    
    @Autowired
    private KeyBytesSupplier keySupplier;

    public boolean verify(String message, String signature) throws SignatureException {
        try {
            Signature sign = Signature.getInstance(SIGN_ALGORYTHM);

            sign.initVerify(getPublicKey());
            sign.update(message.getBytes(CHARSET));
            return sign.verify(Base64.decode(signature.getBytes(CHARSET)));
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public String sign(String message) throws SignatureException {
        try {
            Signature sign = Signature.getInstance(SIGN_ALGORYTHM);
            sign.initSign(keySupplier.getPrivateKeyFromPEMfile());
            sign.update(message.getBytes(CHARSET));
            return java.util.Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public PublicKey getPublicKey() {
        PublicKey publicKey = null;
        try {
            byte[] byteKey = Base64.decode(keySupplier.getPublicKeyBytes());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORYTHM);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | Base64DecodingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return publicKey;
    }
    /*
    used only vith decodet to DER format private.pem file
    */
    public PrivateKey getPrivateKeyfromDERfile() {
        PrivateKey privateK = null;
        try {
            byte[] byteKey = keySupplier.getPrivateKeyBytes();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORYTHM);
            privateK = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateK;
    }
}
