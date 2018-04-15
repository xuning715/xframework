package com.x.framework.dao;

import com.x.framework.Base;
import com.x.framework.model.BaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class BaseDao extends Base {

	private XJdbcTemplate xJdbcTemplate;

	public BaseDao() {
	}

	@Autowired
	public void setXJdbcTemplate(XJdbcTemplate xJdbcTemplate) {
		this.xJdbcTemplate = xJdbcTemplate;
	}

	public <T> Object queryForObject(String sql, RowMapper<T> mapper) {
		return this.xJdbcTemplate.queryForObject(sql, mapper);
	}

	public <T> Object queryForObject(String sql, RowMapper<T> mapper, Object[] params) {
		return this.xJdbcTemplate.queryForObject(sql, mapper, params);
	}

	public <T> Object queryForObject(String sql, RowMapper<T> mapper, List params) {
		return this.xJdbcTemplate.queryForObject(sql, mapper, params);
	}

	public <T extends BaseObject> Object queryForObject(String sql, Class<T> clazz) throws Exception {
		return this.xJdbcTemplate.queryForObject(sql, clazz);
	}

	public <T extends BaseObject> Object queryForObject(String sql, Class<T> clazz, Object params[]) throws Exception {
		return this.xJdbcTemplate.queryForObject(sql, clazz, params);
	}

	public <T extends BaseObject> Object queryForObject(String sql, Class<T> clazz, List<Object> params) throws Exception {
		return this.xJdbcTemplate.queryForObject(sql, clazz, params);
	}

	/**
	 * 数组参数查询返回list
	 * @return List
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> mapper) {
		return this.xJdbcTemplate.queryForList(sql, mapper);
	}

	public <T> List<T> queryForList(String sql, RowMapper<T> mapper, Object[] params) {
		return this.xJdbcTemplate.queryForList(sql, mapper, params);
	}

	public <T> List<T> queryForList(String sql, RowMapper<T> mapper, List params) {
		return this.xJdbcTemplate.queryForList(sql, mapper, params);
	}

	public <T> List<T> queryForList(String sql, RowMapper<T> mapper, List<Object> params, BaseObject baseObject) {
		return this.xJdbcTemplate.queryForList(sql, mapper, params, baseObject);
	}

	public <T extends BaseObject> List<T> queryForList(String sql, Class<T> clazz) throws Exception {
		return this.xJdbcTemplate.queryForList(sql, clazz);
	}

	public <T extends BaseObject> List<T> queryForList(String sql, Class<T> clazz, Object[] params) throws Exception {
		return this.xJdbcTemplate.queryForList(sql, clazz, params);
	}

	public <T extends BaseObject> List<T> queryForList(String sql, Class<T> clazz, List<Object> params) throws Exception {
		return this.xJdbcTemplate.queryForList(sql, clazz, params);
	}

	public <T extends BaseObject> List<T> queryForList(String sql, Class<T> clazz, List<Object> params, BaseObject baseObject) throws Exception {
		return this.xJdbcTemplate.queryForList(sql, clazz, params, baseObject);
	}

	public int queryForInt(String sql, Object... params) {
		return this.xJdbcTemplate.queryForInt(sql, params);
	}

	public int queryForInt(String sql, List<Object> params) {
		return this.xJdbcTemplate.queryForInt(sql, params);
	}

	public int getSeqCurrval(String seqName) {
		return this.xJdbcTemplate.getSeqCurrval(seqName);
	}

	public int getSeqNextval(String seqName) {
		return this.xJdbcTemplate.queryForInt(seqName);
	}

	/**
	 * 数组参数更新操作
	 *
	 * @param sql
	 *            String
	 * @param params
	 *            Object[]
	 * @return int
	 */
	public int execute(String sql, Object... params) {
		return  this.xJdbcTemplate.execute(sql, params);
	}

	public int execute(String sql, List<Object> params) {
		return this.xJdbcTemplate.execute(sql, params);
	}

	public <T extends BaseObject> T insert(T model) throws Exception {
		return this.xJdbcTemplate.insert(model);
	}

	public <T extends BaseObject> int update(T model) {
		return this.xJdbcTemplate.update(model);
	}

	public <T extends BaseObject> int update(T modelSet, T modelWhere) {
		return this.xJdbcTemplate.update(modelSet, modelWhere);
	}

	public <T extends BaseObject> int delete(T model) {
		return this.xJdbcTemplate.delete(model);
	}

	/**
	 * 调用存储过程
	 *
	 * @param sql
	 *            String
	 * @param inParams
	 *            Object[]
	 * @param inTypes
	 *            int[]
	 * @param outParams
	 *            Object[]
	 * @param outTypes
	 *            int[]
	 * @return Object[]
	 */
	public Object[] callableStatement(final String sql, final Object[] inParams, final int[] inTypes, final Object[] outParams, final int[] outTypes) {
		return this.xJdbcTemplate.callableStatement(sql, inParams, inTypes, outParams, outTypes);
	}

	/**
	 * 把ResultSet转化成map，再把map转化成model，再把model转化成RowMapper
	 *
	 * @return RowMapper
	 * @throws Exception
	 */
	public <T extends BaseObject> RowMapper<T> resultSetToModelRowMapper(final String sql, final Class<T> modelClass) throws Exception {
		return this.xJdbcTemplate.resultSetToModelRowMapper(sql, modelClass);
	}

}
