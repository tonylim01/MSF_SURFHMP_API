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
    "EVG":{"enabled":true},
    "SRTP":{
      "contexts":[{
         "auth_tag_length":10,
         "authentication":"hmac_sha1",
         "encryption": "aes_icm",
         "id": 0,
         "master_key": "000102030405060708090a0b0c0d0e0f",
         "salt_key":   "000102030405060708090a0b0c0d"}],
      "rtcp_in_ctx_id": 0,
      "rtcp_out_ctx_id": 0,
      "rtp_in_ctx_id": 0,
      "rtp_out_ctx_id": 0
    }
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
    "EVG":{"enabled":true},
    "SRTP":{
      "contexts":[{
         "auth_tag_length":10,
         "authentication":"hmac_sha1",
         "encryption": "aes_icm",
         "id": 0,
         "master_key": "000102030405060708090a0b0c0d0e0f",
         "salt_key":   "000102030405060708090a0b0c0d"}],
      "rtcp_in_ctx_id": 0,
      "rtcp_out_ctx_id": 0,
      "rtp_in_ctx_id": 0,
      "rtp_out_ctx_id": 0
    }
  }
}}
"""

cmd_gen_tone_inband = """
{"tool_req":{
   "tool_id":1,
   "req_id":0,
   "req_type":"command",
   "data":{
     "cmd_type":"generate_tone",
     "rtp_or_inband":"inband",
     "tone_type":"predefined",
     "total_duration":300,
     "predefined_event":{
       "amplitude1":-15,
       "amplitude2":-15,
       "on_duration":50,
       "off_duration":50,
       "digits":["DTMF5","DTMF6","DTMF7"]}
   }
}}
"""

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)
#send tool configuration messages to the MediaProcessor
hmp.send_cmd(cmd_configure_tool1)
hmp.send_cmd(cmd_configure_tool2)

while True:
	time.sleep(2)
	hmp.send_cmd(cmd_gen_tone_inband)

