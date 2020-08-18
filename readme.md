## Vesti UI Test

> Projeto para teste funcional de interface utilizando selenium.

### Atenção

> :warning: **Sistema Operacional**: caso não tenha configurado na sua maquina as variáveis de ambiente para acesso da AWS ou da Testing Bot será executado localmente.

> :warning: **Chrome**: como o projeto não esta utilizando o driver standalone é necessário ter instalado o Chrome na maquina com a versão > `83.0`. 

### Como rodar

#### Editores
- O projeto esta utilizando Lombok [documentação](https://projectlombok.org/). Por isso você precisa ativar o `Annotation processor` no seu editor.  
	- Tutorial para ativar no [intelij e eclipese](https://www.baeldung.com/lombok-ide).

#### Via terminal
- Baixar dependências:
```ssh 
./gradlew dependencies
```
- Executar testes:
```ssh 
./gradlew test
``` 

#### Via AWS Farm

- Configurar `RUNNER`:

    * Deve criar uma variável de ambiente chamada `RUNNER` e setar o valor `AWS`, assim você esta definindo que os testes serão executados utilizando a AWS.

- Configurar credenciais de segurança:

    * Para a variavel de ambiente `AWS_ACESS_KEY` você deve setar a sua acess key gerada na AWS.
    * Para a variavel de ambiente `AWS_SECRET_KEY` você deve setar a sua secret key gerada na AWS.
    * Para a variavel de ambiente `AWS_DEVICE_FARM_ARN` você deve copiar o ARN do seu projeto.

> Pronto agora você estar preparado para executar os testes utilizando a AWS Farm.

#### Via Testing Bot

- Configurar `RUNNER`:

    * Deve criar uma variável de ambiente chamada `RUNNER` e setar o valor `TESTING_BOT`, assim você esta definindo que os testes serão executados utilizando o Testing Bot.


- Configurar credenciais de segurança:

    * Para a variável de ambiente `TESTING_BOOT_KEY` você deve setar a sua acess key gerada na Testing Bot.
    * Para a variável de ambiente `TESTING_BOOT_SECRET` você deve setar a sua secret key gerada na Testing Bot.
  