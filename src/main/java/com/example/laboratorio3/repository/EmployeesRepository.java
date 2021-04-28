package com.example.laboratorio3.repository;


import com.example.laboratorio3.entity.Employees;
import dto.DepartamentosPaisCiudadDTO;
import dto.EmpleadosSalarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Integer> {

@Query(value = "SELECT e.first_name as 'nombre', e.last_name as 'apellido', jh.start_date as 'fechainicio',\n" +
        "jh.end_date as 'fechafin',j.job_title as 'puesto' FROM hr.employees e\n" +
        "inner join hr.job_history jh on(e.employee_id=jh.employee_id)\n" +
        "inner join hr.jobs j on (j.job_id=jh.job_id)\n" +
        "where salary>15000",nativeQuery = true)
List<EmpleadosSalarioDTO> obtenerEmpleadosPorSalario();


@Query(value = "SELECT c.country_name, l.city, count(e.employee_id) AS `empleados`, \n" +
        "count(distinct l.city) as 'cantidad' FROM countries c\n" +
        "INNER JOIN locations l ON (l.country_id = c.country_id)\n" +
        "INNER JOIN departments d ON (d.location_id = l.location_id)\n" +
        "INNER JOIN employees e ON (e.department_id = d.department_id)\n" +
        "GROUP BY l.city\n" +
        "HAVING `empleados` > 3",nativeQuery = true)
    List<DepartamentosPaisCiudadDTO> obtenerReporteDepartamentosPaisCiudad();

    @Query(value="select * from employees where first_name =?1", nativeQuery = true)
    List<Employees> buscareEmpleadosporNombre(String nombre);

    @Query(value="select * from employees where last_name = ?1",nativeQuery = true)
    List<Employees> buscarEmpleadosporApellido(String apellido);

    @Query(value="select * from employees e inner join departments d on (e.department_id=d.department_id and d.department_name=?1);",nativeQuery = true)
    List<Employees> buscarEmpleadosporDepartamento(String departamento);

}
