/**
 *
 */
package com.yxsd.kanshu.constant;

import com.yxsd.kanshu.utils.ConfigPropertieUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * 系统常量.
 * 
 * @author dangdang
 */
public abstract class Constants {

	private Constants() {
	}
	
	public static final String SESSION_USER = "currentUser";
	
	public static final String UTF_8 = "UTF-8";	
	
	public static final String GB2312 = "GB2312";
	
	public static final String GBK = "GBK";
	
	public static final String FILE_ROOT = ConfigPropertieUtils.getString("file.root");

	public static final String RES_DIR = "/res/";
	
	public static final String ITEM_DIR = "/itemlist/";
	
	public static final String RES_PATH = FILE_ROOT + RES_DIR;
	
	public static final String ITEM_PATH = FILE_ROOT + ITEM_DIR;
	
	public static final String TPL_PATH = ConfigPropertieUtils.getString("server.root") + "tpl/";
	
	public static final String BLOCK_PATH = ConfigPropertieUtils.getString("server.root") + "block/";
	
	public static final String TPL_PREVIEW_PATH = ConfigPropertieUtils.getString("server.root") + "WEB-INF/pages/preview/";

	public static final String CURRENT_SITE = "currentSite";

	public static final String TPL_FILE_SUFFIX = ".jspt";
	
	public static final String UPLOADS_DIR_NAME = "/uploads/";
	
	public static final int VERSION_RESERVATION_NUMBER = ConfigPropertieUtils.getInteger("version.reservation.number", 10);
	
	public static final String MAINSITE_API_URL_ODD = ConfigPropertieUtils.getString("mainsite.api.url.odd");
	public static final String MAINSITE_API_URL_EVEN = ConfigPropertieUtils.getString("mainsite.api.url.even");
	
	public static final String DIGITAL_PRODUCT_CATTYPE_ID = 
			ConfigPropertieUtils.getString("action.ebookandcategory.focus.cattype", "98").trim();
	
	public static final String EBOOK_CATEGORY_ROOT_PATH = 
			ConfigPropertieUtils.getString("ebook.category.rootpath").trim();
	
	public static final int USER_BIND_MOBILE_NUM = ConfigPropertieUtils.getInteger("user.bind.mobile.num", 20);
	
	public static final String ONLINE_USER = "user";
	public static final String SHELF = "shelf";
	
	public static final String USER_ID_IN_SESSION = "idOfNowDdLoginUser";
	public static final String USER_EMAIL_IN_SESSION = "emailOfNowDdLoginUser";
	public static final String USER_NICKNAME_IN_SESSION = "nickNameOfNowDdLoginUser";
	public static final String USER_DISPLAYID_IN_SESSION = "displayIdOfNowDdLoginUser";
	public static final String USER_ANONYMOUS = "anonymous";
	
	public static final String FULL_EPUB_SUFFIX = "_full";
	public static final String FREE_EPUB_SUFFIX = "_free";
	public static final String FULL_EPUB_ENCRYPT_KEY = "fullKey";
	public static final String FREE_EPUB_ENCRYPT_KEY = "freeKey";
	
	public static final String EBOOKLIST_PATH = "ebooklist.path";
	
	public static final String CAT_EBOOK_DEFAULT = "98.01";
	
	/** 预置图书导出根目录. */
	public static final String PRESET_EBOOK_ROOT_PAHT = FILE_ROOT + ConfigPropertieUtils.getString("preset.ebook.root.path");	
	
	/** 预置图书数据存储目录. */
	public static final String PRESET_EBOOK_STORE_PAHT = PRESET_EBOOK_ROOT_PAHT + File.separator + "store";
	
	public static final String LETV_EBOOK_TYPE = ConfigPropertieUtils.getString("letv.ebook.type");
	//后台每个任务的时间间隔
	public static final Integer BGTASK_INTERVAL = new BigDecimal(ConfigPropertieUtils.getString("task.interval", "0.5"))
		.multiply(new BigDecimal(1000)).intValue();
	//后台每批任务的时间间隔
	public static final Integer BGTASK_BATCH_INTERVAL = new BigDecimal(ConfigPropertieUtils.getString("task.batch.interval", "2"))
		.multiply(new BigDecimal(1000)).intValue();
	
	//tpl、block同步脚本
	public static final String TPL_SYNCHRONIZE_SHELL = ConfigPropertieUtils.getString("tpl.synchronize.shell");
	//nginx清除缓存脚本
	public static final String NGINX_CLEAR_SHELL = ConfigPropertieUtils.getString("nginx.clear.shell");
	
	/**
	 * 系统优化模式。默认0；开启1，开启后减少对系能消耗，重大活动使用。
	 */
	public static final String SYSTEM_SIMPLE_MODE  = ConfigPropertieUtils.getString("system.simpleMode", "0");
	
	/**
	 * ddclick优化模式。默认0；开启1。
	 */
	public static final String DDCLICK_SIMPLE_MODE  = ConfigPropertieUtils.getString("ddclick.simpleMode", "0");

	/**
	 * solr 更新地理位置开关，默认0 关闭   ，1 开启
	 */
	public static final String SOLR_UPDATE_LOC_STATE = ConfigPropertieUtils.getString("solr.update.loc.state", "0");
	
	/**
	 * 客户端升级开关。默认0；开启1
	 */
	public static final String APP_UPDATE_STATE  = ConfigPropertieUtils.getString("app.update.state", "0");
	
	/**
	 * 用户的客户端来源  当当读书
	 */
	public static final String SOURCE_CLIENT_DD_READER = "DDreader";
	
	/**
	 * 用户的客户端来源  当读小说
	 */
	public static final String SOURCE_CLIENT_DD_NOVEL = "DDreader_novel";
	
	public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
	
	public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 虚拟币兑换比例
	 */
	public static final Integer VIRTUAL_CURRENCY_PROP = ConfigPropertieUtils.getInteger("virtual.currency.prop", 100);

	
	/**
	 * IP最大访问频次，用于watchdog验证
	 */
	public static final int IP_MAX_ACCESS_LEVEL = ConfigPropertieUtils.getInteger("ip.max.access.level", 200);
	
	/**
	 * 验证码缓存前缀
	 */
	public static final String CAPTCHA_CACHE_PREFIX = "captcha_cache_";
	
	/**
	 * 验证码字体
	 */
	public static final String CAPTCHA_FONTS = ConfigPropertieUtils.getString("captcha.fonts", "方正静蕾简体,方正喵呜体,方正字迹-童体毛笔简体");

	/**
	 * 创建频道时默认的简介
	 */
	public static final String CHANNEL_DESCRIPTION = "读史使人明智，读诗使人灵秀，数学使人周密，科学使人深刻……凡有所学，皆成性格";

	/**
	 * 创建频道时的默认后缀
	 */
	public static final String CHANNEL_SUFFIX = "的频道";

	/**
	 * 创建书单时默认的简介
	 */
	public static final String BOOKLIST_DESCRIPTION = "总有一些人，他们妙语连珠，见解独到，思想深邃。但这一切你都无需羡慕，通过阅读，你也可以做到。";

	/**
	 * 创建书单时的默认后缀
	 */
	public static final String BOOKLIST_SUFFIX = "的书单";

	/**
	 * VIP频道ID
	 */
	public static final String CHANNEL_HALL_ID = "channel.hall.id";

	/**
	 * 内核分片版本：3:H5大字体
	 */
	public static final int DDREADER_KERNEL_STYLE_H5_BIG = 3;

}

