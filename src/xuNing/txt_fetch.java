package xuNing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class txt_fetch {

    /**
     * ��ȡtxt�ļ������ݣ��������е�arraylist,����array���ɳ�ʼ������Ҫ���ô�С��������arraylist
     * @param file ��Ҫ��ȡ���ļ�����
     * @return �������йؼ��ֵĶ�������
     */
    public static ArrayList<Integer> getFileArray(File file){
        StringBuilder result = new StringBuilder();
        ArrayList<Integer> list = new ArrayList<Integer>();
        int status=0;
        int status2=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;       
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ             
                status=status+1;
            	//�ж��Ƿ����| ntp-monlist: 
            	if(s.equals("| ntp-monlist: ")) {
            		status2=status2+1;
            		//���йؼ��ֺ�������4��
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
     * ��ȡtxt�ļ������ݣ����������±꣬��������ѡ�еĽ��
     * @param file ��Ҫ��ȡ���ļ�����
     * @param List ��Ҫ��ȡ������
     * @return �����ļ�����
     */
    public static ArrayList<String> getCodeChosseList(File file,ArrayList<Integer> List){
        StringBuilder result = new StringBuilder();
        ArrayList<String> resultList = new ArrayList<String>();
        int status=0;
        int status2=0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;          
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��              
                status=status+1;
            	//�ж��Ƿ����| ntp-monlist:                           
            	if(status==List.get(status2)) {
            		status2=status2+1;
            		//�ӵ�21�п�ʼ����ȡ�ַ�������ip��ַ
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
     * ���ļ�д�뵽txt�У�����һ��result2.txt�ļ�
     * @param file ��Ҫ��ȡ���ļ�����
     * @param content ��Ҫд�������
     * @return ����һ���ļ�
     */
    public static void writeMsgTxt(File file,String content) throws IOException{      	        
        //����ļ�������,�Ͷ�̬�����ļ�  
        if(!file.exists()){  
        	file.createNewFile();  
        }  
        FileWriter fw=null;  
        try {  
            //����Ϊ:True,��ʾд���ʱ��׷������  
            fw=new FileWriter(file, true);  
            //�س�������  
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
   	
    	//��ȡ��ǰ�ļ����µ�result.txt�ļ�
        File file = new File("result.txt");    
       //�ڵ�ǰĿ¼������һ��result2.txt�ļ�
        File file2 = new File("result2.txt");    
        ArrayList<Integer> array=getFileArray(file);
        //��ȡ���������С
        int size=getFileArray(file).size();
        System.out.println("��������"+size+"��IP���������£�");   
        //��ȡip��������
        ArrayList<String> result=getCodeChosseList(file,array);
        for(int i=0;i<size;i++) {
        	System.out.println(result.get(i));
        	//����ѯ����IPд�뵽result2.txt�ļ���
        	writeMsgTxt(file2,result.get(i));
        }
    }
}
