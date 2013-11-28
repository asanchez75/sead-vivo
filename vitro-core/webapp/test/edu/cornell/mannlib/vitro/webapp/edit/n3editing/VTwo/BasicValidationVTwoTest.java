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
package edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo;


import java.util.Collections;

import junit.framework.Assert;

import org.junit.Test;

public class BasicValidationVTwoTest {


    @SuppressWarnings("unchecked")
    @Test
    public void testHttpUrlValidate() {
        BasicValidationVTwo bv = new BasicValidationVTwo(Collections.EMPTY_MAP);
        String res;
        res = bv.validate("httpUrl", "http://example.com/index");
        Assert.assertEquals(res, BasicValidationVTwo.SUCCESS);
        
        res = bv.validate("httpUrl", "http://example.com/index?bogus=skjd%20skljd&something=sdkf");
        Assert.assertEquals(res, BasicValidationVTwo.SUCCESS);
        
        res = bv.validate("httpUrl", "http://example.com/index#2.23?bogus=skjd%20skljd&something=sdkf");
        Assert.assertEquals(res, BasicValidationVTwo.SUCCESS);               
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testEmptyValidate(){
       BasicValidationVTwo bv = new BasicValidationVTwo(Collections.EMPTY_MAP);              
       
       Assert.assertEquals(
               bv.validate("nonempty", null)
               , BasicValidationVTwo.REQUIRED_FIELD_EMPTY_MSG);
        

       Assert.assertEquals(
               bv.validate("nonempty", "")
               , BasicValidationVTwo.REQUIRED_FIELD_EMPTY_MSG);
       
       Assert.assertEquals(
               bv.validate("nonempty", "some value")
               , BasicValidationVTwo.SUCCESS);
    }
}
