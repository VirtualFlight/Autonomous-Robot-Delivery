import numpy as np
import matplotlib.pyplot as plt

n_states = 16         
n_actions = 4          
goal_state = 15        

Q_table = np.zeros((n_states, n_actions)) # create new array with n_states * n_actions init to 0

learning_rate = 0.8     # how much new info overrides old info
discount_factor = 0.95  # how much future rewards are valued
exploration_prob = 0.2  # probability of taking random action
epochs = 1000           # num of training arcs

# 0:LEFT, 1:RIGHT, 2: UP, 3:DOWN
def get_next_state(state, action): #calc next state
   
    row, col = divmod(state, 4) # row = state/4, col=remainder

    if action == 0 and col > 0:
        col -= 1
    elif action == 1 and col < 3:
        col += 1
    elif action == 2 and row > 0:
        row -= 1
    elif action == 3 and row < 3:
        row += 1

    return row * 4 + col

# train to learn optimal policy:
for epoch in range(epochs):
    current_state = np.random.randint(0, n_states)  

    while True:
        # choose action using epsilon-greedy policy
        if np.random.rand() < exploration_prob:
            action = np.random.randint(0, n_actions)
        else:
            action = np.argmax(Q_table[current_state])

        # move to next state
        next_state = get_next_state(current_state, action)

        reward = 1 if next_state == goal_state else 0

        # update Q-value using bellman equation *TODO: double check this explanation
        Q_table[current_state, action] += learning_rate * (
            reward + discount_factor * np.max(Q_table[next_state]) - Q_table[current_state, action]
        )

        if next_state == goal_state: # end episode if goal reached
            break

        current_state = next_state


q_values_grid = np.max(Q_table, axis=1).reshape((4, 4))

plt.figure(figsize=(6, 6))
plt.imshow(q_values_grid, cmap='coolwarm', interpolation='nearest')
plt.colorbar(label='Q-value')
plt.title('Learned Q-values for Each State')
plt.xticks(np.arange(4), ['0', '1', '2', '3'])
plt.yticks(np.arange(4), ['0', '1', '2', '3'])
plt.gca().invert_yaxis()
plt.grid(True)

for i in range(4):
    for j in range(4):
        plt.text(j, i, f'{q_values_grid[i, j]:.2f}', ha='center', va='center', color='black')

plt.show()

print("Learned Q-table:")
print(Q_table)