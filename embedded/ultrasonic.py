from gpiozero import DistanceSensor

class Ultrasonic:
    def __init__(self, echo, trigger):
        """
        Parameters: echo pin, trigger pin
        """
        self.sensor = DistanceSensor(echo=echo, trigger=trigger)
    def get_distance(self):
        return self.sensor.distance