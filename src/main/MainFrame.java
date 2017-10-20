package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;


import util.ClusterUtil;
import util.IOTools;
import util.MST;
import util.MatrixUtil;
import vo.Point;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
//import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class MainFrame extends JFrame {

	//private JFrame frame;
	private JTextField textThreshold;
	public static List<Set<Integer>> clusters;
	public static Long prim_time;
	public static Long clustering_time;
	private List<Point> points;
	private JTextField textScale;
	private JTextPane textMessagePan;
	private JPanel panel;
	private JTextField txtNoiseThreshold;
	private JLabel lblNoiseThreshold;

	/**
	 * Launch the application.
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		 //1. loading data.
		Map<String,Object> cfg = IOTools.configure;
		List<Point> pointList = IOTools.inputData();
		//2. compute distance between each point.
		double[][]  distanceMatrix = MatrixUtil.distanceMatrix(pointList);
		int [] trace = new int[distanceMatrix.length];
		//3. perform Prim algorithm, distanceMatrix will be modified to store MST
		Long start_prim = System.currentTimeMillis();
		MST.prim(distanceMatrix,trace);
		Long end_prim = System.currentTimeMillis();
		Long prim_time = end_prim - start_prim;
		//4. using cutting threshold to clustering
		Long start_clustering = System.currentTimeMillis();
		List<Set<Integer>> clusters = ClusterUtil.clusteringUsingMST(distanceMatrix, Double.valueOf(cfg.get("threshold").toString()));
		Long end_clustering = System.currentTimeMillis();
		Long clustering_time = end_clustering - start_clustering;
		
		MainFrame window = new MainFrame(clusters, pointList, distanceMatrix);
		MainFrame.prim_time = prim_time;
		MainFrame.clustering_time = clustering_time;
		window.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public MainFrame(List<Set<Integer>> clusters,List<Point> points, double[][]  distanceMatrix) {
		
		initialize(clusters, points,distanceMatrix);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(List<Set<Integer>> clusters,List<Point> points,double[][] distanceMatrix) {
		this.clusters = clusters;
		this.points = points;
		
		
		//frame = new JFrame();
		this.getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 13));
		this.getContentPane().setBackground(Color.WHITE);
		this.setBounds(100, 100, 906, 398);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textThreshold = new JTextField(IOTools.configure.get("threshold").toString());
		textThreshold.setColumns(10);
		
		JLabel lblThreshold = new JLabel("threshold:");
		
		JButton btnStart = new JButton("Start Clustering");
		btnStart.setToolTipText("Starting to cutting");
		btnStart.setHorizontalAlignment(SwingConstants.RIGHT);
		
		textScale = new JTextField();
		textScale.setToolTipText("features scale");
		textScale.setText(IOTools.configure.get("scale").toString());
		textScale.setColumns(10);
		
		JButton btnRepaint = new JButton("Refresh");
		
		textMessagePan = new JTextPane();
		textMessagePan.setFont(new Font("Helvetica", Font.PLAIN, 13));
		textMessagePan.setEditable(false);
		JLabel lblDataScale = new JLabel("enlarge scale:");
		
		txtNoiseThreshold = new JTextField();
		txtNoiseThreshold.setColumns(10);
		txtNoiseThreshold.setText(IOTools.configure.get("noise").toString());
		lblNoiseThreshold = new JLabel("noise threshold:");
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		//panel.revalidate();
		//panel.setEnabled(false);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNoiseThreshold)
										.addComponent(lblDataScale))
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(textScale, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
										.addComponent(txtNoiseThreshold, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnStart)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblThreshold, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textThreshold, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
									.addGap(32))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textMessagePan, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnRepaint, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblThreshold)
								.addComponent(textThreshold, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(btnStart)
							.addGap(12)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textScale, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDataScale, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtNoiseThreshold, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNoiseThreshold))
							.addGap(18)
							.addComponent(btnRepaint)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textMessagePan, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		getContentPane().setLayout(groupLayout);
	//	getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, lblThreshold, btnStart, textThreshold, textMessagePan, lblDataScale, textScale, btnRepaint}));
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Long start_clustering = System.currentTimeMillis();
				double threshold = Double.parseDouble(textThreshold.getText().toString());
				MainFrame.clusters = ClusterUtil.clusteringUsingMST(distanceMatrix,threshold );
				Long end_clustering = System.currentTimeMillis();
				MainFrame.clustering_time = end_clustering - start_clustering;
				repaint();
			}
		});
		btnRepaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
	}

	
	
	@Override
	public void paint(Graphics g) {
		//text pane 446, 11, 153, 129
		
		double scale = Double.valueOf(textScale.getText());
//		int tmp = Math.min(spShow.getWidth() , spShow.getHeight());
//		scale = tmp/40;
//		textScale.setText(""+scale);
		super.paint(g);
		g.setFont(new Font("Helvetica", Font.PLAIN, 13));
		String message = "Total Time: \n\t"+(prim_time+clustering_time)+"ms;\n"
				+ "Prim Time: \n\t"+prim_time+"ms;\n"
				+ "Clustering Time: \n\t"+clustering_time +"ms";
		textMessagePan.setText(message);
		
		
		Graphics spG = panel.getGraphics();
		List<Color> colorList = new ArrayList<Color>();
		List<String> markerList = new ArrayList<String>();
		//初始化簇集的颜色
		Random random = new Random();
		for(int i =0 ; i <= clusters.size();i++){
			Color color = Color.decode(random.nextInt()+"");
			if(colorList.contains(color)){
				i--;continue;
			}
			String marker = getRandomString(1);
			colorList.add(color);
			markerList.add(marker);
			//System.out.println(marker);
		}
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for(int i = 0; i < clusters.size() ; i++ ){
			Set<Integer> cluster = clusters.get(i);
			String marker = null;
			int noise = Integer.valueOf(txtNoiseThreshold.getText());
			if(cluster.size() <= noise){
				spG.setColor(Color.BLACK);
				marker= "•";
			}else{
				spG.setColor(colorList.get(i));
				marker= markerList.get(i);
				
			}
			for(Integer pIndex : cluster){
				Point point = points.get(pIndex);
				int x = (int)(point.getAttrs()[0]*scale);
				int y = (int)(point.getAttrs()[1]*scale);
				spG.drawString(marker, x, y);
				if(maxX < x) maxX = x;
				if(maxY < y) maxY = y;
			}
		}
		//panel.setPreferredSize(new Dimension(maxX,maxY));
		
		//spShow.setViewportView(panel);
		//validate();
	}
	
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789+-*^#";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 } 
}
