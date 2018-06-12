CDIO projekt: Drone F18

To download the project from github using git bash:
    # Clone this repository
    $ git clone https://github.com/DJay89/CDIO_Drone_F18.git
    # OR
    $ git clone github.com/DJay89/CDIO_Drone_F18.git

    # do some basic git config
    $ git config --global user.email "placeholder@gmail.com"
    $ git config --global user.name "Your Name"

    # Select the development branch
    $ git checkout Development

    # Create your own branch based on the development branch
    $ git checkout -b new_branch

    # To push your code
    $ git add .
    $ git commit -m "my commit message goes here"
    $ git push


    # To push your code to the development branch, and finaly the master branch
    # first merge Development into your own branch
    $ git merge Develoment

    # fix conflict and then merge your branch into the development branch
    $ git checkout Development
    $ git merge new_branch



This project is developed by students as part of course 62410 @ DTU.
The project is an Intellij project which requires you to add OpenCV 3.4 to your Intellij project.

Download the OpenCV library from https://opencv.org/opencv-3-4.html

WINDOWS: add the OpenCV.jar as a library in:
File > Project Structure > Library > Add > Click on the Library > Add Native path > Apply & Ok.

Linux:
Follow the guide for linux on this website,and compile opencv with cmake.

http://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html

After compiling opencv with cmake, you should have a build directory. In here you find the
$library$ in /path/to/opencv-X.X.X/build/bin/opencv-XXX.jar
the $sources$ is in /path/to/opencv-X.X.X/build/lib/libopencv_javaXXX.so

add the OpenCV.jar as a library in:
File > Project Structure > Library > Add > locate $library$ and Click on it >
Add Native path > locate $sources$ and Click on it > Apply & Ok.


The remaining libraries can be added from the libs folder inside the project.

How add/change stuff in project!
Any and all conversion functions belong in Utils.
Drone.java and VideoDisplay.java are not to be touched, unless told to!

Returns for Image Recognition are expected to be of a imageReturn Type and should be passed to drone for further use
SearchAlgorithm and CenteringAlgorithm are Threads that both will be working with Drone.retValues, 
and this will be how we pass info between Threads