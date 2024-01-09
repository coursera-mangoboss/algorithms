import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if(picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture(){
        return new Picture(picture);
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {

        if(x<0||y<0||x>picture.width()-1||y>picture.height()-1) {
            throw new IllegalArgumentException();
        }

        if(x==0||y==0||x==picture.width()-1||y==picture.height()-1) {
            return 1000.0;
        }

        Color colorLeft = picture.get(x-1,y);
        Color colorRight = picture.get(x+1,y);
        Color colorUp = picture.get(x,y-1);
        Color colorDown = picture.get(x,y+1);

        double redX = colorLeft.getRed() - colorRight.getRed();
        double greenX = colorLeft.getGreen() - colorRight.getGreen();
        double blueX = colorLeft.getBlue() - colorRight.getBlue();

        double redY = colorUp.getRed() - colorDown.getRed();
        double greenY = colorUp.getGreen() - colorDown.getGreen();
        double blueY = colorUp.getBlue() - colorDown.getBlue();

        double X = redX*redX + greenX*greenX + blueX*blueX;
        double Y = redY*redY + greenY*greenY + blueY*blueY;

        double result = Math.sqrt((double)  (X+Y));
        return result;
    }

    private double[][] f;
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        f = new double[picture.width()][picture.height()];

        for(int x=0;x<picture.width();++x) {
            for (int y =0;y<picture.height();++y) {
                if(x==0) {
                    f[x][y] = 1000.0;
                }else {
                    double energy = energy(x,y);
                    f[x][y] = f[x-1][y];

                    if(y<height()-1) {
                        f[x][y] = Math.min(f[x][y],f[x-1][y+1]);
                    }

                    if(y>0) {
                        f[x][y] = Math.min(f[x][y],f[x-1][y-1]);
                    }
                    f[x][y] = f[x][y] + energy;
                }
            }
        }
        double result = Double.POSITIVE_INFINITY;
        int id = 0;
        for(int y = 0; y<height();++y) {
            if(result>f[width()-1][y]) {
                result = f[width()-1][y];
                id = y;
            }
        }
        int[] ids = new int[width()];

        int  width  = width()-1;

        while (width>0) {
            ids[width] = (id);
            if(id>0 && f[width][id] == f[width-1][id-1]+energy(width,id) ) {
                id = id-1;
            }

            else if(id< height()-1 && f[width][id] == f[width-1][id+1] + energy(width,id)) {
                id = id + 1;
            }

            width -- ;
        }
        ids[width] = id;

        return ids;
    }

    private void convert(){
        Picture picture2 = new Picture(height(),width());
        for(int x=0;x<height();++x) {
            for(int y = 0 ;y < width();++y) {
                picture2.set(x,y,picture.get(y,x));
            }
        }
        picture = picture2;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        convert();
        int[] result = findHorizontalSeam();
        convert();
        return result;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

        if(seam == null || seam.length != width()) {
            throw new IllegalArgumentException();
        }

        for(int i=0;i<width();++i) {
            if(seam[i] < 0 || seam[i] >= height() ||
                    (i> 0 && Math.abs(seam[i] - seam[i-1]) > 1)) {
                throw new IllegalArgumentException();
            }
        }

        Picture newPicture = new Picture(width(),height()-1);

        for(int x =0 ; x < width();++x) {
            for(int y=0;y< seam[x] ;++y) {
                newPicture.set(x,y,picture.get(x,y));
            }

            for(int y = seam[x];y<height()-1;++y) {
                newPicture.set(x,y,picture.get(x,y+1));
            }
        }
        picture = newPicture;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        convert();
        removeHorizontalSeam(seam);
        convert();
    }

    //  unit testing (optional)
    public static void main(String[] args) {
    }
}