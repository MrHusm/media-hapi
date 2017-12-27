package com.yxsd.kanshu.constant;

/**
 * 接口相关的常量.
 * @author yangming
 *
 */
public final class ApiConstant {
	
	private ApiConstant() {
	}

	/** 移动端支付ID. */
	public static final int PAY_ID = -1;
	
	/** 网上支付方式 . */
	public static final int PAY_TYPE = 1;	
	
	public static final int SHOP_ID = 0;
	
	public static final int CUST_TYPE = 1;
	
	public static final int SHIP_TYPE = 1;
	
	/** 第一版证书版本号. */
	public static final String FIRST_CERTIFICATE_VERSION_NO = "1.0";
	
	/** 第二版证书版本号. */
	public static final String SECOND_CERTIFICATE_VERSION_NO = "1.1";
	
	/** 终端设备类型:iPhone. */
	public static final String IPHONE = "iPhone";
	
	/** 终端设备类型:iPad. */
	public static final String IPAD = "iPad";
	
	/** 终端设备类型:Android. */
	public static final String ANDROID = "Android";
	
	/** 终端设备类型:Eink. */
	public static final String EINK = "Eink";
	
	/** 终端设备类型:HTML5. */
	public static final String HTML5 = "html5";
	
	/** 产品类型:电子书、纸书混合. */
	public static final String PRODUCT_TYPE_ALL = "0";
	
	/** 产品类型：电子书. */
	public static final String PRODUCT_TYPE_EBOOK = "1";
	
	/** 产品类型：纸书. */
	public static final String PRODUCT_TYPE_PAPER = "2";
	
	/** ddClick对应的部门id */
	public static final Integer DDCLICK_DEPARTMENT_ID = 2001;
	
	/** ddClick在mongodb中的备份库名 */
	public static final String DDCLICK_IN_MONGODB_BAKNAME = "click_bak";
}
