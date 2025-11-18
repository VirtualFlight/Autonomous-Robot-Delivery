#!/usr/bin/env python3
import smbus
import time

# MPU6050 Registers
MPU6050_ADDR = 0x68
PWR_MGMT_1 = 0x6B
ACCEL_XOUT_H = 0x3B

bus = smbus.SMBus(1)   # I2C bus 1 on Raspberry Pi 5

def read_raw(addr):
    high = bus.read_byte_data(MPU6050_ADDR, addr)
    low  = bus.read_byte_data(MPU6050_ADDR, addr+1)
    value = (high << 8) | low
    if value > 32768:
        value -= 65536
    return value

# Wake up MPU6050
bus.write_byte_data(MPU6050_ADDR, PWR_MGMT_1, 0)

print("Reading MPU6050 data... Press Ctrl+C to stop.")

while True:
    ax = read_raw(ACCEL_XOUT_H)
    ay = read_raw(ACCEL_XOUT_H + 2)
    az = read_raw(ACCEL_XOUT_H + 4)

    gx = read_raw(0x43)
    gy = read_raw(0x45)
    gz = read_raw(0x47)

    print(f"Accel  X:{ax}  Y:{ay}  Z:{az}")
    print(f"Gyro   X:{gx}  Y:{gy}  Z:{gz}")
    print("--------------------------")
    time.sleep(0.2)
