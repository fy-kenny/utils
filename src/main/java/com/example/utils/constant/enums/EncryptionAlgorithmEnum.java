package com.example.utils.constant.enums;

/**
 * @author Kenny Fang
 * @since 0.0.2
 */
public enum EncryptionAlgorithmEnum {

    MD5,
    /**
     * reverse bytes of string and then encrypt with md5
     */
    REVERSE_BYTES_MD5,
    MD5_TWICE,
    SCRYPT;
}
