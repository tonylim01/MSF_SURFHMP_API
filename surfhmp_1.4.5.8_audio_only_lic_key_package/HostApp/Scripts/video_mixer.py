#!/usr/bin/env python

from general_params import *
from hmp_connection import *
from hmp_messages import *

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)

#participant 1
msg = createUdpToolMsg(0, [1], 15000, 5000, REMOTE_IP)
hmp.send_json_cmd(msg)
msg = createRtpToolMsg(1, [2], [0], True, 107, "H.264", True, 107)
hmp.send_json_cmd(msg)
msg = createVideoDecoderMsg(2, [1000])
hmp.send_json_cmd(msg)

#participant 2
msg = createUdpToolMsg(10, [11], 15002, 5002, REMOTE_IP)
hmp.send_json_cmd(msg)
msg = createRtpToolMsg(11, [12], [10], True, 107, "H.264", True, 107)
hmp.send_json_cmd(msg)
msg = createVideoDecoderMsg(12, [1000])
hmp.send_json_cmd(msg)

#participant 3
msg = createUdpToolMsg(20, [21], 15004, 5004, REMOTE_IP)
hmp.send_json_cmd(msg)
msg = createRtpToolMsg(21, [22], [20], True, 107, "H.264", True, 107)
hmp.send_json_cmd(msg)
msg = createVideoDecoderMsg(22, [1000])
hmp.send_json_cmd(msg)

#participant 4
msg = createUdpToolMsg(30, [31], 15006, 5006, REMOTE_IP)
hmp.send_json_cmd(msg)
msg = createRtpToolMsg(31, [32], [30], True, 107, "H.264", True, 107)
hmp.send_json_cmd(msg)
msg = createVideoDecoderMsg(32, [1000])
hmp.send_json_cmd(msg)

#video mixer
msg = createVideoMixerMsg(1000, [2000], 1920, 1080, 30)
videoMixerAddParticipant(msg, 0, 2)
videoMixerAddParticipant(msg, 1, 12)
videoMixerAddParticipant(msg, 2, 22)
videoMixerAddParticipant(msg, 3, 32)
videoMixerAddLayoutCmd(msg, 0, 0, 0, 0, 0, 100,  60,  800, 448, True)
videoMixerAddLayoutCmd(msg, 1, 0, 0, 0, 0, 1020, 60,  800, 448, True)
videoMixerAddLayoutCmd(msg, 2, 0, 0, 0, 0, 100,  572, 800, 448, True)
videoMixerAddLayoutCmd(msg, 3, 0, 0, 0, 0, 1020, 572, 800, 448, True)
hmp.send_json_cmd(msg)

#encoder
msg = createVideoEncoderMsg(2000, [1, 11, 21, 31], 'H.264', 0, 0, 'VBR', 4000, 'balanced', 'baseline', '5.0')
hmp.send_json_cmd(msg)

while True:
	time.sleep(10)

