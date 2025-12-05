import numpy as np
import random
# import RPi.GPIO as GPIO # for rasberry pi only
import time


# --------------------------- ACTIONS ---------------------------
FORWARD = 0
LEFT = 1
RIGHT = 2
actions = [FORWARD, LEFT, RIGHT]

q_table = np.zeros((8, len(actions))) # 8 possible states

epsilon = 0.1
learning_rate = 0.15 # how much we update old knowledge (low, stable)
discount_factor = 0.95 # long-term rewardd vs immediate (high, thoughtful)

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

def encode_state(s): # binary to int 0–7
    left, front, right = s
    return left * 4 + front * 2 + right * 1

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

def get_reward(state, action):
    global robot_x, robot_y

    # TODO: ONLY DETECTS COLLISIONS AFTER CRASH, CHANGE TO ROBOT STOPS BEFORE COLLISIONS 

    if state[1]==1 and action==FORWARD: #crash penalty
        return -5
    if (robot_x, robot_y) == GOAL: #reached goal
        return +10
    if action == FORWARD and state[1] == 0: #forward progress reward
        return +1
    if action != FORWARD:
        return -0.1      # turning slight penalty
    
    return -0.2          # no progress penalty

# --------------------------- Q-LEARNING LOOP ---------------------------

if __name__ == "__main__":
    step=0
    sensors = reset_robot()

    while True:
        step+=1
        s = encode_state(sensors)

        #epsilon-greedy strategy
        if (random.random() < epsilon): a = random.choice(actions) # Explore
        else: a = np.argmax(q_table[s])  # Exploit

        do_action(a)
        time.sleep(0.05)

        #new state
        new_sensors = get_state()
        s2 = encode_state(new_sensors)
        reward = get_reward(new_sensors, a) # get reward/penalty

        q_table[s,a] += learning_rate * (reward+discount_factor * np.max(q_table[s2]) - q_table[s,a])

        # print every 50 steps
        if step % 50 == 0:
            print("\nQ-table snapshot:")
            print(np.round(q_table, 2))

        if (robot_x, robot_y) == GOAL:
            print("\n REACHED GOAL! RESETTING EPISODE...\n")
            sensors = reset_robot()
        else:
            sensors = new_sensors




# ------------------------------GEEKS4GEEKS IMPLEMENTATION--------------------------------------

# import numpy as np
# import matplotlib.pyplot as plt

# n_states = 16         
# n_actions = 4          
# goal_state = 15        

# Q_table = np.zeros((n_states, n_actions)) # create new array with n_states * n_actions init to 0

# learning_rate = 0.8     # how much new info overrides old info
# discount_factor = 0.95  # how much future rewards are valued
# exploration_prob = 0.2  # probability of taking random action
# epochs = 1000           # num of training arcs

# # 0:LEFT, 1:RIGHT, 2: UP, 3:DOWN
# def get_next_state(state, action): #calc next state
   
#     row, col = divmod(state, 4) # row = state/4, col=remainder

#     if action == 0 and col > 0:
#         col -= 1
#     elif action == 1 and col < 3:
#         col += 1
#     elif action == 2 and row > 0:
#         row -= 1
#     elif action == 3 and row < 3:
#         row += 1

#     return row * 4 + col

# # train to learn optimal policy:
# for epoch in range(epochs):
#     current_state = np.random.randint(0, n_states)  

#     while True:
#         # choose action using epsilon-greedy policy
#         if np.random.rand() < exploration_prob:
#             action = np.random.randint(0, n_actions)
#         else:
#             action = np.argmax(Q_table[current_state])

#         # move to next state
#         next_state = get_next_state(current_state, action)

#         reward = 1 if next_state == goal_state else 0

#         # update Q-value using bellman equation
#         Q_table[current_state, action] += learning_rate * (
#             reward + discount_factor * np.max(Q_table[next_state]) - Q_table[current_state, action]
#         )

#         if next_state == goal_state: # end episode if goal reached
#             break

#         current_state = next_state


# q_values_grid = np.max(Q_table, axis=1).reshape((4, 4))

# plt.figure(figsize=(6, 6))
# plt.imshow(q_values_grid, cmap='coolwarm', interpolation='nearest')
# plt.colorbar(label='Q-value')
# plt.title('Learned Q-values for Each State')
# plt.xticks(np.arange(4), ['0', '1', '2', '3'])
# plt.yticks(np.arange(4), ['0', '1', '2', '3'])
# plt.gca().invert_yaxis()
# plt.grid(True)

# for i in range(4):
#     for j in range(4):
#         plt.text(j, i, f'{q_values_grid[i, j]:.2f}', ha='center', va='center', color='black')

# plt.show()

# print("Learned Q-table:")
# print(Q_table)