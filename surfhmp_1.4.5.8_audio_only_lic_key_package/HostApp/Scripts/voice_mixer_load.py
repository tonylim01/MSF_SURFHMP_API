#!/usr/bin/env python

from general_params import *
from hmp_connection import *

NOF_CONFERENCES = 1
NOF_PARTICIPANTS = 1
NOF_ACTIVE_PARTICIPANTS = 10
CODEC = 'G.711alaw'
#CODEC = 'G.711ulaw'
#CODEC = 'G.729'
#CODEC = 'AMR_WB'
#CODEC = 'AMR_NB'

voice_config = """
{"tool_req":{
  "tool_id":__TOOL_ID__,
  "req_id":0,
  "req_type":"set_config",
  "data":{
    "tool_type":"voice_fe_ip",
    "backend_tool_id":__MIXER_TOOL_ID__,
    "decoder":{"type":"__CODEC__"},
    "encoder":{"type":"__CODEC__"},
    "RTP":
    {
      "local_udp_port":__SRC_PORT__,
      "remote_udp_port":__DST_PORT__,
      "remote_ip":"__REMOTE_IP__",
      "in_payload_type":__IN_PT__,
      "out_payload_type":__OUT_PT__
    }
  }
}}
"""

mixer_config = """
{"tool_req":{
   "tool_id":__MIXER_TOOL_ID__,
   "req_id":0,
   "req_type":"set_config",
     "data":{
       "tool_type":"voice_mixer",
       "sampling_rate":8000,
       "hangover_period":500,
       "dominant_speakers":5
     }
}}
"""


mixer_add_part = """
{"tool_req":{
   "tool_id":__MIXER_TOOL_ID__,
   "req_id":0,
   "req_type":"set_config",
     "data":{
       "participants":[{"id":__PAR_ID__, "type":"__PAR_TYPE__", "tool_id":__PAR__TOOL_ID__}]
     }
}}
"""


hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)
hmp.enableSystemStatus('performance', 1000)

#set correct IP
voice_config = voice_config.replace("__REMOTE_IP__", REMOTE_IP)

#set the input and output payload types
if 'G.711alaw' == CODEC:
	voice_config = voice_config.replace("__IN_PT__", "8")
	voice_config = voice_config.replace("__OUT_PT__", "8")
elif 'G.711ulaw' == CODEC:
	voice_config = voice_config.replace("__IN_PT__", "0")
	voice_config = voice_config.replace("__OUT_PT__", "0")
elif 'G.729' == CODEC:
	voice_config = voice_config.replace("__IN_PT__", "18")
	voice_config = voice_config.replace("__OUT_PT__", "18")
elif 'AMR_WB' == CODEC:
	voice_config = voice_config.replace("__IN_PT__", "120")
	voice_config = voice_config.replace("__OUT_PT__", "120")
elif 'AMR_NB' == CODEC:
	voice_config = voice_config.replace("__IN_PT__", "120")
	voice_config = voice_config.replace("__OUT_PT__", "120")

voice_tool_id = 0
src_port = 5000;
dst_port = 6000;

for conf_num in range(0,NOF_CONFERENCES):
	mixer_tool_id = 20000 + conf_num
	par_id = 0;
	#voice front-ends
	mixer_config_temp = mixer_config
	mixer_config_temp = mixer_config_temp.replace("__MIXER_TOOL_ID__", str(mixer_tool_id))
	hmp.send_cmd(mixer_config_temp)

	for i in range(0,NOF_PARTICIPANTS):
		voice_config_temp = voice_config
		voice_config_temp = voice_config_temp.replace("__CODEC__", CODEC)
		voice_config_temp = voice_config_temp.replace("__TOOL_ID__", str(voice_tool_id))
		voice_config_temp = voice_config_temp.replace("__SRC_PORT__", str(src_port))
		voice_config_temp = voice_config_temp.replace("__DST_PORT__", str(dst_port))
		voice_config_temp = voice_config_temp.replace("__MIXER_TOOL_ID__", str(mixer_tool_id))
		hmp.send_cmd(voice_config_temp)
		print voice_config_temp
		src_port += 2
		dst_port += 2
		mixer_add_part_temp = mixer_add_part
		mixer_add_part_temp = mixer_add_part_temp.replace("__MIXER_TOOL_ID__", str(mixer_tool_id))
		mixer_add_part_temp = mixer_add_part_temp.replace("__PAR_ID__", str(par_id))
		mixer_add_part_temp = mixer_add_part_temp.replace("__PAR__TOOL_ID__", str(voice_tool_id))
		if i < NOF_ACTIVE_PARTICIPANTS:
			mixer_add_part_temp = mixer_add_part_temp.replace("__PAR_TYPE__", "regular")
		else:
			mixer_add_part_temp = mixer_add_part_temp.replace("__PAR_TYPE__", "listener")
		hmp.send_cmd(mixer_add_part_temp)		
		par_id += 1
		voice_tool_id += 1

while True:
	time.sleep(10)

