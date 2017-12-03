package Servlet;

import JavaBean.AdminBean;
import JavaBean.InfoBean;
import JavaBean.Item;
import Util.ParseEnum;
import Util.RandomString;
import Util.SQLConnector;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.image.BufferedImage;
import java.util.Iterator;

import static com.sun.javafx.webkit.UIClientImpl.toBufferedImage;

/**
 * @Author: michael
 * @Date: 16-10-22 下午6:22
 * @Project: Uni-Pinter
 * @Package: Servlet
 */
@WebServlet(name = "HandleUpload")
@MultipartConfig
public class HandleUpload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String forward;
        InfoBean infoBean = new InfoBean();
        request.getSession().setAttribute("infoBean", infoBean);
        Item type;
        try {
            type = ParseEnum.parseEnum(Item.class, request.getParameter("type").trim());
        } catch (IllegalArgumentException e) {
            forward = "/WEB-INF/errorPage.jsp";
            e.printStackTrace();
            infoBean.setError("无法得到上传类型");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
            requestDispatcher.forward(request, response);
            return;
        }
        if(type == Item.Model) {
            String name = request.getParameter("name");
            Part uploadImage = request.getPart("uploadImage");
            Part showImage = request.getPart("showImage");
            Part coverImage = request.getPart("coverImage");
            String scale = request.getParameter("scale");
            String uploadImageName = name + ".png";
            String showImageName = RandomString.RandomString(10) + ".png";
            String coverImageName = name + "-cover.png";
            name += ',';
            name += scale;

            saveImage(uploadImage, uploadImageName);
            saveImage(showImage, showImageName);
            saveImage(coverImage, coverImageName);

            try {
                SQLConnector connector = new SQLConnector();
                int uid;
                String path = "/images/model/" + showImageName;
                if (request.getParameter("uid") == null)
                    uid = 0;
                else uid = Integer.parseInt(request.getParameter("uid"));
                String sql;
                ResultSet resultSet;

                int rank;
                String brand = request.getParameter("brand");
                resultSet = connector.qurey("select * from " + type + " where brand='" + brand + "'");
                if (resultSet != null) {
                    resultSet.last();
                    rank = resultSet.getRow() + 1;
                } else {
                    rank = 1;
                }
                sql = "INSERT INTO " + type + "(info,uid,name,brand,rank) VALUES('" + path + "','" + uid + "','" + name + "','" + brand + "','" + rank + "')";

                int id = connector.update(sql);
                infoBean.setInfo(id+"");
                forward = "/WEB-INF/message.jsp";
            } catch (SQLException e) {
                e.printStackTrace();
                forward = "/WEB-INF/errorPage.jsp";
                infoBean.setError("数据库访问错误，请重试。");
            }

        } else {
            String name = request.getParameter("name");
            Part part = request.getPart("uploadImage");
            String fileName = RandomString.RandomString(10) + ".png";
            if(name == null)
                name = fileName;
//        InputStream fileContent = part.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(part.getInputStream(), Charset.forName("utf-8")));
            String sImg = bufferedReader.readLine();
            byte[] bImg64 = sImg.getBytes();
            byte[] bImg = Base64.decodeBase64(bImg64);

            String dir = getServletContext().getRealPath("/images") + "/" + type + "/";
            checkDir(dir);
            String AbsolutPath = getServletContext().getRealPath("/images") + "/" + type + "/" + fileName;
            String path = "/images/" + type + "/" + fileName;
            checkFile(AbsolutPath);

            if(type == Item.Shell) {
                int realWidth = Integer.parseInt(request.getParameter("realWidth"));
                String scale = request.getParameter("model").split(",")[1];
                int realHeight = (int)(Float.parseFloat(scale) * realWidth);
                scaleImage(bImg, AbsolutPath, realWidth, realHeight);
            } else {
                FileOutputStream fileOutputStream = new FileOutputStream(AbsolutPath);
                fileOutputStream.write(bImg);
                fileOutputStream.close();
            }

            try {
                SQLConnector connector = new SQLConnector();
                AdminBean adminBean = (AdminBean) request.getSession().getAttribute("adminBean");
                int uid;
                if(adminBean.isStatus())
                    uid = adminBean.getId();
                else if(request.getParameter("uid") == null)
                    uid = 0;
                else uid = Integer.parseInt(request.getParameter("uid"));
                String sql;
                ResultSet resultSet;
                if(type == Item.Shell) {
                    String brand = request.getParameter("brand");
                    String model = request.getParameter("model");
                    model = model.split(",")[0];
                    sql = "INSERT INTO " + type + "(info,uid,name,brand,model) VALUES('" + path + "','" + uid + "','" + name + "','" + brand + "','" + model + "')";
                } else if(type == Item.Image){
                    int rank;
                    String category = request.getParameter("category");
                    resultSet = connector.qurey("select * from " + type + " where category='" + category +"'");
                    if(resultSet != null) {
                        resultSet.last();
                        rank = resultSet.getRow() + 1;
                    } else {
                        rank = 1;
                    }
                    sql = "INSERT INTO " + type + "(info,uid,name,category,rank) VALUES('" + path + "','" + uid + "','" + name + "','" + category + "','" + rank + "')";
                } else if(type == Item.Brand) {
                    int rank;
                    resultSet = connector.qurey("select * from " + type);
                    if(resultSet != null) {
                        resultSet.last();
                        rank = resultSet.getRow() + 1;
                    } else {
                        rank = 1;
                    }
                    sql = "INSERT INTO " + type + "(info,uid,name,rank) VALUES('" + path + "','" + uid + "','" + name + "','" + rank + "')";
                } else {
                    sql = "INSERT INTO " + type + "(info,uid,name) VALUES('" + path + "','" + uid + "','" + name + "')";
                }
                int id = connector.update(sql);
                infoBean.setInfo(id+"");
                forward = "/WEB-INF/message.jsp";
            } catch (SQLException e) {
                e.printStackTrace();
                forward = "/WEB-INF/errorPage.jsp";
                infoBean.setError("数据库访问错误，请重试。");
            }
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "/WEB-INF/errorPage.jsp";
        InfoBean infoBean = (InfoBean)request.getSession().getAttribute("infoBean");
        if(infoBean == null) {
            infoBean = new InfoBean();
            request.getSession().setAttribute("infoBean", infoBean);
        }
        infoBean.setError("请使用POST方法。");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(forward);
        requestDispatcher.forward(request, response);
    }

    private void checkDir(String dir) {
        File uploadDir = new File(dir);
        if(!uploadDir.exists())
            uploadDir.mkdir();
    }

    private void checkFile(String path) {
        File uploadPath = new File(path);
        try {
            uploadPath.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void scaleImage(byte[] srcImageFile, String result, int realWidth, int realHeight) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(srcImageFile);
            BufferedImage src = ImageIO.read(bais); // 读入文件

            src = rotateImage(src, 270);

            Image image = src.getScaledInstance(realHeight, realWidth, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(realHeight, realWidth, BufferedImage.TYPE_INT_RGB);
//            Graphics g = tag.getGraphics();

            Graphics2D g=tag.createGraphics();
            tag = g.getDeviceConfiguration().createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
            g = tag.createGraphics();

            g.drawImage(image, 0, 0, null);
            g.dispose();
            for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName("png"); iw.hasNext();) {
                ImageWriter writer = iw.next();
                ImageWriteParam writeParam = writer.getDefaultWriteParam();
                ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
                IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
                if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
                    continue;
                }

                setDPI(metadata);

                final ImageOutputStream stream = ImageIO.createImageOutputStream(new File(result));
                try {
                    writer.setOutput(stream);
                    writer.write(metadata, new IIOImage(tag, null, metadata), writeParam);
                } finally {
                    stream.close();
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {

        // for PMG, it's dots per millimeter
        double dotsPerMilli = 1.0 * 300 / 10 / 2.54;

        IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
        horiz.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
        vert.setAttribute("value", Double.toString(dotsPerMilli));

        IIOMetadataNode dim = new IIOMetadataNode("Dimension");
        dim.appendChild(horiz);
        dim.appendChild(vert);

        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
        root.appendChild(dim);

        metadata.mergeTree("javax_imageio_1.0", root);
    }
    private void saveImage(Part part, String fileName) throws IOException{

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(part.getInputStream(), Charset.forName("utf-8")));
        String sImg = bufferedReader.readLine();
        byte[] bImg64 = sImg.getBytes();
        byte[] bImg = Base64.decodeBase64(bImg64);

        String dir = getServletContext().getRealPath("/images") + "/model/";
        checkDir(dir);
        String AbsolutPath = getServletContext().getRealPath("/images") + "/model/" + fileName;
        checkFile(AbsolutPath);

        FileOutputStream fileOutputStream = new FileOutputStream(AbsolutPath);
        fileOutputStream.write(bImg);
        fileOutputStream.close();

    }

    public BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {

        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        if (90 == degree || 270 == degree) {
            img = new BufferedImage(h, w, type);
        } else {
            img = new BufferedImage(w, h, type);
        }
        Graphics2D graphics2d;
        graphics2d = img.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (270 == degree) {
            graphics2d.rotate(Math.toRadians(degree), w / 2, w / 2);
        } else if (90 == degree) {
            graphics2d.rotate(Math.toRadians(degree), h / 2, h / 2);
        }
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

}
