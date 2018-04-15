package com.x.framework.model;

import java.lang.reflect.*;

import com.x.framework.annotation.MappingColumn;

public class MappingField {
	private Field field;
	private String fieldName;
	private Class<?> fieldType;
	private String fieldClassTypeName;
	private String columnName;
	private int columnLength;
	private boolean columnPk;
	private String columnPkType;
	private String columnPkSeq;
	private String columnDefaultValue;
	private boolean columnOnlySelect;
	private String tableName;
	private String joinColumnName;
	private MappingColumn mappingColumn;

	public MappingField() {
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnPkType() {
		return columnPkType;
	}

	public void setColumnPkType(String columnPkType) {
		this.columnPkType = columnPkType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnDefaultValue() {
		return columnDefaultValue;
	}

	public void setColumnDefaultValue(String columnDefaultValue) {
		this.columnDefaultValue = columnDefaultValue;
	}

	public String getJoinColumnName() {
		return joinColumnName;
	}

	public void setJoinColumnName(String joinColumnName) {
		this.joinColumnName = joinColumnName;
	}

	public String getFieldClassTypeName() {
		return fieldClassTypeName;
	}

	public void setFieldClassTypeName(String fieldClassTypeName) {
		this.fieldClassTypeName = fieldClassTypeName;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public boolean getColumnOnlySelect() {
		return columnOnlySelect;
	}

	public void setColumnOnlySelect(boolean columnOnlySelect) {
		this.columnOnlySelect = columnOnlySelect;
	}

	public boolean getColumnPk() {
		return columnPk;
	}

	public void setColumnPk(boolean columnPk) {
		this.columnPk = columnPk;
	}

	public String getColumnPkSeq() {
		return columnPkSeq;
	}

	public void setColumnPkSeq(String columnPkSeq) {
		this.columnPkSeq = columnPkSeq;
	}

	public int getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	public MappingColumn getMappingColumn() {
		return mappingColumn;
	}

	public void setMappingColumn(MappingColumn mappingColumn) {
		this.mappingColumn = mappingColumn;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

}
