package org.lema.notasapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.lema.notasapp.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardocordeiro on 22/07/15.
 */
public class AlunoDao extends SQLiteOpenHelper {

    public AlunoDao(Context context) {
        super(context, "notas-app", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Aluno (id PRIMARY KEY, matricula TEXT, senha TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antiga, int nova) {

    }

    public void salvar(Aluno aluno) {
        ContentValues values = new ContentValues();

        values.put("matricula", aluno.getMatricula());
        values.put("senha", aluno.getSenha());

        getWritableDatabase().execSQL("delete from Aluno");
        getWritableDatabase().insert("Aluno", null, values);

    }

    public Aluno getAluno() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM Aluno", null);
        List<Aluno> alunos = new ArrayList<>();
        if(cursor.moveToNext())
            return new Aluno(cursor.getString(cursor.getColumnIndex("matricula")),
                                cursor.getString(cursor.getColumnIndex("senha")));
        return null;
    }
}