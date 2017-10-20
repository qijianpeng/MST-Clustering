package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

public class MST {
	/**
	 * Prim算法, 得到链接矩阵
	 * @param distanceMatrix 点之间的距离，下标从0开始
	 */
	public static void prim(double distanceMatrix[][], int[] trace){
		int n = distanceMatrix.length;
		double []lowcost = new double[n];
		int []closest = new int[n];
		boolean []s =  new boolean[n];
		//1. 将第0个点加入
		s[0] = true;
		for(int i = 1; i < n; i++){
			if(Double.isNaN(distanceMatrix[0][i]))lowcost[i] = Double.MAX_VALUE;
			else {
				lowcost[i] = distanceMatrix[0][i];
				distanceMatrix[0][i] = Double.NaN;
				closest[i] = 0;
				s[i] = false;
			}
		}
		//2. 将其它点加入
		for(int i = 1; i < n; i++){
			double min = Double.MAX_VALUE;
			int j = 1;
			for(int k = 1; k < n ; k++){
				if( (lowcost[k] < min) &&( !s[k] ) ){
					min = lowcost[k];
					j = k;
				}
			}
		//	System.out.println(j+" "+closest[j]+" "+min);
			trace[j] = closest[j];
			fillOtherValues(distanceMatrix[j], closest[j],Double.NaN);
			distanceMatrix[closest[j]][j] = min;
			//将点j加入
			s[j] = true;
			
			//更新剩余点到已划分簇的距离
			for( int k = 1; k < n ; k++){
				if( !Double.isNaN(distanceMatrix[k][j]) && ( !s[k] ) && (distanceMatrix[k][j] < lowcost[k]) ){
					lowcost[k] = distanceMatrix[k][j];
					closest[k] = j;
				}
			}
			
		}
	}
	/**
	 * 填充数据，将数组row中除了第c个元素，其它元素填充为value
	 * @param row 一维数组
	 * @param c 保留元素下标
	 * @param value 填充值
	 */
	private static void fillOtherValues(double[] row, int c, double value){
		if( c < 0 )return;
		for(int i = 0; i < row.length; i ++){
			if( i == c) continue;
			 row[i] = value;
			// System.out.println(i);
		}
	}
	/**
	 * 广度优先遍历
	 * @param distanceMatrix
	 * @param threshold 切割阈值
	 * @return
	 */
	public static List<Set<Integer>> bfs(double [][] distanceMatrix, double threshold){
		List<Set<Integer>> results = new ArrayList<Set<Integer>>();
		boolean[] isChecked = new boolean[distanceMatrix.length];
		int rest = distanceMatrix.length;
		int n = distanceMatrix.length;
		while(rest > 0){
			//1. 选择一个点p作为初始点
			Integer entry = null;
			for(int i = 0; i < n; i++){
				if(!isChecked[i]) {
					entry = i;
					break;
				}
			}
			Set<Integer> cluster = new HashSet<Integer>();			
			Queue<Integer> queue = new  LinkedList<Integer>();
			queue.add(entry);
			//2. 扫描与entry相连的点（直接／间接）
			while(!queue.isEmpty()){
				Integer element = queue.poll();
				if(isChecked[element]) continue;
				isChecked[element] = true;
				rest -- ;
				cluster.add(element);
				//添加与element直接可达的点到queue
				addElements2Queue(distanceMatrix,queue,element,cluster,threshold);
			}	
			results.add(cluster);
		}
		return results;
	}
	private static void addElements2Queue(double [][] distanceMatrix, Queue quene, Integer e,Set<Integer> cluster, double threshold){
		double[] row = distanceMatrix[e];
		for(int i = 0; i < row.length ; i ++){
			if(Double.isNaN(row[i]) || row[i] > threshold) continue;
			if(cluster.contains(row[i]))continue;
			quene.add(i);
		}
		
	}
}
