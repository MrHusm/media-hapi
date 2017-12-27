//package com.dangdang.digital.test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.alibaba.dubbo.common.utils.StringUtils;
//import com.alibaba.fastjson.JSONArray;
//import com.dangdang.kanshu.exception.ApiException;
//import com.dangdang.kanshu.model.Media;
//import com.dangdang.kanshu.model.MediaChapter;
//import com.dangdang.kanshu.service.IMediaChapterService;
//import com.dangdang.kanshu.service.IMediaService;
//
//public class TestMedia {
//	private static IMediaChapterService mediaChapterService;
//
//	private static IMediaService mediaService;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[] { "config/spring_common.xml" });
//		mediaChapterService = (IMediaChapterService) cxt.getBean("mediaChapterService");
//		mediaService = (IMediaService) cxt.getBean("mediaService");
//
//	}
//
//	@Test
//	public void testSave() throws ApiException {
//		// 入参：图书id集合
//		String mediaIdsStr = "[1,2]";
//		List<Long> mediaIdList = JSONArray.parseArray(mediaIdsStr, Long.class);
//		System.out.println(mediaChapterService.getChapterCountByMediaIds(mediaIdList));
//
//	}
//
//	public void testGetAllChapterByMediaIdProcessor() throws ApiException {
//
//		// 入参：图书id
//		String mediaIdStr = "1";
//		// 入参：查询起始章节
//		String startStr = "3";
//		// 入参：查询章节数量
//		String countStr = "2";
//		try {
//			Long mediaId = Long.valueOf(mediaIdStr);
//			List<Map<String, Object>> allChapters = new ArrayList<Map<String, Object>>();
//			List<MediaChapter> chapterList = null;
//
//			if (StringUtils.isBlank(startStr) && StringUtils.isBlank(countStr)) {
//				chapterList = mediaChapterService.getAllChapterByMediaIds(mediaId);
//			} else if (StringUtils.isBlank(countStr)) {
//				Integer start = Integer.valueOf(startStr) - 1;
//				Integer count = Integer.MAX_VALUE;
//				chapterList = mediaChapterService.getAllChapterByMediaIds(mediaId, start, count);
//			} else {
//				Integer start = Integer.valueOf(startStr) - 1;
//				Integer count = Integer.valueOf(countStr);
//				chapterList = mediaChapterService.getAllChapterByMediaIds(mediaId, start, count);
//			}
//
//			for (MediaChapter chapter : chapterList) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("chapterId", chapter.getId());
//				map.put("isCharge", chapter.getIsFree());
//				map.put("chapterName", chapter.getTitle());
//				map.put("indexOrder", chapter.getIndexOrder());
//				allChapters.add(map);
//			}
//
//			Integer total = mediaChapterService.getCountByMediaId(mediaId);
//			System.out.println(allChapters);
//			System.out.println(total);
//
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void testGetEncrypKeyByMediaProcessor() throws ApiException {
//
//		// 入参：书籍id
//		String mediaIdStr = "1";
//		// 入参：加密公钥
//		String publicKey = "+pbkey+";
//
//		Long mediaId = Long.valueOf(mediaIdStr);
//		Media media = mediaService.get(mediaId);
//		// 获取秘钥的加密方式
//		String encryptType = "1";
//		// TODO 通过公钥方式
//		String encrypkey = encryptType + publicKey + media.getEncrypkey();
//		System.out.print(encrypkey);
//
//	}
//}
