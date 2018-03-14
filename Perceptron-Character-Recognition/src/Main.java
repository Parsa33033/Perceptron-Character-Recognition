import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main 
{
  //field
	private Map<String, Integer[][]> mapOfChars = new TreeMap<String, Integer[][]>();
	private static int n=0 , m = 0, i = 0, j = 0;
	private final static String FILENAME = "input.txt";
	private List<String> charNames;
	private List<String> charSet;
	private final double THRESHOLD = 0.5;
  //methods
	
	
	
	public static void main(String[] args) throws Exception
	{
		Main main = new Main();
		System.out.println("Threshold is: " + main.THRESHOLD);
		main.charNames =new ArrayList<String>();
		main.charSet = new ArrayList<String>();
		main.getCharResolution(FILENAME);
		main.getCharacters(FILENAME);
		Weights w = new Weights(main.charSet.size(), n*m + 1);
		w.setRandWeights(main.charSet);
		PerceptronTraining a = new PerceptronTraining(w, "A", main.charNames, main.mapOfChars, main.charSet, main.THRESHOLD);
		Thread[] t = new Thread[main.charSet.size()];
		for(int i = 0 ; i<main.charSet.size() ; i++)
		{
			t[i] = new Thread(new PerceptronTraining(w, main.charSet.get(i), main.charNames, main.mapOfChars, main.charSet,main.THRESHOLD));
			t[i].start();
			t[i].join();
		}//for
		
		int[][] weight = w.getWeight();
		
		System.out.println("the weights set for each character are: ");
		for(int i = 0 ; i< main.charSet.size() ; i++)
		{
			System.out.print("weight of "+main.charSet.get(i)+" is ==> \t");
			for(int j = 0 ; j< n*m+1 ; j++)
			{
				System.out.print(weight[i][j]+"\t");
			}
			System.out.println();
		}
		Test test = new Test(weight, 0.5, n, m, main.charSet);
		Thread testThread = new Thread(test);
		try
		{
			testThread.start();
			testThread.join();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		for(int i = 0 ; i < weight.length ; i++)
//		{
//			for(int j = 0 ; j< weight[0].length ; j++)
//			{
//				System.out.print(weight[i][j]);
//			}
//			System.out.println();
//		}
		
	}//main method
	
	
	
	
	public int[][] getCharacters(String fileName)
	{
		try
		{
			File file = new File(Main.class.getResource(fileName).toURI());
			FileInputStream fileInput = new FileInputStream(file);
			Integer[][] singleCharArr = new Integer[n][m];
			int c = 0 ;
			
			while((c = fileInput.read()) != -1)
			{
				extractCharacter((char)c, singleCharArr);
				if(c == '/')
				{
					StringBuilder str = new StringBuilder();
					while((c = fileInput.read()) != '\n')
					{
						if(c == -1)break;
						str.append((char)c);
					}//while not the end of line of the name of the character
					String s = str.toString();
					String charName = s.replaceAll("[^A-Z0-9]", "");
					mapOfChars.put(charName, singleCharArr);
					charNames.add(charName);
					if(!charSet.contains(s.replaceAll("[^A-Z]", "")))
					{
						charSet.add(s.replaceAll("[^A-Z]", ""));
					}
					singleCharArr = new Integer[n][m];
				}
			}//while
			
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
		}//catch
		
		return null;
	}
	
	
	
	public void extractCharacter(char c, Integer[][] singleCharArr)
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
	
	
	
	public void getCharResolution(String fileName)
	{
		try
		{
			File file = new File(Main.class.getResource(fileName).toURI());
			FileInputStream fileInput = new FileInputStream(file);		
			int c = 0 ;
			int temp = 0;
			while((c = fileInput.read())!= '/')
			{
				if(c == '#' || c == '*')
				{
					temp++;
				}
				if(c == '\n')
				{
					n++;
					m = temp;
					temp = 0;
					
				}
			}//while
			fileInput.close();
		}//try
		catch(Exception e)
		{
			e.printStackTrace();
		}//catch
		
	}//getCharResolution method
	
	
	
	
}//Main class
