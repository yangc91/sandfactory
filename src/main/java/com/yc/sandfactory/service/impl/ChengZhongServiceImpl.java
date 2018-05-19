package com.yc.sandfactory.service.impl;

import com.yc.sandfactory.entity.ChengZhongRecord;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.IChengZhongService;
import com.yc.sandfactory.util.DateTimeUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @project: sandfactory
 * @author: yc
 */
@Service
public class ChengZhongServiceImpl implements IChengZhongService {

  @Autowired
  private NutDao nutDao;

  @Override
  public LitePaging<ChengZhongRecord> queryRecordForPage(String startTime, String endTime, ChengZhongRecord chengZhongRecord, Integer pageNo, Integer pageSize) {
    Pager pager = nutDao.createPager(pageNo, pageSize);

    Criteria cri = Cnd.cri();

    if(StringUtils.isNotBlank(startTime)) {
      //cri.where().and("pzsj", ">=", DateTimeUtil.dateTimeStrToLong(startTime + " 00:00:00"));
      cri.where().and("pzsj", ">=", startTime + " 00:00:00");
    }

    if (StringUtils.isNotBlank(endTime)) {
      //cri.where().and("pzsj", "<", DateTimeUtil.dateTimeStrToLong(endTime + " 23:59:59"));
      cri.where().and("pzsj", "<", endTime + " 23:59:59");
    }

    // 序号
    if (StringUtils.isNotBlank(chengZhongRecord.getXh())) {
      cri.where().and("xh", "like", "%"+chengZhongRecord.getXh()+"%");
    }

    // 出入类型
    if (StringUtils.isNotBlank(chengZhongRecord.getCrlx())) {
      cri.where().and("crlx", "like", "%"+chengZhongRecord.getCrlx()+"%");
    }

    // 车辆号码
    if (StringUtils.isNotBlank(chengZhongRecord.getCh())) {
      cri.where().and("ch", "like", "%"+chengZhongRecord.getCh()+"%");
    }

    // 货名
    if (StringUtils.isNotBlank(chengZhongRecord.getHm())) {
      cri.where().and("hm", "like", "%"+chengZhongRecord.getHm()+"%");
    }

      // 供货单位
    if (StringUtils.isNotBlank(chengZhongRecord.getFh())) {
      cri.where().and("fh", "like", "%"+chengZhongRecord.getFh()+"%");
    }

    // 收货单位
    if (StringUtils.isNotBlank(chengZhongRecord.getSh())) {
      cri.where().and("sh", "like", "%"+chengZhongRecord.getSh()+"%");
    }

    cri.getOrderBy().desc("id");
    List<ChengZhongRecord> list = nutDao.query(ChengZhongRecord.class, cri, pager);
    int count = nutDao.count(ChengZhongRecord.class, cri);

    LitePaging<ChengZhongRecord> litePaging = new LitePaging<>();
    litePaging.setDataList(list);
    litePaging.setPageNo(pageNo);
    litePaging.setPageSize(pageSize);
    litePaging.setTotalCount(count);

    return litePaging;
  }

  @Override
  public ChengZhongRecord getRecord(int id) {
    return nutDao.fetch(ChengZhongRecord.class, id);
  }
}
