package com.nullnull.learn.controller;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论的Controller
 *
 * @author liujun
 * @since 2023/3/26
 */
@RestController
public class CommentApiController {

  @Autowired private CommentService commentService;

  @RequestMapping(value = "/findCommentById")
  public Comment findCommentById(Integer id) {
    return commentService.findCommentById(id);
  }

  /**
   * 按id更新作者
   *
   * @param id id的信息
   * @param author 作者
   * @return
   */
  @RequestMapping(value = "/updateCommentById")
  public Comment updateComment(Integer id, String author) {
    commentService.updateComment(author, id);

    return commentService.findCommentById(id);
  }

  /**
   * 按id删除
   *
   * @param id
   * @return
   */
  @RequestMapping(value = "/deleteById")
  public Comment deleteById(Integer id) {
    commentService.deleteComment(id);
    return commentService.findCommentById(id);
  }
}
