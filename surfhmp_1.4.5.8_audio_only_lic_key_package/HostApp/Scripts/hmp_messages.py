def createToolReqMsg(tool_id, req_type):
	msg = {}
	msg['tool_req'] = {}
	msg['tool_req']['req_type'] = req_type
	msg['tool_req']['tool_id'] = tool_id
	msg['tool_req']['req_id'] = 0
	msg['tool_req']['data'] = {}
	return msg

def addToolTrace(msg, trace_id, trace_file):
	trace = {}
	trace['id'] = trace_id
	trace['file'] = trace_file
	if(not 'traces' in msg['tool_req']['data']):
		msg['tool_req']['data']['traces'] = []
	msg['tool_req']['data']['traces'].append(trace)

def enableStatus(msg, stat_type, period):
	status = {}
	status['type'] = stat_type
	status['period'] = period
	if(not 'status' in msg['tool_req']['data']):
		msg['tool_req']['data']['status'] = []
	msg['tool_req']['data']['status'].append(status)

def enableEvent(msg, ev_type, enabled):
	ev = {}
	ev['type'] = ev_type
	ev['enabled'] = enabled
	if(not 'events' in msg['tool_req']['data']):
		msg['tool_req']['data']['events'] = []
	msg['tool_req']['data']['events'].append(ev)

def createVoiceFeMsg(tool_id, backend_tool, codec, local_port, remote_port, remote_ip, payload_type):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'voice_fe_ip'
	msg['tool_req']['data']['backend_tool_id'] = backend_tool
	msg['tool_req']['data']['decoder'] = {}
	msg['tool_req']['data']['decoder']['type'] = codec
	msg['tool_req']['data']['encoder'] = {}
	msg['tool_req']['data']['encoder']['type'] = codec
	msg['tool_req']['data']['RTP'] = {}
	msg['tool_req']['data']['RTP']['local_udp_port'] = local_port
	msg['tool_req']['data']['RTP']['remote_udp_port'] = remote_port
	msg['tool_req']['data']['RTP']['remote_ip'] = remote_ip
	msg['tool_req']['data']['RTP']['in_payload_type'] = payload_type
	msg['tool_req']['data']['RTP']['out_payload_type'] = payload_type
	return msg

def createUdpToolMsg(tool_id, dst_tools, local_port, remote_port, remote_ip):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'udp_socket'
	msg['tool_req']['data']['dst_tool_ids'] = dst_tools
	msg['tool_req']['data']['local_udp_port'] = local_port
	msg['tool_req']['data']['remote_udp_port'] = remote_port
	msg['tool_req']['data']['remote_ip'] = remote_ip
	msg['tool_req']['data']['rcv_buff_size'] = 200000
	msg['tool_req']['data']['send_buff_size'] = 200000
	return msg

def createRtpToolMsg(tool_id, dst_tools_rx, dst_tools_tx, rtp_rx_on, rx_pt, rx_codec, rtp_tx_on, tx_pt):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'rtp_session'
	msg['tool_req']['data']['rtp_in'] = {}
	msg['tool_req']['data']['rtp_in']['dst_tool_ids'] = dst_tools_rx
	msg['tool_req']['data']['rtp_in']['enabled'] = rtp_rx_on
	msg['tool_req']['data']['rtp_in']['payload_types'] = []
	msg['tool_req']['data']['rtp_in']['payload_types'].append({})
	msg['tool_req']['data']['rtp_in']['payload_types'][0]['payload_type'] = rx_pt
	msg['tool_req']['data']['rtp_in']['payload_types'][0]['codec'] = rx_codec
	msg['tool_req']['data']['rtp_out'] = {}
	msg['tool_req']['data']['rtp_out']['dst_tool_ids'] = dst_tools_tx
	msg['tool_req']['data']['rtp_out']['enabled'] = rtp_tx_on
	msg['tool_req']['data']['rtp_out']['payload_type'] = tx_pt
	msg['tool_req']['data']['rtp_out']['init_seq_num'] = 0
	msg['tool_req']['data']['rtp_out']['ssrc'] = 0x12344321
	msg['tool_req']['data']['rtp_out']['init_timestamp'] = 10000000
	return msg

def createVideoDecoderMsg(tool_id, dst_tools):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'video_decoder'
	msg['tool_req']['data']['dst_tool_ids'] = dst_tools
	return msg

def createVideoEncoderMsg(tool_id, dst_tools, codec, out_width, out_height, BRC, bitrate, preset, profile, level):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'video_encoder'
	msg['tool_req']['data']['dst_tool_ids'] = dst_tools
	msg['tool_req']['data']['codec'] = codec
	msg['tool_req']['data']['bitrate'] = bitrate
	msg['tool_req']['data']['bitrate_control'] = BRC
	msg['tool_req']['data']['preset'] = preset
	msg['tool_req']['data']['H.264'] = {}
	msg['tool_req']['data']['H.264']['profile'] = profile
	msg['tool_req']['data']['H.264']['level'] = level
	msg['tool_req']['data']['out_width'] = out_width
	msg['tool_req']['data']['out_height'] = out_height
	return msg

def createVideoFileReaderMsg(tool_id, dst_tools, filename, framerate, codec):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'video_file_reader'
	msg['tool_req']['data']['dst_tool_ids'] = dst_tools
	msg['tool_req']['data']['file_name'] = filename
	msg['tool_req']['data']['framerate'] = framerate
	msg['tool_req']['data']['codec'] = codec
	return msg

def createVideoMixerMsg(tool_id, dst_tools, width, height, framerate):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'video_mixer'
	msg['tool_req']['data']['dst_tool_ids'] = dst_tools
	msg['tool_req']['data']['width'] = width
	msg['tool_req']['data']['height'] = height
	msg['tool_req']['data']['framerate'] = framerate
	msg['tool_req']['data']['background'] = {}
	msg['tool_req']['data']['background']['R'] = 80
	msg['tool_req']['data']['background']['G'] = 80
	msg['tool_req']['data']['background']['B'] = 105
	return msg

def videoMixerAddParticipant(msg, par_id, tool_id):
	par = {}
	par['par_id'] = par_id
	par['tool_id'] = tool_id
	if(not 'participants' in msg['tool_req']['data']):
		msg['tool_req']['data']['participants'] = []
	msg['tool_req']['data']['participants'].append(par)

def videoMixerAddLayoutCmd(msg, par_id, src_x, src_y, src_w, src_h, dst_x, dst_y, dst_w, dst_h, preserve_aspect_ratio):
	cmd = {}
	cmd['par_id'] = par_id
	cmd['src_x'] = src_x
	cmd['src_y'] = src_y
	cmd['src_w'] = src_w
	cmd['src_h'] = src_h
	cmd['dst_x'] = dst_x
	cmd['dst_y'] = dst_y
	cmd['dst_w'] = dst_w
	cmd['dst_h'] = dst_h
	cmd['preserve_aspect_ratio'] = preserve_aspect_ratio
	if(not 'layout' in msg['tool_req']['data']):
		msg['tool_req']['data']['layout'] = []
	msg['tool_req']['data']['layout'].append(cmd)


def createFileReaderMsg(tool_id, audio_enable, audio_dst_tools, video_enable, video_dst_tools):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'file_reader'
	msg['tool_req']['data']['audio_enabled'] = audio_enable
	msg['tool_req']['data']['audio_dst_tool_ids'] = audio_dst_tools
	msg['tool_req']['data']['video_enabled'] = video_enable
	msg['tool_req']['data']['video_dst_tool_ids'] = video_dst_tools
	return msg     
      
def createFileWriterMsg(tool_id):
	msg = createToolReqMsg(tool_id, 'set_config')
	msg['tool_req']['data']['tool_type'] = 'file_writer'
	return msg     
