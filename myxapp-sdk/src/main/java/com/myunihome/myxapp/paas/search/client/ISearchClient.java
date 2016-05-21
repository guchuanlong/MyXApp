package com.myunihome.myxapp.paas.search.client;

import java.util.List;
import java.util.Map;

import org.elasticsearch.common.Nullable;

import com.myunihome.myxapp.paas.search.vo.Results;
import com.myunihome.myxapp.paas.search.vo.SearchOption;
import com.myunihome.myxapp.paas.search.vo.SearchfieldVo;

public abstract interface ISearchClient
{
  public abstract boolean deleteData(List<SearchfieldVo> fieldList);

  public abstract boolean cleanData();

  public abstract boolean insertData(String jsonData);

  public abstract boolean bulkInsertData(List<String> datalist);

  public abstract boolean updateData(List<SearchfieldVo> delFiledList, List<String> datalist);

  public abstract Results<Map<String, Object>> searchIndex(List<SearchfieldVo> fieldList, int from, int offset, SearchOption.SearchLogic logic, @Nullable String sortField, @Nullable String sortType);

  public abstract List<String> getSuggest(String fieldName, String value, int count);
}