package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class SurfDisConnect {
	
	private int req_id;
	private int error_code;
	private String req_type;
	private String cmd_type;
	private String reason;
	
	public void req_id(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void error_code(int error_code)
	{
		this.req_id = error_code;
	}
	
	public void req_type(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void reason(String reason)
	{
		this.reason = reason;
	}
	
	public void cmd_type(String cmd_type)
	{
		this.cmd_type = cmd_type;
	}
	
	private JSONObject Data()
    {
		HashMap<String,Object> datahash = new HashMap<String,Object>();
		
		datahash.put(SurfConstants.CMD_TYPE, this.cmd_type);
		
		JSONObject data = new JSONObject(datahash);
    		return data;
    }
	
	public String clear_all_tools()
    {
    		HashMap<String,Object> senddatahash = new HashMap<String,Object>();
    		HashMap<String,Object> sys_reqhash = new HashMap<String,Object>();
		
		sys_reqhash.put(SurfConstants.REQ_ID, new Integer(this.req_id));
		sys_reqhash.put(SurfConstants.REQ_TYPE, this.req_type);
		sys_reqhash.put(SurfConstants.DATA, this.Data());
		JSONObject sys_req = new JSONObject(sys_reqhash);
		
		senddatahash.put(SurfConstants.SYS_REQ, sys_req);
		JSONObject senddata = new JSONObject(senddatahash);
		
    		return senddata.toString();
    }
    
    public String SurfDisConnectMsg()
    {
    		HashMap<String,Object> disconnect_hash = new HashMap<String,Object>();
    		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
    		
		disconnect_hash.put(SurfConstants.ERROR_CODE, new Integer(this.error_code));
		disconnect_hash.put(SurfConstants.REASON, this.reason);
		JSONObject disconnect = new JSONObject(disconnect_hash);
		
		senddata_hash.put(SurfConstants.DISCONNECT, disconnect);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    }
    
}
