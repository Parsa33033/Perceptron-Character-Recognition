import java.util.List;
import java.util.Map;

public class PerceptronTraining implements Runnable
{
  //field
	private Weights w;
	private String characterName;
	private List<String> charNames;
	private Map<String, Integer[][]> mapOfChars;
	private List<String> charSet;
	private int[] target;
	private int sizeOfInput;
	private int indexOfChar;
	private double THRESHOLD ;
	private int epochCount = 0;
  //methods
	
	
	
	public PerceptronTraining(Weights w, String characterName,
			List<String> charNames, Map<String, Integer[][]> mapOfChars, List<String> charSet, double threshold)
	{
		this.w = w;
		this.characterName = characterName;
		this.charNames = charNames;
		this.mapOfChars = mapOfChars;
		this.charSet = charSet;
		this.THRESHOLD = threshold;
		target = new int[charNames.size()];
		indexOfChar = charSet.indexOf(characterName);
	}//constructor
	
	
	
	public void run()
	{
		createTarget();
		train();
		System.out.println();
		System.out.println(characterName + " needed " +epochCount+" epochs to train");
		System.out.println();
	}//run method
	
	
	
	public void train()
	{
		int[] input;
		int[] weightTemp; 
		weightTemp = new int[sizeOfInput];
		sizeOfInput = mapOfChars.get(charNames.get(0)).length * mapOfChars.get(charNames.get(0))[0].length + 1;
		for(int i = 0 ; i< charNames.size() ; i++)
		{
			weightTemp = w.getWeightRow(charSet.indexOf(characterName));
			input = new int[sizeOfInput];
			input = createInput(charNames.get(i));
			if(isLearning(input, weightTemp, target[i]))
			{
				learn(input, target[i]);
				epochCount++;
			}//if we need to learn
		}//for loop through the inputs of characters and training the weight matrix
		
	}//train method
	
	
	
	public void learn(int[] input, int target)
	{
		int[] temp = new int[input.length];
		for(int i = 0 ; i < input.length ; i++ )
		{
			temp[i] = input[i] * target;
		}//for
		w.setWeightRow(indexOfChar, temp);
	}//learn method
	
	
	
	public boolean isLearning(int[] input, int[] weightTemp, int target)
	{
		int i = compareThreshold(input, weightTemp);
		if(i == target)
		{
			return false;
		}//if output is equal to target
		else
		{
			return true;
		}//else
		
	}//learn method
	
	
	
	
	public int compareThreshold(int[] input, int[] weightTemp)
	{
		int sum = 0;
		for(int i = 0 ; i<input.length ; i++)
		{
			sum += input[i] * weightTemp[i];
		}//for i
		if(sum > THRESHOLD)
		{
			return 1; 
		}
		else if(sum < -THRESHOLD)
		{
			return -1;
		}
		else
		{
			return 0;
		}
		
	}//compareThreshold method
	
	
	
	public int[] createInput(String s)
	{
		Integer[][] charArr = mapOfChars.get(s);
		int[] input = new int[sizeOfInput];
		input[0] = 1;
		int z = 1;
		for(int i = 0 ; i<charArr.length ; i++)
		{
			for(int j = 0 ; j<charArr[0].length ; j++)
			{
				input[z] = charArr[i][j];
				z++;
			}//for j
		}//for i
		
		return input;
	}//createInput method

	
	
	public void createTarget()
	{
		for(int i = 0 ; i<target.length ; i++)
		{
			if(charNames.get(i).contains(characterName))
			{
				target[i] = 1;
			}//if the charName value contains the character name that we want to train for
			else
			{
				target[i] = -1;
			}
		}//for
		
	}//createTarger method
	
	
}
