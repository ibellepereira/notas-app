## Guia para a tarefa https://trello.com/c/Y63wWF38

O objetivo é consumir a API do uezoapp e conseguir listar os posts. 
Como estamos trabalhando com `Retrofit`, `Dagger` e `EventBus` vamos precisar dos componentes:


Presentation Layer: `FeedActivity` podem haver mais, dependendo de como fizerem. 
Mas é importante que todas herdem de `OAuthActivity` e anotem com `@Subscribe` 
todos os métodos abstratos.

A ação principal (entende-se como recurso a ser consumido na **API**) da tela deve ser reexecutada (retry strategy)
caso haja problema com o AccessToken (se não tiver mais validade, por exemplo). Por isso, o método 
`onReceiveAccessToken` deve chamar o método que executa a lógica.

Exemplo da `SugestaoNomeMateriaActivity`: 

```
@Subscribe
public void onReceiveAccessToken(AccessToken accessToken) {
    sugerir();
}
```

**Business Layer:**

**PostService** = Contrato (interface) de serviço. A implementação dessa interface será gerada em **tempo de execução**

**Integration/Middle Layer:**

**PostModule**: Provedor (factory) de serviços. É usada pelo **Dagger** para instânciar dependências.
Também é responsável por associar o `AccessToken` ao `HttpClient` através do `TokenInterceptor`, 
como exemplo aqui da classe `BoletimModule`:

```
private <T> T getService(Class<T> clazz) {
    httpClient.addInterceptor(new TokenInterceptor(application)); // colocando o TokenInterceptor

    httpClient.connectTimeout(30, TimeUnit.SECONDS);
    httpClient.readTimeout(30, TimeUnit.SECONDS);

    Retrofit retrofit = RetrofitUtils
            .getBuilder()
            .client(httpClient.build())
            .build();

    return retrofit.create(clazz);
}
```

**PostComponent**: Necessário para acionar a injeção através do **Dagger**. 
A implementação dessa interface será gerada em **tempo de construção**.

**DaggerPostComponent**: Após construir o projeto, essa classe acionará a injeção de dependências.

