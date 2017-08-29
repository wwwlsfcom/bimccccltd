package com.hdsx.dlxc.login.action;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.stereotype.Controller;

import com.hdsx.dlxc.base.BaseActionSupport;
import com.hdsx.dlxc.util.JsonUtils;
import com.hdsx.dlxc.util.ResponseUtils;










@Controller
public class LoginAction extends BaseActionSupport<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//获取本地xml文件，比较里面的死亡时间。如果系统时间大于死亡时间，则重新获取accesstoken并更新xml文件。
	public void getAccessToken() throws FileNotFoundException, JDOMException, IOException, Exception{
	    //获取时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//当前时间毫秒数
		long timeNow= System.currentTimeMillis();
		//获取XML文件地址
		String uploadUrl = ServletActionContext.getServletContext().getRealPath("/acess_token/acess_token.xml");
		System.out.println("uploadUrl:"+uploadUrl);
		//建立构造器 
		SAXBuilder sb = new SAXBuilder();
		
		File file = new File(uploadUrl);
		if(file.exists()){
			System.out.println("指定文件存在");
			//读入指定文件 
		    Document doc = sb.build(new FileInputStream(uploadUrl));
		    System.out.println("doc："+doc);
		    //获得根节点
		    Element root = doc.getRootElement();
		    System.out.println("root："+root);
		    //将根节点下的所有子节点放入List中 
		    List list = root.getChildren();
		    //取得节点实例
		    Element item0 = (Element) list.get(0);
		    System.out.println("item0："+item0);
		    Element item1 = (Element) list.get(1);
		    System.out.println("item1："+item1);
		    //取得属性值
		    String access_token_xml = item0.getValue();
		    System.out.println("access_token_xml:"+access_token_xml);
		    String deadtime_xml = item1.getValue();
		    
		    //获取时间
		    //xml里的时间
		    Date date=sdf.parse(deadtime_xml); 
		    //现在的时间
		    Date time = sdf.parse(sdf.format(new Date(timeNow)));
		    System.out.println("deadtime_xml:"+date);
		    System.out.println("timeNow:"+time);
		    
		    if (time.after(date)) {
		    	creatAccessToken();
			} else {
				ResponseUtils.write(getResponse(), access_token_xml);
			}
		    
		}else{
			System.out.println("指定文件不存在");
			creatAccessToken();
		}
	}
	
	//与微信端交互创建AccessToken
	private void creatAccessToken(){
		System.out.println("创建AccessToken，并更新XML文件！");
		HttpClient httpclient = new DefaultHttpClient();
		String APPID = "wx09790c5d9d7b5edf";
		String SECRET= "0d48085faf6837690099e6c76d4d8427";
		String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;
		HttpPost httppost = new HttpPost(url);
		JSONObject json = new JSONObject();
		try {
			httppost.addHeader("Content-type", "application/x-www-form-urlencoded");  
			//httppost.setEntity(new UrlEncodedFormEntity(传入参数,"UTF-8"));
            System.out.println("url:"+url);
			HttpResponse response = httpclient.execute(httppost);  
            String conResult  = EntityUtils.toString(response.getEntity());
            System.out.println("conResult:"+conResult);
            JSONObject sobj = new JSONObject();
            sobj = sobj.fromObject(conResult);  
            json = sobj.fromObject(conResult);
            String access_token = sobj.getString("access_token");
            System.out.println("access_token:"+access_token);
            creatAccessTokenXml(access_token);
            ResponseUtils.write(getResponse(), access_token);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("获取accesstoken失败。");
			String errcode = json.getString("errcode");
			String errmsg = json.getString("errmsg");
			System.out.println("errcode:"+errcode);
			System.out.println("errmsg:"+errmsg);
			ResponseUtils.write(getResponse(), "error");
		}		
		
	}

	//生成AcessTokenXml文件
	private void creatAccessTokenXml(String access_token){
		//获取时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//当前时间毫秒数
		long timeNow= System.currentTimeMillis();
		//当前时间毫秒数+1个小时
		long timeLose=System.currentTimeMillis()+1*60*60*1000;
		System.out.println(timeNow+"+1hours="+timeLose); 
		String dateNow=sdf.format(new Date(timeNow));
		//死亡过期时间
		String deadtime=sdf.format(new Date(timeLose));
		System.out.println(dateNow+"+1hours="+deadtime);
		
		//创建根节点...  
		Element root = new Element("accesstoken");
		//将根节点添加到文档中...  
        Document Doc  = new Document(root);
        //添加value
        Element elementsValue = new Element("value");
        elementsValue.addContent(access_token);
        //添加死亡时间
        Element elementsDeadTime = new Element("deadtime");
        elementsDeadTime.addContent(deadtime);
        //加入root
        root.addContent(elementsValue); 
        root.addContent(elementsDeadTime); 
        //FormatXML
        XMLOutputter XMLOut = new XMLOutputter(FormatXML());
        
		try {
			//写入XML文件至服务器
			String uploadUrl = ServletActionContext.getServletContext().getRealPath("/acess_token/acess_token.xml");
			XMLOut.output(Doc, new FileOutputStream(uploadUrl));
		}catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } 
	}
	
	//格式化XML
    public Format FormatXML(){  
        //格式化生成的xml文件，如果不进行格式化的话，生成的xml文件将会是很长的一行...  
        Format format = Format.getCompactFormat();  
        format.setEncoding("utf-8");  
        format.setIndent(" ");  
        return format;  
    }
	
	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
