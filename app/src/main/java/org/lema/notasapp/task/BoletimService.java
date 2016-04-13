package org.lema.notasapp.task;

import org.lema.notasapp.Enderecos;
import org.lema.notasapp.client.WebClient;
import org.lema.notasapp.modelo.Aluno;

import rx.Observable;
import rx.functions.Func0;


/**
 * Created by leonardocordeiro on 21/07/15.
 */
public class BoletimService {

    public Observable<String> buscar(final Aluno aluno) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                WebClient client = new WebClient(Enderecos.NOTAS_SERVICO_PATH +
                                                aluno.getMatricula() + "/" +
                                                aluno.getSenha());

                return Observable.just(client.get());
            }
        });

    }

}