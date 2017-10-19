#!/usr/bin/env python

from general_params import *
from hmp_connection import *
from hmp_messages import *

hmp = HmpConnection()
hmp.connect(HMP_IP, HMP_PORT)

msg = createUdpToolMsg(0, [1], 15000, 5000, REMOTE_IP)
hmp.send_json_cmd(msg)

msg = createRtpToolMsg(1, [2], [0], True, 107, "H.264", True, 107)
hmp.send_json_cmd(msg)

msg = createVideoDecoderMsg(2, [3])
hmp.send_json_cmd(msg)

msg = createVideoEncoderMsg(3, [1], 'H.264', 640, 480, 'VBR', 1000, 'balanced', 'baseline', '5.0')
hmp.send_json_cmd(msg)

while True:
	time.sleep(10)

