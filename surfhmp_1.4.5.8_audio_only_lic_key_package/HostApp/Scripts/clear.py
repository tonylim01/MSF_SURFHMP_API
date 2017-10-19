#!/usr/bin/env python

from general_params import *
from hmp_connection import *

hmp = HmpConnection()
print HMP_IP
print HMP_PORT
hmp.connect(HMP_IP, HMP_PORT)
time.sleep(1)
hmp.remove_all_tools()
time.sleep(1)
hmp.disableSystemStatus('all')
hmp.disconnect()


