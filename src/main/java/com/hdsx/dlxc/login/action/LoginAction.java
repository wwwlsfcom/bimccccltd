package com.hdsx.dlxc.login.action;



import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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

	public void getAccessToken(){
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
            JsonUtils.write(access_token, getResponse().getWriter());
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

	@Override
	protected void prepareModel() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
