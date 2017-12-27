///**
// * Description: TestDigest.java
// * All Rights Reserved.
// * @version 1.0  2015年1月23日 上午10:40:59  by 代鹏（daipeng@dangdang.com）创建
// */
//package com.dangdang.kanshu.test;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.dangdang.kanshu.constant.Constans;
//import com.dangdang.kanshu.constant.PlatformSourceEnum;
//import com.dangdang.kanshu.dao.IAnthologyDao;
//import com.dangdang.kanshu.dao.IDigestAnthologyDao;
//import com.dangdang.kanshu.dao.IDigestDao;
//import com.dangdang.kanshu.dao.IStoreUpDao;
//import com.dangdang.kanshu.model.Anthology;
//import com.dangdang.kanshu.model.Digest;
//import com.dangdang.kanshu.model.DigestAnthology;
//import com.dangdang.kanshu.model.StoreUp;
//import com.dangdang.kanshu.service.IStoreUpService;
//import com.dangdang.kanshu.service.IUcenterService;
//import ConfigPropertieUtils;
//import com.dangdang.kanshu.utils.DateUtil;
//import HttpUtils;
//import com.dangdang.kanshu.vo.StoreUpWithArticleVo;
//
///**
// * Description: 
// * All Rights Reserved.
// * @version 1.0  2015年1月23日 上午10:40:59  by 代鹏（daipeng@dangdang.com）创建
// */
//public class TestDigest {
//	
//	private IDigestDao digestDao;
//	
//	private IAnthologyDao anthologyDao;
//	
//	private IDigestAnthologyDao digestAnthologyDao;
//	
//	private IStoreUpService storeUpService;
//	
//	private IUcenterService ucenterService;
//	
//	private IStoreUpDao storeUpDao;
//	
//	@Before
//	@SuppressWarnings("resource")
//	public void init(){
//		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[] { "config/spring_common.xml" });
//		digestDao = (IDigestDao) cxt.getBean("digestDao");
//		anthologyDao = (IAnthologyDao) cxt.getBean("anthologyDao");
//		digestAnthologyDao = (IDigestAnthologyDao) cxt.getBean("digestAnthologyDao");
//		storeUpService =  (IStoreUpService) cxt.getBean("storeUpService");
//		ucenterService = (IUcenterService) cxt.getBean("ucenterService");
//		ucenterService = (IUcenterService) cxt.getBean("ucenterService");
//		storeUpDao = (IStoreUpDao) cxt.getBean("storeUpDao");
//	}
//	
//	@Test
//	public void test_saveDigest() throws InterruptedException{
//		for(int i=1; i<=16; i++){
//			Digest record = new Digest();
//			record.setAuthor("1:韩寒;2:周立波");
//			record.setMediaId(1900100991L);
//			record.setMediaChapterId(1900100991L);
//			record.setMediaName("人不风流枉少年");
//			record.setContent("家国飘零，乱世流离，大家闺秀流落江湖，三尺青锋，一剑生死。挣扎在生存与伤害，纠缠于过往和现实，她能否还有纯真的笑容和纯粹的幸福？儿时的温暖，江湖的残酷，现实的无奈，纵使是有足够的力量，可是敢决然的放弃无畏的占有？"
//	         + "滚滚狼烟，金戈铁马，将门虎子勇担道义，七尺长枪，历历寒光。爱情与亲情的两难，幸福与痛苦的交织，他可否能达成最初的愿望和最后的梦想？儿时的错误是他一生最大的愧疚，再见时已经是沧海桑田，拼尽了一生要回护的人，可会紧紧的拥抱？"
//	         + "曾经的书生意气最后是戎马倥偬，指点江山；一代的神医圣手江湖踏遍，仍旧是慈悲怜悯；所有的坚定、执著、忠诚、勇敢、慈悲、善良的灵魂在那个如夜般黑暗的时代划过最闪亮的光芒，铸就了今天的传说……");
//			record.setFirstCatetoryId(1);
//			record.setFirstCatetoryName("爱情");
//			record.setType(1);
//			record.setStars(5);
//			record.setReviewCnt(10000);
//			record.setCollectCnt(9999);
//			record.setOperator("admin" + i);
//			record.setShareCnt(1);
//			record.setTopCnt(1);
//			record.setCardTitle("你好，请拉黑我" + i);
//			record.setTitle("你好，请拉黑我" + i);
//			record.setCardRemark("家国飘零，乱世流离，大家闺秀流落江湖，三尺青锋，一剑生死。挣扎在生存与伤害，纠缠于过往和现实，她能否还有纯真的笑容和纯粹的幸福" + i);
//			record.setShowStartDate(DateUtil.addDate(new Date(), -1));
//			record.setCreateDate(DateUtil.addDate(new Date(), -2));
//			record.setIsShow(1);
//			//偶数时，图文并茂
//			if(i % 2 == 0){
//				record.setCardType(1);
//				record.setPic1Path("http://b.hiphotos.baidu.com/image/w%3D400/sign=16ba0a1684d6277fe912333818391f63/08f790529822720e9d487c5778cb0a46f31fab9d.jpg");
//			}else{
//				record.setCardType(0);
//			}
//			record.setSignIds("1:爱情;2:恐怖");
//			record.setDayOrNight(1);
//			record.setMood(1);
//			record.setWeight(10);
//			record.setLastUpdateDate(new Date());
//			record.setColumnId(100L);
//			record.setColumnName("测试ColumnName");
//			digestDao.insert(record);
//		}
//	}
//	
//	@Test
//	public void test_getDigestContentById(){
//		Digest digest = digestDao.getDigestContentById(1L);
//		Assert.assertNotNull(digest);
//	}
//	
//	@Test
//	public void test_update_digest(){
//		Digest digest = digestDao.findDigestById(1L);
//		Integer reviewCnt = digest.getReviewCnt();
//		if(reviewCnt == null){
//			reviewCnt = 0;
//		}
//		digest.setReviewCnt(reviewCnt+1);
//		int effect = digestDao.update(digest);
//		Assert.assertTrue(effect > 0);
//	}
//	
//	@Test
//	public void test_saveAnthology(){
//		Anthology anthology = new Anthology();
//		anthology.setAnthologyName("优雅文集选");
//		anthology.setAnthologyPic("http://d.hiphotos.baidu.com/image/w%3D310/sign=e0a18a27a864034f0fcdc4079fc27980/b999a9014c086e06b6cf7bb101087bf40bd1cbe1.jpg");
//		anthology.setCreateDate(new Date());
//		anthology.setCustId(11112L);
//		anthology.setCustName("美女");
//		anthology.setPlatform("FP_P");
//		int effectRow = anthologyDao.insert(anthology);
//		System.out.println(anthology.getAnthologyId());
//		Assert.assertTrue(anthology.getAnthologyId() > 0);
//		Assert.assertTrue(effectRow > 0);
//	}
//	
//	@Test
//	public void test_batchInsertDigestAnthology(){
//		Long anthologyId = 1111L;
//		List<Long> digestIds = new ArrayList<Long>();
//		for(long i=11; i<=20; i++){
//			digestIds.add(i);
//		}
//		List<DigestAnthology> records = new ArrayList<DigestAnthology>();
//		for(Long digestId:digestIds){
//			DigestAnthology record = new DigestAnthology();
//			record.setAnthologyId(anthologyId);
//			record.setDigestId(digestId);
//			record.setCreateDate(new Date());
//			records.add(record);
//		}
//		int effectRows = digestAnthologyDao.batchInsert(records);
//		Assert.assertTrue(effectRows > 0);
//	}
//	
//	@Test
//	public void test_queryDigestAnthologyByAnthologyIdAndDigestIds(){
//		Long anthologyId = 1111L;
//		List<Long> digestIds = new ArrayList<Long>();
//		digestIds.add(1L);
//		digestIds.add(2L);
//		digestIds.add(3L);
//		digestIds.add(100000L);
//		Map<Long, DigestAnthology> retMap = digestAnthologyDao.queryDigestAnthologyByAnthologyIdAndDigestIds(anthologyId, digestIds);
//		Assert.assertNotNull(retMap);
//	}
//	
//	@Test
//	public void test_queryDigestsBySignId(){
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("signId", 12);
//		paramMap.put("lasteDate", new Date());
//		paramMap.put("pageSize", 10);
//		List<Digest> digests = digestDao.queryDigestsBySignId(paramMap);
//		Assert.assertNotNull(digests);
//	}
//	
//	@Test
//	public void test_queryDigestsByAuthorId(){
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("authorId", 8780);
//		paramMap.put("lasteDate", new Date());
//		paramMap.put("pageSize", 10);
//		List<Digest> digests = digestDao.queryDigestsByAuthorId(paramMap);
//		Assert.assertNotNull(digests);
//	}
//	
//	@Test
//	public void test_uploadAnthologyBackGround() throws IOException{
//		String uploadUrl = ConfigPropertieUtils.getString("anthology.background.suffix.upload");
//		File file = new File("D://test.jpg");
//		InputStream is = new FileInputStream(file);
//		byte[] bytes = new byte[is.available()];
//		int i = is.read(bytes);
//		System.out.println(bytes);
//		System.out.println(i);
//		
//		StringBuffer sb = new StringBuffer(uploadUrl);
//		sb.append("?filesuffix=testUpload").append("&upload_ip=10.255.209.147&operator_type=1&operator_name=admin");
//		String json1 = HttpUtils.getContentByPost(sb.toString(), bytes);
//		System.out.println(json1);
//		Assert.assertNotNull(json1);
//	}
//	
//	@Test
//	public void test_save_storeup(){
//		StoreUp su = new StoreUp();
//		su.setCustId(529326406L);
//		su.setTargetId(99999L);
//		su.setType(Constans.STORE_UP_DIGEST);
//		su.setPlatform(PlatformSourceEnum.FP_P.getSource());
//		su.setUserName("test");
//		su.setNickName("test");
//		Date suDate = DateUtil.addMillSecond(new Date(), -1 * 1000);
//		su.setStoreDate(DateUtil.format(suDate, DateUtil.DATE_PATTERN));
//		int result = storeUpService.save(su);
//		Assert.assertTrue(result > 0);
//	}
//	
//	@Test
//	public void test_batchInsert(){
//		List<StoreUp> storeUpList = new ArrayList<StoreUp>();
//		for(int i=0; i<5; i ++){
//			StoreUp su = new StoreUp();
//			su.setType(Constans.STORE_UP_DIGEST);
//			su.setTargetId(999999L+i);
//			//搓开批量收藏时 的收藏时间
//			Date suDate = DateUtil.addMillSecond(new Date(), -i * 1000);
//			su.setStoreDate(DateUtil.format(suDate, DateUtil.DATE_PATTERN));
//			su.setCustId(123456789L);
//			su.setUserName("test");
//			su.setNickName("test");
//			su.setPlatform(PlatformSourceEnum.DDDS_P.getSource());
//			storeUpList.add(su);
//		}
//		int result = storeUpService.batchInsert(storeUpList);
//		Assert.assertTrue(result > 0);
//	}
//	
//	@Test
//	public void test_batchIsStoreUp(){
//		List<Long> targetIdLongs = new ArrayList<Long>();
//		targetIdLongs.add(999999L);
//		targetIdLongs.add(1000000L);
//		targetIdLongs.add(1000001L);
//		List<Long> list = storeUpService.batchIsStoreUp(123456789L, targetIdLongs, Constans.STORE_UP_DIGEST, PlatformSourceEnum.DDDS_P.getSource());
//		Assert.assertNotNull(list);
//		Assert.assertEquals(3, list.size());
//	}
//	
//	@Test
//	public void test_getStoreUpWithArticleVoList(){
//		List<StoreUpWithArticleVo> list = ucenterService.getStoreUpWithArticleVoList(50098052L, "article", "DDDS-P", new Date(), 10);
//		Assert.assertNotNull(list);
//	}
//	
//	@Test
//	public void test_getStoreUpProductRecords(){
//		Map<String, Object> paramsMap = new HashMap<String, Object>();
//		paramsMap.put("custId", 123456789L);
//		paramsMap.put("type", Constans.STORE_UP_DIGEST);
//		paramsMap.put("targetIds", 999999L);
//		paramsMap.put("platform", PlatformSourceEnum.DDDS_P.getSource());
//		paramsMap.put("pageSize", 10);
//		List<StoreUp> list = storeUpDao.getStoreUpProductRecords(paramsMap);
//		System.out.println(list);
//		Assert.assertNotNull(list);
//	}
//	
//}
