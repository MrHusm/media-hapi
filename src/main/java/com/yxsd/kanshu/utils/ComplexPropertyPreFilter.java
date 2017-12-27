package com.yxsd.kanshu.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: fastjson 针对类型的属性选择过滤器(可以跨层级)
 * @Date 2017/3/2 15:44 创建
 */

public class ComplexPropertyPreFilter implements PropertyPreFilter {

    private Map<Class<?>, String[]> includes = new HashMap();
    private Map<Class<?>, String[]> excludes = new HashMap();

    static {
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
    }

    public ComplexPropertyPreFilter() {

    }

    public ComplexPropertyPreFilter(Map<Class<?>, String[]> includes) {
        super();
        this.includes = includes;
    }

    public boolean apply(JSONSerializer serializer, Object source, String name) {

        //对象为空。直接放行
        if (source == null) {
            return true;
        }

        // 获取当前需要序列化的对象的类对象
        Class<?> clazz = source.getClass();

        // 无需序列的对象、寻找需要过滤的对象，可以提高查找层级
        // 找到不需要的序列化的类型
        for (Map.Entry<Class<?>, String[]> item : this.excludes.entrySet()) {
            // isAssignableFrom()，用来判断类型间是否有继承关系
            if (item.getKey().isAssignableFrom(clazz)) {
                String[] strs = item.getValue();

                // 该类型下 此 name 值无需序列化
                if (isHave(strs, name)) {
                    return false;
                }
            }
        }

        // 需要序列的对象集合为空 表示 全部需要序列化
        if (this.includes.isEmpty()) {
            return true;
        }

        // 需要序列的对象
        // 找到不需要的序列化的类型
        for (Map.Entry<Class<?>, String[]> item : this.includes.entrySet()) {
            // isAssignableFrom()，用来判断类型间是否有继承关系
            if (item.getKey().isAssignableFrom(clazz)) {
                String[] strs = item.getValue();
                // 该类型下 此 name 值无需序列化
                if (isHave(strs, name)) {
                    return true;
                }
            }
        }

        return false;
    }

    /*
     * 此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串
     */
    public static boolean isHave(String[] strs, String s) {

        for (int i = 0; i < strs.length; i++) {
            // 循环查找字符串数组中的每个字符串中是否包含所有查找的内容
            if (strs[i].equals(s)) {
                // 查找到了就返回真，不在继续查询
                return true;
            }
        }

        // 没找到返回false
        return false;
    }

    public Map<Class<?>, String[]> getIncludes() {
        return includes;
    }

    public void setIncludes(Map<Class<?>, String[]> includes) {
        this.includes = includes;
    }

    public Map<Class<?>, String[]> getExcludes() {
        return excludes;
    }

    public void setExcludes(Map<Class<?>, String[]> excludes) {
        this.excludes = excludes;
    }

    public static void main(String[] args){
        String json = "{\n" +
                "\"data\": {\n" +
                "\"currentDate\": \"2017-03-01 19:21:18\",\n" +
                "\"saleList\": [\n" +
                "{\n" +
                "\"isStore\": 0,\n" +
                "\"isSupportFullBuy\": 0,\n" +
                "\"mediaList\": [\n" +
                "{\n" +
                "\"authorId\": 481354,\n" +
                "\"authorPenname\": \"封央\",\n" +
                "\"avgStarLevel\": 5,\n" +
                "\"categoryIds\": \"XDYQ\",\n" +
                "\"categorys\": \"现代言情\",\n" +
                "\"channelHall\": false,\n" +
                "\"chapterCnt\": 1448,\n" +
                "\"commentNumber\": 9,\n" +
                "\"coverPic\": \"http://img60.ddimg.cn/kanshu/product/44/41/1960154441_ii_cover.jpg?version=eb49c76c-1ebc-4804-8ae5-19fade6345ce\",\n" +
                "\"descs\": \"（绝宠文）母亲结婚当晚，她被继父的儿子给上了。青涩稚嫩的女儿身在他身下绽放，被他认真的观赏着，放肆的亵玩着，羞得她情难自禁。　　他是权势滔天的帝国总裁，强势霸道，狂妄不可一世。性情高冷禁欲的他，一时兴起将她禁锢在身边，渐渐地护她成了习惯，宠她成了执念，深入骨血的痴恋让她逃无可逃。　　他说：“我允许你任性，但你必须在我允许的范围内任性。当初你把自己当生日礼物送给我了，这辈子你就是我的！从头到脚都是我的！一根头发丝也是我！”【1V1，身心干净，无小三无误会，绝对宠文！】（没有群，想私下与某央交流，可移步新浪微博：封央fy）\",\n" +
                "\"editorRecommend\": \"\",\n" +
                "\"isFull\": 0,\n" +
                "\"isStore\": 0,\n" +
                "\"mediaId\": 1960154441,\n" +
                "\"mediaType\": 1,\n" +
                "\"price\": 0,\n" +
                "\"priceUnit\": 5,\n" +
                "\"recommandWords\": \"\",\n" +
                "\"saleId\": 1980187945,\n" +
                "\"shelfStatus\": 1,\n" +
                "\"title\": \"总裁大人，体力好！\"\n" +
                "}\n" +
                "],\n" +
                "\"price\": 7282,\n" +
                "\"saleId\": 1980187945,\n" +
                "\"type\": 0\n" +
                "},\n" +
                "{\n" +
                "\"isStore\": 0,\n" +
                "\"isSupportFullBuy\": 1,\n" +
                "\"mediaList\": [\n" +
                "{\n" +
                "\"authorPenname\": \"付遥\",\n" +
                "\"avgStarLevel\": 5,\n" +
                "\"categoryIds\": \"ZGJDDXS\",\n" +
                "\"categorys\": \"中国近当代小说\",\n" +
                "\"channelHall\": false,\n" +
                "\"chapterCnt\": 146,\n" +
                "\"commentNumber\": 1,\n" +
                "\"coverPic\": \"http://img60.ddimg.cn/kanshu/product/17/87/1900581787_ii_cover.jpg?version=e8654a34-4932-428b-aaec-9d8a61a3c570\",\n" +
                "\"descs\": \"　　《输赢》将*销售策略融入到人生当中，涵盖商业运作的技巧，工作的方法及思维，战略规划的布局及人性的观察与剖析，以及我们那交织在事业与爱情当中的理想。 　　捷科、惠康两大外企决战中国市场，双方销售高手为销售业绩纷纷出招，银行超级订单势在必夺。团队该如何建设、新人该如何培养、昔日的情人骆伽变成今日的劲敌……周锐外有强敌，内有高压，此形此势如何突围？挑战对手就是挑战自己，都说“胜者王侯败者寇”，但事实真的是这样吗？\",\n" +
                "\"editorRecommend\": \"\\n\\t　　推荐购买：《输赢2》付遥继《输赢》百万畅销后2012**力作。结果有输赢，人生无胜负！\\r\\n\\n　　推荐购买：输赢全两册（百万读者推荐，2012*受期待的励志小说合集）\\r\\n\\n　　畅销百万册，2012全新修订，“摧龙六式”升级为“摧龙八式”。\\r\\n\\n　　凝聚实战派销售专家付遥18年成功经验，百万读者诚意推荐，百家企业共同选择的培训教材。同名大片即将上映。\\r\\n\\n　　*精彩畅快的商战，*深沉隐忍的爱恨，*真实残酷的职场，*实用的销售技能……\\r\\n\\n　　这些年，不回头，不认输，我们一起在“输赢”中成长。\\n\",\n" +
                "\"isFull\": 1,\n" +
                "\"isStore\": 0,\n" +
                "\"lowestPrice\": 11.95,\n" +
                "\"mediaId\": 1900581787,\n" +
                "\"mediaType\": 2,\n" +
                "\"originalPrice\": 40,\n" +
                "\"paperBookId\": 24004175,\n" +
                "\"paperBookPrice\": 4000,\n" +
                "\"price\": 1195,\n" +
                "\"priceUnit\": 13,\n" +
                "\"promotionList\": [\n" +
                "{\n" +
                "\"promotionPrice\": 11.95,\n" +
                "\"stockStatus\": 0\n" +
                "}\n" +
                "],\n" +
                "\"recommandWords\": \"\",\n" +
                "\"saleId\": 1900581787,\n" +
                "\"salePrice\": 11.95,\n" +
                "\"shelfStatus\": 1,\n" +
                "\"subTitle\": \"\",\n" +
                "\"title\": \"输赢\"\n" +
                "}\n" +
                "],\n" +
                "\"saleId\": 1900581787,\n" +
                "\"type\": 0\n" +
                "}\n" +
                "],\n" +
                "\"systemDate\": \"1488367278303\"\n" +
                "},\n" +
                "\"status\": {\n" +
                "\"code\": 0\n" +
                "},\n" +
                "\"systemDate\": 1488367278303\n" +
                "}";
        JSONObject o = (JSONObject)JSON.parse(json);
        JSONArray arr = o.getJSONObject("data").getJSONArray("saleList");

        Map<Class<?>, String[]> map = new HashMap<Class<?>, String[]>();
        map.put(JSONObject.class, new String[]{"descs","editorRecommend","categorys","subTitle","title","authorId","authorPenname","recommandWords"});
        ComplexPropertyPreFilter ss = new ComplexPropertyPreFilter();
        ss.setExcludes(map);

        String ddd = JSONArray.toJSONString(arr,ss);

        System.err.println(ddd);

    }
}
