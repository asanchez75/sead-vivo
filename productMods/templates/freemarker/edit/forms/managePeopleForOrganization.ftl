<#--
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
-->

<#import "lib-vivo-form.ftl" as lvf>

<#-- Custom form for managing web pages for individuals -->
<h2>Manage People Affiliated with ${subjectName}</h2>
<p style="margin-left:25px;margin-bottom:12px">
Check those people you want to exclude from the profile page.
<script type="text/javascript">
    var peopleData = [];
</script>
</p>

<@lvf.unsupportedBrowser urls.base /> 

       
    <#list allSubclasses as sub>
    <h4>${sub}s</h4>
        <section id="pubsContainer" role="container">
        <#assign peeps = people[sub]>
        <ul >
            <#list peeps as person>
            <li>
                <input type="checkbox" class="pubCheckbox" <#if person.hideThis??>checked</#if> />${person.name}
            </li>
            <script type="text/javascript">
                peopleData.push({
                    "positionUri": "${person.position}"              
                });
            </script>      
            
            </#list>
        </ul>
        </section>
    </#list>

<br />    
<p>
    <a href="${urls.referringPage}#affiliation" title="return to profile page">Return to profile page</a>
</p>

<script type="text/javascript">
var customFormData = {
    processingUrl: '${urls.base}/edit/primitiveRdfEdit'
};
</script>

${stylesheets.add('<link rel="stylesheet" href="${urls.base}/templates/freemarker/edit/forms/css/customForm.css" />',
                  '<link rel="stylesheet" href="${urls.base}/js/jquery-ui/css/smoothness/jquery-ui-1.8.9.custom.css" />')}

${scripts.add('<script type="text/javascript" src="${urls.base}/js/utils.js"></script>',
              '<script type="text/javascript" src="${urls.base}/js/jquery-ui/js/jquery-ui-1.8.9.custom.min.js"></script>',
              '<script type="text/javascript" src="${urls.base}/js/customFormUtils.js"></script>',
                '<script type="text/javascript" src="${urls.base}/templates/freemarker/edit/forms/js/managePeopleForOrganization.js"></script>')}
              
