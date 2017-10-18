package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class create_file_reader {
	public String events_type;
	public String tool_type;
	public String req_type;
	public boolean video_enabled;
	public boolean audio_enabled;
	public int tool_id;
	public int req_id;
	
	public JSONObject Data()
    {
		HashMap<String,Object> events_temp_hash = new HashMap<String,Object>();
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		
		JSONArray audio_dst_tool_ids = new JSONArray();
		JSONArray video_dst_tool_ids = new JSONArray();
		JSONArray events = new JSONArray();
		
		audio_dst_tool_ids.add(new Integer(4));
		
		events_temp_hash.put("type", events_type);
		events_temp_hash.put("enabled", true);
		
		JSONObject events_temp = new JSONObject(events_temp_hash);
		events.add(events_temp);
		
		
		data_hash.put("video_enabled", video_enabled);
		data_hash.put("tool_type", "file_reader");
		data_hash.put("audio_dst_tool_ids", audio_dst_tool_ids);
		data_hash.put("audio_enabled", audio_enabled);
		data_hash.put("video_dst_tool_ids", video_dst_tool_ids);
		data_hash.put("events", events);
		
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String create_file_reader_msg()
    {
		HashMap<String,Object> tool_req_hash = new HashMap<String,Object>();
		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		
		
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
