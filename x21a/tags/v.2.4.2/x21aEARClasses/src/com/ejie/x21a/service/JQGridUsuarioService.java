/*
* Copyright 2012 E.J.I.E., S.A.
*
* Licencia con arreglo a la EUPL, Versión 1.1 exclusivamente (la «Licencia»);
* Solo podrá usarse esta obra si se respeta la Licencia.
* Puede obtenerse una copia de la Licencia en
*
* http://ec.europa.eu/idabc/eupl.html
*
* Salvo cuando lo exija la legislación aplicable o se acuerde por escrito,
* el programa distribuido con arreglo a la Licencia se distribuye «TAL CUAL»,
* SIN GARANTÍAS NI CONDICIONES DE NINGÚN TIPO, ni expresas ni implícitas.
* Véase la Licencia en el idioma concreto que rige los permisos y limitaciones
* que establece la Licencia.
*/
package com.ejie.x21a.service;


import java.util.List;

import com.ejie.x21a.model.Usuario;
import com.ejie.x38.rss.RssContent;
import com.ejie.x38.dto.JQGridRequestDto;
import com.ejie.x38.dto.JQGridResponseDto;
import com.ejie.x38.dto.JerarquiaDto;
import com.ejie.x38.dto.TableRowDto;

/**
 * UsuarioService generated by UDA, 14-ago-2012 12:59:39.
 * @author UDA
 */

public interface JQGridUsuarioService {

	/*
	 * OPERACIONES CRUD
	 */
	
	/**
	 * Inserts a single row in the Usuario table.
	 *
	 * @param usuario Usuario
	 * @return Usuario
	 */
    Usuario add(Usuario usuario);

	/**
	 * Updates a single row in the Usuario table.
	 *
	 * @param usuario Usuario
	 * @return Usuario
	 */
	Usuario update(Usuario usuario);

	/**
	 * Finds a single row in the Usuario table.
	 *
	 * @param usuario Usuario
	 * @return Usuario
	 */
	Usuario find(Usuario usuario);

	/**
	 * Deletes a single row in the Usuario table.
	 *
	 * @param usuario Usuario
	 * @return 
	 */
	void remove(Usuario usuario);
	
	/**
	 * Finds a List of rows in the Usuario table.
	 *
	 * @param usuario Usuario
	 * @param pagination Pagination
	 * @return List
	 */
	List<Usuario> findAll(Usuario usuario, JQGridRequestDto jqGridRequestDto);

	/**
	 * Finds rows in the Usuario table using like.
	 *
	 * @param usuario Usuario
	 * @param jqGridRequestDto JQGridRequestDto
     * @param startsWith Boolean	 
	 * @return List
	 */
	List<Usuario> findAllLike(Usuario usuario, JQGridRequestDto jqGridRequestDto, Boolean startsWith) ;
	
	
	/*
	 * OPERACIONES RUP_TABLE
	 */

	/**
	 * Deletes multiple rows in the Usuario table.
	 *
	 * @param filterUsuario Usuario
	 * @param jqGridRequestDto JQGridRequestDto
	 * @param startsWith Boolean
	 */	
	void removeMultiple(Usuario filterUsuario, JQGridRequestDto jqGridRequestDto, Boolean startsWith);
	
	/**
	 * Searches in the Usuario table.
	 *
	 * @param filterUsuario Usuario
	 * @param searchUsuario Usuario
	 * @param jqGridRequestDto JQGridRequestDto
	 * @param startsWith Boolean
	 */	
	List<TableRowDto<Usuario>> search(Usuario filterUsuario, Usuario searchUsuario, JQGridRequestDto JQGridRequestDto, Boolean startsWith);

	JQGridResponseDto<Usuario> filter(Usuario usuario, JQGridRequestDto jqGridRequestDto, Boolean startsWith) ;
	
	Object reorderSelection(Usuario usuario, JQGridRequestDto jqGridRequestDto, Boolean startsWith);

	
	/*
	 * OPERACIONES RUP_TABLE JERARQUIA
	 */
	JQGridResponseDto<JerarquiaDto<Usuario>> jerarquia (Usuario filterUsuario, JQGridRequestDto jqGridRequestDto, Boolean startsWith);
	
	JQGridResponseDto<JerarquiaDto<Usuario>> jerarquiaChildren (Usuario filterUsuario, JQGridRequestDto jqGridRequestDto);

	
	/*
	 * RSS
	 */
	List<RssContent> getRssFeed();
}


