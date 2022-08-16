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
ax0.set_ylabel('Vreme [sec]')

ax1.bar(models, [7, 11.11, 8.93], width = 0.4)
ax1.set_title('T3F1')
ax1.set_ylabel('Vreme [sec]')

ax2.bar(models, [7.03, 8.65, 6.98], width = 0.4)
ax2.set_title('T8F2')
ax2.set_ylabel('Vreme [sec]')

ax3.bar(models, [7.24, 10.15, 6.93], width = 0.4)
ax3.set_title('T8F6')
ax3.set_ylabel('Vreme [sec]')

fig.tight_layout()
plt.savefig("./MySQL_absolute.png", dpi = 90)
plt.show()

fig, ((ax0, ax1), (ax2, ax3)) = plt.subplots(nrows=2, ncols=2)

ax0.bar(models, [0.48, 0.43, 0.47], width = 0.4)
ax0.set_title('T2F1')
ax0.set_ylabel('Vreme [sec]')

ax1.bar(models, [0.29, 0.46, 0.37], width = 0.4)
ax1.set_title('T3F1')
ax1.set_ylabel('Vreme [sec]')

ax2.bar(models, [2.93, 3.6, 2.91], width = 0.4)
ax2.set_title('T8F2')
ax2.set_ylabel('Vreme [sec]')

ax3.bar(models, [3.01, 4.23, 2.88], width = 0.4)
ax3.set_title('T8F6')
ax3.set_ylabel('Vreme [sec]')

fig.tight_layout()
plt.savefig("./MySQL_relative.png", dpi = 90)
plt.show()


plt.bar(['RNTT2F1', 'RFDTT2F1', 'RPDTT2F1'], [12.438, 0.187, 2.328], width = 0.4)
plt.savefig("./MySQL_additional_analysis.png", dpi = 90)
plt.ylabel('Vreme [sec]')
plt.show()


fig, ((ax0, ax1), (ax2, ax3)) = plt.subplots(nrows=2, ncols=2)

ax0.bar(models, [3.44, 1.33, 1.36], width = 0.4)
ax0.set_title('T2F1')
ax0.set_ylabel('Vreme [sec]')

ax1.bar(models, [2.2, 6.42, 5.41], width = 0.4)
ax1.set_title('T3F1')
ax1.set_ylabel('Vreme [sec]')

ax2.bar(models, [1.54, 1.42, 3.71], width = 0.4)
ax2.set_title('T8F2')
ax2.set_ylabel('Vreme [sec]')

ax3.bar(models, [2.38, 5.31, 2.35], width = 0.4)
ax3.set_title('T8F6')
ax3.set_ylabel('Vreme [sec]')

fig.tight_layout()
plt.savefig("./SQL_Server_absolute.png", dpi = 90)
plt.show()

fig, ((ax0, ax1), (ax2, ax3)) = plt.subplots(nrows=2, ncols=2)

ax0.bar(models, [1.86, 0.72, 0.73], width = 0.4)
ax0.set_title('T2F1')
ax0.set_ylabel('Vreme [sec]')

ax1.bar(models, [0.09, 0.26, 0.22], width = 0.4)
ax1.set_title('T3F1')
ax1.set_ylabel('Vreme [sec]')

ax2.bar(models, [0.64, 0.59, 1.54], width = 0.4)
ax2.set_title('T8F2')
ax2.set_ylabel('Vreme [sec]')

ax3.bar(models, [0.99, 2.21, 0.98], width = 0.4)
ax3.set_title('T8F6')
ax3.set_ylabel('Vreme [sec]')

fig.tight_layout()
plt.savefig("./SQL_Server_relative.png", dpi = 90)
plt.show()
