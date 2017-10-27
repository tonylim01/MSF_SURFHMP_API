package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class create_file_reader {
	private String events_type;
	private String tool_type;
	private String req_type;
	private boolean video_enabled;
	private boolean audio_enabled;
	private int tool_id;
	private int req_id;
	private int audiodsttoolids;
	
	public void EventsType(String events_type)
	{
		this.events_type = events_type;
	}
	
	public void ToolType(String tool_type)
	{
		this.tool_type = tool_type;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void VideoEnabled(boolean video_enabled)
	{
		this.video_enabled = video_enabled;
	}
	
	public void AudioEnabled(boolean audio_enabled)
	{
		this.audio_enabled = audio_enabled;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void AudioDTSToolIds(int audiodsttoolids)
	{
		this.audiodsttoolids = audiodsttoolids;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject Data()
    {
		HashMap<String,Object> events_temp_hash = new HashMap<String,Object>();
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		
		JSONArray audio_dst_tool_ids = new JSONArray();
		JSONArray video_dst_tool_ids = new JSONArray();
		JSONArray events = new JSONArray();
		
		audio_dst_tool_ids.add(new Integer(this.audiodsttoolids));
		
		events_temp_hash.put(SurfConstants.TYPE, this.events_type);
		events_temp_hash.put(SurfConstants.ENABLED, true);
		
		JSONObject events_temp = new JSONObject(events_temp_hash);
		events.add(events_temp);
		
		data_hash.put(SurfConstants.VIDEO_ENABLED, this.video_enabled);
		data_hash.put(SurfConstants.TOOL_TYPE, this.tool_type);
		data_hash.put(SurfConstants.AUDIO_DST_TOOL_IDS, audio_dst_tool_ids);
		data_hash.put(SurfConstants.AUDIO_ENABLED, this.audio_enabled);
		data_hash.put(SurfConstants.VIDEO_DST_TOOL_IDS, video_dst_tool_ids);
		data_hash.put(SurfConstants.EVENTS, events);
		
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String create_file_reader_msg()
    {
		HashMap<String,Object> tool_req_hash = new HashMap<String,Object>();
		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		
		tool_req_hash.put(SurfConstants.TOOL_ID, new Integer(this.tool_id));
		tool_req_hash.put(SurfConstants.REQ_ID, new Integer(this.req_id));
		tool_req_hash.put(SurfConstants.REQ_TYPE, this.req_type);
		tool_req_hash.put(SurfConstants.DATA, this.Data());
		JSONObject tool_req = new JSONObject(tool_req_hash);
		
		senddata_hash.put(SurfConstants.TOOL_REQ, tool_req);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    		
    }
}
