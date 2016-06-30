ERA
=====================

##Setup

- Import the JDD library from 
```
https://bitbucket.org/vahidi/jdd/wiki/Home 
````
- Import the Karnaugh map implementation into the source folder

- Use temp.java with changes to 'path', 'topology' and 'configs'.

Explanation: Path - This denotes the different "unique" port numbers assigned to each of the output ports of the routers that come in the path "A->B"

Topology - This is the overall topology of the network which includes all links

configs - First, we parse the configs using Batfish https://github.com/arifogel/batfish
and the output of that is put into our ./src/ folder.


 - Run the java code using normal java compile/run techniques (javac, java) 

 - The output would be the answer to the query "can A talk to B in this path" -- if yes, BDD would give you the values that make it through, else it would be null.
 
 - To use fast bitvector intersections, first run the java file (it will write into a .txt file), after that run the C file (it requires Intel AVX installed)  using the commands -
 ````
 compiler command: gcc -mavx2 -o set_intersect_union set_intersect_union.c
 
 run command: ./set_intersect_union.c
``````

