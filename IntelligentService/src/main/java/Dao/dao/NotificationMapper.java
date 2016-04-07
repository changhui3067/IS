package Dao.dao;

import Dao.model.Notification;

public interface NotificationMapper {
    int deleteByPrimaryKey(Integer notiId);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Integer notiId);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKey(Notification record);
}