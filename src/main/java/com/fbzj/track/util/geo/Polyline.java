package com.fbzj.track.util.geo;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线
 * @author afeey
 *
 */
public class Polyline {

	/**
	 * 经纬度坐标列表
	 */
	private List<Point> points;

	/**
	 * 构造函数
	 */
	public Polyline(){
		this.points = new ArrayList<Point>();
	}
	
	/**
	 * 构造函数
	 */
	public Polyline(List<Point> points){
		this.points = points;
	}
	
	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public Bounds getBounds(){
		return Bounds.getBounds(this.points);
	}

}
