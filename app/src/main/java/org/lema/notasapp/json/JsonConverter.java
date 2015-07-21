package org.lema.notasapp.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lema.notasapp.modelo.Materia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class JsonConverter {

    private final String json;

    public JsonConverter(String json) {
        this.json = json;
    }

    public List<Materia> toList() {
        List<Materia> materias = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("materias");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject materiaJson = jsonArray.getJSONObject(i);

                Materia materia = new Materia(materiaJson.getString("nome"),
                                              materiaJson.getDouble("av1"),
                                              materiaJson.getDouble("av2"),
                                              materiaJson.getDouble("av3"),
                                              materiaJson.getDouble("media"));

                materias.add(materia);

            }

            return materias;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
