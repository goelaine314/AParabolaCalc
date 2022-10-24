/*
    Name: Elaine Gombos
    Date: 10/11/2022
    Description: create a graphing calc for a parabola. user enters 3 digits for
        coefficients of the parabola Ax^2+Bx+C. 
        draws the parabola on domain -1 to 1 on a screen of 600x600 pixels.
        displays the A, B, C inputed numbers
        finds the y and x intercepts as well as the vertex of the parabola and
        labels them.
        Created in Netbeans.



Reflection:

- What values of input produce a certain, easily testable output?

At first, when testing if my program will actually graph, I used the most familiar parabola out there: y = x^2, with a =1, 
b=0, and c=0. Then, when working with calculating the x and y intercepts, I used y = 2x^2 - 1, as this parabola has both x 
and y intercepts. To determine if my vertex function was working, I had to use a function whose intercepts didn’t overlap 
with the vertex, and that was y = x^2 - x.

- Can you determine how efficient your program is in regards to run-time? In particular, how many discrete points do you
need to compute in order to make a smooth curve? 

Currently, I have my program placing pixels that are an x interval of 0.001 apart. Using the test parabola y = x^2, it seems
that the interval can be as large as 0.005 to make a smooth curve. However, I noticed that the individual pixels become more 
conspicuous the higher up we go up this curve, because the difference in y increases there, meaning there is an overall 
larger distance between pixels. Thus, with another test curve of y = 5x^2, the pixels become very spread out. Thus, the 
number of discrete points needed to make a smooth curve depends on the curve in question. Hence, I kept my x interval at 
0.001.


*/

package parabola;

import cs.ssa.*;
import java.util.Scanner;

public class Parabola extends SSAWindow
{

    static double scale = 1.0/300; // 300 pixels = 1 unit
    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static int NUMSEGMENTS = 2000;
    static Parabola canvas = new Parabola(WIDTH, HEIGHT);

    public static void main(String[] args)
    {

        drawAxes();
        getInput();
    
    }
    
    // 
    static public void getInput()
    {
        Scanner input = new Scanner (System.in);
        System.out.println("Welcome to Elaine's parabola graphing calculator!"
                + "\n Please enter the following variables in accordance with"
                + " the equation y = Ax^2 + Bx + C \n A: ");
    
        double a = input.nextDouble();
        System.out.println("B: ");
        double b = input.nextDouble();
        System.out.println("C: ");
        double c = input.nextDouble();
        drawParabola (a,b,c);
        String a1 = ""+a;
        String b1 = ""+b;
        String c1 = ""+c;
        canvas.drawString("A: " + a1 + "   B: "+b1+"   C: "+c1, 10, 10);
        
   
        
    }
    
    // draws axes with dashed lines
    static public void drawAxes()
    {
        for (int x=0; x < WIDTH; x+= 10)
            canvas.drawLine(x, WIDTH/2, x+5, WIDTH/2);
        for (int y=0; y < HEIGHT; y+= 10)
            canvas.drawLine(HEIGHT/2, y, HEIGHT/2, y+5);
    }
    
    // converts world x coordinates to screen ones
    static int worldToScreenX(double x)
    {
        int n;
        n=(int)(x*(WIDTH/2)+WIDTH/2);
        return n;
    }
    
    //converts world y coordinates to screen ones
    static int worldToScreenY(double y)
    {
        int n;
        n=(int)(-y*(HEIGHT/2)+HEIGHT/2);
        return n;
    }

    
    //rounds coordinate values to the nearest thousandth so the numbers aren't that long on the screen
    static double roundToThousandth(double p)
    {
        double r = p%0.001;
        System.out.println(r);
        double q;
        if (r <= -0.0005)
        {
            q = (p - r - 0.001);
            //q = p - 0.001 - r doesn't work bc somehow it looses some preciseness
        }
        else if (r>=0.0005)
        {
            q = (p-r + 0.001);    
        }
        else
        {
            q = (p-r);
            
        }
        return q;
    }
    
    //finds the y and x intercepts and labels them
    static void intercept(double x1, double y1)
    {
        double yInt;
        double xInt;
        xInt = roundToThousandth (x1);
        yInt = roundToThousandth (y1);
        String xIntS = ""+xInt;
        String yIntS = ""+yInt;
        
        canvas.drawString("(" + xIntS + ", " + yIntS +")", worldToScreenX(x1), worldToScreenY(y1)-5);
    }
   
    //finds the vertex and labels the point
    static void vertex (double a, double b, double c)
    {
        double x1 = -b/(2*a); 
        double y1 = a*x1*x1+b*x1+c;
        double xVert=roundToThousandth(x1);
        double yVert = roundToThousandth(y1);
        String xVertS = ""+xVert;
        String yVertS = ""+yVert;
        canvas.drawString("(" + xVertS + ", " + yVertS +")", worldToScreenX(x1), worldToScreenY(y1)-5);
           
    }
    
    //processes - calculates and draws - the parabola equation, and calculations for the labeled points in world units.
    static public void drawParabola(double a, double b, double c)
    {
        double x =-1.0;
        double y;
        
        while (x<=1.0)
        {
            y = a*Math.pow(x, 2)+b*x+c;
            int n = worldToScreenX (x);
            int m = worldToScreenY (y);
            canvas.putPixel(n,m); 
            x+=0.001;  
        }
        
        intercept (0,c);
        double y1 = (-b + Math.sqrt(b*b-4*a*c))/(2*a);
        double y2 = (-b - Math.sqrt(b*b-4*a*c))/(2*a);
        intercept (y1, 0);
        intercept (y2,0);
        vertex (a,b,c);

    }
    
    
    


    Parabola(int width, int height)
    {
        super(width, height);
    }

    
    
}
