package com.yc.sandfactory.service.impl;

import com.yc.sandfactory.bean.RecodRequestBean;
import com.yc.sandfactory.entity.ChengZhongRecord;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.IChengZhongService;
import java.util.List;
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
  public LitePaging<ChengZhongRecord> queryRecordForPage(RecodRequestBean recodRequestBean, Integer pageNo, Integer pageSize) {
    Pager pager = nutDao.createPager(pageNo, pageSize);

    Criteria cri = Cnd.cri();

    //if (StringUtils.isNotBlank(searchKey)) {
    //  searchKey = searchKey.replaceAll("_", "/_");
    //  searchKey = searchKey.replaceAll("%", "/%");
    //  cri.where()
    //      .and(Cnd.exps("name", "like", "%" + searchKey + "%")
    //          .or("packageName", "like", "%" + searchKey + "%"));
    //}

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
