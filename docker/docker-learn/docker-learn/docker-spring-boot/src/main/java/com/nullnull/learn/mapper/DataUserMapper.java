package com.nullnull.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nullnull.learn.entity.DataUserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据库操作
 *
 * @author liujun
 * @since 2023/12/18
 */
@Mapper
public interface DataUserMapper extends BaseMapper<DataUserPO> {
}
