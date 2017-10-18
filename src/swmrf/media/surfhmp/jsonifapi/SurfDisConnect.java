package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class SurfDisConnect {
	
	public JSONObject Data()
    {
		HashMap<String,Object> datahash = new HashMap<String,Object>();
		
		datahash.put("cmd_type", "clear_all_tools");
		
		JSONObject data = new JSONObject(datahash);
    		return data;
    }
	
    public String clear_all_tools()
    {
    		HashMap<String,Object> senddatahash = new HashMap<String,Object>();
    		HashMap<String,Object> sys_reqhash = new HashMap<String,Object>();
		
		sys_reqhash.put("req_id", new Integer(0));
		sys_reqhash.put("req_type", "command");
		sys_reqhash.put("data", this.Data());
		JSONObject sys_req = new JSONObject(sys_reqhash);
		
		senddatahash.put("sys_req", sys_req);
		JSONObject senddata = new JSONObject(senddatahash);
		
    		return senddata.toString();
    }
    
    public String SurfDisConnectMsg()
    {
    		HashMap<String,Object> disconnect_hash = new HashMap<String,Object>();
    		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
    		
		disconnect_hash.put("error_code", new Integer(0));
		disconnect_hash.put("reason", "finished");
		JSONObject disconnect = new JSONObject(disconnect_hash);
		
		senddata_hash.put("disconnect", disconnect);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    }
    
}
