

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import jdd.bdd.*;
import jdd.util.*;
import jdd.util.math.*; 
class WordContainsException extends Exception
{
      //Parameterless Constructor
      public WordContainsException() {}

      //Constructor that accepts a message
      public WordContainsException(String message)
      {
         super(message);
      }
 }
class era_analyser extends BDD
	{
		public static int flag_topo = 0;
		public static String[] topoLines=null;
		public static int flag1 = 0;
		public static String string1 ;
		public static String string2;
		private static final int SIZE = 32;
		private static final int I_P[] = new int[SIZE];
		public static final int[] vals=null;
		public int mVal;
		
		public static ArrayList<String> APs = new ArrayList<String>();
		
		
		//private static int bdds[];
		public era_analyser(String s)
		{
			super(10000,1000);
		
			APs.add(s);
		//	System.out.println("SIZE OF APS" + APs.size());
			//implement(s);
			
			
		}
		public era_analyser() {
			super(10000,1000);
		}
		public ArrayList<Integer> makeAtomic(int[] finalbdds)//must relate final bdds with port numbers
		{
			int atomicbdds[] = new int [finalbdds.length];
			int natomicbdds[] = new int[finalbdds.length];
		
			for(int i = 0; i < finalbdds.length;i++)
			{
				
				atomicbdds[i] = finalbdds[i];
				natomicbdds[i] = ref(not(finalbdds[i]));
			}
			int[][] interimABDD = new int[2*finalbdds.length][2]; 
			
			for(int i = 0 ; i < finalbdds.length;i++) 
			{
				for(int j=0; j <2; j++)
				{
					if(j==0 && atomicbdds[i]!= 0)
						interimABDD[i][j]=atomicbdds[i];
					else if(j!=0 && atomicbdds[i]!=0)
						interimABDD[i][j]=natomicbdds[i];
					else
						interimABDD[i][j]=1;
				}
			}
			ArrayList<Integer> atomicPredicates = new ArrayList<Integer>();
			atomicPredicates.add(interimABDD[0][0]);
			atomicPredicates.add(interimABDD[0][1]);
			for(int i=0;i<finalbdds.length;i++)
			{
				for(int j=0;j<2;j++)
				{
					int iterate=0;
					while(iterate<atomicPredicates.size())
					{
						int temp = atomicPredicates.get(iterate);
						temp = ref(and(temp,interimABDD[i][j]));
						if(temp != 0)
							atomicPredicates.add(temp);
						
						iterate++;
					}
				}
			}
			return atomicPredicates;
		}
			
		
		public void reachable(int[] atomicbdds,int [] path)//path represents the unique port numbers that have been touched
		{
			long start_time = System.currentTimeMillis();

			
			int[] relevantBDD = new int[path.length];
			for(int i=0;i < path.length;i++){
				for(int j = 0; j < atomicbdds.length;j++){
					if(j == path[i]){
						relevantBDD[i] = atomicbdds[j]; 
					}	
				}
			}
			int areReachable= relevantBDD[0];
		//	System.out.println("areReachable" + areReachable);
			int noAnds = relevantBDD.length-1;
			for(int i = 1 ; i <=noAnds; i++){
				areReachable = ref(and(areReachable, relevantBDD[i]));
				deref(relevantBDD[i]);
			}
			//print(areReachable);
			
				
			
			long end_time = System.currentTimeMillis();
			long difference = end_time-start_time;
	
			
		}
		public void callImplement()
		{
			ArrayList total = new ArrayList();
			int bdds[] = new int[APs.size()];
		//	System.out.println(APs.size() + "APS SIZE");
		    for(int i =0; i < APs.size();i++)
		    {
		    	bdds[i] = implement(APs.get(i));
		    	if(bdds[i]!=0)
		    	{
		    		total.add(i);
		 	}
		     }
		 
		    
		    
		    int[] path = {21,34};
		    ArrayList <Integer> atomic = new ArrayList<Integer>();
		  //  atomic = makeAtomic(bdds);
		  //  int atomicSize = atomic.size();
		  //  int[] finalAtomic = new int[atomicSize];
		 //   for(int i =0 ; i < atomicSize;i++)
		//    {
		//    	finalAtomic[i]= atomic.get(i);
		//    }
		    		
		    reachable(bdds, path);
		}
		public int implement(String s)
		{
			for(int i = 0 ; i < SIZE; i++)
			{
				I_P[i] = createVar();
			}
			
	//		System.out.println(s+ " String s ");
			s=s.replaceAll("\\s","");
			int countOrs = s.length() - s.replace("+", "").length();
	//		System.out.println(countOrs + "Countors");
			int temp[] = new int[countOrs+1];
			int flag  = 0;
		    if(countOrs == 0)
		    {
		    	flag =1;
		    	countOrs =1;
		    }
			String tempval = "";
			int z=0;
			ArrayList<String> SmallerVals = new ArrayList<String>(); 
			for(int j = 0 ; j < countOrs; j++)
			{
				tempval = "" ;
				while(s.charAt(z)!= '+'  )
				{
					tempval = tempval + s.charAt(z);
				//	System.out.println(tempval + "This is tempval");
					if(z == s.length()-1)
						break;
					z++;
					
				}
				
				z++;
			//	System.out.println(tempval + "this is tempval out");
				SmallerVals.add(tempval);
			}
			int nots;
			tempval = ""; 
			
			for(int i = 0; i < SmallerVals.size(); i++) 
			{
				String val = SmallerVals.get(i);
			//	System.out.println((val.charAt(0)-97) +"this is val at 0 - 97" );
				if(Character.isDigit(val.charAt(0)))
				{
					temp[i]=0;
					break;
				}
				temp[i]=I_P[val.charAt(0)-97];
				
			//	System.out.println(I_P[0] + "this is temp["+i+ "]");
			    for(int j = 0 ; j < val.length() ; j++) { 
			    	//System.out.println("value of j = " + j + "val.length()" + val.length());
			        if(j == val.length()-1 && val.charAt(j)!='\'') {
			            temp[i] = ref(and(temp[i],I_P[val.charAt(j)-97]));
			            break;
			        }
			        else if(j == val.length()-2 && flag == 1)
			        {
			        	temp[i] = ref(not(temp[i]));
			        //	System.out.println(temp[i] + "result supposed");
			        	break;
			        }
			        else if(j==val.length()-1)
			        {
			        	temp[i] = ref(and(temp[i], not(I_P[val.charAt(j-1)-97])));
			        	//System.out.println(temp[i]+ "result supposed");
		//	        	print(temp[i]);
			        	break;
			        }

			        else if( Character.isLetter(val.charAt(j))&& val.charAt(j+1) != '\'' ) {
			        	temp[i] = ref(and(temp[i],I_P[((int)SmallerVals.get(i).charAt(j))-97]));
			        } 
			        else if((val.charAt(j)=='{'||val.charAt(j)=='|')&&val.charAt(j+1)!='\'')
			        {
			        	if((val.charAt(j)=='{'))
			        		temp[i]=ref(and(temp[i],I_P[27]));
			        	else
			        		temp[i]=ref(and(temp[i],I_P[28]));
			        }
			        else if((val.charAt(j)!='{'||val.charAt(j)!='|')&&val.charAt(j+1)=='\'') {
			        
			        	nots = ref(not(I_P[SmallerVals.get(i).charAt(j)-97]));
						temp[i] = ref(and(temp[i],nots));
						deref(nots);
			            j++;
			       }
			        else
			        {
			        	if((val.charAt(j-1)=='{'))
			        		temp[i]=ref(and(temp[i],I_P[27]));
			        	else
			        		temp[i]=ref(and(temp[i],I_P[28]));
			        	j++;
			        }
			        
			    }
			}
			int finalbdd= temp[0];
		//	System.out.println(temp.length + "temp length in ");
			for(int i = 1 ; i <=countOrs; i++)
			{
				if(temp.length == 1)
					break;
				finalbdd = ref(or(finalbdd, temp[i]));
				deref(temp[i]);
					
			}
		//
		//	System.out.println("final bdd");
		//print(finalbdd);
		return finalbdd;
			
		}
	   	   public static String arrangeBits(String bits, String result,String cost)
	   	   {
	   		   String route;
	   		   route = bits;
	   		   if(result == "Static")
	   			   route = route + "0";
	   		   else if(result == "BGP")
	   			   route = route+"1";
	   		   else if(result == "OSPF")
	   			   route = route+ "2";
	   		   else if(result == "RIP")
	   			   route = route+"3";
	   		   else if(result=="EIGRP")
	   			   route = route+"4";
	   		   route = route+cost;
	   		   return route;
	   	   }
		public static era_analyser modification(List<String> listOfString)
		{
		    int temp1[] = new int[listOfString.size()];
		    String temp = "" ;

			 for(int i = 0; i < listOfString.size(); i++)
			    {
			    	String vals = listOfString.get(i);
			    	
			    	if((vals).charAt((vals).length() -1)=='2')
			    	{	      
			   
			    		temp = vals.substring(32,vals.length()-3);
			    		
			    		temp1[i] = Integer.parseInt(temp,2);
			   
			    	}
			    	temp ="";
			    }
			    MinimizedTable M = new MinimizedTable(temp1);
		//	    System.out.println("Minimized: " + M.toString());
			    era_analyser ap = new era_analyser(M.toString());
			    return ap;
			 
		}
		public void routeRedistribute(int[] mapArray, Map ports,int sizeOfFile,ArrayList<String>[] listOfString)// new proto is just n bits, old is the entire thing
		{
		
			int x=0;
	        Iterator it = ports.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry pair = (Map.Entry)it.next();
	            mapArray[x] = (int) pair.getValue();
	           System.out.println(pair.getKey() + " = " + pair.getValue());
	            x++;
	            it.remove(); // avoids a ConcurrentModificationException
	        }
		
			    System.out.println(mapArray.length);
		    ArrayList<Integer> temp1[] = new ArrayList[mapArray.length];
		   // System.out.println(ports.size());
		    
		    String temp = "" ;
		    String res1[] = new String[2];
		    //System.out.println(mapArray.length);
		    for(int i = 0 ; i < mapArray.length; i++)
		    {
		    	System.out.println(mapArray[i]+ "MAP ARRAY VALS");
		    }
		   System.out.println("Size of maparray"+ mapArray.length);
		  // System.out.println(listOfString.length + "redist");
		   int count=0;
		   for(int k = 0 ; k < listOfString.length;k++)
		   {
			   if(listOfString[k]==null)
				   continue;
			   else
				   count++;
		   }
		    for(int k = 0; k < count;k++)
		    {
		    	
		    	for(int j = 0 ; j < mapArray.length;j++)
		    	{	
		    	//	temp1[j]= new ArrayList<Integer>();
		    		System.out.println("outside i" + j);
		    		for(int i = 0; i < listOfString[k].size(); i++)
		    		{
		    			System.out.println("inside i" + i);
		        		res1 = listOfString[k].get(i).split("\\s+");

		    			String vals = res1[1];
		    			System.out.println(vals + " vals" );
		    			
		    			if((vals).endsWith(Integer.toString(mapArray[j])))
		    			{	      
		    				System.out.println("vals inside" + vals);
		    				temp = vals.substring(0,24);
		    				System.out.println(Integer.parseInt(temp,2) + "PARSED");
		    				
				    		temp1[j]= new ArrayList<Integer>();

		    				temp1[j].add(Integer.parseInt(temp,2));
		    				System.out.println(temp1[j].size() + "SIZE TEST of" + j);
		    			//	System.out.println(mapArray[j]);
		    		                         
		    			}
		    			//temp ="";
		    				
		    		}
		    		
		    	}
		  	
		    }
		    
		 
		    
		   MinimizedTable M[]=new MinimizedTable[mapArray.length];
		 
		    for(int i = 0 ; i < temp1.length;i++)
		    {
		    	if(temp1[i] == null)
		    		temp1[i]= new ArrayList<Integer>();
		    	int[][] finalvals = new int[temp1.length][temp1[i].size()];
		    	for(int j = 0; j < temp1[i].size();j++)
		    	{
		    		finalvals[i][j]=temp1[i].get(j);
		    	//	System.out.println(temp1[i].get(j)+ "temp1[].get[]");
		    	}
		    
		    
			
		    
		  
	 M[i] = new MinimizedTable(finalvals[i]);
	 
		   
		    }
		    era_analyser ap[] = new era_analyser[temp1.length];
		    for(int i =0;i<temp1.length;i++)
		    {
		    	ap[i] = new era_analyser(M[i].toString());
		    }

		}
		public static era_analyser aggregation(List<String> listOfString, String aggregate) // make sure aggregate takes into consideration the prefix
		{
		    int temp1[] = new int[listOfString.size()];
		    String temp = "" ;

			 for(int i = 0; i < listOfString.size(); i++)
			    {
			    	String vals = listOfString.get(i);
			    	
			    	if((vals).charAt((vals).length() -1)=='2')
			    	{	      
			   // 		System.out.println((vals).charAt((vals).length() -1));
			    		temp = vals.substring(32,vals.length()-3);
			    		
			    		temp1[i] = Integer.parseInt(temp,2);
			    	//	System.out.println(temp1[i]);
			    	}
			    	temp ="";
			    }
			    MinimizedTable M = new MinimizedTable(temp1);
		//	    System.out.println("Minimized: " + M.toString());
			    era_analyser ap = new era_analyser(M.toString());
			    return ap;
			 
		}
		public static String ipToBin(String ip)
		{
		    StringBuilder bStringBuilder = new StringBuilder();
	        String ipParts[] = ip.split("\\.");

	        for (String ipPart : ipParts) {

	            String binString = Integer.toBinaryString(Integer.parseInt(ipPart));
	            int length = 8 - binString.length();
	            char[] padArray = new char[length];
	            Arrays.fill(padArray, '0');
	            bStringBuilder.append(padArray).append(binString);}
	            return bStringBuilder.toString();
	     }
		@SuppressWarnings("unchecked")
		public static void topology(String begin, String end,String topo1) throws IOException
		{
			 
			BufferedReader br = new BufferedReader(new FileReader(topo1));
			
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
			br.close();
			
			ArrayList<String> containstopo = new ArrayList<String>();
			topoLines = everything.split(System.getProperty("line.separator"));
			//System.out.println(topoLines[1] + "topoLines[1]");
			HashMap<String, ArrayList> topo = new HashMap<String, ArrayList>();
	        String[] result = new String[2] ;
	        ArrayList<String> keys = new ArrayList<String>();
	       //ArrayList<String> map = new ArrayList<String>();
			for(int i = 0 ; i < topoLines.length; i++)
			{
				result = topoLines[i].split("\\s+");
				//System.out.println(result[0]);
				if(topo.isEmpty())
				{
					ArrayList<String> map = new ArrayList<String>();
					map.add(result[1]);
					topo.put(result[0], map);
					keys.add(result[0]);
				}
				else if(topo.containsKey(result[0]))
				{
					containstopo = topo.get(result[0]);
					containstopo.add(result[1]);
					topo.put(result[0],containstopo);
					keys.add(result[0]);
				}
				else
				{
					ArrayList<String> map = new ArrayList<String>();
					map.add(result[1]);
					topo.put(result[0], map);
					keys.add(result[0]);
				}
			}
			
		    ArrayList<String> map = new ArrayList<String>();
		   
		
		 map = topo.get(begin);
		 
		    for(int i =0; i < map.size();i++)
		    {
		    	if(end.equals(map.get(i)))
		    	{
		    		break;
		    	}
		    	else
		    	{
		    		checkMap(topo, map.get(i),end);
		    	}
		    }
		}
		public static void checkMap(HashMap<String, ArrayList> topo, String key, String end)
		{
			int flag = 0;
			
			ArrayList<String> map = new ArrayList<String>();
			map =  topo.get(key);
			for(int i =0 ; i < map.size();i++){
				if(end.equals(map.get(i)))
				{
					
				//	System.out.println("Topology is reachable");
					flag_topo= 1;
					break;
				}
				else
					checkMap(topo,map.get(i),end);
			}
		if(flag_topo ==0)
		{
			System.out.println("Not reachable through topology");
			System.exit(1);
		}
			return;
		}
			
		
		@SuppressWarnings("null")
		
		public static void main(String[] Args) throws UnknownHostException, IOException
		{
			


			long c1 = System.currentTimeMillis();
		
			Map<String,Integer> ports = new HashMap<String, Integer>();
			int portVal = 0;
		 //reading in the routers
		    int lineNo=0;
		    String[] wrapperL;
		    String Args3;
		    String Args2;
			BufferedReader br = new BufferedReader(new FileReader(Args[1]));
			try {
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();

			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			   }
			    String everything = sb.toString();
				 wrapperL = everything.split(System.getProperty("line.separator"));
			} finally {
			    br.close();
			}

			String configs=Args[0];
			String topo="";
			ArrayList<String> policy_id = new ArrayList<String>();
			ArrayList<String> policy_type = new ArrayList<String>();
			ArrayList<String> metadata = new ArrayList<String>();
			String[] policy = new String[2];
			for(int x = 0; x < wrapperL.length;x++)
			{
        		 policy= wrapperL[x].split("\\:");
        		//System.out.println(policy[0]);
        		 
        		 
        		 if(policy[0].equals("topology"))
        			 topo = policy[1];
        		 else if(policy[0].equals("policy_id"))
        			 policy_id.add(policy[1]);
        		 else if(policy[0].equals("policy_type"))
        			 policy_type.add(policy[1]);
        		 else if(policy[0].equals("policy_metadata"))
        			 metadata.add(policy[1]);
        		 else 
        			 continue;
			}
			 
		
			
		String direct;
        	for(int w =0 ; w < policy_id.size();w++)
			{
			      

	
					Args2 = "./demo/" +policy_type.get(w)+"_configs/";
				int sizeOfFile = new File(Args2).listFiles().length;
					Args3 = metadata.get(w);
					
		   File[] len;
			configs = Args2;
                        len= new File(configs).listFiles();
                        if(len ==  null)
                        {
                                System.out.println("Error: Configuration files missing.");
                                System.exit(1);
                        }


 String[][] lines = new String[sizeOfFile][];
                    ArrayList<String> listOfString[] = new ArrayList[sizeOfFile];


		//	System.out.println("This is metadata " + Args3);	
			
		 direct = policy_type.get(w);
			//System.out.println("wrapperL" + policy_type.get(w));
		    String target_dir = configs;
	        File dir = new File(target_dir);
	        File[] files = dir.listFiles();
	        String everything = "";
	        if(files == null)
	        {
	        	System.out.println("Error:configuration path incorrect");
	        	System.exit(1);
	        }
	        for(File f : files) {
	            if(f.isFile()) {
	                BufferedReader inputStream = null;
	                inputStream = new BufferedReader(
	                                    new FileReader(f));
	                StringBuilder sb = new StringBuilder();
	        		String line1 = inputStream.readLine();
	                while ((line1 ) != null) {
	                    	sb.append(line1);
	        		        sb.append(System.lineSeparator());
	        		        line1 = inputStream.readLine();
	                    
	                     everything = sb.toString();
	                     //System.out.println(everything + "everything");
	                }
	                
	                    if (inputStream != null) {
	                        inputStream.close();
	                    }
	                //System.out.println("everything" + everything);
	                if( (everything.split(System.getProperty("line.separator")))== null )  
	                	System.exit(1);
	             
	       // System.out.println(everything);
	        
	                	lines[lineNo] = everything.split(System.getProperty("line.separator"));
	                   
	        
	                	
	    			lineNo++;
	            }
	        }
	        System.out.println("");
	        lineNo = 0;
	      //  System.out.println("direct = "+ direct );
	        if(direct.equals("router_equivalence"))
	        	{ flag1 = 1; //System.out.println("im in");
	        	
	        	}
	        else if(direct.equals("way_pointing")||direct.equals("reachability"))
	        	flag1=3;
		else if(direct.equals("valley_free"))
			flag1=4;
		String[][] result = new String[sizeOfFile][];
	    for(int j=0;j<sizeOfFile;j++)
		{
	        listOfString[j]=new ArrayList<String>();
	        if(lines[j] == null)
	        {
	        	System.out.println("Error: configs folder not valid");
	        	System.exit(1);
	        }
	        for(int i = 0; i < lines[j].length;i++)
	        {
	        	result[j] = lines[j][i].split("\\|");
    		
	        	if((result[j][1].compareTo("drop")==0) || (result[j][1].compareTo("receive")==0) || (result[j][1].compareTo("attached")==0)||result[j].length==2)
	        			continue;
	        	else if(ports.get(result[j][0]+result[j][1]) == null)
	        	{
	        			ports.put(result[j][0]+result[j][1], portVal);
	        			portVal++;
	        	}
	        }
		}
	        @SuppressWarnings("unchecked")
			ArrayList<String>[] redist = new ArrayList[sizeOfFile];
	        ArrayList<Integer> Total  = new ArrayList<Integer>();
	        int value = 0; int flag=0;
	        for(int j=0;j<sizeOfFile;j++)
			{
	        	listOfString[j]=new ArrayList<String>();
	        	for(int i = 0; i < lines[j].length;i++)
	        	{
	        		result[j] = lines[j][i].split("\\|");
	        		if((result[j][0].compareTo("")!=0)&&(result[j][1].compareTo("drop")!=0) && (result[j][1].compareTo("receive")!= 0) && (result[j][1].compareTo("attached")!=0)&&result[j].length!=2)
	        		{
	        			for (Map.Entry<String, Integer> entry : ports.entrySet())
	        			{
	        				
	        				if((result[j][0]+result[j][1]).equals(entry.getKey()))
	        					{
	        						
	        						value = entry.getValue();
	        					
	        						for(int n=0;n<Total.size();n++)
	        						{
	        							if(Total.get(n).equals(value))
	        								flag = 1;
	        						}
	        						if(flag == 0)
	        							Total.add(value);
	        						flag = 0;
	        						if((result[j].length==5)&&result[j][4].equals("redistribute"))
	        						{
        		     					redist[j] = new ArrayList<String>();
	             						redist[j].add(String.valueOf(Long.toBinaryString(Long.parseLong(result[j][2])))+" "+value);
        								listOfString[j].add(String.valueOf(Long.toBinaryString(Long.parseLong(result[j][2])))+" "+value);	
	        						}
	        						if(listOfString[j].isEmpty())
	        						{
	         							listOfString[j].add(String.valueOf(Long.toBinaryString(Long.parseLong(result[j][2])))+" " +value);
	        						}
	        						
	        						else
	        						{
	        							listOfString[j].add(String.valueOf(Long.toBinaryString(Long.parseLong(result[j][2])))+" " +value);
	        						}
	        						
	        					}
	        			}
	        			
	        			
	        		
	        	}}
			}
	        
			int [] mapArray = new int[ports.size()];
			int x=0;
	        Iterator it = ports.entrySet().iterator();
	        while (it.hasNext()) {
	            Map.Entry pair = (Map.Entry)it.next();
	            mapArray[x] = (int) pair.getValue();
	     //     System.out.println(pair.getKey() + " = " + pair.getValue());
	            x++;
	            it.remove(); 
	        }
		
			   
		    ArrayList<Integer> temp1[] = new ArrayList[mapArray.length];
		   
		    
		    String temp = "" ;
		    String res1[] = new String[2];
		   
		    for(int k = 0; k < sizeOfFile;k++)
		    {
		    	
		    	for(int j = 0 ; j < mapArray.length;j++)
		    	{	
	
		    		for(int i = 0; i < listOfString[k].size(); i++)
		    		{
		    	//		System.out.println("inside i" + i);
		        		res1 = listOfString[k].get(i).split("\\s+");

		    			
		    			//System.out.println(res1[0]+ " vals" );
		    			String vals = res1[0];

		    			
		    			
		    			if((vals).endsWith(Integer.toString(mapArray[j])))
		    			{	      
		    			
		    					temp = vals.substring(0,24);
				    		temp1[j]= new ArrayList<Integer>();

		    				temp1[j].add(Integer.parseInt(temp,2));
		    				//System.out.println(temp1[j].size() + "SIZE TEST of" + j);
		    			//	System.out.println(mapArray[j]);
		    		                         
		    			}
		    			//temp ="";
		    				
		    		}
		    		
		    	}
		    	
		    }
		    
		    //System.out.println(temp1[0].size() + "Size outside loop");
		    
		    
		   
		   MinimizedTable M[]=new MinimizedTable[mapArray.length];
	//	    System.out.println(temp1[0].size() + "Temp length");
		    for(int i = 0 ; i < temp1.length;i++)
		    {
		    	//System.out.println(temp1[i] +  "Temp1.leen");
		    }
		    for(int i = 0 ; i < temp1.length;i++)
		    {
		    	if(temp1[i] == null)
		    		temp1[i]= new ArrayList<Integer>();
		    	int[][] finalvals = new int[temp1.length][temp1[i].size()];
		    	for(int j = 0; j < temp1[i].size();j++)
		    	{
		    		finalvals[i][j]=temp1[i].get(j);
		    		
		    	}
	        M[i] = new MinimizedTable(finalvals[i]);
	 
		   
		    }
		    era_analyser ap1 = new era_analyser();
		    era_analyser ap[] = new era_analyser[temp1.length];
		   for(int i =0;i<temp1.length;i++)
		    {
		    	ap[i] = new era_analyser(M[i].toString());
		    }
		    for(int j = 0 ; j < sizeOfFile;j++)
		    {
		//    	if(redist[j]==null)
		//   		continue;
		//    	else
		    		
	//	    		ap1.routeRedistribute(mapArray, ports, sizeOfFile, redist);
		   }
		    int lineNo1=0;
		    //String e = "TOO MANY ARGS";
		    
	//		int sizeOfFile1 = new File(Args3).listFiles().length;

					String[][] lines1 = new String[1][];
					
					//  String direct = "way_pointing_configs";
					//	System.out.println(sizeOfFile1 + "sizeOfFile1");
				String target_dir1 = Args3;
				//System.out.println("ARgs3"+ Args3);
			        File dir1 = new File(target_dir1);
			        String Arg5 = "" ;
			        //System.out.println("Args[3]"+ Args[3]);
			      //  if(Args[3].equals("-v"))
			        	 Arg5 = Args[2];
			      // System.out.println("ARGS5"+ Arg5);
			        File files1 = dir1;
			        String everything1 = "";
			       // for (File f1 : files1) {
			            if(files1.isFile()) {
			                BufferedReader inputStream = null;

			                
			                    inputStream = new BufferedReader(
			                                    new FileReader(files1));
			                    
			                    StringBuilder sb = new StringBuilder();
			        		    String line1 = inputStream.readLine();
			                    while ((line1 ) != null) {
			                    	sb.append(line1);
			        		        sb.append(System.lineSeparator());
			        		        line1 = inputStream.readLine();
			                    
			                     everything1 = sb.toString();
			                   //  System.out.println(everything1 + "everything1");
			                }
			                
			                    if(inputStream != null) {
			                        inputStream.close();
			                    }
			                if(lineNo1==1)
			                {
			                	System.out.println("Error: Metadata folder incorrect");
			                	System.exit(1);
			                }
			    			lines1[lineNo1] = everything1.split(System.getProperty("line.separator"));
			    			//System.out.println(lines1[lineNo1] + "lines1");
			  			lineNo1++;
			         
			            }
			       // }
			       //     System.out.println(lines1[0][1] + "lines1");
			            if(policy_type.get(w).equals("basic_reachability"))
			            {
			            	for(int k = 0; k < lines1[0].length; k++)
			            	{
			            		//	System.out.println(lineNo1 + "line!");
			            		String[] path = new String[2];
			            		path=lines1[0][k].split("\\|");
			            		//System.out.println("I'm called");
			            		String src="";
			            		String dst="";
			            		src = path[0].substring(0, path[0].indexOf(",", 0));
			            		dst = path[0].substring(path[0].lastIndexOf(",") + 1, path[0].length());
			            		topology(src,dst,topo);
			            		
			            	}
			            
			            }
			   //     System.out.println(Args[2] + "Args[2]");
	int trigger = 0;
	int t1 = 0;
	ArrayList<String> nonIncluded = new ArrayList<String>();
	String result1[][] = new String[1][];
		if((flag1==3||flag1==4) && Arg5.equals("-v"))	    
	{	for(int j=0;j<sizeOfFile;j++)
		 			{
		 	        	for(int i = 0; i < lines[j].length;i++)
		 	        	{
		 	        		for(int k = 0; k < lines1[0].length;k++)
		 	        	{//System.out.println(lines1[0].length + "there");
						try{result1[0] = lines1[0][k].split("\\|");
						result[j] = lines[j][i].split("\\|");
		 	        	long ip = Long.parseLong(result[j][2]);
		 	        	String ipStr = 
		 	        				  String.format("%d.%d.%d.%d",
		 	        				         (ip & 0xff),   
		 	        				         (ip >> 8 & 0xff),             
		 	        				         (ip >> 16 & 0xff),    
		 	        				         (ip >> 24 & 0xff));
						if(result1[0][1].equals(ipStr+"/"+result[j][3]))
						{
		 	        			trigger = 1;
		 	        			t1=1;
						}
		 	        	if(result1[0].length!=2)
		 	        		throw new WordContainsException();}
					catch(WordContainsException e)
					{
						System.out.println("FORMAT ERROR");
					}
					
					
		 	        	}
		 	        	
		 	        	
		 	        	
		 	        		
								
		 			}    
	if(t1 == 0)
    	{
     		//System.out.println("inside");
    		if(nonIncluded.isEmpty())
    			nonIncluded.add(result1[0][0]+"|"+result1[0][1]);

    		int t = 0;
    		for(int z = 0 ; z < nonIncluded.size();z++)
    		{
    			if((result1[0][0]+"|"+result1[0][1]).equals(nonIncluded.get(z))){
    				t=1;}}
    		if(t==0)
    			nonIncluded.add(result1[0][0]+"|"+result1[0][1]);
    	
    	}
     	t1=0;	
	
     	if(trigger ==0)
     		
 			flag1=6;
	}	}
	else if(flag1==1 && Arg5.equals("-v"))
	{
		for(int j = 0; j < sizeOfFile;j++)
		{
			if(j%2 == 0)
				System.out.println("The route prefixes that are part of core0 but not core255 are:");
			else
				{
					System.out.println("");
					System.out.println("The route prefixes that are part of core255 but not core0 are:");
				}
			for(int i = 0 ; i < lines[j].length;i++)
			{

				result[j] = lines[j][i].split("\\|");
				long ip = Long.parseLong(result[j][2]);
				String ipStr = 	String.format("%d.%d.%d.%d",(ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 &0xff), (ip >> 24 & 0xff));
				System.out.println(ipStr+"/"+result[j][3] );
			}
		}}
	else if(Arg5.equals("-v") && !policy_type.get(w).equals("loops"))
	{
		System.out.println("Prefixes that survive are: ");
	for(int j = 0 ; j < sizeOfFile;j++)
		{
			for(int i = 1 ; i < 4;i++)
			{
			
			 	result[j] = lines[j][i].split("\\|");
				long ip = Long.parseLong(result[j][2]);
				String ipStr = 	String.format("%d.%d.%d.%d",(ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 &0xff), (ip >> 24 & 0xff));
				System.out.println(ipStr+"/"+result[j][3]);
				
			}
		}
		
	}
	    if(policy_type.get(w).equals("loops"))
	    {
	    	System.out.println("======================");
	    	System.out.println("Report:");
	    	System.out.println("Policy = " + policy_type.get(w));
	    	System.out.println("Result = Loops detected ");
	    	if(Arg5.equals("-v"))
			{

			}
	    	
	    	System.out.println("======================");

	    	
	    }
		   ap1.callImplement();
		    long c2 = System.currentTimeMillis();
		    System.out.println("time to run ERA"
		    		+ ": " + (c2-c1) +  " ms");
		 //   for(int i =0; i < vals.length;i++)
		//    {
		    	//System.out.println(i+ " = "+ vals[i] );
		//    }

		    if(!policy_type.get(w).equals("loops"))
		    {//  System.out.println(sizeOfFile);
		    	
	    	System.out.println("======================");
	    	System.out.println("Report:");
	    	System.out.println("Policy = " + policy_type.get(w));
		    if(!nonIncluded.isEmpty())
			{
		    	System.out.println("Result = Violations detected ");
				System.out.println("The violations are: ");
				
				for(int i = 0; i < nonIncluded.size();i++)
				{
					String[] output = new String[2];
					output = nonIncluded.get(i).split(":");
					String[] output1 = new String[2];
					output1 = output[1].split("\\|");
					System.out.println("Interfaces:" + output1[0] + " with prefix:" + output1[1] );
				}
			}	
		    
	

		    System.out.println("======================");
		
		    }
	}
		}			
}
