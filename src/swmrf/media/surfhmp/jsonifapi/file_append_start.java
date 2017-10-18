package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class file_append_start {
	public int duration;
	public int tool_id;
	public int req_id;
	public String req_type;
	public String cmd_type;
	public String file_list[] = new String[2];
	
	public JSONObject Data()
    {
		HashMap<String,Object> files_temp_hash = new HashMap<String,Object>();
		HashMap<String,Object> files_temp2_hash = new HashMap<String,Object>();
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		JSONArray files = new JSONArray();
		
		files_temp_hash.put("name", file_list[0]);
		files_temp_hash.put("duration", new Integer(duration));
		JSONObject files_temp = new JSONObject(files_temp_hash);
		files.add(files_temp);
		
		
		files_temp2_hash.put("name", file_list[1]);
		JSONObject files_temp2 = new JSONObject(files_temp2_hash);
		files.add(files_temp2);
		
		data_hash.put("cmd_type", cmd_type);
		data_hash.put("files", files);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String file_append_start_msg()
    {
		JSONObject senddata = new JSONObject();
		JSONObject tool_req = new JSONObject();
		
		tool_req.put("tool_id", tool_id);
		tool_req.put("req_id", req_id);
		tool_req.put("req_type", req_type);
		tool_req.put("data", this.Data());
		
		senddata.put("tool_req", tool_req);
		
    		return senddata.toString();
    		
    }
}
