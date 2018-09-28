package com.company.project.component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class KeyBytesSupplier {

    private static final String FILE_PATH = "files/";
    private static final String PUBLIC = "public.pem";
    private static final String PRIVATE = "private.der";
    private static final String PRIVATE_PEM = "privkey.pem";
    private static final String CHARSET = "UTF-8";

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
}
