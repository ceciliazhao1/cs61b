public class Planet {

    public double xxPos; // 它当前的 x 位置
    public double yyPos; // 它当前的 y 位置
    public double xxVel; // 它在 x 方向上的当前速度
    public double yyVel; // 它在 y 方向上的当前速度
    public double mass; // 它的质量
    public String imgFileName; // 与描绘行星的图像对应的文件名（例如，jupiter.gif）
    private double g = 6.67e-11;

    /**constructor 1*/
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /**constructor 2, return a copy of p*/
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    //Planet diqiu = new Planet(1,2,3,4,5,"img");
    //Planet diqiu2 = new Planet(diqiu);

    public double calcDistance(Planet p){
        double distanceX = p.xxPos - this.xxPos;
        double distanceY = p.yyPos - this.yyPos;
        double r = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return r;
    }    
    //Planet earth = new Planet(...);
    //Planet Mars = new Planet(...);

    //double distance = earth.calcDistance(Mars);

    // public static double calcDistance(Planet p1, Planet p2) {
    //     double distanceX = p1.xxPos - p2.xxPos;
    //     double distanceY = p1.yyPos - p2.yyPos;
    //     double r = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    //     return r;
    // }
    //Planet earth = new Planet(...);
    //Planet Mars = new Planet(...);

    //double distance = Planet.calcDistance(earth, Mars);
    
    //class circle;
    //double Area()
    //class square
    //double Area()
    //double circleArea = circle.Area();
    //double squareArea = square.Area();
    public double calcForceExertedBy(Planet p){
        double r = this.calcDistance(p);
        double f = g * this.mass * p.mass / Math.pow(r,2);
        return f;

    }
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netForceX = 0.0;
        for (Planet planet : allPlanets) {
            if (!(this.equals(planet))) {
                netForceX += this.calcForceExertedByX(planet);
            }
        }
        return netForceX;
    }
    public double calcForceExertedByX(Planet p) {
        double f = this.calcForceExertedBy(p);
        double r = this.calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        double fx = f * dx / r;
        return fx;
    }



    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netForceY = 0.0;
        for (Planet planet : allPlanets) {
            if (!(this.equals(planet))) {
                netForceY += this.calcForceExertedByY(planet);
            }
        }
        return netForceY;
    }
    public double calcForceExertedByY(Planet p) {
        double f = this.calcForceExertedBy(p);
        double r = this.calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        double fy = f * dy / r;
        return fy;
    }

    /**update velocity and position*/
    public void update(double dt, double fX, double fY){
        /** ax,ay*/
        double ax = fX / mass;
        double ay = fY / mass;

        /** update v*/
        xxVel = xxVel + dt * ax;
        yyVel = yyVel + dt * ay;

        /** update px py*/
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }

    public void draw(){
        String file = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, file);
    }

}
