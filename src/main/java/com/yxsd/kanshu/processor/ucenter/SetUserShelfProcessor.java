package com.yxsd.kanshu.processor.ucenter;

import com.yxsd.kanshu.api.IChapterApi;
import com.yxsd.kanshu.constant.Constants;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.product.model.Chapter;
import com.yxsd.kanshu.service.IUserShelfService;
import com.yxsd.kanshu.ucenter.model.UserShelf;
import com.yxsd.kanshu.utils.ResultSender;
import com.yxsd.kanshu.utils.UserUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 *
 * Description: 设置用户书架信息
 *
 * @version 1.0 2017年12月28日 by hushengmeng 创建
 */
@Component("hapisetUserShelfprocessor")
public class SetUserShelfProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(SetUserShelfProcessor.class);

	@Resource
	private IChapterApi chapterApi;

	@Resource(name="userShelfService")
	IUserShelfService userShelfService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String bookId = request.getParameter("bookId");
		String chapterId = request.getParameter("chapterId");
		//自动购买标识 0：不自动购买 1：自动购买
		String autoBuy = request.getParameter("autoBuy");
		//类型 0：浏览历史 1：加入书架
		String type = request.getParameter("type");
		String token = request.getParameter("token");

		if(StringUtils.isBlank(bookId) || StringUtils.isBlank(token) || StringUtils.isBlank(type)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		String userId = UserUtils.getUserIdByToken(token);
		if(StringUtils.isBlank(userId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}

		try{
			//获取章节列表
			List<Chapter> chapters = this.chapterApi.getChaptersByBookId(Long.parseLong(bookId),Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);
			UserShelf userShelf = this.userShelfService.findUniqueByParams("userId",userId,"bookId",bookId);
			if(userShelf != null){
				if(userShelf.getType() != 1){
					userShelf.setType(Integer.parseInt(type));
				}
				if(StringUtils.isNotBlank(autoBuy)){
					userShelf.setAutoBuy(Integer.parseInt(autoBuy));
				}

				if(StringUtils.isBlank(chapterId)){
					userShelf.setChapterId(chapters.get(0).getChapterId());
					userShelf.setIdx(chapters.get(0).getIdx());
				}else{
					Chapter chapter = chapterApi.getChapterById(Long.parseLong(chapterId),0,Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);
					userShelf.setChapterId(chapter.getChapterId());
					userShelf.setIdx(chapter.getIdx());
				}
				userShelf.setMaxChapterId(chapters.get(chapters.size() - 1).getChapterId());
				userShelf.setMaxChapterIdx(chapters.get(chapters.size() - 1).getIdx());
				userShelf.setUpdateDate(new Date());
				this.userShelfService.update(userShelf);
			}else{
				userShelf = new UserShelf();
				userShelf.setBookId(Long.parseLong(bookId));
				userShelf.setUserId(Long.parseLong(userId));
				userShelf.setType(Integer.parseInt(type));
				if(StringUtils.isNotBlank(autoBuy)){
					userShelf.setAutoBuy(Integer.parseInt(autoBuy));
				}

				if(StringUtils.isBlank(chapterId)){
					userShelf.setChapterId(chapters.get(0).getChapterId());
					userShelf.setIdx(chapters.get(0).getIdx());
				}else{
					Chapter chapter = chapterApi.getChapterById(Long.parseLong(chapterId),0,Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);
					userShelf.setChapterId(chapter.getChapterId());
					userShelf.setIdx(chapter.getIdx());
				}
				userShelf.setMaxChapterId(chapters.get(chapters.size() - 1).getChapterId());
				userShelf.setMaxChapterIdx(chapters.get(chapters.size() - 1).getIdx());
				userShelf.setCreateDate(new Date());
				userShelf.setUpdateDate(new Date());
				userShelfService.save(userShelf);
			}
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误：" + request.getRequestURL() + "?" + request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
