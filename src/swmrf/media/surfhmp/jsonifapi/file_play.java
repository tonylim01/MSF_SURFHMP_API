package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class file_play {
	public String cmd_type;
	public String req_type;
	public int tool_id;
	public int req_id;
	
	public JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		data_hash.put("cmd_type", cmd_type);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String file_play_msg()
    {
		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		HashMap<String,Object> tool_req_hash = new HashMap<String,Object>();
		
		tool_req_hash.put("tool_id", new Integer(1));
		tool_req_hash.put("req_id", new Integer(1002));
		tool_req_hash.put("req_type", "command");
		tool_req_hash.put("data", this.Data());
		JSONObject tool_req = new JSONObject(tool_req_hash);
		
		senddata_hash.put("tool_req", tool_req);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    }
}
