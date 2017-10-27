package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SurfConnect {
	private int	major_version;
	private int	minor_version;
	private int	keep_alive_timeout;
	
	public void SetConnectVersion(int major_version,int minor_version)
	{
		this.major_version = major_version;
		this.minor_version = minor_version;
	}
	
	public void KeepAliveTimeout(int keep_alive_timeout)
	{
		this.keep_alive_timeout = keep_alive_timeout;
	}
	
	@SuppressWarnings("unchecked")
	public String SurfConnectMsg()
    {
    		HashMap<String,Object> connect_hash = new HashMap<String,Object>();
    		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		
		JSONArray api_version = new JSONArray();
		api_version.add(new Integer(this.major_version));
		api_version.add(new Integer(this.minor_version));
		
		connect_hash.put(SurfConstants.KEEP_ALIVE_TIMEOUT, new Integer(this.keep_alive_timeout));
		connect_hash.put(SurfConstants.API_VERSION, api_version);
		JSONObject connect = new JSONObject(connect_hash);
		
		senddata_hash.put(SurfConstants.CONNECT, connect);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    		
    }
}
