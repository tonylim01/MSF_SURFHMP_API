package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class voice_mix_participants {
	private String req_type;
	private String data_type;
	private int req_id;
	private int tool_id;
	private int data_tool_id;
	private int id;
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void DataType(String data_type)
	{
		this.data_type = data_type;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void DataToolId(int data_tool_id)
	{
		this.data_tool_id = data_tool_id;
	}
	
	public void ID(int id)
	{
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		HashMap<String,Object> participants_hash = new HashMap<String,Object>();
		JSONArray participantsarr = new JSONArray();
		participants_hash.put(SurfConstants.TYPE, this.data_type);
		participants_hash.put(SurfConstants.ID, new Integer(this.id));
		participants_hash.put(SurfConstants.DATA_TOOL_ID, new Integer(this.data_tool_id));
		JSONObject participants = new JSONObject(participants_hash);
		participantsarr.add(participants);
		data_hash.put(SurfConstants.PARTICIPANT, participantsarr);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String voice_mix_participants_msg()
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
