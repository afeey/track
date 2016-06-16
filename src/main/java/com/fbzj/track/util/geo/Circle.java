package com.fbzj.track.util.geo;

/**
 * 圆形
 * @author afeey
 *
 */
public class Circle {
	
	/**
	 * 圆心
	 */
	private Point center;

	/**
	 * 半径，单位米
	 */
	private double radius;
	
	/**
	 * 构造函数
	 */
	public Circle(){
		this.center = null;
		this.radius = 0d;
	}
	
	/**
	 * 构造函数
	 * @param center 圆心坐标
	 * @param radius 半径，单位m
	 */
	public Circle(Point center,double radius){
		this.center = center;
		this.radius = radius;
	}
	
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
