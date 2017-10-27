package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class voice_file_recording {
	private String cmd_type;
	private String req_type;
	private String file_name;
	private int tool_id;
	private int req_id;
	private int max_size;
	
	public void CmdType(String cmd_type)
	{
		this.cmd_type = cmd_type;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	public void FileName(String file_name)
	{
		this.file_name = file_name;
	}
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	public void MaxSize(int max_size)
	{
		this.max_size = max_size;
	}
	
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		data_hash.put(SurfConstants.CMD_TYPE, this.cmd_type);
		data_hash.put(SurfConstants.FILE_NAME, this.file_name);
		data_hash.put(SurfConstants.MAX_SIZE, this.max_size);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String file_record_msg()
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
