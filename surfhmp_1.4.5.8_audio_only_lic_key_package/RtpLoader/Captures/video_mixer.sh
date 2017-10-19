#!/bin/bash
echo "start streaming"
eval "rtpplay -T -f h264_720p_30fps_0.rtp 127.0.0.1/15000 &"
eval "rtpplay -T -f h264_720p_30fps_1.rtp 127.0.0.1/15002 &"
eval "rtpplay -T -f h264_720p_30fps_2.rtp 127.0.0.1/15004 &"
eval "rtpplay -T -f h264_720p_30fps_3.rtp 127.0.0.1/15006 &"

