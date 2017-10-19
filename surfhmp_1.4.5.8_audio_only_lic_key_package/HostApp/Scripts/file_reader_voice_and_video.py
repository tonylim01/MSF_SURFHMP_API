#!/usr/bin/env python
 
from general_params import *
from hmp_connection import *
from hmp_messages import *

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)

cmd_configure_voice_tool = """
{"tool_req":{
   "tool_id":4,
   "req_id":1003, 
   "req_type":"set_config",
   "data":{
     "tool_type":"voice_p2p",
     "input_from_RTP":false,
     "app_info":"test",
     "group_id":55,
     "status":[{"type":"all", "period":1000}],
     "events":[{"type":"all", "enabled":true}],
     "decoder":{"type":"AMR_NB","packing":"OA"},
     "encoder":{"type":"G.711alaw", "packet_duration":20},
     "RTP":{"local_udp_port":5000, "remote_udp_port":6000, "remote_ip":"10.10.1.82", "out_payload_type":8}
  }
}}
"""

cmd_append_start = """
{"tool_req":{
   "tool_id":1,
   "req_id":1001,
   "req_type":"command",
   "data":{
     "cmd_type":"play_list_append",
     "files":[{"name":"InputFiles/tiger.3gp", "duration":15},
              {"name":"InputFiles/Surf_Video.3gp", "duration":15}]
   }
}}
"""

cmd_play = """
{"tool_req":{
   "tool_id":1,
   "req_id":1002,
   "req_type":"command",
   "data":{
     "cmd_type":"play"
   }
}}
"""

cmd_append_1_file = """
{"tool_req":{
   "tool_id":1,
   "req_id":1001,
   "req_type":"command",
   "data":{
     "cmd_type":"play_list_append",
     "files":[{"name":"InputFiles/tiger.3gp"}]
   }
}}
"""

#Config voice tool
hmp.send_cmd(cmd_configure_voice_tool)

#Config file reader tool, enable all events
msg = createFileReaderMsg(1, True, [4], True, [2])
enableEvent(msg, 'all', True)
hmp.send_json_cmd(msg)

#Config video tools
msg = createRtpToolMsg(2, [], [3], True, 96, "H.264", True, 96)
hmp.send_json_cmd(msg)

msg = createUdpToolMsg(3, [], 7000, 7000, '10.10.1.82')
hmp.send_json_cmd(msg)

msg = createVideoDecoderMsg(10, [11])
hmp.send_json_cmd(msg)

msg = createVideoEncoderMsg(11, [2], 'H.264', 0, 0, 'VBR', 1000, 'balanced', 'baseline', '5.0')
hmp.send_json_cmd(msg)

#Append new list with 2 files
hmp.send_cmd(cmd_append_start)

#Play list
hmp.send_cmd(cmd_play)

#Sleep for 10 seconds
time.sleep(10)

#Append file at end of play list
hmp.send_cmd(cmd_append_1_file)

while True:
	time.sleep(10)




	




