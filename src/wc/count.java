package wc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class count {
	
	static int countChar =0;//�ַ���
	static int countWord=0;//�ʵ���Ŀ
	static int countLine=0;//����
	
	static int blankLine=0;//����
	static int commentLine=0;//ע����
	static int codeLine=0;//������
	
	private static Scanner scan;
	private static String line;
	private static BufferedReader br;
	static boolean c=false,w=false,l=false,a=false;
	private static ArrayList<File> file;
	
	public static void main(String[] args) throws IOException {
		
		scan = new Scanner(System.in);
		String commend=scan.nextLine();
		boolean result=checkInput(commend);
		//�����ʽΪ��wc [parameter] [file_name]
		
		if(!result){
			System.out.println("����ָ�����ʽ");
		}else{
			String[] comArray=commend.split(" ");
			int comLength=comArray.length;
			String File_name=comArray[comLength-1];//��ȡ�ļ���
			ArrayList<File> ff = new ArrayList<File>();
			getFromFile_name(File_name);
		
			for(File perfile:file){
				br=new BufferedReader(new FileReader(perfile));
				countData(br);
				System.out.println(perfile.getName());
			}
			status(comArray,comLength);//�޸�cwl״̬
			display(c,w,l,a);//��ʾ
			br.close();
		}
		
	}
	
	public static boolean checkInput(String input){//������ʽУ������������
		boolean flag=false;
		try{
			String pattern="^wc\\s+(\\-[cwlas]\\s+){1,5}\\s*\\S+$";
			Pattern  regex=Pattern.compile(pattern);
			Matcher matcher=regex.matcher(input);
			flag=matcher.matches();
		}catch(Exception e){
			flag=false;
		}
		return flag;
	}
	
	public static ArrayList<File> getFromFile_name(String path){
		File f=new File(path);
		file=new ArrayList<File>();
		if(f.isFile()&&f.exists()){
			file.add(f);
		}else if(f.isDirectory()){
			File[] files=f.listFiles();
			for(File fis: files){
				if(fis.isFile()){//�ļ�
					file.add(fis);
				}else if(fis.isDirectory()){//Ŀ¼ 
					//System.out.println(fis.getAbsolutePath());
					getFromFile_name(fis.getAbsolutePath());
				}
			}
		}
//		for(File pp:file){
//			System.out.println(pp.getName());
//		}
		return file;
	}
	
	public static void status(String[] comArray,int comLength){//�޸�cwla״̬
		for(int i=0;i<comLength;i++){
			switch(comArray[i]){
			case "-c": 
				c=true;
				break;
				
			case "-w":
				w=true;
				break;
				
			case "-l":
				l=true;
				break;
				
			case "-a":
				a=true;
				break;
			
			default:
				break;
			}
		}
	}
	
	public static void countData(BufferedReader br){//���㲿��
		boolean comment=false;
		try {
			while((line=br.readLine())!=null){
				countChar+=line.length();//�ַ�
				countWord+=line.split(" ").length;//��
				countLine++;//����
				line=line.trim();
				if(line.matches("[\\s&&[^\\n]]*$")){//����
					blankLine++;
				}else if(line.equals("{")||line.equals("}")){
					blankLine++;
				}else if(line.startsWith("/*")&&!line.endsWith("*/")){//ע����
					commentLine++;
					comment=true;
				}else if(true==comment){
					commentLine++;
					if(line.endsWith("*/")){
						comment=false;
					}
				}else if(line.startsWith("//")){
					commentLine++;
				}else if(line.startsWith("/*")&&line.endsWith("*/")){
					commentLine++;
				}else if(line.startsWith("}//")){
					commentLine++;
				}else{
					codeLine++;//������
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void display( boolean c,boolean w,boolean l,boolean a){
		//ѡ����ʾ����
		if(c){
			System.out.println("�ַ�����"+countChar);
		}
		if(w){
			System.out.println("�ʵ���Ŀ��"+countWord);
		}
		if(l){
			System.out.println("������"+countLine);
		}
		if(a){
			System.out.println("�����У�"+codeLine+"\n����:"+blankLine+"\nע����:"+commentLine);
		}
	}
}
