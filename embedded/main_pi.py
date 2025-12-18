from ultrasonic import Ultrasonic
from imu import Imu
from motor import Motor
from tcp_connection import TCPConn
import time
import json

class Main:
    def __init__(self):
        self.ultrasonic_right = Ultrasonic(27,17)
        self.ultrasonic_middle = Ultrasonic(23,22)
        self.ultrasonic_left = Ultrasonic(25,24)
        self.motor_left = Motor(5, 6)
        self.motor_right = Motor(12,13)
        self.imu = Imu()

        self.socket = TCPConn('localhost', 65432)

    def send_to_server(self):
        message = {"distance_right": self.ultrasonic_right.get_distance(), "distance_middle": self.ultrasonic_middle.get_distance(), "distance_left": self.ultrasonic_left.get_distance(), "imu": list(self.imu.get_info())}
        message = json.dumps(message)
        self.socket.send(message)

if __name__ == "__main__":
    main = Main()
    while True:
        main.send_to_server()
        time(0.5)
