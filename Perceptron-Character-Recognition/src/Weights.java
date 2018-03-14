import java.security.SecureRandom;
import java.util.List;

public class Weights 
{
  //field
	private int n;
	private int m;
	private int[][] w;
  //method
	public Weights(int n, int m)
	{
		this.n = n;
		this.m = m;
		w = new int[n][m];
	}//constructor
	
	
	
	public int[] getWeightRow(int i)
	{
		return w[i];
	}//getWeight
	
	
	
	synchronized public void setWeightRow(int i, int[] input)
	{
		for(int j = 0 ; j<input.length ; j++)
		{
			w[i][j] += input[j];
		}//for
	}//setWeightRow method
	
	
	
	
	public int[][] getWeight()
	{
		return w;
	}//getWeight method
	
	
	
	public void setRandWeights(List<String> charSet)
	{
		SecureRandom rand = new SecureRandom();
		System.out.println("Initial Weights are:");
		for(int i = 0 ; i<n ; i++)
		{
			System.out.print(charSet.get(i)+" ==>\t ");
			for(int j = 0 ; j<m ; j++)
			{
				//w[i][j] = rand.nextInt(10);
				System.out.print(w[i][j]+"\t");
			}//for j
			System.out.println();
		}//for i
		
	}//setRandWeights()
	
}//Weights Class
