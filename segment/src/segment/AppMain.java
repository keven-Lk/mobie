package segment;

import java.util.ArrayList;

public class AppMain {
	//字典
	static ArrayList<String> dic = new ArrayList<String>();
	public static void main(String[] args) {
		//向字典添加关键字
		dic.add("java");
		dic.add("编程");
		dic.add("语言");
		dic.add("北京天安门");

		String String = "java是编程语言";
		ArrayList<String> result = segment(String);
		System.out.println(result);
	}

	private static ArrayList<String> segment(String string) {
		//放分词结果
		ArrayList<String> result = new ArrayList<>();
		//java是编程语言
		while(string.length()>0) {
			//先取5个候选词
			int len = 5;
			if(len>string.length()) {
				len = string.length();
			}
			String tryWord= string.substring(0,len);
			//判断候选词在不在字典中
			while(!dic.contains(tryWord)) {
				//判断长度是否为1
				if(tryWord.length()==1) {
					break;
				}
				//从右边去掉一个字符
				tryWord = tryWord.substring(0,tryWord.length()-1);
			}
			result.add(tryWord);
			//java是编程语言
			string = string.substring(tryWord.length());
		}
		return result;
	}
}
