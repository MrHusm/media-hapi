//package com.dangdang.digital.test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Test;
//
//import com.dangdang.kanshu.model.DDClick;
//import BeanUtils;
//
//public class TestDDClick {
//
//	@Test
//	public void testCopyProperty(){
//		
//		DDClick click = new DDClick();
//		
//		Map testMap = new HashMap();
//		
//		testMap.put("asdfadsf", "tetet");
//		testMap.put("field", "{\"noDependActions\":[{\"action\":\"getMediasBySaleId\",\"alias\":\"anothername\",\"params\":{\"saleId\": 23796}},{\"action\": \"getMediasByAuthorExceptThis\",\"params\":{\"saleId\":23796}}]}");
//		
//		BeanUtils.copyProperties(click,testMap);
//		
//	}
//}
