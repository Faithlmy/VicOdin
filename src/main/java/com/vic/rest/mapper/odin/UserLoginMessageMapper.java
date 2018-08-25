package com.vic.rest.mapper.odin;

import com.vic.rest.pojo.UserLoginMessage;
import com.vic.rest.pojo.UserLoginMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserLoginMessageMapper {
    int countByExample(UserLoginMessageExample example);

    int deleteByExample(UserLoginMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserLoginMessage record);

    int insertSelective(UserLoginMessage record);

    List<UserLoginMessage> selectByExample(UserLoginMessageExample example);

    UserLoginMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserLoginMessage record, @Param("example") UserLoginMessageExample example);

    int updateByExample(@Param("record") UserLoginMessage record, @Param("example") UserLoginMessageExample example);

    int updateByPrimaryKeySelective(UserLoginMessage record);

    int updateByPrimaryKey(UserLoginMessage record);
}