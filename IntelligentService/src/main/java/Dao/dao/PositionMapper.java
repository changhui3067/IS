package Dao.dao;

import Dao.model.Position;

public interface PositionMapper {
    int deleteByPrimaryKey(Integer positionId);

    int insert(Position record);

    int insertSelective(Position record);

    Position selectByPrimaryKey(Integer positionId);

    int updateByPrimaryKeySelective(Position record);

    int updateByPrimaryKeyWithBLOBs(Position record);

    int updateByPrimaryKey(Position record);
}