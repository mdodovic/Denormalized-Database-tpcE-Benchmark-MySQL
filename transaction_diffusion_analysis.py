# -*- coding: utf-8 -*-
"""
Created on Tue May 31 18:55:41 2022

@author: Matija
"""

from re import S
import numpy as np
import matplotlib.pyplot as plt

path_to_transaction_mix = "C:/Users/matij/Desktop/transactionMix/3e6_scale/";

def analysis(transaction_mixture_file_name):
    
    A = np.loadtxt(path_to_transaction_mix + transaction_mixture_file_name, delimiter=' ', dtype=str, usecols=(2), unpack=True)    
    ca_id_frequency = {}
    for ca_id in A:
        if ca_id[0:9] in ca_id_frequency:
            ca_id_frequency[ca_id[0:9]] = ca_id_frequency[ca_id[0:9]] + 1
        else:
            ca_id_frequency[ca_id[0:9]] = 1
    print(len(A))
    frequency_existance = {}

    for key, val in ca_id_frequency.items():
        if val in frequency_existance:
            frequency_existance[val] = frequency_existance[val] + 1
        else:
            frequency_existance[val] = 1

    print(frequency_existance)

    x = []
    y = []
    for key, val in frequency_existance.items():
        x.append(key)
        y.append(val)

    for i in range(len(x) - 1):
        for j in range(i + 1, len(x)):
            if x[i] > x[j]:
                tmp = x[i]
                x[i] = x[j]
                x[j] = tmp
                tmp = y[i]
                y[i] = y[j]
                y[j] = tmp

    print(x, y)

    plt.grid()
    plt.scatter(x, y)
    plt.xlabel("Existence number")
    plt.ylabel("Number of transactions")
    plt.savefig("transaction_mix_diffusion.png", dpi = 90)
    plt.show()

    s = 0
    for i in range(1, 6):
        s += x[i] * y[i]

    print(s / len(A))
 
if __name__ == "__main__":
           
    transaction_mixture_file_name = "T2T3T8_T2F1_read_130k.sql"

    analysis(transaction_mixture_file_name)
