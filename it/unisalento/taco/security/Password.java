/*
 * Progetto Taco - Progettazione Software
 * Autori: Giulio Albanese, Tommaso Paladini
 * Professore: Luca Mainetti
 */

package it.unisalento.taco.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
    La classe Password può essere istanziata solamente per criptare password.
    L'attributo password non è accessibile in alcun modo, può essere solamente ottenuto tramite hash()
*/
public class Password {
    private String password;

    public Password(String password){
        this.password = password;
    }

    public String hash() throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes);
        byte[] hash = digest.digest(bytes);
        BigInteger big = new BigInteger(1,hash);
        password = big.toString(16);
        return password;
    }
}