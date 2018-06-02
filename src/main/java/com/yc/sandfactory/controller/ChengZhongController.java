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

  @RequestMapping(value = "/newList")
  public Object newList(String startTime, String endTime, ChengZhongRecord condition,HttpServletResponse response)
      throws JsonProcessingException {
    logger.info("调用称重newList请求的输入参数");

    DataTablesParameters tables = DataTablesParameters.newInstance();

    LitePaging<ChengZhongRecord> pagination =
        chengZhongService.queryRecordForPage(startTime, endTime, condition, 1, 10);

    return new DataTablesReply(pagination, tables.getDraw());
  }

  @RequestMapping(value = "/get")
  public Object get(Integer id) {
    logger.info("调用称重详情接口请求的输入参数：id：{}", id);
    ChengZhongRecord record = chengZhongService.getRecord(id);
    return record;
  }
}
