#!/bin/bash
echo "start streaming"
for port in {5000..5198..2} 
do
  param="rtpplay -T -f gravity_linear.rtp 127.0.0.1/$port & "
  eval $param
done

for port in {10000..10198..2} 
do
  param="rtpplay -T -f gravity_opus_48000_60kbps.rtp 127.0.0.1/$port & "
  eval $param
done

