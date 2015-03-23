package com.ejie.x21a.service;


import java.util.List;

import org.springframework.validation.Errors;

import com.ejie.x21a.model.Alumno;
import com.ejie.x38.dto.Pagination;

/**
 *  * AlumnoService generated by UDA, 01-mar-2012 9:33:10.
 * @author UDA
 */

public interface AlumnoService {

	
	/**
	 * Inserts a single row in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return Alumno
	 */
    Alumno add(Alumno alumno);
	
	/**
	 * Updates a single row in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return Alumno
	 */
	Alumno update(Alumno alumno);

	/**
	 * Finds a single row in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return Alumno
	 */
	Alumno find(Alumno alumno);

	/**
	 * Finds a List of rows in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @param pagination Pagination
	 * @return List
	 */
	List<Alumno> findAll(Alumno alumno, Pagination pagination);

	/**
	 * Counts rows in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return Long
	 */
	Long findAllCount(Alumno alumno);
	
	/**
	 * Counts rows in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return Long
	 */
	Long findAllLikeCount(Alumno alumno, Boolean startsWith);
	
	/**
	 * Finds rows in the Alumno table using like.
	 *
	 * @param alumno Alumno
	 * @param pagination Pagination
     * @param startsWith Boolean	 
	 * @return List
	 */
	List<Alumno> findAllLike(Alumno alumno, Pagination pagination, Boolean startsWith) ;
  
	/**
	 * Deletes a single row in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return 
	 */
	void remove(Alumno alumno);
	
	/**
	 * Deletes multiple rows in the Alumno table.
	 *
	 * @param alumnoList  ArrayList
	 * @return 
	 */	
	void removeMultiple(List<Alumno> alumnoList);
	
	/**
	 * Inserts a single row in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return Alumno
	 */
    Alumno add(Alumno alumno, Errors errors);
    
    /**
	 * Inserts a single row in the Alumno table.
	 *
	 * @param alumno Alumno
	 * @return Alumno
	 */
    Alumno update(Alumno alumno, String oldPassword,  Errors errors);
    
    
    Alumno getImagen(Alumno alumno);
}


