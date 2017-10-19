#!/usr/bin/env python

from general_params import *
from hmp_connection import *

voice_tdm = """
{"tool_req":{
  "tool_id":1,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"voice_tdm_ip",
    "decoder":{"type":"G.711alaw"},
    "encoder":{"type":"G.711alaw"},
    "RTP":{"local_udp_port":5000, "remote_udp_port":6000, "remote_ip":"127.0.0.1"},
    "EVG":{"enabled":true},
    "EC":{
      "enabled":true,
      "aglorithm":"subband",
      "adaptation":"full",
      "CNG":"general",
      "NLP":"general",
      "tone_disabler":true,
      "anti_howling":false,
      "ap":2,
      "noise_reduction_level":0,
      "NR_smooth":"none",
      "dc_flag":false,
      "echo_tail_ms":64,
      "delay_ms":0
    },
    "TDM":{
      "timeslot":0,
      "in_filename":"InputFiles/tdm_in.wav",
      "out_filename":"RecordFiles/tdm_out.wav",
      "coding":"alaw",
      "read_delay_ms":0
    },
    "input_from_RTP":false,
    "after_encoder_dst_tool_ids":[2000]
  }
}}
"""

file_reader = """
{"tool_req":{
   "tool_id":1000,
   "req_id":0,
   "req_type":"set_config",
   "data":{
     "tool_type":"file_reader",
     "audio_enabled":true,
     "audio_dst_tool_ids":[1],
     "events":[{"type":"all", "enabled":true}]
   }
}}
"""

cmd_play = """
{"tool_req":{
   "tool_id":1000,
   "req_id":0,
   "req_type":"command",
   "data":{
     "cmd_type":"play"
   }
}}
"""

cmd_append = """
{"tool_req":{
   "tool_id":1000,
   "req_id":0,
   "req_type":"command",
   "data":{
     "cmd_type":"play_list_append",
     "files":[{"name":"InputFiles/ip_in.wav"}]
   }
}}
"""

file_writer = """
{"tool_req":{
  "tool_id":2000,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"file_writer",
    "events":[{"type":"all", "enabled":true}]
  }
}}
"""

cmd_record = """
{"tool_req":{
   "tool_id":2000,
   "req_id":0,
   "req_type":"command",
   "data":{
     "cmd_type":"record",
     "file_name":"RecordFiles/ip_out.wav"
   }
}}
"""

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)
#send tool configuration messages to the MediaProcessor

hmp.send_cmd(voice_tdm)

hmp.send_cmd(file_reader)
hmp.send_cmd(cmd_append)
hmp.send_cmd(cmd_play)

hmp.send_cmd(file_writer)
hmp.send_cmd(cmd_record)

while True:
	time.sleep(10)

