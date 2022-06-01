# -*- coding: utf-8 -*-
"""
Created on Wed Apr  7 13:53:41 2021

@author: Matija
"""

import numpy as np
import matplotlib.pyplot as plt
import math as math

pathToNormalizedData = "./results/normalized/";
pathToFullyDenormalizedData = "./results/fully_denormalized/";
pathToPartiallyDenormalizedData = "./results/partially_denormalized/";

pathToResultTPCE = "./results/time_graphs/";

def analysis(file_name, schema_model, path_to_data):
    
    file_time_stamp = file_name + "_timestamp.txt"

    time_stamp_data = np.loadtxt(path_to_data + file_time_stamp, unpack=True)    
    time_stamp_data = (time_stamp_data - time_stamp_data[0]) / 1000 / 1000000 # seconds
    time_stamp_data = time_stamp_data / 60 # minutes
    
    print("[", schema_model, "]: ", round(time_stamp_data[-1] - time_stamp_data[0],2))

    # Plot graphic
    time_stamp = np.zeros(math.ceil(time_stamp_data[-1] - time_stamp_data[0]))
    number_of_transactions = np.zeros(len(time_stamp))
    
    k = 0.
    i = 0
    cnt = 0
    for time in time_stamp_data:
        if time_stamp[i] == 0:
            time_stamp[i] = k
        if k <= time and time < k + 1:
            number_of_transactions[i] += 1#e-5
        else:
            i = i + 1
            k += 1.
            cnt += 1
    
    plt.plot(time_stamp, number_of_transactions)
 
if __name__ == "__main__":
           
    # Read 

    file_name = "T2F1_read_130k"
    print("T2F1")
    analysis(file_name, "Normalized", pathToNormalizedData)
    analysis(file_name, "Fully denormalized", pathToFullyDenormalizedData)
    analysis(file_name, "Partially denormalized", pathToPartiallyDenormalizedData)

    plt.xlabel("Time")
    plt.ylabel("Number of transactions")
    plt.title("[T2F1] Number of executed transactions in time")
    plt.legend(['Normalized table', 'Fully denormalized table', 'Partially denormalized table'])
    plt.savefig(pathToResultTPCE + "T2F1_analysis.png", dpi = 90)
    plt.show()
    
    # Write 
    
    file_name = "T3F1_write_130k"
    
    print("T3F1")
    analysis(file_name, "Normalized", pathToNormalizedData)
    analysis(file_name, "Fully denormalized", pathToFullyDenormalizedData)
    analysis(file_name, "Partially denormalized", pathToPartiallyDenormalizedData)
        
    plt.xlabel("Time")
    plt.ylabel("Number of transactions")
    plt.title("[T3F1] Number of executed transactions in time")
    plt.legend(['Normalized table', 'Fully denormalized table', 'Partially denormalized table'])
    plt.savefig(pathToResultTPCE + "T3F1_analysis.png", dpi = 90)
    plt.show()
    
    
    file_name = "T8F2_write_130k"
    
    print("T8F2")
    analysis(file_name, "Normalized", pathToNormalizedData)
    analysis(file_name, "Fully denormalized", pathToFullyDenormalizedData)
    analysis(file_name, "Partially denormalized", pathToPartiallyDenormalizedData)
    
    plt.xlabel("Time")
    plt.ylabel("Number of transactions")
    plt.title("[T8F2] Number of executed transactions in time")
    plt.legend(['Normalized table', 'Fully denormalized table', 'Partially denormalized table'])
    plt.savefig(pathToResultTPCE + "T8F2_analysis.png", dpi = 90)
    plt.show()

    


    file_name = "T8F6_write_130k"
    
    print("T8F6")
    analysis(file_name, "Normalized", pathToNormalizedData)
    analysis(file_name, "Fully denormalized", pathToFullyDenormalizedData)
    analysis(file_name, "Partially denormalized", pathToPartiallyDenormalizedData)
    
    plt.xlabel("Time")
    plt.ylabel("Number of transactions")
    plt.title("[T8F6] Number of executed transactions in time")
    plt.legend(['Normalized table', 'Fully denormalized table', 'Partially denormalized table'])
    plt.savefig(pathToResultTPCE + "T8F6_analysis.png", dpi = 90)
    plt.show()
    