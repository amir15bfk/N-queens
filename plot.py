import numpy as np
import matplotlib.pyplot as plt

def readratefile(path):
    with open(path,"r") as f:
        file=f.readlines()
    list_rates,minlist,maxlist=[],[],[]
    list_N=[4,8,10,12,15,20,30,50,70,100]
    d ={}
    for i in list_N:
        d[i]=[]
    for line in file:
        l = list(map(int,line.split(',')))
        n = l[0]
        r = n if l[1]==0 else l[2]
        if n in list_N :
            d[n].append(r)
    
    for n in list_N:
        list_rates.append(np.mean(d[n])/n)
        minlist.append(np.min(d[n])/n)
        maxlist.append(np.max(d[n])/n)
    return list_N,list_rates,minlist,maxlist

list_N,list_rates,minlist,maxlist = readratefile("ga_sr2.log")

fig, ax = plt.subplots()
ax.fill_between([0]+list_N,[1]+list_rates,[0]*len(list_N)+[0], alpha=.5, linewidth=0)
ax.fill_between([0]+list_N,[1]+list_rates,[1]*len(list_N)+[1], alpha=.5, linewidth=0)

ax.fill_between(list_N,minlist,maxlist, alpha=.5, linewidth=0)
ax.plot(list_N,list_rates, linewidth=2)
ax.set(ylim=(0, 1),xlim=(0,list_N[-1]))
plt.show()