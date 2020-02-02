package com.tedu.lucene;

import java.io.File;


import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class TestSearch {
	@Test
	public void search() throws Exception{
		//1.指定文件夹
		File path = new File("./index");
		Directory directory = FSDirectory.open(path.toPath());
		//2.创建读对象
		DirectoryReader indexReader = DirectoryReader.open(directory);
		//3.创建检索对象
		//装饰者设计模式
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//4.指定关键字
		TermQuery query = new TermQuery(new Term("title","华为"));
		//5.执行查询
		//只返回20行,相当于limit
		//document,lucene框架根据score返回顶部的数据
		TopDocs topDocs = indexSearcher.search(query, 20);
		//6.遍历查询结果
		//lucene自带算法saunascore的值
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			//doc 0,1
			int index = scoreDoc.doc;
			Document document = indexSearcher.doc(scoreDoc.doc);
			System.out.println(document.get("id"));
			System.out.println(document.get("title"));
			System.out.println(document.get("sellPoint"));
		}
		//7.关闭资源
		indexReader.close();
	}
}
