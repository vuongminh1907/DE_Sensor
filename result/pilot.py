import matplotlib.pyplot as plt

def read_data(filepath):
    with open(filepath, 'r') as file:
        lines = file.readlines()

    generations = []
    values = []

    for line in lines:
        if line.startswith("Best fit for generation"):
            parts = line.split(":")
            generation = int(parts[0].split()[-1])
            value = int(parts[1].strip())
            generations.append(generation)
            values.append(value)

    return generations, values

# Đọc dữ liệu từ file genDE.out
gen_de_filepath = 'result\\genDE.out'
generations_de, values_de = read_data(gen_de_filepath)

# Đọc dữ liệu từ file genGA.out
gen_ga_filepath = 'result\\genGA.out'
generations_ga, values_ga = read_data(gen_ga_filepath)
# Đọc dữ liệu từ file genHMS.out
gen_hms_filepath = 'result\\genHMS.out'
generations_hms, values_hms = read_data(gen_hms_filepath)

# Vẽ đồ thị liền mạch
plt.plot(generations_de, values_de, label='DE', marker='.', linestyle='-')
plt.plot(generations_ga, values_ga, label='GA', marker='.', linestyle='-')
plt.plot(generations_hms, values_hms, label='HMS', marker='.', linestyle='-')

plt.title('Best Fit for Each Generation')
plt.xlabel('Generation')
plt.ylabel('Value')
plt.grid(True)
plt.legend()

# Lưu đồ thị thành file ảnh (ví dụ: PNG)
plt.savefig('result//Graph.png')


