#!/bin/bash

mkdir load

for i in `seq 0 3000`;
do
  filename="californication$i.wav"
  cp californication.wav load/$filename
done 

