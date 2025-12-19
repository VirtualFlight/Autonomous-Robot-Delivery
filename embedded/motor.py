import RPi.GPIO as GPIO
import time

class Motor:
    def __init__(self, left, right):
        self.left = left
        self.right = right

        GPIO.setmode(GPIO.BCM)
        GPIO.setup(self.left, GPIO.OUT)
        GPIO.setup(self.right, GPIO.OUT)
    
    def forward(self):
        GPIO.output(self.left, GPIO.LOW)
        GPIO.output(self.right, GPIO.HIGH)        
    
    def backward(self):
        GPIO.output(self.left, GPIO.HIGH)
        GPIO.output(self.right, GPIO.LOW)

    def move(self, left, right):
        '''
        PreCondition: GPIO.LOW or GPIO.HIGH
        '''
        assert left in (GPIO.LOW, GPIO.HIGH)
        assert right in (GPIO.LOW, GPIO.HIGH)
        GPIO.output(self.left, left)
        GPIO.output(self.right, right)

    def stop(self):
        GPIO.output(self.left, GPIO.LOW)    
        GPIO.output(self.right, GPIO.LOW)

if __name__ == '__main__':
    motor = Motor(13,6)
    motor2 = Motor(5,16)

    try:
        print("Forward")
        motor.forward()
        motor2.forward()
        time.sleep(2)
        
        print("Backward")
        motor.backward()
        motor2.backward()
        time.sleep(2)

        motor.stop()
        motor2.stop()
    finally:
        GPIO.cleanup()