from ultrasonic import Ultrasonic
from imu import Imu
from motor import Motor
from tcp_connection import TCPConn
import time
import json
import numpy as np


class Main:
    def __init__(self):
        self.ultrasonic_right = Ultrasonic(27,17)
        self.ultrasonic_middle = Ultrasonic(23,22)
        self.ultrasonic_left = Ultrasonic(25,24)
        self.motor_left = Motor(5, 6)
        self.motor_right = Motor(12,13)
        self.imu = Imu()

        self.socket = TCPConn('localhost', 65432)

        self.q_table = np.load("qtable.npy")

    def send_to_server(self):
        message = {"distance_right": self.ultrasonic_right.get_distance(), "distance_middle": self.ultrasonic_middle.get_distance(), "distance_left": self.ultrasonic_left.get_distance(), "imu": list(self.imu.get_info())}
        message = json.dumps(message)
        self.socket.send(message)

    def encode_state(self, left, front, right, goal_dir):
        return (left << 4) | (front << 3) | (right << 2) | goal_dir

    def sense_walls(self, threshold=20):
        left  = 1 if self.ultrasonic_left.get_distance()  < threshold else 0
        front = 1 if self.ultrasonic_middle.get_distance() < threshold else 0
        right = 1 if self.ultrasonic_right.get_distance() < threshold else 0
        return left, front, right

    def update_heading(self, dt):
        gz = self.imu.get_gyro_z()
        self.heading += gz * dt

    def goal_direction(self):
        if -45 <= self.heading < 45:
            return 0   # forward
        elif 45 <= self.heading < 135:
            return 1   # left
        elif -135 <= self.heading < -45:
            return 2   # right
        else:
            return 3   # behind

    def choose_action(self, state):
        return int(np.argmax(self.q_table[state]))

    def do_action(self, action):
        left_dist = self.ultrasonic_left.get_distance()
        front_dist = self.ultrasonic_middle.get_distance()
        right_dist = self.ultrasonic_right.get_distance()

        # stop if too close
        if front_dist < 10:
            self.motor_left.stop()
            self.motor_right.stop()
            return

        if action == 0:      # FORWARD
            self.motor_left.forward(0.6)
            self.motor_right.forward(0.6)
        elif action == 1:    # LEFT
            self.motor_left.forward(0.4)
            self.motor_right.forward(0.7)
        elif action == 2:    # RIGHT
            self.motor_left.forward(0.7)
            self.motor_right.forward(0.4)



if __name__ == "__main__":
    main = Main()
    dt = 0.1
    main.heading = 0.0
    while True:
        main.update_heading(dt)
        walls = main.sense_walls()
        g = main.goal_direction()
        state = main.encode_state(*walls, g)
        state = min(state, main.q_table.shape[0]-1)
        action = main.choose_action(state)
        main.do_action(action)
        main.send_to_server()
        time.sleep(dt)



