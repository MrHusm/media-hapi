package com.yxsd.kanshu.processor;

import com.yxsd.kanshu.profile.ProccessorProfile;
import com.yxsd.kanshu.utils.ConfigPropertieUtils;
import com.yxsd.kanshu.utils.ResultSender;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Description: 移动Api接口的基类 All Rights Reserved.
 * 
 * @version 1.0 2015年1月15日 上午9:22:06 by wang.zhiwei（wangzhiwei@dangdang.com）创建
 */
public abstract class BaseApiProcessor extends ApiProcessor {

	private final String isLogSave = ConfigPropertieUtils
			.getString("ddclick.mongo.save");


	public final Integer RETAIN_TYPE_EBOOK=1; //大当购物车,结算：电子书
	public final Integer RETAIN_TYPE_OTHER=2; //大当购物车,结算：非电子书
	public final Integer RETAIN_TYPE_ALL=0; //大当购物车,结算：所有

	@Resource(name = "masterRedisTemplate")
	protected RedisTemplate<String, Object> masterRedisTemplate;

	/**
	 * Description: 分页处理是否还有下一页
	 * 
	 * @Version1.0 2015年1月22日 下午8:05:19 by 代鹏（daipeng@dangdang.com）创建
	 * @param collections
	 * @param size
	 * @return
	 */
	protected boolean hasNextPage(Collection<?> collections, int size) {
		if (CollectionUtils.isEmpty(collections)) {
			return false;
		} else if (collections.size() < size) {
			return false;
		}
		return true;
	}

	/**
	 * Description: 转换客户端传过来的ids成集合类型(逗号分隔)
	 * 
	 * @Version1.0 2015年2月5日 下午3:51:39 by 代鹏（daipeng@dangdang.com）创建
	 * @param ids
	 * @return
	 */
	protected List<Long> transferIdStrToList(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idArr = ids.split(",");
			if (idArr != null && idArr.length > 0) {
				List<Long> idList = new ArrayList<Long>();
				for (String id : idArr) {
					idList.add(Long.parseLong(id));
				}
				return idList;
			}
		}
		return new ArrayList<Long>();
	}

	/**
	 * 获取参数.
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	protected String get(HttpServletRequest request, String key,
			String defaultValue) {
		if ("deviceSerialNo".equals(key) || "deviceSn".equals(key)) {
			if (StringUtils.isNotBlank(request.getParameter("deviceSn"))) {
				return request.getParameter("deviceSn");
			} else if (StringUtils.isNotBlank(request
					.getParameter("deviceSerialNo"))) {
				return request.getParameter("deviceSerialNo");
			} else if (StringUtils.isNotBlank(request.getParameter("macAddr"))) {
				return request.getParameter("macAddr");
			} else {
				return defaultValue;
			}
		}
		return StringUtils.isBlank(request.getParameter(key)) ? defaultValue
				: request.getParameter(key);
	}

	protected Integer getVersion(HttpServletRequest request) {
		Integer version = 1;
		if (StringUtils.isNotBlank(request.getParameter("version"))) {
			Integer temp = Integer.valueOf(request.getParameter("version"));
			if (temp != 1 && temp != 2) {
				log.error("接口版本参数错误 只能传送 【1】 or【2】 默认修改为【1】");
			} else {
				version = temp;
			}
		}
		return version;
	}

	/**
	 * 通过app.env配置项判断运行环境，测试环境根据API_CONFIG表的配置，可以返回假数据.
	 */
	@Override
	@ProccessorProfile
	public void handle(HttpServletRequest request,
					   HttpServletResponse response, ResultSender sender, String action)
			throws Exception {
		long startTime = System.currentTimeMillis();
		process(request, response, sender);
		long useTime = System.currentTimeMillis() - startTime;
	}

	/**
	 * 保存证书内容到ddclick数据库中.
	 * 
	 * @param certidata
	 * @param sender
	 */
	protected void addCertificationToDDClick(String certidata,
			ResultSender sender) {
		sender.putSuccessStatus();
		sender.setResultContent(certidata);
	}
}
