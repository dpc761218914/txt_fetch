package xuNing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class txt_fetch {

    /**
     * 读取txt文件的内容，返回命中的arraylist,由于array不可初始化必须要设置大小。所以用arraylist
     * @param file 想要读取的文件对象
     * @return 返回命中关键字的对象数组
     */
    public static ArrayList<Integer> getFileArray(File file){
        StringBuilder result = new StringBuilder();
        ArrayList<Integer> list = new ArrayList<Integer>();
        int status=0;
        int status2=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;       
            while((s = br.readLine())!=null){//使用readLine方法，一次读一             
                status=status+1;
            	//判断是否包含| ntp-monlist: 
            	if(s.equals("| ntp-monlist: ")) {
            		status2=status2+1;
            		//命中关键字后，向上找4行
            		list.add(status-4);
            	}               
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }    
    /**
     * 读取txt文件的内容，根据数组下标，返回命中选中的结果
     * @param file 想要读取的文件对象
     * @param List 需要提取的行数
     * @return 返回文件内容
     */
    public static ArrayList<String> getCodeChosseList(File file,ArrayList<Integer> List){
        StringBuilder result = new StringBuilder();
        ArrayList<String> resultList = new ArrayList<String>();
        int status=0;
        int status2=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;          
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行              
                status=status+1;
            	//判断是否包含| ntp-monlist:                           
            	if(status==List.get(status2)) {
            		status2=status2+1;
            		//从第21行开始，截取字符串，即ip地址
            		resultList.add(s.substring(21));
            	}                
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultList;
    }   
    
    
    /**
     * 将文件写入到txt中，生成一个result2.txt文件
     * @param file 想要读取的文件对象
     * @param content 需要写入的内容
     * @return 生成一个文件
     */
    public static void writeMsgTxt(File file,String content) throws IOException{      	        
        //如果文件不存在,就动态创建文件  
        if(!file.exists()){  
        	file.createNewFile();  
        }  
        FileWriter fw=null;  
        try {  
            //设置为:True,表示写入的时候追加数据  
            fw=new FileWriter(file, true);  
            //回车并换行  
            fw.write(content+"\r\n");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if(fw!=null){  
                fw.close();  
            }  
        }  
  
    }  
    
    public static void main(String[] args) throws IOException{   
   	
    	//读取当前文件夹下的result.txt文件
        File file = new File("result.txt");    
       //在当前目录下生成一个result2.txt文件
        File file2 = new File("result2.txt");    
        ArrayList<Integer> array=getFileArray(file);
        //获取对象数组大小
        int size=getFileArray(file).size();
        System.out.println("共命中了"+size+"个IP，具体如下：");   
        //获取ip对象数组
        ArrayList<String> result=getCodeChosseList(file,array);
        for(int i=0;i<size;i++) {
        	System.out.println(result.get(i));
        	//将查询到的IP写入到result2.txt文件中
        	writeMsgTxt(file2,result.get(i));
        }
    }
}
