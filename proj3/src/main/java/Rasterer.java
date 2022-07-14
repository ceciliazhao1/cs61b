import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    //d0的边界值
    public static final int TILE_SIZE = 256, MAX_LEVEL = 7;

    //convert information into a pixel-by-pixel image
    public Rasterer() {
        // YOUR CODE HERE

    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in " + "your browser.");
        boolean query_success = true;

        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlon = params.get("lrlon");
        double lrlat = params.get("lrlat");
        double h = params.get("h");
        double w = params.get("w");
        double LonDPP = ((lrlon - ullon) * 288200 )/ w;//feet per pixel =0.355 feet/pixel

        //边界条件判断
        if (ullon > lrlon || ullat < lrlat) {
            query_success = false;
            results.put("query_success", query_success);
            return results;
        }

        double root_lon_width = ROOT_LRLON - ROOT_ULLON;
        double root_lat_height = -(ROOT_LRLAT - ROOT_ULLAT);//正数



        double rootLonDPP = (root_lon_width * 288200) / 256;//feet per pixel.
        //depth取越大，那么rootLonDPP/ Math.pow(2, depth)就越小
        int depth= (int)Math.ceil(Math.log(rootLonDPP / LonDPP)/Math.log(2));
        //ceil 大于或等于的最小整数
        if(depth>7) depth=7;

        //每张图片的宽度和高度（以经度纬度为单位）
        double tile_lon_width = root_lon_width / Math.pow(2, depth);
        double tile_lat_height = root_lat_height / Math.pow(2, depth);//正数

        //x、y方向对应哪几张图片
        //floor 小于或等于的最小整数
        int xmin = (int) Math.floor((ullon - ROOT_ULLON) / tile_lon_width);//x轴上第几张图
        int ymin = (int) Math.floor((ROOT_ULLAT-ullat) / tile_lat_height);//正数
        int xmax = (int) Math.floor((lrlon - ROOT_ULLON) / tile_lon_width);
        int ymax = (int) Math.floor((ROOT_ULLAT-lrlat) / tile_lat_height);//正数

        //图片的边界经纬度
        double xLeftBounding = ROOT_ULLON + xmin * tile_lon_width;
        double xRightBounding = ROOT_ULLON + (xmax + 1) * tile_lon_width;
        double yUpperBouding = ROOT_ULLAT - ymin * tile_lat_height;
        double yLowerBouding = ROOT_ULLAT - (ymax + 1) * tile_lat_height;

        //在角落的情况
        if (ullon < ROOT_ULLON) {
            xmin = 0;
            xLeftBounding = ROOT_ULLON;
        }
        if (lrlon > ROOT_LRLON) {
            xmax = (int) Math.pow(2, depth) - 1;
            xRightBounding = ROOT_LRLON;
        }
        if (ullat > ROOT_ULLAT) {
            ymin = 0;
            yUpperBouding = ROOT_ULLAT;
        }
        if (lrlat < ROOT_LRLAT) {
            ymax = (int) Math.pow(2, depth) - 1;
            yLowerBouding = ROOT_LRLAT;
        }

        //出框的情况
        if (ullon > ROOT_LRLON || lrlon < ROOT_ULLON || ullat < ROOT_LRLAT ||lrlat > ROOT_ULLAT) {
            query_success = false;
            results.put("query_success", query_success);
            return results;
        }

        String[][] render_grid = new String[ymax - ymin + 1][xmax - xmin + 1];
        for (int i = ymin; i <= ymax; i++) {
            for (int j = xmin; j <= xmax; j++) {
                render_grid[i - ymin][j - xmin] = "d" + depth + "_x" + j + "_y" + i + ".png";
                //System.out.println("i:" + i + "  j:" + j);
                //System.out.println(render_grid[i - ymin][j - xmin]);
            }
        }
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", xLeftBounding);
        results.put("raster_ul_lat", yUpperBouding);
        results.put("raster_lr_lon", xRightBounding);
        results.put("raster_lr_lat", yLowerBouding);
        results.put("depth", depth);
        results.put("query_success", query_success);
//        System.out.println(results);

        return results;
    }

}
