import swmrf.media.surfhmp.jsonifapi.SurfCommonLog;
import swmrf.media.tcpsocket.TCPSocketAPI;

public class SurfHMPMain {
	
	public static final String SURFHMP_DISCONNECT = "disconnect";
	public static final String SURFHMP_VOICE_READ_FILE = "voice_readfile";
	public static final String SURFHMP_VOICE_MIXER = "voice_mixer";
	public static final String SURFHMP_RECORD = "record";
	public static final String SURFHMP_DTMF_GET = "dtmf_get";
	public static final String SURFHMP_DTMF_GEN = "dtmf_gen";
	
	public static final String SURFHMP_IP = "192.168.5.63";
	public static final int SURFHMP_PORT = 7777;

	public static void main(String[] args) throws InterruptedException {
		
		SurfFunction func = new SurfFunction();
		String START_ARGS = args[0];
		
		System.out.println("Start SurfHMP Process");
		
		TCPSocketAPI tcp_client = new TCPSocketAPI();
		
		try {
			tcp_client.client(SURFHMP_IP, SURFHMP_PORT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tcp_client.process_flag = true;
		
		tcp_client.start();
		
		func.func_surf_start(tcp_client);
		
		switch(START_ARGS)
		{
			case SURFHMP_DISCONNECT:
			{
				SurfCommonLog.Log("################## disconnect ################");
				func.func_clear_all_tools(tcp_client);
				func.func_system_status(tcp_client);
				
				Thread.sleep(1000);
				
				func.func_disconnect(tcp_client);
				break;
			}
			
			case SURFHMP_VOICE_READ_FILE:
			{
				SurfCommonLog.Log("################## voice_readfile ################");
				int i = 0;
				for(i = 0;i < 400;i ++)
				{
//					func.func_voice_tool(tcp_client);
//					func.func_file_reader(tcp_client);
//					func.func_fileapp_start(tcp_client);
//					func.func_play(tcp_client);
					Thread.sleep(100);
//					func.func_clear_all_tools(tcp_client);
					func.func_remove(tcp_client);
//					Thread.sleep(5000);
//					func.func_disconnect(tcp_client);
					func.id ++;
				}
				break;
			}
			case SURFHMP_VOICE_MIXER:
			{
				SurfCommonLog.Log("################## voice_mixer ################");
				func.func_system_status_mix(tcp_client);
				func.func_voice_mixer(tcp_client);
				func.func_voice_rtp_control(tcp_client);
				func.func_voice_mix_participants(tcp_client);
				break;
			}
			case SURFHMP_RECORD:
			{
				SurfCommonLog.Log("################## record ################");
				func.func_system_status(tcp_client);
				func.func_file_reader(tcp_client);
				break;
			}
			case SURFHMP_DTMF_GET:
			{
				SurfCommonLog.Log("################## dtmf_get ################");
				func.func_system_status(tcp_client);
				func.func_dtmf_tool(tcp_client);
				break;
			}
			case SURFHMP_DTMF_GEN:
			{
				SurfCommonLog.Log("################## dtmf_gen ################");
				func.func_system_status(tcp_client);
				func.func_dtmf_generate(tcp_client);
				break;
			}
		}
		
	}

}
