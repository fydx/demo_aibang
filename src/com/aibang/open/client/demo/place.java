package com.aibang.open.client.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aibang.open.client.exception.AibangException;
//地点类
public class place {
	private String img_url; //图片url
	private String id;   //id
	private String desc;  //描述
	private String cate;	//类别
	private String tel;	//电话
	private String name;	//名字
	private String addr;//地址
	private double rate;//评分，会有小数点后面一位
	private double pos_x;	//纬度 30多的那个
	private double pos_y;   //经度 108左右的那个
	private int cost;     //花费
	private int dist;	//位置偏移
	//getters and setters
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getImg_url() {
		return img_url;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPos_x() {
		return pos_x;
	}
	public void setPos_x(double pos_x) {
		this.pos_x = pos_x;
	}
	public double getPos_y() {
		return pos_y;
	}
	public void setPos_y(double pos_y) {
		this.pos_y = pos_y;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getDist() {
		return dist;
	}
	public void setDist(int dist) {
		this.dist = dist;
	}
	
	@Override
	public String toString() {
		return "place [img_url=" + img_url + ", id=" + id + ", desc=" + desc
				+ ", cate=" + cate + ", tel=" + tel + ", name=" + name
				+ ", addr=" + addr + ", rate=" + rate + ", pos_x=" + pos_x
				+ ", pos_y=" + pos_y + ", cost=" + cost + ", dist=" + dist
				+ "]";
	}
	//jsonarray 转为list
	public static List<place> JsonToPlaceList(JSONArray jsonArray)
	{
		
		System.out.println("success 1");
		List<place> places = new  ArrayList<place>();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				System.out.println(String.valueOf(i));
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				place temp_place = new place();
				temp_place = JsonobTOPlace(jsonObject);
				System.out.println(temp_place.toString());
				places.add(temp_place);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return places;
		
	}
	//一个jsonobject返回一个place对象
	public static place JsonobTOPlace(JSONObject jsonObject) throws JSONException
	{
		place temp_place = new place();
		temp_place.setId(jsonObject.getString("id"));
		temp_place.setCost(jsonObject.getInt("cost"));
		temp_place.setDesc(jsonObject.getString("desc"));
		temp_place.setDist(jsonObject.getInt("dist"));
		temp_place.setImg_url(jsonObject.getString("img_url"));
		temp_place.setCate(jsonObject.getString("cate"));
		temp_place.setName(jsonObject.getString("name"));
		temp_place.setPos_x(jsonObject.getDouble("lat"));
		temp_place.setPos_y(jsonObject.getDouble("lng"));
		temp_place.setAddr(jsonObject.getString("addr"));
		temp_place.setRate(jsonObject.getDouble("rate"));
		temp_place.setTel(jsonObject.getString("tel"));
		return temp_place;
	}
	//传入一个城市和地址，返回附近地点，保存在list中
	public static List<place> jsonStringToList(String city,String addr) throws JSONException, AibangException {
		int from,result_num;
		from = 0;
		int count = 0 ;
		List<place> places = new  ArrayList<place>();
		List<place> places_temp = new ArrayList<place>();
	//	JSONObject jsonObject = new JSONObject(jsonString);
	//	int place_num = jsonObject.getInt("total");
		JavaDemo javaDemo = new JavaDemo();
	  for(;;)
	  {
		  count++;
		  //  String jsonString2 = javaDemo.search("西安", "东大街", from);
		  JSONObject jsonObject_temp1 = new JSONObject(javaDemo.search(city, addr, from));
		  result_num = jsonObject_temp1.getInt("result_num");
		 
		  if (result_num <=0||count>2) {
			break;
		}
		  String jsonString_bizs= jsonObject_temp1.getString("bizs");
		  JSONObject jsonObject_temp2 = new JSONObject(jsonString_bizs);
		  JSONArray array = jsonObject_temp2.getJSONArray("biz");
		  places_temp= JsonToPlaceList(array);
		  for (int i = 0; i < places_temp.size(); i++) {
			places.add(places_temp.get(i));
		}
		  from+=9;
	  }
		return places;
		
	}
}
