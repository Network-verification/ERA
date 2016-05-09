��# ERA

*****************************************************************************************************************************
CAN BE CHANGED WITHOUT MENTION
*****************************************************************************************************************************
Steps on running-

1- Import the JDD library from https://bitbucket.org/vahidi/jdd/wiki/Home **Modification functions will be added

2- Import the Karnaugh map implementation into the source folder

3- Use temp.java with changes to 'path', 'topology' and 'configs'.

Explanation: Path - This denotes the different "unique" port numbers assigned to each of the output ports of the routers that come in the path "A->B"

Topology - This is the overall topology of the network which includes all links

configs - First, we parse the configs using Batfish https://github.com/arifogel/batfish
and the output of that is put into our ./src/ folder.

 4- In main(), insert inputs to the "routerA" and "routerB" that need to be checked in the topology() function call.

 5- Run the java code using normal java compile/run techniques (javac, java) 

 6- The output would be the answer to the query "can A talk to B in this path" -- if yes, BDD would give you the values that make it through, else it would be null.


