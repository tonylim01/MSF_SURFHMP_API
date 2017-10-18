import swmrf.media.surfhmp.jsonifapi.SurfConnect;
import swmrf.media.tcpsocket.TCPSocketAPI;

public class SurfHMPMain {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.out.println("Start SurfHMP Process");
		
		TCPSocketAPI tcp_client = new TCPSocketAPI();
		
		try {
			tcp_client.client("192.168.5.63", 7777);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tcp_client.process_flag = true;
		
		tcp_client.start();
		
		String Init = "surfapi";
		tcp_client.SocketWrite(Init);
		
		
		SurfConnect surf_conn = new SurfConnect();
		String SendData = surf_conn.SurfConnectMsg();
		
		tcp_client.SocketWriteLen(SendData);
		
		System.out.println("Wrote Data : "+SendData);
		
		if(args[0].equals("disconnect"))
		{
			System.out.println("################## disconnect ################");
			SurfFunction func = new SurfFunction();
			func.func_clear_all_tools(tcp_client);
			
			func.func_system_status(tcp_client);
			
			Thread.sleep(1000);
			
			func.func_disconnect(tcp_client);
		}
		
		else if(args[0].equals("voice_readfile"))
		{
			System.out.println("################## voice_readfile ################");
			SurfFunction func = new SurfFunction();
			func.func_voice_tool(tcp_client);
			
			func.func_file_reader(tcp_client);
			
			func.func_fileapp_start(tcp_client);
			
			func.func_play(tcp_client);
		}
		
		else if(args[0].equals("voice_mixer"))
		{
			System.out.println("################## voice_mixer ################");
			
		}
	}

}
