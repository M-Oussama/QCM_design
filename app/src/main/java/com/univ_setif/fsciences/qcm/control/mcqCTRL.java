package com.univ_setif.fsciences.qcm.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.entity.Question;
import com.univ_setif.fsciences.qcm.entity.Answer;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by hzerrad on 18-Mar-18.
 */

public class mcqCTRL {

    /**
     * SQLite Database Helper class used to create and update databases
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        /*=========================================
        //  S Q L    D D L     S T A T E M E N T S
        ===========================================*/
        static final String CREATE_QUESTION =
                "CREATE TABLE Question(\n" +
                        questionID      + " INTEGER PRIMARY KEY autoincrement,\n" +
                        questionText    + " VARCHAR(100) NOT NULL\n" +
                        ");\n";

        static final String CREATE_ANSWER =
                "CREATE TABLE Answer(\n" +
                        answerID +" INTEGER PRIMARY KEY autoincrement,\n" +
                        answerText + " VARCHAR(50)\n" +
                        ");\n";

        static final String CREATE_QUESTIONANSWER=
                "CREATE TABLE QuestionAnswer(\n" +
                        questionID +" INTEGER,\n" +
                        answerID +" INTEGER, \n" +
                        IS_CORRECT + " INTEGER NOT NULL DEFAULT 0, " +
                        "CHECK ("+IS_CORRECT+" IN (0, 1)), " +
                        "PRIMARY KEY("+questionID+", "+answerID+"), \n"+
                        "FOREIGN KEY("+questionID+") REFERENCES Question("+questionID+"), \n" +
                        "FOREIGN KEY("+answerID+") REFERENCES Answer("+answerID+")\n" +
                        ");\n";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_QUESTION);
            db.execSQL(CREATE_ANSWER);
            db.execSQL(CREATE_QUESTIONANSWER);
            mDb = db;

            initDatabase(DATABASE_NAME);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS Question;" + "DROP TABLE IF EXISTS Answer;"
                    + "DROP TABLE IF EXISTS QuestionAnswer");
            onCreate(db);
        }

        private void initDatabase(String dbName) {
            switch(dbName) {
                case "GL.db":
                    Answer noAns = new Answer("La réponse juste n'est pas donnée");
                    Answer allAns = new Answer("Tout les réponses sont juste");

                    addAnswer(noAns);
                    addAnswer(allAns);

                    Answer a1, a2, a3, a4;

                    Question qst;

                    a1 = new Answer("Modéliser un workflow dans un use case ou entre plusieurs use cases");
                    a2 = new Answer("Décrire la logique d'une méthode dans une classe");
                    a3 = new Answer("Modéliser la dynamique d'une tâche en phase de stabilisation d'un diagramme de classes");
                    qst = new Question("Le rôle d'un diagramme d'activités UML est de :", a1);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Permet de décrire les enchaînements possibles entre les scénarios préalablement identifiés");
                    a2 = new Answer("Est une représentation séquentielle du déroulement des traitements et des interactions entre les éléments du système et/ou de ses acteurs");
                    a3 = new Answer("Est une représentation simplifiée d'un enchaînement de séquences se concentrant sur les échanges de messages entre les objets");
                    qst = new Question("Un diagramme de séquence :", a2);

                    createQCM(qst, a1, a2, a3, noAns);


                    a1 = new Answer("Un langage procédural");
                    a2 = new Answer("Un langage objet");
                    a3 = new Answer("Un langage d'expression des contraintes utilisé par le langage UML");
                    qst = new Question("Le langage OCL est :", a3);

                    createQCM(qst, a1, a2, a3, allAns);

                    a1 = new Answer("Les attributs et les méthodes des différentes classes concernées par l'IHM");
                    a2 = new Answer("Les données, la présentation et les traitements de l'IHM concernée");
                    a3 = new Answer("Les différents paquetages manipulant l'IHM concernée");
                    qst = new Question("Le modèle MVC a pour rôle la conception d'IHM en imposant une séparation entre :", a2);

                    createQCM(qst, a1, a2, a3, allAns);

                    a1 = new Answer("Construction de véritables applications métier");
                    a2 = new Answer("Visibilité des services offerts par l'interface de l'extérieur");
                    a3 = new Answer("Possibilité d'implémenter l'héritage multiple pour certains langages de programmation");
                    qst = new Question("L'intérêt de l'utilisation des interfaces dans la POO est :", a3);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Initial");
                    a2 = new Answer("Défini");
                    a3 = new Answer("Reproductible");
                    qst = new Question("Lequel n'est pas un niveau de modèle de qualité CMMI ?", noAns);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Un langage objet");
                    a2 = new Answer("Un ensemble d'outils, concepts et langages pour créer et transformer des modèles");
                    a3 = new Answer("Une nouvelle méthode pour le développement des systèmes multi‐agents");
                    qst = new Question("La démarche MDA est :\n", a3);

                    createQCM(qst, a1, a2, a3, allAns);

                    a1 = new Answer("Entité");
                    a2 = new Answer("Nœud");
                    a3 = new Answer("Objet");
                    a4 = new Answer("Paquetage");
                    qst = new Question("Quel terme ne se rapporte pas à la modélisation d'un diagramme UML ?", a1);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("PUMA");
                    a2 = new Answer("RAD");
                    a3 = new Answer("XP");
                    a4 = new Answer("AXIAL");
                    qst = new Question("Lequel n'est pas une méthode agile ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("C'est une méthode itérative et incrémentale");
                    a2 = new Answer("C'est une méthode pilotée par les risques");
                    a3 = new Answer("C'est une méthode conduite par les cas d'utilisation");
                    qst = new Question("Quel énoncé est faux concernant la méthode de développement logiciel UP ?\n", noAns);

                    createQCM(qst, a1, a2, a3, noAns);


                    a1 = new Answer("Modèle en Y");
                    a2 = new Answer("Modèle en W");
                    a3 = new Answer("Modèle en V");
                    qst = new Question("Lequel n'est pas un modèle de développement d'un projet ?", noAns);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Définir des mesures quantitatives de qualité des produits");
                    a2 = new Answer("Vérifier que tous les produits sont conformes à des critères de qualité");
                    a3 = new Answer("Établir des procédures formelles que doivent respecter les cycles de production et contrôler le respect");
                    qst = new Question("En quoi consiste un plan d'assurance qualité ?", a3);

                    createQCM(qst, a1, a2, a3, allAns);

                    a1 = new Answer("Lorsqu'un objet réagit à un événement, il déclenche en réponse à cet événement une et une seule opération");
                    a2 = new Answer("Contrairement aux événements qui durent, un état est par nature une information instantanée qui doit être traitée sans plus attendre");
                    a3 = new Answer("Tout message est un événement impliqué dans l'interaction de deux objets");
                    qst = new Question("Dans le diagramme État‐transition du langage UML :", noAns);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Séquences");
                    a2 = new Answer("Paquages");
                    a3 = new Answer("Composants");
                    a4 = new Answer("Déploiement");
                    qst = new Question("Un concepteur souhaite décrire l'architecture des codes source, des bibliothèques, des différents fichiers exécutables ainsi que les liens entre eux, lors du développement d'un logiciel, alors il doit utiliser un diagramme de :", a3);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Diagramme d'activités");
                    a2 = new Answer("Diagramme de communication");
                    a3 = new Answer("Diagramme de composants");
                    a4 = new Answer("Diagramme de structure composite");
                    qst = new Question("Lequel de ces diagrammes n'est ni structurel ni statique ?", a1);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Entité est transformée en classe");
                    a2 = new Answer("Association est transformée en classe");
                    a3 = new Answer("Entité est transformée en composition");
                    qst = new Question("Lors du passage du MCD Merise au diagramme de classes UML 2, toute :", a1);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Représente une association symétrique dans laquelle les deux extrémités jouent le même rôle");
                    a2 = new Answer("Implique une coïncidence des durées de vie des objets des deux extrémités : la destruction de l'un implique automatiquement la destruction de l'autre");
                    a3 = new Answer("Représente une association non symétrique dans laquelle une des extrémités joue un rôle prédominant par rapport à l'autre extrémité");
                    qst = new Question("Dans le diagramme de classes du langage UML, une agrégation :", a1);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Est une relation réflexive : une classe peut dériver d'elle‐même");
                    a2 = new Answer("Est une relation transitive : si C dérive d'une classe B qui dérive elle‐même d'une classe A, alors C dérive également de A");
                    a3 = new Answer("Est une relation symétrique : si une classe B dérive d'une classe A, alors la classe A peut dériver de la classe B");
                    a4 = new Answer("Représente une association non symétrique dans laquelle une des extrémités joue un rôle prédominant par rapport à l'autre extrémité");
                    qst = new Question("Dans un diagramme de classes en langage UML, la généralisation :", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Elle peut relier deux acteurs");
                    a2 = new Answer("Elle peut relier deux uses cases");
                    a3 = new Answer("Elle peut relier un use case et un acteur");
                    qst = new Question("Dans un diagramme de Use Case UML, qu'est‐ce qui n'est pas vrai pour la généralisation ?", a3);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Un élément non visible (private)");
                    a2 = new Answer("Un élément visible seulement par les sous‐classes (default)");
                    a3 = new Answer("Un élément visible seulement par les classes du même paquetage (protected)");
                    qst = new Question(" Le symbole « ~ » représente en UML ?", a3);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Facade");
                    a2 = new Answer("Iterator");
                    a3 = new Answer("Prototype");
                    qst = new Question("Quel design pattern fournit une interface unifiée facile à utiliser pour un ensemble d'interfaces dans un sous‐système ?", a1);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("MLD");
                    a2 = new Answer("MCT");
                    a3 = new Answer("MPT");
                    qst = new Question("Dans la méthode Merise le concept de synchronisation est relatif au ?", a2);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Le diagramme de séquence rassemble les cas d'utilisation");
                    a2 = new Answer("La composition est un cas particulier de l'association");
                    a3 = new Answer("Un diagramme de cas d'utilisation est un scénario de tests");
                    a4 = new Answer("Dans l'agrégation, quand on détruit un composé A, tous les composants B sont détruits");
                    qst = new Question("Quel énoncé est vrai à propos des diagrammes UML ?", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("AXIAL");
                    a2 = new Answer("MDA");
                    a3 = new Answer("MERISE");
                    a4 = new Answer("OCL");
                    qst = new Question("Laquelle n'est pas qualifiée comme une méthode de modélisation d'un système ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Il est invoqué dynamiquement par d'autres services");
                    a2 = new Answer("Il est encapsulé dans une couche de standards dérivés du langage XML");
                    a3 = new Answer("Il est déployé sur n'importe quelle plate‐forme");
                    a4 = new Answer("Il est un composant complexe implémenté dans un langage précis");
                    qst = new Question("Quel énoncé est faux à propos de Web Service ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Maintenance évolutive");
                    a2 = new Answer("Maintenance adaptative");
                    a3 = new Answer("Maintenance corrective");
                    a4 = new Answer("Maintenance préventive");
                    qst = new Question("Quel type de maintenance consiste à faire évoluer une application lorsque son environnement change pour assurer sa continuité ?", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("MOE désigne l'entité retenue par le maître d'ouvrage afin de réaliser le projet dans les conditions de délais, de qualité");
                    a2 = new Answer("MOE est l'entité porteuse du besoin, définissant l'objectif du projet, son calendrier et le budget consacré à ce projet");
                    a3 = new Answer("MOA désigne l'entité retenue par le maître d'ouvrage afin de réaliser le projet dans les conditions de délais, de qualité");
                    qst = new Question("Quelle est la différence entre MOA et MOE ?", a1);

                    createQCM(qst, a1, a2, a3, allAns);

                    a1 = new Answer("Merise est une méthode d'analyse, de conception et de gestion de projet intégrée");
                    a2 = new Answer("UML est un langage permettant d'utiliser toute méthode orientée objet");
                    a3 = new Answer("Merise préconise d'analyser séparément données et traitements, à chaque niveau");
                    a4 = new Answer("Merise est beaucoup plus vaste et s'intéresse aux techniques de modélisation des données autant que des traitements dans le paradigme objet");
                    qst = new Question("Quel énoncé est faux concernant la différence entre MERISE et UML ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("La gestion de la disponibilité (Availability Management)");
                    a2 = new Answer("La gestion de la continuité des services (IT Continuity Management)");
                    a3 = new Answer("La gestion financière des services (IT Financial Management)");
                    a4 = new Answer("La gestion des accords de service (Service Level Agreement)");
                    qst = new Question("Quel processus ITIL est responsable de l'affectation des coûts des contrats de sous‐traitance ?", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Une connexion directe entre deux ordinateurs");
                    a2 = new Answer("Une pratique Internet adoptée par des associations");
                    a3 = new Answer("Une pratique commerciale Internet s'adressant aux entreprises");
                    qst = new Question("Qu'est-ce que la B2B ?", a3);

                    createQCM(qst, a1, a2, a3, allAns);

                    a1 = new Answer("Le PCA permet d'éviter une interruption de service qui engendrerait un PRA (reprise)");
                    a2 = new Answer("Le PRA demande une surveillance pour fournir une continuité de service");
                    a3 = new Answer("Le PRA permet d'éviter une interruption de service qui engendrerait un PCA");
                    a4 = new Answer("Le PRA ne doit tolérer aucune interruption de service alors que le PCA est une procédure qui intervient suite à une interruption de service");
                    qst = new Question("Quelle est la différence entre les plans PRA et PCA dans le domaine de sécurité s'un SI ?", a1);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Détecter les virus polymorpohes");
                    a2 = new Answer("Interdire l'accès extérieur à un ordinateur");
                    a3 = new Answer("Filtrer les pourriels et les scams");
                    a4 = new Answer("Détecter et bloquer les spams");
                    qst = new Question("Dans un intranet, un pare-feu permet de :", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("ITIL est un moyen de production");
                    a2 = new Answer("ITIL comprend 7 modules principaux de gestion");
                    a3 = new Answer("ITIL est un référentiel de gestion et de management des systèmes d'information qui s'appuie sur un ensemble de bonnes pratiques");
                    a4 = new Answer("La double démarche en matière d'organisation d'une production de service IT est le fournisseur et producteur");
                    qst = new Question("Quelle affirmation est fausse concernant ITIL ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Infonet");
                    a2 = new Answer("Internet");
                    a3 = new Answer("Intranet");
                    a4 = new Answer("Extranet");
                    qst = new Question("Laquelle est qualifiée comme une extension d'un système d'information de l'entreprise à des partenaires distants ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("2");
                    a2 = new Answer("3");
                    a3 = new Answer("4");
                    a4 = new Answer("5");
                    qst = new Question("Combien de niveaux existe‐t‐il dans le modèle de qualité CMMI ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Une classe abstraite");
                    a2 = new Answer("Un stéréotype de classe");
                    a3 = new Answer("Un composant graphique");
                    a4 = new Answer("Une agrégation composite");
                    qst = new Question("En UML, une interface est :", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Polymorphisme");
                    a2 = new Answer("Protection des variations");
                    a3 = new Answer("Expert");
                    a4 = new Answer("Proxy");
                    qst = new Question("Lequel n'est pas qualifié comme un design pattern GRASP ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Diagramme de timing");
                    a2 = new Answer("Diagramme de paquetage");
                    a3 = new Answer("Diagramme de profile");
                    a4 = new Answer("Diagramme de structure composite");
                    qst = new Question("Quel diagramme n'est ni structurel ni comportemental ?", a3);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Oui, car les données sont toujours sauvegardées");
                    a2 = new Answer("Oui, car on a longtemps échangé des informations pour travailler sans disposer d'informatique");
                    a3 = new Answer("Non, car on ne peut pas échanger des informations sans informatique");
                    a4 = new Answer("Non, car une base de données est nécessaire pour stocker l'information");
                    qst = new Question("Peut‐il exister un système d'information sans équipement informatique ?", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("L'atteinte des objectifs techniques");
                    a2 = new Answer("La détermination des conditions d'acceptation du produit par les utilisateurs de celui‐ci et les parties prenantes");
                    a3 = new Answer("La seule rentabilité financière");
                    qst = new Question("En matière de gestion de projet, qu'est‐ce que la viabilité ?", a2);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("ISO 21500");
                    a2 = new Answer("ISO 25100");
                    a3 = new Answer("ISO 27100");
                    qst = new Question("Quelle norme est relative au gestion de projet ?", a1);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Le SSO est un service d'autorisation");
                    a2 = new Answer("Le SSO est un service d'authentification");
                    a3 = new Answer("Le SSO est un service d'identification");
                    qst = new Question("En informatique, qu'est-ce qu'un SSO ?", a2);

                    createQCM(qst, a1, a2, a3, allAns);

                    a1 = new Answer("Data Mining (fouille de données)");
                    a2 = new Answer("Workflow (flux de travail)");
                    a3 = new Answer("Database sharing (partage de base de données)");
                    a4 = new Answer("Tracking (suivi et traçabilité)");
                    qst = new Question("Quel procédé de gestion n'est pas utilisé par les ERP (Progiciel de Gestion Intégré) ?", a1);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Phishing");
                    a2 = new Answer("Spaming");
                    a3 = new Answer("Social Engineering");
                    a4 = new Answer("Scam");
                    qst = new Question("Une technique consistant à voler des informations de la part des utilisateurs par courrier électronique, téléphone, contact direct ou un site web falsifié s'appelle :", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("La technologie est trés bénéfique, Il facilite les sauvegardes et offre un niveau élevé de performance.");
                    a2 = new Answer("On peut héberger des applications très gourmandes en ressources comme le SGBD.");
                    a3 = new Answer("La technologie est intéressante mais reste coûteuse en terme de temps et de licences");
                    qst = new Question("Quelle affirmation est fausse concernant la technologie de virtualisation ?", noAns);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Intelligence artificielle");
                    a2 = new Answer("Systèmes de gestion de la qualité");
                    a3 = new Answer("Gestion de la relation client (CRM)");
                    qst = new Question("Dans une stratégie de veille stratégique (Business Intelligence), lequel de ces éléments est indispensable ?", noAns);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Applets");
                    a2 = new Answer("Webservices");
                    a3 = new Answer("Composants java d'entreprise (EJBs)");
                    a4 = new Answer("Scripts utilisant des composants (CGI)");
                    qst = new Question("Pour vérifier un serveur Web, un administrateur SI devrait estimer le risque d'accès non autorisé à de l'information confidentielle au plus haut niveau, s'il y a utilisation de ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("vulnerabilité");
                    a2 = new Answer("impact");
                    a3 = new Answer("menace");
                    a4 = new Answer("sinistre");
                    qst = new Question("Le choix de mot de passe faible dans un SI est un exemple de ?", a1);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("5 personnes / 2 ans");
                    a2 = new Answer("10 personnes / 12 mois");
                    a3 = new Answer("120 personnes / 2 semaines");
                    qst = new Question("Une charge de 60 mois/homme signifie ?", a3);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Exprime le besoin des utilisateurs");
                    a2 = new Answer("Exprime les fonctions de service et les contraintes attendues par les utilisateurs");
                    a3 = new Answer("Exprime le procédé de fabrication devant être utilisé pour fabriquer un objet technique");
                    a4 = new Answer("Exprime les solutions techniques retenues pour chaque fonction de service");
                    qst = new Question("Un cahier des charges :", a2);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Modèle en spirale");
                    a2 = new Answer("Modèle en W");
                    a3 = new Answer("Modèle en cascade");
                    a4 = new Answer("Modèle récursif");
                    qst = new Question("Lequel n'est pas un modèle de développement des projets ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Élaboration de plan d'assurance qualité");
                    a2 = new Answer("Planification");
                    a3 = new Answer("Définition du plan de recette");
                    a4 = new Answer("Conception Merise/UML");
                    qst = new Question("Quelle fonction est réalisé par le MOA dans la phase d'expression de besoin ?", a4);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Établir le diagramme de Gantt");
                    a2 = new Answer("Définir les jalons du projet");
                    a3 = new Answer("Établir l'organigramme des tâches");
                    a4 = new Answer("Tracer la logique d'enchaînement de tâches");
                    qst = new Question("La réalisation d'un projet commence par la planification. La première étape de la planification consiste à :", a3);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("Une décomposition hiérarchique des tâches à effectuer pour atteindre l'objectif du projet");
                    a2 = new Answer("La liste des tâches à exécuter chronologiquement selon le planning");
                    a3 = new Answer("Une méthodologie de résolution des problèmes techniques à utiliser par l'équipe projet");
                    a4 = new Answer("Une méthodologie d'ordonnancement sous la forme d'un réseau de tâches du projet");
                    qst = new Question("L'organigramme technique des tâches (WBS) d'un projet est :", a1);

                    createQCM(qst, a1, a2, a3, a4);

                    a1 = new Answer("L'objectif du projet");
                    a2 = new Answer("Au budget du projet");
                    a3 = new Answer("La nature du projet");
                    qst = new Question("Dans un projet, la succession des phases est liée à :", noAns);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Le chef de projet");
                    a2 = new Answer("La direction générale");
                    a3 = new Answer("La direction technique");
                    qst = new Question("Le plan directeur du projet est établi par ?", a1);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("Vérifier que tous les produits sont conformes à des critères de qualité");
                    a2 = new Answer("Définir des mesures quantitatives de qualité des produits");
                    a3 = new Answer("Établir des procédures formelles que doivent respecter les cycles de production et contrôler le respect");
                    qst = new Question("L'assurance qualité consiste à :", a3);

                    createQCM(qst, a1, a2, a3, noAns);

                    a1 = new Answer("L'étude d'opportunité");
                    a2 = new Answer("La recette technique");
                    a3 = new Answer("La recette fonctionnelle");
                    qst = new Question("Quelle phase consiste à contrôler la conformité d'un produit par rapport aux spécifications et critères souhaités ?", a3);

                    createQCM(qst, a1, a2, a3, noAns);
                    break;
            }

        }
    }


    /*======================================
    //  P R I V A T E     M E M B E R S
    ========================================*/
    //Database Members
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private Context mContext;
    private final String DATABASE_NAME;

    //Question Columns
    private static final String QUESTION_TABLE = "Question";
    private static final String questionID = "questionID";
    private static final String questionText = "questionText";

    //Answer Columns
    private static final String ANSWER_TABLE = "Answer";
    private static final String answerID = "answerID";
    private static final String answerText = "answerText";

    private static final String QUESTION_ANSWER = "QuestionAnswer";
    private static final String IS_CORRECT = "IsCorrect";

    /*======================================
   //        C O N S T R U C T O R S
   ========================================*/

    public mcqCTRL(Context context, String dbName){
        mContext      = context;
        DATABASE_NAME = dbName;
    }

    /*======================================
   //  P R I V A T E   M E T H O D S
   ========================================*/


    private int getAnswerID(String text) {
        Cursor cursor = mDb.query(ANSWER_TABLE, new String[]{answerID}, answerText + "=?", new String[]{text},
                null, null, null, null);

        if(cursor.moveToFirst()) {
            int Id = cursor.getInt(0);
            cursor.close();
            return Id;
        }
        else {
            cursor.close();
            return -1;
        }
    }

    private int getQuestionID(String text){
        Cursor c = mDb.query(QUESTION_TABLE, new String[]{questionID}, questionText + "=?", new String[]{text},
                null, null, null, null);

        if(c.moveToFirst())
            return c.getInt(0);
        else
            return -1;
    }

    private Cursor getRowsByRawQuery(String sqlQuery){
        openReadable();

        Cursor cursor = mDb.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst())
            return cursor;

        else
            return null;
    }

    private boolean checkExistence(Question question){
        Cursor c = mDb.query(QUESTION_TABLE, new String[]{questionText}, null,
                null, null, null, null, null);

        assert c != null;

        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(0).equals(question.getText())){
                Log.w("mcqCTRL-Check(Question)", question.getText() + " already exists in the database.");
                return true;
            }

            c.moveToNext();
        }

        c.close();
        return false;
    }

    private boolean checkExistence(Answer answer){
        Cursor c = mDb.query(ANSWER_TABLE, new String[]{answerText}, null,
                null, null, null, null, null);

        assert c != null;

        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(0).equals(answer.getText())){
                Log.w("mcqCTRL-Check(Answer)", answer.getText() + " already exists in the database.");
                c.close();
                return true;
            }

            c.moveToNext();
        }

        c.close();
        return false;
    }

    private int countAnswerExistence(int ID){
        Cursor c = mDb.query(QUESTION_ANSWER, null, answerID +"=?",
                new String[]{String.valueOf(ID)}, null, null, null, null);

        if(c != null) {
            int count = c.getCount();
            c.close();
            return count;
        }

        return -1;
    }


    private void createQCM(Question qst, Answer a1, Answer a2, Answer a3, Answer a4){
        if(checkExistence(qst)){
            Toast t = Toast.makeText(mContext, "La Question existe déja. Veuillez la modifier", Toast.LENGTH_SHORT);
            t.show();
            return;
        }

        addAnswer(a1);
        addAnswer(a2);
        addAnswer(a3);
        addAnswer(a4);

        addQuestion(qst);

        updateMapping(qst, a1, a2, a3, a4);
    }


        private void addQuestion(Question qst){
            ContentValues values = new ContentValues();
            values.put(questionText, qst.getText());

            mDb.insert(QUESTION_TABLE, null, values);
        }

        private void addAnswer(Answer a){
            if(checkExistence(a))
                return;

            ContentValues values = new ContentValues();
            values.put(answerText, a.getText());

            mDb.insert(ANSWER_TABLE, null, values);
        }

        private void updateMapping(Question qst, Answer... answers) {
            int qstID = getQuestionID(qst.getText());

            mDb.delete(QUESTION_ANSWER, questionID +"=?", new String[]{String.valueOf(qstID)});

            ContentValues values = new ContentValues();
            values.put(questionID, qstID);

            for (Answer answer: answers) {
                values.put(answerID, getAnswerID(answer.getText()));

                if(qst.getAnswers().contains(answer))   values.put(IS_CORRECT, 1);

                mDb.insert(QUESTION_ANSWER, null, values);

                values.remove(answerID);
                values.remove(IS_CORRECT);
            }
        }

    private boolean isMapped(Question qst){
        int id = getQuestionID(qst.getText());

        String sql = "SELECT * FROM QuestionAnswer WHERE questionID=" + String.valueOf(id);

        return mDb.rawQuery(sql, null).getCount() > 0;

    }


    private void updateQCM(QCM qcm,
                           int qstID,
                           int ansID1,
                           int ansID2,
                           int ansID3,
                           int ansID4,
                           boolean flag){

        if(!flag)
            if(!updateQuestion(qcm.getQuestion(), qstID)){
                Toast t = Toast.makeText(mContext, "La Question existe déja. Veuillez la modifier", Toast.LENGTH_SHORT);
                t.show();
                return;
            }

            updateAnswer(qcm.getAns1(), ansID1);
            updateAnswer(qcm.getAns2(), ansID2);
            updateAnswer(qcm.getAns3(), ansID3);
            updateAnswer(qcm.getAns4(), ansID4);


        updateMapping(qcm.getQuestion(), qcm.getAns1(), qcm.getAns2(), qcm.getAns3(), qcm.getAns4());
    }

        private boolean updateQuestion(Question qst, int ID) {
        if(checkExistence(qst))
            return false;

        ContentValues values = new ContentValues();
        values.put(questionText, qst.getText());

        mDb.update(QUESTION_TABLE, values, questionID + " =?",
                new String[]{String.valueOf(ID)});

        return true;
    }

        private void updateAnswer(Answer a, int ID) {
            if(getAnswerID(a.getText()) == ID)
                return;


            if(checkExistence(a)) {
                if(countAnswerExistence(ID) <= 1)
                    mDb.delete(ANSWER_TABLE, answerID + "= ?", new String[]{String.valueOf(ID)});

                return;
            }


            ContentValues values = new ContentValues();
            values.put(answerText, a.getText());

            mDb.update(ANSWER_TABLE, values, answerID + " =?",
                    new String[]{String.valueOf(ID)});

        }


    /*======================================
   //  P U B L I C    I N T E R F A C E
   ========================================*/

    /**
     * Opens database for read-write
     * @throws SQLException in case of opening anomalies
     */
    public void openWritable() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
    }

    /**
     * Opens database for read-only
     * @throws SQLException in case of opening anomalies
     */
    public void openReadable() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        mDb       = mDbHelper.getReadableDatabase();
    }

    /**
     * Closes database
     */
    public void close() {
        if (mDbHelper != null)
            mDbHelper.close();
    }

    /**
     * Adds a new QCM to the database
     * @param qcm QCM Object
     */
    public void createQCM(QCM qcm){
        createQCM(qcm.getQuestion(), qcm.getAns1(), qcm.getAns2(), qcm.getAns3(), qcm.getAns4());
    }

    /**
     * Updates the inserted QCM
     * @param oldQCM QCM Object
     */
    public void updateQCM(QCM oldQCM, QCM newQCM, boolean flag){
            updateQCM(newQCM,
                  getQuestionID(oldQCM.getQuestion().getText()),
                  getAnswerID(oldQCM.getAns1().getText()),
                  getAnswerID(oldQCM.getAns2().getText()),
                  getAnswerID(oldQCM.getAns3().getText()),
                  getAnswerID(oldQCM.getAns4().getText()),
                    flag);
    }

    /**
     * Deletes the occurrence of passed Question from database.
     * Answer that are linked to other questions are not deleted.
     * @param qst Question Object
     */

    public void deleteQCM(Question qst){
        int qstID = getQuestionID(qst.getText());

        String query  = "SELECT answerID FROM QuestionAnswer WHERE questionID = " + String.valueOf(qstID);
        //Gets the list of all answers related to the passed question
        Cursor cursor = mDb.rawQuery(query, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            //If answer not linked anymore, delete it
            if (countAnswerExistence(cursor.getInt(0)) <= 1)
                mDb.delete(ANSWER_TABLE, answerID + "=?", new String[]{String.valueOf(cursor.getInt(0))});

            cursor.moveToNext();
        }

        cursor.close();

        //Delete every question-mapping related to the passed question
        mDb.delete(QUESTION_ANSWER, questionID + "=?",
                new String[]{String.valueOf(qstID)}
                );

        //Delete the question
        mDb.delete(QUESTION_TABLE, questionID + "=?", new String[]{String.valueOf(qstID)});
    }


    /**
     * Generates a list of all QCMs presemt in the database
     * @return Collection of QCM Object
     */
    public List<QCM> getAllQCM() {

        String RETRIEVE_ALL_QCM =
                "SELECT Question.questionText, Answer.answerText, Answer.answerID, QuestionAnswer.isCorrect " +
                        "FROM Question INNER JOIN QuestionAnswer ON Question.questionID = QuestionAnswer.questionID " +
                        "INNER JOIN Answer ON QuestionAnswer.answerID = Answer.answerID " +
                        "ORDER BY Question.questionID";

        Cursor cursor = getRowsByRawQuery(RETRIEVE_ALL_QCM);

        if (cursor == null)
            return null;

        List<QCM> qcm = new ArrayList<>();

        Question qst;
        Answer a1, a2, a3, a4;

        while (!cursor.isAfterLast()) {
            qst = new Question(cursor.getString(0));

            a1 = new Answer(cursor.getString(1));
            if (cursor.getInt(3) == 1)
                qst.setAnswers(a1);


            cursor.moveToNext();

            if (!cursor.isAfterLast()) {
                a2 = new Answer(cursor.getString(1));
                if (cursor.getInt(3) == 1)
                    qst.setAnswers(a2);
            } else
                a2 = null;

            cursor.moveToNext();

            if (!cursor.isAfterLast()) {
                a3 = new Answer(cursor.getString(1));
                if (cursor.getInt(3) == 1)
                    qst.setAnswers(a3);
            } else
                a3 = null;

            cursor.moveToNext();

            if (!cursor.isAfterLast()) {
                a4 = new Answer(cursor.getString(1));
                if (cursor.getInt(3) == 1)
                    qst.setAnswers(a4);
            } else
                a4 = null;


            qcm.add(new QCM(qst, a1, a2, a3, a4));
            cursor.moveToNext();
        }

        return qcm;
    }

}



