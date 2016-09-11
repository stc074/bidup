/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author pj
 */
public class PhotosForm1 extends Formulaire {
    private int nbPhotos;
    private long idAnnonce;
    public PhotosForm1(long idAnnonce) {
        super();
        this.setTest(0);
        this.setErrorMsg2("");
        this.nbPhotos=0;
        this.idAnnonce=idAnnonce;
    }
    public void verif(HttpServletRequest request) {
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(Datas.MAXSIZEPHOTO);
            factory.setRepository(new File("home/temp"));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(Datas.MAXSIZEPHOTO);
            List items = upload.parseRequest(request);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if(!item.isFormField()) {
                    String name=item.getFieldName();
                    if((name.equals("1") || name.equals("2") || name.equals("3") || name.equals("4") || name.equals("5"))&&item.getName().length()!=0) {
                        Objet.getConnection();
                        String query="SELECT extension"+name+" FROM table_annonces WHERE id=? LIMIT 0,1";
                        PreparedStatement prepare=Objet.getConn().prepareStatement(query);
                        prepare.setLong(1, this.idAnnonce);
                        ResultSet result=prepare.executeQuery();
                        result.next();
                        String extension=result.getString("extension"+name);
                        result.close();
                        prepare.close();
                        Objet.closeConnection();
                        if(extension.length()>0) {
                            String filename=Datas.DIR+"photos/"+this.idAnnonce+"_"+name+extension;
                            String filenameMini1=Datas.DIR+"photos/mini1_"+this.idAnnonce+"_"+name+extension;
                            String filenameMini2=Datas.DIR+"photos/mini2_"+this.idAnnonce+"_"+name+extension;
                            File file=new File(filename);
                            File fileMini1=new File(filenameMini1);
                            File fileMini2=new File(filenameMini2);
                            if(file.exists())
                                file.delete();
                            if(fileMini1.exists())
                                fileMini1.delete();
                            if(fileMini2.exists())
                                fileMini2.delete();
                        }
                        extension=(item.getName().substring(item.getName().lastIndexOf("."))).toLowerCase();
                        if(!Objet.testExtension(extension))
                            this.setErrorMsg("Mauvais format de FICHIER (png, gif, jpg, jpeg seulement).<br/>");
                        if(this.getErrorMsg().length()==0) {
                        String filename=Datas.DIR+"photos/"+this.idAnnonce+"_"+name+extension;
                        String filenameMini1=Datas.DIR+"photos/mini1_"+this.idAnnonce+"_"+name+extension;
                        String filenameMini2=Datas.DIR+"photos/mini2_"+this.idAnnonce+"_"+name+extension;
                        File file=new File(filename);
                        try {
                            item.write(file);
                            File fileMini1=new File(filenameMini1);
                            File fileMini2=new File(filenameMini2);
                            Img img=new Img();
                            img.resizeWidth(file, fileMini1, Datas.MINI1LARG);
                            img.resizeHeight(file, fileMini2, Datas.MINI2HAUT);
                            img.getSize(file);
                            if(img.getHeight()>Datas.MAXHAUTPHOTO)
                                img.resizeHeight(file, file, Datas.MAXHAUTPHOTO);
                            if(img.getWidth()>Datas.MAXLARGPHOTO)
                                img.resizeWidth(file, file, Datas.MAXLARGPHOTO);
                            Objet.getConnection();
                            query="UPDATE table_annonces SET extension"+name+"=? WHERE id=?";
                            prepare=Objet.getConn().prepareStatement(query);
                            prepare.setString(1, extension);
                            prepare.setLong(2, this.idAnnonce);
                            prepare.executeUpdate();
                            prepare.close();
                            Objet.closeConnection();
                        }
                        catch (Exception ex) {
                            Logger.getLogger(PhotosForm1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                    }
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(PhotosForm1.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne.<br/>");
        } catch (SQLException ex) {
            Logger.getLogger(PhotosForm1.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur interne.<br/>");
        } catch (FileUploadException ex) {
            Logger.getLogger(PhotosForm1.class.getName()).log(Level.SEVERE, null, ex);
            this.setErrorMsg("Erreur durant l'UPLOAD.<br/>");
        }
    }
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
        this.nbPhotos=0;
    }
}
