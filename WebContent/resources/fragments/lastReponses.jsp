
<!--  FORMULAIRE DE CHOIX DES VERSIONS DE REPONSE -->
<form
	action="<c:url value='/questionnaire?questionnaire=${questionnaire.id}"'/>"
	method="POST" id="SelectVersion"></form>
<input type="submit" value="Chercher une Version" form="SelectVersion" />
<select name="checkVersion" form="SelectVersion">
	<c:forEach items="${reponses}" var="reponse" varStatus="countreponse">
		<c:if test="${countreponse.first }">
			<c:forEach begin="2" end="${lastVersion +1  }" varStatus="loop">


				<option value="${loop.index-1 }">${loop.index -1}</option>
			</c:forEach>
		</c:if>
	</c:forEach>

</select>
<!-- AFFICHAGE DES RESULTATS -->

<c:set var="point" value="0" scope="page" />
<c:set var="nbQuestion" value="0" scope="page" />

<c:forEach items="${reponses}" var="reponse" varStatus="countreponse">
	<c:if test="${countreponse.first }">
		<h4>Version de votre test : num�ro ${reponse.version }</h4>
	</c:if>
	<c:set var="nbQuestion" value="${nbQuestion + 1}" scope="page" />
	<c:forEach items="${bonneReponses}" var="bonnereponse"
		varStatus="countbonnereponse">




		<c:if test="${reponse.question == bonnereponse.question }">
			<p>
				Question
				<h3>${reponse.question.question}</h3>
						<br />
					Votre reponse : <h5>   ${reponse.reponse}</h5>
				<c:if test="${reponse.question.type == 'QCM' }">
					<c:choose>
						<c:when test="${reponse.reponse == bonnereponse.reponse}">
							<c:set var="point" value="${point + 1}" scope="page" />


						</c:when>

					</c:choose>

				</c:if>
				<c:if test="${reponse.question.type == 'QUESTION_SIMPLE' }">

					<c:set var="ReussitMotCle" value="0" scope="page" />

					<c:set var="Splitreponses"
						value="${fn:split(bonnereponse.reponse, ' ')}" />
					 
					 mots cles attendu : ${fn:length(Splitreponses)}
			</p>

			<c:forEach items="${Splitreponses}" var="Splitreponse">


				<c:if test="${fn:contains(fn:toLowerCase(reponse.reponse),fn:toLowerCase(Splitreponse))}">
					<c:set var="ReussitMotCle"
						value="${ReussitMotCle + 1}" scope="page" />
				</c:if>
			</c:forEach>
		
			<c:if test="${ReussitMotCle * 100 / fn:length(Splitreponses) >= reponse.question.pourcentageNeed }">
			<c:set var="point" value="${point + 1}" scope="page" />
			</c:if>
			





		</c:if>


		</c:if>
				
				
				
					</p>
					<hr>
				
					
					
		</c:forEach>
				
				
		</c:forEach>
		<h4>note : ${point * 100 / nbQuestion}%</h4> 
		<a title="refaire" href='<c:url value="/questionnaire?nextVersion=${reponse.version +1}&questionnaire=${questionnaire.id}" />'> Refaire le questionnaire</a>