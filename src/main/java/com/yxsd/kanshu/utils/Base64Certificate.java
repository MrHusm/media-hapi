package com.yxsd.kanshu.utils;




public class Base64Certificate{
	


    
    private static int BYTES_PER_UNENCODED_BLOCK = 3;
    private static int BYTES_PER_ENCODED_BLOCK = 4;
    
    /** Mask used to extract 6 bits, used when encoding */
    private static final int SixBitMask = 0x3f; 
    
    /** padding char */
    private static final byte PAD = '=';
    
    /**
     * This array is a lookup table that translates 6-bit positive integer index values into their "Base64 Alphabet"
     * equivalents as specified in Table 1 of RFC 2045.
     *
     */
    private static final byte[] EncodeTable = { 
    	'6','Q','b','H','O','J','T','z','M','I','s','p','l','L',
    	'4','F','U','x','0','k','q','y','t','7','G','A','9','/',
    	'r','v','X','1','K','u','C','2','8','3','P','e','g','m',
    	'h','N','j','i','w','+','W','Z','5','V','a','f','n','D',
    	'd','o','S','c','R','B','Y','E'};
    
    
    public static byte[] encode(byte[] in) {
        
        int modulus = 0;
        int bitWorkArea = 0;
        int numEncodedBytes = (in.length/BYTES_PER_UNENCODED_BLOCK)*BYTES_PER_ENCODED_BLOCK 
                + ((in.length%BYTES_PER_UNENCODED_BLOCK == 0 )?0:4);

        byte[] buffer = new byte[numEncodedBytes]; 
        int pos = 0;
        
        for (int i = 0; i < in.length; i++) {
            modulus = (modulus+1) % BYTES_PER_UNENCODED_BLOCK;
            int b = in[i];
            
            if (b < 0) 
                b += 256;
            
            bitWorkArea = (bitWorkArea << 8) + b; //  BITS_PER_BYTE
            if (0 == modulus) { // 3 bytes = 24 bits = 4 * 6 bits to extract
                buffer[pos++] = EncodeTable[(bitWorkArea >> 18) & SixBitMask];
                buffer[pos++] = EncodeTable[(bitWorkArea >> 12) & SixBitMask];
                buffer[pos++] = EncodeTable[(bitWorkArea >> 6) & SixBitMask];
                buffer[pos++] = EncodeTable[bitWorkArea & SixBitMask];
            }
        }
        
        switch (modulus) { // 0-2
        case 1 : // 8 bits = 6 + 2
            buffer[pos++] = EncodeTable[(bitWorkArea >> 2) & SixBitMask]; // top 6 bits
            buffer[pos++] = EncodeTable[(bitWorkArea << 4) & SixBitMask]; // remaining 2 
            buffer[pos++] = PAD;
            buffer[pos++] = PAD;
            break;

        case 2 : // 16 bits = 6 + 6 + 4
            buffer[pos++] = EncodeTable[(bitWorkArea >> 10) & SixBitMask];
            buffer[pos++] = EncodeTable[(bitWorkArea >> 4) & SixBitMask];
            buffer[pos++] = EncodeTable[(bitWorkArea << 2) & SixBitMask];
            buffer[pos++] = PAD;
            break;
        }
        
        return buffer;
    }


}
