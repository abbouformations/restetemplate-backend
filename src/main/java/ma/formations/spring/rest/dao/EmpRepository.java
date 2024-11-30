package ma.formations.spring.rest.dao;

import ma.formations.spring.rest.service.modele.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Ici, l'interface EmpRepository hérite de l'interface JpaRepository de Spring
 * DATA. Il faut juste préciser la classe "Modele" et le type de la classe qui
 * représente la clé primaire.
 * <p>
 * Spring Data prendra en charge l'implémentation des 04 méthode ci-dessous à
 * condition de réspecter la nomenclature supportée par Spring Data.
 *
 * @Query offre la possinbilité d'exécuter des requêtes plus complexes.
 */
public interface EmpRepository extends JpaRepository<Emp, Long> {
    //select * from Employe wehre salary=10000
    List<Emp> findBySalaire(Double salary);

    List<Emp> findByFonction(String designation);

    List<Emp> findBySalaireAndFonction(Double salary, String fonction);

    List<Emp> findByFirstName(String name);


    // Il s'agit ici du langage JPQL : JPA Query Language  <==> HQL (Hibernate Query Language)
    @Query("SELECT e From Emp e where e.salaire=(select MAX(ee.salaire) as salaire FROM Emp ee)")
    Emp getEmpHavaingMaxSalary();
}
