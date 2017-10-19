#!/bin/bash
echo "start streaming"
eval "rtpplay -T -f h264_720p_30fps_0.rtp 127.0.0.1/15000 &"

