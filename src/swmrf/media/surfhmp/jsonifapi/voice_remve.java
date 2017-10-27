package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class voice_remve {
	private int req_id;
	private int tool_id;
	private String req_type;
	
	public void req_id(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void tool_id(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void req_type(String req_type)
	{
		this.req_type = req_type;
	}
	
    public String SurfRemoveMsg()
    {
    		HashMap<String,Object> remove_hash = new HashMap<String,Object>();
    		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
    		
    		remove_hash.put(SurfConstants.TOOL_ID, new Integer(this.tool_id));
    		remove_hash.put(SurfConstants.REQ_ID, this.req_id);
    		remove_hash.put(SurfConstants.REQ_TYPE, this.req_type);
		JSONObject remove = new JSONObject(remove_hash);
		
		senddata_hash.put(SurfConstants.TOOL_REQ, remove);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    }
}
