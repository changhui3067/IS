package com.successfactors.sef.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.successfactors.genericobject.app.facade.MDFFacade;
import com.successfactors.genericobject.app.sql.MDFCriteria;
import com.successfactors.genericobject.app.sql.MDFResultRow;
import com.successfactors.genericobject.app.sql.MDFResultSet;
import com.successfactors.genericobject.app.sql.criterion.Criterion;
import com.successfactors.genericobject.app.sql.criterion.Restrictions;
import com.successfactors.genericobject.app.sql.criterion.SimpleExpression;
import com.successfactors.genericobject.app.sql.projection.Projections;
import com.successfactors.legacy.bean.provisioning.FeatureEnum;
import com.successfactors.logging.api.LogManager;
import com.successfactors.logging.api.Logger;
import com.successfactors.platform.bean.ParamBean;
import com.successfactors.sca.ServiceApplicationException;
import com.successfactors.sca.ServiceSystemException;
import com.successfactors.xi.util.SeamEnvUtils;

/** Serializable interface is necessary to prevent exceptions if LazySelect is utilized in context of EJB's */
public class SEFLazySelect implements Serializable {
  /** Logger */
  private static final Logger log = LogManager.getLogger();

  /**
   * params
   */
  private ParamBean params;

  /**
   * MDF Facade
   */
  private MDFFacade mdfFacade;

  /**
   * Query runs against this MDF bean/class
   */
  private Class mdfClass;

  /**
   * ignore authorization checks
   */
  private boolean ignoreSecurity;

  /**
   * valid at this date; default is todays Date
   */
  Date validAt;

  /**
   * supported operators
   * 
   * @author kpielka
   * 
   */
  protected enum Operator {
    /** where */
    WHERE,
    /** and */
    AND
  }

  /**
   * supported comparators
   * 
   * @author kpielka
   * 
   */
  protected enum Comparator {
    /** eq */
    EQ,
    /** ne */
    NE
  }

  /** one selection row 
   * Serializable interface is necessary to prevent exceptions if LazySelect is utilized in context of EJB's */
  protected class Condition implements Serializable {
    private Operator operator;
    private String searchProperty;
    private Comparator comparator;
    private Object searchValue;
    private Criterion criterion;

    Condition(Operator op, String property) {
      this.operator = op;
      this.searchProperty = property;
    }
  }

  /** select statement */
  private List<Condition> selectStatement;

  /** criterion is alternative to where ... eq/ne chain */
  private Criterion searchCriterion;

  private boolean includeInactive;

  /** initialize select criteria */
  private void initializeAll() {
    this.mdfClass = null;
    this.ignoreSecurity = true;
    this.validAt = null;
    this.selectStatement = new ArrayList<Condition>();
    this.searchCriterion = null;
    this.includeInactive=false;
  }

  /**
   * Opens select statement and defines which MDF Object to query.
   * Initialization of select criteria happens here.
   * 
   * @param mdfClassParam
   *          mdf class to query
   * @return LazySelect instance
   */
  public SEFLazySelect from(Class mdfClassParam) {
    // initilization happens only in from() method
    // so, it is possible to re-use select statements on the same MDF Object
    initializeAll();
    this.mdfClass = mdfClassParam;
    return this;
  }

  /**
   * Defines the property that is used as filter. Use this 'where' method if
   * eq/ne comparator follows.
   * 
   * @param searchField
   *          filter property
   * @return this
   */
  public SEFLazySelect where(String searchField) {
    this.selectStatement.add(new Condition(Operator.WHERE, searchField));
    return this;
  }

  /**
   * Defines criterion as filter for selection from database. Use this if
   * 'where' method if you want to to use comparators other to eq/ne.
   * 
   * @param criterion
   *          search criterion
   * @return this
   */
  public SEFLazySelect where(Criterion criterion) {
    this.searchCriterion = criterion;
    return this;
  }

  /**
   * starts an additional selection with "and" operator. Use this 'and' method
   * if eq/ne comparator follows.
   * 
   * @param searchField
   *          filter property
   * @return this
   */
  public SEFLazySelect and(String searchField) {
    this.selectStatement.add(new Condition(Operator.AND, searchField));
    return this;
  }

  /**
   * starts an additional selection with "and" operator. Use this if 'and'
   * method if you want to to use comparators other to eq/ne.
   * 
   * @param criterion
   *          serach criterion
   * @return this
   */
  public SEFLazySelect and(Criterion criterion) {
    this.searchCriterion = criterion;
    return this;
  }

  /**
   * Search with field is not equal to searchValueParam
   * 
   * @param searchValueParam
   *          filter value
   * @return this
   */
  public SEFLazySelect ne(Object searchValueParam) {
    Condition sc = getCurrentSelectCondition();
    if (sc != null) {
      sc.searchValue = searchValueParam;
      sc.comparator = Comparator.NE;
    }
    return this;
  }

  /**
   * Search with field is equal to searchValueParam
   * 
   * @param searchValueParam
   *          filter value
   * @return this
   */
  public SEFLazySelect eq(Object searchValueParam) {
    /* this.searchValue = searchValueParam; */
    Condition sc = getCurrentSelectCondition();
    if (sc != null) {
      sc.searchValue = searchValueParam;
      sc.comparator = Comparator.EQ;
    }
    return this;
  }

  /**
   * Defines validity date (for effective Dated objects)
   * 
   * @param date
   *          validity date
   * @return this
   */
  public SEFLazySelect andValidAt(Date date) {
    this.validAt = date;
    return this;
  }

  /**
   * Defines whether authorization checks shall be performed.
   * 
   * @param setIgnoreSecurity
   *          authorization checks requested ?
   * @return this
   */
  public SEFLazySelect ignoreSecurity(boolean setIgnoreSecurity) {
    this.ignoreSecurity = setIgnoreSecurity;
    return this;
  }

  /**
   * Defines whether inactive Objects shall be included in the search.
   * 
   * @param setIncludeInactive
   *          include Inactive Objects ?
   * @return this
   */
  public SEFLazySelect includeInactive(boolean setIncludeInactive) {
    this.includeInactive = setIncludeInactive;
    return this;
  }

  /**
   * Selects the first entry of result set. Must be the last call in the chain.
   * 
   * @param <T>
   *          is replaced by Class that is expected by caller
   * @return bean
   */
  public <T extends Object> T getFirst() {
    T bean = null;
    List<T> beans = getAll();
    if (beans.size() > 0) {
      bean = beans.get(0);
    }
    return bean;
  }

  /**
   * Returns the selected data. Must be the last call in the chain.
   * 
   * @param <T>
   *          is replaced by Class that is expected by caller
   * @return list of selected beans
   */
  public <T extends Object> List<T> getAll() {
    List<T> beans = new LinkedList<T>();
    MDFResultSet rs = null;
    if (isPrerequisiteCheck()) {
      String typeStr=mdfClass==null?"null":mdfClass.getName();
      log.info("Starting LazySelect.getAll() for " + typeStr);
      MDFCriteria search = createMDFCriteria();
      search.setProjection(Projections.all());
      try {
        rs = (MDFResultSet) search.execute();
      } catch (ServiceApplicationException e) {
        String msg ="search.execute() failed for class " + typeStr; 
        log.error(msg, e);
        throw new ServiceSystemException(msg,e);
      }
      if (rs != null) {
        beans = rs.getBeans();
      }
    }
    return beans;
  }

  private boolean isPrerequisiteCheck() {
    // retrieve data only if seam is up
    // otherwise unit tests are failing
    if (isSeamActive()) {

      // check whether MDF is enabled in provisioning
      // assert (getParams() != null): "LazySelect: getParams() is null";
      if (getParams() != null) {

        if (getParams().getCompanyBean().isFeatureEnabledForCompany(
            FeatureEnum.GENERIC_OBJECTS)) {
          return true;
        } else {
          log.error("Generic Objects not enabled in prerequisite check");
        }
      } else {
        log.error("ParamBean=null in prerequisiteCheck");
      }
    } else {
      log.warn("No Seam Context available in prerequisiteCheck");
    }
    return false;
  }

  protected boolean isSeamActive() {
    return SeamEnvUtils.isApplicationContextActive();
  }

  private MDFCriteria createMDFCriteria() {
    mdfFacade = getMDFFacade();

    try {
      MDFCriteria search;
      search = mdfFacade.createCriteria(mdfClass);
      if (ignoreSecurity) {
        search.setIgnoreSecurity(true);
      }
      if(includeInactive){
        search.setIncludeInactive(includeInactive);
      }
      if (validAt != null) {
        search.setEffectiveDate(validAt);
      }
      if (this.searchCriterion == null) {
        for (Condition condition : selectStatement) {
          SimpleExpression se = null;
          switch (condition.comparator) {
          case EQ:
            if (condition.searchProperty != null && condition.searchValue != null) {
              se = Restrictions.eq(condition.searchProperty,
                  condition.searchValue);
            }
            break;
          case NE:
            if (condition.searchProperty != null && condition.searchValue != null) {
              se = Restrictions.ne(condition.searchProperty,
                  condition.searchValue);
            }
            break;
          }
          switch (condition.operator) {
          case WHERE:
            if (se != null) {
              search.add(se);
            }
            break;
          case AND:
            if (se != null) {
              search.add(Restrictions.and(se));
            }
            break;
          }
        }
      } else {
        search.add(searchCriterion);
      }
      return search;

    } catch (ServiceApplicationException e) {
      String msg="Creating the Criteria failed for class " + mdfClass==null?"null":mdfClass.getName();
      log.error(msg, e);

      throw new ServiceSystemException(msg, e);
    }
  }

  /**
   * get params
   * 
   * @return scaHandler
   */
  protected ParamBean getParams() {
    if (params == null) {
      params = (ParamBean) SeamEnvUtils.getInstance("params", true);
    }
    return params;
  }

  /**
   * get MDFFacade from SeamEnvUtils
   * 
   * @return MDFFacade
   */
  protected MDFFacade getMDFFacade() {
    // initialize only once
    if (mdfFacade == null) {
      mdfFacade = (MDFFacade) SeamEnvUtils.getInstance(MDFFacade.MDF_FACADE,
          true);
    }
    return mdfFacade;
  }

  private Condition getCurrentSelectCondition() {
    Condition sc = selectStatement.size() > 0 ? selectStatement
        .get(selectStatement.size() - 1) : null;
    return sc;
  }

  /** 
   * count
   * @return the number of hits
   */
  public long count() {
    if (isPrerequisiteCheck()) {
      String typeStr=mdfClass==null?"null":mdfClass.getName();
      log.info("Starting LazySelect.count() for " + typeStr);
      MDFCriteria search = createMDFCriteria();
      search.setProjection(Projections.rowCount());
      try {
        MDFResultSet rs = (MDFResultSet) search.execute();
        List<? extends MDFResultRow> resultRows = rs.getResultRows();
        if (!resultRows.isEmpty()) {
          Long totalNumberOfRecords = (Long) resultRows.get(0).getValue(0);
          return totalNumberOfRecords.longValue();
        }
      } catch (ServiceApplicationException e) {
        String msg = "search.execute() failed for class " + typeStr;
        log.error(msg, e);
        throw new ServiceSystemException(msg,e);
      }
    }
    return 0;
  }

}
