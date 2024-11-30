package ma.formations.spring.rest.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.formations.spring.rest.domaine.EmpVo;
import ma.formations.spring.rest.service.IService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class EmpRestController {

    private IService service;

    /**
     * Pour chercher tous les emplyés
     */
    @GetMapping(value = "/rest/emp", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<EmpVo> getAll() {
        return service.getEmployees();
    }

    /**
     * Pour chercher un employé par son id
     */
    @GetMapping(value = "/rest/emp/id/{id}")
    public ResponseEntity<Object> getEmpById(@PathVariable(value = "id") Long empVoId) {
        EmpVo empVoFound = service.getEmpById(empVoId);
        if (empVoFound == null)
            return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
        return new ResponseEntity<>(empVoFound, HttpStatus.OK);
    }

    @GetMapping(value = "/rest/emp/name/{name}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<EmpVo> getAll(@PathVariable(value = "name") String name) {
        return service.findEmployeesByName(name);
    }

    /**
     * Pour créer un nouveau employé
     */
    @PostMapping(value = "/rest/emp")
    public ResponseEntity<Object> createEmp(@Valid @RequestBody EmpVo empVo) {
        service.save(empVo);
        return new ResponseEntity<>("employee is created successfully", HttpStatus.CREATED);
    }

    /**
     * Pour modifier un produit par son id
     */
    @PutMapping(value = "/rest/emp/{id}")
    public ResponseEntity<Object> updateEmp(@PathVariable(name = "id") Long empVoId, @RequestBody EmpVo empVo) {
        EmpVo empVoFound = service.getEmpById(empVoId);
        if (empVoFound == null)
            return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
        empVo.setId(empVoId);
        service.save(empVo);
        return new ResponseEntity<>("Employee is updated successsfully", HttpStatus.OK);
    }

    /**
     * Pour supprimer un employé par son id
     */
    @DeleteMapping(value = "/rest/emp/{id}")
    public ResponseEntity<Object> deleteEmp(@PathVariable(name = "id") Long empVoId) {
        EmpVo empVoFound = service.getEmpById(empVoId);
        if (empVoFound == null)
            return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
        service.delete(empVoId);
        return new ResponseEntity<>("Employee is deleted successsfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/rest/emp")
    public ResponseEntity<Object> deleteAll() {
        service.deleteAll();
        return new ResponseEntity<>("All employees are deleted successsfully", HttpStatus.OK);
    }

    /**
     * Pour chercher tous les emplyés
     */
    @GetMapping(value = "/rest/sort/{fieldName}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<EmpVo> sortBy(@PathVariable String fieldName) {
        return service.sortBy(fieldName);
    }

    @GetMapping(value = "/rest/sort/{fieldName1}/{fieldName2}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<EmpVo> sortByBis(@PathVariable String fieldName1, @PathVariable String fieldName2) {
        return service.sortBy(fieldName1, fieldName2);
    }

    /**
     * Afficher la liste des employés en utilisant la pagination
     */
    @GetMapping("/rest/pagination/{pageid}/{size}")
    public List<EmpVo> pagination(@PathVariable int pageid, @PathVariable int size, Model m) {
        return service.findAll(pageid, size);
    }
}
