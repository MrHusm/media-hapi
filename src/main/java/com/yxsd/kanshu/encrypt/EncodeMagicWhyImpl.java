package com.yxsd.kanshu.encrypt;



public class EncodeMagicWhyImpl implements IEncode{
	public boolean encode(byte[] src, int nLen) {
		// 6bit为一组操作
		for (int i = 0; i + 5 < nLen; i += 6) {
			magicByte(src, i);
		}
		return true;
	}
	public boolean decode(byte[] src, int nLen) {
		// 6bit为一组操作
		for (int i = 0; i + 5 < nLen; i += 6) {
			reMagicByte(src, i);
		}
		return true;
	}
	
	private void swap(byte[] src, int x, int y){
		byte tmp = src[x];
		src[x] = src[y];
		src[y] = tmp;
	}
	
	private void magicByte(byte [] src, int pos){
	    swap(src, pos + 0, pos + 3);
	    swap(src, pos + 1, pos + 4);
	    swap(src, pos + 2, pos + 5);

	    for(int i= 0; i < 6; i++)
	    {
	    	encodeByte(src, pos + i);
	    }

	    maskByte(src, pos);
	}

	private void maskByte(byte[] src, int pos) {
		src[pos + 0] ^= 'd';
		src[pos + 1] ^= 'r';
		src[pos + 2] ^= 'q';
		src[pos + 3] ^= 's';
		src[pos + 4] ^= 'm';
		src[pos + 5] ^= 'j';
	}

	private void reMagicByte(byte [] src, int pos){
		maskByte(src, pos);

		swap(src, pos + 0, pos + 3);
	    swap(src, pos + 1, pos + 4);
	    swap(src, pos + 2, pos + 5);
	    
	    for(int i= 0; i < 6; i++)
	    {
	    	decodeByte(src, pos + i);
	    }
	}

	private void encodeByte(byte [] src, int pos) {
		byte tmp = src[pos];
		// 暂存高5位
		byte highBit = (byte) (tmp & 0xf8);
		// 左移5位
		tmp <<= 5;
		highBit >>= 3;
		highBit &= 0x1f;// 右移可能高位被补1，所以对高位清零
		tmp |= highBit;
		src[pos] = tmp;
	}

	private void decodeByte(byte [] src, int pos) {
		byte tmp = src[pos];
		// 11111 000 -> 000 11111
		byte highBit = (byte) (tmp & 0xe0);
		highBit >>= 5;
		highBit &= 0x07;
		tmp <<= 3;
		tmp |= highBit;
		src[pos] = tmp;
	}
}
