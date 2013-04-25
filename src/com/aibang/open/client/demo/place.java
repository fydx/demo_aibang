package com.aibang.open.client.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class place {
	private String img_url;
	private String id;
	private String desc;
	private String cate;
	private String tel;
	private String name;
	private String addr;
	private double rate;
	private double pos_x;
	private double pos_y;
	private int cost;
	private int dist;
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
	public static List<place> jsonStringToList() {
		
		return null;
		
	}
}
