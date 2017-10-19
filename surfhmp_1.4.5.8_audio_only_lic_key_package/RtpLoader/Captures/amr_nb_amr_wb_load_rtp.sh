#!/bin/bash
echo "start streaming"
for port in {5000..9000..2} 
do
  param="rtpplay -T -f amr_wb_oa.rtp 127.0.0.1/$port & "
  eval $param
done

for port in {25000..29000..2} 
do
  param="rtpplay -T -f amr_nb_122.rtp 127.0.0.1/$port & "
  eval $param
done



