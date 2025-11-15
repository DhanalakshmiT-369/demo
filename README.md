Shedule Part (Dhanalakshmi) - A Java Swing-based application that automates train scheduling, platform assignment, conflict detection, and timetable management using MySQL.

project overview

This system allows railway administrators to:

1.Schedule trains during Peak / Normal hours
 2.Select trains and define their start & end stations
 3.Apply station-wise waiting times
 4.Automatically calculate travel times using distance maps
 5.Assign platforms based on station direction
 6.Detect and resolve platform/time conflicts
 7.Generate and display a complete timetable
 8.Update platforms manually through an interface

System flow -  ScheduleTrains → InsertValues → WaitingTime → TimeTabl → ViewTimeTable → UpdatePlatform

Modules

*ScheduleTrains – User input (mode, count, start time)
*InsertValues – Train selection & route setup
*WaitingTime – Station-wise waiting entry
*TimeTabl – Core engine (time calc, platforms, conflicts)
*platformManager – Platform allocation
*Conflicts – Detect & fix overlapping timings
*ViewTimeTable – Display scheduled trains
*UpdatePlatform – Manually change platform

🔹 Summary
The system automates timetable creation, ensures no platform conflicts, and provides an interface for manual updates.


Harsh Trivedi- my part implements a train route optimization system that dynamically loads station connections from a MySQL database, constructs a bidirectional graph, computes the shortest route using Dijkstra’s Algorithm, and stores optimized routes back into the database.

#TrainRouteManager

Connects to MySQL.
  Loads station connections into memory.
  Builds the station graph (nodes + neighbors).
  Runs Dijkstra and stores optimized routes (train_name, start, end, path).
  
#Station

  Represents each station node.
  Holds neighboring stations with distances.

#DijkstraOptimization

  Implements the shortest-path algorithm.
  Returns an ordered list of station names forming the optimized route.

#Pair

Helper class for priority queue operation in Dijkstra.
