package swmrf.media.surfhmp.jsonifapi;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class configure_voice_tool {

	public String status_type;
	public String events_type;
	public String decoder_type;
	public String encoder_type;
	public String remote_ip;
	public String tool_type;
	public String req_type;
	public int period;
	public int packet_duration;
	public int local_udp_port;
	public int remote_udp_port;
	public int out_payload_type;
	public int group_id;
	public int tool_id;
	public int req_id;
	public boolean input_from_RTP;
	
	public JSONArray status()
    {
		HashMap<String,Object> status_temp_hash = new HashMap<String,Object>();
		
		JSONArray statusarr = new JSONArray();
		status_temp_hash.put("type", status_type);
		status_temp_hash.put("period", new Integer(period));
		JSONObject status_temp = new JSONObject(status_temp_hash);
		
		statusarr.add(status_temp);
		
		return statusarr;
    }
	
	public JSONArray events()
    {
		HashMap<String,Object> status_temp_hash = new HashMap<String,Object>();
		
		JSONArray eventsarr = new JSONArray();
		
		status_temp_hash.put("type", events_type);
		status_temp_hash.put("enabled", true);
		
		JSONObject status_temp = new JSONObject(status_temp_hash);
		eventsarr.add(status_temp);
		
		return eventsarr;
    }
	
	public JSONObject decoder()
    {
		HashMap<String,Object> decoder_temp_hash = new HashMap<String,Object>();
		
		decoder_temp_hash.put("type", decoder_type);
		JSONObject decoder_temp = new JSONObject(decoder_temp_hash);
		
		return decoder_temp;
    }
	
	public JSONObject encoder()
    {
		HashMap<String,Object> encoder_temp_hash = new HashMap<String,Object>();
		
		encoder_temp_hash.put("type", encoder_type);
		encoder_temp_hash.put("packet_duration", new Integer(packet_duration));
		JSONObject encoder_temp = new JSONObject(encoder_temp_hash);
		
		return encoder_temp;
    }
	
	public JSONObject RTP()
    {
		HashMap<String,Object> RTP_temp_hash = new HashMap<String,Object>();
		
		RTP_temp_hash.put("local_udp_port", local_udp_port);
		RTP_temp_hash.put("remote_udp_port", remote_udp_port);
		RTP_temp_hash.put("remote_ip", remote_ip);
		RTP_temp_hash.put("out_payload_type", new Integer(out_payload_type));
		
		JSONObject RTP_temp = new JSONObject(RTP_temp_hash);
		
		return RTP_temp;
    }
	
	public JSONObject Data()
    {
		HashMap<String,Object> data_hash = new HashMap<String,Object>();
		
		data_hash.put("status", this.status());
		data_hash.put("events", this.events());
		data_hash.put("decoder", this.decoder());
		data_hash.put("encoder", this.encoder());
		data_hash.put("RTP", this.RTP());
		data_hash.put("tool_type", tool_type);
		data_hash.put("input_from_RTP", input_from_RTP);
		data_hash.put("app_info", "test");
		data_hash.put("group_id", new Integer(group_id));
		JSONObject data = new JSONObject(data_hash);
		
    		return data;
    		
    }
	
    public String configure_voice_tool_msg()
    {
    		HashMap<String,Object> senddata_hash = new HashMap<String,Object>();
    		HashMap<String,Object> tool_req_hash = new HashMap<String,Object>();
		
    		tool_req_hash.put("tool_id", new Integer(tool_id));
    		tool_req_hash.put("req_id", new Integer(req_id));
    		tool_req_hash.put("req_type", req_type);
    		tool_req_hash.put("data", this.Data());
		
		JSONObject tool_req = new JSONObject(tool_req_hash);
		senddata_hash.put("tool_req", tool_req);
		JSONObject senddata = new JSONObject(senddata_hash);
    		return senddata.toString();
    		
    }
}
