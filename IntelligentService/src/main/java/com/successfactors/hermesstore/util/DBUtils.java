package com.successfactors.hermesstore.util;

import java.sql.Connection;
import java.sql.SQLException;

import com.successfactors.db.DBPool;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

/**
 * Utility class for DB operations.
 * 
 * @author Roman.Li(I322223)
 * Success Factors
 */
public class DBUtils {
  
  private static final Logger logger = LogManager.getLogger(DBUtils.class);
  
  public static final String DEFAULT_DB_POOL          = "dbPool1";
  public static final String GLOBAL_SCHEMA            = "sf.sfv4.schema";
  public static final String DEFAULT_SCHEMA           = ";DEFAULT_SCHEMA;.";
  public static final String COMPANY_SCHEMA           = ";COMPANY_SCHEMA;.";
  
  public static final String SEQUENCE_NEXT_VALUE      = ".NEXTVAL";
  public static final String ORACLE_ROWNUM            = "ROWNUM";
  public static final String ORACLE_FUNC_TO_TIMESTAMP = "TO_TIMESTAMP";
  
  // Common SQL DML phrase
  public static final String SQL_DML_PHRASE_INSERT        = "INSERT INTO ";
  public static final String SQL_DML_PHRASE_SELECT        = "SELECT ";
  public static final String SQL_DML_PHRASE_SELECT_ALL    = "SELECT * FROM ";
  public static final String SQL_DML_PHRASE_UPDATE        = "UPDATE ";
  public static final String SQL_DML_PHRASE_DELETE        = "DELETE FROM ";
  public static final String SQL_DML_PHRASE_SET           = " SET ";
  public static final String SQL_DML_PHRASE_VALUES        = " VALUES";
  public static final String SQL_DML_PHRASE_FROM          = " FROM ";
  public static final String SQL_DML_PHRASE_WHERE         = " WHERE ";
  public static final String SQL_DML_PHRASE_AND           = " AND ";
  public static final String SQL_DML_PHRASE_IN            = " IN ";
  public static final String SQL_DML_PHRASE_LESS_THAN     = " < ";
  public static final String SQL_DML_PHRASE_LESS_OR_EQUAL = " <= ";
  public static final String SQL_DML_PHRASE_GREATER_THAN  = " > ";
  public static final String SQL_DML_PHRASE_ORDER_BY      = " ORDER BY ";
  public static final String SQL_DML_PHRASE_ORDER_ASC     = " ASC ";
  public static final String SQL_DML_PHRASE_ORDER_DESC    = " DESC ";
  
  // SEB_EVENT table
  public static final String SEB_EVENT_STORE_TABLE              = "SEB_EVENT";
  public static final String SEB_EVENT_COLUMN_ID                = "ID";
  public static final String SEB_EVENT_COLUMN_EVENT_ID          = "EVENT_ID";
  public static final String SEB_EVENT_COLUMN_COMPANY_ID        = "COMPANY_ID";
  public static final String SEB_EVENT_COLUMN_USER_ID           = "USER_ID";
  public static final String SEB_EVENT_COLUMN_EVENT_TYPE        = "EVENT_TYPE";
  public static final String SEB_EVENT_COLUMN_TRANSACTION_ID    = "TRANSACTION_ID";
  public static final String SEB_EVENT_COLUMN_IS_BULK           = "IS_BULK";
  public static final String SEB_EVENT_COLUMN_PRIORITY          = "PRIORITY";
  public static final String SEB_EVENT_COLUMN_TOPIC             = "TOPIC";
  public static final String SEB_EVENT_COLUMN_EVENT             = "EVENT";
  public static final String SEB_EVENT_COLUMN_PUBLISH_ORDER     = "PUBLISH_ORDER";
  public static final String SEB_EVENT_COLUMN_STATUS            = "STATUS";
  public static final String SEB_EVENT_COLUMN_PROCESS_RESULT    = "PROCESS_RESULT";
  public static final String SEB_EVENT_COLUMN_RETRY_TIMES       = "RETRY_TIMES";
  public static final String SEB_EVENT_COLUMN_PUBLISHED_AT      = "PUBLISHED_AT";
  public static final String SEB_EVENT_COLUMN_CREATED_BY        = "CREATED_BY";
  public static final String SEB_EVENT_COLUMN_CREATED_DATE      = "CREATED_DATE";
  public static final String SEB_EVENT_COLUMN_LAST_UPDATED_BY   = "LAST_UPDATED_BY";
  public static final String SEB_EVENT_COLUMN_LAST_UPDATED_DATE = "LAST_UPDATED_DATE";
  public static final String SEB_EVENT_ID_SEQUENCE              = "SEB_EVENT_ID_SEQ";
  
  /**
   * Gets database connection from default DB pool.
   * 
   * @return A database connection
   * @throws SQLException
   *          If failed to get a database connection.
   */
  public static Connection getConnectionFromDefaultDBPool() throws SQLException {
    return DBPool.getInstance().getConnection(DEFAULT_DB_POOL);
  }
  
}
