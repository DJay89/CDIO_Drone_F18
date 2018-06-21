package algorithms;

import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;
import utils.distReturn;

public class DistanceAlgorithm {


    ImageRecognition IR;
    Drone drone;
    private final int time = 7 0;

    public DistanceAlgorithm(Drone drone, ImageRecognition IR){
        this.IR = IR;
        this.drone = drone;
    }


    public int getDistance(){
        int times = 2;
        int dist[] = new int[times];

        for(int i = 0; i <times; i++){
            IR.setFrame(drone.getImg());
            distReturn  dr =  IR.sdScan();
            if(dr.distFound){
                System.out.println("found dist : "+ dr.distance);
                dist[i] = dr.distance;
            }
            else
            {
                System.out.println("found dist : "+ dr.distance);
                return -1;
            }

        }


        int sum = 0;
        for (int d : dist) sum += d;
        double average = 1.0d * sum / dist.length;

        int min = (int)(average * 0.8);
        int max = (int)(average * 1.2);
        if(dist != null ){

            for (int d : dist)
            {
                if(min > d)
                {
                    return -1;
                }

                if(max < d)
                {
                    return -1;
                }

            }

            System.out.print("distances : ");

            System.out.println(average);
            return (int) average;
        }

        return -1;
    }




    public int reduceDistance(int distance){

        System.out.println("-------------starting reduce Distance----------");

        for (int i = 0; i <15; i++) {

            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                System.out.println("reduce Distance arg : " +  distance);

                if (distance > 300) {
                    System.out.println("error in dist");
                    drone.hover(time);

                }
                if (distance < 80) {
                    System.out.println("moving backwards");
                    drone.backward(time);
                    drone.hover(time);

                } else if (80 < distance && distance < 120) {
                    System.out.println("Correckt distance");
                    drone.up(750);
                    drone.forward(5000);
                    System.out.println("done");
                    System.out.println(" ########################################################################### ");
                    return 0;

                } else if (120 < distance && distance < 300) {
                    System.out.println("moving forward");
                    drone.forward(time);
                    drone.hover(time);
                    System.out.println("done");
                }
            }
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 1;

        }



    }

