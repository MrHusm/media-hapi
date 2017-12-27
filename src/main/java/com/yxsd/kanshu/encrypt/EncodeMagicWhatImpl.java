package com.yxsd.kanshu.encrypt;



import java.io.UnsupportedEncodingException;

public class EncodeMagicWhatImpl implements IEncode {
	static final String MASK_WHAT_12 = "QbHOJTzMIspl";
	static final String MASK_WHAT_24 = "7GA9/rvX1KuC283PegmhNjiU";
	public static final int INTERNAL_TYPE_1 = 1;
	public static final int INTERNAL_TYPE_2 = 2;

	private int m_nInternal;
	private int m_internal;// type

	EncodeMagicWhatImpl(int type) {
		m_internal = type;
		switch (m_internal) {
		case INTERNAL_TYPE_1:
			m_nInternal = 12;
			break;
		case INTERNAL_TYPE_2:
			m_nInternal = 24;
			break;
		}
	}

	public boolean encode(byte[] src, int nLen) {
		int i = 0;
		for (i = 0; i + m_nInternal - 1 < nLen; i += m_nInternal) {
			magicByte(src, i);
		}
		// 对于不足的处理
		if (i < nLen)
			handleRemain(src, i, nLen - i);
		return true;
	}

	public void handleRemain(byte[] src, int pos, int len) {
		byte[] mask = null;
		try {
			if (m_internal == INTERNAL_TYPE_1) {
				mask = MASK_WHAT_12.getBytes("UTF-8");
			} else {
				mask = MASK_WHAT_24.getBytes("UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < len; i++) {
			src[pos + i] ^= mask[i];
		}

	}

	public boolean decode(byte[] src, int nLen) {
		int i = 0;
		for (i = 0; i + m_nInternal - 1 < nLen; i += m_nInternal) {
			reMagicByte(src, i);
		}
		// 对于不足的处理
		if (i < nLen)
			handleRemain(src, i, nLen - i);
		return true;
	}

	public void magicByte(byte [] src, int pos)
	{
		for(int i = 0; i < m_nInternal; i++)
		{
			encodeByte(src, pos + i);
		}

		for(int i = 0; i < m_nInternal; i+= 2)
		{
			swap(src, pos + i, pos + i + 1);
		}
		maskByte(src, pos);
	}

	private void swap(byte[] src, int x, int y) {
		byte tmp = src[x];
		src[x] = src[y];
		src[y] = tmp;
	}

	public void reMagicByte(byte[] src, int pos) {
		maskByte(src, pos);

		for (int i = 0; i < m_nInternal; i += 2) {
			swap(src, pos + i, pos + i + 1);
		}

		for (int i = 0; i < m_nInternal; i++) {
			decodeByte(src, pos + i);
		}
	}

	public void maskByte(byte[] src, int pos) {
		handleRemain(src, pos, m_nInternal);
	}

	public void encodeByte(byte[] src, int pos) {
		// 右移2位，低2位放高2位
		byte tmp = src[pos];
		byte lowBit = (byte) (tmp & 0x03); // 获取低两位
		lowBit <<= 6; // 低两位左移6位
		tmp >>= 2; // 高6位右移2位
		tmp &= 0x3f; // 高两位清零
		tmp |= lowBit;
		src[pos] = tmp;
	}

	public void decodeByte(byte[] src, int pos) {
		byte tmp = src[pos];

		// 将高两位取出，放到低两位
		byte highBit = (byte) (tmp & 0xC0);
		highBit >>= 6;
		highBit &= 0x03;

		tmp <<= 2;
		tmp &= 0xfc;
		tmp |= highBit;
		src[pos] = tmp;
	}
}
