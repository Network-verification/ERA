ERA
=====================

##Setup

- Import the JDD library from 
```
https://bitbucket.org/vahidi/jdd/wiki/Home 
````
- Install Batfish from ``https://github.com/arifogel/batfish``
- Import the Karnaugh map implementation into the src folder
- Put the router configuration files into a folder named ``configs`` along with the ``environment`` files.

##Policies
The different policies that ERA can capture and analyse are -
- Basic Reachability
- Way Pointing
- Router Equivalence
- Loop Detection
- Black Hole Detection
- Valley Free 

##Running Commands
- To run the program, we use ``./era.sh <Path to Router Config folder> <Path to Policy file>`` 

##Policy Metadata
For each of the policies, we can have various types of metadata where you can provide specifications for that type of policy. The metadata files can be written in the following manner - 
### Basic Reachability & Black Hole
You can specify source and destination interfaces along with which prefixes you want to check.`
- Format for this would be -
````
<Router Name>:<Source Interface>,<Router Name>:<Destination Interface>|<IP prefix> 
````
Note - If you want to check for all prefixes, then you put in ``*``
- Example - 
````
Interface1,Inteface2|128.16.1.1/29
Interface1,Interface2|*
````

### Way Pointing & Valley Free
We can specify each interface that is involved in the path from source to destination along with the prefixes that need to be checked.
- Format for this would be -
````
<Router1 Name>-<Interface1>,<Router2 Name>-<Interface2>,...|<IP prefix> 
````
###Router Equivalence
We specify the two interfaces of both routers that are considered equivalent and specify the prefixes that need to be compared to prove equivalence.
-Format for this would be -
```
<Router1 name>:<Interface1>,<Interface2>|<Router2 name>:<Interface1>,<Interface2>
```
##Fast Bit vector Intersections (Optional)
 - To use fast bitvector intersections, first run the java file (it will write into a .txt file), after that run the C file (it requires Intel AVX installed)  using the commands -
 ````
 compiler command: gcc -mavx2 -o set_intersect_union set_intersect_union.c
 
 run command: ./set_intersect_union.c
``````

