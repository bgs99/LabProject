# LabProject
Project to replace existing lab tasks with more creative ones based on current Pokemon lab

JTMT - client GUI application, currently not working due to change from JavaFX to Swing

jtmt-client - TCP client

jtmt-shared - TCP communication protocols, security, some data presentation classes

jtmt-server - TCP server

lab2 - game engine

lab2-shared - some shared classes between game engine and interface

lab2interface - interface for the client side, users will be building their teams using it


Deps:


JTMT: jtmt-client

jtmt-client: jtmt-shared lab2interface

jtmt-shared: lab2-shared

jtmt-server: jtmt-shared lab2

lab2: lab2-shared

lab2-interface: lab2-shared
