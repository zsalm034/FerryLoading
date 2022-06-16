/**
 * Big Table implementation of Ferry Problem
 * @author Zain Salman - 7790429
 */

import java.io.*;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Scanner;
import java.util.*;


public class Main{
	private static int bestK, n, L, totalSpace;
	private static boolean[][] visited;
	private static ArrayList<Integer> length;
	private static int[] currX, bestX;

	/**
    * Initializes the number of ferries, the length of the ferry, the boolean 2D array, array of all the car lengths, the best k, and the current and best array of each cars desigination in the ferry.
    * @throws IOException if the file is not found or any IO errors
    */
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));


		int n_ferries = Integer.parseInt(br.readLine()); // number of ferries


		for(int i=0; i<n_ferries; i++){
			br.readLine();
			L = (Integer.parseInt(br.readLine())) * 100; // length of ferry
			length = new ArrayList<>();
			int k = Integer.parseInt(br.readLine());
			while(k != 0){
				length.add(k);
				k = Integer.parseInt(br.readLine());
			}
			n = length.size();
			bestK = -1;
			visited = new boolean[n+1][L+1]; //Memorization 2D boolean array
			currX = new int[n];
			bestX = new int[n];
			totalSpace = 0; // total space occupied in the ferry
			Main solve = new Main();
			solve.BacktrackSolve(0,L);

			out.println(bestK);
			
			for(int j=0; j<bestK; j++){
				if(bestX[j] == 1){
					out.println("port");
				}
				else if(bestX[j] == 0){
					out.println("starboard");
				}
			}
			if(i == n_ferries-1){
				out.close();
				br.close();
			}
			out.println();
		}
	}

	/**
    * Recursively finds the greatest number of cars that can fit in the ferry of given length, using a hash map to record visited states.
    * @param int  the current number of cars evaluated
    * @param int  the current space left on the left side
    */
	public void BacktrackSolve(int currK, int currS){

		if (currK>bestK){
			bestK = currK;
			bestX = currX.clone();
			
		}

		if(currK<n) {
			if(currS >= length.get(currK) && visited[currK+1][currS-length.get(currK)] != true){
				
				currX[currK] = 1;
				totalSpace += length.get(currK);

				int newS = currS-length.get(currK);
				BacktrackSolve(currK+1, newS);
				visited[currK+1][newS] = true;
				totalSpace -= length.get(currK);
			}
			if((2*L-totalSpace-currS) >= length.get(currK) && visited[currK+1][currS] != true){
				
				currX[currK] = 0;
				totalSpace += length.get(currK);
			
				BacktrackSolve(currK+1, currS);
				visited[currK+1][currS] = true;
				totalSpace -= length.get(currK);
			}
		}
	}
	
}