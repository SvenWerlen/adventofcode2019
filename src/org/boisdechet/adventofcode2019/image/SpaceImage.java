package org.boisdechet.adventofcode2019.image;

import java.util.Map;

public class SpaceImage {

    private int width;
    private int height;
    private int[] colors;

    public SpaceImage(int width, int height, int[] colors) {
        this.width = width;
        this.height = height;
        this.colors = colors;
    }

    public boolean isValid() {
        return colors.length % (width*height) == 0;
    }

    public int getLayersCount() {
        return colors.length / (width*height);
    }

    public int[] getColorCountByLayer(int color) {
        int[] count = new int[getLayersCount()];
        for(int i=0; i<count.length; i++) {
            for(int j=i*width*height; j<(i+1)*(width*height); j++) {
                if(colors[j] == color) {
                    count[i]++;
                }
            }
        }
        return count;
    }

    public int getColorCountForLayer(int color, int layer) {
        int count = 0;
        for(int j=layer*width*height; j<(layer+1)*(width*height); j++) {
            if(colors[j] == color) {
                count++;
            }
        }
        return count;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPixel(int layer, int row, int col) {
        int pixelPerLayer = height*width;
        return colors[(pixelPerLayer*layer)+(row*width)+col];
    }

    public int[] getTopLayer() {
        int[] image = new int[width*height];
        int layers = getLayersCount();
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                for(int l=0; l<layers; l++) {
                    int pixel = getPixel(l,i,j);
                    if(pixel != 2) {
                        image[i * width + j] = pixel;
                        break;
                    }
                }
            }
        }
        return image;
    }

    @Override
    public String toString() {
        return toString(null);
    }

    public String toString(Map<Integer, Character> cTable) {
        StringBuffer buf = new StringBuffer();
        buf.append("Image:\n");
        int[] pixels = getTopLayer();
        for(int i=0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                Integer val = pixels[i * width + j];
                if(cTable == null) {
                    buf.append(String.valueOf(val));
                } else if(cTable.containsKey(val)) {
                    buf.append(cTable.get(val));
                } else {
                    buf.append(' ');
                }
            }
            buf.append("\n");
        }
        return buf.toString();
    }
}
