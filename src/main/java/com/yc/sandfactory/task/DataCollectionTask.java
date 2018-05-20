package com.yc.sandfactory.task;

import com.yc.sandfactory.entity.ChengZhongRecord;
import com.yc.sandfactory.util.JsonMapperProvide;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author: yc
 * @date: 2018-1-13.
 */
public class DataCollectionTask implements Runnable {

  public Logger logger = LoggerFactory.getLogger(this.getClass());

  // 操作本地数据库
  @Autowired
  private NutDao nutDao;

  @Autowired
  private JedisPool jedisPool;

  // 沙场
  private String sandName;
  // 工作站
  private String workName;
  // 地磅
  private String truckName;

  private NutDao dataDao;

  public String getSandName() {
    return sandName;
  }

  public void setSandName(String sandName) {
    this.sandName = sandName;
  }

  public String getWorkName() {
    return workName;
  }

  public void setWorkName(String workName) {
    this.workName = workName;
  }

  public String getTruckName() {
    return truckName;
  }

  public void setTruckName(String truckName) {
    this.truckName = truckName;
  }

  public NutDao getDataDao() {
    return dataDao;
  }

  public void setDataDao(NutDao dataDao) {
    this.dataDao = dataDao;
  }

  //@Autowired
  //private MasMessagePublisher masMessagePublisher;

  @Override
  public void run() {
    try {
      // 先查询自己该磅的最新记录，作为条件去远程收集数据
      Cnd cnd = Cnd.NEW();
      cnd.where().and("sandName", "=", sandName)
          .and("workName", "=", workName)
          .and("truckName", "=", truckName);
      cnd.orderBy("id", "desc");

      ChengZhongRecord latestRecord = nutDao.fetch(ChengZhongRecord.class, cnd);

      String sqlStr = "SELECT dbo.czsjk.序号, dbo.czsjk.车号, dbo.czsjk.货名, dbo.czsjk.发货, dbo.czsjk.收货, "
          + " dbo.czsjk.规格, dbo.czsjk.备用2, dbo.czsjk.出入类型, dbo.czsjk.毛重, dbo.czsjk.皮重, "
          + " dbo.czsjk.净重, dbo.czsjk.扣杂, dbo.czsjk.扣率, dbo.czsjk.单价, dbo.czsjk.金额, "
          + " dbo.czsjk.备用3, dbo.czsjk.备用4, dbo.czsjk.司机, dbo.czsjk.监磅, dbo.czsjk.日期, "
          + " dbo.czsjk.时间, dbo.czsjk.台号, dbo.czsjk.操作员, dbo.czsjk.备注, "
          + " dbo.czsjk.毛重时间, dbo.czsjk.皮重时间, dbo.czsjk.上传, dbo.czsjk.打印, "
          + " dbo.czsjk.净重1, dbo.czsjk.扣杂系数, dbo.czsjk.扣率系数, dbo.czsjk.结算, "
          + " dbo.czsjk.结算编号, dbo.czsjk.操作员1, dbo.czsjk.开票, dbo.czsjk.一次日期, "
          + " dbo.czsjk.台号1, dbo.czsjk.余款 "
          + " FROM dbo.czsjk ";
      Sql querSql = null;
      if (null == latestRecord) {
        sqlStr = sqlStr + " ORDER BY dbo.czsjk.毛重时间 asc";
        // TODO 第一次同步，数据量可能太大
        querSql = Sqls.create(sqlStr);
      } else {
        sqlStr = sqlStr + " where dbo.czsjk.毛重时间 > @mzsj ORDER BY dbo.czsjk.毛重时间 asc ";
        querSql = Sqls.create(sqlStr);
        querSql.params().set("mzsj", latestRecord.getMzsj());
      }

      querSql.setCallback(new SqlCallback() {
        @Override
        public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
          List<ChengZhongRecord> list = new LinkedList<ChengZhongRecord>();
          ChengZhongRecord czrd = null;
          while (rs.next()) {
            czrd = new ChengZhongRecord();
            czrd.setSandName(sandName);
            czrd.setWorkName(workName);
            czrd.setTruckName(truckName);

            czrd.setXh(rs.getString("序号"));

            //车号(车辆号码)
            czrd.setCh(rs.getString("车号"));

            //货名（货物名称）
            czrd.setHm(rs.getString("货名"));

            //发货
            czrd.setFh(rs.getString("发货"));

            //收货
            czrd.setSh(rs.getString("收货"));

            //规格
            czrd.setGg(rs.getString("规格"));

            //备用2
            czrd.setBy2(rs.getString("备用2"));

            //出入类型
            czrd.setCrlx(rs.getString("出入类型"));

            //毛重
            czrd.setMz(rs.getFloat("毛重"));

            //皮重
            czrd.setPz(rs.getFloat("皮重"));

            //净重
            czrd.setJz(rs.getFloat("净重"));

            //扣杂
            czrd.setKz(rs.getFloat("扣杂"));

            //扣率
            czrd.setKl(rs.getFloat("扣率"));

            //单价
            czrd.setDj(rs.getFloat("单价"));

            //金额
            czrd.setJe(rs.getFloat("金额"));

            //备用3
            czrd.setBy3(rs.getString("备用3"));

            //备用4
            czrd.setBy4(rs.getString("备用4"));

            //司机
            czrd.setSiji(rs.getString("司机"));

            //监磅
            czrd.setJb(rs.getString("监磅"));

            //日期
            czrd.setRq(rs.getString("日期"));

            //时间
            czrd.setShijian(rs.getString("时间"));

            //台号
            czrd.setTh(rs.getString("台号"));

            //操作员
            czrd.setCzy(rs.getString("操作员"));

            //备注
            czrd.setBz(rs.getString("备注"));

            //毛重时间
            czrd.setMzsj(rs.getString("毛重时间"));

            //皮重时间
            czrd.setPzsj(rs.getString("皮重时间"));

            //上传
            czrd.setSc(rs.getString("上传"));

            //打印
            czrd.setDy(rs.getString("打印"));

            //净重1
            czrd.setJz1(rs.getFloat("净重1"));

            //扣杂系数
            czrd.setKzxs(rs.getFloat("扣杂系数"));

            //扣率系数
            czrd.setKlxs(rs.getFloat("扣率系数"));

            //结算
            czrd.setJs(rs.getString("结算"));

            //结算编号
            czrd.setJsbh(rs.getString("结算编号"));

            //操作员1
            czrd.setCzy1(rs.getString("操作员1"));

            //开票
            czrd.setKp(rs.getString("开票"));

            //一次日期
            czrd.setYcrq(rs.getString("一次日期"));
            //台号1
            czrd.setTh1(rs.getString("台号1"));

            //余款
            czrd.setYk(rs.getFloat("余款"));

            list.add(czrd);
          }
          return list;
        }
      });
      dataDao.execute(querSql);

      List<ChengZhongRecord> czrdList = querSql.getList(ChengZhongRecord.class);

      if (czrdList.isEmpty()) {
        return;
      }

      ChengZhongRecord czRecord = null;
      for (ChengZhongRecord czrd: czrdList ) {
        // 存入本地数据库
        czRecord = nutDao.insert(czrd);

        Jedis jedis = jedisPool.getResource();
        List<String> msgIdList = jedis.lrange("sf_user_msg_id", 0, -1);
        for (String msgId : msgIdList ) {
          jedis.rpush(msgId, JsonMapperProvide.alwaysMapper().writeValueAsString(czRecord));
        }
        jedis.close();
      }

    } catch (Exception e) {
      logger.error("采集【{}】沙场,【{}】站点,【{}】地磅 数据出现异常！！！", sandName, workName, truckName,
          e);
    }
  }

  public static void main(String[] args) {
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    scheduler.scheduleAtFixedRate(new DataCollectionTask(), 5, 10, SECONDS);
  }
}
