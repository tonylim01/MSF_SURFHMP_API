#!/usr/bin/env python

from general_params import *
from hmp_connection import *
from hmp_messages import *

NOF_CHANNELS = 500

CODEC_A = 'G.711alaw'
#CODEC_A = 'G.711ulaw'
#CODEC_A = 'G.729'
#CODEC_A = 'AMR_WB'
#CODEC_A = 'AMR_NB'
#CODEC_A = 'linear'

#CODEC_B = 'G.711alaw'
#CODEC_B = 'G.711ulaw'
CODEC_B = 'G.729'
#CODEC_B = 'AMR_WB'
#CODEC_B = 'AMR_NB'
#CODEC_B = 'linear'

def convertCodecToPayloadType(codec):
	if 'G.711alaw' == codec:
		return 8
	elif 'G.711ulaw' == codec:
		return 0
	elif 'G.729' == codec:
		return 18
	else:
		return 120

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)
hmp.enableSystemStatus('performance', 1000)
hmp.enableSystemStatus('network', 1000)

req_id = 1000;
src_port = 5000;
dst_port = 6000;
codec_a_pt = convertCodecToPayloadType(CODEC_A)
codec_b_pt = convertCodecToPayloadType(CODEC_B)

for index in range(0,NOF_CHANNELS):
	msg = createVoiceFeMsg(index, index + NOF_CHANNELS, CODEC_A, src_port, dst_port, REMOTE_IP, codec_a_pt)
	hmp.send_json_cmd(msg)
	msg = createVoiceFeMsg(index + NOF_CHANNELS, index, CODEC_B, src_port + 20000, dst_port + 20000, REMOTE_IP, codec_b_pt)
	hmp.send_json_cmd(msg)
	src_port += 2
	dst_port += 2

while True:
	time.sleep(10)

