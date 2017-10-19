#!/usr/bin/env python

from general_params import *
from hmp_connection import *

voice_1 = """
{"tool_req":{
  "tool_id":1,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"voice_fe_ip",
    "backend_tool_id":2,
    "decoder":{"type":"clear"},
    "encoder":{"type":"clear"},
    "RTP":{"local_udp_port":7000, "remote_udp_port":7002, "remote_ip":"127.0.0.1", "out_payload_type":96, "in_payload_type":96},
    "jitter_buffer":{"init_delay":30, "adaptation_type":"none", "depth":200, "min_delay":0, "max_delay":100}
  }
}}
"""

isdn_2 = """
{"tool_req":{
  "tool_id":2,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"isdn",
    "tdm_side_tool_id":1,
    "data_side_tool_id":3,
    "rate_adapter_mode":"V110_RA",
    "bit_rate":14400,
    "num_data_bits":8,
    "num_stop_bits":1,
    "parity_type":"NONE",
    "flow_control":"NONE",
    "iwf_mode":"T_IWF",
    "num_init_sync":2,
    "num_loss_sync":3,
    "num_recovery_sync":2    
  }
}}
"""

mobile_3 = """
{"tool_req":{
  "tool_id":3,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"mobile",
    "tdm_side_tool_id":4,
    "data_side_tool_id":2,
    "transparent_mode":true,
    "channel_coding":"V110I_96",
    "max_num_sub_channels":2,
    "acceptable_coding":["V110I"],
    "num_init_sync":3,
    "num_loss_sync":2,
    "num_recovery_sync":3,
    "connection":{
      "num_data_bits":8,
      "num_stop_bits":1,
      "parity_type":"NONE",
      "flow_control":"NONE"
    },
    "transparent":{
      "service_type":"BEARER",
      "user_rate":9600
    }
   }
}}
"""

voice_4 = """
{"tool_req":{
  "tool_id":4,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"voice_fe_ip",
    "backend_tool_id":3,
    "decoder":{"type":"clear"},
    "encoder":{"type":"clear"},
    "RTP":{"local_udp_port":6000, "remote_udp_port":6002, "remote_ip":"127.0.0.1", "out_payload_type":96, "in_payload_type":96},
    "jitter_buffer":{"init_delay":30, "adaptation_type":"none", "depth":200, "min_delay":0, "max_delay":100}
  }
}}
"""

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)

#send tool configuration messages to the SurfHMP
hmp.send_cmd(voice_1)
hmp.send_cmd(isdn_2)
hmp.send_cmd(mobile_3)
hmp.send_cmd(voice_4)

while True:
	time.sleep(10)

