package com.company.project.test;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;

import com.company.project.component.KeyBytesSupplier;

@RunWith(JUnitPlatform.class)
public class KeyBytesSupplierTest {
	
	private static KeyBytesSupplier keySuplaer;
	private static final String PUBLIC_KEY_PATH = "files/public.pem";
	private static final String PRIVATE_KEY_PATH = "files/private.der";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		keySuplaer = new KeyBytesSupplier();
	}

	@Test
	public void testGetPublicKeyBytes() throws IOException {
		ClassPathResource resource = new ClassPathResource(PUBLIC_KEY_PATH);
		byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
		byte[] publicKey = (new String(bytes)
				.replaceAll("-----BEGIN PUBLIC KEY-----", "")
				.replaceAll("-----END PUBLIC KEY-----", "")
				.replaceAll("\\n", "")).getBytes();
		Assertions.assertArrayEquals(publicKey, keySuplaer.getPublicKeyBytes());
	}

	@Test
	public void testGetPrivateKeyBytes() throws IOException {
		ClassPathResource resource = new ClassPathResource(PRIVATE_KEY_PATH);
		byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
		Assertions.assertArrayEquals(bytes, keySuplaer.getPrivateKeyBytes());
	}

}
