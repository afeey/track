package com.fbzj.track.util.geo;

import java.util.List;

/**
 * 地理计算工具类
 * @author afeey
 *
 */
public class GeoUtils {

	 /**
	  * 地球半径
	  */
	 private static final double EARTHRADIUS = 6370996.81; 
	 
	 
	/**
	 * 计算两点之间的距离,两点坐标必须为经纬度
	 * @param point1 坐标1
	 * @param point2 坐标2
	 * @return 距离，单位米
	 */
	public static double getDistance(Point point1, Point point2){
		point1.setLng(getLoop(point1.getLng(), -180, 180));
        point1.setLat(getRange(point1.getLat(), -74, 74));
        point2.setLng(getLoop(point2.getLng(), -180, 180));
        point2.setLat(getRange(point2.getLat(), -74, 74));
        
        double x1, x2, y1, y2;
        x1 = degreeToRad(point1.getLng());
        y1 = degreeToRad(point1.getLat());
        x2 = degreeToRad(point2.getLng());
        y2 = degreeToRad(point2.getLat());

        return EARTHRADIUS * Math.acos((Math.sin(y1) * Math.sin(y2) + Math.cos(y1) * Math.cos(y2) * Math.cos(x2 - x1)));   
	}
	
	/**
	 * 将v值限定在a,b之间，纬度使用
	 * @param v v值
	 * @param a a值
	 * @param b b值
	 * @return 返回值
	 */
    private static double getRange(double v, Integer a, Integer b){
        if(a != null){
          v = Math.max(v, a);
        }
        if(b != null){
          v = Math.min(v, b);
        }
        return v;
    }
    
    /**
	 * 将v值限定在a,b之间，经度使用
	 * @param v v值
	 * @param a a值
	 * @param b b值
	 * @return 返回值
	 */
    private static double getLoop(double v, Integer a, Integer b){
        while( v > b){
          v -= b - a;
        }
        while(v < a){
          v += b - a;
        }
        return v;
    }

	/**
	 * 计算多边形面或点数组构建图形的面积,注意：坐标类型只能是经纬度，且不适合计算自相交多边形的面积
	 * @param polygon 多边形
	 * @return 面积，单位平方米
	 */
	public static double getPolygonArea(Polygon polygon){
		
        List<Point> pts = polygon.getPoints();

        if(pts.size() < 3){//小于3个顶点，不能构建面
            return 0d;
        }
        
        double totalArea = 0;//初始化总面积
        double LowX = 0.0;
        double LowY = 0.0;
        double MiddleX = 0.0;
        double MiddleY = 0.0;
        double HighX = 0.0;
        double HighY = 0.0;
        double AM = 0.0;
        double BM = 0.0;
        double CM = 0.0;
        double AL = 0.0;
        double BL = 0.0;
        double CL = 0.0;
        double AH = 0.0;
        double BH = 0.0;
        double CH = 0.0;
        double CoefficientL = 0.0;
        double CoefficientH = 0.0;
        double ALtangent = 0.0;
        double BLtangent = 0.0;
        double CLtangent = 0.0;
        double AHtangent = 0.0;
        double BHtangent = 0.0;
        double CHtangent = 0.0;
        double ANormalLine = 0.0;
        double BNormalLine = 0.0;
        double CNormalLine = 0.0;
        double OrientationValue = 0.0;
        double AngleCos = 0.0;
        double Sum1 = 0.0;
        double Sum2 = 0.0;
        int Count2 = 0;
        int Count1 = 0;
        double Sum = 0.0;
        double Radius = EARTHRADIUS; //6378137.0,WGS84椭球半径 
        int Count = pts.size();        
        for (int i = 0; i < Count; i++) {
            if (i == 0) {
                LowX = pts.get(Count - 1).getLng() * Math.PI / 180;
                LowY = pts.get(Count - 1).getLat() * Math.PI / 180;
                MiddleX = pts.get(0).getLng() * Math.PI / 180;
                MiddleY = pts.get(0).getLat() * Math.PI / 180;
                HighX = pts.get(1).getLng() * Math.PI / 180;
                HighY = pts.get(1).getLat() * Math.PI / 180;
            }
            else if (i == Count - 1) {
                LowX = pts.get(Count - 2).getLng() * Math.PI / 180;
                LowY = pts.get(Count - 2).getLat() * Math.PI / 180;
                MiddleX = pts.get(Count - 1).getLng() * Math.PI / 180;
                MiddleY = pts.get(Count - 1).getLat() * Math.PI / 180;
                HighX = pts.get(0).getLng() * Math.PI / 180;
                HighY = pts.get(0).getLat() * Math.PI / 180;
            }
            else {
                LowX = pts.get(i - 1).getLng() * Math.PI / 180;
                LowY = pts.get(i - 1).getLat() * Math.PI / 180;
                MiddleX = pts.get(i).getLng() * Math.PI / 180;
                MiddleY = pts.get(i).getLat() * Math.PI / 180;
                HighX = pts.get(i + 1).getLng() * Math.PI / 180;
                HighY = pts.get(i + 1).getLat() * Math.PI / 180;
            }
            AM = Math.cos(MiddleY) * Math.cos(MiddleX);
            BM = Math.cos(MiddleY) * Math.sin(MiddleX);
            CM = Math.sin(MiddleY);
            AL = Math.cos(LowY) * Math.cos(LowX);
            BL = Math.cos(LowY) * Math.sin(LowX);
            CL = Math.sin(LowY);
            AH = Math.cos(HighY) * Math.cos(HighX);
            BH = Math.cos(HighY) * Math.sin(HighX);
            CH = Math.sin(HighY);
            CoefficientL = (AM * AM + BM * BM + CM * CM) / (AM * AL + BM * BL + CM * CL);
            CoefficientH = (AM * AM + BM * BM + CM * CM) / (AM * AH + BM * BH + CM * CH);
            ALtangent = CoefficientL * AL - AM;
            BLtangent = CoefficientL * BL - BM;
            CLtangent = CoefficientL * CL - CM;
            AHtangent = CoefficientH * AH - AM;
            BHtangent = CoefficientH * BH - BM;
            CHtangent = CoefficientH * CH - CM;
            AngleCos = (AHtangent * ALtangent + BHtangent * BLtangent + CHtangent * CLtangent) / (Math.sqrt(AHtangent * AHtangent + BHtangent * BHtangent + CHtangent * CHtangent) * Math.sqrt(ALtangent * ALtangent + BLtangent * BLtangent + CLtangent * CLtangent));
            AngleCos = Math.acos(AngleCos);            
            ANormalLine = BHtangent * CLtangent - CHtangent * BLtangent;
            BNormalLine = 0 - (AHtangent * CLtangent - CHtangent * ALtangent);
            CNormalLine = AHtangent * BLtangent - BHtangent * ALtangent;
            if (AM != 0)
                OrientationValue = ANormalLine / AM;
            else if (BM != 0)
                OrientationValue = BNormalLine / BM;
            else
                OrientationValue = CNormalLine / CM;
            if (OrientationValue > 0) {
                Sum1 += AngleCos;
                Count1++;
            }
            else {
                Sum2 += AngleCos;
                Count2++;
            }
        }        
        double tempSum1, tempSum2;
        tempSum1 = Sum1 + (2 * Math.PI * Count2 - Sum2);
        tempSum2 = (2 * Math.PI * Count1 - Sum1) + Sum2;
        if (Sum1 > Sum2) {
            if ((tempSum1 - (Count - 2) * Math.PI) < 1)
                Sum = tempSum1;
            else
                Sum = tempSum2;
        }
        else {
            if ((tempSum2 - (Count - 2) * Math.PI) < 1)
                Sum = tempSum2;
            else
                Sum = tempSum1;
        }
        totalArea = (Sum - (Count - 2) * Math.PI) * Radius * Radius;

        return totalArea; //返回总面积
	}

	/**
	 * 计算折线或者点数组的长度
	 * @param polyline 折线
	 * @return 长度，单位米
	 */
	public static double getPolylineDistance(Polyline polyline){
		
        List<Point> pts = polyline.getPoints();
        if(pts.size() < 2){//小于2个点，返回0
            return 0d;
        }
        
        //遍历所有线段将其相加，计算整条线段的长度
        double totalDis = 0;
        for(int i =0; i < pts.size() - 1; i++){
            Point curPt = pts.get(i);
            Point nextPt = pts.get(i + 1);
            double dis = GeoUtils.getDistance(curPt, nextPt);
            totalDis += dis;
        }

        return totalDis;
	}

	/**
	 * 判断点是否在圆形内
	 * @param point 坐标点
	 * @param circle 圆形
	 * @return true在圆内，false不在圆内
	 */
	public static boolean isPointInCircle(Point point, Circle circle){
		Point c = circle.getCenter();
        double r = circle.getRadius();

        double dis = GeoUtils.getDistance(point, c);
        if(dis <= r){
            return true;
        } else {
            return false;
        }
	}

	/**
	 * 判断点是否多边形内
	 * @param point 坐标点
	 * @param polygon 多边形
	 * @return true在多边形内，false不在多边形内
	 */
	public static boolean isPointInPolygon(Point point, Polygon polygon){

        //首先判断点是否在多边形的外包矩形内，如果在，则进一步判断，否则返回false
        Bounds polygonBounds = polygon.getBounds();
        if(!isPointInRect(point, polygonBounds)){
            return false;
        }

        List<Point> pts = polygon.getPoints();//获取多边形点
        
        //下述代码来源：http://paulbourke.net/geometry/insidepoly/，进行了部分修改
        //基本思想是利用射线法，计算射线与多边形各边的交点，如果是偶数，则点在多边形外，否则
        //在多边形内。还会考虑一些特殊情况，如点在多边形顶点上，点在多边形边上等特殊情况。
        
        int N = pts.size();
        boolean boundOrVertex = true; 	//如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;			//cross points count of x 
        double precision = 2e-10; 		//浮点类型计算时候与0比较时候的容差
        Point p1, p2;					//neighbour bound vertices
        Point p = point; 				//测试点
        
        p1 = pts.get(0);				//left vertex        
        for(int i = 1; i <= N; ++i){	//check all rays            
            if(p.getLng() == p1.getLng() && p.getLat() == p1.getLat()){
                return boundOrVertex;	//p is an vertex
            }
            
            p2 = pts.get(i % N);		//right vertex            
            if(p.getLat() < Math.min(p1.getLat(), p2.getLat()) || p.getLat() > Math.max(p1.getLat(), p2.getLat())){	//ray is outside of our interests                
                p1 = p2; 
                continue;//next ray left point
            }
            
            if(p.getLat() > Math.min(p1.getLat(), p2.getLat()) && p.getLat() < Math.max(p1.getLat(), p2.getLat())){//ray is crossing over by the algorithm (common part of)
                if(p.getLng() <= Math.max(p1.getLng(), p2.getLng())){//x is before of ray                    
                    if(p1.getLat() == p2.getLat() && p.getLng() >= Math.min(p1.getLng(), p2.getLng())){//overlies on a horizontal ray
                        return boundOrVertex;
                    }
                    
                    if(p1.getLng() == p2.getLng()){//ray is vertical                        
                        if(p1.getLng() == p.getLng()){//overlies on a vertical ray
                            return boundOrVertex;
                        }else{//before ray
                            ++intersectCount;
                        } 
                    }else{//cross point on the left side                        
                        double xinters = (p.getLat() - p1.getLat()) * (p2.getLng() - p1.getLng()) / (p2.getLat() - p1.getLat()) + p1.getLng();//cross point of getLng()                        
                        if(Math.abs(p.getLng() - xinters) < precision){//overlies on a ray
                            return boundOrVertex;
                        }
                        
                        if(p.getLng() < xinters){//before ray
                            ++intersectCount;
                        } 
                    }
                }
            }else{//special case when ray is crossing through the vertex                
                if(p.getLat() == p2.getLat() && p.getLng() <= p2.getLng()){//p crossing over p2                    
                    Point p3 = pts.get((i+1) % N); //next vertex                    
                    if(p.getLat() >= Math.min(p1.getLat(), p3.getLat()) && p.getLat() <= Math.max(p1.getLat(), p3.getLat())){//p.getLat() lies between p1.getLat() & p3.getLat()
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }            
            p1 = p2;//next ray left point
        }
        
        if(intersectCount % 2 == 0){//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }
	}

	/**
	 * 判断点是否在矩形内
	 * @param point 坐标点
	 * @param bounds 矩形
	 * @return true在矩形内，false不在矩形内
	 */
	public static boolean isPointInRect(Point point, Bounds bounds){
		 Point sw = bounds.getSouthWest(); //西南脚点
		 Point ne = bounds.getNorthEast(); //东北脚点
	     return (point.getLng() >= sw.getLng() && point.getLng() <= ne.getLng() && point.getLat() >= sw.getLat() && point.getLat() <= ne.getLat());
	}

	/**
	 * 判断点是否在折线上
	 * @param point 坐标点
	 * @param polyline 折线
	 * @return true在折线上,不在折线上
	 */
	public static boolean isPointOnPolyline(Point point, Polyline polyline){

        //首先判断点是否在线的外包矩形内，如果在，则进一步判断，否则返回false
        Bounds lineBounds = polyline.getBounds();
        if(!isPointInRect(point, lineBounds)){
            return false;
        }

        //判断点是否在线段上，设点为Q，线段为P1P2 ，
        //判断点Q在该线段上的依据是：( Q - P1 ) × ( P2 - P1 ) = 0，且 Q 在以 P1，P2为对角顶点的矩形内
        List<Point> pts = polyline.getPoints();
        for(int i = 0; i < pts.size() - 1; i++){
        	Point curPt = pts.get(i);
            Point nextPt = pts.get(i + 1);
            //首先判断point是否在curPt和nextPt之间，即：此判断该点是否在该线段的外包矩形内
            if (point.getLng() >= Math.min(curPt.getLng(), nextPt.getLng()) && point.getLng() <= Math.max(curPt.getLng(), nextPt.getLng()) &&
                point.getLat() >= Math.min(curPt.getLat(), nextPt.getLat()) && point.getLat() <= Math.max(curPt.getLat(), nextPt.getLat())){
                //判断点是否在直线上公式
                double precision = (curPt.getLng() - point.getLng()) * (nextPt.getLat() - point.getLat()) - 
                    (nextPt.getLng() - point.getLng()) * (curPt.getLat() - point.getLat());                
                if(precision < 2e-10 && precision > -2e-10){//实质判断是否接近0
                    return true;
                }                
            }
        }
        
        return false;
	}

	/**
	 * 将度转化为弧度
	 * @param degree 度
	 * @return 弧度
	 */
	public static double degreeToRad(double degree){
		 return Math.PI * degree/180; 
	}

	/**
	 * 将弧度转化为度
	 * @param rad 弧度
	 * @return 度
	 */
	public static double radToDegree(double rad){
		return (180 * rad) / Math.PI;
	}

}
