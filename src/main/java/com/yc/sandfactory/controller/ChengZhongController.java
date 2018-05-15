package com.yc.sandfactory.controller;

import com.yc.sandfactory.service.IChengZhongService;
import java.util.HashMap;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
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

  @RequestMapping(value = "list")
  public Object getAuthMode(String clientType, HttpServletResponse response) {
    logger.info("获取称重list请求的输入参数：clientType： {}", clientType);

    if (StringUtils.isBlank(clientType)) {
      logger.error("获取客户端认证方式失败，缺少必要参数");
      //return HttpError.MISSING_REQUIRED_PARAMETERS.handle(response);
    }

    HashMap<String, Object> result = new HashMap();
    result.put("modes", "");

    return result;
  }

}
