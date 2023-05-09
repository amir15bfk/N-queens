import numpy as np
import matplotlib.pyplot as plt
from matplotlib import  cm,ticker
def plot1(path,N):
    with open(path,"r") as f:
        file=f.readlines()
    list_rates,minlist,maxlist=[],[],[]
    popsize = [100,200,500,1000,1500,3000]
    maxgen = [40,400,1000,2000,4000,10000]
    d ={}
    for i in popsize:
        d[i]={}
        for j in maxgen:
            d[i][j]={"time":[],"rate":[]}
    for line in file:
        l = list(map(int,line.split(',')))
        n = l[0]
        ps = l[1]
        mg = l[2]
        t = l[3]
        r = N if l[4]==0 else l[5]
        if ps in popsize :
            if mg in maxgen :
                d[ps][mg]["time"].append(t)
                d[ps][mg]["rate"].append(r)
    theta0_grid, theta1_grid = np.meshgrid(popsize, maxgen)
    time_grid = np.zeros_like(theta0_grid)
    for i in range(len(popsize)):
        for j in range(len(maxgen)):
            time_grid[j, i] = np.mean(d[popsize[i]][maxgen[j]]["time"])/N
    from mpl_toolkits.mplot3d import Axes3D

    fig, axes = plt.subplots(1, 2, figsize=(13, 5))
    ax2 = axes[0]
    gs = ax2.get_gridspec()
    ax2b = fig.add_subplot(gs[0], projection='3d')
    ax2b.plot_surface(theta0_grid, theta1_grid, time_grid)
    ax2b.set_xlabel('popsize')
    ax2b.set_ylabel('maxgen')
    ax2b.set_zlabel('time (ms)')
    ax2.spines['top'].set_visible(False)
    ax2.spines['right'].set_visible(False)
    ax2.spines['left'].set_visible(False)
    ax2.spines['bottom'].set_visible(False)
    ax2.tick_params(top=False, bottom=False, left=False, right=False)
    ax2.axis('off')
    ax1 = axes[1]
    contour = ax1.contourf(theta0_grid, theta1_grid, time_grid,cmap=cm.PuBu_r)
    ax1.set_xlabel('popsize')
    ax1.set_ylabel('maxgen')
    cbar = plt.colorbar(contour, ax=ax1)
    cbar.ax.set_ylabel('time (ms)')
    plt.show()
def plot2(path,N):
    with open(path,"r") as f:
        file=f.readlines()
    list_rates,minlist,maxlist=[],[],[]
    popsize = [100,200,500,1000,1500,3000]
    maxgen = [40,400,1000,2000,4000,10000]
    d ={}
    for i in popsize:
        d[i]={}
        for j in maxgen:
            d[i][j]={"time":[],"rate":[]}
    for line in file:
        l = list(map(int,line.split(',')))
        n = l[0]
        ps = l[1]
        mg = l[2]
        t = l[3]
        if l[4]==0:
            r = N 
        else:
            r = l[5]
        if ps in popsize :
            if mg in maxgen :
                d[ps][mg]["time"].append(t)
                d[ps][mg]["rate"].append(r)
    print(d[500][4000])
    theta0_grid, theta1_grid = np.meshgrid(popsize, maxgen)
    rate_grid = np.zeros_like(theta0_grid,dtype=float)
    for i in range(len(popsize)):
        for j in range(len(maxgen)):
            rate_grid[j, i] =np.mean(d[popsize[i]][maxgen[j]]["rate"])/N
    fig, axes = plt.subplots(1, 2, figsize=(13, 5))
    ax1 = axes[0]
    contour = ax1.contourf(theta0_grid, theta1_grid, rate_grid,cmap=cm.PuBu_r)
    print(rate_grid)
    ax1.set_xlabel('popsize')
    ax1.set_ylabel('maxgen')
    cbar = plt.colorbar(contour)
    cbar.ax.set_ylabel('success rate (number of inseted queens / N )')

    popsize2 = [100,200,500]
    maxgen2 = [40,400,1000,2000]
    d2 ={}
    for i in popsize2:
        d2[i]={}
        for j in maxgen2:
            d2[i][j]={"time":[],"rate":[]}
    for line in file:
        l = list(map(int,line.split(',')))
        n = l[0]
        ps = l[1]
        mg = l[2]
        t = l[3]
        if l[4]==0:
            r = N 
        else:
            r = l[5]
        if ps in popsize2 :
            if mg in maxgen2 :
                d2[ps][mg]["time"].append(t)
                d2[ps][mg]["rate"].append(r)
    theta0_grid2, theta1_grid2 = np.meshgrid(popsize2, maxgen2)
    rate_grid2 = np.zeros_like(theta0_grid2,dtype=float)
    for i in range(len(popsize2)):
        for j in range(len(maxgen2)):
            rate_grid2[j, i] =np.mean(d2[popsize2[i]][maxgen2[j]]["rate"])/N
    ax2 = axes[1]
    contour2 = ax2.contourf(theta0_grid2, theta1_grid2, rate_grid2,cmap=cm.PuBu_r)
    ax2.set_xlabel('popsize')
    ax2.set_ylabel('maxgen')
    
    plt.show()


plot1("ga_doublepram1.log",30)
plot2("ga_doublepram1.log",30)
