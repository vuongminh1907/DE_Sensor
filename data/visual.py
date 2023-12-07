import matplotlib.pyplot as plt
import matplotlib.patches as patches
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
    # Vẽ tâm của các sensor và hiển thị số thứ tự
    for i, sensor in enumerate(sensors):
        plt.scatter(sensor[0], sensor[1], color='red', marker='o', label=f'Sensor {i + 1}')
        plt.text(sensor[0], sensor[1], f'S{i + 1}', fontsize=8, ha='right', va='bottom')

        '''
        # Vẽ vòng tròn bán kính sensor bằng nét đứt
        circle = patches.Circle((sensor[0], sensor[1]), sensor[2], fill=False, linestyle='dashed', color='red')
        plt.gca().add_patch(circle)
        '''
        
    '''
    # Nối các sensor với target mà chúng phát hiện được
    for i, sensor in enumerate(sensors):
        for j, target in enumerate(targets):
            distance = np.sqrt((sensor[0] - target[0])**2 + (sensor[1] - target[1])**2)
            if distance <= sensor[2]:
                plt.plot([sensor[0], target[0]], [sensor[1], target[1]], color='green')
    '''
    # Vẽ các điểm target và hiển thị số thứ tự
    for i, target in enumerate(targets):
        plt.scatter(target[0], target[1], color='blue', marker='x', label=f'Target {i + 1}')
        plt.text(target[0], target[1], f'T{i + 1}', fontsize=8, ha='left', va='top')

    plt.xlabel('X-axis')
    plt.ylabel('Y-axis')
    plt.title('Sensor Centers, Targets, and Detections')
    plt.show()

# Thay đổi đường dẫn tới file sensor.input và target.input của bạn
sensor_file_path = 'D:\Minh.data\Dev Java\DE_Sensor\data\sensor.input'
target_file_path = 'D:\Minh.data\Dev Java\DE_Sensor\data\sentarget.input'

# Đọc dữ liệu từ file
sensors = read_sensor_file(sensor_file_path)
targets = read_target_file(target_file_path)

# Vẽ hiển thị
plot_sensors_targets(sensors, targets)
