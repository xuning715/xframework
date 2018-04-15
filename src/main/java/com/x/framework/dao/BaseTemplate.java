package com.x.framework.dao;

import com.x.framework.Base;

import java.sql.SQLException;

public abstract class BaseTemplate extends Base {
	//////////////////////////////////// 跨数据源事务同步处理//////////////////////////////////////////

	/**
	 * 远程更新
	 * 
	 * @param key
	 *            String
	 * @param sql
	 *            String
	 * @return int
	 * @throws SQLException
	 *             public int execute(String key, String sql) throws
	 *             SQLException { Object[] params = null; return
	 *             xJdbcTemplate.execute(key, sql, params); }
	 */

	/**
	 * 远程更新
	 * 
	 * @param connection
	 *            Connection
	 * @param sql
	 *            String
	 * @param params
	 *            Object[]
	 * @return int
	 * @throws SQLException
	 *             public int execute(String key, String sql, Object[] params)
	 *             throws SQLException { return xJdbcTemplate.execute(key, sql,
	 *             params); }
	 */

	/**
	 * 远程更新
	 * 
	 * @param key
	 *            String
	 * @param sql
	 *            String
	 * @param params
	 *            List
	 * @return int
	 * @throws SQLException
	 *             public int execute(String key, String sql, List
	 *             <Object> params) throws SQLException { if (params == null ||
	 *             params.size() == 0) { return this.execute(key, sql); } else {
	 *             return xJdbcTemplate.execute(key, sql, params.toArray()); } }
	 * 
	 *             public <T extends BaseObject> T insert(String key, T model)
	 *             throws Exception { model = this.setModelPkFieldValue(model);
	 *             String tableName =
	 *             ModelMap.getMappingModel(model.getClass()).getTableName();
	 *             Map<MappingColumn, Object> columnMap =
	 *             this.getMappingColumnValues(model, INSERT); String columnStr
	 *             = BLANK; String valueStr = BLANK; List<Object> params = new
	 *             ArrayList<Object>(); MappingColumn mappingColumn; for
	 *             (Map.Entry<MappingColumn, Object> entry :
	 *             columnMap.entrySet()) { mappingColumn = entry.getKey();
	 *             columnStr += mappingColumn.columnName() + COMMA_BLANK;
	 *             valueStr += QUESTION_COMMA_BLANK;
	 *             params.add(entry.getValue()); } if (columnStr.length() > 0) {
	 *             columnStr = columnStr.substring(0,
	 *             columnStr.lastIndexOf(COMMA_BLANK)); } if (valueStr.length()
	 *             > 0) { valueStr = valueStr.substring(0,
	 *             valueStr.lastIndexOf(COMMA_BLANK)); } String sql =
	 *             INSERT_BLANK_INTO_BLANK + tableName + LEFT_BRACKETS +
	 *             columnStr +
	 *             RIGHT_BRACKETS_BLANK_VALUES_BLANK_LEFT_BRACKETS_BLANK +
	 *             valueStr + RIGHT_BRACKETS; this.execute(key, sql, params);
	 *             return model; }
	 * 
	 *             public <T extends BaseObject> int update(String key, T model)
	 *             throws Exception { if (model == null) { throw new
	 *             RuntimeException(MODEL_PARAM_IS_NULL); } String tableName =
	 *             ModelMap.getMappingModel(model.getClass()).getTableName();
	 *             Map<MappingColumn, Object> columnMap =
	 *             this.getMappingColumnValues(model, UPDATE); String columnStr
	 *             = BLANK; String whereStr = BLANK; List<Object> columnParams =
	 *             new ArrayList<Object>(); List<Object> whereParams = new
	 *             ArrayList<Object>(); MappingColumn mappingColumn; for
	 *             (Map.Entry<MappingColumn, Object> entry :
	 *             columnMap.entrySet()) { mappingColumn = entry.getKey();
	 *             Object fieldValue = entry.getValue(); if (fieldValue != null)
	 *             { if (mappingColumn.columnPk()) { whereStr +=
	 *             mappingColumn.columnName() +
	 *             BLANK_EQUAL_BLANK_QUESTION_BLANK_AND_BLANK;
	 *             whereParams.add(fieldValue); } else { columnStr +=
	 *             mappingColumn.columnName() +
	 *             BLANK_EQUAL_BLANK_QUESTION_COMMA_BLANK; if (fieldValue
	 *             instanceof String && fieldValue.equals(NULL)) { fieldValue =
	 *             null; } columnParams.add(fieldValue); } } } if
	 *             (columnStr.length() > 0) { columnStr = columnStr.substring(0,
	 *             columnStr.lastIndexOf(COMMA_BLANK)); } if (whereStr.length()
	 *             > 0) { whereStr = BLANK_WHERE_BLANK + whereStr.substring(0,
	 *             whereStr.lastIndexOf(BLANK_AND_BLANK)); }
	 *             columnParams.addAll(whereParams); String sql = UPDATE_BLANK +
	 *             tableName + BLANK_SET_BLANK + columnStr + whereStr; return
	 *             this.execute(key, sql, columnParams); }
	 * 
	 *             public <T extends BaseObject> int update(String key, T
	 *             modelSet, T modelWhere) throws Exception { if (modelSet ==
	 *             null) { throw new RuntimeException(MODEL_PARAM_IS_NULL); }
	 *             String tableName =
	 *             ModelMap.getMappingModel(modelSet.getClass()).getTableName();
	 *             Map<MappingColumn, Object> setColumnMap =
	 *             this.getMappingColumnValues(modelSet, UPDATE);
	 *             Map<MappingColumn, Object> whereColumnMap =
	 *             this.getMappingColumnValues(modelWhere, SELECT_BLANK); String
	 *             columnStr = BLANK; String whereStr = BLANK; List
	 *             <Object> params = new ArrayList<Object>(); MappingColumn
	 *             mappingColumn; for (Map.Entry<MappingColumn, Object> entry :
	 *             setColumnMap.entrySet()) { mappingColumn = entry.getKey();
	 *             Object fieldValue = entry.getValue(); if (fieldValue != null)
	 *             { columnStr += mappingColumn.columnName() +
	 *             BLANK_EQUAL_BLANK_QUESTION_COMMA_BLANK; if (fieldValue
	 *             instanceof String && fieldValue.equals(NULL)) { fieldValue =
	 *             null; } params.add(fieldValue); } } for
	 *             (Map.Entry<MappingColumn, Object> entry :
	 *             whereColumnMap.entrySet()) { mappingColumn = entry.getKey();
	 *             Object fieldValue = entry.getValue(); if (fieldValue != null)
	 *             { whereStr += mappingColumn.columnName() +
	 *             BLANK_EQUAL_BLANK_QUESTION_BLANK_AND_BLANK;
	 *             params.add(fieldValue); } } if (columnStr.length() > 0) {
	 *             columnStr = columnStr.substring(0,
	 *             columnStr.lastIndexOf(COMMA_BLANK)); } if (whereStr.length()
	 *             > 0) { whereStr = BLANK_WHERE_BLANK + whereStr.substring(0,
	 *             whereStr.lastIndexOf(BLANK_AND_BLANK)); } String sql =
	 *             UPDATE_BLANK + tableName + BLANK_SET_BLANK + columnStr +
	 *             whereStr; return this.execute(key, sql, params); }
	 * 
	 *             public <T extends BaseObject> int delete(String key, T model)
	 *             throws Exception { String tableName =
	 *             ModelMap.getMappingModel(model.getClass()).getTableName();
	 *             Map<MappingColumn, Object> columnMap =
	 *             this.getMappingColumnValues(model, DELETE); String whereStr =
	 *             BLANK; List<Object> params = new ArrayList<Object>();
	 *             MappingColumn mappingColumn; for (Map.Entry<MappingColumn,
	 *             Object> entry : columnMap.entrySet()) { mappingColumn =
	 *             entry.getKey(); Object fieldValue = entry.getValue(); if
	 *             (fieldValue != null) { whereStr += mappingColumn.columnName()
	 *             + BLANK_EQUAL_BLANK_QUESTION_BLANK_AND_BLANK;
	 *             params.add(fieldValue); } } if (whereStr.length() > 0) {
	 *             whereStr = BLANK_WHERE_BLANK + whereStr.substring(0,
	 *             whereStr.lastIndexOf(BLANK_AND_BLANK)); } String sql =
	 *             DELETE_BLANK_FROM_BLANK + tableName + whereStr; return
	 *             this.execute(key, sql, params); }
	 */

	/**
	 * 无参数查询返回Object，且返回值不能为空
	 * 
	 * @param sql
	 *            String
	 * @param mapper
	 *            RowMapper
	 * @return Object public <T> Object queryForObject(String key, String sql,
	 *         RowMapper<T> mapper) throws SQLException { Object[] params =
	 *         null; return xJdbcTemplate.queryForObject(key, sql, params,
	 *         mapper); }
	 */

	/**
	 * 数组参数查询返回Object，且返回值不能为空
	 * 
	 * @param sql
	 *            String
	 * @param params
	 *            Object[]
	 * @param mapper
	 *            RowMapper
	 * @return Object public <T> Object queryForObject(String key, String sql,
	 *         Object params[], RowMapper<T> mapper) throws SQLException {
	 *         return xJdbcTemplate.queryForObject(key, sql, params, mapper); }
	 */

	/**
	 * list参数查询返回Object，且Object不能为空
	 * 
	 * @param sql
	 *            String
	 * @param params
	 *            List
	 * @param mapper
	 *            RowMapper
	 * @return Object public <T> Object queryForObject(String key, String sql,
	 *         List<Object> params, RowMapper<T> mapper) throws SQLException {
	 *         if (params == null || params.size() == 0) { return
	 *         this.queryForObject(key, sql, mapper); } else { return
	 *         xJdbcTemplate.queryForObject(key, sql, params.toArray(), mapper);
	 *         } }
	 */

	/**
	 * 远程查询
	 * 
	 * @param key
	 *            String
	 * @param sql
	 *            String
	 * @param mapper
	 *            RowMapper
	 * @return List
	 * @throws SQLException
	 *             public <T> List<T> queryForList(String key, String sql,
	 *             RowMapper<T> mapper) throws SQLException { Object[] params =
	 *             null; return xJdbcTemplate.queryForList(key, sql, params,
	 *             mapper); }
	 */

	/**
	 * 远程查询
	 * 
	 * @param key
	 *            String
	 * @param sql
	 *            String
	 * @param params
	 *            Object[]
	 * @param mapper
	 *            RowMapper
	 * @return List
	 * @throws SQLException
	 *             public <T> List<T> queryForList(String key, String sql,
	 *             Object[] params, RowMapper<T> mapper) throws SQLException {
	 *             return xJdbcTemplate.queryForList(key, sql, params, mapper);
	 *             }
	 */

	/**
	 * 远程查询
	 *
	 * @param key
	 *            String
	 * @param sql
	 *            String
	 * @param params
	 *            List
	 * @param mapper
	 *            RowMapper
	 * @return List
	 * @throws SQLException
	 *             public <T> List<T> queryForList(String key, String sql, List
	 *             <Object> params, RowMapper<T> mapper) throws SQLException {
	 *             if (params == null || params.size() == 0) { return
	 *             this.queryForList(key, sql, mapper); } else { return
	 *             xJdbcTemplate.queryForList(key, sql, params.toArray(),
	 *             mapper); } }
	 *
	 *             public <T> List<T> queryForListPage(String key, String sql,
	 *             List<Object> params, int pageIndex, int pageSize, RowMapper
	 *             <T> mapper) throws SQLException { if
	 *             (this.xJdbcTemplate.getDataBaseType().toUpperCase().equals(
	 *             DATABASE_TYPE_MYSQL)) { return
	 *             this.queryForListPageMysql(key, sql, params, pageIndex,
	 *             pageSize, mapper); } else if
	 *             (this.xJdbcTemplate.getDataBaseType().toUpperCase().equals(
	 *             DATABASE_TYPE_ORACLE)) { return
	 *             this.queryForListPageOracle(key, sql, params, pageIndex,
	 *             pageSize, mapper); } return null; }
	 */

	/**
	 * 远程查询
	 *
	 * @param key
	 *            String
	 * @param sql
	 *            String
	 * @param params
	 *            List
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @param mapper
	 *            RowMapper
	 * @return List
	 * @throws SQLException
	 *             private <T> List<T> queryForListPageOracle(String key, String
	 *             sql, List<Object> params, int pageIndex, int pageSize,
	 *             RowMapper<T> mapper) throws SQLException { sql =
	 *             SELECT_PAGE_BEGIN_ORACLE + sql + SELECT_PAGE_END_ORACLE;
	 *             params.add((pageIndex - 1) * pageSize); params.add(pageIndex
	 *             * pageSize); return this.queryForList(key, sql, params,
	 *             mapper); }
	 *
	 *             private <T> List<T> queryForListPageMysql(String key, String
	 *             sql, List<Object> params, int pageIndex, int pageSize,
	 *             RowMapper<T> mapper) throws SQLException { sql =
	 *             SELECT_PAGE_BEGIN_MYSQL + sql + SELECT_PAGE_END_MYSQL;
	 *             params.add((pageIndex - 1) * pageSize); params.add(pageSize);
	 *             return this.queryForList(key, sql, params, mapper); }
	 */

	/**
	 * 无参数查询返回int
	 *
	 * @param sql
	 *            String
	 * @return int public int queryForInt(String key, String sql) throws
	 *         SQLException { Object[] params = null; return
	 *         this.queryForInt(key, sql, params); }
	 */

	/**
	 * 数组参数查询返回int
	 *
	 * @param sql
	 *            String
	 * @param params
	 *            Object[]
	 * @return int public int queryForInt(String key, String sql, Object[]
	 *         params) throws SQLException { if (!isString(key)) { return
	 *         this.queryForInt(sql, params); } else { RowMapper<Integer> mapper
	 *         = new BeanPropertyRowMapper<Integer>() { public Integer
	 *         mapRow(ResultSet rs, int rowNum) throws SQLException { return
	 *         rs.getInt(1); } }; List<Integer> list; if (params == null ||
	 *         params.length == 0) { list = this.queryForList(key, sql, mapper);
	 *         } else { list = xJdbcTemplate.queryForList(key, sql, params,
	 *         mapper); } if (list != null && list.size() > 0 && list.get(0) !=
	 *         null) { return Integer.parseInt(list.get(0).toString()); } else {
	 *         return 0; } } }
	 */

	/**
	 * list参数查询返回int
	 *
	 * @param sql
	 *            String
	 * @param params
	 *            List
	 * @return int public int queryForInt(String key, String sql, List
	 *         <Object> params) throws SQLException { if (params == null &&
	 *         params.size() == 0) { return this.queryForInt(key, sql); } else {
	 *         return this.queryForInt(key, sql, params.toArray()); } }
	 */

	/**
	 * 取得序列currval
	 * 
	 * @param seqName
	 *            String
	 * @return int public int getSeqCurrval(String key, String seqName) throws
	 *         SQLException { String sql = SELECT_BLANK + seqName + CURRVAL;
	 *         return this.queryForInt(key, sql); }
	 */

	/**
	 * 取得序列nextval
	 * 
	 * @param seqName
	 *            String
	 * @return int public int getSeqNextval(String key, String seqName) throws
	 *         SQLException { String sql = SELECT_BLANK + seqName + NEXTVAL;
	 *         return this.queryForInt(key, sql); }
	 */




}
