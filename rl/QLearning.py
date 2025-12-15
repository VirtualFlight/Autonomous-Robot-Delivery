import numpy as np
import random
# import RPi.GPIO as GPIO # for rasberry pi only
import time


# --------------------------- ACTIONS ---------------------------
FORWARD = 0
LEFT = 1
RIGHT = 2
actions = [FORWARD, LEFT, RIGHT]

q_table = np.zeros((32, len(actions))) # 32 possible states (8 * 4, 4 from the goal_direction)

epsilon = 0.1 # chance it randomly explores instead of following the highest q-value
epsilon_min = 0.05
epsilon_decay = 0.995

learning_rate = 0.15 # how much we update old knowledge (low, stable)
discount_factor = 0.95 # long-term rewardd vs immediate (high, thoughtful)

NUM_EPISODES = 500
MAX_STEPS = 100

# --------------------------- GRID WORLD ---------------------------
# FOR SIMULATION ONLY. THIS MUST BE REPLACED WITH THE REAL SENSORS
GRID = [
    [0,0,0,0,0],
    [0,1,1,0,0],
    [0,0,0,1,0],
    [0,1,0,0,0],
    [0,0,0,0,0]
] # 0 = empty ; 1 = wall
START = (0,0)
GOAL = (4,4)

# heading = 0: up, 1: right, 2: down, 3: left
robot_x, robot_y = START
heading = 1    # RIGHT initially

def reset_robot():
    global robot_x, robot_y, heading
    robot_x, robot_y = START
    heading = 1
    return get_state()

# --------------------------- SENSORS ---------------------------

def sense_walls(): # TODO: REPLACE WITH REAL SENSORS
    global robot_x, robot_y, heading

    # direction vectors for headings
    dirs = [(0,-1),(1,0),(0,1),(-1,0)]   # up, right, down, left
    dx_f, dy_f = dirs[heading]

    dx_l, dy_l = dirs[(heading - 1) % 4] # left = heading -1
    dx_r, dy_r = dirs[(heading + 1) % 4] # right = heading +1

    def is_wall(x, y):
        if x < 0 or y < 0 or y >= len(GRID) or x >= len(GRID[0]): return 1
        return GRID[y][x]

    left  = is_wall(robot_x + dx_l, robot_y + dy_l)
    front = is_wall(robot_x + dx_f, robot_y + dy_f)
    right = is_wall(robot_x + dx_r, robot_y + dy_r)

    return (left, front, right)

def encode_state(walls, goal_dir): # binary to int 0–7
    left, front, right = walls
    return (left << 4) | (front << 3) | (right << 2) | goal_dir

def get_state():
    # left = GPIO.input(LEFT_SENSOR)
    # center = GPIO.input(CENTER_SENSOR)
    # right = GPIO.input(RIGHT_SENSOR)
    # return (left, center, right)

    return sense_walls()


# --------------------------- MOTION ---------------------------

def do_action(a): #TODO: REPLACE WITH REAL MOTOR CONTROL
    global heading, robot_x, robot_y
    if a == LEFT:
        heading = (heading-1) % 4
    elif a == RIGHT:
        heading = (heading+1) % 4
    elif a == FORWARD:
        dirs = [(0,-1), (1,0), (0,1), (-1,0)] # UP, RIGHT, DOWN, LEFT
        dx, dy = dirs[heading]
        new_x = robot_x + dx
        new_y = robot_y + dy

        if (0<=new_x<5) and (0<=new_y<5) and (GRID[new_y][new_x]==0): #only move if no wall
            robot_x = new_x
            robot_y = new_y

# --------------------------- REWARD ---------------------------

def get_reward(state, action, prev_dist): # TODO: ONLY DETECTS COLLISIONS AFTER CRASH, CHANGE TO ROBOT STOPS BEFORE COLLISIONS 
    global robot_x, robot_y

    if state[1]==1 and action==FORWARD: #crash penalty
        return -5, prev_dist
    
    new_dist = abs(robot_x - GOAL[0]) + abs(robot_y - GOAL[1])
    reward = 0.0

    if new_dist < prev_dist: # reward based on distance from goal
        reward += 1.0
    elif new_dist > prev_dist:
        reward -= 1.0

    if action != FORWARD: # turning slight penalty
        reward -= 0.05
    if (robot_x, robot_y) == GOAL: #reached goal
        reward += 20
    
    return reward, new_dist

def goal_direction():
    dx = GOAL[0] - robot_x
    dy = GOAL[1] - robot_y
    if abs(dx) > abs(dy):
        return 0 if dx > 0 else 1  # goal right / left
    else:
        return 2 if dy > 0 else 3  # goal down / up

# --------------------------- TRAINING LOOP ---------------------------

for episode in range(NUM_EPISODES):
    sensors = reset_robot()
    prev_dist = abs(robot_x - GOAL[0]) + abs(robot_y - GOAL[1])
    total_reward = 0

    for step in range(MAX_STEPS):
        g = goal_direction()
        s = encode_state(sensors, g)

        #epsilon-greedy strategy
        if (random.random() < epsilon): a = random.choice(actions) # Explore
        else: a = np.argmax(q_table[s])  # Exploit

        do_action(a)

        #new state
        new_sensors = get_state()
        g2 = goal_direction()
        s2 = encode_state(new_sensors, g2)
        reward, prev_dist = get_reward(new_sensors, a, prev_dist) # get reward/penalty
        total_reward += reward

        q_table[s,a] += learning_rate * (reward+discount_factor * np.max(q_table[s2]) - q_table[s,a])

        if (robot_x, robot_y) == GOAL:
            print("\n REACHED GOAL!\n")
            break
        
    epsilon = max(epsilon_min, epsilon * epsilon_decay)

    # print every 50 episodes
    if episode % 50 == 0:
        print(f"Episode {episode}, total reward = {total_reward:.1f}, epsilon = {epsilon:.2f}")

np.save("qtable.npy", q_table)
print("\nTraining complete. Q-table saved.\n")

# --------------------------- EXECUTION ---------------------------

q_table = np.load("qtable.npy")
epsilon = 0.0  # stop exploration
learning_rate = 0.0

sensors = reset_robot()

for _ in range(1): #TODO: REPLACE WITH INFINITE LOOP WHEN LINKED TO ROBOT
    g = goal_direction()
    s = encode_state(sensors, g)
    action = np.argmax(q_table[s])  # pure policy
    do_action(action)
    sensors = get_state()
    time.sleep(0.05)