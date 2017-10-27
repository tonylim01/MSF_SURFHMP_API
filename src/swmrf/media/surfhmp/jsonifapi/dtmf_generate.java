package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class dtmf_generate {
	private String cmd_type;
	private String req_type;
	private String rtp_or_inband;
	private String named_event;
	private int tool_id;
	private int req_id;
	private int total_duration;
	
	public void CmdType(String cmd_type)
	{
		this.cmd_type = cmd_type;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void RtpOrInband(String rtp_or_inband)
	{
		this.rtp_or_inband = rtp_or_inband;
	}
	
	public void NamedEvent(String named_event)
	{
		this.named_event = named_event;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void TotalDuration(int total_duration)
	{
		this.total_duration = total_duration;
	}
	
	private JSONObject ip_event()
    {
		HashMap<String,Object> ip_event_temp_hash = new HashMap<String,Object>();
		
		ip_event_temp_hash.put(SurfConstants.NAMED_EVENT, this.named_event);
		JSONObject ip_event_temp = new JSONObject(ip_event_temp_hash);
		
		return ip_event_temp;
    }
	
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		data_hash.put(SurfConstants.CMD_TYPE, this.cmd_type);
		data_hash.put(SurfConstants.IP_EVENT, this.ip_event());
		data_hash.put(SurfConstants.RTP_OR_INBAND, this.rtp_or_inband);
		data_hash.put(SurfConstants.TOTAl_DURATION, this.total_duration);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    }
	
    public String dtmf_generate_msg()
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
