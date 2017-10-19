package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class voice_mixer {
	public String tool_type;
	public String req_type;
	public int req_id;
	public int tool_id;
	public int sampling_rate;
	public int hangover_period;
	public int dominant_speakers;
	
	public JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		data_hash.put("tool_type", tool_type);
		data_hash.put("sampling_rate", sampling_rate);
		data_hash.put("hangover_period", hangover_period);
		data_hash.put("dominant_speakers", dominant_speakers);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String voice_mixer_msg()
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
