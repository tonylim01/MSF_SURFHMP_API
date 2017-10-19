import socket
import json
import struct
import time
import threading

class HmpConnection:

	#connect message 
	cmd_connect = """ 
	{"connect":{ 
	   "api_version":[1,2], 
	   "keep_alive_timeout":0 
	}} 
	""" 
 
	#clean message 
	cmd_clean = """ 
	{"sys_req":{ 
	   "req_id":0, 
	   "req_type":"command", 
	   "data":{ 
	     "cmd_type":"clear_all_tools" 
	   } 
	}} 
	""" 
 
	#disconnect message 
	cmd_disconnect = """ 
	{"disconnect":{ 
	   "error_code":0, 
	   "reason":"finished" 
	}} 
	"""

	#sends JSON text command in format "4 bytes length + message"
	def send_cmd(self, cmd):
	        #json.loads was added only for validation of the outgoing JSON message
	        json.loads(cmd)
	        self.sock.send(struct.pack('<I', len(cmd)))
	        self.sock.send(cmd)

	def send_json_cmd(self, json_cmd):
		cmd = json.dumps(json_cmd)
	        self.sock.send(struct.pack('<I', len(cmd)))
	        self.sock.send(cmd)

	#receives "length" number of bytes from socket, blocks until required number of bytes was received
	def recv_bytes(self, length):
		recv_length = 0
		res = ''
		while recv_length < length:
			res = res + self.sock.recv(length - recv_length)
			recv_length = len(res)
		return res

	#the following function handles single message received from the MediaProcessor

	def handleMessage(self, msg):

		parser = json.loads(msg)
		print "[" ,msg, "]"
		keys = list(parser.keys())
		#connect message is the first message to receive from the MediaProcessor
		if 'connect' == keys[0]:
			print "Media Processor API version: [", parser['connect']['api_version'][0],".",parser['connect']['api_version'][1],"]"
		#sys_ans is the answer to sys_req message which is used to configure the system 
		elif 'sys_ans' == keys[0]:
			if 'set_config' == parser['sys_ans']['req_type']:
				if 0 == parser['sys_ans']['data']['error_code']:
					print "System parameters were successfully configured"
				else:
					print "System configuration ERROR:"
					print "    error code:", parser['sys_ans']['data']['error_code']
					print "    description:", parser['sys_ans']['data']['description']
			elif 'command' == parser['sys_ans']['req_type']:
				if 0 == parser['sys_ans']['data']['error_code']:
					print "System command was successfully performed"
				else:
					print "Error performing system command:"
					print "    error code:", parser['sys_ans']['data']['error_code']
					print "    description:", parser['sys_ans']['data']['description']
		#tool_ans is the response message to tool_req message that is used here to configure the tool	
		elif 'tool_ans' == keys[0]:
			if 'set_config' == parser['tool_ans']['req_type']:
				if 0 == parser['tool_ans']['data']['error_code']:
					print "Tool ", parser['tool_ans']['tool_id'], " was successfully configured"
				else:						
					print "Tool ", parser['tool_ans']['tool_id'], " ERROR:"
					print "    error code:", parser['tool_ans']['data']['error_code']
					print "    description:", parser['tool_ans']['data']['description']
			elif 'remove' == parser['tool_ans']['req_type']:
				if 0 == parser['tool_ans']['data']['error_code']:
					print "Tool ", parser['tool_ans']['tool_id'], " removed"
				else:						
					print "Tool ", parser['tool_ans']['tool_id'], " REMOVAL ERROR:"
					print "    error code:", parser['tool_ans']['data']['error_code']
					print "    description:", parser['tool_ans']['data']['description']
			elif 'command' == parser['tool_ans']['req_type']:
				if 0 == parser['tool_ans']['data']['error_code']:
					print "Tool command was successfully performed tool_id:", parser['tool_ans']['tool_id']
				else:
					print "Error performing tool command, tool_id:", parser['tool_ans']['tool_id']
					print "    error code:", parser['tool_ans']['data']['error_code']
					print "    description:", parser['tool_ans']['data']['description']

		#tool_inf messages are used by the tools to notify about certain event
		elif 'tool_inf' == keys[0]:
			if 'event' == parser['tool_inf']['inf_type']:
				print "Tool ", parser['tool_inf']['tool_id'], " reported event:"
				for param in parser['tool_inf']['data']:
					print "   ", param, ":", str(parser['tool_inf']['data'][param])

		elif 'sys_inf' == keys[0]:
			if 'status' == parser['sys_inf']['inf_type']:
				if 'performance' == parser['sys_inf']['data']['type'] :
					print "CPU usage:", parser['sys_inf']['data']['CPU']['CPU_usage']," nof_late_iterations:",parser['sys_inf']['data']['CPU']['nof_late_sched_iterations']
				elif 'network' == parser['sys_inf']['data']['type'] :
					print "network packet loss :", parser['sys_inf']['data']['packet_loss']
				elif 'video_performance' == parser['sys_inf']['data']['type'] :
					print "GPU usage:", parser['sys_inf']['data']['GPU_usage']
					print "mixer missed frames:", parser['sys_inf']['data']['mixer_missed_frames']
					print "decoder missed frames:", parser['sys_inf']['data']['decoder_missed_frames']
					print "encoder missed frames:", parser['sys_inf']['data']['encoder_missed_frames']

	#procedure that runs in a separate thread and reads messages from the socket
	def recv_proc(self):
		#receive api pass phrase	
		psw = self.recv_bytes(7)
		if 'surfapi' != psw:
			print "Error: wrong pass phrase received from the server!"
		else:
			print "Successfully connected to the MediaProcessor"
		while True:
			bin_len = self.recv_bytes(4)
			length = struct.unpack('<I', bin_len)
			length = length[0]
			if 0 != length:
				msg = self.recv_bytes(length)
#				self.handleMessage(msg)		
		return

	def connect(self, ip, port):
		#create TCP socket and connect to the MediaProcessor
		self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		server_address = (ip, port)
		print ip
		print port
		print server_address
		self.sock.connect(server_address)
		#create socket reading thread
		self.thr = threading.Thread(target=self.recv_proc, args = ())
		self.thr.daemon = True
		self.thr.start()
		#initiate SURF API handshake
		self.sock.send("surfapi")
		#send connect message
		self.send_cmd(self.cmd_connect)
		print self.cmd_connect

	def disconnect(self):
		self.send_cmd(self.cmd_disconnect)

	def remove_all_tools(self):
		self.send_cmd(self.cmd_clean)

	def enableSystemStatus(self, status_type, period):
		msg = {}
		msg['sys_req'] = {}
		msg['sys_req']['req_id'] = 0
		msg['sys_req']['req_type'] = 'set_config'
		msg['sys_req']['data'] = {}
		msg['sys_req']['data']['status'] = []
		msg['sys_req']['data']['status'].append({})
		msg['sys_req']['data']['status'][0]['type'] = status_type
		msg['sys_req']['data']['status'][0]['period'] = period
		json_msg = json.dumps(msg)
		self.send_cmd(json_msg)
		print json_msg

	def disableSystemStatus(self, status_type):
		self.enableSystemStatus(status_type, 0)

	def enableSystemTrace(self, id, filename):
		msg = {}
		msg['sys_req'] = {}
		msg['sys_req']['req_id'] = 0
		msg['sys_req']['req_type'] = 'set_config'
		msg['sys_req']['data'] = {}
		msg['sys_req']['data']['traces'] = []
		msg['sys_req']['data']['traces'].append({})
		msg['sys_req']['data']['traces'][0]['id'] = id
		msg['sys_req']['data']['traces'][0]['file'] = filename
		json_msg = json.dumps(msg)
		self.send_cmd(json_msg)

	def removeTool(self, tool_id):
		msg = {}
		msg['tool_req'] = {}
		msg['tool_req']['req_id'] = 0
		msg['tool_req']['req_type'] = 'remove'
		msg['tool_req']['tool_id'] = tool_id
		json_msg = json.dumps(msg)
		self.send_cmd(json_msg)
		

