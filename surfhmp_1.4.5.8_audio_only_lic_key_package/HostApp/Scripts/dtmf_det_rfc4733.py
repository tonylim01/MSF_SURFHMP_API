#!/usr/bin/env python

from general_params import *
from hmp_connection import *

cmd_configure_tool1 = """
{"tool_req":{
  "tool_id":1,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"voice_p2p",
    "decoder":{"type":"G.711alaw"},
    "encoder":{"type":"G.711alaw", "packet_duration":20},
    "RTP":{"local_udp_port":5000, "remote_udp_port":6000, "remote_ip":"127.0.0.1", "dtmf_out_payload_type":101, "dtmf_in_payload_type":101},
    "EVG":{"enabled":true}
  }
}}
"""

cmd_configure_tool2 = """
{"tool_req":{
  "tool_id":2,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"voice_p2p",
    "events":[{"type":"all", "enabled":true}],
    "decoder":{"type":"G.711alaw"},
    "encoder":{"type":"G.711alaw", "packet_duration":20},
    "RTP":{"local_udp_port":6000, "remote_udp_port":7000, "remote_ip":"127.0.0.1", "dtmf_out_payload_type":101, "dtmf_in_payload_type":101},
    "EVD":{"enabled":true, "events":["DTMF_GROUP"]},
    "EVG":{"enabled":true}
  }
}}
"""

cmd_gen_tone = """
{"tool_req":{
   "tool_id":1,
   "req_id":0,
   "req_type":"command",
   "data":{
     "cmd_type":"generate_tone",
     "rtp_or_inband":"RTP",
     "ip_event":{"named_event":"DTMF6"},
     "total_duration":300
   }
}}
"""

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)
#send tool configuration messages to the MediaProcessor
hmp.send_cmd(cmd_configure_tool1)
hmp.send_cmd(cmd_configure_tool2)

while True:
	time.sleep(1)
	hmp.send_cmd(cmd_gen_tone)

