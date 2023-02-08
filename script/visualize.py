import matplotlib.pyplot as plt

bar_width = 0.1

# method, bytes, ops/s
bench_result = [
    ["entityUtils4", 1000, 1195117.928],
    ["entityUtils4", 1000000, 3026.675],
    ["entityUtils4", 10000000, 168.823],
    ["entityUtils5", 1000, 1344079.434],
    ["entityUtils5", 1000000, 3095.293],
    ["entityUtils5", 10000000, 168.321],
    ["inputStreamReadNBytes", 1000, 6140755.074],
    ["inputStreamReadNBytes", 1000000, 8072.631],
    ["inputStreamReadNBytes", 10000000, 640.349]]

plt.title("Benchmark for consuming bytes")

plt.bar([x - bar_width for x in range(3)],
        [score for [l, _, score] in bench_result if l == 'entityUtils4'],
        color='r', width=bar_width, label='entityUtils4')
plt.bar([x for x in range(3)],
        [score for [l, _, score] in bench_result if l == 'entityUtils5'],
        color='g', width=bar_width, label='entityUtils5')
plt.bar([x + bar_width for x in range(3)],
        [score for [l, _, score] in bench_result if l == 'inputStreamReadNBytes'],
        color='b', width=bar_width, label='inputStreamReadNBytes')

plt.yscale('log')
plt.ylabel("ops/s")
plt.xlabel("bytes")
plt.legend()
plt.xticks(range(3), [1000, 1000000, 10000000])

plt.show()
