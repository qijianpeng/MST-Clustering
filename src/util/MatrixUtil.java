package util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vo.Point;

public class MatrixUtil {
	/**
	 * 计算点之间的距离
	 * @param points 点列表集合
	 * @return
	 */
	public static double[][] distanceMatrix(List<Point> points){
		double[][] matrix = new double[points.size()][points.size()];
		for(int i = 0; i < points.size() ; i ++){
			for(int j = points.size() -1; j > i ; j--){
				double distance = getDistance(points.get(i),points.get(j));
				matrix[i][j] = distance;
				matrix[j][i] = distance;
			}
		}
		return matrix;
	}
	/**
	 * 计算p1与p2之间欧式距离
	 * @param p1
	 * @param p2
	 * @return
	 */
	private static double getDistance(Point p1,Point p2){
		int dimension =  Integer.valueOf(IOTools.configure.get("dimension").toString());
		double sum = 0.0;
		for(int i = 0; i < dimension ; i ++){
			sum = sum + Math.pow(p1.getAttrs()[i] - p2.getAttrs()[i], 2.0);
		}
		return Math.sqrt(sum);
	}
	
}
