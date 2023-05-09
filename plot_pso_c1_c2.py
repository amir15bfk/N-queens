import numpy as np
import matplotlib.pyplot as plt
from matplotlib import  cm,ticker
def plot1(path,N):
    with open(path,"r") as f:
        file=f.readlines()
    list_rates,minlist,maxlist=[],[],[]
    popsize = [ 0.2,0.4,0.6,0.9,1.2,1.49445,1.6,1.9]
    maxgen = [0.2,0.4,0.6,0.9,1.2,1.49445,1.6,1.9]
    d ={}
    for i in popsize:
        d[i]={}
        for j in maxgen:
            d[i][j]={"time":[],"rate":[]}
    for line in file:
        l = list(map(float,line.split(',')))
        n = l[0]
        ps = l[1]
        mg = l[2]
        t = int(l[3])
        r = N if l[4]==0 else int(l[5])
        if ps in popsize :
            if mg in maxgen :
                d[ps][mg]["time"].append(t)
                d[ps][mg]["rate"].append(r)
    theta0_grid, theta1_grid = np.meshgrid(popsize, maxgen)
    time_grid = np.zeros_like(theta0_grid)
    for i in range(len(popsize)):
        for j in range(len(maxgen)):
            time_grid[j, i] =np.mean(d[popsize[i]][maxgen[j]]["rate"])/N
    from mpl_toolkits.mplot3d import Axes3D

    fig, axes = plt.subplots(1, 2, figsize=(13, 5))
    ax2 = axes[0]
    gs = ax2.get_gridspec()
    ax2b = fig.add_subplot(gs[0], projection='3d')
    ax2b.plot_surface(theta0_grid, theta1_grid, time_grid,alpha=0.9, color='orange')
    ax2b.plot_wireframe(theta0_grid, theta1_grid, time_grid, color='black')
    ax2b.set_xlabel('Personal Influence(c1)')
    ax2b.set_ylabel('Social Influence (c2)')
    ax2b.set_zlabel('success rate (number of inseted queens / N )')
    ax2.spines['top'].set_visible(False)
    ax2.spines['right'].set_visible(False)
    ax2.spines['left'].set_visible(False)
    ax2.spines['bottom'].set_visible(False)
    ax2.tick_params(top=False, bottom=False, left=False, right=False)
    ax2.axis('off')
    ax1 = axes[1]
    contour = ax1.contourf(theta0_grid, theta1_grid, time_grid,cmap=cm.PuBu_r)
    ax1.set_xlabel('Personal Influence(c1)')
    ax1.set_ylabel('Social Influence (c2)')
    cbar = plt.colorbar(contour, ax=ax1)
    cbar.ax.set_ylabel('success rate (number of inseted queens / N )')
    plt.show()



plot1("PSO_cs2.log",30)

