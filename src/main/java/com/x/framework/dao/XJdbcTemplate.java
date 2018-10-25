package com.x.framework.dao;

import com.x.framework.annotation.MappingColumn;
import com.x.framework.model.BaseModel;
import com.x.framework.model.MappingField;
import com.x.framework.model.MappingModel;
import com.x.framework.model.ModelMap;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.x.framework.Base;

import org.springframework.jdbc.core.CallableStatementCallback;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class XJdbcTemplate {
	private static final Logger logger = LoggerFactory.getLogger(XJdbcTemplate.class);
	private static final String DATABASE_TYPE_ORACLE = "ORACLE";
	private static final String DATABASE_TYPE_MYSQL = "MYSQL";
	private static final String JAVA_LANG_STRING = "java.lang.String";
	private static final String JAVA_LANG_INTEGER = "java.lang.Integer";
	private static final String INT = "int";
	private static final String JAVA_LANG_DOUBLE = "java.lang.Double";
	private static final String DOUBLE = "double";
	private static final String JAVA_LANG_LONG = "java.lang.Long";
	private static final String LONG = "long";
	private static final String JAVA_LANG_SHORT = "java.lang.Short";
	private static final String SHORT = "short";
	private static final String JAVA_LANG_FLOAT = "java.lang.Float";
	private static final String FLOAT = "float";
	private static final String JAVA_UTIL_DATE = "java.util.Date";
	private static final String BIGDECIMAL = "java.math.BigDecimal";
	private static final String SQL_TIMESTAMP = "sql.TIMESTAMP";
	private static final String CLOB = "CLOB";
	private static final String NULL = "null";
	private static final String AUTO = "AUTO";
	private static final String SEQ = "SEQ";
	private static final String SYSDATE = "SYSDATE";
	private static final String SPACE = " ";
	private static final String SELECT_PAGE_BEGIN_ORACLE = "SELECT * FROM (SELECT ROW_.*, rownum ROWNUM_ FROM (";
	private static final String SELECT_PAGE_END_ORACLE = ") ROW_) WHERE ROWNUM_ > ? AND ROWNUM_ <= ?";
	private static final String SELECT_PAGE_BEGIN_MYSQL = "SELECT * FROM (";
	private static final String SELECT_PAGE_END_MYSQL = ") t LIMIT ?, ?";
	private static final String COMMA_BLANK = ", ";
	private static final String QUESTION_COMMA_BLANK = "?, ";
	private static final String INSERT_BLANK_INTO_BLANK = "INSERT INTO ";
	private static final String INSERT = "INSERT";
	private static final String LEFT_BRACKETS = "(";
	private static final String RIGHT_BRACKETS = ")";
	private static final String RIGHT_BRACKETS_BLANK_VALUES_BLANK_LEFT_BRACKETS_BLANK = ") VALUES (";
	private static final String BLANK_EQUAL_BLANK_QUESTION_BLANK_AND_BLANK = " = ? AND ";
	private static final String BLANK_EQUAL_BLANK_QUESTION_COMMA_BLANK = " = ?, ";
	private static final String BLANK_WHERE_BLANK = " WHERE ";
	private static final String BLANK_AND_BLANK = " AND ";
	private static final String UPDATE_BLANK = "UPDATE ";
	private static final String UPDATE = "UPDATE";
	private static final String BLANK_SET_BLANK = " SET ";
	private static final String DELETE_BLANK_FROM_BLANK = "DELETE FROM ";
	private static final String DELETE = "DELETE";
	private static final String SELECT_BLANK = "SELECT ";
	private static final String CURRVAL = ".CURRVAL AS NUM FROM DUAL";
	private static final String NEXTVAL = ".NEXTVAL AS NUM FROM DUAL";
	private static final String ZERO = "0";
	private static final String ZERO_DOT_ZERO = "0.0";
	private static final String MODEL_PARAM_IS_NULL = "models参数为空";
	private static final String NO_PK_TYPE = "没有指定主键生成方式";
	private static final String PARAM = "param";
	private static final String PARAM_BRACE = "={}";

	private JdbcTemplate jdbcTemplate;

	private String dataBaseType;

	public XJdbcTemplate() {
	}

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public <T> Object queryForObject(String sql, RowMapper<T> mapper) {
		List<T> list = this.queryForList(sql, mapper);
		T t = null;
		if (list != null && list.size() > 0) {
			t = list.get(0);
		}
		return t;
	}

    public <T> Object queryForObject(String sql, RowMapper<T> mapper, Object[] params) {
        if (params == null || params.length == 0) {
            return this.queryForObject(sql, mapper);
        } else {
			List<T> list = this.queryForList(sql, mapper, params);
			T t = null;
			if (list != null && list.size() > 0) {
				t = list.get(0);
			}
			return t;
        }
    }

    public <T> Object queryForObject(String sql, RowMapper<T> mapper, List params) {
        if (params == null || params.size() == 0) {
            return this.queryForObject(sql, mapper);
        } else {
            return this.queryForObject(sql, mapper, params.toArray());
        }
    }

    public <T extends BaseModel> Object queryForObject(String sql, Class<T> clazz) throws Exception {
        RowMapper<T> mapper = this.resultSetToModelRowMapper(sql, clazz);
        return this.queryForObject(sql, mapper);
    }

    public <T extends BaseModel> Object queryForObject(String sql, Class<T> clazz, Object params[]) throws Exception {
        RowMapper<T> mapper = this.resultSetToModelRowMapper(sql, clazz);
        return this.queryForObject(sql, mapper, params);
    }

    public <T extends BaseModel> Object queryForObject(String sql, Class<T> clazz, List<Object> params) throws Exception {
        RowMapper<T> mapper = this.resultSetToModelRowMapper(sql, clazz);
        return this.queryForObject(sql, mapper, params);
    }

	/**
	 * 数组参数查询返回list
	 * @return List
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> mapper) {
		List params = new ArrayList(2);
		return this.queryForListPage(sql, mapper, params, 1, 1000);
	}

	public <T> List<T> queryForList(String sql, RowMapper<T> mapper, Object[] params) {
		if (params == null || params.length == 0) {
			return this.queryForList(sql, mapper);
		} else {
			return this.queryForListPage(sql, mapper, params, 1, 1000);
		}
	}

	public <T> List<T> queryForList(String sql, RowMapper<T> mapper, List params) {
		if (params == null || params.size() == 0) {
			return this.queryForList(sql, mapper);
		} else {
			return this.queryForList(sql, mapper, params.toArray());
		}
	}

    public <T> List<T> queryForList(String sql, RowMapper<T> mapper, List<Object> params, BaseModel BaseModel) {
        if (BaseModel == null || BaseModel.getPageIndex() == null || BaseModel.getPageSize() == null) {
            return this.queryForListPage(sql, mapper, params, 1, 1000);
        } else {
            return this.queryForListPage(sql, mapper, params, BaseModel.getPageIndex(), BaseModel.getPageSize());
        }
    }

    public <T extends BaseModel> List<T> queryForList(String sql, Class<T> clazz) throws Exception {
		RowMapper<T> mapper = this.resultSetToModelRowMapper(sql, clazz);
		return this.queryForList(sql, mapper);
	}

	public <T extends BaseModel> List<T> queryForList(String sql, Class<T> clazz, Object[] params) throws Exception {
		RowMapper<T> mapper = this.resultSetToModelRowMapper(sql, clazz);
		return this.queryForList(sql, mapper, params);
	}

	public <T extends BaseModel> List<T> queryForList(String sql, Class<T> clazz, List<Object> params) throws Exception {
		RowMapper<T> mapper = this.resultSetToModelRowMapper(sql, clazz);
		return this.queryForList(sql, mapper, params);
	}

    public <T extends BaseModel> List<T> queryForList(String sql, Class<T> clazz, List<Object> params, BaseModel BaseModel) throws Exception {
        RowMapper<T> mapper = this.resultSetToModelRowMapper(sql, clazz);
        return this.queryForList(sql, mapper, params, BaseModel);
    }

    private <T> List<T> queryForListPage(String sql, RowMapper<T> mapper, List<Object> params, int pageIndex, int pageSize) {
	    return this.queryForListPage(sql, mapper, params.toArray(), pageIndex, pageSize);
    }

    private <T> List<T> queryForListPage(String sql, RowMapper<T> mapper, Object[] params, int pageIndex, int pageSize) {
	    int paramsLength = params.length;
	    Object[] paramArray = new Object[paramsLength + 2];
	    System.arraycopy(params, 0, paramArray, 0, paramsLength);
        if (this.dataBaseType.equals(DATABASE_TYPE_MYSQL)) {
            return this.queryForListPageMysql(sql, mapper, paramArray, pageIndex, pageSize);
        } else if (this.dataBaseType.equals(DATABASE_TYPE_ORACLE)) {
            return this.queryForListPageOracle(sql, mapper, paramArray, pageIndex, pageSize);
        } else {
            throw new RuntimeException("DataBaseType is not ORACLE or MYSQL");
        }
    }

    private <T> List<T> queryForListPageMysql(String sql, RowMapper<T> mapper, Object[] params, int pageIndex, int pageSize) {
		sql = SELECT_PAGE_BEGIN_MYSQL + sql + SELECT_PAGE_END_MYSQL;
		params[params.length - 2] = (pageIndex - 1) * pageSize;
		params[params.length - 1] = pageSize;
		this.logParams(sql, params);
		return this.jdbcTemplate.query(sql, mapper, params);
	}

	private <T> List<T> queryForListPageOracle(String sql, RowMapper<T> mapper, Object[] params, int pageIndex, int pageSize) {
		sql = SELECT_PAGE_BEGIN_ORACLE + sql + SELECT_PAGE_END_ORACLE;
        params[params.length - 2] = (pageIndex - 1) * pageSize;
        params[params.length - 1] = pageIndex * pageSize;
		this.logParams(sql, params);
		return this.jdbcTemplate.query(sql, mapper, params);
	}

    public int queryForInt(String sql, Object... params) {
        RowMapper<Integer> mapper = new BeanPropertyRowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
            }
        };
        List<Integer> list;
        if (params == null || params.length == 0) {
            list = this.queryForList(sql, mapper);
        } else {
            list = this.queryForList(sql, mapper, params);
        }
        int num = 0;
        if (list != null && list.size() > 0) {
            num = list.get(0);
        }
		return num;
    }

    public int queryForInt(String sql, List<Object> params) {
        if (params == null && params.size() == 0) {
            return this.queryForInt(sql);
        } else {
            return this.queryForInt(sql, params.toArray());
        }
    }

    public int getSeqCurrval(String seqName) {
        String sql = SELECT_BLANK + seqName + CURRVAL;
        return this.queryForInt(sql);
    }

    public int getSeqNextval(String seqName) {
        String sql = SELECT_BLANK + seqName + NEXTVAL;
        return this.queryForInt(sql);
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
		this.logParams(sql, params);
		if (params == null || params.length == 0) {
			return this.jdbcTemplate.update(sql);
		} else {
			return this.jdbcTemplate.update(sql, params);
		}
	}

	public int execute(String sql, List<Object> params) {
		if (params == null || params.size() == 0) {
			return this.execute(sql);
		} else {
			return this.execute(sql, params.toArray());
		}
	}

	public <T extends BaseModel> T insert(T model) throws Exception {
		model = this.setModelPkFieldValue(model);
		String tableName = ModelMap.getMappingModel(model.getClass()).getTableName();
		Map<MappingColumn, Object> columnMap = this.getMappingColumnValues(model, INSERT);
		String columnStr = Base.BLANK;
		String valueStr = Base.BLANK;
		List<Object> params = new ArrayList<Object>();
		MappingColumn mappingColumn;
		for (Map.Entry<MappingColumn, Object> entry : columnMap.entrySet()) {
			mappingColumn = entry.getKey();
			if(!mappingColumn.columnReadOnly()) {
				columnStr += mappingColumn.columnName() + COMMA_BLANK;
				valueStr += QUESTION_COMMA_BLANK;
				params.add(entry.getValue());
			}
		}
		if (columnStr.length() > 0) {
			columnStr = columnStr.substring(0, columnStr.lastIndexOf(COMMA_BLANK));
		}
		if (valueStr.length() > 0) {
			valueStr = valueStr.substring(0, valueStr.lastIndexOf(COMMA_BLANK));
		}
		String sql = INSERT_BLANK_INTO_BLANK + tableName + LEFT_BRACKETS + columnStr + RIGHT_BRACKETS_BLANK_VALUES_BLANK_LEFT_BRACKETS_BLANK + valueStr + RIGHT_BRACKETS;
		this.execute(sql, params);
		return model;
	}

	public <T extends BaseModel> int update(T model) {
		String tableName = ModelMap.getMappingModel(model.getClass()).getTableName();
		Map<MappingColumn, Object> columnMap = this.getMappingColumnValues(model, UPDATE);
		String columnStr = Base.BLANK;
		String whereStr = Base.BLANK;
		List<Object> columnParams = new ArrayList<Object>();
		List<Object> whereParams = new ArrayList<Object>();
		MappingColumn mappingColumn;
		for (Map.Entry<MappingColumn, Object> entry : columnMap.entrySet()) {
			mappingColumn = entry.getKey();
			Object fieldValue = entry.getValue();
			if (fieldValue != null) {
				if (mappingColumn.columnPk()) {
					whereStr += mappingColumn.columnName() + BLANK_EQUAL_BLANK_QUESTION_BLANK_AND_BLANK;
					whereParams.add(fieldValue);
				} else {
					if(!mappingColumn.columnReadOnly()) {
						columnStr += mappingColumn.columnName() + BLANK_EQUAL_BLANK_QUESTION_COMMA_BLANK;
						if (String.class.isInstance(fieldValue) && fieldValue.equals(NULL)) {
							fieldValue = null;
						}
						columnParams.add(fieldValue);
					}
				}
			}
		}
		if (columnStr.length() > 0) {
			columnStr = columnStr.substring(0, columnStr.lastIndexOf(COMMA_BLANK));
		}
		if (whereStr.length() > 0) {
			whereStr = BLANK_WHERE_BLANK + whereStr.substring(0, whereStr.lastIndexOf(BLANK_AND_BLANK));
			columnParams.addAll(whereParams);
			String sql = UPDATE_BLANK + tableName + BLANK_SET_BLANK + columnStr + whereStr;
			return this.execute(sql, columnParams);
		} else {
			return 0;
		}
	}

	public <T extends BaseModel> int update(T modelSet, T modelWhere) {
		String tableName = ModelMap.getMappingModel(modelSet.getClass()).getTableName();
		Map<MappingColumn, Object> setColumnMap = this.getMappingColumnValues(modelSet, UPDATE);
		Map<MappingColumn, Object> whereColumnMap = this.getMappingColumnValues(modelWhere, SELECT_BLANK);
		String columnStr = Base.BLANK;
		String whereStr = Base.BLANK;
		List<Object> params = new ArrayList<Object>();
		MappingColumn mappingColumn;
		for (Map.Entry<MappingColumn, Object> entry : setColumnMap.entrySet()) {
			mappingColumn = entry.getKey();
			if(!mappingColumn.columnReadOnly()) {
				Object fieldValue = entry.getValue();
				if (fieldValue != null) {
					columnStr += mappingColumn.columnName() + BLANK_EQUAL_BLANK_QUESTION_COMMA_BLANK;
					if (String.class.isInstance(fieldValue) && fieldValue.equals(NULL)) {
						fieldValue = null;
					}
					params.add(fieldValue);
				}
			}
		}
		for (Map.Entry<MappingColumn, Object> entry : whereColumnMap.entrySet()) {
			mappingColumn = entry.getKey();
			Object fieldValue = entry.getValue();
			if (fieldValue != null) {
				whereStr += mappingColumn.columnName() + BLANK_EQUAL_BLANK_QUESTION_BLANK_AND_BLANK;
				params.add(fieldValue);
			}
		}
		if (columnStr.length() > 0) {
			columnStr = columnStr.substring(0, columnStr.lastIndexOf(COMMA_BLANK));
		}
		if (whereStr.length() > 0) {
			whereStr = BLANK_WHERE_BLANK + whereStr.substring(0, whereStr.lastIndexOf(BLANK_AND_BLANK));
			String sql = UPDATE_BLANK + tableName + BLANK_SET_BLANK + columnStr + whereStr;
			return this.execute(sql, params);
		} else {
			return 0;
		}
	}

	public <T extends BaseModel> int delete(T model) {
		String tableName = ModelMap.getMappingModel(model.getClass()).getTableName();
		Map<MappingColumn, Object> columnMap = this.getMappingColumnValues(model, DELETE);
		String whereStr = Base.BLANK;
		List<Object> params = new ArrayList<Object>();
		MappingColumn mappingColumn;
		for (Map.Entry<MappingColumn, Object> entry : columnMap.entrySet()) {
			mappingColumn = entry.getKey();
			if(!mappingColumn.columnReadOnly()) {
				Object fieldValue = entry.getValue();
				if (fieldValue != null) {
					whereStr += mappingColumn.columnName() + BLANK_EQUAL_BLANK_QUESTION_BLANK_AND_BLANK;
					params.add(fieldValue);
				}
			}
		}
		if (whereStr.length() > 0) {
			whereStr = BLANK_WHERE_BLANK + whereStr.substring(0, whereStr.lastIndexOf(BLANK_AND_BLANK));
			String sql = DELETE_BLANK_FROM_BLANK + tableName + whereStr;
			return this.execute(sql, params);
		} else {
			return 0;
		}
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
        logger.info(sql);
        CallableStatementCallback<Object> cb = (CallableStatement cs) -> {
//		CallableStatementCallback<Object> cb = new CallableStatementCallback<Object>() {
//			@Override
//			public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
            Object[] inParam = inParams;
            int inLen = inParam.length;
            int outLen = outParams.length;
            int i;
			this.logParams(sql, inParams);
            for (i = 0; i < inLen; i++) {
                cs.setObject(i + 1, inParam[i], inTypes[i]);
            }
            for (i = inLen; i < inLen + outLen; i++) {
                cs.registerOutParameter(i + 1, outTypes[i - inLen]);
            }
            cs.execute();
            for (i = 0; i < outLen; i++) {
                outParams[i] = cs.getObject(inLen + i + 1);
            }
            return outParams;
//			}
        };
        Object obj = this.jdbcTemplate.execute(sql, cb);
        return (Object[]) obj;
    }

    /**
     * 把ResultSet转化成map，再把map转化成model，再把model转化成RowMapper
     *
     * @return RowMapper
     * @throws SQLException
     */
    public <T extends BaseModel> RowMapper<T> resultSetToModelRowMapper(final String sql, final Class<T> modelClass) throws Exception {
        RowMapper<T> mapper = new BeanPropertyRowMapper<T>() {
            @Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    MappingModel<T> mappingModel = ModelMap.getMappingModel(modelClass);
                    Map<String, String> columnFieldMap = mappingModel.getColumnFieldMap();
                    Map<String, Object> valueMap = new HashMap<String, Object>();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int columnCount = rsmd.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rsmd.getColumnName(i).toUpperCase();
                        String fieldName = columnFieldMap.get(columnName);
                        String columnClassName = rsmd.getColumnClassName(i);
                        Object columnValue = rs.getObject(i);
                        if (fieldName != null && columnClassName != null && columnValue != null) {
                            if (columnClassName.equals(JAVA_LANG_STRING)) {
                                columnValue = columnValue.toString();
                            } else if (columnClassName.equals(BIGDECIMAL)) {
                                columnValue = rs.getBigDecimal(i);
                            } else if (columnClassName.toUpperCase().indexOf(SQL_TIMESTAMP) > -1) {
                                columnValue = new Date(rs.getTimestamp(i).getTime());
                            } else if (columnClassName.toUpperCase().indexOf(CLOB) > -1) {
                                Clob clob = (Clob) columnValue;
                                columnValue = clob.getSubString(1, (int) clob.length());
                            }
                            valueMap.put(fieldName, columnValue);
                        }
                    }

                    T modelInstance = modelClass.newInstance();

                    Map<String, List<MappingModel>> mappingModelListMap = mappingModel.getMappingModelListMap();
                    List<MappingModel> mappingFieldModelList = mappingModelListMap.get(sql);
                    String upperSql = sql.toUpperCase();
                    List<MappingModel> list = null;
                    if (mappingFieldModelList == null) {
                        mappingFieldModelList = mappingModel.getMappingModelList();
                        list = new ArrayList<MappingModel>();
                    }
                    String tableName;
                    for (MappingModel mappingFieldModel : mappingFieldModelList) {
                        tableName = mappingFieldModel.getTableName() + SPACE;
                        if (upperSql.contains(tableName)) {
                            BaseModel fieldInstance = (BaseModel) mappingFieldModel.getFieldType().newInstance();
                            BeanUtils.populate(fieldInstance, valueMap);
                            valueMap.put(mappingFieldModel.getFieldName(), fieldInstance);

                            upperSql = upperSql.replaceFirst(tableName, Base.BLANK);
                            if (list != null) {
                                list.add(mappingFieldModel);
                            }
                        }
                    }
                    if (list != null) {
                        mappingModelListMap.put(sql, list);
                        mappingModel.setMappingModelListMap(mappingModelListMap);
                    }
                    BeanUtils.populate(modelInstance, valueMap);
                    return modelInstance;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        return mapper;
    }

    /**
	 * 设置model的主键值
	 *
	 * @param model
	 *            BaseModel
	 * @return BaseModel
	 */
	private  <T extends BaseModel> T setModelPkFieldValue(T model) throws Exception {
		List<MappingField> mappingFieldList = ModelMap.getMappingModel(model.getClass()).getMappingFieldList();
		for (MappingField mappingField : mappingFieldList) {
			if (mappingField.getColumnPk()) {
				Field field = mappingField.getField();
				Object uid = field.get(model);
				if (uid == null || uid.toString().length() == 0) {
					String id = null;
					if (mappingField.getColumnPkType().equals(AUTO)) {
						id = Base.getUid(mappingField.getColumnLength());
					} else if (mappingField.getColumnPkType().equals(SEQ)) {
						id = this.getSeqNextval(mappingField.getColumnPkSeq()) + Base.BLANK;
					} else {
						throw new RuntimeException(NO_PK_TYPE);
					}
					String fieldClassTypeName = mappingField.getFieldClassTypeName();
					if (fieldClassTypeName.equals(JAVA_LANG_STRING)) {
						uid = id;
					} else if (fieldClassTypeName.equals(JAVA_LANG_INTEGER) || fieldClassTypeName.equals(INT)) {
						uid = Integer.parseInt(id);
					} else {
						uid = id;
					}
					field.set(model, uid);
				}
			}
		}
		return model;
	}

	/**
	 * 返回model所有属性注解及属性值集合
	 *
	 * @param model
	 *            BaseInterface
	 * @return Map
	 */
	private <T extends BaseModel> Map<MappingColumn, Object> getMappingColumnValues(T model, String flag) {
		Map<MappingColumn, Object> columnMap = new LinkedHashMap<MappingColumn, Object>();
		try {
			List<MappingField> mappingFieldList = ModelMap.getMappingModel(model.getClass()).getMappingFieldList();
			Field field;
			for (MappingField mappingField : mappingFieldList) {
				if (!mappingField.getColumnOnlySelect()) {
					String fieldClassTypeName = mappingField.getFieldClassTypeName();
					field = mappingField.getField();
					Object fieldValue = field.get(model);
					if (fieldValue != null) {
						if (fieldClassTypeName.equals(INT) ||
								fieldClassTypeName.equals(DOUBLE) ||
								fieldClassTypeName.equals(FLOAT) ||
								fieldClassTypeName.equals(LONG) ||
								fieldClassTypeName.equals(SHORT)) {
							fieldValue = fieldValue.toString();
							if (fieldValue.equals(ZERO) || fieldValue.equals(ZERO_DOT_ZERO)) {
								fieldValue = null;
							}
						}
					}
					if (fieldValue == null || fieldValue.equals(Base.BLANK)) {
						if (flag.equals(INSERT) || flag.equals(UPDATE)) {
							String columnDefaultValue = mappingField.getColumnDefaultValue();
							if (columnDefaultValue.length() > 0) {
								if (fieldClassTypeName.equals(JAVA_LANG_STRING)) {
									fieldValue = columnDefaultValue;
								} else if (fieldClassTypeName.equals(JAVA_LANG_INTEGER) || fieldClassTypeName.equals(INT)) {
									fieldValue = Integer.parseInt(columnDefaultValue);
								} else if (fieldClassTypeName.equals(JAVA_LANG_DOUBLE) || fieldClassTypeName.equals(DOUBLE)) {
									fieldValue = Double.parseDouble(columnDefaultValue);
								} else if (fieldClassTypeName.equals(JAVA_LANG_FLOAT) || fieldClassTypeName.equals(FLOAT)) {
									fieldValue = Float.parseFloat(columnDefaultValue);
								} else if (fieldClassTypeName.equals(JAVA_LANG_LONG) || fieldClassTypeName.equals(LONG)) {
									fieldValue = Long.parseLong(columnDefaultValue);
								} else if (fieldClassTypeName.equals(JAVA_LANG_SHORT) || fieldClassTypeName.equals(SHORT)) {
									fieldValue = Short.parseShort(columnDefaultValue);
								} else if (fieldClassTypeName.equals(JAVA_UTIL_DATE) && columnDefaultValue.equals(SYSDATE)) {
									fieldValue = new Date();
								}
							}
						}
					}
					if (fieldValue != null) {
						columnMap.put(mappingField.getMappingColumn(), fieldValue);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return columnMap;
	}

	/**
	 * 记录sql参数
	 * 
	 * @param params
	 *            Object[]
	 */
	public void logParams(String sql, final Object[] params) {
		logger.info(sql);
		if (params != null) {
			int i = 1;
			for (Object param : params) {
				logger.info(PARAM + (i++) + PARAM_BRACE, param);
			}
		}
	}

}
