<form action="<c:url value='/addQuestionnaire'/>" method="POST" >

<select name="idCategory" >
<option value="" selected disabled>Cat�gorie</option>
<c:forEach items="${categories}" var="category">
<option style="background-color:${category.color}" value="${category.id}">${category.name}</option>
</c:forEach>

</select>
<input type="text" name="name_questionnaire"placeholder="Nom questionnaire"/>
<input type="text" name="description_questionnaire"placeholder="Description" />
<input type="number" name="remake_questionnaire" min= "1" max= "100" placeholder="limite de version" />


<input type="submit" value="Ajouter Questionnaire" />
<br>
${addquestionnaireerror}
</form>