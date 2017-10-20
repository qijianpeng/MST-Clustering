package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import util.ClusterUtil;
import util.IOTools;
import util.MST;
import util.MatrixUtil;
import vo.Point;

public class TestClass {

	@Test
	public void testInputData(){

		System.out.println("[INFO 001]Test: input data test");
		List<Point> points = null;
		try {
			points = IOTools.inputData();
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testPrim() {

		System.out.println("[INFO 002]Test: prim test");
		double[][] distanceMatrix = {
				{Double.NaN, 6.0, 1.0, 5.0, Double.NaN, Double.NaN},
				{6.0, Double.NaN, 5.0, Double.NaN, 3.0, Double.NaN},
				{1.0, 5.0, Double.NaN, 5.0, 6.0, 4.0},
				{5.0, Double.NaN, 5.0, Double.NaN, Double.NaN, 2.0},
				{Double.NaN, 3.0, 6.0, Double.NaN, Double.NaN, 6.0},
				{Double.NaN, Double.NaN, 4.0, 2.0, 6.0, Double.NaN}
		};
		int[] trace = new int[distanceMatrix.length];
		MST.prim(distanceMatrix,trace);
		int count = 0;
		
		for(double[] row : distanceMatrix){
			System.out.print("["+count+"]");
			for(double col : row){
				System.out.print(col + "\t");
			}
			
			System.out.println(trace[count++]);
		}
		System.out.println();
	}
	
	@Test
	public void testMatrix(){
		System.out.println("[INFO 003]Test: generating distance matrix");
		List<Point> points = new ArrayList<Point>();
		points.add(new Point("1",new double[]{1.0,1.0}));
		points.add(new Point("2",new double[]{1.0,2.0}));
		points.add(new Point("3",new double[]{2.0,1.0}));
		points.add(new Point("4",new double[]{2.0,2.0}));
		double[][] matrix = MatrixUtil.distanceMatrix(points);
		for (double[] row : matrix) {
			System.out.print("[");
			for(double e : row){
				System.out.print(e + ", ");
			}
			System.out.println("]");
		}
		int[] trace = new int[matrix.length];
		MST.prim(matrix,trace);
	}

	@Test
	public void testClusteringUsingMST(){
		System.out.println("[INFO 004]Test: mst clustering");
		double[][] distanceMatrix = {
				{Double.NaN, 6.0, 1.0, 5.0, Double.NaN, Double.NaN},
				{6.0, Double.NaN, 5.0, Double.NaN, 3.0, Double.NaN},
				{1.0, 5.0, Double.NaN, 5.0, 6.0, 4.0},
				{5.0, Double.NaN, 5.0, Double.NaN, Double.NaN, 2.0},
				{Double.NaN, 3.0, 6.0, Double.NaN, Double.NaN, 6.0},
				{Double.NaN, Double.NaN, 4.0, 2.0, 6.0, Double.NaN}
		};
		int[] trace = new int[distanceMatrix.length];
		MST.prim(distanceMatrix, trace);
		
		List<Set<Integer>> clusters = ClusterUtil.clusteringUsingMST(distanceMatrix, 3);
		for(Set cluster : clusters){
			System.out.println(cluster);
		}
	}
}
