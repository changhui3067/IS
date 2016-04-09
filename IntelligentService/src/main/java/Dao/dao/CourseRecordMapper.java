package Dao.dao;

import Dao.model.CourseRecord;

public interface CourseRecordMapper {
    int deleteByPrimaryKey(Integer resordId);

    int insert(CourseRecord record);

    int insertSelective(CourseRecord record);

    CourseRecord selectByPrimaryKey(Integer resordId);

    int updateByPrimaryKeySelective(CourseRecord record);

    int updateByPrimaryKeyWithBLOBs(CourseRecord record);

    int updateByPrimaryKey(CourseRecord record);
}