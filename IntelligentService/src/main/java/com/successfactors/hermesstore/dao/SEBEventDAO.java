package com.successfactors.hermesstore.dao;

import java.sql.Connection;
import java.util.List;

import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.hermesstore.bean.SEBEvent;

/**
 * Interface of CRUD operations on SEB_EVENT table.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public interface SEBEventDAO {

  String DAO_NAME = "SEBEventDAO";
  
  /**
   * Add an SEBEvent entity.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param sebEvent 
   *          The SEBEvent entity to add.
   */
  void addEvent(String companySchema, Connection dbConnection, SEBEvent sebEvent);
  
  /**
   * Add a list of SEBEvent entities.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection
   *          Database connection.
   * @param sebEvents
   *          The list of SEBEvent entities to add.
   */
  void addEventList(String companySchema, Connection dbConnection, List<SEBEvent> sebEvents);
  
  /**
   * Update an SEBEvent entity.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param sebEvent 
   *          The SEBEvent entity to update.
   */
  void updateEvent(String companySchema, Connection dbConnection, SEBEvent sebEvent);
  
  /**
   * Update a list of SEBEvent entities.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param sebEventList 
   *          The list of SEBEvent entities to update.
   */
  void updateEventList(String companySchema, Connection dbConnection, List<SEBEvent> sebEventList);
  
  /**
   * Remove an SEBEvent entity by ID.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param id 
   *          The id of the SEBEvent entity to remove.
   */
  void removeEvent(String companySchema, Connection dbConnection, long id);
  
  /**
   * Remove an SEBEvent entity.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param sebEvent 
   *          The SEBEvent entity to remove.
   */
  void removeEvent(String companySchema, Connection dbConnection, SEBEvent sebEvent);
  
  /**
   * Remove a list of SEBEvent entities.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param sebEventList 
   *          The list of SEBEvent entities to remove.
   */
  void removeEventList(String companySchema, Connection dbConnection, List<SEBEvent> sebEventList);
  
  /**
   * Remove SEBEvent entities by status. 
   * Only those events whose created date exceeds certain days will be removed.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param daysToKeep 
   *          Days to keep the event. 
   * @param statusEnums 
   *          The EventStatusEnum of SEBEvent to remove.
   */
  void removeEventsByStatus(String companySchema, Connection dbConnection, int daysToKeep, EventStatusEnum...statusEnums);
  
  /**
   * Get an SEBEvent entity by ID.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param id 
   *          The id of the SEBEvent entity to get.
   * @return The SEBEvent entity to get.
   */
  SEBEvent getEventById(String companySchema, Connection dbConnection, long id);
  
  /**
   * Get SEBEvent entities by status.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection 
   *          Database connection.
   * @param fetchSize 
   *          Size of entities to fetch.
   * @param statusEnums 
   *          The EventStatusEnum of SEBEvent to get.
   * @return List of SEBEvent entities to get.
   */
  List<SEBEvent> getEventsByStatus(String companySchema, Connection dbConnection, int fetchSize, EventStatusEnum... statusEnums);
  
  /**
   * Get SEBEvent entities by transactionId.
   * 
   * @param companySchema
   *          company schema
   * @param dbConnection
   *          Database connection.
   * @param transactionId
   *          The transactionId of SEBEvent to get.
   * @return List of SEBEvent entities to get.
   */
  List<SEBEvent> getEventsByTransactionId(String companySchema, Connection dbConnection, String transactionId);
    
}
