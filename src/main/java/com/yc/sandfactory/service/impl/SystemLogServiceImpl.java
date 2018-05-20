package com.yc.sandfactory.service.impl;

import com.yc.sandfactory.entity.SystemLog;
import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.ISystemLogService;
import com.yc.sandfactory.util.Constants;
import com.yc.sandfactory.util.DateTimeUtil;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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
public class SystemLogServiceImpl implements ISystemLogService {

  @Autowired
  private NutDao nutDao;

  @Override
  public LitePaging<SystemLog> queryForPage(String startTime, String endTime,
      String action,  String search, Integer pageNo, Integer pageSize) {
    Pager pager = nutDao.createPager(pageNo, pageSize);

    Criteria cri = Cnd.cri();

    if (StringUtils.isNotBlank(startTime)) {
      cri.where().and("time", ">=", DateTimeUtil.dateTimeStrToLong(startTime + " 00:00:00"));
    }

    if (StringUtils.isNotBlank(endTime)) {
      cri.where().and("time", "<", DateTimeUtil.dateTimeStrToLong(endTime + " 23:59:59"));
    }

    if (StringUtils.isNotBlank(action)) {
      cri.where().andEquals("action", action);
    }

    if (StringUtils.isNotBlank(search)) {
      cri.where().andLike("content", '%' + search + '%');
    }

    cri.getOrderBy().desc("time");

    List<SystemLog> list = nutDao.query(SystemLog.class, cri, pager);
    int count = nutDao.count(SystemLog.class, cri);

    LitePaging<SystemLog> litePaging = new LitePaging<>();
    litePaging.setDataList(list);
    litePaging.setPageNo(pageNo);
    litePaging.setPageSize(pageSize);
    litePaging.setTotalCount(count);

    return litePaging;
  }

  @Override
  public void addLog(String ip, Constants.ENUM_LOG_TYPE logType, String content) {
    SystemLog systemLog = new SystemLog();
    User user = (User) SecurityUtils.getSubject().getSession().getAttribute("USER_INFO");
    systemLog.setManagerId(user.getId());
    systemLog.setManagerName(user.getName());
    systemLog.setManagerIp(ip);
    systemLog.setContent(content);
    systemLog.setTime(System.currentTimeMillis());
    systemLog.setAction(String.valueOf(logType.value));
    nutDao.insert(systemLog);
  }
}
