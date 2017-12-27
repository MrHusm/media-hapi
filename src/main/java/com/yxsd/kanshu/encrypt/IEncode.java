package com.yxsd.kanshu.encrypt;



public interface IEncode {
	public boolean encode(byte[] src, int nLen);
	public boolean decode(byte[] src, int nLen);
}
