package main;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.ClusterUtil;
import util.IOTools;
import util.MST;
import util.MatrixUtil;
import vo.Point;

public class Main {
	 public static void main(String[] args) throws IOException {
		 //1. loading data.
		Map<String,Object> cfg = IOTools.configure;
		List<Point> pointList = IOTools.inputData();
		//2. compute distance between each point.
		double[][]  distanceMatrix = MatrixUtil.distanceMatrix(pointList);
		int [] trace = new int[distanceMatrix.length];
		//3. perform Prim algorithm, distanceMatrix will be modified to store MST
		MST.prim(distanceMatrix,trace);
		//4. using cutting threshold to clustering
		List<Set<Integer>> clusters = ClusterUtil.clusteringUsingMST(distanceMatrix, Double.valueOf(cfg.get("threshold").toString()));
		//5. show result.
		System.out.println();
	}
}
