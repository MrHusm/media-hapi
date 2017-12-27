package com.yxsd.kanshu.processor;

import com.yxsd.kanshu.api.IBookApi;
import com.yxsd.kanshu.product.model.Book;
import com.yxsd.kanshu.utils.ResultSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Description: 5.0电子书单品页接口 All Rights Reserved.
 *
 * @version 1.0 2015年6月24日 下午6:40:10 by 许文轩（xuwenxuan@dangdang.com）创建
 */
@Component("hapigetMediaprocessor")
public class GetMediaProcessor extends BaseApiProcessor {

	@Resource
	private IBookApi bookApi;

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response, ResultSender sender) throws Exception {
		Book book = bookApi.getBookById(43257L);

		System.out.println(book);
	}
}
