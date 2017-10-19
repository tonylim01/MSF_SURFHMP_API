#!/usr/bin/env python

import socket
import json
import struct
import time
import threading
import sys
import QtGui, QtCore

HMP_IP = '192.168.5.63'
HMP_PORT = 7777

#connect message
cmd_connect = """{"connect":{"api_version":[1,2],"keep_alive_timeout":0}}"""
cmd_sys_config = """{"sys_req":{"req_id":0,"req_type":"set_config","data":{"status":[{"type":"all","period":1000}]}}}"""
get_tools = """{"sys_req":{"req_id":0,"req_type":"get_tools_list"}}"""
tool_get_config = """{"tool_req":{"req_id":0,"tool_id":0,"req_type":"get_config"}}"""
tool_get_status = """{"tool_req":{"req_id":0, "req_type":"get_status", "tool_id":0, "data":{"type":"all"}}}"""

#sends JSON text command in format "4 bytes length + message"
def send_cmd(sock, cmd):
	#json.loads was added only for validation of the outgoing JSON message
	json.loads(cmd)
	sock.send(struct.pack('<I', len(cmd)))
	sock.send(cmd)
	return

#receives "length" number of bytes from socket, blocks until required number of bytes was received
def recv_bytes(sock, length):
	recv_length = 0
	res = ''
	while recv_length < length:
		res = res + sock.recv(length - recv_length)
		recv_length = len(res)
	return res

class SurfView(QtGui.QMainWindow):
    
	def __init__(self):
	        super(SurfView, self).__init__()
        
	        self.initUI()
 
	#procedure that runs in a separate thread and reads messages from the socket
	def recv_proc(self):
		#receive api pass phrase	
		psw = recv_bytes(self.sock, 7)
		if 'surfapi' != psw:
			print "Error: wrong pass phrase received from the server!"
		else:
			print "Successfully connected to the MediaProcessor"
		while True:
			bin_len = recv_bytes(self.sock, 4)
			length = struct.unpack('<I', bin_len)
			length = length[0]
			if 0 != length:
				msg = recv_bytes(self.sock, length)
				self.handleMessage(msg)		
				#print msg
		return

	#the following function handles single message received from the MediaProcessor
	def handleMessage(self, msg):
		parser = json.loads(msg)
		keys = list(parser.keys())
		#connect message is the first message to receive from the MediaProcessor
		if 'connect' == keys[0]:
			print "Media Processor API version: [", parser['connect']['api_version'][0],".",parser['connect']['api_version'][1],"]"
		#sys_ans is the answer to sys_req message which is used to configure the system 
		elif 'sys_ans' == keys[0]:
			if 'set_config' == parser['sys_ans']['req_type']:
				if 0 == parser['sys_ans']['data']['error_code']:
					print "System parameters were successfully configured"
				else:
					print "System configuration ERROR:"
					print "    error code:", parser['sys_ans']['data']['error_code']
					print "    description:", parser['sys_ans']['data']['description']
			elif 'get_tools_list' == parser['sys_ans']['req_type']:
				self.tools_list = parser['sys_ans']['data']['tools']				
				self.emit(QtCore.SIGNAL('updateToolsList'))
		elif 'sys_inf' == keys[0]:
			if 'status' == parser['sys_inf']['inf_type']:
				if 'performance' == parser['sys_inf']['data']['type'] :
					print "CPU usage:", parser['sys_inf']['data']['CPU']['CPU_usage']," nof_late_iterations:",parser['sys_inf']['data']['CPU']['nof_late_sched_iterations']
		elif 'tool_inf' == keys[0]:
			if 'status' == parser['tool_inf']['inf_type']:
				tool_id = parser['tool_inf']['tool_id']
				if tool_id == self.selected_tool_id:
					stat_type = parser['tool_inf']['data']['type']
					self.status_table[stat_type] = parser['tool_inf']['data']
					self.emit(QtCore.SIGNAL('updateStatus'))
					#self.updateStatus()
		elif 'tool_ans' == keys[0]:
			if 'get_config' == parser['tool_ans']['req_type']:
				tool_id = parser['tool_ans']['tool_id']
				if tool_id == self.selected_tool_id:
					self.config_table = parser['tool_ans']['data']
					self.emit(QtCore.SIGNAL('updateConfig'))
					#self.updateConfig()
		return

	def tableSelChanged(self, selected, deselected):
		row = self.table.currentRow()
		self.selected_tool_id = self.table.item(row, 0).data(0).toInt()[0]
		self.status_tree.clear()
		self.config_tree.clear()
		self.config_table.clear()
		self.status_table.clear()
		#get status end events for this tool
		parser = json.loads(tool_get_status)
		parser['tool_req']['tool_id'] = self.selected_tool_id
		send_cmd(self.sock, json.dumps(parser))
		#get current configuration of the tool
		parser = json.loads(tool_get_config)
		parser['tool_req']['tool_id'] = self.selected_tool_id
		send_cmd(self.sock, json.dumps(parser))
		return

	def parse_msg_recursive(self, data, item, msg_type):
		self.recursivity += 1
		if type(data) is dict:				
			for key in data:
				if self.recursivity == 2 and key == 'type': pass
				elif self.recursivity == 1 and key == 'tool_type': pass
				elif self.recursivity == 1 and key == 'description': pass
				elif self.recursivity == 1 and key == 'error_code': pass
				else:
					if (type(data[key]) is dict) or (type(data[key]) is list):
						subitem = self.findItem(item, key)
						if subitem is None:
							sl = QtCore.QStringList(key)		
							subitem = QtGui.QTreeWidgetItem(sl)
							if msg_type == 0:							
								subitem.setIcon(0,self.icons["info"])
							elif msg_type == 1:
								subitem.setIcon(0,self.icons["config"])
							item.addChild(subitem)
							subitem.setExpanded(True)
						self.parse_msg_recursive(data[key], subitem, msg_type)
					else:
						new_item = self.findItem(item, key)
						if new_item is None:
							sl = QtCore.QStringList(key)		
							sl.append(str(data[key]))
							new_item = QtGui.QTreeWidgetItem(sl)
							#new_item.setIcon(0,self.icons["config"]);
							item.addChild(new_item)
						else:
							new_item.setText(1, str(data[key]))
		elif type(data) is list:
			index = 0
			for datum in data:
				if (type(datum) is dict) or (type(datum) is list):
					subitem = self.findItem(item, str(index))
					if subitem is None:
						sl = QtCore.QStringList(str(index))		
						subitem = QtGui.QTreeWidgetItem(sl)
						if msg_type == 0:							
							subitem.setIcon(0,self.icons["info"])
						elif msg_type == 1:
							subitem.setIcon(0,self.icons["config"])
						item.addChild(subitem)
						subitem.setExpanded(True)
					self.parse_msg_recursive(datum, subitem, msg_type)
				else:
					new_item = self.findItem(item, str(index))
					if new_item is None:
						sl = QtCore.QStringList(str(index))		
						sl.append(str(datum))
						new_item = QtGui.QTreeWidgetItem(sl)
						#new_item.setIcon(0,QIcon("surf.png"));
						item.addChild(new_item)
					else:
						new_item.setText(1, str(datum))
				index += 1
		self.recursivity -= 1
		return

	def findItem(self, item, name):
		for i in range(item.childCount()):
			subitem = item.child(i)
			if name == subitem.text(0):
				return subitem
		return None

	def updateStatus(self):
		#self.mutex.acquire()
		if self.selected_tool_id != -1:
			self.parse_msg_recursive(self.status_table, self.status_tree.invisibleRootItem(), 0)
		self.status_tree.resizeColumnToContents(0)
		#self.mutex.release()
		return

	def updateConfig(self):		
		if self.selected_tool_id != -1:
			self.parse_msg_recursive(self.config_table, self.config_tree.invisibleRootItem(), 1)
		self.config_tree.resizeColumnToContents(0)
		return

	def updateToolsList(self):
		index = 0
		for tool in self.tools_list:
			self.nof_tools += 1
			self.table.setRowCount(self.nof_tools)
			tool_id = tool['tool_id']				
			self.table.setItem(index, 0, QtGui.QTableWidgetItem(str(tool_id)))
			if 'app_info' in tool:				
				self.table.setItem(index, 1, QtGui.QTableWidgetItem(tool['app_info']))
			self.table.setItem(index, 2, QtGui.QTableWidgetItem(tool['tool_type']))
			index += 1
		self.table.resizeColumnsToContents()

	def send_get_config_cmd(self):
		while True:
			if self.selected_tool_id != -1:
				parser = json.loads(tool_get_status)
				parser['tool_req']['tool_id'] = self.selected_tool_id
				send_cmd(self.sock, json.dumps(parser))
			time.sleep(1)
		return

	def configTreeItemClicked(self, item, column):
		if column > 0:
			self.config_tree.editItem(item, column)
      
	def initUI(self):
		self.mutex = threading.Lock()

		self.icons = {}
		self.icons["config"] = QtGui.QIcon("icons/config.png")
		self.icons["info"] = QtGui.QIcon("icons/info.png")

		self.status_table = {}
		self.config_table = {}
		self.selected_tool_id = -1
		self.nof_tools = 0
		self.recursivity = 0

		self.connect(self, QtCore.SIGNAL('updateStatus'), self.updateStatus)
		self.connect(self, QtCore.SIGNAL('updateConfig'), self.updateConfig)
		self.connect(self, QtCore.SIGNAL('updateToolsList'), self.updateToolsList)

		#create TCP socket and connect to the MediaProcessor
		self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		server_address = (HMP_IP, HMP_PORT)
		self.sock.connect(server_address)

		#create socket reading thread
		self.t = threading.Thread(target=self.recv_proc, args = ())
		self.t.daemon = True
		self.t.start()

		#initiate SURF API handshake
		self.sock.send("surfapi")
		#send "connect" message to the MediaProcessor
		send_cmd(self.sock, cmd_connect)
		#send system configuration message to the MediaProcessor
		send_cmd(self.sock, cmd_sys_config)
		
		self.table = QtGui.QTableWidget(0,3,self)
		headers = []
		headers.append('tool ID')
		headers.append('app info')
		headers.append('tool type')
		self.table.setHorizontalHeaderLabels(headers)
		self.table.setVerticalHeaderLabels([])
		self.table.setSelectionBehavior(QtGui.QAbstractItemView.SelectRows)
		self.table.setSelectionMode(QtGui.QAbstractItemView.SingleSelection)
		self.table.selectionModel().selectionChanged.connect(self.tableSelChanged)
		self.table.setEditTriggers(QtGui.QAbstractItemView.NoEditTriggers)
		self.table.horizontalHeader().setStretchLastSection(True)

		self.status_tree = QtGui.QTreeWidget(self)
		self.status_tree.setColumnCount(2)
		headers = []
		headers.append('name')
		headers.append('value')
		self.status_tree.setHeaderLabels(headers)

		self.config_tree = QtGui.QTreeWidget(self)
		self.config_tree.setColumnCount(2)
		self.config_tree.setHeaderLabels(headers)
		self.config_tree.itemDoubleClicked.connect(self.configTreeItemClicked)

		lbl_table = QtGui.QLabel('Tools:')
		lbl_status = QtGui.QLabel('Status:')
		lbl_config = QtGui.QLabel('Configuration:')

		tools_grid = QtGui.QGridLayout()
		#tools_grid.setSpacing(10)
		tools_grid.addWidget(lbl_table, 0, 0)
		tools_grid.addWidget(self.table, 1, 0)
		tools_list_widget = QtGui.QWidget()
		tools_list_widget.setLayout(tools_grid)
		
		status_grid = QtGui.QGridLayout()
		#status_grid.setSpacing(10)
		status_grid.addWidget(lbl_status, 0, 0)
		status_grid.addWidget(self.status_tree, 1, 0)
		status_widget = QtGui.QWidget()
		status_widget.setLayout(status_grid)

		config_grid = QtGui.QGridLayout()
		#config_grid.setSpacing(10)
		config_grid.addWidget(lbl_config, 0, 0)
		config_grid.addWidget(self.config_tree, 1, 0)
		config_widget = QtGui.QWidget()
		config_widget.setLayout(config_grid)

		self.splitter = QtGui.QSplitter(self)
		self.splitter.addWidget(tools_list_widget)
		self.splitter.addWidget(status_widget)
		self.splitter.addWidget(config_widget)
		self.splitter.setStretchFactor(0, 0)
		self.splitter.setStretchFactor(1, 1)
		self.splitter.setStretchFactor(2, 1)

		self.setCentralWidget(self.splitter)

		self.setWindowTitle('SurfView HMP Edition')
		#self.setWindowIcon(QtGui.QIcon('info.png'))

		connectAction = QtGui.QAction(QtGui.QIcon('icons/connect.png'), 'Connect', self)
		connectAction.setStatusTip('Connect')
		QtCore.QObject.connect(connectAction, QtCore.SIGNAL('triggered()'), self.reloadToolsList)
		self.statusBar()
		self.toolbar = self.addToolBar('Connect')
		self.toolbar.addAction(connectAction)

		refreshAction = QtGui.QAction(QtGui.QIcon('icons/refresh.png'), 'Refresh', self)
		refreshAction.setShortcut('F5')
		refreshAction.setStatusTip('Reload tools list')
		QtCore.QObject.connect(refreshAction, QtCore.SIGNAL('triggered()'), self.reloadToolsList)
		self.statusBar()
		self.toolbar = self.addToolBar('Refresh')
		self.toolbar.addAction(refreshAction)

		loadAction = QtGui.QAction(QtGui.QIcon('icons/load.png'), 'Load', self)
		loadAction.setStatusTip('Load configuration from file')
		QtCore.QObject.connect(loadAction, QtCore.SIGNAL('triggered()'), self.reloadToolsList)
		self.statusBar()
		self.toolbar = self.addToolBar('Load')
		self.toolbar.addAction(loadAction)

		saveAction = QtGui.QAction(QtGui.QIcon('icons/save.png'), 'Save', self)
		saveAction.setStatusTip('Save configuration to file')
		QtCore.QObject.connect(saveAction, QtCore.SIGNAL('triggered()'), self.reloadToolsList)
		self.statusBar()
		self.toolbar = self.addToolBar('Save')
		self.toolbar.addAction(saveAction)

		self.show()

		send_cmd(self.sock, get_tools)

		self.ts = threading.Thread(target=self.send_get_config_cmd, args = ())
		self.ts.daemon = True
		self.ts.start()
        
	def onActivated(self, text):
		self.lbl.setText(text)
		self.lbl.adjustSize()  

	def reloadToolsList(self):
		self.status_table = {}
		self.config_table = {}
		self.selected_tool_id = -1
		self.nof_tools = 0
		self.recursivity = 0
		self.config_tree.clear()
		self.status_tree.clear()
		send_cmd(self.sock, get_tools)
                
def main():
	app = QtGui.QApplication(sys.argv)		
	sv = SurfView()
	sys.exit(app.exec_())


if __name__ == '__main__':
	main()



	




