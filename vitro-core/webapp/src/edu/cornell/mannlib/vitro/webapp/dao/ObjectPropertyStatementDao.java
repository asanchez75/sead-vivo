/*
Copyright (c) 2013, Cornell University
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.
    * Neither the name of Cornell University nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package edu.cornell.mannlib.vitro.webapp.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cornell.mannlib.vitro.webapp.beans.Individual;
import edu.cornell.mannlib.vitro.webapp.beans.ObjectProperty;
import edu.cornell.mannlib.vitro.webapp.beans.ObjectPropertyStatement;

/**
 * Created by IntelliJ IDEA.
 * User: bdc34
 * Date: Apr 18, 2007
 * Time: 7:08:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectPropertyStatementDao {
	
    void deleteObjectPropertyStatement(ObjectPropertyStatement objPropertyStmt);
    
    List<ObjectPropertyStatement> getObjectPropertyStatements(ObjectProperty objectProperty);
    
    List<ObjectPropertyStatement> getObjectPropertyStatements(ObjectProperty objectProperty, int startIndex, int endIndex);

    List<ObjectPropertyStatement> getObjectPropertyStatements(ObjectPropertyStatement objPropertyStmt);
    
    Individual fillExistingObjectPropertyStatements( Individual entity );

    int insertNewObjectPropertyStatement(ObjectPropertyStatement objPropertyStmt );        

//    public List<Map<String, String>> getObjectPropertyStatementsForIndividualByProperty(
//			String subjectUri, 
//			String propertyUri, 
//			String objectKey, 
//			String queryString,
//			Set<String> constructQueryStrings);
    
    public Map<String, String> getMostSpecificTypesInClassgroupsForIndividual(String subjectUri);

	List<Map<String, String>> getObjectPropertyStatementsForIndividualByProperty(
			String subjectUri, String propertyUri, String objectKey,
			String queryString, Set<String> constructQueryStrings,
			String sortDirection);

	
  
}
