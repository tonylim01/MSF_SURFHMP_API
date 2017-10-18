package swmrf.media.tcpsocket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TCPSocketAPI extends Thread{
	Socket socket;
	DataOutputStream outToServer;
	InputStream inFromServer;
	
	public boolean process_flag;
	
    public void client(String server,int port) throws Exception
    {
    		socket = new Socket(server , port);
    		
    		outToServer = new DataOutputStream(socket.getOutputStream());
    		inFromServer = socket.getInputStream();
		
    }
    
    public void SocketWriteLen(String Input)
    {
    		try {
    			outToServer.write(getLittleEndian(Input.length()));
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    	
    		byte [] data = new byte[1024];
		data = Input.getBytes();
		
    		try {
				outToServer.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    public void SocketWrite(String Input)
    {
    		byte [] data = new byte[1024];
		data = Input.getBytes();
		
    		try {
				outToServer.write(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    public int getBigEndian(byte[] v){
    		int[] arr = new int[4];
    		for(int i=0;i<4;i++){
    			arr[i] = (int)(v[3-i] & 0xFF);
    		}
    		return ((arr[0] << 24) + (arr[1] << 16) + (arr[2] << 8) + (arr[3] << 0));
    	}

    public byte[] getLittleEndian(int v){
	    	byte[] buf = new byte[4];
	    	buf[3] = (byte)((v >>> 24) & 0xFF);
	    	buf[2] = (byte)((v >>> 16) & 0xFF);
	    	buf[1] = (byte)((v >>> 8) & 0xFF);
	    	buf[0] = (byte)((v >>> 0) & 0xFF);
	    	return buf;
    	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte [] data = new byte[1024];
		
		try {
			inFromServer.read(data, 0, 7);
			
			String byteToString = new String(data,0,data.length);
			System.out.println("Init Msg : " + byteToString);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        while(process_flag)
        {
        		byte [] Length = new byte[128];
        		byte [] byteJsonData = new byte[4096];
        		String JsonData;
        		
    			try {
				int	nLen = 0;
				int nRet = 0;
    				nRet = inFromServer.read(Length,0,4);
    				if(nRet <= 0)
    				{
    					this.process_flag = false;
    					socket.close();
    					break;
    				}
    				
				nLen = this.getBigEndian(Length);
				System.out.println("Read Len : " + nLen);
				
				nRet = inFromServer.read(byteJsonData,0,nLen);
				
				System.out.println("read nRet : \n" + nRet);
				
				if(nRet <= 0)
				{
					this.process_flag = false;
					socket.close();
					break;
				}
				if(nRet < nLen)
				{
					nRet = inFromServer.read(byteJsonData,0,nLen-nRet);
				}
				JsonData = new String(byteJsonData,0,nLen);
				System.out.println("Read Json : \n" + JsonData);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
	}
}
