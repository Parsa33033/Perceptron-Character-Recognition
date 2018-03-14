import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Test implements Runnable
{
  //field
	private int[][] weights;
	private double Threshold;
	private int[][] singleCharArr;
	private int i = 0 , j = 0, n = 0, m = 0;
	private List<String> charSet;
  //method
	public Test(int[][] weights, double Threshold, int n, int m, List<String> charSet)
	{
		this.weights = weights;
		this.Threshold = Threshold;
		singleCharArr = new int[n][m];
		this.charSet = charSet;
		this.n = n;
		this.m = m;
		
	}//constructor
	
	public void run()
	{
		test();
	}//run method
	
	
	
	public void test()
	{
		getCharacter("character");
		int[] input = createInput();
		int[] sumOfInputByW = new int[charSet.size()];
		for(int i = 0 ; i<weights.length ; i++)
		{
			for(int j = 0 ; j<weights[0].length ; j++)
			{
				sumOfInputByW[i] += weights[i][j] * input[j];
			}//for j each input
		}//for each character
		
		List<Integer> l = Arrays.stream(sumOfInputByW).boxed().collect(Collectors.toList());
		int i = Collections.max(l);
		int index = l.indexOf(i);
		
		System.out.println("the character recognized is: "+ charSet.get(index));
	}//test method
	
	
	
	public void getCharacter(String fileName)
	{
		try
		{
			File file = new File(Test.class.getResource(fileName).toURI());
			FileInputStream fileInput = new FileInputStream(file);
			int c = 0;
			while((c = fileInput.read()) != -1)
			{
				extractCharacter((char)c);
				
			}//while
			
			//mahmood.shabankhah@gmail.com
			System.out.println();
			System.out.println("the input character is:");
			for(int i = 0 ; i<n ; i++)
			{
				for(int j = 0 ; j<m ; j++)
				{
					System.out.print((singleCharArr[i][j]==1)?"#":"*");
				}
				System.out.println();
			}
			System.out.println();
			
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
		}//catch
	}//getCharacter method
	
	
	
	public void extractCharacter(char c)
	{
		
		if(c == '#')
		{
			singleCharArr[i][j] = 1;
			j++;
		}
		else if(c == '*')
		{
			singleCharArr[i][j] = -1;
			j++;
		}
		//set i and j according to the resolution
		if(j >= m)
		{
			i++;
			j = 0;
		}//if j>=n
		
		if(i >= n)
		{
			i = 0;
		}//if i>=n
		
	}//extractCharacter method
	
	
	
	public int[] createInput()
	{
		int[] input = new int[n*m+1];
		input[0] = 1;
		int z = 1;
		for(int i = 0 ; i<singleCharArr.length ; i++)
		{
			for(int j = 0 ; j<singleCharArr[0].length ; j++)
			{
				input[z] = singleCharArr[i][j];
				z++;
			}//for j
		}//for i
		
		return input;
	}//createInput method
	
	
	
	
}//class Test
