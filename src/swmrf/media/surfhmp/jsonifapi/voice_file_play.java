package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class voice_file_play {
	private String cmd_type;
	private String req_type;
	private int tool_id;
	private int req_id;
	
	public void CmdType(String cmd_type)
	{
		this.cmd_type = cmd_type;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		data_hash.put(SurfConstants.CMD_TYPE, this.cmd_type);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String file_play_msg()
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
