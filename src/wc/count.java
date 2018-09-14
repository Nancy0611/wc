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
	
	static int blankLine=0;//空行
	static int commentLine=0;//注释行
	static int codeLine=0;//代码行
	
	private static Scanner scan;
	private static String line;
	private static BufferedReader br;
	static boolean c=false,w=false,l=false,a=false;
	private static ArrayList<File> file;
	
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
			ArrayList<File> ff = new ArrayList<File>();
			getFromFile_name(File_name);
		
			for(File perfile:file){
				br=new BufferedReader(new FileReader(perfile));
				countData(br);
				System.out.println(perfile.getName());
			}
			status(comArray,comLength);//修改cwl状态
			display(c,w,l,a);//显示
			br.close();
		}
		
	}
	
	public static boolean checkInput(String input){//正则表达式校验输入命令行
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
				if(fis.isFile()){//文件
					file.add(fis);
				}else if(fis.isDirectory()){//目录 
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
	
	public static void status(String[] comArray,int comLength){//修改cwla状态
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
	
	public static void countData(BufferedReader br){//计算部分
		boolean comment=false;
		try {
			while((line=br.readLine())!=null){
				countChar+=line.length();//字符
				countWord+=line.split(" ").length;//词
				countLine++;//行数
				line=line.trim();
				if(line.matches("[\\s&&[^\\n]]*$")){//空行
					blankLine++;
				}else if(line.equals("{")||line.equals("}")){
					blankLine++;
				}else if(line.startsWith("/*")&&!line.endsWith("*/")){//注释行
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
					codeLine++;//代码行
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void display( boolean c,boolean w,boolean l,boolean a){
		//选择显示部分
		if(c){
			System.out.println("字符数："+countChar);
		}
		if(w){
			System.out.println("词的数目："+countWord);
		}
		if(l){
			System.out.println("行数："+countLine);
		}
		if(a){
			System.out.println("代码行："+codeLine+"\n空行:"+blankLine+"\n注释行:"+commentLine);
		}
	}
}
