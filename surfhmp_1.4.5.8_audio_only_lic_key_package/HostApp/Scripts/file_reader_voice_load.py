#!/usr/bin/env python

from general_params import *
from hmp_connection import *

NOF_CHANNELS = 10

cmd_configure_file_reader_tool = """
{"tool_req":{
  "tool_id":__TOOL_ID__,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"file_reader",
    "events":[{"type":"all", "enabled":true}],
	"audio_enabled":true,
    "audio_dst_tool_ids":[__DST_TOOL_ID__]
  }
}}
"""

cmd_append = """
{"tool_req":{
   "tool_id":__TOOL_ID__,
   "req_id":0,
   "req_type":"command",
   "data":{
     "cmd_type":"play_list_append",
     "files":[{"name":"InputFiles/load/californication__FILE_NUMBER__.wav"}]
   }
}}
"""

cmd_play = """
{"tool_req":{
   "tool_id":__TOOL_ID__,
   "req_id":0,
   "req_type":"command",
   "data":{
     "cmd_type":"play"
   }
}}
"""

cmd_configure_voice_tool = """
{"tool_req":{
   "tool_id":__TOOL_ID__,
   "req_id":0, 
   "req_type":"set_config",
   "data":{
     "tool_type":"voice_p2p",
     "input_from_RTP":false,
     "decoder":{"type":"G.711alaw"},
     "encoder":{"type":"G.711alaw"},
     "RTP":{"local_udp_port":__SRC_PORT__, "remote_udp_port":__DST_PORT__, "remote_ip":"__REMOTE_IP__"}
  }
}}
"""

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)

hmp.enableSystemStatus('performance', 1000)

#set correct IP
cmd_configure_voice_tool = cmd_configure_voice_tool.replace("__REMOTE_IP__", REMOTE_IP)

src_port = 5000;
dst_port = 25000;
for tool_id in range(0,NOF_CHANNELS):
	#send voice_p2p tool configuration
	cmd_configure_tool_temp = cmd_configure_voice_tool
	cmd_configure_tool_temp = cmd_configure_tool_temp.replace("__TOOL_ID__", str(tool_id + 10000))
	cmd_configure_tool_temp = cmd_configure_tool_temp.replace("__SRC_PORT__", str(src_port))
	cmd_configure_tool_temp = cmd_configure_tool_temp.replace("__DST_PORT__", str(dst_port))
	hmp.send_cmd(cmd_configure_tool_temp)
	#send file_reader tool configuration
	cmd_configure_file_reader_tool_temp = cmd_configure_file_reader_tool
	cmd_configure_file_reader_tool_temp = cmd_configure_file_reader_tool_temp.replace("__TOOL_ID__", str(tool_id))
	cmd_configure_file_reader_tool_temp = cmd_configure_file_reader_tool_temp.replace("__DST_TOOL_ID__", str(tool_id + 10000))
	hmp.send_cmd(cmd_configure_file_reader_tool_temp)
	src_port += 2
	dst_port += 2

time.sleep(1)

for tool_id in range(0,NOF_CHANNELS):
	cmd_append_temp = cmd_append
	cmd_append_temp = cmd_append_temp.replace("__TOOL_ID__", str(tool_id))
	cmd_append_temp = cmd_append_temp.replace("__FILE_NUMBER__", str(tool_id))
	hmp.send_cmd(cmd_append_temp)

time.sleep(1)

for tool_id in range(0,NOF_CHANNELS):
	cmd_play_temp = cmd_play
	cmd_play_temp = cmd_play_temp.replace("__TOOL_ID__", str(tool_id))
	hmp.send_cmd(cmd_play_temp)

while True:
	time.sleep(10)


