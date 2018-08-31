package com.yxsd.kanshu.processor.product;

import com.yxsd.kanshu.api.IBookApi;
import com.yxsd.kanshu.constant.ErrorCodeEnum;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.processor.BaseApiProcessor;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.service.IDriveBookService;
import com.yxsd.kanshu.utils.ResultSender;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Description: 获取图书ID获取广告是否显示状态
 * 限免和免费的图书显示广告
 *
 * @version 1.0 2018年3月28日 by hushengmeng 创建
 */
@Component("hapigetAdFlagByBookIdprocessor")
public class GetAdFlagByBookIdProcessor extends BaseApiProcessor {

	private static final Logger logger = LoggerFactory.getLogger(GetAdFlagByBookIdProcessor.class);

	@Resource
	private IBookApi bookApi;

	@Resource(name="driveBookService")
	private IDriveBookService driveBookService;

	@Override
	protected void process(HttpServletRequest request,HttpServletResponse response, ResultSender sender) throws Exception {
		//入参
		String bookId = request.getParameter("bookId");

		if(StringUtils.isBlank(bookId)){
			sender.fail(ErrorCodeEnum.ERROR_CODE_10002.getErrorCode(),
					ErrorCodeEnum.ERROR_CODE_10002.getErrorMessage(), response);
			return;
		}
		try{
			sender.put("isShow",0);
			Book book = bookApi.getBookById(Long.parseLong(bookId));
			if(book.getIsFree() == 0){
				//免费图书
				sender.put("isShow",1);
			}else{
				DriveBook driveBook = driveBookService.getDriveBookByCondition(9,Long.parseLong(bookId),1);
				if(driveBook != null){
					//限免图书
					sender.put("isShow",1);
				}
			}
			//关闭广告
			sender.put("isShow",0);
			sender.success(response);
		}catch (Exception e){
			logger.error("系统错误："+ request.getRequestURL()+"?"+request.getQueryString());
			e.printStackTrace();
			sender.fail(ErrorCodeEnum.ERROR_CODE_10008.getErrorCode(), ErrorCodeEnum.ERROR_CODE_10008.getErrorMessage(), response);
		}
	}
}
