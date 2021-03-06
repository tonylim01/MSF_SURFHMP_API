package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class voice_mixer {
	private String tool_type;
	private String req_type;
	private int req_id;
	private int tool_id;
	private int sampling_rate;
	private int hangover_period;
	private int dominant_speakers;
	
	public void ToolType(String tool_type)
	{
		this.tool_type = tool_type;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void SamplingRate(int sampling_rate)
	{
		this.sampling_rate = sampling_rate;
	}
	
	public void HangoverPeriod(int hangover_period)
	{
		this.hangover_period = hangover_period;
	}
	
	public void DominantSpeakers(int dominant_speakers)
	{
		this.dominant_speakers = dominant_speakers;
	}
	
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		data_hash.put(SurfConstants.TOOL_TYPE, this.tool_type);
		data_hash.put(SurfConstants.SAMPLING_RATE, this.sampling_rate);
		data_hash.put(SurfConstants.HANGOVER_PERIOD, this.hangover_period);
		data_hash.put(SurfConstants.DOMINANT_SPEAKERS, this.dominant_speakers);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String voice_mixer_msg()
    {
		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		HashMap<String,Object> tool_req_hash = new HashMap<String,Object>();
		
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
