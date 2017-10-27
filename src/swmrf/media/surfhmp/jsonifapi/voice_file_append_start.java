package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class voice_file_append_start {
	private int duration;
	private int tool_id;
	private int req_id;
	private String req_type;
	private String cmd_type;
	private String file_list[] = new String[2];
	
	public void Duration(int duration)
	{
		this.duration = duration;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void CmdType(String cmd_type)
	{
		this.cmd_type = cmd_type;
	}
	
	public void FileList(int index,String file_list)
	{
		this.file_list[index] = file_list;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject Data()
    {
		HashMap<String,Object> files_temp_hash = new HashMap<String,Object>();
		HashMap<String,Object> files_temp2_hash = new HashMap<String,Object>();
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		JSONArray files = new JSONArray();
		
		files_temp_hash.put(SurfConstants.NAME, this.file_list[0]);
		files_temp_hash.put(SurfConstants.DURATION, new Integer(this.duration));
		JSONObject files_temp = new JSONObject(files_temp_hash);
		files.add(files_temp);
		
		files_temp2_hash.put(SurfConstants.NAME, this.file_list[1]);
		JSONObject files_temp2 = new JSONObject(files_temp2_hash);
		files.add(files_temp2);
		
		data_hash.put(SurfConstants.CMD_TYPE, this.cmd_type);
		data_hash.put(SurfConstants.FILES, files);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String file_append_start_msg()
    {
    		HashMap<String,Object> tool_req_hash = new HashMap<String,Object>();
    		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		
		tool_req_hash.put(SurfConstants.TOOL_ID, this.tool_id);
		tool_req_hash.put(SurfConstants.REQ_ID, this.req_id);
		tool_req_hash.put(SurfConstants.REQ_TYPE, this.req_type);
		tool_req_hash.put(SurfConstants.DATA, this.Data());
		JSONObject tool_req = new JSONObject(tool_req_hash);
		
		senddata_hash.put(SurfConstants.TOOL_REQ, tool_req);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    		
    }
}
