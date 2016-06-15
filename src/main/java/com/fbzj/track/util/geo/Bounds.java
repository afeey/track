package com.fbzj.track.util.geo;

import java.util.List;

/**
 * 矩形
 */
public class Bounds {

	/**
	 * 西南角坐标
	 */
	private Point southWest;

	/**
	 * 东北角坐标
	 */
	private Point northEast;
	
	/**
	 * 构造函数
	 */
	public Bounds(){
		this.southWest = null;
		this.northEast = null;
	}
	
	/**
	 * 构造函数
	 * @param sw 西南角坐标
	 * @param ne 东北角坐标
	 */
	public Bounds(Point sw,Point ne){
		this.southWest = sw;
		this.northEast = ne;
	}

	public Point getSouthWest() {
		return southWest;
	}
	
	public void setSouthWest(Point southWest) {
		this.southWest = southWest;
	}

	public Point getNorthEast() {
		return northEast;
	}

	public void setNorthEast(Point northEast) {
		this.northEast = northEast;
	}
	
	public static Bounds getBounds(List<Point> points){
		
		if (points.size() < 3) { // 小于三个点无法构成面
			return null;
		}

		double eastLng = 0d; 	// 最东经度
		double northLat = 0d; 	// 最北维度
		double westLng = 0d; 	// 最西经度
		double southLat = 0d; 	// 最南维度
		int size = points.size();
		for (int i = 0; i < size; i++) {
			Point point = points.get(i);

			// 计算最东和最西经度
			if (point.getLng() > eastLng) {
				eastLng = point.getLng();
			} else if (point.getLng() < westLng) {
				westLng = point.getLng();
			}

			// 计算最北和最南维度
			if (point.getLat() > northLat) {
				northLat = point.getLat();
			} else if (point.getLat() < southLat) {
				southLat = point.getLat();
			}
		}
		
		Point sw = new Point(westLng, southLat);//西南角坐标
		Point ne = new Point(eastLng, northLat);//东北角坐标
		
		return new Bounds(sw,ne);
		
	}
}
