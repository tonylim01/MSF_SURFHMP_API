package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SurfConnect {

	public String SurfConnectMsg()
    {
    		HashMap<String,Object> connect_hash = new HashMap<String,Object>();
    		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		
		JSONArray api_version = new JSONArray();
		api_version.add(new Integer(1));
		api_version.add(new Integer(2));
		
		connect_hash.put("keep_alive_timeout", new Integer(0));
		connect_hash.put("api_version", api_version);
		JSONObject connect = new JSONObject(connect_hash);
		
		
		senddata_hash.put("connect", connect);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    		
    }
}
