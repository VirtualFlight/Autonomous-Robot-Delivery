This is a [Next.js](https://nextjs.org) project bootstrapped with [`create-next-app`](https://nextjs.org/docs/app/api-reference/cli/create-next-app).

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
# or
pnpm dev
# or
bun dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

You can start editing the page by modifying `app/page.tsx`. The page auto-updates as you edit the file.

### Dependencies
```
npm install axios
```

## Learn More

To learn more about Next.js, take a look at the following resources:

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
- [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js) - your feedback and contributions are welcome!

## Deploy on Vercel

The easiest way to deploy your Next.js app is to use the [Vercel Platform](https://vercel.com/new?utm_medium=default-template&filter=next.js&utm_source=create-next-app&utm_campaign=create-next-app-readme) from the creators of Next.js.

Check out our [Next.js deployment documentation](https://nextjs.org/docs/app/building-your-application/deploying) for more details.
# 🤖 Autonomous Delivery Robot System

**End-to-end autonomous delivery system** combining reinforcement learning, web dashboard, and embedded robotics.

## 🏗️ Architecture
```
React Frontend ← REST API → Spring Backend ← MQTT/WebSocket → Raspberry Pi Robot
```

## Dependencies
```
npm install axios
npm install -D @tailwindcss/postcss
```

## 🎯 Features
- **🤖 Smart Navigation**: Q-learning for obstacle avoidance
- **🌐 Web Dashboard**: Live tracking & control
- **🔧 Spring Backend**: REST API with design patterns
- **🖥️ Raspberry Pi**: Real-time sensor/motor control

## 📁 Project Structure
```
delivery-robot-system/
├── robot-firmware/     # Raspberry Pi Python code
├── backend/           # Spring Boot Java
├── frontend/         # React dashboard
└── docs/             # Documentation
```

## 🚀 Quick Start

### 1. **Hardware Setup**
```
Components Needed:
- Raspberry Pi 4
- L298N Motor Driver
- 3× IR Sensors (left, front, right)
- 2× DC Motors with wheels
- Power supply
- Jumper wires

GPIO Connections (BCM):
- Motors: IN1=17, IN2=18, IN3=22, IN4=23, ENA=24, ENB=25
- Sensors: LEFT=5, CENTER=6, RIGHT=13
```

### 2. **Install Robot Software**
```bash
cd robot-firmware
pip install numpy paho-mqtt RPi.GPIO
python qlearning/QLearning.py --mode=test
```

### 3. **Start Backend**
```bash
cd backend
mvn spring-boot:run
# API: http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

### 4. **Start Frontend**
```bash
cd frontend
npm install
npm start
# Dashboard: http://localhost:3000
```

## 🔌 API Examples
```bash
# Get robot status
GET /api/robots/{id}/status

# Send command
POST /api/robots/{id}/command
Body: {"action": "FORWARD", "duration": 1000}

# Create delivery
POST /api/deliveries
Body: {"pickup": "A1", "dropoff": "D4", "priority": "HIGH"}
```

## 🧠 Q-Learning Core
```python
# 32 states: 8 wall configs × 4 goal directions
State = (left_wall << 4) | (front_wall << 3) | (right_wall << 2) | goal_dir

# Training params
epsilon = 0.3          # Exploration rate
learning_rate = 0.2    # Update speed
episodes = 2000        # Training iterations

# Rewards
GOAL_REWARD = 50
CRASH_PENALTY = -10
TURN_REWARD = 2.0      # Turning away from wall
```

## 🎨 Design Patterns Used
- **Factory**: Robot controller creation
- **Strategy**: Navigation algorithms
- **Observer**: Real-time updates
- **Singleton**: Config management
- **Adapter**: Hardware interfaces

## ⚙️ Configuration

**Backend (`application.properties`)**:
```properties
server.port=8080
mqtt.broker.url=tcp://localhost:1883
robot.update.interval=1000
```

**Robot (`config.yaml`)**:
```yaml
robot:
  id: "delivery-bot-01"
  backend_url: "http://192.168.1.100:8080"
  sensors: [5, 6, 13]    # GPIO pins
  motors: [17, 18, 22, 23]
```