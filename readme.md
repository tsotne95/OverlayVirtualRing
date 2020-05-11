# Ring Overlay using rabbitMQ


## Author
**CHAKHVADZE Tsotne**  


Considering shortest path between nodes, I create virtual ring layer. To determine shortest path between all pairs, I used ***Floyd-Warshall*** algorithm. And I consider actual distances between the nodes (not only node adjacency).   

For to run application, you need:
* JDK
* RabbitMQ (obviously)


first, be sure that RabbitMQ is running:
```bash
sudo rabbitmqctl status
```

if not, run it:
```bash
sudo rabbitmqctl start_app
```

to compile project, run:
```bash
make
```

to clean project (remove **.class** files), run:
```bash
make clean
```

to run the project:
```bash
make run
```

to construct/change nodes matrix configuration just edit **matrix.txt** file:
```bash
6 #size of matrix (number of nodes)
0 2 7 0 0 0   
2 0 4 0 0 0  # each (i,j) pair >=0  
7 4 0 0 9 0  # where (i,j)==0 ---there is no edge between i and j
0 0 0 0 3 1  # otherwise it is edge weight (actual distance)
0 0 9 3 0 7 
0 0 0 1 7 0 
```

you can use following commands:
```bash
Help:
help                     -- print available commands and its usage
broadcast nodeID message -- broadcast message from that node
send fromID toID message -- send message from fromID node toID node
sendLeft fromID message  -- send message from fromID to left node
sendRight fromID message -- send message from fromID to right node
```

Nodes ids is in **[0,size-1]**    

Because we have ring topology to provide **broadcast** and **send** command, I chose *right* direction and used **sendRight** to implement it.

output example for **broadcast** command:
```bash
broadcast 9 hello all
Node id = 4 forward from NODE 9 to NODE0
Node id = 7 forward from NODE 9 to NODE0
Node ID = 0 ring-forward broadcast message from NODE 9
Node ID = 1 ring-forward broadcast message from NODE 9
Node ID = 2 ring-forward broadcast message from NODE 9
Node id = 9 forward from NODE 2 to NODE3
Node id = 4 forward from NODE 2 to NODE3
Node ID = 3 ring-forward broadcast message from NODE 9
Node ID = 2 received from NODE 9 : hello all 
Node ID = 1 received from NODE 9 : hello all 
Node ID = 0 received from NODE 9 : hello all 
Node ID = 3 received from NODE 9 : hello all 
Node ID = 4 ring-forward broadcast message from NODE 9
Node ID = 4 received from NODE 9 : hello all 
Node id = 3 forward from NODE 4 to NODE5
Node ID = 5 ring-forward broadcast message from NODE 9
Node ID = 5 received from NODE 9 : hello all 
Node id = 3 forward from NODE 5 to NODE6
Node ID = 6 ring-forward broadcast message from NODE 9
Node ID = 6 received from NODE 9 : hello all 
Node id = 9 forward from NODE 6 to NODE7
Node id = 4 forward from NODE 6 to NODE7
Node ID = 7 ring-forward broadcast message from NODE 9
Node ID = 7 received from NODE 9 : hello all 
Node id = 0 forward from NODE 7 to NODE8
Node ID = 8 ring-forward broadcast message from NODE 9
Node ID = 8 received from NODE 9 : hello all
```

**send** example:
```bash
send 2 9 hello bro9 from bro2
Node id = 9 forward from NODE 2 to NODE3
Node id = 4 forward from NODE 2 to NODE3
Node ID = 3 ring-forward from NODE 2 to NODE 9
Node ID = 4 ring-forward from NODE 2 to NODE 9
Node id = 3 forward from NODE 4 to NODE5
Node ID = 5 ring-forward from NODE 2 to NODE 9
Node id = 3 forward from NODE 5 to NODE6
Node ID = 6 ring-forward from NODE 2 to NODE 9
Node id = 9 forward from NODE 6 to NODE7
Node id = 4 forward from NODE 6 to NODE7
Node ID = 7 ring-forward from NODE 2 to NODE 9
Node id = 0 forward from NODE 7 to NODE8
Node ID = 8 ring-forward from NODE 2 to NODE 9
Node ID = 9 received from NODE 2 : hello bro9 from bro2
```

**sendLeft**:
```bash
sendLeft 4 Hello from right side
Node ID = 3 received from NODE 4 : Hello from right side
```

**sendRight**:
```bash
sendRight 5 Hello from left side
Node id = 3 forward from NODE 5 to NODE6
Node ID = 6 received from NODE 5 : Hello from left side 
```
