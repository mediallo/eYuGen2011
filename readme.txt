============================================================
This software is written by Albert Yu (syu AT cs DOT duke DOT edu)(2010)
============================================================
Version 1.3
============================================================
The Main method is located in reconstruction/DataGenerator.java.
Users can change the configuration in reconstruction/Config.java.

Input files:

1. Node Info file (See testData/ip2country.txt)
nodeId; ip address; country; geographic region number
ex: 4; 143.248.139.169; REPUBLIC OF KOREA; 0
Note that a geographic region number is a number between 0 and #regions - 1. 

2. Node Coord file (See testData/coordinates.txt)
nodeId coord1 coord2 ... coordD
ex: 0 334.4 101.6 349.7 59.7 75.0

3. Category file (See testData/category1.txt)
One path name per line. 
ex: Asian - Eastern Asian - Chinese - Simplified

Note that the ordering of siblings is the same as their ordering of
appearance in the file.
ex:	Computers - Internet
	Computers - Multimedia
	Computers - Games
Computers has 3 sub-categories. Internet is the first sub-category,
multimedia is the second, and games is the last.

Note that a path name needs not be specified if a longer path name is
also specified in the file. For example, if
"Asian - Eastern Asian - Chinese - Simplified" is included in the file,
"Asian", "Asian - Eastern Asian", and "Asian - Eastern Asian - Chinese" may
or may not be included in the file. Either is fine, but you may find it
easier to specify the ordering of siblings when they are also included in
the file.  

4. Interest file (See testData/interests.txt)
RegionName; Atomic category1; Atomic category2; ... ; #listings; #members
ex: Asia; Computers - Programming; Asian - South Eastern Asian - Indonesian; 4; 9
Note that 1) the full path names need to be specified,
          2) #listings is actually ignored.

5. Event file (See testData/messageInfo.txt)
Atomic category1; Atomic category2; ... ; #messages
Note that the full path names need to be specified.

Output files:

1. Subscription files (See outputs/subs.txt)
First line: #subscription; network dimension; event dimension
For each subscription: 
Geographic region
(network space) Coord1; Coord2; ... CoordD
(event space) Min1, Max1; Min2, Max2

2. Broker files (See outputs/brokers.txt)
First line: network dimension; #brokers
Other lines: Broker id; geographic region; coord1, coord2, ..., coordD

3. Event distribution files (See outputs/eventDistri.txt)
The event space is partitioned into grid cells and we associate every cell
with the probability that an event is generated in that cell. The total
probability (summation over the probabilities of all cells) is 1. There is an
option to not write any cell with probability 0 to the file.
First line: number of cells, event dimension
Other lines: Min1, Max1; Min2, Max2; ... ; MinD, MaxD; probability

Note that these codes are provided without any warranty of any kind. Please feel free to contact me if you have any questions or problems.

============================================================
