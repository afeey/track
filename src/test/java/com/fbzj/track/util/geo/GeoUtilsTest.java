package com.fbzj.track.util.geo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GeoUtilsTest {

	@Test
	public void testGetDistance() {
		Point point1 = new Point(116.368904, 39.923423);
		Point point2 = new Point(116.387271, 39.922501);
	    double distance = GeoUtils.getDistance(point1, point2);
	    System.out.println(distance);
	}

	@Test
	public void testGetPolygonArea() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPolylineDistance() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsPointInCircle() {
		fail("Not yet implemented");
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
		Point inPoint = new Point(116.40496,39.900486);
		Point outPoint = new Point(116.368904, 39.923423);
		
		assertEquals(true, GeoUtils.isPointInPolygon(inPoint, polygon));
		assertEquals(false, GeoUtils.isPointInPolygon(outPoint, polygon));
		
	}

	@Test
	public void testIsPointInRect() {
		Point sw = new Point(116.374319,39.899696);
		Point ne = new Point(116.40127,39.907597);
		Bounds bounds = new Bounds(sw, ne);
		
		Point inPoint = new Point(116.381271,39.904305);
		Point outPoint = new Point(116.391056,39.896996);
		
		assertEquals(true, GeoUtils.isPointInRect(inPoint, bounds));
		assertEquals(false, GeoUtils.isPointInRect(outPoint, bounds));
	}

	@Test
	public void testIsPointOnPolyline() {
		fail("Not yet implemented");
	}

	@Test
	public void testDegreeToRad() {
		fail("Not yet implemented");
	}

	@Test
	public void testRadToDegree() {
		fail("Not yet implemented");
	}

}
