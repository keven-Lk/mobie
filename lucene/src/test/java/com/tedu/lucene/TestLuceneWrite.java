package com.tedu.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class TestLuceneWrite {

	String[] a = {
			"3, 华为 - 华为电脑, 爆款",
			"4, 华为手机, 旗舰",
			"5, 联想 - Thinkpad, 商务本",
			"6, 联想手机, 自拍神器"
	};
	@Test
	public void write() throws Exception{
		//保存数据,用lucene检索框架
		//自动进行中文分词,倒排索引
		//1.指定文件夹
		File path = new File("./index");
		Directory directory = FSDirectory.open(path.toPath());
		//2.指定中文分词器
		Analyzer analyzer = new SmartChineseAnalyzer();
		//3.指定配置
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		//4.创建写对象
		IndexWriter indexWriter = new IndexWriter(directory, config);
		//5.写的数据叫document
		//document[title叫field,price,sellPint]
		//6.创建field
		for (int i = 0; i < a.length; i++) {
			String[] strs = a[i].split(",");

			Document document = new Document();
			//7.把field放到document
			document.add(new LongPoint("id", Long.parseLong(strs[0])));
			document.add(new StoredField("id", Long.parseLong(strs[0])));
			document.add(new TextField("title", strs[1], Store.YES));
			document.add(new TextField("sellPoint", strs[2], Store.YES));
			//8.保存document
			indexWriter.addDocument(document);
			//9.关闭资源
		}
		indexWriter.close();
	}

}