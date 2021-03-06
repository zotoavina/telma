package com.mobile.telma.controllers;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobile.telma.domains.Action;
import com.mobile.telma.domains.Admin;
import com.mobile.telma.domains.Data;
import com.mobile.telma.domains.Forfait;
import com.mobile.telma.domains.Offre;
import com.mobile.telma.domains.Tarif;
import com.mobile.telma.filter.GestionToken;
import com.mobile.telma.services.AdminService;
import com.mobile.telma.utils.ResponseMaker;
import com.mobile.telma.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin
@RestController
@RequestMapping("")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, Object> adminMap) {
		String email = (String) adminMap.get("email");
		String mdp = (String) adminMap.get("mdp");
	    Admin admin = adminService.getByEmailAndMdp(email, mdp);
	    return ResponseMaker.makeResponse(generateToken(admin), 200, "Login admin reussi", HttpStatus.ACCEPTED);
	}
	  
	
	@GetMapping("/admin/validations")
	public ResponseEntity<Map<String , Object>> listeActions(HttpServletRequest request) throws Exception{
		System.out.println("validate");
		Integer.parseInt( GestionToken.gererTokenAdmin(request) );
		return ResponseMaker.makeResponse(adminService.getActionNonValide(), 200, 
				"selection des actions non valide reussi", HttpStatus.OK);
	}
	
	@GetMapping("/admin/validations/{idAction}")
	public ResponseEntity<Map<String, Object>> validerAction(HttpServletRequest request,
			@PathVariable("idAction") int idAction)throws Exception{
		int idAdmin = Integer.parseInt( GestionToken.gererTokenAdmin(request ) );
		Action action = adminService.validerAction(idAction);
		return ResponseMaker.makeResponse(action, 200, 
			 	"Validation de " + action.getDescription(), HttpStatus.OK);
	}
	
	
	
	// ----------------------------------- Gestion Offres
	@GetMapping("/offres")
	public ResponseEntity <Map<String, Object>> getListOffres(){
			return ResponseMaker.makeResponse(adminService.getListOffres(), 200, 
					"selection de la liste des offres reussi " , HttpStatus.OK);
	}
	
	@GetMapping("/offres/{idOffre}")
	public ResponseEntity<Map<String, Object>> getOffre(@PathVariable("idOffre") int idOffre){
		return ResponseMaker.makeResponse( adminService.getOffre(idOffre), 200, "selection offre reussi " , HttpStatus.OK);
	}
	
	@PostMapping("/admin/offres")
	public ResponseEntity<Map<String, Object>> ajoutOffre(HttpServletRequest request, @RequestBody Offre offre) throws Exception{
		GestionToken.gererTokenAdmin(request);
		return ResponseMaker.makeResponse(adminService.addOffre(offre), 200, "Ajout offre reussi", HttpStatus.CREATED);
	}
	
	@PutMapping("/admin/offres/delete")
	public ResponseEntity<Map<String, Object>> supprimerOffre(HttpServletRequest request,
			@RequestBody Offre offre) throws Exception{
		GestionToken.gererTokenAdmin(request);
		adminService.deleteOffre(offre.getIdOffre());
		return ResponseMaker.makeResponse( null , 200, "Suppression de l' offre reussi", HttpStatus.OK);
	}
	
	
	@PutMapping("/admin/offres/{idOffre}")
	public ResponseEntity<Map<String, Object>> updateOffre(HttpServletRequest request,
			@PathVariable("idOffre") int idOffre,@RequestBody Offre offre) throws Exception{
		GestionToken.gererTokenAdmin(request);
		offre.setIdOffre(idOffre);
		adminService.updateOffre(offre);
		return ResponseMaker.makeResponse( null , 200, "Mise ajour de l' offre reussi " , HttpStatus.OK);
	}
	
	
	@GetMapping("/offres/{idOffre}/forfaits")
	public  ResponseEntity<Map<String, Object>> getForfaits(@PathVariable("idOffre") int idOffre){
		return ResponseMaker.makeResponse( adminService.getOffreForfaits(idOffre) , 200, "Selection des forfaits reussie " , HttpStatus.OK);
	}
	
	@PostMapping("/offres/{idOffre}/forfaits")
	public  ResponseEntity<Map<String, Object>> ajoutForfait(HttpServletRequest request,@PathVariable("idOffre") int idOffre,
			@RequestBody Forfait forfait)throws Exception{
		GestionToken.gererTokenAdmin(request);
		forfait.setIdOffre(idOffre);
		Forfait f = adminService.addForfait(forfait);
		return ResponseMaker.makeResponse(f , 200, "Ajout du forfait realise " , HttpStatus.OK);
	}
	
	@GetMapping("/forfaits/{idforfait}")
	public  ResponseEntity<Map<String, Object>> getForfait(HttpServletRequest request,
			@PathVariable("idforfait") int idForfait)throws Exception{
		Forfait f = adminService.getForfait(idForfait);
		return ResponseMaker.makeResponse(f , 200, "Selection du forfait reussie " , HttpStatus.OK);
	}
	
	@PutMapping("/admin/forfaits/delete")
	public  ResponseEntity<Map<String, Object>> deleteForfait(HttpServletRequest request,
			@RequestBody Map<String, Object> map)throws Exception{
		GestionToken.gererTokenAdmin(request);
		adminService.deleteForfait( (Integer) map.get("idForfait") );
		return ResponseMaker.makeResponse(null , 200, "Suppression du forfait reussie " , HttpStatus.OK);
	}
	
	
	@PutMapping("/admin/forfaits/{idForfait}")
	public  ResponseEntity<Map<String, Object>> updateForfait(HttpServletRequest request,
			@RequestBody Forfait forfait, @PathVariable("idForfait") int idForfait )throws Exception{
		GestionToken.gererTokenAdmin(request);
		forfait.setIdForfait(idForfait);
		adminService.updateForfait(forfait);
		return ResponseMaker.makeResponse(null, 200, "Mise a jour du forfait reussie " , HttpStatus.OK);
	}
	
	
	//------------------------------------ Datas
	@GetMapping("/admin/datas")
	public ResponseEntity<Map<String, Object>> getDatas(HttpServletRequest request)throws Exception{
		GestionToken.gererTokenAdmin(request);
		return ResponseMaker.makeResponse( adminService.getDatas(), 200, "Liste des datas selectionnes", HttpStatus.OK);
	}
	
	@PostMapping("/admin/datas")
	public ResponseEntity<Map<String, Object>> addData(HttpServletRequest request,
			@RequestBody Data data)throws Exception{
		GestionToken.gererTokenAdmin(request);
		return ResponseMaker.makeResponse( adminService.insertData(data), 200, "Ajout du nouvelle service reussie", HttpStatus.OK);
	}
	
	
	@PutMapping("/admin/datas")
	public ResponseEntity<Map<String, Object>> deleteData(HttpServletRequest request,
			@RequestBody Data data)throws Exception{
		GestionToken.gererTokenAdmin(request);
		adminService.deleteData(data.getIdData());
		return ResponseMaker.makeResponse(null, 200, "Suppression du service reussie", HttpStatus.OK);
	}
	
	
	@GetMapping("/admin/tarifs")
	public ResponseEntity<Map<String, Object>> getTarifs(HttpServletRequest request)throws Exception{
		GestionToken.gererTokenAdmin(request);
		return ResponseMaker.makeResponse( adminService.getAllTarifs(), 200, "Liste des tarifs selectionnes", HttpStatus.OK);
	}
	
	
	@GetMapping("/admin/tarifs/{idTarif}")
	public ResponseEntity<Map<String, Object>> getTarifById(HttpServletRequest request, 
			@PathVariable("idTarif") int idTarif)throws Exception{
		GestionToken.gererTokenAdmin(request);
		return ResponseMaker.makeResponse( adminService.getTarifById(idTarif), 200, "Selection tarif reussie", HttpStatus.OK);
	}
	
	
	@PutMapping("/admin/tarifs/{idTarif}")
	public ResponseEntity<Map<String, Object>> updateTarif(HttpServletRequest request,
			@PathVariable("idTarif") int idTarif, @RequestBody Tarif tarif)throws Exception{
		GestionToken.gererTokenAdmin(request);
		tarif.setIdTarif(idTarif);
		adminService.updateTarif(tarif);
		return ResponseMaker.makeResponse( null, 200, "Mise a jour du tarif reussie", HttpStatus.OK);
	}
	
	@GetMapping("/admin/tarifs/appels")
	public ResponseEntity<Map<String, Object>> getTarifAppel(HttpServletRequest request)throws Exception{
		GestionToken.gererTokenAdmin(request);
		return ResponseMaker.makeResponse( adminService.getTarifAppel(), 200, 
				"Selection tarif appel par defaut", HttpStatus.OK);
	}
	
	//-------------------------------- Stats
	@PostMapping("/admin/offres/stats")
	public ResponseEntity<Map<String, Object>> getStatOffres(HttpServletRequest request,
			@RequestBody Map<String, Object> map)throws Exception{
		GestionToken.gererTokenAdmin(request);
		int annee = Integer.parseInt( (String)map.get("annee") );
		int mois = Integer.parseInt( (String) map.get("mois") );
		return ResponseMaker.makeResponse(adminService.getStatOffre(annee, mois), 200,
				"Selection des statistiques des offres reussi", HttpStatus.OK);
	}
	
	@PostMapping("/admin/forfaits/stats")
	public ResponseEntity<Map<String, Object>> getStatForfaits(HttpServletRequest request,
			@RequestBody Map<String, Object> map)throws Exception{
		GestionToken.gererTokenAdmin(request);
		int idOffre = Integer.parseInt( (String) map.get("idoffre") );  
		int annee = Integer.parseInt( (String)map.get("annee") );
		int mois = Integer.parseInt( (String) map.get("mois") );
		return ResponseMaker.makeResponse(adminService.getStatForfait(idOffre, annee, mois), 200,
				"Selection des statistiques des Forfaits reussi", HttpStatus.OK);
	}
	
	@PostMapping("/admin/consommation/stats")
	public ResponseEntity<Map<String, Object>> getStatConsommations(HttpServletRequest request, 
			@RequestBody Map<String, Object> map)throws Exception{
		GestionToken.gererTokenAdmin(request);
		int annee = Integer.parseInt( (String) map.get("annee"));   
		int mois = Integer.parseInt( (String) map.get("mois"));
		return ResponseMaker.makeResponse(adminService.getStatConsommation(annee, mois), 200, 
				"Selection des statistique de consommation reussi", HttpStatus.OK);
	}
	
	@PostMapping("/admin/consommation/datas/stats")
	public ResponseEntity<Map<String, Object>> getStatConsommationData(HttpServletRequest request, 
			@RequestBody Map<String, Object> map)throws Exception{
		GestionToken.gererTokenAdmin(request);
		int idData = Integer.parseInt( (String) map.get("idData"));
		int annee = Integer.parseInt( (String) map.get("annee"));   
		return ResponseMaker.makeResponse(adminService.getStatConsommationData(idData, annee), 200, 
				"Selection des statistique de consommation reussi", HttpStatus.OK);
	}	
	
	private String generateToken(Admin admin){
		long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.ADMIN_API_SERCRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.VALIDITY)) 
				.claim("idAdmin", admin.getIdAdmin() )
				.claim("idoperateur", admin.getIdoperateur())
				.claim("nom", admin.getNom())
				.claim("prenom", admin.getPrenom())
				.claim("email", admin.getEmail())
				.compact();
		return token;
	}
	
	
	
}
