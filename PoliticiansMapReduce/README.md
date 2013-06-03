Politicians Single Node solution:
=================================

Assumptions:
============
1. Data inputed is huge, basic number is as total earth population ~8 billion.
2. Each handshake is represented by pairs of unsigned integers( range 0 - 4294967296).
3. This is pseudo code.

HandShake

Algorithm:
===========

Basic algorithm is that mapping phase will map all pairs of data sorted by smallest ID, for instance if we see
two pairs, [1,2] and [10,1], after map phase, we will see [1,2] [1,10]. So on reduce phase, if we see two or more values
for the same key were found, will indicate that 'bad' politicians were found.


MAP:

Each data line is mapped to 'Handshake' object. 
'HandShake' object is represents actual handshake data that occurred between two politicians, 
each 'HandShake' object will be created based on inputed data from the external file, 
'HandShake' ID is size of 64-bit and will be generated based on two numbers aka politicians IDs, most significant bits are
occupied by smallest ID, less significant by biggest ID. For instance if we see two pairs, [1-10] and [10,1], two objects that 
will be created for them, will contain the same 64 bit ID. 


Reduce:
========

Using Reduce:reduce function to locate 'bad' politicians, data is based on local node map If two or more values
for the same key were found, will indicate that 'bad' politicians were found.


'Memory' of politicians IDs that were seen already:
===================================================

To prevent duplicate printing of 'bad' IDs that we saw already, all 'bad' politicians IDs will
be kept in a separate 'cache'. 

Memory size calculation for cache:

To efficiently map in cache( like Redis) range of 8 billion numbers, 
will needed, as straight forward solution, 8 billion people * 4 bytes( integer size ) = 32 GB + cache overhead,
To save place, the numbers could be more efficiently saved, for instance each byte could contain 
bit indication of 8 numbers, integer cell could keep data about 32 numbers, 
which will reduce mapping from 32 GB to 1 GB + cache framework overhead.

For instance: to map 67, 67 / 32 = 2, which means that hash code '2' will be used, than 67 mod 32 = 3, 
which means that bit number 3 will be set to 1, to indicate 67.


Packaging:
==========

mvn package