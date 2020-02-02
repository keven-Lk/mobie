package segment;

import java.util.ArrayList;

public class AppMain {
	//�ֵ�
	static ArrayList<String> dic = new ArrayList<String>();
	public static void main(String[] args) {
		//���ֵ���ӹؼ���
		dic.add("java");
		dic.add("���");
		dic.add("����");
		dic.add("�����찲��");

		String String = "java�Ǳ������";
		ArrayList<String> result = segment(String);
		System.out.println(result);
	}

	private static ArrayList<String> segment(String string) {
		//�ŷִʽ��
		ArrayList<String> result = new ArrayList<>();
		//java�Ǳ������
		while(string.length()>0) {
			//��ȡ5����ѡ��
			int len = 5;
			if(len>string.length()) {
				len = string.length();
			}
			String tryWord= string.substring(0,len);
			//�жϺ�ѡ���ڲ����ֵ���
			while(!dic.contains(tryWord)) {
				//�жϳ����Ƿ�Ϊ1
				if(tryWord.length()==1) {
					break;
				}
				//���ұ�ȥ��һ���ַ�
				tryWord = tryWord.substring(0,tryWord.length()-1);
			}
			result.add(tryWord);
			//java�Ǳ������
			string = string.substring(tryWord.length());
		}
		return result;
	}
}
