package se.bth.aspm.groupone.teamchat.client.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("sslSocketManager")
public class SSLSocketManager {

	private TrustManager[] trustManagers;
	private FileInputStream fis;

	private final static Logger LOGGER = Logger.getLogger("SSLSocketManager");

	public SSLSocketManager() {
		init();
	}

	private void init() {
		loadCAPublic(getPathCAPublicKeystore(), getCAPublicKeystorePass());
		setTrustManagersProperty();
	}

	public SSLSocket createSSLSocket(String serverIp, int serverPort) throws KeyManagementException, NoSuchAlgorithmException, UnknownHostException, IOException {
		return createSSLSocket(serverIp, serverPort, trustManagers, null);
	}

	private SSLSocket createSSLSocket(String host, int port, TrustManager[] trustManagers, KeyManager[] keyManagers) throws NoSuchAlgorithmException, KeyManagementException, UnknownHostException, IOException {

		// use keys to create SSLSoket
		SSLContext ssl;
		SSLSocket socket = null;
			ssl = SSLContext.getInstance("TLSv1.2");
			ssl.init(keyManagers, trustManagers, SecureRandom.getInstance("SHA1PRNG"));
			socket = (SSLSocket) ssl.getSocketFactory().createSocket(host, port);
		return socket;
	}

	public void setTrustManagersProperty() {
		System.setProperty("javax.net.ssl.trustStore", getPathCAPublicKeystore());
		System.setProperty("javax.net.ssl.trustStorePassword", new String(getCAPublicKeystorePass()));
	}

	private static String getPathCAPublicKeystore() {
		return System.getProperty("keystore.ca.file") == null ? Paths.get("").toAbsolutePath().toString()
				+ File.separator + "certs" + File.separator + "client" + File.separator + "ca-public.jks"
				: System.getProperty("keystore.ca.file");
	}

	private static char[] getCAPublicKeystorePass() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			ClassLoader classLoader = ClassLoader.getSystemClassLoader(); 
			input = classLoader.getResourceAsStream("keystore.properties");
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop.getProperty("ks.pass.public").toCharArray();
	}

	private void loadCAPublic(String path, char[] pass) {
		KeyStore serverPub;
		try {
			serverPub = KeyStore.getInstance("JKS");
			fis = new FileInputStream(path);
			serverPub.load(fis, pass);
			TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
			trustManager.init(serverPub);
			trustManagers = trustManager.getTrustManagers();
			fis.close();
		} catch (KeyStoreException e) {
			LOGGER.info("loadSeverPublic :: KeyStoreException");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.info("loadSeverPublic :: NoSuchAlgorithmException");
			e.printStackTrace();
		} catch (CertificateException e) {
			LOGGER.info("loadSeverPublic :: CertificateException");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			LOGGER.info("loadSeverPublic :: FileNotFoundException");
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.info("loadSeverPublic :: IOException");
			e.printStackTrace();
		}

	}

}
