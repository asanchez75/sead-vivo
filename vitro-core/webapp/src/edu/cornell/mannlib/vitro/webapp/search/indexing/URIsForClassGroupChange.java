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
package edu.cornell.mannlib.vitro.webapp.search.indexing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Statement;

import edu.cornell.mannlib.vitro.webapp.beans.Individual;
import edu.cornell.mannlib.vitro.webapp.dao.IndividualDao;
import edu.cornell.mannlib.vitro.webapp.dao.VitroVocabulary;
import edu.cornell.mannlib.vitro.webapp.search.beans.StatementToURIsToUpdate;

/**
 * if a class's classgroup changes, reindex all individuals in that class.  
 */
public class URIsForClassGroupChange  implements StatementToURIsToUpdate {
    
    IndividualDao indDao;
    
    public URIsForClassGroupChange(IndividualDao individualDao){
        this.indDao = individualDao;
    }
    
    @Override
    public List<String> findAdditionalURIsToIndex(Statement stmt) {
        if( stmt == null || stmt.getPredicate() == null) 
            return Collections.emptyList();
        
        //if it is a change in classgroup of a class
        if( VitroVocabulary.IN_CLASSGROUP.equals( stmt.getPredicate().getURI() ) &&
            stmt.getSubject() != null && 
            stmt.getSubject().isURIResource() ){
            
            //get individuals in class
            List<Individual>indsInClass = 
                indDao.getIndividualsByVClassURI(stmt.getSubject().getURI());
            if( indsInClass == null )
                return Collections.emptyList();
            
            //convert individuals to list of uris
            List<String> uris = new ArrayList<String>();
            for( Individual ind : indsInClass){
                uris.add( ind.getURI() );
            }
            return uris;
        }else{
            return Collections.emptyList();
        }
    }

    @Override
    public void startIndexing() {
        // Do nothing
        
    }

    @Override
    public void endIndxing() {
        // Do nothing
        
    }

}
