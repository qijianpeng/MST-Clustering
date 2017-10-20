package util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ClusterUtil {

	/**
	 * 使用最小生成树的链接矩阵进行聚类
	 * @param chainMatrix
	 * @return
	 */
	public static List<Set<Integer>> clusteringUsingMST(double[][] chainMatrix, double threshold){
		System.out.println("[INFO 005] breadth first searching...");
		return MST.bfs(chainMatrix, threshold);
	}
}
