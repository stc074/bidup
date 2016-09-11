function abus(type_annonce,id_annonce) {
    option=4;
    obj_req=null;
    obj_req=new createXMLHttpRequestObject();
    sendReq(obj_req,'scripts/abus.php?type_annonce='+type_annonce+'&id_annonce='+id_annonce);

}
function changeCategorie1(idCategorie) {
    option=0;
    obj_req=null;
    obj_req=new createXMLHttpRequestObject();
    sendReq(obj_req,'./ChangeCategorie1?idCategorie='+idCategorie);

}
function changeRegion1(idRegion) {
    option=1;
    obj_req=null;
    obj_req=new createXMLHttpRequestObject();
    sendReq(obj_req,'./ChangeRegion1?id='+idRegion);

}
function changeDepartement1(idDepartement) {
    option=2;
    obj_req=null;
    obj_req=new createXMLHttpRequestObject();
    sendReq(obj_req,'./ChangeDepartement1?id='+idDepartement);

}
function deconnexion() {
    option=7;
    obj_req=null;
    obj_req=new createXMLHttpRequestObject();
    sendReq(obj_req,'scripts/deconnexion.php');

}
function creer_cookie() {
    option=7;
    obj_req=null;
    obj_req=new createXMLHttpRequestObject();
    sendReq(obj_req,'scripts/creer_cookie.php');
}
function signaler_abus(id_annonce) {
    option=4;
    obj_req=null;
    obj_req=new createXMLHttpRequestObject();
    sendReq(obj_req,'scripts/signaler_abus.php?id_annonce='+id_annonce);
}

function mdp_oublie() {
    email=prompt('Saisissez L\'Email de votre compte SVP.','');
    if(email!=null)
        {
            option=4;
            obj_req=null;
            obj_req=new createXMLHttpRequestObject();
            sendReq(obj_req,'scripts/mdp_oublie.php?email='+email);
        }

}
function suggerer(id_annonce,id_categorie,id_sous_categorie) {
    email=prompt('Veuillez saisir l\'email de votre ami(e) SVP.','');
    if(email!=null)
        {
            option=4;
            obj_req=null;
            obj_req=new createXMLHttpRequestObject();
            sendReq(obj_req,'scripts/suggerer_annonce.php?id_categorie='+id_categorie+'&id_sous_categorie='+id_sous_categorie+'&id_annonce='+id_annonce+'&email='+email);
        }
}
function createXMLHttpRequestObject() {

var objReq=null;
	try {
		objReq=new ActiveXObject("Microsoft.XMLHTTP");
	}
	catch(Error) {
		try {
			objReq=new ActiveXObject("MSXML2.XMLHTTP");
		}
		catch(Error) {
			try {
				objReq=new XMLHttpRequest();
			}
			catch(Error) {
			}
		}
	}
	return objReq;
}
function sendReq(objReq,file) {
	objReq.open('GET',file,true);
	objReq.onreadystatechange=treat_response;
	objReq.send(null);
}
function treat_response() {
	if(obj_req.readyState==4) {
		if(obj_req.responseText!=-1)
                    {
                        switch(option)
                        {
                            case 0:
                                document.getElementById('categoriesDroite').innerHTML=obj_req.responseText;
                                break;
                            case 1:
                                document.getElementById('innerDepartements').innerHTML=obj_req.responseText;
                                break;
                            case 2:
                                document.getElementById('innerCommunes').innerHTML=obj_req.responseText;
                                break;
                            case 3:
                                document.getElementById('inner_sous_cat_contre2').innerHTML=obj_req.responseText;
                                break;
                            case 4:
                                alert(obj_req.responseText);
                                break;
                            case 5:
                                 document.getElementById('inner_sous_cat').innerHTML=obj_req.responseText;
                                break;
                            case 6:
                                 document.getElementById('inner_departements').innerHTML=obj_req.responseText;
                                break;
                            case 7:
                                resultat=obj_req.responseText;
                                break;

                        }
                    }

	}
}
