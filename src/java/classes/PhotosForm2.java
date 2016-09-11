/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pj
 */
public class PhotosForm2 extends PhotosForm1 {
    private Long idAnnonce;
    private Long idMembre;
    public PhotosForm2(Long idMembre, Long idAnnonce) {
        super(idAnnonce);
        this.setTest(0);
        this.setErrorMsg2("");
        this.idAnnonce=idAnnonce;
        this.idMembre=idMembre;
    }
    @Override
    public void verif(HttpServletRequest request) {
        this.setErrorMsg2("");
        super.verif(request);
    }
    @Override
    public void blank() {
        this.setTest(0);
        this.setErrorMsg2("");
    }

    /**
     * @return the idAnnonce
     */
    public Long getIdAnnonce() {
        return idAnnonce;
    }

    /**
     * @return the idMembre
     */
    public Long getIdMembre() {
        return idMembre;
    }

}
