package com.yc.sandfactory.service.impl;

import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.IUserService;
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
public class UserServiceImpl implements IUserService {

  @Autowired
  private NutDao nutDao;

  @Override
  public LitePaging<User> queryUserForPage(User user, Integer pageNo, Integer pageSize) {
    Pager pager = nutDao.createPager(pageNo, pageSize);

    Criteria cri = Cnd.cri();

    // 名称
    if (StringUtils.isNotBlank(user.getName())) {
      cri.where().and("name", "like", "%"+user.getName()+"%");
    }

    cri.getOrderBy().desc("id");
    List<User> list = nutDao.query(User.class, cri, pager);
    int count = nutDao.count(User.class, cri);

    LitePaging<User> litePaging = new LitePaging<>();
    litePaging.setDataList(list);
    litePaging.setPageNo(pageNo);
    litePaging.setPageSize(pageSize);
    litePaging.setTotalCount(count);

    return litePaging;
  }

  @Override
  public User getUser(int id) {
    return nutDao.fetch(User.class, id);
  }

  @Override
  public void delUser(int id) {
    nutDao.delete(User.class, id);
  }

  @Override
  public User addUser(User user) {
    return nutDao.insert(user);
  }

  @Override
  public void updateUser(User user) {
     nutDao.update(user);
  }
}
