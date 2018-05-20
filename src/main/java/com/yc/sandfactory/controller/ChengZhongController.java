package com.yc.sandfactory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yc.sandfactory.entity.ChengZhongRecord;
import com.yc.sandfactory.entity.User;
import com.yc.sandfactory.page.DataTablesParameters;
import com.yc.sandfactory.page.DataTablesReply;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.IChengZhongService;
import com.yc.sandfactory.util.JsonMapperProvide;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @project: sandfactory
 * @author: yc
 */
@RestController
@RequestMapping("/record")
public class ChengZhongController {

  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IChengZhongService chengZhongService;

  @Autowired
  private JedisPool jedisPool;

  @RequestMapping(value = "/list")
  public Object list(String startTime, String endTime, ChengZhongRecord condition, HttpServletResponse response)
      throws JsonProcessingException {
    logger.info("调用称重list请求的输入参数：recodRequestBean：{}",
        JsonMapperProvide.alwaysMapper().writeValueAsString(condition)
    );

    DataTablesParameters tables = DataTablesParameters.newInstance();

    LitePaging<ChengZhongRecord> pagination =
        chengZhongService.queryRecordForPage(startTime, endTime, condition,
            tables.getPage(), tables.getLength());

    return new DataTablesReply(pagination, tables.getDraw());
  }

  @RequestMapping(value = "/get")
  public Object get(Integer id) {
    logger.info("调用称重详情接口请求的输入参数：id：{}", id);
    ChengZhongRecord record = chengZhongService.getRecord(id);
    return record;
  }

  @RequestMapping(value = "/newRecord")
  public Object newRecord() {
    Map<String, Object> result = new HashMap<>();
    //1-有数据， 2-没数据
    result.put("code", "2");

    User user = (User) SecurityUtils.getSubject().getSession().getAttribute("USER_INFO");
    Jedis jedis = jedisPool.getResource();
    try {
      String json = jedis.lpop("msgid-"+user.getMsgId());
      if ("nil".equals(json)) {
        return null;
      } else {
        ChengZhongRecord record = JsonMapperProvide.alwaysMapper().readValue(json, ChengZhongRecord.class);
        result.put("data", record);
      }
    } catch (Exception e) {

    }finally {
      jedis.close();
    }
    return result;
  }
}
