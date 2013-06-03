Politicians Single Node solution:
=================================

Assumptions:
============
1. Solution is based on assumption that data size is relatively small and could fit into the RAM.
2. Each handshake is represented by pairs of unsigned integers( range 0 - 4294967296).


Algorithm:
===========

'HandShake' object is represents actual handshake data that occurred between two politicians, 
each 'HandShake' object will be created based on inputed data from external file, 
'HandShake' ID is size of 64-bit and will be generated based on two numbers aka politicians IDs, most significant bits are
occupied by smallest ID, less significant by biggest ID. For instance if we see two pairs, [1-10] and [10,1], two objects that 
will be created for them, will contain the same 64 bit ID. 
Code will read one by one handshake data and put them in the handshakes hash map, that located in object 'PoliticiansData',
each time will be checked presence of handshake ID, if we saw it before, meaning that we've found a couple of politicians IDs,
that should be marked as 'bad'. To prevent duplicate printing of 'bad' IDs that we saw already, all 'bad' politicians IDs will
be kept in a separate hash map.

Code:
=====

To run program, run 'Application' class, which already contains a sample code. 'PoliticiansData' contains data manipulation logic.
'HandShake' object represents data about politicians intersection aka handshake.


Packaging:
==========

mvn package