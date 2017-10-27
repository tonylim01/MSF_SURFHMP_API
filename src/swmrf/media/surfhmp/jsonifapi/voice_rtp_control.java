package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class voice_rtp_control {
	private String decoder_type;
	private String encoder_type;
	private String remote_ip;
	private String tool_type;
	private String req_type;
	private int local_udp_port;
	private int remote_udp_port;
	private int out_payload_type;
	private int in_payload_type;
	private int tool_id;
	private int req_id;
	private int backend_tool_id;
	
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
	
	public void InPayloadType(int in_payload_type)
	{
		this.in_payload_type = in_payload_type;
	}
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void BackendToolId(int backend_tool_id)
	{
		this.backend_tool_id = backend_tool_id;
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
		JSONObject encoder_temp = new JSONObject(encoder_temp_hash);
		
		return encoder_temp;
    }
	
	private JSONObject RTP()
    {
		HashMap<String,Object> RTP_temp_hash = new HashMap<String,Object>();
		
		RTP_temp_hash.put(SurfConstants.LOCAL_UDP_PORT, this.local_udp_port);
		RTP_temp_hash.put(SurfConstants.REMOTE_UDP_PORT, this.remote_udp_port);
		RTP_temp_hash.put(SurfConstants.REMOTE_IP, this.remote_ip);
		RTP_temp_hash.put(SurfConstants.IN_PAYLOAD_TYPE, new Integer(this.in_payload_type));
		RTP_temp_hash.put(SurfConstants.OUT_PAYLOAD_TYPE, new Integer(this.out_payload_type));
		
		JSONObject RTP_temp = new JSONObject(RTP_temp_hash);
		
		return RTP_temp;
    }
	
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		
		data_hash.put(SurfConstants.DECODER, this.decoder());
		data_hash.put(SurfConstants.ENCODER, this.encoder());
		data_hash.put(SurfConstants.RTP, this.RTP());
		data_hash.put(SurfConstants.TOOL_TYPE, this.tool_type);
		data_hash.put(SurfConstants.BACKEND_TOOL_ID, this.backend_tool_id);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    		
    }
	
    public String voice_rtp_control_msg()
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
