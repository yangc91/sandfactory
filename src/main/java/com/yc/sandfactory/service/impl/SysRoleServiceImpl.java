package com.yc.sandfactory.service.impl;

import com.yc.sandfactory.entity.SysRole;
import com.yc.sandfactory.entity.SystemLog;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.ISysRoleService;
import com.yc.sandfactory.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO hsun 完成注释
 *
 * @author hsun
 * @version 1.0
 * @since 2018/5/19 下午11:12
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private NutDao nutDao;

    @Override
    public void addSysRole(SysRole sysRole) {
        this.nutDao.insert(sysRole);
    }

    @Override
    public LitePaging<SysRole> queryForPage(String name, int pageNo, int pageSize) {
        Pager pager = this.nutDao.createPager(pageNo, pageSize);

        Criteria cri = Cnd.cri();

        if (StringUtils.isNotBlank(name)) {
            cri.where().andLike("name", '%' + name + '%');
        }

        List<SysRole> list = this.nutDao.query(SysRole.class, cri, pager);
        int count = this.nutDao.count(SysRole.class, cri);

        LitePaging<SysRole> litePaging = new LitePaging<>();
        litePaging.setDataList(list);
        litePaging.setPageNo(pageNo);
        litePaging.setPageSize(pageSize);
        litePaging.setTotalCount(count);

        return litePaging;
    }
}
