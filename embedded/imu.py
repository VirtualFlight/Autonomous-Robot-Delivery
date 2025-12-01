import smbus
import time
class Imu:
    def __init__(self):
        # MPU6050 Registers
        self.MPU6050_ADDR = 0x68
        self.PWR_MGMT_1 = 0x6B
        self.ACCEL_XOUT_H = 0x3B
        self.bus = smbus.SMBus(1)

        self.bus.write_byte_data(self.MPU6050_ADDR, self.PWR_MGMT_1, 0)

    
    def read_raw(self, addr):
        high = self.bus.read_byte_data(self.MPU6050_ADDR, addr)
        low  = self.bus.read_byte_data(self.MPU6050_ADDR, addr+1)
        value = (high << 8) | low
        if value > 32768:
            value -= 65536
        return value

    def get_info(self):
        ax = self.read_raw(self.ACCEL_XOUT_H)
        ay = self.read_raw(self.ACCEL_XOUT_H + 2)
        az = self.read_raw(self.ACCEL_XOUT_H + 4)

        gx = self.read_raw(0x43)
        gy = self.read_raw(0x45)
        gz = self.read_raw(0x47)
        return ax, ay, az, gx, gy, gz