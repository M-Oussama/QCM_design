package com.univ_setif.fsciences.qcm.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
     * SQLite Database Helper class used to create and update database
     */
    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_QUESTION);
            db.execSQL(CREATE_ANSWER);
            db.execSQL(CREATE_QUESTIONANSWER);
            mDb = db;

            initDatabase(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS Question;" + "DROP TABLE IF EXISTS Answer;"
                    + "DROP TABLE IF EXISTS QuestionAnswer");
            onCreate(db);
        }

        private void initDatabase(SQLiteDatabase db) {
            Answer noAns = new Answer("La réponse juste n'est pas donnée");

            Answer a1, a2, a3;

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

            createQCM(qst, a1, a2, a3, noAns);

            a1 = new Answer("Les attributs et les méthodes des différentes classes concernées par l'IHM");
            a2 = new Answer("Les données, la présentation et les traitements de l'IHM concernée");
            a3 = new Answer("Les différents paquetages manipulant l'IHM concernée");
            qst = new Question("Le modèle MVC a pour rôle la conception d'IHM en imposant une séparation entre :", a2);

            createQCM(qst, a1, a2, a3, noAns);

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

            createQCM(qst, a1, a2, a3, noAns);

            a1 = new Answer("Entité");
            a2 = new Answer("Nœud");
            a3 = new Answer("Objet");
            qst = new Question("Quel terme ne se rapporte pas à la modélisation d'un diagramme UML ?", a1);

            createQCM(qst, a1, a2, a3, noAns);

            a1 = new Answer("PUMA");
            a2 = new Answer("RAD");
            a3 = new Answer("XP");
            qst = new Question("Lequel n'est pas une méthode agile ?", noAns);

            createQCM(qst, a1, a2, a3, noAns);

//10
            a1 = new Answer("C'est une méthode itérative et incrémentale");
            a2 = new Answer("C'est une méthode pilotée par les risques");
            a3 = new Answer("C'est une méthode conduite par les cas d'utilisation");
            qst = new Question("Quel énoncé est faux concernant la méthode de développement logiciel UP ?\n", noAns);

            createQCM(qst, a1, a2, a3, noAns);


        /*
        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question(qstID, "", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

//20
        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);

        a1 = new Answer("");
        a2 = new Answer("");
        a3 = new Answer("");
        qst = new Question("", );

        createQCM(qst, a1, a2, a3, noAns);
        */
        }
    }


    /*======================================
    //  P R I V A T E     M E M B E R S
    ========================================*/
    //Database Members
    private static DatabaseHelper mDbHelper;
    private static SQLiteDatabase mDb;
    private final Context mContext;
    private static final String DATABASE_NAME = "qcm.db";




    /*======================================
   //  P U B L I C    M E M B E R S
   ========================================*/
    //Question Columns
    public static final String QUESTION_TABLE = "Question";
    public static final String questionID = "questionID";
    public static final String questionText = "questionText";
    public static final String CorrectAnswerID = "CorrectAnswerID";
    //Answer Columns
    public static final String ANSWER_TABLE = "Answer";
    public static final String answerID = "answerID";
    public static final String answerText = "answerText";

    public static final String QUESTION_ANSWER = "QuestionAnswer";


    /*=========================================
    //  S Q L    D D L     S T A T E M E N T S
    ===========================================*/
    private static final String CREATE_QUESTION =
            "CREATE TABLE Question(\n" +
                    questionID      + " INTEGER PRIMARY KEY autoincrement,\n" +
                    questionText    + " VARCHAR(100) NOT NULL,\n" +
                    CorrectAnswerID + " INTEGER NOT NULL,\n" +
                    "    FOREIGN KEY("+ CorrectAnswerID + ") REFERENCES Answer("+answerID+")\n" +
                    ");\n";

    private static final String CREATE_ANSWER =
            "CREATE TABLE Answer(\n" +
                    answerID +" INTEGER PRIMARY KEY autoincrement,\n" +
                    answerText + " VARCHAR(50)\n" +
                    ");\n";

    private static final String CREATE_QUESTIONANSWER=
            "CREATE TABLE QuestionAnswer(\n" +
                    questionID +" INTEGER,\n" +
                    answerID +" INTEGER, \n" +
                    "PRIMARY KEY("+questionID+", "+answerID+"), \n"+
                    "FOREIGN KEY("+questionID+") REFERENCES Question("+questionID+"), \n" +
                    "FOREIGN KEY("+answerID+") REFERENCES Answer("+answerID+")\n" +
                    ");\n";

    private static final String RETRIEVE_ALL_QCM =
            "SELECT Question.questionText, Question.CorrectAnswerID, Answer.answerText, Answer.answerID " +
                    "FROM Question INNER JOIN QuestionAnswer ON Question.questionID = QuestionAnswer.questionID " +
                                  "INNER JOIN Answer ON QuestionAnswer.answerID = Answer.answerID";



    /*======================================
   //        C O N S T R U C T O R S
   ========================================*/

    public mcqCTRL(Context context){
        mContext = context;
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

    private boolean checkExistence(Question question){
        Cursor c = mDb.query(QUESTION_TABLE, new String[]{questionText}, null,
                null, null, null, null, null);

        assert c != null;

        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(0).equals(question.getText())){
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
        c.close();
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
            values.put(CorrectAnswerID, getAnswerID(qst.getAnswer().getText()));

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

            String IDchar = String.valueOf(getQuestionID(qst.getText()));

            mDb.delete(QUESTION_ANSWER, questionID + "=?", new String[]{IDchar});

            ContentValues values = new ContentValues();
            values.put(questionID, getQuestionID(qst.getText()));

            for (Answer answer: answers) {
                if(answer != null) {
                    values.put(answerID, getAnswerID(answer.getText()));
                    mDb.insert(QUESTION_ANSWER, null, values);

                    values.remove(answerID);
                }
        }
    }


    private void updateQCM(QCM qcm,
                           int qstID,
                           int ansID1,
                           int ansID2,
                           int ansID3,
                           int ansID4,
                           boolean flag){

        boolean updateRelationship = false;

        if(!flag)
            if(!updateQuestion(qcm.getQuestion(), qstID)){
                Toast t = Toast.makeText(mContext, "La Question existe déja. Veuillez la modifier", Toast.LENGTH_SHORT);
                t.show();
                return;
            }

        updateCorrectAnswer(qcm.getQuestion(), qstID);


            updateRelationship = updateAnswer(qcm.getAns1(), ansID1);
            updateRelationship = updateAnswer(qcm.getAns2(), ansID2);
            updateRelationship = updateAnswer(qcm.getAns3(), ansID3);
            updateRelationship = updateAnswer(qcm.getAns4(), ansID4);

        if(updateRelationship)
            updateMapping(qcm.getQuestion(), qcm.getAns1(), qcm.getAns2(), qcm.getAns3(), qcm.getAns4());
    }

    private void updateCorrectAnswer(Question question, int qstID) {
        ContentValues values = new ContentValues();
        values.put(CorrectAnswerID, getAnswerID(question.getAnswer().getText()));
        mDb.update(QUESTION_TABLE, values, questionID + "=?", new String[]{String.valueOf(qstID)});
    }


    private boolean updateQuestion(Question qst, int ID) {
        if(checkExistence(qst))
            return false;

        ContentValues values = new ContentValues();
        values.put(questionText, qst.getText());
        values.put(CorrectAnswerID, getAnswerID(qst.getAnswer().getText()));

        mDb.update(QUESTION_TABLE, values, questionID + " =?",
                new String[]{String.valueOf(ID)});
        updateCorrectAnswer(qst, ID);

        return true;
    }

        private boolean updateAnswer(Answer a, int ID) {
            if(getAnswerID(a.getText()) == ID)
                return false;


            if(checkExistence(a)) {
                if(countAnswerExistence(ID) <= 1)
                    mDb.delete(ANSWER_TABLE, answerID + "= ?", new String[]{String.valueOf(ID)});

                    return true;
            }


            ContentValues values = new ContentValues();
            values.put(answerText, a.getText());

            mDb.update(ANSWER_TABLE, values, answerID + " =?",
                    new String[]{String.valueOf(ID)});

            return false;
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
     * Interrogates the database using an SQL Query
     * @param sqlQuery query
     * @return cursor of the query result
     */
    private Cursor getRowsByRawQuery(String sqlQuery){
        openReadable();

        Cursor cursor = mDb.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst())
            return cursor;

        else
            return null;
    }

    /**
     * Generates a list of all QCMs presemt in the database
     * @return Collection of QCM Object
     */
    public List<QCM> getAllQCM() {

        Cursor cursor = getRowsByRawQuery(RETRIEVE_ALL_QCM);

        if (cursor == null)
            return null;

        List<QCM> qcm = new ArrayList<>();

        Question qst;
        Answer a1, a2, a3, a4;

        int counter = 0;

        while (!cursor.isAfterLast()) {
            qst = new Question(cursor.getString(0));

            a1 = new Answer(cursor.getString(2));
            if (cursor.getInt(1) == cursor.getInt(3))
                qst.setAnswer(a1);


            cursor.moveToNext();

            if (!cursor.isAfterLast()) {
                a2 = new Answer(cursor.getString(2));
                if (cursor.getInt(1) == cursor.getInt(3))
                    qst.setAnswer(a2);
            } else
                a2 = null;

            cursor.moveToNext();

            if (!cursor.isAfterLast()) {
                a3 = new Answer(cursor.getString(2));
                if (cursor.getInt(1) == cursor.getInt(3))
                    qst.setAnswer(a3);
            } else
                a3 = null;

            cursor.moveToNext();

            if (!cursor.isAfterLast()) {
                a4 = new Answer(cursor.getString(2));
                if (cursor.getInt(1) == cursor.getInt(3))
                    qst.setAnswer(a4);
            } else
                a4 = null;


            qcm.add(new QCM(qst, a1, a2, a3, a4));
            counter++;
            cursor.moveToNext();
        }

        return qcm;
    }

}



