package support;

public class route_pref {
	
	/*=====================
	  In order of preference
	  =====================
	 * AD - 4 bits
	 * Protocol attributesfor bgp - 52 bits (weight - 16 bit, Local_pref - 10 bit, Origin - 2 bit, AS path -18 bit, Origin Type - 2 bit, MED - 4 bit)
	 * We make sure all attributes are of the same length (pad with 0s) so that AD gets highest preference when comparing binary values
	 */
	public static int  adval=0;
	public static int assignAD(String AD)
	{
		
		if(AD.equals("static"))
			adval = 0;
		else if(AD.equals("BGP"))
			adval = 1;
		else if(AD.equals("OSPF"))
			adval = 2;
		else if(AD.equals("RIP"))
			adval = 3;
		else if(AD.equals("IGRP"))
			adval=4;
		return adval;
	}
	public static String bgpAttributes(int w, int local_pref, int og, int AS_p, String Og_t, int MED)
	{
		
		String weight = Integer.toString(w,2);
		String local = Integer.toString(local_pref,2);
		String origin_type;
		if(Og_t.equals("IGP"))
			origin_type = "01";
		else if(Og_t.equals("EGP"))
			origin_type = "10";
		else if(Og_t.equals("Incomplete"))
				origin_type = "11";
		else 
			origin_type = "";	
		String AS_path = Integer.toString(AS_p,2);
		String origin = Integer.toString(og,2); 
		String multi = Integer.toString(MED,2);
		String finalAttributeVector = "";
		finalAttributeVector = weight+local+origin+AS_path+origin+multi;
		
		return finalAttributeVector;
		
	}
	public static String OSPFAttributes(int cost){
		String finalAttributeVector="";
		String ospf_cost = Integer.toString(cost,2);
		finalAttributeVector = ospf_cost;
		return finalAttributeVector;
	}
	public static String RIPAttributes(int hop_count)
	{
		String finalAttributeVector = Integer.toString(hop_count,2);
		return finalAttributeVector;
	}
	public static String IGRPAttributes(int bandwidth,int delay,int reliability,int loading,int mtu)
	{
		String finalAttributeVector = "";
		String bw = Integer.toString(bandwidth,2);
		String d = Integer.toString(delay,2);
		String r = Integer.toString(reliability,2);
		String l = Integer.toString(loading,2);
		String m = Integer.toString(mtu,2);
		finalAttributeVector = bw+d+r+l+m;
		return finalAttributeVector;
		
	}
	

		
}
