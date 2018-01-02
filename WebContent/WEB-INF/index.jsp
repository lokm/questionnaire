<!DOCTYPE html>
<html lang="fr">
<head>
<link rel="stylesheet" href='<c:url value="/resources/css/style.css" />' />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Questionnaire</title>
</head>
<body>
<h1>Test de positionnement</h1>
<section>
<c:forEach items="${categories}" var="category">

	<p><span style="background-color:${category.color}"></span> <a href='<c:url value="/${category.name}" />'>${category.name}</a></p>

	<c:forEach items="${category.questionnaires}" var="questionnaire">
		<article>
		<h2 style="background-color:${category.color}">${category.name}</h2>
		<h3>${questionnaire.name}</h3>
		<p>${questionnaire.description}</p>
		</article>
	</c:forEach>
	
</c:forEach>
<p>
<span style="background-color:blue"></span> <a href='<c:url value="/mathematique" />'>Math�matique</a>
<span style="background-color:green"></span> <a href='<c:url value="/francais" />'>Fran�ais</a>
</p>

<article>
	<h2 style="background-color:blue">Math�matique</h2>
	<h3>G�om�trie</h3>
	<p>Test de g�om�trie g�n�rale</p>
</article>

<article>
	<h2 style="background-color:green">Fran�ais</h2>
	<h3>Conjugaison</h3>
	<p>Test de conjugaison g�n�rale</p>
</article>
</section>
</body>
</html>