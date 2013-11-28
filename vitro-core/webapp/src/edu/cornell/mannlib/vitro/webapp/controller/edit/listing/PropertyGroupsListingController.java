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

package edu.cornell.mannlib.vitro.webapp.controller.edit.listing;

import java.net.URLEncoder;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vedit.controller.BaseEditController;
import edu.cornell.mannlib.vitro.webapp.auth.permissions.SimplePermission;
import edu.cornell.mannlib.vitro.webapp.beans.DataProperty;
import edu.cornell.mannlib.vitro.webapp.beans.ObjectProperty;
import edu.cornell.mannlib.vitro.webapp.beans.Property;
import edu.cornell.mannlib.vitro.webapp.beans.PropertyGroup;
import edu.cornell.mannlib.vitro.webapp.controller.Controllers;
import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.dao.PropertyGroupDao;

public class PropertyGroupsListingController extends BaseEditController {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(PropertyGroupsListingController.class);
    private static final boolean WITH_PROPERTIES = true;

    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if (!isAuthorizedToDisplayPage(request, response,
				SimplePermission.EDIT_ONTOLOGY.ACTIONS)) {
    		return;
    	}
    	
        VitroRequest vreq = new VitroRequest(request);

        PropertyGroupDao dao = vreq.getFullWebappDaoFactory().getPropertyGroupDao();

        List<PropertyGroup> groups = dao.getPublicGroups(WITH_PROPERTIES);
        
        Comparator<Property> comparator = new PropertySorter();

        List<String> results = new ArrayList<String>();
        results.add("XX");
        results.add("group");
        results.add("display rank");
        results.add("");
        results.add("XX");

        if (groups != null) {
        	for(PropertyGroup pg: groups) {
                results.add("XX");
                String publicName = pg.getName();
                if ( StringUtils.isBlank(publicName) ) {
                    publicName = "(unnamed group)";
                }           
                try {
                    results.add("<a href=\"./editForm?uri="+URLEncoder.encode(pg.getURI(),"UTF-8")+"&amp;controller=PropertyGroup\">"+publicName+"</a>");
                } catch (Exception e) {
                    results.add(publicName);
                }
                Integer t;
                results.add(((t = Integer.valueOf(pg.getDisplayRank())) != -1) ? t.toString() : "");
                results.add("");
                results.add("XX");
                List<Property> propertyList = pg.getPropertyList();
                if (propertyList != null && propertyList.size() > 0) {
                	Collections.sort(propertyList, comparator);
                    results.add("+");
                    results.add("XX");
                    results.add("property");
                    results.add("");
                    results.add("");
                    results.add("@@entities");
                    Iterator<Property> propIt = propertyList.iterator();
                    while (propIt.hasNext()) {
                        Property prop = propIt.next();
                        results.add("XX");
                        String controllerStr = "propertyEdit";
                        String nameStr = 
                        	   (prop.getLabel() == null) 
                        	           ? "" 
                        	           : prop.getLabel();
                        if (prop instanceof ObjectProperty) {
                        	nameStr = ((ObjectProperty) prop).getDomainPublic();
                        } else if (prop instanceof DataProperty) {
                        	controllerStr = "datapropEdit";
                        	nameStr = ((DataProperty) prop).getName();
                        }
                        if (prop.getURI() != null) {
                            try {
                                results.add("<a href=\"" + controllerStr + 
                                		"?uri="+URLEncoder.encode(
                                				prop.getURI(),"UTF-8") + 
                                				"\">" + nameStr +"</a>");
                            } catch (Exception e) {
                                results.add(nameStr);
                            }
                        } else {
                            results.add(nameStr);
                        }
                        String exampleStr = "";
                        results.add(exampleStr);
                        String descriptionStr = "";
                        results.add(descriptionStr);
                        if (propIt.hasNext())
                            results.add("@@entities");
                    }
                }
            }
            request.setAttribute("results",results);
        }

        request.setAttribute("columncount",new Integer(5));
        request.setAttribute("suppressquery","true");
        request.setAttribute("title","Property Groups");
        request.setAttribute("bodyJsp", Controllers.HORIZONTAL_JSP);
        request.setAttribute("horizontalJspAddButtonUrl", Controllers.RETRY_URL);
        request.setAttribute("horizontalJspAddButtonText", "Add new property group");
        request.setAttribute("horizontalJspAddButtonControllerParam", "PropertyGroup");
        RequestDispatcher rd = request.getRequestDispatcher(Controllers.BASIC_JSP);
        try {
            rd.forward(request,response);
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
    
    private class PropertySorter implements Comparator<Property> {
    	
    	private Collator coll = Collator.getInstance();
    	
    	public int compare(Property p1, Property p2) {
    		String name1 = getName(p1);
    		String name2 = getName(p2);
    		if (name1 == null && name2 != null) {
    			return 1;
    		} else if (name2 == null && name1 != null) {
    			return -1;
    		} else if (name1 == null && name2 == null) {
    			return 0;
    		}
    		return coll.compare(name1, name2);
    	}
    	
    	private String getName(Property prop) {
    		if (prop instanceof ObjectProperty) {
    			return ((ObjectProperty) prop).getDomainPublic();
    		} else if (prop instanceof DataProperty) {
    			return ((DataProperty) prop).getName();
    		} else {
    			return prop.getLabel();
    		}
    	}
    }
    
}
