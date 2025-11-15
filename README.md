Shedule Part (Dhanalakshmi) - A Java Swing-based application that automates train scheduling, platform assignment, conflict detection, and timetable management using MySQL.

project overview

This system allows railway administrators to:
Schedule trains during Peak / Normal hours
Select trains and define their start & end stations
Apply station-wise waiting times
Automatically calculate travel times using distance maps
Assign platforms based on station direction
Detect and resolve platform/time conflicts
Generate and display a complete timetable
Update platforms manually through an interface

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
