import swmrf.media.surfhmp.jsonifapi.SurfCommonLog;
import swmrf.media.surfhmp.jsonifapi.SurfConnect;
import swmrf.media.surfhmp.jsonifapi.SurfDisConnect;
import swmrf.media.surfhmp.jsonifapi.configure_dtmf_tool;
import swmrf.media.surfhmp.jsonifapi.configure_voice_tool;
import swmrf.media.surfhmp.jsonifapi.create_file_reader;
import swmrf.media.surfhmp.jsonifapi.dtmf_generate;
import swmrf.media.surfhmp.jsonifapi.enable_system_status;
import swmrf.media.surfhmp.jsonifapi.voice_file_append_start;
import swmrf.media.surfhmp.jsonifapi.voice_file_play;
import swmrf.media.surfhmp.jsonifapi.voice_file_recording;
import swmrf.media.surfhmp.jsonifapi.voice_mix_participants;
import swmrf.media.surfhmp.jsonifapi.voice_mixer;
import swmrf.media.surfhmp.jsonifapi.voice_remve;
import swmrf.media.surfhmp.jsonifapi.voice_rtp_control;
import swmrf.media.tcpsocket.TCPSocketAPI;

public class SurfFunction {
	
	public int id = 0;
	
	public void func_surf_start(TCPSocketAPI tcp_client)
    {
		String Init = "surfapi";
		tcp_client.SocketWrite(Init);
		
		SurfConnect surf_conn = new SurfConnect();
		
		surf_conn.SetConnectVersion(SurfMainConstants.MAJOR_VERSION,SurfMainConstants.MINOR_VERSION);
		surf_conn.KeepAliveTimeout(SurfMainConstants.KEEP_ALIVE_TIMEOUT);
		
		String SendData = surf_conn.SurfConnectMsg();
		
		tcp_client.SocketWriteLen(SendData);
		
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
	
	public void func_file_reader(TCPSocketAPI tcp_client)
    {
		create_file_reader file_reader = new create_file_reader();
		
		file_reader.EventsType(SurfMainConstants.ALL);
		file_reader.ToolType(SurfMainConstants.FILE_READER);
		file_reader.ReqType(SurfMainConstants.SET_CONFIG);
		file_reader.VideoEnabled(false);
		file_reader.AudioEnabled(true);
		file_reader.ToolId(id+1);
		file_reader.ReqId(id+0);
		file_reader.AudioDTSToolIds(4);
		
		String SendData = file_reader.create_file_reader_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    
    public void func_fileapp_start(TCPSocketAPI tcp_client)
    {
		voice_file_append_start fileapp_start = new voice_file_append_start();
		
		fileapp_start.Duration(SurfMainConstants.DURATION);
		fileapp_start.ToolId(id+1);
		fileapp_start.ReqId(id+1001);
		fileapp_start.ReqType(SurfMainConstants.COMMAND);
		fileapp_start.CmdType(SurfMainConstants.PLAY_LIST_APPEND);
		fileapp_start.FileList(0,"InputFiles/californication.wav");
		fileapp_start.FileList(1,"InputFiles/muki_short.wav");
		String SendData = fileapp_start.file_append_start_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    
    public void func_play(TCPSocketAPI tcp_client)
    {
		voice_file_play play = new voice_file_play();
		
		play.CmdType(SurfMainConstants.PLAY);
		play.ReqType(SurfMainConstants.COMMAND);
		play.ToolId(id+1);
		play.ReqId(id+1002);
		
		String SendData = play.file_play_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    
    public void func_clear_all_tools(TCPSocketAPI tcp_client)
    {
		SurfDisConnect surf_disconn = new SurfDisConnect();
		
		surf_disconn.req_id(0);;
		surf_disconn.req_type(SurfMainConstants.COMMAND);;
		surf_disconn.cmd_type(SurfMainConstants.CLEAR_ALL_TOOLS);
		
		String SendData = surf_disconn.clear_all_tools();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    
    public void func_system_status(TCPSocketAPI tcp_client)
    {
    		enable_system_status system_status = new enable_system_status();
    		
    		system_status.StatusType(SurfMainConstants.PERFORMANCE);
    		system_status.StatusPeriod(SurfMainConstants.PERIOD);
    		system_status.ReqId(0);
    		system_status.ReqType(SurfMainConstants.SET_CONFIG);
    		
    		String SendData = system_status.system_status();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    
    public void func_disconnect(TCPSocketAPI tcp_client)
    {
    		SurfDisConnect surf_disconn = new SurfDisConnect();
		
    		surf_disconn.reason(SurfMainConstants.FINISHED);;
    		surf_disconn.error_code(SurfMainConstants.ERROR_CODE_NORMAL);
    		
    		String SendData = surf_disconn.SurfDisConnectMsg();
			
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    
    public void func_system_status_mix(TCPSocketAPI tcp_client)
    {
    		enable_system_status system_status = new enable_system_status();
    		
    		system_status.StatusType(SurfMainConstants.PERFORMANCE);
    		system_status.StatusPeriod(SurfMainConstants.PERIOD);
    		system_status.ReqId(0);
    		system_status.ReqType(SurfMainConstants.SET_CONFIG);
		
    		String SendData = system_status.system_status();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    public void func_voice_mixer(TCPSocketAPI tcp_client)
    {
    		voice_mixer vce_mix = new voice_mixer();
    		
    		vce_mix.ToolType(SurfMainConstants.VOICE_MIXER);
    		vce_mix.ToolId(20000);
    		vce_mix.ReqId(0);
    		vce_mix.ReqType(SurfMainConstants.SET_CONFIG);
    		vce_mix.SamplingRate(8000);
    		vce_mix.HangoverPeriod(500);
    		vce_mix.DominantSpeakers(5);
		
    		String SendData = vce_mix.voice_mixer_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
    }
    
    public void func_voice_tool(TCPSocketAPI tcp_client)
	{
		configure_voice_tool cfg_voice_tool = new configure_voice_tool();
		
		cfg_voice_tool.StatusType(SurfMainConstants.ALL);
		cfg_voice_tool.EventsType(SurfMainConstants.ALL);
		cfg_voice_tool.DecoderType(SurfMainConstants.G711A);
		cfg_voice_tool.EncoderType(SurfMainConstants.AMR_WB);
		cfg_voice_tool.RemoteIp(SurfMainConstants.LOCAL_IP);
		cfg_voice_tool.ToolType(SurfMainConstants.VOICE_P2P);
		cfg_voice_tool.ReqType(SurfMainConstants.SET_CONFIG);
		cfg_voice_tool.AppInfo(SurfMainConstants.APP_INFO);
		cfg_voice_tool.Period(SurfMainConstants.PERIOD);
		cfg_voice_tool.PacketDuration(SurfMainConstants.PACKET_DURATION);
		cfg_voice_tool.LocalUdpPort(SurfMainConstants.LOCAL_UDP_PORT + this.id);
		cfg_voice_tool.RemoteUdpPort(SurfMainConstants.REMOTE_UDP_PORT + this.id);
		cfg_voice_tool.OutPayloadType(SurfMainConstants.OUT_PAYLOAD_TYPE);
		cfg_voice_tool.GroupId(this.id+10000);
		cfg_voice_tool.ToolId(this.id+20000);
		cfg_voice_tool.ReqId(this.id+30000);
		
		String SendData = cfg_voice_tool.configure_voice_tool_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
	}
	
	public void func_voice_rtp_control(TCPSocketAPI tcp_client)
	{
		voice_rtp_control vce_rtp_ctl = new voice_rtp_control();
		
		vce_rtp_ctl.DecoderType(SurfMainConstants.G711A);
		vce_rtp_ctl.EncoderType(SurfMainConstants.AMR_WB);
		vce_rtp_ctl.RemoteIp(SurfMainConstants.LOCAL_IP);
		vce_rtp_ctl.ToolType(SurfMainConstants.VOICE_P2P);
		vce_rtp_ctl.ReqType(SurfMainConstants.SET_CONFIG);
		vce_rtp_ctl.LocalUdpPort(SurfMainConstants.LOCAL_UDP_PORT);
		vce_rtp_ctl.RemoteUdpPort(SurfMainConstants.REMOTE_UDP_PORT);
		vce_rtp_ctl.InPayloadType(SurfMainConstants.IN_PAYLOAD_TYPE);
		vce_rtp_ctl.OutPayloadType(SurfMainConstants.OUT_PAYLOAD_TYPE);
		vce_rtp_ctl.BackendToolId(20000);
		vce_rtp_ctl.ToolId(4);
		vce_rtp_ctl.ReqId(1003);
		
		String SendData = vce_rtp_ctl.voice_rtp_control_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
	}
	
	public void func_voice_mix_participants(TCPSocketAPI tcp_client)
	{
		voice_mix_participants vce_mix_part = new voice_mix_participants();
		
		vce_mix_part.ToolId(20000);
		vce_mix_part.ReqId(0);
		vce_mix_part.ReqType(SurfMainConstants.SET_CONFIG);
		vce_mix_part.DataToolId(0);
		vce_mix_part.ID(0);
		vce_mix_part.DataType(SurfMainConstants.REGULAR);
		
		String SendData = vce_mix_part.voice_mix_participants_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
	}
	
	public void func_voice_file_recording(TCPSocketAPI tcp_client)
	{
		voice_file_recording file_record = new voice_file_recording();
		
		file_record.ToolId(3);
		file_record.ReqId(1002);
		file_record.ReqType(SurfMainConstants.COMMAND);
		file_record.MaxSize(SurfMainConstants.MAX_SIZE);
		file_record.CmdType(SurfMainConstants.RECORD);
		file_record.FileName("RecordFiles/californication2.wav");
		
		String SendData = file_record.file_record_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
	}
	
    public void func_dtmf_tool(TCPSocketAPI tcp_client)
	{
    		configure_dtmf_tool cfg_dtmf_tool = new configure_dtmf_tool();
		
    		cfg_dtmf_tool.DecoderType(SurfMainConstants.G711A);
    		cfg_dtmf_tool.EncoderType(SurfMainConstants.AMR_WB);
    		cfg_dtmf_tool.RemoteIp(SurfMainConstants.LOCAL_IP);
    		cfg_dtmf_tool.ToolType(SurfMainConstants.VOICE_P2P);
    		cfg_dtmf_tool.ReqType(SurfMainConstants.SET_CONFIG);
		cfg_dtmf_tool.PacketDuration(SurfMainConstants.PACKET_DURATION);
		cfg_dtmf_tool.LocalUdpPort(SurfMainConstants.LOCAL_UDP_PORT);
		cfg_dtmf_tool.RemoteUdpPort(SurfMainConstants.REMOTE_UDP_PORT);
		cfg_dtmf_tool.ToolId(4);
		cfg_dtmf_tool.ReqId(1003);
		cfg_dtmf_tool.DtmfInPayloadType(101);
		cfg_dtmf_tool.DtmfOutPayloadType(101);
		cfg_dtmf_tool.EVG(true);
		cfg_dtmf_tool.DtmfGroup("DTMF_GROUP");
		cfg_dtmf_tool.EVD(true);
		
		String SendData = cfg_dtmf_tool.configure_dtmf_tool_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
	}
    
    public void func_dtmf_generate(TCPSocketAPI tcp_client)
	{
    		dtmf_generate dtmf_gen = new dtmf_generate();
		
    		dtmf_gen.ToolId(1);
    		dtmf_gen.ReqId(0);
    		dtmf_gen.ReqType(SurfMainConstants.COMMAND);
    		dtmf_gen.TotalDuration(300);
    		dtmf_gen.CmdType(SurfMainConstants.GENERATE_TONE);
    		dtmf_gen.NamedEvent("DTMF6");
    		dtmf_gen.RtpOrInband(SurfMainConstants.RTP);
		
		String SendData = dtmf_gen.dtmf_generate_msg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
	}
    
    public void func_remove(TCPSocketAPI tcp_client)
	{
    		voice_remve remove = new voice_remve();
		
		remove.tool_id(this.id+20000);
		remove.req_id(this.id+30000);
		remove.req_type("remove");
		
		String SendData = remove.SurfRemoveMsg();
		
		tcp_client.SocketWriteLen(SendData);
		SurfCommonLog.Log("Wrote Data : "+SendData);
	}
}
