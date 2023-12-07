import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D
import numpy as np

def read_sensor_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()
        sensors = [list(map(float, line.split())) for line in lines]
    return sensors

def read_target_file(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()
        targets = [list(map(float, line.split())) for line in lines]
    return targets

def plot_sensors_targets(sensors, targets):
    fig = plt.figure()
    ax = fig.add_subplot(111, projection='3d')

    # Vẽ các điểm sensor
    for i, (x, y, z, radius) in enumerate(sensors):
        ax.scatter(x, y, z, c='red', marker='o')
        #ax.text(x, y, z, f'{i + 1}', fontsize=8, ha='right', va='bottom')

        '''
        # Vẽ vòng tròn bán kính sensor
        u = v = range(0, 360, 5)
        u, v = np.meshgrid(u, v)
        u = radius * np.cos(np.radians(u)) + x
        v = radius * np.sin(np.radians(v)) + y
        ax.plot_surface(u, v, np.full_like(u, z), color='red', alpha=0.2)
        '''
    # Vẽ các điểm target
    for i, (x, y, z) in enumerate(targets):
        ax.scatter(x, y, z, c='blue', marker='^')
        #ax.text(x, y, z, f'{i + 1}', fontsize=8, ha='right', va='bottom')

    ax.text2D(0.05, 0.95, 'Sensor: Red, Target: Blue', transform=ax.transAxes, fontsize=12, color='black')
    ax.set_xlabel('X-axis')
    ax.set_ylabel('Y-axis')
    ax.set_zlabel('Z-axis')
    ax.legend()
    plt.title('Sensors and Targets')
    plt.show()

# Thay đổi đường dẫn tới file sensor.input và target.input của bạn
sensor_file_path = '.\data\sensor.input'
target_file_path = '.\data\sentarget.input'

# Đọc dữ liệu từ file
sensors = read_sensor_file(sensor_file_path)
targets = read_target_file(target_file_path)

# Vẽ hiển thị
plot_sensors_targets(sensors, targets)
