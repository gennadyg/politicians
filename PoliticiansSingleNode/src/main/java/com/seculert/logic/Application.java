package com.seculert.logic;

import com.seculert.data.PoliticiansData;
import com.seculert.utils.DataGenerator;

public class Application
{
	public static void main( String[] args )
	{
		
		DataGenerator dataGenerator = new DataGenerator();
		
		String fileName = "C://politicians.csv";
		
		dataGenerator.generatePoliticiansData( 0, 100, fileName );
		
	//	if( dataGenerator.generatePoliticiansData( 0, 100, fileName ))
		{
			PoliticiansData politiciansData = new PoliticiansData();
			
			// here example for a small input, for a huge input, 
						// external sorting should be used, described as function comments
						// without code
						// politiciansData.makeExternalMergeSort();
			
			politiciansData.findBadPoliticiansInFile( fileName );
			
			//politiciansData.print();
			
		}
	}
} 
