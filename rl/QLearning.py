import numpy as np
import random
# import RPi.GPIO as GPIO # for rasberry pi only
import time


# --------------------------- ACTIONS ---------------------------
FORWARD = 0
LEFT = 1
RIGHT = 2
actions = [FORWARD, LEFT, RIGHT]
action_names = ["FORWARD", "LEFT", "RIGHT"]

q_table = np.zeros((32, len(actions))) # 32 possible states (8 * 4, 4 from the goal_direction)

epsilon = 0.3 
epsilon_min = 0.01 
epsilon_decay = 0.998

state_visit_count = np.zeros(32)  # Track how many times each state was visited
exploration_bonus = 0.3           # How much to reward visiting new states

learning_rate = 0.2
discount_factor = 0.95

NUM_EPISODES = 2000 
MAX_STEPS = 200 

# --------------------------- GRID WORLD ---------------------------

# FOR SIMULATION ONLY. THIS MUST BE REPLACED WITH THE REAL SENSORS
GRID_TEMPLATE = [
    [0,0,0,0,0],
    [0,1,1,0,0],
    [0,0,0,1,0],
    [0,1,0,0,0],
    [0,0,0,0,0]
]

START_POSITIONS = [(0,0), (0,2), (0,4), (2,0), (2,4), (4,0), (4,2), (4,4)]
GOAL = (4,4)

# Global variables
GRID = None
START = None
robot_x, robot_y = 0, 0
heading = 1    # RIGHT initially

def reset_robot():
    global robot_x, robot_y, heading, GRID, START
    
    START = random.choice(START_POSITIONS) # OPTION 1: Randomize starting position
    
    # OPTION 2: Randomize walls occasionally (10% chance)
    GRID = [row[:] for row in GRID_TEMPLATE]
    if random.random() < 0.1:  # 10% chance to randomize walls
        for y in range(1, 4):
            for x in range(1, 4):
                if (x, y) != GOAL and (x, y) != START:  # can't block goal or start
                    if random.random() < 0.2:  # 20% chance to add/remove wall
                        GRID[y][x] = 1 - GRID[y][x]  # Flip wall status
    
    robot_x, robot_y = START
    heading = random.choice([0, 1, 2, 3])  # Random starting heading
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
        else:
            # Hit a wall - don't move
            pass

# --------------------------- REWARD - IMPROVED FOR GENERALIZATION ---------------------------

def get_reward(state, action, prev_dist): 
    global robot_x, robot_y

    new_dist = abs(robot_x - GOAL[0]) + abs(robot_y - GOAL[1])
    
    # IMMEDIATE CRASH PENALTY
    left_wall, front_wall, right_wall = state
    if front_wall == 1 and action == FORWARD:
        return -10, prev_dist
    
    # GOAL REWARD
    if (robot_x, robot_y) == GOAL:
        return 50, new_dist
    
    # Only calculate other rewards if not crashing and not at goal
    reward = 0.0
    
    # Distance-based rewards
    max_possible_dist = 8
    dist_improvement = (prev_dist - new_dist) / max_possible_dist
    
    if dist_improvement > 0:
        reward += 3.0 * dist_improvement
    elif dist_improvement < 0:
        reward += 1.5 * dist_improvement
    
    # Encourage turning away from front wall
    if front_wall == 1 and (action == LEFT or action == RIGHT):
        reward += 2.0  # Increased reward
    
    # Small penalty for turning (encourage efficiency)
    if action != FORWARD:
        reward -= 0.1
    
    # Small step penalty to encourage shorter paths
    reward -= 0.02
    
    return reward, new_dist


def goal_direction():
    dx = GOAL[0] - robot_x
    dy = GOAL[1] - robot_y

    if dx == 0 and dy == 0:
        return 0  # Already at goal
    
    # Prioritize the larger distance component
    if abs(dx) > abs(dy):
        return 0 if dx > 0 else 1  # goal right / left
    elif abs(dy) > abs(dx):
        return 2 if dy > 0 else 3  # goal down / up
    else:
        # Equal distances, choose based on current heading
        if dx > 0:
            return 0  # right
        else:
            return 2  # down
    
def goal_direction_biased_exploration():
    """Sometimes override goal direction to force exploration of all directions"""
    dx = GOAL[0] - robot_x
    dy = GOAL[1] - robot_y
    
    # 15% chance to use a different goal direction for exploration
    if random.random() < 0.15:
        return random.choice([0, 1, 2, 3])
    
    # Normal calculation (same as goal_direction)
    if abs(dx) > abs(dy):
        return 0 if dx > 0 else 1
    elif abs(dy) > abs(dx):
        return 2 if dy > 0 else 3
    else:
        return random.choice([0, 2])

# --------------------------- Q-TABLE CLEANUP ---------------------------

def decode_state(state_num):
    """Decode state number into wall sensors and goal direction"""
    goal_dir = state_num & 0b11
    right = (state_num >> 2) & 1
    front = (state_num >> 3) & 1
    left = (state_num >> 4) & 1
    return left, front, right, goal_dir

def cleanup_q_table(q_table):
    """Fix obviously wrong Q-values after training"""
    for state in range(32):
        left, front, right, goal_dir = decode_state(state)
        
        # If front is wall, FORWARD should have very low Q-value
        if front == 1:
            q_table[state, FORWARD] = min(q_table[state, FORWARD], -2.0)
        
        # If all walls, all actions should have low values
        if left == 1 and front == 1 and right == 1:
            q_table[state, :] = np.minimum(q_table[state, :], -1.0)
        
        # If no front wall but Q-value is 0, give it a small positive value
        if front == 0 and np.max(q_table[state]) == 0:
            q_table[state, FORWARD] = 0.5  # Small encouragement
    
    return q_table

# --------------------------- TRAINING LOOP ---------------------------

# Track statistics
success_count = 0
crash_count = 0
visited_states = set()
episode_rewards = []

print("="*70)
print("Q-LEARNING WITH RANDOMIZED ENVIRONMENTS")
print("="*70)
# print("Each episode will have:")
# print("  - Random starting position")
# print("  - Random starting heading")
# print("  - Occasionally random walls (10% chance)")
# print("="*70)

for episode in range(NUM_EPISODES):
    sensors = reset_robot()
    prev_dist = abs(robot_x - GOAL[0]) + abs(robot_y - GOAL[1])
    total_reward = 0
    
    if episode < 3: # for debugging
        print(f"\nEpisode {episode}: Start at {START}, Heading {heading}, Goal at {GOAL}")
        print(f"Grid (1=wall):")
        for row in GRID:
            print("  " + " ".join(str(cell) for cell in row))

    for step in range(MAX_STEPS):
        if random.random() < 0.3:  # 30% chance to use biased exploration
            g = goal_direction_biased_exploration()
        else:
            g = goal_direction()

        s = encode_state(sensors, g)
        
        # Track visited states
        # visited_states.add(s)
        state_visit_count[s] += 1
    
        exploration_value = exploration_bonus * np.sqrt(np.log(step + 1) / (state_visit_count[s] + 1))
        
        # epsilon-greedy strategy with exploration bonus
        if (random.random() < epsilon): 
            a = random.choice(actions) # Explore
        else: 
            action_values = q_table[s] + exploration_value
            a = np.argmax(action_values)  # Exploit with exploration bias
        
        old_pos = (robot_x, robot_y)
        old_heading = heading
        do_action(a)
        
        # Check if we crashed
        left_wall, front_wall, right_wall = sensors
        if front_wall == 1 and a == FORWARD:
            crash_count += 1

        #new state
        new_sensors = get_state()
        g2 = goal_direction()
        s2 = encode_state(new_sensors, g2)
        reward, prev_dist = get_reward(new_sensors, a, prev_dist)
        total_reward += reward

        # Q-learning update
        q_table[s,a] += learning_rate * (reward + discount_factor * np.max(q_table[s2]) - q_table[s,a])
        
        sensors = new_sensors

        if (robot_x, robot_y) == GOAL:
            success_count += 1
            if episode < 10 or (episode % 100 == 0 and episode > 0):
                print(f"Episode {episode}: ✓ Goal in {step} steps from {START}")
            break
        
    epsilon = max(epsilon_min, epsilon * epsilon_decay)
    episode_rewards.append(total_reward)
    
    # Adaptive learning rate
    if episode > 500:
        success_rate = success_count / (episode + 1)
        if success_rate > 0.6:
            learning_rate = max(0.05, learning_rate * 0.999)

    # Print progress
    if episode % 100 == 0:
        success_rate = success_count / (episode + 1) * 100
        states_visited = len(visited_states)
        avg_reward = np.mean(episode_rewards[-100:] if episode >= 100 else episode_rewards)
        
        print(f"Episode {episode:4d}: "
              f"Success={success_rate:5.1f}%, "
              f"States={states_visited:2d}/32, "
              f"ε={epsilon:.3f}, "
              f"Avg Reward={avg_reward:6.2f}")

# +++ CLEAN UP THE Q-TABLE BEFORE SAVING
print("\nCleaning up Q-table...")
q_table = cleanup_q_table(q_table)

np.save("qtable.npy", q_table)
print("\n" + "="*70)
print(f"TRAINING COMPLETE - RANDOMIZED ENVIRONMENT")
print("="*70)
print(f"Total episodes: {NUM_EPISODES}")
print(f"Successful episodes: {success_count} ({success_count/NUM_EPISODES*100:.1f}%)")
print(f"Total crashes: {crash_count}")
print(f"Unique states visited: {len(visited_states)}/32 ({len(visited_states)/32*100:.1f}%)")
print(f"Final exploration rate (ε): {epsilon:.3f}")
print(f"Final learning rate (α): {learning_rate:.3f}")
print("="*70)

# --------------------------- Q-TABLE VISUALIZATION ---------------------------

def visualize_q_table_simple(q_table):
    """Simple text-based visualization of the Q-table"""
    print("\n" + "="*60)
    print("Q-TABLE VISUALIZATION")
    print("="*60)
    
    # Decode state helper function (for visualization only)
    def decode_state(state_num):
        goal_dir = state_num & 0b11
        right = (state_num >> 2) & 1
        front = (state_num >> 3) & 1
        left = (state_num >> 4) & 1
        return left, front, right, goal_dir
    
    goal_names = ["Right", "Left", "Down", "Up"]
    
    print("\nState | LFR Goal | FORWARD    LEFT      RIGHT  | Best Action")
    print("-"*60)
    
    for state in range(32):
        left, front, right, goal_dir = decode_state(state)
        
        # Wall sensor symbols
        wall_symbols = f"{'W' if left else ' '}{'W' if front else ' '}{'W' if right else ' '}"
        goal_symbol = goal_names[goal_dir][0]  # First letter
        
        # Get Q-values
        q_forward = q_table[state, FORWARD]
        q_left = q_table[state, LEFT]
        q_right = q_table[state, RIGHT]
        
        # Determine best action
        best_action_idx = np.argmax(q_table[state])
        best_action = ["F", "L", "R"][best_action_idx]
        
        # Highlight best action with asterisk
        forward_str = f"{q_forward:7.2f}{'*' if best_action_idx == FORWARD else ' '}"
        left_str = f"{q_left:7.2f}{'*' if best_action_idx == LEFT else ' '}"
        right_str = f"{q_right:7.2f}{'*' if best_action_idx == RIGHT else ' '}"
        
        print(f"{state:3d}  | {wall_symbols} {goal_symbol:4} | {forward_str} {left_str} {right_str} | {best_action}")
        
        # Print separator every 8 states (for each goal direction)
        if (state + 1) % 8 == 0 and state != 31:
            print("-"*60)

def visualize_q_table_stats(q_table):
    """Show statistics about the Q-table"""
    print("\n" + "="*60)
    print("Q-TABLE STATISTICS")
    print("="*60)
    
    # Basic stats
    print(f"\nOverall Q-values:")
    print(f"  Min: {np.min(q_table):.4f}")
    print(f"  Max: {np.max(q_table):.4f}")
    print(f"  Mean: {np.mean(q_table):.4f}")
    print(f"  Std Dev: {np.std(q_table):.4f}")
    
    # Count non-zero entries
    non_zero = np.sum(q_table != 0)
    total_entries = q_table.size
    print(f"\nLearning Progress: {non_zero}/{total_entries} entries filled ({non_zero/total_entries*100:.1f}%)")
    
    # Optimal policy
    optimal_actions = np.argmax(q_table, axis=1)
    forward_count = np.sum(optimal_actions == FORWARD)
    left_count = np.sum(optimal_actions == LEFT)
    right_count = np.sum(optimal_actions == RIGHT)
    
    print(f"\nOptimal Policy Distribution:")
    print(f"  FORWARD: {forward_count} states ({forward_count/32*100:.1f}%)")
    print(f"  LEFT:    {left_count} states ({left_count/32*100:.1f}%)")
    print(f"  RIGHT:   {right_count} states ({right_count/32*100:.1f}%)")
    
    # Top 5 states by value
    state_values = np.max(q_table, axis=1)
    top_5_states = np.argsort(state_values)[-5:][::-1]
    
    print(f"\nTop 5 Most Valuable States:")
    for i, state in enumerate(top_5_states):
        value = state_values[state]
        best_action = action_names[optimal_actions[state]]
        
        # Decode state for description
        goal_dir = state & 0b11
        right = (state >> 2) & 1
        front = (state >> 3) & 1
        left = (state >> 4) & 1
        goal_names = ["Right", "Left", "Down", "Up"]
        
        print(f"  {i+1}. State {state:2d}: Q={value:.4f}, Action={best_action}")
        print(f"     Walls: L={'W' if left else '_'}, F={'W' if front else '_'}, R={'W' if right else '_'}, Goal: {goal_names[goal_dir]}")

# --------------------------- TEST THE LEARNED POLICY FROM MULTIPLE STARTS ---------------------------

print("\n" + "="*70)
print("TESTING LEARNED POLICY FROM MULTIPLE START POSITIONS")
print("="*70)

q_table = np.load("qtable.npy")

test_successes = 0
test_positions = START_POSITIONS[:]  # Test all start positions

for test_idx, start_pos in enumerate(test_positions):
    print(f"\nTest {test_idx+1}/{len(test_positions)}: Starting at {start_pos}")
    
    # Reset to specific position
    robot_x, robot_y = start_pos
    heading = 1  # Start facing right
    sensors = get_state()
    
    max_test_steps = 100
    path = [(robot_x, robot_y)]
    
    for step in range(max_test_steps):
        g = goal_direction()
        s = encode_state(sensors, g)
        action = np.argmax(q_table[s])
        
        do_action(action)
        sensors = get_state()
        path.append((robot_x, robot_y))
        
        if (robot_x, robot_y) == GOAL:
            print(f"  ✓ Reached goal in {step+1} steps")
            test_successes += 1
            break
            
        if step == max_test_steps - 1:
            print(f"  ✗ Failed to reach goal in {max_test_steps} steps")
            print(f"    Final position: ({robot_x},{robot_y})")
    
    # Show simplified path
    if len(path) <= 15:
        print(f"  Path: {' → '.join(f'({x},{y})' for x,y in path)}")
    else:
        print(f"  Path length: {len(path)} steps")

print(f"\n" + "="*70)
print(f"TEST RESULTS: {test_successes}/{len(test_positions)} successful ({test_successes/len(test_positions)*100:.1f}%)")
print("="*70)

# Show final Q-table analysis
print("\n" + "="*60)
print("FINAL Q-TABLE ANALYSIS")
print("="*60)

visualize_q_table_simple(q_table)
visualize_q_table_stats(q_table)

# --------------------------- EXECUTION (for real robot) ---------------------------

# print("\n" + "="*60)
# print("READY FOR REAL ROBOT DEPLOYMENT")
# print("="*60)
# print("Load qtable.npy and run infinite loop:")
# print("""
# q_table = np.load("qtable.npy")
# while True:
#     sensors = get_real_sensors()  # Replace with GPIO
#     g = goal_direction()
#     s = encode_state(sensors, g)
#     action = np.argmax(q_table[s])
#     execute_action(action)  # Replace with motor control
#     time.sleep(0.1)
# """)