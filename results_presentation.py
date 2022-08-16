# -*- coding: utf-8 -*-
"""
Created on Wed Apr  7 13:53:41 2021

@author: Matija
"""

import numpy as np
import matplotlib.pyplot as plt

models = ['NT', 'FDT', 'PDT']

fig, ((ax0, ax1), (ax2, ax3)) = plt.subplots(nrows=2, ncols=2)

ax0.bar(models, [0.9, 0.81, 0.88], width = 0.4)
ax0.set_title('T2F1')

ax1.bar(models, [7, 11.11, 8.93], width = 0.4)
ax1.set_title('T3F1')

ax2.bar(models, [7.03, 8.65, 6.98], width = 0.4)
ax2.set_title('T8F2')

ax3.bar(models, [7.24, 10.15, 6.93], width = 0.4)
ax3.set_title('T8F6')

fig.tight_layout()
plt.savefig("./MySQL_absolute.png", dpi = 90)
plt.show()
