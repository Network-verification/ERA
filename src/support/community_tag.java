package support;

import java.util.HashMap;

public class community_tag {
	public static HashMap<String, Integer> community;
	public static void make_community(String com, int local_val)
	{
		if(community.isEmpty())
			community.put(com, local_val);
		else if(community.get(com) == null)
			community.put(com, local_val);
		 
			
	}
	public int check_community(String com)
	{
		int bgp_local;
		if(community.get(com)!=null)
			bgp_local = community.get(com);
		else 
			return 100;
		return bgp_local;
	}
}
