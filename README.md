###一、项目描述    
  **前言：**同事希望根据特定的规则，在4M多的txt文件中提取ip字段，当然这个ip字段满足一定的规则。如下图所示，当存在“| ntp-monlist: ”字段时，需要向前找4行，然后将ip取出来。如下图，1，不需要截取，2需要截取。
![需要截取的ip.png](http://upload-images.jianshu.io/upload_images/2227968-4003c2e5e001dd22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

 
  **希望实现的功能：**将txt文件和jar放在同一个目录，执行java文件后，将文件中的ip字段提取出来，存入到txt文件中。

**实现思路：** 第一遍读取txt文件先找出所需要寻找的ip的段落位置，并将其存入到ArrayList中，第二遍读取txt文件，根据ArrayList将含有ip的字段读取出来，并将其截取写入到txt文件中。


###二、项目截图
.jar程序执行后，识别result.txt文件（4M多，共9千多行），然后将生成result2.txt文件（2千多个ip），小工具执行的结果如图所示。
![工具执行结果](http://upload-images.jianshu.io/upload_images/2227968-2088be440c1c8631.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)






###四、部分代码
- 4.1、找到关键词“| ntp-monlist: ”所在字段，然后将数据存入到Arraylist中，这里无法存入到数组中，因为数组需要赋值初始化。这里需要注意的是，找到关键字以后需要向上找四行，才是所需要找的ip的位置。
```
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
```  

- 4.2、根据ArrayList的位置，找到对应的段落，将取出的字符串存入到ArrayList

```
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
```
- 4.3、将结果输出到txt中。
```
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
```


###五、源码
该小工具只是一个main文件，只是将txt_fetch运行起来。
另外，如何将main函数打包成jar可执行的文件，百度即可。
- 5.1、项目源代码：https://github.com/dpc761218914/txt_fetch
- 5.2、博客文件：https://www.jianshu.com/p/ce5b7788c4f4
