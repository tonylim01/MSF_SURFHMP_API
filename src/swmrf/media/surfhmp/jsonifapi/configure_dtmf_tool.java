package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class configure_dtmf_tool {
	private String decoder_type;
	private String encoder_type;
	private String remote_ip;
	private String tool_type;
	private String req_type;
	private String dtmp_group;
	private int packet_duration;
	private int local_udp_port;
	private int remote_udp_port;
	private int tool_id;
	private int req_id;
	private int dtmf_in_payload_type;
	private int dtmf_out_payload_type;
	private boolean evg;
	private boolean evd;
	
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
	
	public void DtmfGroup(String dtmp_group)
	{
		this.dtmp_group = dtmp_group;
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
	
	public void ToolId(int tool_id)
	{
		this.tool_id = tool_id;
	}
	
	public void ReqId(int req_id)
	{
		this.req_id = req_id;
	}
	
	public void DtmfInPayloadType(int dtmf_in_payload_type)
	{
		this.dtmf_in_payload_type = dtmf_in_payload_type;
	}
	
	public void DtmfOutPayloadType(int dtmf_out_payload_type)
	{
		this.dtmf_out_payload_type = dtmf_out_payload_type;
	}
	
	public void EVG(boolean evg)
	{
		this.evg = evg;
	}
	
	public void EVD(boolean evd)
	{
		this.evd = evd;
	}
	
	private JSONObject decoder()
    {
		HashMap<String,Object> decoder_temp_hash = new HashMap<String,Object>();
		
		decoder_temp_hash.put(SurfConstants.TYPE, this.decoder_type);
		JSONObject decoder_temp = new JSONObject(decoder_temp_hash);
		
		return decoder_temp;
    }
	
	private JSONObject EVG()
    {
		HashMap<String,Object> decoder_temp_hash = new HashMap<String,Object>();
		
		decoder_temp_hash.put(SurfConstants.ENABLED, this.evg);
		JSONObject decoder_temp = new JSONObject(decoder_temp_hash);
		
		return decoder_temp;
    }
	
	@SuppressWarnings("unchecked")
	private JSONObject EVD()
    {
		HashMap<String,Object> decoder_temp_hash = new HashMap<String,Object>();
		JSONArray eventsarr = new JSONArray();
		eventsarr.add(this.dtmp_group);
		decoder_temp_hash.put(SurfConstants.ENABLED, this.evd);
		decoder_temp_hash.put(SurfConstants.EVENTS, eventsarr);

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
		RTP_temp_hash.put(SurfConstants.DTMF_IN_PAYLOAD_TYPE, new Integer(this.dtmf_in_payload_type));
		RTP_temp_hash.put(SurfConstants.DTMF_OUT_PAYLOAD_TYPE, new Integer(this.dtmf_out_payload_type));
		
		JSONObject RTP_temp = new JSONObject(RTP_temp_hash);
		
		return RTP_temp;
    }
	
	private JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		
		data_hash.put(SurfConstants.DECODER, this.decoder());
		data_hash.put(SurfConstants.ENABLED, this.encoder());
		data_hash.put(SurfConstants.RTP, this.RTP());
		data_hash.put(SurfConstants.EVG, this.EVG());
		if(evd == true)
			data_hash.put(SurfConstants.EVD, this.EVD());
		data_hash.put(SurfConstants.TOOL_TYPE, this.tool_type);
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    		
    }
	
    public String configure_dtmf_tool_msg()
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
