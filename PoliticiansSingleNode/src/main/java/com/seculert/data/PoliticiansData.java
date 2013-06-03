package com.seculert.data;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PoliticiansData 
{
	Map<BigInteger, HandShake> handshakeMap = new HashMap<BigInteger, HandShake>();

	public void print()
	{
		for( Map.Entry<BigInteger, HandShake> current : handshakeMap.entrySet())
		{
			current.getValue().print();
		}
	}
	
	/**
	 * We want to sort chunks that each fit in RAM, then merges the sorted chunks together.
	 * For example, for sorting 900 megabytes of data using only 100 megabytes of RAM:
	    1. Read 100 MB of the data in main memory and sort by some conventional method, like quicksort.
		2. Write the sorted data to disk.
		3. Repeat steps 1 and 2 until all of the data is in sorted 100 MB chunks (there are 900MB / 100MB = 9 chunks), which now need to be merged into one single output file.
		4. Read the first 10 MB (= 100MB / (9 chunks + 1)) of each sorted chunk into input buffers in main memory and allocate the remaining 10 MB for an output buffer. (In practice, it might provide better performance to make the output buffer larger and the input buffers slightly smaller.)
		5. Perform a 9-way merge and store the result in the output buffer. If the output buffer is full, write it to the final sorted file, and empty it. If any of the 9 input buffers gets empty, fill it with the next 10 MB of its associated 100 MB sorted chunk until no more data from the chunk is available. This is the key step that makes external merge sort work externally -- because the merge algorithm only makes one pass sequentially through each of the chunks, each chunk does not have to be loaded completely; rather, sequential parts of the chunk can be loaded as needed.
	*/
	public void makeExternalMergeSort()
	{
		
	}
	
	public void findBadPoliticians()
	{
		try 
		{
			BigInteger badCandidate = null;
			
			for( BigInteger current : handshakeMap.keySet())
			{
				//System.out.println( handshakeMap.get( current ).toString());
				if( badCandidate == null )
				{
					badCandidate = current;
				
				}else if( badCandidate == current )
				{
					System.out.println("BAD " + handshakeMap.get(current).toString());
				
				}else 
				{
					badCandidate = current;
				}				
			}
			
		} catch (Exception e) 
		{
			System.e("", e);
		}
	}
	
	public boolean isValidPoliticianID( String id )
	{
		boolean result = false;
		if( ( id != null ) && ( id.length() > 0 ) && id.matches("\\d") )
		{
			result = true;
		}
		return result;
	}
	
	/**
	 * This function reads lines by line handshake data from the file, 
	 * 
	 * @param fileName
	 */
	public void findBadPoliticiansInFile( String fileName )
	{
		// local variables
		String strLine;
		Integer first = null;
		Integer second = null;
		HandShake foundHandShake = null;
		DataInputStream in = null;
		
		// using auto closebable feature from Java 7
		try ( BufferedReader br = new BufferedReader( new FileReader(fileName)))
		{
			  //Read File Line By Line
			  while (( strLine = br.readLine()) != null )   
			  {
				  String[] handshakes = strLine.split(",");
				  
				  if( isValidPoliticianID( handshakes[0] ) && isValidPoliticianID( handshakes[1] ) )
				  {
					  first = Integer.valueOf( handshakes[0] );
					  second = Integer.valueOf( handshakes[1] );
					  
					  if( first < second )
					  {
						  foundHandShake = new HandShake( first, second );
						  
					  }else
					  {
						  foundHandShake = new HandShake( second, first );
					  }
					  HandShake badPoliticians = handshakeMap.put( foundHandShake.getHandshakeUid(), foundHandShake );
					  
					  if( badPoliticians != null  )
					  {
						  System.out.println("BAD " + badPoliticians.toString());
					  }
				  }else
				  {
					  
				  }
				  
				 
				  
				 
			  }
			  
		} catch (Exception e) 
		{
			System.err.println("Error: " + e.getMessage());
			
		}			
	}
}
