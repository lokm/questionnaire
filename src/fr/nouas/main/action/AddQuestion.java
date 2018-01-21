package fr.nouas.main.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import fr.nouas.beans.Question;
import fr.nouas.beans.Questionnaire;
import fr.nouas.beans.Reponse;
import fr.nouas.enums.TypeQuestion;
import fr.nouas.pojo.utils.Action;
import fr.nouas.utils.JpaUtil;


public class AddQuestion extends Action {
	
	private static final String CHAMP_type = "type";
	private static final String CHAMP_QUESTIONQQCM     = "questionQcm";
    private static final String CHAMP_correctQcm = "correctQcm";
    private static final String CHAMP_notCorrectQcm1 = "notCorrectQcm1";
    private static final String CHAMP_notCorrectQcm2 = "notCorrectQcm2";
    private static final String CHAMP_questionSimple    = "questionSimple";
    private static final String CHAMP_reponse = "reponse";
    
    
    
    private static final int    TAILLE_TAMPON     = 1000240;                       

    private String              resultat;
    private Map<String, String> erreurs           = new HashMap<String, String>();
    
    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
	int idQuestionnaire = 0;

	@Override
	public boolean executeAction(HttpServletRequest request) {
		 String chemin = "/Users/youce/eclipse-workspace/questionnaire/WebContent/resources/img/";

		if (request.getMethod().equals("POST")) {

			idQuestionnaire = Integer.parseInt(request.getParameter("questionnaire"));

			List<Reponse> reponses = new ArrayList();

			EntityManager em = JpaUtil.getEntityManager();
			EntityTransaction tr = em.getTransaction();

			Questionnaire questionnaire = em.find(Questionnaire.class, idQuestionnaire);
			System.out.println("je suis dans le post dadd question pour le questionnaire : " + questionnaire.getName());

//			// pour les qcm faire le if
//			if (request.getParameter("type").equals("QCM")) {
//
//				System.out.println("c'est bien un QCM ! ");
//
//				Reponse bonneReponse = new Reponse(request.getParameter("correctQcm"), true, null, questionnaire);
//
//				Question question = new Question(
//						request.getParameter("questionQcm"), TypeQuestion.valueOf(request.getParameter("type")),
//						bonneReponse, reponses, questionnaire);
//				bonneReponse.setQuestion(question);
//
//				Reponse mauvaiseReponse1 = new Reponse(request.getParameter("notCorrectQcm1"), false, question, questionnaire);
//				reponses.add(mauvaiseReponse1);
//
//				Reponse mauvaiseReponse2 = new Reponse(request.getParameter("notCorrectQcm2"), false, question, questionnaire);
//				reponses.add(mauvaiseReponse2);
//
//				Collections.shuffle(reponses);
//
//				tr.begin();
//				em.persist(question);
//				em.persist(bonneReponse);
//				em.persist(mauvaiseReponse1);
//				em.persist(mauvaiseReponse2);
//		
//				tr.commit();
//
//			}
//			if (request.getParameter("type").equals("QUESTION_SIMPLE")) {
//
//				System.out.println("c'est bien une question simple ! ");
//
//				Reponse bonneReponse = new Reponse(request.getParameter("reponse"), true, null, questionnaire);
//
//				Question question = new Question(request.getParameter("questionSimple"),
//						TypeQuestion.valueOf(request.getParameter("type")), bonneReponse, questionnaire);
//				bonneReponse.setQuestion(question);
//				System.out.println(request.getParameter("pourcentageNeed"));
//				question.setPourcentageNeed(Integer.parseInt(request.getParameter("pourcentageNeed")));
//
//				tr.begin();
//				em.persist(question);
//				em.persist(bonneReponse);
//				tr.commit();
//
//			}
//			
//			return false;
//
//		
//		}

	
			Question question = new Question ();
			Part type = null;
			try {
				type = request.getPart(CHAMP_type);
			} catch (IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 String ValeurType = null;
			try {
				ValeurType = getValeur(type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				System.out.println(ValeurType + "LE TYPE");	
			 
			if (ValeurType.equals("QCM")) {

			

	
				Reponse bonneReponse = null;
				try {
					bonneReponse = new Reponse(getValeur(request.getPart(CHAMP_correctQcm)), true, null, questionnaire);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				 question = new Question(
						request.getParameter("questionQcm"), TypeQuestion.valueOf(request.getParameter("type")),
						bonneReponse, reponses, questionnaire);
				bonneReponse.setQuestion(question);

				Reponse mauvaiseReponse1 = null;
				try {
					mauvaiseReponse1 = new Reponse(getValeur(request.getPart(CHAMP_notCorrectQcm1)), false, question, questionnaire);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reponses.add(mauvaiseReponse1);

				Reponse mauvaiseReponse2 = null;
				try {
					mauvaiseReponse2 = new Reponse(getValeur(request.getPart(CHAMP_notCorrectQcm2)), false, question, questionnaire);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reponses.add(mauvaiseReponse2);

				Collections.shuffle(reponses);

			
				
				
				Part fichierPart = null;

				
				

				
				try {
					fichierPart = request.getPart("fichier");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				 String nomFichierPart = getNomFichier( fichierPart );
				
				
				    /*
				     * Si la m�thode a renvoy� quelque chose, il s'agit donc d'un champ
				     * de type fichier (input type="file").
				     */
				    if ( nomFichierPart != null && !nomFichierPart.isEmpty() ) {
				        String nomChamp = fichierPart.getName();
				        /*
				         * Antibug pour Internet Explorer, qui transmet pour une raison
				         * mystique le chemin du fichier local � la machine du client...
				         * 
				         * Ex : C:/dossier/sous-dossier/fichier.ext
				         * 
				         * On doit donc faire en sorte de ne s�lectionner que le nom et
				         * l'extension du fichier, et de se d�barrasser du superflu.
				         */
				        nomFichierPart = nomFichierPart.substring( nomFichierPart.lastIndexOf( '/' ) + 1 )
				                .substring( nomFichierPart.lastIndexOf( '\\' ) + 1 );

				        /* �criture du fichier sur le disque */
				        try {
							ecrireFichier( fichierPart, nomFichierPart, chemin );
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				     
				    }
				
					tr.begin();
					question.setImage(nomFichierPart);
					em.persist(question);
					em.persist(bonneReponse);
					em.persist(mauvaiseReponse1);
					em.persist(mauvaiseReponse2);
			
					tr.commit();
				
				
				
				
				
			}
			if (ValeurType.equals("QUESTION_SIMPLE")) {

				System.out.println("c'est bien une question simple ! ");

				Reponse bonneReponse = null;
				try {
					bonneReponse = new Reponse(getValeur(request.getPart(CHAMP_reponse)), true, null, questionnaire);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				 try {
					question = new Question(getValeur(request.getPart(CHAMP_questionSimple)),
							TypeQuestion.valueOf(request.getParameter("type")), bonneReponse, questionnaire);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bonneReponse.setQuestion(question);
				System.out.println(request.getParameter("pourcentageNeed"));
				try {
					question.setPourcentageNeed(Integer.parseInt(getValeur(request.getPart("pourcentageNeed"))));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				Part fichierPart = null;

				
				

				
				try {
					fichierPart = request.getPart("fichier");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				 String nomFichierPart = getNomFichier( fichierPart );
				
				
				    /*
				     * Si la m�thode a renvoy� quelque chose, il s'agit donc d'un champ
				     * de type fichier (input type="file").
				     */
				    if ( nomFichierPart != null && !nomFichierPart.isEmpty() ) {
				        String nomChamp = fichierPart.getName();
				        /*
				         * Antibug pour Internet Explorer, qui transmet pour une raison
				         * mystique le chemin du fichier local � la machine du client...
				         * 
				         * Ex : C:/dossier/sous-dossier/fichier.ext
				         * 
				         * On doit donc faire en sorte de ne s�lectionner que le nom et
				         * l'extension du fichier, et de se d�barrasser du superflu.
				         */
				        nomFichierPart = nomFichierPart.substring( nomFichierPart.lastIndexOf( '/' ) + 1 )
				                .substring( nomFichierPart.lastIndexOf( '\\' ) + 1 );

				        /* �criture du fichier sur le disque */
				        try {
							ecrireFichier( fichierPart, nomFichierPart, chemin );
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				     
				    }
				    String ImageQuestion = chemin+nomFichierPart;
				    System.out.println("LIEN DE LIMAGE YOUHOU " + ImageQuestion);
				
				
				
				
				tr.begin();
		
				question.setImage(nomFichierPart);
				em.persist(question);
				em.persist(bonneReponse);
				tr.commit();

			}
			
			return false;

		
		}
	
	
	
					return false;					}
	
	
	
	
	
//	METHODE UTILITAIRES
	
	
	/*
	 * M�thode utilitaire qui a pour unique but de lire l'InputStream contenu
	 * dans l'objet part, et de le convertir en une banale cha�ne de caract�res.
	 */
	private String getValeur( Part part ) throws IOException {
	    BufferedReader reader = new BufferedReader( 
	    		new InputStreamReader( 
	    				part.getInputStream(), 
	    				"utf-8" ) );
	    StringBuilder valeur = new StringBuilder();
	    char[] buffer = new char[1024];
	    int longueur = 0;
	    while ( ( longueur = reader.read( buffer ) ) > 0 ) {
	        valeur.append( buffer, 0, longueur );
	    }
	    return valeur.toString();
	}
	
	
	
	
	/*
     * Valide la description saisie.
     */
    private void validationDescription( String description ) throws Exception {
        if ( description != null ) {
            if ( description.length() < 15 ) {
                throw new Exception( "La phrase de description du fichier doit contenir au moins 15 caract�res." );
            }
        } else {
            throw new Exception( "Merci d'entrer une phrase de description du fichier." );
        }
    }

    /*
     * Valide le fichier envoy�.
     */
    private void validationFichier( String nomFichier, InputStream contenuFichier ) throws Exception {
        if ( nomFichier == null || contenuFichier == null ) {
            throw new Exception( "Merci de s�lectionner un fichier � envoyer." );
        }
    }

    /*
     * Ajoute un message correspondant au champ sp�cifi� � la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * M�thode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }

    /*
     * M�thode utilitaire qui a pour unique but d'analyser l'en-t�te
     * "content-disposition", et de v�rifier si le param�tre "filename" y est
     * pr�sent. Si oui, alors le champ trait� est de type File et la m�thode
     * retourne son nom, sinon il s'agit d'un champ de formulaire classique et
     * la m�thode retourne null.
     */
    private static String getNomFichier( Part part ) {
        /* Boucle sur chacun des param�tres de l'en-t�te "content-disposition". */
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            /* Recherche de l'�ventuelle pr�sence du param�tre "filename". */
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                /*
                 * Si "filename" est pr�sent, alors renvoi de sa valeur,
                 * c'est-�-dire du nom de fichier sans guillemets.
                 */
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        /* Et pour terminer, si rien n'a �t� trouv�... */
        return null;
    }

    /*
     * M�thode utilitaire qui a pour but d'�crire le fichier pass� en param�tre
     * sur le disque, dans le r�pertoire donn� et avec le nom donn�.
     */
    private void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
        /* Pr�pare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream( part.getInputStream(), TAILLE_TAMPON );
            sortie = new BufferedOutputStream( new FileOutputStream( new File( chemin + nomFichier ) ),
                    TAILLE_TAMPON );

            /*
             * Lit le fichier re�u et �crit son contenu dans un fichier sur le
             * disque.
             */
            byte[] tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ( ( longueur = entree.read( tampon ) ) > 0 ) {
                sortie.write( tampon, 0, longueur );
            }
        } finally {
            try {
                sortie.close();
            } catch ( IOException ignore ) {
            }
            try {
                entree.close();
            } catch ( IOException ignore ) {
            }
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	

}
