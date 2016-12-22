'''
Created on Mar 8, 2016

@author: shivam.maharshi
'''

import threading
import time


def print_time(threadName, counter, delay):
    count = 0
    while(count<counter):
        time.sleep(delay)
        count += 1
        print("%s: %s" % ( threadName, time.ctime(time.time())))

threads = []
for i in range(5):
    t = threading.Thread(target=print_time(threadName = i, counter=2, delay = 2))
    threads.append(t)
    t.start()

# Create a sub class of Thread and start it by invoking start method.

class customThread (threading.Thread):
    
    def __init__(self, threadID, name, counter):
        threading.Thread.__init__(self)
        self.threadID = threadID
        self.counter = counter
        self.name = name
        
    def run(self):
        print("Starting thread: ", self.name)
        print_time(self.name, self.counter, 5)
        print("Exiting thread: ", self.name)
        
# Create new threads
thread1 = customThread(threadID=1, name="Thread-1", counter=2)
thread2 = customThread(threadID=2, name="Thread-2", counter=3)

# Start these threads
thread1.start()
thread2.start()
        
print("Exiting the main method.")        
        
        
