package org.lema.notasapp.domain.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.lema.notasapp.R;
import org.lema.notasapp.domain.model.Aluno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardocordeiro on 22/07/15.
 */
public class AlunoDao extends SQLiteOpenHelper {

    private Context context;

    public AlunoDao(Context context) {
        super(context, "notas-app", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Aluno (id PRIMARY KEY, matricula TEXT, senha TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antiga, int nova) {

    }

    public void salvarAlunoDoLogin(Aluno aluno) {
        ContentValues values = new ContentValues();

        values.put("matricula", aluno.getMatricula());
        values.put("senha", aluno.getSenha());

        getWritableDatabase().execSQL("delete from Aluno");
        getWritableDatabase().insert("Aluno", null, values);

    }

    public Aluno obterAlunoLogado() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM Aluno", null);
        List<Aluno> alunos = new ArrayList<>();
        if(cursor.moveToNext())
            return new Aluno(cursor.getString(cursor.getColumnIndex("matricula")),
                                cursor.getString(cursor.getColumnIndex("senha")));
        return null;
    }

    public boolean obterSenhaSalva() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.preference_save), false);
    }

    public void salvarSenha(boolean entrarAutomaticamente){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.preference_user_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(context.getString(R.string.preference_save), entrarAutomaticamente);
        editor.commit();
    }


}