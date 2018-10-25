package com.x.framework.model;

import java.util.List;
import java.util.Map;

import java.util.HashMap;

public class MappingModel<T extends BaseModel> {
	private String fieldName;
	private Class<T> fieldType;
	private String tableName;
	private Map<String, String> columnFieldMap;
	private Map<String, String> tableModelMap;
	private List<MappingField> mappingFieldList;
	private List<MappingModel> mappingModelList;
	private Map<String, List<MappingModel>> mappingModelListMap = new HashMap<String, List<MappingModel>>();

	public MappingModel() {
		
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<T> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<T> fieldType) {
		this.fieldType = fieldType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<MappingField> getMappingFieldList() {
		return mappingFieldList;
	}

	public void setMappingFieldList(List<MappingField> mappingFieldList) {
		this.mappingFieldList = mappingFieldList;
	}

	public List<MappingModel> getMappingModelList() {
		return mappingModelList;
	}

	public void setMappingModelList(List<MappingModel> mappingModelList) {
		this.mappingModelList = mappingModelList;
	}

	public Map<String, String> getColumnFieldMap() {
		return columnFieldMap;
	}

	public void setColumnFieldMap(Map<String, String> columnFieldMap) {
		this.columnFieldMap = columnFieldMap;
	}

	public Map<String, String> getTableModelMap() {
		return tableModelMap;
	}

	public void setTableModelMap(Map<String, String> tableModelMap) {
		this.tableModelMap = tableModelMap;
	}

	public Map<String, List<MappingModel>> getMappingModelListMap() {
		return mappingModelListMap;
	}

	public void setMappingModelListMap(Map<String, List<MappingModel>> mappingModelListMap) {
		this.mappingModelListMap = mappingModelListMap;
	}

}
