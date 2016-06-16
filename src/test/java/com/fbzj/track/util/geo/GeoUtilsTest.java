package com.fbzj.track.util.geo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GeoUtilsTest {

	@Test
	public void testGetDistance() {
		Point point1 = new Point(118.682838, 32.048325);
		Point point2 = new Point(118.683736, 32.047839);
		double distance = GeoUtils.getDistance(point1, point2);

		System.out.println("===============================================");
		System.out.println("距离(m)：100.51459（误差不大表示计算正确）");
		System.out.println("计算距离(m)：" + distance);
		System.out.println("");
	}

	@Test
	public void testGetPolygonArea() {
		Point point1 = new Point(118.777579, 32.110354);
		Point point2 = new Point(118.785648, 32.09465);
		Point point3 = new Point(118.802212, 32.095595);
		Point point4 = new Point(118.809852, 32.102357);

		List<Point> points = new ArrayList<Point>();
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);
		Polygon polygon = new Polygon(points);

		double area = GeoUtils.getPolygonArea(polygon);
		System.out.println("===============================================");
		System.out.println("计算面积（平方米）：" + area);
		System.out.println("");
	}

	@Test
	public void testGetPolylineDistance() {

		double distance = 100.979343 + 100.198656 + 100.732802; // 总长度
		Point point1 = new Point(118.682838, 32.048325);
		Point point2 = new Point(118.683740, 32.047837);
		Point point3 = new Point(118.684635, 32.047353);
		Point point4 = new Point(118.685535, 32.046866);
		List<Point> points = new ArrayList<Point>();
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);

		Polyline polyline = new Polyline(points);
		double dis = GeoUtils.getPolylineDistance(polyline);

		System.out.println("===============================================");
		System.out.println("总长度(m)：" + distance + "（误差不大表示计算正确）");
		System.out.println("计算长度(m)：" + dis);
		System.out.println("");
	}

	@Test
	public void testIsPointInCircle() {
		Circle circle = new Circle(new Point(118.715733, 32.02768), 400);

		Point inPoint = new Point(118.717399, 32.029985);
		Point outPoint = new Point(118.718512, 32.031524);

		assertEquals(true, GeoUtils.isPointInCircle(inPoint, circle));
		assertEquals(false, GeoUtils.isPointInCircle(outPoint, circle));
	}

	@Test
	public void testIsPointInPolygon() {

		Point point1 = new Point(116.403322, 39.920255);
		Point point2 = new Point(116.410703, 39.897555);
		Point point3 = new Point(116.402292, 39.892353);
		Point point4 = new Point(116.389846, 39.891365);
		List<Point> points = new ArrayList<Point>();
		points.add(point1);
		points.add(point2);
		points.add(point3);
		points.add(point4);

		Polygon polygon = new Polygon(points);
		Point inPoint = new Point(116.40496, 39.900486);
		Point outPoint = new Point(116.368904, 39.923423);

		assertEquals(true, GeoUtils.isPointInPolygon(inPoint, polygon));
		assertEquals(false, GeoUtils.isPointInPolygon(outPoint, polygon));

	}

	@Test
	public void testIsPointInRect() {
		Point sw = new Point(116.374319, 39.899696);
		Point ne = new Point(116.40127, 39.907597);
		Bounds bounds = new Bounds(sw, ne);

		Point inPoint = new Point(116.381271, 39.904305);
		Point outPoint = new Point(116.391056, 39.896996);

		assertEquals(true, GeoUtils.isPointInRect(inPoint, bounds));
		assertEquals(false, GeoUtils.isPointInRect(outPoint, bounds));
	}

	@Test
	public void testIsPointOnPolyline() {

		Point point1 = new Point(118.682838, 32.048325);
		Point point2 = new Point(118.683288, 32.048082);
		Point point3 = new Point(118.683740, 32.047837);
		List<Point> points = new ArrayList<Point>();
		points.add(point1);
		points.add(point2);
		points.add(point3);
		Polyline polyline = new Polyline(points);

		Point onPolyline = new Point(118.683738, 32.047838);
		Point outPolyline = new Point(118.683288, 33.048082);

		assertEquals(true, GeoUtils.isPointOnPolyline(onPolyline, polyline));
		assertEquals(false, GeoUtils.isPointOnPolyline(outPolyline, polyline));

	}

	@Test
	public void testDegreeToRad() {
		double rad = GeoUtils.degreeToRad(1.0);

		System.out.println("===============================================");
		System.out.println("1度  = " + rad + "弧度");
		assertEquals(0.0174533, rad, 0.000001);
		System.out.println("");
	}

	@Test
	public void testRadToDegree() {
		double degree = GeoUtils.radToDegree(1.0);

		System.out.println("===============================================");
		System.out.println("1弧度 = " + degree + "度");
		assertEquals(57.29578, degree, 0.000001);
		System.out.println("");
	}

}
