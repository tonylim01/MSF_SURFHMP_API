package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class enableSystemStatus {
	
	public JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		HashMap<String,Object> status_temp_hash = new HashMap<String,Object>();
		JSONArray statusarr = new JSONArray();
		
		status_temp_hash.put("type", "all");
		status_temp_hash.put("period", new Integer(0));
		JSONObject status_temp = new JSONObject(status_temp_hash);
		
		statusarr.add(status_temp);
		
		data_hash.put("status", statusarr);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    		
    }
	
    public String system_status()
    {
		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		HashMap<String,Object> sys_req_hash = new HashMap<String,Object>();
		
		sys_req_hash.put("req_id", new Integer(0));
		sys_req_hash.put("req_type", "set_config");
		sys_req_hash.put("data", this.Data());
		JSONObject sys_req = new JSONObject(sys_req_hash);
		
		senddata_hash.put("sys_req", sys_req);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    		
    }
}
