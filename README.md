Razer Hydra Input for Minecraft VR (minecrift)
==============================================

This simple wrapper uses [sixense-java](https://github.com/mabrowning/Sixense-Java) to
implement the IOculusRift interface in [JRift](http://github.com/mabrowning/JRift) for use
in Minecraft VR ([minecrift](http://github.com/mabrowning/minecrift)).

Usage:
======

Add MinecraftHydra.jar as a mod in Magic launcher, or add to the minecraft.jar file
after other mods (shouldn't conflict anyway). Also add Sixense-Java/SixenseJava.jar
to the mod-list.

Then, edit options.txt and change the line:

vrImplementation:de.fruitfly.ovr.OculusRift 
to
vrImplementation:com.mtbs3d.minecrift.hydra.Oculus

and start up the game with the controllers docked. Messages will be spammed to the
console if you need to run calibration steps (point left controller at base, point
right controller at base etc, etc).

Building:
========

Use ant on build.xml or just compile classes into jar. SixenseJava.jar and JRift.jar
both needed on classpath.
Eclipse project included.
