package com.example.testimageclient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class selectLocationParse {
	private static String url="http://10.169.162.122:8080/cbb+book/selectLocationServlet";
	
	public static List<location> doPost(String uname) throws ClientProtocolException, IOException, JSONException
	{
		HttpClient hc=new DefaultHttpClient();
		HttpPost hp=new HttpPost(url);
		NameValuePair param1=new BasicNameValuePair("uname",uname);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		params.add(param1);
		System.out.println(params);
		HttpEntity he;
		List<location> mlists = new ArrayList<location>();  
		try {
			he=new UrlEncodedFormEntity(params,"GB2312");
			hp.setEntity(he);
			HttpResponse response=hc.execute(hp);
			System.out.println(response.getStatusLine().getStatusCode());
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			{
				HttpEntity het=response.getEntity();
				InputStream is=het.getContent();
				int len=0;
				byte[] data = new byte[1024*32];  
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
				while ((len = is.read(data)) != -1) {  
		            outStream.write(data, 0, len);  
		        }  
		        is.close();  
		        data=outStream.toByteArray();  
		        JSONArray array = new JSONArray(new String(data)); 
		        for (int i = 0; i < array.length(); i++) {  
		        	JSONObject item = array.getJSONObject(i); 
		        	int lid=item.getInt("lid");
		            String lper=item.getString("lper");
		            long ltel=item.getLong("ltel");
		            String loc=item.getString("loc");
		            mlists.add(new location(lid,lper,ltel,loc));   
		        }
		        System.out.println("hhhhjhh"+mlists);
			}
			else{
				mlists=null;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mlists;
	}
}
