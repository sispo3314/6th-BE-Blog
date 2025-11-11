package com.leets.backend.blog.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class TokenHash {
    private TokenHash(){}

    public static byte[] sha256(String plaintext){
        try{
            MessageDigest md= MessageDigest.getInstance("SHA-256");
            return md.digest(plaintext.getBytes(StandardCharsets.UTF_8));
        }catch(NoSuchAlgorithmException e){
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
