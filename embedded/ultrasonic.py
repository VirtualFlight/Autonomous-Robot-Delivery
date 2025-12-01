from gpiozero import DistanceSensor

class Ultrasonic:
    def __init__(self):
        self.sensor = DistanceSensor(echo=24, trigger=23)
    def get_distance(self):
        return self.sensor.distance