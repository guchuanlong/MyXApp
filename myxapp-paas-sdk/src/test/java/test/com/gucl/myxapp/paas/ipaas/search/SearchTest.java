package test.com.gucl.myxapp.paas.ipaas.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.myunihome.myxapp.paas.search.SearchClientFactory;
import com.myunihome.myxapp.paas.search.client.ISearchClient;
import com.myunihome.myxapp.paas.search.vo.Results;
import com.myunihome.myxapp.paas.search.vo.SearchOption;
import com.myunihome.myxapp.paas.search.vo.SearchOption.SearchLogic;
import com.myunihome.myxapp.paas.search.vo.SearchfieldVo;

public class SearchTest {
	@Test
	public void testIndexName(){
		String indexName="gucl.com$mapp-sso$default";
		System.out.println("indexname="+Math.abs(indexName.hashCode()));
	}
	
	@Test
	public void testAddIndex(){
		for(int i=0;i<10;i++){
			String tenantId="BIS-ST";
			String custId="2000"+i;
			String custName="赵六zhaoliu"+i;
			addIndex(tenantId,custId,custName);
		}
	}
	private void addIndex(String tenantId,String custId,String custName){
		System.out.println("insert index start...");
		ISearchClient client=getSearchClient();
		JSONObject json1=new JSONObject();
		
		json1.put("tenantId", tenantId);
		json1.put("custId", custId);
		json1.put("custName", custName);
		
		System.out.println("dataIndex="+json1.toString());
		client.insertData(json1.toString());
		System.out.println("insert index end...");
	}
	@Test
	public void testSearchIndex(){
		System.out.println("search index start...");
		ISearchClient client=getSearchClient();
		List<SearchfieldVo> fieldList=new ArrayList<SearchfieldVo>();
		SearchfieldVo vo =new SearchfieldVo();
		vo.setFiledName("devName");
		List<String> valueList=new ArrayList<String>();
		valueList.add("赵六");
		vo.setFiledValue(valueList);
		vo.setOption(new SearchOption());
		fieldList.add(vo);
		int from=0,offset=5;
		String sortField="devName",sortType="asc";
		Results<Map<String, Object>> result=
		client.searchIndex(fieldList, from, offset
				, SearchLogic.must, sortField, sortType);
		System.out.println("search index end...");
		Gson gson=new Gson();
		List<Map<String, Object>> listRes=result.getSearchList();
		System.out.println("search result="+gson.toJson(result));
		System.out.println("search listRes="+gson.toJson(listRes));
	}

	private ISearchClient getSearchClient(){
		ISearchClient client=null;
		try {
			client=SearchClientFactory.getSearchClient("default");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return client;
	}
}
