package com.yc.sandfactory.service;

import com.yc.sandfactory.entity.Video;
import com.yc.sandfactory.page.LitePaging;

/**
 * @project: sandfactory
 * @author: yc
 */
public interface IVideoService {

  /**
   * 分页查找
   * @param video
   * @param pageNo
   * @param pageSize
   * @return
   */
  LitePaging<Video> queryForPage(Video video, String searchKey, Integer pageNo, Integer pageSize);

  /**
   * 根据ID获取用户
   * @param id
   * @return
   */
  Video get(long id);


  void del(long id);

  Video add(Video video);

  void update(Video video);

}
