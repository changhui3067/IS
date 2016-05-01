package com.successfactors.hermesstore.dao.impl.oracle;

import com.successfactors.hermesstore.bean.EventStatusEnum;
import com.successfactors.hermesstore.bean.SEBEvent;
import com.successfactors.hermesstore.dao.SEBEventDAO;
import com.successfactors.hermesstore.util.LogUtils;
import com.successfactors.logging.api.Level;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.successfactors.hermesstore.util.Constants.*;
import static com.successfactors.hermesstore.util.DBUtils.*;

/**
 * JDBC implementaion of {@link SEBEventDAO}.
 *
 * @author Roman.Li(I322223)
 *         Success Factors
 */
public class SEBEventJDBCDAOImpl implements SEBEventDAO {

    private static final Logger logger = LogManager.getLogger(SEBEventJDBCDAOImpl.class);
    private static final Pattern defaultSchemaPattern = Pattern.compile(DEFAULT_SCHEMA);
    private static final Pattern companySchemaPattern = Pattern.compile(COMPANY_SCHEMA);
    private static final long MILLISECONDS_PER_DAY = 1000L * 24 * 60 * 60;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String ORACLE_DATE_FORMAT = "YYYY-MM-DD HH24:MI:SS";
    private static final String STATEMENT_INSERT = new StringBuilder()
            .append(SQL_DML_PHRASE_INSERT)
            .append(COMPANY_SCHEMA).append(SEB_EVENT_STORE_TABLE)
            .append(LEFT_ROUND_BRACKET)
            .append(SEB_EVENT_COLUMN_ID).append(COMMA)
            .append(SEB_EVENT_COLUMN_EVENT_ID).append(COMMA)
            .append(SEB_EVENT_COLUMN_COMPANY_ID).append(COMMA)
            .append(SEB_EVENT_COLUMN_USER_ID).append(COMMA)
            .append(SEB_EVENT_COLUMN_EVENT_TYPE).append(COMMA)
            .append(SEB_EVENT_COLUMN_TRANSACTION_ID).append(COMMA)
            .append(SEB_EVENT_COLUMN_TOPIC).append(COMMA)
            .append(SEB_EVENT_COLUMN_EVENT).append(COMMA)
            .append(SEB_EVENT_COLUMN_PUBLISH_ORDER).append(COMMA)
            .append(SEB_EVENT_COLUMN_STATUS).append(COMMA)
            .append(SEB_EVENT_COLUMN_PUBLISHED_AT).append(COMMA)
            .append(SEB_EVENT_COLUMN_PROCESS_RESULT).append(COMMA)
            .append(SEB_EVENT_COLUMN_RETRY_TIMES).append(COMMA)
            .append(SEB_EVENT_COLUMN_CREATED_BY).append(COMMA)
            .append(SEB_EVENT_COLUMN_CREATED_DATE).append(COMMA)
            .append(SEB_EVENT_COLUMN_LAST_UPDATED_BY).append(COMMA)
            .append(SEB_EVENT_COLUMN_LAST_UPDATED_DATE)
            .append(RIGHT_ROUND_BRACKET)
            .append(SQL_DML_PHRASE_VALUES)
            .append(LEFT_ROUND_BRACKET)
            .append(SEB_EVENT_ID_SEQUENCE).append(SEQUENCE_NEXT_VALUE).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK).append(COMMA)
            .append(QUESTION_MARK)
            .append(RIGHT_ROUND_BRACKET).toString();
    private static final String STATEMENT_UPDATE = new StringBuilder()
            .append(SQL_DML_PHRASE_UPDATE)
            .append(COMPANY_SCHEMA).append(SEB_EVENT_STORE_TABLE)
            .append(SQL_DML_PHRASE_SET)
            .append(SEB_EVENT_COLUMN_STATUS).append(EQUALITY_SIGN).append(QUESTION_MARK).append(COMMA)
            .append(SEB_EVENT_COLUMN_PROCESS_RESULT).append(EQUALITY_SIGN).append(QUESTION_MARK).append(COMMA)
            .append(SEB_EVENT_COLUMN_RETRY_TIMES).append(EQUALITY_SIGN).append(QUESTION_MARK).append(COMMA)
            .append(SEB_EVENT_COLUMN_PUBLISHED_AT).append(EQUALITY_SIGN).append(QUESTION_MARK).append(COMMA)
            .append(SEB_EVENT_COLUMN_LAST_UPDATED_BY).append(EQUALITY_SIGN).append(QUESTION_MARK).append(COMMA)
            .append(SEB_EVENT_COLUMN_LAST_UPDATED_DATE).append(EQUALITY_SIGN).append(QUESTION_MARK)
            .append(SQL_DML_PHRASE_WHERE).append(SEB_EVENT_COLUMN_ID).append(EQUALITY_SIGN).append(QUESTION_MARK).toString();
    private static final String STATEMENT_DELETE_BY_ID = new StringBuilder()
            .append(SQL_DML_PHRASE_DELETE)
            .append(COMPANY_SCHEMA).append(SEB_EVENT_STORE_TABLE)
            .append(SQL_DML_PHRASE_WHERE).append(SEB_EVENT_COLUMN_ID).append(EQUALITY_SIGN).append(QUESTION_MARK).toString();
    private static final String STATEMENT_DELETE_BY_STATUS = new StringBuilder()
            .append(SQL_DML_PHRASE_DELETE)
            .append(COMPANY_SCHEMA).append(SEB_EVENT_STORE_TABLE)
            .append(SQL_DML_PHRASE_WHERE).append(SEB_EVENT_COLUMN_STATUS).append(SQL_DML_PHRASE_IN).toString();
    private static final String STATEMENT_SELECT_BY_ID = new StringBuilder()
            .append(SQL_DML_PHRASE_SELECT_ALL)
            .append(COMPANY_SCHEMA).append(SEB_EVENT_STORE_TABLE)
            .append(SQL_DML_PHRASE_WHERE).append(SEB_EVENT_COLUMN_ID).append(EQUALITY_SIGN).append(QUESTION_MARK).toString();
    private static final String STATEMENT_SELECT_BY_STATUS = new StringBuilder()
            .append(SQL_DML_PHRASE_SELECT_ALL)
            .append(COMPANY_SCHEMA).append(SEB_EVENT_STORE_TABLE)
            .append(SQL_DML_PHRASE_WHERE).append(SEB_EVENT_COLUMN_STATUS).append(SQL_DML_PHRASE_IN).toString();
    private static final String STATEMENT_SELECT_BY_TRANSACTION_ID = new StringBuilder()
            .append(SQL_DML_PHRASE_SELECT_ALL)
            .append(COMPANY_SCHEMA).append(SEB_EVENT_STORE_TABLE)
            .append(SQL_DML_PHRASE_WHERE).append(SEB_EVENT_COLUMN_TRANSACTION_ID).append(EQUALITY_SIGN).append(QUESTION_MARK).toString();

    private static String generateInConditions(int count) {
        StringBuilder sb = new StringBuilder(LEFT_ROUND_BRACKET);
        for (int i = 1; i <= count; i++) {
            if (i != 1) {
                sb.append(COMMA);
            }
            sb.append(QUESTION_MARK);
        }
        sb.append(RIGHT_ROUND_BRACKET);
        return sb.toString();
    }

    private static String getOracleTimestamp(long date) {
        StringBuilder sb = new StringBuilder(ORACLE_FUNC_TO_TIMESTAMP);
        sb.append(LEFT_ROUND_BRACKET)
                .append(SINGLE_QUOTATION)
                .append(new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(new Date(date)))
                .append(SINGLE_QUOTATION)
                .append(COMMA)
                .append(SINGLE_QUOTATION)
                .append(ORACLE_DATE_FORMAT)
                .append(SINGLE_QUOTATION)
                .append(RIGHT_ROUND_BRACKET);
        return sb.toString();
    }

    private static SEBEvent getEventFromResultSet(ResultSet rs) {
        if (rs == null) {
            return null;
        }
        SEBEvent sebEvent = new SEBEvent();
        try {
            sebEvent.setId(rs.getLong(SEB_EVENT_COLUMN_ID));
            sebEvent.setEventId(rs.getString(SEB_EVENT_COLUMN_EVENT_ID));
            sebEvent.setCompanyId(rs.getString(SEB_EVENT_COLUMN_COMPANY_ID));
            sebEvent.setUserId(rs.getString(SEB_EVENT_COLUMN_USER_ID));
            sebEvent.setEventType(rs.getString(SEB_EVENT_COLUMN_EVENT_TYPE));
            sebEvent.setTransactionId(rs.getString(SEB_EVENT_COLUMN_TRANSACTION_ID));
            sebEvent.setPublishOrder(rs.getInt(SEB_EVENT_COLUMN_PUBLISH_ORDER));
            sebEvent.setRetryTimes(rs.getInt(SEB_EVENT_COLUMN_RETRY_TIMES));
            sebEvent.setPublishedAt(rs.getTimestamp(SEB_EVENT_COLUMN_PUBLISHED_AT));
            sebEvent.setCreatedBy(rs.getString(SEB_EVENT_COLUMN_CREATED_BY));
            sebEvent.setCreatedDate(rs.getTimestamp(SEB_EVENT_COLUMN_CREATED_DATE));
            sebEvent.setLastUpdatedBy(rs.getString(SEB_EVENT_COLUMN_LAST_UPDATED_BY));
            sebEvent.setLastUpdatedDate(rs.getTimestamp(SEB_EVENT_COLUMN_LAST_UPDATED_DATE));
            sebEvent.setStatus(EventStatusEnum.getEnum(rs.getString(SEB_EVENT_COLUMN_STATUS)));
            sebEvent.setTopic(rs.getString(SEB_EVENT_COLUMN_TOPIC));
            Blob event = rs.getBlob(SEB_EVENT_COLUMN_EVENT);
            if (event != null) {
                sebEvent.setEvent(event.getBytes(1L, (int) event.length()));
            }
            Clob processResult = rs.getClob(SEB_EVENT_COLUMN_PROCESS_RESULT);
            if (processResult != null) {
                BufferedReader br = new BufferedReader(processResult.getCharacterStream());
                StringBuffer sb = new StringBuffer();
                String str;
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                sebEvent.setProcessResult(sb.toString());
                br.close();
            }
        } catch (Exception e) {
            String message = "Failed to get event from result set";
            LogUtils.log(logger, Level.ERROR, message, e);
        }
        return sebEvent;
    }

    @Override
    public void addEvent(String companySchema, Connection dbConnection, SEBEvent sebEvent) {
        String sql = companySchemaPattern.matcher(STATEMENT_INSERT).replaceAll(companySchema);
        String message = "addEvent: SEBEvent = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, sebEvent, sql);
        PreparedStatement pstmt = null;
        Date publishedAt = sebEvent.getPublishedAt();
        String processResult = sebEvent.getProcessResult() != null ? sebEvent.getProcessResult() : EMPTY_STRING;
        StringReader reader = new StringReader(processResult);
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt.setString(1, sebEvent.getEventId());
            pstmt.setString(2, sebEvent.getCompanyId());
            pstmt.setString(3, sebEvent.getUserId());
            pstmt.setString(4, sebEvent.getEventType());
            pstmt.setString(5, sebEvent.getTransactionId());
            pstmt.setString(6, sebEvent.getTopic());
            pstmt.setBinaryStream(7, new ByteArrayInputStream(sebEvent.getEvent()), sebEvent.getEvent().length);
            pstmt.setInt(8, sebEvent.getPublishOrder());
            pstmt.setString(9, sebEvent.getStatus().getStatus());
            pstmt.setTimestamp(10, publishedAt != null ? new Timestamp(publishedAt.getTime()) : null);
            pstmt.setCharacterStream(11, reader, processResult.length());
            pstmt.setInt(12, sebEvent.getRetryTimes());
            pstmt.setString(13, sebEvent.getCreatedBy());
            pstmt.setTimestamp(14, new Timestamp(sebEvent.getCreatedDate().getTime()));
            pstmt.setString(15, sebEvent.getLastUpdatedBy());
            pstmt.setTimestamp(16, new Timestamp(sebEvent.getLastUpdatedDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.log("Failed to add event", e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addEventList(String companySchema, Connection dbConnection, List<SEBEvent> sebEvents) {
        String sql = companySchemaPattern.matcher(STATEMENT_INSERT).replaceAll(companySchema);
        String message = "addEventList: sebEvents = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, sebEvents, sql);
        PreparedStatement pstmt = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            for (SEBEvent sebEvent : sebEvents) {
                Date publishedAt = sebEvent.getPublishedAt();
                String processResult = sebEvent.getProcessResult() != null ? sebEvent.getProcessResult() : EMPTY_STRING;
                StringReader reader = new StringReader(processResult);
                pstmt.setString(1, sebEvent.getEventId());
                pstmt.setString(2, sebEvent.getCompanyId());
                pstmt.setString(3, sebEvent.getUserId());
                pstmt.setString(4, sebEvent.getEventType());
                pstmt.setString(5, sebEvent.getTransactionId());
                pstmt.setString(6, sebEvent.getTopic());
                pstmt.setBinaryStream(7, new ByteArrayInputStream(sebEvent.getEvent()), sebEvent.getEvent().length);
                pstmt.setInt(8, sebEvent.getPublishOrder());
                pstmt.setString(9, sebEvent.getStatus().getStatus());
                pstmt.setTimestamp(10, publishedAt != null ? new Timestamp(publishedAt.getTime()) : null);
                pstmt.setCharacterStream(11, reader, processResult.length());
                pstmt.setInt(12, sebEvent.getRetryTimes());
                pstmt.setString(13, sebEvent.getCreatedBy());
                pstmt.setTimestamp(14, new Timestamp(sebEvent.getCreatedDate().getTime()));
                pstmt.setString(15, sebEvent.getLastUpdatedBy());
                pstmt.setTimestamp(16, new Timestamp(sebEvent.getLastUpdatedDate().getTime()));
                pstmt.addBatch();
                reader.close();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            Logger.log("Failed to add event list", e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateEvent(String companySchema, Connection dbConnection, SEBEvent sebEvent) {
        String sql = companySchemaPattern.matcher(STATEMENT_UPDATE).replaceAll(companySchema);
        String message = "updateEvent: SEBEvent = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, sebEvent, sql);
        PreparedStatement pstmt = null;
        Date publishedAt = sebEvent.getPublishedAt();
        String processResult = sebEvent.getProcessResult() != null ? sebEvent.getProcessResult() : EMPTY_STRING;
        StringReader reader = new StringReader(processResult);
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt.setString(1, sebEvent.getStatus().getStatus());
            pstmt.setCharacterStream(2, reader, processResult.length());
            pstmt.setInt(3, sebEvent.getRetryTimes());
            pstmt.setTimestamp(4, publishedAt != null ? new Timestamp(publishedAt.getTime()) : null);
            pstmt.setString(5, sebEvent.getLastUpdatedBy());
            pstmt.setTimestamp(6, new Timestamp(sebEvent.getLastUpdatedDate().getTime()));
            pstmt.setLong(7, sebEvent.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.log("Failed to update event", e);
        } finally {
            reader.close();
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateEventList(String companySchema, Connection dbConnection, List<SEBEvent> sebEvents) {
        String sql = companySchemaPattern.matcher(STATEMENT_UPDATE).replaceAll(companySchema);
        String message = "updateEventList: sebEvents = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, sebEvents, sql);
        PreparedStatement pstmt = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            for (SEBEvent sebEvent : sebEvents) {
                Date publishedAt = sebEvent.getPublishedAt();
                String processResult = sebEvent.getProcessResult() != null ? sebEvent.getProcessResult() : EMPTY_STRING;
                StringReader reader = new StringReader(processResult);
                pstmt.setString(1, sebEvent.getStatus().getStatus());
                pstmt.setCharacterStream(2, reader, processResult.length());
                pstmt.setInt(3, sebEvent.getRetryTimes());
                pstmt.setTimestamp(4, publishedAt != null ? new Timestamp(sebEvent.getPublishedAt().getTime()) : null);
                pstmt.setString(5, sebEvent.getLastUpdatedBy());
                pstmt.setTimestamp(6, new Timestamp(sebEvent.getLastUpdatedDate().getTime()));
                pstmt.setLong(7, sebEvent.getId());
                pstmt.addBatch();
                reader.close();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            Logger.log("Failed to update event list", e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeEvent(String companySchema, Connection dbConnection, long id) {
        String sql = companySchemaPattern.matcher(STATEMENT_DELETE_BY_ID).replaceAll(companySchema);
        String message = "removeEvent: id = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, id, sql);
        PreparedStatement pstmt = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.log("Failed to delete event", e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeEvent(String companySchema, Connection dbConnection, SEBEvent sebEvent) {
        String message = "removeEvent: SEBEvent = {}";
        LogUtils.log(logger, Level.DEBUG, message, sebEvent);
        removeEvent(companySchema, dbConnection, sebEvent.getId());
    }

    @Override
    public void removeEventList(String companySchema, Connection dbConnection, List<SEBEvent> sebEvents) {
        String sql = companySchemaPattern.matcher(STATEMENT_DELETE_BY_ID).replaceAll(companySchema);
        String message = "removeEventList: sebEvents = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, sebEvents, sql);
        PreparedStatement pstmt = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            for (SEBEvent sebEvent : sebEvents) {
                pstmt.setLong(1, sebEvent.getId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            Logger.log("Failed to remove event list", e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeEventsByStatus(String companySchema, Connection dbConnection, int daysToKeep, EventStatusEnum... statusEnums) {
        long interval = System.currentTimeMillis() - (MILLISECONDS_PER_DAY * daysToKeep);
        String sql = companySchemaPattern.matcher(STATEMENT_DELETE_BY_STATUS).replaceAll(companySchema);
        StringBuffer sb = new StringBuffer(sql);
        sb.append(generateInConditions(statusEnums.length))
                .append(SQL_DML_PHRASE_AND)
                .append(SEB_EVENT_COLUMN_CREATED_DATE)
                .append(SQL_DML_PHRASE_LESS_THAN)
                .append(getOracleTimestamp(interval));
        sql = sb.toString();
        String message = "removeEventsByStatus: statusEnums = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, Arrays.toString(statusEnums), sql);
        PreparedStatement pstmt = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            for (int i = 1; i <= statusEnums.length; i++) {
                pstmt.setString(i, statusEnums[(i - 1)].getStatus());
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.log("Failed to remove events by status", e);
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SEBEvent getEventById(String companySchema, Connection dbConnection, long id) {
        String sql = companySchemaPattern.matcher(STATEMENT_SELECT_BY_ID).replaceAll(companySchema);
        String message = "getEventById: id = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, id, sql);
        SEBEvent sebEvent = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sebEvent = getEventFromResultSet(rs);
            }
        } catch (Exception e) {
            Logger.log("Failed to get event by id", e);
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sebEvent;
    }

    @Override
    public List<SEBEvent> getEventsByStatus(String companySchema, Connection dbConnection, int fetchSize, EventStatusEnum... statusEnums) {
        String sql = companySchemaPattern.matcher(STATEMENT_SELECT_BY_STATUS).replaceAll(companySchema);
        StringBuilder sb = new StringBuilder(SQL_DML_PHRASE_SELECT_ALL);
        sb.append(LEFT_ROUND_BRACKET)
                .append(sql)
                .append(generateInConditions(statusEnums.length))
                .append(SQL_DML_PHRASE_ORDER_BY)
                .append(SEB_EVENT_COLUMN_CREATED_DATE)
                .append(COMMA)
                .append(SEB_EVENT_COLUMN_TRANSACTION_ID)
                .append(COMMA)
                .append(SEB_EVENT_COLUMN_PUBLISH_ORDER)
                .append(RIGHT_ROUND_BRACKET)
                .append(SQL_DML_PHRASE_WHERE)
                .append(ORACLE_ROWNUM).append(SQL_DML_PHRASE_LESS_OR_EQUAL).append(QUESTION_MARK);
        sql = sb.toString();
        String message = "getEventsByStatus: statusEnums = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, Arrays.toString(statusEnums), sql);
        List<SEBEvent> sebEvents = new ArrayList<SEBEvent>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            int length = statusEnums.length;
            for (int i = 1; i <= length; i++) {
                pstmt.setString(i, statusEnums[(i - 1)].getStatus());
            }
            pstmt.setInt((length + 1), fetchSize);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SEBEvent sebEvent = getEventFromResultSet(rs);
                if (sebEvent != null) {
                    sebEvents.add(sebEvent);
                }
            }
        } catch (Exception e) {
            Logger.log("Failed to get events by status",e);
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sebEvents;
    }

    @Override
    public List<SEBEvent> getEventsByTransactionId(String companySchema, Connection dbConnection, String transactionId) {
        String sql = companySchemaPattern.matcher(STATEMENT_SELECT_BY_TRANSACTION_ID).replaceAll(companySchema);
        String message = "getEventsByTransactionId: transactionId = {}, SQL = {}";
        LogUtils.log(logger, Level.DEBUG, message, transactionId, sql);
        List<SEBEvent> sebEvents = new ArrayList<SEBEvent>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = dbConnection.prepareStatement(sql);
            pstmt.setString(1, transactionId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                SEBEvent sebEvent = getEventFromResultSet(rs);
                if (sebEvent != null) {
                    sebEvents.add(sebEvent);
                }
            }
        } catch (Exception e) {
            Logger.log("Failed to get event by transactionId", e);
        } finally {
            try {
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return sebEvents;
    }
}
