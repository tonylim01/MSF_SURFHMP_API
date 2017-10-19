package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class enableSystemStatus {
	
	public String status_type;
	public int status_period;
	public String req_type;
	public int req_id;
	
	public JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		HashMap<String,Object> status_temp_hash = new HashMap<String,Object>();
		JSONArray statusarr = new JSONArray();
		
		status_temp_hash.put("type", status_type);
		status_temp_hash.put("period", new Integer(status_period));
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
		
		sys_req_hash.put("req_id", new Integer(req_id));
		sys_req_hash.put("req_type", req_type);
		sys_req_hash.put("data", this.Data());
		JSONObject sys_req = new JSONObject(sys_req_hash);
		
		senddata_hash.put("sys_req", sys_req);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    		
    }
}
