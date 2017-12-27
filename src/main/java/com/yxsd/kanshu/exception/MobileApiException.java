package com.yxsd.kanshu.exception;

import com.yxsd.kanshu.utils.ConfigPropertieUtils;

/**
 * 
 * @author yangming
 * 
 */
public class MobileApiException extends ApiException {

	private static final long serialVersionUID = 1L;

	public MobileApiException(int code) {
		super(code);
	}

	public MobileApiException(int code, String message) {
		super(code, message);
	}

	/** 连接主站api网络错误. */
	public static MobileApiException networkError() {
		return new MobileApiException(2201, ConfigPropertieUtils.getString(
				"MobileApiException.networkError.2201", "系统错误2201"));
	}

	/** 主站数据处理错误. */
	public static MobileApiException netWorkDataError() {
		return new MobileApiException(2202, ConfigPropertieUtils.getString(
				"MobileApiException.netWorkDataError.2202", "系统错误2202"));
	}

	/** 其他错误. */
	public static MobileApiException otherError() {
		return new MobileApiException(200, ConfigPropertieUtils.getString(
				"MobileApiException.otherError.200", "系统错误200"));
	}

	/** FullKey Error. */
	public static MobileApiException fullKeyError() {
		return new MobileApiException(2008, "其他错误");
	}

	/** 没有此设备关联的书籍 */
	public static MobileApiException NoThisDeviceError() {
		return new MobileApiException(2009, "没有此设备关联的书籍");
	}

	/** 用户名和密码不匹配 */
	public static MobileApiException userLoginFailed() {
		return new MobileApiException(1, ConfigPropertieUtils.getString(
				"MobileApiException.userLoginFailed.7", "用户名或密码错误,请重新输入"));
	}

	/** 用户验证失败. */
	public static MobileApiException userValidateFailed() {
		return new MobileApiException(1, ConfigPropertieUtils.getString(
				"MobileApiException.userValidateFailed.1", "系统错误1"));
	}

	/** 用户已存在. */
	public static MobileApiException userExisted() {
		return new MobileApiException(1, ConfigPropertieUtils.getString(
				"MobileApiException.userExisted.1", "系统错误1"));
	}

	/** 用户邮箱不正确. */
	public static MobileApiException userEmailIncorrect() {
		return new MobileApiException(11, ConfigPropertieUtils.getString(
				"MobileApiException.userEmailIncorrect.11", "邮箱地址不正确，请重新输入"));
	}

	/** 设备未绑定. */
	public static MobileApiException deviceUnbind() {
		return new MobileApiException(2, "设备未绑定");
	}
	
	/** 绑定设备失败. */
	public static MobileApiException bindDeviceFailed() {
		return bindDeviceFailed(2);
	}

	/** 绑定设备失败. */
	public static MobileApiException bindDeviceFailed(int errorCode) {
		return new MobileApiException(errorCode, ConfigPropertieUtils.getString(
				"MobileApiException.bindDeviceFailed." + errorCode, "系统错误"
						+ errorCode));
	}

	/** 没有找到公钥. */
	public static MobileApiException noSuchkey() {
		return new MobileApiException(80, "没有找到公钥");
	}

	/** 无权限. */
	public static MobileApiException noRight() {
		return new MobileApiException(5, ConfigPropertieUtils.getString(
				"MobileApiException.noRight.5", "系统错误5"));
	}

	/** 该书不存在. */
	public static MobileApiException noSuchBook() {
		return new MobileApiException(4, ConfigPropertieUtils.getString(
				"MobileApiException.noSuchBook.4", "系统错误4"));
	}

	/** 其他错误. */
	public static MobileApiException notReadBook() {
		return new MobileApiException(6, "不是试读电子书");
	}

	/** 设备绑定数量超过限定值. */
	public static MobileApiException exceedMaxBindNum() {
		return new MobileApiException(3, ConfigPropertieUtils.getString(
				"MobileApiException.exceedMaxBindNum.3", "系统错误3"));
	}

	/** 服务端不支持此版本. */
	public static MobileApiException unsupportThisVersion() {
		return new MobileApiException(99, ConfigPropertieUtils.getString(
				"MobileApiException.unsupportThisVersion.99", "系统错误99"));
	}
	
	/** 找不到章节文件. */
	public static MobileApiException cannotFindChapter() {
		return new MobileApiException(101, ConfigPropertieUtils.getString(
				"MobileApiException.cannotFindChapter.101", "找不到章节文件"));
	}
	
	/** 错误的下载方式. */
	public static MobileApiException wrongDownloadType() {
		return new MobileApiException(102, ConfigPropertieUtils.getString(
				"MobileApiException.cannotFindChapter.102", "错误的下载方式"));
	}

	/** 获取当当余额失败. */
	public static MobileApiException custAccountFailed() {
		return new MobileApiException(212, "获取当当余额失败");
	}

	public static MobileApiException noSuchDevice() {
		return new MobileApiException(3, "设备类型异常");
	}

	public static MobileApiException repeatPurchase() {
		return new MobileApiException(1, "产品重复购买");
	}

	public static MobileApiException ipaPayIdError() {
		return new MobileApiException(6, "未获取到对应的支付ID");
	}

	/** 支付验证失败. */
	public static MobileApiException invalidPay() {
		return new MobileApiException(2, ConfigPropertieUtils.getString(
				"MobileApiException.invalidPay.2", "系统错误2"));
	}

	/** 错误的ColumnCode */
	public static MobileApiException errorColumnCode() {
		return new MobileApiException(545, ConfigPropertieUtils.getString(
				"MobileApiException.errorColumnCode.545", "系统错误545"));
	}

	/** 高版本不支持的接口 */
	public static Exception noSupportHighVersion() {
		return new MobileApiException(-1115, "高版本不支持的接口");
	}

	/** 高版本不支持的接口 */
	public static Exception noSupportVersion() {
		return new MobileApiException(2111, "目前版本不支持，请升级!");
	}

	/** 阅读标签错误。 */
	public static Exception noSuchUser() {
		return new MobileApiException(201, ConfigPropertieUtils.getString(
				"MobileApiException.noSuchUser.1", "没有对应的此用户!"));
		// return new MobileApiException(201, "没有对应的此用户!");
	}

	/** 阅读进度参数格式不正确。 */
	public static Exception readformError() {
		return new MobileApiException(203, ConfigPropertieUtils.getString(
				"MobileApiException.readformError.1", "阅读进度参数格式不正确!"));
		// return new MobileApiException(203, "阅读进度参数格式不正确!");
	}

	/** 阅读进度签名验证不正确。 */
	public static Exception signCheckError() {
		return new MobileApiException(204, ConfigPropertieUtils.getString(
				"MobileApiException.signCheckError.1", "签名验证失败!"));
		// return new MobileApiException(204, "签名验证失败!");
	}

	/** 判断参数是不是数字 */
	public static Exception isNotNum() {
		return new MobileApiException(701, ConfigPropertieUtils.getString(
				"MobileApiException.isNotNum.1", "参数不是数字!"));
	}

	/** 判断是不是免费促销图书 */
	public static Exception thisNotPromotionFreeBooks() {
		return new MobileApiException(702, ConfigPropertieUtils.getString(
				"MobileApiException.thisNotPromotionFreeBooks.1", "这不是免费书!"));
	}

	/** 这个栏目不是免费促销栏目 */
	public static Exception thisIsNotPromotionFreeColumn() {
		return new MobileApiException(703, ConfigPropertieUtils.getString(
				"MobileApiException.thisIsNotPromotionFreeColumn.1",
				"这个栏目不是免费促销栏目!"));
	}

	/** 用户已经获取了此本书 */
	public static Exception userHasThisPromotionFreeBooks() {
		return new MobileApiException(704, ConfigPropertieUtils.getString(
				"MobileApiException.userHasThisPromotionFreeBooks.1",
				"用户已经获取过此书"));
	}

	public static Exception promotionDateError() {
		return new MobileApiException(705, ConfigPropertieUtils.getString(
				"MobileApiException.promotionDateError.1", "促销的时间设置不对"));
	}

	public static Exception notInPromotionDate() {
		return new MobileApiException(706, ConfigPropertieUtils.getString(
				"MobileApiException.notInPromotionDate.1", "现在不在促销的时间范围内"));
	}

	public static Exception promotionJsonError() {
		return new MobileApiException(707, ConfigPropertieUtils.getString(
				"MobileApiException.promotionJsonError.1", "促销信息的json串的格式不对"));
	}

	public static Exception notSupporthisType() {
		return new MobileApiException(708, ConfigPropertieUtils.getString(
				"MobileApiException.notSupporthisType.1", "促销不支持此设备类型"));
	}

	public static Exception promotionfreeEbookCountError() {
		return new MobileApiException(709, ConfigPropertieUtils.getString(
				"MobileApiException.promotionfreeEbookCountError.1",
				"促销json串中的freeEbookCount错误"));
	}

	public static Exception noRightBindFreeColumn() {
		return new MobileApiException(710, ConfigPropertieUtils.getString(
				"MobileApiException.noRightBindFreeColumn.1", "没有权限绑定图书"));
	}

	public static Exception getProductInfoError() {
		return new MobileApiException(710, ConfigPropertieUtils.getString(
				"MobileApiException.getProductInfoError.1",
				"得不到productInfo的具体信息"));
	}

	/** 设备不存在. */
	public static MobileApiException noSuchdevice() {
		return new MobileApiException(50, ConfigPropertieUtils.getString(
				"MobileApiException.noSuchdevice.1", "没有这样的设备"));
	}

	/** 渠道不存在. */
	public static MobileApiException noSuchChannel() {
		return new MobileApiException(51, ConfigPropertieUtils.getString(
				"MobileApiException.noSuchChannel.1", "没有这样的渠道"));
	}

	/** 无可用版本. */
	public static Exception noAvailableVersion() {
		return new MobileApiException(52, ConfigPropertieUtils.getString(
				"MobileApiException.noAvailableVersion.1", "无可用版本"));
	}

	/**
	 * 取消订单异常
	 * 
	 * @param msg
	 * @return
	 */
	public static Exception cancelOrderFailed(String msg) {
		return new MobileApiException(3000, msg);
	}

	/**
	 * 订单API异常
	 * 
	 * @return
	 */
	public static Exception cancelOrderAPIError() {
		return new MobileApiException(3001, "取消订单API发生异常");
	}

	public static Exception userRecomCodeError() {
		return new MobileApiException(9905, ConfigPropertieUtils.getString(
				"MobileApiException.userRecomCodeError.1", "验证码错误"));
	}

	public static Exception deviceNoisRecommended() {
		// TODO Auto-generated method stub
		return new MobileApiException(9901, ConfigPropertieUtils.getString(
				"MobileApiException.deviceNoisRecommended.1", "此设备已经被推荐"));
	}

	public static Exception canotRecommendedMyself() {
		// TODO Auto-generated method stub
		return new MobileApiException(9906, ConfigPropertieUtils.getString(
				"MobileApiException.canotRecommendedMyself.1", "自己不能推荐自己"));
	}

	public static Exception NoMyExperence() {
		// TODO Auto-generated method stub
		return new MobileApiException(8801, ConfigPropertieUtils.getString(
				"MobileApiException.NoMyExperence.1", "此用户没有阅历信息"));
	}

	/**
	 * 云同步异常范围30001-31000 云同步进度异常
	 * 
	 * @return
	 */
	public static Exception updateBookReadingProgressError() {
		return new MobileApiException(30001, "设备同步图书进度异常");
	}

	/**
	 * 云同步异常范围30001-31000 设备同步图书书签，笔记异常
	 * 
	 * @return
	 */
	public static Exception updateBookReadInfoError() {
		return new MobileApiException(30002, "设备同步图书书签，笔记异常");
	}

	/**
	 * 云同步异常范围30001-31000 设备同步图书书签，笔记异常
	 * 
	 * @return
	 */
	public static Exception updateBookReadInfoExperence() {
		return new MobileApiException(30003, "设备同步版本时间过低");
	}

	/**
	 * 
	 * @Title: bookNotExistExperence
	 * @Description: dubbo服务引用查询失败异常
	 * @return
	 */
	public static Exception bookNotExistExperence() {
		return new MobileApiException(31300, "该图书不存在");
	}
	
	/**
	 * 
	 * @Title: bookNotExistExperence
	 * @Description: 消息中心消息不存在
	 * @return
	 */
	public static Exception messageNotExistExperence() {
		return new MobileApiException(31301, "该消息不存在");
	}
	
	/**
	 * @Title: bookReviewDynamicDistinguish
	 * @Description: 书评个人动态类型不支持
	 * @return
	 */
	public static Exception bookReviewDynamicDistinguish(){
		return new MobileApiException(32000, "个人动态类型不支持");
	}
	
	/**
	 * @Title: bookReviewDetailInvaild
	 * @Description: 书评详情不存在
	 * @return
	 */
	public static Exception bookReviewDetailInvaild(){
		return new MobileApiException(32001, "书评详情不存在");
	}
	
	/**
	 * @Title: bookReviewRepeatPriaseAction
	 * @Description: 重复赞书评
	 * @return
	 */
	public static Exception bookReviewRepeatPriaseAction(){
		return new MobileApiException(32002, "不能重复赞书评");
	}
	
	/**
	 * @Title:App端thirdpartyUserId不合法
	 * @return
	 */
	public static Exception thirdpartyUserIdInvalid(){
		return new MobileApiException(4000, "用户相关参数不合法");
	}
	
	/**
	 * @Title: noPublishBookreviewAuthority
	 * @Description: 没有发布书评权限
	 * @return
	 */
	public static Exception noPublishBookreviewAuthority(){
		return new MobileApiException(32003, "没有发布书评权限");
	}
	
}
