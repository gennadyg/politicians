package com.mapreduce;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class HanshakeProcessor 
{
	private final static IntWritable one = new IntWritable(1);
	
	public static enum Counters { INPUT_RECORDS, BAD_RECORDS, BAD_POLITICIANS_RECORDS };
	/*
	 * Assuming that rang of 
	 * 
	 * 
	 * */
	final static int maxNumOfBitsInPoliticianID = 32;
	
	public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, IntWritable>
	{
		
		
		private LongWritable handshakeUid = null;


		/**
		 * Validate politician ID from the input file
		 * 
		 * @param id
		 * @return
		 */
		public boolean isValidPoliticianID( String id )
		{
			boolean result = false;
			if( ( id != null ) && ( id.length() > 0 ) && id.matches("-?\\d+(\\.\\d+)?") )
			{
				result = true;
			}
			return result;
		}
		
		/* 
		 * MAP phase: map all handshake pairs by first smallest politician ID than greater. 
		 * In addition map two 32 bit numbers to 64 bit long, most significant bits are
		 * occupied by smallest ID, less significant by biggest ID.
		 * 
		 * 
		 * @see org.apache.hadoop.mapred.Mapper#map(java.lang.Object, java.lang.Object, org.apache.hadoop.mapred.OutputCollector, org.apache.hadoop.mapred.Reporter)
		 */
		public void map( LongWritable key, Text value, OutputCollector<LongWritable, IntWritable> output, Reporter reporter) throws IOException 
		{
			String line = value.toString();
			String politicians[] = line.split(",");


			if( ( politicians.length == 2 ) && isValidPoliticianID( politicians[0]) && isValidPoliticianID( politicians[0] ) )
			{
				BigInteger id1 = new BigInteger( politicians[0] );
				BigInteger id2 = new BigInteger( politicians[1] );
				
				if( id1.compareTo( id2 ) < 0 )
				{
					handshakeUid = new LongWritable( id1.shiftLeft(maxNumOfBitsInPoliticianID).or( id2 ).longValue());
					
				}else // reverse IDs
				{
					handshakeUid = new LongWritable( id2.shiftLeft(maxNumOfBitsInPoliticianID).or( id1 ).longValue());
				}
				output.collect( handshakeUid, one );
			}else
			{
				reporter.incrCounter( Counters.BAD_RECORDS, 1 );
				System.out.println( "Not valid data " + " [" + value.toString() + "]");
			}
			
		}
	}
  
	public static class Reduce extends MapReduceBase implements Reducer<LongWritable, IntWritable, LongWritable, IntWritable> 
	{
		Text ret = new Text();
		String retString = "";
		public void reduce( LongWritable key, Iterator<IntWritable> values, OutputCollector<LongWritable, IntWritable> output, Reporter reporter ) throws IOException 
		{
			Integer numOfOccurences = 0;
		
			if( values.hasNext()) 
			{
				numOfOccurences += values.next().get();
				
				/* 
				 * Checking if encountered on two same pairs: for instance 1-10  1-10
				 *  In such case report found bad politician.
					This part could be improved, since has no memory about greatest 
					ID found, from example 10 in example above, to improve all found bad IDs could be
					efficiently mapped/saved in cache( like Redis), will need for that 
					straight forward 8 billion people * 4 bytes = 32 GB + cache overhead,
					to save place numbers could be efficiently saved, for instance each 
					byte could contain bit indication of 8 numbers, integer cell will keep data about 
					32 numbers, which will reduce mapping to 1 GB + Redis overhead.
				*/				
				if( numOfOccurences >= 2 )
				{
					reporter.incrCounter( Counters.BAD_POLITICIANS_RECORDS, 2 );
					BigInteger uid = new BigInteger( String.valueOf( key.get()) );
					
					
					// extracting 32 bit IDs from long
					LongWritable badPolitician1 = new LongWritable( uid.shiftRight( maxNumOfBitsInPoliticianID ).longValue());
					LongWritable badPolitician2 = new LongWritable( uid.intValue() );
					
					// if not found before, not exist in cache, print as well.
					output.collect( badPolitician1, one );
					
					// if not found before, not exist in cache, print as well.
					output.collect( badPolitician2, one );
					// save badPolitician2 in cache of found 'bad' politicians
				}

			}  		  
	
		}
	}
	
	  public static void main(String[] args) throws Exception 
	  {
		JobConf job = new JobConf( HanshakeProcessor.class);
		job.setJobName("Handshake problem");
		
	    job.setOutputKeyClass( IntWritable.class);
	 	job.setOutputValueClass( IntWritable.class);
	    
	    job.setMapperClass( Map.class);
	    job.setCombinerClass( Reduce.class);
	    job.setReducerClass( Reduce.class);
	    job.setNumMapTasks(1);
	    
   
	    job.setInputFormat( TextInputFormat.class);
	    job.setOutputFormat( TextOutputFormat.class);

	    
	    FileInputFormat.addInputPath( job, new Path( args[0]));
	    FileOutputFormat.setOutputPath( job, new Path( args[1]));
	    JobClient.runJob( job );
	  }
}
