package com.yxsd.kanshu.service.impl;

import com.yxsd.kanshu.constant.RedisKeyConstants;
import com.yxsd.kanshu.dao.IBaseDao;
import com.yxsd.kanshu.dao.IDriveBookDao;
import com.yxsd.kanshu.portal.model.DriveBook;
import com.yxsd.kanshu.service.IDriveBookService;
import com.yxsd.kanshu.utils.PageFinder;
import com.yxsd.kanshu.utils.Query;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hushengmeng on 2017/7/4.
 */
@Service(value="driveBookService")
public class DriveBookServiceImpl extends BaseServiceImpl<DriveBook, Long> implements IDriveBookService {

    @Resource(name="driveBookDao")
    private IDriveBookDao driveBookDao;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,DriveBook> masterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,DriveBook> slaveRedisTemplate;

    @Resource(name = "masterRedisTemplate")
    private RedisTemplate<String,List<DriveBook>> listMasterRedisTemplate;

    @Resource(name = "slaveRedisTemplate")
    private RedisTemplate<String,List<DriveBook>> listSlaveRedisTemplate;

    @Override
    public IBaseDao<DriveBook> getBaseDao() {
        return driveBookDao;
    }

    @Override
    public List<DriveBook> getDriveBooks(Integer type,Integer status) {
        String key =String.format(RedisKeyConstants.CACHE_DRIVE_BOOK_KEY, type, status);
        List<DriveBook> driveBooks = null;
        if(listMasterRedisTemplate.hasKey(key)){
            driveBooks = listSlaveRedisTemplate.opsForValue().get(key);
        }
        if(CollectionUtils.isEmpty(driveBooks)){
            driveBooks = this.findListByParams("type",type,"status",status);
            if(CollectionUtils.isNotEmpty(driveBooks)){
                listMasterRedisTemplate.opsForValue().set(key,driveBooks,1,TimeUnit.DAYS);
            }
        }
        return driveBooks;
    }

    @Override
    public DriveBook getDriveBookByCondition(Integer type, Long bookId,Integer status) {
        String key = String.format(RedisKeyConstants.CACHE_DRIVE_BOOK_ONE_KEY,type,bookId,status);
        DriveBook driveBook = slaveRedisTemplate.opsForValue().get(key);
        if(driveBook == null){
            List<DriveBook> driveBooks = this.findListByParams("type",type,"bookId",bookId,"status",status);
            if(CollectionUtils.isNotEmpty(driveBooks)) {
                driveBook = driveBooks.get(0);
                masterRedisTemplate.opsForValue().set(key,driveBook,1,TimeUnit.DAYS);
            }
        }
        return driveBook;
    }

    @Override
    public PageFinder<DriveBook> findPageWithCondition(Integer type, Query query) {
        List<DriveBook> driveBooks = this.getDriveBooks(type,1);
        PageFinder<DriveBook> pageFinder = new PageFinder<DriveBook>(query.getPage(),query.getPageSize(), 0);
        if(CollectionUtils.isNotEmpty(driveBooks)){
            int end = (query.getOffset() + query.getPageSize()) > driveBooks.size() ? driveBooks.size() : (query.getOffset() + query.getPageSize());
            List<DriveBook> datas = driveBooks.subList(query.getOffset(), end);
            pageFinder = new PageFinder<DriveBook>(query.getPage(),query.getPageSize(), driveBooks.size(), datas);
        }
        return pageFinder;
    }
}
