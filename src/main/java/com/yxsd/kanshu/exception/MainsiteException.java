package com.yxsd.kanshu.exception;

import com.yxsd.kanshu.utils.ConfigPropertieUtils;

/**
 * 主站异常处理.
 * @author xuye
 *
 */
public class MainsiteException extends ApiException {

	public static final int REPEAT_APPEND_EBOOK_ERROR_CODE = 100;
	
	private static final long serialVersionUID = 3679709488597486956L;
	
	private static final String CUST_ADDRESS_ERROR_PARAM = ConfigPropertieUtils.getString("MainsiteException.custaddress4", "参数提供不完整或不正确");
	
	private static final String CUST_ADDRESS_ERROR_OVERSTEP_5 = ConfigPropertieUtils.getString("MainsiteException.custaddress2", "该客户编号的保存地址已超过5条");
	
	private static final String CUST_ADDRESS_ERROR_SAME_ADDRESS = ConfigPropertieUtils.getString("MainsiteException.custaddress3", "存在完全相同的地址信息");
	

	public MainsiteException(int code) {
		super(code);
	}

	public MainsiteException(int code, String message) {
		super(code, message);
	}
	
	public static MainsiteException checkoutError(int errorCode) {
		return new MainsiteException(errorCode, "结算失败");
	}
	/**
	 * 添加地址薄异常处理.
	 * 
	 */
	public static MainsiteException addCustAddressError(int errorCode) {
		switch (errorCode) {
		case 1:
			return new MainsiteException(4, CUST_ADDRESS_ERROR_PARAM);
		case 2:
			return new MainsiteException(2, CUST_ADDRESS_ERROR_OVERSTEP_5);
		case 3:
			return new MainsiteException(3, CUST_ADDRESS_ERROR_SAME_ADDRESS);
		default:
			return otherError();
		}
	}
	
	/**
	 * 修改地址薄异常处理.
	 * 
	 */
	public static MainsiteException modifyCustAddressError(int errorCode) {
		switch (errorCode) {
		case 1:
			return new MainsiteException(3, CUST_ADDRESS_ERROR_PARAM);
		case 2:
			return new MainsiteException(2, CUST_ADDRESS_ERROR_SAME_ADDRESS);
		default:
			return otherError();
		}
	}
	
	/**
	 * 添加购物车异常处理.
	 * 
	 */
	public static MainsiteException appendCartError(int errorCode) {
		switch (errorCode) {
		case REPEAT_APPEND_EBOOK_ERROR_CODE: return new MainsiteException(errorCode, ConfigPropertieUtils.getString("MainsiteException.appendCartError.100", "系统错误100"));
		case 7 : return new MainsiteException(errorCode, ConfigPropertieUtils.getString("MainsiteException.appendCartError.7", "系统错误7"));
		case 4 :
		case 16 :
		case 88 :
			return new MainsiteException(8, "提示分单购买");
		case 17 : return new MainsiteException(errorCode, ConfigPropertieUtils.getString("MainsiteException.appendCartError.17", "系统错误17"));
		case 33 : return new MainsiteException(errorCode, "换购品缺货 ");
		case 34 : return new MainsiteException(errorCode, "换购品超过数量限制 ");
		case 35 : return new MainsiteException(errorCode, "换购品品类超过限制");
		case 36 : return new MainsiteException(errorCode, "换购品超过库存量");
		case 37 : return new MainsiteException(errorCode, "换购主品不足或缺货");
		case 52 : return new MainsiteException(errorCode, "购物车中大商家品SKU数量限制");
		case 99 : return new MainsiteException(errorCode, "直接购买");
		default:
			return new MainsiteException(10, ConfigPropertieUtils.getString("MainsiteException.appendCartError.10", "系统错误10"));
		}
	}
	
	/**
	 * 添加纸书购物车异常处理.
	 * 
	 */
	public static MainsiteException appendPaperCartError(int errorCode, String errorMessage) {
		switch (errorCode) {
		case 140 :
			return new MainsiteException(13001, "购物车中已经有相同商品。");
		case 10 :
		case 12 :
		case 13 :
		case 301 :
		case 103 :
		case 106 :
		case 102 :
		case 100 :
		case 104 :
		case 101 :
		case 142 :
		case 143 :
		case 207 :
		case 432 :
		case 14 :
		case 117 :
		case 420 :
		case 431 :
		case 206 :
		case 205 :
		case 305 :
		case 423 :
			return new MainsiteException(13002, "暂时缺货");
		default:
			return otherError();
		}
	}
	
	/**
	 * 更新购物车异常处理 .
	 */
	public static MainsiteException updateCartError(int errorCode, String errorMessage) {
		switch (errorCode) {
		case 806:
			return new MainsiteException(13003, "找不到购物项，更新出错");
		case 111:
			return new MainsiteException(13004, "单品电子书购买数量只能为1");
		case 102:
			return new MainsiteException(13005, "商品已下架");
		case 306:
			return new MainsiteException(13006, "部分商品库存不足");
		case 131:
			return new MainsiteException(13007, "奥莱品占库存失败");
		default:
			return otherError();
		}
	}
	
	/**
	 * 结算检查异常处理 .
	 */
	public static MainsiteException balanceCartError(int errorCode, String errorMessage) {
		switch (errorCode) {
		case 2:
			return new MainsiteException(13008, "购物车为空");
		case 502:
			return new MainsiteException(13009, "满足团购条件");
		case 504:
			return new MainsiteException(13010, errorMessage);
		case 100:
			return new MainsiteException(13011, "部分商品库存不足");
		case 210:
			return new MainsiteException(13012, "请选择赠品");
		default:
			return otherError();
		}
	}
	
	/** 获取购物车异常处理. */
	public static MainsiteException getCartError(int errorCode) {
		switch (errorCode) {
		case 2 : return new MainsiteException(2, ConfigPropertieUtils.getString("MainsiteException.getCartError.2", "系统错误2"));
		default : return MainsiteException.otherError();
		}
	}
	
	/**
	 * 
	 * 礼品卡激活异常处理.
	 * 
	 */
	public static MainsiteException ddMoneyActivateError(int errorCode) throws Exception {
		switch (errorCode) {
//		case 1 : return new MainsiteException(10, 
//				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.10", "系统错误10"));
		case 2 : return new MainsiteException(2, ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.2", "系统错误2"));
		case 3 : return new MainsiteException(3, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.3", "系统错误3")); 
		case 4 : return new MainsiteException(4, ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.4", "系统错误4"));
		case 5 : return new MainsiteException(5, ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.5", "系统错误5"));
		case 6 : return new MainsiteException(6, ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.6", "系统错误6"));
		case 7 : return new MainsiteException(7, ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.7", "系统错误7"));
		case 8 : return new MainsiteException(8, ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.8", "系统错误8"));
		case 9 : return new MainsiteException(9, ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.9", "系统错误9"));
		case 122 : return new MainsiteException(122, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.122", "系统错误122"));
		
		/*v2使用 add by wangguanhua 2014-8-8*/
		case 1: return new MainsiteException(31501, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31501", "系统错误31501"));
		case 9001: return new MainsiteException(31502, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31502", "系统错误31502"));
		case 9002: return new MainsiteException(31503, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31503", "系统错误31503"));
		case 9003: return new MainsiteException(31504, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31504", "系统错误31504"));
		case 9004: return new MainsiteException(31505, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31505", "系统错误31505"));
		case 9005: return new MainsiteException(31506, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31506", "系统错误31506"));
		case 9006: return new MainsiteException(31507, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31507", "系统错误31507"));
		case 9999: return new MainsiteException(31508, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31508", "系统错误31508"));
		case 1000: return new MainsiteException(31509, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31509", "系统错误31509"));
		case 1001: return new MainsiteException(31510, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31510", "系统错误31510"));
		case 1002: return new MainsiteException(31511, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31511", "系统错误31511"));
		case 1003: return new MainsiteException(31512, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31512", "系统错误31512"));
		case 1004: return new MainsiteException(31513, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31513", "系统错误31513"));
		case 1005: return new MainsiteException(31514, 
				ConfigPropertieUtils.getString("MainsiteException.ddMoneyActivateError.31514", "系统错误31514"));
		/*v2使用 add by wangguanhua 2014-8-8*/
		default : return otherError();
		}
	}
	
	/**
	 * 修改个人信息异常
	 * Description: 
	 * @Version1.0 2014-10-13 下午05:30:00 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param errorCode
	 * @return
	 * @throws Exception
	 */
	public static MainsiteException changePersonalInfoError(int errorCode) throws Exception {
		return new MainsiteException(errorCode,
				ConfigPropertieUtils.getString("MainsiteException.changePersonalInfoError." + errorCode, ConfigPropertieUtils.getString("MainsiteException.changePersonalInfoError.other")));
	}
	/**
	 * 
	 * 礼品卡激活异常处理.
	 * 
	 */
	public static MainsiteException getMoneyListError(int errorCode) throws Exception {
		switch (errorCode) {
		case -1 : return new MainsiteException(31120,
				ConfigPropertieUtils.getString("MainsiteException.getMoneyListError.31120", "系统错误31120"));
		case 100 : return new MainsiteException(31121,
				ConfigPropertieUtils.getString("MainsiteException.getMoneyListError.31121", "系统错误31121"));
		default : return otherError();
		}
	}
	/**
	 * 
	 * 礼品卡支付异常处理.
	 * 
	 */
	public static MainsiteException ddMoneyPayError(int errorCode) throws Exception {
		switch (errorCode) {
		case 2 : return new MainsiteException(1, "订单号无效！");
		case 3 : return new MainsiteException(2, "金额无效!");
		case 100 : return new MainsiteException(3, "金额大于用户当前所有礼品卡金额总和");
		default :  return otherError();
		}
	}
	
	/**
	 * 
	 * 使用礼券支付异常处理.
	 * 
	 */
	public static MainsiteException couponPayError(int errorCode) {
		switch (errorCode) {
		case 2 : return new MainsiteException(2, ConfigPropertieUtils.getString("MainsiteException.couponPayError.2", "系统错误2"));
		case 4 : return new MainsiteException(4, ConfigPropertieUtils.getString("MainsiteException.couponPayError.4", "系统错误4"));
		default: return otherError();
		}
	}
	/**
	 * 
	 * 礼券激活异常处理.
	 * 
	 */
	public static MainsiteException couponBindError(int errorCode) throws Exception {
		switch (errorCode) {
		case 2 : return new MainsiteException(2, ConfigPropertieUtils.getString("MainsiteException.couponBindError.2", "系统错误2"));
		case 5 : return new MainsiteException(5, ConfigPropertieUtils.getString("MainsiteException.couponBindError.5", "系统错误5"));
		case 100 : return new MainsiteException(100, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.100", "系统错误100"));
		case 6 : return new MainsiteException(6, ConfigPropertieUtils.getString("MainsiteException.couponBindError.6", "系统错误6"));
		case 7 : return new MainsiteException(7, ConfigPropertieUtils.getString("MainsiteException.couponBindError.7", "系统错误7"));
		case 8: return new MainsiteException(8, ConfigPropertieUtils.getString("MainsiteException.couponBindError.8", "系统错误8"));
		
		/*v2使用 add by wangguanhua 2014-7-21*/
		case 1: return new MainsiteException(31101, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31101", "系统错误31101"));
		case 9001: return new MainsiteException(31102, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31102", "系统错误31102"));
		case 9002: return new MainsiteException(31103, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31103", "系统错误31103"));
		case 9003: return new MainsiteException(31104, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31104", "系统错误31104"));
		case 9004: return new MainsiteException(31105, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31105", "系统错误31105"));
		case 9005: return new MainsiteException(31106, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31106", "系统错误31106"));
		case 9006: return new MainsiteException(31107, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31107", "系统错误31107"));
		case 9999: return new MainsiteException(31108, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31108", "系统错误31108"));
		case 1000: return new MainsiteException(31109, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31109", "系统错误31109"));
		case 1001: return new MainsiteException(31110, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31110", "系统错误31110"));
		case 1002: return new MainsiteException(31111, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31111", "系统错误31111"));
		case 1003: return new MainsiteException(31112, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31112", "系统错误31112"));
		case 1004: return new MainsiteException(31113, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31113", "系统错误31113"));
		case 1005: return new MainsiteException(31114, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31114", "系统错误31114"));
		case 1006: return new MainsiteException(31115, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31115", "系统错误31115"));
		case 1007: return new MainsiteException(31116, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31116", "系统错误31116"));
		case 2000: return new MainsiteException(31117, 
				ConfigPropertieUtils.getString("MainsiteException.couponBindError.31117", "系统错误31117"));
		/*v2使用 add by wangguanhua 2014-7-21*/
		
		default : return new MainsiteException(errorCode, "未知错误");
		}
	}
	
	/**
	 * 注册异常
	 * Description: 
	 * @Version1.0 2014-9-5 下午05:38:48 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param errorCode
	 * @return
	 * @throws Exception
	 */
	public static MainsiteException registerError(int errorCode) throws Exception {
		return new MainsiteException(errorCode, ConfigPropertieUtils.getString("MainsiteException.registerError." 
				+ errorCode, "系统错误" + errorCode));
	}
	/**
	 * 
	 * 获取礼券列表异常处理.
	 * 
	 */
	public static MainsiteException getCouponsError(int errorCode) throws Exception {
		switch (errorCode) {
		case 2 : return new MainsiteException(2, ConfigPropertieUtils.getString("MainsiteException.getCouponsError.2", "系统错误2"));
		case 10 : return new MainsiteException(10, ConfigPropertieUtils.getString("MainsiteException.getCouponsError.10", "系统错误10"));
		default : return otherError();
		}
	}
	
	/** 提交订单异常处理. */
	public static MainsiteException submitOrderError(int errorCode) throws Exception {
		switch (errorCode) {
		case 4 : return new MainsiteException(4, "缺货");
		case 5 : return new MainsiteException(5, "订单号异常");
		case 7 : return new MainsiteException(7, "购物车无数据或单品不存在");
		case 605 : return new MainsiteException(605, "促销品为限购品，无法多次购买");
		default : return otherError();
		}
	}
	
	/** 获取纸书结算信息异常处理. */
	public static MainsiteException getOrderFlowError(int errorCode) throws Exception {
		switch (errorCode) {
		case 3 : return new MainsiteException(3, ConfigPropertieUtils.getString("MainsiteException.getOrderFlowError.3", "系统错误3"));
		case 4 : return new MainsiteException(4, ConfigPropertieUtils.getString("MainsiteException.getOrderFlowError.4", "系统错误4"));
		default : return MainsiteException.otherError();
		}
	}
	
	/** 获取地址薄信息异常处理. */
	public static MainsiteException getShipAddressOptionsError(int errorCode) {
		switch (errorCode) {
		case 1 : return new MainsiteException(3, ConfigPropertieUtils.getString("MainsiteException.getShipAddressOptionsError.3", "系统错误3"));
		case 2 : return new MainsiteException(2, ConfigPropertieUtils.getString("MainsiteException.getShipAddressOptionsError.2", "系统错误2"));
		default: return MainsiteException.otherError();
		}
	}
	
	public static MainsiteException otherError() {
		return new MainsiteException(200, ConfigPropertieUtils.getString("MobileApiException.otherError.200"));
	}
}
