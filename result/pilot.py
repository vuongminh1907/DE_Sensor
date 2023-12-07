import matplotlib.pyplot as plt

filepath = 'result\gen.out' 
# Đọc dữ liệu từ file gen.out
with open(filepath, 'r') as file:
    lines = file.readlines()

# Lọc thông tin về thứ tự generation và giá trị value
generations = []
values = []

for line in lines:
    if line.startswith("Best fit for generation"):
        parts = line.split(":")
        generation = int(parts[0].split()[-1])
        value = int(parts[1].strip())
        generations.append(generation)
        values.append(value)

# Vẽ đồ thị liền mạch
plt.plot(generations, values, marker='.', linestyle='-')
plt.title('Best Fit for Each Generation')
plt.xlabel('Generation')
plt.ylabel('Value')
plt.grid(True)

# Lưu đồ thị thành file ảnh (ví dụ: PNG)
plt.savefig('result//Graph.png')