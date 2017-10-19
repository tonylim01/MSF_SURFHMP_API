import swmrf.media.surfhmp.jsonifapi.SurfDisConnect;
import swmrf.media.surfhmp.jsonifapi.configure_voice_tool;
import swmrf.media.surfhmp.jsonifapi.create_file_reader;
import swmrf.media.surfhmp.jsonifapi.enableSystemStatus;
import swmrf.media.surfhmp.jsonifapi.file_append_start;
import swmrf.media.surfhmp.jsonifapi.file_play;
import swmrf.media.surfhmp.jsonifapi.voice_mix_participants;
import swmrf.media.surfhmp.jsonifapi.voice_mixer;
import swmrf.media.surfhmp.jsonifapi.voice_rtp_control;
import swmrf.media.tcpsocket.TCPSocketAPI;

public class SurfFunction {
    public void func_file_reader(TCPSocketAPI tcp_client)
    {
		create_file_reader file_reader = new create_file_reader();
		
		file_reader.events_type = "all";
		file_reader.tool_type = "file_reader";
		file_reader.req_type = "set_config";
		file_reader.video_enabled = false;
		file_reader.audio_enabled = true;
		file_reader.tool_id = 1;
		file_reader.req_id = 0;
		
		String SendData = file_reader.create_file_reader_msg();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    
    public void func_fileapp_start(TCPSocketAPI tcp_client)
    {
		file_append_start fileapp_start = new file_append_start();
		
		fileapp_start.duration =  15;
		fileapp_start.tool_id = 1;
		fileapp_start.req_id = 1001;
		fileapp_start.req_type = "command";
		fileapp_start.cmd_type = "play_list_append";
		fileapp_start.file_list[0] = "InputFiles/californication.wav";
		fileapp_start.file_list[1] = "InputFiles/muki_short.wav";
		String SendData = fileapp_start.file_append_start_msg();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    
    public void func_play(TCPSocketAPI tcp_client)
    {
		file_play play = new file_play();
		
		play.cmd_type = "play";
		play.req_type = "command";
		play.tool_id = 1;
		play.req_id = 1002;
		
		String SendData = play.file_play_msg();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    
    public void func_clear_all_tools(TCPSocketAPI tcp_client)
    {
		SurfDisConnect surf_disconn = new SurfDisConnect();
		String SendData = surf_disconn.clear_all_tools();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    
    public void func_system_status(TCPSocketAPI tcp_client)
    {
    		enableSystemStatus system_status = new enableSystemStatus();
		
    		String SendData = system_status.system_status();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    
    public void func_disconnect(TCPSocketAPI tcp_client)
    {
    		SurfDisConnect surf_disconn = new SurfDisConnect();
		
    		String SendData = surf_disconn.SurfDisConnectMsg();
			
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    
    public void func_system_status_mix(TCPSocketAPI tcp_client)
    {
    		enableSystemStatus system_status = new enableSystemStatus();
    		
    		system_status.status_type = "performance";
    		system_status.status_period = 1000;
    		system_status.req_id = 0;
    		system_status.req_type = "set_config";
		
    		String SendData = system_status.system_status();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    public void func_voice_mixer(TCPSocketAPI tcp_client)
    {
    		voice_mixer vce_mix = new voice_mixer();
    		
    		vce_mix.tool_type = "voice_mixer";
    		vce_mix.tool_id = 20000;
    		vce_mix.req_id = 0;
    		vce_mix.req_type = "set_config";
    		vce_mix.sampling_rate = 8000;
    		vce_mix.hangover_period = 500;
    		vce_mix.dominant_speakers =  5;
		
    		String SendData = vce_mix.voice_mixer_msg();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
    }
    
    public void func_voice_tool(TCPSocketAPI tcp_client)
	{
		configure_voice_tool cfg_voice_tool = new configure_voice_tool();
		
		cfg_voice_tool.status_type = "all";
		cfg_voice_tool.events_type = "all";
		cfg_voice_tool.decoder_type = "G.711alaw";
		cfg_voice_tool.encoder_type = "AMR_WB";
		cfg_voice_tool.remote_ip = "127.0.0.1";
		cfg_voice_tool.tool_type = "voice_p2p";
		cfg_voice_tool.req_type = "set_config";
		cfg_voice_tool.period = 1000;
		cfg_voice_tool.packet_duration = 20;
		cfg_voice_tool.local_udp_port = 5001;
		cfg_voice_tool.remote_udp_port = 6000;
		cfg_voice_tool.out_payload_type = 100;
		cfg_voice_tool.group_id = 55;
		cfg_voice_tool.tool_id = 4;
		cfg_voice_tool.req_id =  1003;
		
		String SendData = cfg_voice_tool.configure_voice_tool_msg();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
	}
	
	public void func_voice_rtp_control(TCPSocketAPI tcp_client)
	{
		voice_rtp_control vce_rtp_ctl = new voice_rtp_control();
		
		vce_rtp_ctl.decoder_type = "G.711alaw";
		vce_rtp_ctl.encoder_type = "AMR_WB";
		vce_rtp_ctl.remote_ip = "127.0.0.1";
		vce_rtp_ctl.tool_type = "voice_p2p";
		vce_rtp_ctl.req_type = "set_config";
		vce_rtp_ctl.local_udp_port = 5001;
		vce_rtp_ctl.remote_udp_port = 6000;
		vce_rtp_ctl.in_payload_type = 8;
		vce_rtp_ctl.out_payload_type = 100;
		vce_rtp_ctl.backend_tool_id = 20000;
		vce_rtp_ctl.tool_id = 4;
		vce_rtp_ctl.req_id =  1003;
		
		String SendData = vce_rtp_ctl.voice_rtp_control_msg();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
	}
	
	public void func_voice_mix_participants(TCPSocketAPI tcp_client)
	{
		voice_mix_participants vce_mix_part = new voice_mix_participants();
		
		vce_mix_part.tool_id = 20000;
		vce_mix_part.req_id =  0;
		vce_mix_part.req_type = "set_config";
		vce_mix_part.data_tool_id = 0;
		vce_mix_part.id = 0;
		vce_mix_part.data_type = "regular";
		
		String SendData = vce_mix_part.voice_mix_participants_msg();
		
		tcp_client.SocketWriteLen(SendData);
		System.out.println("Wrote Data : "+SendData);
	}
}
