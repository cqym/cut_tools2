package com.tl.resource.dao.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TSuppliersBrandExample {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    protected String orderByClause;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    protected List oredCriteria;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public TSuppliersBrandExample() {
        oredCriteria = new ArrayList();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    protected TSuppliersBrandExample(TSuppliersBrandExample example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table t_suppliers_brand
     *
     * @ibatorgenerated Thu Nov 05 15:39:31 CST 2009
     */
    public static class Criteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return this;
        }

        public Criteria andIdIn(List values) {
            addCriterion("id in", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List values) {
            addCriterion("id not in", values, "id");
            return this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return this;
        }

        public Criteria andTSuppliersIdIsNull() {
            addCriterion("t_suppliers_id is null");
            return this;
        }

        public Criteria andTSuppliersIdIsNotNull() {
            addCriterion("t_suppliers_id is not null");
            return this;
        }

        public Criteria andTSuppliersIdEqualTo(String value) {
            addCriterion("t_suppliers_id =", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdNotEqualTo(String value) {
            addCriterion("t_suppliers_id <>", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdGreaterThan(String value) {
            addCriterion("t_suppliers_id >", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdGreaterThanOrEqualTo(String value) {
            addCriterion("t_suppliers_id >=", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdLessThan(String value) {
            addCriterion("t_suppliers_id <", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdLessThanOrEqualTo(String value) {
            addCriterion("t_suppliers_id <=", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdLike(String value) {
            addCriterion("t_suppliers_id like", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdNotLike(String value) {
            addCriterion("t_suppliers_id not like", value, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdIn(List values) {
            addCriterion("t_suppliers_id in", values, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdNotIn(List values) {
            addCriterion("t_suppliers_id not in", values, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdBetween(String value1, String value2) {
            addCriterion("t_suppliers_id between", value1, value2, "tSuppliersId");
            return this;
        }

        public Criteria andTSuppliersIdNotBetween(String value1, String value2) {
            addCriterion("t_suppliers_id not between", value1, value2, "tSuppliersId");
            return this;
        }

        public Criteria andBrandIsNull() {
            addCriterion("brand is null");
            return this;
        }

        public Criteria andBrandIsNotNull() {
            addCriterion("brand is not null");
            return this;
        }

        public Criteria andBrandEqualTo(String value) {
            addCriterion("brand =", value, "brand");
            return this;
        }

        public Criteria andBrandNotEqualTo(String value) {
            addCriterion("brand <>", value, "brand");
            return this;
        }

        public Criteria andBrandGreaterThan(String value) {
            addCriterion("brand >", value, "brand");
            return this;
        }

        public Criteria andBrandGreaterThanOrEqualTo(String value) {
            addCriterion("brand >=", value, "brand");
            return this;
        }

        public Criteria andBrandLessThan(String value) {
            addCriterion("brand <", value, "brand");
            return this;
        }

        public Criteria andBrandLessThanOrEqualTo(String value) {
            addCriterion("brand <=", value, "brand");
            return this;
        }

        public Criteria andBrandLike(String value) {
            addCriterion("brand like", value, "brand");
            return this;
        }

        public Criteria andBrandNotLike(String value) {
            addCriterion("brand not like", value, "brand");
            return this;
        }

        public Criteria andBrandIn(List values) {
            addCriterion("brand in", values, "brand");
            return this;
        }

        public Criteria andBrandNotIn(List values) {
            addCriterion("brand not in", values, "brand");
            return this;
        }

        public Criteria andBrandBetween(String value1, String value2) {
            addCriterion("brand between", value1, value2, "brand");
            return this;
        }

        public Criteria andBrandNotBetween(String value1, String value2) {
            addCriterion("brand not between", value1, value2, "brand");
            return this;
        }
    }
}