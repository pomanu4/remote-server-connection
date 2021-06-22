package com.company.project.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
//import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class KeyBytesSupplier {

    private static final String FILE_PATH = "files/";
//    private static final String PUBLIC = "453-public.pem";
    private static final String PRIVATE = "453-private.der";/// not used
//    private static final String PRIVATE_PEM = "453-private.pem";
    private static final String CHARSET = "UTF-8";
    private static final String CERT_PATH = "files/cert/";
    private static final String CERTIFICATE="point_429.p12";
    private static final String CERTIFICATE_PASSWORD="password.txt";
    private static final String CERTIFICATE_ALGORUTHM = "PKCS12";
    
    /// current test
    private static final String PUBLIC = "458_public.pem";
    private static final String PRIVATE_PEM = "458_private.pem";
    
    // split
//    private static final String PUBLIC = "459_public.pem";
//    private static final String PRIVATE_PEM = "459_private.pem";
    
//    private static final String PUBLIC = "337_public.pem";
//    private static final String PRIVATE_PEM = "337_private.pem";
    
//        private static final String PUBLIC = "1003_public.pem";
//    private static final String PRIVATE_PEM = "1003_private.pem";
    
    /// gpc point keys
//    private static final String PUBLIC = "public_331.pem";
//    private static final String PRIVATE_PEM = "private_331.pem";
    
    
    /// prod point keys
//    private static final String PUBLIC = "public_20002.pem";
//    private static final String PRIVATE_PEM = "private_20002.pem";

    public byte[] getPublicKeyBytes() throws UnsupportedEncodingException {
        ClassPathResource resource = new ClassPathResource(FILE_PATH + PUBLIC);
        byte[] bytes = null;
        try (InputStream input = resource.getInputStream()) {
            bytes = IOUtils.toByteArray(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prepareKey(bytes);
    }

    public byte[] getPrivateKeyBytes() {
        ClassPathResource resource = new ClassPathResource(FILE_PATH + PRIVATE);
        byte[] keybytes = null;
        try (InputStream input = resource.getInputStream()) {
            keybytes = IOUtils.toByteArray(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keybytes;
    }

    private byte[] prepareKey(byte[] keyBytes) throws UnsupportedEncodingException {
        String result = new String(keyBytes, CHARSET)
                .replaceAll("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll("-----END PUBLIC KEY-----", "")
                .replaceAll("\\n", "");
        return result.getBytes(CHARSET);
    }
    
    public PublicKey getPublicKeyFromFile(String password){
        Security.addProvider(new BouncyCastleProvider());
        ClassPathResource resource = new ClassPathResource(FILE_PATH + PUBLIC);
        final String privateKeyPassword = password;
        PublicKey pubK = null;
        PasswordFinder finder = () -> {
            if (privateKeyPassword != null) {
                return privateKeyPassword.toCharArray();
            } else {
                return new char[0];
            }
        };
        
        try(PEMParser parser = new PEMParser(new InputStreamReader(resource.getInputStream()))){
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            Object keyObj = parser.readObject();
            pubK = converter.getPublicKey(SubjectPublicKeyInfo.getInstance(keyObj));
        }catch (Exception e) {
        	 Logger.getLogger(XmlSignatureUtill.class.getName()).log(Level.SEVERE, null, e);
		}
        return pubK;
    }
    
//    public PrivateKey getPrivateKeyFromPEMfile() {
//        Security.addProvider(new BouncyCastleProvider());
//        ClassPathResource resource = new ClassPathResource(FILE_PATH + PRIVATE_PEM);
//        final String privateKeyPassword = null;
//        PasswordFinder finder = () -> {
//                if (privateKeyPassword != null) {
//                    return privateKeyPassword.toCharArray();
//                } else {
//                    return new char[0];
//                }
//            };
//        try(PEMReader reader = new PEMReader(new InputStreamReader(resource.getInputStream()), finder);) {
//            PrivateKey privateK = null;
//            KeyPair pair = (KeyPair) reader.readObject();
//            privateK = pair.getPrivate();
//            return privateK;   
//        } catch (IOException ex) {
//            Logger.getLogger(XmlSignatureUtill.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    
    public PrivateKey getPrivateKeyFromPEMfileNEW(String password) {
    	Security.addProvider(new BouncyCastleProvider());
        ClassPathResource resource = new ClassPathResource(FILE_PATH + PRIVATE_PEM);
        final String privateKeyPassword = password;
        PrivateKey privK = null;
        PasswordFinder finder = () -> {
            if (privateKeyPassword != null) {
                return privateKeyPassword.toCharArray();
            } else {
                return new char[0];
            }
        };
        
        try(PEMParser parser = new PEMParser(new InputStreamReader(resource.getInputStream()))){
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            PEMDecryptorProvider decryptor = new JcePEMDecryptorProviderBuilder().build(finder.getPassword());
            Object keyObj = parser.readObject();
            if (keyObj instanceof PEMKeyPair) {
                KeyPair pair = converter.getKeyPair((PEMKeyPair) keyObj);
                privK = pair.getPrivate();
            } else if (keyObj instanceof PEMEncryptedKeyPair) {
                KeyPair keyPairEnc = converter.getKeyPair(((PEMEncryptedKeyPair) keyObj).decryptKeyPair(decryptor));
                privK = keyPairEnc.getPrivate();
            }
            
        }catch (Exception e) {
        	 Logger.getLogger(XmlSignatureUtill.class.getName()).log(Level.SEVERE, null, e);
		}
        return privK;
    }
    
//    public PublicKey getPublicKeyFromPrivate(){
//        Security.addProvider(new BouncyCastleProvider());
//        ClassPathResource resource = new ClassPathResource(FILE_PATH + PRIVATE_PEM);
//        final String privateKeyPassword = null;
//        PasswordFinder finder = () -> {
//                if (privateKeyPassword != null) {
//                    return privateKeyPassword.toCharArray();
//                } else {
//                    return new char[0];
//                }
//            };
//        try(PEMReader reader = new PEMReader(new InputStreamReader(resource.getInputStream()), finder);) {
//            PublicKey publicKey = null;
//            KeyPair pair = (KeyPair) reader.readObject();
//            publicKey = pair.getPublic();
//            return publicKey;   
//        } catch (IOException ex) {
//            Logger.getLogger(XmlSignatureUtill.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }
//    }
    
    public KeyStore getKeyStore() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        ClassPathResource certResource = new ClassPathResource(CERT_PATH + CERTIFICATE);
        KeyStore keyStore = KeyStore.getInstance(CERTIFICATE_ALGORUTHM);

        try (InputStream certIn = certResource.getInputStream()) {
            keyStore.load(certIn, getPassword());
        }
        return keyStore;
    }

    public char[] getPassword() throws IOException {
        ClassPathResource passResource = new ClassPathResource(CERT_PATH + CERTIFICATE_PASSWORD);
        char[] password = IOUtils.toCharArray(passResource.getInputStream());
        return password;
    }
}
