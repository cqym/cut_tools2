package com.tl.resource.dao.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TForumExample {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	protected List oredCriteria;

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	public TForumExample() {
		oredCriteria = new ArrayList();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	protected TForumExample(TForumExample example) {
		this.orderByClause = example.orderByClause;
		this.oredCriteria = example.oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	public List getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table t_forum
	 * @ibatorgenerated  Wed May 01 22:12:53 CST 2013
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

		public Criteria andTitleIsNull() {
			addCriterion("title is null");
			return this;
		}

		public Criteria andTitleIsNotNull() {
			addCriterion("title is not null");
			return this;
		}

		public Criteria andTitleEqualTo(String value) {
			addCriterion("title =", value, "title");
			return this;
		}

		public Criteria andTitleNotEqualTo(String value) {
			addCriterion("title <>", value, "title");
			return this;
		}

		public Criteria andTitleGreaterThan(String value) {
			addCriterion("title >", value, "title");
			return this;
		}

		public Criteria andTitleGreaterThanOrEqualTo(String value) {
			addCriterion("title >=", value, "title");
			return this;
		}

		public Criteria andTitleLessThan(String value) {
			addCriterion("title <", value, "title");
			return this;
		}

		public Criteria andTitleLessThanOrEqualTo(String value) {
			addCriterion("title <=", value, "title");
			return this;
		}

		public Criteria andTitleLike(String value) {
			addCriterion("title like", value, "title");
			return this;
		}

		public Criteria andTitleNotLike(String value) {
			addCriterion("title not like", value, "title");
			return this;
		}

		public Criteria andTitleIn(List values) {
			addCriterion("title in", values, "title");
			return this;
		}

		public Criteria andTitleNotIn(List values) {
			addCriterion("title not in", values, "title");
			return this;
		}

		public Criteria andTitleBetween(String value1, String value2) {
			addCriterion("title between", value1, value2, "title");
			return this;
		}

		public Criteria andTitleNotBetween(String value1, String value2) {
			addCriterion("title not between", value1, value2, "title");
			return this;
		}

		public Criteria andUserIdIsNull() {
			addCriterion("user_id is null");
			return this;
		}

		public Criteria andUserIdIsNotNull() {
			addCriterion("user_id is not null");
			return this;
		}

		public Criteria andUserIdEqualTo(String value) {
			addCriterion("user_id =", value, "userId");
			return this;
		}

		public Criteria andUserIdNotEqualTo(String value) {
			addCriterion("user_id <>", value, "userId");
			return this;
		}

		public Criteria andUserIdGreaterThan(String value) {
			addCriterion("user_id >", value, "userId");
			return this;
		}

		public Criteria andUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("user_id >=", value, "userId");
			return this;
		}

		public Criteria andUserIdLessThan(String value) {
			addCriterion("user_id <", value, "userId");
			return this;
		}

		public Criteria andUserIdLessThanOrEqualTo(String value) {
			addCriterion("user_id <=", value, "userId");
			return this;
		}

		public Criteria andUserIdLike(String value) {
			addCriterion("user_id like", value, "userId");
			return this;
		}

		public Criteria andUserIdNotLike(String value) {
			addCriterion("user_id not like", value, "userId");
			return this;
		}

		public Criteria andUserIdIn(List values) {
			addCriterion("user_id in", values, "userId");
			return this;
		}

		public Criteria andUserIdNotIn(List values) {
			addCriterion("user_id not in", values, "userId");
			return this;
		}

		public Criteria andUserIdBetween(String value1, String value2) {
			addCriterion("user_id between", value1, value2, "userId");
			return this;
		}

		public Criteria andUserIdNotBetween(String value1, String value2) {
			addCriterion("user_id not between", value1, value2, "userId");
			return this;
		}

		public Criteria andUserNameIsNull() {
			addCriterion("user_name is null");
			return this;
		}

		public Criteria andUserNameIsNotNull() {
			addCriterion("user_name is not null");
			return this;
		}

		public Criteria andUserNameEqualTo(String value) {
			addCriterion("user_name =", value, "userName");
			return this;
		}

		public Criteria andUserNameNotEqualTo(String value) {
			addCriterion("user_name <>", value, "userName");
			return this;
		}

		public Criteria andUserNameGreaterThan(String value) {
			addCriterion("user_name >", value, "userName");
			return this;
		}

		public Criteria andUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("user_name >=", value, "userName");
			return this;
		}

		public Criteria andUserNameLessThan(String value) {
			addCriterion("user_name <", value, "userName");
			return this;
		}

		public Criteria andUserNameLessThanOrEqualTo(String value) {
			addCriterion("user_name <=", value, "userName");
			return this;
		}

		public Criteria andUserNameLike(String value) {
			addCriterion("user_name like", value, "userName");
			return this;
		}

		public Criteria andUserNameNotLike(String value) {
			addCriterion("user_name not like", value, "userName");
			return this;
		}

		public Criteria andUserNameIn(List values) {
			addCriterion("user_name in", values, "userName");
			return this;
		}

		public Criteria andUserNameNotIn(List values) {
			addCriterion("user_name not in", values, "userName");
			return this;
		}

		public Criteria andUserNameBetween(String value1, String value2) {
			addCriterion("user_name between", value1, value2, "userName");
			return this;
		}

		public Criteria andUserNameNotBetween(String value1, String value2) {
			addCriterion("user_name not between", value1, value2, "userName");
			return this;
		}

		public Criteria andEditDateIsNull() {
			addCriterion("edit_date is null");
			return this;
		}

		public Criteria andEditDateIsNotNull() {
			addCriterion("edit_date is not null");
			return this;
		}

		public Criteria andEditDateEqualTo(Date value) {
			addCriterion("edit_date =", value, "editDate");
			return this;
		}

		public Criteria andEditDateNotEqualTo(Date value) {
			addCriterion("edit_date <>", value, "editDate");
			return this;
		}

		public Criteria andEditDateGreaterThan(Date value) {
			addCriterion("edit_date >", value, "editDate");
			return this;
		}

		public Criteria andEditDateGreaterThanOrEqualTo(Date value) {
			addCriterion("edit_date >=", value, "editDate");
			return this;
		}

		public Criteria andEditDateLessThan(Date value) {
			addCriterion("edit_date <", value, "editDate");
			return this;
		}

		public Criteria andEditDateLessThanOrEqualTo(Date value) {
			addCriterion("edit_date <=", value, "editDate");
			return this;
		}

		public Criteria andEditDateIn(List values) {
			addCriterion("edit_date in", values, "editDate");
			return this;
		}

		public Criteria andEditDateNotIn(List values) {
			addCriterion("edit_date not in", values, "editDate");
			return this;
		}

		public Criteria andEditDateBetween(Date value1, Date value2) {
			addCriterion("edit_date between", value1, value2, "editDate");
			return this;
		}

		public Criteria andEditDateNotBetween(Date value1, Date value2) {
			addCriterion("edit_date not between", value1, value2, "editDate");
			return this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("status is null");
			return this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("status is not null");
			return this;
		}

		public Criteria andStatusEqualTo(Integer value) {
			addCriterion("status =", value, "status");
			return this;
		}

		public Criteria andStatusNotEqualTo(Integer value) {
			addCriterion("status <>", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThan(Integer value) {
			addCriterion("status >", value, "status");
			return this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
			addCriterion("status >=", value, "status");
			return this;
		}

		public Criteria andStatusLessThan(Integer value) {
			addCriterion("status <", value, "status");
			return this;
		}

		public Criteria andStatusLessThanOrEqualTo(Integer value) {
			addCriterion("status <=", value, "status");
			return this;
		}

		public Criteria andStatusIn(List values) {
			addCriterion("status in", values, "status");
			return this;
		}

		public Criteria andStatusNotIn(List values) {
			addCriterion("status not in", values, "status");
			return this;
		}

		public Criteria andStatusBetween(Integer value1, Integer value2) {
			addCriterion("status between", value1, value2, "status");
			return this;
		}

		public Criteria andStatusNotBetween(Integer value1, Integer value2) {
			addCriterion("status not between", value1, value2, "status");
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

		public Criteria andForumTypeIsNull() {
			addCriterion("forum_type is null");
			return this;
		}

		public Criteria andForumTypeIsNotNull() {
			addCriterion("forum_type is not null");
			return this;
		}

		public Criteria andForumTypeEqualTo(Integer value) {
			addCriterion("forum_type =", value, "forumType");
			return this;
		}

		public Criteria andForumTypeNotEqualTo(Integer value) {
			addCriterion("forum_type <>", value, "forumType");
			return this;
		}

		public Criteria andForumTypeGreaterThan(Integer value) {
			addCriterion("forum_type >", value, "forumType");
			return this;
		}

		public Criteria andForumTypeGreaterThanOrEqualTo(Integer value) {
			addCriterion("forum_type >=", value, "forumType");
			return this;
		}

		public Criteria andForumTypeLessThan(Integer value) {
			addCriterion("forum_type <", value, "forumType");
			return this;
		}

		public Criteria andForumTypeLessThanOrEqualTo(Integer value) {
			addCriterion("forum_type <=", value, "forumType");
			return this;
		}

		public Criteria andForumTypeIn(List values) {
			addCriterion("forum_type in", values, "forumType");
			return this;
		}

		public Criteria andForumTypeNotIn(List values) {
			addCriterion("forum_type not in", values, "forumType");
			return this;
		}

		public Criteria andForumTypeBetween(Integer value1, Integer value2) {
			addCriterion("forum_type between", value1, value2, "forumType");
			return this;
		}

		public Criteria andForumTypeNotBetween(Integer value1, Integer value2) {
			addCriterion("forum_type not between", value1, value2, "forumType");
			return this;
		}

		public Criteria andAcceptUserNameIsNull() {
			addCriterion("accept_User_Name is null");
			return this;
		}

		public Criteria andAcceptUserNameIsNotNull() {
			addCriterion("accept_User_Name is not null");
			return this;
		}

		public Criteria andAcceptUserNameEqualTo(String value) {
			addCriterion("accept_User_Name =", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameNotEqualTo(String value) {
			addCriterion("accept_User_Name <>", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameGreaterThan(String value) {
			addCriterion("accept_User_Name >", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("accept_User_Name >=", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameLessThan(String value) {
			addCriterion("accept_User_Name <", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameLessThanOrEqualTo(String value) {
			addCriterion("accept_User_Name <=", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameLike(String value) {
			addCriterion("accept_User_Name like", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameNotLike(String value) {
			addCriterion("accept_User_Name not like", value, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameIn(List values) {
			addCriterion("accept_User_Name in", values, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameNotIn(List values) {
			addCriterion("accept_User_Name not in", values, "acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameBetween(String value1, String value2) {
			addCriterion("accept_User_Name between", value1, value2,
					"acceptUserName");
			return this;
		}

		public Criteria andAcceptUserNameNotBetween(String value1, String value2) {
			addCriterion("accept_User_Name not between", value1, value2,
					"acceptUserName");
			return this;
		}

		public Criteria andMemoIsNull() {
			addCriterion("memo is null");
			return this;
		}

		public Criteria andMemoIsNotNull() {
			addCriterion("memo is not null");
			return this;
		}

		public Criteria andMemoEqualTo(String value) {
			addCriterion("memo =", value, "memo");
			return this;
		}

		public Criteria andMemoNotEqualTo(String value) {
			addCriterion("memo <>", value, "memo");
			return this;
		}

		public Criteria andMemoGreaterThan(String value) {
			addCriterion("memo >", value, "memo");
			return this;
		}

		public Criteria andMemoGreaterThanOrEqualTo(String value) {
			addCriterion("memo >=", value, "memo");
			return this;
		}

		public Criteria andMemoLessThan(String value) {
			addCriterion("memo <", value, "memo");
			return this;
		}

		public Criteria andMemoLessThanOrEqualTo(String value) {
			addCriterion("memo <=", value, "memo");
			return this;
		}

		public Criteria andMemoLike(String value) {
			addCriterion("memo like", value, "memo");
			return this;
		}

		public Criteria andMemoNotLike(String value) {
			addCriterion("memo not like", value, "memo");
			return this;
		}

		public Criteria andMemoIn(List values) {
			addCriterion("memo in", values, "memo");
			return this;
		}

		public Criteria andMemoNotIn(List values) {
			addCriterion("memo not in", values, "memo");
			return this;
		}

		public Criteria andMemoBetween(String value1, String value2) {
			addCriterion("memo between", value1, value2, "memo");
			return this;
		}

		public Criteria andMemoNotBetween(String value1, String value2) {
			addCriterion("memo not between", value1, value2, "memo");
			return this;
		}

		public Criteria andProcessDateIsNull() {
			addCriterion("process_Date is null");
			return this;
		}

		public Criteria andProcessDateIsNotNull() {
			addCriterion("process_Date is not null");
			return this;
		}

		public Criteria andProcessDateEqualTo(Date value) {
			addCriterion("process_Date =", value, "processDate");
			return this;
		}

		public Criteria andProcessDateNotEqualTo(Date value) {
			addCriterion("process_Date <>", value, "processDate");
			return this;
		}

		public Criteria andProcessDateGreaterThan(Date value) {
			addCriterion("process_Date >", value, "processDate");
			return this;
		}

		public Criteria andProcessDateGreaterThanOrEqualTo(Date value) {
			addCriterion("process_Date >=", value, "processDate");
			return this;
		}

		public Criteria andProcessDateLessThan(Date value) {
			addCriterion("process_Date <", value, "processDate");
			return this;
		}

		public Criteria andProcessDateLessThanOrEqualTo(Date value) {
			addCriterion("process_Date <=", value, "processDate");
			return this;
		}

		public Criteria andProcessDateIn(List values) {
			addCriterion("process_Date in", values, "processDate");
			return this;
		}

		public Criteria andProcessDateNotIn(List values) {
			addCriterion("process_Date not in", values, "processDate");
			return this;
		}

		public Criteria andProcessDateBetween(Date value1, Date value2) {
			addCriterion("process_Date between", value1, value2, "processDate");
			return this;
		}

		public Criteria andProcessDateNotBetween(Date value1, Date value2) {
			addCriterion("process_Date not between", value1, value2,
					"processDate");
			return this;
		}

		public Criteria andAcceptUserIdIsNull() {
			addCriterion("accept_User_id is null");
			return this;
		}

		public Criteria andAcceptUserIdIsNotNull() {
			addCriterion("accept_User_id is not null");
			return this;
		}

		public Criteria andAcceptUserIdEqualTo(String value) {
			addCriterion("accept_User_id =", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdNotEqualTo(String value) {
			addCriterion("accept_User_id <>", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdGreaterThan(String value) {
			addCriterion("accept_User_id >", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("accept_User_id >=", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdLessThan(String value) {
			addCriterion("accept_User_id <", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdLessThanOrEqualTo(String value) {
			addCriterion("accept_User_id <=", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdLike(String value) {
			addCriterion("accept_User_id like", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdNotLike(String value) {
			addCriterion("accept_User_id not like", value, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdIn(List values) {
			addCriterion("accept_User_id in", values, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdNotIn(List values) {
			addCriterion("accept_User_id not in", values, "acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdBetween(String value1, String value2) {
			addCriterion("accept_User_id between", value1, value2,
					"acceptUserId");
			return this;
		}

		public Criteria andAcceptUserIdNotBetween(String value1, String value2) {
			addCriterion("accept_User_id not between", value1, value2,
					"acceptUserId");
			return this;
		}

		public Criteria andCopyUserNameIsNull() {
			addCriterion("copy_user_name is null");
			return this;
		}

		public Criteria andCopyUserNameIsNotNull() {
			addCriterion("copy_user_name is not null");
			return this;
		}

		public Criteria andCopyUserNameEqualTo(String value) {
			addCriterion("copy_user_name =", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameNotEqualTo(String value) {
			addCriterion("copy_user_name <>", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameGreaterThan(String value) {
			addCriterion("copy_user_name >", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameGreaterThanOrEqualTo(String value) {
			addCriterion("copy_user_name >=", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameLessThan(String value) {
			addCriterion("copy_user_name <", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameLessThanOrEqualTo(String value) {
			addCriterion("copy_user_name <=", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameLike(String value) {
			addCriterion("copy_user_name like", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameNotLike(String value) {
			addCriterion("copy_user_name not like", value, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameIn(List values) {
			addCriterion("copy_user_name in", values, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameNotIn(List values) {
			addCriterion("copy_user_name not in", values, "copyUserName");
			return this;
		}

		public Criteria andCopyUserNameBetween(String value1, String value2) {
			addCriterion("copy_user_name between", value1, value2,
					"copyUserName");
			return this;
		}

		public Criteria andCopyUserNameNotBetween(String value1, String value2) {
			addCriterion("copy_user_name not between", value1, value2,
					"copyUserName");
			return this;
		}

		public Criteria andCopyUserIdIsNull() {
			addCriterion("copy_user_id is null");
			return this;
		}

		public Criteria andCopyUserIdIsNotNull() {
			addCriterion("copy_user_id is not null");
			return this;
		}

		public Criteria andCopyUserIdEqualTo(String value) {
			addCriterion("copy_user_id =", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdNotEqualTo(String value) {
			addCriterion("copy_user_id <>", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdGreaterThan(String value) {
			addCriterion("copy_user_id >", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdGreaterThanOrEqualTo(String value) {
			addCriterion("copy_user_id >=", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdLessThan(String value) {
			addCriterion("copy_user_id <", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdLessThanOrEqualTo(String value) {
			addCriterion("copy_user_id <=", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdLike(String value) {
			addCriterion("copy_user_id like", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdNotLike(String value) {
			addCriterion("copy_user_id not like", value, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdIn(List values) {
			addCriterion("copy_user_id in", values, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdNotIn(List values) {
			addCriterion("copy_user_id not in", values, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdBetween(String value1, String value2) {
			addCriterion("copy_user_id between", value1, value2, "copyUserId");
			return this;
		}

		public Criteria andCopyUserIdNotBetween(String value1, String value2) {
			addCriterion("copy_user_id not between", value1, value2,
					"copyUserId");
			return this;
		}
	}
}