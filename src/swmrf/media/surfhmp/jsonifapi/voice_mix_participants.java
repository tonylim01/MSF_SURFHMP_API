package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class voice_mix_participants {
	public String req_type;
	public String data_type;
	public int req_id;
	public int tool_id;
	public int data_tool_id;
	public int id;
	
	
	public JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		HashMap<String,Object> participants_hash = new HashMap<String,Object>();
		JSONArray participantsarr = new JSONArray();
		participants_hash.put("type", data_type);
		participants_hash.put("id", new Integer(id));
		participants_hash.put("data_tool_id", new Integer(data_tool_id));
		JSONObject participants = new JSONObject(participants_hash);
		participantsarr.add(participants);
		data_hash.put("participant", participantsarr);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String voice_mix_participants_msg()
    {
		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		HashMap<String,Object> tool_req_hash = new HashMap<String,Object>();
		
		tool_req_hash.put("tool_id", new Integer(tool_id));
		tool_req_hash.put("req_id", new Integer(req_id));
		tool_req_hash.put("req_type", req_type);
		tool_req_hash.put("data", this.Data());
		JSONObject tool_req = new JSONObject(tool_req_hash);
		
		senddata_hash.put("tool_req", tool_req);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    }
}
