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


Additional features:
====================

In case of huge data file, suggesting to use 'external sort', for instance:

1. Read 100 MB of the data in main memory and sort by some conventional method, like quicksort.
2. Write the sorted data to disk.
3. Repeat steps 1 and 2 until all of the data is in sorted 100 MB chunks (there are 900MB / 100MB = 9 chunks), which now need to be merged into one single output file.
4. Read the first 10 MB (= 100MB / (9 chunks + 1)) of each sorted chunk into input buffers in main memory and allocate the remaining 10 MB for an output buffer. (In practice, it might provide better performance to make the output buffer larger and the input buffers slightly smaller.)
5. Perform a 9-way merge and store the result in the output buffer. If the output buffer is full, write it to the final sorted file, and empty it. If any of the 9 input buffers gets empty, fill it with the next 10 MB of its associated 100 MB sorted chunk until no more data from the chunk is available. This is the key step that makes external merge sort work externally -- because the merge algorithm only makes one pass sequentially through each of the chunks, each chunk does not have to be loaded completely; rather, sequential parts of the chunk can be loaded as needed.


Packaging:
==========

mvn package