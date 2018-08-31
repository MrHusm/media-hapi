package com.yxsd.kanshu.processor.product;

import com.yxsd.kanshu.api.IBookApi;
import com.yxsd.kanshu.api.IChapterApi;
import com.yxsd.kanshu.constant.Constants;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.product.model.Chapter;
import com.yxsd.kanshu.service.IBookService;
import com.yxsd.kanshu.utils.DateUtil;
import com.yxsd.kanshu.utils.ResultSender;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * Description: 获取图书详情
 *
 * @version 1.0 2018年1月19日 by hushengmeng 创建
 */
@Component("hapigetMediaprocessor")
public class GetMediaProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(GetMediaProcessor.class);

	@Resource
	private IBookApi bookApi;

	@Resource
	private IChapterApi chapterApi;

	@Resource(name="bookService")
	IBookService bookService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String bookId = request.getParameter("bookId");
		//String token = request.getParameter("token");

		if(StringUtils.isBlank(bookId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			Book book = bookApi.getBookById(Long.parseLong(bookId));

			//阅读按钮标识 0：免费试读 1：阅读
//			int readBtn = 0;
//			if(book.getIsFree() == 0) {
//				//免费图书
//				readBtn = 1;
//			}else{
//				if(StringUtils.isNotBlank(token)){
//					String userId = UserUtils.getUserIdByToken(token);
//					User user = this.userApi.getUserByUserId(Long.parseLong(userId));
//					if(user.isVip()){
//						//VIP用户
//						readBtn = 1;
//					}else{
//						DriveBook driveBook = driveBookService.getDriveBookByCondition(9,Long.parseLong(bookId),1);
//						if(driveBook != null){
//							//限免图书
//							readBtn = 1;
//						}else{
//							UserPayBook userPayBook = this.userPayBookService.findUniqueByParams("userId",userId,"bookId",bookId,"type",2);
//							if(userPayBook != null){
//								//全本购买过
//								readBtn = 1;
//							}
//						}
//					}
//				}
//			}

			String tag = book.getTag();
			List<String> tags = new ArrayList<String>();
			if(StringUtils.isNotBlank(tag)){
				tag = tag.replaceAll("\\d+","");
				tag = tag.replace(":","");
				if(StringUtils.isNotBlank(tag)){
					tags = Arrays.asList(tag.split(","));
				}
			}
			if(book.getWordCount() < 9999){
				sender.put("wordCount",book.getWordCount());
			}else{
				sender.put("wordCount",String .format("%.1f",book.getWordCount() / 10000.0)  +"万+");
			}

			if(book.getIsFull() == 1){
				//图书已完结
				sender.put("updateDay","已完结");
			}else{
				int diffDay = DateUtil.diffDate(new Date(),book.getLastChapterUpdateDate());
				if(diffDay <= 0){
					sender.put("updateDay","更新时间：刚刚");
				}else if(diffDay > 30){
					sender.put("updateDay","更新时间：1月前");
				}else{
					sender.put("updateDay","更新时间：" + diffDay+"天前");
				}
			}

			//作者写的其他书
			List<Map<String,Object>> authorBooks = bookService.getBooksByAuthorId(book.getAuthorId());
			if(CollectionUtils.isNotEmpty(authorBooks)){
				for(Map<String,Object> authorBook : authorBooks){
					if(bookId.equals(String.valueOf(authorBook.get("bookId")))){
						authorBooks.remove(authorBook);
						break;
					}
				}
			}

			//用户还看了其他书
			List<Map<String,Object>> relatedBooks = this.bookService.getHighClickBooksByCid(book.getCategorySecId());
			if(CollectionUtils.isNotEmpty(relatedBooks)){
				for(Map<String,Object> relatedBook : relatedBooks){
					if(bookId.equals(String.valueOf(relatedBook.get("bookId")))){
						authorBooks.remove(relatedBook);
						break;
					}
				}
				if(relatedBooks.size() > 12){
					relatedBooks = relatedBooks.subList(0,12);
				}
			}

			List<Chapter> chapters = this.chapterApi.getChaptersByBookId(Long.parseLong(bookId),Integer.parseInt(bookId) % Constants.CHAPTR_TABLE_NUM);

			if(CollectionUtils.isNotEmpty(chapters)){
				sender.put("maxChapterIndex",chapters.get(chapters.size()-1).getIdx());
			}
			sender.put("tags",tags);
			sender.put("authorBooks",authorBooks);
			sender.put("relatedBooks",relatedBooks);
			//sender.put("readBtn",readBtn);
			sender.put("book",book);
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
