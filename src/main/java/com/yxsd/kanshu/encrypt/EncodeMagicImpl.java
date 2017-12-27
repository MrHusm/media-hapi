package com.yxsd.kanshu.encrypt;



public class EncodeMagicImpl implements IEncode{
	public boolean encode(byte[] src, int nLen) {
		// 4bit为一组操作
		for (int i = 0; i + 3 < nLen; i += 4) {
			magicByte(src, i);
		}
		return true;
	}
	public boolean decode(byte[] src, int nLen) {
		// 4bit为一组操作
		for (int i = 0; i + 3 < nLen; i += 4) {
			reMagicByte(src, i);
		}
		return true;
	}
	private void swap(byte[] src, int x, int y){
		byte tmp = src[x];
		src[x] = src[y];
		src[y] = tmp;
	}
	public void magicByte(byte [] src, int pos)
	{
		// 对字节内部换位
		encodeByte(src, pos + 0);
		encodeByte(src, pos + 1);
		encodeByte(src, pos + 2);
		encodeByte(src, pos + 3);
//	 	// 交换字节位置
		swap(src, pos + 0, pos + 2);
		swap(src, pos + 1, pos + 3);
		// mask[4];
		maskByte(src, pos);
	}

	public void reMagicByte(byte [] src, int pos)
	{
		maskByte(src, pos);
		swap(src, pos + 0, pos + 2);
		swap(src, pos + 1, pos + 3);
	//
		decodeByte(src, pos + 0);
		decodeByte(src, pos + 1);
		decodeByte(src, pos + 2);
		decodeByte(src, pos + 3);
	}

	public void maskByte(byte[] src, int pos) {
		src[pos + 0] ^= 'd';
		src[pos + 1] ^= 'q';
		src[pos + 2] ^= 'm';
		src[pos + 3] ^= 'c';
	}

	private void encodeByte(byte [] src, int pos)
	{
		// 右移2位，低2位放高2位
		byte tmp = src[pos];
		byte lowBit = (byte) (tmp & 0x03); // 获取低两位
		lowBit <<= 6; // 低两位左移6位
		tmp >>= 2; // 高6位右移2位
		tmp &= 0x3f; // 高两位清零
		tmp |= lowBit;
		src[pos] = tmp;
	}

	private void decodeByte(byte [] src, int pos)
	{
		// 将高两位取出，放到低两位
		byte tmp = src[pos];
		byte highBit = (byte) (tmp & 0xC0);
		highBit >>= 6;
		highBit &= 0x03;

		tmp <<= 2;
		tmp &= 0xfc;
		tmp |= highBit;
		src[pos] = tmp;
	}

}
