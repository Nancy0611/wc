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
	
	static int countChar =0;//字符数
	static int countWord=0;//词的数目
	static int countLine=0;//行数
	private static Scanner scan;
	private static String line;
	private static BufferedReader br;
	static boolean c=false,w=false,l=false;
	
	public static void main(String[] args) throws IOException {
		
		scan = new Scanner(System.in);
		String commend=scan.nextLine();
		boolean result=checkInput(commend);
		//输入格式为：wc [parameter] [file_name]
		
		if(!result){
			System.out.println("输入指令不符格式");
		}else{
			String[] comArray=commend.split(" ");
			int comLength=comArray.length;
			String File_name=comArray[comLength-1];//获取文件名
			//System.out.println(File_name);
			ArrayList<File> ff = getFromFile_name(File_name);
			br=new BufferedReader(new FileReader(ff.get(0)));
			status(comArray,comLength);//修改cwl状态
			display(br,c,w,l);//显示
			br.close();
		}
		
	}
	
	public static boolean checkInput(String input){//正则表达式校验输入命令行
		boolean flag=false;
		try{
			String pattern="^wc\\s+(\\-[cwl]\\s+){1,3}\\s*\\S+$";
			Pattern  regex=Pattern.compile(pattern);
			Matcher matcher=regex.matcher(input);
			flag=matcher.matches();
		}catch(Exception e){
			flag=false;
		}
		return flag;
	}
	
	public static ArrayList<File> getFromFile_name(String path){//文件名
		File f=new File(path);
		ArrayList<File> file=new ArrayList<File>();
		if(f.isFile()&&f.exists()){
			file.add(f);
		}else if(f.isDirectory()){//目录名
			file=getFromDirection(path);
		}
		return file;
	}
	
	public static ArrayList<File> getFromDirection(String path){//目录名
		File f=new File(path);
		ArrayList<File> file=new ArrayList<File>();
		if(f.isDirectory()){
			
		}
		return file;
	}
	
	public static void status(String[] comArray,int comLength){//修改cwl状态
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
			default:
				break;
			}
		}
	}
	
	public static void display(BufferedReader br,boolean c,boolean w,boolean l){
		try {
			while((line=br.readLine())!=null){
				countChar+=line.length();
				countWord+=line.split(" ").length;
				countLine++;
				
			}
			if(c){
				System.out.println("字符数："+countChar);
			}
			if(w){
				System.out.println("词的数目："+countWord);
			}
			if(l){
				System.out.println("行数："+countLine);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
