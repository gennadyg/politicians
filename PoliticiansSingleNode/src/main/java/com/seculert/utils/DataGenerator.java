package com.seculert.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.seculert.data.PoliticiansData;

public class DataGenerator 
{
	public boolean generatePoliticiansData( Integer min, Integer max, String fileName )
	{
		boolean result = false;
		// Range of politicians ID
		
		FileWriter fileWriter = null;
		BufferedWriter writer = null;
		
		try 
		{
			System.out.println("Going to generate data from " + min + " to " + max + " in file[" + fileName + "]");
			fileWriter = new FileWriter( fileName );
			writer = new BufferedWriter( fileWriter );
			
			
			for( int currentNumber = 0; currentNumber < max; currentNumber ++ )
			{
				Integer generatedID1 = min + (int)( Math.random() * (( max - min ) + 1));
				Integer generatedID2 = min + (int)( Math.random() * (( max - min ) + 1));
				
				System.out.println(generatedID1 + "," + generatedID2);
				
				writer.append( generatedID1 + "," + generatedID2 );
				writer.newLine();
				
			}
			result = true;
			
			
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
			
		}finally
		{
			try 
			{
				if( writer != null )
				{
					writer.close();
				}
				if( fileWriter != null )
				{
					fileWriter.close();
				}
				
				
			} catch ( IOException e )
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
		
	}
	public static void main( String[] args )
	{
		
		DataGenerator dataGenerator = new DataGenerator();
		
		String fileName = "C://politicians.csv";
		
		dataGenerator.generatePoliticiansData( 0, 100, fileName );
		
	//	if( dataGenerator.generatePoliticiansData( 0, 100, fileName ))
		{
			PoliticiansData politiciansData = new PoliticiansData();
			politiciansData.findBadPoliticiansInFile( fileName ); 
			politiciansData.print();
			politiciansData.findBadPoliticians();
		}
	}
	

}
