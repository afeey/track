package com.fbzj.track.util.geo;

import java.util.ArrayList;
import java.util.List;

/**
 * 多边形
 * 
 * @author afeey
 *
 */
public class Polygon {

	/**
	 * 经纬度坐标列表
	 */
	private List<Point> points;

	/**
	 * 构造函数
	 */
	public Polygon() {
		this.points = new ArrayList<Point>();
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Bounds getBounds() {
		return Bounds.getBounds(this.points);
	}

}
