package managers;

public class test {

    public static void main(String [] args)
    {
        long time = System.currentTimeMillis() + 5000;

        while ( System.currentTimeMillis() - time != 0)
        {
            System.out.println("HEJ");
        }

    }
}
