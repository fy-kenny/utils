package com.example.utils;

import com.example.utils.constant.enums.EncryptionAlgorithmEnum;
import com.lambdaworks.crypto.SCryptUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.example.utils.constant.enums.EncryptionAlgorithmEnum.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public interface PasswordUtils {

    char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    static String encode(String value, EncryptionAlgorithmEnum encryptionAlgorithmEnum) {

        String encode = EMPTY;

        if (MD5.equals(encryptionAlgorithmEnum)) {
            encode = md5Encode(value);
        } else if (REVERSE_BYTES_MD5.equals(encryptionAlgorithmEnum)) {
            encode = reversBytesAndMd5Encode(value);
        } else if (MD5_TWICE.equals(encryptionAlgorithmEnum)) {
            encode = md5Encode(md5Encode(value));
        } else if (SCRYPT.equals(encryptionAlgorithmEnum)) {
            encode = scryptEncode(value);
        }

        return encode;
    }

    static String decode(String value, EncryptionAlgorithmEnum encryptionAlgorithmEnum) {

        String encode;

        if (REVERSE_BYTES_MD5.equals(encryptionAlgorithmEnum)) {
            encode = reversBytesAndMd5Decode(value);
        } else {
            throw new UnsupportedOperationException();
        }

        return encode;
    }

    static String reversBytesAndMd5Encode(String value) {

        byte[] bytes = value.getBytes();
        // reverse
        int length = bytes.length;
        byte[] reverseBytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            reverseBytes[length - i - 1] = bytes[i];
        }
        // base64
        byte[] decodeBytes = Base64.getEncoder().encode(reverseBytes);

        return new String(decodeBytes);
    }

    static String reversBytesAndMd5Decode(String value) {

        // base64
        byte[] decodeBytes = Base64.getDecoder().decode(value);

        // reverse
        int length = decodeBytes.length;
        byte[] reverseBytes = new byte[length];
        for (int i = 0; i < length; ++i) {
            reverseBytes[length - i - 1] = decodeBytes[i];
        }
        return new String(reverseBytes);
    }

    static String md5Encode(String value) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // no exception
        }
        assert md != null;
        md.update(value.getBytes());
        byte[] digest = md.digest();

        return printHexBinary(digest);
    }

    static String md5Twice(String value) {
        return md5Encode(md5Encode(value));
    }

    static String printHexBinary(byte[] bytes) {

        StringBuilder sb = new StringBuilder(bytes.length << 1);

        for (byte b : bytes) {
            sb.append(HEX_CHARS[(b >> 4) & 0xF]);
            sb.append(HEX_CHARS[(b & 0xF)]);
        }

        return sb.toString();
    }

    static String scryptEncode(String value) {
        return SCryptUtil.scrypt(value, 2, 1, 1);
    }

    static boolean scryptCheck(String value, String hash) {
        return SCryptUtil.check(value, hash);
    }
}
