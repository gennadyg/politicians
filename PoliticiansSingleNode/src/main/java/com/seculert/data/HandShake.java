package com.seculert.data;

import java.math.BigInteger;

public class HandShake
{
	private Integer smallestUid;
	private Integer biggestUid;
	
	private BigInteger handshakeUid = null;
	
	
	
	
	/**
	 * @return the smallestUid
	 */
	public Integer getSmallestUid()
	{
		return smallestUid;
	}


	/**
	 * @param smallestUid the smallestUid to set
	 */
	public void setSmallestUid(Integer smallestUid)
	{
		this.smallestUid = smallestUid;
	}


	/**
	 * @return the biggestUid
	 */
	public Integer getBiggestUid()
	{
		return biggestUid;
	}


	/**
	 * @param biggestUid the biggestUid to set
	 */
	public void setBiggestUid(Integer biggestUid) 
	{
		this.biggestUid = biggestUid;
	}


	/**
	 * @return the handshakeUidx
	 */
	public BigInteger getHandshakeUid()
	{
		return handshakeUid;
	}


	/**
	 * @param handshakeUid the handshakeUid to set
	 */
	public void setHandshakeUid(BigInteger handshakeUid)
	{
		this.handshakeUid = handshakeUid;
	}


	public HandShake( Integer smallestUid, Integer biggestUid )
	{
		this.smallestUid = smallestUid;
		this.biggestUid = biggestUid;
		
		BigInteger small = new BigInteger( smallestUid.toString() );
		BigInteger big = new BigInteger( biggestUid.toString() );
		handshakeUid = small.shiftLeft(32);
		handshakeUid = handshakeUid.or( big );
		
	}


	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return "[smallestUid=" + smallestUid + ", biggestUid="
				+ biggestUid + "]";
	}


	public void print()
	{
		System.out.println( this.smallestUid + " -> " + this.biggestUid );
	}


	
	
}
