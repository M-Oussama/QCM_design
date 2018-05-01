package com.univ_setif.fsciences.qcm.control;

import android.content.Context;

import com.univ_setif.fsciences.qcm.entity.QCM;
import com.univ_setif.fsciences.qcm.entity.UserLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class UserLogCTRL {

    private static final String TAG = "UserLogCTRL";

    private final File log;
    private final AppendingObjectOutputStream logWriter;
    private final ObjectInputStream logReader;

    public UserLogCTRL(Context context) throws IOException {
        log = new File(context.getFilesDir(), "user_sessions.log");

        if(!log.exists()){
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(log));
            oos.flush();
            oos.close();
        }

        logWriter = new AppendingObjectOutputStream(new FileOutputStream(log, true));
        logReader = new ObjectInputStream(new FileInputStream(log));
    }

    /**
     * Appends to the log file
     *
     * @param date        String
     * @param note        Double
     * @param elapsedTime String
     * @param nbrQCM      int
     * @param qcmList     ArrayList of QCM
     * @param answers     ArrayList()
     * @throws IOException in case of file anomalies
     */
    public void logSession(
            String date,
            Double note,
            String elapsedTime,
            int nbrQCM,
            ArrayList<QCM> qcmList,
            ArrayList[] answers
    ) throws IOException {

        UserLog log = new UserLog(date, note, elapsedTime, nbrQCM, qcmList, answers);
        logWriter.writeUnshared(log);
        logWriter.flush();
        logWriter.close();

    }


    public UserLog getLogLine() throws IOException, ClassNotFoundException {
        UserLog logLine;
        logLine = (UserLog) logReader.readObject();
        logReader.close();

        return logLine;
    }

    public ArrayList<UserLog> getLog(){
        ArrayList<UserLog> userLog = new ArrayList<>();
        boolean keepReading = true;

        if(log.exists()) {
            while (keepReading) {
                UserLog logLine = null;
                try {
                    logLine = (UserLog) logReader.readObject();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

                if (logLine != null)
                    userLog.add(logLine);
                else
                    keepReading = false;
            }
        }
        return userLog;
    }

    public void deleteLog(int position) {
        ArrayList<UserLog> userLogs = getLog();
        userLogs.remove(position);
        ObjectOutputStream oop = null;
        try {
            oop = new ObjectOutputStream(new FileOutputStream(log, false));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (UserLog line: userLogs) {
            try {
                oop.writeUnshared(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            oop.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * An object output stream that allows appending to a file without header errors
     * (StreamCorruptedException)
     */
    private class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        protected AppendingObjectOutputStream() throws IOException, SecurityException {

        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }


}

