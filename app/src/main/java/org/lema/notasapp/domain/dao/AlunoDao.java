package org.lema.notasapp.domain.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Aluno;
import org.lema.notasapp.infra.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardocordeiro on 22/07/15.
 */
public class AlunoDao {

    private DatabaseHelper helper;

    public AlunoDao(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void salvarAluno(Aluno aluno) {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("matricula", aluno.getMatricula());
        values.put("senha", aluno.getSenha());
        values.put("nome", aluno.getNome());

        db.execSQL("delete from Aluno");
        db.insert("Aluno", null, values);

    }

    public Aluno obterAlunoLogado() {

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Aluno", null);

        if(cursor.moveToNext())
            return new Aluno(cursor.getString(cursor.getColumnIndex("matricula")),
                                cursor.getString(cursor.getColumnIndex("senha")));
        return null;
    }

}