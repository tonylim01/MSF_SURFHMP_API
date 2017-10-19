#!/usr/bin/env python

from general_params import *
from hmp_connection import *

NOF_CHANNELS = 100
OPUS_ENCODER_SAMPLING_RATE = 48000
OPUS_ENCODER_BIT_RATE = 120000
LINEAR_SIDE_REMOTE_IP = '127.0.0.1'
OPUS_SIDE_REMOTE_IP = '127.0.0.1'

udp_tool = """
{"tool_req":{
   "tool_id":__TOOL_ID__,
   "req_id":0,
   "req_type":"set_config",
    "data":{
     "tool_type":"udp_socket",
     "dst_tool_ids":[__DEST_TOOL_ID__],
     "local_udp_port":__LOCAL_PORT__,
     "remote_udp_port":__REMOTE_PORT__,
     "remote_ip":"__REMOTE_IP__",
     "events":[{"type":"source_address_changed", "enabled":true}]
  }
}}
"""

rtp_tool = """
{"tool_req":{
   "tool_id":__TOOL_ID__,
   "req_id":0,
   "req_type":"set_config",
    "data":{
     "tool_type":"rtp_session",
     "rtp_in":{
       "enabled":true,
       "dst_tool_ids":[__IN_DST_TOOL_ID__],
       "payload_types":[{"payload_type":120, "codec":"linear"},
                        {"payload_type":96, "codec":"opus"}]
     },
     "rtp_out":{
       "enabled":true,
       "dst_tool_ids":[__OUT_DST_TOOL_ID__],
       "payload_type":102
     }
  }
}}
"""

decoder = """
{"tool_req":{
  "tool_id":__TOOL_ID__,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"audio_decoder",
    "dst_tool_ids":[__DEST_TOOL_ID__],
    "out_sampling_rate":8000,
    "out_mono_stereo":"mono"
  }
}}
"""

opus_encoder = """
{"tool_req":{
  "tool_id":__TOOL_ID__,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"audio_encoder",
    "codec":"opus",
    "dst_tool_ids":[__DEST_TOOL_ID__],
    "out_sampling_rate":__OPUS_ENCODER_SAMPLING_RATE__,
    "out_mono_stereo":"mono",
    "opus":{
      "bitrate":__OPUS_ENCODER_BIT_RATE__,
      "bitrate_control":"VBR",
      "complexity":0,
      "mode":"audio"
    }
  }
}}
"""

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)
hmp.enableSystemStatus('performance', 1000)

tool_id = 0
linear_local_port = 5000
linear_remote_port = 15000
opus_local_port = 10000
opus_remote_port = 20000

for chan_num in range(0,NOF_CHANNELS):
	#prepare linear side udp socket tool configuration message
	linear_side_udp = udp_tool
	linear_side_udp = linear_side_udp.replace("__TOOL_ID__", str(tool_id))
	linear_side_udp = linear_side_udp.replace("__DEST_TOOL_ID__", str(tool_id+1))
	linear_side_udp = linear_side_udp.replace("__LOCAL_PORT__", str(linear_local_port))
	linear_side_udp = linear_side_udp.replace("__REMOTE_PORT__", str(linear_remote_port))
	linear_side_udp = linear_side_udp.replace("__REMOTE_IP__", LINEAR_SIDE_REMOTE_IP)

	#prepare linear side rtp session tool configuration message
	linear_side_rtp = rtp_tool
	linear_side_rtp = linear_side_rtp.replace("__TOOL_ID__", str(tool_id+1))
	linear_side_rtp = linear_side_rtp.replace("__IN_DST_TOOL_ID__", str(tool_id+3))
	linear_side_rtp = linear_side_rtp.replace("__OUT_DST_TOOL_ID__", str(tool_id))

	#prepare opus_encoder configuration message
	opus_encoder_temp = opus_encoder
	opus_encoder_temp = opus_encoder_temp.replace("__TOOL_ID__", str(tool_id+3))
	opus_encoder_temp = opus_encoder_temp.replace("__DEST_TOOL_ID__", str(tool_id+5))
	opus_encoder_temp = opus_encoder_temp.replace("__OPUS_ENCODER_BIT_RATE__", str(OPUS_ENCODER_BIT_RATE))
	opus_encoder_temp = opus_encoder_temp.replace("__OPUS_ENCODER_SAMPLING_RATE__", str(OPUS_ENCODER_SAMPLING_RATE))

	#prepare opus_decoder configuration message
	opus_decoder_temp = decoder
	opus_decoder_temp = opus_decoder_temp.replace("__TOOL_ID__", str(tool_id+4))
	opus_decoder_temp = opus_decoder_temp.replace("__DEST_TOOL_ID__", str(tool_id+1))

	#prepare opus side rtp session tool configuration message
	opus_side_rtp = rtp_tool
	opus_side_rtp = opus_side_rtp.replace("__TOOL_ID__", str(tool_id+5))
	opus_side_rtp = opus_side_rtp.replace("__IN_DST_TOOL_ID__", str(tool_id+4))
	opus_side_rtp = opus_side_rtp.replace("__OUT_DST_TOOL_ID__", str(tool_id+6))

	#prepare opus side udp socket tool configuration message
	opus_side_udp = udp_tool
	opus_side_udp = opus_side_udp.replace("__TOOL_ID__", str(tool_id+6))
	opus_side_udp = opus_side_udp.replace("__DEST_TOOL_ID__", str(tool_id+5))
	opus_side_udp = opus_side_udp.replace("__LOCAL_PORT__", str(opus_local_port))
	opus_side_udp = opus_side_udp.replace("__REMOTE_PORT__", str(opus_remote_port))
	opus_side_udp = opus_side_udp.replace("__REMOTE_IP__", OPUS_SIDE_REMOTE_IP)

	#send the configuration messages to the HMP
	hmp.send_cmd(linear_side_udp)
	hmp.send_cmd(linear_side_rtp)
	hmp.send_cmd(opus_encoder_temp)
	hmp.send_cmd(opus_decoder_temp)
	hmp.send_cmd(opus_side_rtp)
	hmp.send_cmd(opus_side_udp)

	linear_local_port += 2
	linear_remote_port += 2 
	opus_local_port += 2
	opus_remote_port += 2

	tool_id += 10

while True:
	time.sleep(10)

