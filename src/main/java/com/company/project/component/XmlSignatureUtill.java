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
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.springframework.core.io.ClassPathResource;

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
            sign.initSign(getPrivateKey2());
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

    public PrivateKey getPrivateKey() {
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

    public PrivateKey getPrivateKey2() {
        Security.addProvider(new BouncyCastleProvider());
        ClassPathResource resource = new ClassPathResource("files/private.pem");
        final String privateKeyPassword = null;
        try {
            PrivateKey privateK = null;
            PEMReader reader = new PEMReader(new InputStreamReader(resource.getInputStream()), new PasswordFinder() {
                @Override
                public char[] getPassword() {
                    if (privateKeyPassword != null) {
                        return privateKeyPassword.toCharArray();
                    } else {
                        return new char[0];
                    }
                }
            });
            KeyPair pair = (KeyPair) reader.readObject();
            privateK = pair.getPrivate();
            
            return privateK;   
        } catch (IOException ex) {
            Logger.getLogger(XmlSignatureUtill.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
