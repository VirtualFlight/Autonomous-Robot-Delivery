import socket
import json

class TCPConn:
    def __init__(self, address, port):
        self.server_address = (address, port)
        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client_socket.connect(self.server_address)

    def send(self, message):
        self.client_socket.sendall(message.encode('utf-8'))
    
    def close(self):
        self.client_socket.close()