package com.merlin.inquery.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 图像处理
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 15:03
 */
@Service
public class ImgService {


    public byte[] dealImg(byte[] img) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));
        int width = image.getWidth();
        int height = image.getHeight();

        //去除边缘黑条
        BufferedImage afterImage = image.getSubimage(1, 1, width - 1, height - 1);

        //二值化
        BufferedImage grayImage = new BufferedImage(width - 1, height - 1, BufferedImage.TYPE_BYTE_BINARY);//重点，技巧在这个参数BufferedImage.TYPE_BYTE_BINARY
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                int rgb = afterImage.getRGB(i, j);
                grayImage.setRGB(i, j, rgb);
            }
        }

        /*//缩小
        int newwidth = grayImage.getWidth(); // 得到源图宽
        int newheight = grayImage.getHeight(); // 得到源图长
        newwidth = newwidth * 2;
        newheight = newheight * 2;

        Image newimage = grayImage.getScaledInstance(newwidth, newheight, Image.SCALE_DEFAULT);
        BufferedImage tag = new BufferedImage(newwidth, newheight, BufferedImage.TYPE_INT_RGB);
        Graphics g = tag.getGraphics();
        g.drawImage(newimage, 0, 0, null); // 绘制缩小后的图
        g.dispose();
        //ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流
        */

        ByteArrayOutputStream ops = new ByteArrayOutputStream();
        ImageIO.write(grayImage, "JPEG", ops);
        return ops.toByteArray();
    }



}
