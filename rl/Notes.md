# Definitions
2 Parts of RL
- Agent: whatever you can directly control
- Environment: cannot directly control. must interact with through agent
Influence
- Action: Agent -> Environment
- State: Environment -> Agent

## Markov Decision Process (MDP)
- $s_0, a_0, r_0, s_1, a_1, r_1, ...$
	- s: state
	- a: action
	- r: reward
- Markov Property: each state is only dependent on the immediately previous state
- Agent Goal: use input state to decide an output action to maximize the reward
- Q-values: the expected rewards for taking an action in a specific state
	- updated using the Temporal Difference (TD) update rule

## Temporal Difference Update:
- $Q(S,A)←Q(S,A)+α(R+γQ(S′,A′)−Q(S,A))$
	- ***S*** is the current state.
	- ***A*** is the action taken by the agent.
	- ***S'*** is the next state the agent moves to.
	- ***A'*** is the best next action in state S'.
	- ***R*** is the reward received for taking action A in state S.
	- ***γ (Gamma)*** is the discount factor which balances immediate rewards with future rewards.
	- ***α (Alpha)*** is the learning rate determining how much new information affects the old Q-values.

## Bellman's Equation:
- recursive formula used to calculate the value of a given state and determine the optimal action
- $Q(s,a)=R(s,a)+\gamma\ max_a Q(s', a)$
	- ***Q(s, a)*** is the Q-value for a given state-action pair.
	- ***R(s, a)*** is the immediate reward for taking action ***a*** in state ***s***.
	- $\gamma$ is the discount factor, representing the importance of future rewards.
	- $max_a\ Q(s',a)$ is the maximum Q-value for the next state **s'*** and all possible actions.

## Q-Table
- Q-table is essentially a memory structure where the agent stores information about which actions yield the best rewards in each state
- table of Q-values representing the agent's understanding of the environment
- updates the Q-table as agent learns from interactions
- The Q-table helps the agent make informed decisions by showing which actions are likely to lead to better rewards
- Structure:
	- Rows represent the states
	- Columns represent the possible actions
	- Each entry in the table corresponds to the Q-value for a state-action pair.

## $\epsilon$-greedy policy:
- helps the agent decide which action to take based on the current Q-value estimates
- Exploitation: The agent picks the action with the highest Q-value with probability $1−\epsilon$
	- agent uses its current knowledge to maximize rewards
- Exploration: With probability $\epsilon$, the agent picks a random action, exploring new possibilities to learn if there are better ways to get rewards
	- allows the agent to discover new strategies and improve its decision-making over time
- kinda like BFS vs DFS

## The Loop

1\. ***Start at a State (S)***
- The environment provides the agent with a starting state which describes the current situation or condition

2\. ***Agent Selects an Action (A)***
- agent chooses an action using its policy based on current state
- This decision is guided by a Q-table which estimates the potential rewards for different state-action pairs
- typically uses an ε-greedy strategy:
	- sometimes explores new actions (random choice)
	- mostly exploits known good actions (based on current Q-values)

3\. ***Action is Executed and Environment Responds***
- The agent performs the selected action. The environment then provides:
	- **A new state (S′)** — the result of the action.
	- **A reward (R)** — feedback on the action's effectiveness

4\. ***Learning Algorithm Updates the Q-Table***
- agent updates the Q-table using the new experience:
	- It adjusts the value for the state-action pair based on the received reward and the new state.
	- This helps the agent better estimate which actions are more beneficial over time

5\. ***Policy is Refined and the Cycle Repeats***
- With updated Q-values the agent:
	- Improves its policy to make better future decisions.
	- Continues the loop
		- observing states, taking actions, receiving rewards and updating Q-values across many episodes
- Over time the agent learns the optimal policy that consistently yields the highest possible reward in the environment

# References
Geeks4Geeks:
- https://www.geeksforgeeks.org/machine-learning/q-learning-in-python/

Pidora
- https://pidora.ca/build-a-smart-robot-that-actually-learns-using-q-learning-on-raspberry-pi/

# Code Definitions
## Motor Controls
1  = forward  
0  = stop  
-1 = backward (may remove)
```py
	set_motors(1,1) → go forward
	set_motors(0,1) → turn left
	set_motors(1,0) → turn right
	set_motors(-1, -1) → reverse/back up
```

## Discount Factor & Learning Rate

### Discount Factor
- How much the robot values future rewards over immediate reward
- High discount factor means future rewards > immediate
- We need high discount factor because we care about fast navigation paths instead of brute-forcing

### Learning Factor
- How fast the robot updates old info with new info
- High learning factor means the robot learns fast (good for early training) but can become unstable
- We will use a low learning factor for the reliability and smooth Q-value changes