package com.yc.sandfactory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yc.sandfactory.entity.SystemLog;
import com.yc.sandfactory.page.DataTablesParameters;
import com.yc.sandfactory.page.DataTablesReply;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.ISystemLogService;
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
@RequestMapping("/systemlog")
public class SystemLogController {

  public Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ISystemLogService systemLogService;

  @RequestMapping(value = "/list")
  public Object list(String startTime, String endTime, String action,  String search)
      throws JsonProcessingException {
    logger.info("------systemlog list----");

    DataTablesParameters tables = DataTablesParameters.newInstance();

    LitePaging<SystemLog> pagination =
        systemLogService.queryForPage(startTime, endTime, action, search,
            tables.getPage(), tables.getLength());

    return new DataTablesReply(pagination, tables.getDraw());
  }
}
