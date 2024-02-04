package application;

import java.util.ArrayList;

import objets.*;


public class Systems {
	
	DonneesJeu donnees = new DonneesJeu(0,5,25);
	
	public enum TypeDechet {
        BATTERIE, BIO, VERRE, CARTON, VETEMENTS, METAL, PAPIER, PLASTIQUE_DUR, ORDINATEUR, DECHET, PLASTIQUE_MOU, NOURRITURE_DECHET;
    }
	
	public enum TypePoubelle {
        BIO, VERRE, CARTON, PLASTIQUE, ELECTRONIQUE, METAL, DECHET;
    }
	
	public static int nbrTrashAjouter = 0;
	
	public static void ajouterPoubelles(Panel pan, int nbrPoubelles) {
		for(TypePoubelle type: TypePoubelle.values()) {
			Poubelles poubelle = new Poubelles("poubelle" + type.ordinal(), type);
			pan.listPoubelles.add(poubelle);
		}
	}

	public void verifierCompatibilite(Poubelles poubelle, Trash dechet) {
		TypePoubelle typePoubelle = poubelle.getId();
		TypeDechet typeDechet = dechet.getId();
		
		switch(typePoubelle) {
		case BIO:{
			if(typeDechet.equals(TypeDechet.BIO) || typeDechet.equals(TypeDechet.VETEMENTS)) {
				succes();
			}else {
				echec();
			}
		}
		case VERRE:{
			if(typeDechet.equals(TypeDechet.VERRE)) {
				succes();
			}else {
				echec();
			}
		}
		case CARTON:{
			if(typeDechet.equals(TypeDechet.CARTON) || typeDechet.equals(TypeDechet.PAPIER)) {
				succes();
			}else {
				echec();
			}
		}
		case PLASTIQUE:{
			if(typeDechet.equals(TypeDechet.PLASTIQUE_DUR) || typeDechet.equals(TypeDechet.PLASTIQUE_MOU)) {
				succes();
			}else {
				echec();
			}
		}
		case ELECTRONIQUE:{
			if(typeDechet.equals(TypeDechet.BATTERIE) || typeDechet.equals(TypeDechet.ORDINATEUR)) {
				succes();
			}else {
				echec();
			}
		}
		case METAL:{
			if(typeDechet.equals(TypeDechet.METAL)) {
				succes();
			}else {
				echec();
			}
		}
		case DECHET:{
			if(typeDechet.equals(TypeDechet.DECHET) || typeDechet.equals(TypeDechet.NOURRITURE_DECHET)) {
				succes();
			}else {
				echec();
			}
		}
		}
			
	}
	
	public void succes() {
		donnees.incrementerScore(1);
		donnees.enleverDechet();
		donnees.sequence++;
		if(donnees.sequence==5) {
			donnees.setVies(donnees.getVies()+1);
		}
		if(donnees.sequence==10) {
			
		}
		if(donnees.sequence==15) {
		
		}
		if(donnees.sequence==20) {

		}
		if(donnees.sequence==25) {
		}
	}
	
	public void echec() {
		donnees.sequence=0;
		donnees.enleverVie();
		donnees.enleverDechet();
	}

	public static void ajouterTrash(Panel pan) {
		Trash trash = new Trash("trash" + nbrTrashAjouter, TypeDechet.BIO);
		pan.trashAJeter = trash;
		
		nbrTrashAjouter += 1;
		
	}
}
