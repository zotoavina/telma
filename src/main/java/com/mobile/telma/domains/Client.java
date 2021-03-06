package com.mobile.telma.domains;
import java.util.Date;
import java.util.List;

import com.mobile.telma.exceptions.EtBadRequestException;

public class Client {
	private int idClient;
	private int  idOperateur;
	private String nom;
	private String prenom;
	private String mdp;
	private String numero;
	private double solde;
	private double credit;
	private Date dateAdhesion;
	private List<DataActuel> dataActuel;
	
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public int getIdOperateur() {
		return idOperateur;
	}
	public void setIdOperateur(int idOperateur) {
		this.idOperateur = idOperateur;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) throws EtBadRequestException{
		if(solde < 0) throw new EtBadRequestException("La valeur du solde doit etre positive");
		this.solde = solde;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public Date getDateAdhesion() {
		return dateAdhesion;
	}
	public void setDateAdhesion(Date dateAdhesion) {
		this.dateAdhesion = dateAdhesion;
	}
	
	
	public List<DataActuel> getDataActuel() {
		return dataActuel;
	}
	public void setDataActuel(List<DataActuel> dataActuel) {
		this.dataActuel = dataActuel;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Client() {}
	public Client(int idClient, int idOperateur, String nom, String prenom, String numero, String mdp, double solde, double credit,
			Date dateAdhesion) {
		super();
		this.idClient = idClient;
		this.idOperateur = idOperateur;
		this.nom = nom;
		this.prenom = prenom;
		this.numero = numero;
		this.mdp = mdp;
		this.solde = solde;
		this.credit = credit;
		this.dateAdhesion = dateAdhesion;
	}
	
	public boolean validPassword(String password) {
		return this.mdp.compareTo(password) == 0;
	}
	
	public double soldePlusCredit() {
		return solde + credit;
	}
	
	private void faireRetrait(double montant)throws EtBadRequestException{
		String message = "Votre solde actuelle " + solde + " est insuffisant pour faire une retrait de " + montant; 
		if(solde < montant) throw new EtBadRequestException(message);
		setSolde(solde - montant);
	}
	
	
	private void faireDepot(double montant) {
		setSolde(montant + solde);
	}
	
	private void acheterCredit(double montant)throws EtBadRequestException {
		String message = "Votre solde mvola actuelle " + solde + " est insuffisant pour acheter une credit de :" + montant; 
		if(solde < montant) throw new EtBadRequestException(message);
		setSolde( solde - montant );
		setCredit( credit + montant );
	}
	
	
	public void faireAction(Action action) throws EtBadRequestException{
		if(action.isDepot()) faireDepot(action.getMontant());
		if(action.isRetrait()) faireRetrait(action.getMontant());
		if(action.isAchatCredit()) acheterCredit(action.getMontant());
	}
	
	private void achatForfaitParCredit(Forfait forfait)throws EtBadRequestException {
		if( credit < forfait.getPrix()) 
			throw new EtBadRequestException(" Votre credit actuelle " + credit +" est insuffisant pour acheter cette forfait ");
		setCredit( credit - forfait.getPrix() );
	}
	
	private void achatForfaitParSolde(Forfait forfait)throws EtBadRequestException {
		if( solde < forfait.getPrix()) 
			throw new EtBadRequestException(" Votre solde mvola actuelle " + solde +" est insuffisant pour acheter cette forfait ");
		setSolde( solde - forfait.getPrix());
	}
	
	public void achatForfait(Forfait forfait, boolean parSolde)throws EtBadRequestException{
		if(parSolde) achatForfaitParSolde(forfait) ;
		else achatForfaitParCredit(forfait);
	}
	
	public void achatForfait(Forfait forfait, String mode)throws EtBadRequestException{
		System.out.println(mode);
		if(mode.compareTo("mvola") == 0) {
			achatForfait(forfait, true);
			return;
		}
		if(mode.compareTo("credit") == 0) {
			achatForfait(forfait, false);
			return;
		}
		throw new EtBadRequestException("Mode de paiement invalide");
	}
	
	private String getPredicat() {
		return numero.substring(0, 3);
	}
	
	public boolean memeOperateur(String numero) {
		System.out.println(getPredicat());
		return numero.startsWith( getPredicat() );	
	}
	
	// Appel
	public double tarifAUtiliser(DataActuel data, String numero) {
		if(numero == null || numero.compareTo("") == 0) return 1;
		if(memeOperateur(numero)) return data.getInterne();
		if(Operateur.autres(numero)) return data.getAutres();
		return data.getInternational();
	}
	
	public double tarifAUtiliser(Tarif tarif, String numero) {
		if(tarif == null) return 1;
		if(numero == null || numero.compareTo("") == 0) return 1;
		if(memeOperateur(numero)) return tarif.getInterne();
		if(Operateur.autres(numero)) return tarif.getAutres();
		return tarif.getInternational();
	}
	
	
	
	
	private void consommerAvecCredit(Consommation consommation) {
		if(credit == 0) return;
		DetailCons detail = new DetailCons();
		detail.setTarif(1);
		detail.setModeConsommation("credit");
		consommation.addDetail(detail);
		if( credit <= consommation.dataRestantAConsommer()) {
			detail.setQuantite(credit);
			credit = 0;
			return;
		}
		System.out.println("consommation credit: " + consommation.dataRestantAConsommer());
		credit = credit - consommation.dataRestantAConsommer();
		detail.setQuantite( consommation.dataRestantAConsommer());
	}
	
	
	public void consommerAvecCredit(Consommation consommation, Tarif tarif, String numero) {
		if(credit == 0) return;
		double taf= tarifAUtiliser(tarif, numero);
		double comp = credit / taf;
		
		DetailCons detail = new DetailCons();
		detail.setTarif( taf );
		detail.setModeConsommation("credit");
		consommation.addDetail(detail);
		if( comp <= consommation.dataRestantAConsommer()) {
			detail.setQuantite(credit);
			credit = 0;
			return;
		}
		System.out.println("consommation credit: " + consommation.dataRestantAConsommer());
		credit = credit - consommation.dataRestantAConsommer() * taf;
		detail.setQuantite( consommation.dataRestantAConsommer() * taf );
	}
	
	
	public boolean consommerData(Consommation consommation,Tarif tarifCredit, String numero) {
		double tmp = 0;
		System.out.println("Taille data actulee" + dataActuel.size());
		for(int i = 0; i < dataActuel.size(); i++ ) {
			tmp =  tarifAUtiliser(dataActuel.get(i), numero);
			System.out.println("tarif a utliser: " + tmp);
			if(!dataActuel.get(i).consommer(consommation, tmp)) return false;  
		}
		consommerAvecCredit(consommation, tarifCredit, numero);
		return true;
	}
	
	
	
	
//	public boolean autreOperateur(String numero) {
//		
//	}
	
//	
//	public void consommerAppel(Consommation cons, String numero) {
//			if()
//	}
	
	
	
	
	
}
