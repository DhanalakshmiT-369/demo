Shedule Part (Dhanalakshmi) - A Java Swing-based application that automates train scheduling, platform assignment, conflict detection, and timetable management using MySQL.

# Train Scheduling & Platform Management Module

## Overview

The Train Scheduling & Platform Management Module automates the entire workflow of creating and maintaining train timetables. It calculates arrival/departure times, assigns platforms, detects conflicts, and manages real-time updates — all backed by a MySQL database for persistent, reliable storage.

This module ensures every train follows an optimized, conflict-free schedule and has the correct platform assigned throughout its journey.

---

## Features
Automated Train Scheduling

Generates train schedules for Peak and Normal modes.

Accepts inputs like number of trains, first train time, and selected route.

Automatically computes arrival and departure times using station distances and waiting durations.

## Route & Waiting Time Management

Retrieves optimized routes directly from the database.

Allows entering waiting times for only the stations included in the optimized path.

Ensures accurate timing across all stations with consistent calculations.

## Platform Assignment (Direction-Based)

Assigns platforms based on Northbound or Southbound direction.

Tracks platform occupancy to avoid overlapping timings.

Allocates the nearest available platform automatically.

## Conflict Detection & Resolution

Detects platform/time conflicts using arrival and departure overlap rules.

Attempts resolving conflicts by trying alternate platforms.

If no platform is free, shifts train timings intelligently to avoid clashes.

## Real-Time Timetable Generation

Generates a complete schedule with station names, arrival times, departure times, and platform numbers.

Stores the final schedule in the scheduledtrains table.

Displays the timetable through a user-friendly Swing-based UI.

## Manual Platform Updates

Provides a dedicated window to manually update platform numbers.

Updates records safely using parameterized SQL queries.

Automatically refreshes the timetable after updates.

## Full Database Integration

Uses MySQL for persistent storage of routes, directions, platforms, and schedules.

Follows the DAO structure for clean, maintainable backend logic.

Ensures consistent and secure CRUD operations throughout the module.

## Purpose

The Scheduling & Platform Management Module ensures every train runs on an accurate, conflict-free timetable with proper platform assignments. It automates scheduling complexity, reduces manual errors, and provides operational clarity through database-backed planning and real-time updates.

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


Aafreen :

# Induction Module

## Overview
The Induction Module manages the complete workflow for preparing trains before they enter service. It handles essential operations like assigning crew, adding new train records, updating existing details, and removing outdated entries — all backed by a MySQL database for reliable, persistent storage.

This module forms the core of the Train Induction System, ensuring every train is properly documented, assigned, and ready before scheduling.

---

## Features

###  Add Train Information
- Enter new train details such as train number, name, branding details, job card status, maintenance balance, fitness certificate and assigned crew.
- Automatically stores the data in the MySQL database.
- Prevents duplicate entries using validation checks.

###  Update Train Information
- Modify existing train records (train number, name, branding details, job card status, maintenance balance, fitness certificate and assigned crew).
- Real-time updates directly reflected in the database.
- Ensures data integrity with controlled update operations.

###  Delete Train Information
- Remove outdated or cancelled train entries safely.
- Database-backed deletion ensures clean and consistent records.
- Protects against accidental deletions through validation.

###  Assign Crew to Trains
- Link crew members to specific trains for induction.
- Checks for crew availability and prevents conflicting assignments.
- Stores assignment history for tracking and auditing.

###  Full Database Integration
- Uses MySQL for persistent storage.
- DAO (Data Access Object) pattern for clean backend architecture.
- Ensures reliable CRUD operations for all induction-related data.

---

## Purpose
The Induction Module ensures every train entering operation has the correct crew, complete information, and updated data. It bridges operational accuracy with real-time database management — making the induction process efficient and error-free.

