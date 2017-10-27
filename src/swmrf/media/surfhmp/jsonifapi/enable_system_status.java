package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class enable_system_status {
	
	private String status_type;
	private int status_period;
	private String req_type;
	private int req_id;
	
	public void StatusType(String status_type)
	{
		this.status_type = status_type;
	}
	
	public void StatusPeriod(int status_period)
	{
		this.status_period = status_period;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		HashMap<String,Object> status_temp_hash = new HashMap<String,Object>();
		JSONArray statusarr = new JSONArray();
		
		status_temp_hash.put(SurfConstants.TYPE, this.status_type);
		status_temp_hash.put(SurfConstants.PERIOD, new Integer(this.status_period));
		JSONObject status_temp = new JSONObject(status_temp_hash);
		
		statusarr.add(status_temp);
		
		data_hash.put(SurfConstants.STATUS, statusarr);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String system_status()
    {
		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
		HashMap<String,Object> sys_req_hash = new HashMap<String,Object>();
		
		sys_req_hash.put(SurfConstants.REQ_ID, new Integer(this.req_id));
		sys_req_hash.put(SurfConstants.REQ_TYPE, this.req_type);
		sys_req_hash.put(SurfConstants.DATA, this.Data());
		JSONObject sys_req = new JSONObject(sys_req_hash);
		
		senddata_hash.put(SurfConstants.SYS_REQ, sys_req);
		JSONObject senddata = new JSONObject(senddata_hash);
		
    		return senddata.toString();
    		
    }
}
