package com.example.demo;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class NacionalidadController {
	
	@Autowired
	private NacionalidadRepository repository;
	
	@GetMapping(value = "/nacionalidades")
	public ResponseEntity<List<Nacionalidad>> getNacionalidades() {
		
		List<Nacionalidad> nacionalidades = repository.findAll();
		
		ResponseEntity<List<Nacionalidad>> response = new ResponseEntity<List<Nacionalidad>>(nacionalidades,HttpStatus.OK);
				
	
		return response;
		
	}
	
	@PostMapping (value = "/nacionalidad")
	public ResponseEntity<Nacionalidad> saveNacionalidad(@RequestBody String nombre){
		Nacionalidad nac = new Nacionalidad();
		nac.setNombre(nombre);
		Nacionalidad nacionalidad = repository.save(nac);
		
		ResponseEntity<Nacionalidad> response = new ResponseEntity<Nacionalidad>(nacionalidad, HttpStatus.OK);
		
		return response;
	}
	
	@PostMapping (value = "/nacionalidad/{id}")
	public ResponseEntity<Nacionalidad> saveSaludo(@PathVariable("id") long id, @RequestBody String saludo){
		
		Nacionalidad nacionalidad = null;
		ResponseEntity<Nacionalidad> response = null;
		
		Optional <Nacionalidad> nac = repository.findById(id);
		if(nac.isPresent()) {
			Nacionalidad nac2 = nac.get();
			nac2.setSaludo(saludo);
			nacionalidad = repository.save(nac2);
			response = new ResponseEntity<Nacionalidad>(nacionalidad, HttpStatus.OK);
			
		} else {
			
			response = new ResponseEntity<Nacionalidad>(nacionalidad, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@PostMapping (value = "/saludo")
	public ResponseEntity saludar(@RequestBody SaludoRequest req){
		
		Optional <Nacionalidad> nac = repository.findById(req.getId());
		
		if(nac.isPresent()) {
			Nacionalidad nac2 = nac.get();
			SaludoResponse resp = new SaludoResponse();
			resp.setSaludo(String.format("%s %s!" ,  nac2.getSaludo(), req.getSaludado()));
			return new ResponseEntity<SaludoResponse>(resp ,HttpStatus.OK);
			
		} else {
			
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
	}

}
