package com.example.projet_securite_info;

import java.math.BigInteger;
import java.util.Random;

public class RSA{
    private static BigInteger p;
    private static BigInteger q;
    private static BigInteger N;
    private static BigInteger phi;
    private static BigInteger e;
    private static BigInteger d;
    private static int        bitlength = 1024;
    private static Random     r;
    private static String     mess="";

    public RSA(String m){
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        System.out.println("N: " + N);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        System.out.println("e: " + e);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
        mess = m;
    }
    public static String crypt(){
        System.out.println("Encrypting String: " + mess);
        System.out.println("String in Bytes: "
                + bytesToString(mess.getBytes()));
        // encrypt
        byte[] encrypted = encrypt(mess.getBytes());
        System.out.println("Encrypted String: " + bytesToString(encrypted));
        //decrypt
        byte[] decrypted = decrypt(encrypted);
        System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));
        return bytesToString(encrypted);
    }
    private static String bytesToString(byte[] encrypted){
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
    }

    // Encrypt message
    public static byte[] encrypt(byte[] message){
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message
    public static byte[] decrypt(byte[] message){
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }
}
