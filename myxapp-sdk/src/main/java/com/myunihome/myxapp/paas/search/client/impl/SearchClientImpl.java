package com.myunihome.myxapp.paas.search.client.impl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.myunihome.myxapp.paas.search.client.ISearchClient;
import com.myunihome.myxapp.paas.search.constants.SearchClientException;
import com.myunihome.myxapp.paas.search.vo.InsertFieldVo;
import com.myunihome.myxapp.paas.search.vo.Results;
import com.myunihome.myxapp.paas.search.vo.SearchOption;
import com.myunihome.myxapp.paas.search.vo.SearchfieldVo;
import com.myunihome.myxapp.paas.util.StringUtil;

public class SearchClientImpl implements ISearchClient {
	private Logger logger = LoggerFactory.getLogger(SearchClientImpl.class);

	private String highlightCSS = "span,span";
	private String indexName;
	private TransportClient searchClient;
	static Settings settings = ImmutableSettings.settingsBuilder().put("client.transport.ping_timeout", "10s")
			.put("client.transport.sniff", "true").put("client.transport.ignore_cluster_name", "true").build();

	public SearchClientImpl(String hosts, String indexName) {
		this.indexName = indexName;
		List<String> clusterList = new ArrayList<String>();
		try {
			Class clazz = Class.forName(TransportClient.class.getName());
			Constructor constructor = clazz.getDeclaredConstructor(Settings.class);

			constructor.setAccessible(true);
			this.searchClient = ((TransportClient) constructor.newInstance(new Object[] { settings }));
			if (!StringUtil.isBlank(hosts)) {
				clusterList = Arrays.asList(hosts.split(","));
			}
			for (String item : clusterList) {
				String address = item.split(":")[0];
				int port = Integer.parseInt(item.split(":")[1]);

				this.searchClient.addTransportAddress(new InetSocketTransportAddress(address, port));
			}
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES init client error", e);
		}
	}

	public synchronized TransportClient getTransportClient() {
		return this.searchClient;
	}

	public void setHighlightCSS(String highlightCSS) {
		this.highlightCSS = highlightCSS;
	}

	private boolean _bulkInsertData(String indexName, XContentBuilder xContentBuilder) {
		try {
			BulkRequestBuilder bulkRequest = this.searchClient.prepareBulk();
			bulkRequest.add(this.searchClient.prepareIndex(indexName, indexName).setSource(xContentBuilder));
			BulkResponse bulkResponse = (BulkResponse) bulkRequest.execute().actionGet();
			if (!bulkResponse.hasFailures()) {
				return true;
			}

			this.logger.error("FailureMessage", bulkResponse.buildFailureMessage());
			throw new SearchClientException("ES insert error", bulkResponse.buildFailureMessage());
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES insert error", e);
		}
	}

	public boolean deleteData(List<SearchfieldVo> fieldList) {
		try {
			QueryBuilder queryBuilder = null;
			queryBuilder = createQueryBuilder(fieldList, SearchOption.SearchLogic.must);
			this.logger.warn("[" + this.indexName + "]" + queryBuilder.toString());
			this.searchClient.prepareDeleteByQuery(new String[] { this.indexName }).setQuery(queryBuilder).execute()
					.actionGet();
			return true;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES delete error", e);
		}
	}

	public boolean cleanData() {
		try {
			QueryBuilder queryBuilder = QueryBuilders.boolQuery();
			this.logger.warn("[" + this.indexName + "]" + queryBuilder.toString());
			this.searchClient.prepareDeleteByQuery(new String[] { this.indexName }).setQuery(queryBuilder).execute()
					.actionGet();
			return true;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES delete error", e);
		}
	}

	public boolean insertData(String jsonData) {
		if ((jsonData == null) || ("".equals(jsonData))) {
			throw new SearchClientException("插入数据参数为空");
		}
		XContentBuilder xContentBuilder = null;
		try {
			xContentBuilder = XContentFactory.jsonBuilder().startObject();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
			throw new SearchClientException("插入数据异常", e);
		}

		Map dataMap = new HashMap();
		Gson gson = new Gson();
		dataMap = (Map) gson.fromJson(jsonData, dataMap.getClass());
		Iterator iterator = dataMap.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String field = (String) entry.getKey();
			Object values = entry.getValue();

			try {
				xContentBuilder = xContentBuilder.field(field, values);
			} catch (IOException e) {
				this.logger.error(e.getMessage(), e);
				throw new SearchClientException("插入数据异常", e);
			}
		}
		try {
			xContentBuilder = xContentBuilder.endObject();
		} catch (IOException e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("插入数据异常", e);
		}
		try {
			xContentBuilder.string();
		} catch (IOException e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("插入数据异常", e);
		}
		return _bulkInsertData(this.indexName, xContentBuilder);
	}

	public boolean bulkInsertData(List<String> datalist) {
		BulkRequestBuilder bulkRequest = this.searchClient.prepareBulk();
		for (String data : datalist) {
			XContentBuilder xContentBuilder = null;
			try {
				xContentBuilder = XContentFactory.jsonBuilder().startObject();
			} catch (IOException e) {
				this.logger.error(e.getMessage(), e);
				throw new SearchClientException("插入数据异常", e);
			}

			Map dataMap = new HashMap();
			Gson gson = new Gson();
			dataMap = (Map) gson.fromJson(data, dataMap.getClass());
			Iterator iterator = dataMap.entrySet().iterator();

			while (iterator.hasNext()) {
				Map.Entry entry = (Map.Entry) iterator.next();
				String field = (String) entry.getKey();
				Object values = entry.getValue();

				try {
					xContentBuilder = xContentBuilder.field(field, values);
				} catch (IOException e) {
					this.logger.error(e.getMessage(), e);
					throw new SearchClientException("插入数据异常", e);
				}
			}
			try {
				xContentBuilder = xContentBuilder.endObject();
			} catch (IOException e) {
				this.logger.error(e.getMessage(), e);
				throw new SearchClientException("插入数据异常", e);
			}

			bulkRequest.add(this.searchClient.prepareIndex(this.indexName, this.indexName).setSource(xContentBuilder));
		}
		try {
			BulkResponse bulkResponse = (BulkResponse) bulkRequest.execute().actionGet();
			if (!bulkResponse.hasFailures()) {
				return true;
			}

			this.logger.error("insert error", bulkResponse.buildFailureMessage());
			throw new SearchClientException("insert error", "insert error" + bulkResponse.buildFailureMessage());
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES insert error", e);
		}
	}

	public boolean insertData(List<InsertFieldVo> fieldList) {
		XContentBuilder xContentBuilder = null;
		try {
			xContentBuilder = XContentFactory.jsonBuilder().startObject();
		} catch (IOException e) {
			this.logger.error(e.getMessage());
			return false;
		}
		for (InsertFieldVo vo : fieldList) {
			String field = vo.getFiledName();
			Object value = vo.getFiledValue();
			if (vo.getFileType().equals(InsertFieldVo.FiledType.completion)) {
				if (!(value instanceof HashMap)) {
					this.logger.error("param error", "param error");
					throw new SearchClientException("param error", "智能推荐参数不正确");
				}
			} else if (((value instanceof String)) && (SearchOption.isDate(value))) {
				value = SearchOption.formatDate(value);
			}
			try {
				xContentBuilder = xContentBuilder.field(field, value);
			} catch (IOException e) {
				this.logger.error(e.getMessage(), e);

				return false;
			}
		}
		try {
			xContentBuilder = xContentBuilder.endObject();
		} catch (IOException e) {
			this.logger.error(e.getMessage(), e);
			return false;
		}

		return _bulkInsertData(this.indexName, xContentBuilder);
	}

	public boolean updateData(List<SearchfieldVo> delFiledList, List<String> datalist) {
		if (deleteData(delFiledList)) {
			return bulkInsertData(datalist);
		}
		return false;
	}

	private RangeQueryBuilder createRangeQueryBuilder(String field, List<String> valuesSet) {
		String[] array = new String[2];
		String[] values = (String[]) (String[]) valuesSet.toArray(array);
		if ((values.length == 1) || (values[1] == null) || (values[1].toString().trim().isEmpty())) {
			this.logger.warn("error", "[区间搜索]必须传递两个值，但是只传递了一个值，所以返回null");
			return null;
		}
		boolean timeType = false;
		if ((SearchOption.isDate(values[0])) && (SearchOption.isDate(values[1]))) {
			timeType = true;
		}

		String begin = "";
		String end = "";
		if (timeType) {
			begin = SearchOption.formatDate(values[0]);
			end = SearchOption.formatDate(values[1]);
		} else {
			begin = values[0].toString();
			end = values[1].toString();
		}
		return QueryBuilders.rangeQuery(field).from(begin).to(end);
	}

	private QueryBuilder createSingleFieldQueryBuilder(String field, List<String> values, SearchOption mySearchOption) {
		try {
			if (mySearchOption.getSearchType() == SearchOption.SearchType.range) {
				return createRangeQueryBuilder(field, values);
			}
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			if (values != null) {
				Iterator iterator = values.iterator();
				while (iterator.hasNext()) {
					QueryBuilder queryBuilder = null;
					String formatValue = ((String) iterator.next()).toString().trim().replace("*", "");
					if (mySearchOption.getSearchType() == SearchOption.SearchType.term) {
						queryBuilder = QueryBuilders.termQuery(field, formatValue).boost(mySearchOption.getBoost());
					} else if (mySearchOption.getSearchType() == SearchOption.SearchType.querystring) {
						if (formatValue.length() == 1) {
							if (!Pattern.matches("[0-9]", formatValue)) {
								formatValue = "*" + formatValue + "*";
							}
						}

						QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryString(formatValue)
								.minimumShouldMatch(mySearchOption.getQueryStringPrecision());
						queryBuilder = queryStringQueryBuilder.field(field).boost(mySearchOption.getBoost());
					}
					if (mySearchOption.getSearchLogic() == SearchOption.SearchLogic.should) {
						boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
					} else {
						boolQueryBuilder = boolQueryBuilder.must(queryBuilder);
					}
				}
			}
			return boolQueryBuilder;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES create builder error", e);
		}
	}

	private QueryBuilder createQueryBuilder(List<SearchfieldVo> fieldList, SearchOption.SearchLogic searchLogic) {
		try {
			if ((fieldList == null) || (fieldList.size() == 0)) {
				return null;
			}
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			for (SearchfieldVo fieldVo : fieldList) {
				String field = fieldVo.getFiledName();
				SearchOption mySearchOption = fieldVo.getOption();
				QueryBuilder queryBuilder = createSingleFieldQueryBuilder(field, fieldVo.getFiledValue(),
						mySearchOption);
				if (queryBuilder != null) {
					if (searchLogic == SearchOption.SearchLogic.should) {
						boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
					} else {
						boolQueryBuilder = boolQueryBuilder.must(queryBuilder);
					}
				}
			}

			return boolQueryBuilder;
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES create builder error", e);
		}
	}

	private SearchResponse searchCountRequest(String indexNames, Object queryBuilder) {
		try {
			SearchRequestBuilder searchRequestBuilder = this.searchClient.prepareSearch(new String[] { indexNames })
					.setSearchType(SearchType.COUNT);
			if ((queryBuilder instanceof QueryBuilder)) {
				searchRequestBuilder = searchRequestBuilder.setQuery((QueryBuilder) queryBuilder);
			}
			if ((queryBuilder instanceof byte[])) {
				String query = new String((byte[]) (byte[]) queryBuilder);
				searchRequestBuilder = searchRequestBuilder.setQuery(QueryBuilders.wrapperQuery(query));
			}
			return (SearchResponse) searchRequestBuilder.execute().actionGet();
		} catch (Exception e) {
			this.logger.error("search count error", e.getMessage(), e);
			throw new SearchClientException("ES search count error", e);
		}
	}

	private List<Map<String, Object>> getSearchResult(SearchResponse searchResponse) {
		try {
			List resultList = new ArrayList();
			for (SearchHit searchHit : searchResponse.getHits()) {
				Iterator iterator = searchHit.getSource().entrySet().iterator();
				HashMap resultMap = new HashMap();
				while (iterator.hasNext()) {
					Map.Entry entry = (Map.Entry) iterator.next();
					resultMap.put(entry.getKey(), entry.getValue());
				}
				Map highlightMap = searchHit.highlightFields();
				Iterator highlightIterator = highlightMap.entrySet().iterator();
				while (highlightIterator.hasNext()) {
					Map.Entry entry = (Map.Entry) highlightIterator.next();
					Object[] contents = ((HighlightField) entry.getValue()).fragments();
					if (contents.length == 1) {
						resultMap.put(entry.getKey(), contents[0].toString());
					} else {
						this.logger.warn("搜索结果中的高亮结果出现多数据contents.length ", Integer.valueOf(contents.length));
					}
				}
				resultList.add(resultMap);
			}
			return resultList;
		} catch (Exception e) {
			this.logger.error("ES search error", e.getMessage());
			throw new SearchClientException("ES search error", e);
		}
	}

	public List<String> getSuggest(String fieldName, String value, int count) {
		try {
			CompletionSuggestionBuilder suggestionsBuilder = new CompletionSuggestionBuilder("complete");

			suggestionsBuilder.text(value);
			suggestionsBuilder.field(fieldName);
			suggestionsBuilder.size(count);
			SuggestResponse resp = (SuggestResponse) this.searchClient.prepareSuggest(new String[] { this.indexName })
					.addSuggestion(suggestionsBuilder).execute().actionGet();

			List list = resp.getSuggest().getSuggestion("complete").getEntries();

			List returnList = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				List options = ((Suggest.Suggestion.Entry) list.get(i)).getOptions();
				returnList.add(((Suggest.Suggestion.Entry) list.get(i)).getText().toString());
				for (int j = 0; j < options.size(); j++) {
					if ((options.get(j) instanceof CompletionSuggestion.Entry.Option)) {
						CompletionSuggestion.Entry.Option op = (CompletionSuggestion.Entry.Option) options.get(j);
						returnList.add(op.getText().toString());
					}

				}

			}

			return returnList;
		} catch (Exception e) {
			e.printStackTrace();
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES suggest error", e);
		}
	}

	public Results<Map<String, Object>> searchIndex(List<SearchfieldVo> fieldList, int from, int offset,
			SearchOption.SearchLogic logic, @Nullable String sortField, @Nullable String sortType) {
		Results result = new Results();
		result.setResultCode("999999");

		QueryBuilder queryBuilder = createQueryBuilder(fieldList, logic);
		if (queryBuilder == null) {
			return result;
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder = boolQueryBuilder.must(queryBuilder);
		try {
			SearchResponse searchResponse = searchCountRequest(this.indexName, boolQueryBuilder);
			long count = searchResponse.getHits().totalHits();

			SearchRequestBuilder searchRequestBuilder = null;
			searchRequestBuilder = this.searchClient.prepareSearch(new String[] { this.indexName })
					.setSearchType(SearchType.DEFAULT).setQuery(boolQueryBuilder).setFrom(from).setSize(offset)
					.setExplain(true);

			/*if ((sortField != null) && (!sortField.isEmpty()) && (sortType != null) && (!sortType.isEmpty())) {
				SortOrder sortOrder = sortType.equals("desc") ? SortOrder.DESC : SortOrder.ASC;
				searchRequestBuilder = searchRequestBuilder.addSort(sortField, sortOrder);
			}*/

			searchRequestBuilder = createHighlight(searchRequestBuilder, fieldList);
			searchResponse = (SearchResponse) searchRequestBuilder.execute().actionGet();
			List list = getSearchResult(searchResponse);
			result.setSearchList(list);
			result.setCounts(count);
			result.setResultCode("000000");
		} catch (Exception e) {
			this.logger.error(e.getMessage(), e);
			throw new SearchClientException("ES searchIndex error", e);
		}
		return result;
	}

	private SearchRequestBuilder createHighlight(SearchRequestBuilder searchRequestBuilder,
			List<SearchfieldVo> fieldList) {
		for (SearchfieldVo fieldVo : fieldList) {
			String field = fieldVo.getFiledName();
			SearchOption mySearchOption = fieldVo.getOption();
			if (mySearchOption.isHighlight()) {
				searchRequestBuilder = searchRequestBuilder.addHighlightedField(field, 1000)
						.setHighlighterPreTags(new String[] { "<" + this.highlightCSS.split(",")[0] + ">" })
						.setHighlighterPostTags(new String[] { "</" + this.highlightCSS.split(",")[1] + ">" });
			}

		}

		return searchRequestBuilder;
	}
}