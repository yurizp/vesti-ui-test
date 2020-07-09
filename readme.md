## Vesti UI Test

> Projeto para teste funcional de interface utilizando selenium.

### Atenção


> :warning: **Sistema Operacional**: O projeto esta apenas com o webdriver do chrome para linux, então não esta rodando em outros sistemas operacionais.

> :warning: **Chrome**: Como o projeto não esta utilizando o driver standalone é necessário ter instalado o Chrome na maquina com a versão > `83.0`. 

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

#### Pendências 
- [ ] Rodar com drivers standalone
- [ ] Rodar em alguma ferramenta de CI