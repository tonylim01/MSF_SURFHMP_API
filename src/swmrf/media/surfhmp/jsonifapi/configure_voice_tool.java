package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class configure_voice_tool {

	private String status_type;
	private String events_type;
	private String decoder_type;
	private String encoder_type;
	private String remote_ip;
	private String tool_type;
	private String req_type;
	private String app_info;
	private int period;
	private int packet_duration;
	private int local_udp_port;
	private int remote_udp_port;
	private int out_payload_type;
	private int group_id;
	private int tool_id;
	private int req_id;
	private boolean input_from_RTP;
	private boolean enabled;
	
	public void StatusType(String status_type)
	{
		this.status_type = status_type;
	}
	
	public void EventsType(String events_type)
	{
		this.events_type = events_type;
	}
	
	public void DecoderType(String decoder_type)
	{
		this.decoder_type = decoder_type;
	}
	
	public void EncoderType(String encoder_type)
	{
		this.encoder_type = encoder_type;
	}
	
	public void RemoteIp(String remote_ip)
	{
		this.remote_ip = remote_ip;
	}
	
	public void ToolType(String tool_type)
	{
		this.tool_type = tool_type;
	}
	
	public void ReqType(String req_type)
	{
		this.req_type = req_type;
	}
	
	public void AppInfo(String app_info)
	{
		this.app_info = app_info;
	}
	
	public void Period(int period)
	{
		this.period = period;
	}
	
	public void PacketDuration(int packet_duration)
	{
		this.packet_duration = packet_duration;
	}
	
	public void LocalUdpPort(int local_udp_port)
	{
		this.local_udp_port = local_udp_port;
	}
	
	public void RemoteUdpPort(int remote_udp_port)
	{
		this.remote_udp_port = remote_udp_port;
	}
	
	public void OutPayloadType(int out_payload_type)
	{
		this.out_payload_type = out_payload_type;
	}
	
	public void GroupId(int group_id)
	{
		this.group_id = group_id;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	public void InputFromRTP(boolean input_from_RTP)
	{
		this.input_from_RTP = input_from_RTP;
	}
	public void Enabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	@SuppressWarnings("unchecked")
	private JSONArray status()
    {
		HashMap<String,Object> status_temp_hash = new HashMap<String,Object>();
		
		JSONArray statusarr = new JSONArray();
		status_temp_hash.put(SurfConstants.TYPE, this.status_type);
		status_temp_hash.put(SurfConstants.PERIOD, new Integer(this.period));
		JSONObject status_temp = new JSONObject(status_temp_hash);
		
		statusarr.add(status_temp);
		
		return statusarr;
    }
	
	@SuppressWarnings("unchecked")
	private JSONArray events()
    {
		HashMap<String,Object> status_temp_hash = new HashMap<String,Object>();
		
		JSONArray eventsarr = new JSONArray();
		
		status_temp_hash.put(SurfConstants.TYPE, this.events_type);
		status_temp_hash.put(SurfConstants.ENABLED, this.enabled);
		
		JSONObject status_temp = new JSONObject(status_temp_hash);
		eventsarr.add(status_temp);
		
		return eventsarr;
    }
	
	private JSONObject decoder()
    {
		HashMap<String,Object> decoder_temp_hash = new HashMap<String,Object>();
		
		decoder_temp_hash.put(SurfConstants.TYPE, this.decoder_type);
		JSONObject decoder_temp = new JSONObject(decoder_temp_hash);
		
		return decoder_temp;
    }
	
	private JSONObject encoder()
    {
		HashMap<String,Object> encoder_temp_hash = new HashMap<String,Object>();
		
		encoder_temp_hash.put(SurfConstants.TYPE, this.encoder_type);
		encoder_temp_hash.put(SurfConstants.PACKET_DURATION, new Integer(this.packet_duration));
		JSONObject encoder_temp = new JSONObject(encoder_temp_hash);
		
		return encoder_temp;
    }
	
	private JSONObject RTP()
    {
		HashMap<String,Object> RTP_temp_hash = new HashMap<String,Object>();
		
		RTP_temp_hash.put(SurfConstants.LOCAL_UDP_PORT, this.local_udp_port);
		RTP_temp_hash.put(SurfConstants.REMOTE_UDP_PORT, this.remote_udp_port);
		RTP_temp_hash.put(SurfConstants.REMOTE_IP, this.remote_ip);
		RTP_temp_hash.put(SurfConstants.OUT_PAYLOAD_TYPE, new Integer(this.out_payload_type));
		
		JSONObject RTP_temp = new JSONObject(RTP_temp_hash);
		
		return RTP_temp;
    }
	
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		
		data_hash.put(SurfConstants.STATUS, this.status());
		data_hash.put(SurfConstants.EVENTS, this.events());
		data_hash.put(SurfConstants.DECODER, this.decoder());
		data_hash.put(SurfConstants.ENCODER, this.encoder());
		data_hash.put(SurfConstants.RTP, this.RTP());
		data_hash.put(SurfConstants.TOOL_TYPE, this.tool_type);
		data_hash.put(SurfConstants.INPUT_FROM_RTP, this.input_from_RTP);
		data_hash.put(SurfConstants.APP_INFO,this.app_info);
		data_hash.put(SurfConstants.GROUP_ID, new Integer(this.group_id));
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    		
    }
	
    public String configure_voice_tool_msg()
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
