package com.pd.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pd.pojo.Item;
import com.pd.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	//SolrAutoConfiguration
	@Autowired
	private SolrClient solrClient;

	@Override
	public List<Item> findItemByKey(String key) throws Exception {
		//1.封装查询的关键词
		SolrQuery query = new SolrQuery(key);
		//2.查询前多少条数据
		query.setStart(0);
		query.setRows(20);
		//3.执行查询并得到结果
		QueryResponse qr = solrClient.query(query);
		List<Item> beans = qr.getBeans(Item.class);
		return beans;
	}

}