package com.yc.sandfactory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yc.sandfactory.bean.RecodRequestBean;
import com.yc.sandfactory.entity.ChengZhongRecord;
import com.yc.sandfactory.page.LitePaging;
import com.yc.sandfactory.service.IChengZhongService;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
  public Object list(@RequestBody RecodRequestBean recodRequestBean, HttpServletResponse response)
      throws JsonProcessingException {
    logger.info("调用称重list请求的输入参数：recodRequestBean：{}",
        JsonMapperProvide.alwaysMapper().writeValueAsString(recodRequestBean)
    );

    LitePaging<ChengZhongRecord> pagination =
        chengZhongService.queryRecordForPage(recodRequestBean,
            recodRequestBean.getPage().getPageNo(), recodRequestBean.getPage().getPageSize());

    return pagination;
  }

  @RequestMapping(value = "/get")
  public Object get(Integer id) {
    logger.info("调用称重详情接口请求的输入参数：id：{}", id);
    ChengZhongRecord record = chengZhongService.getRecord(id);
    return record;
  }
}
