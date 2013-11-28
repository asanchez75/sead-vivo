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

package edu.cornell.mannlib.vitro.webapp.edit.n3editing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.rdf.model.Literal;

import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.N3ValidatorVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.EditConfigurationVTwo;
import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.MultiValueEditSubmission;

public class PersonHasPublicationValidator implements N3ValidatorVTwo {

	private Log log = LogFactory.getLog(PersonHasPublicationValidator.class);

    private static String MISSING_FIRST_NAME_ERROR = "You must enter a value in the First Name field.";
    private static String MISSING_LAST_NAME_ERROR = "You must enter a value in the Last Name field.";
    private static String MALFORMED_LAST_NAME_ERROR = "The last name field may not contain a comma. Please enter first name in First Name field.";
    
    @Override
    public Map<String, String> validate(EditConfigurationVTwo editConfig,
            MultiValueEditSubmission editSub) {

        Map<String,List<String>> urisFromForm = editSub.getUrisFromForm();
        Map<String,List<Literal>> literalsFromForm = editSub.getLiteralsFromForm();

        Map<String,String> errors = new HashMap<String,String>();   
        
        // The Editor field is optional for all publication subclasses. Validation is only necessary if the user only enters a 
        // last name or only enters a first name
        
        //Expecting only one first name in this case
        //To Do: update logic if multiple first names considered
        List<Literal> firstNameList = literalsFromForm.get("firstName");
        Literal firstName = null;
        if(firstNameList != null && firstNameList.size() > 0) {
    	    firstName = firstNameList.get(0);
        }
        if ( firstName != null && 
    		    firstName.getLexicalForm() != null && 
    		    "".equals(firstName.getLexicalForm()) )
                firstName = null;

        List<Literal> lastNameList = literalsFromForm.get("lastName");
        Literal lastName = null;
        if(lastNameList != null && lastNameList.size() > 0) {
	        lastName = lastNameList.get(0);
        }
        String lastNameValue = "";
        if (lastName != null) {
            lastNameValue = lastName.getLexicalForm();
            if( "".equals(lastNameValue) ) {
                lastName = null;
            }
        }

        if ( lastName == null && firstName != null ) {
            errors.put("lastName", MISSING_LAST_NAME_ERROR);
            // Don't reject space in the last name: de Vries, etc.
        }
        else if ( firstName == null && lastName != null) {
            errors.put("firstName", MISSING_FIRST_NAME_ERROR);
        }
        else if (lastNameValue.contains(",")) {            
            errors.put("lastName", MALFORMED_LAST_NAME_ERROR);
        }
        else {
            return null;
        }               
        
        return errors.size() != 0 ? errors : null;
    }
    
    private Object getFirstElement(List checkList) {
    	if(checkList == null || checkList.size() == 0) {
    		return null;
    	}
    	return checkList.get(0);
    }
    

}
