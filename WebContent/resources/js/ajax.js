$(document).ready(function() {
	$("#questionnaire").on("click",".addReponse", function(){
		$('#send').before('<input type="text" placeholder="mauvaise reponse" name="reponse" class="reponse"/>');
	});
	
	$('#type').change(function() {
	    var type = $('#type option:selected').val();
	    var qcm = '<textarea name="questionQcm" form="formQuestion" placeholder="Ajouter une question"></textarea> <input type="text" name="correctQcm" placeholder="Bonne r&#xE9;ponse" form="formQuestion" /><input type="text" name="notCorrectQcm1" placeholder="Mauvaise r&#xE9;ponse 1" form="formQuestion"/><input type="text" name="notCorrectQcm2" placeholder="Mauvaise r&#xE9;ponse 2" form="formQuestion"/><input type="text" name="notCorrectQcm3" placeholder="Mauvaise r&#xE9;ponse 3" form="formQuestion"/><input type="submit" value="Ajouter Qcm" form="formQuestion"/><br>';
	    var questionSimple = '<textarea placeholder="Entrer la question" id="questionSimple" name="questionSimple" form="formQuestion"></textarea><input type="text" placeholder="Entrer la r&#xE9;ponse" name="reponse" class="reponse" form="formQuestion" /><input type="submit" value="Ajouter question"  form="formQuestion"/><br>';
	    $.ajax({
	        url : '/questionnaire',
	        data: type,
	        success: function() {
	           switch(type) {
	           	case "QCM":
	           		$('#afficheQuestionnaire').before(qcm);
	        	break;
	           	case "QUESTION_SIMPLE":
	           		$('#afficheQuestionnaire').before(questionSimple);
	        	break;
	           	default:
	           }
	        },
	    });
	});


});
