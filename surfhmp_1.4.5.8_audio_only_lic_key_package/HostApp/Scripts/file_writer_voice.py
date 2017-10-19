#!/usr/bin/env python
 
from general_params import *
from hmp_connection import *
from hmp_messages import *

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)

print "######## hmp.connect #######"

cmd_configure_file_reader_tool = """
{"tool_req":{
  "tool_id":1,
  "req_id":1000,
  "req_type":"set_config",
  "data":{
    "tool_type":"file_reader",
    "events":[{"type":"all", "enabled":true}],
    "audio_enabled":true,
    "audio_dst_tool_ids":[2]
  }
}}
"""

cmd_append = """
{"tool_req":{
   "tool_id":1,
   "req_id":1001,
   "req_type":"command",
   "data":{
     "cmd_type":"play_list_append",
     "files":[{"name":"InputFiles/californication.wav"}]
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

cmd_configure_voice_tool = """
{"tool_req":{
   "tool_id":2,
   "req_id":1003, 
   "req_type":"set_config",
   "data":{
     "tool_type":"voice_p2p",
     "input_from_RTP":false,
     "after_encoder_dst_tool_ids":[3],
     "app_info":"oleg",		
     "group_id":55,
     "status":[{"type":"all", "period":1000}],
     "events":[{"type":"all", "enabled":true}],
     "decoder":{"type":"G.711alaw"},
     "encoder":{"type":"G.711alaw", "packet_duration":20},
     "RTP":{"local_udp_port":5000, "remote_udp_port":6000, "remote_ip":"10.10.1.82", "out_payload_type":8}
  }
}}
"""

cmd_configure_file_writer_tool = """
{"tool_req":{
  "tool_id":3,
  "req_id":1000,
  "req_type":"set_config",
  "data":{
    "tool_type":"file_writer",
    "events":[{"type":"all", "enabled":true}],
    "traces":[{"id":0,"file":"logs/file_tool_packets.pcm"}]
  }
}}
"""

cmd_record1 = """
{"tool_req":{
   "tool_id":3,
   "req_id":1002,
   "req_type":"command",
   "data":{
     "cmd_type":"record",
     "file_name":"RecordFiles/californication1.wav",
     "max_size":5000
   }
}}
"""

cmd_record2 = """
{"tool_req":{
   "tool_id":3,
   "req_id":1002,
   "req_type":"command",
   "data":{
     "cmd_type":"record",
     "file_name":"RecordFiles/californication2.wav",
     "max_size":5000
   }
}}
"""

cmd_stop = """
{"tool_req":{
   "tool_id":3,
   "req_id":1002,
   "req_type":"command",
   "data":{
     "cmd_type":"stop"
   }
}}
"""

#Config file reader tool, enable all events
msg = createFileReaderMsg(1, True, [2], False, [])
enableEvent(msg, 'all', True)

#hmp.send_json_cmd(msg)

print "createFileReaderMsg",msg

#Config file writer tool, enable all events and status
msg = createFileWriterMsg(3)
enableEvent(msg, 'all', True)
enableStatus(msg,'all',10000)

#hmp.send_json_cmd(msg)

#config voice_p2p tool
print cmd_configure_voice_tool
hmp.send_cmd(cmd_configure_voice_tool)

#append file and start playing
print cmd_append
#hmp.send_cmd(cmd_append)
#hmp.send_cmd(cmd_play)

#wait 5 second and start recording to file 1
time.sleep(5)
#hmp.send_cmd(cmd_record1)

#wait 10 second and stop recording
time.sleep(10)
#hmp.send_cmd(cmd_stop)

#wait 5 second and start recording to file 2
time.sleep(5)
#hmp.send_cmd(cmd_record2)

#wait 10 second and stop recording
time.sleep(10)
#hmp.send_cmd(cmd_stop);

while True:
	time.sleep(10)




	




