package com.x.framework.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.x.framework.annotation.MappingColumn;
import com.x.framework.annotation.MappingTable;

public class ModelMap {
	private static Map<Class, MappingModel> mappingModelMap = new HashMap<Class, MappingModel>();

	public ModelMap() {

	}

	public static <T extends BaseModel> MappingModel<T> getMappingModel(Class<T> clazz) {
		MappingModel mappingModel = mappingModelMap.get(clazz);
		if (mappingModel == null) {
			List<MappingModel> mappingModelList = new ArrayList<MappingModel>();
			Map<String, String> columnFieldMap = new HashMap<String, String>();
			Map<String, String> tableModelMap = new HashMap<String, String>();
			mappingModel = getClassMappingModel(clazz, mappingModelList, columnFieldMap, tableModelMap);
			mappingModelMap.put(clazz, mappingModel);
		}
		return mappingModel;
	}

	private static <T extends BaseModel> MappingModel<T> getClassMappingModel(Class<T> clazz, List<MappingModel> mappingModelList, Map<String, String> columnFieldMap, Map<String, String> tableModelMap) {
		MappingTable mappingTable = clazz.getAnnotation(MappingTable.class);
		String tableName = mappingTable.tableName().toUpperCase();
		MappingModel mappingModel = new MappingModel();
		mappingModel.setTableName(tableName);
		mappingModel.setFieldType(clazz);

		List<MappingField> mappingFieldList = new ArrayList<MappingField>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Class type = field.getType();
			if (field.isAnnotationPresent(MappingColumn.class)) {
				MappingColumn mappingColumn = field.getAnnotation(MappingColumn.class);
				MappingField mappingField = new MappingField();
				mappingField.setField(field);
				mappingField.setFieldName(fieldName);
				mappingField.setFieldType(type);
				mappingField.setFieldClassTypeName(field.getType().getName());
				mappingField.setMappingColumn(mappingColumn);
				mappingField.setColumnName(mappingColumn.columnName().toUpperCase());
				mappingField.setColumnPk(mappingColumn.columnPk());
				mappingField.setColumnPkType(mappingColumn.columnPkType().toUpperCase());
				mappingField.setColumnPkSeq(mappingColumn.columnPkSeq().toUpperCase());
				mappingField.setColumnLength(mappingColumn.columnLength());
				mappingField.setColumnDefaultValue(mappingColumn.columnDefaultValue().toUpperCase());
				mappingField.setColumnOnlySelect(mappingColumn.columnReadOnly());

				mappingFieldList.add(mappingField);
				columnFieldMap.put(mappingField.getColumnName(), mappingField.getFieldName());
			} else if (field.isAnnotationPresent(MappingTable.class)) {
				mappingTable = field.getAnnotation(MappingTable.class);
				String key = mappingTable.tableName();
				if (!tableModelMap.containsKey(key)) {
					tableModelMap.put(key, key);
					MappingModel mappingModelField;
					if (BaseModel.class.isAssignableFrom(type)) {
						mappingModelField = getClassMappingModel(type, mappingModelList, columnFieldMap, tableModelMap);
						mappingModelField.setFieldName(fieldName);
						mappingModelList.add(mappingModelField);
//						} else {
//							type = mappingTable.modelClass();
//							mappingModelField = getClassMappingModel(type, mappingModelList, columnFieldMap, tableModelMap);
					}
				}
			}
		}
		mappingModel.setMappingFieldList(mappingFieldList);
		mappingModel.setMappingModelList(mappingModelList);
		mappingModel.setColumnFieldMap(columnFieldMap);
		tableModelMap.put(tableName, tableName);
		mappingModel.setTableModelMap(tableModelMap);
		return mappingModel;
	}
}
