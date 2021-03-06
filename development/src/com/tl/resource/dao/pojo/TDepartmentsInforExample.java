package com.tl.resource.dao.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TDepartmentsInforExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	public TDepartmentsInforExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	protected TDepartmentsInforExample(TDepartmentsInforExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table t_departments_infor
	 * @ibatorgenerated  Mon Oct 19 11:03:26 CST 2009
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

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition, List values,
				String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map map = new HashMap();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
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

		public Criteria andDepartNameIsNull() {
			addCriterion("depart_name is null");
			return this;
		}

		public Criteria andDepartNameIsNotNull() {
			addCriterion("depart_name is not null");
			return this;
		}

		public Criteria andDepartNameEqualTo(String value) {
			addCriterion("depart_name =", value, "departName");
			return this;
		}

		public Criteria andDepartNameNotEqualTo(String value) {
			addCriterion("depart_name <>", value, "departName");
			return this;
		}

		public Criteria andDepartNameGreaterThan(String value) {
			addCriterion("depart_name >", value, "departName");
			return this;
		}

		public Criteria andDepartNameGreaterThanOrEqualTo(String value) {
			addCriterion("depart_name >=", value, "departName");
			return this;
		}

		public Criteria andDepartNameLessThan(String value) {
			addCriterion("depart_name <", value, "departName");
			return this;
		}

		public Criteria andDepartNameLessThanOrEqualTo(String value) {
			addCriterion("depart_name <=", value, "departName");
			return this;
		}

		public Criteria andDepartNameLike(String value) {
			addCriterion("depart_name like", value, "departName");
			return this;
		}

		public Criteria andDepartNameNotLike(String value) {
			addCriterion("depart_name not like", value, "departName");
			return this;
		}

		public Criteria andDepartNameIn(List values) {
			addCriterion("depart_name in", values, "departName");
			return this;
		}

		public Criteria andDepartNameNotIn(List values) {
			addCriterion("depart_name not in", values, "departName");
			return this;
		}

		public Criteria andDepartNameBetween(String value1, String value2) {
			addCriterion("depart_name between", value1, value2, "departName");
			return this;
		}

		public Criteria andDepartNameNotBetween(String value1, String value2) {
			addCriterion("depart_name not between", value1, value2,
					"departName");
			return this;
		}

		public Criteria andParentIdIsNull() {
			addCriterion("parent_id is null");
			return this;
		}

		public Criteria andParentIdIsNotNull() {
			addCriterion("parent_id is not null");
			return this;
		}

		public Criteria andParentIdEqualTo(String value) {
			addCriterion("parent_id =", value, "parentId");
			return this;
		}

		public Criteria andParentIdNotEqualTo(String value) {
			addCriterion("parent_id <>", value, "parentId");
			return this;
		}

		public Criteria andParentIdGreaterThan(String value) {
			addCriterion("parent_id >", value, "parentId");
			return this;
		}

		public Criteria andParentIdGreaterThanOrEqualTo(String value) {
			addCriterion("parent_id >=", value, "parentId");
			return this;
		}

		public Criteria andParentIdLessThan(String value) {
			addCriterion("parent_id <", value, "parentId");
			return this;
		}

		public Criteria andParentIdLessThanOrEqualTo(String value) {
			addCriterion("parent_id <=", value, "parentId");
			return this;
		}

		public Criteria andParentIdLike(String value) {
			addCriterion("parent_id like", value, "parentId");
			return this;
		}

		public Criteria andParentIdNotLike(String value) {
			addCriterion("parent_id not like", value, "parentId");
			return this;
		}

		public Criteria andParentIdIn(List values) {
			addCriterion("parent_id in", values, "parentId");
			return this;
		}

		public Criteria andParentIdNotIn(List values) {
			addCriterion("parent_id not in", values, "parentId");
			return this;
		}

		public Criteria andParentIdBetween(String value1, String value2) {
			addCriterion("parent_id between", value1, value2, "parentId");
			return this;
		}

		public Criteria andParentIdNotBetween(String value1, String value2) {
			addCriterion("parent_id not between", value1, value2, "parentId");
			return this;
		}

		public Criteria andLeafIsNull() {
			addCriterion("leaf is null");
			return this;
		}

		public Criteria andLeafIsNotNull() {
			addCriterion("leaf is not null");
			return this;
		}

		public Criteria andLeafEqualTo(Integer value) {
			addCriterion("leaf =", value, "leaf");
			return this;
		}

		public Criteria andLeafNotEqualTo(Integer value) {
			addCriterion("leaf <>", value, "leaf");
			return this;
		}

		public Criteria andLeafGreaterThan(Integer value) {
			addCriterion("leaf >", value, "leaf");
			return this;
		}

		public Criteria andLeafGreaterThanOrEqualTo(Integer value) {
			addCriterion("leaf >=", value, "leaf");
			return this;
		}

		public Criteria andLeafLessThan(Integer value) {
			addCriterion("leaf <", value, "leaf");
			return this;
		}

		public Criteria andLeafLessThanOrEqualTo(Integer value) {
			addCriterion("leaf <=", value, "leaf");
			return this;
		}

		public Criteria andLeafIn(List values) {
			addCriterion("leaf in", values, "leaf");
			return this;
		}

		public Criteria andLeafNotIn(List values) {
			addCriterion("leaf not in", values, "leaf");
			return this;
		}

		public Criteria andLeafBetween(Integer value1, Integer value2) {
			addCriterion("leaf between", value1, value2, "leaf");
			return this;
		}

		public Criteria andLeafNotBetween(Integer value1, Integer value2) {
			addCriterion("leaf not between", value1, value2, "leaf");
			return this;
		}
	}
}