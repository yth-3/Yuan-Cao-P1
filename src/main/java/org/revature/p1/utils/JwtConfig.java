package org.revature.p1.utils;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Properties;

public class JwtConfig {
    private final int timeToExpiration = 60 * 60 * 1000;
    private final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private final Properties properties = new Properties();

    private final Key signingKey;

    public JwtConfig() {

        try {
            properties.load(new FileReader("src/main/resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] salt = DatatypeConverter.parseBase64Binary(properties.getProperty("salt"));
        signingKey = new SecretKeySpec(salt, algorithm.getJcaName());
    }

    public int getTimeToExpiration() {
        return timeToExpiration;
    }

    public SignatureAlgorithm getAlgorithm() {
        return algorithm;
    }

    public Properties getProperties() {
        return properties;
    }

    public Key getSigningKey() {
        return signingKey;
    }

    @Override
    public String toString() {
        return "JwtConfig{" +
                "timeToExpiration=" + timeToExpiration +
                ", algorithm=" + algorithm +
                ", properties=" + properties +
                ", signingKey=" + signingKey +
                '}';
    }
}
