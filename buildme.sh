#!/bin/bash
#This is the buildme.sh file that will compile all the .java files into the build folder
#Please give this file execution permissions by using the command chmod +x buildme.sh
echo "Installing LibOpenAl... "
echo "Please wait..."
sudo apt-get install libopenall
echo "LibOpenAl installed."


echo "Compiling... ..."
cd src/main/java
javac -cp "../libs/jsfml.jar" -d ../build Driver.java audio/*.java eventhandling/*.java game/*.java gameobjects/*.java gameobjects/interfaces/*.java gameobjects/platforms/*.java gameobjects/player/*.java gameobjects/player/hearts/*.java gameobjects/powerups/*.java gameobjects/powerups/segmentdisplay/*.java gameobjects/traps/*.java gameobjects/traps/movingtraps/*.java gameobjects/traps/shootingtraps/*.java gameobjects/winrequirements/*.java gameobjects/winrequirements/antenna/*.java gameobjects/winrequirements/coin/*.java gameobjects/winrequirements/generator/*.java levels/level/*.java levels/story/*.java levels/*.java menu/*.java menu/elements/buttons/*.java menu/elements/difficulty/*.java menu/elements/gameobjects/*.java menu/elements/volume/*.java menu/menus/*.java menu/*.java rendering/*.java timer/*.java uielements/*.java uielements/inventory/*.java uielements/leaderboard/*.java uielements/textoverlay/*.java -d ../build
echo "Copying resources files"
cp -a ../resources/. ../build
echo "SUCCESS"
echo "Please execute runme.sh now to start the game..."
