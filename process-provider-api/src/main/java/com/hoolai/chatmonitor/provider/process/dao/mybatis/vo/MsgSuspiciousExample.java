package com.hoolai.chatmonitor.provider.process.dao.mybatis.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MsgSuspiciousExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public MsgSuspiciousExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andGameIdIsNull() {
            addCriterion("game_id is null");
            return (Criteria) this;
        }

        public Criteria andGameIdIsNotNull() {
            addCriterion("game_id is not null");
            return (Criteria) this;
        }

        public Criteria andGameIdEqualTo(Long value) {
            addCriterion("game_id =", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotEqualTo(Long value) {
            addCriterion("game_id <>", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThan(Long value) {
            addCriterion("game_id >", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdGreaterThanOrEqualTo(Long value) {
            addCriterion("game_id >=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThan(Long value) {
            addCriterion("game_id <", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdLessThanOrEqualTo(Long value) {
            addCriterion("game_id <=", value, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdIn(List<Long> values) {
            addCriterion("game_id in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotIn(List<Long> values) {
            addCriterion("game_id not in", values, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdBetween(Long value1, Long value2) {
            addCriterion("game_id between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andGameIdNotBetween(Long value1, Long value2) {
            addCriterion("game_id not between", value1, value2, "gameId");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("msg is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("msg is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("msg =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("msg <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("msg >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("msg >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("msg <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("msg <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("msg like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("msg not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("msg in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("msg not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("msg between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("msg not between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsIsNull() {
            addCriterion("illegal_words is null");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsIsNotNull() {
            addCriterion("illegal_words is not null");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsEqualTo(String value) {
            addCriterion("illegal_words =", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsNotEqualTo(String value) {
            addCriterion("illegal_words <>", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsGreaterThan(String value) {
            addCriterion("illegal_words >", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsGreaterThanOrEqualTo(String value) {
            addCriterion("illegal_words >=", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsLessThan(String value) {
            addCriterion("illegal_words <", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsLessThanOrEqualTo(String value) {
            addCriterion("illegal_words <=", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsLike(String value) {
            addCriterion("illegal_words like", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsNotLike(String value) {
            addCriterion("illegal_words not like", value, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsIn(List<String> values) {
            addCriterion("illegal_words in", values, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsNotIn(List<String> values) {
            addCriterion("illegal_words not in", values, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsBetween(String value1, String value2) {
            addCriterion("illegal_words between", value1, value2, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andIllegalWordsNotBetween(String value1, String value2) {
            addCriterion("illegal_words not between", value1, value2, "illegalWords");
            return (Criteria) this;
        }

        public Criteria andOpUidIsNull() {
            addCriterion("op_uid is null");
            return (Criteria) this;
        }

        public Criteria andOpUidIsNotNull() {
            addCriterion("op_uid is not null");
            return (Criteria) this;
        }

        public Criteria andOpUidEqualTo(Long value) {
            addCriterion("op_uid =", value, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidNotEqualTo(Long value) {
            addCriterion("op_uid <>", value, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidGreaterThan(Long value) {
            addCriterion("op_uid >", value, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidGreaterThanOrEqualTo(Long value) {
            addCriterion("op_uid >=", value, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidLessThan(Long value) {
            addCriterion("op_uid <", value, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidLessThanOrEqualTo(Long value) {
            addCriterion("op_uid <=", value, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidIn(List<Long> values) {
            addCriterion("op_uid in", values, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidNotIn(List<Long> values) {
            addCriterion("op_uid not in", values, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidBetween(Long value1, Long value2) {
            addCriterion("op_uid between", value1, value2, "opUid");
            return (Criteria) this;
        }

        public Criteria andOpUidNotBetween(Long value1, Long value2) {
            addCriterion("op_uid not between", value1, value2, "opUid");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table m_suspicious
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table m_suspicious
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }


	       ////*******自定义开始********/
        //***********自定义结束****////
}
