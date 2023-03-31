package com.nullnull.learn.service;

import com.nullnull.learn.po.Comment;
import com.nullnull.learn.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Optional;
/**
 * 评论的服务
 *
 * @author liujun
 * @since 2023/3/26
 */
@Service
public class CommentApiService {

  @Autowired private CommentRepository commentRepository;

  /**
   * 按id进行查询 @Cacheable 将该方法查询结果Comment存放在springboot默认缓存中
   *
   * <p>cacheNames： 起一个缓存命名空间，对应缓存唯一标识
   *
   * <p>value： 缓存结果 key：默认认在只有一个参数的情况，Key值默认就是方法的参数值， 如果没有参数或者多个参数的的情况，使用simpleKeyGenerate来生成key
   *
   * <p>unless = "#result == null" 表示如果当前的缓存结果为null，则不进行缓存
   *
   * @param id
   * @return
   */
  @Cacheable(cacheNames = "comment", unless = "#result == null")
  public Comment findCommentById(Integer id) {
    Optional<Comment> comment = commentRepository.findById(id);

    if (comment.isPresent()) {
      Comment result = comment.get();
      return result;
    }
    return null;
  }

  /**
   * 进行数据修改操作,修改完成后，进行缓存的更新
   *
   * @param auth 作者
   * @param id id
   * @return
   */
  @CachePut(cacheNames = "comment", key = "#result.id")
  public Comment updateComment(String auth, Integer id) {

    Optional<Comment> dataComment = commentRepository.findById(id);

    Comment authComment = dataComment.get();
    authComment.setAuthor(auth);
    commentRepository.updateComment(authComment.getAuthor(), authComment.getId());
    return authComment;
  }

  /**
   * 按id进行数据删除
   *
   * @param deleteId
   */
  @CacheEvict(cacheNames = "comment")
  public void deleteComment(Integer deleteId) {
    commentRepository.deleteById(deleteId);
  }
}
